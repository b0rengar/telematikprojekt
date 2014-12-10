<<<<<<< HEAD
/**
 * 
 */
package com.tds.imgproc;

import java.awt.image.BufferedImage;
import java.util.Enumeration;

import org.opencv.core.Mat;

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

	 public void detectSleep(BufferedImage inputframe);
	 public BufferedImage getbufferedImgae(Mat frame);
	 public Enumeration getType();
	 public void detectObject(BufferedImage input, BufferedImage compare);
	
}
=======
/**
 *
 */
package com.tds.imgproc;

import org.opencv.core.Mat;

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

    public Mat detect(Mat inputframe);

}
>>>>>>> 90ddb0f23332b23e27af5a98253a141fff894fd8
