package com.tds.gui;

import java.util.TimerTask;

import javax.swing.JTextField;

import com.tds.gui.panels.ParameterPanel;
import com.tds.obd.IOBDService;
import com.tds.obd.OBDParameterSet;

public class OBDRequester extends TimerTask {

    private ParameterPanel panel;

    private IOBDService obdService;
    private OBDParameterSet set;

    private JTextField textFieldGeschwindigkeit;
    private JTextField textFieldDrehzahl;
    private JTextField textFieldVerbrauch;
    private JTextField textFieldMotortemperatur;
    private JTextField textFieldInnentemperatur;
    private JTextField textFieldAussentemperatur;

    public OBDRequester(IOBDService obdService, ParameterPanel panel) {
        this.panel = panel;
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
        textFieldGeschwindigkeit.setText(String.format("%.0f", set.getSpeed()));
        textFieldDrehzahl.setText(set.getEngineRPM() + "");
        textFieldVerbrauch.setText(String.format("%.2f", set.getFuelConsumptionRate()));
        textFieldMotortemperatur.setText(String.format("%.1f", set.getEngineTemperature()));
        textFieldInnentemperatur.setText(String.format("%.1f", set.getCarIndoorTemperature()));
        textFieldAussentemperatur.setText(String.format("%.1f", set.getCarOutdoorTemperature()));
        repaintCharts();
    }

    private void repaintCharts() {
        panel.getChartGeschwindigkeit().addElement(set.getSpeed());
        panel.getChartDrehzahl().addElement(set.getEngineRPM());
        panel.getChartVerbrauch().addElement(set.getFuelConsumptionRate());
        panel.getChartMotortemperatur().addElement(set.getEngineTemperature());
        panel.getChartAussentemperatur().addElement(set.getCarOutdoorTemperature());
        panel.getChartInnentemperatur().addElement(set.getCarIndoorTemperature());
    }
}
