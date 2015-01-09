/**
 *
 */
package com.tds.persistence;

import java.util.Calendar;
import java.util.List;

/**
 * Interface to define the way data is persisted in the application.
 *
 * @author Andre Finsterbusch
 *
 */
public interface IPersistenceService {
    public static String TABLE_EVENT = "EVENT";
    public static String TABLE_TELEMETRIE = "TELEMETRIE";
    public static String TABLE_FILES = "STREAM_FILES";
    public static String TABLE_STREAM_DATA = "STREAM_DATA";

    public static int FILE_TYPE_SNAPSHOP = 0;
    public static int FILE_TYPE_FRONT_STREAM = 1;
    public static int FILE_TYPE_DRIVER_STREAM = 2;

// public static String TABLE_SNAPSHOT = "SNAPSHOT";

    /**
     * Returns a list of TDS events stored in the application's database.
     *
     * @return A list of TDS events.
     */
    public List<TdsEvent> getTdsEventsFromDB();

    /**
     * Returns the contents of the table by the given name.
     *
     * @param table_name The name of the table to get the contents of.
     * @return The contents of the table by the given name.
     */
    public List<Object> getItems(String table_name);

    /**
     * Stores a new TDS event using the given parameters.
     *
     * @param eventId The event type.
     * @param filename A path to an image file if a picture was taken at the time the event was detected.
     * @param timestamp A timestamp representing the time the event was detected.
     * @param gps GPS coordinates describing the location where the event was detected.
     */
    public void setEvent(int eventId, String filename, long timestamp, String gps);

    /**
     * Stores the given data in the given table.
     *
     * @param tableName The table to store the given data in.
     * @param data The data to store in the given table.
     * @throws Exception If the data could not be stored in the database.
     */
    public void setData(String tableName, Object data) throws Exception;

    public void setStreamData(String filename, Calendar timestamp, String gps);

    public void setFileInfo(int stream_type, String filename, Calendar timestamp_start, Calendar timestamp_end, String gps);

    public void setTelemetrie(Calendar timestamp, String gps, String acceleration, int vehicle_velocity, int fuel_consumption, int fuel, int engine_speed, float engine_temperature, float steering_angle, boolean ABS, boolean ESP, float internal_temperature, float ambient_temperature, int luminosity);

}
