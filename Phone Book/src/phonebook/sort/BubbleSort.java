package phonebook.sort;

public class BubbleSort<T extends Comparable<T>> extends SortingAlgorithm<T> {

    public BubbleSort(T[] values, long timeLimit) {
        super("bubble sort", values, timeLimit);
    }

    @Override
    protected boolean sort() {
        long startTime = System.currentTimeMillis();
        long sortingTime;
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length - i - 1; j++) {
                if (values[j].compareTo(values[j + 1]) > 0) {
                    if (j % 10000 == 0) {
                        sortingTime = System.currentTimeMillis() - startTime;
                        if (sortingTime > timeLimit) {
                            return false;
                        }
                    }
                    swap(values, j, j + 1);
                }
            }
        }
        return true;
    }
}

