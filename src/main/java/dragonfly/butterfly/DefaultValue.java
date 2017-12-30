package dragonfly.butterfly;


public class DefaultValue extends Constraint {
    private final ImmutableLinkedList<Character> fromColumnName;
    private final ImmutableLinkedList<Character> value;

    public DefaultValue(final ImmutableLinkedList<Character> value) {
        super();
        this.fromColumnName = null;
        this.value = value;
    }

    public DefaultValue(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> fromTableName, final ImmutableLinkedList<Character> fromColumnName, final ImmutableLinkedList<Character> value) {
        super(name, fromTableName);
        this.fromColumnName = fromColumnName;
        this.value = value;
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof DefaultValue)) {
            return false;
        }
        if(!(super.equals(obj))) {
            return false;
        }
        if(!(this.fromColumnName == null && ((DefaultValue)obj).fromColumnName == null)) {
            if(this.fromColumnName == null || ((DefaultValue)obj).fromColumnName == null) {
                return false;
            } else {
                if(!(this.fromColumnName.equals(((DefaultValue)obj).fromColumnName))) {
                    return false;
                }
            }
        }
        if(!(this.value == null && ((DefaultValue)obj).value == null)) {
            if(this.value == null || ((DefaultValue)obj).value == null) {
                return false;
            } else {
                if(!(this.value.equals(((DefaultValue)obj).value))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public final int hashCode() {
        return super.hashCode() +
                this.getClass().hashCode() +
                (this.fromColumnName != null ? this.fromColumnName.hashCode() : 0) +
                (this.value != null ? this.value.hashCode() : 0);
    }

    @Override
    public final Object clone() {
        return Utils.copy(this);
    }

    @Override
    public final String toString() {
        return new StringBuilder()
                .append(super.toString())
                .append((this.fromColumnName != null ? "     " + this.fromColumnName.toString() : ""))
                .append((this.value != null ? "     " + this.value.toString() : ""))
                .toString();
    }

    public final ImmutableLinkedList<Character> getFromColumnName() {
        return this.fromColumnName;
    }

    public final ImmutableLinkedList<Character> getValue() {
        return this.value;
    }
}
