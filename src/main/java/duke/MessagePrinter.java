package duke;

import duke.command.DeadlineCommand;
import duke.command.MarkCommand;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Represents the UI of the Duke.
 */
public class MessagePrinter {
    /**
     * The indentation level.
     */
    private int indentationLevel;

    /**
     * The length of horizontal lines around the message.
     */
    private int horizontalLineLength;

    /**
     * The symbol in horizontal lines.
     */
    private char horizontalLineSymbol;

    /**
     * Set the indentation level to given value.
     * @param indentationLevel
     */
    private void setIndentationLevel(int indentationLevel) {
        this.indentationLevel = indentationLevel;
    }

    /**
     * Set the horizontal line length to given value.
     * @param horizontalLineLength
     */
    private void setHorizontalLineLength(int horizontalLineLength) {
        this.horizontalLineLength = horizontalLineLength;
    }

    /**
     * Set the horizontal line symbol to given character.
     * @param horizontalLineSymbol
     */
    private void setHorizontalLineSymbol(char horizontalLineSymbol) {
        this.horizontalLineSymbol = horizontalLineSymbol;
    }

    /**
     * Returns the length of horizontal lines around the message.
     * @return The length of horizontal lines around the message.
     */
    public int getHorizontalLineLength() {
        return horizontalLineLength;
    }

    /**
     * Returns the symbol of the horizontal line.
     * @return The symbol of the horizontal line.
     */
    public char getHorizontalLineSymbol() {
        return horizontalLineSymbol;
    }

    /**
     * Returns the indentation level.
     * @return The indentation level.
     */
    public int getIndentationLevel() {
        return indentationLevel;
    }

    /**
     * The default constructor of the MessagePrinter.
     */
    public MessagePrinter() {
        int defaultIndentationLevel = 3;
        int defaultHorizontalLineLength = 50;
        char defaultHorizontalLineSymbol = '-';
        setIndentationLevel(defaultIndentationLevel);
        setHorizontalLineLength(defaultHorizontalLineLength);
        setHorizontalLineSymbol(defaultHorizontalLineSymbol);
    }

    /**
     * The constructor to customize the behavior of the Message Printer.
     * @param indentationLevel The indentation level of the Message Printer.
     * @param horizontalLineLength The horizontal line length of the Message Printer.
     * @param horizontalLineSymbol The symbol of horizontal line of the Message Printer.
     */
    public MessagePrinter(int indentationLevel, int horizontalLineLength, char horizontalLineSymbol) {
        setIndentationLevel(indentationLevel);
        setHorizontalLineLength(horizontalLineLength);
        setHorizontalLineSymbol(horizontalLineSymbol);
    }

    /**
     * Returns the indentation in String.
     * @return The the indentation in String.
     */
    private String getIndentation() {
        return Stream.generate(() -> " ").limit(this.indentationLevel).reduce("", (x, y) -> x + y);
    }

    /**
     * The method to print horizontal lines.
     */
    private void printLine() {
        String line = Stream.generate(() -> Character.toString(this.horizontalLineSymbol))
                .limit(this.horizontalLineLength)
                .reduce("", (x, y) -> x + y);
        print(line);
    }

    /**
     * The method to print message.
     * @param msg The message to print.
     */
    private void print(String msg) {
        System.out.println(getIndentation() + msg);
    }

    /**
     * The method to accept given message and print with style.
     * @param msg The given message.
     */
    public void printMessage(String msg) {
        printLine();
        Arrays.stream(msg.split("\\n")).forEach(x -> print(" " + x));
        printLine();
    }

    /**
     * Return boolean indicating whether this object
     * is equivalent to another object.
     *
     * @param obj The object to be checked.
     * @return The boolean whether the given object is equivalent to this object.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MessagePrinter) {
            MessagePrinter mp = (MessagePrinter) obj;
            return this.horizontalLineLength == mp.horizontalLineLength
                    && this.horizontalLineSymbol == mp.getHorizontalLineSymbol()
                    && this.indentationLevel == mp.getIndentationLevel();
        }
        return false;
    }
}
