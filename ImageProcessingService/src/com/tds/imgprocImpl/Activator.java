package com.tds.imgprocImpl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.opencv.core.Core;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventAdmin;

import ch.ethz.iks.r_osgi.RemoteOSGiService;

import com.tds.camera.ICameraService;
import com.tds.imgproc.IImageProcessingService;

/**
 *
 * Class to start and stop the Image Processing Service. This class is responsible for any setup that needs to be done before the service can operate as well as any clean up before the service is shut down.
 *
 * This service provides capabilities to process images taken by cameras installed in the system.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 21:13:29
 *
 */
public class Activator implements BundleActivator {

    private static BundleContext context;

    private IImageProcessingService service;

    private ICameraService camService;

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
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Activator.context = bundleContext;
        camService = new CameraService(context, 2);
        service = new ImageProcessingService(camService);
        service.startProcessing();

        Dictionary<String, Object> params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, IImageProcessingService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides image processing capabilities.");
        params.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
        context.registerService(IImageProcessingService.class.getName(), service, params);

        ServiceReference<EventAdmin> ref = (ServiceReference<EventAdmin>) context.getServiceReference(EventAdmin.class.getName());
        if (ref != null) {
            service.bindEventAdmin(context.getService(ref));
        }

        params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, ICameraService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides access to the camera interfaces.");
        params.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
        context.registerService(ICameraService.class.getName(), camService, params);

        camService.startCameraEvents(0, ICameraService.EVENT_OBU_CAMERA_DRIVER, 15);
        // camService.startCameraEvents(1, ICameraService.OBU_EVENT_CAMERA_ROAD, 15);

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
