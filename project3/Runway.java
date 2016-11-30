/**
* This class represents a single runway. The runway can only perform
* one operation at a time, represented using a char:
* I–Idle, L-Landing, T-takeoff
*
* @author  Afeique Sheikh, Sydney McDaniel, Yusen Jiang
*/
class Runway {
   // amount of time a plane requires to land
   private int timeForLanding;
   // amount of time a plane requires to takeoff
   private int timeForTakeoff;
   // the time remaining for the current plane using the runway
   private int runwayTimeLeft; 
   // current operation the runway is performing:
   // I-Idle, T-Takeoff, L-Landing
   private char status;

   /**
    * Constructor, sets the time required for landing and takeoff.
    *
    * @param time_takeoff 
    *    The time in minutes required for a plane to takeoff.
    * @param time_landing 
    *    The time in minutes required for a plane to land.
    * @throws IllegalArgumentException
    *    When either takeoff or landing time is < 1 minute.
    */
   public Runway (int time_takeoff, int time_landing) {
      if (time_takeoff < 1)
         throw new IllegalArgumentException("Takeoff time must be >= 1 minute");
      if (time_landing < 1)
         throw new IllegalArgumentException("Landing time must be >= 1 minute");

      this.timeForTakeoff = time_takeoff;
      this.timeForLanding = time_landing;
      // initialize runway status to I-Idle
      this.setStatus('I');
   }
   //set the time for landing, time for takeoff, and the 
   //status to idle. 

   /**
    * Returns boolean indicating whether the airport is currently busy.
    *
    * @return boolean
    *    True if a plane is landing or taking off, false otherwise.
    */
   public boolean isBusy() {
      // if the runway is idle, the time left should be 0
      // if there is any time left for the current operation,
      // the runway is currently busy
      return (this.runwayTimeLeft > 0);
   }

   /**
    * If the runway is currently busy, decrement the amount of time left
    * for the current takeoff or landing. If the time left on the runway
    * is zero after decrementing, the runway becomes idle. If there is
    * no time left on the runway (already idle), do nothing.
    *
    * @precondition
    *    Runway is busy (not idle) and time left > 0.
    */
   public void reduceRemainingTime() {
      // if the runway is not busy, decrement the time remaining for current operation
      if (this.isBusy())
         // if the current operation is complete, set the runway status to I-Idle
         if (--this.runwayTimeLeft <= 0)
            this.setStatus('I');
   }

   /**
    * Returns true if the runway will become idle after 1 minute, false otherwise.
    *
    * @return boolean
    *    Whether the runway is in the last minute of the current operation.
    */
   public boolean isFinishing() {
      return (this.runwayTimeLeft == 1);
   }

   /**
    * Change the type of operation currently occupying the runway to
    * either landing, takeoff, or idle.
    *
    * @param status
    *    char representing new operation: T - takeoff, L - landing, or I - idle
    * @throws IllegalArgumentException
    *    If the status is not T, L, or I.
    */
   public void setStatus(char status){
      switch (status) {
         // a plane is taking off: runway is busy for however long a takeoff requires
         case 'T':
            this.runwayTimeLeft = this.timeForTakeoff;
            break;
         // a plane is landing: runway is busy for however long a landing requires
         case 'L':
            this.runwayTimeLeft = this.timeForLanding;
            break;
         // runway is idle and unused: not busy
         case 'I':
            this.runwayTimeLeft = 0;
            break;
         // invalid status character was input
         default:
            throw new IllegalArgumentException("Type of use must be T, L, or I");
      }

      this.status = status;
   }
   /**
    * Return the char representing the current operation occupying the runway.
    *
    * @return char
    */
   public char getStatus() {
      return this.status;
   }

}//end Runway