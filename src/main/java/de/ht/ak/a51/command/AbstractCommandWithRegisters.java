/*
 * AbstractCommandWithRegisters.java
 * 
 * Implemented by Danylo Esterman <estermad@hochschule-trier.de>
 * For University of applied sciences Trier, applied cryptology, SS2014
 * 
 */

package de.ht.ak.a51.command;

import org.fusesource.jansi.AnsiConsole;

import de.ht.ak.a51.register.A51Register;
import de.ht.ak.a51.utils.Utilities;


public abstract class AbstractCommandWithRegisters implements Command
{
    A51Register regX, regY, regZ;

    public AbstractCommandWithRegisters(final A51Register regX, final A51Register regY, final A51Register regZ)
    {
        this.regX = regX;
        this.regY = regY;
        this.regZ = regZ;
    }

    private boolean noCorrectKeyEntered(final String input)
    {
        if (input.equals("j"))
        {
            return false;
        }
        if (input.equals("n"))
        {
            return false;
        }
        if (input.equals("w"))
        {
            return false;
        }
        return true;
    }

    @Override
    public boolean execute()
    {
        String input = "";
        boolean executeMore = true;
        while (executeMore)
        {
            final boolean majority = calculateMajority();
            specificExecute(majority);

            System.out.println("Aktueller Stand der Register: \n");
            AnsiConsole.out.println("Register X: " + regX.toAnsi());
            System.out.println();
            AnsiConsole.out.println("Register Y: " + regY.toAnsi());
            System.out.println();
            AnsiConsole.out.println("Register Z: " + regZ.toAnsi());
            System.out.println();

            System.out.println("\nBerechnung noch einmal durchführen? Ja[j] / Nein[n] / Modus wechseln[w]");
            if ((input = Utilities.readFromConsole()) == null)
            {
                System.exit(0);
            }
            while (noCorrectKeyEntered(input))
            {
                System.out.println("\nKeine gültige Eingabe. Berechnung noch einmal durchführen? Ja[j] / Nein[n] / Modus wechseln[w]");
                if ((input = Utilities.readFromConsole()) == null)
                {
                    System.exit(0);
                }
            }
            executeMore = input.equals("j");
        }
        return input.equals("w");
    }

    protected abstract void specificExecute(boolean majority);

    /* @formatter:off
     * 
     * Wertetabelle für das Majority Bit:
     * 
     *  X | Y | Z || m 
     * ----------------
     *  0 | 0 | 0 || 0 
     *  0 | 0 | 1 || 0 
     *  0 | 1 | 0 || 0 
     *  0 | 1 | 1 || 1 
     *  1 | 0 | 0 || 0 
     *  1 | 0 | 1 || 1 
     *  1 | 1 | 0 || 1 
     *  1 | 1 | 1 || 1 
     *
     * => m = y | z     falls x = true
     *        y & z,    sonst
     */
    //@formatter:on
    protected boolean calculateMajority()
    {
        if (regX.getBitForMajority())
        {
            return regY.getBitForMajority() | regZ.getBitForMajority();
        }
        else
        {
            return regY.getBitForMajority() & regZ.getBitForMajority();
        }
    }

    protected boolean generateOutputBit()
    {
        return regX.getLastBit() ^ regY.getLastBit() ^ regZ.getLastBit();
    }

    protected int convertBoolToInt(final boolean b)
    {
        return b ? 1 : 0;
    }

    protected void printGeneratedBit()
    {
        final int lastBitX = convertBoolToInt(regX.getLastBit());
        final int lastBitY = convertBoolToInt(regY.getLastBit());
        final int lastBitZ = convertBoolToInt(regZ.getLastBit());
        final int outputBit = convertBoolToInt(generateOutputBit());
        AnsiConsole.out.print(String.format("Das generierte Bit: %d XOR %d XOR %d = %s%d%s%n%n", lastBitX, lastBitY, lastBitZ, "\u001b[30;47m", outputBit, A51Register.R_STR));
    }

}
