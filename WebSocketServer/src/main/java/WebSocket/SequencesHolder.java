package WebSocket;

import java.util.*;
import java.util.stream.Collectors;

public class SequencesHolder {

    private static SequencesHolder uniqueInstance;

    private final int DEPTH_OF_SEARCH = 10000;
    private final int SEQUENCE_COUNT = 5;
    private final int SEQUENCE_SIZE_FOR_CLIENT = 6;

    private volatile int length;
    private List<Integer> simpleNumbers;
    private List<List<Integer>> listOfSequences = new ArrayList<>();

    private SequencesHolder() {
        simpleNumbers = sieveOfEratosthenes(DEPTH_OF_SEARCH);
    }

    public static SequencesHolder getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SequencesHolder();
        }
        return uniqueInstance;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<List<Integer>> getSequences() {
        return listOfSequences.stream()
                .map(list -> list.stream()
                        .distinct()
                        .limit(SEQUENCE_SIZE_FOR_CLIENT)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public void generateSequences() {
        synchronized (listOfSequences) {
            listOfSequences.clear();

            for (int i = 0; i < SEQUENCE_COUNT; i++) {
                List<Integer> sequence = new ArrayList<>();
                while (sequence.size() < length) {
                    sequence.add(simpleNumbers.get(getRandomIndex()));
                }
                listOfSequences.add(sequence);
            }
        }
    }

    private int getRandomIndex() {
        return (int) (Math.random() * simpleNumbers.size());
    }

    public static List<Integer> sieveOfEratosthenes(int n) {
        boolean prime[]= new boolean[n + 1];
        Arrays.fill(prime, true);
        for (int p = 2; p * p <= n; p++) {
            if (prime[p]) {
                for (int i = p * p; i <= n; i += p) {
                    prime[i]= false;
                }
            }
        }
        List<Integer> primeNumbers = new LinkedList<>();
        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }
}