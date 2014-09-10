package heart;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mateusz Drożdż
 */
public class GotoBuilder {
    private final Grammar grammar;

    public GotoBuilder(Grammar grammar) {
        this.grammar = grammar;
    }

    public Goto compute(Set<StateItem> items, String symbol) {
        Set<StateItem> state = computeGoto(items, symbol);
        return new Goto(state, items, symbol);
    }

    /**
     * coped from ParserGenerator
     */
    public Set<StateItem> computeGoto(Set<StateItem> items, String symbol) {
        Closure closure = new Closure(grammar);
        Set<StateItem> result = new HashSet<StateItem>();

        for (StateItem item : items) {
            if (symbol.equals(item.getSymbolAfterDot())) {
                result.addAll(closure.produce(new HashSet<StateItem>(Arrays.asList(item.moveDotOver()))));
            }
        }
        return result;
    }

}
