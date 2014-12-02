package com.tds.eventImpl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import com.tds.event.IEventDetectorService;

public class Activator implements BundleActivator {

    private static BundleContext context;

    private IEventDetectorService service;

    static BundleContext getContext() {
        return context;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext bundleContext) throws Exception {
        Activator.context = bundleContext;
        service = new EventDetectorService();

        Dictionary<String, Object> params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, IEventDetectorService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides access to inertial measurement unit.");
        context.registerService(IEventDetectorService.class.getName(), service, params);

    }

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext bundleContext) throws Exception {
        Activator.context = null;
    }

}
