package dragonfly.butterfly;


import java.io.Serializable;

public final class ConstraintName implements Serializable, Cloneable {
    private final ImmutableLinkedList<Character> name;

    public ConstraintName(final ImmutableLinkedList<Character> name) {
        this.name = name;
    }

    public ConstraintName(final String name) {
        this.name = Utils.toImmutableLinkedList(name);
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof ConstraintName)) {
            return false;
        }
        if(!(this.name == null && ((ConstraintName)obj).name == null)) {
            if(this.name == null || ((ConstraintName)obj).name == null) {
                return false;
            } else {
                if(!(this.name.equals(((ConstraintName)obj).name))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public final int hashCode() {
        return this.getClass().hashCode() +
                (this.name != null ? this.name.hashCode() : 0);
    }

    @Override
    public final Object clone() {
        return Utils.copy(this);
    }

    @Override
    public final String toString() {
        return new StringBuilder()
                .append("{")
                .append((this.name != null ? this.name.toString() : "null"))
                .append("}")
                .toString();
    }

    public final ImmutableLinkedList<Character> getName() {
        return name;
    }
}