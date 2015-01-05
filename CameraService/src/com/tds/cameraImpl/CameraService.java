package com.tds.cameraImpl;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.osgi.framework.BundleContext;

import com.github.sarxos.webcam.Webcam;
import com.tds.camera.ICameraService;
import com.tds.camera.IPicture;
import com.tds.camera.Picture;

public class CameraService implements ICameraService {

    private List<Webcam> cams;

    private HashMap<Integer, Timer> timers;

    private BundleContext context;

    public CameraService(BundleContext context) {
        this.context = context;
        timers = new HashMap<Integer, Timer>();

        cams = Webcam.getWebcams();

        int i = 0;
//        for (Webcam c : cams) {
//        	System.out.println(c.getName());
//        	System.out.println(i);
//            timers.put(i++, new Timer());
//        }
    }

    @Override
    public void destroyService() {
        if (cams == null) {
            return;
        }

        for (Webcam cam : cams) {
            if (cam.isOpen()) {
                cam.close();
            }
            timers.get(cam).cancel();
            timers.get(cam).purge();
        }
    }

    @Override
    public int getCameraCount() {
        return cams.size();
    }

    @Override
    public String getCamName(int camID) {
        if (camID < 0 && camID >= getCameraCount()) {
            return "";
        }
        return cams.get(camID).getName();

    }

    @Override
    public BufferedImage getLocalCamImage(int camID, int imageType) {
        if (camID < 0 && camID >= getCameraCount()) {
            return null;
        }

        Webcam cam = cams.get(camID);
        if (!cam.isOpen()) {
            cam.setViewSize(new Dimension(640, 480));
            cam.open();
        }

        BufferedImage img = cam.getImage();

        return convertToType(img, imageType);

    }

    @Override
    public IPicture getRemoteCamImage(int camID) {
        if (camID < 0 && camID >= getCameraCount()) {
            return null;
        }

        Webcam cam = cams.get(camID);
        if (!cam.isOpen()) {
            cam.setViewSize(new Dimension(640, 480));
            cam.open();
        }

        BufferedImage img = cam.getImage();
        if (img == null) {
            return null;
        }
        return new Picture(img);
    }

    private static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {

        BufferedImage image;

        // if the source image is already the target type, return the source image
        if (sourceImage.getType() == targetType) {
            image = sourceImage;
        }
        // otherwise create a new image of the target type and draw the new image
        else {
            image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
            image.getGraphics().drawImage(sourceImage, 0, 0, null);
        }

        return image;

    }

    @Override
    public void startCameraEvents(int camID, String topic, int fps) {

        TimerTask tt = new com.tds.cameraImpl.CamPublisher(this.context, this, camID, topic, fps);
        System.out.println("start timer");
        Timer t = timers.get(camID);

        t = new Timer();
        t.scheduleAtFixedRate(tt, 1000 / fps, 1000 / fps);
    }

}
