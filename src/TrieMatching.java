import java.io.*;
import java.util.*;

//class Node
//{
//	public static final int Letters =  4;
//	public static final int NA      = -1;
//	public int next [];
//
//	Node ()
//	{
//		next = new int [Letters];
//		Arrays.fill (next, NA);
//	}
//}

public class TrieMatching implements Runnable {
//	int letterToIndex (char letter)
//	{
//		switch (letter)
//		{
//			case 'A': return 0;
//			case 'C': return 1;
//			case 'G': return 2;
//			case 'T': return 3;
//			default: assert (false); return Node.NA;
//		}
//	}

	List <Integer> solve (String text, int n, List <String> patterns) {
		List <Integer> result = new ArrayList <Integer> ();

		// write your code here
        List<Map<Character, Integer>> trie = buildTrie(patterns);

        for(int i = 0; i < text.length(); i++) {
            Map<Character, Integer> current = trie.get(0);
            int currentChar = i;
            while(current.containsKey(text.charAt(currentChar))) {
                int nextNode = current.get(text.charAt(currentChar));
                current = trie.get(nextNode);
                if(current.isEmpty()) {
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


    private List<Map<Character, Integer>> buildTrie(List<String> patterns) {
        List<Map<Character, Integer>> trie = new ArrayList<Map<Character, Integer>>();
        trie.add(new HashMap<>());
        int nodeCounter = 1;

        // write your code here

        for(String pattern : patterns) {
            Map<Character,Integer> current = trie.get(0);
            for(int i = 0; i < pattern.length(); i++) {
                Character c = pattern.charAt(i);
                if (current.containsKey(c)) {
                    current = trie.get(current.get(c));
                } else {
                    current.put(c, nodeCounter++);
                    current = new HashMap<>();
                    trie.add(current);

                }
            }


        }



        return trie;
    }

	public void run () {
		try {
  	        System.setIn(new FileInputStream("data/triematching/testfile"));

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
		new Thread (new TrieMatching ()).start ();
	}
}
