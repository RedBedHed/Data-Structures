package util;

/**
 * A {@code Queue} interface
 *
 * @param <E> the type
 * @author Ellie Moore
 * @version 1.0, 03-15-2020
 */
public interface Queue<E> extends EllieCollection {

    /**
     * A method to insert an element into the {@code Queue} from the back.
     *
     * @param e the element to be inserted
     */
    void insert(E e);

    /**
     * A method to delete an element from the front of the {@code Queue}
     * and return the deleted element
     *
     * @return the element at the front of the {@code Queue}
     */
    E delete();

    /**
     * A method to return the element from the front of the {@code Queue}
     * without deleting it.
     *
     * @return the element at the front of the {@code Queue}
     */
    E peek();

}
