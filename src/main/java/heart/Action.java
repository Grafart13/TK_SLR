package heart;

/**
 * @author Mateusz Drożdż
 */
public class Action {
    private final ActionType type;
    private final int state;


    public Action(ActionType type, int state) {
        this.type = type;
        this.state = state;
    }

    @Override
    public String toString() {
        String s = null;
        switch (type) {
            case GOTO:
                s = "T_" + state;
                break;
            case SHIFT:
                s = "sh" + state;
                break;
            case REDUCE:
                s = "red" + state;
                break;
            case ACCEPT:
                s = "acc";
                break;
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Action action = (Action) o;

        if (state != action.state) return false;
        if (type != action.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + state;
        return result;
    }
}
