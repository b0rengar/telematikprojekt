package com.tds.gui.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.tds.gui.settings.Client;
import com.tds.gui.settings.Settings;

/**
 * A dialog to add and remove mobile clients to be notified about detected events.
 *
 * @author Christian Bodler
 *
 */
public class ClientServerDialog extends JDialog implements ActionListener, TableModelListener {

    /**
     *
     */
    private static final long serialVersionUID = -4568574307485075131L;

    private Settings settings;

    private final JPanel contentPanel = new JPanel();
    private JTextField textFieldName;
    private JTextField textFieldIPaddress;
    private JTextField textFieldIPport;
    private JTable table;
    private JButton btnSave;
    private JButton btnDisconnect;

    private HashMap<String, Client> selectMap;
    private Object[][] objectMap;

    /**
     * Create the dialog.
     */
    public ClientServerDialog() {
        settings = Settings.getSettingsSingleton();

        setTitle("Client-Server");
        setBounds(100, 100, 381, 450);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(12, 13, 120, 16);
        contentPanel.add(lblName);

        JLabel lblIpadresse = new JLabel("IP-Adresse:");
        lblIpadresse.setBounds(12, 42, 120, 16);
        contentPanel.add(lblIpadresse);

        JLabel lblPort = new JLabel("Port:");
        lblPort.setBounds(12, 71, 120, 16);
        contentPanel.add(lblPort);

        textFieldName = new JTextField();
        textFieldName.setBounds(144, 13, 207, 22);
        textFieldName.setText(settings.getServerName());
        contentPanel.add(textFieldName);
        textFieldName.setColumns(10);

        textFieldIPaddress = new JTextField();
        textFieldIPaddress.setBounds(144, 39, 207, 22);
        textFieldIPaddress.setText(settings.getServerIpAddress());
        contentPanel.add(textFieldIPaddress);
        textFieldIPaddress.setColumns(10);

        textFieldIPport = new JTextField();
        textFieldIPport.setBounds(257, 68, 94, 22);
        textFieldIPport.setText(settings.getServerIpAddressPort());
        contentPanel.add(textFieldIPport);
        textFieldIPport.setColumns(10);

        JButton btnSave = new JButton("Speichern");
        btnSave.setBounds(130, 118, 97, 25);
        btnSave.addActionListener(this);
        contentPanel.add(btnSave);

        JSeparator separator = new JSeparator();
        separator.setBounds(12, 156, 339, 2);
        contentPanel.add(separator);

        table = new JTable();
        objectMap = new Object[settings.getClientList().size()][3];
        selectMap = new HashMap<String, Client>();
        int i = 0;
        for (Client c : settings.getClientList()) {
            objectMap[i][0] = false;
            objectMap[i][1] = c.getName();
            objectMap[i][2] = c.getIpAddress();
            selectMap.put(c.getIpAddress(), c);
            i++;
        }

        TableModel tableModel = new DefaultTableModel(objectMap, new String[] { "Auswahl", "Clientname", "IP-Adresse" }) {
            Class[] columnTypes = new Class[] { Boolean.class, String.class, String.class };

            @Override
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        };
        tableModel.addTableModelListener(this);
        table.setModel(tableModel);
        table.setBounds(12, 174, 339, 181);
        contentPanel.add(table);

        JLabel lblAuswahl = new JLabel("Auswahl:");
        lblAuswahl.setBounds(12, 368, 56, 16);
        contentPanel.add(lblAuswahl);

        btnDisconnect = new JButton("Trennen");
        btnDisconnect.setBounds(130, 364, 97, 25);
        btnDisconnect.addActionListener(this);
        contentPanel.add(btnDisconnect);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

// if(btnSave.equals(e.getSource())){
//
// }

        if (btnDisconnect.equals(e.getSource())) {
            for (int i = 0; i < objectMap.length; i++) {
                if ((Boolean) objectMap[i][0]) {
                    System.out.println("disconnected " + objectMap[i][1]);
                    settings.getClientList().remove(selectMap.get(objectMap[i][2]));
                }
            }
            super.dispose();
        }

    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {
            objectMap[e.getFirstRow()][0] = !(Boolean) objectMap[e.getFirstRow()][0];
        }
    }
}
