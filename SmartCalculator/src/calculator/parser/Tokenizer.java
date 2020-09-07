package calculator.parser;

import calculator.exceptions.UnknownTokenException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Tokenizer {
    private static final String DOUBLE_REGEX = "[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";
    private static final String VAR_REGEX = "[a-zA-Z]+";

    private final List<Token> tokens = new ArrayList<>();
    private int pos = 0;

    boolean hasNext() {
        return pos < tokens.size();
    }

    boolean hasNext(TokenType... types) {
        if (hasNext()) {
            TokenType tokenType = peekNext().getType();
            for (TokenType type : types) {
                if (tokenType == type) {
                    return true;
                }
            }
        }
        return false;
    }

    Token getNext() {
        return tokens.get(pos++);
    }

    Token getNext(TokenType type) throws UnknownTokenException {
        if (hasNext(type)) {
            return getNext();
        }
        throw new UnknownTokenException();
    }

    private Token peekNext() {
        return tokens.get(pos);
    }

    void parseExpression(String expression) throws UnknownTokenException {
        Matcher doubleMatcher = Pattern.compile(DOUBLE_REGEX).matcher(expression);
        Matcher variableMatcher = Pattern.compile(VAR_REGEX).matcher(expression);

        tokens.clear();
        pos = 0;
        expression = expression.replaceAll("\\h+", "");

        while (!expression.isEmpty()) {

            doubleMatcher.reset(expression);
            if (doubleMatcher.lookingAt()) {
                String value = doubleMatcher.group();
                expression = expression.substring(value.length());
                variableMatcher.reset(expression);
                if (!expression.isEmpty() && variableMatcher.lookingAt()) {
                    throw new UnknownTokenException();
                }
                tokens.add(new Token(TokenType.NUMBER, value));
                continue;
            }

            variableMatcher.reset(expression);
            if (variableMatcher.lookingAt()) {
                String value = variableMatcher.group();
                expression = expression.substring(value.length());
                doubleMatcher.reset(expression);
                if (!expression.isEmpty() && doubleMatcher.lookingAt()) {
                    throw new UnknownTokenException();
                }
                tokens.add(new Token(TokenType.VARIABLE, value));
                continue;
            }

            char ch = expression.charAt(0);
            switch (ch) {
                case '+': {
                    tokens.add(new Token(TokenType.ADD, "+"));
                    break;
                }
                case '-': {
                    tokens.add(new Token(TokenType.SUBSTRACT, "-"));
                    break;
                }
                case '*': {
                    tokens.add(new Token(TokenType.MULTIPLY, "*"));
                    break;
                }
                case '/': {
                    tokens.add(new Token(TokenType.DIVIDE, "/"));
                    break;
                }
                case '(': {
                    tokens.add(new Token(TokenType.BRACKET, "("));
                    break;
                }
                case ')': {
                    tokens.add(new Token(TokenType.BRACKET, ")"));
                    break;
                }
                default: {
                    throw new UnknownTokenException();
                }
            }
            expression = expression.substring(1);
        }
    }
}

