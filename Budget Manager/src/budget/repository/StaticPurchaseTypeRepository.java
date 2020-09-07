package budget.repository;

import budget.domain.PurchaseType;

import java.util.ArrayList;
import java.util.List;

public class StaticPurchaseTypeRepository implements PurchaseTypeRepository {

    private static StaticPurchaseTypeRepository instance;
    private final List<PurchaseType> types;

    private StaticPurchaseTypeRepository() {
        types = new ArrayList<>();

        types.add(new PurchaseType("Food"));
        types.add(new PurchaseType("Clothes"));
        types.add(new PurchaseType("Entertainment"));
        types.add(new PurchaseType("Other"));
    }

    public static StaticPurchaseTypeRepository getInstance() {
        if (instance == null) {
            instance = new StaticPurchaseTypeRepository();
        }
        return instance;
    }

    @Override
    public List<PurchaseType> getAll() {
        return types;
    }

    @Override
    public PurchaseType getById(int id) {
        return types.get(id);
    }
}
