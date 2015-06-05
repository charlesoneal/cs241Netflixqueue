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
 *This class creates a red black tree.  A red black tree is a binary tree that
 * is self balancing.  It prevents the binary tree from degrading into a 
 * linked list.  At worst, one side of the tree will be 2log(n+1) larger than 
 * other side of the tree.  A red black tree has five invariants that must
 * be maintained in order to function properly:
 * 1. Every node is either red or black.
 * 2. The root is always black.
 * 3. Leaves are black and contain no data.
 * 4. If a node is red, then both of its children are black.
 * 5. For every node, the simple path to any other node contains the same
 * number of black nodes.
 * 
 * This red black tree contains five {@link Node2}s to facilitate its 
 * implementation.
 *
 * @author Charles O'Neal
 */
public class RBTree<V extends Comparable<V>> implements java.io.Serializable {
  
  /**
   * This {@link Node2} represents the root of the tree.  It is th {@link Node2}
   * with the highest value.  It is the top of the tree and all nodes are
   * descendants of this {@link Node2}.
   */
  private Node2 root;
  
  /**
   * This {@link Node2} represents all leaves of the tree.  All values are set
   * to {@coe null}.  This is a sentinel {@link Node2}.  The parent of the 
   * root is also assigned to this {@link Node2} in order to facilitate
   * the maintenance of the red black tree.
   */
  private Node2 nil = new Node2(null, null, null, null);
  
  /**
   * This {@link Node2} is a helper node used in the comparison of 
   * {@link Node2}s in the {@link RBTree} it is used in conjunction with 
   * {@link y} in order to traverse the tree and assign and swap values.
   */
  private Node2 x;
  
  /**
   * This {@link Node2} is a helper node used in the comparison of 
   * {@link Node2}s in the {@link RBTree} it is used in conjunction with 
   * {@link x} in order to traverse the tree and assign and swap values.
   */
  private Node2 y;
  
  /**
   * This {@link Node2} is a helper node used in the comparison of 
   * {@link Node2}s in the {@link RBTree}.  It represents the sibling of the
   * {@link Node2} that is the focus.  
   */
  private Node2 sibling;

  /**
   *The default constructor of the {@link RBTree}.  When called, it assigns
   * both the root an its parent to the {@link #nil} sentinel {@link Node2}
   */
  public RBTree() {
    root = nil;
    root.parent = nil;
  }

  /**
   * This method finds the max value in the tree.  It recursively traverses
   * the tree to find the rightmost {@link Node2} in the {@link RBTree}.
   * 
   * @param x The {@link Node2} to start with.
   * @return Returns the {@link Node2} with the max value. 
   */
  public Node2 max(Node2 x) {
    if (x.rightChild == nil) {
      return x;
    } else {
      return max(x.rightChild);
    }
  }

  /**
   * This method finds the inorder predecessor of a {@link Node2}.  In order to
   * accomplish this, it calls {@link #max(Node2)}.
   * 
   * @param x The {@link Node2} to start with.
   * @return Returns the {@link Node2} that is the inorder predecessor.
   */
  public Node2 predecessor(Node2 x) {
    if (x.leftChild != nil) {
      return max(x.leftChild);
    }
    y = x.parent;
    while (y != nil && x == y.rightChild) {
      x = y;
      y = y.parent;
    }
    return y;
  }

  /**
   * This method is a helper method for the {@link RBTree}.  In order to 
   * maintain the integrity of the tree, rotations must be done on certain
   * {@link Node2}s.  The rotation is intended to help restore the invariants
   * of the {@link RBTree}.
   * @param x The node to perform the left rotation on.
   */
  public void leftRotate(Node2 x) {
    y = x.rightChild;
    x.rightChild = y.leftChild;

    if (y.leftChild != nil) {
      y.leftChild.parent = x;
    }

    y.parent = x.parent;

    if (x.parent == nil) {
      root = y;
    } else if (x == x.parent.leftChild) {
      x.parent.leftChild = y;
    } else {
      x.parent.rightChild = y;
    }

    y.leftChild = x;
    x.parent = y;

  }

  /**
   * This method is a helper method for the {@link RBTree}.  In order to 
   * maintain the integrity of the tree, rotations must be done on certain
   * {@link Node2}s.  The rotation is intended to help restore the invariants
   * of the {@link RBTree}.
   * @param x The node to perform the right rotation on.
   */
  public void rightRotate(Node2 x) {
    y = x.leftChild;
    x.leftChild = y.rightChild;

    if (y.rightChild != nil) {
      y.rightChild.parent = x;
    }

    y.parent = x.parent;

    if (x.parent == nil) {
      root = y;
    } else if (x == x.parent.rightChild) {
      x.parent.rightChild = y;
    } else {
      x.parent.leftChild = y;
    }

    y.rightChild = x;
    x.parent = y;
  }

  /**
   * This method adds a {@link Node2} to the {@link RBTree}.  The method also
   * sets the {@link Node2}'s values to the parameters passed to the method.
   * Adding a node has the potential to break properties of the tree.  In order
   * to fix this, this method calls {@link #addFix(Node2)}.  The color of the
   * added {@link Node2} is set to red.  
   * 
   * @param title The title of the movie.
   * @param actors The actors in the movie.
   * @param director The director of the movie.
   * @param genre  The genre of the movie.
   */
  public void add(V title, V actors, V director, V genre) {
    x = root;
    y = nil;
    Node2 z = new Node2(title, actors, director, genre);

    while (x != nil) {

      y = x;
      if (z.title.compareTo(x.title) < 0) {
        x = x.leftChild;
      } else {
        x = x.rightChild;
      }
    }

    z.parent = y;

    if (y == nil) {
      root = z;
      root.parent = nil;
    } else if (z.title.compareTo(y.title) < 0) {
      y.leftChild = z;
    } else {
      y.rightChild = z;
    }

    z.leftChild = nil;
    z.rightChild = nil;


    addFix(z);

  }

  /**
   * This method fixes the properties broken by adding {@link Node2} to the 
   * tree.  Adding a node can break properties tow and four.  There are three
   * cases in which need to be addressed. Each case has a mirror version as 
   * well.
   * 1. The {@link Node2}'s uncle is red.
   * 2. The {@link Node2}'s uncle is black and {@code this} {@link Node2} is
   * a right child.
   * 3. The {@link Node2}'s uncle is black, but {@code this} {@link Node2} is
   * a left child.
   * 
   * This method address all three cases and restores the integrity of 
   * the {@link RBTree}.
   * 
   * @param z The {@link Node2} to begin the fix on.
   */
  public void addFix(Node2 z) {
    while (z.parent.isRed == true) {
      if (z.parent == z.parent.parent.leftChild) {
        y = z.parent.parent.rightChild;
        if (y.isRed = true) {
          z.parent.isRed = false;
          y.isRed = false;
          z.parent.parent.isRed = true;
          z = z.parent.parent;
        } else {
          if (z == z.parent.rightChild) {
            z = z.parent;
            leftRotate(z);
          }

          z.parent.isRed = false;
          z.parent.parent.isRed = true;
          rightRotate(z.parent.parent);
        }
      } else {
        y = z.parent.parent.leftChild;
        if (y.isRed = true) {
          z.parent.isRed = false;
          y.isRed = false;
          z.parent.parent.isRed = true;
          z = z.parent.parent;
        } else {
          if (z == z.parent.leftChild) {
            z = z.parent;
            rightRotate(z);
          }

          z.parent.isRed = false;
          z.parent.parent.isRed = true;
          leftRotate(z.parent.parent);
        }
      }
    }

    root.isRed = false;
  }

  /**
   * This method is a helper method.  It takes a value to be deleted, and 
   * calls {@link #search(Node2, V)} and {@link #delete(Node2)}.
   * 
   * @param value The value to be deleted.
   */
  public void deleteHelp(V value) {
    Node2 n = search(root, value);
    delete(n);
  }

  /**
   * This method deletes a {@link Node2} from the {@link RBTree}.  It uses the
   * {@link predecessor(Node2)} method to find the predecessor and replaces the
   * {@link Node2} to be deleted with its predecessor.  Removal of a node
   * can cause the integrity of the red black tree to be compromised.  This
   * method calls {@link #deleteFix(Node2)} to restore the tree's integrity.
   * 
   * @param z The {@link Node2} to be deleted.
   */
  public void delete(Node2 z) {
    if (z.leftChild == nil || z.rightChild == nil) {
      y = z;
    } else {
      y = predecessor(z);
    }

    if (y.rightChild != nil) {
      x = y.rightChild;
    } else {
      x = y.leftChild;
    }

    x.parent = y.parent;

    if (y.parent == nil) {
      root = x;
    } else {
      if (y == y.parent.leftChild) {
        y.parent.leftChild = x;
      } else {
        y.parent.rightChild = x;
      }
    }

    if (y != z) {
      z.title = y.title;
      z.actors = y.actors;
      z.director = y.director;
      z.genre = y.genre;
    }

    if (y.isRed == false) {
      deleteFix(x);
    }
  }

  /**
   * This method restores the integrity of the {@link RBTree} that was 
   * compromised due to deleting a {@link Node2}.  Deletion can violate
   * invariants two, four, and five.  There are four cases which need to be 
   * fixed.
   * 1. {@code this} {@link Node2}'s sibling is red.
   * 2. {@code this} {@link Node2}'s sibling is black and both of the sibling's
   * children are black.
   * 3. {@code this} {@link Node2}'s sibling is black and the sibling's left
   * child is red and the right child is black.
   * 4. {@code this} {@link Node2}'s sibling is black and the sibling's right
   * child is black and the left child is irrelevant.
   * 
   * This method handles all four cases and their mirrors.
   * 
   * @param x The {@link Node2} to begin the fix.
   */
  public void deleteFix(Node2 x) {

    while (x != root && x.isRed == false) {
      if (x == x.parent.leftChild) {
        sibling = x.parent.rightChild;
        if (sibling.isRed == true) {
          sibling.isRed = false;
          x.parent.isRed = true;
          leftRotate(x.parent);
          sibling = x.parent.rightChild;
        }

        if (sibling.leftChild.isRed == false && sibling.rightChild.isRed == false) {
          sibling.isRed = true;
          x = x.parent;
        } else {
          if (sibling.rightChild.isRed == false) {
            sibling.leftChild.isRed = false;
            sibling.isRed = true;
            rightRotate(sibling);
            sibling = x.parent.rightChild;
          }
          sibling.isRed = x.parent.isRed;
          x.parent.isRed = false;
          sibling.rightChild.isRed = false;
          leftRotate(x.parent);
          x = root;
        }
      } else {
        sibling = x.parent.leftChild;
        if (sibling.isRed == true) {
          sibling.isRed = false;
          x.parent.isRed = true;
          rightRotate(x.parent);
          sibling = x.parent.leftChild;
        }

        if (sibling.rightChild.isRed == false && sibling.leftChild.isRed == false) {
          sibling.isRed = true;
          x = x.parent;
        } else {
          if (sibling.leftChild.isRed == false) {
            sibling.rightChild.isRed = false;
            sibling.isRed = true;
            leftRotate(sibling);
            sibling = x.parent.rightChild;
          }
          sibling.isRed = x.parent.isRed;
          x.parent.isRed = false;
          sibling.leftChild.isRed = false;
          rightRotate(x.parent);
          x = root;
        }
      }
    }
  }

  /**
   * This method traverses the tree in order to print the nodes for display.
   * The {@link Node2}s are printed in sorted order.  This method calls
   * {@link #printNode(Node2)}.
   * 
   * @param n The {@link Node2} to begin with.
   */
  public void inOrder(Node2 n) {
    if (n.leftChild != nil && n != nil) {
      inOrder(n.leftChild);
    }
    if (n == nil) {
      System.out.println("\nList is empty\n");
    } else {
      printNode(n);
    }

    if (n.rightChild != nil && n != nil) {
      inOrder(n.rightChild);
    }
  }

  /**
   * This method searches the {@link RBTree} in order to find and return a
   * {@link Node2} that is requested. It is recursive.
   * 
   * @param n The {@link Node2} to start with.
   * @param name The name of the movie.
   * @return  Returns {@link Node2} requested.
   */
  public Node2 search(Node2 n, V name) {
    if (n == nil || n.title.compareTo(name) == 0) {
      return n;
    }
    if (n.title.compareTo(name) < 0) {
      return search(n.rightChild, name);
    } else {
      return search(n.leftChild, name);
    }
  }

  /**
   * This method prints the {@link Node2} for display.  It prints the
   * title, director, actors, and genre of the movie.
   * 
   * @param n The {@link Node2} to be printed.
   */
  public void printNode(Node2 n) {

    System.out.println("Title: " + n.title + " Directed by: " + n.director);
    System.out.println("Actors: " + n.actors + " Genre: " + n.genre + "\n");
  }

  /**
   * This method is a helper method to facilitate the displaying of the movie.
   * It calls {@link #inOrder(Node2)}.
   */
  public void print() {
    inOrder(root);
  }

  /**
   * This method returns a {@code true} or {@code false} value based on 
   * whether or note a node is present in the {@link RBTree}.  This method
   * calls {@link #search(Node2, V)}.
   * 
   * @param name The name of the movie in question.
   * @return Returns whether the value is present or not.
   */
  public boolean inTree(V name) {
    Node2 n;
    n = search(root, name);
    if (n == nil) {
      return false;
    } else {
      return true;
    }
  }
}
