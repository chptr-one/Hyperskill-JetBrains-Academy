package sorting.IO;

public enum ParamType {
    DATA_TYPE("-dataType"),
    SORTING_TYPE("-sortingType");

    String value;

    ParamType(String value) {
        this.value = value;
    }

    static ParamType fromString(String s) {
        for (ParamType paramType : ParamType.values()) {
            if (paramType.value.equals(s))
                return paramType;
        }
        return null;
    }
}