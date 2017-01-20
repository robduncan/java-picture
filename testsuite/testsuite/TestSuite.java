package testsuite;

import static junit.framework.Assert.assertEquals;
import static testsuite.TestSuiteHelper.runMain;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import picture.Utils;

public class TestSuite {

  @Rule
  public TemporaryFolder tmpFolder = new TemporaryFolder();

  @Test
  public void invertBlack() throws IOException {
    assertEquals(Utils.loadPicture("images/white64x64.png"),
        runMain(tmpFolder, "invert", "images/black64x64.png"));
  }

  @Test
  public void grayscaleBlack() throws IOException {
    assertEquals(Utils.loadPicture("images/black64x64.png"),
        runMain(tmpFolder, "grayscale", "images/black64x64.png"));
  }

  @Test
  public void rotate90Green() throws IOException {
    assertEquals(Utils.loadPicture("images/green64x64R90doc.png"),
        runMain(tmpFolder, "rotate", "90", "images/green64x64doc.png"));
  }

  @Test
  public void flipVGreen() throws IOException {
    assertEquals(Utils.loadPicture("images/green64x64FVdoc.png"),
        runMain(tmpFolder, "flip", "V", "images/green64x64doc.png"));
  }

  @Test
  public void blurBWPatterns() throws IOException {
    assertEquals(Utils.loadPicture("images/bwpatternsblur64x64.png"),
        runMain(tmpFolder, "blur", "images/bwpatterns64x64.png"));
  }

  @Test
  public void blendBWAndRainbow() throws IOException {
    assertEquals(
        Utils.loadPicture("images/rainbowpatternsblend64x64.png"),
        runMain(tmpFolder, "blend", "images/bwpatterns64x64.png",
            "images/rainbow64x64doc.png"));
  }

  @Test
  public void invertWhite() throws IOException {
    assertEquals(Utils.loadPicture("images/black64x64.png"),
            runMain(tmpFolder, "invert", "images/white64x64.png"));
  }

  @Test
  public void invertRainbow() throws IOException {
    assertEquals(Utils.loadPicture("images/rainbowI64x64doc.png"),
            runMain(tmpFolder, "invert", "images/rainbow64x64doc.png"));
  }

  @Test
  public void grayscaleRainbow() throws IOException {
    assertEquals(Utils.loadPicture("images/rainbowGS64x64doc.png"),
            runMain(tmpFolder, "grayscale", "images/rainbow64x64doc.png"));
  }

  @Test
  public void grayscaleWhite() throws IOException {
    assertEquals(Utils.loadPicture("images/white64x64.png"),
            runMain(tmpFolder, "grayscale", "images/white64x64.png"));
  }

  @Test
  public void rotate90Blue() throws IOException {
    assertEquals(Utils.loadPicture("images/blueR9064x32doc.png"),
            runMain(tmpFolder, "rotate", "90", "images/blue64x32doc.png"));
  }

  @Test
  public void rotate180Blue() throws IOException {
    assertEquals(Utils.loadPicture("images/blueR18064x32doc.png"),
            runMain(tmpFolder, "rotate", "180", "images/blue64x32doc.png"));
  }

  @Test
  public void rotate270Blue() throws IOException {
    assertEquals(Utils.loadPicture("images/blueR27064x32doc.png"),
            runMain(tmpFolder, "rotate", "270", "images/blue64x32doc.png"));
  }

  @Test
  public void flipVBlue() throws IOException {
    assertEquals(Utils.loadPicture("images/blueFV64x32doc.png"),
            runMain(tmpFolder, "flip", "V", "images/blue64x32doc.png"));
  }

  @Test
  public void flipHBlue() throws IOException {
    assertEquals(Utils.loadPicture("images/blueFH64x32doc.png"),
            runMain(tmpFolder, "flip", "H", "images/blue64x32doc.png"));
  }

  @Test
  public void blurSunset() throws IOException {
    assertEquals(Utils.loadPicture("images/sunsetBlur64x32.png"),
            runMain(tmpFolder, "blur", "images/sunset64x32.png"));
  }

  @Test
  public void blurBlack() throws IOException {
    assertEquals(Utils.loadPicture("images/black64x64.png"),
            runMain(tmpFolder, "blur", "images/black64x64.png"));
  }

  @Test
  public void blendRainbowAndSunset() throws IOException {
    assertEquals(
            Utils.loadPicture("images/rainbowsunsetBlend.png"),
            runMain(tmpFolder, "blend", "images/rainbow64x64doc.png",
                    "images/sunset64x32.png"));
  }

}
