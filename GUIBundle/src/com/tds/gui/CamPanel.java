/**
 *
 */
package com.tds.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Date;

import javax.swing.JPanel;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.tds.camera.ICameraService;
import com.tds.camera.IPicture;
import com.tds.camera.Picture;

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
public class CamPanel extends JPanel  implements EventHandler{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private IPicture picture;
    
    private long start;
    private long last;
    private double size;

    public CamPanel(ICameraService camService, int camID) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(picture == null) return;
        
        BufferedImage img = picture.getBufferedImage();
        
        if (img == null) return;
        
        g.drawImage(img, 0, 0, null);
    }

	@Override
	public void handleEvent(Event e) {
		
//		properties.put("camID", camID);
//        properties.put("interval", interval);
//        properties.put("image", service.getRemoteCamImage(camID).getJPEG());
//        properties.put("timestamp", new Timestamp().toString());
		
//		for (String prop : e.getPropertyNames()) {
//            System.out.println(prop + " -> " + e.getProperty(prop));
//        }
		
		
		byte[] jpg = (byte[])e.getProperty("image");
		if(jpg == null) return;
		
		if(start == 0){
			start = new Date().getTime();
			last = start;
			System.out.println("time: 0");
			System.out.println("size: " + size);
		}
		else{
			long current = new Date().getTime();
			
			long lastTime  = current - last;
			long time = current - start;
			
			double lastSize = jpg.length/(1000.0);
			size += lastSize;
			
			
			System.out.println("time: " + time);
			System.out.println("time since last: " + lastTime);
			System.out.println("size: " + size + " kB");
			System.out.println("size of last: " + lastSize + " kB");
			System.out.println("rate: " + size/time + " kBs");
			System.out.println("rate since last : " + lastSize/lastTime + " kBs");
			
			last = current;
		}
		System.out.println("-------------------------------");
		
		
		
		picture = new Picture(jpg);
		this.repaint();
		
	}

}
