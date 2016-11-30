
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

public class UnboundedInt {
    private int numNodes;
    private IntNode head, tail, cursor;
    
    
    /**
     * Private constructor for initializing class variables in other public 
     * constructors.
     * 
     * @param none
     * @postcondition
     *   UnboundedInt object properties are instantiated to null or 0.
     */
    private UnboundedInt() {
        // start with no linked list`
        this.head = null;
        this.tail = null;
        this.numNodes = 0;
        // we ain't got nothin' to point to
        this.cursor = null;
    }

    /**
     * Public constructor that takes a java.math.BigInteger and uses that
     * to set the value of this UnboundedInt object. Used for testing with BigIntegers.
     *
     * @param BigInteger
     *   The BigInteger with the value that this UnboundedInt should be initialized to.
     * @postcondition
     *   UnboundedInt object is instantiated to the same value as the input BigInteger.
     */
    public UnboundedInt(BigInteger b) {
        this(b.toString());
    }

    /**
     * Public constructor that takes a string input and uses that to initialize
     * the value of this UnboundedInt object.
     *
     * @param String
     *   The numeric value that UnboundedInt should be initialized to.
     * @precondition
     *   The input string has at least one numeric digit.
     * @postcondition
     *   UnboundedInt object is equal to the parsed numeric value of the input String.
     */
    public UnboundedInt(String input) {
        // initialize instance vars
        this();

        // check if negative sign at start of input, set sign accordingly
        int sign = 1;
        if (input.charAt(0) == '-')
            sign = -1;

        // sanitize input String to avoid NumberFormatException
        // use regex to remove all non-digits from input
        input = input.replaceAll("[^\\d]+", "");

        int len = input.length();

        // if the string is empty, then no digits were in the input
        if (len == 0)
            throw new NumberFormatException("String must contain numeric digits");

        // each node contains a 3-digit number 0-999:
        // if we take the input string's length, divide by 3,
        // and ceil the result, that will give us the number of nodes needed
        this.numNodes = (int)Math.ceil(((double)len)/3);
        String nodeStr;
        int nodeInt;
        int n = 0;
        IntNode prevNode = null;
        IntNode newNode = null;

        // now we need to loop through the input string BACKWARDS
        // this is because the ones place starts at the end of the string (rightmost char)
        // use i to loop backwards through the input string, hopping 3 digits each iteration
        // create a new node each iteration of i
        for (int i=len-1; i>-1; i-=3) {
            // create a char[] to hold this node's value in string format, default=000
            char[] digit = {'0', '0', '0'};

            // loop through i-2, i-1, i-0 (in that order)
            // loop j through 2, 1, 0 (in that order)
            // so k = i-j will give us the index in the input string for the current digit
            for (int j=2; j>-1; j--) {
                // current digit's index in the original input string
                int k = i-j;
                // if k is negative, we're past the end (leftmost char) of the input string
                if (k < 0)
                    continue;

                // otherwise, if k is positive, overwrite the digit in the char[]
                // the digit we should overwrite is at index 2-j
                digit[2-j] = input.charAt(k);
            }

            // create a string from the char[]
            nodeStr = new String(digit);
            // parse the integer value from the string
            nodeInt = Integer.parseInt(nodeStr);
            // finally, create a node using the parsed integer value
            newNode = new IntNode(sign*nodeInt, null);
            if (prevNode == null) {
                // if this is the first node we're creating (prevNode null)
                // then maintain a reference to this newNode as the head
                this.head = newNode;
            } else {
                // otherwise set the prevNode to point to this newNode
                prevNode.setLink(newNode);
            }

            // set prevNode to the newNode, then continue to next iteration
            prevNode = newNode;
        }

        this.tail = prevNode;
    }

    /**
     * Loop through IntNodes and concatenate values together to form complete numeric string
     * representing the number encapsulated by this UnboundedInt.
     *
     * @param none
     * @return
     *   String containing number encapsulated by this UnboundedInt
     */
    public String toString() {
        String out, nodeStr;
        out = "";

        // the nodes are stored from least significant to most significant digits
        // thus, we have to prepend each successive node to the running String out
        // the tail node represents the most significant digits: it should NOT be rendered with left-padded zeros
        this.start();
        while (this.cursor != null) {
            if (this.cursor == this.tail) {
                // if the node is the tail (most significant digits),
                // render it without any left-padded zeros
                //System.out.format("\ntoStr: on tail node: %d\n", curNode.getData());
                nodeStr = String.format("%d", Math.abs(this.cursor.getData()));
            } else {
                // if the node is not the tail (most significant digits),
                // use String.format() to left-pad with zeros so length is always 3 digits
                //System.out.format("\ntoStr: on normal node: %03d\n", curNode.getData());
                nodeStr = String.format("%03d", Math.abs(this.cursor.getData()));
            }

            out = nodeStr + out;
            //System.out.format("\nout: %s\n", out);
            this.advance();
        }

        // prepend with a negative sign if tail (most significant digits/node) is negative
        if (this.tail.getData() < 0)
            out = "-" + out;

        return out;
    }

    /**
     * A re-implementation of the Integer.parseInt() static method that is 
     * more flexible.
     *
     * @param input
     *   The string to convert to an integer
     * @precondition
     *   String input contains one or more numeric digits
     */ 
    public static int parseInt(String input) {
        // sanitize input String

        // assume sign is positive
        int sign = 1;

        // check if negative sign at start of input, set sign accordingly
        if (input.charAt(0) == '-')
            sign = -1;

        // use regex to remove all non-digits from input
        // (no scientific notation, kthxbai)
        input = input.replaceAll("[^\\d]+", "");

        int len = input.length();

        // if the string is empty, then no digits were in the input
        if (len == 0)
            throw new NumberFormatException("String must contain one or more digits");

        char digit;
        int asciiCode;
        int pwr = 0;
        int num = 0;

        // loop through input string right-to-left (least significant digit to most significant)
        for (int i=len-1; i>-1; i--) {
            // get char digit at current position
            digit = input.charAt(i);
            // cast digit char as int to get ascii code
            asciiCode = (int)digit;

            // convert asciiCode to actual int digit, multiply digit by 10^(pwr), add to num
            num += UnboundedInt.asciiDigitLookup(asciiCode) * Math.pow(10, pwr++);
        }

        return sign*num;
    }

    /**
     * Lookup and return the numeric digit an asciiCode represents as an int.
     * Throw an IllegalArgumentException if the asciiCode input does not represent a numeric digit.
     *
     * @param asciiCode
     *   Primitive int representing the ASCII code to look up.
     * @precondition
     *   Input asciiCode is the code for a numeric digit [0-9], i.e. within range [48-57]
     * @return
     *   The digit the asciiCode input represents as an integer.
     * @exception IllegalArgumentException
     *   Thrown if the asciiCode input does not represent a numeric digit (code outside of range [48-57])
     */
    public static int asciiDigitLookup(int asciiCode) {
        // ascii code 48 = digit 0; ascii code 57 = digit 9
        // by subtracting the asciiCode input from 48, we get the exact int digit it represents
        int digit = asciiCode - 48;

        // make sure the digit is within bounds
        if (digit < 0 || digit > 9)
            throw new IllegalArgumentException("ASCII code "+ asciiCode +" does not correspond to a digit [0-9]");

        return digit;
    }

    /**
     * Point this UnboundedInt object's cursor to its head IntNode.
     *
     * @param none
     * @postcondition
     *   UnboundedInt object's cursor is pointed at head IntNode.
     */
    public void start() {
        this.cursor = this.head;
    }

    /**
     * Point this UnboundedInt object's cursor at the next IntNode in
     * the linked list (the link of the current IntNode).
     *
     * @param none
     * @precondition
     *   Cursor is not-null and points to an existing IntNode.
     * @postcondition
     *   Cursor points at the next IntNode in the linked list. Cursor
     *   becomes null if the current IntNode is the tail.
     */
    public void advance() {
        if (this.cursor == null)
            throw new IllegalStateException("Cursor is null (not pointing to an IntNode)");

        this.cursor = this.cursor.getLink();
    }

    /**
     * Get the value of the current IntNode the UnboundedInt object's cursor is pointing at.
     *
     * @param none
     * @precondition
     *   The cursor is not-null and points at an existing IntNode.
     * @return int
     *   The primitive int value of the current IntNode the cursor is pointing at.
     */
    public int getNodeValue() {
        if (this.cursor == null)
            throw new IllegalStateException("Cursor is null (not pointing to an IntNode)");

        return this.cursor.getData();
    }

    /**
     * Clone this UnboundedInt object.
     *
     * @param none
     * @precondition
     *   This UnboundedInt object exists.
     * @postcondition
     *   A new UnboundedInt instance with the same value as this one is created in
     *   memory and a reference to that object is returned.
     * @return UnboundedInt
     */
    public UnboundedInt clone() {
        return new UnboundedInt(this.toString());
    }

    /**
     * Create a BigInteger that has the same value as an UnboundedInt object.
     * 
     * @param UnboundedInt
     *   The UnboundedInt object whose value should be used.
     * @return BigInteger
     *   The newly created BigInteger that has the same value as the argument.
     * @precondition
     *   An UnboundedInt object is required.
     * @postcondition
     *   A new BigInteger object is created.
     */
    public static BigInteger toBigInteger(UnboundedInt u) {
        return new BigInteger(u.toString());
    }

    /**
     * Create an UnboundedInt that has the same value as a BigInteger object.
     * 
     * @param BigInteger
     *   The BigInteger object whose value should be used.
     * @return UnboundedInt
     *   The newly created UnboundedInt that has the same value as the argument.
     * @precondition
     *   A BigInteger object is required.
     * @postcondition
     *   A new UnboundedInt object is created.
     */
    public static UnboundedInt fromBigInteger(BigInteger b) {
        return new UnboundedInt(b.toString());
    }

    /**
     * Return this UnboundedInt object's value as a BigInteger object.
     * 
     * @param none
     * @return BigInteger
     *   The newly created BigInteger that has the same value as the argument.
     * @precondition
     *   This UnboundedInt object exists.
     * @postcondition
     *   A new BigInteger object is created.
     */
    public BigInteger toBigInteger() {
        return new BigInteger(this.toString());
    }

    /**
     * Check if this UnboundedInt object is equal to another object. The other
     * object can be an UnboundedInt or a BigInteger.
     * 
     * @param Object
     *   The other numeric object to check equality with.
     * @return boolean
     *   True if this UnboundedInt equals the argument in value.
     * @precondition
     *   The argument represents a numeric value. False is returned
     *   for equality if the argument is a non-numeric String.
     */
    public boolean equals(Object o) {
        if (o instanceof UnboundedInt) {
            UnboundedInt u = (UnboundedInt)o;
            if (this.toBigInteger().equals(u.toBigInteger()))
                return true;
            return false;
        } else if (o instanceof BigInteger) {
            BigInteger b = (BigInteger)o;
            if (this.toBigInteger().equals(b))
                return true;
            return false;
        }
        return false;
    }


    /**
     * Add this UnboundedInt with another UnboundedInt. 
     * Return a new UnboundedInt with the sum.
     *
     * @param UnboundedInt
     *   The other UnboundedInt to add.
     * @return UnboundedInt
     *   A new UnboundedInt containing the sum of the two UnboundedInts.
     */
    public UnboundedInt add(UnboundedInt u) {
        int[] val = {0, 0};
        // addition between an N digit/node number and an M digit/node number
        // results in a sum of at most max(N,M)+1 digits/nodes
        // ex: 99+999 = 1098 (2 digits, 3 digits => max(2,3)+1 = 4 digits)
        int sumNumNodes = Math.max(this.numNodes, u.numNodes)+1;
        UnboundedInt sum = new UnboundedInt();
        int overflow, sumNode, overflowNode, complement;

        // If the addend has a different number of nodes than this, at some point one of the linked lists
        // will reach its end while the other will have more nodes to traverse.

        // Thus, one of the linked lists will have its cursor=null, and will start throwing exceptions
        // as we try to continue advancing and trying to retrieve the cursor node's value.
        
        // However, because these Exceptions are expected, we'll isolate them using try/catch blocks
        // and ignore them, continuing operation as expected.

        //System.out.format("this.numNodes: %d\nu.numNodes: %d\nsumNumNodes: %d\n", this.numNodes, u.numNodes, sumNumNodes);

        // create the first two nodes of the sum
        sum.head = new IntNode(0, new IntNode(0, null));
        sum.tail = sum.head.getLink();
        sum.numNodes = 2;
        // position sum's cursor at the head
        sum.start();

        // start each linked list at its respective head
        this.start(); u.start();

        for (int i=0; i<sumNumNodes-1; i++) {

            // get the node values, handling expected Exceptions
            try {
                val[0] = this.cursor.getData();
            } catch (NullPointerException e) {
                val[0] = 0;
            }
            try {
                val[1] = u.cursor.getData();
            } catch (NullPointerException e) {
                val[1] = 0;
            }

            // if the two signs are opposing, add using complements
            sumNode = sum.cursor.getData() + val[0] + val[1];
            overflowNode = sum.cursor.getLink().getData();
            

            // max possible overflow from addition is 1000:
            // 999+999 = 1998

            // handle the overflow and carry over

            //System.out.format("\n\n\n");
            //System.out.format("Original node value: %d\n", sum.cursor.getData());
            //System.out.format("%d + %d = %d\n", val[0], val[1], val[0]+val[1]);
            //System.out.format("New node value: %d\n", sumNode);

            //System.out.format("Overflow node value before potential overflow: %d\n", overflowNode);
            if (sumNode >= 1000) {
                sumNode -= 1000;
                overflowNode += 1;
                //System.out.format(">> OVERFLOW OCCURRED\n");
                //System.out.format("Node value after overflow: %d\n", sumNode);
                //System.out.format("Overflow node value after overflow: %d\n", overflowNode);
            } else if (sumNode <= -1000) {
                sumNode += 1000;
                overflowNode -= 1;
                //System.out.format(">> OVERFLOW OCCURRED\n");
                //System.out.format("Node value after overflow: %d\n", sumNode);
                //System.out.format("Overflow node value after overflow: %d\n", overflowNode);
            } else {
                //System.out.format("No overflow occurred\n");
            }

            // commit our local variables to the sum IntNodes
            sum.cursor.setData(sumNode);
            sum.cursor.getLink().setData(overflowNode);

            // advance the sum's cursor to the next node (which is the current overflow node)
            sum.advance();
            sum.tail = new IntNode(0, null);
            sum.cursor.setLink(sum.tail);
            sum.numNodes += 1;

            // advance the linked list cursors, handling expected Exceptions
            try {
                this.advance();
            } catch (IllegalStateException e) {
                // do nothing
            }
            try {
                u.advance();
            } catch (IllegalStateException e) {
                // do nothing
            }
        }

        // trim off unused (zero) nodes at the end
        sum.trim();

        //
        // !!! IMPORTANT !!!
        // Using the sign of the sum (sign of the most significant/tail node), \
        // make sure all the nodes have the same sign. 
        // If the nodes do not have the same sign, perform borrow-carries to ensure they do.
        // 
        // If this is not performed, then adding positives to negatives fails ~50% of the time.
        int sign = 1;
        if (sum.tail.getData() < 0)
            sign = -1;

        sum.start();
        while (sum.cursor != sum.tail) {
            sumNode = sum.cursor.getData();
            overflowNode = sum.cursor.getLink().getData();

            if (sign < 0 && sum.cursor.getData() > 0) {
                sumNode -= 1000;
                overflowNode += 1;
            } else if (sign > 0 && sum.cursor.getData() < 0) {
                sumNode += 1000;
                overflowNode -= 1;
            }

            sum.cursor.setData(sumNode);
            sum.cursor.getLink().setData(overflowNode);
            sum.advance();
        }

        // perform another trim() in case we had to borrow from the most significant node and it was = -1.
        // Such a borrow would result in a most significant node (tail) = 0.
        sum.trim();

        return sum;
    }

    /**
     * Trim zero nodes starting at the tail node and traversing backwards.
     * This removes left-padded (extraneous) zeros, i.e. 000470 -> 470
     *
     * @param none
     * @precondition
     *   More than one node.
     * @postcondition
     *   A linked list with no zero nodes around the tail.
     */
    public void trim() {
        while (this.tail.getData() == 0) {
            if (this.numNodes == 1)
                break;
            this.seek(--this.numNodes-1);
            this.cursor.setLink(null);
            this.tail = this.cursor;
        }
    }

    /**
     * Seek to a particular node within the linked list.
     * Throws an IllegalArgumentException if not that many nodes are present.
     *
     * @param int
     *   The position to seek to. The head node is position 0.
     * @precondition
     *   Enough nodes to seek to the specified position.
     * @postcondition
     *   The cursor points at the desired node.
     * @exception IllegalArgumentException
     *   Thrown when a nonexistent position argument is given.
     */
    public void seek(int n) {
        this.start();
        if (n >= this.numNodes || n < 0)
            throw new IllegalArgumentException("Node n must be within bounds: [0, "+ this.numNodes +"]");
        //System.out.println("Seeking to: "+ n);
        for (int i=0; i<n; i++) {
            //System.out.println("Current node "+ i +" = "+ this.cursor.getData());
            this.advance();
        }
    }

    /**
     * Multiply this UnboundedInt object with another UnboundedInt.
     * Return a new UnboundedInt with the product.
     *
     * @param UnboundedInt
     *   The UnboundedInt to multiply by.
     * @return UnboundedInt
     *   Product of the multiply operation.
     */
    public UnboundedInt multiply(UnboundedInt u) {
        int[] val = {0, 0};
        // multiplication of an N digit/node number by an M digit/node number
        // results in a product of at most N+M digits
        // example: 99*99 = 9801 (2+2 = 4 digits)
        UnboundedInt prod = new UnboundedInt();
        int overflow, prodNode, overflowNode;
        
        // create the head node
        prod.numNodes = this.numNodes + u.numNodes;
        prod.head = new IntNode(0, null);
        prod.cursor = prod.head;

        // preallocate prod linked list
        for (int i=1; i<prod.numNodes; i++) {
            prod.cursor.setLink(new IntNode(0, null));
            prod.advance();
        }

        // the last element we preallocate is the tail
        prod.tail = prod.cursor;

        // position the two UnboundedInts we are multiplying
        this.start();
        //System.out.println("\n\n");
        for (int i=0; i<this.numNodes; i++) {
            val[0] = this.cursor.getData();
            prod.seek(i);
            u.start();
            for (int j=0; j<u.numNodes; j++) {
                val[1] = u.cursor.getData();
                
                
                prodNode = prod.cursor.getData() + (val[0] * val[1]);
                //System.out.format("Original Node %d value: %d\n", i+j, prod.cursor.getData());
                //System.out.format("%d x %d = %d\n", val[0], val[1], (val[0] * val[1]));
                //System.out.format("New node %d value: %d\n", i+j, prodNode + prod.cursor.getData());
                overflowNode = prod.cursor.getLink().getData();
                
                // use integer division to determine if there is overflow
                overflow = Math.abs(prodNode/1000);

                // set the data if there 
                if (overflow > 0) {
                    //System.out.format("Overflow from node %d: %d\n", i+j, overflow);
                    // max possible overflow from multiplication is 998,000
                    // handle the overflow and carry over
                    if (prodNode < 0) {
                        prodNode += overflow*1000;
                        overflowNode -= overflow;
                    } else if (prodNode > 0) {
                        prodNode -= overflow*1000;
                        overflowNode += overflow;
                    }
                   
                }
                
                prod.cursor.setData(prodNode);
                prod.cursor.getLink().setData(overflowNode);
                
                //System.out.println("\n\n");
                prod.advance();
                u.advance();
            }
            this.advance();
        }

        // trim off unused (zero) nodes at the end
        prod.trim();
        
        //System.out.format("Num nodes in product: %d\n", prod.numNodes);

        return prod;
    }

}
