package duke.exception;

import duke.task.Task;

/**
 * The DukeRuntimeException that an identical task is being added into the TaskList.
 */
public class TaskAlreadyExistException extends DukeRuntimeException {
    /**
     * The constructor of the Exception.
     * @param task The duplicated task.
     */
    public TaskAlreadyExistException(Task task) {
        super("The task has already been added into the TaskList.\n"
                + "Here are the task details:" + task.toString());
    }
}
