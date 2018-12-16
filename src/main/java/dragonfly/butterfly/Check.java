package dragonfly.butterfly;


import java.io.Serializable;

public final class Check extends Constraint implements Serializable, Cloneable{
    private final ImmutableLinkedList<Character> condition;

    public Check(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> fromTableName, final ImmutableLinkedList<Character> condition) {
        super(name, fromTableName);
        this.condition = condition;
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Check)) {
            return false;
        }
        if(!(super.equals(obj))) {
            return false;
        }
        if(!(this.condition == null && ((Check)obj).condition == null)) {
            if(this.condition == null || ((Check)obj).condition == null) {
                return false;
            } else {
                if(!(this.condition.equals(((Check)obj).condition))) {
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
                (this.condition != null ? this.condition.hashCode() : 0);
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
                .append((this.condition != null ? this.condition.toString() : "null"))
                .append("}")
                .toString();
    }

    public final ImmutableLinkedList<Character> getCondition() {
        return this.condition;
    }
}