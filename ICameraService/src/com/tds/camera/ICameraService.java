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

    public void destroyService();

    public int getCameraCount();

    public String getCamName(int camID);

    public BufferedImage getLocalCamImage(int camID, int imageType);

    public IPicture getRemoteCamImage(int camID);

    public void startCameraEvents(int camID, String topic, int fps);
}
