import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] A = (Item[]) new Object[1];
    private int Size = 0;

    private void resize(int n) {
        Item[] newArray = (Item[]) new Object[n];
        for (int i = 0; i < Size; i++)
            newArray[i] = A[i];
        A = newArray;
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
        if (item == null) throw new IllegalArgumentException();
        if (Size == A.length) resize(A.length * 2);
        A[Size++] = item;
    }

    // remove and return a random item
    public Item dequeue()
    {
        if (isEmpty()) throw new NoSuchElementException();
        if (Size < A.length/4) resize(A.length/2);

        int random = StdRandom.uniform(Size);
        Item item = A[random];

        //Set to null for garbage collector...
        if (random == Size - 1) A[random] = null;
        else {
            A[random] = A[Size - 1];
            A[Size - 1] = null;
        }
        Size--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        if (isEmpty()) throw new NoSuchElementException();
        int random = StdRandom.uniform(Size);
        return A[random];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { return new RandomQueueIterator(); }

    private class RandomQueueIterator implements Iterator<Item> {
        private Item[] shuffledItems;
        private int n = Size;

        RandomQueueIterator()
        {
            shuffledItems = (Item[]) new Object[Size];
            System.arraycopy(A, 0, shuffledItems, 0, Size);
            StdRandom.shuffle(shuffledItems);
        }
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return shuffledItems[--n];
        }

        public boolean hasNext() { return n > 0;}

        public void remove() {throw new UnsupportedOperationException();}

    }

    // unit testing (required)
    public static void main(String[] args)
    {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        System.out.println("Adding 1-5");
        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");
        queue.enqueue("4");
        queue.enqueue("5");
        for (String s: queue) {
            System.out.print(s + "\t");
        }
        System.out.printf("\nRandom item: %s\n", queue.sample());
        System.out.println("Removing 2 items");
        queue.dequeue();
        queue.dequeue();
        for (String s: queue) {
            System.out.print(s + "\t");
        }
        System.out.printf("\nSize: %d", queue.size());

    }

}
