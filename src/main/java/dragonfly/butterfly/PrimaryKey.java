package dragonfly.butterfly;


import java.io.Serializable;

public final class PrimaryKey extends Constraint implements Serializable, Cloneable {
    private final ImmutableLinkedSet<ImmutableLinkedList<Character>> columnNames;

    PrimaryKey(final ImmutableLinkedList<Character> name, final ImmutableLinkedSet<ImmutableLinkedList<Character>> columnNames, final ImmutableLinkedList<Character> fromTableName) {
        super(name, fromTableName);
        this.columnNames = columnNames.set(new Comparator<ImmutableLinkedList<Character>>() {
            @Override
            public int compare(ImmutableLinkedList<Character> o1, ImmutableLinkedList<Character> o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                } else if (o1 == null) {
                    return -1;
                } else if (o2 == null) {
                    return 1;
                }
                if (o1.length() == o2.length() && o1.contains(o2,
                        new Comparator<ImmutableLinkedListNode<Character>>() {
                            @Override
                            public int compare(ImmutableLinkedListNode<Character> o1, ImmutableLinkedListNode<Character> o2) {
                                if(o1 == null && o2 == null) {
                                    return 0;
                                } else if(o1 == null || o2 == null) {
                                    return -1;
                                }
                                if(o1.value() == null && o2.value() == null) {
                                    return 0;
                                } else if(o1.value() == null || o2.value() == null) {
                                    return -1;
                                }
                                if(o1.value().equals(o2.value())) {
                                    return 0;
                                } else {
                                    if(o1.hashCode() >= o2.hashCode()) {
                                        return 1;
                                    } else {
                                        return -1;
                                    }
                                }
                            }
                        })
                        ) {
                    return 0;
                } else {
                    if(o1.hashCode() >= o2.hashCode()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        });
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof PrimaryKey)) {
            return false;
        }
        if(!(super.equals(obj))) {
            return false;
        }
        if(!(this.columnNames == null && ((PrimaryKey)obj).columnNames == null)) {
            if(this.columnNames == null || ((PrimaryKey) obj).columnNames == null) {
                return false;
            } else {
                if(!(this.columnNames.equals(((PrimaryKey) obj).columnNames))) {
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
                (this.columnNames != null ? this.columnNames.hashCode() : 0);
    }

    @Override
    public final Object clone() {
        return Utils.copy(this);
    }

    @Override
    public final String toString() {
        return new StringBuilder()
                .append(super.toString())
                .append((this.columnNames != null ? "     " + this.columnNames.toString() : ""))
                .toString();
    }

    public final ImmutableLinkedSet<ImmutableLinkedList<Character>> getColumnNames() {
        return columnNames;
    }
}