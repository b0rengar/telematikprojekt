package com.tds.obupersistenceImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.tds.gps.IGPSService;
import com.tds.inertial.IInertialMeasurementService;
import com.tds.obd.IOBDService;
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

    /** The file the event data is written to. */
    File file_gps;
    File file_acc;
    File file_obd;

    /** The writer for specific event data . */
    FileWriter writer_gps;
    FileWriter writer_acc;
    FileWriter writer_obd;

    /**
     * Creates new specific writer to write events to files.
     *
     **/
    Writer() {
        try {
            file_gps = new File(DATA_DIR + FILE_GPS);
            file_acc = new File(DATA_DIR + FILE_ACC);
            file_obd = new File(DATA_DIR + FILE_OBD);
            writer_gps = new FileWriter(file_gps, true);
            writer_acc = new FileWriter(file_acc, true);
            writer_obd = new FileWriter(file_obd, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleEvent(Event event) {
        System.out.println("OBUOersistense: " + event.getTopic());
        if (event.getTopic() == IGPSService.EVENT_GPS_TOPIC) {
            Object lat = event.getProperty(IGPSService.EVENT_GPS_DATA_LAT);
            Object lng = event.getProperty(IGPSService.EVENT_GPS_DATA_LONG);
            Object ts = event.getProperty(IGPSService.EVENT_GPS_DATA_TIMESTAMP);

            StringBuilder gps_string = new StringBuilder(ts.toString());
            gps_string.append(";");
            gps_string.append(lat.toString());
            gps_string.append(";");
            gps_string.append(lng.toString());
            gps_string.append('\n');
            write(writer_gps, file_gps, gps_string.toString());
        }
        if (event.getTopic() == IInertialMeasurementService.EVENT_ACC_TOPIC) {
            Object x = event.getProperty(IInertialMeasurementService.EVENT_ACC_DATA_X);
            Object y = event.getProperty(IInertialMeasurementService.EVENT_ACC_DATA_Y);
            Object z = event.getProperty(IInertialMeasurementService.EVENT_ACC_DATA_Z);
            Object ts = event.getProperty(IInertialMeasurementService.EVENT_ACC_DATA_TIMESTAMP);

            StringBuilder data_string = new StringBuilder(ts.toString());
            data_string.append(";");
            data_string.append(x.toString());
            data_string.append(";");
            data_string.append(y.toString());
            data_string.append(";");
            data_string.append(z.toString());
            data_string.append('\n');
            write(writer_acc, file_acc, data_string.toString());
        }
        if (event.getTopic() == IOBDService.EVENT_OBD_TOPIC) {
            try {

                // TODO timestamp
                Object ts = event.getProperty(IOBDService.EVENT_OBD_DATA_TIMESTAMP);
                String speed = (String) event.getProperty(IOBDService.EVENT_OBD_DATA_SPEED);
                String rpm = (String) event.getProperty(IOBDService.EVENT_OBD_DATA_RPM);
                String fuel_level = (String) event.getProperty(IOBDService.eVENT_OBD_DATA_FUEL_LEVEL);
                String temperature_engine = (String) event.getProperty(IOBDService.EVENT_OBD_DATA_TEMPERATURE_ENGINE);
                String throttle_position = (String) event.getProperty(IOBDService.EVENT_OBD_DATA_THROTTLE_POSITION);
                String engine_load = (String) event.getProperty(IOBDService.EVENT_OBD_DATA_ENGINE_LOAD);

                // TODO timestamp
                StringBuilder data_string = new StringBuilder(ts.toString());
                data_string.append(";");
                data_string.append(speed);
                data_string.append(";");
                data_string.append(rpm);
                data_string.append(";");
                data_string.append(fuel_level);
                data_string.append(";");
                data_string.append(temperature_engine);
                data_string.append(";");
                data_string.append(throttle_position);
                data_string.append(";");
                data_string.append(engine_load);
                data_string.append('\n');

                write(writer_obd, file_obd, data_string.toString());
            } catch (Exception e) {
                System.err.println("");
            }
        }
    }

    /**
     * Writes the given event data to file.
     *
     * @param writer specific writer for the data
     * @param file workaround, cause writer has no File attribute
     * @param data The data to write.
     */
    private void write(FileWriter writer, File file, String data) {
        try {
            if (file.exists()) {
                writer.append(data);
            } else {
                writer.write(data);
            }
        } catch (IOException e) {
            System.err.println("Persistence Error, cant write to" + file.getName());
            e.printStackTrace();
        }
    }

    /** Ends a writer session. */
    public void closeWriter() {
        try {
            writer_acc.close();
            writer_gps.close();
            writer_obd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
