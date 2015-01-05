package com.tds.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

public class UnitsDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();

// /**
// * Launch the application.
// */
// public static void main(String[] args) {
// try {
// UnitsDialog dialog = new UnitsDialog();
// dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
// dialog.setVisible(true);
// } catch (Exception e) {
// e.printStackTrace();
// }
// }

    /**
     * Create the dialog.
     */
    public UnitsDialog() {
        setTitle("Einheiten");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JLabel lblGeschwindigkeit = new JLabel("Geschwindigkeit:");
            lblGeschwindigkeit.setBounds(12, 13, 140, 16);
            contentPanel.add(lblGeschwindigkeit);
        }

        JRadioButton rdbtnKmh = new JRadioButton(VelocityUnit.KMH.toString());
        rdbtnKmh.setBounds(22, 38, 127, 25);
        contentPanel.add(rdbtnKmh);

        JRadioButton rdbtnMph = new JRadioButton(VelocityUnit.MPH.toString());
        rdbtnMph.setBounds(153, 38, 127, 25);
        contentPanel.add(rdbtnMph);

        JLabel lblTemperatur = new JLabel("Temperatur:");
        lblTemperatur.setBounds(12, 72, 140, 16);
        contentPanel.add(lblTemperatur);

        JRadioButton rdbtnC = new JRadioButton(TemperatureUnit.CELSIUS.toString());
        rdbtnC.setBounds(22, 97, 127, 25);
        contentPanel.add(rdbtnC);

        JRadioButton rdbtnf = new JRadioButton(TemperatureUnit.FAHRENHEIT.toString());
        rdbtnf.setBounds(153, 97, 127, 25);
        contentPanel.add(rdbtnf);

        JRadioButton rdbtnk = new JRadioButton(TemperatureUnit.KELVIN.toString());
        rdbtnk.setBounds(284, 97, 127, 25);
        contentPanel.add(rdbtnk);

        JLabel lblDruck = new JLabel("Druck:");
        lblDruck.setBounds(12, 131, 140, 16);
        contentPanel.add(lblDruck);

        JRadioButton rdbtnPa = new JRadioButton("Pa");
        rdbtnPa.setBounds(22, 156, 127, 25);
        contentPanel.add(rdbtnPa);

        JRadioButton rdbtnLb = new JRadioButton("lb");
        rdbtnLb.setBounds(153, 156, 127, 25);
        contentPanel.add(rdbtnLb);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }
}