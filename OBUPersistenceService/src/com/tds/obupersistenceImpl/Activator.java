package com.tds.obupersistenceImpl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import com.tds.obupersistence.IOBUPersistenceService;

/**
 * 
 * <b>OBUPersistenceService <br />
 * com.tds.obupersistenceImpl <br />
 * Activator <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 03.01.2015 17:40:53
 *
 */
public class Activator implements BundleActivator {

    private static BundleContext context;
    private IOBUPersistenceService service;

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
        service = new OBUPersistenceService();
        Dictionary<String, Object> params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, IOBUPersistenceService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides access to the persistence layer of the application on the OBU.");
        context.registerService(IOBUPersistenceService.class.getName(), service, params);
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
