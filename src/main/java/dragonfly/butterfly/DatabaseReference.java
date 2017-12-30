package dragonfly.butterfly;


public class DatabaseReference {
    private final ImmutableLinkedList<Character> tableName;
    private final ImmutableLinkedList<Character> columnName;

    public DatabaseReference(ImmutableLinkedList<Character> tableName, ImmutableLinkedList<Character> columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
    }
}
