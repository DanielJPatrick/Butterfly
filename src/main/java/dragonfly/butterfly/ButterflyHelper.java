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
        if(tables.get(currentIndex) != null) {
                return generateTablesSql(sql.add(generateConstraintsSql(generateColumnsSql(new ImmutableLinkedList<Character>().add(new Character('C')).add(new Character('R')).add(new Character('E')).add(new Character('A')).add(new Character('T')).add(new Character('E')).add(new Character(' ')).add(new Character('T'))
                        .add(new Character('A')).add(new Character('B')).add(new Character('L')).add(new Character('E')).add(new Character(' '))
                        .mergeStart(tables.get(currentIndex).value().getName()).add(new Character(' ')).add(new Character('(')), tables.get(currentIndex).value().getColumns(), 0, false), tables.get(currentIndex).value().getConstraints(), 0).add(new Character(')'))
                        .add(new Character(' ')).add(new Character('W')).add(new Character('I')).add(new Character('T')).add(new Character('H')).add(new Character('O')).add(new Character('U')).add(new Character('T')).add(new Character(' '))
                        .add(new Character('R')).add(new Character('O')).add(new Character('W')).add(new Character('I')).add(new Character('D'))), tables, currentIndex - 1);
        } else {
            return sql;
        }
    }

    private static final ImmutableLinkedList<Character> generateColumnsSql(final ImmutableLinkedList<Character> sql, final ImmutableLinkedMap<ImmutableLinkedList<Character>, Column> columns, final int currentIndex, final boolean sqlColumnDividerAdded) {
        if(currentIndex < columns.length()) {
            if(currentIndex != 0) {
                if(!sqlColumnDividerAdded) {
                    return generateColumnsSql(sql.add(new Character(',')).add(new Character(' ')), columns, currentIndex, true);
                }
            }
            return generateColumnsSql(generateDefaultValueSql(generateNotNullSql(sql.mergeStart(columns.get(currentIndex).value().getName()).add(new Character(' ')).mergeStart(columns.get(currentIndex).value().getDataType()), columns.get(currentIndex).value().getNotNull()), columns.get(currentIndex).value().getDefaultValue()), columns, currentIndex + 1, false);
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
        if(currentIndex < columnNames.length()) {
            if(currentIndex != 0) {
                if(!sqlColumnDividerAdded) {
                    return generateColumnNamesSql(sql.add(new Character(',')).add(new Character(' ')), columnNames, currentIndex, true);
                }
            }
            return generateColumnNamesSql(sql.mergeStart(columnNames.get(currentIndex).value()), columnNames, currentIndex + 1, false);
        } else {
            return sql;
        }
    }

    private static final ImmutableLinkedList<Character> generatePrimaryKeySql(final ImmutableLinkedList<Character> sql, PrimaryKey primaryKey) {
        return generateColumnNamesSql(sql.add(new Character(',')).add(new Character(' ')).add(new Character('C')).add(new Character('O')).add(new Character('N')).add(new Character('S')).add(new Character('T')).add(new Character('R'))
                .add(new Character('A')).add(new Character('I')).add(new Character('N')).add(new Character('T')).add(new Character(' '))
                .mergeStart(primaryKey.getName()).add(new Character(' ')).add(new Character('P')).add(new Character('R')).add(new Character('I')).add(new Character('M')).add(new Character('A'))
                .add(new Character('R')).add(new Character('Y')).add(new Character(' ')).add(new Character('K')).add(new Character('E')).add(new Character('Y')).add(new Character(' '))
                .add(new Character('(')), primaryKey.getColumnNames(), 0, false).add(new Character(')'));
    }

    private static final ImmutableLinkedList<Character> generateUniqueSql(final ImmutableLinkedList<Character> sql, Unique unique) {
        return generateColumnNamesSql(sql.add(new Character(',')).add(new Character(' ')).add(new Character('C')).add(new Character('O'))
                .add(new Character('N')).add(new Character('S')).add(new Character('T')).add(new Character('R')).add(new Character('A')).add(new Character('I')).add(new Character('N')).add(new Character('T')).add(new Character(' '))
                .mergeStart(unique.getName()).add(new Character(' ')).add(new Character('U')).add(new Character('N')).add(new Character('I')).add(new Character('Q')).add(new Character('U')).add(new Character('E'))
                .add(new Character(' ')).add(new Character('(')), unique.getColumnNames(), 0, false).add(new Character(')'));
    }

    @SuppressWarnings("unchecked")
    private static final ImmutableLinkedList<Character> generateIndexSql(final ImmutableLinkedList<Character> sql, Index index) {
        return generateColumnNamesSql(sql.add(new Character(',')).add(new Character(' ')).add(new Character('C')).add(new Character('O'))
                .add(new Character('N')).add(new Character('S')).add(new Character('T')).add(new Character('R')).add(new Character('A')).add(new Character('I')).add(new Character('N')).add(new Character('T')).add(new Character(' '))
                .mergeStart(index.getName()).add(new Character(' ')).add(new Character('I')).add(new Character('N')).add(new Character('D')).add(new Character('E')).add(new Character('X'))
                .add(new Character(' ')).add(new Character('(')), index.getColumnNames(), 0, false).add(new Character(')'));
    }

    @SuppressWarnings("unchecked")
    private static final ImmutableLinkedList<Character> generateNotNullSql(final ImmutableLinkedList<Character> sql, NotNull notNull) {
        if(notNull != null) {
            return sql.add(new Character(' ')).add(new Character('N')).add(new Character('O')).add(new Character('T')).add(new Character(' ')).add(new Character('N')).add(new Character('U'))
                    .add(new Character('L')).add(new Character('L'));
        } else {
            return sql;
        }
    }

    @SuppressWarnings("unchecked")
    private static final ImmutableLinkedList<Character> generateDefaultValueSql(final ImmutableLinkedList<Character> sql, DefaultValue defaultValue) {
        if(defaultValue != null) {
            return sql.add(new Character(' ')).add(new Character('D')).add(new Character('E')).add(new Character('F')).add(new Character('A')).add(new Character('U')).add(new Character('L'))
                    .add(new Character('T')).add(new Character(' ')).add(new Character('\'')).mergeStart(defaultValue.getValue()).add(new Character('\''));
        } else {
            return sql;
        }
    }

    @SuppressWarnings("unchecked")
    private static final ImmutableLinkedList<Character> generateForeignKeySql(final ImmutableLinkedList<Character> sql, ForeignKey foreignKey) {
        return generateColumnNamesSql(sql.add(new Character(',')).add(new Character(' ')).add(new Character('C')).add(new Character('O')).add(new Character('N')).add(new Character('S')).add(new Character('T')).add(new Character('R')).add(new Character('A')).add(new Character('I')).add(new Character('N')).add(new Character('T')).add(new Character(' '))
                .mergeStart(foreignKey.getName()).add(new Character(' ')).add(new Character('F')).add(new Character('O')).add(new Character('R')).add(new Character('E')).add(new Character('I')).add(new Character('G')).add(new Character('N')).add(new Character(' '))
                .add(new Character('K')).add(new Character('E')).add(new Character('Y')).add(new Character(' ')).add(new Character('(')), new ImmutableLinkedSet<ImmutableLinkedList<Character>>((Class<ImmutableLinkedList<Character>>)(Class<?>)ImmutableLinkedList.class).add(foreignKey.getFromColumnName()), 0, false)
                .add(new Character(')')).add(new Character(' ')).add(new Character('R')).add(new Character('E')).add(new Character('F')).add(new Character('E')).add(new Character('R')).add(new Character('E')).add(new Character('N')).add(new Character('C')).add(new Character('E'))
                .add(new Character('S')).add(new Character(' ')).mergeStart(foreignKey.getToTableName()).add(new Character('(')).mergeStart(foreignKey.getToColumnName()).add(new Character(')')).add(new Character(' ')).add(new Character('O')).add(new Character('N'))
                .add(new Character(' ')).add(new Character('D')).add(new Character('E')).add(new Character('L')).add(new Character('E')).add(new Character('T')).add(new Character('E')).add(new Character(' ')).add(new Character('C')).add(new Character('A')).add(new Character('S'))
                .add(new Character('C')).add(new Character('A')).add(new Character('D')).add(new Character('E'));
    }

    private static final ImmutableLinkedList<Character> generateCheckSql(final ImmutableLinkedList<Character> sql, Check check) {
        return sql.add(new Character(',')).add(new Character(' ')).add(new Character('C')).add(new Character('O')).add(new Character('N')).add(new Character('S')).add(new Character('T')).add(new Character('R'))
                .add(new Character('A')).add(new Character('I')).add(new Character('N')).add(new Character('T')).add(new Character(' '))
                .mergeStart(check.getName()).add(new Character(' ')).add(new Character('C')).add(new Character('H')).add(new Character('E')).add(new Character('C')).add(new Character('K'))
                .add(new Character(' ')).add(new Character('(')).mergeStart(check.getCondition()).add(new Character(')'));
    }

    public static final void insert (SQLiteDatabase sqLiteDatabase, Object objectToInsert) {

    }
}