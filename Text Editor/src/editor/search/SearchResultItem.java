package editor.search;

public class SearchResultItem {
    private final int index;
    private final String foundText;

    public SearchResultItem(int index, String foundText) {
        this.index = index;
        this.foundText = foundText;
    }

    public int getIndex() {
        return index;
    }

    public String getFoundText() {
        return foundText;
    }

    @Override
    public String toString() {
        return "SearchResultItem{" +
                "index=" + index +
                ", foundText='" + foundText + '\'' +
                '}';
    }
}
