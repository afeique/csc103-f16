/*
Main Driver for the Airport simulation program
Sydney McDaniel
Yusen Jiang
Afeique Sheikh
*/

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
* This class simulates an airport runway.
*
* @author  Afeique Sheikh, Sydney McDaniel, Yusen Jiang
*/
public class RunwaySimulation{

   /**
    * Runs the simulation.
    */
   public static void main(String[ ] args) {
      // create scanner to get user inputs
      Scanner scanner = new Scanner(System.in);
      
      System.out.print("Total time for the simulation: ");
      int totalTime = scanner.nextInt();
      if (totalTime < 1)
         throw new IllegalArgumentException("Total simulation time must be at least 1 minute");
      
      System.out.print("Time needed for take off: ");
      int takeoffTime = scanner.nextInt();
      if (takeoffTime < 1)
         throw new IllegalArgumentException("Time needed for takeoff must be at least 1 minute");
      
      System.out.print("Time needed to land: ");
      int landingTime = scanner.nextInt();
      if (landingTime < 1)
         throw new IllegalArgumentException("Time needed for landing must be at least 1 minute");
      
      System.out.print("Average time between arrivals for takeoff: ");
      int avgTimeTakeoffReady = scanner.nextInt();
      if (avgTimeTakeoffReady < 1)
         throw new IllegalArgumentException("Average time between arrivals for takeoff must be at least 1 minute");
      
      
      System.out.print("Average time between arrivals for landings: ");
      int avgTimeLandingReady = scanner.nextInt();
      if (totalTime < 1)
         throw new IllegalArgumentException("Average time between arrivals for landings must be at least 1 minute");

      
      System.out.print("Max time a plane waiting to land can stay in the air before crashing: ");
      int maxWaitTime = scanner.nextInt();
      if (totalTime < 1)
         throw new IllegalArgumentException("Max time a plane waiting to land can stay in the air must be at least 1 minute");

      // run the simulation using the user inputs
      RunwaySimulation.runSimulation(totalTime, takeoffTime, landingTime, avgTimeTakeoffReady, avgTimeLandingReady, maxWaitTime);
      

      // Randomized testing
      /*
      while (true) {
         int totalTime = ThreadLocalRandom.current().nextInt(30, 121);
         int takeoffTime = ThreadLocalRandom.current().nextInt(1, 11);
         int landingTime = ThreadLocalRandom.current().nextInt(1, 11);
         int avgTimeTakeoffReady = ThreadLocalRandom.current().nextInt(1, 11);
         int avgTimeLandingReady = ThreadLocalRandom.current().nextInt(1, 11);
         int maxWaitTime = ThreadLocalRandom.current().nextInt(1, 31);
         RunwaySimulation.runSimulation(totalTime, takeoffTime, landingTime, avgTimeTakeoffReady, avgTimeLandingReady, maxWaitTime);
      }
      */
      
      
   }

   public static void runSimulation(int totalTime, int takeoffTime, int landingTime, int avgTimeTakeoffReady, int avgTimeLandingReady, int maxWaitTime) {
      // probability of plane arriving to takeoff
      BooleanSource newTakeoff = new BooleanSource(1/(double)avgTimeTakeoffReady);
      // probability of plane arriving to land
      BooleanSource newLanding = new BooleanSource(1/(double)avgTimeLandingReady);

      // total number of takeoffs during simulation run
      int numTakeoffs = 0;
      // total number of landings during simulation run
      int numLandings = 0;
      // total number of crashes during simulation run
      int numCrashes = 0;
      // average time a plane had to wait for takeoff during simulation
      double avgWaitTakeoff;
      // average time a plane had to wait to land during simulation
      double avgWaitLanding;

      // stack of crashed planes
      LinkedStack<Plane> crashedStack = new LinkedStack<Plane>();

      // simple priority queue

      // queues of planes waiting to takeoff and land
      LinkedQueue<Plane> takeoffQueue = new LinkedQueue<Plane>();
      LinkedQueue<Plane> landingQueue = new LinkedQueue<Plane>();
      // averagers for the different queues
      Averager landingAverager = new Averager();
      Averager takeoffAverager = new Averager();
      

      // runway
      Runway runway = new Runway(takeoffTime, landingTime);

      System.out.println("\n");
      System.out.println("\n");

      // current plane on runway
      Plane currentPlane = null;

      // tmp vars
      Plane tmpPlane = null;
      LinkedQueue<Plane> tmpQueue = new LinkedQueue<Plane>();
      
      for (int t=1; t<=totalTime; t++) {
         System.out.format("During minute %d:\n", t);

         // check if a new plane has arrived for takeoff
         if (newTakeoff.query()) {
            tmpPlane = new Plane(t);
            System.out.format("  Arrived for Takeoff: Plane #%d\n", tmpPlane.getId());
            takeoffQueue.add(tmpPlane);
            numTakeoffs++;
         }

         // check if a new plane has arrived for landing
         if (newLanding.query()) {
            tmpPlane = new Plane(t);
            System.out.format("  Arrived for Landing: Plane #%d\n", tmpPlane.getId());
            landingQueue.add(tmpPlane);
            numLandings++;
         }

         // if the runway is not busy, remove a plane from a queue and start using runway
         if (!runway.isBusy()) {
            // if landing queue is not empty
            if (!landingQueue.isEmpty()) {
               // remove plane from landing queue
               currentPlane = landingQueue.remove();
               // start using runway to land
               runway.setStatus('L');
               // add plane wait time to landing averager
               landingAverager.addNumber(t - currentPlane.getTime());
            }
            else if (!takeoffQueue.isEmpty()) {
               // remove plane from takeoff queue
               currentPlane = takeoffQueue.remove();
               // start using runway to takeoff
               runway.setStatus('T');
               // add plane wait time to takeoff averager
               takeoffAverager.addNumber(t - currentPlane.getTime());
            }
         } else {
            // else if the runway is busy, reduece its remaining time
            runway.reduceRemainingTime();

            // loop through the planes waiting to land to check for crashes

            // while the landing queue is not empty,
            while (!landingQueue.isEmpty()) {
               // remove plane from landing queue
               tmpPlane = landingQueue.remove();
               // if wait time exceeds the max wait time, crash the plane
               if ( (t - tmpPlane.getTime()) > maxWaitTime) {
                  // set the plane crash time
                  tmpPlane.setCrashTime(t);
                  // push the plane onto the crashed stack
                  crashedStack.push(tmpPlane);
                  // indicate this plane has crashed
                  System.out.format("  Plane #%d crashed\n", tmpPlane.getId());
                  // increment the count of crashed planes
                  numCrashes++;
               }
               else {
                  // otherwise, add the plane into the tmpQueue
                  tmpQueue.add(tmpPlane);
               }
            }

            // swap the tmpQueue and the landingQueue
            landingQueue = tmpQueue;
            tmpQueue = new LinkedQueue<Plane>();
         }

         // print out runway status information
         System.out.format("  Runway: ");
         switch (runway.getStatus()) {
            case 'I':
            System.out.format("Idle");
            break;

            case 'L':
            System.out.format("Plane #%d is landing ", currentPlane.getId());
            break;

            case 'T':
            System.out.format("Plane #%d is taking off ", currentPlane.getId());
            break;
         }
         // if the runway is on the last minute of its current operation,
         // print out an indicator
         if (runway.isFinishing())
            System.out.format("(finishing)");
         System.out.format("\n\n");
      }

      // print out list of planes that crashed
      System.out.format("\n");
      System.out.format("Crashed Planes:\n");
      // if the stack of crashed planes is empty, indicate no planes crashed
      if (crashedStack.isEmpty())
         System.out.format("  None\n");

      // else if the stack of crashed planes is not empty,
      // loop through the stack of crashed planes
      while (!crashedStack.isEmpty()) {
         // pop a plane off the stack of crashed planes and print its crash time
         tmpPlane = crashedStack.pop();
         System.out.format("  Plane #%d crashed at time: %d\n", tmpPlane.getId(), tmpPlane.getCrashTime());
      }

      // compute average takeoff and landing wait times
      avgWaitTakeoff = takeoffAverager.average();
      if (Double.isNaN(avgWaitTakeoff))
         avgWaitTakeoff = 0;
      avgWaitLanding = landingAverager.average();
      if (Double.isNaN(avgWaitLanding))
         avgWaitLanding = 0;

      // print out summary of inputs
      System.out.format("\n");
      System.out.format("Inputs:\n");
      System.out.format("Total time of simulation: %d minutes\n", totalTime);
      System.out.format("Time needed for takeoff: %d minutes\n", takeoffTime);
      System.out.format("Time needed for landing: %d minutes\n", landingTime);
      System.out.format("Average time between arrivals for takeoff: %d minutes\n", avgTimeTakeoffReady);
      System.out.format("Average time between arrivals for landing: %d minutes\n", avgTimeLandingReady);
      System.out.format("Max time a plane can wait to land before crashing: %d minutes\n", maxWaitTime);
      
      // print out summary of computed statistics: counts and averages
      System.out.format("\nStatistics:\n");
      System.out.format("Number of planes that came to runway for takeoff: %d\n", numTakeoffs);
      System.out.format("Number of planes that came to runway for landing: %d\n", numLandings);
      System.out.format("Number of planes that crashed: %d\n", numCrashes);
      System.out.format("Average time waiting in takeoff queue: %.2f minutes\n", avgWaitTakeoff);
      System.out.format("Average time waiting in landing queue: %.2f minutes\n", avgWaitLanding);
      System.out.format("\n");

      // reset the plane count in case another simulation will be run
      Plane.resetCount();
   }
 
}//end RunwaySimulation