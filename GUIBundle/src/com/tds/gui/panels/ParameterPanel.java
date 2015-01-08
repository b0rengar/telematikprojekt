package com.tds.gui.panels;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.tds.gui.OBDRequester;
import com.tds.obd.IOBDService;
import com.tds.persistence.IPersistenceService;

public class ParameterPanel extends JPanel implements EventHandler {

    /**
     *
     */
    private static final long serialVersionUID = 8860351179422635538L;

    private IPersistenceService persistenceService;
    private IOBDService obdService;

    private JTextField textFieldGeschwindigkeit;
    private JTextField textFieldDrehzahl;
    private JTextField textFieldVerbrauch;
    private JTextField textFieldMotortemperatur;
    private JTextField textFieldInnentemperatur;
    private JTextField textFieldAussentemperatur;

    private OBDLineChart chartGeschwindigkeit;
    private OBDLineChart chartDrehzahl;
    private OBDLineChart chartVerbrauch;
    private OBDLineChart chartMotortemperatur;
    private OBDLineChart chartInnentemperatur;
    private OBDLineChart chartAussentemperatur;

    public ParameterPanel(IPersistenceService persistenceService, IOBDService obdService) {
        this.persistenceService = persistenceService;
        this.obdService = obdService;

        initialize();

        TimerTask tt = new OBDRequester(this.obdService, this);

        JLabel lblKmh = new JLabel("km/h");
        lblKmh.setBounds(199, 14, 46, 14);
        add(lblKmh);

        JLabel lblLh = new JLabel("l/h");
        lblLh.setBounds(199, 191, 46, 14);
        add(lblLh);

        JLabel lblc = new JLabel("°C");
        lblc.setBounds(199, 368, 46, 14);
        add(lblc);

        JLabel lblUmin = new JLabel("U/min");
        lblUmin.setBounds(494, 14, 46, 14);
        add(lblUmin);

        JLabel lblc_1 = new JLabel("°C");
        lblc_1.setBounds(494, 191, 46, 14);
        add(lblc_1);

        JLabel lblc_2 = new JLabel("°C");
        lblc_2.setBounds(494, 368, 46, 14);
        add(lblc_2);

        System.out.println("start timer ODB 2");

        Timer t = new Timer();
        t.scheduleAtFixedRate(tt, 1000, 1000);

// System.out.println("R-OSGI-Speed: " + obd.getSpeed());
// System.out.println("R-OSGI-EngineRPM: " + obd.getEngineRPM());
// System.out.println("R-OSGI-EngineTemp: " + obd.getEngineTemperature());
// System.out.println("R-OSGI-Fuel: " + obd.getFuelConsumptionRate());
// System.out.println("R-OSGI-InTemp: " + obd.getCarIndoorTemperature());
// System.out.println("R-OSGI-OutTemp: " + obd.getCarOutdoorTemperature());
// }
    }

    private void initialize() {
        setLayout(null);

        textFieldGeschwindigkeit = new JTextField();
        textFieldGeschwindigkeit.setEditable(false);
        textFieldGeschwindigkeit.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldGeschwindigkeit.setBounds(103, 11, 86, 20);
        add(textFieldGeschwindigkeit);
        textFieldGeschwindigkeit.setColumns(10);

        JLabel lblGeschwindigkeit = new JLabel("Geschwindigkeit");
        lblGeschwindigkeit.setBounds(10, 14, 83, 14);
        add(lblGeschwindigkeit);

        JLabel lblDrehzahl = new JLabel("Drehzahl");
        lblDrehzahl.setBounds(305, 14, 83, 14);
        add(lblDrehzahl);

        textFieldDrehzahl = new JTextField();
        textFieldDrehzahl.setEditable(false);
        textFieldDrehzahl.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldDrehzahl.setBounds(398, 11, 86, 20);
        add(textFieldDrehzahl);
        textFieldDrehzahl.setColumns(10);

        JLabel lblVerbrauch = new JLabel("Verbrauch");
        lblVerbrauch.setBounds(10, 191, 83, 14);
        add(lblVerbrauch);

        textFieldVerbrauch = new JTextField();
        textFieldVerbrauch.setEditable(false);
        textFieldVerbrauch.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldVerbrauch.setBounds(103, 188, 86, 20);
        add(textFieldVerbrauch);
        textFieldVerbrauch.setColumns(10);

        JLabel lblMotortemperatur = new JLabel("Motortemperatur");
        lblMotortemperatur.setBounds(305, 191, 83, 14);
        add(lblMotortemperatur);

        textFieldMotortemperatur = new JTextField();
        textFieldMotortemperatur.setEditable(false);
        textFieldMotortemperatur.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldMotortemperatur.setBounds(398, 188, 86, 20);
        add(textFieldMotortemperatur);
        textFieldMotortemperatur.setColumns(10);

        JLabel lblInnentemperatur = new JLabel("Innentemp.");
        lblInnentemperatur.setBounds(10, 368, 83, 14);
        add(lblInnentemperatur);

        textFieldInnentemperatur = new JTextField();
        textFieldInnentemperatur.setEditable(false);
        textFieldInnentemperatur.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldInnentemperatur.setBounds(103, 365, 86, 20);
        add(textFieldInnentemperatur);
        textFieldInnentemperatur.setColumns(10);

        JLabel lblAussentemperatur = new JLabel("Außentemp.");
        lblAussentemperatur.setBounds(305, 368, 83, 14);
        add(lblAussentemperatur);

        textFieldAussentemperatur = new JTextField();
        textFieldAussentemperatur.setEditable(false);
        textFieldAussentemperatur.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldAussentemperatur.setColumns(10);
        textFieldAussentemperatur.setBounds(398, 365, 86, 20);
        add(textFieldAussentemperatur);

        chartGeschwindigkeit = new OBDLineChart();
        chartGeschwindigkeit.setBounds(10, 39, 265, 141);
        add(chartGeschwindigkeit);

        chartDrehzahl = new OBDLineChart();
        chartDrehzahl.setBounds(305, 39, 265, 141);
        add(chartDrehzahl);

        chartVerbrauch = new OBDLineChart();
        chartVerbrauch.setBounds(10, 216, 265, 141);
        add(chartVerbrauch);

        chartMotortemperatur = new OBDLineChart();
        chartMotortemperatur.setBounds(305, 216, 265, 141);
        add(chartMotortemperatur);

        chartInnentemperatur = new OBDLineChart();
        chartInnentemperatur.setBounds(10, 393, 265, 141);
        add(chartInnentemperatur);

        chartAussentemperatur = new OBDLineChart();
        chartAussentemperatur.setBounds(305, 393, 265, 141);
        add(chartAussentemperatur);
    }

    @Override
    public void handleEvent(Event arg0) {
        // TODO Auto-generated method stub

    }

    public JTextField getTextFieldGeschwindigkeit() {
        return textFieldGeschwindigkeit;
    }

    public void setTextFieldGeschwindigkeit(JTextField textFieldGeschwindigkeit) {
        this.textFieldGeschwindigkeit = textFieldGeschwindigkeit;
    }

    public JTextField getTextFieldDrehzahl() {
        return textFieldDrehzahl;
    }

    public void setTextFieldDrehzahl(JTextField textFieldDrehzahl) {
        this.textFieldDrehzahl = textFieldDrehzahl;
    }

    public JTextField getTextFieldVerbrauch() {
        return textFieldVerbrauch;
    }

    public void setTextFieldVerbrauch(JTextField textFieldVerbrauch) {
        this.textFieldVerbrauch = textFieldVerbrauch;
    }

    public JTextField getTextFieldMotortemperatur() {
        return textFieldMotortemperatur;
    }

    public void setTextFieldMotortemperatur(JTextField textFieldMotortemperatur) {
        this.textFieldMotortemperatur = textFieldMotortemperatur;
    }

    public JTextField getTextFieldInnentemperatur() {
        return textFieldInnentemperatur;
    }

    public void setTextFieldInnentemperatur(JTextField textFieldInnentemperatur) {
        this.textFieldInnentemperatur = textFieldInnentemperatur;
    }

    public JTextField getTextFieldAussentemperatur() {
        return textFieldAussentemperatur;
    }

    public void setTextFieldAussentemperatur(JTextField textFieldAussentemperatur) {
        this.textFieldAussentemperatur = textFieldAussentemperatur;
    }

    public OBDLineChart getChartGeschwindigkeit() {
        return chartGeschwindigkeit;
    }

    public void setChartGeschwindigkeit(OBDLineChart chartGeschwindigkeit) {
        this.chartGeschwindigkeit = chartGeschwindigkeit;
    }

    public OBDLineChart getChartDrehzahl() {
        return chartDrehzahl;
    }

    public void setChartDrehzahl(OBDLineChart chartDrehzahl) {
        this.chartDrehzahl = chartDrehzahl;
    }

    public OBDLineChart getChartVerbrauch() {
        return chartVerbrauch;
    }

    public void setChartVerbrauch(OBDLineChart chartVerbrauch) {
        this.chartVerbrauch = chartVerbrauch;
    }

    public OBDLineChart getChartMotortemperatur() {
        return chartMotortemperatur;
    }

    public void setChartMotortemperatur(OBDLineChart chartMotortemperatur) {
        this.chartMotortemperatur = chartMotortemperatur;
    }

    public OBDLineChart getChartInnentemperatur() {
        return chartInnentemperatur;
    }

    public void setChartInnentemperatur(OBDLineChart chartInnentemperatur) {
        this.chartInnentemperatur = chartInnentemperatur;
    }

    public OBDLineChart getChartAussentemperatur() {
        return chartAussentemperatur;
    }

    public void setChartAussentemperatur(OBDLineChart chartAussentemperatur) {
        this.chartAussentemperatur = chartAussentemperatur;
    }
}
