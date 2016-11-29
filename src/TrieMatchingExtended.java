import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TrieMatchingExtended implements Runnable {

    private List<Map<Character, Integer>> trie;
    private List<Boolean> patternEnd;

    private List<Map<Character, Integer>> buildTrie(List<String> patterns) {
        trie = new ArrayList<>();
        patternEnd = new ArrayList<>();
        trie.add(new HashMap<>());
        patternEnd.add(false);
        int nodeCounter = 1;

        for(String pattern : patterns) {
            Map<Character,Integer> current = trie.get(0);
            for(int i = 0; i < pattern.length(); i++) {
                Character c = pattern.charAt(i);
                if (current.containsKey(c)) {
                    current = trie.get(current.get(c));
                    if(i == pattern.length() - 1) patternEnd.set(trie.indexOf(current), true);
                } else {
                    current.put(c, nodeCounter);
                    current = new HashMap<>();
                    trie.add(current);
                    if(i == pattern.length() - 1) patternEnd.add(true);
                    else patternEnd.add(false);
                    nodeCounter++;
                }
            }
        }
        return trie;
    }


    List <Integer> solve (String text, int n, List <String> patterns) {
		List <Integer> result = new ArrayList <Integer> ();

		// write your code here
        List<Map<Character, Integer>> trie = buildTrie(patterns);
//        print(trie);
        for(int i = 0; i < text.length(); i++) {
            Map<Character, Integer> current = trie.get(0);
            int currentChar = i;
            while(current.containsKey(text.charAt(currentChar))) {
                int nextNode = current.get(text.charAt(currentChar));
                current = trie.get(nextNode);
                if(patternEnd.get(nextNode)) {
                    result.add(i);
                    break;
                } else {
                    currentChar++;
                    if (currentChar >= text.length()) break;
                }
            }
        }

		return result;
	}
//    public void print(List<Map<Character, Integer>> trie) {
//        for (int i = 0; i < trie.size(); ++i) {
//            Map<Character, Integer> node = trie.get(i);
//            for (Map.Entry<Character, Integer> entry : node.entrySet()) {
//                System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey() + " " + patternEnd.get(entry.getValue()));
//            }
//        }
//    }

	public void run () {
		try {
//            System.setIn(new FileInputStream("data/triematchingextended/sample2"));

            BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
			String text = in.readLine ();
		 	int n = Integer.parseInt (in.readLine ());
		 	List <String> patterns = new ArrayList <String> ();
			for (int i = 0; i < n; i++) {
				patterns.add (in.readLine ());
			}

			List <Integer> ans = solve (text, n, patterns);

			for (int j = 0; j < ans.size (); j++) {
				System.out.print ("" + ans.get (j));
				System.out.print (j + 1 < ans.size () ? " " : "\n");
			}
		}
		catch (Throwable e) {
			e.printStackTrace ();
			System.exit (1);
		}
	}

	public static void main (String [] args) {
		new Thread (new TrieMatchingExtended ()).start ();
	}
}
