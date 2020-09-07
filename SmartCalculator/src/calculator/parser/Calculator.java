package calculator.parser;/*
    expression -> add
    add -> mult ([+|-] mult)*
    mult -> group ([*|/] group)*
    group   -> [(] add [)] | number
    number -> ([+|-])* DOUBLE | VARIABLE
    DOUBLE -> "[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?"
 */

import calculator.exceptions.UnknownTokenException;
import calculator.exceptions.UnknownVariableException;

import java.math.BigInteger;

public class Calculator {
    private final Tokenizer tokenizer = new Tokenizer();
    public final VariableStorage variables = VariableStorage.getInstance();

    public BigInteger calculate(String expression) throws UnknownTokenException, UnknownVariableException {
        tokenizer.parseExpression(expression);
        return expression();
    }

    private BigInteger expression() throws UnknownTokenException, UnknownVariableException {
        BigInteger result = add();
        if (tokenizer.hasNext())
            throw new UnknownTokenException();
        return result;
    }

    private BigInteger group() throws UnknownTokenException, UnknownVariableException {
        BigInteger result;
        if (tokenizer.hasNext(TokenType.BRACKET)) {
            tokenizer.getNext(TokenType.BRACKET);
            result = add();
            tokenizer.getNext(TokenType.BRACKET);
        } else {
            result = number();
        }
        return result;
    }

    private BigInteger mult() throws UnknownTokenException, UnknownVariableException {
        BigInteger result = group();
        while (tokenizer.hasNext(TokenType.MULTIPLY, TokenType.DIVIDE)) {
            Token operator = tokenizer.getNext();
            BigInteger secondOperand = group();
            if ("/".equals(operator.getValue()))
                result = result.divide(secondOperand);
            else
                result = result.multiply(secondOperand);
        }
        return result;
    }

    private BigInteger add() throws UnknownTokenException, UnknownVariableException {
        BigInteger result = mult();
        while (tokenizer.hasNext(TokenType.ADD, TokenType.SUBSTRACT)) {
            Token operator = tokenizer.getNext();
            BigInteger secondOperand = mult();
            if ("-".equals(operator.getValue()))
                secondOperand = secondOperand.negate();
            result = result.add(secondOperand);
        }
        return result;
    }

    private BigInteger number() throws UnknownTokenException, UnknownVariableException {
        int sign = 1;
        while (tokenizer.hasNext(TokenType.ADD, TokenType.SUBSTRACT)) {
            Token operator = tokenizer.getNext();
            if ("-".equals(operator.getValue())) {
                sign *= -1;
            }
        }

        if (tokenizer.hasNext(TokenType.NUMBER))
            return new BigInteger(tokenizer.getNext(TokenType.NUMBER).getValue()).multiply(BigInteger.valueOf(sign));
        else {
            try {
                return variables.getVariable(tokenizer.getNext(TokenType.VARIABLE).getValue());
            } catch (UnknownVariableException e) {
                throw new UnknownVariableException();
            }
        }
    }
}
