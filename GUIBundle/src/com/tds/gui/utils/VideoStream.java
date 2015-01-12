package com.tds.gui.utils;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

/**
 *
 *
 * @author fibu
 *
 */
public class VideoStream {
// private final String outputFilename;
    private final IMediaWriter writer;
    long startTime = 0;

    /**
     * Methode zum erzeugen eines neuen StreamWriters
     *
     * @param outputFilename
     * @param width Auflösung x
     * @param height Auflösung y
     */
    public VideoStream(String outputFilename, int width, int height) {
// this.outputFilename = outputFilename;

        // make a IMediaWriter to write the file.
        writer = ToolFactory.makeWriter(outputFilename);
        // We tell it we're going to add one video stream, with id 0,
        // at position 0, and that it will have a fixed frame rate of FRAME_RATE.
        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4, width, height);
    }

    /**
     * Speichert einen Frame im Stream
     *
     * @param image
     */
    public void newFrame(BufferedImage image) {
        if (startTime == 0) {
            startTime = System.nanoTime();
        }
        // convert to the right image type
        BufferedImage bgrScreen = convertToType(image, BufferedImage.TYPE_3BYTE_BGR);
        System.out.println(System.nanoTime() - startTime);
        // encode the image to stream #0
        writer.encodeVideo(0, bgrScreen, System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
    }

    private static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {

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

    /**
     * schließt den Videostream
     */
    public void close() {
        // tell the writer to close and write the trailer if needed
        writer.close();
    }
}
