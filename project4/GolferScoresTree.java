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

   public static void main(String[] args) 
   {
      int choice, rounds, handicap, score;
      double avg;
      Golfer g;
      String input, name;
      String dbFilename = "golferinfo.txt";
      TreeBag<Golfer> db = GolferScoresTree.loadDbFromFile(dbFilename);


      loop:
      while(true) {
         
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

         
         
         choice = -1;
         while (choice == -1) {
            System.out.print("Select a numeric menu option: ");
            if (!GolferScoresTree.scanner.hasNextInt()) {
               GolferScoresTree.scanner.next();
               System.out.println("Positive integers only\n");
            }
            else {
               choice = GolferScoresTree.scanner.nextInt();
               if (choice < 0 || choice > 9) {
                  choice = -1;
                  System.out.println("Invalid option\n");
               }
            }
         }

         System.out.print("\n\n");


         switch (choice) {
            case 0:
               GolferScoresTree.loadDbFromFile(dbFilename);
               break;
            case 1:
               db.display();
               break;

            case 2:
               db.displayAsTree();
               break;

            case 3:
               name = GolferScoresTree.inputName();
               System.out.print("\n");

               g = db.retrieve(new Golfer(name));
               System.out.print("Golfer '"+ name +"' ");
               if (g != null)
                  System.out.print("found in database:\n"+ g +"\n\n");
               else
                  System.out.print("not found in database");
               break;

            case 4:
               name = GolferScoresTree.inputName();
               System.out.print("\n");

               g = db.retrieve(new Golfer(name));
               System.out.print("Golfer '"+ name +"' ");
               if (g != null) {
                  System.out.print("found in database:\n"+ g +"\n\n");
                  score = GolferScoresTree.inputInt("Score to add: ");

                  g.addScore(score);
                  System.out.print("\n\nAdded score "+ score +" to golfer '"+ name +"':\n"+ g);
               }
               else
                  System.out.print("not found in database");

               break;

            case 5:
               name = GolferScoresTree.inputName();
               System.out.print("\n");

               Boolean success = db.remove(new Golfer(name));
               System.out.print("Golfer '"+ name +"' ");
               if (success)
                  System.out.print("removed from database\n");
               else
                  System.out.print("not found in database, no action taken\n");

               break;

            case 6:
               name = GolferScoresTree.inputName();
               System.out.print("\n");

               rounds = GolferScoresTree.inputInt("Number of rounds played: ");
               System.out.print("\n");

               handicap = GolferScoresTree.inputInt("Player handicap: ");
               System.out.print("\n");

               avg = GolferScoresTree.inputDouble("Average score: ");
               System.out.print("\n");

               System.out.print("\n");

               g = new Golfer(name, rounds, handicap, avg);
               db.add(g);
               System.out.println("Added new golfer:\n"+ g);
               break;

            case 7:
               GolferScoresTree.saveDbToFile(db, dbFilename);
               System.out.println("Goodbye.\n");
               break loop;

            case 8:
               GolferScoresTree.saveDbToFile(db, dbFilename);
               break;

            case 9:
               System.out.print("Enter database filename: ");
               dbFilename = GolferScoresTree.scanner.next().trim();
               System.out.print("\n");
               break;

            default:
               System.out.println("Invalid option\n");
               break;
         }
      }
   }

   public static String inputName() {
      return GolferScoresTree.inputString("Golfer name: ");
   }

   public static String inputString(String msg) {
      //String errorMsg = "Must be a non-empty string\n";

      /*
      String input = "";
      while (input == "") {
         System.out.print(msg);
         input = GolferScoresTree.scanner.next().trim();
         if (input == "")
            System.out.println(errorMsg);
      }
      */

      // scanner automatically ignores empty inputs
      System.out.print(msg);
      input = GolferScoresTree.scanner.next().trim();

      return input;
   }

   public static int inputInt(String msg) {
      String strInput;
      String errorMsg = "Must be a positive integer\n";

      int input = -1;
      while (input == -1) {
         System.out.print(msg);
         if (GolferScoresTree.scanner.hasNextInt()) {
            input = GolferScoresTree.scanner.nextInt();
            if (input < 0) {
               input = -1;
               System.out.println(errorMsg);
            }
         }
         else {
            strInput = GolferScoresTree.scanner.next();
            if (strInput == "") {
               input = 0;
               System.out.println("Defaulting to 0");
            }
            else {
               System.out.println(errorMsg);
            }
         }
      }

      return input;
   }

   public static double inputDouble(String msg) {
      String strInput;
      String errorMsg = "Must be a positive floating-point value\n";

      double input = -1;
      while (input == -1) {
         System.out.print(msg);
         if (GolferScoresTree.scanner.hasNextDouble()) {
            input = GolferScoresTree.scanner.nextDouble();
            if (input < 0) {
               input = -1;
               System.out.println(errorMsg);
            }
         }
         else {
            strInput = GolferScoresTree.scanner.next();
            if (strInput == "") {
               input = 0;
               System.out.println("Defaulting to 0");
            }
            else {
               System.out.println(errorMsg);
            }
         }
      }

      return input;
   }

   public static TreeBag<Golfer> loadDbFromFile(String dbFilename) 
   {
      TreeBag<Golfer> db = new TreeBag<Golfer>();
      String line;
      Pattern p = Pattern.compile("([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)");
      File f = new File("."+ File.separator + dbFilename);

      if (f.exists()) {
         System.out.println("Loading golfers from database file '"+ dbFilename +"'...");
         try {
            FileReader fr = new FileReader(dbFilename);
            BufferedReader buf = new BufferedReader(fr);

            while ( (line = buf.readLine()) != null ) {
               line = line.trim();
               //System.out.println("Read line:\n"+ line +"\n");
               Matcher m = p.matcher(line);
               if (m.find()) {
                  String name = m.group(1);
                  int rounds = Integer.valueOf(m.group(2));
                  int handicap = Integer.valueOf(m.group(3));
                  double avg =  Double.valueOf(m.group(4));
                  Golfer g = new Golfer(name, rounds, handicap, avg);
                  db.add(g);
                  /*
                  System.out.println("Added golfer to database:\n"+ g +"\n");
                  System.out.println("Current database:\n"+ db);
                  System.out.println("Tree view:");
                  db.displayAsTree();
                  System.out.println("\n");
                  */
               }
               else
                  System.out.println("No golfers found in database file");
            }

            buf.close();   
         }
         catch(FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Unable to open file '"+ dbFilename +"': "+ e);
         }
         catch(IOException e) {
            e.printStackTrace();
            System.out.println("Error reading file '"+ dbFilename +"': "+ e);
         }
      }
      else
         System.out.println("Database file '"+ dbFilename +"' not found");
      

      return db;
   }

   public static void saveDbToFile(TreeBag<Golfer> db, String dbFilename) 
   {
      BufferedWriter buf = null;

      System.out.println("Saving to database file '"+ dbFilename +"'...");
      try {
         File f = new File("."+ File.separator + dbFilename);

         if ( ! f.exists() ) {
            f.createNewFile();
         }

         FileWriter fw = new FileWriter(f);
         buf = new BufferedWriter(fw);
         buf.write(db.toString());
      }
      catch(IOException e) {
         
      }
      finally {
         if (buf != null) {
            try {
               buf.close();
            }
            catch (Exception e) {
               e.printStackTrace();
               System.out.println("Error closing buffer to write file: "+ e);
            }
         }
      }

      System.out.println("Save complete.");
   }
}