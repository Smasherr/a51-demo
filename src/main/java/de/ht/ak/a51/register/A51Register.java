/*
 * A51Register.java
 * 
 * Implemented by Danylo Esterman <estermad@hochschule-trier.de>
 * For University of applied sciences Trier, applied cryptology, SS2014
 * 
 */

package de.ht.ak.a51.register;

public abstract class A51Register
{
    private int data;

    private final int length;

    private final int bitForMajority;

    private final String bitForMajorityColor;

    private final int bitsForShift[];

    public final static String R_STR = "\u001b[0m";

    public A51Register(final int key, final int length, final int bitForMajority, final String bitForMajorityColor, final int bitsForShift[])
    {
        this.data = key;
        this.length = length;
        this.bitForMajority = bitForMajority;
        this.bitsForShift = bitsForShift;
        this.bitForMajorityColor = bitForMajorityColor;
    }

    public String toAnsi()
    {
        final String indentedString = String.format("%" + length + "s", Long.toBinaryString(data)).replace(' ', '0');
        return indentedString.substring(0, bitForMajority) + bitForMajorityColor + indentedString.substring(bitForMajority, bitForMajority + 1) + "\u001b[0m" + indentedString.substring(bitForMajority + 1, length);
    }

    public boolean getBitForMajority()
    {
        final int mask = 1 << (length - bitForMajority - 1);
        // System.out.println(String.format("%" + length + "s",
        // Integer.toBinaryString(mask)).replace(' ', '0'));
        // System.out.println(String.format("%" + length + "s",
        // Integer.toBinaryString(data)).replace(' ', '0'));
        // System.out.println(String.format("%" + length + "s",
        // Integer.toBinaryString((data & mask))).replace(' ', '0'));
        return (data & mask) == mask;
    }

    public boolean getLastBit()
    {
        return (data & 1) == 1;
    }

    public void shift()
    {
        boolean newValue = false;
        for (final int element : bitsForShift)
        {
            final int mask = 1 << (length - element - 1);
            newValue ^= (data & mask) == mask;
        }
        data >>>= 1;
        if (newValue)
        {
            data |= 1 << (length - 1);
        }
    }

}
