/*
 * VerboseCommand.java
 * 
 * Implemented by Danylo Esterman <estermad@hochschule-trier.de>
 * For University of applied sciences Trier, applied cryptology, SS2014
 * 
 */

package de.ht.ak.a51.command;

import org.fusesource.jansi.AnsiConsole;

import de.ht.ak.a51.register.A51Register;
import de.ht.ak.a51.register.RegisterX;
import de.ht.ak.a51.register.RegisterY;
import de.ht.ak.a51.register.RegisterZ;
import de.ht.ak.a51.utils.Utilities;


public class VerboseCommand extends AbstractCommandWithRegisters
{

    public VerboseCommand(final A51Register regX, final A51Register regY, final A51Register regZ)
    {
        super(regX, regY, regZ);
    }

    @Override
    public void specificExecute(final boolean majority)
    {
        printGeneratedBit();
        Utilities.readFromConsole();

        final int m_1 = convertBoolToInt(regX.getBitForMajority());
        final int m_2 = convertBoolToInt(regY.getBitForMajority());
        final int m_3 = convertBoolToInt(regZ.getBitForMajority());
        AnsiConsole.out.print(String.format("Majority-Bit: m = maj(%s%d%s, %s%d%s, %s%d%s) = %d%n%n", RegisterX.BIT_MAJ_COLOR, m_1, A51Register.R_STR, RegisterY.BIT_MAJ_COLOR, m_2, A51Register.R_STR, RegisterZ.BIT_MAJ_COLOR, m_3, A51Register.R_STR, convertBoolToInt(majority)));
        Utilities.readFromConsole();

        if (regX.getBitForMajority() == majority)
        {
            System.out.println("Register X wird geschoben");
            regX.shift();
        }
        if (regY.getBitForMajority() == majority)
        {
            System.out.println("Register Y wird geschoben");
            regY.shift();
        }
        if (regZ.getBitForMajority() == majority)
        {
            System.out.println("Register Z wird geschoben");
            regZ.shift();
        }
        Utilities.readFromConsole();
    }
}
