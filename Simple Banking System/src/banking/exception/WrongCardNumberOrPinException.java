package banking.exception;

public class WrongCardNumberOrPinException extends Exception {
    public WrongCardNumberOrPinException() {
        super("Wrong card number or PIN!");
    }
}
