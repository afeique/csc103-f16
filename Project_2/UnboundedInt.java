
import java.math.BigInteger;

public class UnboundedInt implements Cloneable {
    int sign, numNodes;
    IntNode head, tail, cursor;

    public UnboundedInt(BigInteger b) {
        this(b.toString());
    }

    public UnboundedInt(String input) {
        // sanitize input String to avoid NumberFormatException

        // assume sign is positive
        this.sign = 1;

        // start with no linked list
        this.head = null;

        // check if negative sign at start of input, set sign accordingly
        if (input.charAt(0) == '-')
            this.sign = -1;

        // use regex to remove all non-digits from input
        // (no scientific notation, kthxbai)
        input = input.replaceAll("[^\\d]+", "");

        int len = input.length();

        // if the string is empty, then no digits were in the input
        if (len == 0)
            throw new NumberFormatException("String must contain one or more digits");

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
        // reverse the input string for traversal
        //System.out.format("Original input: %s\n", input);
        input = new StringBuilder(input).reverse().toString();
        //System.out.format("Reversed input: %s\n", input);

        // traverse the reversed string and create nodes with each group of 3 digits/chars
        // create the linked list in reverse order, starting from the tail
        for (int i=0; i<len; i+=3) {
            // create a char[] to hold this node's value in string format, default=000
            char[] digit = {'0', '0', '0'};

            for (int j=0; j<3; j++) {
                int k = i+j;
                if (k >= len)
                    break;
                digit[2-j] = input.charAt(k);
                //System.out.format("2-j = %d: %s %s %s\n", 2-j, digit[0], digit[1], digit[2]);
            }

            // create a string from the char[]
            nodeStr = new String(digit);
            // parse the integer value from the string
            nodeInt = Integer.parseInt(nodeStr);
            // finally, create a node using the parsed integer value
            newNode = new IntNode(nodeInt, null);

            if (prevNode == null) {
                // if this is the first node we're creating (prevNode null)
                // then maintain a reference to this newNode as the tail
                this.tail = newNode;
            } else {
                // otherwise set this newNode to point to the prevNode
                newNode.setLink(prevNode);
            }

            // set prevNode to the newNode, then continue to next iteration
            prevNode = newNode;
        }

        // set the head
        this.head = prevNode;
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
        IntNode curNode = this.head;
        String out = String.format("%d", curNode.getData());
        curNode = this.head.getLink();
        while (curNode != null) {
            // use String.format() to left-pad with zeros so length is always 3 digits
            out = out + String.format("%03d", curNode.getData());
            curNode = curNode.getLink();
        }

        if (sign == -1)
            out = "-" + out;

        return out;
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

    public UnboundedInt add(UnboundedInt a) {
        BigInteger result = this.toBigInteger().add(a.toBigInteger());
        return new UnboundedInt(result);
    }

    public UnboundedInt multiply(UnboundedInt m) {
        BigInteger result = this.toBigInteger().multiply(m.toBigInteger());
        return new UnboundedInt(result);
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


}