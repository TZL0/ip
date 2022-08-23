import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Parser {
    private static final String ATTRIBUTE_SEPARATOR = "}";

    public static ArrayList<Integer> getIndicesOfStr1InStr2(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();

        ArrayList<Integer> result = new ArrayList<>();
        Stream.iterate(0, x -> x + 1)
                .limit(len2 - len1 + 1)
                .filter(i -> str2.substring(i, i + len1).equals(str1))
                .forEach(result::add);
        return result;
    }

    public static int getFirstIndexOfStr1InStr2(String str1, String str2) {
        ArrayList<Integer> indices = getIndicesOfStr1InStr2(str1, str2);
        return indices.size() == 0 ? -1 : indices.get(0);
    }

    public static ArrayList<String> separateAttributes(String string) {
        ArrayList<String> result = new ArrayList<>();
        Arrays.stream(string.split(ATTRIBUTE_SEPARATOR)).forEach(s -> result.add(s.trim()));
        return result;
    }

    public static String combineAttributes(ArrayList<String> arrayList) {
        return arrayList.stream()
                .reduce("", (x, y) -> x + " " + ATTRIBUTE_SEPARATOR + " " + y).substring(ATTRIBUTE_SEPARATOR.length() + 2);
    }

    public static String combineAttributes(String... strings) {
        return Arrays.stream(strings)
                .reduce("", (x, y) -> x + " " + ATTRIBUTE_SEPARATOR + " " + y).substring(ATTRIBUTE_SEPARATOR.length() + 2);
    }

    private static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static Command parseCommand(String s) throws DukeException {
        int indexOfName;
        int indexOfTime;
        String arg1;
        String arg2;

        s = s.trim();
        String[] words = s.split(" ");
        Action action = Action.getAction(words[0]);
        switch (action) {
        case GREET:
            return new Command(Action.GREET);
        case EXIT:
            return new Command(Action.EXIT);
        case MARK:
            arg1 = s.substring(Action.getString(Action.MARK).length()).trim();
            if (arg1.equals("")) {
                throw new DukeException.NoArgumentException(Action.MARK);
            } else if (!isInt(arg1)) {
                throw new DukeException.InvalidArgumentException(Action.MARK, "The argument should be an integer.");
            }
            return new Command(Action.MARK, arg1);
        case UNMARK:
            arg1 = s.substring(Action.getString(Action.UNMARK).length()).trim();
            if (arg1.equals("")) {
                throw new DukeException.NoArgumentException(Action.UNMARK);
            } else if (!isInt(arg1)) {
                throw new DukeException.InvalidArgumentException(Action.UNMARK, "The argument should be an integer.");
            }
            return new Command(Action.UNMARK, arg1);
        case LIST:
            return new Command(Action.LIST);
        case ADD:
            arg1 = s.substring(Action.getString(Action.ADD).length()).trim();
            if (arg1.equals("")) {
                throw new DukeException.NoArgumentException(Action.ADD);
            }
            return new Command(Action.ADD, arg1);
        case TODO:
            arg1 = s.substring(Action.getString(Action.TODO).length()).trim();
            if (arg1.equals("")) {
                throw new DukeException.NoArgumentException(Action.TODO);
            } else if (!isValidString(arg1)) {
                throw new DukeException.InvalidArgumentException(Action.EVENT,
                        "Todo details should not contain '}'.");
            }
            return new Command(Action.TODO, arg1);
        case EVENT:
            String START_OF_EVENT_TIME_SYMBOL = " /at ";
            if (!s.contains(START_OF_EVENT_TIME_SYMBOL)) {
                throw new DukeException.InvalidArgumentException(Action.EVENT, "Keyword: [" +
                        START_OF_EVENT_TIME_SYMBOL + " ] or [Time] is not found.");
            }
            indexOfName = Action.getString(Action.EVENT).length();
            indexOfTime = getFirstIndexOfStr1InStr2(START_OF_EVENT_TIME_SYMBOL, s);
            arg1 = s.substring(indexOfName, indexOfTime).trim();
            arg2 = s.substring(indexOfTime + START_OF_EVENT_TIME_SYMBOL.length()).trim();
            if (arg1.equals("") && arg2.equals("")) {
                throw new DukeException.NoArgumentException(Action.EVENT);
            } else if (!isValidString(arg1)) {
                throw new DukeException.InvalidArgumentException(Action.EVENT,
                        "Event [Name] is not found.");
            } else if (!isValidString(arg2)) {
                throw new DukeException.InvalidArgumentException(Action.EVENT,
                        "Event [Time] is not found.");
            }

            return new Command(Action.EVENT, arg1, arg2);
        case DEADLINE:
            String START_OF_DEADLINE_TIME_SYMBOL = " /by ";

            if (!s.contains(START_OF_DEADLINE_TIME_SYMBOL)) {
                throw new DukeException.InvalidArgumentException(Action.DEADLINE, "Keyword: [" +
                        START_OF_DEADLINE_TIME_SYMBOL + " ] or [Time] is not found.");
            }

            indexOfName = Action.getString(Action.DEADLINE).length();
            indexOfTime = getFirstIndexOfStr1InStr2(START_OF_DEADLINE_TIME_SYMBOL, s);
            arg1 = s.substring(indexOfName, indexOfTime).trim();
            arg2 = s.substring(indexOfTime + START_OF_DEADLINE_TIME_SYMBOL.length()).trim();

            if (arg1.equals("") && arg2.equals("")) {
                throw new DukeException.NoArgumentException(Action.DEADLINE);
            } else if (!isValidString(arg1)) {
                throw new DukeException.InvalidArgumentException(Action.DEADLINE,
                        "Deadline [Name] is not found.");
            } else if (!isValidString(arg2)) {
                throw new DukeException.InvalidArgumentException(Action.DEADLINE,
                        "Deadline [Time] is not found.");
            }

            return new Command(Action.DEADLINE, arg1, arg2);
        case DELETE:
            arg1 = s.substring(Action.getString(Action.DELETE).length()).trim();
            if (arg1.equals("")) {
                throw new DukeException.NoArgumentException(Action.DELETE);
            } else if (!isInt(arg1)) {
                throw new DukeException.InvalidArgumentException(Action.DELETE, "The argument should be an integer.");
            }
            return new Command(Action.DELETE, arg1);
        case SAVE:
            return new Command(Action.SAVE);
        case READ:
            return new Command(Action.READ);
        case DONOTHING:
            return new Command(Action.DONOTHING);
        default:
            return null;
        }
    }

    private static boolean isValidString(String input) {
        return input != null && !input.equals("") && !input.contains(ATTRIBUTE_SEPARATOR);
    }

    public static String getAttributeSeparator() {
        return ATTRIBUTE_SEPARATOR;
    }
}