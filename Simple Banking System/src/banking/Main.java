package banking;

import banking.account.AccountController;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Wrong number of arguments");
            return;
        }
        if (!args[0].equals("-fileName")) {
            System.out.println("Missing -fileName argument");
            return;
        }
        if (args[1].isBlank()) {
            System.out.println("File name is missing");
            return;
        }
        var accountController = AccountController.getInstance(args[1]);

        UserInterface userInterface = new UserInterface(accountController);
        userInterface.run();
    }
}

