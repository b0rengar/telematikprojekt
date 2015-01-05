package com.tds.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class CarEditDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8985674789136704492L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldFahrzeugId;
	private JTextField textFieldMarke;
	private JTextField textFieldTyp;
	private JTextField textFieldBaujahr;
	private JTextField textFieldFahrgestellnr;
	private JTextField textFieldIpaddress;
	private JTextField textFieldIpport;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			CarEditDialog dialog = new CarEditDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true); 
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public CarEditDialog() {
		setTitle("Fahrzeug hinzuf\u00FCgen / bearbeiten");
		setBounds(100, 100, 450, 340);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblFahrzeugId = new JLabel("Fahrzeug ID:");
		lblFahrzeugId.setBounds(12, 13, 120, 16);
		contentPanel.add(lblFahrzeugId);
		
		JLabel lblMarke = new JLabel("Marke:");
		lblMarke.setBounds(12, 42, 120, 16);
		contentPanel.add(lblMarke);
		
		JLabel lblTyp = new JLabel("Typ:");
		lblTyp.setBounds(12, 71, 120, 16);
		contentPanel.add(lblTyp);
		
		JLabel lblBaujahr = new JLabel("Baujahr:");
		lblBaujahr.setBounds(12, 100, 120, 16);
		contentPanel.add(lblBaujahr);
		
		JLabel lblFahrgestellnr = new JLabel("Fahrgestellnr.:");
		lblFahrgestellnr.setBounds(12, 129, 120, 16);
		contentPanel.add(lblFahrgestellnr);
		
		JLabel lblIpaddresseport = new JLabel("IP-Addresse:Port:");
		lblIpaddresseport.setBounds(12, 158, 120, 16);
		contentPanel.add(lblIpaddresseport);
		
		JLabel lblSonstiges = new JLabel("Sonstiges:");
		lblSonstiges.setBounds(12, 187, 120, 16);
		contentPanel.add(lblSonstiges);
		
		textFieldFahrzeugId = new JTextField();
		textFieldFahrzeugId.setBounds(144, 10, 276, 22);
		contentPanel.add(textFieldFahrzeugId);
		textFieldFahrzeugId.setColumns(10);
		
		textFieldMarke = new JTextField();
		textFieldMarke.setBounds(144, 39, 276, 22);
		contentPanel.add(textFieldMarke);
		textFieldMarke.setColumns(10);
		
		textFieldTyp = new JTextField();
		textFieldTyp.setBounds(144, 68, 276, 22);
		contentPanel.add(textFieldTyp);
		textFieldTyp.setColumns(10);
		
		textFieldBaujahr = new JTextField();
		textFieldBaujahr.setBounds(144, 97, 276, 22);
		contentPanel.add(textFieldBaujahr);
		textFieldBaujahr.setColumns(10);
		
		textFieldFahrgestellnr = new JTextField();
		textFieldFahrgestellnr.setBounds(144, 126, 276, 22);
		contentPanel.add(textFieldFahrgestellnr);
		textFieldFahrgestellnr.setColumns(10);
		
		textFieldIpaddress = new JTextField();
		textFieldIpaddress.setBounds(144, 155, 206, 22);
		contentPanel.add(textFieldIpaddress);
		textFieldIpaddress.setColumns(10);
		
		textFieldIpport = new JTextField();
		textFieldIpport.setBounds(362, 155, 58, 22);
		contentPanel.add(textFieldIpport);
		textFieldIpport.setColumns(10);
		
		JTextPane textPaneSonstigesPlain = new JTextPane();
		JScrollPane textPaneSonstiges = new JScrollPane(textPaneSonstigesPlain);
		textPaneSonstiges.setBounds(144, 187, 276, 58);
		contentPanel.add(textPaneSonstiges);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			{
				JButton saveButton = new JButton("Speichern");
				buttonPane.add(saveButton);
				saveButton.setHorizontalAlignment(SwingConstants.RIGHT);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				JButton cancelButton = new JButton("Abbrechen");
				buttonPane.add(cancelButton);
				cancelButton.setHorizontalAlignment(SwingConstants.LEFT);
				cancelButton.setActionCommand("Cancel");
			}
		}
	}
}
