package banking.account;

import banking.exception.WrongCardNumberOrPinException;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class AccountController {

    private static AccountController instance;
    private final AccountRepository repo;
    private Account activeAccount;

    public static AccountController getInstance(String url) {
        if (instance == null) {
            instance = new AccountController(AccountRepository.getInstance(url));
        }
        return instance;
    }

    private AccountController(AccountRepository repo) {
        this.repo = repo;
    }

    public Account createNewAccount() {
        var random = ThreadLocalRandom.current();
        long number = generateCorrectNumber(random);
        int pin = random.nextInt(1000, 10000);

        var account = new Account(number, pin);
        repo.add(account);
        return account;
    }

    public void logIntoAccount(long number, int pin) throws WrongCardNumberOrPinException {
        activeAccount = repo.getByNumber(number)
                .filter(account -> account.getPin() == pin)
                .orElseThrow(WrongCardNumberOrPinException::new);
    }

    public void logOut() {
        activeAccount = null;
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public void addIncome(int income) {
        activeAccount.setBalance(activeAccount.getBalance() + income);
        repo.update(activeAccount);
    }

    public void transfer(Account toAccount, int amount) {
        activeAccount.setBalance(activeAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        repo.update(activeAccount, toAccount);
    }

    public void closeAccount() {
        repo.delete(activeAccount);
        activeAccount = null;
    }

    public boolean isNumberCorrect(long number) {
        int sum = getLuhnSum(number, 16);
        return sum % 10 == 0;
    }

    public Optional<Account> getByNumber(long number) {
        return repo.getByNumber(number);
    }

    private long generateCorrectNumber(ThreadLocalRandom random) {
        final int MII = 4;
        final int IIN = MII * 1000_00;
        long customerAccountNumber = random.nextLong(1111_1111_1L, 9999_9999_9L);
        long number = IIN * 1000_0000_00L + customerAccountNumber;

        int sum = getLuhnSum(number, 15);
        byte checkSum = (byte) ((10 - sum % 10) % 10);
        number = number * 10 + checkSum;
        return number;
    }

    private int getLuhnSum(long number, int digits) {
        int sum = 0;
        byte digit;
        for (byte i = 1; i <= digits; i++) {
            digit = (byte) (number % 10);
            number /= 10;
            if (i % 2 != 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        return sum;
    }
}
