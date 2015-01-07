package com.tds.gui;

import java.util.TimerTask;

import javax.swing.JTextField;

import com.tds.obd.IOBDService;
import com.tds.obd.OBDParameterSet;

public class OBDRequester extends TimerTask {

    private IOBDService obdService;
    private OBDParameterSet set;

    private JTextField textFieldGeschwindigkeit;
    private JTextField textFieldDrehzahl;
    private JTextField textFieldVerbrauch;
    private JTextField textFieldMotortemperatur;
    private JTextField textFieldInnentemperatur;
    private JTextField textFieldAussentemperatur;

    public OBDRequester(IOBDService obdService, ParameterPanel panel) {
        this.obdService = obdService;

        this.textFieldGeschwindigkeit = panel.getTextFieldGeschwindigkeit();
        this.textFieldDrehzahl = panel.getTextFieldDrehzahl();
        this.textFieldVerbrauch = panel.getTextFieldVerbrauch();
        this.textFieldMotortemperatur = panel.getTextFieldMotortemperatur();
        this.textFieldInnentemperatur = panel.getTextFieldInnentemperatur();
        this.textFieldAussentemperatur = panel.getTextFieldAussentemperatur();
    }

    @Override
    public void run() {
        set = obdService.getPamrameterSet();
        textFieldGeschwindigkeit.setText(set.getSpeed() + " km/h");
        textFieldDrehzahl.setText(set.getEngineRPM() + " U/min");
        textFieldVerbrauch.setText(set.getFuelConsumptionRate() + " l/h");
        textFieldMotortemperatur.setText(set.getEngineTemperature() + " C");
        textFieldInnentemperatur.setText(set.getCarIndoorTemperature() + " C");
        textFieldAussentemperatur.setText(set.getCarOutdoorTemperature() + " C");
    }

}
