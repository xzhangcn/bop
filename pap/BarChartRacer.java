import java.util.Arrays;

/**
 * created:    2019/11/02
 * <p>
 * {@code BarChartRacer} produces animated bar charts, using {@code BarChart} class to draw static bar charts.
 *
 * @author Xiaoyu Zhang
 */

public class BarChartRacer {

    // sample client
    public static void main(String[] args) {
        // create the bar chart
        In in = new In(args[0]);
        int k = Integer.parseInt(args[1]);

        String title = in.readLine();
        String xAxis = in.readLine();
        String source = in.readLine();

        BarChart chart = new BarChart(title, xAxis, source);
        Bar bar = null;

        String line;
        int nums = 0;   // number of records for each section
        int i = 0;

        Bar[] bars = null;
        String[] fields = null;
        String caption = null;
        String name = null;
        int value = 0;
        String category = null;

        StdDraw.setCanvasSize(1000, 700);
        StdDraw.enableDoubleBuffering();

        while (in.hasNextLine()) {
            line = in.readLine();

            if (line.length() > 0) {

                if (line.matches("^\\d+")) {
                    nums = Integer.parseInt(line);
                }

                bars = new Bar[nums];

                for (i = 0; i < nums; i++) {

                    // read the next line
                    line = in.readLine();

                    fields = line.split(",");
                    caption = fields[0];
                    name = fields[1];
                    value = Integer.parseInt(fields[3]);
                    category = fields[4];

                    bar = new Bar(name, value, category);
                    bars[i] = bar;
                }

                Arrays.sort(bars);

                chart.setCaption(caption);

                // add the bars to the bar chart
                for (i = 0; i < k; i++) {
                    bar = bars[nums - 1 - i];
                    chart.add(bar.getName(), bar.getValue(), bar.getCategory());
                }

                StdDraw.clear();
                chart.draw();

                StdDraw.show();
                StdDraw.pause(100);

                chart.reset();
                bars = null;
            }
        }
    }
}
