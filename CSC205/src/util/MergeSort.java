package util;

public final class MergeSort {

    /** Prevents instantiation. */
    private MergeSort(){
    }

    /**
     * This is a Merge Sort algorithm.
     *
     * <p>
     * Using a recursively-constructed, binary tree, this algorithm breaks down the
     * {@code Array} into runs, merging them into order on the return.
     *
     * @param array the {@code Array} to be sorted
     * @param left  the leftmost index (Starting at 0)
     * @param right the rightmost index (Starting at length - 1)
     * @param c     the comparator to be used
     * @param <E>   the element type
     */
    @SuppressWarnings("Unchecked")
    protected static <E> void sort(final E[] array,
                                   int left,
                                   int right,
                                   final EllieComparator<E> c) {

        if (left < right) {

            // If the interval is small enough, use Quick Sort.
            if ((right - left) <= EllieCollections.QUICK_THRESHOLD) {
                DualPivotQuickSort.sort(array, left, right, c, true);
                return;
            }

            // Find the approximate midpoint of the interval.
            final int mid = (right + left) >>> 1;

            // Sort left and right portions.
            sort(array, left, mid, c);
            sort(array, mid + 1, right, c);

            // If the mid is less than or equal to the next element over,
            // there is no reason to merge.
            if (c.compare(array[mid], array[mid + 1]) > 0) {

                // If the element at left is less than the element at mid + 1,
                // or if the element at mid is less than the element at right,
                // use binary search to narrow the interval before merging.
                if (c.compare(array[left], array[mid + 1]) < 0)
                    left = binarySearch(array, left, mid, mid + 1, c);
                if (c.compare(array[mid], array[right]) < 0)
                    right = binarySearch(array, mid + 1, right, mid, c);

                // Initialize temporary storage.
                E[] lt = (E[]) new Object[mid + 1 - left];
                E[] rt = (E[]) new Object[right - mid];

                // Fill left and right temporary arrays.
                for (int i = 0; i < lt.length; i++)
                    lt[i] = array[i + left];
                for (int j = 0; j < rt.length; j++)
                    rt[j] = array[j + mid + 1];

                // merge.
                int i = 0, j = 0, k = left;
                while (i < lt.length && j < rt.length) {
                    if (c.compare(lt[i], rt[j]) < 0)
                        array[k] = lt[i++];
                    else array[k] = rt[j++];
                    k++;
                }

                // add any remaining elements to the end of the
                // interval.
                while (i < lt.length) array[k++] = lt[i++];
                while (j < rt.length) array[k++] = rt[j++];

            }

        }

    }

    /**
     * This is a binary search designed to find optimal starting and ending
     * positions for the merge operation.
     *
     * @param array the array to be merged
     * @param beg   the beginning of the interval to be searched
     * @param end   the ending of the interval to be searched
     * @param can   the candidate element to find a position for
     * @param c     the comparator
     * @param <E>   the type
     * @return a position at which the candidate element belongs
     */
    private static <E> int binarySearch(final E[] array,
                                        int beg,
                                        int end,
                                        final int can,
                                        final EllieComparator<E> c) {

        while ((end - beg) > 1) {
            int mid = (beg + end) >>> 1;
            final int cmp = c.compare(array[can], array[mid]);
            if (cmp < 0) end = mid;
            else if (cmp > 0) beg = mid;
            else {
                while (
                        mid < end &&
                        c.compare(array[mid], array[can]) == 0
                ) mid++;
                return mid;
            }
        }
        while (c.compare(array[end], array[can]) <= 0) end++;
        return end;

    }

}
