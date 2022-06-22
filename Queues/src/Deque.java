import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int n;

    private class Node
    {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {}

    // is the deque empty?
    public boolean isEmpty() { return first == null; }

    // return the number of items on the deque
    public int size() { return n; }

    // add the item to the front
    public void addFirst(Item item)
    {
        if (item == null) { throw new NullPointerException(); }

        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (n > 0) oldfirst.prev = first;
        else last = first;
        n++;
    }

    // add the item to the back
    public void addLast(Item item)
    {
        if (item == null) { throw new NullPointerException(); }

        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prev = oldlast;
        if (n > 0)  oldlast.next = last;
        else        first = last;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if (this.isEmpty()) { throw new NoSuchElementException(); }

        Item item = first.item;
        if (n > 1) {
            first = first.next;
            first.prev = null;
        }
        else {
            first = null;
            last = null;
        }
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast()
    {
        if (this.isEmpty()) { throw new NoSuchElementException(); }

        Item item = last.item;
        if (n > 1) {
            last = last.prev;
            last.next = null;
        }
        else {
            first = null;
            last = null;
        }
        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new DequeIterator(); }

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;

        public boolean hasNext()
        { return current != null;}

        public void remove()
        { throw new UnsupportedOperationException("Invalid operation for this Queue."); }

        public Item next()
        {
            if (!this.hasNext()) { throw new NoSuchElementException(); }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        Deque<Integer> intDeque = new Deque<Integer>();

        intDeque.addFirst(5);
        intDeque.addLast(6);

    }

}
