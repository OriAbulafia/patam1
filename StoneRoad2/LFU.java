package test;

import java.util.HashMap;
import java.util.Map;

public class LFU implements CacheReplacementPolicy
{
    private final HashMap<String, Integer> frequencyMap;

    public LFU() {
        this.frequencyMap=new HashMap<>();
    }

    //------------------------------------------------------------------------

    @Override
    public void add(String word)
    {
        frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
    }


    @Override
    public String remove()
    {
        String lfu_word = null;
        int minFrequency = Integer.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet())
        {
            if (entry.getValue() < minFrequency) {
                minFrequency = entry.getValue();
                lfu_word = entry.getKey();
            }
        }

        if (lfu_word != null)
        {
            frequencyMap.remove(lfu_word);
        }
        return lfu_word;
    }
}
