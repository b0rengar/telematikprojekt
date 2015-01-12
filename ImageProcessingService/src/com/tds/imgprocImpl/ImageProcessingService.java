/**
 *
 */
package com.tds.imgprocImpl;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.Hashtable;
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
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.tds.camera.ICameraService;
import com.tds.camera.IPicture;
import com.tds.imgproc.IImageProcessingService;

/**
 * <b>ImageProcessingService <br />
 * com.tds.imgproc <br />
 * ImageProcessingService <br />
 * </b>
 *
 * This class implements IImageProcessingService. It's used to detect certain event, like sleepdetection, from images of the cameras. Because the image analyses is quite complex for the CPU the functions are rather slow (4-10 fps depending on the system).
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 21:11:42
 *
 */
public class ImageProcessingService implements IImageProcessingService {
    private Thread faceDetect;
    private Thread humanDetect;
    private static EventAdmin eventAdmin;
    ICameraService camService;

    private CascadeClassifier left_eye, augen_cascade, upperbody;
    private long time = 0;
    private boolean noeyes = false;

    public ImageProcessingService(ICameraService camService) {
        faceDetect = new FaceThread();
        humanDetect = new HumanThread();
        this.camService = camService;
    }

    /**
     *
     * This function uses 2 haarcascade.xml files to detect if the driver might fall asleep. The first haarcascade.xml is used to detect all eyes in the cameras field of view. The second one is used to detect all open eyes in this area. When there is atleast 1 eye detected and alteast 1 of them are open everything is fine (detectSleep=false). When atleast 1 eye is detected but non is detected as
     * opened (doesnt need to be completly shut) for a certain time (here 2 seconds) the driver is considered to migth be sleepy. It also create the file SleepDetected.jpg when this event happens.
     *
     * */
    @Override
    public boolean detectSleep(IPicture p) {
        BufferedImage image = p.getBufferedImage();

        augen_cascade = new CascadeClassifier("haarcascade_eye_tree_eyeglasses.xml");
        /** erkennt auch geschlossene Augen */
        left_eye = new CascadeClassifier("haarcascade_lefteye_2splits.xml");
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat inputframe = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
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
// System.out.println("gefundene Augen:" + faces.toArray().length);
        for (Rect dik : faces.toArray()) {
            if (i == 0) {
                augen = dik.y;
            }
            i = i + 1;

        }

        augen_cascade.detectMultiScale(mGrey, faces);
        int open_eyes = faces.toArray().length;
// System.out.println("offene Augen:" + faces.toArray().length);

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
// System.out.println("ALARM");
// System.out.println("ALARM");
// System.out.println("ALARM");
            sleeping = true;
            // TODO hier event werfen
            createEvent(p, TYPE.SLEEP);

            String filename = "SleepDetected.jpg";
            Highgui.imwrite(filename, mRgba);

        }

        for (Rect dik : faces.toArray()) {
            if ((dik.y < augen + 10) && dik.y > (augen - 10)) {
                Core.rectangle(mRgba, new Point(dik.x, dik.y), new Point(dik.x + dik.width, dik.y + dik.height), new Scalar(255, 0, 255));
            }
        }

        // return mRgba;
// String filename = "SleepDetected.jpg";
// Highgui.imwrite(filename, mRgba);
        return sleeping;
    }

    /**
     * This functionen uses OpenCV SURF to detect any given object in the cameras field of view.
     *
     *
     * */
    @Override
    public void detectObject(BufferedImage input, BufferedImage compare) {

        byte[] pixels = ((DataBufferByte) compare.getRaster().getDataBuffer()).getData();
        Mat inputframe = new Mat(compare.getHeight(), compare.getWidth(), CvType.CV_8UC3);
        inputframe.put(0, 0, pixels);
        pixels = ((DataBufferByte) input.getRaster().getDataBuffer()).getData();
        Mat compareTo = new Mat(input.getHeight(), input.getWidth(), CvType.CV_8UC3);
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

        DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.SURF); // 2 =
        // SURF;

        Mat descriptor_object = new Mat();
        Mat descriptor_scene = new Mat();

        extractor.compute(img_object, keypoints_object, descriptor_object);
        extractor.compute(img_scene, keypoints_scene, descriptor_scene);

        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED); // 1 =
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

// System.out.println("-- Max dist : " + max_dist);
// System.out.println("-- Min dist : " + min_dist);

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
// System.out.println(gm.size());
        Mat img_matches = new Mat();
        Features2d.drawMatches(img_object, keypoints_object, img_scene, keypoints_scene, gm, img_matches, new Scalar(255, 0, 0), new Scalar(0, 0, 255), new MatOfByte(), 2);

        // return img_matches;
// String filename = "ObjectDetected.jpg";
// Highgui.imwrite(filename, img_matches);
    }

    /**
     * This functionen uses the haarcascade_upperbody.xml to detect human bodies. The haarcascade_fullbody.xml might be better but while testing the fullbody.xml didn't detect any bodies at all. When there is atleast 1 human detected int the cameras field of view this pictures gets saved as HumanDetect.jpg and this human gets marked in the picture with an rectangle.
     *
     * */
    @Override
    public boolean detectHuman(IPicture p) {
        BufferedImage image = p.getBufferedImage();

        /** erkennt oberkoerper */
        upperbody = new CascadeClassifier("haarcascade_upperbody.xml");
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat inputframe = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
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
                Core.rectangle(mRgba, new Point(dik.x, dik.y), new Point(dik.x + dik.width, dik.y + dik.height), new Scalar(255, 0, 255));
            }
            human = true;

            // TODO EVENT
            createEvent(p, TYPE.HUMAN);
            String filename = "HumanDetected.jpg";
            Highgui.imwrite(filename, mRgba);
        } else {
            human = false;
        }

        // return mRgba;
        String filename = "HumanDetected.jpg";
// highgui.imwrite(filename, mrgba);
        return human;
    }

    @Override
    public void bindEventAdmin(EventAdmin eventAdmin) {
        ImageProcessingService.eventAdmin = eventAdmin;
    }

    @Override
    public void unbindEventAdmin() {
        ImageProcessingService.eventAdmin = null;
    }

    public class FaceThread extends Thread {
        boolean run = true;

        FaceThread() {
        }

        @Override
        public void run() {
            while (run) {
                try {
                    IPicture p = camService.getRemoteCamImage(0);
// System.out.println("try to detect sleep");
                    detectSleep(p);

                } catch (Exception e) {
                    System.err.println("failed to get image for faceDetect");
                    // TODO: handle exception
                }
            }

        }
    }

    public class HumanThread extends Thread {
        boolean run = true;

        HumanThread() {
        }

        @Override
        public void run() {
            while (run) {
                try {
                    IPicture p = camService.getRemoteCamImage(1);
// System.out.println("try to detect human");

                    detectHuman(p);
                } catch (Exception e) {
                    System.err.println("failed to get image for humanDetect");
                    // TODO: handle exception
                }
            }
        }

    }

    @Override
    public void startProcessing() {
        faceDetect.start();
        humanDetect.start();
    }

    @Override
    public void stopProcessing() {
        faceDetect.stop();
        // TODO Auto-generated method stub

    }

    private void createEvent(IPicture p, TYPE t) {
        Dictionary<String, Object> eventProps = new Hashtable<String, Object>();
        long ts = Calendar.getInstance().getTimeInMillis();
        eventProps.put(EVENT_IMG_DATA_TIMESTAMP, Long.toString(ts));
        eventProps.put(EVENT_IMG_DATA_TYPE, t);
        eventProps.put(EVENT_IMG_DATA_IMAGE, p.getJPEG());
        Event osgiEvent = new Event(EVENT_IMG_TOPIC, eventProps);

// System.out.println("iwas erkannt " + t);
        // "sendEvent()" synchron "postEvent()" asynchron:
        eventAdmin.sendEvent(osgiEvent);
// System.out.println("new image processing event");

    }

}
