package dragonfly.butterfly;


import java.io.Serializable;

public final class ForeignKey extends Constraint implements Serializable, Cloneable {
    private final ImmutableLinkedList<Character> fromColumnName;
    private final ImmutableLinkedList<Character> toTableName;
    private final ImmutableLinkedList<Character> toColumnName;

    ForeignKey(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> toTableName, final ImmutableLinkedList<Character> toColumnName) {
        super(name);
        this.fromColumnName = null;
        this.toTableName = toTableName;
        this.toColumnName = toColumnName;
    }

    ForeignKey(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> fromTableName, final ImmutableLinkedList<Character> fromColumnName, final ImmutableLinkedList<Character> toTableName, final ImmutableLinkedList<Character> toColumnName) {
        super(name, fromTableName);
        this.fromColumnName = fromColumnName;
        this.toTableName = toTableName;
        this.toColumnName = toColumnName;
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof ForeignKey)) {
            return false;
        }
        if(!(super.equals(obj))) {
            return false;
        }
        if(!(this.fromColumnName == null && ((ForeignKey)obj).fromColumnName == null)) {
            if(this.fromColumnName == null || ((ForeignKey)obj).fromColumnName == null) {
                return false;
            } else {
                if(!(this.fromColumnName.equals(((ForeignKey)obj).fromColumnName))) {
                    return false;
                }
            }
        }
        if(!(this.toTableName == null && ((ForeignKey)obj).toTableName == null)) {
            if(this.toTableName == null || ((ForeignKey)obj).toTableName == null) {
                return false;
            } else {
                if(!(this.toTableName.equals(((ForeignKey)obj).toTableName))) {
                    return false;
                }
            }
        }
        if(!(this.toColumnName == null && ((ForeignKey)obj).toColumnName == null)) {
            if(this.toColumnName == null || ((ForeignKey)obj).toColumnName == null) {
                return false;
            } else {
                if(!(this.toColumnName.equals(((ForeignKey)obj).toColumnName))) {
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
                (this.toTableName != null ? this.toTableName.hashCode() : 0) +
                (this.toColumnName != null ? this.toColumnName.hashCode() : 0);
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
                .append((this.fromColumnName != null ? this.fromColumnName.toString() : "null"))
                .append(",")
                .append((this.toTableName != null ? this.toTableName.toString() : "null"))
                .append(",")
                .append((this.toColumnName != null ? this.toColumnName.toString() : "null"))
                .append("}")
                .toString();
    }

    public final ImmutableLinkedList<Character> getToTableName() {
        return toTableName;
    }

    public final ImmutableLinkedList<Character> getToColumnName() {
        return toColumnName;
    }

    public final ImmutableLinkedList<Character> getFromColumnName() {
        return fromColumnName;
    }
}