package heart;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * seems to be working but more tests are needed
 * examples from: http://cs.gmu.edu/~white/CS540/slr.pdf
 *
 * @author Mateusz Drożdż
 */
public class ClosureTest {

    private GrammarParser grammarParser;
    private Grammar grammar;
    private Production production;
    private StateItem stateItem;
    private Closure closure;

    @Before
    public void setUp() throws Exception {
        grammarParser = new GrammarParser();
        String input = "E -> E + T | T\n" +
                "T -> T * F | F\n" +
                "F -> ( E ) | id";
        grammar = grammarParser.parse(input);
        closure = new Closure(grammar);
    }

    @Test
    public void testProduce_1() throws Exception {
        // given
        Set<StateItem> items = new HashSet<StateItem>();
        production = new Production("T", Arrays.asList("T", "*", "F"));
        stateItem = new StateItem(production, 1);

        // when
        items.add(stateItem);

        // then
        Assert.assertEquals(items, closure.produce(items));
    }

    // TODO: test closure() for list of more then only one item
    @Test
    public void testProduce_2() throws Exception {
        // given
        Set<StateItem> items = new HashSet<StateItem>();
        production = new Production("F", Arrays.asList("(", "E", ")"));
        stateItem = new StateItem(production, 1);

        // when
        items.add(stateItem);

        // then
        for (StateItem item : closure.produce(items))
            System.out.println("  " + item);
        Assert.assertTrue(true);
    }

}
