package com.tds.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
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
import com.tds.gui.panels.CamPanel;
import com.tds.gui.panels.CarEditDialog;
import com.tds.gui.panels.CarListDialog;
import com.tds.gui.panels.ClientServerDialog;
import com.tds.gui.panels.EventPanel;
import com.tds.gui.panels.ParameterPanel;
import com.tds.gui.panels.PathsDialog;
import com.tds.gui.panels.UnitsDialog;
import com.tds.obd.IOBDService;
import com.tds.persistence.IPersistenceService;

public class MainFrame implements ServiceTrackerCustomizer<Object, Object> {

    private static final long serialVersionUID = 1L;
    private BundleContext context;
    private ICameraService cameraService;
    private IPersistenceService persistenceService;
    private IOBDService obdService;

    private JFrame frmMainWindow;

    JInternalFrame ifFrontKamera;
    JInternalFrame ifBetriebsparameter;

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

// public MainFrame() {
//
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
// RemoteServiceReference[] rsr = remote.connect(new URI("r-osgi://tds.changeip.org:9278"));
            RemoteServiceReference[] rsr;

            rsr = remote.connect(new URI("r-osgi://localhost:9278"));
            for (int i = 0; i < rsr.length; i++) {
                System.out.println(remote.getRemoteService(rsr[i]).getClass().toString());
                if (remote.getRemoteService(rsr[i]) instanceof IOBDService) {
                    obdService = (IOBDService) remote.getRemoteService(rsr[i]);
                }
            }
            System.out.println("Connected to OBU successfully");
        } catch (Exception e) {
            System.out.println("Connecting not successful");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmMainWindow = new JFrame();
        frmMainWindow.setTitle("TDS - Telemetrie Daten System");
        frmMainWindow.setBounds(100, 100, 1143, 651);
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

        ifBetriebsparameter = new JInternalFrame("Betriebsparameter Ansicht");
        ifBetriebsparameter.setBounds(0, 0, 586, 581);
        ifBetriebsparameter.getContentPane().setLayout(new BorderLayout(0, 0));
        frmMainWindow.getContentPane().add(ifBetriebsparameter);

        JInternalFrame ifMeldungen = new JInternalFrame("Meldungen Ansicht");
        ifMeldungen.setClosable(true);
        ifMeldungen.setIconifiable(true);
        ifMeldungen.setMaximizable(true);
        ifMeldungen.getContentPane().add(new EventPanel());
        ifMeldungen.setBounds(596, 318, 531, 263);
        frmMainWindow.getContentPane().add(ifMeldungen);

        JInternalFrame ifKarte = new JInternalFrame("Karte Ansicht");
        ifKarte.setBounds(596, 0, 270, 315);
        frmMainWindow.getContentPane().add(ifKarte);

        try {
            BufferedImage img = ImageIO.read(new URL("http://maps.googleapis.com/maps/api/staticmap?center=Wildau,Germany&size=512x512&sensor=true_or_false"));
            ifKarte.getContentPane().add(new JLabel(new ImageIcon(img)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ifKarte.setVisible(true);
        ifMeldungen.setVisible(true);
        ifBetriebsparameter.setVisible(true);
        ifFrontKamera.setVisible(true);
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
        if (s instanceof ICameraService) {
            System.out.println("Adding Service CameraService to MainFrame");
            cameraService = (ICameraService) this.context.getService(ref);

            JPanel panel = new CamPanel(cameraService, 0);

            Dictionary<String, String[]> topics = new Hashtable<>();
            topics.put(EventConstants.EVENT_TOPIC, new String[] { "obu/camera/driver" });
            context.registerService(EventHandler.class.getName(), panel, topics);

            ifFrontKamera.getContentPane().add(panel, BorderLayout.CENTER);
            ifFrontKamera.setVisible(false);
            ifFrontKamera.setVisible(true);
            return cameraService;
        }
        if (s instanceof IPersistenceService) {
            System.out.println("Adding Service PersistenceService to MainFrame");
            persistenceService = (IPersistenceService) this.context.getService(ref);
// persistenceService.setEvent(42, "test.file", 123423423, "0,0");
            ifBetriebsparameter.getContentPane().add(new ParameterPanel(persistenceService, obdService));
            ifBetriebsparameter.setVisible(false);
            ifBetriebsparameter.setVisible(true);
        }
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
