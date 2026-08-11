package fixture;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    @Test
    void addReturnsSum() {
        assertEquals(5, new Calculator().add(2, 3));
    }

    @Test
    void isEvenDetectsParity() {
        assertTrue(new Calculator().isEven(4));
        assertFalse(new Calculator().isEven(3));
    }
}
