package util;

/**
 * A {@code Stack} interface.
 *
 * @param <E> the type
 * @author Ellie Moore
 * @version 1.0, 03-15-2020
 */
public interface Stack<E> extends EllieCollection {

    /**
     * A method to push an element onto the top of the {@code Stack}.
     *
     * @param e the element to be pushed
     */
    void push(E e);

    /**
     * A method to delete an element off of the top of the {@code Stack} and
     * return that element.
     *
     * @return the element from the top of the {@code Stack}
     */
    E pop();

    /**
     * A method to return the element from the top of the {@code Stack} without
     * deleting it.
     *
     * @return the element from the top of the {@code Stack}
     */
    E peek();

}
