package calculator;

import calculator.exceptions.UnknownCommandException;

enum Command {
    HELP("/help", "The program calculates the sum of numbers"),
    EXIT("/exit", "Bye!");

    private final String commandLine;
    private final String output;

    Command(String commandLine, String output) {
        this.commandLine = commandLine;
        this.output = output;
    }

    static Command fromString(String s) throws UnknownCommandException {
        for (Command c : Command.values()) {
            if (c.commandLine.equals(s))
                return c;
        }
        throw new UnknownCommandException();
    }

    void execute() {
        System.out.println(output);
    }
    }
