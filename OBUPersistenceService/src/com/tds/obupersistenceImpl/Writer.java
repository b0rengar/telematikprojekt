package com.tds.obupersistenceImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.tds.gps.IGPSService;
import com.tds.obupersistence.IOBUPersistenceService;

/**
 * This class is responsible for writing events to a file.
 *
 * @author Andre Finsterbusch
 *
 */
public class Writer implements EventHandler, IOBUPersistenceService {
    /** The location of the file to write the events to. */
    String filename = DATA_DIR + FILE_GPS;
    // System.getProperty("user.home") + FILE;
    /** The file the event data is written to. */
    File file;
    FileWriter writer;

    /** Creates a new writer to write events to file. */
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

    /**
     * Writes the given event data to file.
     *
     * @param data The data to write.
     */
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

    /** Ends a writer session. */
    public void closeWriter() {
        try {
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
