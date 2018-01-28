/*
 * RegisterZ.java
 * 
 * Implemented by Danylo Esterman <estermad@hochschule-trier.de>
 * For University of applied sciences Trier, applied cryptology, SS2014
 * 
 */

package de.ht.ak.a51.register;

public class RegisterZ extends A51Register
{
    static final int LENGTH = 23;

    static final int MAJ_BIT = 10;

    static final int SHIFT_BITS[] =
    { 7, 20, 21, 22 };

    public static final String BIT_MAJ_COLOR = "\u001b[35m";

    public RegisterZ(final long key)
    {
        super((int) ((key << (RegisterX.LENGTH + RegisterY.LENGTH)) >>> (Long.SIZE - LENGTH)), LENGTH, MAJ_BIT, BIT_MAJ_COLOR, SHIFT_BITS);
    }
}
