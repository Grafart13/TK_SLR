package heart;

import java.util.*;

/**
 * @author Mateusz Drożdż
 */

/**
 * First/Follow explanation:
 * http://www.jambe.co.nz/UNI/FirstAndFollowSets.html
 */
public class ParserGenerator {
    private Grammar grammar;
    private Map<String, Set<String>> firstSet; // = new LinkedHashMap<String, Set<String>>();
    private Map<String, Set<String>> followSet = new HashMap<String, Set<String>>();

    private Map<Integer, DFAState> statesMap;
    private ParserArray parserArray;

    public ParserGenerator(Grammar grammar) {
        this.grammar = grammar;
        this.firstSet = buildFirsts(grammar);
        this.followSet = buildFollows(grammar, firstSet);
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public Map<String, Set<String>> getFirstSet() {
        return firstSet;
    }

    public Map<String, Set<String>> getFollowSet() {
        return followSet;
    }


    private Map<String, Set<String>> buildFirsts(Grammar grammar) {

        Map<String, Set<String>> firstSet = new LinkedHashMap<String, Set<String>>();

        for (String terminal : grammar.getTerminals()) {
            Set<String> first = new HashSet<String>();
            firstSet.put(terminal, first);
        }
        for (String nonterminal : grammar.getNonterminals()) {
            Set<String> first = new HashSet<String>();
            firstSet.put(nonterminal, first);
        }
        Set<String> epsSet = new HashSet<String>();
        firstSet.put(Grammar.EPSILON, epsSet);

        // first set algorithm

        // If X is a terminal then First(X) is just X!
        for (String terminal : grammar.getTerminals()) {
            firstSet.get(terminal).add(terminal);
        }
        // If there is a Production X → ε then add ε to first(X)
        for (Production production : grammar.getProds()) {
            if (production.getRightSide().get(0).equals(Grammar.EPSILON)) {
                firstSet.get(production.getLeftSide()).add(Grammar.EPSILON);
            }
        }

        // If there is a Production X → Y1Y2..Yk then add first(Y1Y2..Yk) to first(X)
        boolean symbolAdded = false;
        do {
            symbolAdded = false;
            for (String nonterminal : grammar.getNonterminals()) {
                for (Production production : grammar.getProds()) {

                    if (!production.getLeftSide().equals(nonterminal)) {
                        continue;
                    }
                    for (String item : firstSet.get(production.getRightSide().get(0))) {
                        if (!item.equals(Grammar.EPSILON) && !firstSet.get(nonterminal).contains(item)) {
                            firstSet.get(nonterminal).add(item);
                            symbolAdded = true;
                        }
                    }

                    for (int i = 1; i < production.getRightSide().size(); i++) {
                        boolean epsilons = true;
                        for (int j = 0; j < i; j++) {
                            if (!firstSet.get(production.getRightSide().get(j)).contains(Grammar.EPSILON)) {
                                epsilons = false;
                            } else {
                                break;
                            }
                        }

                        if (epsilons) {
                            for (String item : firstSet.get(production.getRightSide().get(i))) {
                                if (!item.equals(Grammar.EPSILON) && !firstSet.get(nonterminal).contains(item)) {
                                    firstSet.get(nonterminal).add(item);
                                    symbolAdded = true;
                                }
                            }
                            // ostatni elem i jego first zawiera epsilon - dodajemy do zbioru
                            if ((i == (production.getRightSide().size() - 1)) && firstSet.get(production.getRightSide().get(production.getRightSide().size() - 1)).contains(Grammar.EPSILON)) {
                                firstSet.get(nonterminal).add(Grammar.EPSILON);
                            }
                        }
                    }

                    if ((production.getRightSide().size() == 1) && firstSet.get(production.getRightSide().get(0)).contains(Grammar.EPSILON)) {
                        firstSet.get(nonterminal).add(Grammar.EPSILON);
                    }
                }
            }

        } while (symbolAdded);

        firstSet.remove(Grammar.EPSILON);

        return firstSet;

    }

    private Set<String> firstOfWord(List<String> word, Map<String, Set<String>> firsts) {
        Set<String> result = new HashSet<String>();

        if (word.size() == 0) {
            return result;
        }

        if (word.size() == 1 && word.get(0).equals(Grammar.EPSILON)) {
            result.add(Grammar.EPSILON);
            return result;
        }

        for (String item : firsts.get(word.get(0))) {
            if (!item.equals(Grammar.EPSILON) && !result.contains(item)) {
                result.add(item);
            }
        }

        for (int i = 1; i < word.size(); i++) {
            boolean epsilons = true;
            for (int j = 0; j < i; j++) {
                if (!result.contains(Grammar.EPSILON)) {
                    epsilons = false;
                } else {
                    break;
                }
            }

            if (epsilons) {
                for (String item : firsts.get(word.get(i))) {
                    if (!item.equals(Grammar.EPSILON) && !result.contains(item)) {
                        result.add(item);
                    }
                }

                if (i == word.size() - 1 && result.contains(Grammar.EPSILON)) {
                    result.add(Grammar.EPSILON);
                }
            }

        }

        return result;
    }

    private Map<String, Set<String>> buildFollows(Grammar grammar, Map<String, Set<String>> firsts) {
        Map<String, Set<String>> result = new HashMap<String, Set<String>>();

        for (String nonterminal : grammar.getNonterminals()) {
            Set<String> firstSet = new HashSet<String>();
            result.put(nonterminal, firstSet);
        }

        result.get(grammar.getStartSymbol()).add(Grammar.DOLLAR);

        boolean itemAdded;

        do {
            itemAdded = false;
            for (Production production : grammar.getProds()) {
                for (int i=0; i<production.getRightSide().size(); i++) {
                    String nonterminal = production.getRightSide().get(i);

                    // ommiting terminals and epsilon
                    if (grammar.getTerminals().contains(nonterminal) || nonterminal.equals(Grammar.EPSILON)) {
                        continue;
                    }

                    if (i == production.getRightSide().size() - 1 || firstOfWord(production.getRightSide().subList(i+1, production.getRightSide().size()), firsts).contains(Grammar.EPSILON)) {
                        for (String item : result.get(production.getLeftSide())) {
                            if (!result.get(nonterminal).contains(item)) {
                                result.get(nonterminal).add(item);
                                itemAdded = true;
                            }
                        }
                    }

                    if (i < production.getRightSide().size()) {
                        for (String item : firstOfWord(production.getRightSide().subList(i+1, production.getRightSide().size()), firsts)) {
                            if (!item.equals(Grammar.EPSILON) && !result.get(nonterminal).contains(item)) {
                                result.get(nonterminal).add(item);
                                itemAdded = true;
                            }
                        }
                    }
                }
            }

        } while (itemAdded);

        return result;
    }

    /**
     * Defn: Goto(I,X), where I is a set of items, X is a terminal or non-terminal, is the
     closure(A -> a X . b) where A -> a . X b is in I.
     */

    // TODO: maybe move to other (new?) class;
    // TODO: remove
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

    // TODO: maybe move to other (new?) class;
    // TODO: TEST!!!
    public Set<DFAState> computeDFAStates(Grammar grammar) {
        statesMap = new HashMap<Integer, DFAState>();

        Closure closure = new Closure(grammar);
        // LinkedHashSet because of insertion-order
        Set<DFAState> dfaStates = new LinkedHashSet<DFAState>();

        // TODO: maybe extract method to Grammar?
        Production zeroProd = grammar.getProds().get(0);

        StateItem zeroItem = new StateItem(zeroProd);
        Set<StateItem> state_0 = new HashSet<StateItem>(closure.produce(new HashSet<StateItem>(Arrays.asList(zeroItem))));

        int counter = 0;
        DFAState dfaState_0 = new DFAState(counter, new Goto(state_0, null, null));
        System.out.println("T(0): " + dfaState_0.getState());
        dfaStates.add(dfaState_0);
        counter++;

        Set<String> grammarSymbols = new HashSet<String>();
        grammarSymbols.addAll(grammar.getTerminals());
        grammarSymbols.addAll(grammar.getNonterminals());

        Set<DFAState> prevStates = new LinkedHashSet<DFAState>();
        Set<DFAState> currStates;

        prevStates.add(dfaState_0);

        GotoBuilder gotoBuilder = new GotoBuilder(grammar);

        // iterate over dfaStates until no new sitiations(dfaStates) are generated
        do {
            // start with empty set
            currStates = new LinkedHashSet<DFAState>();
            // for every distinct previous action-set ...

            for (DFAState newState : prevStates) {
                // ... iterate over all symbols for particular state ...
                for (String symbol : grammarSymbols) {
                    // ... and generate GOTO transition
                    Goto gotoSet = gotoBuilder.compute(newState.getState(), symbol);
                    DFAState dfaState = new DFAState(counter, gotoSet);


                    // collect distinct sets of states
                    if (!gotoSet.getState().isEmpty() && dfaStates.add(dfaState)) {
                        // debug
                        System.out.println("  add: GOTO(" + newState.getState() + ", " + symbol + ") = " + gotoSet.getState());
                        currStates.add(dfaState);
                        counter++;
                    }
                }
            }

            // currNewStates (if not empty) is a set of action for next loop;
            prevStates = currStates;

        } while (!currStates.isEmpty());

        // init DFAStates map
        for (DFAState state : dfaStates) {
            statesMap.put(state.getId(), state);
        }

        return dfaStates;
    }

    public ParserArray generateParserArray(List<DFAState> states, List<String> symbols) {
        ParserArray array = new ParserArray(states, symbols);
        for (DFAState state : statesMap.values()) {
            // kropka przed nieterminalem – wtedy jest przejscie
            for (StateItem stateItem : state.getState()) {
                String symbolAfterDot = stateItem.getSymbolAfterDot();
                if (grammar.getNonterminals().contains(symbolAfterDot)) {
                    // szukamy GOTO przez state przez dany symbol
                    int id = -1;
                    for (DFAState dfaState : states) {
                        if (dfaState.from() != null && dfaState.from().equals(state.getState()) && dfaState.symbol().equals(symbolAfterDot)) {
                            id = dfaState.getId();
                        }
                    }
                    array.setAction(state.getId(), symbolAfterDot, "T_" + id );
                }
            }

            // kropka przed terminalem – wtedy jest przesunięecie „sh”,
            for (StateItem stateItem : state.getState()) {
                String symbolAfterDot = stateItem.getSymbolAfterDot();
                if (grammar.getTerminals().contains(symbolAfterDot)) {
                    // szukamy GOTO przez state przez dany symbol
                    int id = -1;
                    for (DFAState dfaState : states) {
                        if (dfaState.from() != null && dfaState.from().equals((state.getState())) && dfaState.symbol().equals(symbolAfterDot)) {
                            id = dfaState.getId();
                        }
                    }
                    array.setAction(state.getId(), symbolAfterDot, "sh" + id);
                }
            }

            // kropka na koncu - red || lub acc dla prod. red0
            for (StateItem stateItem : state.getState()) {
                String symbolAfterDot = stateItem.getSymbolAfterDot();
                if (symbolAfterDot.equals(StateItem.END)) {
                    // szukamy numerku produkcji
                    int id = 0;
                    String nietPrzedStrzalka = null;
                    for (Production production : grammar.getProds())  {
                        if (!production.equals(stateItem.getProduction())) {
                            id++;
                        } else {
                            nietPrzedStrzalka = production.getLeftSide();
                        }

                    }
                    // akceptacja - acc
                    if (id == 0) {
                        for (String redSymbol : followSet.get(nietPrzedStrzalka))
                            array.setAction(state.getId(), redSymbol, "acc");
                    } else {
                        for (String redSymbol : followSet.get(nietPrzedStrzalka))
                            array.setAction(state.getId(), redSymbol, "red" + id);
                    }
                }
            }

        }



        return array;
    }

}
