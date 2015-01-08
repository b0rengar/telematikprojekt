package com.tds.gui.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CarListDialog extends JDialog {

    /**
     *
     */
    private static final long serialVersionUID = 2527373968267497543L;
    private final JPanel contentPanel = new JPanel();
    private JTable table;

// /**
// * Launch the application.
// */
// public static void main(String[] args) {
// try {
// CarListDialog dialog = new CarListDialog();
// dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
// dialog.setVisible(true);
// } catch (Exception e) {
// e.printStackTrace();
// }
// }

    /**
     * Create the dialog.
     */
    @SuppressWarnings({ "serial", "rawtypes", "unchecked" })
    public CarListDialog() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new FlowLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        Object[][] rowMap = new Object[][] {

        };
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        {
            table = new JTable();
            table.setModel(new DefaultTableModel(new Object[][] { { null, "sadsa", "asd", "asd", new Integer(1232), null }, }, new String[] { "Auswahl", "Fahrzeug-ID", "Marke", "Typ", "Baujahr", "Fahrgestellnr." }) {
                Class[] columnTypes = new Class[] { Boolean.class, String.class, String.class, String.class, Integer.class, String.class };

                @Override
                public Class getColumnClass(int columnIndex) {
                    return columnTypes[columnIndex];
                }
            });

            contentPanel.add(table);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton btnEdit = new JButton("Bearbeiten");
                btnEdit.setActionCommand("Edit");
                buttonPane.add(btnEdit);
                getRootPane().setDefaultButton(btnEdit);
            }
            {
                JButton btnDelete = new JButton("L\u00F6schen");
                btnDelete.setActionCommand("Delete");
                buttonPane.add(btnDelete);
            }
            {
                JButton btnAddCar = new JButton("Fahrzeug hinzuf\u00FCgen");
                buttonPane.add(btnAddCar);
            }
        }
    }
}
