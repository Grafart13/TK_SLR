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

    public ParserGenerator(Grammar grammar) {
        this.grammar = grammar;
        this.firstSet = buildFirsts(grammar);
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


}
