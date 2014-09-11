package heart.symulator;

import heart.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mateusz Drożdż
 */
public class ParserSymulator {
    private final Grammar grammar;
    private final ParserArray parserArray;


    public ParserSymulator(Grammar grammar, ParserArray parserArray) {
        this.grammar = grammar;
        this.parserArray = parserArray;
    }

    public List<ParseStep> parse(String wordToParse) {
        boolean accepted = false;
        boolean error = false;

        List<ParseStep> steps = new ArrayList<ParseStep>();
        List<String> inputList = Arrays.asList(wordToParse.split(""));
        inputList = new LinkedList<String>(inputList.subList(1, inputList.size()));
        inputList.add(Grammar.DOLLAR);

        ParseStep initStep = new ParseStep(Arrays.asList(new StackItem("0", StackItem.Type.PRODUCTION)), inputList, Arrays.asList(Grammar.EPSILON));
        steps.add(initStep);

        // start symulation

        // to be honest it's redundant, always starts from 0-prod, but in order to clarify...
//            int initProd = Integer.parseInt(steps.get(steps.size()-1).getLastStackItem().getValue());

        do {

            ParseStep lastStep = steps.get(steps.size() - 1);
            int prod = Integer.parseInt(lastStep.getLastStackItem().getValue());
            String symbol = lastStep.getInputSymbol();
            Action action = parserArray.getAction(prod, symbol);

            System.out.println(prod + " : " + symbol + " : " + action );

            if (action.getType() == ActionType.ACCEPT) {
                accepted = true;
            }

            if (action.getType() == ActionType.SHIFT) {
                List<StackItem> stack = new LinkedList<StackItem>(lastStep.getStack());
                List<String> input = new LinkedList<String>(lastStep.getInput());
                List<String> prods = new LinkedList<String>(lastStep.getProds());

                stack.add(new StackItem(symbol, StackItem.Type.SYMBOL));
                Integer nextProd = action.getState();
                stack.add(new StackItem(nextProd.toString(), StackItem.Type.PRODUCTION));
                input.remove(0); // remove symbol from start

                ParseStep newStep = new ParseStep(stack, input, prods);
                steps.add(newStep);
            }

            if (action.getType() == ActionType.REDUCE) {
                List<StackItem> stack = new LinkedList<StackItem>(lastStep.getStack());
                List<String> input = new LinkedList<String>(lastStep.getInput());
                List<String> prods = new LinkedList<String>(lastStep.getProds());

                int prodNumber = action.getState();
                Production production = grammar.getProds().get(prodNumber);
                int numOfItems = 2 * production.getRightSide().size();

                // remove numOfItems items
                stack = stack.subList(0, stack.size() - numOfItems);

                StackItem tmpItem = stack.get(stack.size() - 1);
                int stateNum = Integer.parseInt(tmpItem.getValue());
                Action tmpAction = parserArray.getAction(stateNum, production.getLeftSide());

                // prod.RSide -> stack
                stack.add(new StackItem(production.getLeftSide(), StackItem.Type.SYMBOL));
                stack.add(new StackItem(String.valueOf(tmpAction.getState()), StackItem.Type.PRODUCTION));


                // reduction wg. prodNumber
                if (prods.get(0).equals(Grammar.EPSILON)) {
                    prods.remove(Grammar.EPSILON);
                }
                prods.add(String.valueOf(prodNumber));

                ParseStep newStep = new ParseStep(stack, input, prods);
                steps.add(newStep);

            }

//                if (steps.get(steps.size()-1).getInputSymbol().equals(Grammar.DOLLAR)) {
//                    accepted = true;
//                }

        } while (!accepted && !error);


        return steps;
    }

}
