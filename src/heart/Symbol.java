package heart;

/**
 * Created by dpjar_000 on 2014-06-14.
 */
public class Symbol {
    private Type type;
    private char sign;

    public Symbol (char _sign) {
       if (Character.isUpperCase(_sign)) {
            this.type = Type.NONTERMINAL;
        } else {
           this.type = Type.TERMINAL;
       }
       this.sign = _sign;
    }

    public String toString () {
        return Character.toString(sign);
    }
}
