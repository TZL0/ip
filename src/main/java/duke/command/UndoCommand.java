package duke.command;

import duke.Duke;
import duke.exception.DukeRuntimeException;
import duke.util.MessagePrinter;
import duke.util.TaskList;

import java.util.Stack;

public class UndoCommand extends Command {
    /**
     * The constructor of the Class.
     */
    protected UndoCommand() {
        super(Action.UNDO);
    }

    /**
     * Executes the Command with given Duke.
     * @param duke The target duke that the command takes effect.
     * @return The response of Duke.
     */
    @Override
    public String execute(Duke duke) {
        Stack<TaskList> taskListHistory = duke.getTaskListHistory();
        MessagePrinter messagePrinter = duke.getMessagePrinter();
        if (taskListHistory.size() < 1) {
            throw new DukeRuntimeException("No more command can be undone.");
        }
        TaskList lastStage = taskListHistory.pop();
        duke.setTaskList(lastStage);
        return messagePrinter.printMessage("Successfully undo last command.\n"
                + "Type [list] to view current Tasks");
    }

    /**
     * Returns the standard format of the Command.
     * @return String of standard format.
     */
    @Override
    public String getFormat() {
        return "undo";
    }

    /**
     * Returns whether this command terminates Duke.
     * @return Returns whether this command terminates Duke.
     */
    @Override
    public boolean isTerminated() {
        return false;
    }

    /**
     * Returns boolean indicating whether this object
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
        return obj instanceof UndoCommand;
    }
}
