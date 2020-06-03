package util;

/**
 * Linked List
 *
 * <p>A {@code LinkedList} is a {@code List} that stores its data in
 * {@code Link}s.
 *
 * <p>A {@code LinkedList} is most efficient when used for storing large,
 * expensive {@code Object}s. The main reason for this is that a {@code LinkedList}
 * can grow without copying its data, while an {@code ArrayList} cannot. Therefore,
 * it is a good idea for a programmer to keep both of these tools under their belt.
 *
 * @param <E> the type
 */
public class LinkedList<E> implements List<E> {

    /**
     * An empty {@code Link} at the start of the {@code List} that will
     * act as a root.
     */
    private Link<E> root;

    /**
     * An empty {@code Link} at the end of the {@code List} that will act as a
     * stopper and allow the client to efficiently append data.
     */
    private Link<E> leaf;

    /**
     * The size of the {@code List}.
     */
    private int size;

    /**
     * A public constructor for a {@code LinkedList}. Its purpose is to initialize
     * the root and leaf {@code Link}s and set the {@code size} equal to zero.
     */
    public LinkedList() {
        root = new Link<>();
        leaf = new Link<>();
        root.next = leaf;
        leaf.prev = root;
        size = 0;
    }

    /**
     * A secondary constructor for a LinkedList which takes an
     * element array as a parameter.
     *
     * @param array the array to be converted.
     */
    public LinkedList(final E[] array){
        this();
        for(final E e: array) add(e);
    }

    /*
     * A method to navigate to the link at the given index.
     * If the index is less than half of the size, this method will
     * start at the root. If the index is greater than half of the size
     * this method will start at the leaf.
     */
    private Link<E> navigateTo(final int index){
        Link<E> n;
        if(index < (size >>> 1)) {
            n = root.next;
            for (
                int i = 0;
                n.next != null
                && i < index;
                n = n.next,
                i++
            );
        } else {
            n = leaf.prev;
            for (
                int i = size;
                n.prev != null
                && i > index + 1;
                n = n.prev,
                i--
            );
        }
        return n;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This method iterates through each {@code Link} in the {@code List}
     * until it arrives at the specified index. It then returns the element
     * at this index.
     *
     * @param index the index of the element to be removed
     * @return the element at the given index
     */
    @Override
    public E get(final int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "The given index is out of bounds."
            );
        }
        return navigateTo(index).store;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This method iterates through each {@code Link} in the {@code List}
     * until it arrives at the specified index. It then returns the element
     * at this index, removes the {@code Link}, and chains its previous and next
     * {@code Link}s together to close the gap.
     *
     * @param index the index of the element to be removed
     * @return the element at the given index
     */
    @Override
    public E remove(final int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "The given index is out of bounds."
            );
        }
        final Link<E> nav = navigateTo(index);
        size--;
        return unlink(nav);
    }

    /**
     * A method that removes a specified element by iterating through the
     * entire list in a sequential O(n) search and removing the first
     * occurrence. If the element is not in the list, this method returns
     * null.
     *
     * @param element the first occurrence of the specified element
     * @return the element
     */
    @Override
    public boolean remove(final E element) {
        if(element == null) {
            for (Link<E> n = root.next; n.next != null; n = n.next) {
                if (n.store == null) {
                    size--;
                    unlink(n);
                    return true;
                }
            }
        } else {
            for (Link<E> n = root.next; n.next != null; n = n.next) {
                if (n.store != null && n.store.equals(element)) {
                    size--;
                    unlink(n);
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
     * @param element the element to be removed
     * @return whether or not the element has been removed
     */
    public boolean removeAll(final E element) {
        boolean flag = false;
        if(element == null) {
            for(Link<E> n = root.next; n.next != null; n = n.next){
                if(n.store == null) {
                    unlink(n);
                    size--;
                    flag = true;
                }
            }
        } else {
            for(Link<E> n = root.next; n.next != null; n = n.next){
                if(n.store != null && n.store.equals(element)) {
                    unlink(n);
                    size--;
                    flag = true;
                }
            }
        }
        return flag;
    }

    /*
     * A method to unlink the given Link from the List.
     */
    private E unlink(Link<E> nav){
        final E removal = nav.store;
        nav.next.prev = nav.prev;
        nav.prev.next = nav.next;
        return removal;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This method iterates through the {@code List} until it arrives
     * at the specified index. Here, it swaps the contents of the {@code Link}
     * with the given element, proceeding to return the contents.
     *
     * @param index   the index to replace
     * @param element the replacement
     * @return the occupant of the given index
     */
    @Override
    public E set(final int index, final E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "The given index is out of bounds."
            );
        }
        return swap(navigateTo(index), element);
    }

    /*
     * A method to mutate the given link, assigning the given element
     * to its storage and returning the previous occupant.
     */
    private E swap(final Link<E> candidate, final E element){
        final E removal = candidate.store;
        candidate.store = element;
        return removal;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This method iterates through the {@code List} until it reaches
     * the given index. Here, it inserts an element by boxing it in a {@code Link},
     * unchaining the {@code List} at the index, and attaching the ends to the new
     * {@code Link}.
     *
     * @param index   the index for the element to be inserted
     * @param element the element to be inserted
     */
    @Override
    public void add(final int index, final E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "The given index is out of bounds."
            );
        }
        if (index < size) {
            insertLink(new Link<>(), navigateTo(index), element);
            size++;
        } else add(element);
    }

    /*
     * A method to insert a new link at the candidate location.
     */
    private void insertLink(final Link<E> insertion,
                            final Link<E> candidate,
                            final E element){
        insertion.store = element;
        insertion.next = candidate;
        candidate.prev.next = insertion;
        insertion.prev = candidate.prev;
        candidate.prev = insertion;
    }

    /**
     * A method that adds a {@code Link} to the front of the {@code List}.
     * the given element is assigned to {@code leaf}'s storage, a new {@code link}
     * is chained onto the end, and the new {@code Link} is assigned to {@code leaf}.
     *
     * @param element the element to be appended to the {@code List}
     */
    public LinkedList<E> add(final E element) {
        Link<E> addition = new Link<>();
        leaf.store = element;
        leaf.next = addition;
        addition.prev = leaf;
        leaf = addition;
        size++;
        return this;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean replace(E candidate, E element){
        if(candidate == null){
            for(Link<E> n = root.next; n.next != null; n = n.next){
                if(n.store == null) {
                    swap(n, element);
                    return true;
                }
            }
        } else {
            for(Link<E> n = root.next; n.next != null; n = n.next){
                if(n.store != null && n.store.equals(candidate)) {
                    swap(n, element);
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
     * @param candidate the element to be replaced
     * @param element the element to replace the candidate
     * @return whether or not the element was replaced successfully
     */
    public boolean replaceAll(E candidate, E element){
        boolean flag = false;
        if(candidate == null){
            for(Link<E> n = root.next; n.next != null; n = n.next){
                if(n.store == null) {
                    swap(n, element);
                    flag = true;
                }
            }
        } else {
            for(Link<E> n = root.next; n.next != null; n = n.next){
                if(n.store != null && n.store.equals(candidate)) {
                    swap(n, element);
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This method iterates through the {@code List} until it comes
     * across the first occurrence of the given element. If the element is
     * found, the method returns the index of the element. If the element is
     * not found, the method returns -1.
     *
     * @param element the element who's index is in question
     * @return the index of the given element
     */
    @Override
    public int indexOf(final E element) {
        if(size == 0) return -1;
        int i = 0;
        if(element == null) {
            for (Link<E> n = root.next; n.next != null; n = n.next) {
                if (n.store == null) return i;
                i++;
            }
        } else {
            for (Link<E> n = root.next; n.next != null; n = n.next) {
                if (n.store != null && n.store.equals(element)) return i;
                i++;
            }
        }
        return -1;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        int i = 0;
        final StringBuilder out = new StringBuilder("[");
        for (Link<E> n = root.next; n.next != null; n = n.next) {
            out.append(n);
            if (++i < size) {
                out.append(", ");
            }
        }
        return out.append("]").toString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toStore() {
        int i = 0;
        final StringBuilder out = new StringBuilder("[");
        for (Link<E> n = root; n != null; n = n.next) {
            out.append(n);
            if (++i <= size + 1) {
                out.append(", ");
            }
        }
        return out.append("]").toString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int hashCode() {
        int hash = 1;
        for (Link<E> n = root.next; n.next != null; n = n.next) {
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
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (!(other instanceof LinkedList)) return false;
        LinkedList<E> cast = (LinkedList<E>) other;
        if (cast.size != this.size) return false;
        for (
             Link<E> n = root.next, on = cast.root.next;
             n.next != null && on.next != null;
             n = n.next, on = on.next
        ) if (!n.equals(on)) return false;
        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (Link<E> navigator = root.next;
             navigator != leaf;
             navigator = navigator.next) {
            array[i++] = navigator.store;
        }
        size = 0;
        root.next = leaf;
        leaf.prev = root;
        return array;
    }

}
