/**
 *
 */
package com.tds.gui.panels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.tds.camera.ICameraService;
import com.tds.camera.IPicture;
import com.tds.camera.Picture;
import com.tds.gui.utils.VideoStream;

/**
 * A panel to display a camera feed in a dialog.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 20.11.2014 23:31:21
 *
 */
public class CamPanel extends JPanel implements EventHandler {

    private static final long serialVersionUID = 1L;

    /** Holds a single frame from the camera. */
    private IPicture picture;
    /** A buffer to create a video stream using single frames. */
    private VideoStream stream;

    /** The number of milliseconds since January 1, 1970, 00:00:00 GMT the feed was started at. */
    private long start;
    /** The number of milliseconds since January 1, 1970, 00:00:00 GMT at which the last frame was retrieved from the camera. */
    private long last;
    /** The number of kilobytes saved in the stream. */
    private double size;

    /**
     * Creates a new camera panel using the given camera service as source.
     *
     * @param camService hand over any {@link ICameraService} to do active requests
     * @param camID A number to identify the camera by.
     */
    public CamPanel(ICameraService camService, int camID) {
        String streamPath = System.getProperty("tds.streampath");
        if (streamPath == null) {
            streamPath = "/data/streams/";
        }
        stream = new VideoStream(String.format("%s%sstream_%d_%d.mp4", streamPath, streamPath.endsWith("/") ? "" : "/", camID, Calendar.getInstance().getTimeInMillis()), 480, 360);
    }

    /**
     * Override method to draw picture as JPanel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (picture == null) {
            return;
        }

        BufferedImage img = picture.getBufferedImage();

        if (img == null) {
            return;
        }

        g.drawImage(img, 0, 0, null);
    }

    /**
     * OSGI event handler receives an event with an image as property
     */
    @Override
    public void handleEvent(Event e) {
// System.out.println("new Cam event");

// properties.put("camID", camID);
// properties.put("interval", interval);
// properties.put("image", service.getRemoteCamImage(camID).getJPEG());
// properties.put("timestamp", new Timestamp().toString());

// for (String prop : e.getPropertyNames()) {
// System.out.println(prop + " -> " + e.getProperty(prop));
// }
        // get image from event
        byte[] jpg = (byte[]) e.getProperty("image");
        if (jpg == null) {
            return;
        }

        if (start == 0) {
            start = new Date().getTime();
            last = start;
            System.out.println("time: 0");
            System.out.println("size: " + size);
        } else {
            long current = new Date().getTime();

            long lastTime = current - last;
            long time = current - start;

            double lastSize = jpg.length / (1000.0);
            size += lastSize;

// System.out.println("time: " + time);
// System.out.println("time since last: " + lastTime);
// System.out.println("size: " + size + " kB");
// System.out.println("size of last: " + lastSize + " kB");
// System.out.println("rate: " + size/time + " kBs");
// System.out.println("rate since last : " + lastSize/lastTime + " kBs");

            last = current;
        }
// System.out.println("-------------------------------");
        // create new picture from jpg
        picture = new Picture(jpg);
        // add picture as new frame to stream
        stream.newFrame(picture.getBufferedImage());
        // repaint the component to show the new picture
        this.repaint();

    }

    /** Closes the camera feed. */
    public void closeStream() {
        stream.close();
    }

}
