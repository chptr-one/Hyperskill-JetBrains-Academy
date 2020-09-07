package sorting.dataTypes;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WordItemType extends ItemType<String> {

    @Override
    public List<String> readData() {
        Scanner scanner = new Scanner(System.in);
        return scanner.useDelimiter("\\s+").tokens().collect(Collectors.toList());
    }

    @Override
    public void printNatural() {
        super.printNatural();
        sortNatural().forEach(i -> System.out.print(i + " "));
        System.out.println();
    }

    @Override
    public void printHeader() {
        System.out.println("Total words: " + totalItems);
    }
}
