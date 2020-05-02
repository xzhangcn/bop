import java.awt.Color;

/**
 * created:    2020/01/01
 * <p>
 * {@code SeamCarver} is a content-aware image resizing technique where the image is reduced in size
 * by one pixel of height (or width) at a time. A vertical seam in an image is a path of pixels connected
 * from the top to the bottom with one pixel in each row; a horizontal seam is a path of pixels connected
 * from the left to the right with one pixel in each column.
 *
 * @author Xiaoyu Zhang
 */
public class SeamCarver {
    private Picture pic;
    private int width, height;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture pic) {
        if (pic == null)
            throw new IllegalArgumentException("constructor: picture argument is null");

        this.pic = new Picture(pic);    // deep copy of the argument picture
        this.width = this.pic.width();
        this.height = this.pic.height();
    }

    // current picture
    public Picture picture() {
        return new Picture(this.pic);
    }

    // width of current picture
    public int width() {
        return this.width;
    }

    // height of current picture
    public int height() {
        return this.height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        // In image processing, pixel (x, y) refers to the pixel in column x and row y,
        // with pixel (0, 0) at the upper left corner and pixel (W − 1, H − 1) at the bottom right corner.
        // This is consistent with the Picture data type in algs4.jar.
        // Warning: this is the opposite of the standard mathematical notation
        // used in linear algebra where (i, j) refers to row i and column j.

        // Here, reuse the method validatePixel which originally designed for validating the coordinates
        // in the standard mathematical notation where (i, j) refers to row i and column j.
        if (!validatePixel(y, x))
            throw new IllegalArgumentException("energy: pixel (x, y) is out of range.");

        // define the energy of a pixel at the border of the image to be 1000,
        // so that it is strictly larger than the energy of any interior pixel.
        if (isOnBorder(x, y)) return 1000;

        return Math.sqrt(dualGradient(x, y));
    }

    // check whether pixel at column x and row y is on the border
    private boolean isOnBorder(int x, int y) {
        return ((x == 0) || (x == (this.width - 1)) || (y == 0) || (y == (this.height - 1)));
    }

    // dual gradent for pixel at column x and row y
    private int dualGradient_2(int x, int y) {
        Color colorNorth = this.pic.get(x, y - 1);
        Color colorSouth = this.pic.get(x, y + 1);
        Color colorWest = this.pic.get(x - 1, y);
        Color colorEast = this.pic.get(x + 1, y);

        int xGradient = (int) Math.pow(colorNorth.getRed() - colorSouth.getRed(), 2)
                + (int) Math.pow(colorNorth.getGreen() - colorSouth.getGreen(), 2)
                + (int) Math.pow(colorNorth.getBlue() - colorSouth.getBlue(), 2);
        int yGradient = (int) Math.pow(colorWest.getRed() - colorEast.getRed(), 2)
                + (int) Math.pow(colorWest.getGreen() - colorEast.getGreen(), 2)
                + (int) Math.pow(colorWest.getBlue() - colorEast.getBlue(), 2);

        return xGradient + yGradient;

    }

    // dual gradent for pixel at column x and row y
    private int dualGradient(int x, int y) {
        int colorNorth = this.pic.getRGB(x, y - 1);
        int rNorth = (colorNorth >> 16) & 0xFF;
        int gNorth = (colorNorth >> 8) & 0xFF;
        int bNorth = (colorNorth >> 0) & 0xFF;

        int colorSouth = this.pic.getRGB(x, y + 1);
        int rSouth = (colorSouth >> 16) & 0xFF;
        int gSouth = (colorSouth >> 8) & 0xFF;
        int bSouth = (colorSouth >> 0) & 0xFF;

        int colorWest = this.pic.getRGB(x - 1, y);
        int rWest = (colorWest >> 16) & 0xFF;
        int gWest = (colorWest >> 8) & 0xFF;
        int bWest = (colorWest >> 0) & 0xFF;

        int colorEast = this.pic.getRGB(x + 1, y);
        int rEast = (colorEast >> 16) & 0xFF;
        int gEast = (colorEast >> 8) & 0xFF;
        int bEast = (colorEast >> 0) & 0xFF;

        int xGradient = (int) Math.pow(rNorth - rSouth, 2)
                + (int) Math.pow(gNorth - gSouth, 2)
                + (int) Math.pow(bNorth - bSouth, 2);
        int yGradient = (int) Math.pow(rWest - rEast, 2)
                + (int) Math.pow(gWest - gEast, 2)
                + (int) Math.pow(bWest - bEast, 2);

        return xGradient + yGradient;

    }

    private class Pixel {
        public int i;
        public int j;

        public Pixel(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public String toString() {
            return "(" + i + ", " + j + ")";
        }
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transpose();
        int[] seamY = findVerticalSeam();
        transpose();

        return seamY;

    }

    private boolean validatePixel(int x, int y) {
        return (x >= 0) && (x < this.height) && (y >= 0) && (y < this.width);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        // construct a 2d energy array in the standard mathematical notation
        // where (i, j) refers to row i and column j
        double[][] energyGrid = new double[this.height][this.width];
        double[][] distTo = new double[this.height][this.width];
        Pixel[][] edgeTo = new Pixel[this.height][this.width];

        for (int i = 0; i < this.height; i++)
            for (int j = 0; j < this.width; j++)
                distTo[i][j] = Double.POSITIVE_INFINITY;

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++)
                energyGrid[j][i] = energy(i, j);
        }

        // testing purpose
        /*
        StdOut.println("*** energyGrid ***");
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++)
                StdOut.printf("%7.2f \t", energyGrid[i][j]);

            StdOut.println();
        }
        */

        // traverse this matrix treating some select entries as reachable
        // from (x, y) to calculate where the seam is located.
        int i = 0, j = 0;
        for (i = 0; i < this.height; i++) {
            for (j = 0; j < this.width; j++) {
                if (validatePixel(i + 1, j - 1)) {
                    if (distTo[i + 1][j - 1] > energyGrid[i][j] + energyGrid[i + 1][j - 1]) {
                        distTo[i + 1][j - 1] = energyGrid[i][j] + energyGrid[i + 1][j - 1];
                        edgeTo[i + 1][j - 1] = new Pixel(i, j);
                    }
                }

                if (validatePixel(i + 1, j)) {
                    if (distTo[i + 1][j] > energyGrid[i][j] + energyGrid[i + 1][j]) {
                        distTo[i + 1][j] = energyGrid[i][j] + energyGrid[i + 1][j];
                        edgeTo[i + 1][j] = new Pixel(i, j);
                    }
                }

                if (validatePixel(i + 1, j + 1)) {
                    if (distTo[i + 1][j + 1] > energyGrid[i][j] + energyGrid[i + 1][j + 1]) {
                        distTo[i + 1][j + 1] = energyGrid[i][j] + energyGrid[i + 1][j + 1];
                        edgeTo[i + 1][j + 1] = new Pixel(i, j);
                    }
                }
            }

            // update the energy value on the next row
            // StdOut.printf("row = %d \n", (i + 1));
            for (j = 0; j < this.width; j++) {
                if (validatePixel(i + 1, j)) {
                    energyGrid[i + 1][j] = distTo[i + 1][j];
                    // StdOut.printf("%7.2f \t", energyGrid[i + 1][j]);
                }
            }

            // StdOut.println();

        }

        // testing purpose
        /*
        StdOut.println("*** edgeTo ***");
        for (i = 0; i < this.height; i++) {
            for (j = 0; j < this.width; j++)
                StdOut.printf("%s \t", edgeTo[i][j]);

            StdOut.println();
        }

        // testing purpose
        /*
        StdOut.printf("i = %d, j = %d \n", i, j);
        StdOut.println("*** distTo ***");
        for (i = 0; i < this.height; i++) {
            for (j = 0; j < this.width; j++)
                StdOut.printf("%7.2f \t", distTo[i][j]);

            StdOut.println();
        }
        */

        // find the minimum sum of energy at the bottom
        double minEnergy = distTo[this.height - 1][0];
        Pixel minPixel = edgeTo[this.height - 1][0];
        int minX = 0;
        for (i = 1; i < this.width; i++) {
            if (distTo[this.height - 1][i] < minEnergy) {
                minEnergy = distTo[this.height - 1][i];
                minPixel = edgeTo[this.height - 1][i];
                minX = i;
            }
        }

        // array to store the sequence of indices for vertical seam
        int[] seamX = new int[height];
        seamX[height - 1] = minX;

        // traverse from the bottom, and fill up the seamX array
        for (i = this.height - 1; i > 0; i--) {
            minPixel = edgeTo[i][minPixel.j];
            seamX[i - 1] = minPixel.j;
        }

        return seamX;
    }

    // transpose the image
    private void transpose() {
        Picture revertedPic = new Picture(this.height, this.width);

        for (int i = 0; i < this.height; i++)
            for (int j = 0; j < this.width; j++) {
                revertedPic.set(i, j, this.pic.get(j, i));
            }

        this.width = revertedPic.width();
        this.height = revertedPic.height();
        this.pic = revertedPic;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        // StdOut.println("*** remove horizontal ***");

        transpose();
        removeVerticalSeam(seam);
        transpose();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

        // StdOut.println("*** remove vertical ***");

        if (!validateSeam(seam, true)) throw new IllegalArgumentException("invalid seam argument");
        Picture newPic = new Picture(this.width - 1, this.height);

        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                if (col != seam[row])
                    newPic.set(col, row, this.pic.get(col, row));
                else {
                    for (int k = seam[row]; k < this.width - 1; k++)
                        newPic.set(k, row, this.pic.get(k + 1, row));
                    break;
                }
            }
        }

        // this.pic = new Picture(newPic);
        this.pic = newPic;
        this.width = this.pic.width();
        this.height = this.pic.height();
    }

    private boolean validateSeam(int[] seam, boolean vertical) {
        if (seam == null) {
            // StdOut.println("seam is null");
            return false;
        }
        int seamLen = seam.length;

        if (vertical) {
            if (this.width <= 1) {
                // StdOut.println("vertical: width is less than 1");
                return false;
            }
            if (seamLen != this.height) {
                // StdOut.println("vertical: seamLen not equal to the height");
                return false;
            }
        }
        if (!vertical) {
            if (this.height <= 1) {
                // StdOut.println("horizontal: width is less than 1");
                return false;
            }
            if (seamLen != this.width) {
                // StdOut.println("horizontal: seamLen not equal to the height");
                return false;
            }
        }

        // testing purpose
        /*
        for (int i = 0; i < seamLen; i++) {
            StdOut.printf("%d, ", seam[i]);
            if (i % 20 == 0)
                StdOut.println();
        }
        */

        for (int i = 0; i < seamLen - 1; i++)
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                // StdOut.println("seam consecutive difference is not 1");
                return false;
            }

        return true;
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        // testing client located in other java files (PrintEnergy, PrintSeam...)
    }

}
