package heart;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Mateusz Drożdż
 *
 * Inne podejście
 */
public class ParserArray {
    private final List<ActionTriple> array = new LinkedList<ActionTriple>();

    public boolean actionExist(int stateId, String symbol) {
        for (ActionTriple action : array) {
            if (stateId == action.stateId && symbol.equals(action.symbol)) {
                return true;
            }
        }
        return false;
    }

    public void addAction(int stateId, String symbol, String action) {
        array.add(new ActionTriple(stateId, symbol, action));
    }

    public void print() {
        for (ActionTriple action : array) {
            System.out.println("(" + action.stateId + ", " + action.symbol + "): " + action.action);
        }
    }

    private class ActionTriple {
        int stateId;
        String symbol;
        String action;

        private ActionTriple(int stateId, String symbol, String action) {
            this.stateId = stateId;
            this.symbol = symbol;
            this.action = action;
        }
    }
}
