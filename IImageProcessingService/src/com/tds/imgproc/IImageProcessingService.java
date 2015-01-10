/**
 *
 */
package com.tds.imgproc;

import java.awt.image.BufferedImage;
import java.util.Enumeration;

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

    public boolean detectSleep(BufferedImage inputframe);

    public boolean detectHuman(BufferedImage inputframe);

// public BufferedImage getbufferedImage(Mat frame);

    public Enumeration getType();

    public void detectObject(BufferedImage input, BufferedImage compare);

}
