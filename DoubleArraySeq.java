// File: DoubleArraySeq.java 

// This is an assignment for students to complete after reading Chapter 3 of
// "Data Structures and Other Objects Using Java" by Michael Main.

// Created by Sydney McDaniel, Yusen Jiang, Afeique Sheikh


import java.lang.*;

/******************************************************************************
* This class is a homework assignment;
* A DoubleArraySeq is a collection of double numbers.
* The sequence can have a special "current element," which is specified and 
* accessed through four methods that are not available in the bag class 
* (start, getCurrent, advance and isCurrent).
*
* @note
*   (1) The capacity of one a sequence can change after it's created, but
*   the maximum capacity is limited by the amount of free memory on the 
*   machine. The constructor, addAfter, 
*   addBefore, clone, 
*   and concatenation will result in an
*   OutOfMemoryError when free memory is exhausted.
*   <p>
*   (2) A sequence's capacity cannot exceed the maximum integer 2,147,483,647
*   (Integer.MAX_VALUE). Any attempt to create a larger capacity
*   results in a failure due to an arithmetic overflow. 
*
* @note
*   This file contains only blank implementations ("stubs")
*   because this is a Programming Project for my students.
*
* @see
*   <A HREF="../../../../edu/colorado/collections/DoubleArraySeq.java">
*   Java Source Code for this class
*   (www.cs.colorado.edu/~main/edu/colorado/collections/DoubleArraySeq.java)
*   </A>
*
* @version
*   March 5, 2002
******************************************************************************/
public class DoubleArraySeq implements Cloneable
{
   // Invariant of the DoubleArraySeq class:
   //   1. The number of elements in the sequences is in the instance variable 
   //      manyItems.
   //   2. For an empty sequence (with no elements), we do not care what is 
   //      stored in any of data; for a non-empty sequence, the elements of the
   //      sequence are stored in data[0] through data[manyItems-1], and we
   //      don�t care what�s in the rest of data.
   //   3. If there is a current element, then it lies in data[currentIndex];
   //      if there is no current element, then currentIndex equals manyItems. 
   private double[ ] data;
   private int manyItems;
   private int currentIndex; 
   
   /**
   * Initialize an empty sequence with an initial capacity of 10.  Note that
   * the addAfter and addBefore methods work
   * efficiently (without needing more memory) until this capacity is reached.
   * @param - none
   * @postcondition
   *   This sequence is empty and has an initial capacity of 10.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for: 
   *   new double[10].
   **/   
   public DoubleArraySeq( )
   {
      int initialCapacity = 10;

      try {
         this.data = new double[initialCapacity];
      } catch (OutOfMemoryError e) {
         throw new OutOfMemoryError("Not enough memory to allocate new double["+ initialCapacity +"]");
      }
      
      this.manyItems = this.currentIndex = 0;
   }
     

   /**
   * Initialize an empty sequence with a specified initial capacity. Note that
   * the addAfter and addBefore methods work
   * efficiently (without needing more memory) until this capacity is reached.
   * @param initialCapacity
   *   the initial capacity of this sequence
   * @precondition
   *   initialCapacity is non-negative.
   * @postcondition
   *   This sequence is empty and has the given initial capacity.
   * @exception IllegalArgumentException
   *   Indicates that initialCapacity is negative.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for: 
   *   new double[initialCapacity].
   **/   
   public DoubleArraySeq(int initialCapacity)
   {
      if (initialCapacity < 0) 
         throw new IllegalArgumentException("initialCapacity of DoubleArraySeq must be nonnegative");
      
      try {
         this.data = new double[initialCapacity];
      } catch (OutOfMemoryError e) {
         throw new OutOfMemoryError("Not enough memory to allocate new double["+ initialCapacity +"]");
      }
      
      this.manyItems = this.currentIndex = 0;
   }
   
 
   /**
   * Add a new element to this sequence, after the current element. 
   * If the new element would take this sequence beyond its current capacity,
   * then the capacity is increased before adding the new element.
   * @param element
   *   the new element that is being added
   * @postcondition
   *   A new copy of the element has been added to this sequence. If there was
   *   a current element, then the new element is placed after the current
   *   element. If there was no current element, then the new element is placed
   *   at the end of the sequence. In all cases, the new element becomes the
   *   new current element of this sequence. 
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for increasing the sequence's capacity.
   * @note
   *   An attempt to increase the capacity beyond
   *   Integer.MAX_VALUE will cause the sequence to fail with an
   *   arithmetic overflow.
   **/
   public void addAfter(double element)
   {
      if (this.manyItems == this.data.length)
         this.ensureCapacity(this.manyItems + 1);
         
      if (this.currentIndex == this.manyItems) {
         this.data[this.manyItems] = element;
      }
      else {
         this.data[this.currentIndex] = element;
      }

      this.manyItems++;
   }


   /**
   * Add a new element to this sequence, before the current element. 
   * If the new element would take this sequence beyond its current capacity,
   * then the capacity is increased before adding the new element.
   * @param element
   *   the new element that is being added
   * @postcondition
   *   A new copy of the element has been added to this sequence. If there was
   *   a current element, then the new element is placed before the current
   *   element. If there was no current element, then the new element is placed
   *   at the start of the sequence. In all cases, the new element becomes the
   *   new current element of this sequence. 
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for increasing the sequence's capacity.
   * @note
   *   An attempt to increase the capacity beyond
   *   Integer.MAX_VALUE will cause the sequence to fail with an
   *   arithmetic overflow.
   **/
   public void addBefore(double element)
   {
      // ensure the array is large enough
      this.ensureCapacity(this.manyItems + 1);
      
      // if the currentIndex is pointing at nothing,
      // point it back to the beginning of the sequence
      if (this.currentIndex == this.manyItems) {
         this.currentIndex = 0;
      }

      if (this.manyItems > 0) {
         // shift data in sequence right, from currentIndex up manyItems-1 (end of sequence)
         System.arraycopy(this.data, this.currentIndex, this.data, this.currentIndex+1, this.manyItems - this.currentIndex);
      }

      // insert new data into currentIndex
      this.data[this.currentIndex] = element;
      this.manyItems++;
   }
   
   
   /**
   * Place the contents of another sequence at the end of this sequence.
   * @param addend
   *   a sequence whose contents will be placed at the end of this sequence
   * @precondition
   *   The parameter, addend, is not null. 
   * @postcondition
   *   The elements from addend have been placed at the end of 
   *   this sequence. The current element of this sequence remains where it 
   *   was, and the addend is also unchanged.
   * @exception NullPointerException
   *   Indicates that addend is null. 
   * @exception OutOfMemoryError
   *   Indicates insufficient memory to increase the size of this sequence.
   * @note
   *   An attempt to increase the capacity beyond
   *   Integer.MAX_VALUE will cause an arithmetic overflow
   *   that will cause the sequence to fail.
   **/
   public void addAll(DoubleArraySeq addend)
   {
      int prevNumItems = this.manyItems;
      int newNumItems = prevNumItems + addend.manyItems;
      this.ensureCapacity(newNumItems);
      System.arraycopy(addend.data, 0, this.data, prevNumItems, addend.manyItems);
   }   
   
   
   /**
   * Move forward, so that the current element is now the next element in
   * this sequence.
   * @param - none
   * @precondition
   *   isCurrent() returns true. 
   * @postcondition
   *   If the current element was already the end element of this sequence 
   *   (with nothing after it), then there is no longer any current element. 
   *   Otherwise, the new element is the element immediately after the 
   *   original current element.
   * @exception IllegalStateException
   *   Indicates that there is no current element, so 
   *   advance may not be called.
   **/
   public void advance( )
   {
      if (this.currentIndex == this.data.length)
         this.currentIndex = manyItems;
      else
         this.currentIndex = this.currentIndex + 1;
   }
   
   
   /**
   * Generate a copy of this sequence.
   * @param - none
   * @return
   *   The return value is a copy of this sequence. Subsequent changes to the
   *   copy will not affect the original, nor vice versa.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for creating the clone.
   **/ 
   public DoubleArraySeq clone( )
   {  // Clone a DoubleArraySeq object.
      DoubleArraySeq answer;
      
      try
      {
         answer = (DoubleArraySeq) super.clone( );
      }
      catch (CloneNotSupportedException e)
      {  // This exception should not occur. But if it does, it would probably
         // indicate a programming error that made super.clone unavailable.
         // The most common error would be forgetting the "Implements Cloneable"
         // clause at the start of this class.
         throw new RuntimeException
         ("This class does not implement Cloneable");
      }
      
      answer.data = data.clone( );
      
      return answer;
   }
   

   /**
   * Create a new sequence that contains all the elements from one sequence
   * followed by another.
   * @param s1
   *   the first of two sequences
   * @param s2
   *   the second of two sequences
   * @precondition
   *   Neither s1 nor s2 is null.
   * @return
   *   a new sequence that has the elements of s1 followed by the
   *   elements of s2 (with no current element)
   * @exception NullPointerException.
   *   Indicates that one of the arguments is null.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for the new sequence.
   * @note
   *   An attempt to create a sequence with a capacity beyond
   *   Integer.MAX_VALUE will cause an arithmetic overflow
   *   that will cause the sequence to fail.
   **/   
   public static DoubleArraySeq concatenation(DoubleArraySeq s1, DoubleArraySeq s2)
   {
      if (s1 == null)
         throw new NullPointerException("Cannot concatenate null DoubleArraySequence s1");
      if (s2 == null)
         throw new NullPointerException("Cannot concatenate null DoubleArraySequence s2");
      
      int newSize = s1.manyItems + s2.manyItems;
      DoubleArraySeq newSeq = new DoubleArraySeq(newSize);
      System.arraycopy(s1.data, 0, newSeq.data, 0, s1.manyItems);
      System.arraycopy(s2.data, 0, newSeq.data, s1.manyItems, s2.manyItems);

      return newSeq;
   }


   /**
   * Change the current capacity of this sequence.
   * @param minimumCapacity
   *   the new capacity for this sequence
   * @postcondition
   *   This sequence's capacity has been changed to at least minimumCapacity.
   *   If the capacity was already at or greater than minimumCapacity,
   *   then the capacity is left unchanged.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for: new int[minimumCapacity].
   **/
   public void ensureCapacity(int minimumCapacity)
   {
      double [] biggerArray;
      
      if (this.data.length < minimumCapacity) {
         int newSize = (minimumCapacity+1)*2;
         int delta = newSize - minimumCapacity;

         // delta_limit is ...
         // Double.SIZE = 64 bits / 8 bits/byte = 8 bytes
         // 4 KiB = 2048 bytes / 8 bytes/double = 256 doubles
         int delta_limit = 2048/(Double.SIZE/8);

         // let's assume we need to allocate a bigger array to
         // increase the capacity, but there is not enough
         // memory to allocate (minimumCapacity+1)*2
         // => an OutOfMemoryException would be thrown

         // we can catch that OutOfMemoryException the first
         // time it is thrown using a try/catch block

         // then we can try to allocate some number greater than
         // minimumCapacity, but less than 2*(minimumCapacity+1)

         // a decent choice for newSize is halfway between:
         // minimumCapacity and
         // 2*(minimumCapacity+1)
         // i.e. at delta/2

         // What if when we try and allocate:
         // minimumCapacity + delta/2
         // we get another OutOfMemoryException?

         // we can just wrap the entire try/catch block in
         // a while(true) block so it will continuously
         // keep trying to allocate while catching and halving
         // the delta on every OutOfMemoryException iteration

         // if we manage to eventually successfully increase the size
         // of the array, simply break the loop and continue
         // (and maybe throw a party, idk)

         // Eventually, at a particular delta limit
         // (we're using 2 KiB = 256 doubles)
         // it's probably meaningless to continue

         // at that point we should despair and throw the freakin'
         // exception already

         // like WELP I TRIED MY BEST LIFE SUCKS O WELL
         // I'M JUST GOING TO GO STARE AT THE CORNER NOW
         // I LIKE THE COBWEBS OVER THERE
         // THEY MAKE ME FEEL WANTED

         while (true) {
            try {
               biggerArray = new double[newSize];
               break;
            } catch (OutOfMemoryError e) {
               if (delta <= delta_limit)
                  throw e;
               newSize = minimumCapacity + (delta/2);
               delta = newSize - minimumCapacity;
            }
         }
         System.arraycopy(data, 0, biggerArray, 0, this.manyItems);
         this.data = biggerArray;
      }  
   }

   
   /**
   * Accessor method to get the current capacity of this sequence. 
   * The add method works efficiently (without needing
   * more memory) until this capacity is reached.
   * @param - none
   * @return
   *   the current capacity of this sequence
   **/
   public int getCapacity( )
   {
      if (this.data == null)
         return 0;
      return this.data.length;
   }


   /**
   * Accessor method to get the current element of this sequence. 
   * @param - none
   * @precondition
   *   isCurrent() returns true.
   * @return
   *   the current element of this sequence
   * @exception IllegalStateException
   *   Indicates that there is no current element, so 
   *   getCurrent may not be called.
   **/
   public double getCurrent( )
   {
      if ( ! this.isCurrent() )
           throw new IllegalStateException("No current element, getCurrent() method cannot be called");
      return this.data[this.currentIndex];
   }


   /**
   * Accessor method to determine whether this sequence has a specified 
   * current element that can be retrieved with the 
   * getCurrent method. 
   * @param - none
   * @return
   *   true (there is a current element) or false (there is no current element at the moment)
   **/
   public boolean isCurrent( )
   {
      if (this.currentIndex == this.manyItems)
         return false;
      return true;
   }
              
   /**
   * Remove the current element from this sequence.
   * @param - none
   * @precondition
   *   isCurrent() returns true.
   * @postcondition
   *   The current element has been removed from this sequence, and the 
   *   following element (if there is one) is now the new current element. 
   *   If there was no following element, then there is now no current 
   *   element.
   * @exception IllegalStateException
   *   Indicates that there is no current element, so 
   *   removeCurrent may not be called. 
   **/
   public void removeCurrent( )
   {
      if ( ! this.isCurrent() )
         throw new IllegalStateException("No current element, removeCurrent() method cannot be called");
      else if (this.currentIndex+1 == this.manyItems)
         this.manyItems--;
      else
         this.data[this.currentIndex] = this.data[this.currentIndex + 1];
   }
                 
   
   /**
   * Determine the number of elements in this sequence.
   * @param - none
   * @return
   *   the number of elements in this sequence
   **/ 
   public int size( )
   {
      return this.manyItems;
   }
   
   
   /**
   * Set the current element at the front of this sequence.
   * @param - none
   * @postcondition
   *   The front element of this sequence is now the current element (but 
   *   if this sequence has no elements at all, then there is no current 
   *   element).
   **/ 
   public void start( )
   {
      this.currentIndex = 0;
   }

   public void end()
   {
   	  this.currentIndex = this.manyItems-1;
   }
   
   
   /**
   * Reduce the current capacity of this sequence to its actual size (i.e., the
   * number of elements it contains).
   * @param - none
   * @postcondition
   *   This sequence's capacity has been changed to its current size.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for altering the capacity. 
   **/
   public void trimToSize( )
   {
      double[ ] trimmedArray;
      
      if (data.length != this.manyItems)
      {
         trimmedArray = new double[this.manyItems];
         System.arraycopy(data, 0, trimmedArray, 0, this.manyItems);
         data = trimmedArray;
      }
   }
   
   /**
   * Add a new element to the front of this sequence. 
   * If the new element would take this sequence beyond its current capacity,
   * then the capacity is increased before adding the new element.
   * @param element
   *   the new element that is being added
   * @postcondition
   *   The element has been added to the front of this sequence.
   *   The currentIndex has been returned to what it was before the method call.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for increasing the sequence's capacity.
   * @note
   *   An attempt to increase the capacity beyond
   *   Integer.MAX_VALUE will cause the sequence to fail with an
   *   arithmetic overflow.
   **/
   public void addFront(double element) {
      int prevIndex = this.currentIndex;
      this.currentIndex = 0;
      this.addBefore(element);
      this.currentIndex = prevIndex;
   }
   
   /**
   * Add a new element to this sequence, after the last element. 
   * If the new element would take this sequence beyond its current capacity,
   * then the capacity is increased before adding the new element.
   * @param element
   *   The new element that is being added.
   * @postcondition
   *   The element has been added after the last element in the sequence.
   *   The currentIndex is returned to what it was before the method call.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for increasing the sequence's capacity.
   * @note
   *   An attempt to increase the capacity beyond
   *   Integer.MAX_VALUE will cause the sequence to fail with an
   *   arithmetic overflow.
   **/

   public void addEnd(double element) {
      int prevIndex = this.currentIndex;
      this.setCurrentLast();
      this.addAfter(element);
      this.currentIndex = prevIndex;
   }
   
   
   /**
   * Remove the first element of the sequence.
   * @precondition
   *   There is at least one item in the sequence.
   * @postcondition
   *   The first element was removed and the other elements were shifted left.
   **/
   public void removeFront() {
      if (this.manyItems > 0)
         System.arraycopy(this.data, 1, this.data, 0, --this.manyItems-1);
   }

   /**
   * Set the last element of the array as the current element
   * @postcondition
   *   The current element has been set to the last element in the array
   * @exception IllegalStateException
   *   Indicates if the array is empty
   **/
   public void setCurrentLast( ) {
      this.currentIndex = this.manyItems - 1;
   }


   /**
   * Sets the cursor to the Nth element of the sequence and returns that element.
   * @postcondition
   *   The current element has been set to the Nth element of the sequence.
   * @exception IllegalStateException
   *   Indicates if the array is empty or if there are not N elements in the array.
   **/
   public double getElement(int n) {
      this.setCurrent(n);
      return this.getCurrent();
   }

   /**
   * Sets the cursor to the Nth element of the sequence
   * @postcondition
   *   The current element has been set to the Nth element of the sequence.
   * @exception IllegalStateException
   *   Indicates if the array is empty or if there are not N elements in the array.
   **/
   public void setCurrent(int n) {
      if (n >= this.manyItems) {
         throw new IllegalStateException("Fewer than "+ ++n +" elements in sequence");

      }
      if (this.manyItems == 0)
         throw new IllegalStateException("Empty sequence");
      this.currentIndex = n;
   }

   /**
   * Sets the cursor to the Nth element of the sequence
   * @return
   *    true or false depending on whether the sequences are equal
   **/
   public boolean equals(Object o) {
      if (o instanceof DoubleArraySeq) {
         DoubleArraySeq c = (DoubleArraySeq) o;
         if (c.data == null) {
            if (this.data == null)
               return true;
            return false;
         }
         for (int i=0; i<c.data.length; i++) {
            if (c.data[i] != this.data[i])
               return false;
         }
      }
      return true;
   }

   /**
   * Sets the cursor to the Nth element of the sequence
   * @return
   *    string containing the concatenated output of the sequence
   **/
   public String toString() {
      String s = "[ ";
      if (this.manyItems == 0)
      	return "[]";
      for (int i=0; i<this.manyItems; i++) {
         s += String.format("  %.2f", this.data[i]);
      }
      s += " ]";
      return s;
   }
}
