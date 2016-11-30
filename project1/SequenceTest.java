import java.util.Scanner;
import java.util.*;
 
public class SequenceTest {
    public static int activeSeq = 0;
    public static String activeAlpha = "A";
 
    public static void UpdateActiveSeq() {
        System.out.println("");
        SequenceTest.activeAlpha = "Unknown";
        if (SequenceTest.activeSeq == 0)
            SequenceTest.activeAlpha = "A";
        else if (SequenceTest.activeSeq == 1)
            SequenceTest.activeAlpha = "B";
    }
 
    public static void PrintMenu() {
        System.out.println("\n\nMenu Selection");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("1. Print the sequences of A and B");
        System.out.println("2. Report the capacities of A and B");
        System.out.println("3. Report if A and B are equal");
        System.out.println("4. Toggle the ACTIVE sequence ("+ SequenceTest.activeAlpha +" is curently ACTIVE)" );
        System.out.println("5. Add a number to the front of the ACTIVE sequence");
        System.out.println("6. Add a number before a specified index in the ACTIVE sequence");
        System.out.println("7. Add a number after a specified index in the ACTIVE sequence");
        System.out.println("8. Add a number to the end of the ACTIVE sequence");
        System.out.println("9. Add sequence B to end of A");
        System.out.println("10. Delete a number at a certain index from the ACTIVE sequence");
        System.out.println("11. Delete the first number from the ACTIVE sequence");
        System.out.println("12. Display a number at a certain index in the ACTIVE sequence");
        System.out.println("13. Display the last element in the ACTIVE sequence");
        System.out.println("14. Trim extra memory from both A and B");
        System.out.println("15. Create a clone of the ACTIVE sequence");
        System.out.println("16. Create a new sequence by concatenating B to A");
        System.out.println("17. Quit");
        System.out.println("-----------------------------------------------------------------------------\n");
    }
 
    public static void main(String [] arg)
    {
        DoubleArraySeq[] s = {new DoubleArraySeq(), new DoubleArraySeq()};
        s[0] = new DoubleArraySeq(5);
        s[1] = new DoubleArraySeq(5);
        SequenceTest.activeSeq = 0;
        Scanner scanner = new Scanner(System.in);
 
        int input;
        double element;
        int index;
        DoubleArraySeq curSeq;
 
        dance:
        while (true) {
            SequenceTest.PrintMenu();
            SequenceTest.UpdateActiveSeq();
            System.out.println("ACTIVE SEQUENCE: "+ SequenceTest.activeAlpha);
             
            //choose menu 
            System.out.printf("Enter the menu selection: ");
            try {
            	input = scanner.nextInt();
            } catch (InputMismatchException e) {
            	System.out.println("Sorry, an invalid input (such as an arrow key) was specified, please try again.");
            	continue dance;
            }
 
            String out = "";
            switch (input) {
                case 1:
                	System.out.println("\tSequence contents:");
                    System.out.println("\tA: "+ s[0].toString() +"\n\tB: "+ s[1].toString());
                    break;
                case 2:
                	System.out.println("\tSequence capacities:");
                    System.out.println("\tA: "+ s[0].getCapacity() +"\n\tB: "+ s[1].getCapacity());
                    break;
                case 3:
                    if (s[0].equals(s[1]))
                        System.out.println("\tA and B are equal");
                    else
                        System.out.println("\tA and B are not equal");
 
                    if (s[0].size() == 0 && s[1].size() == 0)
                        System.out.println("\t(A and B are both empty)");
                    break;
                case 4:
                    if (SequenceTest.activeSeq == 0)
                        SequenceTest.activeSeq = 1;
                    else if (SequenceTest.activeSeq == 1)
                        SequenceTest.activeSeq = 0;
                    SequenceTest.UpdateActiveSeq();
                    System.out.println("\tActive sequence switched to: "+ SequenceTest.activeAlpha);
                    break;
                case 5:
                    System.out.printf("\tEnter the number to add to the front of sequence %s: ", SequenceTest.activeAlpha);
                    element = scanner.nextDouble();
                    s[SequenceTest.activeSeq].addFront(element);
                    System.out.printf("\tAdded %.2f to the front of sequence %s", element, SequenceTest.activeAlpha);
                    break;
                case 6:
                    System.out.printf("\tEnter the index to add BEFORE in sequence %s: ", SequenceTest.activeAlpha);
                    index = scanner.nextInt();
                    System.out.printf("\tEnter the element to add BEFORE index %d in sequence %s: ", index, SequenceTest.activeAlpha);
                    element = scanner.nextDouble();
                    curSeq = s[SequenceTest.activeSeq];
                    System.out.printf("\tOriginal sequence %s: %s\n", SequenceTest.activeAlpha, curSeq.toString());
                    try {
                        curSeq.setCurrent(index);
                        curSeq.addBefore(element);
                        System.out.printf("\tNew sequence %s: %s\n", SequenceTest.activeAlpha, curSeq.toString());
                    } catch (IllegalStateException e) {
                        System.out.println("\t"+ e.getMessage());
                    }
                    break;
                case 7:
                    System.out.printf("\tEnter the index to add AFTER in sequence %s: ", SequenceTest.activeAlpha);
                    index = scanner.nextInt();
                    System.out.printf("\tEnter the element to add AFTER index %d in sequence %s: ", index, SequenceTest.activeAlpha);
                    element = scanner.nextDouble();
                    curSeq = s[SequenceTest.activeSeq];
                    System.out.printf("\tOriginal sequence %s BEFORE: %s\n", SequenceTest.activeAlpha, curSeq.toString());
                    try {
                        curSeq.setCurrent(index);
                        curSeq.addAfter(element);
                        System.out.printf("\tNew sequence %s AFTER: %s\n", SequenceTest.activeAlpha, curSeq.toString());
                    } catch (IllegalStateException e) {
                        System.out.println("\t"+ e.getMessage());
                    }
                    break;
                case 8:
                    System.out.printf("\tEnter the number to add to the end of sequence %s: ", SequenceTest.activeAlpha);
                    element = scanner.nextDouble();
                    curSeq = s[SequenceTest.activeSeq];
                    curSeq.addEnd(element);
                    System.out.printf("\tAdded %.2f to the end of sequence %s", element, SequenceTest.activeAlpha);
                    System.out.printf("\t%s: %s", SequenceTest.activeAlpha, curSeq.toString());
                    break;
                case 9:
                    out = "\tA: " + s[0].toString() +"\n\tB: " + s[1].toString();
                    s[0].addAll(s[1]);
                    out += "\n\tA + B = "+ s[0].toString();
                    out += "\n\n\tAdded sequence B to end of A";
                    System.out.println(out);
                    break;
                case 10:
                    System.out.printf("\tEnter the index number of the element to delete from sequence "+ SequenceTest.activeAlpha +": ");
                    index = scanner.nextInt();
                    curSeq = s[SequenceTest.activeSeq];
                    try {
                        curSeq.setCurrent(index);
                        element = curSeq.getCurrent();
                        curSeq.removeCurrent();
                        System.out.println("\tRemoved "+ element +" from sequence "+ SequenceTest.activeAlpha);
                    } catch (IllegalStateException e) {
                        System.out.println("\t"+ e.getMessage());
                    }
                    break;
                case 11:
                    try {
                        s[SequenceTest.activeSeq].removeFront();
                    } catch (IllegalStateException e) {
                        System.out.println("\t"+ e.getMessage());
                    }
                    break;
 
                case 12:
                    System.out.printf("\tEnter the index number of the element to find from sequence %s: ", SequenceTest.activeAlpha);
                    index = scanner.nextInt();
                    
                    try {
                    	curSeq = s[SequenceTest.activeSeq];
                    	System.out.printf("\t%s: %s\n", SequenceTest.activeAlpha, curSeq.toString());
                        element = curSeq.getElement(index);
                        System.out.printf("\tThe value at index %d in sequence %s is: %.2f", index, SequenceTest.activeAlpha, element);
                    } catch (IllegalStateException e) {
                        System.out.println("\t"+ e.getMessage());
                    }
                      
                    break;
 
                case 13:
                    try {
                    	curSeq = s[SequenceTest.activeSeq];
                    	System.out.printf("\t%s: %s\n", SequenceTest.activeAlpha, curSeq.toString());
                        curSeq.setCurrentLast();
                        System.out.printf("\tLast element in sequence %s: %.2f", SequenceTest.activeAlpha, curSeq.getCurrent());
                    } catch (IllegalStateException e) {
                        System.out.println("\t"+ e.getMessage());
                    }
                     
                    break;
                case 14:
                    System.out.println("\tCapacities BEFORE trimming:");
                    System.out.println("\tA: "+ s[0].getCapacity() +"\n\tB: "+ s[1].getCapacity());
                    s[0].trimToSize();
                    s[1].trimToSize();
                    System.out.println("\n\tCapacities AFTER trimming:");
                    System.out.println("\tA: "+ s[0].getCapacity() +"\n\tB: "+ s[1].getCapacity());
                    break;
                case 15:
                    DoubleArraySeq copy = s[SequenceTest.activeSeq].clone();
                    System.out.printf("\tSequence %s clone: %s", SequenceTest.activeAlpha, copy.toString());
                    break;
                case 16:
                    System.out.println("\tA: "+ s[0].toString() +"\n\tB: "+ s[1].toString());
                    DoubleArraySeq concat = DoubleArraySeq.concatenation(s[0], s[1]);
                    System.out.println("\tA concenated with B: "+ concat.toString());
                    break;
                case 17:
                    System.out.println("Bye!");
                    break dance;
                default:
                    throw new IllegalArgumentException("Whatchu tryin' to pull. That ain't no choice. Make my day and choose again, punk.");
            }
 
        }
    }
}