package dragonfly.butterfly;


import java.io.Serializable;

public final class ImmutableLinkedSetNode<V> implements Serializable, Cloneable {
    private final V value;
    private final ImmutableLinkedSetNode<V> next;

    ImmutableLinkedSetNode(final V value, final ImmutableLinkedSetNode<V> next) {
        this.value = value;
        this.next = next;
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj != null && obj instanceof ImmutableLinkedSetNode<?>) {
            if(!(this.value.equals(((ImmutableLinkedSetNode)obj).value()))) {
                return false;
            }
            if(!(this.next.equals(((ImmutableLinkedSetNode)obj).next()))) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public final int hashCode() {
        return this.getClass().hashCode() +
                (this.value != null ? this.value.hashCode() : 0) +
                (this.next != null ? this.next.hashCode() : 0);
    }

    @Override
    public final Object clone() {
        return Utils.copy(this);
    }

    @Override
    public final String toString() {
        return new StringBuilder()
                .append("{")
                .append((this.value != null ? this.value.toString() : "null"))
                .append(",")
                .append((this.next != null ? this.next.toString() : "null"))
                .append("}")
                .toString();
    }

    public final V value() {
        return this.value;
    }

    public final ImmutableLinkedSetNode<V> next() {
        return this.next;
    }
}
