package heart;

import java.util.List;

/**
 * Created by dpjar_000 on 2014-06-14.
 */
public class Grammar {

    public final static String PRODUCTION_SIGN = "->";
    public final static String EPSILON = "eps";
    public final static String NEXT_PROD = "|";
    public final static String DOLLAR = "$";
    public final static String PRIM = "'";

    private final List<Production> prods;// = new ArrayList<Production>();
    private final List<String> terminals;// = new ArrayList<String>();
    private final List<String> nonterminals;// = new ArrayList<String>();

    // First non terminal symbol in grammar.
    private String startSymbol;

    public Grammar(List<Production> prods, List<String> terminals, List<String> nonterminals) {
        this.prods = prods;
        this.terminals = terminals;
        this.nonterminals = nonterminals;
        this.startSymbol = prods.get(0).getLeftSide();
    }

    public List<Production> getProds() {
        return prods;
    }

    public List<String> getTerminals() {
        return terminals;
    }

    public List<String> getNonterminals() {
        return nonterminals;
    }

    public String getStartSymbol() {
        return startSymbol;
    }

    public int getProdNumber(Production production) {
        return prods.lastIndexOf(production);
    }

    public String toString() {
        String result = "";

        int i = 0;
        for (Production p : prods) {
            result += "(" + i + ")";
            result += p.toStringShow();
            result += "\n";
            i++;
        }

        return result;
    }

}
