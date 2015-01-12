/**
 *
 */
package com.tds.camera;

import java.awt.image.BufferedImage;

/**
 *
 * <b>ICameraService <br />
 * com.tds.camera <br />
 * ICameraService <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 20:19:49
 *
 */
public interface ICameraService {

    public static final String OBU_EVENT_CAMERA_ROAD = "obu/camera/road";

    public static final String EVENT_OBU_CAMERA_DRIVER = "obu/camera/driver";

    public void destroyService();

    public BufferedImage getLocalCamImage(int camID);

    public IPicture getRemoteCamImage(int camID);

    public void startCameraEvents(int camID, String topic, int fps, boolean rotate);

    public void stopCameraEvents(int camID);
}
