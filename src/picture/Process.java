package picture;

import utils.Tuple;

/**
 * Encapsulates and provides different types of transformations which can be
 * applied to an image.
 */
public class Process {

    /**
     * The picture to be processed.
     */
    private Picture picture;

    /**
     * The dimensions of the picture.
     */
    private int width, height;


    /**
     * Default Construct. Construct a new Process object with the specified
     * picture.
     *
     * @param picture
     *          the picture to be processed
     */
    public Process(Picture picture) {
        this.picture = picture;
        this.height = picture.getHeight();
        this.width = picture.getWidth();
    }

    /**
     * Get the processed picture.
     *
     * @return the processed picture
     */
    public Picture getPicture() {
        return this.picture;
    }

    /**
     * Inverts the picture.
     */
    public void invert() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                Color pixel = picture.getPixel(i, j);
                invertPixel(pixel);
                picture.setPixel(i, j, pixel);

            }
        }
    }

    /**
     * Inverts a pixel.
     *
     * @param pixel
     *          the pixel to be inverted
     */
    private void invertPixel(Color pixel) {
        pixel.setRed(255 - pixel.getRed());
        pixel.setBlue(255 - pixel.getBlue());
        pixel.setGreen(255 - pixel.getGreen());
    }

    /**
     * Applies grayscale to the picture.
     */
    public void grayscale() {

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                Color pixel = picture.getPixel(i, j);
                int avg = (pixel.getRed() + pixel.getBlue() + pixel.getGreen()) / 3;
                Color color = new Color(avg, avg, avg);
                picture.setPixel(i, j, color);

            }
        }

    }

    /**
     * Rotates the picture 90 degrees to the right.
     */
    public void rotate90() {
        Picture newPicture = Utils.createPicture(height, width);
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                Color pixel = picture.getPixel(i, j);
                newPicture.setPixel(height - j - 1, i, pixel);
            }
        }
        picture = newPicture;
    }

    /**
     * Rotates the picture 180 degrees to the right.
     */
    public void rotate180() {
        Picture newPicture = Utils.createPicture(width, height);
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                Color pixel = picture.getPixel(i, j);
                newPicture.setPixel(width - 1 - i, height -1 - j, pixel);
            }
        }
        picture = newPicture;
    }

    /**
     * Rotates the picture 270 degrees to the right.
     */
    public void rotate270() {
        rotate180();
        rotate90();
    }

    /**
     * Flips the picture about the y-axis.
     */
    public void flipHorizontal() {
        Picture newPicture = Utils.createPicture(width, height);
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                Color pixel = picture.getPixel(i, j);
                newPicture.setPixel(width - 1 - i, j, pixel);
            }
        }
        picture = newPicture;
    }

    /**
     * Flips the picture about the x-axis.
     */
    public void flipVertical() {
        Picture newPicture = Utils.createPicture(width, height);
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                Color pixel = picture.getPixel(i, j);
                newPicture.setPixel(i, height - 1 - j, pixel);
            }
        }
        picture = newPicture;
    }

    /**
     * Takes a list of pictures and combines them by averaging each colour
     * component of each pixel across the list of pictures at any point.
     * The resulting picture will have dimensions corresponding to the
     * smallest individual width and individual height within the given
     * set of pictures.
     *
     * @param pictures
     *          the array containing the pictures to be blended together
     */
    public void blend(Picture[] pictures) {
        int minWidth = width;
        int minHeight = height;
        getMins(minWidth, minHeight, pictures);
        Picture newPicture = Utils.createPicture(minWidth, minHeight);

        int numberOfPictures = pictures.length;

        for(int i = 0; i < minWidth; i++) {
            for(int j = 0; j < minHeight; j++) {

                Color pixel = pictures[0].getPixel(i, j);

                for(int m = 1; m < numberOfPictures; m++) {
                    addPixel(pixel, pictures[m].getPixel(i, j));
                }
                pixelDiv(pixel, numberOfPictures);

                newPicture.setPixel(i, j, pixel);
            }
        }
        picture = newPicture;
    }

    /**
     * Computes the minimum individual width and individual height from an
     * array of pictures.
     *
     * @param minWidth
     *          the variable to hold the computed minimum width
     * @param minHeight
     *          the variable to hold the computed minimum height
     * @param pictures
     *          the array of pictures
     */
    private void getMins(int minWidth, int minHeight, Picture[] pictures) {
        for(Picture p : pictures) {
            minWidth = Math.min(minWidth, p.getWidth());
            minHeight = Math.min(minHeight, p.getHeight());
        }
    }

    /**
     * Blurs the picture by setting each pixel-value to the average value of
     * surrounding pixels.
     */
    public void blur() {
        Picture newPicture = Utils.createPicture(width, height);
        for(int i = 0; i < width ; i++) {
            for(int j = 0; j < height; j++) {
                if(!isOnEdge(i, j)) {
                    Color blurredPixel = getBlurredPixel(i, j);
                    newPicture.setPixel(i, j, blurredPixel);
                }
                else {
                    newPicture.setPixel(i, j, picture.getPixel(i, j));
                }
            }
        }
        picture = newPicture;
    }

    /**
     * Checks if a point is on the edge of the picture.
     *
     * @param i
     *          the x-coordinate of the point
     * @param j
     *          the y-coordinate of the point
     */
    private boolean isOnEdge (int i, int j) {
        if(i == 0 || j == 0 || i == width - 1 || j == height - 1) {
            return true;
        }
        return false;

    }

    /**
     * Gets the blurred pixel by computing the average of surrounding pixels.
     *
     * @param i
     *          the x-coordinate of the pixel to be blurred
     * @param j
     *          the y-coordinate of the pixel to be blurred
     */
    private Color getBlurredPixel(int i, int j) {
        Color blurredPixel = new Color(0, 0, 0);
        for (int m = i - 1; m <= i + 1; m++) {
            for (int n = j - 1; n <= j + 1; n++) {
                Color pixel = picture.getPixel(m, n);
                addPixel(blurredPixel, pixel);
            }
        }
        pixelDiv(blurredPixel, 9);
        return blurredPixel;
    }

    /**
     * Combines pictures from an array to create a mosaic with tiles of a
     * certain size.
     * The resulting picture will have dimensions corresponding to the smallest
     * individual width and individual height in the array, trimmed to be a
     * multiple of the tile-size.
     * The tiles in the picture are arranged so that for every tile, the
     * neighbouring tiles to the east and south come from the next picture in
     * the list, (wrapping round as appropriate).
     *
     * @param tileSize
     *          the size of a single square mosaic tile
     * @param pictures
     *          the array of pictures to be combined
     */
    public void mosaic(int tileSize, Picture[] pictures) {
        Tuple<Integer, Integer> dimensions = trimDimensions(width, height, tileSize, pictures);
        int minWidth = dimensions.getX();
        int minHeight = dimensions.getY();
        Picture newPicture = Utils.createPicture(minWidth, minHeight);
        int numberOfPictures = pictures.length;
        int m = 0, n;
         for(int j = 0; j < minHeight; j += tileSize) {
             n = m;
             for(int i = 0; i < minWidth; i += tileSize) {
                 changeTile(i, j, tileSize, newPicture, pictures[n]);
                 n++;
                 n = n % numberOfPictures;
            }
             m++;
             m = m % numberOfPictures;
        }
        picture = newPicture;
    }

    /**
     * Modifies picture1 by replacing a certain tile with the corresponding
     * tile from picture2.
     *
     * @param i
     *          the x-coordinate of the top-left corner of the tile
     * @param j
     *          the y-coordinate of the top-left corner of the tile
     * @param tileSize
     *          the size of a single square mosaic tile
     * @param picture1
     *          the picture to be modified by replacing the square region with
     *          a mosaic tile
     * @param picture2
     *          the picture from which the tile will be taken
     */
    private void changeTile(int i, int j, int tileSize, Picture picture1, Picture picture2) {
        for(int m = i; m < i + tileSize; m ++) {
            for(int n = j; n < j + tileSize; n ++) {
                picture1.setPixel(m, n, picture2.getPixel(m, n));

            }
        }
    }

    /**
     * Gets the minimum individual width and height from the array of
     * pictures and trims them to a multiple of tileSize.
     *
     * @param minWidth
     *          the variable which will hold the minimum and trimmed width
     * @param minHeight
     *          the variable which will hold the minimum and trimmed height
     * @param tileSize
     *          the size of a single square mosaic tile
     * @param pictures
     *          the array of pictures
     */
    private Tuple<Integer, Integer> trimDimensions(int minWidth, int minHeight, int tileSize, Picture[] pictures) {
        getMins(minWidth, minHeight, pictures);
        minWidth = minWidth - (minWidth % tileSize);
        minHeight = minHeight - (minHeight % tileSize);
        Tuple<Integer, Integer> dimensions= new utils.Tuple<>(minWidth, minHeight);
        return dimensions;
    }

    /**
     * Adds the individual colour components of the two pixels.
     *
     * @param pixel1
     *          the pixel which will hold the added values of each colour
     *          component
     * @param pixel2
     *          the pixel to be added
     */
    private void addPixel(Color pixel1, Color pixel2) {
        pixel1.setRed(pixel1.getRed() + pixel2.getRed());
        pixel1.setBlue(pixel1.getBlue() + pixel2.getBlue());
        pixel1.setGreen(pixel1.getGreen() + pixel2.getGreen());
    }

    /**
     * Divides the intensity of each colour component by a number.
     *
     * @param pixel
     *          the pixel whixh will be modified by dividing each colour
     *          compnent intensity
     * @param n
     *          the integer by which the intensity of the colour components
     *          will be divided
     */
    private void pixelDiv(Color pixel, int n) {
        pixel.setRed(pixel.getRed() / n);
        pixel.setBlue(pixel.getBlue() / n);
        pixel.setGreen(pixel.getGreen() / n);
    }

}
