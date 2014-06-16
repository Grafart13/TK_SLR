package test;

import heart.Grammar;
import heart.GrammarParser;
import heart.ParserGenerator;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

/**
 * @author Mateusz Drożdż
 */
public class ParserGeneratorTest {
    private ParserGenerator generator;
    private GrammarParser grammarParser;
    private Grammar grammar;
    private String input;
    private String input2;

    @Before
    public void setUp() throws Exception {
        // given
        grammarParser = new GrammarParser();
        // from: http://hackingoff.com/compilers/predict-first-follow-set
        input = "Goal -> A\n" +
                "A -> ( A ) | Two\n" +
                "Two -> a\n" +
                "Two -> b";

        grammar = grammarParser.parse(input);

    }

    @Test
    public void testGetFirstSet() throws Exception {
        // when
        generator = new ParserGenerator(grammar);

        // then
        Assert.assertTrue(generator.getFirstSet().size() > 0);
        System.out.println("First");
        System.out.println(generator.getFirstSet());
        for (Map.Entry<String, Set<String>> entry : generator.getFirstSet().entrySet()) {
            System.out.println(entry.getKey());
            for (String val : entry.getValue()) {
                System.out.print("   " + val);
                System.out.println();
            }
        }

//        System.out.println(grammar.getProds());
    }

    @Test
    public void testGetFollowSet() throws Exception {
        // when
        generator = new ParserGenerator(grammar);

        // then
        Assert.assertTrue(generator.getFollowSet().size() > 0);
        System.out.println("Follow:");
        System.out.println(generator.getFollowSet());
        System.out.println();

    }
}
