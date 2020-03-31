/**
 * created:    2019/09/16
 * <p>
 * {@code ActivationFunction} is to compute various activation functions that arise in neural networks.
 * An activation function is a function that maps real numbers into a desired range, such as between 0 and 1 or between –1 and +1.
 *
 * @author Xiaoyu Zhang
 */

public class ActivationFunction {

    // Returns the Heaviside function of x.
    public static double heaviside(double x)
    {
        if (Double.isNaN(x))
            return Double.NaN;

        if (x < 0)              return 0;
        else if (x == 0)        return 0.5;
        else                    return 1;
    }

    // Returns the sigmoid function of x.
    public static double sigmoid(double x)
    {
        if (Double.isNaN(x))
            return Double.NaN;

        return 1 / (1 + Math.pow(Math.E, -1.0 * x));
    }

    // Returns the hyperbolic tangent of x.
    public static double tanh(double x)
    {
        if (Double.isNaN(x))    return Double.NaN;

        if (x >= 20)            return 1.0;
        if (x <= -20)           return -1.0;

        double t1 = Math.pow(Math.E, x);
        double t2 = Math.pow(Math.E, -1.0 * x);
        return (t1 - t2) / (t1 + t2);
    }

    // Returns the softsign function of x.
    public static double softsign(double x)
    {
        if (Double.isNaN(x))                return Double.NaN;
        if (x == Double.POSITIVE_INFINITY)  return 1.0;
        if (x == Double.NEGATIVE_INFINITY)  return -1.0;

        return x / (1 + Math.abs(x));
    }

    // Returns the square nonlinearity function of x.
    public static double sqnl(double x)
    {
        if (Double.isNaN(x))
            return Double.NaN;

        if (x <= -2)                return -1;
        else if (x > -2 && x < 0)   return (x + x * x / 4);
        else if (x >= 0 && x < 2)   return (x - x * x / 4);
        else                        return 1;
    }

    // Takes a double command-line argument x and prints each activation
    // function, evaluated, in the format (and order) given below.
    public static void main(String[] args)
    {
        double x = Double.parseDouble(args[0]);

        StdOut.println("heaviside(" + x + ")" + " = " + heaviside(x));
        StdOut.println("sigmoid(" + x + ")" + " = " + sigmoid(x));
        StdOut.println("tanh(" + x + ")" + " = " + tanh(x));
        StdOut.println("softsign(" + x + ")" + " = " + softsign(x));
        StdOut.println("sqnl(" + x + ")" + " = " + sqnl(x));
    }

}
