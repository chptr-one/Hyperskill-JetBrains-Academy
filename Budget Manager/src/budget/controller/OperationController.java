package budget.controller;

import budget.domain.*;
import budget.repository.*;
import budget.menu.PurchaseTypeMenu;
import budget.sort.Sorter;

import java.util.List;
import java.util.Scanner;

public class OperationController {

    private final Scanner scanner;
    private final OperationRepository operationRepo;
    private final PurchaseTypeRepository typeRepo;

    public OperationController(Scanner scanner) {
        this.scanner = scanner;
        this.operationRepo = GsonOperationRepository.getInstance();
        this.typeRepo = StaticPurchaseTypeRepository.getInstance();
    }

    public void addIncome() {
        System.out.println("Enter income:");
        double value = Double.parseDouble(scanner.nextLine());

        operationRepo.add(new Income(value));
        System.out.println("Income was added!");
        System.out.println();
    }

    public void addPurchase() {
        var purchaseTypeMenu = new PurchaseTypeMenu(scanner, typeRepo, false, true);

        var type = purchaseTypeMenu.makeChoice();
        while (purchaseTypeMenu.notExit()) {
            System.out.println("Enter purchase name:");
            String name = scanner.nextLine().trim();
            System.out.println("Enter it's price:");
            double price = Double.parseDouble(scanner.nextLine());

            operationRepo.add(new Purchase(price, name, type));
            System.out.println("Purchase was added!");
            System.out.println();

            type = purchaseTypeMenu.makeChoice();
        }
    }

    public void showListOfPurchases() {
        var purchaseTypeMenu = new PurchaseTypeMenu(scanner, typeRepo, true, true);

        var type = purchaseTypeMenu.makeChoice();
        while (purchaseTypeMenu.notExit()) {
            List<Purchase> purchases;

            if (type == PurchaseTypeMenu.ALL) {
                purchases = operationRepo.getPurchases();
            } else {
                purchases = operationRepo.getPurchasesByType(type);
                System.out.println(type.getName() + ":");
            }
            printPurchases(purchases);

            type = purchaseTypeMenu.makeChoice();
        }
    }

    public void printBalance() {
        System.out.printf("Balance: $%.2f\n", getBalance());
        System.out.println();
    }

    public void save() {
        operationRepo.save();
        System.out.println("Purchases were saved!");
        System.out.println();
    }

    public void load() {
        operationRepo.load();
        System.out.println("Purchases were loaded!");
        System.out.println();
    }

    public void analyze() {
        boolean exit = false;

        while (!exit) {
            System.out.println("How do you want to sort?");
            System.out.println("1) Sort all purchases");
            System.out.println("2) Sort by type");
            System.out.println("3) Sort certain type");
            System.out.println("4) Back");
            int choice = Integer.parseInt(scanner.nextLine());
            System.out.println();

            switch (choice) {
                case 1:
                    Sorter.SortAndPrintAllPurchases(operationRepo);
                    System.out.println();
                    break;
                case 2:
                    Sorter.SortAndPrintByType(operationRepo);
                    System.out.println();
                    break;
                case 3:
                    var menu = new PurchaseTypeMenu(scanner, typeRepo, false, false);
                    var type = menu.makeChoice();
                    Sorter.SortAndPrintCertainType(operationRepo, type);
                    System.out.println();
                    break;
                default:
                    exit = true;
                    break;
            }
        }
    }

    // helpers

    private void printPurchases(List<Purchase> purchases) {
        if (purchases.isEmpty()) {
            System.out.println("Purchase list is empty");
        } else {
            purchases.forEach(System.out::println);
            printTotal(getTotal(purchases));
        }
        System.out.println();
    }

    public static void printTotal(double total) {
        System.out.printf("Total: $%.2f\n", total);
    }

    public static double getTotal(List<? extends Operation> operations) {
        return operations.stream().mapToDouble(Operation::getValue).sum();
    }

    private double getBalance() {
        double balance = getTotal(operationRepo.getIncomes()) - getTotal(operationRepo.getPurchases());
        return balance >= 0 ? balance : 0;
    }
}
