package com.tds.cameraImpl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import ch.ethz.iks.r_osgi.RemoteOSGiService;

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

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        this.context = context;
        service = new CameraService(context);

        Dictionary<String, Object> params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, ICameraService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides access to the camera interfaces.");
        params.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
        context.registerService(ICameraService.class.getName(), service, params);

        service.startCameraEvents(0, "obu/camera/driver", 15);
// service.startCameraEvents(1, "obu/camera/road", 15);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        this.context = null;
        service.destroyService();
        service = null;
    }
}
