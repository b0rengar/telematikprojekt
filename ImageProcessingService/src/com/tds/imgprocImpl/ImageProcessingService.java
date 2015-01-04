/**
 *
 */
package com.tds.imgprocImpl;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;
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
	private CascadeClassifier left_eye, augen_cascade, upperbody;
	private long time = 0;
	private boolean noeyes = false;

	@Override
	public boolean detectSleep(BufferedImage image) {
		System.loadLibrary("opencv_java249");
		augen_cascade = new CascadeClassifier(
				"haarcascade_eye_tree_eyeglasses.xml");
		/** erkennt auch geschlossene Augen */
		left_eye = new CascadeClassifier("haarcascade_lefteye_2splits.xml");
		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer())
				.getData();
		Mat inputframe = new Mat(image.getHeight(), image.getWidth(),
				CvType.CV_8UC3);
		inputframe.put(0, 0, pixels);
		Mat mRgba = new Mat();
		Mat mGrey = new Mat();
		MatOfRect faces = new MatOfRect();
		inputframe.copyTo(mRgba);
		inputframe.copyTo(mGrey);
		Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(mGrey, mGrey);
		boolean sleeping = false;

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
			sleeping = false;
		}

		if (noeyes == true && ((System.currentTimeMillis() - time)) > 2000) {
			System.out.println("ALARM");
			System.out.println("ALARM");
			System.out.println("ALARM");
			sleeping = true;

		}

		for (Rect dik : faces.toArray()) {
			if ((dik.y < augen + 10) && dik.y > (augen - 10)) {
				Core.rectangle(mRgba, new Point(dik.x, dik.y), new Point(dik.x
						+ dik.width, dik.y + dik.height), new Scalar(255, 0,
						255));
			}
		}

		// return mRgba;
		String filename = "SleepDetected.jpg";
		Highgui.imwrite(filename, mRgba);
		return sleeping;
	}

	// TODO
	// bekomme n bufferedImage umwandeln zu Mat, rueckgabe als jpeg
	@Override
	public void detectObject(BufferedImage input, BufferedImage compare) {

		byte[] pixels = ((DataBufferByte) compare.getRaster().getDataBuffer())
				.getData();
		Mat inputframe = new Mat(compare.getHeight(), compare.getWidth(),
				CvType.CV_8UC3);
		inputframe.put(0, 0, pixels);
		pixels = ((DataBufferByte) input.getRaster().getDataBuffer()).getData();
		Mat compareTo = new Mat(input.getHeight(), input.getWidth(),
				CvType.CV_8UC3);
		compareTo.put(0, 0, pixels);
		Mat img_object = compareTo;
		Imgproc.cvtColor(img_object, img_object, Imgproc.COLOR_RGB2GRAY);
		Mat img_scene = inputframe;
		Imgproc.cvtColor(img_scene, img_scene, Imgproc.COLOR_RGB2GRAY);

		FeatureDetector detector = FeatureDetector.create(FeatureDetector.SURF); // 4
																					// =
																					// SURF

		MatOfKeyPoint keypoints_object = new MatOfKeyPoint();
		MatOfKeyPoint keypoints_scene = new MatOfKeyPoint();

		detector.detect(img_object, keypoints_object);
		detector.detect(img_scene, keypoints_scene);

		DescriptorExtractor extractor = DescriptorExtractor
				.create(DescriptorExtractor.SURF); // 2 =
		// SURF;

		Mat descriptor_object = new Mat();
		Mat descriptor_scene = new Mat();

		extractor.compute(img_object, keypoints_object, descriptor_object);
		extractor.compute(img_scene, keypoints_scene, descriptor_scene);

		DescriptorMatcher matcher = DescriptorMatcher
				.create(DescriptorMatcher.FLANNBASED); // 1 =
		// FLANNBASED
		MatOfDMatch matches = new MatOfDMatch();

		matcher.match(descriptor_object, descriptor_scene, matches);
		List<DMatch> matchesList = matches.toList();

		Double max_dist = 0.0;
		Double min_dist = 100.0;

		for (int i = 0; i < matchesList.size(); i++) {
			Double dist = (double) matchesList.get(i).distance;
			if (dist < min_dist) {
				min_dist = dist;
			}
			if (dist > max_dist) {
				max_dist = dist;
			}
		}

		System.out.println("-- Max dist : " + max_dist);
		System.out.println("-- Min dist : " + min_dist);

		LinkedList<DMatch> good_matches = new LinkedList<DMatch>();
		MatOfDMatch gm = new MatOfDMatch();
		for (int i = 0; i < matchesList.size(); i++) {
			// if (matchesList.get(i).distance < (3 * min_dist)) {
			if (min_dist < 0.003) {
				if (matchesList.get(i).distance < (3 * min_dist)) {
					good_matches.addLast(matchesList.get(i));
				}
			}
		}

		gm.fromList(good_matches);
		System.out.println(gm.size());
		Mat img_matches = new Mat();
		Features2d.drawMatches(img_object, keypoints_object, img_scene,
				keypoints_scene, gm, img_matches, new Scalar(255, 0, 0),
				new Scalar(0, 0, 255), new MatOfByte(), 2);

		// return img_matches;
		String filename = "ObjectDetected.jpg";
		Highgui.imwrite(filename, img_matches);
	}

	@Override
	public boolean detectHuman(BufferedImage image) {
		System.loadLibrary("opencv_java249");
		/** erkennt oberkörper */
		upperbody = new CascadeClassifier("haarcascade_upperbody.xml");
		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer())
				.getData();
		Mat inputframe = new Mat(image.getHeight(), image.getWidth(),
				CvType.CV_8UC3);
		inputframe.put(0, 0, pixels);
		Mat mRgba = new Mat();
		Mat mGrey = new Mat();
		MatOfRect bodies = new MatOfRect();
		inputframe.copyTo(mRgba);
		inputframe.copyTo(mGrey);
		Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(mGrey, mGrey);
		boolean human = false;

		upperbody.detectMultiScale(mGrey, bodies);
		int bodies_found = bodies.toArray().length;
		if (bodies_found > 0) {
			for (Rect dik : bodies.toArray()) {
				Core.rectangle(mRgba, new Point(dik.x, dik.y), new Point(dik.x
						+ dik.width, dik.y + dik.height), new Scalar(255, 0,
						255));
			}
			human = true;
		} else {
			human = false;
		}

		// return mRgba;
		String filename = "HumanDetected.jpg";
		Highgui.imwrite(filename, mRgba);
		return human;
	}

	@Override
	public BufferedImage getbufferedImgae(Mat frame) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
