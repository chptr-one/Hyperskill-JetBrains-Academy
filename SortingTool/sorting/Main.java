package sorting;

import sorting.IO.*;
import sorting.dataTypes.*;

public class Main {

    public static void main(String[] args) {
        try {
            ApplicationParameters parameters = ApplicationParameters.parseFrom(args);
            ItemType<?> data = null;

            switch (parameters.getDataType()) {
                case LONG: {
                    data = new LongItemType();
                    break;
                }
                case WORD: {
                    data = new WordItemType();
                    break;
                }
                case LINE: {
                    data = new LineItemType();
                    break;
                }
            }

            switch (parameters.getSortingType()) {
                case NATURAL: {
                    data.printNatural();
                    break;
                }
                case BY_COUNT: {
                    data.printByCount();
                    break;
                }
            }
        } catch (ArgumentsExeption argumentsExeption) {
            System.out.println(argumentsExeption.getMessage());
        }
    }
}