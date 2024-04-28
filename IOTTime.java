import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IOTTime {
    public static void main(String[] args) throws IOException, InterruptedException {
        String[] filePaths = {
                "C:\\Users\\user\\Desktop\\кпи\\1 курс\\2 семестр\\програмування лаби\\lab5\\io-rarest-word\\src\\file.txt",
                "C:\\Users\\user\\Desktop\\кпи\\1 курс\\2 семестр\\програмування лаби\\lab5\\io-rarest-word\\src\\fa.txt",
                "C:\\Users\\user\\Desktop\\кпи\\1 курс\\2 семестр\\програмування лаби\\lab5\\io-rarest-word\\src\\оапр.txt",
                "C:\\Users\\user\\Desktop\\кпи\\1 курс\\2 семестр\\програмування лаби\\lab5\\io-rarest-word\\src\\dfg.txt"
        };

        for (String filePath : filePaths) {
            long startTime = System.currentTimeMillis();
            String rarestWord = processFile(filePath);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.println("Rarest word in file " + filePath + ": " + rarestWord);
            System.out.println("Processing time: " + duration + " ms\n");
        }
    }

    public static String processFile(String filePath) throws IOException, InterruptedException {
        Map<String, Integer> wordOccurrences = Collections.synchronizedMap(new HashMap<>());
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+|\\p{Punct}");
                for (String word : words) {
                    if (!word.isEmpty() && Character.isLetter(word.charAt(0))) {
                        synchronized (wordOccurrences) {
                            wordOccurrences.put(word, wordOccurrences.getOrDefault(word, 0) + 1);
                        }
                    }
                }
            }
        }

        return Collections.min(wordOccurrences.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
