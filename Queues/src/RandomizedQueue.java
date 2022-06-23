import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a = (Item[]) new Object[1];
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {}

    // is the randomized queue empty?
    public boolean isEmpty() {return n == 0;}

    // return the number of items on the randomized queue
    public int size() {return n;}

    // add the item
    public void enqueue(Item item) {}

    // remove and return a random item
    public Item dequeue()

    // return a random item (but do not remove it)
    public Item sample()

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { return new Deque.RandomQueueIterator(); }

    private class RandomQueueIterator implements Iterator<Item> {

    }

    // unit testing (required)
    public static void main(String[] args)

}
