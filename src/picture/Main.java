package picture;

public class Main {

  public static void main(String[] args) {
    Picture initialPicture = getPicture(args);
    Picture modifiedPicture = applyProcess(initialPicture, args);
    savePicture(modifiedPicture, args);

  }

  private static Picture getPicture(String[] args) {
    String location = args[args.length - 2];
    return Utils.loadPicture(location);
  }

  private static void savePicture(Picture modifiedPicture, String[] args) {
    String destination = args[args.length - 1];
    Utils.savePicture(modifiedPicture, destination);
  }

  private static Picture applyProcess(Picture picture, String[] args) {
    String detail, process = args[0];
    Process processPicture = new Process(picture);
    switch (process) {
      case "invert": processPicture.invert(); break;
      case "grayscale": processPicture.grayscale(); break;
      case "rotate": {
        detail = args[1];
        switch (detail) {
          case "90": processPicture.rotate90(); break;
          case "180": processPicture.rotate180(); break;
          case "270": processPicture.rotate270(); break;
        }
      } break;
      case "flip": {
        detail = args[1];
        switch (detail) {
          case "H": processPicture.flipHorizontal(); break;
          case "V": processPicture.flipVertical(); break;
        }
      } break;
      case "blend": {
        int numberOfPictures = args.length - 2;
        Picture[] pictures = new Picture[numberOfPictures];
        for(int i = 0; i < numberOfPictures; i++) {
          pictures[i] = Utils.loadPicture(args[i + 1]);
        }
        processPicture.blend(pictures);
      } break;
      case "blur": processPicture.blur(); break;
    }

    return processPicture.getPicture();
  }


}
