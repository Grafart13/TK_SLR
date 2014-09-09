package test.java.heart;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import main.java.heart.*;

/**
 * @author Mateusz Drożdż
 */
public class StateItemTest {

    private Production production;
    private Production epsProduction;

    @Before
    public void setUp() throws Exception {
        production = new Production("E", Arrays.asList("E", "+", "T"));
        epsProduction = new Production("S", Arrays.asList(Grammar.EPSILON));
    }

    @Test
    public void testToString() throws Exception {
        // given
        int dotPosition = 1;
        // when
        StateItem item = new StateItem(production, dotPosition);
        // then
        Assert.assertEquals("E -> E " + StateItem.DOT + " + T", item.toString());

    }

    @Test
    public void testToStringEpsilon() throws Exception {
        // given
        int dotPosition = 0;
        // when
        StateItem item = new StateItem(epsProduction, dotPosition);
        // then
        Assert.assertEquals("S -> " + StateItem.DOT, item.toString());

    }

    @Test
    public void testEquals() throws Exception {
        // given
        int dotPos1 = 1;
        int dotPos2 = 2;

        // when
        StateItem item1 = new StateItem(production, dotPos1);
        StateItem item2 = new StateItem(production, dotPos2);
        StateItem item3 = new StateItem(production, dotPos1);

        // then
        Assert.assertFalse(item1.equals(item2));
        Assert.assertTrue(item1.equals(item3));
    }

    @Test(expected = Exception.class)
    public void testConstructor() throws Exception {
        StateItem item = new StateItem(production, 10);
    }
}
