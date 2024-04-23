import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InputOutputThreads {
    public static void main(String[] args) throws IOException {
        String[] filePaths = {
                "C:\\Users\\user\\Desktop\\кпи\\1 курс\\2 семестр\\програмування лаби\\lab5\\io-rarest-word\\src\\file.txt",
                "C:\\Users\\user\\Desktop\\fa.txt"
        };

        for (String filePath : filePaths) {
            String fileName = filePath.split("\\\\")[filePath.split("\\\\").length - 1];
            System.out.println("Rarest word in file " + fileName + ": " + rarestWord(filePath));
        }
    }

    public static String rarestWord(String filePath) throws IOException {
        Map<String, Integer> wordOccurrences = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+|\\p{Punct}");
                for (String word : words) {
                    if (!word.isEmpty() && Character.isLetter(word.charAt(0))) {
                        wordOccurrences.put(word, wordOccurrences.getOrDefault(word, 0) + 1);
                    }
                }
            }
        }

        Map.Entry<String, Integer> rarestEntry = Collections.min(wordOccurrences.entrySet(), Map.Entry.comparingByValue());

        return rarestEntry.getKey();
    }
}
