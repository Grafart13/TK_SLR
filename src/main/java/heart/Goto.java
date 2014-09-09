package main.java.heart;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Mateusz Drożdż
 */
public class Goto {
    private Set<StateItem> state;

    private Set<StateItem> from;
    private Map<Set<StateItem>, String> fromMap = new LinkedHashMap<Set<StateItem>, String>();
    private String symbol;

    public Goto(Set<StateItem> state, Set<StateItem> from, String symbol) {
        this.state = state;
        this.from = from;
        this.symbol = symbol;
        this.fromMap.put(from, symbol);
    }

    public void addFrom(Set<StateItem> from, String symbol) {
        fromMap.put(from, symbol);
    }

    public void setState(Set<StateItem> state) {
        this.state = state;
    }

    public Set<StateItem> getState() {
        return state;
    }

    public Set<StateItem> getFrom() {
        return from;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Goto aGoto = (Goto) o;

        if (state != null ? !state.equals(aGoto.state) : aGoto.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return state != null ? state.hashCode() : 0;
    }
}
