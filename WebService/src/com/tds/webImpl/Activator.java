package com.tds.webImpl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.util.tracker.ServiceTracker;

import com.tds.persistence.IPersistenceService;
import com.tds.web.IWebService;

public class Activator implements BundleActivator {

    private BundleContext context;

    private WebService service;

    private ServletTracker st;

    private ServiceTracker<IPersistenceService, IPersistenceService> tracker;

// static BundleContext getContext() {
// return context;
// }

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void start(BundleContext bundleContext) throws Exception {

        this.context = bundleContext;
        service = new WebService(context);

        Dictionary<String, Object> params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, IWebService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides access to the Webservices.");
        context.registerService(IWebService.class.getName(), service, params);

        tracker = new ServiceTracker<IPersistenceService, IPersistenceService>(context, IPersistenceService.class.getName(), service);
        tracker.open();

        st = new ServletTracker(bundleContext);
        st.addServlet("/Web", service);
        st.open();

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        this.context = null;
        st.removeServlet("/Web");
        st.close();
        this.context = null;
        tracker.close();
        tracker = null;
    }

}
