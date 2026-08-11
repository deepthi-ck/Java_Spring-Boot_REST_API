package fixture;

public class RetailOrderProcessor {
    public double processOrder(int quantity, double unitPrice, double discountPct) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
        double subtotal = quantity * unitPrice;
        double discount = subtotal * (discountPct / 100);
        double total = subtotal - discount;
        double tax = total * 0.08;
        return total + tax;
    }
}
