package heart;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * see: http://cs.gmu.edu/~white/CS540/slr.pdf
 *
 * @author Mateusz Drożdż
 */
public class Closure {
    private Grammar grammar;

    public Closure(Grammar grammar) {
        if (grammar == null) {
            throw new NullPointerException("Grammar must be not null!");
        }
        this.grammar = grammar;
    }

    public Set<StateItem> produce(Set<StateItem> itemsSet) {
        Set<StateItem> result = new LinkedHashSet<StateItem>(itemsSet);

        for (StateItem item : itemsSet) {
            result.addAll(produceTmp(item));
        }

        // repeat until any new states added; else return
        if (result.equals(itemsSet)) {

            return result;
        } else {
            return produce(result);
        }
    }

    private Set<StateItem> produceTmp(StateItem stateItem) {
        Set<StateItem> result = new LinkedHashSet<StateItem>();
        result.add(stateItem);
        String symbolAfterDot = stateItem.getSymbolAfterDot();
        if (grammar.getTerminals().contains(symbolAfterDot)) {
            return result;
        }

        if (grammar.getNonterminals().contains(symbolAfterDot)) {
            for (Production prod : grammar.getProds()) {
                if (prod.getLeftSide().equals(symbolAfterDot)) {
                    result.add(new StateItem(prod));
                }
            }
        }

        return result;
    }
}
