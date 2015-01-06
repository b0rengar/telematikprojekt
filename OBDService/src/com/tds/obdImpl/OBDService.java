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
    private static SerialPort serialPort;
    private static SerialInputStream input;
    private static SerialOutputStream output;
    private String portName = "COM4";
    private int baudrate = 9600;
    private int dataBits = SerialPort.DATABITS_8;
    private int stopBits = SerialPort.STOPBITS_1;
    private int parity = SerialPort.PARITY_NONE;

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
            // serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_IN |
            // SerialPort.FLOWCONTROL_XONXOFF_OUT);

            input = new SerialInputStream(serialPort);
            output = new SerialOutputStream(serialPort);
            initializeODB2();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initializeODB2() throws Exception {
        System.out.println("Initialize ODB2");
        new SelectProtocolObdCommand(ObdProtocols.AUTO).run(input, output);
    }

    @Override
    public float getSpeed() {
        if (!DEBUG) {
            if (speedCommand == null) {
                speedCommand = new SpeedObdCommand();
            }
            try {
                speedCommand.run(input, output);
                return speedCommand.getImperialSpeed();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return (float) (70 * Math.random());
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
                fuelConsumptionRateCommand.run(input, output);
                return fuelConsumptionRateCommand.getLitersPerHour();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return (float) (12 * Math.random());
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
                engineRPMCommand.run(input, output);
                return engineRPMCommand.getRPM();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return (int) (4000 * Math.random());
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
                engineTemperatureCommand.run(input, output);
                return engineTemperatureCommand.getTemperature();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return (float) (120 * Math.random());
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
                carIndoorTemperatureCommand.run(input, output);
                return carIndoorTemperatureCommand.getTemperature();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return (float) (80 * Math.random());
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
                carOutdoorTemperatureCommand.run(input, output);
                return carOutdoorTemperatureCommand.getTemperature();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return (float) (40 * Math.random());
        }
        return 5;
    }

    // @Override
    // public double getSpeed() {
    // double speed = Math.random() * 180;
    //
    // System.out.println("Service called: Speed = " + speed);
    //
    // return speed;
    // }

}
