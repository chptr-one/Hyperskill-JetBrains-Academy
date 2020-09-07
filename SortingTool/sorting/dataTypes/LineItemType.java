package sorting.dataTypes;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LineItemType extends ItemType<String> {

    @Override
    public List<String> readData() {
        Scanner scanner = new Scanner(System.in);
        return scanner.useDelimiter("\n").tokens().collect(Collectors.toList());
    }

    @Override
    public void printNatural() {
        super.printNatural();
        System.out.println();
        sortNatural().forEach(System.out::println);
    }

    @Override
    public void printHeader() {
        System.out.println("Total lines: " + totalItems);
    }
}
