package calculator.parser;

import calculator.exceptions.UnknownTokenException;
import calculator.exceptions.UnknownVariableException;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class VariableStorage {
    private static final String VAR_REGEX = "[a-zA-Z]+";

    private final static VariableStorage instance = new VariableStorage();
    private final Map<String, BigInteger> pool = new HashMap<>();

    private VariableStorage() {
    }

    static VariableStorage getInstance() {
        return instance;
    }

    public void parseAssignment(String assignment) {
        int splitPosition = assignment.indexOf("=");
        String name = assignment.substring(0, splitPosition).trim();

        if (name.matches(VAR_REGEX)) {
            String expression = assignment.substring(splitPosition + 1).trim();
            Calculator calc = new Calculator();
            try {
                BigInteger value = calc.calculate(expression);
                setVariable(name, value);
            } catch (UnknownTokenException e) {
                System.out.println("Invalid assignment");
            } catch (UnknownVariableException e) {
                System.out.println("Unknown variable");
            }
        } else
            System.out.println("Invalid identifier");
    }

    void setVariable(String name, BigInteger value) {
        if (pool.containsKey(name)) {
            pool.replace(name, value);
        } else {
            pool.put(name, value);
        }
    }

    BigInteger getVariable(String name) throws UnknownVariableException {
        if (pool.containsKey(name)) {
            return pool.get(name);
        } else {
            throw new UnknownVariableException();
        }
    }
}
