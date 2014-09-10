package heart.symulator;

import heart.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ParserSymulatorTest {

    @Test
    public void testParse() throws Exception {

        // given
        String input = "S -> S * | a ( S ) | a";
        GrammarParser grammarParser = new GrammarParser();
        Grammar grammar = grammarParser.parse(input);
        ParserGenerator generator = new ParserGenerator(grammar);
        Set<DFAState> states = generator.computeDFAStates(grammar);
        ParserArray array = generator.generateParserArrayAnother(new ArrayList<DFAState>(states));
        ParserSymulator symulator = new ParserSymulator(grammar, array);
        String wordToParse = "a(a*)*";

        // when
        List<ParseStep> steps = symulator.parse(wordToParse);
        System.out.println("PARSE");
        for (ParseStep step : steps) {
            System.out.println(step.getStack() + " || " + step.getInput() + " || " + step.getProds());
        }
        System.out.println("/PARSE");

    }
}