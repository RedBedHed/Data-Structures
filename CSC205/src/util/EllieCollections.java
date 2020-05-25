package util;

import java.util.Random;

/**
 * Ellie Collections
 *
 * <p>
 * This is a class containing static tools to be used on {@code EllieCollection}s.
 *
 * <p>
 * Sorting is accomplished through a combination of Insertion, Partition, and Merge
 * algorithms depending on the size of the data at any given point. For small arrays,
 * Insertion sort is used. This algorithm is enhanced with pair optimization in the
 * context of non-leftmost partitioning. For medium-sized arrays, Partition sorting
 * is used. This algorithm is optimized to skip over equal or in-order elements at
 * each level of recursion. Finally, for large arrays, Merge sort is used. This
 * algorithm relies on Partitioning to sort each "run" as quickly as possible. Merge
 * sorting also makes use of a binary search algorithm in order to find the optimal
 * indices at which to merge the given runs.
 *
 * <p>
 * The provided sort is NOT a stable sort. That is, it is not guaranteed to arrange
 * equal elements in the same order each time.
 *
 * <p>
 * The static factory method {@code unmodifiableList(List)} wraps a {@code List} in an
 * immutable package. In other words, the resulting {@code List} will be read-only.
 * An {@link UnsupportedOperationException} will be thrown in the case of attempted
 * mutation.
 *
 * @author Ellie Moore
 * @version 1.0, 03.14.2020
 */
public final class EllieCollections {

    /**
     * A constant which represents the size at which quick sort will
     * be used instead of insertion sort.
     */
    public static final int INSERTION_THRESHOLD = 32;

    /**
     * A heuristic maximum threshold at which the Quick Sort may become
     * less-efficient than the Merge Sort. Represents the size at which
     * merge sort will be used instead of quick sort.
     */
    public static final int QUICK_THRESHOLD = 300;

    /**
     * A constant for use in {@link EllieCollection#hashCode()}
     * implementations.
     */
    public static final int HASH_CODE_CONST = 31;

    /**
     * A private constructor to make the class un-instantiable.
     */
    private EllieCollections() {
    }

    /**
     * A sorting method for {@code List} implementations using
     * the default {@code EllieComparator}.
     *
     * @param list the {@code List} to be sorted
     * @param <E>  the type of the elements belonging to the {@code List}
     * @see EllieComparator#compare(E, E)
     */
    public static <E> void sort(final List<E> list) {
        sort(list,  new EllieComparator<>(){});
    }

    /**
     * This is a {@code List} sorting method that relies on an {@code EllieComparator}
     * implementation. {@code List}s are dumped to an Array, sorted, and re-built
     * sequentially.
     *
     * @param list the {@code List} to be sorted
     * @param c the {@code Comparator} to be used
     * @param <E>  the type of the elements belonging to the {@code List}
     */
    @SuppressWarnings("Unchecked")
    public static <E> void sort(final List<E> list, final EllieComparator<E> c) {
        E[] t = (E[]) list.toArray();
        if (t.length < INSERTION_THRESHOLD)
            InsertionSort.sort(t, 0, t.length - 1, c, true);
        else if (t.length < QUICK_THRESHOLD)
            DualPivotQuickSort.sort(t, 0, t.length - 1, c, true);
        else
            MergeSort.sort(t, 0, t.length - 1, c);
        for (E e : t) list.add(e);
    }

    /**
     * This is a static factory method to wrap a {@code List} in an
     * {@code UnmodifiableList}.
     *
     * @param list the {@code List} to be wrapped
     * @param <T>  the type
     * @return an {@code UnmodifiableList}
     */
    public static <T> UnmodifiableList<T> unmodifiableList(final List<T> list) {
        return new UnmodifiableList<>(list);
    }

    private static final class UnmodifiableList<E> implements List<E> {

        final List<E> internal;

        public UnmodifiableList(final List<E> list) {
            internal = list;
        }

        @Override
        public E get(int i) {
            return internal.get(i);
        }

        @Override
        public E remove(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public E set(int i, E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int i, E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<E> add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(E e, E ex) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(E e) {
            return internal.indexOf(e);
        }

        @Override
        public int size() {
            return internal.size();
        }

        @Override
        public boolean isEmpty() {
            return internal.isEmpty();
        }

        @Override
        public String toString() {
            return internal.toString();
        }

        @Override
        public String toStore() {
            return internal.toStore();
        }

        @Override
        public int hashCode() {
            return internal.hashCode();
        }

        @Override
        public boolean equals(final Object o) {
            return internal.equals(o);
        }

    }

}

class test {

    public static void main(String[] args) {

        Random rgen = new Random();
        System.out.println("____________________________________________________________________________________________________");
        final long start = System.currentTimeMillis();
        for (int k = 0; k < 100000; k++) {
            if (k % 1000 == 0) System.out.print("#");
            ArrayList<Integer> l = new ArrayList<>();
            for (int i = 1000; i > 0; i--)
                l.add(rgen.nextInt(100));
            //System.out.println(l);
            //Object[] arr = l.toArray();
            EllieCollections.sort(l);
            test(l.toArray(), k);
        }
        final long end = System.currentTimeMillis();

        System.out.println("\n:) " + (end - start));

    }

    public static <E> void test(E[] arr, int num) {

        EllieComparator<E> comp = new EllieComparator<>() {
            @Override
            public int compare(E e, E ex) {
                return EllieComparator.super.compare(e, ex);
            }
        };
        for (int i = 0; i < arr.length - 1; i++) {
            if (comp.compare(arr[i], arr[i + 1]) > 0) {
                //System.out.println(Arrays.toString(arr));
                System.out.println(num);
                System.out.println(":(");
                break;
            }
        }
        //System.out.println(Arrays.toString(arr));

    }
}
