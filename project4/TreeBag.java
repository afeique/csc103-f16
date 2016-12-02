// File: TreeBag.java 

// The implementation of most methods in this file is left as a student
// exercise from Section 9.5 of "Data Structures and Other Objects Using Java"


/******************************************************************************
* This class is a homework assignment;
* An <code>TreeBag</code> is a collection of int numbers.
*
* <dl><dt><b>Limitations:</b> <dd>
*   Beyond <code>Integer.MAX_VALUE</code> elements, <code>countOccurrences</code>,
*   and <code>size</code> are wrong. 
*
* <dt><b>Note:</b><dd>
*   This file contains only blank implementations ("stubs")
*   because this is a Programming Project for my students.
*
* @version
*   Jan 24, 2016
* @author
*   Afeique Sheikh, Sydney McDaniel, Yusen Jiang
******************************************************************************/
public class TreeBag<E extends Comparable> implements Cloneable
{
   // The Term E extends Comparable is letting the compiler know that any type
   // used to instantiate E must implement Comparable. i. e. that means that whatever
   // type E is must have a compareTo method so that elements can be compared against one another
   // This is required becuase we are doing comparisons in our methods


   // Invariant of the TreeBag class:
   //   1. The elements in the bag are stored in a binary search tree.
   //   2. The instance variable root is a reference to the root of the
   //      binary search tree (or null for an empty tree).
   private BTNode<E> root;


   /**
   * Insert a new <code>element</code> into this bag. Works by encapsulating <code>element</code>
   * in a new node and then using the overloaded <code>add</code> method to add the node.
   * @param element
   *   generic reference to the element being added
   * <dt><b>Postcondition:</b><dd>
   *   A new copy of the element has been added to this bag.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory to create a new IntBTNode.
   **/
   public void add(E element)
   {
      // create a new node containing the element
      if (element == null)
         return;
      BTNode<E> node = new BTNode<E>(element, null, null);
      this.add(node);
   }

   /**
   * Insert a node into this bag.
   * @param node
   *   reference to the new generic node being added
   * <dt><b>Postcondition:</b><dd>
   *   A new copy of the element has been added to this bag.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory a new BTNode.
   **/
   public void add(BTNode<E> node)
   {
      if (node == null)
         return;
      
      E element = node.getData();

      // if there is no root node (null), set the new node as root
      if (this.root == null) {
         this.root = node;
      } 
      else {
         // else if there is a root node,
         // traverse the tree looking for a suitable null leaf node

         // references to the previous (parent) node and current (child) node
         BTNode<E> prevNode = null;
         BTNode<E> curNode = this.root; // start at the root node

         // traverse the tree 

         // while the current node is not null,
         while (curNode != null) {

            // if the new node is <= the current node, branch left
            if ( element.compareTo(curNode.getData()) <= 0 ) {
               // update current and previous node references to branch left
               prevNode = curNode;
               curNode = curNode.getLeft();
            } 
            // else if the new node is > the current node, branch right
            else if ( element.compareTo(curNode.getData()) > 0) {
               // update current and previous node references to branch right
               prevNode = curNode;
               curNode = curNode.getRight();
            }
         }

         // at this point, current node (child) is null
         // previous node (parent) is not null
         // null child node means we've located a leaf node to store the element
         // use the parent node to add the new node
         if ( element.compareTo(prevNode.getData()) <= 0 ) {
            prevNode.setLeft(node);
         } 
         else if ( element.compareTo(prevNode.getData()) > 0 ) {
            prevNode.setRight(node);
         }
      }
   }

   /**
   * Retrieve a specified element from this bag.
   * @param target
   *   the element to locate in the bag
   * @return 
   *  the return value is a reference to the found element in the tree
   * <dt><b>Postcondition:</b><dd>
   *   If <code>target</code> was found in the bag, then method returns
   *   a reference to a comparable element. If the target was not found then
   *   the method returns null.
   *   The bag remains unchanged.
   **/
   public E retrieve(E target)
   {
      // if the root is not null, there are nodes that can be removed
      if (this.root != null) {

         // references to the current (child) node and previous (parent) node
         BTNode<E> prevNode = null;
         BTNode<E> curNode = this.root;

         // whether the current node is the left or right branch of the parent
         boolean isLeft = false;
         boolean isRight = false;

         while (curNode != null) {
            // if the target is < current node, branch left
            if (target.compareTo(curNode.getData()) < 0) {
               prevNode = curNode;
               curNode = curNode.getLeft();
               isLeft = true;
               isRight = false;
            } 
            // else if the target is > current node, branch right
            else if (target.compareTo(curNode.getData()) > 0) {
               prevNode = curNode;
               curNode = curNode.getRight();
               isLeft = false;
               isRight = true;
            } 
            // else if the target == current node, we've found the correct node
            else if (target.compareTo(curNode.getData()) == 0) {
               // return the data for the node we found
               return curNode.getData();
            }
         }
      }

      // if we get here, either root was null OR
      // we searched the whole tree but found no match
      return null;
   }

   
   /**
   * Remove one copy of a specified element from this bag.
   * @param target
   *   the element to remove from the bag
   * <dt><b>Postcondition:</b><dd>
   *   If <code>target</code> was found in the bag, then one copy of
   *   <code>target</code> has been removed and the method returns true. 
   *   Otherwise the bag remains unchanged and the method returns false. 
   **/
   public boolean remove(E target)
   {
      // if the root is not null, there are nodes that can be removed
      if (this.root != null) {

         // references to the current (child) node and previous (parent) node
         BTNode<E> prevNode = null;
         BTNode<E> curNode = this.root;

         // whether the current node is the left or right branch of the parent
         boolean isLeft = false;
         boolean isRight = false;

         while (curNode != null) {

            // if the target is < current node, branch left
            if (target.compareTo(curNode.getData()) < 0) {
               prevNode = curNode;
               curNode = curNode.getLeft();
               isLeft = true;
               isRight = false;
            } 
            // else if the target is > current node, branch right
            else if (target.compareTo(curNode.getData()) > 0) {
               prevNode = curNode;
               curNode = curNode.getRight();
               isLeft = false;
               isRight = true;
            } 
            // else if the target == current node, remove the current node
            else if (target.compareTo(curNode.getData()) == 0) {
               // get references to the left and right of current node
               BTNode<E> left = curNode.getLeft();
               BTNode<E> right = curNode.getRight();

               // if current node's left is not null
               if (left != null) {
                  // if the current node is left of its parent node
                  if (isLeft) {
                     // set the parent node's left to the child node's left
                     prevNode.setLeft(left);
                  } 
                  // else if the current node is right of its parent node,
                  else if (isRight) {
                     // set the parent node's right to the child node's left
                     prevNode.setRight(left);
                  }

                  // if the current node also has a right node,
                  // add it back to the tree
                  if (right != null) {
                     this.add(right);
                  }
               } 
               // else if current node's left is null, but its right is not null
               else if (right != null) {
                  // if the current node is left of its parent node
                  if (isLeft) {
                     // set the parent node's left to child node's right
                     prevNode.setLeft(right);
                  } 
                  // else if the current node is right of its parent node
                  else if (isRight) {
                     // set parent node's right to child node's right
                     prevNode.setRight(right);
                  }
               } 
               // else the current node is a leaf node and has no children
               else {
                  // if the current node is left of its parent node
                  if (isLeft) {
                     // set the parent node's left to null
                     prevNode.setLeft(null);
                  } 
                  // else if the current node is right of its parent node
                  else if (isRight) {

                     // set the parent node's right to null
                     prevNode.setRight(null);
                  }
               }

               // return true indicating we removed an element
               return true;
            }
         }
      }

      // if we get here, either root was null OR
      // we searched the whole tree but found no match
      return false;
   }
   
   /**
   * Displays the entire tree of Node elements in the order specified
   * by the element's compareTo method
   * 
   * @param 
   *   none
   * <dt><b>Postcondition:</b><dd>
   *   Outputs all elements in the tree to Screen.
   *   Does not change the structure 
   **/
   public void display()
   {
      System.out.println(this.toString());
   }

   /**
   * Returns a string containing the entire tree of Node elements in 
   * the order specified by the element's compareTo method. Passes
   * a <code>StringBuilder</code> object to the overloaded recursive
   * <code>toString</code> method. This <code>StringBuilder</code>
   * then contains the final output.
   * <dt><b>Postcondition:</b><dd>
   *   Outputs all elements in the tree to Screen.
   *   Does not change the structure 
   **/
   public String toString() {
      StringBuilder out = new StringBuilder();

      if (this.root != null) 
         this.toString(this.root, out);

      return out.toString();
   }

   /**
   * Recursively traverses the tree in-order as specified by the
   * generic <code>element</code>'s <code>compareTo</code> method. 
   * Builds a string containing the in-order output of every element
   * using the passed-in <code>StringBuilder</code> object. This
   * method does not return anything; instead, the <code>StringBuilder</code>
   * object will contain the final output.
   * <dt><b>Postcondition:</b><dd>
   *   Outputs all elements in the tree to Screen.
   *   Does not change the structure 
   **/
   public void toString(BTNode<E> node, StringBuilder out) 
   {
      if (node == null)
         return;

      BTNode<E> left = node.getLeft();
      BTNode<E> right = node.getRight();
      
      // print out left subtree
      if (left != null) {
         this.toString(left, out);
      }
      
      // print out current node
      out.append(node.getData().toString());
      out.append("\n");
      
      // print out right subtree
      if (right != null) {
         this.toString(right, out);
      }
   }
     
   /**
   * Displays the entire tree of Node elements using the
   * built in print method of BTNode
   * which displays the entire tree in tree format
   * 
   * @param 
   *   none
   * <dt><b>Postcondition:</b><dd>
   *   Outputs all elements in the tree to Screen.
   *   Does not change the structure 
   **/   
   public void displayAsTree() {
      root.print(0);
   }
      
   
   
   /**
   * Generate a copy of this bag.
   * @param - none
   * @return
   *   The return value is a copy of this bag. Subsequent changes to the
   *   copy will not affect the original, nor vice versa. Note that the return
   *   value must be type cast to an <code>TreeBag</code> before it can be used.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for creating the clone.
   **/ 
   public TreeBag<E> clone( )
   {  // Clone an IntTreeBag object.
      // Student will replace this return statement with their own code:
      return null; 
   } 

   /**
   * Accessor method to count the number of occurrences of a particular element
   * in this bag.
   * @param target
   *   the element that needs to be counted
   * @return
   *   the number of times that <code>target</code> occurs in this bag
   **/
   public int countOccurrences(E target)
   {
      // Student will replace this return statement with their own code:
      return 0;
   }
   
       
   /**
   * Determine the number of elements in this bag.
   * @param - none
   * @return
   *   the number of elements in this bag
   **/                           
   public int size( )
   {
      return BTNode.treeSize(this.root);
   }




   /**
   * Add the contents of another bag to this bag.
   * @param addend
   *   a bag whose contents will be added to this bag
   * <dt><b>Precondition:</b><dd>
   *   The parameter, <code>addend</code>, is not null.
   * <dt><b>Postcondition:</b><dd>
   *   The elements from <code>addend</code> have been added to this bag.
   * @exception IllegalArgumentException
   *   Indicates that <code>addend</code> is null.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory to increase the size of the bag.
   **/
   public void addAll(TreeBag<E> addend)
   {
      // Implemented by student.
   }
   
   /**
   * Create a new bag that contains all the elements from two other bags.
   * @param b1
   *   the first of two bags
   * @param b2
   *   the second of two bags
   * <dt><b>Precondition:</b><dd>
   *   Neither b1 nor b2 is null.
   * @return
   *   the union of b1 and b2
   * @exception IllegalArgumentException
   *   Indicates that one of the arguments is null.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for the new bag.
   **/   
   public static TreeBag union(TreeBag b1, TreeBag b2)
   {
      // Student will replace this return statement with their own code:
      return null;
   }
      
}
           
