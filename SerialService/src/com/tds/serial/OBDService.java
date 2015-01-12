package com.tds.serial;

import java.util.Calendar;
import java.util.Dictionary;
import java.util.Hashtable;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.tds.obd.IOBDService;
import com.tds.obd.OBDParameterSet;

/**
 *
 * Service to provide ODB data
 *
 * @author chris
 *
 */
public class OBDService implements IOBDService {
    private static EventAdmin eventAdmin;
    private static OBDParser obdParser = new OBDParser();
    // parameter set to hold data of last request
    private static OBDParameterSet parameterSet = new OBDParameterSet();

    // Serial Port Settings
    private static SerialPort serialPort;

    private static StringBuilder tmp = new StringBuilder();
    // settings of the serial port, do not have to be provied on linux machines
    private String portName = "/dev/rfcomm0";

<<<<<<< HEAD
=======
    private String portName = "/dev/rfcomm0";

>>>>>>> branch 'master' of https://github.com/b0rengar/telematikprojekt.git
// private int baudrate = 9600;
// private int dataBits = SerialPort.DATABITS_8;
// private int stopBits = SerialPort.STOPBITS_1;
// private int parity = SerialPort.PARITY_NONE;

    /**
     * get {@link EventAdmin} to fire events
     */
    @Override
    public void bindEventAdmin(EventAdmin eventAdmin) {
        OBDService.eventAdmin = eventAdmin;
    }

    /**
     * unbind the {@link EventAdmin}
     */
    @Override
    public void unbindEventAdmin() {
        OBDService.eventAdmin = null;
    }

    /**
     * open the {@link SerialPort} to be able to request data
     */
    @Override
    public void openSP() {
        serialPort = new SerialPort(portName);

        try {

<<<<<<< HEAD
            System.out.println("OBD Port opened: " + serialPort.openPort());
=======
            System.out.println("Port opened: " + serialPort.openPort());
>>>>>>> branch 'master' of https://github.com/b0rengar/telematikprojekt.git
// System.out.println("Params setted: " + serialPort.setParams(baudrate, dataBits, stopBits, parity));
            int mask = SerialPort.MASK_RXCHAR;// Prepare mask
            serialPort.setEventsMask(mask);// Set mask
            serialPort.addEventListener(new ODBSerialPortReader());
            // serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_IN |
            // SerialPort.FLOWCONTROL_XONXOFF_OUT);

            initializeODB2();

            runOBDRequests();
        } catch (Exception e) {
            System.out.println("port could not be opened");
            e.printStackTrace();
        }
    }

    /**
     * initialze the connection to the OBD dongle
     *
     * @throws Exception
     */
    private synchronized void initializeODB2() throws Exception {
        // turn off line feeds
        serialPort.writeString("AT L0" + "\r");
        wait(1000);
        // turn off echo
        serialPort.writeString("AT E0" + "\r");
        // get version information of ELM
        System.out.println("1");
        wait(1000);
        // get information about device
        serialPort.writeString("AT I" + "\r");
        System.out.println("2");
        wait(1000);
        // return results without spaces
        serialPort.writeString("AT S0" + "\r");
        // set protocol as auto
        System.out.println("3");
        wait(1000);
        serialPort.writeString("AT SP 0" + "\r");
        wait(1000);

    }

    /**
     * run OBDRequest infinite loop
     *
     * @throws Exception
     */
    private void runOBDRequests() throws Exception {
        // start loop by getting the speed
        serialPort.writeString(IOBDService.TYPE_SPEED + "\r");
    }

    private static void sendEvent(OBDParameterSet parameterSet) {
        System.out.println("=================================");
        System.out.println("Speed" + parameterSet.getSpeed());
        System.out.println("EngineRPM" + parameterSet.getEngineRPM());
        System.out.println("FuelLevel" + parameterSet.getFuelLevel());
        System.out.println("EngineT" + parameterSet.getEngineTemperature());
        System.out.println("ThrottlePos" + parameterSet.getThrottlePosition());
        System.out.println("EngineLoad" + parameterSet.getEngineLoad());

        // send OBBParameterSet with Event and corresponding properties
        Dictionary<String, String> eventProps = new Hashtable<String, String>();
        eventProps.put(EVENT_OBD_DATA_TIMESTAMP, Long.toString(parameterSet.getTimestamp()));
        eventProps.put(EVENT_OBD_DATA_SPEED, Float.toString(parameterSet.getSpeed()));
        eventProps.put(EVENT_OBD_DATA_RPM, Integer.toString(parameterSet.getEngineRPM()));
        eventProps.put(eVENT_OBD_DATA_FUEL_LEVEL, Float.toString(parameterSet.getFuelLevel()));
        eventProps.put(EVENT_OBD_DATA_TEMPERATURE_ENGINE, Float.toString(parameterSet.getEngineTemperature()));
        eventProps.put(EVENT_OBD_DATA_THROTTLE_POSITION, Float.toString(parameterSet.getThrottlePosition()));
        eventProps.put(EVENT_OBD_DATA_ENGINE_LOAD, Float.toString(parameterSet.getEngineLoad()));
        Event osgiEvent = new Event(EVENT_OBD_TOPIC, eventProps);

        // "sendEvent()" synchron "postEvent()" asynchron:
        eventAdmin.sendEvent(osgiEvent);
    }

    @Override
    public OBDParameterSet getPamrameterSet() {
        OBDParameterSet set = new OBDParameterSet(9, 8, 7, 6, 5, 4);
        return set;
    }

    @Override
    public float getSpeed() {
        return 1;
    }

    @Override
    public float getFuelConsumptionRate() {
        return 2;
    }

    @Override
    public int getEngineRPM() {
        return 3;
    }

    @Override
    public float getEngineTemperature() {
        return 4;
    }

    @Override
    public float getCarIndoorTemperature() {
        return 5;
    }

    @Override
    public float getCarOutdoorTemperature() {
        return 6;
    }

    static class ODBSerialPortReader implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR()) {// If data is available
                if (event.getEventValue() > 0) {
                    try {
                        String response = serialPort.readString(); // read string
                        response.replaceAll("\r", "");  // and replace returns
// System.out.println("response = " + response);
                        if (response.contains(">")) {
                            tmp.append(response.substring(0, response.indexOf(">"))); //
                            System.out.println("Result = " + tmp);
                            if (tmp.toString().contains(TYPE_RPM_RESULT)) {
                                parameterSet.setEngineRPM(obdParser.getInt(tmp.toString(), TYPE_RPM_RESULT));
// serialPort.writeString(IOBDService.TYPE_CONSUMPTION + "\r");
                                serialPort.writeString(IOBDService.TYPE_FUEL_LEVEL + "\r");
                            }
                            if (tmp.toString().contains(TYPE_SPEED_RESULT)) {
                                parameterSet.setSpeed(obdParser.getFloat(tmp.toString(), TYPE_SPEED_RESULT));
                                serialPort.writeString(IOBDService.TYPE_RPM + "\r");
                            }
                            if (tmp.toString().contains(TYPE_FUEL_LEVEL_RESULT)) {
                                parameterSet.setFuelLevel(obdParser.getFloat(tmp.toString(), TYPE_FUEL_LEVEL_RESULT));
                                serialPort.writeString(IOBDService.TYPE_TEMPERATURE_ENGINE + "\r");
                            }
                            if (tmp.toString().contains(TYPE_TEMPERATURE_ENGINE_RESULT)) {
                                parameterSet.setEngineTemperature(obdParser.getFloat(tmp.toString(), TYPE_TEMPERATURE_ENGINE_RESULT));
                                serialPort.writeString(IOBDService.TYPE_THROTTLE_POSITION + "\r");
                            }
                            if (tmp.toString().contains(TYPE_THROTTLE_POSITION_RESULT)) {
                                parameterSet.setThrottlePosition(obdParser.getFloat(tmp.toString(), TYPE_THROTTLE_POSITION_RESULT));
                                serialPort.writeString(IOBDService.TYPE_ENGINE_LOAD + "\r");
                            }
                            if (tmp.toString().contains(TYPE_ENGINE_LOAD_RESULT)) {
                                parameterSet.setEngineLoad(obdParser.getFloat(tmp.toString(), TYPE_ENGINE_LOAD_RESULT));
                                parameterSet.setTimestamp(Calendar.getInstance().getTimeInMillis());
                                sendEvent(parameterSet);
                                serialPort.writeString(IOBDService.TYPE_SPEED + "\r");
                            }
                            if (tmp.toString().contains("NO DATA")) {
                                wait(1000);
                                serialPort.writeString(IOBDService.TYPE_SPEED + "\r");
                            }
                            tmp.setLength(0);
// tmp.append(response.substring(response.indexOf(">") + 1));
                        } else {
                            tmp.append(response);
                        }

                    } catch (SerialPortException ex) {
                        ex.printStackTrace();
                        // TODO LOGGER
// System.out.println(ex);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO: handle exception
// System.out.println("Cant create Event: " + e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void closeSP() {
        try {
            System.out.println("GPS Port closed: " + serialPort.closePort());
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

}
