/*
 * Utilities.java
 * 
 * Implemented by Danylo Esterman <estermad@hochschule-trier.de>
 * For University of applied sciences Trier, applied cryptology, SS2014
 * 
 */

package de.ht.ak.a51.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import de.ht.ak.a51.input.NonClosingInputStream;


public class Utilities
{
    public static String readFromConsole()
    {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new NonClosingInputStream())))
        {
            return br.readLine();
        }
        catch (final Exception e)
        {
            System.err.println("Konnte nicht aus dem STDIN lesen: " + e.getMessage());
            return null;
        }
    }
}
