package testservice;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import ch.ethz.iks.r_osgi.RemoteOSGiService;
import ch.ethz.iks.r_osgi.RemoteServiceReference;
import ch.ethz.iks.r_osgi.URI;

import com.tds.obd.IOBDService;

public class Activator implements BundleActivator {

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Hello TDS!!");

        Dictionary<String, String[]> topics = new Hashtable<>();
        topics.put(EventConstants.EVENT_TOPIC, new String[] { "!obu/obd2/speed", "!obu/camera/*" });
        context.registerService(EventHandler.class.getName(), new EventHandler() {

            @Override
            public void handleEvent(Event e) {
                System.out.println("New Event!");
                System.out.println("topic: " + e.getTopic());
                for (String prop : e.getPropertyNames()) {
                    System.out.println(prop + " -> " + e.getProperty(prop));
                }

            }
        }, topics);

        System.out.println("connecting...");
        // get the RemoteOSGiService
        final ServiceReference sref = context.getServiceReference(RemoteOSGiService.class.getName());

        if (sref == null) {
            throw new BundleException("No R-OSGi found");
        }

        RemoteOSGiService remote = (RemoteOSGiService) context.getService(sref);

        // connect
        RemoteServiceReference[] rsr = remote.connect(new URI("r-osgi://tds.changeip.org:9278"));
//      RemoteServiceReference[] rsr = remote.connect(new URI("r-osgi://localhost:9278"));

        for (int i = 0; i < rsr.length; i++) {
            System.out.println(remote.getRemoteService(rsr[i]).getClass().toString());
            if (remote.getRemoteService(rsr[i]) instanceof IOBDService) {
                IOBDService obd = (IOBDService) remote.getRemoteService(rsr[i]);
                System.out.println("R-OSGI-Speed: " + obd.getSpeed());
                System.out.println("R-OSGI-EngineRPM: " + obd.getEngineRPM());
                System.out.println("R-OSGI-EngineTemp: " + obd.getEngineTemperature());
                System.out.println("R-OSGI-Fuel: " + obd.getFuelConsumptionRate());
                System.out.println("R-OSGI-InTemp: " + obd.getCarIndoorTemperature());
                System.out.println("R-OSGI-OutTemp: " + obd.getCarOutdoorTemperature());
            }
        }

        System.out.println("connected!");

    }

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Goodbye TDS!!");
    }

}
