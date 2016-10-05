import java.util.Scanner;

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
		System.out.println("6. Add a number before a specified number in the ACTIVE sequence");
		System.out.println("7. Add a number after a specified number in the ACTIVE sequence");
		System.out.println("8. Add a number to the end of the ACTIVE sequence");
		System.out.println("9. Add sequence B to end of A");
		System.out.println("10. Delete a number at a certain index from the ACTIVE sequence");
		System.out.println("11. Delete the first number from the ACTIVE sequence");
		System.out.println("12. Display a number at a certain index in the ACTIVE sequence");
		System.out.println("13. Display the last element in the ACTIVE sequence");
		System.out.println("14. Trim extra memory from both A and B");
		System.out.println("15. Create a clone of the ACTIVE sequence and display");
		System.out.println("16. Create a new sequence by concatenating B to A and display");
		System.out.println("17. Quit");
		System.out.println("-----------------------------------------------------------------------------\n");
	}

	public static void main(String [] arg)
	{
		DoubleArraySeq[] s = {new DoubleArraySeq(), new DoubleArraySeq()};
		SequenceTest.activeSeq = 0;
		Scanner scanner = new Scanner(System.in);

		int input;
		double secondIn;

		dance:
		while (true) {
			SequenceTest.PrintMenu();
			SequenceTest.UpdateActiveSeq();
			System.out.println("ACTIVE SEQUENCE: "+ SequenceTest.activeAlpha);
			
			//choose menu 
			System.out.println("Enter the menu selection: ");
			input = scanner.nextInt();

			String out = "";
			switch (input) {
				case 1:
					out = "\tA: [ " + s[0].toString() +" ]\n\tB: [ " + s[1].toString() +" ]";
					break;
				case 2:
					out = "\tA: "+ s[0].getCapacity() +"\n\tB: "+ s[1].getCapacity();
					break;
				case 3:
					out = "\tA and B are not equal";
					if (s[0].equals(s[1]))
						out = "\tA and be are equal";
					if (s[0].getCapacity() == 0 && s[1].getCapacity() == 0)
						out += "\n\t(A and B are both empty)";
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
					System.out.println("\nEnter the number to add to the front of sequence "+ SequenceTest.activeAlpha +": ");
					secondIn = scanner.nextDouble();
					s[SequenceTest.activeSeq].addFront(secondIn);
					System.out.printf("Added %.2f to the front of sequence %s", secondIn, SequenceTest.activeAlpha);
					break;
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:
				case 11:
				case 12:
				case 13:
				case 14:
				case 15:
				case 16:
				case 17:
					break dance;
				default:
					throw new IllegalArgumentException("Whatchu tryin' to pull. That ain't no choice. Make my day and choose again, punk.");
			}

			System.out.println(out);
		}
	}
}
