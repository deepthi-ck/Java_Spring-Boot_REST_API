(function () {
  const API = "";

  function $(sel, root) { return (root || document).querySelector(sel); }
  function $all(sel, root) { return Array.prototype.slice.call((root || document).querySelectorAll(sel)); }

  function currentPage() {
    const path = window.location.pathname || "/";
    const name = path.split("/").pop() || "index.html";
    return name === "" ? "index.html" : name;
  }

  function markActiveNav() {
    const page = currentPage();
    $all(".nav a").forEach(function (a) {
      const href = (a.getAttribute("href") || "").split("/").pop();
      if (href === page || (page === "" && href === "index.html")) {
        a.classList.add("active");
      }
    });
  }

  function showFlash(message, ok) {
    const el = $("#flash");
    if (!el) return;
    el.textContent = message;
    el.className = "flash show " + (ok ? "ok" : "err");
  }

  async function api(path, options) {
    const opts = options || {};
    const headers = Object.assign({ "Accept": "application/json" }, opts.headers || {});
    if (opts.body && !headers["Content-Type"]) {
      headers["Content-Type"] = "application/json";
    }
    const res = await fetch(API + path, Object.assign({}, opts, { headers: headers }));
    const text = await res.text();
    let data = null;
    if (text) {
      try { data = JSON.parse(text); } catch (e) { data = text; }
    }
    if (!res.ok) {
      var msg = "Request failed (" + res.status + ")";
      if (data && data.message) msg = data.message;
      else if (data && data.error) msg = data.error;
      else if (typeof data === "string") msg = data;
      throw new Error(msg);
    }
    return data;
  }

  function money(n) {
    var v = Number(n || 0);
    return v.toLocaleString(undefined, { style: "currency", currency: "USD" });
  }

  async function loadHealthBadge() {
    const badge = $("#runtime-badge");
    if (!badge) return;
    try {
      const h = await api("/api/health");
      badge.textContent = "Service " + (h.status || "UP") + " · Java " + (h.javaVersion || "?");
    } catch (e) {
      badge.textContent = "Service unreachable";
    }
  }

  async function loadDashboard() {
    const box = $("#dashboard-stats");
    if (!box) return;
    try {
      const s = await api("/api/stats/summary");
      box.innerHTML =
        card("Customers", s.customerCount) +
        card("Catalog Items", s.itemCount) +
        card("Orders", s.orderCount) +
        card("Inventory Value", money(s.inventoryValue));
    } catch (e) {
      box.innerHTML = '<div class="empty">' + e.message + "</div>";
    }
  }

  function card(label, value) {
    return '<div class="card"><div class="stat-label">' + label + '</div><div class="stat-value">' + value + "</div></div>";
  }

  function escapeHtml(s) {
    return String(s == null ? "" : s)
      .replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;")
      .replace(/"/g, "&quot;");
  }

  async function loadProducts() {
    const tbody = $("#products-body");
    if (!tbody) return;
    try {
      const rows = await api("/api/products");
      if (!rows.length) {
        tbody.innerHTML = '<tr><td colspan="5" class="empty">No products yet. Create one using the form.</td></tr>';
        return;
      }
      tbody.innerHTML = rows.map(function (p) {
        return "<tr>" +
          "<td>" + escapeHtml(p.id) + "</td>" +
          "<td>" + escapeHtml(p.title) + "</td>" +
          "<td>" + escapeHtml(p.description || "—") + "</td>" +
          "<td>" + money(p.unitPrice) + "</td>" +
          "<td>" + (p.active ? "Active" : "Inactive") + "</td>" +
          "</tr>";
      }).join("");
    } catch (e) {
      tbody.innerHTML = '<tr><td colspan="5" class="empty">' + escapeHtml(e.message) + "</td></tr>";
    }
  }

  async function loadCustomers() {
    const tbody = $("#customers-body");
    if (!tbody) return;
    try {
      const rows = await api("/api/customers");
      if (!rows.length) {
        tbody.innerHTML = '<tr><td colspan="5" class="empty">No customers yet. Register a customer to begin.</td></tr>';
        return;
      }
      tbody.innerHTML = rows.map(function (c) {
        var addr = c.address ? [c.address.city, c.address.country].filter(Boolean).join(", ") : "—";
        return "<tr>" +
          "<td>" + escapeHtml(c.id) + "</td>" +
          "<td>" + escapeHtml(c.fullName) + "</td>" +
          "<td>" + escapeHtml(c.email) + "</td>" +
          "<td>" + escapeHtml(c.phone || "—") + "</td>" +
          "<td>" + escapeHtml(addr) + "</td>" +
          "</tr>";
      }).join("");
    } catch (e) {
      tbody.innerHTML = '<tr><td colspan="5" class="empty">' + escapeHtml(e.message) + "</td></tr>";
    }
  }

  async function loadCategories() {
    const tbody = $("#categories-body");
    if (!tbody) return;
    try {
      const rows = await api("/api/categories");
      if (!rows.length) {
        tbody.innerHTML = '<tr><td colspan="3" class="empty">No categories yet. Add a catalog category first.</td></tr>';
        return;
      }
      tbody.innerHTML = rows.map(function (c) {
        return "<tr><td>" + escapeHtml(c.id) + "</td><td>" + escapeHtml(c.name) + "</td><td>" + escapeHtml(c.code) + "</td></tr>";
      }).join("");
    } catch (e) {
      tbody.innerHTML = '<tr><td colspan="3" class="empty">' + escapeHtml(e.message) + "</td></tr>";
    }
  }

  async function loadItems() {
    const tbody = $("#items-body");
    if (!tbody) return;
    try {
      const rows = await api("/api/items");
      if (!rows.length) {
        tbody.innerHTML = '<tr><td colspan="5" class="empty">No inventory items yet. Create an item with SKU and stock.</td></tr>';
        return;
      }
      tbody.innerHTML = rows.map(function (i) {
        return "<tr>" +
          "<td>" + escapeHtml(i.id) + "</td>" +
          "<td>" + escapeHtml(i.name) + "</td>" +
          "<td>" + escapeHtml(i.sku) + "</td>" +
          "<td>" + money(i.price) + "</td>" +
          "<td>" + escapeHtml(i.stock) + "</td>" +
          "</tr>";
      }).join("");
    } catch (e) {
      tbody.innerHTML = '<tr><td colspan="5" class="empty">' + escapeHtml(e.message) + "</td></tr>";
    }
  }

  async function loadOrders() {
    const tbody = $("#orders-body");
    if (!tbody) return;
    try {
      const rows = await api("/api/orders");
      if (!rows.length) {
        tbody.innerHTML = '<tr><td colspan="5" class="empty">No orders yet. Place an order after creating customers and items.</td></tr>';
        return;
      }
      tbody.innerHTML = rows.map(function (o) {
        var lines = (o.lines || []).length;
        return "<tr>" +
          "<td>" + escapeHtml(o.id) + "</td>" +
          "<td>" + escapeHtml(o.customerId) + "</td>" +
          "<td>" + escapeHtml(o.status || "—") + "</td>" +
          "<td>" + escapeHtml(lines) + "</td>" +
          "<td>" + money(o.totalAmount || o.total || 0) + "</td>" +
          "</tr>";
      }).join("");
    } catch (e) {
      tbody.innerHTML = '<tr><td colspan="5" class="empty">' + escapeHtml(e.message) + "</td></tr>";
    }
  }

  async function loadStatsPage() {
    const box = $("#stats-detail");
    if (!box) return;
    try {
      const s = await api("/api/stats/summary");
      const h = await api("/api/health");
      box.innerHTML =
        '<div class="grid stats">' +
        card("Customers", s.customerCount) +
        card("Items", s.itemCount) +
        card("Orders", s.orderCount) +
        card("Inventory Value", money(s.inventoryValue)) +
        "</div>" +
        '<div class="card"><h3>Runtime</h3><p>Status: <strong>' + escapeHtml(h.status) +
        "</strong><br>Java: <strong>" + escapeHtml(h.javaVersion) +
        "</strong><br>Service: <strong>" + escapeHtml(h.service) + "</strong></p></div>";
    } catch (e) {
      box.innerHTML = '<div class="empty">' + escapeHtml(e.message) + "</div>";
    }
  }

  function bindProductForm() {
    const form = $("#product-form");
    if (!form) return;
    form.addEventListener("submit", async function (ev) {
      ev.preventDefault();
      try {
        const body = {
          title: $("#product-title").value.trim(),
          description: $("#product-description").value.trim(),
          unitPrice: Number($("#product-price").value),
          active: $("#product-active").checked
        };
        await api("/api/products", { method: "POST", body: JSON.stringify(body) });
        form.reset();
        $("#product-active").checked = true;
        showFlash("Product created successfully.", true);
        await loadProducts();
      } catch (e) { showFlash(e.message, false); }
    });
  }

  function bindCustomerForm() {
    const form = $("#customer-form");
    if (!form) return;
    form.addEventListener("submit", async function (ev) {
      ev.preventDefault();
      try {
        const body = {
          fullName: $("#customer-name").value.trim(),
          email: $("#customer-email").value.trim(),
          phone: $("#customer-phone").value.trim(),
          line1: $("#customer-line1").value.trim(),
          city: $("#customer-city").value.trim(),
          state: $("#customer-state").value.trim(),
          postalCode: $("#customer-postal").value.trim(),
          country: $("#customer-country").value.trim()
        };
        await api("/api/customers", { method: "POST", body: JSON.stringify(body) });
        form.reset();
        showFlash("Customer registered successfully.", true);
        await loadCustomers();
      } catch (e) { showFlash(e.message, false); }
    });
  }

  function bindCategoryForm() {
    const form = $("#category-form");
    if (!form) return;
    form.addEventListener("submit", async function (ev) {
      ev.preventDefault();
      try {
        const body = {
          name: $("#category-name").value.trim(),
          code: $("#category-code").value.trim().toUpperCase()
        };
        await api("/api/categories", { method: "POST", body: JSON.stringify(body) });
        form.reset();
        showFlash("Category created successfully.", true);
        await loadCategories();
      } catch (e) { showFlash(e.message, false); }
    });
  }

  function bindItemForm() {
    const form = $("#item-form");
    if (!form) return;
    form.addEventListener("submit", async function (ev) {
      ev.preventDefault();
      try {
        const cat = $("#item-category").value.trim();
        const body = {
          name: $("#item-name").value.trim(),
          sku: $("#item-sku").value.trim().toUpperCase(),
          price: Number($("#item-price").value),
          stock: Number($("#item-stock").value)
        };
        if (cat) body.categoryId = Number(cat);
        await api("/api/items", { method: "POST", body: JSON.stringify(body) });
        form.reset();
        showFlash("Inventory item created successfully.", true);
        await loadItems();
      } catch (e) { showFlash(e.message, false); }
    });
  }

  function bindOrderForm() {
    const form = $("#order-form");
    if (!form) return;
    form.addEventListener("submit", async function (ev) {
      ev.preventDefault();
      try {
        const body = {
          customerId: Number($("#order-customer").value),
          lines: [{
            itemId: Number($("#order-item").value),
            quantity: Number($("#order-qty").value)
          }]
        };
        await api("/api/orders", { method: "POST", body: JSON.stringify(body) });
        form.reset();
        showFlash("Order placed successfully.", true);
        await loadOrders();
      } catch (e) { showFlash(e.message, false); }
    });
  }

  document.addEventListener("DOMContentLoaded", function () {
    markActiveNav();
    loadHealthBadge();
    loadDashboard();
    loadProducts();
    loadCustomers();
    loadCategories();
    loadItems();
    loadOrders();
    loadStatsPage();
    bindProductForm();
    bindCustomerForm();
    bindCategoryForm();
    bindItemForm();
    bindOrderForm();
  });
})();