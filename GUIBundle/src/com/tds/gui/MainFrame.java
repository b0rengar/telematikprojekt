package com.tds.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import ch.ethz.iks.r_osgi.RemoteOSGiService;
import ch.ethz.iks.r_osgi.RemoteServiceReference;
import ch.ethz.iks.r_osgi.URI;

import com.tds.camera.ICameraService;
import com.tds.gps.IGPSService;
import com.tds.gui.panels.CamPanel;
import com.tds.gui.panels.CarEditDialog;
import com.tds.gui.panels.CarListDialog;
import com.tds.gui.panels.ClientServerDialog;
import com.tds.gui.panels.EventPanel;
import com.tds.gui.panels.InertialPanel;
import com.tds.gui.panels.MapPanel;
import com.tds.gui.panels.ParameterPanel;
import com.tds.gui.panels.PathsDialog;
import com.tds.gui.panels.UnitsDialog;
import com.tds.inertial.IInertialMeasurementService;
import com.tds.obd.IOBDService;
import com.tds.persistence.IPersistenceService;

public class MainFrame implements ServiceTrackerCustomizer<Object, Object> {

    private static final long serialVersionUID = 1L;
    private BundleContext context;
    private ICameraService cameraService;
    private IPersistenceService persistenceService;
    private IOBDService obdService;
    private IGPSService gpsService;
    private IInertialMeasurementService inertialService;

    private JFrame frmMainWindow;

    JInternalFrame ifFrontKamera;
    JInternalFrame ifDriverKamera;
    JInternalFrame ifBetriebsparameter;
    JInternalFrame ifKarte;
    JInternalFrame ifBeschleunigungssensor;

    JMenuItem mntmFahrzeugHinzufuegen;
    JMenuItem mntmFahrzeuglisteBearbeiten;
    JMenuItem mntmClientserver;
    JMenuItem mntmEinheiten;
    JMenuItem mntmSpeicherpfade;

// /**
// * Launch the application.
// */
// public static void main(String[] args) {
// EventQueue.invokeLater(new Runnable() {
// public void run() {
// try {
// MainFrame window = new MainFrame();
// window.frame.setVisible(true);
// } catch (Exception e) {
// e.printStackTrace();
// }
// }
// });
// }

    /**
     * Create the application.
     */

    public MainFrame(BundleContext context) {
// super("(T)elemetrie (D)aten (S)ystem");
        this.context = context;

        getRemoteOSGiServices();

        initialize();

        addPanelsWithEventHandler();

// public MainFrame() {
//
    }

    private void addPanelsWithEventHandler() {
        // add service for road camera
        JPanel panel = new CamPanel(cameraService, 0);
        Dictionary<String, String[]> topics = new Hashtable<>();
        topics.put(EventConstants.EVENT_TOPIC, new String[] { ICameraService.OBU_EVENT_CAMERA_ROAD });
        context.registerService(EventHandler.class.getName(), panel, topics);
        ifFrontKamera.getContentPane().add(panel, BorderLayout.CENTER);
        ifFrontKamera.setVisible(false);
        ifFrontKamera.setVisible(true);

        // add service for driver camera
        JPanel driverPanel = new CamPanel(cameraService, 0);
        topics = new Hashtable<>();
        topics.put(EventConstants.EVENT_TOPIC, new String[] { ICameraService.EVENT_OBU_CAMERA_DRIVER });
        context.registerService(EventHandler.class.getName(), driverPanel, topics);
        ifDriverKamera.getContentPane().add(driverPanel, BorderLayout.CENTER);
        ifDriverKamera.setVisible(false);
        ifDriverKamera.setVisible(true);

        // add service for OBD data
        JPanel parameterPanel = new ParameterPanel(obdService);
        topics = new Hashtable<>();
        topics.put(EventConstants.EVENT_TOPIC, new String[] { IOBDService.EVENT_OBD_TOPIC });
        context.registerService(EventHandler.class.getName(), parameterPanel, topics);
        ifBetriebsparameter.getContentPane().add(parameterPanel);
        ifBetriebsparameter.setVisible(false);
        ifBetriebsparameter.setVisible(true);

        // add service for GPS data
        JPanel mapPanel = new MapPanel(gpsService);
        topics = new Hashtable<>();
        topics.put(EventConstants.EVENT_TOPIC, new String[] { IGPSService.EVENT_GPS_TOPIC });
        context.registerService(EventHandler.class.getName(), mapPanel, topics);
        ifKarte.getContentPane().add(mapPanel);
        ifKarte.setVisible(false);
        ifKarte.setVisible(true);

        // add service for Inertial Measurement data
        JPanel inertialPanel = new InertialPanel(inertialService);
        // TODO reset topic String by from from IInertialMeasurementService interface
        topics = new Hashtable<>();
        topics.put(EventConstants.EVENT_TOPIC, new String[] { IInertialMeasurementService.EVENT_ACC_TOPIC });
        context.registerService(EventHandler.class.getName(), inertialPanel, topics);
        ifBeschleunigungssensor.getContentPane().add(inertialPanel);
        ifBeschleunigungssensor.setVisible(false);
        ifBeschleunigungssensor.setVisible(true);
    }

    private void getRemoteOSGiServices() {
        System.out.println("Connecting to OBU at MainFrame");

        try {

            final ServiceReference sref = context.getServiceReference(RemoteOSGiService.class.getName());

            if (sref == null) {
                throw new BundleException("No R-OSGi found");
            }

            RemoteOSGiService remote = (RemoteOSGiService) context.getService(sref);

            // connect
// RemoteServiceReference[] rsr = remote.connect(new URI("r-osgi://192.168.2.117:9278"));
// RemoteServiceReference[] rsr = remote.connect(new URI("r-osgi://tds.changeip.org:9278"));
            RemoteServiceReference[] rsr = remote.connect(new URI("r-osgi://localhost:55555"));
// RemoteServiceReference[] rsr = remote.connect(new URI("r-osgi://localhost:9278"));

// for (int i = 0; i < rsr.length; i++) {
// System.out.println("RSR Connect" + remote.getRemoteService(rsr[i]).getClass().toString());
// if (remote.getRemoteService(rsr[i]) instanceof IOBDService) {
// obdService = (IOBDService) remote.getRemoteService(rsr[i]);
// }
// if (remote.getRemoteService(rsr[i]) instanceof IGPSService) {
// System.out.println("CONNECTING TO GPS SERVICE !!!!!!!!!!!!!!!!!!!!!!!");
// gpsService = (IGPSService) remote.getRemoteService(rsr[i]);
// }
// }
// System.out.println("Connected to OBU successfully");
        } catch (Exception e) {
            System.out.println("Connecting not successful");
            e.printStackTrace();
        }

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmMainWindow = new JFrame();
        frmMainWindow.setTitle("TDS - Telemetrie Daten System");
// frmMainWindow.setBounds(100, 100, 1143, 651);
        frmMainWindow.setBounds(100, 100, 1400, 651);
        frmMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainFrameActionListener actionListener = new MainFrameActionListener();

        JMenuBar menuBar = new JMenuBar();
        frmMainWindow.setJMenuBar(menuBar);

        JMenu mnDatei = new JMenu("Datei");
        menuBar.add(mnDatei);

        JMenuItem mntmOeffnen = new JMenuItem("\u00D6ffnen");
        mnDatei.add(mntmOeffnen);

        JMenuItem mntmSpeichern = new JMenuItem("Speichern");
        mnDatei.add(mntmSpeichern);

        JMenuItem mntmSpeichernUnter = new JMenuItem("Speichern unter");
        mnDatei.add(mntmSpeichernUnter);

        JMenuItem mntmBeenden = new JMenuItem("Beenden");
        mnDatei.add(mntmBeenden);

        JMenu mnBearbeiten = new JMenu("Bearbeiten");
        menuBar.add(mnBearbeiten);

        mntmFahrzeugHinzufuegen = new JMenuItem("Fahrzeug hinzuf\u00FCgen");
        mntmFahrzeugHinzufuegen.addActionListener(actionListener);
        mnBearbeiten.add(mntmFahrzeugHinzufuegen);

        mntmFahrzeuglisteBearbeiten = new JMenuItem("Fahrzeugliste bearbeiten");
        mntmFahrzeuglisteBearbeiten.addActionListener(actionListener);
        mnBearbeiten.add(mntmFahrzeuglisteBearbeiten);

        JMenu mnAnsicht = new JMenu("Ansicht");
        menuBar.add(mnAnsicht);

        JMenuItem mntmBetriebsparameter = new JMenuItem("Betriebsparameter");
        mnAnsicht.add(mntmBetriebsparameter);

        JMenuItem mntmFrontkamera = new JMenuItem("Front-Kamera");
        mnAnsicht.add(mntmFrontkamera);

        JMenuItem mntmFahrerkamera = new JMenuItem("Fahrer-Kamera");
        mnAnsicht.add(mntmFahrerkamera);

        JMenuItem mntmMeldungen = new JMenuItem("Meldungen");
        mnAnsicht.add(mntmMeldungen);

        JMenuItem mntmKarte = new JMenuItem("Karte");
        mnAnsicht.add(mntmKarte);

        JMenuItem mntmPosition = new JMenuItem("Position");
        mnAnsicht.add(mntmPosition);

        JMenu mnEinstellungen = new JMenu("Einstellungen");
        menuBar.add(mnEinstellungen);

        mntmClientserver = new JMenuItem("Client-Server");
        mntmClientserver.addActionListener(actionListener);
        mnEinstellungen.add(mntmClientserver);

        mntmEinheiten = new JMenuItem("Einheiten");
        mntmEinheiten.addActionListener(actionListener);
        mnEinstellungen.add(mntmEinheiten);

        mntmSpeicherpfade = new JMenuItem("Speicherpfade");
        mntmSpeicherpfade.addActionListener(actionListener);
        mnEinstellungen.add(mntmSpeicherpfade);

        JMenu mnHilfe = new JMenu("Hilfe");
        menuBar.add(mnHilfe);

        JMenu mnUeber = new JMenu("\u00DCber");
        menuBar.add(mnUeber);
        frmMainWindow.getContentPane().setLayout(null);

        ifFrontKamera = new JInternalFrame("Front-Kamera Ansicht");
        ifFrontKamera.setBounds(876, 0, 250, 315);
        ifFrontKamera.setIconifiable(true);
        ifFrontKamera.setResizable(true);
        ifFrontKamera.setMaximizable(true);
        ifFrontKamera.setClosable(true);
        frmMainWindow.getContentPane().add(ifFrontKamera);

        ifDriverKamera = new JInternalFrame("Fahrer Ansicht");
        ifDriverKamera.setBounds(1138, 0, 250, 315);
        ifDriverKamera.setIconifiable(true);
        ifDriverKamera.setResizable(true);
        ifDriverKamera.setMaximizable(true);
        ifDriverKamera.setClosable(true);
        frmMainWindow.getContentPane().add(ifDriverKamera);

        ifBetriebsparameter = new JInternalFrame("Betriebsparameter Ansicht");
        ifBetriebsparameter.setBounds(0, 0, 586, 581);
        ifBetriebsparameter.getContentPane().setLayout(new BorderLayout(0, 0));
        frmMainWindow.getContentPane().add(ifBetriebsparameter);

        ifBeschleunigungssensor = new JInternalFrame("Beschleunigungssensor");
        ifBeschleunigungssensor.setBounds(596, 318, 272, 263);
        frmMainWindow.getContentPane().add(ifBeschleunigungssensor);

        JInternalFrame ifMeldungen = new JInternalFrame("Meldungen Ansicht");
        ifMeldungen.setClosable(true);
        ifMeldungen.setIconifiable(true);
        ifMeldungen.setMaximizable(true);
        ifMeldungen.getContentPane().add(new EventPanel());
        ifMeldungen.setBounds(876, 318, 512, 263);
        frmMainWindow.getContentPane().add(ifMeldungen);

        ifKarte = new JInternalFrame("Karte Ansicht");
        ifKarte.setBounds(596, 0, 272, 315);
        frmMainWindow.getContentPane().add(ifKarte);
        ifKarte.setVisible(true);
        ifMeldungen.setVisible(true);
        ifBetriebsparameter.setVisible(true);
        ifFrontKamera.setVisible(true);
        ifBeschleunigungssensor.setVisible(true);
    }

    public JFrame getFrame() {
        return frmMainWindow;
    }

    public void setFrame(JFrame frame) {
        this.frmMainWindow = frame;
    }

    private class MainFrameActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (mntmFahrzeugHinzufuegen.equals(e.getSource())) {
                CarEditDialog dialog = new CarEditDialog();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }

            if (mntmFahrzeuglisteBearbeiten.equals(e.getSource())) {
                CarListDialog dialog = new CarListDialog();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }

            if (mntmClientserver.equals(e.getSource())) {
                ClientServerDialog dialog = new ClientServerDialog();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }

            if (mntmEinheiten.equals(e.getSource())) {
                UnitsDialog dialog = new UnitsDialog();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }

            if (mntmSpeicherpfade.equals(e.getSource())) {
                PathsDialog dialog = new PathsDialog();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }

        }

    }

    @Override
    public Object addingService(ServiceReference<Object> ref) {

        Object s = this.context.getService(ref);
// if (s instanceof ICameraService) {
// System.out.println("Adding Service CameraService to MainFrame");
// cameraService = (ICameraService) this.context.getService(ref);
//
// JPanel panel = new CamPanel(cameraService, 0);
//
// Dictionary<String, String[]> topics = new Hashtable<>();
// topics.put(EventConstants.EVENT_TOPIC, new String[] { "obu/camera/driver" });
// context.registerService(EventHandler.class.getName(), panel, topics);
//
// ifFrontKamera.getContentPane().add(panel, BorderLayout.CENTER);
// ifFrontKamera.setVisible(false);
// ifFrontKamera.setVisible(true);
// return cameraService;
// }
        if (s instanceof IPersistenceService) {
            System.out.println("Adding Service PersistenceService to MainFrame");
            persistenceService = (IPersistenceService) this.context.getService(ref);
// persistenceService.setEvent(42, "test.file", 123423423, "0,0");
// ifBetriebsparameter.getContentPane().add(new ParameterPanel(persistenceService, obdService));
// ifBetriebsparameter.setVisible(false);
// ifBetriebsparameter.setVisible(true);
        }
        if (s instanceof IGPSService) {
            System.out.println("Adding Service GPSService to MainFrame");
            gpsService = (IGPSService) this.context.getService(ref);

            JPanel mapPanel = new MapPanel(gpsService);

            Dictionary<String, String[]> topics = new Hashtable<>();
            topics.put(EventConstants.EVENT_TOPIC, new String[] { IGPSService.EVENT_GPS_TOPIC });
            context.registerService(EventHandler.class.getName(), mapPanel, topics);

            ifKarte.getContentPane().add(mapPanel);
            ifKarte.setVisible(false);
            ifKarte.setVisible(true);

        }
// if (s instanceof IOBDService) {
// System.out.println("Adding Service OBDService_raw to MainFrame");
// obdService = (IOBDService) this.context.getService(ref);
//
// JPanel parameterPanel = new ParameterPanel(obdService);
//
// Dictionary<String, String[]> topics = new Hashtable<>();
// topics.put(EventConstants.EVENT_TOPIC, new String[] { IOBDService.EVENT_OBD_TOPIC });
// context.registerService(EventHandler.class.getName(), parameterPanel, topics);
//
// ifBetriebsparameter.getContentPane().add(parameterPanel);
// ifBetriebsparameter.setVisible(false);
// ifBetriebsparameter.setVisible(true);
// }

        return s;
    }

    @Override
    public void modifiedService(ServiceReference<Object> ref, Object service) {
        System.out.println("service mod");
    }

    @Override
    public void removedService(ServiceReference<Object> ref, Object service) {
        if (service instanceof ICameraService) {
            // TODO service or context.getService(ref) ??
            cameraService = null;
        }
        if (service instanceof IPersistenceService) {
            persistenceService = null;
        }

    }
}
