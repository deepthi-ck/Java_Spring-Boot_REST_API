package com.example.restapi.service;

import com.example.restapi.dto.OrderLineRequest;
import com.example.restapi.dto.OrderRequest;
import com.example.restapi.exception.BadRequestException;
import com.example.restapi.exception.ResourceNotFoundException;
import com.example.restapi.model.Item;
import com.example.restapi.model.Order;
import com.example.restapi.model.OrderLine;
import com.example.restapi.repository.OrderRepository;
import com.example.restapi.util.MoneyUtils;
import com.example.restapi.util.OrderStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final CustomerService customerService;
    private final PricingService pricingService;

    public OrderService(OrderRepository orderRepository, ItemService itemService,
                        CustomerService customerService, PricingService pricingService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
        this.customerService = customerService;
        this.pricingService = pricingService;
    }

    public List<Order> findAll() { return orderRepository.findAll(); }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
    }

    public List<Order> findByCustomer(Long customerId) {
        customerService.findById(customerId);
        return orderRepository.findByCustomerId(customerId);
    }

    public Order create(OrderRequest request) {
        customerService.findById(request.getCustomerId());
        List<OrderLine> lines = new ArrayList<>();
        for (OrderLineRequest lineRequest : request.getLines()) {
            Item item = itemService.requireItem(lineRequest.getItemId());
            if (item.getStock() < lineRequest.getQuantity()) {
                throw new BadRequestException("Insufficient stock for item " + item.getSku());
            }
            item.setStock(item.getStock() - lineRequest.getQuantity());
            lines.add(new OrderLine(item.getId(), item.getName(), lineRequest.getQuantity(), item.getPrice()));
        }
        double subtotal = lines.stream().mapToDouble(OrderLine::lineTotal).sum();
        double total = pricingService.finalizeTotal(subtotal);

        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setStatus(OrderStatus.NEW.name());
        order.setLines(lines);
        order.setTotalAmount(MoneyUtils.round(total));
        order.setPlacedAt(Instant.now());
        return orderRepository.save(order);
    }

    public Order updateStatus(Long id, String status) {
        Order order = findById(id);
        OrderStatus parsed = OrderStatus.from(status);
        order.setStatus(parsed.name());
        return orderRepository.save(order);
    }

    public long count() { return orderRepository.count(); }
}
