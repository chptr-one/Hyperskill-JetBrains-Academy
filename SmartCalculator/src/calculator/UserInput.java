package calculator;

import java.util.Scanner;

class UserInput {

    static final Scanner SC = new Scanner(System.in);
    private String inputString = "";
    private InputType type = InputType.UNKNOWN;

    void readFromConsole() {
        inputString = SC.nextLine();

        if (isCommand())
            type = InputType.COMMAND;
        else if (isAssignment())
            type = InputType.ASSIGNMENT;
        else if (isExpression())
            type = InputType.EXPRESSION;
        else
            type = InputType.UNKNOWN;
    }

    InputType getType() {
        return type;
    }

    private boolean isCommand() {
        return inputString.startsWith("/");
    }

    private boolean isAssignment() {
        return inputString.contains("=");
    }

    private boolean isExpression() {
        return inputString.trim().length() > 0;
    }

    @Override
    public String toString() {
        return inputString;
    }

    enum InputType {
        COMMAND,
        ASSIGNMENT,
        EXPRESSION,
        UNKNOWN
    }
}
