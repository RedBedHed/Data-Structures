package util;

public final class InsertionSort {

    /** Prevents instantiation. */
    private InsertionSort() {
    }

    /**
     * This is an insertion sort algorithm.
     *
     * <p>
     * Traditional insertion sort will be used after a leftmost partition or in the
     * case of a small initial array, otherwise pair insertion sort will be used,
     * dividing the time complexity by a constant factor of two for each use.
     *
     * @param array      the Array to be sorted
     * @param left       the leftmost index
     * @param right      the rightmost index
     * @param c          the {@code comparator} to be used
     * @param isLeftmost indicates whether the interval is the leftmost portion.
     * @param <E>        the type
     */
    public static <E> void sort(final E[] array,
                                int left,
                                int right,
                                final EllieComparator<E> c,
                                final boolean isLeftmost) {

        if (isLeftmost) {

            // Traditional insertion sort.
            for (int i = left; i <= right; i++) {
                final E store = array[i];
                int j = i - 1;
                while (j >= left && c.compare(array[j], store) > 0)
                    array[j + 1] = array[j--];
                array[j + 1] = store;
            }

        } else { // Pair insertion sort.

            // Skip elements that are in ascending order.
            do if (left >= right) return;
            while (c.compare(array[++left], array[left - 1]) >= 0);

            // Pair insertion sort. Requires prior partitioning.
            // Uses the sub-array at left as a sentinel.
            for (int i = left; ++left <= right; i = ++left) {
                E ex = array[i];
                E ey = array[left];
                if (c.compare(ex, ey) < 0) {
                    ey = ex;
                    ex = array[left];
                }
                while (c.compare(ex, array[--i]) < 0)
                    array[i + 2] = array[i];
                array[++i + 1] = ex;
                while (c.compare(ey, array[--i]) < 0)
                    array[i + 1] = array[i];
                array[i + 1] = ey;
            }
            E ez = array[right];
            while (c.compare(ez, array[--right]) < 0)
                array[right + 1] = array[right];
            array[right + 1] = ez;

        }

    }

}
