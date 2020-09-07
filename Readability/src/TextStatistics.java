import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TextStatistics {
    private int sentences = 0;
    private int words = 0;
    private int characters = 0;
    private int syllables = 0;
    private int polysyllables = 0;

    public TextStatistics(String text) {
        if (text.length() > 0) {
            sentences = text.split("[.!?]").length;
            characters = text.replaceAll("\\s", "").length();

            List<String> wordsList = countWords(text);
            words = wordsList.size();
            for (String word : wordsList) {
                int syllables = countSyllables(word);
                if (syllables > 2) polysyllables++;
                this.syllables += syllables;
            }
        }
    }

    private List<String> countWords(String text) {
        return Arrays.asList(text.replaceAll("\\p{Punct}", "").split("\\s"));
    }

    /*
     * 1. Count the number of vowels in the word.
     * 2. Do not count double-vowels (for example, "rain" has 2 vowels but is only 1 syllable)
     * 3. If the last letter in the word is 'e' do not count it as a vowel (for example, "side" is 1 syllable)
     * 4. If at the end it turns out that the word contains 0 vowels, then consider this word as 1-syllable.
     */
    private int countSyllables(String word) {
        word = word.toLowerCase();
        Matcher matcher = Pattern.compile("[aeiouy][^aeiouy]|[aeiouy]$").matcher(word);
        int syllables = 0;
        while (matcher.find())
            syllables++;
        if (word.endsWith("e"))
            syllables--;
        if (syllables == 0)
            syllables = 1;
        return syllables;
    }

    public double ARI() {
        return 4.71 * getCharacters() / countWords() + 0.5 * countWords() / countSentences() - 21.43;
    }

    public double FK() {
        return 0.39 * countWords() / countSentences() + 11.8 * getSyllables() / countWords() - 15.59;
    }

    public double SMOG() {
        return 1.043 * Math.sqrt(getPolysyllables() * 30.0 / countSentences()) + 3.1291;
    }

    public double CL() {
        double l = 1.0 * getCharacters() / countWords() * 100.0;
        double s = 1.0 * countSentences() / countWords() * 100.0;
        return 0.0588 * l - 0.296 * s - 15.8;
    }

    public int countSentences() {
        return sentences;
    }

    public int countWords() {
        return words;
    }

    public int getCharacters() {
        return characters;
    }

    public int getSyllables() {
        return syllables;
    }

    public int getPolysyllables() {
        return polysyllables;
    }
}
