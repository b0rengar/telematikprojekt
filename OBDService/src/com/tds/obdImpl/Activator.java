package com.tds.obdImpl;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Timer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventAdmin;

import ch.ethz.iks.r_osgi.RemoteOSGiService;

import com.tds.obd.IOBDService;

/**
 * Class to start and stop the OBD Service. This class is responsible for any setup that needs to be done before the service can operate as well as any clean up before the service is shut down. *
 *
 * This service provides access to the OBD2 interface.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 *
 */
public class Activator implements BundleActivator {

    private static BundleContext context;

    private IOBDService service;

    private Timer t;

    static BundleContext getContext() {
        return context;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        Activator.context = bundleContext;
        service = new OBDService_raw();

        Dictionary<String, Object> params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, IOBDService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides access to the OBD2 interface.");
        params.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
        context.registerService(IOBDService.class.getName(), service, params);

        ServiceReference<EventAdmin> ref = (ServiceReference<EventAdmin>) context.getServiceReference(EventAdmin.class.getName());
        if (ref != null) {
            service.bindEventAdmin(context.getService(ref));
        }

// TimerTask tt = new TimerTask() {
//
// @Override
// public void run() {
// ServiceReference ref = context.getServiceReference(EventAdmin.class.getName());
// if (ref != null) {
// EventAdmin eventAdmin = (EventAdmin) context.getService(ref);
//
// Dictionary<String, Object> properties = new Hashtable<>();
// properties.put("speed", Math.round(Math.random() * 180));
//
// Event event = new Event("obu/obd2/speed", properties);
// // System.out.println("send event");
// eventAdmin.postEvent(event);
// }
//
// }
// };
// System.out.println("start timer");
// t = new Timer();
// t.scheduleAtFixedRate(tt, 1000, 1000);

    }

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        Activator.context = null;
        t.cancel();
    }

}
