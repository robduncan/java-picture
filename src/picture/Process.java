package picture;

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
                int red = 255 - pixel.getRed();
                int blue = 255 - pixel.getBlue();
                int green = 255 - pixel.getGreen();
                Color color = new Color(red, green, blue);
                picture.setPixel(i, j, color);

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
                newPicture.setPixel(j, i, pixel);
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
        Picture newPicture = Utils.createPicture(minWidth, minHeight);
        int numberOfPictures = pictures.length;
        for(Picture picture : pictures) {
            minWidth = Math.min(minWidth, picture.getWidth());
            minHeight = Math.min(minHeight, picture.getHeight());
        }
        for(int i = 0; i < minWidth; i++) {
            for(int j = 0; j < minHeight; j++) {
                Color pixel = pictures[0].getPixel(i, j);
                for(int m = 1; m < numberOfPictures; m++) {
                    addPixel(pixel, pictures[m].getPixel(i, j));
                }
                avgPixel(pixel, numberOfPictures);
                newPicture.setPixel(i, j, pixel);
            }
        }
        picture = newPicture;
    }


    public void blur() {
        Picture newPicture = picture;
        for(int i = 1; i < width - 1; i++) {
            for(int j = 1; j < height - 1; j++) {
                Color pixel1 = new Color(0, 0, 0);
                for(int m = i - 1; m <= i + 1; m++) {
                    for (int n = j - 1; n <= j + 1; n++) {
                        Color pixel2 = picture.getPixel(m, n);
                        addPixel(pixel1, pixel2);
                    }
                }
                avgPixel(pixel1, 9);
                newPicture.setPixel(i, j, pixel1);
            }
        }
        picture = newPicture;
    }

    private void addPixel(Color pixel1, Color pixel2) {
        pixel1.setRed(pixel1.getRed() + pixel2.getRed());
        pixel1.setBlue(pixel1.getBlue() + pixel2.getBlue());
        pixel1.setGreen(pixel1.getGreen() + pixel2.getGreen());
    }

    private void avgPixel(Color pixel, int n) {
        pixel.setRed(pixel.getRed() / n);
        pixel.setBlue(pixel.getBlue() / n);
        pixel.setGreen(pixel.getGreen() / n);
    }

}
