package heart.symulator;

import java.util.LinkedList;
import java.util.List;

/**
 * Contains: actual stack; input and list of prods numbers
 *
 * @author Mateusz Drożdż
 */

public class ParseStep {
    private final List<StackItem> stack;
    private final List<String> input;
    private final List<String> prods;

    public ParseStep(List<StackItem> stack, List<String> input, List<String> prods) {
        this.stack = new LinkedList<StackItem>(stack);
        this.input = new LinkedList<String>(input);
        this.prods = new LinkedList<String>(prods);
    }

    public List<StackItem> getStack() {
        return stack;
    }

    public List<String> getInput() {
        return input;
    }

    public List<String> getProds() {
        return prods;
    }

    public StackItem getLastStackItem() {
        return stack.get(stack.size() - 1);
    }

    public String getInputSymbol() {
        return input.get(0);
    }
}
