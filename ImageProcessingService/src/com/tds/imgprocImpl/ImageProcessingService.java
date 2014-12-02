/**
 * 
 */
package com.tds.imgprocImpl;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import com.tds.imgproc.IImageProcessingService;

/**
 * <b>ImageProcessingService <br />
 * com.tds.imgproc <br />
 * ImageProcessingService <br />
 * </b>
 * 
 * Description.
 * 
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 21:11:42
 * 
 */
public class ImageProcessingService implements IImageProcessingService {
	private CascadeClassifier left_eye, augen_cascade;
	private long time = 0;
	private boolean noeyes = false;	

	@Override
	public Mat detect(Mat inputframe) {
		System.loadLibrary("opencv_java249");
		augen_cascade = new CascadeClassifier(
				"haarcascade_eye_tree_eyeglasses.xml");
		/** erkennt auch geschlossene Augen */
		left_eye = new CascadeClassifier("haarcascade_lefteye_2splits.xml");

		Mat mRgba = new Mat();
		Mat mGrey = new Mat();
		MatOfRect faces = new MatOfRect();
		inputframe.copyTo(mRgba);
		inputframe.copyTo(mGrey);
		Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(mGrey, mGrey);

		left_eye.detectMultiScale(mGrey, faces);
		int augen = 0;
		int i = 0;
		int eyes_found = faces.toArray().length;
		System.out.println("gefundene Augen:" + faces.toArray().length);
		for (Rect dik : faces.toArray()) {
			if (i == 0) {
				augen = dik.y;
			}
			i = i + 1;

		}

		augen_cascade.detectMultiScale(mGrey, faces);
		int open_eyes = faces.toArray().length;
		System.out.println("offene Augen:" + faces.toArray().length);

		if (noeyes == false && (eyes_found > 0 && open_eyes < 1)) {
			time = System.currentTimeMillis();
			noeyes = true;
		}

		if (eyes_found > 0 && open_eyes > 0) {
			time = System.currentTimeMillis();
			noeyes = false;
		}

		if (noeyes == true && ((System.currentTimeMillis() - time)) > 2000) {
			System.out.println("ALARM");
			System.out.println("ALARM");
			System.out.println("ALARM");

		}

		for (Rect dik : faces.toArray()) {
			if ((dik.y < augen + 10) && dik.y > (augen - 10)) {
				Core.rectangle(mRgba, new Point(dik.x, dik.y), new Point(dik.x
						+ dik.width, dik.y + dik.height), new Scalar(255, 0,
						255));
			}
		}

		return mRgba;
	}

}
