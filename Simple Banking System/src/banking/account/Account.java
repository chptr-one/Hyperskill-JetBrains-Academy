package banking.account;

import java.util.Objects;

public class Account {

    private final long Number;
    private final int pin;
    private int balance;

    public Account(long Number, int pin) {
        this.Number = Number;
        this.pin = pin;
        this.balance = 0;
    }

    public long getNumber() {
        return Number;
    }

    public int getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Number == account.Number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Number);
    }
}
