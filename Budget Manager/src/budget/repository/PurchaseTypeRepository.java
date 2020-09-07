package budget.repository;

import budget.domain.PurchaseType;

import java.util.List;

public interface PurchaseTypeRepository {
    List<PurchaseType> getAll();

    PurchaseType getById(int id);
}
