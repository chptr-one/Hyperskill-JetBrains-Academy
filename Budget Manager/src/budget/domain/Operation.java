package budget.domain;

abstract public class Operation {

    protected final double value;

    public Operation(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
