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

        initXBee("/dev/xbee0");

        mf = new MainFrame(context);
        Filter filter = context.createFilter("(" + Constants.OBJECTCLASS + "=com.tds*)");
        tracker = new ServiceTracker<>(context, filter, mf);
        tracker.open();
        mf.getFrame().setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initXBee(String port) {
// final Dictionary properties = new Hashtable();
// properties.put(NetworkChannelFactory.PROTOCOL_PROPERTY, SerialNetworkFactory.PROTOCOL);
// context.registerService(NetworkChannelFactory.class.getName(), new SerialNetworkFactory("/dev/xbee0"), properties);
//
// try {
//
// final ServiceReference sref = context.getServiceReference(RemoteOSGiService.class.getName());
//
// if (sref == null) {
// throw new BundleException("No R-OSGi found");
// }
// // System.out.println("Connecting to OBU ...");
// // RemoteOSGiService remote = (RemoteOSGiService) context.getService(sref);
//
// // connect
// // RemoteServiceReference[] rsr = remote.connect(new URI(SerialNetworkFactory.PROTOCOL + "://localhost:10"));
//
// } catch (Exception e) {
// System.out.println("Connecting not successful");
// e.printStackTrace();
// }

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
