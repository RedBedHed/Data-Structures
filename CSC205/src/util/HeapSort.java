package util;

import java.util.Arrays;
import java.util.Random;

public final class HeapSort {

    private HeapSort(){
    }

    public static <E> void sort(final E[] array){
        sort(array, new EllieComparator<>(){});
    }

    public static <E> void sort(final E[] array,
                                final EllieComparator<E> c) {
        if(array.length <= 1) return;
        for(int i = (array.length - 1) >>> 1; i >= 0; i--)
            siftDown(array, i, array.length, c);
        siftUp(array, array.length - 1, c);
        for(int i = array.length - 1; i > 1; i--){
            swap(array, 0, i);
            siftDown(array, 0, i, c);
        }
        if(c.compare(array[0], array[1]) > 0)
            swap(array, 0, 1);
    }

    /*
     * A method to fix the Heap tree starting at the given leaf
     * of the sub-tree to be fixed and working upwards.
     */
    private static <E> void siftUp(final E[] heap,
                                   int x,
                                   final EllieComparator<E> c) {
        if(x < 1) return;
        int parent;
        while (x > 0 &&
               c.compare(heap[parent = (x - 1) >>> 1], heap[x]) < 0) {
            swap(heap, parent, x);
            x = parent;
        }
    }

    /*
     * A method to fix the Heap tree, starting at the given root
     * of the sub-tree to be fixed and working downwards.
     */
    private static <E> void siftDown(E[] heap,
                                     int x,
                                     int n,
                                     EllieComparator<E> c) {
        int left;
        int right;
        while ((left = (x << 1) + 1) < n &&
               (right = left + 1) < n &&
               (c.compare(heap[x], heap[left]) < 0 ||
                c.compare(heap[x], heap[right]) < 0)){
            if (c.compare(heap[left], heap[right]) < 0) {
                swap(heap, x, right);
                x = right;
            } else {
                swap(heap, x, left);
                x = left;
            }
        }
    }

    /*
     * A method to swap the elements at the specified indices in the
     * heap.
     */
    private static <E> void swap(E[] heap, int i, int ix) {
        E e = heap[i];
        heap[i] = heap[ix];
        heap[ix] = e;
    }

}
