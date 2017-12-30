package dragonfly.butterfly;


import java.io.Serializable;

public abstract class Constraint implements Serializable, Cloneable {
    private final ImmutableLinkedList<Character> name;
    private final ImmutableLinkedList<Character> fromTableName;

    Constraint() {
        this.name = null;
        this.fromTableName = null;
    }

    Constraint(final ImmutableLinkedList<Character> name) {
        this.name = name;
        this.fromTableName = null;
    }

    Constraint(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> fromTableName) {
        this.name = name;
        this.fromTableName = fromTableName;
    }

    @Override
    public boolean equals(final Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Constraint)) {
            return false;
        }
        if(!(this.name == null && ((Constraint)obj).name == null)) {
            if(this.name == null || ((Constraint) obj).name == null) {
                return false;
            } else {
                if(!(this.name.equals(((Constraint) obj).name))) {
                    return false;
                }
            }
        }
        if(!(this.fromTableName == null && ((Constraint)obj).fromTableName == null)) {
            if(this.fromTableName == null || ((Constraint) obj).fromTableName == null) {
                return false;
            } else {
                if(!(this.fromTableName.equals(((Constraint) obj).fromTableName))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode() +
                (this.name != null ? this.name.hashCode() : 0) +
                (this.fromTableName != null ? this.fromTableName.hashCode() : 0);
    }

    @Override
    public Object clone() {
        return Utils.copy(this);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append((this.name != null ? this.name.toString() : ""))
                .append((this.fromTableName != null ? "     " + this.fromTableName.toString() : ""))
                .toString();
    }

    public final ImmutableLinkedList<Character> getName() {
        return name;
    }

    public final ImmutableLinkedList<Character> getFromTableName() {
        return fromTableName;
    }
}