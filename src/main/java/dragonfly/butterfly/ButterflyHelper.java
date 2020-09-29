package dragonfly.butterfly;


import android.database.sqlite.SQLiteDatabase;

public abstract class ButterflyHelper {

    @SuppressWarnings("unchecked")
    public static final String[] generateDatabaseSql(final Schema schema) {
        final ImmutableLinkedList<ImmutableLinkedList<Character>> sql = generateTablesSql(new ImmutableLinkedList(ImmutableLinkedList.class), schema.getTables(), schema.getTables().length() - 1);
        return generateDatabaseSql(sql.startNode(), new String[sql.length()], 0);
    }

    private static final String[] generateDatabaseSql(final ImmutableLinkedListNode<ImmutableLinkedList<Character>> currentNode, final String[] strSql, final int currentIndex) {
        if(currentNode != null) {
            strSql[currentIndex] = Character.toString(currentNode.value().values(true));
            return generateDatabaseSql(currentNode.next(), strSql, currentIndex + 1);
        } else {
            return strSql;
        }
    }

    private static final ImmutableLinkedList<ImmutableLinkedList<Character>> generateTablesSql(final ImmutableLinkedList<ImmutableLinkedList<Character>> sql, final ImmutableLinkedMap<ImmutableLinkedList<Character>, Table> tables, final int currentIndex) {
        if(currentIndex >= 0) {
                return generateTablesSql(sql.prepend(generateConstraintsSql(generateColumnsSql(new ImmutableLinkedList<Character>().prepend(new Character('C')).prepend(new Character('R')).prepend(new Character('E')).prepend(new Character('A')).prepend(new Character('T')).prepend(new Character('E')).prepend(new Character(' ')).prepend(new Character('T'))
                        .prepend(new Character('A')).prepend(new Character('B')).prepend(new Character('L')).prepend(new Character('E')).prepend(new Character(' '))
                        .mergeStart(tables.get(currentIndex).value().getName()).prepend(new Character(' ')).prepend(new Character('(')), tables.get(currentIndex).value().getColumns(), tables.get(currentIndex).value().getColumns().length() - 1, false), tables.get(currentIndex).value().getConstraints(), 0).prepend(new Character(')'))
                        .prepend(new Character(' ')).prepend(new Character('W')).prepend(new Character('I')).prepend(new Character('T')).prepend(new Character('H')).prepend(new Character('O')).prepend(new Character('U')).prepend(new Character('T')).prepend(new Character(' '))
                        .prepend(new Character('R')).prepend(new Character('O')).prepend(new Character('W')).prepend(new Character('I')).prepend(new Character('D'))), tables, currentIndex - 1);
        } else {
            return sql;
        }
    }

    private static final ImmutableLinkedList<Character> generateColumnsSql(final ImmutableLinkedList<Character> sql, final ImmutableLinkedMap<ImmutableLinkedList<Character>, Column> columns, final int currentIndex, final boolean sqlColumnDividerAdded) {
        if(currentIndex >= 0) {
            if(currentIndex != columns.length() - 1) {
                if(!sqlColumnDividerAdded) {
                    return generateColumnsSql(sql.prepend(new Character(',')).prepend(new Character(' ')), columns, currentIndex, true);
                }
            }
            return generateColumnsSql(generateDefaultValueSql(generateNotNullSql(sql.mergeStart(columns.get(currentIndex).value().getName()).prepend(new Character(' ')).mergeStart(columns.get(currentIndex).value().getDataType()), columns.get(currentIndex).value().getNotNull()), columns.get(currentIndex).value().getDefaultValue()), columns, currentIndex - 1, false);
        } else {
            return sql;
        }
    }

    private static final ImmutableLinkedList<Character> generateConstraintsSql(final ImmutableLinkedList<Character> sql, final ImmutableLinkedMap<ImmutableLinkedList<Character>, Constraint> constraints, final int currentIndex) {
        if(currentIndex < constraints.length()) {
            if(constraints.get(currentIndex).value() instanceof PrimaryKey) {
                return generateConstraintsSql(generatePrimaryKeySql(sql, ((PrimaryKey)constraints.get(currentIndex).value())), constraints, currentIndex + 1);
            } else if(constraints.get(currentIndex).value() instanceof Unique) {
                return generateConstraintsSql(generateUniqueSql(sql, ((Unique)constraints.get(currentIndex).value())), constraints, currentIndex + 1);
            } else if(constraints.get(currentIndex).value() instanceof Index) {
                return generateConstraintsSql(generateIndexSql(sql, ((Index)constraints.get(currentIndex).value())), constraints, currentIndex + 1);
            } else if(constraints.get(currentIndex).value() instanceof ForeignKey) {
                return generateConstraintsSql(generateForeignKeySql(sql, ((ForeignKey)constraints.get(currentIndex).value())), constraints, currentIndex + 1);
            } else if(constraints.get(currentIndex).value() instanceof Check) {
                return generateConstraintsSql(generateCheckSql(sql, ((Check)constraints.get(currentIndex).value())), constraints, currentIndex + 1);
            }
            return generateConstraintsSql(sql, constraints, currentIndex + 1);
        } else {
            return sql;
        }
    }

    private static final ImmutableLinkedList<Character> generateColumnNamesSql(final ImmutableLinkedList<Character> sql, final ImmutableLinkedSet<ImmutableLinkedList<Character>> columnNames, final int currentIndex, final boolean sqlColumnDividerAdded) {
        if(currentIndex >= 0) {
            if(currentIndex != columnNames.length() - 1) {
                if(!sqlColumnDividerAdded) {
                    return generateColumnNamesSql(sql.prepend(new Character(',')).prepend(new Character(' ')), columnNames, currentIndex, true);
                }
            }
            return generateColumnNamesSql(sql.mergeStart(columnNames.get(currentIndex).value()), columnNames, currentIndex - 1, false);
        } else {
            return sql;
        }
    }

    private static final ImmutableLinkedList<Character> generatePrimaryKeySql(final ImmutableLinkedList<Character> sql, PrimaryKey primaryKey) {
        return generateColumnNamesSql(sql.prepend(new Character(',')).prepend(new Character(' ')).prepend(new Character('C')).prepend(new Character('O')).prepend(new Character('N')).prepend(new Character('S')).prepend(new Character('T')).prepend(new Character('R'))
                .prepend(new Character('A')).prepend(new Character('I')).prepend(new Character('N')).prepend(new Character('T')).prepend(new Character(' '))
                .mergeStart(primaryKey.getName()).prepend(new Character(' ')).prepend(new Character('P')).prepend(new Character('R')).prepend(new Character('I')).prepend(new Character('M')).prepend(new Character('A'))
                .prepend(new Character('R')).prepend(new Character('Y')).prepend(new Character(' ')).prepend(new Character('K')).prepend(new Character('E')).prepend(new Character('Y')).prepend(new Character(' '))
                .prepend(new Character('(')), primaryKey.getColumnNames(), primaryKey.getColumnNames().length() - 1, false).prepend(new Character(')'));
    }

    private static final ImmutableLinkedList<Character> generateUniqueSql(final ImmutableLinkedList<Character> sql, Unique unique) {
        return generateColumnNamesSql(sql.prepend(new Character(',')).prepend(new Character(' ')).prepend(new Character('C')).prepend(new Character('O'))
                .prepend(new Character('N')).prepend(new Character('S')).prepend(new Character('T')).prepend(new Character('R')).prepend(new Character('A')).prepend(new Character('I')).prepend(new Character('N')).prepend(new Character('T')).prepend(new Character(' '))
                .mergeStart(unique.getName()).prepend(new Character(' ')).prepend(new Character('U')).prepend(new Character('N')).prepend(new Character('I')).prepend(new Character('Q')).prepend(new Character('U')).prepend(new Character('E'))
                .prepend(new Character(' ')).prepend(new Character('(')), unique.getColumnNames(), unique.getColumnNames().length() - 1, false).prepend(new Character(')'));
    }

    @SuppressWarnings("unchecked")
    private static final ImmutableLinkedList<Character> generateIndexSql(final ImmutableLinkedList<Character> sql, Index index) {
        return generateColumnNamesSql(sql.prepend(new Character(',')).prepend(new Character(' ')).prepend(new Character('C')).prepend(new Character('O'))
                .prepend(new Character('N')).prepend(new Character('S')).prepend(new Character('T')).prepend(new Character('R')).prepend(new Character('A')).prepend(new Character('I')).prepend(new Character('N')).prepend(new Character('T')).prepend(new Character(' '))
                .mergeStart(index.getName()).prepend(new Character(' ')).prepend(new Character('I')).prepend(new Character('N')).prepend(new Character('D')).prepend(new Character('E')).prepend(new Character('X'))
                .prepend(new Character(' ')).prepend(new Character('(')), index.getColumnNames(), index.getColumnNames().length() - 1, false).prepend(new Character(')'));
    }

    @SuppressWarnings("unchecked")
    private static final ImmutableLinkedList<Character> generateNotNullSql(final ImmutableLinkedList<Character> sql, NotNull notNull) {
        if(notNull != null) {
            return sql.prepend(new Character(' ')).prepend(new Character('N')).prepend(new Character('O')).prepend(new Character('T')).prepend(new Character(' ')).prepend(new Character('N')).prepend(new Character('U'))
                    .prepend(new Character('L')).prepend(new Character('L'));
        } else {
            return sql;
        }
    }

    @SuppressWarnings("unchecked")
    private static final ImmutableLinkedList<Character> generateDefaultValueSql(final ImmutableLinkedList<Character> sql, DefaultValue defaultValue) {
        if(defaultValue != null) {
            return sql.prepend(new Character(' ')).prepend(new Character('D')).prepend(new Character('E')).prepend(new Character('F')).prepend(new Character('A')).prepend(new Character('U')).prepend(new Character('L'))
                    .prepend(new Character('T')).prepend(new Character(' ')).prepend(new Character('\'')).mergeStart(defaultValue.getValue()).prepend(new Character('\''));
        } else {
            return sql;
        }
    }

    @SuppressWarnings("unchecked")
    private static final ImmutableLinkedList<Character> generateForeignKeySql(final ImmutableLinkedList<Character> sql, ForeignKey foreignKey) {
        return generateColumnNamesSql(sql.prepend(new Character(',')).prepend(new Character(' ')).prepend(new Character('C')).prepend(new Character('O')).prepend(new Character('N')).prepend(new Character('S')).prepend(new Character('T')).prepend(new Character('R')).prepend(new Character('A')).prepend(new Character('I')).prepend(new Character('N')).prepend(new Character('T')).prepend(new Character(' '))
                .mergeStart(foreignKey.getName()).prepend(new Character(' ')).prepend(new Character('F')).prepend(new Character('O')).prepend(new Character('R')).prepend(new Character('E')).prepend(new Character('I')).prepend(new Character('G')).prepend(new Character('N')).prepend(new Character(' '))
                .prepend(new Character('K')).prepend(new Character('E')).prepend(new Character('Y')).prepend(new Character(' ')).prepend(new Character('(')), new ImmutableLinkedSet<ImmutableLinkedList<Character>>((Class<ImmutableLinkedList<Character>>)(Class<?>)ImmutableLinkedList.class).prepend(foreignKey.getFromColumnName()), 0, false)
                .prepend(new Character(')')).prepend(new Character(' ')).prepend(new Character('R')).prepend(new Character('E')).prepend(new Character('F')).prepend(new Character('E')).prepend(new Character('R')).prepend(new Character('E')).prepend(new Character('N')).prepend(new Character('C')).prepend(new Character('E'))
                .prepend(new Character('S')).prepend(new Character(' ')).mergeStart(foreignKey.getToTableName()).prepend(new Character('(')).mergeStart(foreignKey.getToColumnName()).prepend(new Character(')')).prepend(new Character(' ')).prepend(new Character('O')).prepend(new Character('N'))
                .prepend(new Character(' ')).prepend(new Character('D')).prepend(new Character('E')).prepend(new Character('L')).prepend(new Character('E')).prepend(new Character('T')).prepend(new Character('E')).prepend(new Character(' ')).prepend(new Character('C')).prepend(new Character('A')).prepend(new Character('S'))
                .prepend(new Character('C')).prepend(new Character('A')).prepend(new Character('D')).prepend(new Character('E'));
    }

    private static final ImmutableLinkedList<Character> generateCheckSql(final ImmutableLinkedList<Character> sql, Check check) {
        return sql.prepend(new Character(',')).prepend(new Character(' ')).prepend(new Character('C')).prepend(new Character('O')).prepend(new Character('N')).prepend(new Character('S')).prepend(new Character('T')).prepend(new Character('R'))
                .prepend(new Character('A')).prepend(new Character('I')).prepend(new Character('N')).prepend(new Character('T')).prepend(new Character(' '))
                .mergeStart(check.getName()).prepend(new Character(' ')).prepend(new Character('C')).prepend(new Character('H')).prepend(new Character('E')).prepend(new Character('C')).prepend(new Character('K'))
                .prepend(new Character(' ')).prepend(new Character('(')).mergeStart(check.getCondition()).prepend(new Character(')'));
    }

    public static final void insert (Object objectToInsert) {

    }
}