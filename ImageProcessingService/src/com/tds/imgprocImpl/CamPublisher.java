/**
 * 
 */
package com.tds.imgprocImpl;

import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.TimerTask;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.tds.camera.ICameraService;

/**
 * @author Admin
 *
 */
public class CamPublisher extends TimerTask {

	private ICameraService service;
    private int camID;
    private String topic;
    private int interval;

    private BundleContext context;

    public CamPublisher(BundleContext context, ICameraService service, int camID, String topic, int fps) {
        this.context = context;
        this.service = service;
        this.camID = camID;
        this.topic = topic;
        this.interval = 1000 / fps;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        ServiceReference<EventAdmin> ref = (ServiceReference<EventAdmin>) context.getServiceReference(EventAdmin.class.getName());
        if (ref != null) {
            EventAdmin eventAdmin = context.getService(ref);

            Dictionary<String, Object> properties = new Hashtable<>();
            properties.put("camID", camID);
            properties.put("interval", interval);
            properties.put("image", service.getRemoteCamImage(camID).getJPEG());
            properties.put("timestamp", new Date().getTime());
            Event event = new Event(topic, properties);
            eventAdmin.postEvent(event);
        }

    }

}
