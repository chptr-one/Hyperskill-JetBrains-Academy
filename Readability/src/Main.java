import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.DoubleSupplier;
import java.util.stream.Collectors;

public class Main {

    static private String readFileToString(String fileName) throws IOException {
        return Files
                .lines(Paths.get(fileName))
                .collect(Collectors.joining());
    }

    static private int calculateAge(double index) {
        int roundedIndex = (int) Math.round(index);
        if (roundedIndex < 3)
            return roundedIndex + 5;
        if (roundedIndex < 13)
            return roundedIndex + 6;
        else
            return 24;
    }

    public static void main(String[] args) throws IOException {
        String text = readFileToString(args[0]);

        TextStatistics textStatistics = new TextStatistics(text);
        System.out.println("The text is:");
        System.out.println(text);
        System.out.println();
        System.out.println("Words: " + textStatistics.countWords());
        System.out.println("Sentences: " + textStatistics.countSentences());
        System.out.println("Characters: " + textStatistics.getCharacters());
        System.out.println("Syllables: " + textStatistics.getSyllables());
        System.out.println("Polysyllables: " + textStatistics.getPolysyllables());

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String param;
        try (Scanner in = new Scanner(System.in)) {
            param = in.nextLine();
        }

        System.out.println();
        Map<String, DoubleSupplier> functions = new LinkedHashMap<>();
        functions.put("ARI", textStatistics::ARI);
        functions.put("FK", textStatistics::FK);
        functions.put("SMOG", textStatistics::SMOG);
        functions.put("CL", textStatistics::CL);

        Map<String, String> format = Map.of(
                "ARI", "Automated Readability Index: %.2f (about %s year olds).\n",
                "FK", "Flesch–Kincaid readability tests: %.2f (about %s year olds).\n",
                "SMOG", "Simple Measure of Gobbledygook: %.2f (about %s year olds).\n",
                "CL", "Coleman–Liau index: %.2f (about %s year olds).\n"
        );

        if (param.equals("all")) {
            double averageAge = 0;
            for (String function : functions.keySet()) {
                double index = functions.get(function).getAsDouble();
                int age = calculateAge(index);
                averageAge += age;
                System.out.printf(format.get(function), index, age);
            }
            averageAge /= functions.size();
            System.out.println();
            System.out.printf("This text should be understood in average by %.2f year olds.\n", averageAge);
        } else {
            double index = functions.get(param).getAsDouble();
            int age = calculateAge(index);
            System.out.printf(format.get(param), index, age);
        }
    }
}