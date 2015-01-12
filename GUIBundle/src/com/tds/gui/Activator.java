package com.tds.gui;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.util.tracker.ServiceTracker;

import com.tds.gui.panels.CamPanel;

/**
 * Class to start and stop the GUI Service. This class is responsible for any setup that needs to be done before the service can operate as well as any clean up before the service is shut down.
 *
 * This service manages the the graphical user interface to display various data collected by the system.
 *
 * @author Christian Bodler
 *
 */
public class Activator implements BundleActivator {

    private BundleContext context;
    private ServiceTracker<Object, Object> tracker;

    MainFrame mf;

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        this.context = context;

        mf = new MainFrame(context);
        Filter filter = context.createFilter("(" + Constants.OBJECTCLASS + "=com.tds*)");
        tracker = new ServiceTracker<>(context, filter, mf);
        tracker.open();
        mf.getFrame().setVisible(true);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        for (CamPanel p : mf.getCamPanels()) {
            p.closeStream();
        }
        this.context = null;
        tracker.close();
        tracker = null;
    }

}
