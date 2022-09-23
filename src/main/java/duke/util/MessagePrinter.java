package duke.util;

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
     * The constructor of the class
     */
    public MessagePrinter() {
        setHorizontalLineLength(50);
        setHorizontalLineSymbol('*');
        setIndentationLevel(2);
    }

    /**
     * The constructor of the class
     */
    public MessagePrinter(int horizontalLineLength, char horizontalLineSymbol, int indentationLevel) {
        setHorizontalLineLength(horizontalLineLength);
        setHorizontalLineSymbol(horizontalLineSymbol);
        setIndentationLevel(indentationLevel);
    }

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
     * Returns the indentation in String.
     * @return The indentation in String.
     */
    private String getIndentation() {
        return Stream.generate(() -> " ")
                .limit(this.indentationLevel)
                .reduce("", (x, y) -> x + y);
    }

    /**
     * Returns String of horizontal line.
     * @return The String of horizontal line.
     */
    private String getBorderLine() {
        return getBorderLine(this.horizontalLineSymbol, horizontalLineLength);
    }

    /**
     * Returns String of horizontal line with given style.
     * @param symbol The given symbol.
     * @param length The given length.
     * @return The String of horizontal line.
     */
    private String getBorderLine(char symbol, int length) {
        String line = Stream.generate(() -> Character.toString(symbol))
                .limit(length)
                .reduce("", (x, y) -> x + y);
        return line;
    }

    /**
     * The method to print message.
     * @param input The user input.
     * @param output The duke's response.
     */
    public void printInTerminal(String input, String output) {
        char logBorderLineSymbol = '-';
        int logBorderLineLength = 50;
        String timestamp = new java.util.Date().toString();
        System.out.println("At " + timestamp + ":" + System.lineSeparator()
                + "User input: [" + input + "]" + System.lineSeparator()
                + "Response:" + System.lineSeparator()
                + output);
        System.out.println(getBorderLine(logBorderLineSymbol, logBorderLineLength));
    }

    /**
     * The method to accept given message and print with style.
     * @param msg The given message.
     * @return The message with style.
     */
    public String getPrintMessage(String msg) {
        StringBuilder result = new StringBuilder();
        result.append(getBorderLine()).append(System.lineSeparator());
        result.append(Arrays.stream(msg.split(System.lineSeparator()))
                        .reduce("", (x, y) -> x
                                + (x.equals("") ? "" : System.lineSeparator())
                                + getIndentation() + y))
                .append(System.lineSeparator());
        result.append(getBorderLine());
        return result.toString();
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
