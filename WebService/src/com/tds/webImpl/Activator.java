package com.tds.webImpl;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.http.HttpServlet;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import com.tds.web.IWebService;

public class Activator implements BundleActivator {

    private static BundleContext context;

    private IWebService service;

    private ServletTracker st;

    static BundleContext getContext() {
        return context;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void start(BundleContext bundleContext) throws Exception {

        Activator.context = bundleContext;
        service = new WebService();

        Dictionary<String, Object> params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, IWebService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides access to the OBD2 interface.");
        context.registerService(IWebService.class.getName(), service, params);

        st = new ServletTracker(bundleContext);
        st.addServlet("/Web", (HttpServlet) service);
        st.open();

    }

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        Activator.context = null;
        st.removeServlet("/Web");
        st.close();
    }

}
