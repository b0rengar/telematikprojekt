package com.tds.obupersistenceImpl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import com.tds.obupersistence.IOBUPersistenceService;

/**
 *
 * Class to start and stop the OBD Persistence Service. This class is responsible for any setup that needs to be done before the service can operate as well as any clean up before the service is shut down. *
 *
 * This service provides access to the persistence layer of the application on the On-Board Unit.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 03.01.2015 17:40:53
 *
 */
public class Activator implements BundleActivator {

    private static BundleContext context;
    private OBUPersistenceService service;

// private OBUPersistenceService service;
// private ServiceTracker<Object, Object> serviceTracker;

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
        service = new OBUPersistenceService(bundleContext);

        System.out.println("ACTIVATOR: " + bundleContext.getBundle().getSymbolicName());

        Dictionary<String, Object> params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, IOBUPersistenceService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides access to the persistence layer of the application on the OBU.");
        context.registerService(IOBUPersistenceService.class.getName(), service, params);

// Filter filter = context.createFilter("(" + Constants.OBJECTCLASS + "=com.tds*)");
// serviceTracker = new ServiceTracker<>(context, filter, service);
// serviceTracker = new ServiceTracker<Object, Object>(context, Object.class.getName(), service);
// serviceTracker.open();
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
