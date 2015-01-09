package com.tds.obupersistenceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.tds.gps.IGPSService;
import com.tds.obupersistence.IOBUPersistenceService;

public class Writer implements EventHandler, IOBUPersistenceService {

    @Override
    public void handleEvent(Event event) {

        if (event.getTopic() == IGPSService.Event_GPS_TOPIC) {
            Object lat = event.getProperty(IGPSService.Event_GPS_DATA_LAT);
            Object lng = event.getProperty(IGPSService.Event_GPS_DATA_LONG);

            StringBuilder gps_string = new StringBuilder("[");
            gps_string.append(lat.toString());
            gps_string.append(" , ");
            gps_string.append(lng.toString());
            gps_string.append("]");
            System.err.println("WRITER:" + gps_string.toString());
            write(gps_string.toString());
        }

    }

    private void write(String data) {
        try {

            String filename = FILE;

            File file = new File(filename);

            FileWriter writer = new FileWriter(file);
            if (file.exists()) {
                writer.append(data);
            } else {
                writer.write(data);
            }

            writer.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
