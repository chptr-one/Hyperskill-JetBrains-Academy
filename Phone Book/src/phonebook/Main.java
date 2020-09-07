package phonebook;

import phonebook.sort.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.function.ToIntBiFunction;

public class Main {
    private static String[] directory;
    private static String[] targets;

    static {
        try {
            directory = Files.lines(Path.of("/home/peter/directory.txt"))
                    .map(s -> s.substring(s.indexOf(" ") + 1))
                    .toArray(String[]::new);
            targets = Files.lines(Path.of("/home/peter/find.txt"))
                    .toArray(String[]::new);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static String formatTime(Duration d) {
        return String.format("%d min. %d sec. %d ms.",
                d.toMinutesPart(),
                d.toSecondsPart(),
                d.toMillisPart());
    }

    private static void testSort(ToIntBiFunction<String[], String> searcher,
                                 String searcherName,
                                 SortingAlgorithm<String> sorter)
    {
        System.out.printf("Start searching (%s + %s)...\n", sorter.getName(), searcherName);

        sorter.performSort();
        Duration sortingDuration = sorter.getDuration();

        String[] values = directory;
        String sortingStatusFormat = "Sorting time: %s\n";
        if (sorter.isSortCompleted()) {
            values = sorter.getValues();
        } else {
            searcher = SearchUtils::linearSearch;
            sortingStatusFormat = "Sorting time: %s - STOPPED, moved to linear search\n";
        }

        long start = System.currentTimeMillis();
        ToIntBiFunction<String[], String> finalSearcher = searcher;
        String[] finalValues = values;
        long found = Arrays.stream(targets)
                .mapToInt(target -> finalSearcher.applyAsInt(finalValues, target))
                .filter(i -> i >= 0)
                .count();
        Duration searchingDuration = Duration.ofMillis(System.currentTimeMillis() - start);

        System.out.printf("Found %d/%d entries. Time taken: %s\n",
                found,
                targets.length,
                formatTime(sortingDuration.plus(searchingDuration)));
        System.out.printf(sortingStatusFormat, formatTime(sortingDuration));
        System.out.printf("Searching time: %s\n", formatTime(searchingDuration));
    }

    public static void main(String[] args) {
        System.out.println("Start searching (linear search)...");
        long start = System.currentTimeMillis();
        long found = Arrays.stream(targets)
                .mapToInt(target -> SearchUtils.linearSearch(directory, target))
                .filter(i -> i >= 0)
                .count();
        Duration linearSearchDuration = Duration.ofMillis(System.currentTimeMillis() - start);
        System.out.printf("Found %d/%d entries. Time taken: %s\n",
                found,
                targets.length,
                formatTime(linearSearchDuration));

        System.out.println();
        testSort(SearchUtils::jumpSearch, "jump search",
                new BubbleSort<>(directory, linearSearchDuration.toMillis()));

        System.out.println();
        testSort(Arrays::binarySearch, "binary search",
                new QuickSort<>(directory, linearSearchDuration.toMillis()));

    }
}
