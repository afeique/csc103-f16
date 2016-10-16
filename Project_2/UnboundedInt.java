
public class UnboundedInt {
    byte sign;
    IntNode head;

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
        input = input.replaceAll("[^\\d]+", "");

        int len = input.length();

        // if the string is empty, then no digits were in the input
        if (len == 0)
            throw new NumberFormatException("String must contain numeric digits");

        // each node contains a 3-digit number 0-999:
        // if we take the input string's length, divide by 3,
        // and ceil the result, that will give us the number of nodes needed
        int numNodes = (int)Math.ceil(((double)len)/3);
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
                // index in input string of current digit
                int k = i-j;
                // if k is negative, we're past the end (leftmost char) of the input string
                // break out of this inner loop
                if (k < 0)
                    break;

                // otherwise, if k is positive, overwrite the digit in the char[]
                // i-2 = 0th digit (j=2)
                // i-1 = 1st digit (j=1)
                // i-0 = 2nd digit (j=0)
                // so the digit we should overwrite is 2-j
                digit[2-j] = input.charAt(k);
            }

            // create a string from the char[]
            nodeStr = new String(digit);
            // parse the integer value from the string
            nodeInt = Integer.parseInt(nodeStr);
            // finally, create a node using the parsed integer value
            newNode = new IntNode(nodeInt, null);
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
    }
}