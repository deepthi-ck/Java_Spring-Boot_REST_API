package fixture;

public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public double divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("cannot divide by zero");
        }
        return (double) a / b;
    }
}
