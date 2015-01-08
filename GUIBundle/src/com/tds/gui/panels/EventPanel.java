package com.tds.gui.panels;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.tds.persistence.TdsEvent;

public class EventPanel extends JPanel implements ListSelectionListener {

    /**
     *
     */
    private static final long serialVersionUID = -4941595227914534470L;
    private EventListModel listModel;
    JList<TdsEvent> list;

    private JTextField textFieldPosition;
    private JTextField textFieldPath;
    private JTextField textFieldTyp;

    public EventPanel() {

        listModel = new EventListModel();
        listModel.addElement(new TdsEvent(23, 8456466, "1231321321,4546464", "C:\\test1.png"));
        listModel.addElement(new TdsEvent(35, 3454535, "3453451,4546464", "C:\\test2.png"));
        listModel.addElement(new TdsEvent(64, 5677567, "121231,4123464", "C:\\test3.png"));
        listModel.addElement(new TdsEvent(12, 1231213, "1231234321,454232344", "C:\\test4.png"));

        initialize();

    }

    private void initialize() {
        setLayout(null);
        JSplitPane splitPane = new JSplitPane();
        splitPane.setBounds(10, 5, 498, 222);
        add(splitPane);

        list = new JList<TdsEvent>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);
        splitPane.setLeftComponent(list);

        JPanel panel = new JPanel();
        splitPane.setRightComponent(panel);
        panel.setLayout(null);

        JLabel lblDetailsDerMeldung = new JLabel("Details der Meldung");
        lblDetailsDerMeldung.setBounds(99, 11, 157, 14);
        panel.add(lblDetailsDerMeldung);

        JLabel lblDate = new JLabel("14.10.2014");
        lblDate.setBounds(37, 50, 94, 14);
        panel.add(lblDate);

        JLabel lblTime = new JLabel("15:33");
        lblTime.setBounds(127, 50, 46, 14);
        panel.add(lblTime);

        JLabel lblMeldungstyp = new JLabel("Meldungstyp");
        lblMeldungstyp.setBounds(37, 75, 157, 14);
        panel.add(lblMeldungstyp);

        JLabel lblPosition = new JLabel("Position:");
        lblPosition.setBounds(37, 100, 121, 14);
        panel.add(lblPosition);

        textFieldPosition = new JTextField();
        textFieldPosition.setEditable(false);
        textFieldPosition.setBounds(37, 122, 255, 20);
        panel.add(textFieldPosition);
        textFieldPosition.setColumns(10);

        textFieldPath = new JTextField();
        textFieldPath.setEditable(false);
        textFieldPath.setBounds(37, 156, 255, 20);
        panel.add(textFieldPath);
        textFieldPath.setColumns(10);

        textFieldTyp = new JTextField();
        textFieldTyp.setEditable(false);
        textFieldTyp.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldTyp.setBounds(242, 72, 50, 20);
        panel.add(textFieldTyp);
        textFieldTyp.setColumns(10);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            TdsEvent event = list.getSelectedValue();
            textFieldTyp.setText(event.getEventId() + "");
            textFieldPosition.setText(event.getGps());
            textFieldPath.setText(event.getFilename());
        }
    }
}
