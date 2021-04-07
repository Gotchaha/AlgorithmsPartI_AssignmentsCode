# AlgorithmsPartI_AssignmentsCode
*The course is on Coursera(https://www.coursera.org/learn/algorithms-part1), provided by Princeton, text book: Algorithms, 4th edition.*
*The programming assignments code are finished by myself.*

## Week1 -- Percolation
*grade: 100*

[*Specification*](https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php)

[Code1 Percolation class](/src/Percolation.java)  
[Code2 PercolationStats](/src/PercolationStats.java)

:exclamation:note:  
* It's necessary to read the FAQ in the specification.   
* You have to use *WeightedQuickUnionUF* in algs4.jar. I didn't notice that at first and spent much time improving the UF
algorithm.
* The API specifies that valid row and column indices are between 1 and n.
* The crucial part:
   1. Plan the map from a 2-dimensional (row, column) pair to a 1-dimensional union find object index.
   2. The course introduces the trick of two virtual nodes to reduce computational complexity. However, in practice this will lead to the **'backwash'** problem (see the specification's FAQ for more info).
   There're many solutions on the Internet such as using another UF (but this may cause space usage problem). 
   
   Here's my solution :  
   First, I came up with the idea of using a 2-dimention array to store the status of each node, such as 0 <--> blocked, 1 <--> open ... . However this was proved to be having some problem. Then 
   got inspired from [here](https://stackoverflow.com/questions/61396690/how-to-handle-the-backwash-problem-in-percolation-without-creating-an-extra-wuf), I added a one-dimention byte array status[]
   to **store three kinds of state: open, connect to the top, connect to the bottom** with three bits. And instead of recording every node's state, it's important to only store and update 
   the state of the root of each component. With **bit manipulation** we can easily and fast implement the methods. For example:
   ```java
   public boolean isFull(int row, int col) {
        validate(row, col);
        int id = xyTo1D(row, col);
        int root = uf.find(id);
        return (status[root] & 2) == 2;  // using bit manipulation.
    }
   ```
   It's easy to check some condition of any node using bit manipulation. And it's also very elegant to update the state in `open()` method (just combine the state of different roots 
   and current node using 'OR'). This solution is also space-saving so it can pass all and the bonus Memory check. Plus, I kept the top virtual node which may reduce some steps.
   

## Week2 -- Deques and Randomized Queues
*grade: 100*

[*Specification*](https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php)

[Code1 Deque class](/src/Deque.java)  
[Code2 RandomizedQueue class](/src/RandomizedQueue.java)
[Code3 Permutation client](/src/Permutation.java)

:exclamation:note:  
* Dual Linked List used in *Deque* implementation
* Use Resizing Array to implement *RandomizedQueue* due to the performance requirement.
* The crucial part:
   For the bonus credit in *permutation*, you need to use [Reservoir sampling](https://en.wikipedia.org/wiki/Reservoir_sampling#:~:text=Reservoir%20sampling%20is%20a%20family,to%20fit%20into%20main%20memory.). It's very interesting.
* The funny part is that I'v got 99 for 3 times, then I found that I made a mistake in corner case in inner class.
