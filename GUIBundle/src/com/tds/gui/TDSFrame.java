/**
 *
 */
package com.tds.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.tds.camera.ICameraService;

/**
 * <b>GUIBundle <br />
 * com.tds.gui - TDSFrame <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 20.11.2014 22:43:16
 *
 */
public class TDSFrame extends JFrame implements ServiceTrackerCustomizer<ICameraService, ICameraService> {

    private static final long serialVersionUID = 1L;

    private BundleContext context;

    private ICameraService cameraService;

    public TDSFrame(BundleContext context) {
        super("(T)elemetrie (D)aten (S)ystem");
        this.context = context;
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        getContentPane().add(panel, BorderLayout.NORTH);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.RED);
        getContentPane().add(panel_1, BorderLayout.WEST);

        JPanel panel_2 = new JPanel();
        panel_2.setBackground(Color.RED);
        getContentPane().add(panel_2, BorderLayout.EAST);

        JPanel panel_3 = new JPanel();
        panel_3.setBackground(Color.RED);
        getContentPane().add(panel_3, BorderLayout.SOUTH);


    }

    @Override
    public ICameraService addingService(ServiceReference<ICameraService> ref) {
        if (ref == null) {
            System.out.println("ref gleich null");
            return null;
        }
        cameraService = this.context.getService(ref);

//        System.out.println("detected Cams: " + cameraService.getCameraCount());
//        for (int i = 0; i < cameraService.getCameraCount(); i++) {
//            System.out.println(cameraService.getCamName(i));
//        }

        JPanel panel = new CamPanel(cameraService, 0);
        
        Dictionary<String, String[]> topics = new Hashtable<>();
        topics.put(EventConstants.EVENT_TOPIC, new String[] { "obu/camera/driver" });
        context.registerService(EventHandler.class.getName(), panel, topics);
        
       
        getContentPane().add(panel, BorderLayout.CENTER);
        return cameraService;
    }

    @Override
    public void modifiedService(ServiceReference<ICameraService> ref, ICameraService service) {
        System.out.println("service mod");
    }

    @Override
    public void removedService(ServiceReference<ICameraService> ref, ICameraService service) {
        cameraService = null;

    }

    public void destroyGUI() {
        this.setVisible(false);
        this.dispose();
    }

}
