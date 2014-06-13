package heart;

import heart.Production;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dpjar_000 on 2014-06-14.
 */
public class Grammar {
    Map<Integer, Production> productions;


    public Grammar() {
        productions = new HashMap<Integer, Production>();

    }

    public void addProduction(Production prod) {
        int key = productions.size();
        if (key == 0) {
            // add zero production
            key++;
        }
        productions.put(key, prod);
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
}
