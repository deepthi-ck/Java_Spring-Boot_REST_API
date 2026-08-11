package fixture;

public class PmdViolations {
    public int riskyDivide(int a, int b) {
        try {
            return a / b;
        } catch (ArithmeticException e) {
            // empty catch block - PMD EmptyCatchBlock
        }
        int unusedLocal = 42;
        return 0;
    }
}
