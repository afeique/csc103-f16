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

    public Golfer(String name) 
    {
        this(name, 0, 0, 0);
    }

    public Golfer(String name, int rounds, int handicap, double avg) 
    {
        this.name = name;
        this.rounds = rounds;
        this.handicap = handicap;
        this.avg = avg;
    }

    public int compareTo(Golfer g) 
    {
        return this.name.compareToIgnoreCase(g.name);
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public void setRounds(int rounds) 
    {
        this.rounds = rounds;
    }

    public void setHandicap(int handicap) 
    {
        this.handicap = handicap;
    }

    public void setAvg(double avg) 
    {
        this.avg = avg;
    }

    public String getName() 
    {
        return this.name;
    }

    public int getRounds() 
    {
        return this.rounds;
    }

    public int getHandicap() 
    {
        return this.handicap;
    }

    public double getAvg() 
    {
        return this.avg;
    }

    public void addScore(double newScore) 
    {
        double totalScore = this.avg * this.rounds;
        this.avg = (totalScore + newScore)/++this.rounds;
    }

    public String toString() 
    {
        String avgStr = String.format("%.2f", this.avg);
        return this.name +" "+ this.rounds +" "+ this.handicap +" "+ avgStr;
    }
}