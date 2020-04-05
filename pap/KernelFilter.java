/**
 * created:    2019/10/07
 * <p>
 * {@code KernelFilter} applies various kernel filters to images, such as Gaussian blur, sharpen, Laplacian, emboss, and motion blur.
 * A kernel filter modifies the pixels in an image by replacing each pixel with a linear combination of its neighboring pixels.
 * The matrix that characterizes the linear combination is known as the kernel.
 *
 * Remarks:
 * Kernel filters are widely used for image processing in applications such as Photoshop or GIMP. They are also used
 * in convolutional neural networks to extract features from an image and in digital cameras to remove camera shake.
 *
 * @author Xiaoyu Zhang
 */

import java.awt.Color;

public class KernelFilter {

    // Returns a new picture that applies a Gaussian blur filter to the given picture.
    public static Picture gaussian(Picture picture) {
        double[][] filterTemp = {{1.0, 2.0, 1.0}, {2.0, 4.0, 2.0}, {1.0, 2.0, 1.0}};

        double[][] filter = new double[filterTemp.length][filterTemp[0].length];

        // build the gaussian blur filter
        for (int m = 0; m < filterTemp.length; m++) {
            for (int n = 0; n < filterTemp[0].length; n++) {

                filter[m][n] = filterTemp[m][n] / 16.0;
            }
        }

        return kernel(picture, filter);
    }

    // Returns a new picture that applies a sharpen filter to the given picture.
    public static Picture sharpen(Picture picture) {
        double[][] filter = {{0.0, -1.0, 0.0}, {-1.0, 5.0, -1.0}, {0.0, -1.0, 0.0}};      // sharpen filter

        return kernel(picture, filter);
    }

    // Returns a new picture that applies an Laplacian filter to the given picture.
    public static Picture laplacian(Picture picture) {
        double[][] filter = {{-1.0, -1.0, -1.0}, {-1.0, 8.0, -1.0}, {-1.0, -1.0, -1.0}};  // Laplacian filter

        return kernel(picture, filter);
    }

    // Returns a new picture that applies an emboss filter to the given picture.
    public static Picture emboss(Picture picture) {
        double[][] filter = {{-2.0, -1.0, 0.0}, {-1.0, 1.0, 1.0}, {0.0, 1.0, 2.0}};         // emboss filter

        return kernel(picture, filter);
    }

    // Returns a new picture that applies a motion blur filter to the given picture.
    public static Picture motionBlur(Picture picture) {
        double[][] filterTemp = {{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0}};

        double[][] filter = new double[filterTemp.length][filterTemp[0].length];

        // build motion blur filter
        for (int m = 0; m < filterTemp.length; m++) {
            for (int n = 0; n < filterTemp[0].length; n++) {

                filter[m][n] = filterTemp[m][n] / 9.0;
            }
        }

        return kernel(picture, filter);
    }

    // Returns a new picture that applies an arbitrary kernel filter to the given picture.
    private static Picture kernel(Picture picture, double[][] weights) {

        int rowFilter = weights.length;
        int colFilter = weights[0].length;

        // StdOut.printf("TRACE: rowFilter/2 = (%d), colFilter/2 = (%d) \n", rowFilter/2, colFilter/2);

        int width = picture.width();
        int height = picture.height();
        Picture target = new Picture(width, height);

        double sumRed, sumGreen, sumBlue;
        int red, green, blue, targetRed, targetGreen, targetBlue, col, row, i, j;
        Color srcColor, targetColor;

        // processing picture pixels starts from columns to rows
        for (col = 0; col < width; col++)
            for (row = 0; row < height; row++) {

                sumRed = sumGreen = sumBlue = 0.0;

                // processing kernel filter starts from rows to columns  
                for (i = 0; i < rowFilter; i++)
                    for (j = 0; j < colFilter; j++) {

                        // periodic wrap around, particularly for boundary conditions.
                        // Pay attention to how to calculate the column index and row index for the source picture.
                        // srcColor = picture.get((col - 1 + width + j) % width, (row - 1 + height + i) % height);
                        srcColor = picture.get((col - colFilter/2 + width + j) % width, (row - rowFilter/2 + height + i) % height);

                        red = srcColor.getRed();
                        green = srcColor.getGreen();
                        blue = srcColor.getBlue();

                        sumRed += weights[i][j] * red;
                        sumGreen += weights[i][j] * green;
                        sumBlue += weights[i][j] * blue;
                    }

                targetRed = (int) Math.round(sumRed);
                targetRed = Math.max(targetRed, 0);
                targetRed = Math.min(targetRed, 255);

                targetGreen = (int) Math.round(sumGreen);
                targetGreen = Math.max(targetGreen, 0);
                targetGreen = Math.min(targetGreen, 255);

                targetBlue = (int) Math.round(sumBlue);
                targetBlue = Math.max(targetBlue, 0);
                targetBlue = Math.min(targetBlue, 255);

                targetColor = new Color(targetRed, targetGreen, targetBlue);
                target.set(col, row, targetColor);
            }

        return target;
    }

    // Test client (ungraded).
    public static void main(String[] args) {
        Picture pic = new Picture(args[0]);
        // Picture target = sharpen(pic);
        Picture target = laplacian(pic);
        // Picture target = emboss(pic);
        // Picture target = gaussian(pic);
        // Picture target = motionBlur(pic);

        // pic.show();
        target.show();
    }
}