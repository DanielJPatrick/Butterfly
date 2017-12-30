package dragonfly.butterfly;


import java.io.Serializable;

public final class NotNull extends Constraint implements Serializable, Cloneable {
    private final ImmutableLinkedList<Character> fromColumnName;

    NotNull() {
        super();
        this.fromColumnName = null;
    }

    NotNull(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> fromTableName, final ImmutableLinkedList<Character> fromColumnName) {
        super(name, fromTableName);
        this.fromColumnName = fromColumnName;
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof NotNull)) {
            return false;
        }
        if(!super.equals(obj)) {
            return false;
        }
        if(!(this.fromColumnName == null && ((NotNull)obj).fromColumnName == null)) {
            if(this.fromColumnName == null || ((NotNull) obj).fromColumnName == null) {
                return false;
            } else {
                if(!(this.fromColumnName.equals(((NotNull) obj).fromColumnName))) {
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
                (this.fromColumnName != null ? this.fromColumnName.hashCode() : 0);
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
                .toString();
    }

    public final ImmutableLinkedList<Character> getFromColumnName() {
        return fromColumnName;
    }
}