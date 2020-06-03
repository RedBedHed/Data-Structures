package util;

/**
 * An interface to be implemented for sorting purposes.
 *
 * @param <E> the type
 * @author Ellie Moore
 * @version 1.0, 03-15-2020
 */
public interface EllieComparator<E> {

    /**
     * This method allows for the comparison of two elements utilizing whichever method
     * that the client deems best. All implementations of {@code EllieComparator} must
     * override this method. The default method can be accessed with the following syntax:
     * {@code EllieComparator.super.compare()}.
     *
     * @param e  the first element to be compared
     * @param ex the second element to be compared
     * @return an integer value based on the comparison (1 if >, -1 if <, 0 if ==)
     */
    default int compare(E e, E ex) {
        if(e != null && ex != null) return Integer.compare(e.hashCode(), ex.hashCode());
        return e == null? (ex == null? 0: -1): 1;
    }

}
