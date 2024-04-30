package test;

import java.util.HashSet;
import java.util.Set;

public class CacheManager {
    private final int size;
    private final CacheReplacementPolicy crp;
    private final Set<String> words;

    public CacheManager(int size, CacheReplacementPolicy crp) {
        this.size = size;
        this.crp = crp;
        this.words = new HashSet<>();
    }

    public boolean query(String word) {
       return words.contains(word);
    }

    public void add(String word) {
        if(size == words.size())
        {
            String toRemove = crp.remove();
            words.remove(toRemove);
        }
        words.add(word);
        crp.add(word);
    }
}