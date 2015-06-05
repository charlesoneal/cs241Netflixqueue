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
 * A generic interface for the creation of a heap class.  This interface
 * was given by Professor Ridr&iacute;guez in order to facilitate the creation
 * of a heap to be implemented in the project.
 */
public interface Heap<V extends Comparable<V>> {

  public void add(V value);

  public V[] toArray(V[] array);

  public void remove(V value);

  public void fromArray(V[] array);

  public V[] getSortedContents(V[] array);

}

