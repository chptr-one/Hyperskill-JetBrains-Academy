package sorting.IO;

public enum DataType {
    LONG("long"),
    WORD("word"),
    LINE("line");

    String value;

    DataType(String value) {
        this.value = value;
    }

    static DataType fromString(String s) {
        for (DataType dataType : DataType.values()) {
            if (dataType.value.equals(s))
                return dataType;
        }
        return null;
    }
}