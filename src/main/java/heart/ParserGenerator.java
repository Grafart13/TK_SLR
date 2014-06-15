package heart;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Mateusz Drożdż
 */
public class ParserGenerator {
    private Grammar grammar;
    private Map<String, Set<String>> firstSet = new HashMap<String, Set<String>>();
    private Map<String, Set<String>> followSet = new HashMap<String, Set<String>>();

    public ParserGenerator(Grammar grammar) {
        this.grammar = grammar;
        buildFirsts(grammar);
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


    // TODO: FIX!!! (I need method to get i-th elem. of map or dictionary...)
    private void buildFirsts(Grammar grammar) {
        for (String terminal : grammar.getTerminals()) {
            Set<String> first = new HashSet<String>();
            firstSet.put(terminal, first);
        }
        for (String nonterminal : grammar.getNonterminals()){
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
        boolean symbolAdded;
        do {
            symbolAdded = false;
            for (String nonterminal : grammar.getNonterminals()) {
                for (Production production : grammar.getProds()) {

                    if (production.getLeftSide() != nonterminal) {
                        continue;
                    }
                    for (String item : firstSet.get(production.getRightSide().get(0))) {
                        if (item != Grammar.EPSILON && !firstSet.get(nonterminal).contains(item)) {
                            firstSet.get(nonterminal).add(item);
                            symbolAdded = true;
                        }
                    }

                    for (int i=1; i<production.getRightSide().size(); i++) {
                        boolean epsilons = true;
                        for (int j=0; j<i; j++) {
                            if (!firstSet.get(production.getRightSide().get(j)).contains(Grammar.EPSILON)) {
                                epsilons = false;
                            } else {
                                break;
                            }
                        }

                        if (epsilons) {
                            for (String item : firstSet.get(production.getRightSide().get(i))) {
                                if (item != Grammar.EPSILON && !firstSet.get(nonterminal).contains(item)) {
                                    firstSet.get(nonterminal).add(item);
                                    symbolAdded = true;
                                }
                            }

                            if (i == (production.getRightSide().size() - 1) && firstSet.get(production.getRightSide().get(production.getRightSide().size() - 1)).contains(Grammar.EPSILON)) {
                                firstSet.get(nonterminal).add(Grammar.EPSILON);
                            }
                        }
                    }

                    if (production.getRightSide().size() == 1 && firstSet.get(production.getRightSide().get(0)).contains(Grammar.EPSILON)) {
                        firstSet.get(nonterminal).add(Grammar.EPSILON);
                    }
                }
            }

        } while (symbolAdded);

        firstSet.remove(Grammar.EPSILON);

    }


}
