package test;

import java.util.LinkedHashMap;
import java.util.Map;


public class LRU implements CacheReplacementPolicy
{
    private final LinkedHashMap<String, Integer> OrderMap;

    public LRU()
    {
        this.OrderMap = new LinkedHashMap<>();
    }

    @Override
    public void add(String word)
    {
        OrderMap.put(word,OrderMap.size());
    }

    @Override
    public String remove() {
        Map.Entry<String,Integer> temp = null;

        for (Map.Entry<String, Integer> entry : OrderMap.entrySet())
        {
            if (temp == null || entry.getValue() < temp.getValue())
            {
                temp=entry;
            }
        }
        if (temp != null)
        {
            OrderMap.remove(temp.getKey());
            return temp.getKey();
        }

        return null ;
    }
}
