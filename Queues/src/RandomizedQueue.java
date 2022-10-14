import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] Items = (Item[]) new Object[1];
    private int Size = 0;

    private void resize(int n) {
        Item[] newArray = (Item[]) new Object[n];

        for (int i = 0; i < Size; i++) {
            newArray[i] = Items[i];
        }
        Items = newArray;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {}

    // is the randomized queue empty?
    public boolean isEmpty() {return Size == 0;}

    // return the number of items on the randomized queue
    public int size() {return Size;}

    // add the item
    public void enqueue(Item item)
    {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (Size == Items.length) {
            resize(Items.length * 2);
        }
        Items[Size++] = item;
    }

    // remove and return a random item
    public Item dequeue()
    {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (Size < Items.length/4) {
            resize(Items.length/2);
        }

        int random = StdRandom.uniform(Size);
        Item item = Items[random];

        //Set to null for garbage collector...
        if (random == Size - 1)
        {
            Items[random] = null;
        }
        else {
            Items[random] = Items[Size - 1];
            Items[Size - 1] = null;
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int random = StdRandom.uniform(Size);
        return Items[random];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { return new Deque.RandomQueueIterator(); }

    private class RandomQueueIterator implements Iterator<Item> {

        private int n = 0;

        RandomQueueIterator()
        {

        }
        public Item next() {
            if (n >= size) throw new NoSuchElementException();
        }

        public boolean hasNext() {
            return n < Size;
        }

    }

    // unit testing (required)
    public static void main(String[] args)

}
