package util;

/**
 * Singly Linked List
 *
 * <p>
 * This is a {@code LinkedList} implemented with one-way linking!
 *
 * <p>
 * A {@code LinkedList} is an efficient way to store large, complex
 * Objects, as a {@code LinkedList} doesn't need to copy its elements
 * during growth. However, a {@code LinkedList} is likely to be less
 * efficient than an {@code ArrayList} when used for storing small
 * Objects or boxed primitives as random access is impossible.
 *
 * @author Ellie Moore
 * @version 04.08.2020
 * @param <E> the type
 */
public class SinglyLinkedList<E> implements List<E> {

    /**
     * The first {@code Link} in the list.
     */
    private Link<E> head;

    /**
     * The size of the {@code List}.
     */
    private int size;

    /**
     * Constructs an empty LinkedList.
     * ({@code root} is null and {@code size} is zero by default)
     */
    public SinglyLinkedList(){
    }

    /**
     * Constructs a LinkedList from an element array.
     *
     * @param array the array to be converted.
     */
    public SinglyLinkedList(final E[] array){
        for(final E e: array) add(e);
    }

    /*
     * Navigator
     *
     * A struct to store the current and previous Link during
     * traversal of the LinkedList.
     */
    private static final class Navigator<E> {

        public Link<E> prev;
        public Link<E> curr;

        public Navigator(Link<E> prev, Link<E> curr){
            this.prev = prev;
            this.curr = curr;
        }

    }

    /*
     * A method to navigate to the specified index and return
     * two Links-- One AT the index, and one in front of
     * (or behind from the client's perspective).
     */
    private Navigator<E> navigateTo(final int index){
        final Navigator<E> n = new Navigator<>(head, head.next);
        for(
                int i = 1;
                n.curr != null
                && i < (size - index);
                n.prev = n.curr,
                n.curr = n.curr.next,
                i++
        );
        return n;
    }

    /*
     * A method to navigate to the given index and return the Link
     * AT that index.
     */
    private Link<E> navigateToLinkAt(final int index){
        Link<E> c = head;
        for (
                int i = 1;
                c != null
                && i < (size - index + 1);
                c = c.next, i++
        );
        return c;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * This version of {@code List#add(E)} iterates backwards over
     * each {@code Link} until arriving at the desired index.
     * Here, it wraps the given element in a {@code Link} and
     * adds it to the {@code List}. If the desired index is size + 1
     * (The front of the list), then the insertion will replace the
     * head {@code Link}.
     *
     * @throws IndexOutOfBoundsException
     */
    @Override
    public void add(final int index, final E element) {
        if (index < 1 || index > size + 1){
            throw new IndexOutOfBoundsException(
                    "The given index is out of bounds."
            );
        }
        final Link<E> insertion = new Link<>();
        insertion.store = element;
        if(index == size + 1) {
            insertion.next = head;
            head = insertion;
        } else {
            final Navigator<E> nav = navigateTo(index - 1);
            nav.prev.next = insertion;
            insertion.next = nav.curr;
        }
        size++;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<E> add(final E element) {
        add(size + 1, element);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * This version of {@code List#set(int, E)} iterates over
     * the data until reaching the desired index. Here, it swaps
     * out the current {@code Link}'s storage for the new element,
     * returning the previous occupant.
     *
     * @throws IndexOutOfBoundsException
     */
    @Override
    public E set(final int index, final E element) {
        if (index < 1 || index > size){
            throw new IndexOutOfBoundsException(
                    "The given index is out of bounds."
            );
        }
        final Link<E> curr = navigateToLinkAt(index);
        final E removal = curr.store;
        curr.store = element;
        return removal;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * This version of {@code List#get(int)} iterates over the data
     * until reaching the desired index. Here, it simply returns the
     * element.
     *
     * @throws IndexOutOfBoundsException
     */
    @Override
    public E get(final int index){
        if (index < 1 || index > size){
            throw new IndexOutOfBoundsException(
                    "The given index is out of bounds."
            );
        }
        return navigateToLinkAt(index).store;
    }

    /**
     * A method to remove the first occurrence of an existing element
     * from the list. If the element does not exist, this method will
     * return false, otherwise it will return true. This method accepts
     * a null parameter safely.
     *
     * @param element the desired element
     * @return whether or not the removal was successful
     */
    public boolean remove(final E element) {
        if (head == null) return false;
        if (element == null) {
            if (head.store == null) {
                unlinkHead();
                return true;
            }
            for (
                    Link<E> p = head, c = head.next;
                    c != null;
                    p = c, c = c.next
            ) {
                if(c.store == null) {
                    p.next = c.next;
                    size--;
                    return true;
                }
            }
        } else {
            if (head.store != null
                    && head.store.equals(element)) {
                unlinkHead();
                return true;
            }
            for (
                    Link<E> p = head, c = head.next;
                    c != null;
                    p = c, c = c.next
            ) {
                if (c.store != null
                        && c.store.equals(element)) {
                    p.next = c.next;
                    size--;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A method to remove all occurrences of an existing element
     * from the list. If the element does not exist, this method will
     * return false, otherwise it will return true. This method accepts
     * a null parameter safely.
     *
     * @param element the desired element
     * @return whether or not the removal was successful
     */
    public boolean removeAll(final E element) {
        if (head == null) return false;
        boolean isRemoved = false;
        if (element == null) {
            if (head.store == null) {
                unlinkHead();
                isRemoved = true;
            }
            if(!isEmpty()) {
                Link<E> p = head, c = head.next;
                while (c != null) {
                    if (c.store == null) {
                        p.next = c.next;
                        c = c.next;
                        size--;
                        isRemoved = true;
                    } else {
                        p = c;
                        c = c.next;
                    }
                }
            }
        } else {
            if (head.store != null
                    && head.store.equals(element)) {
                unlinkHead();
                isRemoved = true;
            }
            if(!isEmpty()) {
                Link<E> p = head, c = head.next;
                while (c != null) {
                    if (c.store != null
                            && c.store.equals(element)) {
                        p.next = c.next;
                        c = c.next;
                        size--;
                        isRemoved = true;
                    } else {
                        p = c;
                        c = c.next;
                    }
                }
            }
        }
        return isRemoved;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * This version of {@code List#remove(int)} iterates over the
     * data until reaching the desired index. Here it removes the
     * {@code link}, and returns it's storage.
     *
     * @throws IndexOutOfBoundsException
     */
    @Override
    public E remove(final int index) {
        if (index < 1 || index > size){
            throw new IndexOutOfBoundsException(
                    "The given index is out of bounds."
            );
        }
        if(index == size) return unlinkHead();
        final Navigator<E> nav = navigateTo(index);
        nav.prev.next = nav.curr.next;
        size--;
        return nav.curr.store;
    }

    /**
     * A method to replace the first occurrence of an existing
     * element with a new element. This method returns a boolean
     * value indicating whether or not the replacement was successful.
     * This method can safely accept null parameters.
     *
     * @param element the element to be replaced
     * @param candidate the replacement element
     * @return whether or not the replacement was successful
     */
    public boolean replace(final E element, final E candidate){
        if (head == null) return false;
        if (element == null) {
            for (
                    Link<E> c = head;
                    c != null;
                    c = c.next
            ) {
                if(c.store == null) {
                    c.store = candidate;
                    return true;
                }
            }
        } else {
            for (
                    Link<E> c = head;
                    c != null;
                    c = c.next
            ) {
                if (c.store != null
                        && c.store.equals(element)) {
                    c.store = candidate;
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * A method to replace all occurrences of an existing
     * element with a new element. This method returns a boolean
     * value indicating whether or not the replacement was successful.
     * This method can safely accept null parameters.
     *
     * @param element the element to be replaced
     * @param candidate the replacement element
     * @return whether or not the replacement was successful
     */
    public boolean replaceAll(final E element, final E candidate){
        if (head == null) return false;
        boolean isReplaced = false;
        if (element == null) {
            for (
                    Link<E> c = head;
                    c != null;
                    c = c.next
            ) {
                if(c.store == null) {
                    c.store = candidate;
                    isReplaced = true;
                }
            }
        } else {
            for (
                    Link<E> c = head;
                    c != null;
                    c = c.next
            ) {
                if (c.store != null
                        && c.store.equals(element)) {
                    c.store = candidate;
                    isReplaced = true;
                }
            }
        }
        return isReplaced;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int indexOf(final E element) {
        if(head == null) return -1;
        int i = size;
        if (element == null) {
            for (Link<E> c = head; c != null; c = c.next, i--) {
                if (c.store == null) return i;
            }
        } else {
            for (Link<E> c = head; c != null; c = c.next, i--) {
                if (c.store != null && c.store.equals(element))
                    return i;
            }
        }
        return -1;
    }

    /**
     * A method that returns an {@code UnmodifiableList} of all of the
     * indexes occupied by the given element. If the element doesn't
     * exist, the method returns an empty {@code List}.
     *
     * @param element the element to look for
     * @return every index which the element occupies
     */
    public List<Integer> findAll(final E element){
        final List<Integer> indices = new ArrayList<>();
        int i = size;
        if(element == null){
            for (Link<E> c = head; c != null; c = c.next, i--) {
                if(c.store == null) indices.add(i);
            }
        }else{
            for (Link<E> c = head; c != null; c = c.next, i--) {
                if(c.store != null && c.store.equals(element))
                    indices.add(i);
            }
        }
        return EllieCollections.unmodifiableList(indices);
    }

    /*
     * A method to unlink and return the head.
     */
    private E unlinkHead(){
        final E removal = head.store;
        head = head.next;
        size--;
        return removal;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int size(){
        return size;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * A void method to delete all elements within a given range
     * (inclusive). Hints to garbage collection afterwards.
     */
    public void deleteRange(final int startPos, final int endPos){
        if(startPos < 1 || endPos < 1 || startPos > size || endPos > size)
            throw new IndexOutOfBoundsException(
                    "The given index is out of bounds."
            );
        if ((endPos - startPos) >= 0) {
            Link<E> start = navigateToLinkAt(startPos - 1);
            if(endPos == size) head = start;
            else {
                Link<E> end = navigateToLinkAt(endPos + 1);
                end.next = start;
            }
            size -= (endPos - startPos + 1);
            System.gc();
        }
    }

    /**
     * A method to describe the {@code List} with a {@code String}.
     *
     * @return a {@code String} describing the {@code List}
     */
    @Override
    public String toString() {
        final StringBuilder out = new StringBuilder("]");
        for (Link<E> n = head; n != null; n = n.next) {
            out.insert(0, n).insert(
                    0, (n.next != null ? ", " : "")
            );
        }
        return out.insert(0, "[").toString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toStore() {
        return toString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = size - 1;
        for (Link<E> n = head; n != null; n = n.next) {
            array[i--] = n.store;
        }
        head = null;
        size = 0;
        System.gc();
        return array;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int hashCode() {
        int hash = 1;
        for (Link<E> n = head; n != null; n = n.next) {
            hash = EllieCollections.HASH_CODE_CONST * hash +
                    (n.store != null ? n.hashCode() : 0);
        }
        return hash;
    }

    /**
     * @inheritDoc
     */
    @Override
    @SuppressWarnings("Unchecked")
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (!(other instanceof LinkedList)) return false;
        final SinglyLinkedList<E> cast = (SinglyLinkedList<E>) other;
        if (cast.size != this.size) return false;
        for (
                Link<E> n = head, on = cast.head;
                n != null && on != null;
                n = n.next, on = on.next
        ) {
            if (!n.equals(on)) return false;
        }
        return true;
    }

    /**
     * Link
     *
     * <p>
     * The basic unit of a singly-linked data structure.
     *
     * @author Ellie Moore
     * @version 04.09.2020
     * @param <E> the type
     */
    private static final class Link<E> {

        /**
         * The storage of the {@code Link}
         */
        public E store;

        /**
         * A pointer to the next {@code Link}
         */
        public Link<E> next;

        /**
         * A public constructor for a {@code Link}.
         * ({@code store} and {@code next} are guaranteed to be null)
         */
        public Link(){
        }

        /**
         * A method that returns a unique integer to represent the
         * {@code Link}.
         *
         * @return an integer representative of the {@code Link}
         */
        @Override
        public int hashCode(){
            return store != null? store.hashCode(): 0;
        }

        /**
         * A method to check for equality between two {@code Link}s
         *
         * @param other the {@code} Object to be compared
         * @return whether or not the {@code Link}s are equal
         */
        @Override
        @SuppressWarnings("Unchecked")
        public boolean equals(final Object other){
            if(this == other) return true;
            else if(!(other instanceof Link)) return false;
            final Link<E> cast = (Link<E>) other;
            return this.store.equals(cast.store);
        }

        /**
         * A method to describe the {@code Link} using a string.
         *
         * @return a {@code String} describing the {@code Link}.
         */
        @Override
        public String toString(){
            return (store != null)? store.toString(): "null";
        }

    }

}
