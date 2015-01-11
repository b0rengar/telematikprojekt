package com.tds.gui.panels;

import javax.swing.JPanel;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.tds.inertial.IInertialMeasurementService;

public class InertialPanel extends JPanel implements EventHandler {

    /**
     *
     */
    private static final long serialVersionUID = -6409457414135624237L;

    private IInertialMeasurementService inertialService;

    public InertialPanel(IInertialMeasurementService inertialService) {
        this.inertialService = inertialService;
        setLayout(null);
    }

    @Override
    public void handleEvent(Event arg0) {

    }
}
