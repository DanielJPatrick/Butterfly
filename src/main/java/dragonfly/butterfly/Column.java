package dragonfly.butterfly;


import java.io.Serializable;

public final class Column implements Serializable, Cloneable {
    private final ImmutableLinkedList<Character> name;
    private final ImmutableLinkedList<Character> fromTableName;
    private final ImmutableLinkedList<Character> dataType;
    private final DefaultValue defaultValue;
    private final NotNull notNull;
    private final ForeignKey foreignKey;
    private final PrimaryKey primaryKey;

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = null;
        this.notNull = null;
        this.foreignKey = null;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final ImmutableLinkedList<Character> defaultValue) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = new DefaultValue(defaultValue);
        this.notNull = null;
        this.foreignKey = null;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final DefaultValue defaultValue) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        this.notNull = null;
        this.foreignKey = null;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final boolean nullable) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = null;
        if(nullable) {
            this.notNull = null;
        } else {
            this.notNull = new NotNull();
        }
        this.foreignKey = null;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final NotNull notNull) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = null;
        this.notNull = notNull;
        this.foreignKey = null;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final ForeignKey foreignKey) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = null;
        this.notNull = null;
        this.foreignKey = foreignKey;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final ImmutableLinkedList<Character> defaultValue, final boolean nullable) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = new DefaultValue(defaultValue);
        if(nullable) {
            this.notNull = null;
        } else {
            this.notNull = new NotNull();
        }
        this.foreignKey = null;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final DefaultValue defaultValue, final boolean nullable) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        if(nullable) {
            this.notNull = null;
        } else {
            this.notNull = new NotNull();
        }
        this.foreignKey = null;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final ImmutableLinkedList<Character> defaultValue, final NotNull notNull) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = new DefaultValue(defaultValue);
        this.notNull = notNull;
        this.foreignKey = null;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final DefaultValue defaultValue, final NotNull notNull) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        this.notNull = notNull;
        this.foreignKey = null;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final ImmutableLinkedList<Character> defaultValue, final ForeignKey foreignKey) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = new DefaultValue(defaultValue);
        this.notNull = null;
        this.foreignKey = foreignKey;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final DefaultValue defaultValue, final ForeignKey foreignKey) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        this.notNull = null;
        this.foreignKey = foreignKey;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final boolean nullable, final ForeignKey foreignKey) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = null;
        if(nullable) {
            this.notNull = null;
        } else {
            this.notNull = new NotNull();
        }
        this.foreignKey = foreignKey;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final NotNull notNull, final ForeignKey foreignKey) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = null;
        this.notNull = notNull;
        this.foreignKey = foreignKey;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final ImmutableLinkedList<Character> defaultValue, final boolean nullable, final ForeignKey foreignKey) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = new DefaultValue(defaultValue);
        if(nullable) {
            this.notNull = null;
        } else {
            this.notNull = new NotNull();
        }
        this.foreignKey = foreignKey;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final DefaultValue defaultValue, final boolean nullable, final ForeignKey foreignKey) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        if(nullable) {
            this.notNull = null;
        } else {
            this.notNull = new NotNull();
        }
        this.foreignKey = foreignKey;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final ImmutableLinkedList<Character> defaultValue, final NotNull notNull, final ForeignKey foreignKey) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = new DefaultValue(defaultValue);
        this.notNull = notNull;
        this.foreignKey = foreignKey;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> dataType, final DefaultValue defaultValue, final NotNull notNull, final ForeignKey foreignKey) {
        this.name = name;
        this.fromTableName = null;
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        this.notNull = notNull;
        this.foreignKey = foreignKey;
        this.primaryKey = null;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> fromTableName, final ImmutableLinkedList<Character> dataType, final ImmutableLinkedList<Character> defaultValue, final NotNull notNull, final ForeignKey foreignKey, final PrimaryKey primaryKey) {
        this.name = name;
        this.fromTableName = fromTableName;
        this.dataType = dataType;
        this.defaultValue = new DefaultValue(defaultValue);
        this.notNull = notNull;
        this.foreignKey = foreignKey;
        this.primaryKey = primaryKey;
    }

    Column(final ImmutableLinkedList<Character> name, final ImmutableLinkedList<Character> fromTableName, final ImmutableLinkedList<Character> dataType, final DefaultValue defaultValue, final NotNull notNull, final ForeignKey foreignKey, final PrimaryKey primaryKey) {
        this.name = name;
        this.fromTableName = fromTableName;
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        this.notNull = notNull;
        this.foreignKey = foreignKey;
        this.primaryKey = primaryKey;
    }

    public final boolean isAutoIncrementing() {
        if(this.primaryKey != null && this.dataType.contains(new ImmutableLinkedList<Character>().prepend(new Character('i')).prepend(new Character('n')).prepend(new Character('t')),
                new Comparator<ImmutableLinkedListNode<Character>>() {
                    @Override
                    public final int compare(final ImmutableLinkedListNode<Character> o1, final ImmutableLinkedListNode<Character> o2) {
                        if (o1 == null && o2 == null) {
                            return 0;
                        } else if (o1 == null) {
                            return -1;
                        } else if (o2 == null) {
                            return 1;
                        }
                        if(o1.value() == null && o2.value() == null) {
                            return 0;
                        } else if(o1.value() == null) {
                            return -1;
                        } else if(o2.value() == null) {
                            return 1;
                        }
                        if(Character.toLowerCase(o1.value()).equals(Character.toLowerCase(o2.value()))) {
                            return 0;
                        } else {
                            if(o1.value().hashCode() >= o2.value().hashCode()) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }
                    }
                }))
        {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Column)) {
            return false;
        }
        if(!(this.name == null && ((Column)obj).name == null)) {
            if(this.name == null || ((Column)obj).name == null) {
                return false;
            } else {
                if(!(this.name.equals(((Column)obj).name))) {
                    return false;
                }
            }
        }
        if(!(this.fromTableName == null && ((Column)obj).fromTableName == null)) {
            if(this.fromTableName == null || ((Column)obj).fromTableName == null) {
                return false;
            } else {
                if(!(this.fromTableName.equals(((Column)obj).fromTableName))) {
                    return false;
                }
            }
        }
        if(!(this.dataType == null && ((Column)obj).dataType == null)) {
            if(this.dataType == null || ((Column)obj).dataType == null) {
                return false;
            } else {
                if(!(this.dataType.equals(((Column)obj).dataType))) {
                    return false;
                }
            }
        }
        if(!(this.defaultValue == null && ((Column)obj).defaultValue == null)) {
            if(this.defaultValue == null || ((Column)obj).defaultValue == null) {
                return false;
            } else {
                if(!(this.defaultValue.equals(((Column)obj).defaultValue))) {
                    return false;
                }
            }
        }
        if(!(this.notNull == null && ((Column)obj).notNull == null)) {
            if(this.notNull == null || ((Column)obj).notNull == null) {
                return false;
            } else {
                if(!(this.notNull.equals(((Column)obj).notNull))) {
                    return false;
                }
            }
        }
        if(!(this.foreignKey == null && ((Column)obj).foreignKey == null)) {
            if(this.foreignKey == null || ((Column)obj).foreignKey == null) {
                return false;
            } else {
                if(!(this.foreignKey.equals(((Column)obj).foreignKey))) {
                    return false;
                }
            }
        }
        if(!(this.primaryKey == null && ((Column)obj).primaryKey == null)) {
            if(this.primaryKey == null || ((Column)obj).primaryKey == null) {
                return false;
            } else {
                if(!(this.primaryKey.equals(((Column)obj).primaryKey))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public final int hashCode() {
        return this.getClass().hashCode() +
                (this.name != null ? this.name.hashCode() : 0) +
                (this.fromTableName != null ? this.fromTableName.hashCode() : 0) +
                (this.dataType != null ? this.dataType.hashCode() : 0) +
                (this.defaultValue != null ? this.defaultValue.hashCode() : 0) +
                (this.notNull != null ? this.notNull.hashCode() : 0) +
                (this.foreignKey != null ? this.foreignKey.hashCode() : 0) +
                (this.primaryKey != null ? this.primaryKey.hashCode() : 0);
    }

    @Override
    public final Object clone() {
        return Utils.copy(this);
    }

    @Override
    public final String toString() {
        return new StringBuilder()
                .append("{")
                .append((this.name != null ? this.name.toString() : "null"))
                .append(",")
                .append((this.fromTableName != null ? this.fromTableName.toString() : "null"))
                .append(",")
                .append((this.dataType != null ? this.dataType.toString() : "null"))
                .append(",")
                .append((this.defaultValue != null ? this.defaultValue.toString() : "null"))
                .append(",")
                .append((this.notNull != null ? this.notNull.toString() : "null"))
                .append(",")
                .append((this.foreignKey != null ? this.foreignKey.toString() : "null"))
                .append(",")
                .append((this.primaryKey != null ? this.primaryKey.toString() : "null"))
                .append("}")
                .toString();
    }

    public final ImmutableLinkedList<Character> getName() {
        return name;
    }

    public final ImmutableLinkedList<Character> getDataType() {
        return dataType;
    }

    public final DefaultValue getDefaultValue() {
        return defaultValue;
    }

    public final NotNull getNotNull() {
        return notNull;
    }

    public final ForeignKey getForeignKey() {
        return foreignKey;
    }

    public final ImmutableLinkedList<Character> getFromTableName() {
        return fromTableName;
    }

    public final PrimaryKey getPrimaryKey() {
        return primaryKey;
    }
}