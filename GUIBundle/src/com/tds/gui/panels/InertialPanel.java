package com.tds.gui.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.tds.inertial.IInertialMeasurementService;

public class InertialPanel extends JPanel implements EventHandler {

    /**
     *
     */
    private static final long serialVersionUID = -6409457414135624237L;

    private IInertialMeasurementService inertialService;

    private float x = 1;
    private float y = 2;
    private float z = 3;

    private JTextField textFieldX;
    private JTextField textFieldY;
    private JTextField textFieldZ;

    private InertialLineChart chartInertial;

    public InertialPanel(IInertialMeasurementService inertialService) {
        this.inertialService = inertialService;

        initialize();

    }

    private void initialize() {
        setLayout(null);

        JLabel lblX = new JLabel("X");
        lblX.setBounds(12, 13, 15, 16);
        add(lblX);

        textFieldX = new JTextField();
        textFieldX.setEditable(false);
        textFieldX.setBounds(30, 10, 41, 22);
        add(textFieldX);
        textFieldX.setColumns(10);

        JLabel lblY = new JLabel("Y");
        lblY.setBounds(92, 13, 15, 16);
        add(lblY);

        textFieldY = new JTextField();
        textFieldY.setEditable(false);
        textFieldY.setBounds(110, 10, 41, 22);
        add(textFieldY);
        textFieldY.setColumns(10);

        JLabel lblZ = new JLabel("Z");
        lblZ.setBounds(172, 13, 15, 16);
        add(lblZ);

        textFieldZ = new JTextField();
        textFieldZ.setEditable(false);
        textFieldZ.setBounds(190, 10, 41, 22);
        add(textFieldZ);
        textFieldZ.setColumns(10);

        chartInertial = new InertialLineChart();
        chartInertial.setBounds(10, 39, 238, 188);
        add(chartInertial);
    }

    @Override
    public void handleEvent(Event event) {
        // TODO replace Strings by Strings from IInertialMeasurement Interface
        x = Float.parseFloat((String) event.getProperty(IInertialMeasurementService.EVENT_ACC_DATA_X));
        y = Float.parseFloat((String) event.getProperty(IInertialMeasurementService.EVENT_ACC_DATA_Y));
        z = Float.parseFloat((String) event.getProperty(IInertialMeasurementService.EVENT_ACC_DATA_Z));

        textFieldX.setText(String.format("%.2f", x));
        textFieldY.setText(String.format("%.2f", y));
        textFieldZ.setText(String.format("%.2f", z));

        this.getChartInertial().addElement(x, y, z);
    }

    public InertialLineChart getChartInertial() {
        return chartInertial;
    }

    public void setChartInertial(InertialLineChart chartInertial) {
        this.chartInertial = chartInertial;
    }
}
