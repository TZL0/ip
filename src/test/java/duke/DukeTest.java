package duke;

import duke.util.Storage;

public class DukeTest {
    protected final String testPath = "test/data/duke.txt";
    protected final Storage testStorage = new Storage(testPath);
    protected Duke duke = new Duke(testPath);

    protected final String wholeFormattedTasklist = "T } 1 } explore\n"
            + "E } 0 } presentation } 2022-10-01 12:34\n"
            + "D } 0 } week 3 cs2103t project } 2022-08-25 23:45\n"
            + "E } 1 } CS2109S Lecture } 2022-08-26 11:22\n"
            + "E } 0 } CS2109S Lecture } 2022-08-19 00:01\n"
            + "E } 1 } CS2109S Lecture } 2022-08-12 02:59\n"
            + "E } 0 } CS2100 week 3 recitation } 2022-08-23 16:00\n"
            + "D } 0 } CS2102 tutorial 1 to finish } 2022-08-26 12:00";
}
