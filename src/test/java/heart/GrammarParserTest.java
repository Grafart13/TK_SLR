package test.java.heart;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import main.java.heart.*;

/**
 * @author Mateusz Drożdż
 */
public class GrammarParserTest {

    private GrammarParser grammarParser;

    @Before
    public void setUp() throws Exception {
        grammarParser = new GrammarParser();
    }

    @Test
    public void testParseNo1() throws Exception {
        // given
        String input = "S -> S + A\n" +
                        "A -> *";
        //todo: change results type to set
//        List<String> nonterminals = Arrays.asList("S", "A");
        List<String> nonterminals = Arrays.asList("S", "A", "S'");
        List<String> terminals = Arrays.asList("+", "*");

        // when
        Grammar grammar = grammarParser.parse(input);

        // then
        Assert.assertEquals(nonterminals, grammar.getNonterminals());
        Assert.assertEquals(terminals, grammar.getTerminals());
        Assert.assertEquals(3, grammar.getProds().size());

        System.out.println(grammar.getProds());
    }

    @Test
    public void testParseNo2() throws Exception {
        // given
        String input = "S -> a A | b B | A S B\n" +
                       "A -> A b | eps\n" +
                       "B -> B a | eps";

//        List<String> nonterminals = Arrays.asList("S", "A", "B");
        List<String> nonterminals = Arrays.asList("S", "A", "B", "S'");
        List<String> terminals = Arrays.asList("a", "b");

        // when
        Grammar grammar = grammarParser.parse(input);

        // then
        Assert.assertEquals(nonterminals, grammar.getNonterminals());
        Assert.assertEquals(terminals, grammar.getTerminals());
        Assert.assertEquals(8, grammar.getProds().size());

        System.out.println(grammar.getProds());
    }

    @Test
    public void testParseNo3() throws Exception {
        // given
        String input = "Goal -> A\n" +
                "A -> ( A ) | Two\n" +
                "Two -> a\n" +
                "Two -> b";

//        List<String> nonterminals = Arrays.asList("Goal", "A", "Two");
        List<String> nonterminals = Arrays.asList("Goal", "A", "Two", "Goal'");
        List<String> terminals = Arrays.asList("(", ")", "a", "b");

        // when
        Grammar grammar = grammarParser.parse(input);

        // then
        Assert.assertEquals(nonterminals, grammar.getNonterminals());
        Assert.assertEquals(terminals, grammar.getTerminals());
        Assert.assertEquals(6, grammar.getProds().size());

        System.out.println(grammar.getProds());
    }


    // todo: test for some illegal inputs
    @Test(expected = Exception.class)
    public void testParseErrors() throws Exception {
        // given
        String input = "dadfsdf";

        // when
        Grammar grammar = grammarParser.parse(input);



    }
}
