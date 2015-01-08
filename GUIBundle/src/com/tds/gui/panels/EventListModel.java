package com.tds.gui.panels;

import javax.swing.DefaultListModel;

import com.tds.persistence.TdsEvent;

public class EventListModel extends DefaultListModel<TdsEvent> {

    /**
     *
     */
    private static final long serialVersionUID = 5912897611985714705L;

    @Override
    public TdsEvent getElementAt(int index) {
        TdsEvent event = super.getElementAt(index);
        return event;
    }

}
