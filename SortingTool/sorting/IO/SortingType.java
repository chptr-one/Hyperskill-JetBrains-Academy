package sorting.IO;

public enum SortingType {
    NATURAL("natural"),
    BY_COUNT("byCount");

    String value;

    SortingType(String value) {
        this.value = value;
    }

    static SortingType fromString(String s) {
        for (SortingType sortingType : SortingType.values()) {
            if (sortingType.value.equals(s))
                return sortingType;
        }
        return null;
    }
}