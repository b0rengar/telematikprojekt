package com.tds.inertialImpl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

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

        Dictionary<String, Object> params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, IInertialMeasurementService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides access to inertial measurement unit.");
        context.registerService(IInertialMeasurementService.class.getName(), service, params);

    }

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        Activator.context = null;
    }

}
