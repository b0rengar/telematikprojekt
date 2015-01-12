/**
 *
 */
package com.tds.obupersistenceImpl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import com.tds.gps.IGPSService;
import com.tds.inertial.IInertialMeasurementService;
import com.tds.obd.IOBDService;
import com.tds.obupersistence.IOBUPersistenceService;

/**
 * Concrete class implementing {@link IOBUPersistenceService} to provide access to the persistence layer of the On-Board Unit.
 *
 * @author Phillip Kopprasch
 *
 */
public class OBUPersistenceService implements IOBUPersistenceService {
    private BundleContext context;
    private Writer writer;

    OBUPersistenceService(BundleContext context) {
        this.context = context;
        writer = new Writer();
        addingTopics();
    }

    public void addingTopics() {
        Dictionary<String, String[]> topics = new Hashtable<>();
        if (writer == null) {
            writer = new Writer();
        }
        topics.put(EventConstants.EVENT_TOPIC, new String[] { IGPSService.EVENT_GPS_TOPIC });
        System.out.println("OBUPersistenceService Eventhandler for: " + IGPSService.EVENT_GPS_TOPIC + "registered");
        topics.put(EventConstants.EVENT_TOPIC, new String[] { IOBDService.EVENT_OBD_TOPIC });
        System.out.println("OBUPersistenceService Eventhandler for: " + IOBDService.EVENT_OBD_TOPIC + "registered");
        topics.put(EventConstants.EVENT_TOPIC, new String[] { IInertialMeasurementService.EVENT_ACC_TOPIC });
        System.out.println("OBUPersistenceService Eventhandler for: " + IInertialMeasurementService.EVENT_ACC_TOPIC + "registered");

        context.registerService(EventHandler.class.getName(), writer, topics);
    }

}
