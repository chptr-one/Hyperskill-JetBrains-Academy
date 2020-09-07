package phonebook.sort;

import java.time.Duration;
import java.util.Arrays;

abstract public class SortingAlgorithm<T extends Comparable<T>> {
    public final String name;
    protected final long timeLimit;
    protected final T[] values;
    protected boolean sorted;
    protected long sortingTime;

    protected SortingAlgorithm(String name, T[] values, long timeLimit) {
        this.name = name;
        this.values = Arrays.copyOf(values, values.length);
        this.timeLimit = timeLimit;
        this.sorted = false;
    }

    public void performSort() {
        long startTime = System.currentTimeMillis();
        sorted = sort();
        sortingTime = System.currentTimeMillis() - startTime;
    }

    abstract protected boolean sort();

    public boolean isSortCompleted() {
        return sorted;
    }

    public String getName() {
        return name;
    }

    public T[] getValues() {
        return values;
    }

    public Duration getDuration() {
        return Duration.ofMillis(sortingTime);
    }

    protected void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
