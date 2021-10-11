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


## Week3 -- Collinear Points
*grade: 100*

[*Specification*](https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php)

[Code1 Point class](/src/Point.java)  
[Code2 Brute method](/src/BruteCollinearPoints.java)  
[Code3 Fast method](/src/FastCollinearPoints.java)

:exclamation:note:
* The main idea of this taks is using sort to implement more efficient algorithms. (Brute --> Fast)
* In the big picture, the most difficult part is to eliminate the duplicate LineSegments you find. This may be confusing at first sight, however it's quite simple. I learnt from *lilsweetcaligula*'s answer in
 [*forum discussion*](https://www.coursera.org/learn/algorithms-part1/discussions/threads/5d_M1A-lEeiH3hLxl_H-4A/replies/OXr2msY7EeibyxL0G41HFA): 
 > "The basic idea behind elimination of duplicate segments turned out to be, in fact, deceivingly very simple: make sure you draw segments between min-points and max-points (min-max based on the natural order), and ignore all the points on the line between them."

So, to be specific, we just need to ensure every line we add is start or end with the current origin point p (I choose to begin with).
1. For Brute case, it's easy since what we do is just sort (by natural order) then iterate in order.
2. For Fast case, I used a support array. Every time when a line is ready to check, put the four or more points into that array then sort them (here exists a problem which I will mention below), then I can compare the min point with the current origin point to decide whether to add it. 
```java
if (flag) {
             for (int k = 1; k <= count; k++) {
                 sup[k] = clone[j-k];
             }
             sup[0] = origin;
             // Arrays.sort(sup, 0, count+1); too much cost
             findMinMax(sup, 0, count+1);

             // avoid duplicate
             if (sup[min].compareTo(origin) == 0) {
                 segAdd(new LineSegment(sup[min], sup[max]));
             }
         }
```
After solving this problem, I got 80 point which is just a passing point. Hence, the following part -- details is what I think the more crucial and significant one to handle with and to get full score.
1. First, `the corner case check`. The checking order and different situation (e.g when there are less than 4 points). Also, a small trap is that when doing the duplicate points check, I combined it with the null point check at the beginning. However, this may fail some test since you will call on the null point and then throw the **NullPointerException** instead of **IllegalArgumentException**. So I just use two for loops to solve the problem. (PS I used *HashSet* for checking at first, it's easier overall but not permitted in the assignment requirement.)
2. Second, `the implementation details`. 
   1. *`public LineSegment[] segments()`* method. This method requires you to return all the line segments you find, so it's intuitive to use another container to hold every line. I tried *ArrayList* at beginning, but then I found it's not permitted, so I decided to use *linkedlist* (by creating inner class 'segNode' and method 'segAdd'). It worked well.
   2. *`the vertical case`*. Since in the Fast method you need to sort by slope first, this makes all the points that are perpendicular to the current origin point in the end of the array. So if it can be added to a line, you should ensure your loop won't miss it. I used another if condition to solve this problem.
   3. There are still some other details that are worth paying attention to, I won't go on about that.
3. Finally, I'd like to point out the importance of *output report* on your code. I once got 97.56%, after studied carefully on the report I found that it's one of the timing test that I have failed. It is very clear that it's the *compareTo()* that is beyond the range. But furthermore, we can find that the order of growth of it is not very large which really helps to lock the target -- the *Arrays.sort* I used in sup array to avoid duplicate line (as mentioned above). To handle this, I just wrote a new *findMinMax* method to just find the min and max points in one for loop. Then it worked and I got 100 points.
