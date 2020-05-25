package util;

/**
 * A {@code List} interface
 *
 * @param <E> the type
 * @author Ellie Moore
 * @version 1.0, 03-15-2020
 */
public interface List<E> extends EllieCollection {

    /**
     * A method to remove and return the {@code List} element at the
     * specified index.
     *
     * @param i the index of the desired {@code List} element.
     * @return the {@code List} element at the given index.
     * @throws IndexOutOfBoundsException an forces the client to enter a valid index.
     */
    E get(int i);

    /**
     * A method to remove and return the {@code List} element at the
     * specified {@code index}.
     *
     * @param i the index of the desired {@code List} element.
     * @return the {@code List} element at the given index.
     * @throws IndexOutOfBoundsException an forces the client to enter a valid index.
     */
    E remove(int i);

    /**
     * A method that removes the first occurrence of a given element from
     * the {@code List}. If the index isn't in range, an {@code Exception} is thrown.
     *
     * @param e the element to be removed
     * @return
     */
    boolean remove(E e);

    /**
     * A method to replace the first occurrence of an element specified in the
     * first argument with the element given in the second argument.
     *
     * @param e the element to be replaced
     * @param ex the element to replace with
     * @return whether or not the element was replaced successfully
     */
    boolean replace(E e, E ex);

    /**
     * A method to set the specified index to the specified {@code List} element
     * and return the previous occupant.
     *
     * @param i the desired index.
     * @return the occupant of the given index.
     * @throws IndexOutOfBoundsException forces the client to enter a valid index.
     */
    E set(int i, E e);

    /**
     * A method to add an element at a given index without replacing or removing
     * the occupant of that index. Instead, all proceeding indexes should be shifted
     * down.
     *
     * @param i the desired index.
     * @param e the {@code List} element to insert at the given index.
     * @throws IndexOutOfBoundsException forces the client to enter a valid index.
     */
    void add(int i, E e);

    /**
     * A method to append an element to the {@code List}.
     *
     * @param e the {@code List} element to be appended
     * @return the instance
     */
    List<E> add(E e);

    /**
     * A method to obtain the first index of a {@code List} element.
     *
     * @param e the {@code List} element to look for.
     * @return the index of the first {@code List} element to match the input.
     * @throws IndexOutOfBoundsException forces the client to enter a valid index
     */
    int indexOf(E e);

    /**
     * A method to convert the {@code List} to an {@code Array}. This method
     * completely empties the {@code List}s data. This method strictly returns
     * an {@code Object Array}. Casts that are unchecked will result in a
     * {@link ClassCastException}.
     *
     * @return an object {@code Array} containing the elements of the {@code List}
     */
    Object[] toArray();

}
