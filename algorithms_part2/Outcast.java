/**
 * created:    2018/05/07
 * <p>
 * Given a list of WordNet nouns x1, x2, ..., xn, which noun is the least related to the others? To identify an
 * outcast, compute the sum of the distances between each noun and every other one.
 *
 * @author Xiaoyu Zhang
 */

public class Outcast {
    private final WordNet wordnet;

    /**
     * constructor takes a WordNet object
     */
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    /**
     * given an array of WordNet nouns, return an outcast
     */
    public String outcast(String[] nouns) {
        int largest_distance = 0;
        int current_distance = 0;

        String outcast_noun = null;

        int i, j;

        for (i = 0; i < nouns.length; i++) {
            for (j = 0; j < nouns.length; j++) {
                current_distance += wordnet.distance(nouns[i], nouns[j]);
            }

            if (current_distance > largest_distance) {
                largest_distance = current_distance;
                outcast_noun = nouns[i];
            }

            current_distance = 0;
        }

        return outcast_noun;
    }

    /**
     * test client
     */
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);

        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }

    }
}