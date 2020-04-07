/**
 * created:    2019/10/28
 * <p>
 * {@code ColorHSB} represents a color in hue–saturation–brightness (HSB) format, along with a sample client.
 * The HSB color format is widely used in color pickers.
 *
 * Execution:    java-introcs ColorHSB 25 84 97 < wiki.txt
 *
 * @author Xiaoyu Zhang
 */

public class ColorHSB {
    private static final int MAX_HUE = 359;
    private static final int MAX_SAT_BRI = 100;

    private final int hue;
    private final int saturation;
    private final int brightness;

    // Creates a color with hue h, saturation s, and brightness b.
    public ColorHSB(int h, int s, int b) {
        if (h < 0 || h > MAX_HUE)
            throw new IllegalArgumentException("the hue must be between 0 and 359");

        if (s < 0 || s > MAX_SAT_BRI)
            throw new IllegalArgumentException("the saturation must be between 0 and 100");

        if (b < 0 || b > MAX_SAT_BRI)
            throw new IllegalArgumentException("the brightness must be between 0 and 100");

        this.hue = h;
        this.saturation = s;
        this.brightness = b;
    }

    private int getHue() {
        return this.hue;
    }

    private int getSaturation() {
        return this.saturation;
    }

    private int getBrightness() {
        return this.brightness;
    }

    // Returns a string representation of this color, using the format (h, s, b).
    public String toString() {
        return "(" + this.hue + ", " + this.saturation + ", " + this.brightness + ")";
    }

    // Is this color a shade of gray?
    public boolean isGrayscale() {
        return this.saturation == 0 || this.brightness == 0;
    }

    // Returns the squared distance between the two colors.
    public int distanceSquaredTo(ColorHSB that) {
        int d1 = (int) Math.min(Math.pow(this.hue - that.getHue(), 2.0), Math.pow(360 - Math.abs(this.hue - that.getHue()), 2.0));
        int d2 = (int) Math.pow(this.saturation - that.getSaturation(), 2);
        int d3 = (int) Math.pow(this.brightness - that.getBrightness(), 2);

        return (d1 + d2 + d3);
    }

    // Sample client (see below).
    public static void main(String[] args) {
        // Get the specified color from standard input
        int h = Integer.parseInt(args[0]);
        int s = Integer.parseInt(args[1]);
        int b = Integer.parseInt(args[2]);

        ColorHSB thatColor = new ColorHSB(h, s, b);

        // the closest color to the specified thatColor
        int hue = 0, saturation = 0, brightness = 0;
        ColorHSB minColor = null;

        // initial value for the minmium distance to the speficifed color
        int thatDistance = Integer.MAX_VALUE;

        // temporary value for the distance to the specified color 
        int distance =  0;

        // String name for the closest color
        String minColorStr = " ";

        // temporary value for the String name 
        String colorStr = " ";

        while (!StdIn.isEmpty()) {

            colorStr = StdIn.readString();
            hue = StdIn.readInt();
            saturation = StdIn.readInt();
            brightness = StdIn.readInt();

            ColorHSB color = new ColorHSB(hue, saturation, brightness);
            distance = thatColor.distanceSquaredTo(color);

            if (distance < thatDistance) {
                thatDistance = distance;
                minColorStr = colorStr;
                minColor = color;
            }

            // StdOut.printf("TRACE: color %s = {%d, %d, %d} \n", color, hue, saturation, brightness);
        }

        StdOut.println(minColorStr + " " + minColor);
    }
}