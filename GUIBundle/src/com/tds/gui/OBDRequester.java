package com.tds.gui;

import java.util.TimerTask;

import javax.swing.JTextField;

import com.tds.obd.IOBDService;

public class OBDRequester extends TimerTask {

    private IOBDService obdService;

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
        textFieldGeschwindigkeit.setText(this.obdService.getSpeed() + " km/h");
        textFieldDrehzahl.setText(this.obdService.getEngineRPM() + " U/min");
        textFieldVerbrauch.setText(this.obdService.getFuelConsumptionRate() + " l/h");
        textFieldMotortemperatur.setText(this.obdService.getEngineTemperature() + " C");
        textFieldInnentemperatur.setText(this.obdService.getCarIndoorTemperature() + " C");
        textFieldAussentemperatur.setText(this.obdService.getCarOutdoorTemperature() + " C");
    }

}
