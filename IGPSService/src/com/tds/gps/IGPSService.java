/**
 *
 */
package com.tds.gps;

import org.osgi.service.event.EventAdmin;

/**
 * <b>IGPSService <br />
 * com.tds.gps <br />
 * IGPSService <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 21:29:30
 *
 */
public interface IGPSService {

    public static String EVENT_GPS_DATA_TIMESTAMP = "GPS/Data/ts";
    public static String EVENT_GPS_DATA_LONG = "GPS/Data/lat";
    public static String EVENT_GPS_DATA_LAT = "GPS/Data/long";
    public static String EVENT_GPS_TOPIC = "gpsservice/eventsender/GPS";

// String getGpsPosition();

    void bindEventAdmin(EventAdmin eventAdmin);

    void unbindEventAdmin();

    void openSP();

    void closeSP();

    float getLat();

    float getLon();
}
