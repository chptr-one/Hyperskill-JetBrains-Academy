package sorting.dataTypes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ItemType<T extends Comparable<T>> {

    protected List<T> items;
    protected int totalItems;

    public ItemType() {
        items = readData();
        totalItems = items.size();
    }

    protected List<T> sortNatural() {
        return items.stream().sorted().collect(Collectors.toList());
    }

    protected List<Map.Entry<T, Long>> sortByCount() {
        Map<T, Long> itemsToCount = items.stream()
                .collect(Collectors.groupingBy(l -> l, Collectors.counting()));

        return itemsToCount.entrySet().stream()
                .sorted(Map.Entry.<T, Long>comparingByValue()
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(Collectors.toList());
    }

    public abstract List<T> readData();

    abstract void printHeader();

    public void printNatural() {
        printHeader();
        System.out.print("Sorted data: ");
    }

    public void printByCount() {
        printHeader();
        sortByCount().forEach(e -> System.out.printf("%s: %d time(s), %.0f%%\n",
                e.getKey(),
                e.getValue(),
                (1.0 * e.getValue() / totalItems) * 100)
        );
    }
}
