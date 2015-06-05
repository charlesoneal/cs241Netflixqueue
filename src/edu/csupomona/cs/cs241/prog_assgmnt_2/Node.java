/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #2
 *
 * In this assignment, the goal is to recreate Netflix's instant queue and
 * improve upon it.  It also illustrates the use of a priority queue and a
 * red black tree.  Users will now have the ability to add a priority to the
 * movies that they place into the Netflix queue.  The movies are stored in
 * a red black tree an added to the queue from a sorted list to titles.
 * 
 * The program is also persistent and extensible.  
 *
 * Team #N / Charles O'Neal
 *   
 */
package edu.csupomona.cs.cs241.prog_assgmnt_2;

/**
 *This class is a {@link Node} to be used in the creation of a binary tree.
 * It is set up to contain all that is needed to represent be a part of the 
 * construction of a heap or a priority queue represented in a heap. 
 * 
 * @author Charles O'Neal
 */
public class Node<V extends Comparable<V>> implements java.io.Serializable{
  
  /**
   * This variable is of a generic type to hold data to be placed in a tree.
   * It's type is to be determined at compile time.  It is also the key value
   * for the tree.
   */
    protected V data;
    
    /**
    * This variable allows the {@link Node} to maintain a link with the 
    * {@link Node}'s left child.  It is essential for tree traversal.
    */
    protected Node leftChild;
    
    /**
    * This variable allows the {@link Node} to maintain a link with the 
    * {@link Node}'s right child.  It is essential for tree traversal.
    */
    protected Node rightChild;
    
    /**
    * This variable to allow the node to maintain link with the {@link Node}'s
    * parent.  This variable is essential for tree traversal.  It also allows the 
    * node to keep track of whether it is the parent's left or right child.
    */
    protected Node parent;
    
    /**
     * This variable holds the {@code int} value of the node number.  It is 
     * used to facilitate tree traversal and help maintain the integrity of the
     * tree.  It is used by other methods to accomplish their tasks.
     */
    protected int nodeNumber;
    
    /**
     * This variable is used as a key value in implementing a priority queue.
     * It allows for the {@link Node} to be sorted and placed into a queue by
     * priority.
     */
    protected int priority;
    
    /**
     * The default constructor for the {@link Node} class.  It sets the data
     * of {@code this} {@link Node} to the data parameter.  It also sets the
     * left child, right child, and parent to {@code null}.  The node number
     * is initialized to zero.  It also sets {@code this} {@link Node}'s 
     * priority to the parameter passed in.
     * 
     * @param data The data to be stored in the {@link Node}.
     * @param priority The priority of the {@link Node}.
     */
    public Node(V data, int priority) {
      this.data = data;
      leftChild = null;
      rightChild = null;
      parent = null;
      nodeNumber = 0;
      this.priority = priority;
    }
    
    /**
     * The second constructor for the {@link Node} class.  It sets both
     * the left and right child to {@code null}.  It sets {@code this} 
     * {@link Node}'s data to the value of the parameter. Sets the node
     * number to zero.
     * 
     * @param data The data to be stored in the {@link Node}.
     */
    public Node(V data) {
      this.data = data;
      leftChild = null;
      rightChild = null;
      nodeNumber = 0;
    }
  }
