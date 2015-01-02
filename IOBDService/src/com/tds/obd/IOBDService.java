package com.tds.obd;

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

    public void openSP();

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
