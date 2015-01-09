/**
 *
 */
package com.tds.obupersistenceImpl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.tds.gps.IGPSService;
import com.tds.obupersistence.IOBUPersistenceService;

/**
 * @author Phillip Kopprasch
 *
 */
public class OBUPersistenceService implements IOBUPersistenceService, ServiceTrackerCustomizer<Object, Object> {
    private BundleContext context;
// private IGPSService gpsService;
// private IOBDService odbService;
    private Writer writer;

    OBUPersistenceService(BundleContext context) {
        this.context = context;
        System.out.println("Construktor: " + this.getClass().getSimpleName());
    }

    @Override
    public Object addingService(ServiceReference reference) {
        Dictionary<String, String[]> topics = new Hashtable<>();

        Object addedService = this.context.getService(reference);

        if (addedService instanceof IGPSService) {
            writer = new Writer();
            topics.put(EventConstants.EVENT_TOPIC, new String[] { IGPSService.EVENT_GPS_TOPIC });
            context.registerService(EventHandler.class.getName(), writer, topics);
            System.out.println("Eventhandler for: " + IGPSService.EVENT_GPS_TOPIC + "registered");
        }
        return addedService;
    }

    @Override
    public void modifiedService(ServiceReference reference, Object service) {
        // TODO
    }

    @Override
    public void removedService(ServiceReference reference, Object service) {
        if (reference instanceof IGPSService) {
            writer.closeWriter();
            writer = null;

// gpsService = null;
        }
    }
}
