/*
 * BitwiseCommand.java
 * 
 * Implemented by Danylo Esterman <estermad@hochschule-trier.de>
 * For University of applied sciences Trier, applied cryptology, SS2014
 * 
 */

package de.ht.ak.a51.command;

import de.ht.ak.a51.register.A51Register;

public class BitwiseCommand extends AbstractCommandWithRegisters
{

    public BitwiseCommand(final A51Register regX, final A51Register regY, final A51Register regZ)
    {
        super(regX, regY, regZ);
    }

    @Override
    public void specificExecute(final boolean majority)
    {
        printGeneratedBit();
        if (regX.getBitForMajority() == majority)
        {
            regX.shift();
        }
        if (regY.getBitForMajority() == majority)
        {
            regY.shift();
        }
        if (regZ.getBitForMajority() == majority)
        {
            regZ.shift();
        }
    }
}
