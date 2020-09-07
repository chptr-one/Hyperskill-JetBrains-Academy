package budget.sort;

import budget.domain.Purchase;
import budget.domain.PurchaseType;
import budget.repository.OperationRepository;

import java.util.Comparator;
import java.util.Map;

import static budget.controller.OperationController.getTotal;
import static budget.controller.OperationController.printTotal;
import static java.util.stream.Collectors.*;

public class Sorter {

    static public void SortAndPrintAllPurchases(OperationRepository repo) {
        System.out.println("All:");
        var sorted = repo.getPurchases().stream()
                .sorted(Comparator.reverseOrder())
                .collect(toList());

        if (sorted.isEmpty()) {
            System.out.println("Purchase list is empty!");
        } else {
            sorted.forEach(System.out::println);
            printTotal(getTotal(sorted));
        }
    }

    static public void SortAndPrintByType(OperationRepository repo) {
        System.out.println("Types:");
        var grouped = repo.getPurchases().stream()
                .collect(groupingBy(Purchase::getType, summingDouble(Purchase::getValue)));
        var sum = grouped.values().stream()
                .mapToDouble(v -> v)
                .sum();

        grouped.entrySet().stream()
                .sorted(Map.Entry.<PurchaseType, Double>comparingByValue().reversed())
                .forEach(entry -> System.out.printf("%s - $%.2f\n", entry.getKey().getName(), entry.getValue()));
        System.out.printf("Total sum: $%.2f\n", sum);
    }

    static public void SortAndPrintCertainType(OperationRepository repo, PurchaseType type) {
        System.out.println(type.getName() + ":");
        var sorted = repo.getPurchasesByType(type).stream()
                .sorted(Comparator.reverseOrder())
                .collect(toList());

        if (sorted.isEmpty()) {
            System.out.println("Purchase list is empty!");
        } else {
            sorted.forEach(System.out::println);
            printTotal(getTotal(sorted));
        }
    }
}
