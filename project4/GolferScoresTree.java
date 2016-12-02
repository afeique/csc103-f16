// File: GolferScoresTree.java

import java.io.*;
import java.util.Scanner;
import java.util.regex.*;

/**
* Primary driver for interacting with and manipulating a database of golfers
* via console. The data from the database is stored as a text file on disk. 
* Golfer data is loaded from the text file into a binary tree structure for 
* manipulation during execution of the program.
* 
* This is a static class which contains static methods for printing a menu, 
* handling menu inputs, reading golfers from a text file and adding them to 
* a binary tree, and storing the entire binary tree to a text file.
*
* @author Afeique Sheikh, Sydney McDaniel, Yusen Jiang
*/
public class GolferScoresTree 
{
   private static Scanner scanner = new Scanner(System.in);

   /**
   * Main method containing main loop for console application.
   * @param args
   *   command-line arguments, unused
   **/
   public static void main(String[] args) 
   {
      int choice, rounds, handicap, score;
      double avg;
      Golfer g;
      String input, name;

      // default database file
      String dbFilename = "golferinfo.txt";
      // load initial db from default db file
      TreeBag<Golfer> db = GolferScoresTree.loadDbFromFile(dbFilename);

      // main loop with label so we can break out of it
      loop:
      while(true) {
         // print the menu
         System.out.print("\n\n");
         System.out.println("Main Menu");
         System.out.println("------------------------------------------------------");
         System.out.println("Current database file: "+ dbFilename +"\n");
         System.out.println("0.  Load database from current file");
         System.out.println("1.  Display all golfer stats in order");
         System.out.println("2.  Display all golfers as a tree");
         System.out.println("3.  Find a specific golfer and display their stats");
         System.out.println("4.  Update a golfer's stats by adding a score" );
         System.out.println("5.  Remove a golfer from the database");
         System.out.println("6.  Add a golfer to the database");
         System.out.println("7.  Quit and save database to current file");
         System.out.println("8.  Save database to current file");
         System.out.println("9.  Change database file");
         System.out.println("------------------------------------------------------");
         System.out.print("\n");

         
         // get menu option choice from user
         choice = -1;
         // keep asking the user to input values until a valid menu option is specified
         while (choice == -1) {
            // prompt user
            System.out.print("Select a numeric menu option: ");

            // if the user did not input an int, then their input is invalid
            if (!GolferScoresTree.scanner.hasNextInt()) {
               // scan their input and discard it (do nothing with it)
               GolferScoresTree.scanner.next();
               // indicate what a valid input is
               System.out.println("Positive integers in range [0-9] only\n");
            }
            // else, if the user input an int
            else {
               // store the user input as the choice
               choice = GolferScoresTree.scanner.nextInt();
               // if the choice is not valid and does not fall in the [0-9] range,
               if (choice < 0 || choice > 9) {
                  // reset their choice so the while loop continues
                  choice = -1;
                  // indicate the user selected a nonexistent menu option
                  System.out.println("Invalid option\n");
               }
            }
         }

         System.out.print("\n");

         // perform the action corresponding to the user's menu choice
         switch (choice) {
            // load database from file
            case 0:
               GolferScoresTree.loadDbFromFile(dbFilename);
               break;

            // display the database in order
            case 1:
               db.display();
               break;

            // display the database as a tree
            case 2:
               db.displayAsTree();
               break;

            // search for a golfer by name and display their stats
            case 3:
               // have user input golfer's name
               name = GolferScoresTree.inputName();
               System.out.print("\n");

               // search the database for the golfer and retrieve the golfer object ref
               g = db.retrieve(new Golfer(name));

               System.out.print("Golfer '"+ name +"' ");
               // if the object ref is not null, the golfer was found: print their stats
               if (g != null)
                  System.out.print("found in database:\n"+ g +"\n\n");
               else
                  System.out.print("not found in database");
               break;

            // add a score to an existing golfer and re-compute their average score
            case 4:
               // have user input golfer's name
               name = GolferScoresTree.inputName();
               System.out.print("\n");

               // search the database for the golfer and retrieve the golfer object ref
               g = db.retrieve(new Golfer(name));

               System.out.print("Golfer '"+ name +"' ");
               // if the object ref is not null, the golfer was found
               if (g != null) {
                  // print out golfer's current stats
                  System.out.print("found in database:\n"+ g +"\n\n");

                  // have user input the score to be added
                  score = GolferScoresTree.inputInt("Score to add: ");

                  // add the score to the found golfer and recompute their average score
                  g.addScore(score);

                  // print out golfer's new stats
                  System.out.print("\n\nAdded score "+ score +" to golfer '"+ name +"':\n"+ g);
               }
               else
                  System.out.print("not found in database");

               break;

            // remove a golfer from the database
            case 5:
               // have user input golfer's name
               name = GolferScoresTree.inputName();
               System.out.print("\n");

               // search db and remove golfer, true if successful
               Boolean success = db.remove(new Golfer(name));

               // indicate result of removal operation
               System.out.print("Golfer '"+ name +"' ");
               if (success)
                  System.out.print("removed from database\n");
               else
                  System.out.print("not found in database, no action taken\n");

               break;

            // add a new golfer to the database
            case 6:
               // have user input new golfer's name
               name = GolferScoresTree.inputName();
               System.out.print("\n");

               // have user input number of rounds played by new golfer
               // must be an int >= 0
               rounds = GolferScoresTree.inputInt("Number of rounds played: ");
               System.out.print("\n");

               // have user input new golfer's handicap
               // must be an int >= 0
               handicap = GolferScoresTree.inputInt("Player handicap: ");
               System.out.print("\n");

               // have user input new golfer's average score
               // must be a number >= 0
               avg = GolferScoresTree.inputDouble("Average score: ");
               System.out.print("\n");

               System.out.print("\n");

               // create new golfer object using input stats
               g = new Golfer(name, rounds, handicap, avg);
               // add new golfer object to database sorted by name
               db.add(g);
               // indicate success by regurgitating new golfer object
               System.out.println("Added new golfer:\n"+ g);
               break;

            // save database to file and quit
            case 7:
               GolferScoresTree.saveDbToFile(db, dbFilename);
               System.out.println("\nGoodbye.");
               // break using label to quit main loop
               break loop;

            // save database to file, then continue working
            case 8:
               GolferScoresTree.saveDbToFile(db, dbFilename);
               break;

            // change the database file that will be used for saving/loading
            case 9:
               // have user input new database filename
               dbFilename = GolferScoresTree.inputString("Enter database filename: ");
               System.out.print("\n");
               break;

            // we should never reach here: we validate the user's input outside the switch
            // this is a catch-all
            default:
               System.out.println("Invalid option\n");
               break;
         }
      }
   }

   /**
   * Prompt the user to input a golfer's name.
   * Keep prompting the user until a valid name is input.
   * @return golfer name input by user
   **/
   public static String inputName() {
      return GolferScoresTree.inputString("Golfer name: ");
   }

   /**
   * Prompt the user to input a string.
   * Keep prompting the user until a non-empty string is input.
   * @param prompt
   *   prompt to display to the user when requesting input
   * @return string input by user
   **/
   public static String inputString(String prompt) {
      //String errorMsg = "Must be a non-empty string\n";

      /*
      String input = "";
      while (input == "") {
         System.out.print(prompt);
         input = GolferScoresTree.scanner.next().trim();
         if (input == "")
            System.out.println(errorMsg);
      }
     */

      // scanner automatically ignores empty / all-whitespace inputs
      System.out.print(prompt);
      // trim whitespace off ends of input
      String input = GolferScoresTree.scanner.next().trim();

      return input;
   }

   /**
   * Prompt the user to input an int.
   * Keep prompting the user until a positive int >= 0 is input.
   * @param prompt
   *   prompt to display to the user when requesting input
   * @return int input by user
   **/
   public static int inputInt(String prompt) {
      String errorMsg = "Must be a positive integer >= 0\n";

      int input = -1;
      // keep prompting user until int >= 0 is input
      while (input < 0) {
         // print prompt
         System.out.print(prompt);

         // if the user input an int
         if (GolferScoresTree.scanner.hasNextInt()) {
            // scan the user input
            input = GolferScoresTree.scanner.nextInt();

            // if the input is < 0, it is invalid
            if (input < 0) {
               // print error message defining a valid value
               System.out.println(errorMsg);
               // loops and prompt user for another input
            }
         }
         // else if the user did not input an int, input is invalid
         else {
            System.out.println(errorMsg);
         }
      }

      return input;
   }

   /**
   * Prompt the user to input a double.
   * Keep prompting the user until a positive floating-point value >= 0 is input.
   * @param prompt
   *   prompt to display to the user when requesting input
   * @return double input by user
   **/
   public static double inputDouble(String prompt) {
      String errorMsg = "Must be a positive floating-point value >= 0\n";

      double input = -1;
      // keep prompting user until double >= 0 is input
      while (input < 0) {
         // print prompt
         System.out.print(prompt);

         // if the user input a double
         if (GolferScoresTree.scanner.hasNextDouble()) {
            // scan the user input
            input = GolferScoresTree.scanner.nextDouble();

            // if the input is < 0, it is invalid
            if (input < 0) {
               // print error message defining a valid value
               System.out.println(errorMsg);
               // loops and prompt user for another input
            }
         }
         // else if the user did not input an int, input is invalid
         else {
            System.out.println(errorMsg);
         }
      }

      return input;
   }

   /**
   * Load a database from the specified text file in the same directory as the application.
   * @param dbFilename
   *   name of the database file in the application directory
   * @return database populated with entries in the text file
   * <dt><b>Precondition:</b><dd>
   *   file specified by <code>dbFilename</code> exists in the application directory
   * <dt><b>Postcondition:</b><dd>
   *   database object is created and returned using entries from the specified file
   **/
   public static TreeBag<Golfer> loadDbFromFile(String dbFilename) 
   {
      // used to store lines read from the database file
      String line;
      // create a new empty database
      TreeBag<Golfer> db = new TreeBag<Golfer>();
      // compile a regex pattern for parsing lines from the database file
      Pattern p = Pattern.compile("([\\w\\s-]+)(\\d+)\\s+(\\d+)\\s+(\\d+\\.?\\d*)");
      // create file handle for the filename specified (in current directory)
      File f = new File("."+ File.separator + dbFilename);

      // if the filename specified exists in the current directory,
      if (f.exists()) {
         System.out.println("\nLoading golfers from database file '"+ dbFilename +"'...");

         // try loading from the database file
         try {
            // create a new file reader using the file handle
            FileReader fr = new FileReader(f);
            // buffer the file reader
            BufferedReader buf = new BufferedReader(fr);

            // keep reading lines from the file buffer until we reach the end of file
            while ( (line = buf.readLine()) != null ) {
               // trim whitespace off the lines
               line = line.trim();
               //System.out.println("Read line:\n"+ line +"\n");

               // create a matcher object using the pre-compiled regex pattern
               Matcher m = p.matcher(line);
               // if the matcher object found a match on the current line,
               if (m.find()) {
                  // populate golfer data using match groups
                  String name = m.group(1);
                  int rounds = Integer.valueOf(m.group(2));
                  int handicap = Integer.valueOf(m.group(3));
                  double avg =  Double.valueOf(m.group(4));
                  // create a new golfer object
                  Golfer g = new Golfer(name, rounds, handicap, avg);
                  // add new golfer to the database
                  db.add(g);
                  /*
                  System.out.println("Added golfer to database:\n"+ g +"\n");
                  System.out.println("Current database:\n"+ db);
                  System.out.println("Tree view:");
                  db.displayAsTree();
                  System.out.println("\n");
                 */
               }
            }

            // at the end of the file, close the buffer
            buf.close();
         }
         // exception: specified file does not exist
         catch(FileNotFoundException e) {
            // print stack trace for debugging
            e.printStackTrace();
            System.out.println("File '"+ dbFilename +"' not found: "+ e);
         }
         // exception: I/O error when reading from file
         catch(IOException e) {
            // print stack trace for debugging
            e.printStackTrace();
            System.out.println("Error reading file '"+ dbFilename +"': "+ e);
         }
      }
      else
         System.out.println("Database file '"+ dbFilename +"' not found");
      
      // return the newly compiled database
      return db;
   }

   /**
   * Write a database to the specified text file in the same directory as the application.
   * @param db
   *   database object whose values should be written to the text file
   * @param dbFilename
   *   name of the database file in the application directory
   * <dt><b>Precondition:</b><dd>
   *   file specified by <code>dbFilename</code> exists in the application directory
   * <dt><b>Postcondition:</b><dd>
   *   specified text file is either created or overwritten with database entries
   **/
   public static void saveDbToFile(TreeBag<Golfer> db, String dbFilename) 
   {
      BufferedWriter buf = null;
      // create file handle for the specified filename (in current directory)
      File f = new File("."+ File.separator + dbFilename);

      System.out.println("Saving database to file '"+ dbFilename +"'...");

      // try creating the file if it doesn't exist
      try {
         // if the file doesn't exist, create it (in current directory)
         if ( ! f.exists() ) {
            f.createNewFile();
         }
      }
      // catch any exceptions generated by trying to create the file
      // can be IOException or SecurityException
      catch (Exception e) {
         e.printStackTrace();
         System.out.println("Problem creating file '"+ dbFilename +"': "+ e);
      }

      // try writing to the file once it exists
      try {
         // create a new file writer and buffer it
         FileWriter fw = new FileWriter(f);
         buf = new BufferedWriter(fw);
         
         // use the buffer to write the entire database to the file at once
         buf.write(db.toString());
      }
      // exception: I/O error writing to file
      catch(IOException e) {
         // print stack trace for debugging
         e.printStackTrace();
         System.out.println("Problem writing to file '"+ dbFilename +"': "+ e);
      }
      // finally: perform cleanup by closing the buffer if it is still open
      // this executes even if an exception is thrown
      finally {
         // if the buffer is open
         if (buf != null) {
            // try closing it
            try {
               buf.close();
            }
            // exception: I/O error closing the buffer
            catch (IOException e) {
               // stack trace for debugging
               e.printStackTrace();
               System.out.println("Error closing buffer to file '"+ dbFilename +"': "+ e);
            }
         }
      }

      System.out.println("Save complete.");
   }
}