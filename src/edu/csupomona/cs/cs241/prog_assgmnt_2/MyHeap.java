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
 * An implementation of the generic {@link Heap} interface.  It contains four 
 * {@link Node}s to be utilized by the {@link Heap} in order to generate the 
 * tree and maintain the heap property.  Heaps are essentially complete trees.
 * In order to generate a tree, each {@link Node} must have a parent, 
 * and at most two children.  
 * 
 * @author Charles O'Neal
 * @param <V> The generic parameter that is to be defined at the time of 
 * implementation and compilation. 
 */
public class MyHeap<V extends Comparable<V>>
        implements Heap<V>, java.io.Serializable {
/**
 * This {@link Node} represents the root of the tree.  It is the highest value
 * or priority in the tree. Initially, the value is set to {@code null}
 * by the constructor. All values in the tree are less in value or 
 * priority than the root.
 */
  private Node root;
  
  /**
   * This (@link Node} represents the place holder for the {@link MyHeap}.  It
   * keeps the {@link Node} count as well. It is essential in facilitating 
   * traversal of the tree and maintaining the heap property. It is initially
   * set to the {@link #root} when the tree is implemented.  
   */
  private Node placeHolder;
  
  /**
   * This {@link Node} is a second place holder for the tree.  It is used
   * to facilitate {@link #siftUp(Node)} and {@link #siftDown(Node)}.  When 
   * {@link MyHeap} is implemented, it is set to {@code null}, initially.
   */
  private Node placeHolder2;
  
  /**
   * This {@link Node} is utilized to for the currently out section of the
   * Netflix queue.  It holds the value for the {@link Node} that represents
   * the movie that is currently out.  Initially, upon implementation by the
   * constructor, it is set to {@code null}.
   */
  private Node out;

  /**
   * The default constructor for {@link myHeap}. Initially, {@link #placeHolder}
   * , {@link #out}, and {@link #placeHolder2} are set to {@code null}.  
   * {@link #placeHolder} is set to {@link #root}.
   */
  public MyHeap() {
    root = null;
    placeHolder = root;
    placeHolder2 = null;
    out = new Node(null);
  }

  /**
   * This method adds a {@link Node} to {@link MyHeap}.  Initially the tree is
   * empty and the {@link #root} is added through this method.  It also tests 
   * the conditions for which of a {@link Node}'s children are empty and where
   * to place the new {@link Node}.  The method ends by running 
   * {@link #siftUp(Node)} on the new Node in order to maintain the heap
   * property.  
   * 
   * @param value The value to be place into the {@link Node}.
   */
  @Override
  public void add(V value) {
    if (root == null) {
      root = new Node(value);
      placeHolder = root;
      root.nodeNumber = 0;
      placeHolder.nodeNumber = 0;
      System.out.println(value + " added!");
    } else {
      traversal(root, placeHolder.nodeNumber);
      if (placeHolder.nodeNumber % 2 != 0) {
        int temp = placeHolder.nodeNumber;
        placeHolder.parent.rightChild = new Node(value);
        placeHolder.parent.rightChild.parent = placeHolder.parent;
        placeHolder.parent.rightChild.nodeNumber = temp + 1;
        placeHolder = placeHolder.parent.rightChild;
        siftUp(placeHolder);
      } else {
        int temp = placeHolder.nodeNumber;
        traversal(root, ((placeHolder.nodeNumber - 1) / 2) + 1);
        placeHolder.leftChild = new Node(value);
        placeHolder.leftChild.parent = placeHolder;
        placeHolder = placeHolder.leftChild;
        placeHolder.nodeNumber = temp + 1;
        siftUp(placeHolder);
      }
      System.out.println(value + " added!");
    }

  }

/**
 * This method is the second version of the add method for the tree
 * it has two parameters instead of one.  It is intended to be used when
 * {@link MyHeap} is used as the Netflix priority queue.  It assigns value to,
 * not only the {@link #data} field for the node, as well as the 
 * {@link #priority} field.
 * 
 * @param value The value to be place into the {@link Node}.
 * @param priority The priority to be given to {@code this} {@link Node}. 
 */
  public void add(V value, int priority) {
    if (root == null && out == null) {
      out = new Node(value, priority);
    } else if (root == null && out != null) {
      root = new Node(value, priority);      
      root.nodeNumber = 0;      
      placeHolder = root;
      
      System.out.println(value + " added to queue!");
    } else {
      traversal(root, placeHolder.nodeNumber);
      if (placeHolder.nodeNumber % 2 != 0) {
        int temp = placeHolder.nodeNumber;
        placeHolder.parent.rightChild = new Node(value, priority);
        placeHolder.parent.rightChild.parent = placeHolder.parent;
        placeHolder.parent.rightChild.nodeNumber = temp + 1;
        placeHolder = placeHolder.parent.rightChild;
        siftUp(placeHolder);
      } else {
        int temp = placeHolder.nodeNumber;
        traversal(root, ((placeHolder.nodeNumber - 1) / 2) + 1);
        placeHolder.leftChild = new Node(value, priority);
        placeHolder.leftChild.parent = placeHolder;
        placeHolder = placeHolder.leftChild;
        placeHolder.nodeNumber = temp + 1;
        siftUp(placeHolder);
      }
      System.out.println(value + " added!");
    }

  }

  /**
   * This method returns {@link MyHeap} as an array.  It takes the values of the
   * {@link Node}s and places them in to an array.  Since the heap is already
   * sorted, the values are placed into the array in order and automatically
   * heapified.
   * 
   * @param array The array to hold the values from the {@link Node}.
   * @return The array of {@link Node} values.
   */
  public V[] toArray(V[] array) {

    array = (V[]) java.lang.reflect.Array.newInstance(array.getClass()
             .getComponentType(), placeHolder.nodeNumber + 1);
    for (int i = 0; i < array.length; i++) {
      traversal(root, i);
      array[i] = (V) placeHolder.data;

    }
    return array;
  }

  /**
   * This method removes {@link Node}s from the tree.  It uses 
   * {@link #traversal(Node)} to find the value that needs to be removed. Also,
   * it performs {@link #siftUp(Node)} in order to maintain the heap property.
   * @param value The value to be removed.
   */
  public void remove(V value) {

    traversal2(root, value);
    placeHolder2.data = placeHolder.data;
    siftDown(placeHolder2);
    
    if (placeHolder.parent == null)
    {
      root = null;
    } else if (placeHolder.nodeNumber % 2 != 0) {

      placeHolder.parent.leftChild = null;
      placeHolder.parent = null;
    } else {
      placeHolder.parent.rightChild = null;
      placeHolder.parent = null;
    }
    if (root != null) {
    traversal(root, placeHolder.nodeNumber - 1);
    }
  }

  /**
   * This method takes values from an array and places them in the heap.  It 
   * uses the {@link add(Value)} method.  This allows for the tree to be 
   * ordered automatically into the heap property.  
   * @param array The array of values to be placed into the heap.
   */
  @Override
  public void fromArray(V[] array) {

    root = null;

    for (int i = 0; i < array.length; i++) {
      add(array[i]);
    }
  }

  /**
   * This method takes the heap and places it into a sorted array.  It uses
   * two methods to accomplish this.  The {@link #toArray(V[])} method is
   * used to take the contents of the heap and place them in an array.  Also 
   * the {@link heapSort(V[])} method is used in order to sort the contents 
   * of the array.
   * @param array The array to hold th sorted values.
   * @return The sorted array.
   */
  @Override
  public V[] getSortedContents(V[] array) {

    array = (V[]) java.lang.reflect.Array.newInstance(array.getClass()
             .getComponentType(), placeHolder.nodeNumber + 1);
    array = toArray(array);
    array = heapSort(array);
    return array;
  }

  /**
   * This Method performs a heapSort on the array.  It puts the content of
   * the array in order from lowest to highest.  And returns the array to the
   * calling method.
   * @param array The array to be sorted.
   * @return The sorted array.
   */
  public V[] heapSort(V[] array) {
    int n = array.length - 1;
    while (n != 0) {
      array = swap(array, 0, n);
      n--;
      array = siftDown(array, n, 0);

    }
    array = heapSort(array);
    return array;
  }

  /**
   * This method traverses {@link MyHeap} in order to find a node to be used
   * by other methods. It performs an inorder traversal of the tree and sets
   * {@link #placeHolder} to the {@link Node} that is being searched for. 
   * 
   * @param n The {@link Node} to start with.
   * @param i The value of the node number to search.
   */
  public void traversal(Node n, int i) {
    if (n.nodeNumber == i) {
      placeHolder = n;
    }
    if (n.leftChild != null) {
      traversal(n.leftChild, i);
    }
    if (n.rightChild != null) {
      traversal(n.rightChild, i);
    }
  }

  /**
   *This method is a helper method used by other methods.  Its sole purpos is
   * take to values and swap them.  
   * 
   * @param a The first value to be swapped.
   * @param b The second value to be swapped.
   */
  public V[] swap(V[] array, int a, int b) {
    V temp;
    temp = array[a];
    array[a] = array[b];
    array[b] = temp;
    return array;
  }
/**
 * This method is similar to {@link #traversal}.  Instead of traversing the
 * tree by the value of the node number, it traverses the tree by the value in
 * {@link #value} of the  {@link Node} to be found.
 * 
 * @param n The {@link Node} to start with.
 * @param value The {@link #Value} to be found.
 */
  public void traversal2(Node n, V value) {
    if (n.data == value) {
      System.out.println("found\n");

      placeHolder2 = n;
    }
    if (n.leftChild != null) {
      traversal2(n.leftChild, value);
    }
    if (n.rightChild != null) {
      traversal2(n.rightChild, value);
    }

  }

  /**
   * This method compares the {@link #priority} of a {@link Node} with its 
   * parent's priority. If the priority of the parent is less, then the two 
   * Nodes are swapped
   * @param n The {@link Node} to compare with its parent.
   */
  public void siftUp(Node n) {
    if (n != root) {
      if (n.priority < n.parent.priority) {
        Node temp = new Node(null);
        temp.priority = n.priority;
        temp.data = n.data;
        n.data = n.parent.data;
        n.priority = n.parent.priority;
        n.parent.data = temp.data;
        n.parent.priority = temp.priority;
        siftUp(n.parent);
      }
    }
  }

  /**
   * This method compares the {@link #value} of an element in the array with its 
   * parent's value. If the value of the parent is greater, then the {@link #swap} 
   * method is called and the values are swapped
   * @param array The array to compare the values in
   * @param i The index to start with
   */
  public void siftUp(V[] array, int i) {
    if (i != 0) {
      if (array[i].compareTo(array[(i - 1) / 2]) > 0) {
        array = swap(array, i, (i - 1) / 2);
        siftUp(array, (i - 1) / 2);
      }
    }
  }

  /**
   * This method compares the {@link #value} of an element in the array with its 
   * two children.  It swaps the value with whichever of its children are 
   * greater
   * @param array The array to compare the values in
   * @param n The index for the left child
   * @param i The index for the right child
   * @return The resulting array.
   */
  public V[] siftDown(V[] array, int n, int i) {
    if (n == 0) {
      return array;
    } else {
      int j = (2 * i) + 1;
      if (array[j].compareTo(array[j + 1]) <= 0) {
        j++;
      }
      if (array[j].compareTo(array[i]) > 1) {
        array = swap(array, j, i);
        i = j;
        siftDown(array, n, i);
      }
      return array;
    }
  }

  /**
   * This method compares the {@link #priority} of an {@link Node} in the array 
   * with its two children.  It swaps the value with whichever of its children
   * are less 
   * @param n The {@link Node} to start with.
   */
  public void siftDown(Node n) {
    
    if (n.rightChild == null && n.leftChild == null) {
     
    } else if((n.rightChild == null) && (n.priority > n.leftChild.priority)) {
      Node temp = n.leftChild;
      n.leftChild.data = n.data;
      n.leftChild.priority = n.priority;
      n.data = temp.data;
      n.priority = temp.priority;
    } else if(n.rightChild == null && n.priority < n.leftChild.priority) {

    } else if (n.leftChild.priority < n.rightChild.priority
            && n.priority > n.leftChild.priority) {
      Node temp = n;
      n.data = n.leftChild.data;
      n.priority = n.leftChild.priority;
      n.leftChild.data = temp.data;
      n.leftChild.priority = temp.priority;
      siftDown(n.leftChild);
    } else if (n.rightChild.priority < n.leftChild.priority
            && n.priority > n.rightChild.priority) {
      Node temp = n;
      n.data = n.rightChild.data;
      n.priority = n.rightChild.priority;
      n.rightChild.data = temp.data;
      n.rightChild.priority = temp.priority;
      siftDown(n.rightChild);
    }


  }

  /**
   * This method takes an array an turns it into a {@link Heap}.  It makes sure
   * that the heap property is enforced.
   * @param array The array to turn into a heap.
   * @return The resulting heap array.
   */
  public V[] heapify(V[] array) {
    if (array.length == 0) {
      return array;
    } else {
      for (int i = array.length - 1; i >= array.length / 2; i--) {
        siftUp(array, i);
      }
      return array;
    }
  }

  /**
   * This method performs an inorder traversal on {@link MyHeap}.  When it 
   * visits a {@link Node}, it prints it to the screen. 
   * @param n The {@link Node} to start with.
   */
  public void inOrder(Node n) {
    
    if (n == null) {
      System.out.println("\nQueue is empty\n\n");
    } else {
      String priority = "";
      switch (n.priority) {
        case 1: 
          priority = "High";
          break;
        case 2:
          priority = "Medium";
          break;
        case 3: 
          priority = "Low";
          break;
    }
      System.out.println("\"" +n.data + "\" Priority: " + priority);
    }
    if (n.leftChild != null && n != null) {
      inOrder(n.leftChild);
    }

    if (n.rightChild != null && n != null) {
      inOrder(n.rightChild);
    }
  }

  /**
   * This method is a helper function for the {@link inOrder(Node)} method.
   * It tests to see if the heap is empty and calls {@link inOrder(Node)} if
   * the heap is not empty.
   */
  public void helper() {
    if (root == null) {
      System.out.println("Queue is empty\n");
    } else {
      
      System.out.println("Movies in the queue:\n");
      inOrder(root);
    }
  }

  /**
   * This method prints the value of the {@link #currentlyOut()} {@link Node}. 
   */
  public void currentlyOut() {
    if (out == null) {
      System.out.println("None currently out.\n");
    } else {
      System.out.println("Currently out: " + out.data + "\n");
    }


  }

  /**
   * This method is a helper method for {@link remove(Node)}.  It checks to see
   * if the heap is empty and if {@link #currentlyOut()} is {@code null};
   * If necessary, it calls {@link remove(Node)}.
   */
  public void removeHelp() {
    
    if(root != null) {
      if (out == null) {
        out = new Node(root.data); 
      }
    out.data = root.data;
    remove((V) root.data);
    } else {
      out = null;
      System.out.println("Queue is empty.  Please go to main menu.");
      System.out.println("and add movies to the queue.\n");
    }
  }
}