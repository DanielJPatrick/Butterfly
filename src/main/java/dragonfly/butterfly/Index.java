package dragonfly.butterfly;

import java.io.Serializable;

public final class Index extends Constraint implements Serializable, Cloneable {
    private final ImmutableLinkedSet<ImmutableLinkedList<Character>> columnNames;

    public Index(final ImmutableLinkedList<Character> name, final ImmutableLinkedSet<ImmutableLinkedList<Character>> columnNames, final ImmutableLinkedList<Character> fromTableName) {
        super(name, fromTableName);
        this.columnNames = columnNames;
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Unique)) {
            return false;
        }
        if(!(super.equals(obj))) {
            return false;
        }
        if(!(this.columnNames == null && ((Index)obj).columnNames == null)) {
            if(this.columnNames == null || ((Index)obj).columnNames == null) {
                return false;
            } else {
                if(!(this.columnNames.equals(((Index)obj).columnNames))) {
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
                (this.columnNames != null ? this.columnNames.hashCode() : 0);
    }

    @Override
    public final Object clone() {
        return Utils.copy(this);
    }

    @Override
    public final String toString() {
        return new StringBuilder()
                .append("{")
                .append(super.toString())
                .append(",")
                .append((this.columnNames != null ? this.columnNames.toString() : "null"))
                .append("}")
                .toString();
    }

    public final ImmutableLinkedSet<ImmutableLinkedList<Character>> getColumnNames() {
        return columnNames;
    }
}