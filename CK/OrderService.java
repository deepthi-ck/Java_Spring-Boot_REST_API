package fixture;

public class OrderService {
    private int orderCount;

    public void addOrder() {
        orderCount++;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public boolean isEmpty() {
        return orderCount == 0;
    }
}
