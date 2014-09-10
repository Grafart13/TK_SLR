package heart.symulator;

/**
 * @author Mateusz Drożdż
 */
public class StackItem {

    private final String value;
    private final Type type;

    public StackItem(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        if (type == Type.PRODUCTION) {
            return "T" + value;
        } else {
            return value;
        }
    }

    enum Type {
        PRODUCTION, SYMBOL;
    }
}
