package com.jvalkucak.bsc;

/**
 * Utilities class
 */
public class Utils {

    /**
     * Checks if input string is numeric
     * @param str Input String
     * @return True if input string is numeric, else false
     */
    public static boolean isNumeric(String str) {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }

        return true;
    }
}
