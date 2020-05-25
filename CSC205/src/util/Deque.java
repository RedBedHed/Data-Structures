package util;

/**
 * A {@code Deque} interface that inherits from the {@code Queue}
 * interface.
 *
 * @param <E> the type
 * @author Ellie Moore
 * @version 1.0, 03-15-2020
 */
public interface Deque<E> extends Queue<E> {

    /**
     * A method that allows the client to delete an element from the back
     * of the {@code Deque}.
     *
     * @return the element at the back of the Deque
     */
    E deleteFromBack();

    /**
     * A method that allows the client to insert an element onto the
     * front of the Deque.
     *
     * @param e the element to be inserted
     */
    void insertOnFront(E e);

    /**
     * A method that gives the client access to the reference of the
     * element at the back of the {@code Deque} without deleting it.
     *
     * @return the element at the back of the {@code Deque}
     */
    E peekFromBack();

}
