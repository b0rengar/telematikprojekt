package com.tds.imgprocImpl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import com.tds.imgproc.IImageProcessingService;

/**
 *
 * <b>ImageProcessingService <br />
 * com.tds.imgproc <br />
 * Activator <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 21:13:29
 *
 */
public class Activator implements BundleActivator {

    private static BundleContext context;

    private IImageProcessingService service;

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
        service = new ImageProcessingService();

        Dictionary<String, Object> params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, IImageProcessingService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides image processing capabilities.");
        context.registerService(IImageProcessingService.class.getName(), service, params);

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
