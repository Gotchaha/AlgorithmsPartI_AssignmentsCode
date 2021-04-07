import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int count = 0;
        RandomizedQueue<String> queue = new RandomizedQueue<>();

//        while (!StdIn.isEmpty())
//        {
//            String s = StdIn.readString();
//            queue.enqueue(s);
//        }
//        for (int i = 0; i < k; i++) {
//            StdOut.println(queue.dequeue());
//        }

        // for the bonus credit [Reservoir Sampling]
        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            count++;
            if (queue.size() < k)
            {
                queue.enqueue(s);
            }
            else
            {
                if (StdRandom.uniform() < (double) k / count)
                {
                    queue.dequeue();
                    queue.enqueue(s);
                }
            }
        }

        for (String s :
                queue) {
            StdOut.println(s);
        }
    }
}
