package heart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dpjar_000 on 2014-06-14.
 */
public class Goto {
    private int id;
    private Goto from;
    private Symbol przez; // change
    private List<Production> productionList;

    public Goto(int _id, Goto _from, Symbol _przez) {
        productionList = new ArrayList<Production>();
        this.id = _id;
        this.from = _from;
        this.przez = _przez;
    }

    public boolean isEqual(Goto anotherGoto) {
        // add new Goto only, when it is unrepeated
        //
        // How?

        List<Production> anotherProductionList = anotherGoto.getProductionList();
        int i=0;
        if (anotherProductionList.size() != productionList.size()) {
            System.out.println("Different set of goto");
            return false;
        }
        int max = productionList.size();
        while (i < max ) {
            Production prod = productionList.get(i);
            Production prod2 = anotherProductionList.get(i);
            if (!prod2.isEqual(prod)) {
                return false;
            }
            i++;
        }

        return true;
    }

    public List<Production> getProductionList() {
        return productionList;
    }
}
