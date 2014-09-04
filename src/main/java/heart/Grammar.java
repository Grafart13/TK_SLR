package heart;

import java.util.Map;
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



    Map<Integer, Production> productions;
    Map<Symbol, List<Symbol>> follows;
    List<GotoOld> gotoOlds;
    int numberOfProduction;

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

    //    public Grammar() {
//        productions = new HashMap<Integer, Production>();
//        numberOfProduction =0;
//        gotoOlds = new ArrayList<GotoOld>();
//        follows = new HashMap<Symbol, List<Symbol>>();
//    }

//    protected void addZeroProduction(Production prod) {
//        String line = "S'->";
//        line += prod.getLeft();
//        Production zeroProduction = new Production(line);
//        productions.put(numberOfProduction, zeroProduction);
//        numberOfProduction++;
//    }

//    public void addProduction(Production prod) {
//        if (numberOfProduction == 0) {
//            // add zero production
//            addZeroProduction(prod);
//        }
//        productions.put(numberOfProduction, prod);
//        numberOfProduction++;
//    }

//    public Production parseLine(String line) {
  //      Production production = new Production(line);
  //      return null;
   // }

//    public void parseAll(String all) {
//        String[] lines = all.split(" ");
//        // or \n ->look
//        //String[] lines = all.split("\n");
//        for (String line : lines) {
//            //Production production = new Production(line);
//            addProduction(new Production(line));
//        }
//    }

    public void generateFollows() {

    }

    public void generateGoto() {

    }

    public void generateParseTable() {

    }
}
