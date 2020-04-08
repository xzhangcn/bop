import java.util.Arrays;

/**
 * created:    2019/11/01
 * <p>
 * {@code Bar} presents a bar that aggregates related information (name, value, and category) for use in a bar chart.
 *
 * @author Xiaoyu Zhang
 */

public class Bar implements Comparable<Bar> {

    private String name;
    private int value;
    private String category;

    // Creates a new bar.
    public Bar(String name, int value, String category) {
        if (name == null)
            throw new IllegalArgumentException("the name can not be null");

        if (value < 0)
            throw new IllegalArgumentException("the value must be greater than 0");

        if (category == null)
            throw new IllegalArgumentException("the category can not be null");

        this.name = name;
        this.value = value;
        this.category = category;
    }

    // Returns the name of this bar.
    public String getName() {
        return this.name;
    }

    // Returns the value of this bar. 
    public int getValue() {
        return this.value;
    }

    // Returns the category of this bar.
    public String getCategory() {
        return this.category;
    }

    public String toString() {
        return "(" + this.name + ", " + this.value + ", " + this.category + ")";
    }

    // Compare two bars by value.
    public int compareTo(Bar that) {
        if (that == null)
            throw new NullPointerException("the compared bar can not be null");

        int temp = that.getValue();

        if (this.value < temp)           return -1;
        else if (this.value == temp)     return 0;
        else                            return 1;
    }

    // Sample client (see below).
    public static void main(String[] args) {
        // create an array of 10 bars
        Bar[] bars = new Bar[10];
        bars[0] = new Bar("Beijing",     22674, "East Asia");
        bars[1] = new Bar("Cairo",       19850, "Middle East");
        bars[2] = new Bar("Delhi",       27890, "South Asia");
        bars[3] = new Bar("Dhaka",       19633, "South Asia");
        bars[4] = new Bar("Mexico City", 21520, "Latin America");
        bars[5] = new Bar("Mumbai",      22120, "South India");
        bars[6] = new Bar("Osaka",       20409, "East Asia");
        bars[7] = new Bar("São Paulo",   21698, "Latin America");
        bars[8] = new Bar("Shanghai",    25779, "East Asia");
        bars[9] = new Bar("Tokyo",       38194, "East Asia");

        // sort in ascending order by weight
        Arrays.sort(bars);

        for (Bar bar : bars)
            StdOut.println(bar);
    }
}