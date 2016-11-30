/**
 * This class models a plane for the runway simulation. 
 * Each plane has a unique identifier based on which plane # it is
 * in the overall simulation.
 */
class Plane {
   // static count of total number of planes for a particular simulation run
   private static int planeCount = 0;
   // the time the plane arrived in queue
   private int time; 
   // time the plane crashed
   private int crashTime;
   // unique identifier for a particular plane within a single simulation run
   private int id;
   
   /**
    * Create a new plane given its arrival time.
    * 
    * @param time
    *    The time the plane arrived in its respective queue.
    */
   public Plane(int time)
   {
      if (time < 1)
         throw new IllegalArgumentException("Plane arrival time in queue must be at minute >= 1");
      this.time = time;
      this.crashTime = 0;
      this.id = ++Plane.planeCount;
   }

   /**
    * Get the arrival time of the plane in its respective queue.
    * 
    * @return int
    *    Time the plane arrived.
    */
   public int getTime()  {
      return time;
   }

   /**
    * Returns true if the plane crashed, false otherwise.
    * 
    * @return boolean
    *    Whether or not the plane crashed.
    */
   public boolean isCrashed() {
      return (this.crashTime > 0);
   }

   /**
    * Returns the time the plane crashed. Returns 0 if the plane did not crash.
    * 
    * @return int
    *    The time the plane crashed.
    */
   public int getCrashTime() {
      return this.crashTime;
   }

   /**
    * Sets the crash time of the plane. Must be > 0.
    * 
    * @param time
    *    Plane's crash time.
    */
   public void setCrashTime(int time) {
      if (time < 1)
         throw new IllegalArgumentException("Plane crash time must be at minute >= 1");
      this.crashTime = time;
      
   }
   
   /**
    * Get the plane ID so it can be identified uniquely.
    * 
    * @return int
    *    Plane's unique identifier.
    */
   public int getId() {
      return id;
   }

   /**
    * This static method resets the static count of number of planes.
    * It is primarily used when multiple simulations are run within
    * a single execution.
    */
   public static void resetCount() {
      Plane.planeCount = 0;
   }
   

}// end Plane
