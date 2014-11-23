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

    public BufferedImage getCamImage(int camID, int imageType);
}
