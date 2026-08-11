package fixture;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataFlowSampleTest {
    @Test
    void largeOrderAppliesDiscount() {
        assertEquals(950, new DataFlowSample().processOrder(1, 100, 11));
    }

    @Test
    void smallOrderNoDiscount() {
        assertEquals(20, new DataFlowSample().processOrder(1, 2, 10));
    }
}
