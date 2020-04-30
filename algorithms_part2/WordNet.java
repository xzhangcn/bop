import java.util.ArrayList;

/**
 * created:    2018/05/07
 * <p>
 * {@code WordNet} is a semantic lexicon for the English language that computational linguists and cognitive scientists use extensively.
 * For example, WordNet was a key component in IBM’s Jeopardy-playing Watson computer system. WordNet groups words
 * into sets of synonyms called synsets. For example, { AND circuit, AND gate } is a synset that represent a logical gate that
 * fires only when all of its inputs fire. WordNet also describes semantic relationships between synsets. One such relationship is
 * the is-a relationship, which connects a hyponym (more specific synset) to a hypernym (more general synset). For example, the
 * synset { gate, logic gate } is a hypernym of { AND circuit, AND gate } because an AND gate is a kind of logic gate.
 *
 *
 * Your first task is to build the WordNet digraph: each vertex v is an integer that represents a synset,
 * and each directed edge v→w represents that w is a hypernym of v. The WordNet digraph is a rooted DAG: it is acyclic and has
 * one vertex—the root—that is an ancestor of every other vertex. However, it is not necessarily a tree because a synset can have
 * more than one hypernym. A small subgraph of the WordNet digraph appears below.
 *
 * @author Xiaoyu Zhang
 */
public class WordNet {
    private ArrayList<String> al_synsets;               // store synsets in the arraylist for the indexing
    private RedBlackBST<String, String> st_nouns;       // RedBlackBST tree mapping noun to the string with the concatenation of corresponding id numbers
    private Digraph dg_hypernyms;                       // Digraph represeting the hypernyms relationship, with nodes representing the synsets
    private SAP sap;
    private int num_synsets;                            // number of synsets

    /**
     * constructor takes the name of the two input files
     */
    public WordNet(String synsets, String hypernyms) {
        processSynsets(synsets);
        processHypernyms(hypernyms);

        sap = new SAP(dg_hypernyms);

        Topological topological = new Topological(dg_hypernyms);
        // StdOut.printf("rank of vertex 1 is %d \n", topological.rank(1));
        if ((new Topological(dg_hypernyms)).hasOrder() == false) throw new IllegalArgumentException();
    }

    /**
     * Read in the synsets.txt file and build appropriate data structures (RedBlackBST tree, mapping noun to the id number),  
     * and record the number of synsets for later use.
     */
    private void processSynsets(String synsets) {
        String s;
        int id;
        String str_id;

        String[] fields_synsets;
        String[] fields_nouns;

        al_synsets = new ArrayList<String>();
        st_nouns= new RedBlackBST<String, String>();
        num_synsets = 0;

        try {
            In in_synsets = new In(synsets);
            while (!in_synsets.isEmpty()) {
                s = in_synsets.readLine();         // read one line from the synsets file

                fields_synsets = s.split(",");     // split the line 

                id = Integer.parseInt(fields_synsets[0]);      // store the id number, and will be stored as the value in the RedBlackBST tree
                str_id = fields_synsets[0].concat(",");

                al_synsets.add(id, fields_synsets[1]);        // put the synsets 

                fields_nouns = fields_synsets[1].split(" ");   // split the nouns

                for (String noun : fields_nouns) {
                    if (st_nouns.contains(noun)) {
                        st_nouns.put(noun, str_id.concat(st_nouns.get(noun)));   // mapping all the id belong to that same noun by concatenation
                    } else {
                        st_nouns.put(noun, str_id);
                    }
                }

                num_synsets++;
            }
        }
        catch (IllegalArgumentException e) {
            StdOut.println(e);
        }

        // StdOut.printf("Total number of nouns: {%d} \n",st_nouns.size());

        // for testing purpose
        /*
        for (String noun : st_nouns.keys())
            StdOut.printf("noun: %s has index: %s\n", noun, st_nouns.get(noun));
        */
    }

    /**
     * Read in the hypernyms.txt file and build a Digraph.
     */
    private void processHypernyms(String hypernyms) {
        String s;
        String[] fields;
        int id_hyponym;      // more specific synset
        int id_hypernym;     // more general synset
        int i;

        dg_hypernyms = new Digraph(num_synsets);

        try {
            In in_hypernyms = new In(hypernyms);
            while (!in_hypernyms.isEmpty()) {
                s = in_hypernyms.readLine();
                fields = s.split(",");     // split the line

                id_hyponym = Integer.parseInt(fields[0]);

                for (i = 1; i < fields.length; i++) {
                    id_hypernym = Integer.parseInt(fields[i]);
                    dg_hypernyms.addEdge(id_hyponym, id_hypernym);
                }
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }          
        
        /*
        StdOut.println(dg_hypernyms);
        StdOut.printf("number of vertices: %d \n", dg_hypernyms.V());
        StdOut.printf("number of edges: %d \n", dg_hypernyms.E());       
        */
    }

    /**
     * returns all WordNet nouns 
     */
    public Iterable<String> nouns() {
        return st_nouns.keys();
    }

    /**
     *
     */
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();

        return st_nouns.contains(word);
    }

    /**
     * distance between nounA and nounB (defined below)
     */
    public int distance(String nounA, String nounB) {
        if (isNoun(nounA) == false || isNoun(nounB) == false) throw new IllegalArgumentException();

        // SAP sap = new SAP(dg_hypernyms);

        return sap.length(buildIds(nounA), buildIds(nounB));
    }

    /**
     * a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
     * in a shortest ancestral path (defined below)
     */
    public String sap(String nounA, String nounB) {
        if (isNoun(nounA) == false || isNoun(nounB) == false) throw new IllegalArgumentException();

        // SAP sap = new SAP(dg_hypernyms);

        int ancestor = sap.ancestor(buildIds(nounA), buildIds(nounB));

        return al_synsets.get(ancestor);
    }

    /**
     * helper function to build the ArrayList of IDs mapped from noun
     */
    private Iterable<Integer> buildIds(String noun) {
        String str_ids_noun = st_nouns.get(noun);

        String[] ids_noun = str_ids_noun.split(",");     // split the ids for nounA        

        ArrayList<Integer> al_ids_noun = new ArrayList<Integer>();

        for (String id_noun : ids_noun) {
            al_ids_noun.add(Integer.parseInt(id_noun));
        }

        return al_ids_noun;
    }

    /**
     * do unit testing of this class
     */
    public static void main(String[] args) {
        String synsets = args[0];
        String hypernyms = args[1];

        WordNet word_net = new WordNet(synsets, hypernyms);


        String noun_A = "Brown_Swiss";
        String noun_B = "barrel_roll";

        StdOut.printf("{%s, %s} => distance: {%d}; ancestor: {%s} \n", noun_A, noun_B, word_net.distance(noun_A, noun_B), word_net.sap(noun_A, noun_B));


        /* ------------------------- test code below ------------------------------- */
        /*
         String str_ids_nounA = "1,";
         
         String[] ids_nounA = str_ids_nounA.split(",");     // split the ids for nounA        
         ArrayList<Integer> al_ids_nounA = new ArrayList<Integer>();
         
         for (String id_nounA : ids_nounA) {
         StdOut.printf("the {%s} \n",id_nounA);
         al_ids_nounA.add(Integer.parseInt(id_nounA));
         }        
         */
        /* ------------------------- test code above ------------------------------- */

    }
}