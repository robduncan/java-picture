package picture;

import utils.Tuple;


public class Process {

    private Picture picture;
    private int width, height;

    public Process(Picture picture) {
        this.picture = picture;
        this.height = picture.getHeight();
        this.width = picture.getWidth();
    }

    public Picture getPicture() {
        return this.picture;
    }

    public void invert() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                Color pixel = picture.getPixel(i, j);
                invertPixel(pixel);
                picture.setPixel(i, j, pixel);

            }
        }
    }

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

    public void rotate270() {
        rotate180();
        rotate90();
    }

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

    private void getMins(int minWidth, int minHeight, Picture[] pictures) {
        for(Picture p : pictures) {
            minWidth = Math.min(minWidth, p.getWidth());
            minHeight = Math.min(minHeight, p.getHeight());
        }
    }

    public void blur() {
        Picture newPicture = Utils.createPicture(width, height);
        for(int i = 0; i < width ; i++) {
            for(int j = 0; j < height; j++) {
                if(!isOnEdge(i, j)) {
                    Color blurredPixel = getBulrredPixel(i, j);
                    newPicture.setPixel(i, j, blurredPixel);
                }
                else {
                    newPicture.setPixel(i, j, picture.getPixel(i, j));
                }
            }
        }
        picture = newPicture;
    }

    public void mosaic(int tileSize, Picture[] pictures) {
        Tuple<Integer, Integer> dimensions = trimDimensions(width, height, tileSize, pictures);
        int minWidth = dimensions.getX();
        int minHeight = dimensions.getY();
        Picture newPicture = Utils.createPicture(minWidth, minHeight);
        int numberOfPictures = pictures.length;
        int m = 0, n = 0;
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

    private boolean isOnEdge (int i, int j) {
        if(i == 0 || j == 0 || i == width - 1 || j == height - 1) {
            return true;
        }
        return false;

    }

    private Color getBulrredPixel(int i, int j) {
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

    private void changeTile(int i, int j, int tileSize, Picture picture1, Picture picture2) {
        for(int m = i; m < i + tileSize; m ++) {
            for(int n = j; n < j + tileSize; n ++) {
                picture1.setPixel(m, n, picture2.getPixel(m, n));

            }
        }
    }

    private Tuple<Integer, Integer> trimDimensions(int minWidth, int minHeight, int tileSize, Picture[] pictures) {
        getMins(minWidth, minHeight, pictures);
        minWidth = minWidth - (minWidth % tileSize);
        minHeight = minHeight - (minHeight % tileSize);
        Tuple<Integer, Integer> dimensions= new utils.Tuple<>(minWidth, minHeight);
        return dimensions;
    }

    private void invertPixel(Color pixel) {
        pixel.setRed(255 - pixel.getRed());
        pixel.setBlue(255 - pixel.getBlue());
        pixel.setGreen(255 - pixel.getGreen());
    }

    private void addPixel(Color pixel1, Color pixel2) {
        pixel1.setRed(pixel1.getRed() + pixel2.getRed());
        pixel1.setBlue(pixel1.getBlue() + pixel2.getBlue());
        pixel1.setGreen(pixel1.getGreen() + pixel2.getGreen());
    }

    private void pixelDiv(Color pixel, int n) {
        pixel.setRed(pixel.getRed() / n);
        pixel.setBlue(pixel.getBlue() / n);
        pixel.setGreen(pixel.getGreen() / n);
    }

}
