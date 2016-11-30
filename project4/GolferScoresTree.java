// File: GolferScoresTree.java

import java.io.*
import java.util.*;

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

   public static void printMenu() 
   {
      System.out.println("\n");
      System.out.println("Main Menu");
      System.out.println("----------------------------------------------------");
      System.out.println("0. Enter database filename, default='golferinfo.txt'")
      System.out.println("1. Display all golfer stats in order");
      System.out.println("2. Display all golfers as a tree");
      System.out.println("3. Find a specific golfer and display their stats");
      System.out.println("4. Update a golfer's stats by adding a score" );
      System.out.println("5. Remove a golfer from the database");
      System.out.println("6. Add a golfer to the database");
      System.out.println("7. Quit and update the data file");
      System.out.println("----------------------------------------------------\n");
   }

public static void main(String[] args) 
{
   Scanner scanner = new Scanner(System.in);
   int intChoice;
   String choice, input;
   TreeBag<Golfer> db = new TreeBag<Golfer>();


   loop:
   while(true) {
      GolferScoresTree.printMenu();
      System.out.print("Select a menu option: ");
      choice = scanner.next();

      System.out.println("\n");

      try {
         intChoice = Integer.parseInt(choice);
      } catch (NumberFormatException e) {
         System.out.println("Numbers only, please try again.");
         continue;
      }

      switch (intChoice) {
         case 1:

            break;

         case 2:

            break;

         case 3:

            break;

         case 4:

            break;

         case 5:

            break;

         case 6:

            break;

         case 7:
            System.out.println("Saving to data file...");

            System.out.println("Save complete. Goodbye.");
            break loop;

   }

   public static loadDbFromFile(String filename) 
   {
      TreeBag<Golfer> db = new TreeBag<Golfer>();
      String line;
      Pattern p = Pattern.compile("(\\w+) +(\\d+) +(\\d+) +([\\d\\.]+)")

      try {
         FileReader f = new FileReader(filename);
         BufferedReader buf = new BufferedReader(f);

         while ( (line = buf.readLine()) != null ) {
            Matcher m = p.matcher(line);
            String name = m.group(1);
            int numRounds = Integer.valueOf(m.group(2));
            int handicap = Integer.valueOf(m.group(3));
            double avgScore = 
         }

            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
   }
}