import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InputOutputThreads {
    public static void main(String[] args) throws IOException, InterruptedException {
        String[] filePaths = {
                "C:\\Users\\user\\Desktop\\кпи\\1 курс\\2 семестр\\програмування лаби\\lab5\\io-rarest-word\\src\\file.txt",
                "C:\\Users\\user\\Desktop\\кпи\\1 курс\\2 семестр\\програмування лаби\\lab5\\io-rarest-word\\src\\fa.txt",
                "C:\\Users\\user\\Desktop\\кпи\\1 курс\\2 семестр\\програмування лаби\\lab5\\io-rarest-word\\src\\оапр.txt",
                "C:\\Users\\user\\Desktop\\кпи\\1 курс\\2 семестр\\програмування лаби\\lab5\\io-rarest-word\\src\\dfg.txt"
        };

        Thread[] threads = new Thread[filePaths.length];
        for (int i = 0; i < filePaths.length; i++) {
            threads[i] = new Thread(new FileProcessor(filePaths[i]));
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    public static class FileProcessor implements Runnable {
        private final String filePath;
        private final Map<String, Integer> wordOccurrences;

        public FileProcessor(String filePath) {
            this.filePath = filePath;
            this.wordOccurrences = Collections.synchronizedMap(new HashMap<>());
        }

        @Override
        public void run() {
            try {
                String fileName = filePath.split("\\\\")[filePath.split("\\\\").length - 1];
                synchronized (wordOccurrences) {
                    System.out.println("Rarest word in file " + fileName + ": " + rarestWord(filePath));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String rarestWord(String filePath) throws IOException {
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
                return Collections.min(wordOccurrences.entrySet(), Map.Entry.comparingByValue()).getKey();
            }
        }
    }
}