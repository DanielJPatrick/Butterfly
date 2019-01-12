package dragonfly.butterfly;


import java.io.Serializable;

public final class Table implements ITable, Serializable, Cloneable {
    private final ImmutableLinkedList<Character> name;
    private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Column> columns;
    private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> constraints;

    @SuppressWarnings("unchecked")
    public Table(final String name) {
        this.name = Utils.toImmutableLinkedList(name);
        this.columns = new ImmutableLinkedMap<ImmutableLinkedList<Character>, Column>((Class<ImmutableLinkedList<Character>>)(Class<?>)ImmutableLinkedList.class, Column.class);
        this.constraints = new ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint>((Class<ImmutableLinkedList<Character>>)(Class<?>)ImmutableLinkedList.class, Constraint.class);
    }

    @SuppressWarnings("unchecked")
    Table(final ImmutableLinkedList<Character> name) {
        this.name = name;
        this.columns = new ImmutableLinkedMap<ImmutableLinkedList<Character>, Column>((Class<ImmutableLinkedList<Character>>)(Class<?>)ImmutableLinkedList.class, Column.class);
        this.constraints = new ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint>((Class<ImmutableLinkedList<Character>>)(Class<?>)ImmutableLinkedList.class, Constraint.class);
    }

    Table(final ImmutableLinkedList<Character> name, final ImmutableLinkedMap<ImmutableLinkedList<Character>, Column> columns, final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> constraints) {
        this.name = name;
        this.columns = this.completeColumnDetails(columns, columns.startNode(), this.getPrimaryKey(constraints.startNode()));
        this.constraints = this.addColumnConstraintsToTableConstraints(constraints, this.columns.startNode());
    }

    final PrimaryKey getPrimaryKey() {
        return this.getPrimaryKey(this.constraints.startNode());
    }

    private final PrimaryKey getPrimaryKey(final ImmutableLinkedMapNode<ImmutableLinkedList<Character>, Constraint> currentNode) {
        if(currentNode != null) {
            if(currentNode.value() != null && currentNode.value() instanceof PrimaryKey) {
                return (PrimaryKey)currentNode.value();
            } else {
                return getPrimaryKey(currentNode.next());
            }
        } else {
            return null;
        }
    }

    private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Column> completeColumnDetails(ImmutableLinkedMap<ImmutableLinkedList<Character>, Column> columns, ImmutableLinkedMapNode<ImmutableLinkedList<Character>, Column> currentNode, PrimaryKey primaryKey) {
        if(currentNode != null) {
            if(currentNode.value() != null) {
                // Currently can't give custom name to a notNull constraint. May add this functionality later.
                final NotNull notNull;
                if(currentNode.value().getNotNull() != null) {
                    if(currentNode.value().getNotNull().getName() != null && currentNode.value().getNotNull().getName().length() > 0) {
                        notNull = new NotNull(currentNode.value().getNotNull().getName(), this.name, currentNode.value().getName());
                    } else {
                        notNull = new NotNull(this.getNextConstraintName(NotNull.class, this, currentNode.value()), this.name, currentNode.value().getName());
                    }
                } else {
                    notNull = null;
                }

                // Currently can't give custom name to a defaultValue constraint. May add this functionality later.
                final DefaultValue defaultValue;
                if(currentNode.value().getDefaultValue() != null) {
                    if(currentNode.value().getDefaultValue().getName() != null && currentNode.value().getDefaultValue().getName().length() > 0) {
                        defaultValue = new DefaultValue(currentNode.value().getDefaultValue().getName(), this.name, currentNode.value().getName(), currentNode.value().getDefaultValue().getValue());
                    } else {
                        defaultValue = new DefaultValue(this.getNextConstraintName(DefaultValue.class, this, currentNode.value()), this.name, currentNode.value().getName(), currentNode.value().getDefaultValue().getValue());
                    }
                } else {
                    defaultValue = null;
                }

                // Don't need to check for if name set as it is checked when addForeignKey() is called. Leaving check here anyway as a safeguard.
                final ForeignKey foreignKey;
                if(currentNode.value().getForeignKey() != null) {
                    if(currentNode.value().getForeignKey().getName() != null && currentNode.value().getForeignKey().getName().length() > 0) {
                        foreignKey = new ForeignKey(currentNode.value().getForeignKey().getName(), this.name, currentNode.value().getName(), currentNode.value().getForeignKey().getToTableName(),
                                currentNode.value().getForeignKey().getToColumnName());
                    } else {
                        foreignKey = new ForeignKey(this.getNextConstraintName(ForeignKey.class, this, currentNode.value()), this.name, currentNode.value().getName(), currentNode.value().getForeignKey().getToTableName(),
                                currentNode.value().getForeignKey().getToColumnName());
                    }
                } else {
                    foreignKey = null;
                }

                final PrimaryKey columnPrimaryKey;
                if(primaryKey != null && primaryKey.getColumnNames() != null && primaryKey.getColumnNames().contains(currentNode.value().getName())) {
                    columnPrimaryKey = primaryKey;
                } else {
                    columnPrimaryKey = null;
                }

                return this.completeColumnDetails(columns.replace(currentNode, new ImmutableLinkedMapNode<ImmutableLinkedList<Character>, Column>(currentNode.key(), new Column(currentNode.value().getName(), this.name, currentNode.value().getDataType(),
                        defaultValue, notNull, foreignKey, columnPrimaryKey), currentNode.next())), currentNode.next(), primaryKey);
            } else {
                return this.completeColumnDetails(columns, currentNode.next(), primaryKey);
            }
        } else {
            return columns;
        }
    }

    private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> addColumnConstraintsToTableConstraints(final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> constraints, final ImmutableLinkedMapNode<ImmutableLinkedList<Character>, Column> currentNode) {
        return addColumnForeignKeyConstraintsToTableConstraints(addColumnNotNullConstraintsToTableConstraints(addColumnDefaultValueConstraintsToTableConstraints(constraints, currentNode), currentNode), currentNode);
    }

    private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> addColumnNotNullConstraintsToTableConstraints(final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> constraints, final ImmutableLinkedMapNode<ImmutableLinkedList<Character>, Column> currentNode) {
        if(currentNode != null) {
            if (currentNode.value() != null) {
                if(currentNode.value().getNotNull() != null) {
                    return this.addColumnNotNullConstraintsToTableConstraints(constraints.prepend(currentNode.value().getNotNull().getName(), currentNode.value().getNotNull()), currentNode.next());
                }
            }
            return this.addColumnNotNullConstraintsToTableConstraints(constraints, currentNode.next());
        } else {
            return constraints;
        }
    }

    private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> addColumnDefaultValueConstraintsToTableConstraints(final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> constraints, final ImmutableLinkedMapNode<ImmutableLinkedList<Character>, Column> currentNode) {
        if(currentNode != null) {
            if (currentNode.value() != null) {
                if(currentNode.value().getDefaultValue() != null) {
                    return this.addColumnDefaultValueConstraintsToTableConstraints(constraints.prepend(currentNode.value().getDefaultValue().getName(), currentNode.value().getDefaultValue()), currentNode.next());
                }
            }
            return this.addColumnDefaultValueConstraintsToTableConstraints(constraints, currentNode.next());
        } else {
            return constraints;
        }
    }

    private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> addColumnForeignKeyConstraintsToTableConstraints(final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> constraints, final ImmutableLinkedMapNode<ImmutableLinkedList<Character>, Column> currentNode) {
        if(currentNode != null) {
            if (currentNode.value() != null) {
                if(currentNode.value().getForeignKey() != null) {
                    return this.addColumnForeignKeyConstraintsToTableConstraints(constraints.prepend(currentNode.value().getForeignKey().getName(), currentNode.value().getForeignKey()), currentNode.next());
                }
            }
            return this.addColumnForeignKeyConstraintsToTableConstraints(constraints, currentNode.next());
        } else {
            return constraints;
        }
    }

    public final FkTable addColumn(final String name, final String dataType) {
        return new FkTable(this.name, this.columns.prepend(Utils.toImmutableLinkedList(name), new Column(Utils.toImmutableLinkedList(name), Utils.toImmutableLinkedList(dataType))), this.constraints);
    }

    public final FkTable addColumn(final String name, final String dataType, final String defaultValue) {
        return new FkTable(this.name, this.columns.prepend(Utils.toImmutableLinkedList(name), new Column(Utils.toImmutableLinkedList(name), Utils.toImmutableLinkedList(dataType), Utils.toImmutableLinkedList(defaultValue))), this.constraints);
    }

    public final FkTable addColumn(final String name, final String dataType, final boolean nullable) {
        return new FkTable(this.name, this.columns.prepend(Utils.toImmutableLinkedList(name), new Column(Utils.toImmutableLinkedList(name), Utils.toImmutableLinkedList(dataType), nullable)), this.constraints);
    }

    public final FkTable addColumn(final String name, final String dataType, final String defaultValue, final boolean nullable) {
        return new FkTable(this.name, this.columns.prepend(Utils.toImmutableLinkedList(name), new Column(Utils.toImmutableLinkedList(name), Utils.toImmutableLinkedList(dataType), Utils.toImmutableLinkedList(defaultValue), nullable)), this.constraints);
    }

    public final Table addPrimaryKey(final String... columnNames) {
        return new Table(this.name, this.columns, this.constraints.prepend(this.getNextConstraintName(PrimaryKey.class, this, null), new PrimaryKey(new ConstraintName(this.getNextConstraintName(PrimaryKey.class, this, null)).getName(), Utils.toImmutableLinkedSet(columnNames), this.name)));
    }

    public final Table addPrimaryKey(final ConstraintName constraintName, final String... columnNames) {
        return new Table(this.name, this.columns, this.constraints.prepend(constraintName.getName(), new PrimaryKey(constraintName.getName(), Utils.toImmutableLinkedSet(columnNames), this.name)));
    }

    public final Table addUnique(final String... columnNames) {
        return new Table(this.name, this.columns, this.constraints.prepend(this.getNextConstraintName(Unique.class, this, null), new Unique(new ConstraintName(this.getNextConstraintName(Unique.class, this, null)).getName(), Utils.toImmutableLinkedSet(columnNames), this.name)));
    }

    public final Table addUnique(final ConstraintName constraintName, final String... columnNames) {
        return new Table(this.name, this.columns, this.constraints.prepend(constraintName.getName(), new Unique(constraintName.getName(), Utils.toImmutableLinkedSet(columnNames), this.name)));
    }

    public final Table addIndex(final String... columnNames) {
        return new Table(this.name, this.columns, this.constraints.prepend(this.getNextConstraintName(Index.class, this, null), new Index(new ConstraintName(this.getNextConstraintName(Index.class, this, null)).getName(), Utils.toImmutableLinkedSet(columnNames), this.name)));
    }

    public final Table addIndex(final ConstraintName constraintName, final String... columnNames) {
        return new Table(this.name, this.columns, this.constraints.prepend(constraintName.getName(), new Index(constraintName.getName(), Utils.toImmutableLinkedSet(columnNames), this.name)));
    }

    public final Table addCheck(final String condition) {
        return new Table(this.name, this.columns, this.constraints.prepend(this.getNextConstraintName(Check.class, this, null), new Check(new ConstraintName(this.getNextConstraintName(Check.class, this, null)).getName(), this.name, Utils.toImmutableLinkedList(condition))));
    }

    public final Table addCheck(final ConstraintName constraintName, final String condition) {
        return new Table(this.name, this.columns, this.constraints.prepend(constraintName.getName(), new Check(constraintName.getName(), this.name, Utils.toImmutableLinkedList(condition))));
    }

    private final ImmutableLinkedList<Character> getNextConstraintName(final Class constraintType, final Table table, final Column column) {
        if(constraintType == PrimaryKey.class) {
            return Utils.toImmutableLinkedList("PrimaryKey").mergeStart(table.name);
        } else if(constraintType == ForeignKey.class) {
            return Utils.toImmutableLinkedList("ForeignKey").mergeStart(table.name).mergeStart(column.getName());
        } else if(constraintType == NotNull.class) {
            return Utils.toImmutableLinkedList("NotNull").mergeStart(table.name).mergeStart(column.getName());
        } else if(constraintType == DefaultValue.class) {
            return Utils.toImmutableLinkedList("DefaultValue").mergeStart(table.name).mergeStart(column.getName());
        } else if(constraintType == Unique.class) {
            return Utils.toImmutableLinkedList("Unique").mergeStart(table.name).mergeStart(Utils.toImmutableLinkedList(String.valueOf(table.constraints.length() + 1)));
        } else if(constraintType == Index.class) {
            return Utils.toImmutableLinkedList("Index").mergeStart(table.name).mergeStart(Utils.toImmutableLinkedList(String.valueOf(table.constraints.length() + 1)));
        } else if(constraintType == Check.class) {
            return Utils.toImmutableLinkedList("Check").mergeStart(table.name).mergeStart(Utils.toImmutableLinkedList(String.valueOf(table.constraints.length() + 1)));
        } else {
            return null;
        }
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Table)) {
            return false;
        }
        if(!(this.name == null && ((Table)obj).name == null)) {
            if(this.name == null || ((Table)obj).name == null) {
                return false;
            } else {
                if (!(this.name.equals(((Table)obj).name))) {
                    return false;
                }
            }
        }
        if(!(this.columns == null && ((Table)obj).columns == null)) {
            if(this.columns == null || ((Table)obj).columns == null) {
                return false;
            } else {
                if (!(this.columns.equals(((Table)obj).columns))) {
                    return false;
                }
            }
        }
        if(!(this.constraints == null && ((Table)obj).constraints == null)) {
            if(this.constraints == null || ((Table)obj).constraints == null) {
                return false;
            } else {
                if (!(this.constraints.equals(((Table)obj).constraints))) {
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
                (this.columns != null ? this.columns.hashCode() : 0) +
                (this.constraints != null ? this.constraints.hashCode() : 0);
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
                .append((this.columns != null ? this.columns.toString() : "null"))
                .append(",")
                .append((this.constraints != null ? this.constraints.toString() : "null"))
                .append("}")
                .toString();
    }

    public final ImmutableLinkedList<Character> getName() {
        return name;
    }

    public final ImmutableLinkedMap<ImmutableLinkedList<Character>, Column> getColumns() {
        return columns;
    }

    public final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> getConstraints() {
        return constraints;
    }

    public static final class FkTable implements ITable, Serializable, Cloneable {
        private final ImmutableLinkedList<Character> name;
        private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Column> columns;
        private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> constraints;

        private FkTable(final ImmutableLinkedList<Character> name, final ImmutableLinkedMap<ImmutableLinkedList<Character>, Column> columns, final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> constraints) {
            this.name = name;
            this.columns = this.completeColumnDetails(columns, columns.startNode(), this.getPrimaryKey(constraints.startNode()));
            this.constraints = this.addColumnConstraintsToTableConstraints(constraints, this.columns.startNode());
        }

        final PrimaryKey getPrimaryKey(final ImmutableLinkedMapNode<ImmutableLinkedList<Character>, Constraint> currentNode) {
            if(currentNode != null) {
                if(currentNode.value() != null && currentNode.value() instanceof PrimaryKey) {
                    return (PrimaryKey)currentNode.value();
                } else {
                    return getPrimaryKey(currentNode.next());
                }
            } else {
                return null;
            }
        }

        private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Column> completeColumnDetails(ImmutableLinkedMap<ImmutableLinkedList<Character>, Column> columns, ImmutableLinkedMapNode<ImmutableLinkedList<Character>, Column> currentNode, PrimaryKey primaryKey) {
            if(currentNode != null) {
                if(currentNode.value() != null) {
                    // Currently can't give custom name to a notNull constraint. May add this functionality later.
                    final NotNull notNull;
                    if(currentNode.value().getNotNull() != null) {
                        if(currentNode.value().getNotNull().getName() != null && currentNode.value().getNotNull().getName().length() > 0) {
                            notNull = new NotNull(currentNode.value().getNotNull().getName(), this.name, currentNode.value().getName());
                        } else {
                            notNull = new NotNull(this.getNextConstraintName(NotNull.class, this, currentNode.value()), this.name, currentNode.value().getName());
                        }
                    } else {
                        notNull = null;
                    }

                    // Currently can't give custom name to a defaultValue constraint. May add this functionality later.
                    final DefaultValue defaultValue;
                    if(currentNode.value().getDefaultValue() != null) {
                        if(currentNode.value().getDefaultValue().getName() != null && currentNode.value().getDefaultValue().getName().length() > 0) {
                            defaultValue = new DefaultValue(currentNode.value().getDefaultValue().getName(), this.name, currentNode.value().getName(), currentNode.value().getDefaultValue().getValue());
                        } else {
                            defaultValue = new DefaultValue(this.getNextConstraintName(DefaultValue.class, this, currentNode.value()), this.name, currentNode.value().getName(), currentNode.value().getDefaultValue().getValue());
                        }
                    } else {
                        defaultValue = null;
                    }

                    // Don't need to check for if name set as it is checked when addForeignKey() is called. Leaving check here anyway as a safeguard.
                    final ForeignKey foreignKey;
                    if(currentNode.value().getForeignKey() != null) {
                        if(currentNode.value().getForeignKey().getName() != null && currentNode.value().getForeignKey().getName().length() > 0) {
                            foreignKey = new ForeignKey(currentNode.value().getForeignKey().getName(), this.name, currentNode.value().getName(), currentNode.value().getForeignKey().getToTableName(),
                                    currentNode.value().getForeignKey().getToColumnName());
                        } else {
                            foreignKey = new ForeignKey(this.getNextConstraintName(ForeignKey.class, this, currentNode.value()), this.name, currentNode.value().getName(), currentNode.value().getForeignKey().getToTableName(),
                                    currentNode.value().getForeignKey().getToColumnName());
                        }
                    } else {
                        foreignKey = null;
                    }

                    final PrimaryKey columnPrimaryKey;
                    if(primaryKey != null && primaryKey.getColumnNames() != null && primaryKey.getColumnNames().contains(currentNode.value().getName())) {
                        columnPrimaryKey = primaryKey;
                    } else {
                        columnPrimaryKey = null;
                    }

                    return this.completeColumnDetails(columns.replace(currentNode, new ImmutableLinkedMapNode<ImmutableLinkedList<Character>, Column>(currentNode.key(), new Column(currentNode.value().getName(), this.name, currentNode.value().getDataType(),
                            defaultValue, notNull, foreignKey, columnPrimaryKey), currentNode.next())), currentNode.next(), primaryKey);
                } else {
                    return this.completeColumnDetails(columns, currentNode.next(), primaryKey);
                }
            } else {
                return columns;
            }
        }

        private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> addColumnConstraintsToTableConstraints(final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> constraints, final ImmutableLinkedMapNode<ImmutableLinkedList<Character>, Column> currentNode) {
            return addColumnForeignKeyConstraintsToTableConstraints(addColumnNotNullConstraintsToTableConstraints(addColumnDefaultValueConstraintsToTableConstraints(constraints, currentNode), currentNode), currentNode);
        }

        private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> addColumnNotNullConstraintsToTableConstraints(final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> constraints, final ImmutableLinkedMapNode<ImmutableLinkedList<Character>, Column> currentNode) {
            if(currentNode != null) {
                if (currentNode.value() != null) {
                    if(currentNode.value().getNotNull() != null) {
                        return this.addColumnNotNullConstraintsToTableConstraints(constraints.prepend(currentNode.value().getNotNull().getName(), currentNode.value().getNotNull()), currentNode.next());
                    }
                }
                return this.addColumnNotNullConstraintsToTableConstraints(constraints, currentNode.next());
            } else {
                return constraints;
            }
        }

        private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> addColumnDefaultValueConstraintsToTableConstraints(final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> constraints, final ImmutableLinkedMapNode<ImmutableLinkedList<Character>, Column> currentNode) {
            if(currentNode != null) {
                if (currentNode.value() != null) {
                    if(currentNode.value().getDefaultValue() != null) {
                        return this.addColumnDefaultValueConstraintsToTableConstraints(constraints.prepend(currentNode.value().getDefaultValue().getName(), currentNode.value().getDefaultValue()), currentNode.next());
                    }
                }
                return this.addColumnDefaultValueConstraintsToTableConstraints(constraints, currentNode.next());
            } else {
                return constraints;
            }
        }

        private final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> addColumnForeignKeyConstraintsToTableConstraints(final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> constraints, final ImmutableLinkedMapNode<ImmutableLinkedList<Character>, Column> currentNode) {
            if(currentNode != null) {
                if (currentNode.value() != null) {
                    if(currentNode.value().getForeignKey() != null) {
                        return this.addColumnForeignKeyConstraintsToTableConstraints(constraints.prepend(currentNode.value().getForeignKey().getName(), currentNode.value().getForeignKey()), currentNode.next());
                    }
                }
                return this.addColumnForeignKeyConstraintsToTableConstraints(constraints, currentNode.next());
            } else {
                return constraints;
            }
        }

        public final FkTable addColumn(final String name, final String dataType) {
            return new FkTable(this.name, this.columns.prepend(Utils.toImmutableLinkedList(name), new Column(Utils.toImmutableLinkedList(name), Utils.toImmutableLinkedList(dataType))), this.constraints);
        }

        public final FkTable addColumn(final String name, final String dataType, final String defaultValue) {
            return new FkTable(this.name, this.columns.prepend(Utils.toImmutableLinkedList(name), new Column(Utils.toImmutableLinkedList(name), Utils.toImmutableLinkedList(dataType), Utils.toImmutableLinkedList(defaultValue))), this.constraints);
        }

        public final FkTable addColumn(final String name, final String dataType, final boolean nullable) {
            return new FkTable(this.name, this.columns.prepend(Utils.toImmutableLinkedList(name), new Column(Utils.toImmutableLinkedList(name), Utils.toImmutableLinkedList(dataType), nullable)), this.constraints);
        }

        public final FkTable addColumn(final String name, final String dataType, final String defaultValue, final boolean nullable) {
            return new FkTable(this.name, this.columns.prepend(Utils.toImmutableLinkedList(name), new Column(Utils.toImmutableLinkedList(name), Utils.toImmutableLinkedList(dataType), Utils.toImmutableLinkedList(defaultValue), nullable)), this.constraints);
        }

        public final Table addPrimaryKey(final String... columnNames) {
            return new Table(this.name, this.columns, this.constraints.prepend(this.getNextConstraintName(PrimaryKey.class, this, null), new PrimaryKey(new ConstraintName(this.getNextConstraintName(PrimaryKey.class, this, null)).getName(), Utils.toImmutableLinkedSet(columnNames), this.name)));
        }

        public final Table addPrimaryKey(final ConstraintName constraintName, final String... columnNames) {
            return new Table(this.name, this.columns, this.constraints.prepend(constraintName.getName(), new PrimaryKey(constraintName.getName(), Utils.toImmutableLinkedSet(columnNames), this.name)));
        }

        public final Table addUnique(final String... columnNames) {
            return new Table(this.name, this.columns, this.constraints.prepend(this.getNextConstraintName(Unique.class, this, null), new Unique(new ConstraintName(this.getNextConstraintName(Unique.class, this, null)).getName(), Utils.toImmutableLinkedSet(columnNames), this.name)));
        }

        public final Table addUnique(final ConstraintName constraintName, final String... columnNames) {
            return new Table(this.name, this.columns, this.constraints.prepend(constraintName.getName(), new Unique(constraintName.getName(), Utils.toImmutableLinkedSet(columnNames), this.name)));
        }

        public final Table addIndex(final String... columnNames) {
            return new Table(this.name, this.columns, this.constraints.prepend(this.getNextConstraintName(Index.class, this, null), new Index(new ConstraintName(this.getNextConstraintName(Index.class, this, null)).getName(), Utils.toImmutableLinkedSet(columnNames), this.name)));
        }

        public final Table addIndex(final ConstraintName constraintName, final String... columnNames) {
            return new Table(this.name, this.columns, this.constraints.prepend(constraintName.getName(), new Index(constraintName.getName(), Utils.toImmutableLinkedSet(columnNames), this.name)));
        }

        public final Table addCheck(final String condition) {
            return new Table(this.name, this.columns, this.constraints.prepend(this.getNextConstraintName(Check.class, this, null), new Check(new ConstraintName(this.getNextConstraintName(Check.class, this, null)).getName(), this.name, Utils.toImmutableLinkedList(condition))));
        }

        public final Table addCheck(final ConstraintName constraintName, final String condition) {
            return new Table(this.name, this.columns, this.constraints.prepend(constraintName.getName(), new Check(constraintName.getName(), this.name, Utils.toImmutableLinkedList(condition))));
        }

        // Creates a foreign key from the last added Column
        public final Table addForeignKey(final String toTableName, final String toColumnName) {
            if (this.columns.length() > 0) {
                if (this.columns.get(0) != null) {
                    if (this.columns.get(0).value() != null) {
                        return new Table(this.name, this.columns.replace(0,
                                new Column(this.columns.get(0).value().getName(), this.columns.get(0).value().getFromTableName(),
                                        this.columns.get(0).value().getDataType(), this.columns.get(0).value().getDefaultValue(),
                                        this.columns.get(0).value().getNotNull(),
                                        new ForeignKey(this.getNextConstraintName(ForeignKey.class, this, this.columns.get(0).value()),
                                                Utils.toImmutableLinkedList(toTableName), Utils.toImmutableLinkedList(toColumnName)),
                                        this.columns.get(0).value().getPrimaryKey())
                        ), this.constraints);
                    }
                }
            }
            return new Table(this.name, this.columns, this.constraints);
        }

        // Creates a foreign key from the last added Column
        public final Table addForeignKey(final String name, final String toTableName, final String toColumnName) {
            if (this.columns.length() > 0) {
                if (this.columns.get(0) != null) {
                    if (this.columns.get(0).value() != null) {
                        return new Table(this.name, this.columns.replace(0,
                                new Column(this.columns.get(0).value().getName(), this.columns.get(0).value().getFromTableName(),
                                        this.columns.get(0).value().getDataType(), this.columns.get(0).value().getDefaultValue(),
                                        this.columns.get(0).value().getNotNull(),
                                        new ForeignKey(Utils.toImmutableLinkedList(name), Utils.toImmutableLinkedList(toTableName), Utils.toImmutableLinkedList(toColumnName)),
                                        this.columns.get(0).value().getPrimaryKey())
                        ), this.constraints);
                    }
                }
            }
            return new Table(this.name, this.columns, this.constraints);
        }

        private final ImmutableLinkedList<Character> getNextConstraintName(final Class constraintType, final FkTable table, final Column column) {
            if(constraintType == PrimaryKey.class) {
                return Utils.toImmutableLinkedList("PrimaryKey").mergeStart(table.name);
            } else if(constraintType == ForeignKey.class) {
                return Utils.toImmutableLinkedList("ForeignKey").mergeStart(table.name).mergeStart(column.getName());
            } else if(constraintType == NotNull.class) {
                return Utils.toImmutableLinkedList("NotNull").mergeStart(table.name).mergeStart(column.getName());
            } else if(constraintType == DefaultValue.class) {
                return Utils.toImmutableLinkedList("DefaultValue").mergeStart(table.name).mergeStart(column.getName());
            } else if(constraintType == Unique.class) {
                return Utils.toImmutableLinkedList("Unique").mergeStart(table.name).mergeStart(Utils.toImmutableLinkedList(String.valueOf(table.constraints.length() + 1)));
            } else if(constraintType == Index.class) {
                return Utils.toImmutableLinkedList("Index").mergeStart(table.name).mergeStart(Utils.toImmutableLinkedList(String.valueOf(table.constraints.length() + 1)));
            } else if(constraintType == Check.class) {
                return Utils.toImmutableLinkedList("Check").mergeStart(table.name).mergeStart(Utils.toImmutableLinkedList(String.valueOf(table.constraints.length() + 1)));
            } else {
                return null;
            }
        }

        @Override
        public final boolean equals(final Object obj) {
            if(obj == null) {
                return false;
            }
            if(!(obj instanceof FkTable)) {
                return false;
            }
            if(!(this.name == null && ((FkTable)obj).name == null)) {
                if(this.name == null || ((FkTable)obj).name == null) {
                    return false;
                } else {
                    if(!(this.name.equals(((FkTable)obj).name))) {
                        return false;
                    }
                }
            }
            if(!(this.columns == null && ((FkTable)obj).columns == null)) {
                if(this.columns == null || ((FkTable)obj).columns == null) {
                    return false;
                } else {
                    if(!(this.columns.equals(((FkTable)obj).columns))) {
                        return false;
                    }
                }
            }
            if(!(this.constraints == null && ((FkTable)obj).constraints == null)) {
                if(this.constraints == null || ((FkTable)obj).constraints == null) {
                    return false;
                } else {
                    if(!(this.constraints.equals(((FkTable)obj).constraints))) {
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
                    (this.columns != null ? this.columns.hashCode() : 0) +
                    (this.constraints != null ? this.constraints.hashCode() : 0);
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
                    .append((this.columns != null ? this.columns.toString() : "null"))
                    .append(",")
                    .append((this.constraints != null ? this.constraints.toString() : "null"))
                    .append("}")
                    .toString();
        }

        public final ImmutableLinkedList<Character> getName() {
            return name;
        }

        public final ImmutableLinkedMap<ImmutableLinkedList<Character>, Column> getColumns() {
            return columns;
        }

        public final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> getConstraints() {
            return constraints;
        }
    }
}