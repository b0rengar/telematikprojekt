/**
 *
 */
package com.tds.persistence;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author fibu
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

    public ArrayList getItems(String table_name);

    public void setEvent(int evnetid, String filename, long timestamp, String gps);

    public void setData(String tableName, Object data) throws Exception;

    public void setStreamData(String filename, Calendar timestamp, String gps);

    public void setFileInfo(int stream_type, String filename, Calendar timestamp_start, Calendar timestamp_end, String gps);

    public void setTelemetrie(Calendar timestamp, String gps, String acceleration, int vehicle_velocity, int fuel_consumption, int fuel, int engine_speed, float engine_temperature, float steering_angle, boolean ABS, boolean ESP, float internal_temperature, float ambient_temperature, int luminosity);

}
