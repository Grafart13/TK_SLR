package test;

import heart.Production;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Mateusz Drożdż
 */
public class ProductionTest {
    private Production production1;
    private Production production2;
    private Production production3;

    @Before
    public void setUp() throws Exception {
        production1 = new Production("S", Arrays.asList("A", "+", "B"));
        production2 = new Production("S", Arrays.asList("A", "+", "B"));
        production3 = new Production("S", Arrays.asList("A", "B", "+"));


    }

    @Test
    public void testEquals() throws Exception {
        Assert.assertTrue(production1.equals(production2));
        Assert.assertTrue(production2.equals(production1));

        Assert.assertFalse(production1.equals(production3));
        Assert.assertFalse(production3.equals(production1));
    }
}
