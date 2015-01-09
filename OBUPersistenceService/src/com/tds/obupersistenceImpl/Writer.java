package com.tds.obupersistenceImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.tds.gps.IGPSService;
import com.tds.obupersistence.IOBUPersistenceService;

public class Writer implements EventHandler, IOBUPersistenceService {
    String filename = DATA_DIR + FILE_GPS;
    // System.getProperty("user.home") + FILE;
    File file;
    FileWriter writer;

    Writer() {
        file = new File(filename);
        try {
            writer = new FileWriter(file, true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getTopic() == IGPSService.EVENT_GPS_TOPIC) {
            Object lat = event.getProperty(IGPSService.EVENT_GPS_DATA_LAT);
            Object lng = event.getProperty(IGPSService.EVENT_GPS_DATA_LONG);
            Object ts = event.getProperty(IGPSService.EVENT_GPS_DATA_TIMESTAMP);

// System.out.println("GPS-Event: " + ts + ";" + lat + ";" + lng);

            StringBuilder gps_string = new StringBuilder(ts.toString());
            gps_string.append(";");
            gps_string.append(lat.toString());
            gps_string.append(";");
            gps_string.append(lng.toString());
            gps_string.append('\n');
// System.err.println("WRITER:" + gps_string.toString() + " at:" + ts);
            write(gps_string.toString());
        }

    }

    private void write(String data) {
        try {
            if (file.exists()) {
                writer.append(data);
            } else {
                writer.write(data);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void closeWriter() {
        try {
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
