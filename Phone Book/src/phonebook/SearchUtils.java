package phonebook;

public class SearchUtils {
    public static <T extends Comparable<T>> int linearSearch(T[] values, T target) {
        int index = -1;
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(target)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static <T extends Comparable<T>> int jumpSearch(T[] values, T target) {
        int currentRight = 0;
        int previousRight = 0;
        if (values.length == 0) {
            return -1;
        }
        if (values[currentRight].equals(target)) {
            return 0;
        }
        int arrLength = values.length;
        int jumpSize = (int) Math.sqrt(arrLength);
        while (currentRight < arrLength - 1) {
            currentRight = Math.min(arrLength - 1, currentRight + jumpSize);
            if (values[currentRight].compareTo(target) >= 0) {
                break;
            }
            previousRight = currentRight;
        }
        if ((currentRight == arrLength - 1) && target.compareTo(values[currentRight]) > 0) {
            return -1;
        }
        return backwardSearch(values, target, previousRight, currentRight);
    }

    private static <T extends Comparable<T>> int backwardSearch(T[] arr, T target, int leftExcl, int rightIncl) {
        for (int i = rightIncl; i > leftExcl; i--) {
            if (target.compareTo(arr[i]) == 0) {
                return i;
            }
        }
        return -1;
    }
}
