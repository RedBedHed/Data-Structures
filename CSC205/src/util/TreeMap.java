package util;


/**
 * Tree Map
 *
 * <p>
 * A {@code TreeMap} is a map that uses a binary search {@code Tree}
 * to store its data. A {@code TreeMap} is an efficient way to store
 * large Objects as it is capable of dynamic growth and provides
 * logarithmic-time access to stored objects in large quantities.
 *
 * <p>
 * Get and put operations for a balanced tree are ~O(logN). This
 * means that if N = 1,000,000 elements, then the time to retrieve
 * an element will be approximately proportional to log(1,000,000)
 * (~20 if base 2 is used). However, The time it takes to place an
 * element will be roughly the same. This is an important downside
 * for the programmer to consider.
 *
 * <p>
 * A {@code TreeMap} is ideal in situations where the data size
 * is large, and a "lookup" table is necessary. Here, the
 * {@code TreeMap} combines the practicality of a Map with the
 * speed of the binary search.
 *
 * <p>
 * This {@code TreeMap} is a kind of self balancing binary search
 * {@code Tree} called a Red-Black {@code Tree}. A Red-Black
 * {@code Tree} has Colored nodes. At all times, no two Red nodes
 * may be adjacent, and the number of Black nodes must be equivalent
 * for each path from the {@code Tree}'s root to its leaves. The root
 * must always be Black. These properties ensure that the {@code Tree}
 * is approximately balanced after each operation is performed. The
 * maximum height of the tree will always be 2log_2_(N+1) where N
 * is the number of nodes.
 *
 * <p>
 * One advantage that a Red-Black {@code Tree} has over other types
 * of self-balancing trees is that a Red-Black {@code Tree} doesn't
 * require the use of expensive arithmetic operations. This makes it
 * an ideal candidate for data-structures that will require many
 * insertions and deletions.
 *
 * @param <K> the key type
 * @param <V> the value type
 * @author Ellie Moore
 * @version 04.21.2020
 */
public class TreeMap<K, V> implements Map<K, V> {

    /**
     * The root node.
     */
    private Node<K, V> root;

    /**
     * The size of the {@code Map}.
     */
    private int size;

    /**
     * The {@code EllieComparator} to be used.
     */
    private EllieComparator<K> comp;

    /**
     * A public constructor for a {@code TreeMap} to use the provided
     * {@code Comparator}.
     *
     * @param comp the {@code Comparator} to be used.
     */
    public TreeMap(EllieComparator<K> comp) {

        this.comp = comp;

    }

    /**
     * A public constructor for a {@code TreeMap} to use the default
     * {@code Object#hashCode() Comparator}.
     */
    public TreeMap() {

        this(new EllieComparator<>() {
            @Override
            public int compare(K k, K kx) {
                return EllieComparator.super.compare(k, kx);
            }
        });

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * This version of {@code #put(K, V)} simply creates
     * a new {@code Node} and inserts the given key and
     * value into this node. {@code isDeleted} is reset
     * to false. The size counter is incremented.
     */
    @Override
    public void put(K key, V value) {

        Node<K, V> put = createNodeAt(key);
        put.value = value;
        balance(put);

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * This version of {@code #get(K)} navigates to the
     * {@code Node} corresponding to the given key. it
     * returns the value at this {@code Node}. If
     * {@code isDeleted}, this method will return null.
     * The client must check for this to prevent
     * {@link NullPointerException} from being thrown.
     */
    @Override
    public V get(K key) {

        Node<K, V> nav = navigateTo(key);
        if (nav == null) return null;
        return nav.value;

    }


    /**
     * {@inheritDoc}
     *
     * <p>
     * This version of {@code #remove(K)} returns the value
     * associated with the given key. {@code isDeleted} is
     * toggled to true. The size counter is decremented.
     */
    @Override
    public V remove(K key) {

        Node<K, V> nav = navigateTo(key);
        if (nav == null) return null;
        size--;
        return deleteNode(nav);

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

        return root == null;

    }

    /////////////////////////////////////////////////////////////////////
    // <<<<<<<<<< START INTERNAL COMPONENTS OF THE TREE MAP >>>>>>>>>> //
    /////////////////////////////////////////////////////////////////////

    /*
     * Node
     *
     * This is the definition of a Node, the basic unit of a
     * Tree Data-Structure. A Node has the following defining
     * characteristics:
     *
     *      1) key       - This is an element of any type that will
     *                     serve as an identifier and allow for
     *                     efficient retrieval of data.
     *
     *      2) value     - This is the reference to the data and
     *                     the primary reason for the Node's
     *                     existence.
     *
     *      3) left      - A reference to the left child Node.
     *
     *      4) right     - A reference to the right child Node.
     *
     *      5) parent    - A reference to the parent Node.
     *
     *      6) Color     - The color of the node (RED or BLACK).
     *
     */
    private static final class Node<K, V> {

        public K key;
        public V value;
        public Node<K, V> left;
        public Node<K, V> right;
        public Node<K, V> parent;
        public Color color;

        public Node(K key, Color color, Node<K, V> parent) {
            this.key = key;
            this.color = color;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return value != null ? value.toString() : "null";
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) return true;
            if (other == null) return false;
            if (!(other instanceof Node)) return false;
            Node<K, V> cast = (Node<K, V>) other;
            return this.value.equals(cast.value);
        }

    }

    /*
     * Color
     *
     * An enumeration to represent the color of each Node.
     * RED or BLACK.
     */
    private enum Color {

        RED {
            @Override
            public boolean isRed() {
                return true;
            }

            @Override
            public boolean isBlack() {
                return false;
            }
        },
        BLACK {
            @Override
            public boolean isRed() {
                return false;
            }

            @Override
            public boolean isBlack() {
                return true;
            }
        };

        public abstract boolean isRed();
        public abstract boolean isBlack();

    }

    /////////////////////////////////////////////////////////////////////
    // <<<<< NOTE: The sentinel value is *null* for all methods. >>>>> //
    /////////////////////////////////////////////////////////////////////

    /*
     * A method to navigate to the node with the given key.
     */
    private Node<K, V> navigateTo(final K key) {

        if(root == null) return null;
        if (key == null)
            throw new NullPointerException();
        Node<K, V> nav = root;
        while (true) {
            int cmp = comp.compare(key, nav.key);
            if (cmp < 0) {
                if (nav.left == null)
                    return null;
                nav = nav.left;
            } else if (cmp > 0) {
                if (nav.right == null)
                    return null;
                nav = nav.right;
            } else {
                return nav;
            }
        }

    }

    /*
     * A method to return either a new, ordered node with a unique key,
     * or an already existing node which holds the given key.
     */
    private Node<K, V> createNodeAt(final K key) {

        if (key == null)
            throw new NullPointerException();
        Node<K, V> nav = root;
        while (nav != null) {
            int cmp = comp.compare(key, nav.key);
            if (cmp < 0) {
                if (nav.left == null) {
                    size++;
                    return nav.left = new Node<>(
                            key, Color.RED, nav
                    );
                }
                nav = nav.left;
            } else if (cmp > 0) {
                if (nav.right == null) {
                    size++;
                    return nav.right = new Node<>(
                            key, Color.RED, nav
                    );
                }
                nav = nav.right;
            } else {
                return nav;
            }
        }
        size = 1;
        return root = new Node<>(
                key, Color.BLACK, null
        );

    }

    /*
     * A series of readability enhancing methods that
     * protect the Red-Black balancing act from the
     * inevitable Null Pointer.
     */
    private Node<K, V> parentOf(final Node<K, V> curr) {

        return curr == null ? null : curr.parent;

    }

    private Node<K, V> grandParentOf(final Node<K, V> curr) {

        return curr == null || curr.parent == null ?
                null : curr.parent.parent;

    }

    private Node<K, V> rightOf(final Node<K, V> curr) {

        return curr == null ? null : curr.right;

    }

    private Node<K, V> leftOf(final Node<K, V> curr) {

        return curr == null ? null : curr.left;

    }

    private Color colorOf(final Node<K, V> curr) {

        return curr == null ? Color.BLACK : curr.color;

    }

    private void setColor(final Node<K, V> curr, final Color color) {

        if (curr != null) curr.color = color;

    }

    /*
     * An algorithm to balance the Tree in Red-Black fashion.
     *
     * Sources:
     *
     *      - GeeksForGeeks.com
     *      - Programiz.com
     *      - ohio-state.edu
     *      - lmu.edu
     *      - java.util
     *      - duke.edu
     *
     * Rules:
     *
     *      1) The root Node must be BLACK.
     *
     *      2) No two adjacent Nodes may be RED. However,
     *         two adjacent Nodes may be BLACK.
     *
     *      3) Every path from the root to any leaf node
     *         must have the same number of BLACK nodes.
     */
    private void balance(Node<K, V> curr) {

        // If current Node's parent is Black, no balancing is needed.
        // If Red, the maximum number of rotations needed should be
        // two.
        // Always set the root to Black.
        while (curr != null && curr != root
                && colorOf(parentOf(curr)).isRed()) {
            // Note: parent of curr is Red. Its aunt/uncle may or may not
            // be Red.
            if (parentOf(curr) == leftOf(grandParentOf(curr))) {
                // The parent of the current node is the left child of
                // its parent.
                final Node<K, V> aunt = rightOf(grandParentOf(curr));
                if (colorOf(aunt).isRed()) {
                    // 1) If the color of the parent && aunt Node is Red,
                    // Try re-coloring. Change parent and aunt to Black,
                    // then change grandparent to Red and make curr point
                    // at its grandparent. Push the problem upwards.
                    setColor(parentOf(curr), Color.BLACK);
                    setColor(aunt, Color.BLACK);
                    setColor(grandParentOf(curr), Color.RED);
                    curr = grandParentOf(curr);
                    // Check loop condition.
                } else {
                    // 2) If the aunt Node is Black, perform necessary
                    // rotations.
                    // If the current Node is the right child of
                    // its parent, make current Node point at its parent,
                    // and rotate to the left around the new current.
                    if (curr == rightOf(parentOf(curr))) {
                        curr = parentOf(curr);
                        rotateLeft(curr);
                    }
                    // Note: at this point all Nodes surrounding current
                    // and current's parent MUST be Black.
                    // Re-color parent and grandparent and rotate
                    // right around grandparent.
                    setColor(parentOf(curr), Color.BLACK);
                    setColor(grandParentOf(curr), Color.RED);
                    rotateRight(grandParentOf(curr));
                    // Check loop condition. Color of parent of current
                    // should now be Black.
                }
            } else { // Note: symmetrical
                final Node<K, V> aunt = leftOf(grandParentOf(curr));
                if (colorOf(aunt).isRed()) {
                    setColor(parentOf(curr), Color.BLACK);
                    setColor(aunt, Color.BLACK);
                    setColor(grandParentOf(curr), Color.RED);
                    curr = grandParentOf(curr);
                } else {
                    if (curr == leftOf(parentOf(curr))) {
                        curr = parentOf(curr);
                        rotateRight(curr);
                    }
                    setColor(parentOf(curr), Color.BLACK);
                    setColor(grandParentOf(curr), Color.RED);
                    rotateLeft(grandParentOf(curr));
                }
            }
        }
        // Set the root to Black as it may have been changed to Red.
        setColor(root, Color.BLACK);

    }

    /*
     * A method to rotate a sub-tree to the left about
     * the given Node. Preserves order.
     *
     *   5R              9B
     *  /  \            /  \
     * 3B  9B    -->   5R  10B
     *    /  \        /  \
     *   7B  10B     3B  7B
     *
     * Sources:
     *
     *      - GeeksForGeeks.com
     *      - Programiz.com
     *      - java.util
     */
    private void rotateLeft(final Node<K, V> curr) {

        if (curr != null) {
            Node<K, V> rr = curr.right;
            curr.right = rr.left;
            if (rr.left != null)
                rr.left.parent = curr;
            rr.parent = curr.parent;
            if (curr.parent == null)
                root = rr;
            else if (curr == curr.parent.left)
                curr.parent.left = rr;
            else curr.parent.right = rr;
            rr.left = curr;
            curr.parent = rr;
        }

    }

    /*
     * A method to rotate a sub-tree to the right about
     * the given Node. Preserves order.
     *
     *     9R    -->   5B
     *    /  \        /  \
     *   5B  10B     3B  9R
     *  /  \            /  \
     * 3B  7B          7B  10B
     *
     * Sources:
     *
     *      - GeeksForGeeks.com
     *      - Programiz.com
     *      - java.util
     */
    private void rotateRight(final Node<K, V> curr) {

        if (curr != null) {
            Node<K, V> ll = curr.left;
            curr.left = ll.right;
            if (ll.right != null)
                ll.right.parent = curr;
            ll.parent = curr.parent;
            if (curr.parent == null)
                root = ll;
            else if (curr == curr.parent.right)
                curr.parent.right = ll;
            else curr.parent.left = ll;
            ll.right = curr;
            curr.parent = ll;
        }

    }

    /*
     * A method to get the successor of a node.
     *
     * 7B - current node              7B - current node
     *   \                  > OR <      \
     *   12R - successor                12R
     *                                  /
     *                                10B
     *                                /
     *                               9R - successor
     *
     *   6R -successor                 5B - successor
     *  /                   > OR <    /
     * 5B - current node             1R
     *                                 \
     *                                 3B
     *                                   \
     *                                   4R - current node
     */
    private Node<K, V> successor(final Node<K, V> n) {

        Node<K, V> x;
        if (n.right != null) {
            // Successor will be the minimum value from the
            // right child of n.
            x = n.right;
            while (x.left != null) {
                x = x.left;
            }
            return x;
        } else {
            // Successor will be the parent of the first left
            // child above n.
            x = n;
            Node<K, V> y = n.parent;
            while (y != null && x == y.right) {
                x = y;
                y = y.parent;
            }
            return y;
        }

    }

    /*
     * A method to get the predecessor of a Node.
     *
     *   3B - current node             5B - current node
     *  /                   > OR <    /
     * 1R - predecessor              1R
     *                                 \
     *                                 3B
     *                                   \
     *                                   4R - predecessor
     *
     *   3R - predecessor              4B - predecessor
     *     \                   > OR <    \
     *     6B - current node             7R
     *                                  /
     *                                 6B
     *                                /
     *                               5R - current node
     */
    private Node<K, V> predecessor(final Node<K, V> n) {

        Node<K, V> x;
        if (n.left != null) {
            // predecessor will be the maximum value from the
            // left child of n.
            x = n.left;
            while (x.right != null) {
                x = x.right;
            }
            return x;
        } else {
            // Successor will be the parent of the first right
            // child above n.
            x = n;
            Node<K, V> y = n.parent;
            while (y != null && x == y.left) {
                x = y;
                y = y.parent;
            }
            return y;
        }

    }

    /*
     * A method to transplant a Node. Transplants Original with
     * Replacement.
     */
    private void transplant(final Node<K, V> o, final Node<K, V> n) {

        if (o.parent == null)
            root = n;
        else if (o == o.parent.left)
            o.parent.left = n;
        else o.parent.right = n;
        if (n != null)
            n.parent = o.parent;
        else o.parent = null;

    }

    /*
     * An algorithm for the deletion of a node.
     */
    private V deleteNode(Node<K, V> n) {

        V nValue = null;
        if (n.left != null && n.right != null) {
            // n is internal. n has two children.
            // 1) If the node to be deleted, n, is either at the root or
            // somewhere inside the tree, find its successor.
            // 2) n's successor will either be the parent of a null left
            // child, or an ancestor of n in the case that n has no right child.
            // 3) Make n hold its successor's key and value, enabling successor
            // to be replaced via transplant.
            // 4) Make n point to its successor.
            Node<K, V> succ = successor(n);
            nValue = n.value;
            n.key = succ.key;
            n.value = succ.value;
            n = succ;
        }
        // NOTE: if n was internal, then n is now n's successor.
        // re is replacement Node. if the left child of n is null,
        // re is the right child. Otherwise, re is the left child.
        final Node<K, V> re = n.left == null ? n.right : n.left;
        if (re != null) {
            transplant(n, re); // If re is not null, transplant n with re.
            n.parent = n.right = n.left = null; // clear n to be safe.
            // re-balance if n was black in order to keep Red-Black
            // property #3 intact.
            if (n.color.isBlack()) reBalance(re);
        } else if (n.parent == null) {
            // If the parent of n is null, and both children of n are null,
            // then n is the only Node.
            nValue = n.value;
            root = null; // If n is the only Node.
        } else {
            // If n is not the only Node, but both children are null, then
            // n will need to be transplanted with null, and the tree must
            // be re-balanced beforehand if n is Black.
            if (n.color.isBlack()) reBalance(n);
            transplant(n, null);
        }
        return nValue;

    }

    /*
     * An algorithm to re-balance the tree after deletion.
     *
     * Sources:
     *
     *      - GeeksForGeeks.com
     *      - Programiz.com
     *      - ohio-state.edu
     *      - lmu.edu
     *      - duke.edu
     *      - java.util
     *
     */
    private void reBalance(Node<K, V> curr) {

        // Requires at most three rotations.
        // If current node is Black... Enter the loop.
        // If current node is Red... Skip the loop and set it
        // to Black.
        // If exiting the loop, make sure the root is Black.
        while (curr != root && colorOf(curr).isBlack()) {
            // Note: current Node is Black.
            if (curr == leftOf(parentOf(curr))) {
                // current node is a left child.
                Node<K, V> sib = rightOf(parentOf(curr));
                if (colorOf(sib).isRed()) {
                    // current Node has a Red sister.
                    // Note: parent must be black.
                    // Re-color and rotate left around parent.
                    setColor(sib, Color.BLACK);
                    setColor(parentOf(curr), Color.RED);
                    rotateLeft(parentOf(curr));
                    sib = rightOf(parentOf(curr));
                }
                if (colorOf(rightOf(sib)).isBlack()
                        && colorOf(leftOf(sib)).isBlack()) {
                    // Current Node has a Black sister with two Black
                    // children.
                    // re-color the sister.
                    setColor(sib, Color.RED);
                    // Push the problem upwards by one level.
                    curr = parentOf(curr);
                    // Check loop condition.
                } else {
                    if (colorOf(rightOf(sib)).isBlack()) {
                        // Current node has a Black sister with a Black
                        // right child and a Red left child.
                        // re-color and rotate right.
                        setColor(leftOf(sib), Color.BLACK);
                        setColor(sib, Color.RED);
                        rotateRight(sib);
                        sib = rightOf(parentOf(curr));
                    }
                    // Current node has a black sister with a Red
                    // right child.
                    // re-color and rotate left.
                    // Note: parent's color is unknown.
                    setColor(sib, colorOf(parentOf(curr)));
                    setColor(parentOf(curr), Color.BLACK);
                    setColor(rightOf(sib), Color.BLACK);
                    rotateLeft(parentOf(curr));
                    curr = root; // curr is now the root.
                    // Check loop condition.
                }
            } else { // Note: Symmetrical
                Node<K, V> sib = leftOf(parentOf(curr));
                if (colorOf(sib).isRed()) {
                    setColor(sib, Color.BLACK);
                    setColor(parentOf(curr), Color.RED);
                    rotateRight(parentOf(curr));
                    sib = leftOf(parentOf(curr));
                }
                if (colorOf(leftOf(sib)).isBlack()
                        && colorOf(rightOf(sib)).isBlack()) {
                    setColor(sib, Color.RED);
                    curr = parentOf(curr);
                } else {
                    if (colorOf(leftOf(sib)).isBlack()) {
                        setColor(rightOf(sib), Color.BLACK);
                        setColor(sib, Color.RED);
                        rotateLeft(sib);
                        sib = leftOf(parentOf(curr));
                    }
                    setColor(sib, colorOf(parentOf(curr)));
                    setColor(parentOf(curr), Color.BLACK);
                    setColor(leftOf(sib), Color.BLACK);
                    rotateRight(parentOf(curr));
                    curr = root;
                }
            }
        }
        setColor(curr, Color.BLACK);
        // If current node was Red, now it is Black.
        // If current node is root, now root is Black.

    }

    /*
     * A method which navigates the Tree and builds a String
     * in key order.
     */
    private String inOrderBuild(final Node<K, V> curr) {

        if (curr == null) return "";
        return inOrderBuild(curr.left) + (
                curr.key + "=" + curr.value + ", "
        ) + inOrderBuild(curr.right);

    }

    /*
     * A method that builds a String in pre-order.
     */
    private String preOrderBuild(final Node<K, V> curr) {

        if (curr == null) return "";
        return curr.key + "=" + curr.value + ", " +
                preOrderBuild(curr.left) +
                preOrderBuild(curr.right);

    }

    /*
     * A method that builds a String in post-order.
     */
    private String postOrderBuild(final Node<K, V> curr) {

        if (curr == null) return "";
        return postOrderBuild(curr.left) +
                postOrderBuild(curr.right) +
                curr.key + "=" + curr.value + ", ";

    }

    /*
     * A method to print a key order String of keys, values,
     * colors, and depths.
     */
    private String buildDepthChart(final Node<K, V> curr, final int depth) {

        if (curr == null) return "";
        return buildDepthChart(curr.left, depth - 1) + (
                curr.key + "-" + curr.color + "-" + depth + ", "
        ) + buildDepthChart(curr.right, depth - 1);

    }

    /*
     * A method to navigate the Tree and return a boolean value
     * reflecting equality between two TreeMaps.
     */
    private boolean splitEq(final Node<K, V> other, final Node<K, V> curr) {

        if (other == null) return curr == null;
        if (curr == null) return false;
        return splitEq(other.left, curr.left)
                && ((other.equals(curr))
                && splitEq(other.right, curr.right));

    }

    /*
     * A method to navigate the Tree and return a unique integer
     * representative of its data.
     */
    private int splitHash(final Node<K, V> curr, int hash) {

        if (curr == null) return 0;
        hash = EllieCollections.HASH_CODE_CONST * hash + curr.hashCode();
        return splitHash(curr.left, hash) +
                hash +
                splitHash(curr.right, hash);

    }

    /*
     * A constant to trim the excess in toString().
     */
    private static final int TRIM_CONSTANT = 2;

    /////////////////////////////////////////////////////////////////////
    // <<<<<<<<<<< END INTERNAL COMPONENTS OF THE TREE MAP >>>>>>>>>>> //
    /////////////////////////////////////////////////////////////////////

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {

        String str = inOrderBuild(root);
        return "[" + (
                str.length() >= TRIM_CONSTANT?
                        str.substring(0, str.length() - TRIM_CONSTANT): ""
        ) + "]";

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
     * @see Object#equals(Object)
     */
    @Override
    @SuppressWarnings("Unchecked")
    public boolean equals(Object other) {

        if (this == other) return true;
        if (other == null) return false;
        if (!(other instanceof TreeMap)) return false;
        TreeMap<K, V> cast = (TreeMap<K, V>) other;
        if (this.root == null) return cast.root != null;
        if (cast.root == null) return false;
        return this.splitEq(cast.root, this.root);

    }

    /**
     * @inheritDoc
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {

        return splitHash(root, 1);

    }

}
