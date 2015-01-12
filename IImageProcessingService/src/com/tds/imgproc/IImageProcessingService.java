/**
 *
 */
package com.tds.imgproc;

import java.awt.image.BufferedImage;

import org.osgi.service.event.EventAdmin;

import com.tds.camera.IPicture;

/**
 * <b>IImageProcessingService <br />
 * com.tds.imgproc <br />
 * IImageProcessingService <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 20:33:30
 *
 */
public interface IImageProcessingService {
    public static String EVENT_IMG_DATA_TIMESTAMP = "img/Data/ts";
    public static String EVENT_IMG_DATA_IMAGE = "img/Data/img";
    public static String EVENT_IMG_DATA_TYPE = "img/Data/type";
    public static String EVENT_IMG_TOPIC = "ImageProcessing/evts/GPS";

    public enum TYPE {
        SLEEP, OBJECT, HUMAN
    }

    void bindEventAdmin(EventAdmin eventAdmin);

    void unbindEventAdmin();

    public boolean detectSleep(IPicture p);

    public boolean detectHuman(IPicture p);

// public BufferedImage getbufferedImage(Mat frame);

    public void detectObject(BufferedImage input, BufferedImage compare);

    public void startProcessing();

    public void stopProcessing();
}
