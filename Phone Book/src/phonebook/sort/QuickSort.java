package phonebook.sort;

public class QuickSort<T extends Comparable<T>> extends SortingAlgorithm<T> {
    public QuickSort(T[] values, long timeLimit) {
        super("quick sort", values, timeLimit);
    }

    private void quickSort(T[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(array, left, right);
            quickSort(array, left, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, right);
        }
    }

    private int partition(T[] array, int left, int right) {
        T pivot = array[right];
        int partitionIndex = left;
        for (int i = left; i < right; i++) {
            if (array[i].compareTo(pivot) <= 0) {
                swap(array, i, partitionIndex);
                partitionIndex++;
            }
        }
        swap(array, partitionIndex, right);
        return partitionIndex;
    }

    @Override
    protected boolean sort() {
        quickSort(values, 0, values.length - 1);
        return true;
    }
}
