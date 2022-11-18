package it.unibo.mvc;

import java.util.List;

/**
 *  Model a simple Controller responsible of I/O access.
 */
public interface Controller {
    /**
     * Set the next string to print.
     * @param nextString
     */
    void setNextString(String nextString);

    /**
     * @return the next string to print
     */
    String getNextString();

    /**
     *
     * @return the history of the printed strings in form of List<String>
     */
    List<String> getPrintedStrings();

    /**
     * Prints the current string.
     */
    void printCurrentString();
}
