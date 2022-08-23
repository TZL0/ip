import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

public class Duke {
    private final String LOGO = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    private final String STORAGE_PATH = "./data/duke.txt";
    private final HashMap<Action, Consumer<Command>> actionConsumerMap = new HashMap<>();
    private boolean isTerminated;
    private MessagePrinter mp;
    private TaskList tasks;

    public Duke() {
        initialize();
        greet();
    }

    public static void main(String[] args) {
        Duke duke = new Duke();
        Scanner scanner = new Scanner(System.in);
        while (!duke.getIsTerminated()) {
            try {
                if (scanner.hasNext()) {
                    String entry = scanner.nextLine();
                    Command command = Parser.parseCommand(entry);
                    duke.execute(command);
                }
            } catch (DukeException dukeException) {
                duke.handle(dukeException);
            }
        }
    }

    private void initializeActionConsumerMap() {
        HashMap<Action, Consumer<Command>> map = this.actionConsumerMap;
        map.put(Action.DONOTHING, command -> {
        });
//        Level_1
        map.put(Action.GREET, command -> greet());
        map.put(Action.ECHO, command -> echo(command.getParameters().get(0)));
        map.put(Action.EXIT, command -> exit());
//        Level_2
//        map.put(Action.ADD, command -> add(command.getParameters().get(0)));
        map.put(Action.LIST, command -> list());
//        Level_3
        map.put(Action.MARK, command -> mark(Integer.parseInt(command.getParameters().get(0))));
        map.put(Action.UNMARK, command -> unmark(Integer.parseInt(command.getParameters().get(0))));
        map.put(Action.TODO, command -> todo(command.getParameters().get(0)));
//        Level_4
        map.put(Action.EVENT, command -> event(command.getParameters().get(0),
                Parser.parseStringToDateTime(command.getParameters().get(1))));
        map.put(Action.DEADLINE, command -> deadline(command.getParameters().get(0),
                Parser.parseStringToDateTime(command.getParameters().get(1))));
//       Level_5
//        No Actions is added.
//        Level_6
        map.put(Action.DELETE, command -> delete(Integer.parseInt(command.getParameters().get(0))));
//        Level_7
        map.put(Action.SAVE, command -> save());
        map.put(Action.READ, command -> read());
    }

    private void initialize() {
        this.tasks = new TaskList();
        initializeActionConsumerMap();
        this.mp = new MessagePrinter(3, 100, '-');
    }

    public boolean getIsTerminated() {
        return this.isTerminated;
    }

    public void greet() {
        String HELLO_MESSAGE = "Hello! I'm Duke \n" + "What can I do for you?";
        mp.printMessage("Hello from\n" + this.LOGO + "\n" + HELLO_MESSAGE);
    }

    //      Removed Functionality
    public void echo(String msg) {
        mp.printMessage(msg);
    }

//    Removed Functionality
//    public void add(String msg) {
//        Task task = new Task(msg);
//        tasks.add(task);
//        mp.printMessage("added: " + task.getName());
//    }

    public void list() {
        String message = "Here are the tasks in your list:\n";
        if (tasks.size() == 0) {
            message = "Currently no tasks in the list.";
        } else {
            message = message + this.tasks.toString();
        }
        mp.printMessage(message);
    }

    public void mark(int idTask) {
        String successMsg = "Nice! I've marked this task as done:";
        Task task = this.tasks.get(idTask - 1);
        task.markAsDone();
        mp.printMessage(successMsg + "\n" + task);
    }

    public void unmark(int idTask) {
        String successMsg = "OK, I've marked this task as not done yet:";
        Task task = this.tasks.get(idTask - 1);
        task.markAsNotDone();
        mp.printMessage(successMsg + "\n" + task);
    }

    public void todo(String msg) {
        String successMsg = "Got it. I've added this task:";
        Task todo = Task.todo(msg);
        tasks.add(todo);
        successMsg = successMsg + "\n" + todo + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
        mp.printMessage(successMsg);
    }

    public void event(String msg, LocalDateTime time) {
        String successMsg = "Got it. I've added this task:";
        Task event = Task.event(msg, time);
        tasks.add(event);
        successMsg = successMsg + "\n" + event + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
        mp.printMessage(successMsg);
    }

    public void deadline(String msg, LocalDateTime time) {
        String successMsg = "Got it. I've added this task:";
        Task deadline = Task.deadline(msg, time);
        tasks.add(deadline);
        successMsg = successMsg + "\n" + deadline + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
        mp.printMessage(successMsg);
    }

    public void exit() {
        String FAREWELL_MESSAGE = "Bye. Hope to see you again soon!";
        this.isTerminated = true;
        mp.printMessage(FAREWELL_MESSAGE);
    }

    public void execute(Command command) {
        Optional.ofNullable(command).ifPresent(x -> this.actionConsumerMap.get(x.getAction()).accept(x));
    }

    public void handle(DukeException dukeException) {
        this.mp.printMessage(dukeException.getMessage());
    }

    public void delete(int idTask) {
        String successMsg = "Noted. I've removed this task:";
        Task task = this.tasks.remove(idTask - 1);
        successMsg = successMsg + "\n" + task + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
        mp.printMessage(successMsg);
    }

    public void save() {
        this.tasks.save();
        int size = tasks.size();
        String temp = size == 1 ? "task has" : "tasks have";
        mp.printMessage("Your " + size + " " + temp + " been saved successfully");
    }

    public void read() {
        this.tasks = this.tasks.parse(this.tasks.read());
        int size = tasks.size();
        String temp = size == 1 ? "task has" : "tasks have";
        mp.printMessage("Your " + size + " " + temp + " been loaded successfully\n"
                + "Type [list] to view your tasks");
    }


}
