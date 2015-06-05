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
 *
 * @author Charles O'Neal
 */


/**
 * This class represents the nodes for the {@link RBTree}.  The Node2
 * contains a left child and a right child as well as a parent.  It also has
 * a parameter for a value to be passed in as well as a key.  The Node2 has
 * a boolean variable for the color, as color is essential to maintaining the 
 * integrity of the RedBlackTree.  
 * 
 * @author Charles O'Neal
 * @param <V> The generic variable associated with the type of data that the 
 * RedBlackTree is storing.
 */

public class Node2<V extends Comparable<V>> implements java.io.Serializable {
  
  /**
   * This variable to allow the node to maintain link with the {@link Node2}'s
   * parent.  This variable is essential for tree traversal.  It also allows the 
   * node to keep track of whether it is the parent's left or right child.
   */
  protected Node2 parent;
  
  /**
   * This variable allows the {@link Node2} to maintain a link with the 
   * {@link Node2}'s left child.  It is essential for tree traversal.
   */
  protected Node2 leftChild;
  
  /**
   * This variable allows the {@link Node2} to maintain a link with the 
   * {@link Node2}'s right child.  It is essential for tree traversal.
   */
  protected Node2 rightChild;
  
  /**
   * This variable holds the title of the movie to be inserted into the 
   * {@link Node}. All movies have a title.  This is also the key value for the
   * {@link RBTree}.
   */
  protected V title;
  
  /**
   * This variable holds the genre of the movie to be inserted into the 
   * {@link Node2}.  Movies are classified by genres.  
   */
  protected V genre;
  
  /**
   * This variable holds the actors of the movie to be inserted into the
   * {@link Node2}.  Actors are essential to movies and movies are often billed
   * by the actors in them. 
   */
  protected V actors;
  
  /**
   * This variable holds the director's name of the movie to be inserted into
   * the {@link Node2}.  Directors are an important part of the movie making 
   * process.
   */
  protected V director;
  
  /**
   * This is a {@code boolean} variable that holds the color of the 
   * {@link Node2}.  The value is initially set to {@code false} for black. 
   * The value is changed to {@code true} to represent red.
   */
  boolean isRed;
  
  
  /**
   * The default constructor for {@link Node2}. When the constructor is called,
   * it assigns a title, actors, director and genre to {@code this} 
   * {@link Node2}.  It also initially sets the {@link #isRed} value to
   * {@code false}.
   * 
   * @param title The title of the movie.
   * @param actors The actors in the movie.
   * @param director The director of the movie.
   * @param genre  The genre of the movie.
   */
  public Node2(V title, V actors, V director, V genre) {
    this.title = title;
    this.actors = actors;
    this.director = director;
    this.genre = genre;
    leftChild = null;
    rightChild = null;
    parent = null;
    isRed = false;
  }
}
