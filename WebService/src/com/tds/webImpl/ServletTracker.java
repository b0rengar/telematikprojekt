/**
 *
 */
package com.tds.webImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

/**
 * <b>WebService <br />
 * com.tds.web <br />
 * ServletTracker <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 18.11.2014 17:55:34
 *
 */
public class ServletTracker extends ServiceTracker<HttpService, HttpService> {

    private Map<String, HttpServlet> map = new HashMap<>();

    public void addServlet(String path, HttpServlet servlet) {
        map.put(path, servlet);
    }

    public void removeServlet(String path) {
        map.remove(path);
    }

    public ServletTracker(BundleContext context) {
        super(context, HttpService.class, null);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
     */
    @Override
    public HttpService addingService(ServiceReference<HttpService> reference) {
        System.out.println("adding Service");
        // HTTP service is available, register our servlet...
        HttpService httpService = this.context.getService(reference);
        try {
            for (Entry<String, HttpServlet> entry : map.entrySet()) {
                httpService.registerServlet(entry.getKey(), entry.getValue(), null, null);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return httpService;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.util.tracker.ServiceTracker#removedService(org.osgi.framework.ServiceReference, java.lang.Object)
     */
    @Override
    public void removedService(ServiceReference<HttpService> reference, HttpService service) {
        System.out.println("removed Service");
        HttpService httpService = this.context.getService(reference);
        // HTTP service is no longer available, unregister our servlet...
        try {
            for (Entry<String, HttpServlet> entry : map.entrySet()) {
                httpService.unregister(entry.getKey());
            }
        } catch (IllegalArgumentException exception) {
            // Ignore; servlet registration probably failed earlier on...
        }
    }

}
