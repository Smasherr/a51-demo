/*
 * RegisterY.java
 * 
 * Implemented by Danylo Esterman <estermad@hochschule-trier.de>
 * For University of applied sciences Trier, applied cryptology, SS2014
 * 
 */

package de.ht.ak.a51.register;

public class RegisterY extends A51Register
{
    static final int LENGTH = 22;

    static final int BIT_MAJ = 10;

    static final int SHIFT_BITS[] =
    { 20, 21 };

    public static final String BIT_MAJ_COLOR = "\u001b[32m";

    public RegisterY(final long key)
    {
        super((int) ((key << RegisterX.LENGTH) >>> (Long.SIZE - LENGTH)), LENGTH, BIT_MAJ, BIT_MAJ_COLOR, SHIFT_BITS);
    }

}
