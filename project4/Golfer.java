// File: Golfer.java

/**
* Objects of this class represent a single golfer and their attributes:
* Name, number of rounds played, handicap, and average score
* 
* Standard accessor/mutator functions are provided for all attributes.
* Additionally, a method for adding a new score to a golfer and recomputing
* their average score is provided.
*
* @author Afeique Sheikh, Sydney McDaniel, Yusen Jiang
*/
public class Golfer implements Comparable<Golfer> 
{
   private String name;
   private int rounds;
   private int handicap;
   private double avg;

   /**
   * Construct a new Golfer object with a name and all-zero stats.
   * This is useful when specifying a golfer for a generic class method
   * that performs search.
   * @param name
   *   golfer name
   * @throws NullPointerException
   *   if parameter name is a null String reference
   * @throws IllegalArgumentException
   *   if parameter name is all whitespace or an empty string
   * <dt><b>Precondition:</b><dd>
   *   String name is not null
   * <dt><b>Postcondition:</b><dd>
   *   new Golfer object created with specified name and all-zero stats
   **/
   public Golfer(String name) 
   {
      this(name, 0, 0, 0);
   }

   /**
   * Construct a new Golfer object by specifying all attributes.
   * @param name
   *   golfer name
   * @param rounds
   *   number of rounds played by golfer
   * @param handicap
   *   player's handicap
   * @param avg
   *   player's average score
   * @throws NullPointerException
   *   if parameter name is a null String reference
   * @throws IllegalArgumentException <ul>
   *   <li>if name is all whitespace or an empty string</li>
   *   <li>if rounds < 0</li>
   *   <li>if handicap < 0</li>
   *   <li>if avg < 0.00</li></ul>
   * <dt><b>Precondition:</b><dd> <ul>
   *   <li>parameter name is not null</li>
   *   <li>parameter rounds is a positive int >= 0</li>
   *   <li>parameter handicap is a positive int >= 0</li>
   *   <li>parameter avg is a positive double >= 0.00</li></ul>
   * <dt><b>Postcondition:</b><dd>
   *   new Golfer object created with specified attributes
   **/
   public Golfer(String name, int rounds, int handicap, double avg) 
   {
      this.setName(name);
      this.setRounds(rounds);
      this.setHandicap(handicap);
      this.setAvg(avg);
   }

   /**
   * Compare this Golfer to another Golfer by name,
   * return 0 if both Golfers have the same name; return int < 0
   * if this Golfer's name comes alphabetically before;
   * return int > 0 if this Golfer's name comes
   * alphabetically after.
   * @param g
   *   Golfer object to compare this to
   * <dt><b>Precondition:</b><dd>
   *   parameter g is a non-null reference to a Golfer object
   **/
   public int compareTo(Golfer g) 
   {
      if (g == null)
         throw new NullPointerException("Cannot compare Golfer object to null");

      // case-insensitive string comparison of names
      return this.name.compareToIgnoreCase(g.name);
   }

   /**
   * Set this Golfer's name attribute.
   * @param name
   *   golfer name
   * @throws NullPointerException
   *   if parameter name is a null String reference
   * @throws IllegalArgumentException
   *   if parameter name is all whitespace or an empty string
   * <dt><b>Precondition:</b><dd>
   *   parameter name is not null
   * <dt><b>Postcondition:</b><dd>
   *   this Golfer's name attribute modified
   **/
   public void setName(String name) 
   {
      if (name == null)
         throw new NullPointerException("Golfer name cannot be null");

      // trim whitespace from input
      name = name.trim();
      // check if input was all whitespace / empty
      if (name == "")
         throw new IllegalArgumentException("Golfer name cannot be empty string, must contain non-whitespace characters");

      this.name = name;
   }

   /**
   * Set number of rounds played by this Golfer.
   * @param rounds
   *   number of rounds played by golfer
   * @throws IllegalArgumentException
   *   if parameter rounds is < 0
   * <dt><b>Precondition:</b><dd>
   *   parameter rounds is a positive int >= 0
   * <dt><b>Postcondition:</b><dd>
   *   this Golfer's rounds attribute modified
   **/
   public void setRounds(int rounds) 
   {
      if (rounds < 0)
         throw new IllegalArgumentException("Number of rounds must be positive int >= 0");
      this.rounds = rounds;
   }

   /**
   * Set handicap for this Golfer.
   * @param handicap
   *   player's handicap
   * @throws IllegalArgumentException
   *   if parameter handicap is < 0
   * <dt><b>Precondition:</b><dd>
   *   parameter handicap is a positive int >= 0
   * <dt><b>Postcondition:</b><dd>
   *   this Golfer's handicap attribute modified
   **/
   public void setHandicap(int handicap) 
   {
      if (handicap < 0)
         throw new IllegalArgumentException("Player handicap must be positive int >= 0");
      this.handicap = handicap;
   }

   /**
   * Set average score for this Golfer.
   * @param avg
   *   player's average score
   * @throws IllegalArgumentException
   *   if parameter avg is < 0.00
   * <dt><b>Precondition:</b><dd>
   *   parameter avg is a positive floating-point value >= 0.00
   * <dt><b>Postcondition:</b><dd>
   *   this Golfer's avg attribute modified
   **/
   public void setAvg(double avg) 
   {
      if (avg < 0)
         throw new IllegalArgumentException("Average score must be positive floating-point value >= 0.00");
      this.avg = avg;
   }

   /**
   * Accessor method for this Golfer's name attribute.
   * @return this Golfer's name
   **/
   public String getName() 
   {
      return this.name;
   }

   /**
   * Accessor method for this Golfer's rounds attribute.
   * @return number of rounds played by this Golfer
   **/
   public int getRounds() 
   {
      return this.rounds;
   }

   /**
   * Accessor method for this Golfer's handicap attribute.
   * @return this Golfer's handicap
   **/
   public int getHandicap() 
   {
      return this.handicap;
   }

   /**
   * Accessor method for this Golfer's avg attribute.
   * @return this Golfer's average score
   **/
   public double getAvg() 
   {
      return this.avg;
   }

   /**
    * Add the score of another round and re-compute this Golfer's average score.
    * @param newScore
    *   score from another round of golf
    * <dt><b>Postcondition:</b><dd>
    *   this Golfer's avg is re-computed
    */
   public void addScore(double newScore) 
   {
      // multiply current average by current number of rounds
      // this yields the player's current total score
      double totalScore = this.avg * this.rounds;
      // add the new score to the current total score to compute new total score
      // divide by incremented number of rounds to compute new average
      this.avg = (totalScore + newScore)/++this.rounds;
   }

   /**
    * Return this Golfer's attributes as a single concatenated String 
    * e.g. for printing.
    * @return String containing all of this Golfer's attributes in one line.
    */
   public String toString() 
   {
      // format the average score with two decimal places (performs half-to-even rounding)
      String avgStr = String.format("%.2f", this.avg);
      // concatenate Golfer attributes together into a single string
      // behind-the-scenes, Java uses StringBuilder, so this is efficient
      return this.name +" "+ this.rounds +" "+ this.handicap +" "+ avgStr;
   }
}