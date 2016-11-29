import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Trie {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements()) {
                tok = new StringTokenizer(in.readLine());
            }
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    static int nodeCount = 1;
    private Map<Character, Integer> root = new HashMap<>();

    List<Map<Character, Integer>> buildTrie(String[] patterns) {
        List<Map<Character, Integer>> trie = new ArrayList<Map<Character, Integer>>();
        trie.add(root);

        // write your code here

        for(String pattern : patterns) {
            Map<Character,Integer> current = trie.get(0);
            for(int i = 0; i < pattern.length(); i++) {
                Character c = pattern.charAt(i);
                if (current.containsKey(c)) {
                    current = trie.get(current.get(c));
                } else {
                    current.put(c, nodeCount++);
                    current = new HashMap<>();
                    trie.add(current);

                }
            }


        }



        return trie;
    }

    static public void main(String[] args) throws IOException {
//        System.setIn(new FileInputStream("data/trie/testfile"));
        new Trie().run();
    }

    public void print(List<Map<Character, Integer>> trie) {
        for (int i = 0; i < trie.size(); ++i) {
            Map<Character, Integer> node = trie.get(i);
            for (Map.Entry<Character, Integer> entry : node.entrySet()) {
                System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey());
            }
        }
    }

    public void run() throws IOException {
//        long start = System.nanoTime();
        FastScanner scanner = new FastScanner();
        int patternsCount = scanner.nextInt();
        String[] patterns = new String[patternsCount];
        for (int i = 0; i < patternsCount; ++i) {
            patterns[i] = scanner.next();
        }
        List<Map<Character, Integer>> trie = buildTrie(patterns);
//        long duration = (System.nanoTime() - start);
//        double time = duration / 1000000000.0;
//        System.out.println("Trie built in " + time + " seconds");
//        System.out.println((nodeCount - 1) + " total trie nodes");
        print(trie);
    }
}
