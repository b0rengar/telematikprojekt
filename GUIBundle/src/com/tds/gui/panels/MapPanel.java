package com.tds.gui.panels;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.tds.gps.IGPSService;

public class MapPanel extends JPanel implements EventHandler {

    /**
     *
     */
    private static final long serialVersionUID = 3216448054722853075L;

    private static final boolean DEBUG = false;

    private JLabel imageLabel = null;
    int i = 0;

    private IGPSService gpsService;

    private final static int maxNumOfLastPositions = 20;
    private ArrayList<Coordinates> lastPositions;
    private Coordinates currentPosition;

    public MapPanel(IGPSService gpsService) {
        this.gpsService = gpsService;
        this.lastPositions = new ArrayList<Coordinates>();

        if (DEBUG) {
            currentPosition = new Coordinates(gpsService.getLat(), gpsService.getLon());

            imageLabel = new JLabel(generateImage(lastPositions, currentPosition));
            this.add(imageLabel);

            TimerTask tt = new MapsTimerTask(this);

            System.out.println("start timer maps");

            Timer t = new Timer();
            t.scheduleAtFixedRate(tt, 5000, 5000);
        }

    }

    @Override
    public void handleEvent(Event event) {
        if (!DEBUG) {
            System.out.println("NEW EVENT AT MAPS PANEL");
            System.out.println("lat = " + event.getProperty(IGPSService.EVENT_GPS_DATA_LAT));
            System.out.println("lon = " + event.getProperty(IGPSService.EVENT_GPS_DATA_LONG));
            currentPosition = new Coordinates(Float.parseFloat((String) event.getProperty(IGPSService.EVENT_GPS_DATA_LAT)), Float.parseFloat((String) event.getProperty(IGPSService.EVENT_GPS_DATA_LONG)));
            System.out.println("Position: " + currentPosition.getLat() + ";" + currentPosition.getLon());
            if (lastPositions.size() == maxNumOfLastPositions) {
                lastPositions.remove(lastPositions.size() - 1);
            }

            lastPositions.add(0, currentPosition);
            if (imageLabel == null) {
                imageLabel = new JLabel(generateImage(lastPositions, currentPosition));
                this.add(imageLabel);
                this.setVisible(false);
                this.setVisible(true);
            }
        }

    }

    private ImageIcon generateImage(ArrayList<Coordinates> lastPositions, Coordinates currentPosition) {
        BufferedImage img = null;

        try {
            StringBuilder sb = new StringBuilder();
            // add icon
            sb.append("http://maps.googleapis.com/maps/api/staticmap?center=");
            sb.append(currentPosition.getLat() + "," + currentPosition.getLon());
            sb.append("&size=256x256&zoom=14&markers=color:red|label:C|");
            sb.append(currentPosition.getLat() + "," + currentPosition.getLon());
            sb.append("&sensor=true_or_false");

            // add route
            if (!lastPositions.isEmpty()) {
                sb.append("&path=color:0xff0000ff|weight:5");
                for (Coordinates c : lastPositions) {
                    sb.append("|" + c.getLat() + "," + c.getLon());
                }
            }

            System.out.println("lastPositions: " + lastPositions.size() + " run " + i + " URL Size = " + sb.length());
            i++;
            img = ImageIO.read(new URL(sb.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ImageIcon(img);
    }

    private class MapsTimerTask extends TimerTask {
        MapPanel panel;

        public MapsTimerTask(MapPanel panel) {
            this.panel = panel;
        }

        @Override
        public void run() {
            if (lastPositions.size() == maxNumOfLastPositions) {
                lastPositions.remove(lastPositions.size() - 1);
            }
            lastPositions.add(0, currentPosition);
            currentPosition = new Coordinates(currentPosition.getLat() + 0.0001f, currentPosition.getLon() + 0.0001f);
            panel.imageLabel.setIcon(generateImage(lastPositions, currentPosition));
        }

    }

    public JLabel getImageLabel() {
        return imageLabel;
    }

    public void setImageLabel(JLabel imageLabel) {
        this.imageLabel = imageLabel;
    }

    public IGPSService getGpsService() {
        return gpsService;
    }

    public void setGpsService(IGPSService gpsService) {
        this.gpsService = gpsService;
    }

    public ArrayList<Coordinates> getLastPositions() {
        return lastPositions;
    }

    public void setLastPositions(ArrayList<Coordinates> lastPositions) {
        this.lastPositions = lastPositions;
    }

    public Coordinates getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Coordinates currentPosition) {
        this.currentPosition = currentPosition;
    }

}
