package test;


import java.io.FileReader;
import java.util.HashMap;

public class DictionaryManager {
    private static DictionaryManager instance;
    private HashMap<String, HashMap<String, Boolean>> dictionary;

    public DictionaryManager() {
        dictionary = new HashMap<>();
    }

    public static DictionaryManager get() {
        if (instance == null) {
            instance = new DictionaryManager();
        }
        return instance;
    }

    private boolean searchWord(String word, String fileName) {
        try {
            FileReader in = new FileReader(fileName);
            int c;
            while ((c = in.read()) != -1) {
                if(c == word.charAt(0)){
                    boolean found = true;
                    for(int j=1; j<word.length(); j++){
                        c = in.read();
                        if(c != word.charAt(j)){
                            found = false;
                            break;
                        }
                    }
                    if(found){
                        in.close();
                        return true;
                    }
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("File not found");
        }
        return false;
    }

    public boolean query(String ...args) {
        if (args.length  < 2) {
            return false;
        }
        boolean result = false;
        String query = args[args.length - 1];
        for (int i = 0; i < args.length - 1; i++) {
            HashMap<String, Boolean> d = dictionary.get(args[i]);
            if (d == null) {
                d = new HashMap<>();
                dictionary.put(args[i], d);
            }
            if (d.containsKey(query)) {
                result = true;
            } else {
                if (searchWord(query, args[i])) {
                    d.put(query, true);
                    result = true;
                } else {
                    d.put(query, false);
                }
            }
        }
        return result;
    }

    public boolean challenge(String ...args) {
        if (args.length  < 2) {
            return false;
        }
        boolean result = false;
        String query = args[args.length - 1];
        for (int i = 0; i < args.length - 1; i++) {
            HashMap<String, Boolean> d = dictionary.get(args[i]);
            if (d == null) {
                d = new HashMap<>();
                dictionary.put(args[i], d);
            }
            if (searchWord(query, args[i])) {
                d.put(query, true);
                result = true;
            } else {
                d.put(query, false);
            }
        }
        return result;
    }

    public int getSize() {
        return dictionary.size();
    }

}
