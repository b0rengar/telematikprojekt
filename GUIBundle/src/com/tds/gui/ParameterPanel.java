package com.tds.gui;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

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

    public ParameterPanel(IPersistenceService persistenceService, IOBDService obdService) {
        this.persistenceService = persistenceService;
        this.obdService = obdService;

        initialize();

        TimerTask tt = new OBDRequester(this.obdService, this);
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
        textFieldGeschwindigkeit.setBounds(103, 11, 86, 20);
        add(textFieldGeschwindigkeit);
        textFieldGeschwindigkeit.setColumns(10);

        JLabel lblGeschwindigkeit = new JLabel("Geschwindigkeit");
        lblGeschwindigkeit.setBounds(10, 14, 83, 14);
        add(lblGeschwindigkeit);

        JLabel lblDrehzahl = new JLabel("Drehzahl");
        lblDrehzahl.setBounds(250, 14, 83, 14);
        add(lblDrehzahl);

        textFieldDrehzahl = new JTextField();
        textFieldDrehzahl.setBounds(343, 11, 86, 20);
        add(textFieldDrehzahl);
        textFieldDrehzahl.setColumns(10);

        JLabel lblVerbrauch = new JLabel("Verbrauch");
        lblVerbrauch.setBounds(10, 116, 83, 14);
        add(lblVerbrauch);

        textFieldVerbrauch = new JTextField();
        textFieldVerbrauch.setBounds(103, 113, 86, 20);
        add(textFieldVerbrauch);
        textFieldVerbrauch.setColumns(10);

        JLabel lblMotortemperatur = new JLabel("Motortemperatur");
        lblMotortemperatur.setBounds(250, 116, 83, 14);
        add(lblMotortemperatur);

        textFieldMotortemperatur = new JTextField();
        textFieldMotortemperatur.setBounds(343, 113, 86, 20);
        add(textFieldMotortemperatur);
        textFieldMotortemperatur.setColumns(10);

        JLabel lblInnentemperatur = new JLabel("Innentemp.");
        lblInnentemperatur.setBounds(10, 219, 83, 14);
        add(lblInnentemperatur);

        textFieldInnentemperatur = new JTextField();
        textFieldInnentemperatur.setBounds(103, 216, 86, 20);
        add(textFieldInnentemperatur);
        textFieldInnentemperatur.setColumns(10);

        JLabel lblAussentemperatur = new JLabel("Außentemp.");
        lblAussentemperatur.setBounds(250, 219, 83, 14);
        add(lblAussentemperatur);

        textFieldAussentemperatur = new JTextField();
        textFieldAussentemperatur.setColumns(10);
        textFieldAussentemperatur.setBounds(343, 216, 86, 20);
        add(textFieldAussentemperatur);
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

}
