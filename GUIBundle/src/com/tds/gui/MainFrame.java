package com.tds.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.tds.camera.ICameraService;

public class MainFrame implements ServiceTrackerCustomizer<ICameraService, ICameraService> {

    private static final long serialVersionUID = 1L;
    private BundleContext context;
    private ICameraService cameraService;

    private JFrame frmMainWindow;

    JInternalFrame ifFrontKamera;

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

        initialize();

// public MainFrame() {
//
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmMainWindow = new JFrame();
        frmMainWindow.setTitle("TDS - Telemetrie Daten System");
        frmMainWindow.setBounds(100, 100, 1142, 743);
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

        XYSeries series = new XYSeries("Geschwindigkeit");
        series.add(1, 1);
        series.add(1, 2);
        series.add(2, 1);
        series.add(3, 9);
        series.add(4, 10);
        series.add(5, 1);
        series.add(6, 2);
        series.add(7, 1);
        series.add(8, 9);
        series.add(9, 10);
        series.add(10, 1);
        // Add the series to your data set
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        // Generate the graph
        JFreeChart chart = ChartFactory.createXYLineChart("Geschwindigkeit", // Title
                "", // x-axis Label
                "", // y-axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                false, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
                );
        ChartPanel chartPanel = new ChartPanel(chart);

        XYSeries series2 = new XYSeries("Temperature");
        series2.add(1, 1);
        series2.add(1, 2);
        series2.add(2, 1);
        series2.add(3, 9);
        series2.add(4, 10);
        series2.add(5, 1);
        series2.add(6, 2);
        series2.add(7, 1);
        series2.add(8, 9);
        series2.add(9, 10);
        series2.add(10, 1);
        // Add the series to your data set
        XYSeriesCollection dataset2 = new XYSeriesCollection();
        dataset2.addSeries(series2);
        // Generate the graph
        JFreeChart chart2 = ChartFactory.createXYLineChart("Temperature", // Title
                "", // x-axis Label
                "", // y-axis Label
                dataset2, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                false, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
                );
        ChartPanel chartPanel2 = new ChartPanel(chart2);

        ifFrontKamera = new JInternalFrame("Front-Kamera Ansicht");
        ifFrontKamera.setBounds(592, 0, 445, 315);
        ifFrontKamera.setIconifiable(true);
        ifFrontKamera.setResizable(true);
        ifFrontKamera.setMaximizable(true);
        ifFrontKamera.setClosable(true);
        frmMainWindow.getContentPane().add(ifFrontKamera);

        JInternalFrame ifBetriebsparameter = new JInternalFrame("Betriebsparameter Ansicht");
        ifBetriebsparameter.setBounds(0, 0, 445, 315);
        ifBetriebsparameter.getContentPane().setLayout(new GridLayout(3, 2));
        ifBetriebsparameter.add(chartPanel);
        ifBetriebsparameter.add(chartPanel2);
        frmMainWindow.getContentPane().add(ifBetriebsparameter);

        JInternalFrame ifMeldungen = new JInternalFrame("Meldungen Ansicht");
        ifMeldungen.setBounds(0, 328, 445, 315);
        frmMainWindow.getContentPane().add(ifMeldungen);

        JInternalFrame ifKarte = new JInternalFrame("Karte Ansicht");
        ifKarte.setBounds(592, 328, 445, 315);
        frmMainWindow.getContentPane().add(ifKarte);

        try {
            BufferedImage img = ImageIO.read(new URL("http://maps.googleapis.com/maps/api/staticmap?center=Wildau,Germany&size=512x512&sensor=true_or_false"));
            ifKarte.getContentPane().add(new JLabel(new ImageIcon(img)));
        } catch (Exception e) {
            // TODO Auto-generated catch block
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
    public ICameraService addingService(ServiceReference<ICameraService> ref) {
        if (ref == null) {
            System.out.println("ref gleich null");
            return null;
        }
        cameraService = this.context.getService(ref);


        JPanel panel = new CamPanel(cameraService, 0);

        Dictionary<String, String[]> topics = new Hashtable<>();
        topics.put(EventConstants.EVENT_TOPIC, new String[] { "obu/camera/driver" });
        context.registerService(EventHandler.class.getName(), panel, topics);

        ifFrontKamera.getContentPane().add(panel, BorderLayout.CENTER);
        ifFrontKamera.setVisible(false);
        ifFrontKamera.setVisible(true);
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

}
