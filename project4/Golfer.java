// File: Golfer.java

/**
* Objects of this class represent a single golfer and their attributes:
* Lastname, number of rounds played, handicap, and average score
* 
* Standard accessor/mutator functions are provided for all attributes.
* Additionally, a method for adding a new score to a golfer and recomputing
* their average score is provided.
*
* @author Afeique Sheikh, Sydney McDaniel, Yusen Jiang
*/
public class Golfer implements Comparable<Golfer> 
{
    private String lastname;
    private int numberOfRounds;
    private int handicap;
    private double averageScore;

    public Golfer(String lastname) 
    {
        this(lastname, 0, 0, 0);
    }

    public Golfer(String lastname, int numberOfRounds, int handicap, double averageScore) 
    {
        this.lastname = lastname;
        this.numberOfRounds = numberOfRounds;
        this.handicap = handicap;
        this.averageScore = averageScore;
    }

    public int compareTo(Golfer g) 
    {
        return this.lastname.compareToIgnoreCase(g.lastname);
    }

    public void setLastname(String lastname) 
    {
        this.lastname = lastname;
    }

    public void setNumberOfRounds(int numberOfRounds) 
    {
        this.numberOfRounds = numberOfRounds;
    }

    public void setHandicap(int handicap) 
    {
        this.handicap = handicap;
    }

    public void setAverageScore(double averageScore) 
    {
        this.averageScore = averageScore;
    }

    public String getLastname() 
    {
        return this.lastname;
    }

    public int getNumberOfRounds() 
    {
        return this.numberOfRounds;
    }

    public int getHandicap() 
    {
        return this.handicap;
    }

    public double getAverageScore() 
    {
        return this.averageScore;
    }

    public void addScore(double newScore) 
    {
        double totalScore = this.averageScore * this.numberOfRounds;
        this.averageScore = (totalScore + newScore)/++this.numberOfRounds;
    }

    public String toString() 
    {
        String avgStr = String.format("%.2f", this.averageScore);
        return this.lastname +" "+ this.numberOfRounds +" "+ this.handicap +" "+ avgStr;
    }
}