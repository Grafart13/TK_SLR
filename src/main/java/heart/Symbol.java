package main.java.heart;

/**
 * Created by dpjar_000 on 2014-06-14.
 */
public class Symbol {
    private Type type;
    //private char sign;
    private String sign; // String is more comfortable because of symbol like S'
/*
    public Symbol (char _sign) {
       if (Character.isUpperCase(_sign)) {
            this.type = Type.NONTERMINAL;
        } else {
           this.type = Type.TERMINAL;
       }
       this.sign = _sign;
    }
  */

    public Symbol (String _sign) {
        if (Character.isUpperCase(_sign.charAt(0))) {
            this.type = Type.NONTERMINAL;
        } else {
            this.type = Type.TERMINAL;
        }
        if (_sign.length() <= 2) {
            this.sign = _sign;
        } else
            System.out.println("Wrong symbol.");
    }

    public String toString () {
        return sign;
    }

    public boolean isNonterminal () {
        return type == Type.NONTERMINAL ? true : false;
    }

    public String getSign() {
        return sign;
    }

    public Type getType() {
        return type;
    }
    public boolean isEqual(Symbol sym) {
        if (sign.equals(sym.getSign()) && type == sym.getType()) {
            return true;
        }
        return false;
    }
}
