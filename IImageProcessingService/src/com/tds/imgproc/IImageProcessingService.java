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
