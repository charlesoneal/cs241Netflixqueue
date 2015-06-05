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


import java.util.Scanner;
import java.io.*;
import java.util.Locale;

public class EduCsupomonaCsCs241Prog_assgmnt_2 {

  /**
  * This is the main class for the assignment.  It implements both the 
  * RBTree and the MyHeap classes.  It also provides the user
  * interface for the user to interact with the program.  It is a text based
  * user interface with menus that allows the user to run through the options 
  * and add movies to both the queue and the movies list.
  * @param args the command line arguments
  */
  public static void main(String[] args) {

    Scanner kb = new Scanner(System.in);
    MyHeap<String> h = new MyHeap();
    RBTree<String> t = new RBTree();

    /**
     * Opening the files that store the tree and heap.
     * They are stored in two different files as serializable data.  
     */
    try {

      
      FileInputStream fis = new FileInputStream("pqueue.dat");
      ObjectInputStream ois = new ObjectInputStream(fis);

      h = (MyHeap) ois.readObject();

      FileInputStream fis2 = new FileInputStream("tree.dat");
      ObjectInputStream ois2 = new ObjectInputStream(fis2);

      t = (RBTree) ois2.readObject();





    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Error");
      System.out.print(e.getMessage());

    }

    System.out.println("Welcome to the improved version of Netflix queue\n");
    
    int choice = -1;
    do {
      
      System.out.println("Please make a selection:\n");
      System.out.println("1. Movies Library");
      System.out.println("2. Movies Queue");
      System.out.println("3. Exit Program\n");

      while (!kb.hasNextInt()) {
        kb.next();
        System.out.println("\nPlease enter a number from 1 to 3\n");
      }

      choice = kb.nextInt();

      if (choice < 1 || choice > 3) {
        System.out.println("Invalid choice! Please select either 1, 2, or 3\n");

      }

      if (choice == 1) {
        int choice2;
        do {
          t.print();

          System.out.println("Please Enter a choice: \n");
          System.out.println("1. Add movie to queue");
          System.out.println("2. Add new movie to library");
          System.out.println("3. Remove movie from library");
          System.out.println("4. Return to main menu\n\n");

          while (!kb.hasNextInt()) {
            kb.next();
            System.out.println("\nPlease enter a number from 1 to 3\n");
          }

          choice2 = kb.nextInt();

          if (choice2 < 1 || choice2 > 4) {
            System.out.println("\nInvalid choice! Please select from 1 to 4\n");
          }

          if (choice2 == 1) {


            t.print();
            System.out.println("Enther the name of the movie"
                    + " to add to the queue");
            kb.nextLine();
            String name = kb.nextLine();
            boolean result = t.inTree(name);

            if (result) {

              
              int priority = 0;
              while (priority < 1 || priority > 3) {
                System.out.println("\nSelect priority(1 high, 2 med, 3 low): ");
                while (!kb.hasNextInt()) {
                  kb.next();
                  System.out.println("\nPlease enter a number from 1 to 3\n");
                  
                }
                priority = kb.nextInt();
              }
              h.add(name, priority);
              System.out.println(name + " has been added to the queue\n");
            } else {
              System.out.println(name + " is not in the Movies list.  "
                      + "Please add the movie to the list before "
                      + "adding to the Movies queue\n");
            }

          }

          if (choice2 == 2) {
            System.out.print("Enter the name of the movie to add to library: ");
            kb.nextLine();
            String title = kb.nextLine();
            boolean result = t.inTree(title);

            if (result) {
              System.out.println(title + " already in list\n");
            } else {
              System.out.print("\nEnter actors: ");
              String actors = kb.nextLine();

              System.out.print("\nEnter director: ");
              String director = kb.nextLine();

              System.out.print("\nEnter genre: ");
              String genre = kb.nextLine();

              t.add(title, actors, director, genre);
              System.out.println("\n" + title + " added to the list.\n");
            }

          }

          if (choice2 == 3) {
            t.print();
            System.out.println("\nEnter the title to remove: ");
            kb.nextLine();
            String title = kb.nextLine();
            boolean result = t.inTree(title);

            if (result) {
              t.deleteHelp(title);
              System.out.println("\n" + title + " has been removed\n");
            } else {
              System.out.println("\n" + title + " is not in the list.\n");
            }
          }

        } while (choice2 != 4);
      }

      if (choice == 2) {
        int choice2;
        do {
          h.helper();
          h.currentlyOut();


          System.out.println("1. Send back currently out");
          System.out.println("2. Main Menu");

          while (!kb.hasNextInt()) {
            kb.next();
            System.out.println("\nPlease enter a number!");
          }

          choice2 = kb.nextInt();

          if (choice2 < 1 || choice2 > 2) {
            System.out.println("\nInvalid choice! Please select 1 or 2\n");
          }

          if (choice2 == 1) {
            h.removeHelp();
          }

        } while (choice2 != 2);
      }
    } while (choice != 3);

    System.out.println("\nThank you for using the improved version of Netflix");

    try {
      FileOutputStream fos = new FileOutputStream("pqueue.dat");
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(h);

      FileOutputStream fos2 = new FileOutputStream("tree.dat");
      ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
      oos2.writeObject(t);

      oos.close();
      oos2.close();
    } catch (Exception e) {
    }
  }
}
