package dragonfly.butterfly;


import java.io.Serializable;

public final class ImmutableLinkedListNode<V> implements Serializable, Cloneable {
    private final V value;
    private final ImmutableLinkedListNode<V> next;

    ImmutableLinkedListNode(final V value, final ImmutableLinkedListNode<V> next) {
        this.value = value;
        this.next = next;
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj != null && obj instanceof ImmutableLinkedListNode<?>) {
            return super.equals(obj);
        } else {
            return false;
        }
    }

    @Override
    public final int hashCode() {
        return this.getClass().hashCode() +
                (this.value != null ? this.value.hashCode() : 0);
    }

    @Override
    public final Object clone() {
        return Utils.copy(this);
    }

    @Override
    public final String toString() {
        return new StringBuilder()
                .append((this.value != null ? this.value.toString() : ""))
                .toString();
    }

    public final V value() {
        return this.value;
    }

    public final ImmutableLinkedListNode<V> next() {
        return this.next;
    }
}