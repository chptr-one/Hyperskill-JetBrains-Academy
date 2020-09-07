package calculator;

import calculator.exceptions.*;
import calculator.parser.Calculator;

public class Main {

    public static void main(String[] args) {
        UserInput input = new UserInput();
        Calculator calc = new Calculator();
        Command command = null;

        while (command != Command.EXIT) {
            input.readFromConsole();

            switch (input.getType()) {
                case COMMAND: {
                    try {
                        command = Command.fromString(input.toString());
                        command.execute();
                    } catch (UnknownCommandException e) {
                        System.out.println("Unknown command");
                    }
                    break;
                }
                case ASSIGNMENT: {
                    calc.variables.parseAssignment(input.toString());
                    break;
                }
                case EXPRESSION: {
                    try {
                        System.out.println(calc.calculate(input.toString()));
                    } catch (UnknownTokenException e) {
                        System.out.println("Invalid expression");
                    } catch (UnknownVariableException e) {
                        System.out.println("Unknown variable");
                    }
                    break;
                }
                default: {
                }
            }
        }
    }
}
