package heart;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mateusz Drożdż
 */
public class ParserArray {
    private final List<DFAState> dfaStates;
    private final List<String> symbols;

    private final Map<Integer, Map<String, String>> parserArray = new LinkedHashMap<Integer, Map<String, String>>();



    public ParserArray(List<DFAState> dfaStates, List<String> symbols) {
        this.dfaStates = dfaStates;
        this.symbols = symbols;
        init();
    }

    public boolean isAction(Integer state, String symbol) {
        if (parserArray.get(state).isEmpty()) {
            return false;
        }
        if (parserArray.get(state).get(symbol).isEmpty()) {
            return false;
        }
        return true;
    }
    public String getAction(Integer state, String symbol) {
        return parserArray.get(state).get(symbol);
    }

    public void setAction(Integer state, String symbol, String action) {
        parserArray.get(state).put(symbol, action);
    }

    private void init() {
        for (DFAState state : dfaStates) {
            parserArray.put(state.getId(), new LinkedHashMap<String, String>());
        }
    }

    public void print() {
        System.out.print("   ");
        for (String symbol : symbols) {
            System.out.print("     " + symbol);
        }
        System.out.println();
        for (DFAState dfaState : dfaStates) {
            System.out.print(dfaState.getId() + "   ");
            for (String symbol : symbols) {
                if (parserArray.get(dfaState.getId()) == null || parserArray.get(dfaState.getId()).isEmpty() || parserArray.get(dfaState.getId()).get(symbol).isEmpty()) {
                    System.out.print("-----");
                } else {
                    System.out.print(parserArray.get(dfaState.getId()).get(symbol));
                }
            }
            System.out.println();
        }
    }



}
