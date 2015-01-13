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
    // OSGi Event Keys
    public static String EVENT_IMG_DATA_TIMESTAMP = "img/Data/ts";
    public static String EVENT_IMG_DATA_IMAGE = "img/Data/img";
    public static String EVENT_IMG_DATA_TYPE = "img/Data/type";
    public static String EVENT_IMG_TOPIC = "ImageProcessing/evts/GPS";

    // event type
    public enum TYPE {
        SLEEP, OBJECT, HUMAN
    }

    /**
     * bind OSGi EventAdmin
     *
     * @param eventAdmin
     */
    void bindEventAdmin(EventAdmin eventAdmin);

    /**
     * delete OSGi EventAdmin
     */
    void unbindEventAdmin();

    /**
     * This function uses 2 haarcascade.xml files to detect if the driver might fall asleep. The first haarcascade.xml is used to detect all eyes in the cameras field of view. The second one is used to detect all open eyes in this area. When there is at least 1 eye detected and alteast 1 of them are open everything is fine (detectSleep=false). When atleast 1 eye is detected but non is detected as
     * opened (doesn't need to be completely shut) for a certain time (2 seconds) the driver is considered to might be sleepy. It also create the file SleepDetected.jpg when this event happens.
     *
     * @param p
     * @return
     */
    public boolean detectSleep(IPicture p);

    /**
     * This functionen uses the haarcascade_upperbody.xml to detect human bodies. The haarcascade_fullbody.xml might be better but while testing the fullbody.xml didn't detect any bodies at all. When there is at least 1 human detected int the cameras field of view this pictures gets saved as HumanDetect.jpg and this human gets marked in the picture with an rectangle.
     *
     * @param p
     * @return
     */
    public boolean detectHuman(IPicture p);

// public BufferedImage getbufferedImage(Mat frame);

    /**
     * This function uses OpenCV SURF to detect any given object in the cameras field of view.
     *
     * not used
     *
     * @param input
     * @param compare
     */
    public void detectObject(BufferedImage input, BufferedImage compare);

    /**
     * start all processing thread's
     */
    public void startProcessing();

    /**
     * stops all active thread's
     */
    public void stopProcessing();
}
