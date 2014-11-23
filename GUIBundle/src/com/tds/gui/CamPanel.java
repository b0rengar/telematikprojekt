/**
 * 
 */
package com.tds.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.tds.camera.ICameraService;

/**
 * <b>GUIBundle <br />
 * com.tds.gui - CamPanel <br />
 * </b>
 * 
 * Description.
 * 
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 20.11.2014 23:31:21
 * 
 */
public class CamPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private ICameraService camSevice;

    private int camID;

    public CamPanel(ICameraService camService, int camID) {
        this.camSevice = camService;
        this.camID = camID;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("draw cam image");
        g.drawImage(camSevice.getCamImage(camID, BufferedImage.TYPE_3BYTE_BGR), 0, 0, null);
    }

}
