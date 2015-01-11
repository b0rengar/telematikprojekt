package com.tds.inertialImpl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventAdmin;

import ch.ethz.iks.r_osgi.RemoteOSGiService;

import com.tds.inertial.IInertialMeasurementService;

/**
 * Class to start and stop the Inertial Measurement Service. This class is responsible for any setup that needs to be done before the service can operate as well as any clean up before the service is shut down. *
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 *
 */
public class Activator implements BundleActivator {

    private static BundleContext context;

    private IInertialMeasurementService service;

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
        service = new InertialMeasurementService();
        service.openSP();

        Dictionary<String, Object> params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, IInertialMeasurementService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides access to inertial measurement unit.");
        params.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);

        context.registerService(IInertialMeasurementService.class.getName(), service, params);

        ServiceReference<EventAdmin> ref = (ServiceReference<EventAdmin>) context.getServiceReference(EventAdmin.class.getName());
        if (ref != null) {
            service.bindEventAdmin(context.getService(ref));
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        service.closeSP();
        service.unbindEventAdmin();
        Activator.context = null;
    }

}
