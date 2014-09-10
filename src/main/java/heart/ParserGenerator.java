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

    public String firstToString() {
        String result = "";
        for (String key : firstSet.keySet()) {
            result += key + " : ";
            for (String a : firstSet.get(key)) {
                result += a + " , ";
            }
            result+= "\n";
        }

        return result;
    }

    public String followToString() {
        String result = "";
        for (String key : followSet.keySet()) {
            result += key + " : ";
            for (String a : followSet.get(key)) {
                result += a + " , ";
            }
            result+= "\n";
        }

        return result;
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
                for (int i = 0; i < production.getRightSide().size(); i++) {
                    String nonterminal = production.getRightSide().get(i);

                    // ommiting terminals and epsilon
                    if (grammar.getTerminals().contains(nonterminal) || nonterminal.equals(Grammar.EPSILON)) {
                        continue;
                    }

                    if (i == production.getRightSide().size() - 1 || firstOfWord(production.getRightSide().subList(i + 1, production.getRightSide().size()), firsts).contains(Grammar.EPSILON)) {
                        for (String item : result.get(production.getLeftSide())) {
                            if (!result.get(nonterminal).contains(item)) {
                                result.get(nonterminal).add(item);
                                itemAdded = true;
                            }
                        }
                    }

                    if (i < production.getRightSide().size()) {
                        for (String item : firstOfWord(production.getRightSide().subList(i + 1, production.getRightSide().size()), firsts)) {
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
     * closure(A -> a X . b) where A -> a . X b is in I.
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

            for (DFAState prevState : prevStates) {
                // ... iterate over all symbols for particular state ...
                for (String symbol : grammarSymbols) {
                    // ... and generate GOTO transition
                    Goto newGotoSet = gotoBuilder.compute(prevState.getState(), symbol);
                    DFAState newDfaState = new DFAState(counter, newGotoSet);


                    // collect distinct sets of states (if newState is different than prev)
                    if (!newGotoSet.getState().isEmpty() && dfaStates.add(newDfaState)) {
                        // debug
                        System.out.println("  add: " + counter + ":  GOTO(" + prevState.getState() + ", " + symbol + ") = " + newGotoSet.getState());
                        //---
                        newDfaState.addFromId(prevState.getId(), symbol);
                        //---
                        currStates.add(newDfaState);
                        counter++;
                        // else we computed prev. existed state - add new fromId to computed prev state
                    } else if (!newGotoSet.getState().isEmpty()) {
                        // add additional 'from' state
                        for (DFAState possiblyTheSamePrevState : dfaStates) {
                            //  if corresponding state founded
                            if (possiblyTheSamePrevState.getState().equals(newGotoSet.getState())) {
                                possiblyTheSamePrevState.addFromId(prevState.getId(), symbol);
                            }
                        }
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

    public String DFAStatesToString (Grammar grammar) {
        String result = "";
        Set<DFAState> states = computeDFAStates(grammar);

        for (DFAState state : states) {
            result += state.toString() + "\n";
        }

        return result;
    }

    public ParserArray generateParserArrayAnother(List<DFAState> states) {
        ParserArray array = new ParserArray();

        for (DFAState dfaState : states) {
            // kropka przed nieterminalem – wtedy jest przejscie
            for (StateItem stateItem : dfaState.getState()) {
                String symbolAfterDot = stateItem.getSymbolAfterDot();
                if (grammar.getNonterminals().contains(symbolAfterDot)) {
                    // szukamy GOTO dla naszego stanu przez symbol po kropce
                    for (DFAState stateTmp : states) {
                        if (!stateTmp.getState().isEmpty() && symbolAfterDot.equals(stateTmp.symbol()) && stateTmp.containsInFrom(dfaState.getId(), symbolAfterDot)) {
                            array.addAction(dfaState.getId(), symbolAfterDot, new Action(ActionType.GOTO, stateTmp.getId()));
                        }
                    }
                }
            }

            // kropka przed terminalem - shift
            for (StateItem stateItem : dfaState.getState()) {
                String symbolAfterDot = stateItem.getSymbolAfterDot();
                // jesli terminal...
                if (grammar.getTerminals().contains(symbolAfterDot)) {
                    // szukamy GOTO j.w.
                    for (DFAState stateTmp : states) {
                        if (!stateTmp.getState().isEmpty() && symbolAfterDot.equals(stateTmp.symbol()) && stateTmp.containsInFrom(dfaState.getId(), symbolAfterDot)) {
                            // add shift
                            array.addAction(dfaState.getId(), symbolAfterDot, new Action(ActionType.SHIFT, stateTmp.getId()));
                        }
                    }
                }
            }

            // kropka na koncu - redukcja
            for (StateItem stateItem : dfaState.getState()) {
                String symbolAfterDot = stateItem.getSymbolAfterDot();
                // jesli koniec...
                if (StateItem.END.equals(symbolAfterDot)) {
                    // jesli red do 0 == acc
                    if (grammar.getProdNumber(stateItem.getProduction()) == 0) {
                        array.addAction(dfaState.getId(), Grammar.DOLLAR, new Action(ActionType.ACCEPT, 0));
                    } else {
                        // lecimy przejscia wg. FOLLOW
                        for (String s : followSet.get(stateItem.getProduction().getLeftSide())) {
                            array.addAction(dfaState.getId(), s, new Action(ActionType.REDUCE, grammar.getProdNumber(stateItem.getProduction())));
                        }
                    }
                }
            }
        }

        return array;
    }

}
