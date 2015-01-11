package com.tds.obd;

import java.util.Calendar;

/**
 * Class to store a set of vehicle parameters which were collected via the OBD interface.
 *
 * @author Christian Bodler
 *
 */
public class OBDParameterSet {

    /** The speed of the vehicle. */
    private float speed;
    /** The fuel consumption in liter per hour. */
    private float fuelLevel;
    /** The performance of the engine. */
    private int engineRPM;
    /** The temperature of the engine. */
    private float engineTemperature;
    /** The temperature measured inside the vehicle. */
    private float throttlePosition;
    /** The temperature measured outside the vehicle. */
    private float engineLoad;
    /** The timestamp of the last measured value */
    private long timestamp;

    /** Initializes an empty parameter set with each value set to 0. */
    public OBDParameterSet() {
        speed = 1;
        fuelLevel = 2;
        engineRPM = 3;
        engineTemperature = 4;
        throttlePosition = 5;
        engineLoad = 6;
        timestamp = Calendar.getInstance().getTimeInMillis();
    }

    /**
     * Initializes an empty parameter set with each value set using the given parameter.
     *
     * @param speed The speed of the vehicle.
     * @param fuelConsumptionRate The fuel consumption in liter per hour.
     * @param engineRPM The performance of the engine.
     * @param engineTemperature The temperature of the engine.
     * @param carIndoorTemperature The temperature measured inside the vehicle.
     * @param carOutdoorTemperature The temperature measured outside the vehicle.
     */
    public OBDParameterSet(float speed, float fuelConsumptionRate, int engineRPM, float engineTemperature, float carIndoorTemperature, float carOutdoorTemperature) {
        this.speed = speed;
        this.fuelLevel = fuelConsumptionRate;
        this.engineRPM = engineRPM;
        this.engineTemperature = engineTemperature;
        this.throttlePosition = carIndoorTemperature;
        this.engineLoad = carOutdoorTemperature;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
    }

    /**
     * Returns the speed of the vehicle.
     *
     * @return The speed of the vehicle.
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Assigns a new value to the speed parameter.
     *
     * @param speed The new value for the speed parameter.
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Returns the value of the fuel consumption parameter.
     *
     * @return The value of the fuel consumption parameter.
     */
    public float getFuelLevel() {
        return fuelLevel;
    }

    /**
     * Assigns a new value to the fuel consumption parameter.
     *
     * @param fuelConsumptionRate The new value for the fuel consumption parameter.
     */
    public void setFuelLevel(float fuelConsumptionRate) {
        this.fuelLevel = fuelConsumptionRate;
    }

    /**
     * Returns the value of the engine performance parameter.
     *
     * @return The value of the engine performance parameter.
     */
    public int getEngineRPM() {
        return engineRPM;
    }

    /**
     * Assigns a new value to the engine performance parameter.
     *
     * @param engineRPM The new value for the engine performance parameter.
     */
    public void setEngineRPM(int engineRPM) {
        this.engineRPM = engineRPM;
    }

    /**
     * Returns the value of the engine temperature parameter.
     *
     * @return The value of the engine temperature parameter.
     */
    public float getEngineTemperature() {
        return engineTemperature;
    }

    /**
     * Assigns a new value to the engine temperature parameter.
     *
     * @param engineTemperature The new value to the engine temperature parameter.
     */
    public void setEngineTemperature(float engineTemperature) {
        this.engineTemperature = engineTemperature;
    }

    /**
     * Returns the value of the indoor temperature parameter.
     *
     * @return The value of the indoor temperature parameter.
     */
    public float getThrottlePosition() {
        return throttlePosition;
    }

    /**
     * Assigns a new value to the indoor temperature parameter.
     *
     * @param carIndoorTemperature The new value to the indoor temperature parameter.
     */
    public void setThrottlePosition(float carIndoorTemperature) {
        this.throttlePosition = carIndoorTemperature;
    }

    /**
     * Returns the value of the outdoor temperature parameter.
     *
     * @return The value of the outdoor temperature parameter.
     */
    public float getEngineLoad() {
        return engineLoad;
    }

    /**
     * Assigns a new value to the outdoor temperature parameter.
     *
     * @param carOutdoorTemperature The new value to the outdoor temperature parameter.
     */
    public void setEngineLoad(float carOutdoorTemperature) {

        this.engineLoad = carOutdoorTemperature;
    }

    /**
     * Assigns a new value to the timestamp parameter.
     *
     * @param engineLoad The new value to the outdoor temperature parameter.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the value of the timestamp parameter.
     *
     * @return The value of the timestamp parameter.
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
