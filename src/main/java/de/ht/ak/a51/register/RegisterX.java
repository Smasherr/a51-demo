/*
 * RegisterX.java
 * 
 * Implemented by Danylo Esterman <estermad@hochschule-trier.de>
 * For University of applied sciences Trier, applied cryptology, SS2014
 * 
 */

package de.ht.ak.a51.register;

public class RegisterX extends A51Register
{
    static final int LENGTH = 19;

    static final int BIT_MAJ = 8;

    static final int SHIFT_BITS[] =
    { 13, 16, 17, 18 };

    public static final String BIT_MAJ_COLOR = "\u001b[31m";

    public RegisterX(final long key)
    {
        super((int) (key >>> (Long.SIZE - LENGTH)), LENGTH, BIT_MAJ, BIT_MAJ_COLOR, SHIFT_BITS);
    }
}
