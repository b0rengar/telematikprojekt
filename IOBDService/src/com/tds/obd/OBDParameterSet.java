package com.tds.obd;

public class OBDParameterSet {

    private float speed;
    private float fuelConsumptionRate;
    private int engineRPM;
    private float engineTemperature;
    private float carIndoorTemperature;
    private float carOutdoorTemperature;

    public OBDParameterSet() {

    }

    public OBDParameterSet(float speed, float fuelConsumptionRate, int engineRPM, float engineTemperature, float carIndoorTemperature, float carOutdoorTemperature) {
        this.speed = speed;
        this.fuelConsumptionRate = fuelConsumptionRate;
        this.engineRPM = engineRPM;
        this.engineTemperature = engineTemperature;
        this.carIndoorTemperature = carIndoorTemperature;
        this.carOutdoorTemperature = carOutdoorTemperature;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getFuelConsumptionRate() {
        return fuelConsumptionRate;
    }

    public void setFuelConsumptionRate(float fuelConsumptionRate) {
        this.fuelConsumptionRate = fuelConsumptionRate;
    }

    public int getEngineRPM() {
        return engineRPM;
    }

    public void setEngineRPM(int engineRPM) {
        this.engineRPM = engineRPM;
    }

    public float getEngineTemperature() {
        return engineTemperature;
    }

    public void setEngineTemperature(float engineTemperature) {
        this.engineTemperature = engineTemperature;
    }

    public float getCarIndoorTemperature() {
        return carIndoorTemperature;
    }

    public void setCarIndoorTemperature(float carIndoorTemperature) {
        this.carIndoorTemperature = carIndoorTemperature;
    }

    public float getCarOutdoorTemperature() {
        return carOutdoorTemperature;
    }

    public void setCarOutdoorTemperature(float carOutdoorTemperature) {
        this.carOutdoorTemperature = carOutdoorTemperature;
    }

}
