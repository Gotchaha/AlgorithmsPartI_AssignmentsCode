import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int n;

    public Deque() { }

    private class Node
    { // Memory usage: 16+8+3*8=48bytes
        Item item;
        Node next;
        Node prev;  // to assure removeLast() in constant worst-case time.
    }

    public boolean isEmpty()
    {
        return n == 0;
    }

    public int size()
    {
        return n;
    }

    public void addFirst(Item item)
    {
        if (item == null) throw new IllegalArgumentException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (isEmpty()) last = first;
        else oldFirst.prev = first;
        n++;
    }

    public void addLast(Item item)
    {
        if (item == null) throw new IllegalArgumentException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        n++;
    }

    public Item removeFirst()
    {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = first;
        else first.prev = null;
        return item;
    }

    public Item removeLast()
    {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        n--;
        if (isEmpty()) first = last;
        else last.next = null;
        return item;
    }

    @Override
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args)
    {
        Deque<Integer> s = new Deque<>();
        Deque<Integer> q = new Deque<>();
        while (!StdIn.isEmpty())
        {
            Integer item = StdIn.readInt();
            s.addFirst(item);
            q.addLast(item);
        }
        while (!s.isEmpty())
        {
            StdOut.print(s.removeFirst() + " ");
        }
        StdOut.printf("\n");
        while (!q.isEmpty())
        {
            StdOut.print(q.removeFirst() + " ");
        }
    }


}
