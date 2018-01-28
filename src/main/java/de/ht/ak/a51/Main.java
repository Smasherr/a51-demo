/*
 * Implementation of A5/1 steam cipher
 * Uses JANSI (http://jansi.fusesource.org/)
 * 
 * By Danylo Esterman <estermad@hochschule-trier.de>
 * For University of applied sciences Trier, applied cryptology, SS2014
 * 
 */

package de.ht.ak.a51;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.fusesource.jansi.AnsiConsole;

import de.ht.ak.a51.command.BitwiseCommand;
import de.ht.ak.a51.command.BytewiseCommand;
import de.ht.ak.a51.command.Command;
import de.ht.ak.a51.command.StreamCommand;
import de.ht.ak.a51.command.VerboseCommand;
import de.ht.ak.a51.register.A51Register;
import de.ht.ak.a51.register.RegisterX;
import de.ht.ak.a51.register.RegisterY;
import de.ht.ak.a51.register.RegisterZ;
import de.ht.ak.a51.utils.Utilities;


public class Main
{
    final static byte KEY_LENGTH = 8;

    public static void main(final String[] args)
    {
        if (System.console() == null)
        {
            System.setProperty("jansi.passthrough", "true");
        }
        else
        {
            try
            {
                System.setOut(new java.io.PrintStream(System.out, true, "CP850"));
            }

            catch (final java.io.UnsupportedEncodingException e)
            {
                System.out.println(e);
            }
        }

        System.out.println("Ein Tool zur Demonstration der Stromchiffre A5/1");
        System.out.println("Bitte geben Sie einen Schlüssel ein (max. " + KEY_LENGTH + " Zeichen)");
        System.out.println("oder zeilenweise 3 Register in binärer Darstellung");
        System.out.println();
        String input = null;
        String input2 = null;
        String input3 = null;
        if ((input = Utilities.readFromConsole()) == null)
        {
            System.exit(0);
        }
        long key = 0L;
        if (isInputProperRegister(input, 19))
        {
            try
            {

                if (((input2 = Utilities.readFromConsole()) == null) || !isInputProperRegister(input2, 22))
                {
                    throw new Exception();
                }
                if (((input3 = Utilities.readFromConsole()) == null) || !isInputProperRegister(input3, 23))
                {
                    throw new Exception();
                }
                key = Long.parseUnsignedLong(input + input2 + input3, 2);
            }
            catch (final Exception e)
            {
                System.out.println("Die Eingabe war nicht korrekt.");
                System.exit(0);
            }
        }
        else
        {
            if (input.length() == 0)
            {
                System.out.println("Kein Schlüssel angegeben, beende das Programm.");
                System.exit(0);
            }
            else if (input.length() > KEY_LENGTH)
            {
                System.out.println("Der eingegebene Schlüssel ist zu lang und wird auf 8 Zeichen gekürzt.");
                input = input.substring(0, KEY_LENGTH);
            }

            for (int i = 0; i < input.length(); i++)
            {
                final char singleCharacter = input.charAt(i);
                key <<= 8;
                key |= singleCharacter;
            }

            if (input.length() < 8)
            {
                System.out.println("Der eingegebene Schlüssel ist zu kurz und wird auf 8 Bytes mit Zufallswerten erweitert.");
                final long randomLong = new Random().nextLong() >>> (Long.SIZE - ((KEY_LENGTH - input.length()) * Byte.SIZE));
                key <<= (KEY_LENGTH - input.length()) * Byte.SIZE;
                key |= randomLong;
            }
        }
        System.out.println();
        System.out.println("Schlüssel in binärer Darstellung:");
        if (!isInputProperRegister(input, 19))
        {
            for (int i = 0; i < input.length(); i++)
            {
                System.out.print(input.charAt(i) + "        ");
            }
            System.out.println();
            for (int i = KEY_LENGTH - 1; i >= 0; i--)
            {
                final int indent = KEY_LENGTH * (KEY_LENGTH - i);
                final String indentedKeyPart = String.format("%" + indent + "s", Long.toBinaryString(key >> (i * Byte.SIZE))).replace(' ', '0');
                System.out.print(indentedKeyPart.substring((KEY_LENGTH - i - 1) * Byte.SIZE) + " ");
            }
            System.out.println();
        }
        else
        {
            System.out.println(input.substring(0, 8) + " " + input.substring(8, 16) + " " + input.substring(16, 19) + input2.substring(0, 5) + " " + input2.substring(5, 13) + " " + input2.substring(13, 21) + " " + input2.substring(21, 22) + input3.substring(0, 7) + " " + input3.substring(7, 15) + " " + input3.substring(15, 23));
        }
        System.out.println();

        System.out.println("Initialisiere Register...");
        System.out.println();
        final A51Register regX = new RegisterX(key);
        final A51Register regY = new RegisterY(key);
        final A51Register regZ = new RegisterZ(key);

        AnsiConsole.out.println("Register X: " + regX.toAnsi());
        System.out.println();
        AnsiConsole.out.println("Register Y: " + regY.toAnsi());
        System.out.println();
        AnsiConsole.out.println("Register Z: " + regZ.toAnsi());
        System.out.println();

        final Map<Integer, Command> commandMap = new HashMap<>();
        commandMap.put(1, new VerboseCommand(regX, regY, regZ));
        commandMap.put(2, new BitwiseCommand(regX, regY, regZ));
        commandMap.put(3, new BytewiseCommand(regX, regY, regZ));
        commandMap.put(4, new StreamCommand(regX, regY, regZ));

        boolean changeMode = true;
        while (changeMode)
        {
            System.out.println("Wählen Sie einen Modus aus: ");
            System.out.println("1    : Ausführlich");
            System.out.println("2    : Bitweise");
            System.out.println("3    : Byteweise");
            System.out.println("4    : Strom erzeugen");
            System.out.println("Sonst: Beenden");
            input = Utilities.readFromConsole();
            int selection = -1;
            try
            {
                selection = Integer.parseInt(input);
            }
            catch (final Exception e)
            {
            }
            if (changeMode = commandMap.containsKey(selection))
            {
                System.out.println();
                changeMode = commandMap.get(selection).execute();
            }
        }
    }

    private static boolean isInputProperRegister(final String input, final int length)
    {
        return (input.matches("[01]{" + length + "}"));
    }
}
