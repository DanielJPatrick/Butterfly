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
            if((!this.key.equals(((ImmutableLinkedMapNode)obj).key))) {
                return false;
            }
            if(!(this.value.equals(((ImmutableLinkedMapNode)obj).value()))) {
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
                (this.key != null ? this.key.hashCode() : 0) +
                (this.value != null ? this.value.hashCode() : 0);
    }

    @Override
    public final Object clone() {
        return Utils.copy(this);
    }

    @Override
    public final String toString() {
        return new StringBuilder()
                .append((this.key != null ? this.key.toString() : ""))
                .append((this.value != null ? this.value.toString() : ""))
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