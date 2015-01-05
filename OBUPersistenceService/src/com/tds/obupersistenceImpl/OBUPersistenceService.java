/**
 *
 */
package com.tds.obupersistenceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.tds.obupersistence.IOBUPersistenceService;

/**
 * @author Phillip Kopprasch
 *
 */
public class OBUPersistenceService implements IOBUPersistenceService, EventHandler {

    public OBUPersistenceService() {
    }

    @Override
    public void handleEvent(Event event) {
        // TODO Auto-generated method stub
        System.out.println(event.getProperty("gpsService/Data"));

    }

    private void write(String data, int type) {
        try {

            String filename = null;
            if (type == TYPE_GPS) {
                filename = FILE_GPS;
            } else if (type == TYPE_ODB) {
                filename = FILE_ODB;
            } else {
                return;
            }

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
