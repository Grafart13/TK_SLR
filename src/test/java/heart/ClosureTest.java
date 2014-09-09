package test.java.heart;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import main.java.heart.*;

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
        //Closure ({T Æ T . * F}) = {T Æ T . * F}

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
        // Closure ({F Æ ( . E ) } ) = {F Æ ( . E ), E Æ . E + T, E Æ . T, T Æ . T * F, T Æ . F, F Æ . ( E ), F Æ . id }

        // when
        items.add(stateItem);

        // then
        for (StateItem item : closure.produce(items))
            System.out.println("  " + item);
        Assert.assertTrue(true);
    }

    @Test
    public void testProduce_3() throws Exception {
        // given
        Set<StateItem> items = new LinkedHashSet<StateItem>();
        production = new Production("T", Arrays.asList("T", "*", "F"));
        stateItem = new StateItem(production, 1);
        // Closure ({T Æ T . * F, T Æ T * . F}) = {T Æ T .* F, T Æ T * . F, F Æ . (E ), F Æ . id}

        // when
        items.add(new StateItem(new Production("T", Arrays.asList("T", "*", "F")), 1));
        items.add(new StateItem(new Production("T", Arrays.asList("T", "*", "F")), 2));

        // then
        System.out.println("Produce_3");
        System.out.println(closure.produce(items));
        System.out.println("/Produce_3");

        Assert.assertTrue(true);

    }
}
