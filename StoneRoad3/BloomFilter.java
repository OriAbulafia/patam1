package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class BloomFilter {
    private BitSet bitSet;
    private MessageDigest[] hashFunctions;

    public BloomFilter(int size, String... algorithms) {
        bitSet = new BitSet(size);
        hashFunctions = new MessageDigest[algorithms.length];
        try {
            for (int i = 0; i < algorithms.length; i++) {
                hashFunctions[i] = MessageDigest.getInstance(algorithms[i]);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void add(String word) {
        for (MessageDigest hashFunction : hashFunctions) {
            hashFunction.reset();
            byte[] hashBytes = hashFunction.digest(word.getBytes());
            BigInteger hashNumber = new BigInteger(1, hashBytes);
            int index = Math.abs(hashNumber.intValue()) % bitSet.size();
            bitSet.set(index, true); // Set the bit at the calculated index to true
        }
    }


    public boolean contains(String word) {
        for (MessageDigest hashFunction : hashFunctions) {
            hashFunction.reset();
            byte[] hashBytes = hashFunction.digest(word.getBytes());
            BigInteger hashNumber = new BigInteger(1, hashBytes);
            int index = Math.abs(hashNumber.intValue()) % bitSet.size();
            if (!bitSet.get(index)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int lastIndex = bitSet.length() - 1;
        for (int i = lastIndex; i >= 0; i--) {
            if (bitSet.get(i)) {
                lastIndex = i;
                break;
            }
        }
        for (int i = 0; i <= lastIndex; i++) {
            result.append(bitSet.get(i) ? "1" : "0");
        }
        return result.toString();
    }


}