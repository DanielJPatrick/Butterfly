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
            if(!(this.value == null && ((ImmutableLinkedListNode)obj).value == null)) {
                if(this.value == null || ((ImmutableLinkedListNode)obj).value == null) {
                    return false;
                } else {
                    if(!(this.value.equals(((ImmutableLinkedListNode)obj).value))) {
                        return false;
                    }
                }
            }
            if(!(this.next == null && ((ImmutableLinkedListNode)obj).next == null)) {
                if(this.next == null || ((ImmutableLinkedListNode)obj).next == null) {
                    return false;
                } else {
                    if(!(this.next.equals(((ImmutableLinkedListNode)obj).next))) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public final int hashCode() {
        return super.hashCode();
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

    public final ImmutableLinkedListNode<V> next() {
        return this.next;
    }
}