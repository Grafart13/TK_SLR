package heart;

import com.sun.xml.internal.org.jvnet.fastinfoset.stax.LowLevelFastInfosetStreamWriter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dpjar_000 on 2014-06-14.
 */
public class Production {

    Symbol left;
    Map<Integer, Symbol> right;
    int pointPosition;

    public Production(String line) {
        right = new HashMap<Integer, Symbol>();
        pointPosition=0;
     //   parseLine(line);
        int i = 0;

        String sign = "" + line.charAt(i);
        i++;
        if (line.charAt(i) == '\'') {
            sign = sign + line.charAt(i);
        }
        i++;
        Symbol pom = new Symbol(sign);
        if (pom.isNonterminal()) {
            left = pom;
        } else {
            System.out.println("Grammar is not SLR, it has left recursion");
            return;
        }
        if ((Character.compare(line.charAt(i), '-')==1) && (Character.compare(line.charAt(++i), '>')==1)) {
            int key=0;
            sign = "" + line.charAt(i);
            while (sign.compareTo("\n")==0) {
                right.put(key, new Symbol(sign));
                key++;
                sign = "" + line.charAt(++i);
            }
        }
    }

    public void setPointPosition (int i) {
        this.pointPosition = i;
    }

    public Symbol getLeft() {
        return left;
    }

    public Map<Integer,Symbol> getRight() {
        return right;
    }

    public int getPointPosition () {
        return pointPosition;
    }

    public boolean isEqual(Production prod) {
        if (!left.isEqual(prod.getLeft()))
           return false;
        int i=0;
        if (prod.getRight().size() != right.size()) {
            return false;
        }
        int max = right.size();
        List<Symbol> right2_v = (List<Symbol>) prod.getRight().values();
        List<Symbol> right_v = (List<Symbol>) getRight().values();

        while (i<max) {
            Symbol sym = right_v.get(i);
            Symbol sym2 = right2_v.get(i);
            if (!sym.isEqual(sym2)) {
                return false;
            }
        }
        return true;
    }
}
