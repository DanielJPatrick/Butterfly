package dragonfly.butterfly;


import java.io.Serializable;

public class Comparator<T> implements Serializable, Cloneable {
    public int compare(final T o1, final T o2) {
        return 0;
    }

    @Override
    public final int hashCode() {
        return super.hashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        return super.equals(obj);
    }

    @Override
    public final Object clone() {
        return Utils.copy(this);
    }

    @Override
    public final String toString() {
        return super.toString();
    }
}