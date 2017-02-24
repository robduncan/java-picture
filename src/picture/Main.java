package picture;

/**
 * The main program which allows a user to compute image transformations.
 */
public class Main {

  /**
   * The program will be invoked with command line arguments specifying which
   * operation to perform and then the input and output image locations.
   *
   * @param args
   *          the instructions for the transformation
   */
  public static void main(String[] args) {
    Picture initialPicture = getPicture(args);
    Picture modifiedPicture = applyProcess(initialPicture, args);
    savePicture(modifiedPicture, args);

  }

  /**
   * Gets the initial picture from the specified destination, prints an error
   * message if the location is invalid.
   *
   * @param args
   *          the instructions for the transformation, containing the location
   *
   * @return the Picture object corresponding to the image from the given
   * location
   */
  private static Picture getPicture(String[] args) {
    String location = args[args.length - 2];
    Picture temp = Utils.loadPicture(location);
    if(temp == null) {
      System.err.println("invalid location");
    }
    return temp;
  }

  /**
   * Saves the transformed picture to the given location.
   */
  private static void savePicture(Picture modifiedPicture, String[] args) {
    String destination = args[args.length - 1];
    if(!Utils.savePicture(modifiedPicture, destination)) {
      System.err.println("invalid destination");
    }
  }

  /**
   * Parses the transformation type from the command line arguments and
   * applies it to the picture object.
   *
   * @param picture
   *          the initial Picture object
   * @param args
   *          the specifications for the transformation
   *
   * @return the transformed Picture object
   */
  private static Picture applyProcess(Picture picture, String[] args) {
    String process = args[0];
    Process processPicture = new Process(picture);
    switch (process) {
      case "invert": processPicture.invert(); break;
      case "grayscale": processPicture.grayscale(); break;
      case "rotate": rotate(args[1], processPicture); break;
      case "flip": flip(args[1], processPicture); break;
      case "blend": blend(args, processPicture); break;
      case "blur": processPicture.blur(); break;
      case "mosaic": mosaic(args, processPicture); break;
    }
    return processPicture.getPicture();
  }

  /**
   * Applies a certain rotation transformation.
   *
   * @param detail
   *          the string representing which rotate transformation must be
   *          applied
   * @param processPicture
   *          the Process object on which the transformation will be applied
   */
  private static void rotate(String detail, Process processPicture) {
    switch (detail) {
      case "90": processPicture.rotate90(); break;
      case "180": processPicture.rotate180(); break;
      case "270": processPicture.rotate270(); break;
    }
  }

  /**
   * Applies a certain flip transformation.
   *
   * @param detail
   *          the string representing which flip transformation must be
   *          applied
   * @param processPicture
   *          the Process object on which the transformation will be applied
   */
  private static void flip(String detail, Process processPicture) {
    switch (detail) {
      case "H": processPicture.flipHorizontal(); break;
      case "V": processPicture.flipVertical(); break;
    }
  }

  /**
   * Loads all the pictures from the command line arguments and applies the
   * blend transformation.
   *
   * @param args
   *          the command line arguments specifying the location of all the
   *          pictures to be blended
   * @param processPicture
   *          the Process object on which the transformation will be applied
   */
  private static void blend(String[] args, Process processPicture) {
    int numberOfPictures = args.length - 2;
    Picture[] pictures = new Picture[numberOfPictures];

    for(int i = 0; i < numberOfPictures; i++) {
      pictures[i] = Utils.loadPicture(args[i + 1]);
      if(pictures[i] == null) {
        System.err.println("invalid location");
      }
    }

    processPicture.blend(pictures);
  }

  /**
   * Loads all the pictures from the command line arguments and applies the
   * mosaic transformation.
   *
   * @param args
   *          the command line arguments specifying the location of all the
   *          pictures to be combined into the mosaic
   * @param processPicture
   *          the Process object on which the transformation will be applied
   */
  private static void mosaic(String[] args, Process processPicture) {
    int tileSize = Integer.parseInt(args[1]);
    int numberOfPictures = args.length - 3;
    Picture[] pictures = new Picture[numberOfPictures];

    for(int i = 0; i < numberOfPictures; i++) {
      pictures[i] = Utils.loadPicture(args[i + 2]);
      if(pictures[i] == null) {
        System.err.println("invalid location");
      }
    }

    processPicture.mosaic(tileSize, pictures);
  }

}
