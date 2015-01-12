/**
 *
 */
package com.tds.imgprocImpl;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.TimerTask;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.tds.camera.ICameraService;
import com.tds.camera.Picture;

/**
 * @author Admin
 *
 */
public class CamPublisher extends TimerTask {

    private ICameraService service;
    private int camID;
    private String topic;
    private int interval;
    private boolean rotate = false;

    private BundleContext context;

    public CamPublisher(BundleContext context, ICameraService service, int camID, String topic, int fps, boolean rotate) {
        this.context = context;
        this.service = service;
        this.camID = camID;
        this.topic = topic;
        this.interval = 1000 / fps;
        this.rotate = rotate;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        ServiceReference<EventAdmin> ref = (ServiceReference<EventAdmin>) context.getServiceReference(EventAdmin.class.getName());
        if (ref != null) {
            EventAdmin eventAdmin = context.getService(ref);
            BufferedImage buf = Util.scaleDownImage(service.getLocalCamImage(camID), 0.75);
            if (rotate) {
                buf = Util.flipImage(buf);
            }
            Picture p = new Picture(buf);
// Picture p = new Picture(service.getLocalCamImage(camID));
// System.out.println("image size: " + (p.getJPEG().length / 1024) + "kB");
            Dictionary<String, Object> properties = new Hashtable<>();
            properties.put("camID", camID);
            properties.put("interval", interval);
            properties.put("image", p.getJPEG());
            properties.put("timestamp", new Date().getTime());
            Event event = new Event(topic, properties);
            eventAdmin.postEvent(event);
        }

    }
}
