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
    Map<Integer, Production> productions;
    Map<Symbol, List<Symbol>> follows;
    List<Goto> gotos;
    int numberOfProduction;

    public Grammar() {
        productions = new HashMap<Integer, Production>();
        numberOfProduction =0;
        gotos = new ArrayList<Goto>();
        follows = new HashMap<Symbol, List<Symbol>>();
    }

    protected void addZeroProduction(Production prod) {
        String line = "S'->";
        line += prod.getLeft();
        Production zeroProduction = new Production(line);
        productions.put(numberOfProduction, zeroProduction);
        numberOfProduction++;
    }

    public void addProduction(Production prod) {
        if (numberOfProduction == 0) {
            // add zero production
            addZeroProduction(prod);
        }
        productions.put(numberOfProduction, prod);
        numberOfProduction++;
    }

//    public Production parseLine(String line) {
  //      Production production = new Production(line);
  //      return null;
   // }

    public void parseAll(String all) {
        String[] lines = all.split(" ");
        // or \n ->look
        //String[] lines = all.split("\n");
        for (String line : lines) {
            //Production production = new Production(line);
            addProduction(new Production(line));
        }
    }

    public void generateFollows() {

    }

    public void generateGoto() {

    }

    public void generateParseTable() {

    }
}
