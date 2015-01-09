/**
 * 
 */
package com.tds.imgprocImpl;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.UnsupportedDataTypeException;
import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

/**
 * <b>ImageProcessingService <br />
 * com.tds.imgprocImpl <br />
 * Util <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 05.01.2015 15:05:07
 *
 */
public class Util {

	/**
	 * Converts an OpenCV {@link Mat} into a {@link BufferedImage}
	 * @param mat - the input {@link Mat} to be converted
	 * @return the {@link BufferedImage} containing the pixels of the input {@link Mat}
	 * @throws UnsupportedDataTypeException
	 */
	public static BufferedImage convertToBufferedImage(Mat mat) 
			throws UnsupportedDataTypeException 
	{
		MatOfByte bytemat = new MatOfByte();

		Highgui.imencode(".jpg", mat, bytemat);

		byte[] bytes = bytemat.toArray();

		InputStream in = new ByteArrayInputStream(bytes);

		try {
			return ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
