package com.tds.cameraImpl;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.Webcam;

public class Xuggler {

    private static final double FRAME_RATE = 50;

    private static final int SECONDS_TO_RUN_FOR = 20;

// private static final String outputFilename = "~/tmp/v_test.mp4";
    private static final String outputFilename = "C:/Users/fibu/Desktop/telematikprojekt/v_test2.mp4";

    private static Dimension screenBounds;

    private static Webcam webcam;

// private static final Logger LOGGER = Logger.getLogger(Xuggler.getClass().getName());

    public static void main(String[] args) {

        // Webcam to read from
        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
// webcam.setViewSize(WebcamResolution.HD720.getSize());
        webcam.open();

        long startTime = System.nanoTime();

        for (int index = 0; index < SECONDS_TO_RUN_FOR * FRAME_RATE; index++) {

            // take the screen shot
            BufferedImage w_image = webcam.getImage();

            // convert to the right image type
            BufferedImage bgrScreen = convertToType(w_image, BufferedImage.TYPE_3BYTE_BGR);

            // sleep for frame rate milliseconds
            try {
                Thread.sleep((long) (1000 / FRAME_RATE));
            } catch (InterruptedException e) {
                // ignore
            }

        }

        // close webcam so other programms can use this
        webcam.close();

        // tell the writer to close and write the trailer if needed

    }

    public static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {

        BufferedImage image;

        // if the source image is already the target type, return the source image
        if (sourceImage.getType() == targetType) {
            image = sourceImage;
        }
        // otherwise create a new image of the target type and draw the new image
        else {
            image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
            image.getGraphics().drawImage(sourceImage, 0, 0, null);
        }

        return image;

    }

}
