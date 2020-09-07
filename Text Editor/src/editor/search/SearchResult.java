package editor.search;

import java.util.List;

public class SearchResult {
    private final List<SearchResultItem> foundItems;
    private int currentItemIndex;

    public SearchResult(List<SearchResultItem> foundItems) {
        this.foundItems = foundItems;
        currentItemIndex = -1;
    }

    public SearchResultItem getNext() {
        currentItemIndex++;
        if (currentItemIndex == foundItems.size()) {
            currentItemIndex = 0;
        }
        return foundItems.get(currentItemIndex);
    }

    public SearchResultItem getPrev() {
        currentItemIndex--;
        if (currentItemIndex < 0) {
            currentItemIndex = foundItems.size() - 1;
        }
        return foundItems.get(currentItemIndex);
    }

    public boolean hasResult() {
        return foundItems.size() > 0;
    }
}
