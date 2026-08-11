package fixture;

public class DataFlowSample {
    public int processOrder(int sku, int quantity, int price) {
        int unusedNote = -1; // dead definition
        int total = quantity * price;
        if (total > 1000) {
            return total - 50;
        }
        return total;
    }
}
