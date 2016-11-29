import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SuffixTree {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    class Node {
        public int start;
        public int length;
        public List<Node> children;
        public Node(int s, int l) {
            start = s;
            length = l;
            children = new ArrayList<>();
        }

        /**
         * Returns an int length of matching characters
         * @param otherS
         * @param otherL
         * @return
         */
        public int match(int otherS, int otherL){
//            System.out.print("matching " + this.toString() + " and " + text.substring(otherS, otherS + otherL));
            int l = Math.min(length, otherL);
            for(int i = 0; i < l; i++) {
                if(text.charAt(start + i) != text.charAt(otherS + i)) {
//                    System.out.println(".... " + l);

                    return i;
                }
            }
            return l;
        }

        /**
         * Splits the node into a parent and a child at the point indicated - same point
         * as match method returns for length of matching string
         * @param splitPoint
         */
        public void split(int splitPoint) {
//            System.out.print("splitting...");
//            System.out.print(this.toString());
            Node newChild = new Node(start + splitPoint, length - splitPoint);
            if(children.size() > 0) {
                for(Node n : children) {
                    newChild.children.add(n);
                }
                children.clear();
            }
            children.add(newChild);
            length = splitPoint;

//            System.out.print(" -> " + this.toString());
//            System.out.println(", " + newChild.toString());
        }

        public int numChildren() {
            return children.size();
        }
        public String toString() {
            String s = "XXX";
            try {
                s = text.substring(start, start + length);
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Out of Bounds, start: " + start + ", length: " + length);
            }
            return s;
//            return text.substring(start, start + length);
        }
    }

    private String text;

    // Build a suffix tree of the string text and return a list
    // with all of the labels of its edges (the corresponding 
    // substrings of the text) in any order.
    public List<String> computeSuffixTreeEdges(String t) {
        text = t;
        List<String> result = new ArrayList<String>();
        // Implement this function yourself
        Node root = new Node(0, 0);

        for(int i = 0; i < text.length(); i++) {
//            System.out.println("Add #" + i + ": " + text.substring(i));
            addSuffixToTree(i, root);
//            printNodes(root);
        }

        int nodeCounter = 1;

        nodesToStrings(root, result);

        return result;
    }

    private void nodesToStrings(Node root, List<String> result) {
        for(Node child : root.children) {
            nodesToStrings(child, result);
        }
        result.add(root.toString());
    }
    private void printNodes(Node root) {
        for(Node child : root.children) {
            printNodes(child);
        }
        System.out.println(root.toString());
    }

    private void addSuffixToTree(int suffixStart, Node root) {
//        System.out.println("adding");
        Node current = null;
        int match = 0;
        for(int i = 0; i < root.numChildren(); i++) {
            current = root.children.get(i);
            match = current.match(suffixStart, text.length() - suffixStart);
            if(match > 0) break;
        }
        if(match == 0) {
//            System.out.println("...no match found");
            root.children.add(new Node(suffixStart, text.length() - suffixStart));
            return;
        }
        if(match == current.length) {
//            System.out.println("Recursively adding " + text.substring(suffixStart + match) + " to " + current.toString());
            addSuffixToTree(suffixStart + match, current);
        } else if(match < current.length) {
            current.split(match);
            current.children.add(new Node(suffixStart + match , text.length() - (suffixStart + match)));
        } else if(match > current.length) {
            System.out.println("match > current.length .... this shouldn't happen");
        }
    }

    static public void main(String[] args) throws IOException {
        new SuffixTree().run();
    }

    public void print(List<String> x) {
        for (String a : x) {
            System.out.println(a);
        }
    }

    public void run() throws IOException {
//        System.setIn(new FileInputStream("data/suffixTree/sample3"));

        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        List<String> edges = computeSuffixTreeEdges(text);
        print(edges);
    }
}
