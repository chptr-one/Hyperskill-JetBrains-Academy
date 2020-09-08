package banking;

import banking.account.AccountController;
import banking.exception.WrongCardNumberOrPinException;
import banking.menu.Menu;
import banking.menu.MenuBuilder;

import java.util.Scanner;

public class UserInterface {

    private final Menu mainMenu;
    private final Menu accountMenu;
    private final AccountController accountController;
    private final Scanner scanner;

    public UserInterface(AccountController accountController) {
        this.accountController = accountController;
        this.scanner = new Scanner(System.in);

        this.mainMenu = new MenuBuilder()
                .setScanner(scanner)
                .addItem(1, "Create an account", this::createAccount)
                .addItem(2, "Log into account", this::logIntoAccount)
                .addItem(0, "Exit", this::mainMenuExit)
                .build();

        this.accountMenu = new MenuBuilder()
                .setScanner(scanner)
                .addItem(1, "Balance", this::printBalance)
                .addItem(2, "Add income", this::addIncome)
                .addItem(3, "Do transfer", this::doTransfer)
                .addItem(4, "Close account", this::closeAccount)
                .addItem(5, "Log out", this::logOut)
                .addItem(0, "Exit", this::accountMenuExit)
                .build();
    }

    private void createAccount() {
        var account = accountController.createNewAccount();
        System.out.println("Your card has been created\n" +
                "Your card number:");
        System.out.println(account.getNumber());
        System.out.println("Your card PIN:");
        System.out.println(account.getPin());
        System.out.println();
    }

    private void logIntoAccount() {
        System.out.println("Enter your card number:");
        long number = Long.parseLong(scanner.nextLine());
        System.out.println("Enter your PIN:");
        int pin = Integer.parseInt(scanner.nextLine());
        System.out.println();

        try {
            accountController.logIntoAccount(number, pin);
            System.out.println("You have successfully logged in!");
            System.out.println();

            while (accountMenu.notExit()) {
                accountMenu.selectItem().getAction().execute();
            }

        } catch (WrongCardNumberOrPinException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }
    }

    private void printBalance() {
        System.out.println("Balance: " + accountController.getActiveAccount().getBalance());
        System.out.println();
    }

    private void addIncome() {
        System.out.println("Enter income:");
        int income = Integer.parseInt(scanner.nextLine());
        accountController.addIncome(income);
        System.out.println("Income was added!");
        System.out.println();
    }

    private void doTransfer() {
        System.out.println("Enter card number:");
        long toNumber = Long.parseLong(scanner.nextLine());
        if (!accountController.isNumberCorrect(toNumber)) {
            System.out.println("Probably you made mistake in the card number. Please try again!");
            System.out.println();
            return;
        }

        var toAccount = accountController.getByNumber(toNumber);
        if (toAccount.isEmpty()) {
            System.out.println("Such a card does not exist.");
            System.out.println();
            return;
        }

        System.out.println("Enter how much money you want to transfer:");
        int amount;
        while ((amount = Integer.parseInt(scanner.nextLine())) <= 0) {
            System.out.println("Please enter a number greater than zero");
            System.out.println();
        }

        if (amount > accountController.getActiveAccount().getBalance()) {
            System.out.println("Not enough money!");
            System.out.println();
            return;
        }

        accountController.transfer(toAccount.get(), amount);
        System.out.println("Success!");
        System.out.println();
    }

    private void closeAccount() {
        accountController.closeAccount();
        System.out.println("The account has been closed!");
        System.out.println();
        accountMenu.setExit();
    }

    private void logOut() {
        accountController.logOut();
        accountMenu.setExit();
        System.out.println("You have successfully logged out!");
        System.out.println();
    }

    public void run() {
        while (mainMenu.notExit()) {
            mainMenu.selectItem().getAction().execute();
        }
        System.out.println("Bye!");
    }

    private void mainMenuExit() {
        mainMenu.setExit();
    }

    private void accountMenuExit() {
        accountMenu.setExit();
        mainMenu.setExit();
    }
}
