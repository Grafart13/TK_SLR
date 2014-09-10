package heart;

import java.util.List;
import java.util.Map;

/**
 * Created by dpjar_000 on 2014-06-14.
 */
public class Production {

    private final String leftSide;
    private final List<String> rightSide;

    public Production(String leftSide, List<String> rightSide) {
        if (rightSide == null) {
            throw new NullPointerException("Right side must be not null!");
        }
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public String getLeftSide() {
        return leftSide;
    }

    public List<String> getRightSide() {
        return rightSide;
    }

    @Override
    public String toString() {
        return "Production{" +
                "leftSide='" + leftSide + '\'' +
                ", rightSide=" + rightSide +
                '}';
    }

    public String toStringShow() {
        String result = "";
        result = leftSide + "->";

        for (String a : rightSide) {
            result += a;
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Production that = (Production) o;

        if (!leftSide.equals(that.leftSide)) return false;
        if (rightSide.size() != that.rightSide.size()) return false;
        for (int i = 0; i < rightSide.size(); i++) {
            if (!rightSide.get(i).equals(that.rightSide.get(i))) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = leftSide.hashCode();
        result = 31 * result + rightSide.hashCode();
        return result;
    }

    Symbol left;
    Map<Integer, Symbol> right;
    int pointPosition;

//    public Production(String line) {
//        leftSide = line;
//        right = new HashMap<Integer, Symbol>();
//        pointPosition=0;
//     //   parseLine(line);
//        int i = 0;
//
//        String sign = "" + line.charAt(i);
//        i++;
//        if (line.charAt(i) == '\'') {
//            sign = sign + line.charAt(i);
//        }
//        i++;
//        Symbol pom = new Symbol(sign);
//        if (pom.isNonterminal()) {
//            left = pom;
//        } else {
//            System.out.println("Grammar is not SLR, it has left recursion");
//            return;
//        }
//        if ((Character.compare(line.charAt(i), '-')==1) && (Character.compare(line.charAt(++i), '>')==1)) {
//            int key=0;
//            sign = "" + line.charAt(i);
//            while (sign.compareTo("\n")==0) {
//                right.put(key, new Symbol(sign));
//                key++;
//                sign = "" + line.charAt(++i);
//            }
//        }
//    }

    public void setPointPosition(int i) {
        this.pointPosition = i;
    }

    public Symbol getLeft() {
        return left;
    }

    public Map<Integer, Symbol> getRight() {
        return right;
    }

    public int getPointPosition() {
        return pointPosition;
    }

    public boolean isEqual(Production prod) {
        if (!left.isEqual(prod.getLeft()))
            return false;
        int i = 0;
        if (prod.getRight().size() != right.size()) {
            return false;
        }
        int max = right.size();
        List<Symbol> right2_v = (List<Symbol>) prod.getRight().values();
        List<Symbol> right_v = (List<Symbol>) getRight().values();

        while (i < max) {
            Symbol sym = right_v.get(i);
            Symbol sym2 = right2_v.get(i);
            if (!sym.isEqual(sym2)) {
                return false;
            }
        }
        return true;
    }
}
