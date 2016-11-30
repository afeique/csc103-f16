import java.util.*;

public class bigBadTester {

    public static void showMenu() {
        System.out.println("\n\nMenu Selection");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("1. Display both numbers");
        System.out.println("2. Input two new numbers");
        System.out.println("3. Check if numbers are equal");
        System.out.println("4. Report the sum of two numbers");
        System.out.println("5. Report the multiplication of the two numbers");
        System.out.println("6. Create and output the clone of the numbers");
        System.out.println("7. Quit");
        System.out.println("-----------------------------------------------------------------------------\n");
    }
    
    public static void main(String [] arg)
    { 
        Scanner scanner = new Scanner(System.in);

        int intChoice;
        String choice;
        String input = "";

        UnboundedInt a, b;
        a = null;
        b = null;
      
        dance:
        while(true) {
            bigBadTester.showMenu();
            System.out.print("Choose your fate (numbers only, kthxbai): ");
            choice = scanner.next();

            try {
                intChoice = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                System.out.println("\nOhmigosh, SERIOUSLY? You can't even enter a NUMBER without messing it up?");
                continue;
            }

            System.out.println("");
        
            switch (intChoice) {
                case 1 :
                    if (a != null && b != null) {
                        System.out.format("A = %s\n\nB = %s\n\n", a.toString(), b.toString());   
                    } else {
                        String s = "No numbers to display. Want to count with Count von Count instead?\n\n"+
"              oooOOOooo\n"+
"           oOOOOOOOOOOOOOo\n"+
"         oOO\"           \"OO\n"+
"    ____oOO  ====   ====  OOo____    Ah, ah, ah!\n"+
"    \\   OO'      ! !.---. 'OO   /\n"+
"     \\  OO   <0> ! !!<0>!  OO  /\n"+
"      \\ Oo       ! !'---'  oO /\n"+
"       \\o        \\_/        o/\n"+
"        .' _______________ '.\n"+
"      ,'   :   V     V   :   '.\n"+
"    ,'      -_         _-      '.\n"+
"  ,'          \"oOOOOOo\"          '.\n"+
",'              OOOOO              '.\n"+
"-----------     \"OOO\"     -----------\n"+
"                 \"O\"\n";
                        
                        System.out.println(s);
                    } 
                    break;
                 
                case 2 :
                    System.out.println("Note: non-integer characters will be stripped from your input like clothes off an exotic dancer.\n");
                    System.out.print("Input A: ");
                    input = scanner.next();
                    try {
                        a = new UnboundedInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("For cryin' out loud: you need AT LEAST ONE NUMERIC DIGIT.");
                    }
                    
                    
                    System.out.println("");

                    System.out.print("Input B: ");
                    input = scanner.next();
                    try {
                        b = new UnboundedInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Really? Is it that hard to type in at least one number?");
                    }
                    
                    break;
                 
              case 3 :
                  if (a != null && b != null)
                      System.out.format("%s == %s : \n\n%s\n", a, b, a.equals(b));
                  else
                      System.out.println("No numbers to compare.");
                  break;
              case 4 :
                  if (a != null && b != null)
                      System.out.format("%s + %s = \n\n%s\n", a, b, a.add(b));
                  else
                      System.out.println("No numbers to add.");
                  break;
              
              case 5 :
                  if (a != null && b != null)
                      System.out.format("%s * %s = \n\n%s\n", a, b, a.multiply(b));
                  else
                      System.out.println("No numbers to procreate... I mean, multiply.");
                  break;
                 
              case 6 :
                  if (a != null && b != null) {
                      System.out.format("Original A:\n%s\n\nCloned A:\n%s\n", a, a.clone());
                      System.out.println("\n***\n");
                      System.out.format("Original B:\n%s\n\nCloned B:\n%s\n", b, b.clone());
                  } else {
                      String s = "No numbers to clone. Here is a sheep instead:\n\n"+
"              __\n"+
"    ,'```--'''  ``-''-.\n"+
"  ,'            ,-- ,-'.\n"+
" (//            `\"'| 'a \\   baaaaaargh *hack* *cough* \n"+
"   |    `;         |--._/\n"+
"   \\    _;-._,    /\n"+
"    \\__/\\\\   \\__,'\n"+
"     ||  `'   \\|\\\\\n"+
"     \\\\        \\\\`'\n"+
"      `'        `'\n";
                      System.out.println(s);
                  }
                  break;
                 
              case 7 :
                  System.out.println("See ya later alligator ~");
                  break dance;
              
              default:
                  System.out.println("That ain't no choice. Make my day and choose again, punk.");
                  break;
                 
            }
         
        }
           
    }
}