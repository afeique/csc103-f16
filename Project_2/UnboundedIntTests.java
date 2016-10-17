
import java.math.BigInteger;

public class UnboundedIntTests {
	public static void main(String[] args) {
		UnboundedInt u1, u2, usum, uprod, clone;
		BigInteger b1, b2, bsum, bprod;
		String[] val;

		String[][] testValues = {
			{"9999", "9999"},
			{"-9999", "-9999"},
			{"+123,456,789", "+987,654,321"},
			{"-123,456,789", "-987,654,321"},
			{"-1", "-1"},
			{"-1", "+1"},
			{"123", "456"},
			{"-123", "-456"},
			{"+hello1", "-hello2"}
		};

		boolean r;

		System.out.println("\nStarting Tests...\n\n");

		for (int i=0; i<testValues.length; i++) {
			val = testValues[i];
			System.out.format("TEST VALUES: %s, %s\n", val[0], val[1]);
			System.out.format("============\n\n");

			u1 = new UnboundedInt(val[0]);
			System.out.format("Created UnboundedInt u1 = %s\n", u1.toString());
			u2 = new UnboundedInt(val[1]);
			System.out.format("Created UnboundedInt u2 = %s\n", u2.toString());
			usum = u1.add(u2);
			System.out.format("u1+u2 = %s\n", usum.toString());
			uprod = u1.multiply(u2);
			System.out.format("u1*u2 = %s\n", uprod.toString());

			System.out.println("");

			b1 = new BigInteger(u1.toString());
			System.out.format("Created BigInteger b1 = %s\n", b1.toString());
			b2 = new BigInteger(u2.toString());
			System.out.format("Created BigInteger b2 = %s\n", b2.toString());
			bsum = b1.add(b2);
			System.out.format("b1+b2 = %s\n", bsum.toString());
			bprod = b1.multiply(b2);
			System.out.format("b1*b2 = %s\n", bprod.toString());

			System.out.println("");

			r = u1.equals(b1);
			assert r;
			System.out.format("u1==b1 :: %s == %s : %s\n", u1, b1, r);
			r = u2.equals(b2);
			assert r;
			System.out.format("u2==b2 :: %s == %s : %s\n", u2, b2, r);
			System.out.println("");


			r = usum.equals(bsum);
			assert r;
			System.out.format("u1+u2 == b1+b2 :: %s == %s : %s\n", usum, bsum, r);
			r = uprod.equals(bprod);
			assert r;
			System.out.format("u1*u2 == b1*b2 :: %s == %s : %s\n", uprod, bprod, r);
			System.out.println("");

			clone = u1.clone();
			r = u1.equals(clone);
			assert r;
			System.out.format("u1.clone() == u1 :: %s == %s : %s\n", clone, u1, r);
			clone = u2.clone();
			r = u2.equals(clone);
			assert r;
			System.out.format("u2.clone() == u2 :: %s == %s : %s\n", clone, u2, r);
			System.out.println("");

			/*
			r = u1.equals(u2);
			System.out.format("u1==u2 :: %s == %s : %s\n", u1, u2, r);
			r = b1.equals(b2);
			System.out.format("b1==b2 :: %s == %s : %s\n", b1, b2, r);
			r = u1.equals(b2);
			System.out.format("u1==b2 :: %s == %s : %s\n", u1, b2, r);
			r = u2.equals(b1);
			System.out.format("u2==b1 :: %s == %s : %s\n", u2, b1, r);
			r = b1.equals(u2);
			System.out.format("b1==u2 :: %s == %s : %s\n", b1, u2, r);
			r = b2.equals(u1);
			System.out.format("b2==u1 :: %s == %s : %s\n", b2, u1, r);
			System.out.println("");
			*/
		}
			
	}
}