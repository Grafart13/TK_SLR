package heart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dpjar_000 on 2014-06-14.
 */
public class Production {

    Symbol left;
    Map<Integer, Symbol> right;

    public Production(String line) {
        right = new HashMap<Integer, Symbol>();
     //   parseLine(line);
        left = new Symbol(line.charAt(0));
        if ((Character.compare(line.charAt(1), '-')==1) && (Character.compare(line.charAt(2), '>')==1)) {

        }

    }
}
