package main.java.heart;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Mateusz Drożdż
 *         <p/>
 *         Inne podejście
 */
public class ParserArray {
    private final Set<ActionTriple> array = new LinkedHashSet<ActionTriple>();
    private boolean conflictExist = false;

    public void addAction(int stateId, String symbol, Action action) {
        // if added more than one different action in array cell == conflict
        if (actionExist(stateId, symbol) && !getAction(stateId, symbol).equals(action)) {
            conflictExist = true;
        }
        array.add(new ActionTriple(stateId, symbol, action));
    }

    public boolean conflictExist() {
        return conflictExist;
    }

    public void print() {
        for (ActionTriple action : array) {
            System.out.println("(" + action.stateId + ", " + action.symbol + "): " + action.action);
        }
        System.out.println("conflict: " + conflictExist());
    }

    private boolean actionExist(int stateId, String symbol) {
        for (ActionTriple actionTriple : array) {
            if (stateId == actionTriple.stateId && symbol.equals(actionTriple.symbol)) {
                return true;
            }
        }
        return false;
    }

    private Action getAction(int stateId, String symbol) {
        for (ActionTriple actionTriple : array) {
            if (stateId == actionTriple.stateId && symbol.equals(actionTriple.symbol)) {
                return actionTriple.action;
            }
        }
        return null;
    }


    private class ActionTriple {
        int stateId;
        String symbol;
        Action action;

        private ActionTriple(int stateId, String symbol, Action action) {
            this.stateId = stateId;
            this.symbol = symbol;
            this.action = action;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ActionTriple that = (ActionTriple) o;

            if (stateId != that.stateId) return false;
            if (!action.equals(that.action)) return false;
            if (!symbol.equals(that.symbol)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = stateId;
            result = 31 * result + symbol.hashCode();
            result = 31 * result + action.hashCode();
            return result;
        }
    }
}
