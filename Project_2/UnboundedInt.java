
import java.math.BigInteger;

public class UnboundedInt {
    private int numNodes;
    private IntNode head, tail, cursor;

    private UnboundedInt() {
        // start with no linked list
        this.head = null;
        this.tail = null;
        this.numNodes = 0;
        // we ain't got nothin' to point to
        this.cursor = null;
    }

    /**
     * Overloaded constructor that takes a java.math.BigInteger and uses that
     * to set the value of the UnboundedInt.
     *
     * @param BigInteger
     *   The BigInteger with the value that this UnboundedInt should be initialized to.
     */
    public UnboundedInt(BigInteger b) {
        this(b.toString());
    }

    public UnboundedInt(String input) {
        // initialize instance vars
        this();

        // sanitize input String to avoid NumberFormatException

        // check if negative sign at start of input, set sign accordingly
        int sign = 1;
        if (input.charAt(0) == '-')
            sign = -1;

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
     * A re-implementation of the Integer.parseInt() static method.
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

    public void start() {
        this.cursor = this.head;
    }

    public void advance() {
        if (this.cursor == null)
            throw new IllegalStateException("Cursor is null (not pointing to an IntNode)");

        this.cursor = this.cursor.getLink();
    }

    public int getNodeValue() {
        if (this.cursor == null)
            throw new IllegalStateException("Cursor is null (not pointing to an IntNode)");

        return this.cursor.getData();
    }

    public UnboundedInt clone() {
        return new UnboundedInt(this.toString());
    }

    public static BigInteger toBigInteger(UnboundedInt u) {
        return new BigInteger(u.toString());
    }

    public static UnboundedInt fromBigInteger(BigInteger b) {
        return new UnboundedInt(b.toString());
    }

    public BigInteger toBigInteger() {
        return new BigInteger(this.toString());
    }

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
     * Add another UnboundedInt to the current one, returning a new UnboundedInt object as the result.
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
        int overflow, sumNode, overflowNode;
        String sumStr;

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
            } catch (IllegalStateException e) {
                val[0] = 0;
            }
            try {
                val[1] = u.cursor.getData();
            } catch (IllegalStateException e) {
                val[1] = 0;
            }

            sumNode = sum.cursor.getData() + val[0] + val[1];
            overflowNode = sum.cursor.getLink().getData();

            // use integer division to determine if there is overflow
            overflow = Math.abs(sumNode/1000);

            //System.out.format("sum[%d]: %d\noverflow: %d\n", i, sum[i], overflow);

            // handle the overflow and carry over
            if (overflow > 0) {
                if (sumNode < 0) {
                    sumNode += 1000;
                    overflowNode -= 1;
                } else {
                    sumNode -= 1000;
                    overflowNode += 1;
                }
            }

            // commit our local variables to the sum IntNodes
            sum.cursor.setData(sumNode);
            sum.cursor.getLink().setData(overflowNode);

            // advance the sum's cursor to the next node (which is the current overflow node)
            sum.advance();
            sum.tail = new IntNode(0, null);
            sum.cursor.setLink(sum.tail);
            sum.numNodes += 1;

            //System.out.format("sum[%d]: %d\nsum[%d+1]: %d\n", i, sum[i], i, sum[i+1]);

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

        /*
        sumStr = UnboundedInt.resultArrayToString(sum);
        return new UnboundedInt(sumStr);
        */
        return sum;
    }

    public void trim() {
        while (this.tail.getData() == 0) {
            if (this.numNodes == 1)
                break;
            this.start();
            while (this.cursor.getLink() != this.tail) {
                this.advance();
            }
            this.cursor.setLink(null);
            this.tail = this.cursor;
            this.numNodes -= 1;
        }
    }

    public UnboundedInt multiply(UnboundedInt u) {
        // multiplication of an N digit/node number by an M digit/node number
        // results in a product of at most N+M digits
        // example: 99*99 = 9801 (2+2 = 4 digits)
        int prodNumNodes = this.numNodes + u.numNodes;
        int[] prod = new int[prodNumNodes];


        System.out.format("this.numNodes: %d\nu.numNodes: %d\nprodNumNodes: %d\n", this.numNodes, u.numNodes, prodNumNodes);
        return new UnboundedInt("0");
    }

}