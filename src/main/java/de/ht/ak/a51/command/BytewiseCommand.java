/*
 * BytewiseCommand.java
 * 
 * Implemented by Danylo Esterman <estermad@hochschule-trier.de>
 * For University of applied sciences Trier, applied cryptology, SS2014
 * 
 */

package de.ht.ak.a51.command;

import de.ht.ak.a51.register.A51Register;

public class BytewiseCommand extends AbstractCommandWithRegisters
{

    public BytewiseCommand(final A51Register regX, final A51Register regY, final A51Register regZ)
    {
        super(regX, regY, regZ);
    }

    @Override
    public void specificExecute(boolean majority)
    {
        System.out.print("Das generierte Byte: ");
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++)
        {
            sb.append(convertBoolToInt(generateOutputBit()));
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
            majority = calculateMajority();
        }
        System.out.println(sb.toString() + "\n");
    }
}
