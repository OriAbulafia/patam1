package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Dictionary {
    private CacheManager existingWordsCache;
    private CacheManager nonExistingWordsCache;
    private BloomFilter bloomFilter;
    private String[] fileNames;

    public Dictionary(String... fileNames) {
        this.fileNames = fileNames;
        existingWordsCache = new CacheManager(400, new LRU());
        nonExistingWordsCache = new CacheManager(100, new LFU());
        bloomFilter = new BloomFilter(256, "MD5", "SHA-1");

        for (String fileName : fileNames) {
            String[] words = readFromFile(fileName);
            for (String word : words) {
                bloomFilter.add(word);
            }
        }
    }

    public boolean query(String word) {
        if (existingWordsCache.query(word)) {
            return true;
        }
        if (nonExistingWordsCache.query(word)) {
            return false;
        }
        boolean result = bloomFilter.contains(word);
        if (result) {
            existingWordsCache.add(word);
        } else {
            nonExistingWordsCache.add(word);
        }
        return result;
    }

    public boolean challenge(String word) {
        try {
            boolean result = IOSearcher.search(word, fileNames);
            if (result) {
                existingWordsCache.add(word);
            } else {
                nonExistingWordsCache.add(word);
            }
            return result;
        } catch (Exception e) {
            nonExistingWordsCache.add(word);
            return false;
        }
    }

    private String[] readFromFile(String fileName) {
        List<String> wordsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] wordsInLine = line.split(" ");
                for (String word : wordsInLine) {
                    word = word.trim();
                    if (!word.isEmpty()) {
                        wordsList.add(word);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName);
        }
        return wordsList.toArray(new String[0]);
    }
}
