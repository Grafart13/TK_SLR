package heart;

/**
 * StateItem == An LR(0) item of a grammar G
 * == a production of G with a dot ( . ) at some
 * point on the right side. (ex. E -> . E + T)
 *
 * @author Mateusz Drożdż
 */

public class StateItem {
    public static final String END = "<end>";
    public static final String DOT = ".";
    private Production production;
    /**
     * dotPosition = i means ( . ) is before prodRightSide[i] elem. (0 <==> E -> . E + T; 1 <==> E -> E . + T etc.)
     */
    private int dotPosition;

    public StateItem(Production production) {
        if (production == null) {
            throw new NullPointerException("Production must be not null!");
        }
        this.production = production;
        this.dotPosition = 0;
    }


    // TODO: remove, rather unnecessary - StateItem(Production production) is better.
    public StateItem(Production production, int dotPosition) {
        if (production == null) {
            throw new NullPointerException("Production must be not null!");
        }
        if (dotPosition < 0 || dotPosition > production.getRightSide().size()) {
            throw new IllegalArgumentException("dotPosition must be in range <0, prod.legth)");
        }
        this.production = production;
        this.dotPosition = dotPosition;
    }

    public String getSymbolAfterDot(){
        if (dotPosition < production.getRightSide().size()) return production.getRightSide().get(dotPosition);
        else return END;
    }

    public StateItem moveDotOver() {
        return new StateItem(production, dotPosition + 1);
    }

    @Override
    public String toString() {
        String ret = production.getLeftSide();
        ret += " ->";
        if (production.getRightSide().size() == 1 && production.getRightSide().contains(Grammar.EPSILON)) {
            ret += " " + DOT;
            return ret;
        }
        int counter = 0;
        for (String symbol : production.getRightSide()) {
            if (counter == dotPosition) {
                ret += " " + DOT;
            }
            ret += " " + symbol;
            counter++;
        }
        // dot at the end of prod.
        if (counter == dotPosition) {
            ret += " " + DOT;
        }
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateItem stateItem = (StateItem) o;

        if (dotPosition != stateItem.dotPosition) return false;
        if (!production.equals(stateItem.production)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = production.hashCode();
        result = 31 * result + dotPosition;
        return result;
    }
}
