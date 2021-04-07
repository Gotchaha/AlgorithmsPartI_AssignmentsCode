import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a = (Item[]) new Object[1];
    private int n = 0;

    public RandomizedQueue() { }

    private void resize(int max)
    {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    public boolean isEmpty()
    {
        return n == 0;
    }

    public int size()
    {
        return n;
    }

    public void enqueue(Item item)
    {
        if (item == null) throw new IllegalArgumentException();
        if (n == a.length) resize(2 * a.length);
        a[n++] = item;
    }

    public Item dequeue()
    {
        if (isEmpty()) throw new NoSuchElementException();
        if (n == a.length/4) resize(a.length/2);
        int index = StdRandom.uniform(n);
        Item temp = a[index];
        a[index] = a[--n];
        a[n] = null;
        return temp;
    }

    public Item sample()
    {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(n);
        return a[index];
    }


    @Override
    public Iterator<Item> iterator() {
        return new RandomizedIt();
    }


    private class RandomizedIt implements Iterator<Item>
    {
        private Item[] random = (Item[]) new Object[n];
        private int t = n;

        public RandomizedIt()
        {
            for (int i = 0; i < n; i++) {
                random[i] = a[i];
            }
            StdRandom.shuffle(random);
        }

        @Override
        public boolean hasNext()
        {
            return t > 0;
        }

        @Override
        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException();
            return random[--t];
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }
}
