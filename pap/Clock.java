/**
 * created:    2019/10/28
 * <p>
 * {@code Clock} represents time on a 24-hour clock, such as 00:00, 13:30, or 23:59. Time is measured in hours (00–23) and minutes (00–59).
 *
 * @author Xiaoyu Zhang
 */

public class Clock {

    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;

    private int hour;
    private int minute;

    // Creates a clock whose initial time is h hours and m minutes.
    public Clock(int h, int m) {
        validate(h, m);

        this.hour = h;
        this.minute = m;
    }

    // Creates a clock whose initial time is specified as a string, using the format HH:MM.
    public Clock(String s) {

        String reg = "^\\d{2}:\\d{2}$";

        if (!s.matches(reg))
            throw new IllegalArgumentException("the String format must be HH:MM");

        // String[] strClock = s.split(":");
        // int h = Integer.parseInt(strClock[0]);
        // int m = Integer.parseInt(strClock[1]);

        int h = Integer.parseInt(s.substring(0, 2));
        int m = Integer.parseInt(s.substring(3));

        validate(h, m);

        this.hour = h;
        this.minute = m;
    }

    private void validate(int h, int m) {
        if (h < 0 || h >= HOURS_PER_DAY)
            throw new IllegalArgumentException("the hour must be between 0 and 23");

        if (m < 0 || m >= MINUTES_PER_HOUR)
            throw new IllegalArgumentException("the minute must be between 0 and 59");
    }

    private int getHour() {
        return this.hour;
    }

    private int getMinute() {
        return this.minute;
    }

    // Returns a string representation of this clock, using the format HH:MM.
    public String toString() {
        String padHour = "", padMinute = "";

        if (this.hour < 10)     padHour = "0";
        if (this.minute < 10)   padMinute = "0";

        return padHour + this.hour + ":" + padMinute + this.minute;
    }

    // Is the time on this clock earlier than the time on that one?
    public boolean isEarlierThan(Clock that) {
        if (this.hour > that.getHour())
            return false;

        if (this.hour == that.getHour() && this.minute >= that.getMinute())
            return false;

        return true;
    }

    // Adds 1 minute to the time on this clock.
    public void tic() {
        toc(1);
    }

    // Adds Δ minutes to the time on this clock.
    public void toc(int delta) {
        if (delta < 0)
            throw new IllegalArgumentException("the delta must be greter than 0");

        int temp = this.minute + delta;

        this.minute = temp % MINUTES_PER_HOUR;
        this.hour = (this.hour + temp / MINUTES_PER_HOUR) % HOURS_PER_DAY;
    }

    // Test client (see below).
    public static void main(String[] args) {
        // Test cases
        // Clock clock = new Clock(21:65);
        Clock clock1 = new Clock("05:23");
        Clock clock2 = new Clock(3, 32);

        if (clock1.isEarlierThan(clock2))
            StdOut.println(clock1 + " is earlier than " + clock2);

        clock1.tic();
        clock2.tic();
        clock1.toc(65);
        clock2.toc(70);
        StdOut.println(clock1);
        StdOut.println(clock2);

    }
}