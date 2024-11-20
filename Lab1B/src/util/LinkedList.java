/** @author Eimutis Karčiauskas, KTU IF Department of Software Engineering, 23/09/2014
        *
        * This is the first class of complex data structure that allows merging
        * individual objects into one whole - a list. Objects that will be placed in
        * list, must meet the interface Comparable <E>.
        *
        * Task: Review and clarify the methods provided. Algorithms of methods
        * is discussed during the lecture. Implement the specified methods.
        * ******************************************************** ****************************
        */

package util;

import java.util.Arrays;
import java.util.Comparator;

/**
        * Corrected on 09/18/2015
        *
        * @author Aleksis
        * @param <E> List item type (class)
        */

public class LinkedList<E extends Comparable<E>>
        implements List<E>, Iterable<E>, Cloneable {

    private Node<E> first;   // arrow to the first node
    private Node<E> last;    // arrow to the last node
    private Node<E> current; // pointer to the current node used by getNext
    private int size;        // the size of the list, the same number of items

    /**
     * Constructs an empty list.
     */
    public LinkedList() {
    }

    /**
     * the add method adds an item to the end of the list
     *
     * @param e is an insert (it cannot be null)
     * @return true if the operation was successful
     */
    @Override
    public boolean add(E e) {
        if (e == null) {
            return false;   // zero objects are not accepted
        }
        if (first == null) {
            first = new Node<>(e, first);
            last = first;
        } else {
            Node<E> e1 = new Node(e, null);
            last.next = e1;
            last = e1;
        }
        size++;
        return true;
    }

    /*** Inserts an element before the kth position
      ** @param k position
      * @param e insert
      * @return if k is bad, returns null
            */

    @Override
    public boolean add(int k, E e) {
        if (e == null) {
            return false;
        }
        if (k < 0 || k >= size) {
            return false;
        }
        throw new UnsupportedOperationException("Students needs to implement add (int k, E e) method");
    }

    /**
        *
        * @return list size (number of items)
     */
    @Override
    public int size() {
        return size;
    }

    /*** checks if the list is empty
        *
        * @return true if empty
    */
    @Override
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Clears the list
     */
    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;
        current = null;
    }

    /**
     * Returns an item based on its index (initial index 0)
     *
     * @param k index
     * @return the value of the kth element; if k is bad, returns null
     */
    @Override
    public E get(int k) {
        if (k < 0 || k >= size) {
            return null;
        }
        current = first.findNode(k);
        return current.element;
    }

    /**
     * Changes the value of the kth element
     *
     * @param k index of the element to be replaced
     * @param e is the new value of the element
     * @return old value
     */
    @Override
    public E set(int k, E e) {
        throw new UnsupportedOperationException("Students need to implement set (int k, E e) method");
    }

        /*** moves to another value and returns it
            ** @return other value
         * */
    @Override
    public E getNext() {
        if (current == null) {
            return null;
        }
        current = current.next;
        if (current == null) {
            return null;
        }
        return current.element;
    }

    /**
     * Removes an item by index
     *
     * @param k removal index
     * @return item removed
     */
    @Override
    public E remove(int k) {
        throw new UnsupportedOperationException("Students need to implement remove (int k)");
    }

    /**
     *
     * @return a copy of the list
     */
    @Override
    public LinkedList<E> clone() {
        LinkedList<E> cl = null;
        try {
            cl = (LinkedList<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            Ks.ern("Bad operation of the ListKTU class ancestor method clone ()");
            System.exit(1);
        }
        if (first == null) {
            return cl;
        }
        cl.first = new Node<>(this.first.element, null);
        Node<E> e2 = cl.first;
        for (Node<E> e1 = first.next; e1 != null; e1 = e1.next) {
            e2.next = new Node<>(e2.element, null);
            e2 = e2.next;
            e2.element = e1.element;
        }
        cl.last = e2.next;
        cl.size = this.size;
        return cl;
    }
    //  !

    /**
     * An Object array is formed (an E-type array cannot be created) and enumerated
     * list items
     *
     * @return an array of list items
     */
    public Object[] toArray() {
        Object[] a = new Object[size];
        int i = 0;
        for (Node<E> e1 = first; e1 != null; e1 = e1.next) {
            a[i++] = e1.element;
        }
        return a;
    }

    /**
     * Array sorting by the Arrays class method sort
     */
    public void sortSystem() {
        Object[] a = this.toArray();
        Arrays.sort(a);
        int i = 0;
        for (Node<E> e1 = first; e1 != null; e1 = e1.next) {
            e1.element = (E) a[i++];
        }
    }

    /**
     * Sorting Arrays class method sort by comparator
     *
     * @param c comparator
     */
    public void sortSystem(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);
        int i = 0;
        for (Node<E> e1 = first; e1 != null; e1 = e1.next) {
            e1.element = (E) a[i++];
        }
    }

    /**
     * List sorting by bubble sort method
     */
    public void sortBuble() {
        if (first == null) {
            return;
        }
        for (;;) {
            boolean jauGerai = true;
            Node<E> e1 = first;
            for (Node<E> e2 = first.next; e2 != null; e2 = e2.next) {
                if (e1.element.compareTo(e2.element) > 0) {
                    E t = e1.element;
                    e1.element = e2.element;
                    e2.element = t;
                    jauGerai = false;
                }
                e1 = e2;
            }
            if (jauGerai) {
                return;
            }
        }
    }

    /**
     * Bubble sort order by comparator
     *
     * @param c comparator
     */
    public void sortBuble(Comparator<? super E> c) {
        if (first == null) {
            return;
        }
        for (;;) {
            boolean jauGerai = true;
            Node<E> e1 = first;
            for (Node<E> e2 = first.next; e2 != null; e2 = e2.next) {
                if (c.compare(e1.element, e2.element) > 0) {
                    E t = e1.element;
                    e1.element = e2.element;
                    e2.element = t;
                    jauGerai = false;
                }
                e1 = e2;
            }
            if (jauGerai) {
                return;
            }
        }
    }

    /**
     * Creates an iterator object for viewing list items
     *
     * @return iterator object
     */
    @Override
    public java.util.Iterator<E> iterator() {
        return new Iterator();
    }

    /**
     * Iterator methods class
     */
    class Iterator implements java.util.Iterator<E> {

        private Node<E> iterPosition;

        Iterator() {
            iterPosition = first;
        }

        @Override
        public boolean hasNext() {
            return iterPosition != null;
        }

        @Override
        public E next() {
            E d = iterPosition.element;
            iterPosition = iterPosition.next;
            return d;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Students need to implement remove ()");
        }
    }

    /**
     * Internal node class
     *
     * @param <E> node data type
     */
    private static class Node<E> {

        private E element;    // it is not visible outside the ListKTU class
        private Node<E> next; // next - as usual - a reference to another node

        Node(E data, Node<E> next) { //node constructor
            this.element = data;
            this.next = next;
        }

        /**
         * Finds the kth node in the list
         *
         * @param k index of the searched node (starting from 0)
         * @return nodes found
         */
        public Node<E> findNode(int k) {
            Node<E> e = this;
            for (int i = 0; i < k; i++) {
                e = e.next;
            }
            return e;
        }
    } // end of class node
}
