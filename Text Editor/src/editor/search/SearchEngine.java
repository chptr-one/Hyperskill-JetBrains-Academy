package editor.search;

import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

public class SearchEngine {
    private final String text;
    private final String toFind;
    private final boolean searchMode;

    public SearchEngine(String text, String toFind, boolean searchMode) {
        this.text = text;
        this.toFind = toFind;
        this.searchMode = searchMode;
    }

    public SearchResult search() {
        SearchResult result;
        if (searchMode) {
            // find using regex
            Pattern pattern = Pattern.compile(toFind);
            Scanner scanner = new Scanner(text);
            result = new SearchResult(scanner.findAll(pattern)
                    .map(matchResult -> new SearchResultItem(
                            matchResult.start(), matchResult.group()))
                    .collect(Collectors.toList()));
        } else {
            // find plane text
            List<SearchResultItem> foundItems = new ArrayList<>();
            int index = text.indexOf(toFind);
            while (index >= 0) {
                foundItems.add(new SearchResultItem(index, toFind));
                index = text.indexOf(toFind, index + 1);
            }
            result = new SearchResult(foundItems);
        }
        return result;
    }
}
