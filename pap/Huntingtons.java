/**
 * created:    2019/10/07
 * <p>
 * {@code Huntingtons} is to analyze a DNA string for Huntington’s disease and produce a diagnosis.
 *
 * @author Xiaoyu Zhang
 */

public class Huntingtons {

    // Returns the maximum number of consecutive repeats of CAG in the DNA string.
    public static int maxRepeats(String dna) {
        int count = 0;          // number of "CAG" repeats
        int maxCount = 0;       // maximum number of consecutive repeats
        int len = dna.length();

        for (int i = 0; i < len; i++) {

            if ((i+3) <= len && dna.substring(i, i+3).equals("CAG")) {

                count++;
                i += 2;   // here i only increments by 2, because i will increment by 1 within the for loop.
            }
            else {
                count = 0;
            }

            maxCount = Math.max(maxCount, count);
        }

        return maxCount;
    }

    // Returns a copy of s, with all whitespace (spaces, tabs, and newlines) removed.
    public static String removeWhitespace(String s) {
        String newStr = s.replace(" ", "");
        newStr = newStr.replace("\t", "");
        newStr = newStr.replace("\n", "");

        return newStr;
    }

    // Returns one of these diagnoses corresponding to the maximum number of repeats:
    // "not human", "normal", "high risk", or "Huntington's".
    public static String diagnose(int maxRepeats) {

        if (maxRepeats <= 9 || maxRepeats >= 181)           return "not human";
        else if (maxRepeats >= 10 && maxRepeats <= 35)      return "normal";
        else if (maxRepeats >= 36 && maxRepeats <= 39)      return "high risk";
        else                                                return "Huntington's";
    }

    // Sample client (see below).
    public static void main(String[] args) {
        // String dna = "TCAGCAG       CCAGTCAGCAGCAGCA";
        // StdOut.println(maxRepeats(dna));
        // StdOut.printf("new strign is (%s) \n", removeWhitespace(dna));

        String filename = args[0];
        In in = new In(filename);
        String dna = in.readAll();
        dna = removeWhitespace(dna);

        // StdOut.println(dna);
        int maxRepeats = maxRepeats(dna);
        StdOut.println("max repeats = " + maxRepeats);
        StdOut.println(diagnose(maxRepeats));
    }
}