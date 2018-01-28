/*
 * StreamCommand.java
 * 
 * Implemented by Danylo Esterman <estermad@hochschule-trier.de>
 * For University of applied sciences Trier, applied cryptology, SS2014
 * 
 */

package de.ht.ak.a51.command;

import de.ht.ak.a51.register.A51Register;
import de.ht.ak.a51.utils.Utilities;

public class StreamCommand extends AbstractCommandWithRegisters implements Command
{

    public StreamCommand(final A51Register regX, final A51Register regY, final A51Register regZ)
    {
        super(regX, regY, regZ);
    }

    @Override
    protected void specificExecute(final boolean majority)
    {
        System.out.println("Geben Sie Anzahl ausgegebener Bits ein.");
        System.out.println("(0 oder nichts Eingeben für unbegrentze Ausgabe, Abbruch nur mit ^C");
        String input = null;
        boolean inputUnsuccessful = true;
        int bitCount = 0;
        while (inputUnsuccessful)
        {
            if ((input = Utilities.readFromConsole()) == null)
            {
                System.exit(0);
            }
            inputUnsuccessful = false;
            if (!input.equals(""))
            {
                try
                {
                    bitCount = Integer.parseInt(input);
                }
                catch (final NumberFormatException e)
                {
                    System.out.println("Bitte eine Zahl eingeben.");
                    inputUnsuccessful = true;
                }
            }
        }
        if (bitCount == 0)
        {
            while (true)
            {
                printAndShift(majority);
            }
        }
        else
        {
            for (int i = 0; i < bitCount; i++)
            {
                printAndShift(majority);
            }
            System.out.println();
            System.out.println();
        }
    }

    private void printAndShift(boolean majority)
    {
        System.out.print(convertBoolToInt(generateOutputBit()));
        majority = calculateMajority();
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
