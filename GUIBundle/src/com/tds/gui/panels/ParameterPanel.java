package com.tds.gui.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.tds.obd.IOBDService;
import com.tds.obd.OBDParameterSet;

public class ParameterPanel extends JPanel implements EventHandler {

    /**
     *
     */
    private static final long serialVersionUID = 8860351179422635538L;

// private IPersistenceService persistenceService;
    private IOBDService obdService;

    private OBDParameterSet parameterSet;

    private JTextField textFieldGeschwindigkeit;
    private JTextField textFieldDrehzahl;
    private JTextField textFieldVerbrauch;
    private JTextField textFieldMotortemperatur;
    private JTextField textFieldGaspedal;
    private JTextField textFieldMotorlast;

    private OBDLineChart chartGeschwindigkeit;
    private OBDLineChart chartDrehzahl;
    private OBDLineChart chartVerbrauch;
    private OBDLineChart chartMotortemperatur;
    private OBDLineChart chartGaspedal;
    private OBDLineChart chartMotorlast;

    public ParameterPanel(IOBDService obdService) {
// this.persistenceService = persistenceService;
        this.obdService = obdService;

        parameterSet = new OBDParameterSet();

// System.out.println("num at mongo = " + persistenceService.getTdsEventsFromDB().size());

        initialize();

// TimerTask tt = new OBDRequester(this.obdService, this);
//
// System.out.println("start timer ODB 2");
//
// Timer t = new Timer();
// t.scheduleAtFixedRate(tt, 1000, 1000);

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
        textFieldGeschwindigkeit.setBounds(140, 11, 60, 20);
        add(textFieldGeschwindigkeit);
        textFieldGeschwindigkeit.setColumns(10);

        JLabel lblGeschwindigkeit = new JLabel("Geschwindigkeit");
        lblGeschwindigkeit.setBounds(10, 14, 120, 14);
        add(lblGeschwindigkeit);

        JLabel lblDrehzahl = new JLabel("Drehzahl");
        lblDrehzahl.setBounds(305, 14, 119, 14);
        add(lblDrehzahl);

        textFieldDrehzahl = new JTextField();
        textFieldDrehzahl.setEditable(false);
        textFieldDrehzahl.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldDrehzahl.setBounds(434, 11, 60, 20);
        add(textFieldDrehzahl);
        textFieldDrehzahl.setColumns(10);

        JLabel lblVerbrauch = new JLabel("Tankfüllstand");
        lblVerbrauch.setBounds(10, 191, 120, 14);
        add(lblVerbrauch);

        textFieldVerbrauch = new JTextField();
        textFieldVerbrauch.setEditable(false);
        textFieldVerbrauch.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldVerbrauch.setBounds(140, 188, 60, 20);
        add(textFieldVerbrauch);
        textFieldVerbrauch.setColumns(10);

        JLabel lblMotortemperatur = new JLabel("Motortemperatur");
        lblMotortemperatur.setBounds(305, 191, 119, 14);
        add(lblMotortemperatur);

        textFieldMotortemperatur = new JTextField();
        textFieldMotortemperatur.setEditable(false);
        textFieldMotortemperatur.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldMotortemperatur.setBounds(434, 188, 60, 20);
        add(textFieldMotortemperatur);
        textFieldMotortemperatur.setColumns(10);

        JLabel lblInnentemperatur = new JLabel("Gaspedal Position");
        lblInnentemperatur.setBounds(10, 368, 120, 14);
        add(lblInnentemperatur);

        textFieldGaspedal = new JTextField();
        textFieldGaspedal.setEditable(false);
        textFieldGaspedal.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldGaspedal.setBounds(140, 365, 60, 20);
        add(textFieldGaspedal);
        textFieldGaspedal.setColumns(10);

        JLabel lblAussentemperatur = new JLabel("Motorlast");
        lblAussentemperatur.setBounds(305, 368, 119, 14);
        add(lblAussentemperatur);

        textFieldMotorlast = new JTextField();
        textFieldMotorlast.setEditable(false);
        textFieldMotorlast.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldMotorlast.setColumns(10);
        textFieldMotorlast.setBounds(434, 365, 60, 20);
        add(textFieldMotorlast);

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

        chartGaspedal = new OBDLineChart();
        chartGaspedal.setBounds(10, 393, 265, 141);
        add(chartGaspedal);

        chartMotorlast = new OBDLineChart();
        chartMotorlast.setBounds(305, 393, 265, 141);
        add(chartMotorlast);

        JLabel lblKmh = new JLabel("km/h");
        lblKmh.setBounds(210, 14, 46, 14);
        add(lblKmh);

        JLabel lblLh = new JLabel("%");
        lblLh.setBounds(210, 191, 46, 14);
        add(lblLh);

        JLabel lblc = new JLabel("%");
        lblc.setBounds(210, 368, 46, 14);
        add(lblc);

        JLabel lblUmin = new JLabel("U/min");
        lblUmin.setBounds(504, 14, 46, 14);
        add(lblUmin);

        JLabel lblc_1 = new JLabel("°C");
        lblc_1.setBounds(504, 191, 46, 14);
        add(lblc_1);

        JLabel lblc_2 = new JLabel("%");
        lblc_2.setBounds(504, 368, 46, 14);
        add(lblc_2);
    }

    @Override
    public void handleEvent(Event event) {
        parameterSet.setSpeed(Float.parseFloat((String) event.getProperty(IOBDService.EVENT_OBD_DATA_SPEED)));
        parameterSet.setEngineRPM(Integer.parseInt((String) event.getProperty(IOBDService.EVENT_OBD_DATA_RPM)));
        parameterSet.setFuelLevel(Float.parseFloat((String) event.getProperty(IOBDService.eVENT_OBD_DATA_FUEL_LEVEL)));
        parameterSet.setEngineTemperature(Float.parseFloat((String) event.getProperty(IOBDService.EVENT_OBD_DATA_TEMPERATURE_ENGINE)));
        parameterSet.setThrottlePosition(Float.parseFloat((String) event.getProperty(IOBDService.EVENT_OBD_DATA_THROTTLE_POSITION)));
        parameterSet.setEngineLoad(Float.parseFloat((String) event.getProperty(IOBDService.EVENT_OBD_DATA_ENGINE_LOAD)));

        textFieldGeschwindigkeit.setText(String.format("%.0f", parameterSet.getSpeed()));
        textFieldDrehzahl.setText(parameterSet.getEngineRPM() + "");
        textFieldVerbrauch.setText(String.format("%.2f", parameterSet.getFuelLevel()));
        textFieldMotortemperatur.setText(String.format("%.1f", parameterSet.getEngineTemperature()));
        textFieldGaspedal.setText(String.format("%.1f", parameterSet.getThrottlePosition()));
        textFieldMotorlast.setText(String.format("%.1f", parameterSet.getEngineLoad()));

        this.getChartGeschwindigkeit().addElement(parameterSet.getSpeed());
        this.getChartDrehzahl().addElement(parameterSet.getEngineRPM());
        this.getChartVerbrauch().addElement(parameterSet.getFuelLevel());
        this.getChartMotortemperatur().addElement(parameterSet.getEngineTemperature());
        this.getChartMotorlast().addElement(parameterSet.getEngineLoad());
        this.getChartGaspedal().addElement(parameterSet.getThrottlePosition());
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
        return textFieldGaspedal;
    }

    public void setTextFieldInnentemperatur(JTextField textFieldInnentemperatur) {
        this.textFieldGaspedal = textFieldInnentemperatur;
    }

    public JTextField getTextFieldAussentemperatur() {
        return textFieldMotorlast;
    }

    public void setTextFieldAussentemperatur(JTextField textFieldAussentemperatur) {
        this.textFieldMotorlast = textFieldAussentemperatur;
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

    public OBDLineChart getChartGaspedal() {
        return chartGaspedal;
    }

    public void setChartGaspedal(OBDLineChart chartInnentemperatur) {
        this.chartGaspedal = chartInnentemperatur;
    }

    public OBDLineChart getChartMotorlast() {
        return chartMotorlast;
    }

    public void setChartMotorlast(OBDLineChart chartAussentemperatur) {
        this.chartMotorlast = chartAussentemperatur;
    }
}
