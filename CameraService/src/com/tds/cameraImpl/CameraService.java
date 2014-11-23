package com.tds.cameraImpl;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.List;

import com.github.sarxos.webcam.Webcam;
import com.tds.camera.ICameraService;

public class CameraService implements ICameraService {

    private static List<Webcam> cams;

    public CameraService() {
        cams = Webcam.getWebcams();
    }

    public void destroyService() {
        if (cams == null) {
            return;
        }

        for (Webcam cam : cams) {
            if (cam.isOpen()) {
                cam.close();
            }
        }
    }

    public int getCameraCount() {
        return cams.size();
    }

    public String getCamName(int camID) {
        if (camID <= 0 && camID >= getCameraCount()) {
            return "";
        }
        return cams.get(camID).getName();

    }

    public BufferedImage getCamImage(int camID, int imageType) {
        if (camID <= 0 && camID >= getCameraCount()) {
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
}
