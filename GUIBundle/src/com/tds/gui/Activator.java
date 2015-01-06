package com.tds.gui;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

    private BundleContext context;
    private TDSFrame frame;
    private ServiceTracker<Object, Object> tracker;

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        this.context = context;

        MainFrame mf = new MainFrame(context);
        Filter filter = context.createFilter("(" + Constants.OBJECTCLASS + "=com.tds*)");
        tracker = new ServiceTracker<>(context, filter, mf);
        tracker.open();
        mf.getFrame().setVisible(true);

// frame = new TDSFrame(context);
// cameraTracker = new ServiceTracker<>(context, ICameraService.class.getName(), frame);
// cameraTracker.open();
// frame.setVisible(true);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        this.context = null;
        tracker.close();
        tracker = null;
// frame.destroyGUI();
// frame = null;
    }

}
