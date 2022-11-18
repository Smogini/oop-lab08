package it.unibo.mvc;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 
 */
public final class SimpleController implements Controller {
    private final List<String> stringHistory = new LinkedList<>();
    private String nextString;

    /**
     * Set the next string to print.
     * @param nextString
     */
    @Override
    public void setNextString(final String nextString) {
        this.nextString = Objects.requireNonNull(nextString, "The string should not be null");
    }

    /**
     * @return the next string to print
     */
    @Override
    public String getNextString() {
        return this.nextString;
    }

    /**
     *
     * @return the history of the printed strings in form of List<String>
     */
    @Override
    public List<String> getPrintedStrings() {
        return new LinkedList<>(this.stringHistory);
    }

    /**
     * Prints the current string.
     */
    @Override
    public void printCurrentString() {
        if (this.nextString == null) {
            throw new IllegalStateException("The string is not set");
        }
        System.out.println(this.nextString); // NOPMD: allowed in exercises
    }
}
