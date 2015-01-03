/**
 *
 */
package com.tds.obdImpl;

import jssc.SerialPort;
import pt.lighthouselabs.obd.commands.SpeedObdCommand;
import pt.lighthouselabs.obd.commands.engine.EngineRPMObdCommand;
import pt.lighthouselabs.obd.commands.fuel.FuelConsumptionRateObdCommand;
import pt.lighthouselabs.obd.commands.protocol.SelectProtocolObdCommand;
import pt.lighthouselabs.obd.commands.temperature.AirIntakeTemperatureObdCommand;
import pt.lighthouselabs.obd.commands.temperature.AmbientAirTemperatureObdCommand;
import pt.lighthouselabs.obd.commands.temperature.EngineCoolantTemperatureObdCommand;
import pt.lighthouselabs.obd.enums.ObdProtocols;

import com.tds.obd.IOBDService;

/**
 * <b>OBDService <br />
 * com.tds.obd <br />
 * OBDService <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 20:05:47
 *
 */
public class OBDService implements IOBDService {
    private final static boolean DEBUG = true;

    // Serial Port Settings
    static SerialPort serialPort;
    String portName = "COM4";
    int baudrate = 9600;
    int dataBits = SerialPort.DATABITS_8;
    int stopBits = SerialPort.STOPBITS_1;
    int parity = SerialPort.PARITY_NONE;

    // OBD Objects
    private SpeedObdCommand speedCommand = null;
    private FuelConsumptionRateObdCommand fuelConsumptionRateCommand = null;
    private EngineRPMObdCommand engineRPMCommand = null;
    private EngineCoolantTemperatureObdCommand engineTemperatureCommand = null;
    private AmbientAirTemperatureObdCommand carIndoorTemperatureCommand = null;
    private AirIntakeTemperatureObdCommand carOutdoorTemperatureCommand = null;

    @Override
    public void openSP() {
        serialPort = new SerialPort(portName);

        try {

            System.out.println("Port opened: " + serialPort.openPort());
            System.out.println("Params setted: " + serialPort.setParams(baudrate, dataBits, stopBits, parity));
            int mask = SerialPort.MASK_RXCHAR;// Prepare mask
            serialPort.setEventsMask(mask);// Set mask
// serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_IN | SerialPort.FLOWCONTROL_XONXOFF_OUT);
            initializeODB2();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initializeODB2() throws Exception {
        System.out.println("Initialize ODB2");
        new SelectProtocolObdCommand(ObdProtocols.AUTO).run(serialPort);
    }

    @Override
    public float getSpeed() {
        if (!DEBUG) {
            if (speedCommand == null) {
                speedCommand = new SpeedObdCommand();
            }
            try {
                speedCommand.run(serialPort);
                return speedCommand.getImperialSpeed();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return 23.0f;
        }
        return 1;
    }

    @Override
    public float getFuelConsumptionRate() {
        if (!DEBUG) {
            if (fuelConsumptionRateCommand == null) {
                fuelConsumptionRateCommand = new FuelConsumptionRateObdCommand();
            }
            try {
                fuelConsumptionRateCommand.run(serialPort);
                return fuelConsumptionRateCommand.getLitersPerHour();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return 5.9f;
        }
        return 2;
    }

    @Override
    public int getEngineRPM() {
        if (!DEBUG) {
            if (engineRPMCommand == null) {
                engineRPMCommand = new EngineRPMObdCommand();
            }
            try {
                engineRPMCommand.run(serialPort);
                return engineRPMCommand.getRPM();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return 1500;
        }
        return 3;
    }

    @Override
    public float getEngineTemperature() {
        if (!DEBUG) {
            if (engineTemperatureCommand == null) {
                engineTemperatureCommand = new EngineCoolantTemperatureObdCommand();
            }
            try {
                engineTemperatureCommand.run(serialPort);
                return engineTemperatureCommand.getTemperature();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return 90.0f;
        }
        return 3;
    }

    @Override
    public float getCarIndoorTemperature() {
        if (!DEBUG) {
            if (carIndoorTemperatureCommand == null) {
                carIndoorTemperatureCommand = new AmbientAirTemperatureObdCommand();
            }
            try {
                carIndoorTemperatureCommand.run(serialPort);
                return carIndoorTemperatureCommand.getTemperature();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return 20.0f;
        }
        return 4;
    }

    @Override
    public float getCarOutdoorTemperature() {
        if (!DEBUG) {
            if (carOutdoorTemperatureCommand == null) {
                carOutdoorTemperatureCommand = new AirIntakeTemperatureObdCommand();
            }
            try {
                carOutdoorTemperatureCommand.run(serialPort);
                return carOutdoorTemperatureCommand.getTemperature();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return 36.0f;
        }
        return 5;
    }

// @Override
// public double getSpeed() {
// // TODO Auto-generated method stub
// double speed = Math.random() * 180;
//
// System.out.println("Service called: Speed = " + speed);
//
// return speed;
// }

}
