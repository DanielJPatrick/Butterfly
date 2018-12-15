package dragonfly.butterfly;


import java.io.Serializable;

public final class ImmutableLinkedMapNode<K, V> implements Serializable, Cloneable {
    private final K key;
    private final V value;
    private final ImmutableLinkedMapNode<K, V> next;

    ImmutableLinkedMapNode(final K key, final V value, final ImmutableLinkedMapNode<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj != null && obj instanceof ImmutableLinkedMapNode<?, ?>) {
            if(!(this.key == null && ((ImmutableLinkedMapNode)obj).key() == null)) {
                if(this.key == null || ((ImmutableLinkedMapNode)obj).key() == null) {
                    return false;
                } else {
                    if(!(this.key.equals(((ImmutableLinkedMapNode)obj).key()))) {
                        return false;
                    }
                }
            }
            if(!(this.value == null && ((ImmutableLinkedMapNode)obj).value() == null)) {
                if(this.value == null || ((ImmutableLinkedMapNode)obj).value() == null) {
                    return false;
                } else {
                    if(!(this.value.equals(((ImmutableLinkedMapNode)obj).value()))) {
                        return false;
                    }
                }
            }
            if(!(this.next == null && ((ImmutableLinkedMapNode)obj).next() == null)) {
                if(this.next == null || ((ImmutableLinkedMapNode)obj).next() == null) {
                    return false;
                } else {
                    if(!(this.next.equals(((ImmutableLinkedMapNode)obj).next()))) {
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
        return this.getClass().hashCode() +
                (this.key != null ? this.key.hashCode() : 0) +
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
                .append((this.key != null ? this.key.toString() : "null"))
                .append(",")
                .append((this.value != null ? this.value.toString() : "null"))
                .append(",")
                .append((this.next != null ? this.next.toString() : "null"))
                .append("}")
                .toString();
    }

    public final K key() {
        return this.key;
    }

    public final V value() {
        return this.value;
    }

    public final ImmutableLinkedMapNode<K, V> next() {
        return this.next;
    }
}