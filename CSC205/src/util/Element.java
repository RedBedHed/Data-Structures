package util;

/**
 * Element
 *
 * The basic unit of a {@code PriorityQueue}.
 *
 * @author Ellie Moore
 * @version 05.09.2020
 */
final class Element<E> {

    /**
     * The priority of the {@code Element}.
     */
    protected int priority;

    /**
     * The storage of the {@code Element}.
     */
    protected E store;

    /**
     * A public constructor for an {@code Element}.
     *
     * @param priority the priority of the {@code Element}
     * @param store the internal storage of the {@code Element}
     */
    public Element(final int priority, final E store){
        this.priority = priority;
        this.store = store;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString(){
        return store.toString() + "-" + priority;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int hashCode(){
        return store.hashCode();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean equals(Object other){
        if(this == other) return true;
        if(other == null) return false;
        if(!(other instanceof Element)) return false;
        Element<E> cast = (Element<E>) other;
        return this.store.equals(cast.store) &&
                this.priority == cast.priority;
    }

}
