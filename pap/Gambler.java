/**
 * created:    2020/03/18
 * <p>
 * Simulate a gambler who starts with $stake and place fair $1 bets until she goes broke or reaches goal.
 * Keep track of the number of times she wins and the number of bets she makes.
 * Run the experiment $trial times, average the results and print them out.
 * <p>
 * Probability of winning  = $stake / $goal
 * Expected number of bets = $stake * desired gain = $stake * ($goal - $stake)
 *
 * @author Xiaoyu Zhang
 */

public class Gambler {
    public static void main(String[] args) {
        int stake = Integer.parseInt(args[0]);      // gambler's starting bankroll
        int goal = Integer.parseInt(args[1]);       // gambler's desired bankroll
        int trials = Integer.parseInt(args[2]);     // number of trials to perform

        int bets = 0;       // total number of bets made
        int wins = 0;       // total number of games won

        // repeat trial times
        for (int t = 0; t < trials; t++) {

            // do one gambler's run simulation
            int cash = stake;
            while (cash > 0 && cash < goal) {
                bets++;
                if (Math.random() < 0.5)
                    cash++;     // win $1
                else
                    cash--;     // loss $1
            }

            if (cash == goal)
                wins++;         // gambler achieves desired goal
        }

        // Print out the results
        System.out.printf("%d wins of %d\n", wins, trials);
        System.out.printf("Percent of games won = %.2f\n", 100.0 * wins/trials);
        System.out.printf("Avg # bets           = %.2f\n", 1.0 * bets/trials);
    }
}
