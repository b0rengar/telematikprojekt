package com.tds.gui;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.tds.camera.ICameraService;

public class Activator implements BundleActivator {

    private BundleContext context;
    private TDSFrame frame;
    private ServiceTracker<Object, Object> cameraTracker;

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        this.context = context;

        MainFrame mf = new MainFrame(context);
        cameraTracker = new ServiceTracker<>(context, ICameraService.class.getName(), mf);
        cameraTracker.open();
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
        cameraTracker.close();
        cameraTracker = null;
// frame.destroyGUI();
// frame = null;
    }

}
