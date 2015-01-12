package com.tds.serial;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventAdmin;

import ch.ethz.iks.r_osgi.RemoteOSGiService;
import ch.ethz.iks.r_osgi.channels.NetworkChannelFactory;

import com.tds.gps.IGPSService;
import com.tds.inertial.IInertialMeasurementService;
import com.tds.obd.IOBDService;
import com.tds.serial.network.SerialNetworkFactory;

/**
 * Class to start and stop the GPS Service. This class is responsible for any setup that needs to be done before the service can operate as well as any clean up before the service is shut down.
 *
 * This service provides access to the GPS coordinates of the On-Board Unit.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 21:33:18
 *
 */
public class Activator implements BundleActivator {

    private static BundleContext context;

    private IGPSService gps;
    private IInertialMeasurementService inertial;
    private IOBDService obd;

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
// initGPS();
        initInertial();
        initOBD();

        initXBee("/dev/xbee0");
        try {
            initGPS();
        } catch (Exception e) {
            System.out.println("Could not load GPSService");
        }

        try {
            initInertial();
        } catch (Exception e) {
            System.out.println("Could not load InertialService");
        }

        try {
            initOBD();
        } catch (Exception e) {
            System.out.println("Could not load OBDService");
        }

    }

    private void initXBee(String port) {
        Dictionary properties = new Hashtable();
        properties.put(NetworkChannelFactory.PROTOCOL_PROPERTY, SerialNetworkFactory.PROTOCOL);
        context.registerService(NetworkChannelFactory.class.getName(), new SerialNetworkFactory("/dev/xbee0"), properties);
    }

    private void initGPS() {
        gps = new GPSService();
        gps.openSP();
        Dictionary<String, Object> params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, IGPSService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides access to the gps navigation information.");
        params.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);

        context.registerService(IGPSService.class.getName(), gps, params);
        ServiceReference<EventAdmin> ref = (ServiceReference<EventAdmin>) context.getServiceReference(EventAdmin.class.getName());
        if (ref != null) {
            gps.bindEventAdmin(context.getService(ref));
        }
    }

    private void initInertial() {
        inertial = new InertialMeasurementService();
        inertial.openSP();

        Dictionary<String, Object> params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, IInertialMeasurementService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides access to inertial measurement unit.");
        params.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);

        context.registerService(IInertialMeasurementService.class.getName(), inertial, params);

        ServiceReference<EventAdmin> ref = (ServiceReference<EventAdmin>) context.getServiceReference(EventAdmin.class.getName());
        if (ref != null) {
            inertial.bindEventAdmin(context.getService(ref));
        }
    }

    private void initOBD() {
        obd = new OBDService();
        obd.openSP();

        Dictionary<String, Object> params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, IOBDService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides access to the OBD2 interface.");
        params.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
        context.registerService(IOBDService.class.getName(), obd, params);

        ServiceReference<EventAdmin> ref = (ServiceReference<EventAdmin>) context.getServiceReference(EventAdmin.class.getName());
        if (ref != null) {
            obd.bindEventAdmin(context.getService(ref));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        if (gps != null) {
            gps.closeSP();
            gps.unbindEventAdmin();
        }
        if (inertial != null) {
            inertial.closeSP();
            inertial.unbindEventAdmin();
        }
        if (obd != null) {
            obd.closeSP();
            obd.unbindEventAdmin();
        }
        Activator.context = null;
    }

}
