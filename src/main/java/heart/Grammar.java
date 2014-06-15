package heart;

import heart.Production;

import java.util.ArrayList;
import java.util.HashMap;
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



    Map<Integer, Production> productions;
    Map<Symbol, List<Symbol>> follows;
    List<Goto> gotos;
    int numberOfProduction;

    public Grammar(List<Production> prods, List<String> terminals, List<String> nonterminals) {
        this.prods = prods;
        this.terminals = terminals;
        this.nonterminals = nonterminals;
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

    //    public Grammar() {
//        productions = new HashMap<Integer, Production>();
//        numberOfProduction =0;
//        gotos = new ArrayList<Goto>();
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
