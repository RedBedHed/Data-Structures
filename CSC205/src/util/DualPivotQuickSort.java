package util;

public final class DualPivotQuickSort {

    /**
     * Prevents instantiation.
     */
    private DualPivotQuickSort() {
    }

    /**
     * This is a Quick Sort algorithm. (aka Partition Sort)
     *
     * <p>
     * Elements are partitioned around "pivots" at each level of recursion.
     * Upon return, the {@code Array} will be in sorted order.
     *
     * @param array      the {@code Array} to be sorted
     * @param left       the leftmost index (Starting at 0)
     * @param right      the rightmost index (Starting at length - 1)
     * @param c          the comparator to be used
     * @param isLeftmost indicates whether the interval is the leftmost portion.
     * @param <E>        the element type
     */
    protected static <E> void sort(final E[] array,
                                   final int left,
                                   final int right,
                                   final EllieComparator<E> c,
                                   final boolean isLeftmost) {

        if (left < right) {

            final int interval = right - left;

            // Insertion sort small intervals.
            if (interval < EllieCollections.INSERTION_THRESHOLD) {
                InsertionSort.sort(array, left, right, c, isLeftmost);
                return;
            }

            // Find an inexpensive approximation of a third of
            // the interval.
            final int third = (interval >> 2) + (interval >> 3);

            // Find an approximate midpoint of the interval.
            final int mid = (left + right) >>> 1;

            // Assign tercile indices to candidate pivots.
            final int sl = left + third;
            final int sr = right - third;

            // Insertion sort all five candidate pivots in-place.
            if (c.compare(array[sl], array[left]) < 0) {
                E e = array[sl];
                array[sl] = array[left];
                array[left] = e;
            }

            if (c.compare(array[mid], array[sl]) < 0) {
                E e = array[mid];
                array[mid] = array[sl];
                array[sl] = e;
                if (c.compare(e, array[left]) < 0) {
                    array[sl] = array[left];
                    array[left] = e;
                }
            }

            if (c.compare(array[sr], array[mid]) < 0) {
                E e = array[sr];
                array[sr] = array[mid];
                array[mid] = e;
                if (c.compare(e, array[sl]) < 0) {
                    array[mid] = array[sl];
                    array[sl] = e;
                    if (c.compare(e, array[left]) < 0) {
                        array[sl] = array[left];
                        array[left] = e;
                    }
                }
            }

            if (c.compare(array[right], array[sr]) < 0) {
                E e = array[right];
                array[right] = array[sr];
                array[sr] = e;
                if (c.compare(e, array[mid]) < 0) {
                    array[sr] = array[mid];
                    array[mid] = e;
                    if (c.compare(e, array[sl]) < 0) {
                        array[mid] = array[sl];
                        array[sl] = e;
                        if (c.compare(e, array[left]) < 0) {
                            array[sl] = array[left];
                            array[left] = e;
                        }
                    }
                }
            }

            // If none of the sorted candidate pivots are equal,
            // use Dual-Pivot Quick Sort.
            if (c.compare(array[left], array[sl]) != 0 &&
                    c.compare(array[sl], array[mid]) != 0 &&
                    c.compare(array[mid], array[sr]) != 0 &&
                    c.compare(array[sr], array[right]) != 0) {

                // Assign the elements at the first and second terciles
                // to the pivot variables.
                final E leftPivot = array[sl];
                final E rightPivot = array[sr];

                // Bring the first and last elements inside.
                // These spots will be filled and Pivots will
                // be swapped into place later.
                array[sl] = array[left];
                array[sr] = array[right];

                // Initialize iterator variables for use in partitioning.
                int l = left + 1, g = right - 1;

                // Skip elements that are already in order.
                while (l < right && c.compare(array[l], leftPivot) < 0) l++;
                while (g > left && c.compare(array[g], rightPivot) > 0) g--;

                // partition.
                for (int i = l; i <= g; i++) {
                    if (c.compare(array[i], leftPivot) < 0)
                        swap(array, i, l++);
                    else if (c.compare(array[i], rightPivot) > 0) {
                        while (c.compare(array[g], rightPivot) > 0 && i < g)
                            g--;
                        swap(array, i, g--);
                        if (c.compare(array[i], leftPivot) < 0)
                            swap(array, i, l++);
                    }
                }

                // Fill ends. Swap the pivots back into place.
                array[left] = array[--l];
                array[l] = leftPivot;
                array[right] = array[++g];
                array[g] = rightPivot;

                // Copy pivot indices.
                int p = l;
                int q = g;

                // Skip equal elements.
                while (p > left && c.compare(array[p], leftPivot) == 0) p--;
                while (q < right && c.compare(array[q], rightPivot) == 0) q++;

                // Sort right and left portions.
                sort(array, left, p, c, isLeftmost);
                sort(array, q, right, c, false);

                // If left pivot and right pivot are equal,
                // there is no need to sort the middle.
                if (c.compare(leftPivot, rightPivot) < 0) {

                    // Skip over elements that are equal.
                    while (l < g && c.compare(array[l], leftPivot) == 0) l++;
                    while (g > l && c.compare(array[g], rightPivot) == 0) g--;

                    // If middle portion is too large (>= 2/3 of the interval),
                    // get duplicates out of the way.
                    if ((g - l) >= (interval - third)) {
                        for (int k = l; k <= g; k++) {
                            if (c.compare(array[k], leftPivot) == 0) {
                                swap(array, k, l++);
                            } else if (c.compare(array[k], rightPivot) == 0) {
                                swap(array, k, g--);
                                if (c.compare(array[k], leftPivot) == 0) {
                                    swap(array, k, l++);
                                }
                            }
                        }
                    }

                    //Sort middle portion.
                    sort(array, l, g, c, false);

                }

            } else { // Use traditional, single-pivot Quick Sort.

                // Assign midpoint to pivot variable.
                final E pivot = array[mid];

                // Bring left end inside. Left end will be
                // replaced and pivot will be swapped back later.
                array[mid] = array[left];

                // initialize iterator variable for use in partitioning.
                int l = left + 1;
                int g = right - 1;

                // Skip over elements that are already in order.
                while (l < right && c.compare(array[l], pivot) < 0) l++;
                while (g > left && c.compare(array[g], pivot) > 0) g--;

                // Partition.
                for (int i = l; i <= g; i++) {
                    if (c.compare(array[i], pivot) < 0) {
                        swap(array, i, l++);
                    }
                }

                // Replace left end and swap pivot into place.
                array[left] = array[--l];
                array[l] = pivot;

                // Copy l, recycle g. "lesser" and "greater" respectively.
                g = l;

                // Skip over elements that are equal to the pivot.
                while (l > left && c.compare(array[l], pivot) == 0) l--;
                while (g < right && c.compare(array[g], pivot) == 0) g++;

                // Sort left and right portions.
                sort(array, left, l, c, isLeftmost);
                sort(array, g, right, c, false);

            }

        }

    }

    /*
     * A method to swap two indices of the given {@code Array}.
     */
    private static <E> void swap(final E[] a, final int i, final int j) {

        final E temp = a[i];
        a[i] = a[j];
        a[j] = temp;

    }

}
