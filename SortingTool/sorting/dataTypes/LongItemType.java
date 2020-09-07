package sorting.dataTypes;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LongItemType extends ItemType<Long> {

    @Override
    public List<Long> readData() {
        Scanner scanner = new Scanner(System.in);
        return scanner.tokens()
                .map(Long::valueOf)
                .collect(Collectors.toList());

    }

    @Override
    public void printNatural() {
        super.printNatural();
        sortNatural().forEach(i -> System.out.print(i + " "));
        System.out.println();
    }

    @Override
    public void printHeader() {
        System.out.println("Total numbers: " + totalItems);
    }
}
