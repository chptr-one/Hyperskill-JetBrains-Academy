package budget.repository;

import budget.domain.Income;
import budget.domain.Operation;
import budget.domain.Purchase;
import budget.domain.PurchaseType;

import java.util.List;

public interface OperationRepository {

    void add(Operation purchase);

    void save();

    void load();

    List<Purchase> getPurchases();

    List<Purchase> getPurchasesByType(PurchaseType type);

    List<Income> getIncomes();
}
