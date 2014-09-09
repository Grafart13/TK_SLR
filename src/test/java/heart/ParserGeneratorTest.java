package java.heart;

import main.java.heart.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

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
        // from: http://hackingoff.com/compilers/predict-first-follow-set
        input = "Goal -> A\n" +
                "A -> ( A ) | Two\n" +
                "Two -> a\n" +
                "Two -> b";

        String input2 = "S -> S*\n" +
                        "S -> a(S)\n" +
                        "S -> a";

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

    @Test
    public void testComputeGoto() throws Exception {
        // given
        String inputG = "E -> E + T | T\n" +
                "T -> T * F | F\n" +
                "F -> ( E ) | id";
        grammarParser = new GrammarParser();
        Grammar grammarG = grammarParser.parse(inputG);
        ParserGenerator generatorG = new ParserGenerator(grammarG);

        StateItem item1 = new StateItem(new Production("E'", Arrays.asList("E")), 1);
        StateItem item2 = new StateItem(new Production("E", Arrays.asList("E", "+", "T")), 1);
        String symbol = "+";
        Set<StateItem> items = new LinkedHashSet<StateItem>();
        items.add(item1);
        items.add(item2);
        // GotoOld({E’ÆE ., E Æ E . + T},+) = closure({E Æ E + . T}) =
        // {E Æ E + . T, T Æ . T * F, T Æ . F, F Æ . id, F Æ . ( E )}

        // when
        Set<StateItem> gotos = generatorG.computeGoto(items, symbol);

        // then
        System.out.println("--GOTOS---");
        System.out.println(gotos);
        System.out.println("--/GOTOS---");
        Assert.assertTrue(true);
    }

    @Test
    public void testComputeGoto_2() throws Exception {
        // given
        String inputG = "E -> E + T | T\n" +
                "T -> T * F | F\n" +
                "F -> ( E ) | id";
        grammarParser = new GrammarParser();
        Grammar grammarG = grammarParser.parse(inputG);
        ParserGenerator generatorG = new ParserGenerator(grammarG);

        StateItem item1 = new StateItem(new Production("T", Arrays.asList("T", "*", "F")), 2);
        StateItem item2 = new StateItem(new Production("T", Arrays.asList("F")), 0);
        String symbol = "F";
        Set<StateItem> items = new LinkedHashSet<StateItem>();
        items.add(item1);
        items.add(item2);
        // GotoOld({T Æ T * . F, T Æ . F},F) = closure({T Æ T * F ., T Æ F .}) =
        // {T Æ T * F ., T Æ F .}

        // when
        Set<StateItem> gotos = generatorG.computeGoto(items, symbol);

        // then
        System.out.println("--GOTOS_2---");
        System.out.println(gotos);
        System.out.println("--/GOTOS_2---");
        Assert.assertTrue(true);
    }

    @Test
    public void testComputeGoto_3() throws Exception {
        // given
        String input = "E -> E + T | T\n" +
                "T -> T * F | F\n" +
                "F -> ( E ) | id";
        grammarParser = new GrammarParser();
        Grammar grammar = grammarParser.parse(input);
        ParserGenerator generator = new ParserGenerator(grammar);

        StateItem item1 = new StateItem(new Production("E'", Arrays.asList("E")), 1);
        StateItem item2 = new StateItem(new Production("E", Arrays.asList("E", "+", "T")), 2);
        String symbol = "+";
        Set<StateItem> items = new LinkedHashSet<StateItem>();
        items.add(item1);
        items.add(item2);
        // GotoOld({E’ÆE ., E Æ E + . T},+) = closure({ }) = { }

        // when
        Set<StateItem> gotos = generator.computeGoto(items, symbol);

        // then
        System.out.println("--GOTOS_3---");
        System.out.println(gotos);
        System.out.println("--/GOTOS_3---");
        Assert.assertTrue(true);
    }

    @Test
    public void testComputeDFAStates() throws Exception {
        // given
        String input = "E -> E + T | T\n" +
                "T -> T * F | F\n" +
                "F -> ( E ) | id";
        grammarParser = new GrammarParser();
        Grammar grammar = grammarParser.parse(input);
        ParserGenerator generator = new ParserGenerator(grammar);
        Set<DFAState> states;

        // when
        states = generator.computeDFAStates(grammar);

        // then
        System.out.println("--- DFA ---");
        for (DFAState state : states) {
            System.out.println("  " + state.getState());
        }
        System.out.println("--- /DFA ---");
    }

    @Test
    public void testComputeDFAStatesEx2() throws Exception {
        // given
        String input = "S -> S * | a ( S ) | a";
        grammarParser = new GrammarParser();
        Grammar grammar = grammarParser.parse(input);
        ParserGenerator generator = new ParserGenerator(grammar);
        Set<DFAState> states;

        // when
        states = generator.computeDFAStates(grammar);

        // then
        System.out.println("--- DFA EX2 ---");
        for (DFAState state : states) {
            System.out.println("  " + state.getId() + ": "  + state.getState());
        }
        System.out.println("--- /DFA EX2 ---");
    }


    /**
     * 1) Przejscia - OK
     * 2) shift - brakuje (T4, a): sh2  i  (T5, *): sh3 ... // chyba nie wszystkie stany maja ustawiane 'from'...
     */
    @Test
    public void testGenerateParserArrayAnother() throws Exception {
        // given
        String input = "S -> S * | a ( S ) | a";
        grammarParser = new GrammarParser();
        Grammar grammar = grammarParser.parse(input);
        ParserGenerator generator = new ParserGenerator(grammar);
        Set<DFAState> states = generator.computeDFAStates(grammar);
        ParserArray array;

        // when
        array = generator.generateParserArrayAnother(new ArrayList<DFAState>(states));

        // then
        System.out.println("ARRAY 2");
        System.out.println(array);
        System.out.println();
        array.print();
        System.out.println();
        System.out.println("//ARRAY 2");

    }
}
