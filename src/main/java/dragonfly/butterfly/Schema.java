package dragonfly.butterfly;


import java.io.Serializable;

public final class Schema implements Serializable, Cloneable {
    private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Table> tables;

    private Schema(final ImmutableLinkedMap<ImmutableLinkedList<Character>, Table> tables) {
        this.tables = tables;
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Schema)) {
            return false;
        }
        if(!(this.tables == null && ((Schema)obj).tables == null)) {
            if(this.tables == null || ((Schema) obj).tables == null) {
                return false;
            } else {
                if(!(this.tables.equals(((Schema) obj).tables))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public final int hashCode() {
        return this.getClass().hashCode() +
                (this.tables != null ? this.tables.hashCode() : 0);
    }

    @Override
    public final Object clone() {
        return Utils.copy(this);
    }

    @Override
    public final String toString() {
        return new StringBuilder()
                .append("{")
                .append((this.tables != null ? this.tables.toString() : "null"))
                .append("}")
                .toString();
    }

    public final ImmutableLinkedMap<ImmutableLinkedList<Character>, Table> getTables() {
        return tables;
    }

    public final static class Builder {
        private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Table> tables;

        @SuppressWarnings("unchecked")
        public Builder(final ITable... tables) {
            this.tables = this.create(new ImmutableLinkedMap<ImmutableLinkedList<Character>, Table>((Class<ImmutableLinkedList<Character>>)(Class<?>)ImmutableLinkedList.class, Table.class), tables, 0);
        }

        private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Table> create(final ImmutableLinkedMap<ImmutableLinkedList<Character>, Table> immutableLinkedMap, final ITable[] tables, final int currentIndex) {
            if(tables.length > currentIndex) {
                if(tables[currentIndex] instanceof Table) {
                    return this.create(immutableLinkedMap.add(((Table)tables[currentIndex]).getName(), (Table)tables[currentIndex]), tables, currentIndex + 1);
                }
                if(tables[currentIndex] instanceof Table.FkTable) {
                    return this.create(immutableLinkedMap.add(((Table.FkTable)tables[currentIndex]).getName(), Table.newInstance(((Table.FkTable)tables[currentIndex]).getName(),
                            ((Table.FkTable)tables[currentIndex]).getColumns(), ((Table.FkTable)tables[currentIndex]).getConstraints())), tables, currentIndex + 1);
                }
                return this.create(immutableLinkedMap, tables, currentIndex + 1);
            } else {
                return immutableLinkedMap;
            }
        }

        public final Schema build() {
            return new Schema(this.tables);
        }
    }
}