/**
 * created:    2019/10/11
 * <p>
 * {@code AudioCollage} is a library to manipulate digital audio and use that library to create an audio collage.
 * We will represent sound as an array of real numbers between –1 and +1, with 44,100 samples per second.
 *
 * <p>
 * Amplify. Crate a new sound that contains the same samples as an existing sound, but with each sample multiplied by a constant α.
 * This increases the volume when α > 1 and decreases it when 0 < α < 1.
 *
 * <p>
 * Reverse. Create a new sound that contains the same samples as an existing sound, but in reverse order.
 * This can lead to unexpected and entertaining results.
 *
 * <p>
 * Merge/join. Create a new sound that combines two existing sounds by appending the second one after the first.
 * If the two sounds have m and n samples, then the resulting sound has m + n samples. This enables you to play two sounds sequentially.
 *
 * <p>
 * Mix/overlay. Create a new sound that combines two existing sounds by summing the values of the corresponding samples.
 * If one sound is longer than the other, append 0s to the shorter sound before summing. This enables you to play two sounds simultaneously.
 *
 * <p>
 * Change speed. Create a new sound that changes the duration of an existing sound via resampling. If the existing sound has n samples,
 * then the new sound has ⌊n/α⌋ samples for some constant α > 0, with sample i of the new sound having the same amplitude
 * as sample ⌊iα⌋ of the existing sound.
 *
 * @author Xiaoyu Zhang
 */

public class AudioCollage {

    // Returns a new array that rescales a[] by a multiplicative factor of alpha.
    public static double[] amplify(double[] a, double alpha)
    {

        double[] b = new double[a.length];

        // If alpha is greater than 1, the result may be greater than 1.
        // It's OK to manipulate such values along the way, because StdAudio.play method will handle it.
        for (int i = 0; i < a.length; i++)
            b[i] = a[i] * alpha;
        return b;
    }

    // Returns a new array that is the reverse of a[].
    public static double[] reverse(double[] a)
    {
        double[] b = new double[a.length];
        for (int i = (a.length - 1); i >= 0; i--)
            b[a.length - 1 - i] = a[i];

        return b;
    }

    // Returns a new array that is the concatenation of a[] and b[],
    public static double[] merge(double[] a, double[] b)
    {
        double[] c = new double[a.length + b.length];

        int i, j;

        for (i = 0; i < a.length; i++)
            c[i] = a[i];

        for (j = 0; j < b.length; j++)
            c[i+j] = b[j];

        return c;
    }

    // Returns a new array that is the sum of a[] and b[],
    // padding the shorter arrays with trailing 0s if necessary.
    public static double[] mix(double[] a, double[] b)
    {
        int len = Math.max(a.length, b.length);
        double[] c = new double[len];

        // When summing up, the result may be greater than 1.
        // It's OK to manipulate such values along the way, because StdAudio.play method will handle it.
        for (int i = 0; i < len; i++)
        {
            if (i >= a.length)              c[i] = b[i] + 0.0;
            else if (i >= b.length)         c[i] = a[i] + 0.0;
            else                            c[i] = a[i] + b[i];
        }

        return c;
    }

    // Returns a new array that changes the speed by the given factor.
    public static double[] changeSpeed(double[] a, double alpha)
    {
        double[] b = new double[(int) Math.floor((a.length / alpha))];

        int index;  // array index for new array
        for (int i = 0; i < b.length; i++) {
            index = (int) Math.floor(alpha * i);
            b[i] = a[index];
        }

        return b;
    }

    // Creates an audio collage and plays it on standard audio.
    // See below for the requirements.
    public static void main(String[] args)
    {
        double[] a = StdAudio.read("/Users/zhangxiaoyu02/IdeaProjects/algorithms/audio/piano.wav");
        double[] b = StdAudio.read("/Users/zhangxiaoyu02/IdeaProjects/algorithms/audio/singer.wav");

        StdAudio.play(amplify(a, 0.5));
        StdAudio.play(reverse(a));
        StdAudio.play(merge(a, b));
        StdAudio.play(mix(a, b));
        StdAudio.play(changeSpeed(a, 0.5));
    }
}
