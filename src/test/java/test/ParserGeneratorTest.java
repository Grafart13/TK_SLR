package test;

import heart.Grammar;
import heart.GrammarParser;
import heart.ParserGenerator;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mateusz Drożdż
 */
public class ParserGeneratorTest {
    private ParserGenerator generator;
    private GrammarParser grammarParser;
    private Grammar grammar;
    private String input;

    @Before
    public void setUp() throws Exception {
        // given
        grammarParser = new GrammarParser();
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
        System.out.println(generator.getFirstSet());

    }
}
