package com.tds.obd;

import org.osgi.service.event.EventAdmin;

/**
 *
 * <b>IOBDService <br />
 * com.tds.obd <br />
 * IOBDService <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 20:05:55
 *
 */
public interface IOBDService {
    public static String EVENT_OBD_DATA_SPEED = "OBD/data/speed";
    public static String EVENT_OBD_DATA_CONSUMPTION = "OBD/data/consumption";
    public static String EVENT_OBD_DATA_RPM = "OBD/data/rpm";
    public static String EVENT_OBD_DATA_TEMPERATURE_ENGINE = "OBD/data/enginetemperature";
    public static String EVENT_OBD_DATA_TEMPERATURE_INDOOR = "OBD/data/indoortemperature";
    public static String EVENT_OBD_DATA_TEMPERATURE_OUTDOOR = "OBD/data/outdoortemperature";

    public static String TYPE_SPEED = "010D";
    public static String TYPE_CONSUMPTION = "015E";
    public static String TYPE_RPM = "010C";
    public static String TYPE_TEMPERATURE_ENGINE = "0105";
    public static String TYPE_TEMPERATURE_INDOOR = "0146";
    public static String TYPE_TEMPERATURE_OUTDOOR = "010F";

    public static String TYPE_SPEED_RESULT = "410D";
    public static String TYPE_CONSUMPTION_RESULT = "415E";
    public static String TYPE_RPM_RESULT = "410C";
    public static String TYPE_TEMPERATURE_ENGINE_RESULT = "4105";
    public static String TYPE_TEMPERATURE_INDOOR_RESULT = "4146";
    public static String TYPE_TEMPERATURE_OUTDOOR_RESULT = "410F";

    public static String EVENT_OBD_TOPIC = "obdservice/eventsender/OBD";

    void bindEventAdmin(EventAdmin eventAdmin);

    void unbindEventAdmin();

    public void openSP();

    public OBDParameterSet getPamrameterSet();

    public float getSpeed();

    public float getFuelConsumptionRate();

    public int getEngineRPM();

    public float getEngineTemperature();

// public float getSteerAngle();
// public boolean getABSActive();
// public boolean getESPActive();
    public float getCarIndoorTemperature();

    public float getCarOutdoorTemperature();
// public float getOutdoorBrightness();

}
