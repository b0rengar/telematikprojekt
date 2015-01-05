/**
 *
 */
package com.tds.gps;

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
    public static String Event_GPS = "GPS/Data";
    public static String Event_GPS_TOPIC = "gpsService/Data";

    String getGpsPosition();

    void openSP();
}
