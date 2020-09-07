package budget.domain;

import org.jetbrains.annotations.NotNull;

public class Purchase extends Operation implements Comparable<Purchase>{

    private final String name;
    private final PurchaseType type;

    public Purchase(double value, String name, PurchaseType type) {
        super(value);
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public PurchaseType getType() {
        return type;
    }

    @Override
    public int compareTo(@NotNull Purchase purchase) {
        return Double.compare(value, purchase.value);
    }

    @Override
    public String toString() {
        return String.format("%s $%.2f", name, value);
    }
}
