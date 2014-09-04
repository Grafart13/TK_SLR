package heart;

import java.util.Set;

/**
 * @author Mateusz Drożdż
 */
public class DFAState {
    private final int id;
    private final Goto gotoState;

    public DFAState(int id, Goto gotoState) {
        this.id = id;
        this.gotoState = gotoState;
    }

    public int getId() {
        return id;
    }

    public Set<StateItem> getState() {
        return gotoState.getState();
    }

    public Set<StateItem> from() {
        return gotoState.getFrom();
    }

    public String symbol() {
        return gotoState.getSymbol();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DFAState dfaState = (DFAState) o;

        if (gotoState != null ? !gotoState.equals(dfaState.gotoState) : dfaState.gotoState != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return gotoState != null ? gotoState.hashCode() : 0;
    }
}
