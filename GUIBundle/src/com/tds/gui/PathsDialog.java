package com.tds.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PathsDialog extends JDialog implements ActionListener{

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldProjectPath;
	private JTextField textFieldTempPath;
	private JTextField textFieldMsgPath;
	
	private JButton btnOpenProjectPath;
	private JButton btnOpenTempPath;
	private JButton btnOpenMsgPath;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			PathsDialog dialog = new PathsDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public PathsDialog() {
		setTitle("Speicherpfade");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		{
			JLabel lblProjektpfad = new JLabel("Projektpfad:");
			lblProjektpfad.setBounds(12, 13, 120, 16);
			contentPanel.add(lblProjektpfad);
		}
		{
			textFieldProjectPath = new JTextField();
			textFieldProjectPath.setBounds(12, 42, 300, 22);
			contentPanel.add(textFieldProjectPath);
			textFieldProjectPath.setColumns(10);
		}
		{
			JLabel lblTemporrerPfad = new JLabel("Tempor\u00E4rer Pfad:");
			lblTemporrerPfad.setBounds(12, 77, 120, 16);
			contentPanel.add(lblTemporrerPfad);
		}
		{
			textFieldTempPath = new JTextField();
			textFieldTempPath.setBounds(12, 106, 300, 22);
			contentPanel.add(textFieldTempPath);
			textFieldTempPath.setColumns(10);
		}
		{
			JLabel lblPfadFrMeldungsdaten = new JLabel("Pfad f\u00FCr Meldungsdaten:");
			lblPfadFrMeldungsdaten.setBounds(12, 141, 168, 16);
			contentPanel.add(lblPfadFrMeldungsdaten);
		}
		{
			textFieldMsgPath = new JTextField();
			textFieldMsgPath.setBounds(12, 170, 300, 22);
			contentPanel.add(textFieldMsgPath);
			textFieldMsgPath.setColumns(10);
		}
		{
			btnOpenProjectPath = new JButton("\u00D6ffnen");
			btnOpenProjectPath.setBounds(323, 41, 97, 25);
			btnOpenProjectPath.addActionListener(this);
			contentPanel.add(btnOpenProjectPath);
		}
		{
			btnOpenTempPath = new JButton("\u00D6ffnen");
			btnOpenTempPath.setBounds(323, 105, 97, 25);
			btnOpenTempPath.addActionListener(this);
			contentPanel.add(btnOpenTempPath);
		}
		{
			btnOpenMsgPath = new JButton("\u00D6ffnen");
			btnOpenMsgPath.setBounds(324, 169, 97, 25);
			btnOpenMsgPath.addActionListener(this);
			contentPanel.add(btnOpenMsgPath);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton saveButton = new JButton("Speichern");
				saveButton.setActionCommand("OK");
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(btnOpenProjectPath.equals(e.getSource())){
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    int returnVal = chooser.showOpenDialog(null);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		       System.out.println("You chose to open this file: " +
		            chooser.getCurrentDirectory().getAbsolutePath());
		       textFieldProjectPath.setText(chooser.getCurrentDirectory().getAbsolutePath());
		    }		    
		}
		
		if(btnOpenTempPath.equals(e.getSource())){
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    int returnVal = chooser.showOpenDialog(null);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		       System.out.println("You chose to open this file: " +
		            chooser.getCurrentDirectory().getAbsolutePath());
		       textFieldTempPath.setText(chooser.getCurrentDirectory().getAbsolutePath());
		    }
		}
		
		if(btnOpenMsgPath.equals(e.getSource())){
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    int returnVal = chooser.showOpenDialog(null);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		       System.out.println("You chose to open this file: " +
		            chooser.getCurrentDirectory().getAbsolutePath());
		       textFieldMsgPath.setText(chooser.getCurrentDirectory().getAbsolutePath());
		    }
		}	
		
	}

}
