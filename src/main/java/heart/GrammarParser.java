package heart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author Mateusz Drożdż
 */
public class GrammarParser {
    String startSymbol = null;
    List<String> terminals = new ArrayList<String>();
    List<String> nonterminals = new ArrayList<String>();
    // i-th element is a list of 'prodRightSides' corresponded to i-th nonterminal from list above
    List<List<String>> prodRightSides = new ArrayList<List<String>>();

    List<Production> productions = new ArrayList<Production>();

    public Grammar parse(String input) throws Exception {


        int lineNumber = 0;
        for (String line : input.split("\n")) {
            if (line.replace(" ", "").equals("")) {
                continue;
            }
            if (line.indexOf(Grammar.PRODUCTION_SIGN) == -1) {
                throw new Exception("Line nr + " + lineNumber + " does not contain a production (" + Grammar.PRODUCTION_SIGN + ") sign");
            }
            if (line.indexOf(Grammar.PRODUCTION_SIGN) != line.lastIndexOf(Grammar.PRODUCTION_SIGN)) {
                throw new Exception("Line nr + " + lineNumber + " contains multiple production (" + Grammar.PRODUCTION_SIGN + ") signs");
            }
            if (line.indexOf(Grammar.NEXT_PROD) != -1 && line.indexOf(Grammar.PRODUCTION_SIGN) > line.indexOf(Grammar.NEXT_PROD)) {
                throw new Exception("Line nr " + lineNumber + " contains " + Grammar.NEXT_PROD + " sign before " + Grammar.PRODUCTION_SIGN + " sign.");
            }
            // trimming
            line = line.trim();

            String[] productionSides = line.split(Grammar.PRODUCTION_SIGN);
            // productionSides[0] - before -> sign.
            // productionSides[1] - after -> sing.

            // trimming
           for (int i=0; i<productionSides.length; i++) {
            productionSides[i] = productionSides[i].trim();
           }

            // getting nonterminals
            nonterminals.add(productionSides[0]); // -----

            // saving starting symbol
            if (startSymbol == null) {
                startSymbol = productionSides[0].concat(Grammar.PRIM);
                addZerothProduction(startSymbol, Arrays.asList(productionSides[0]));
            }

            // getting corresponded rightSides
            // todo: test for " " occurence or sth. similar

            // split right sides by | sign
            String[] rightSides = productionSides[1].split("\\" + Grammar.NEXT_PROD);

            // trimming
            for (int i=0; i<rightSides.length; i++) {
                rightSides[i] = rightSides[i].trim();
            }

            List<String> sides = new ArrayList<String>();
            for (String rightSide : rightSides) {
                sides.add(rightSide);
            }
            prodRightSides.add(sides); // -----

        }

        generateProductions();

        removeDuplicatedSymbols();

        nonterminals.add(startSymbol);

        return new Grammar(productions, terminals, nonterminals);
    }

    private void removeDuplicatedSymbols() {
        nonterminals = new ArrayList<String>(new LinkedHashSet<String>(nonterminals));
        terminals = new ArrayList<String>(new LinkedHashSet<String>(terminals));

    }

    // todo: test for " " occurence or sth. similar
    private void generateProductions() throws Exception {
        for (int i=0; i<nonterminals.size(); i++) {
            for (String rightSide : prodRightSides.get(i)) {
                Production production = new Production(nonterminals.get(i), Arrays.asList(rightSide.split(" ")));
                if (productions.contains(production)) {
                    throw new Exception("Duplicated production " + production);
                }
                productions.add(production);
                // collect terminals
                for (String symbol : rightSide.split(" ")) {
                    addTerminal(symbol);
                }
            }
        }
    }

    private void addZerothProduction(String startSymbol, List<String> strings) {
        productions.add(new Production(startSymbol, strings));
    }

    private void addTerminal(String symbol) {
        if (!nonterminals.contains(symbol) && !symbol.equals(Grammar.EPSILON)) {
            terminals.add(symbol);
        }
    }


}
