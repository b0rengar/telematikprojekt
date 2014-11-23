package com.tds.cameraImpl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import com.tds.camera.ICameraService;

/**
 * 
 * <b>CameraService <br />
 * com.tds.camera <br />
 * Activator <br />
 * </b>
 * 
 * Description.
 * 
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 21:13:36
 * 
 */
public class Activator implements BundleActivator {

    private static BundleContext context;

    private ICameraService service;

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
        service = new CameraService();

        Dictionary<String, Object> params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, ICameraService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides access to the camera interfaces.");
        context.registerService(ICameraService.class.getName(), service, params);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        Activator.context = null;
        service.destroyService();
        service = null;
    }
}
