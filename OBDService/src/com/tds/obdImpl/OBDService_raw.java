package com.tds.obdImpl;

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

public class OBDService_raw implements IOBDService {
    private static EventAdmin eventAdmin;
    private static OBDParser obdParser = new OBDParser();

    private static OBDParameterSet parameterSet = new OBDParameterSet();

    // Serial Port Settings
    private static SerialPort serialPort;

    private static StringBuilder tmp = new StringBuilder();

    private String portName = "COM4";
    private int baudrate = 9600;
    private int dataBits = SerialPort.DATABITS_8;
    private int stopBits = SerialPort.STOPBITS_1;
    private int parity = SerialPort.PARITY_NONE;

    @Override
    public void bindEventAdmin(EventAdmin eventAdmin) {
        OBDService_raw.eventAdmin = eventAdmin;
    }

    @Override
    public void unbindEventAdmin() {
        OBDService_raw.eventAdmin = null;
    }

    @Override
    public void openSP() {
        serialPort = new SerialPort(portName);

        try {

            System.out.println("Port opened: " + serialPort.openPort());
            System.out.println("Params setted: " + serialPort.setParams(baudrate, dataBits, stopBits, parity));
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

    private synchronized void initializeODB2() throws Exception {
        serialPort.writeString("AT L0" + "\r");
        wait(1000);
        serialPort.writeString("AT E0" + "\r");
        // get version information of ELM
        System.out.println("1");
        wait(1000);
        serialPort.writeString("AT I" + "\r");
        // no spaces
        System.out.println("2");
        wait(1000);
        serialPort.writeString("AT S0" + "\r");
        // set protocol as auto
        System.out.println("3");
        wait(1000);
        serialPort.writeString("AT SP 0" + "\r");
        wait(1000);

    }

    private void runOBDRequests() throws Exception {
// while (true) {
// System.out.println("new while");
        // get speed
        serialPort.writeString(IOBDService.TYPE_SPEED + "\r");
// // get fuel consumption
// serialPort.writeString(IOBDService.TYPE_CONSUMPTION + "\r");
// // RPM
// serialPort.writeString(IOBDService.TYPE_RPM + "\r");
// // engine temperature
// serialPort.writeString(IOBDService.TYPE_TEMPERATURE_ENGINE + "\r");
// // indoor temperature
// serialPort.writeString(IOBDService.TYPE_TEMPERATURE_INDOOR + "\r");
// // outdoor temperature
        System.out.println("4");
// serialPort.writeString(IOBDService.TYPE_TEMPERATURE_OUTDOOR + "\r");
        System.out.println("5");
// wait(1000);
// }
    }

    private static void sendEvent(OBDParameterSet parameterSet) {
        System.out.println("=================================");
        System.out.println("Speed" + parameterSet.getSpeed());
        System.out.println("RPM" + parameterSet.getEngineRPM());
        System.out.println("Consum" + parameterSet.getFuelConsumptionRate());
        System.out.println("EngineT" + parameterSet.getEngineTemperature());
        System.out.println("IndoorT" + parameterSet.getCarIndoorTemperature());
        System.out.println("OutdoorT" + parameterSet.getCarOutdoorTemperature());

        Dictionary<String, String> eventProps = new Hashtable<String, String>();
        eventProps.put(EVENT_OBD_DATA_SPEED, Float.toString(parameterSet.getSpeed()));
        eventProps.put(EVENT_OBD_DATA_RPM, Integer.toString(parameterSet.getEngineRPM()));
        eventProps.put(EVENT_OBD_DATA_CONSUMPTION, Float.toString(parameterSet.getFuelConsumptionRate()));
        eventProps.put(EVENT_OBD_DATA_TEMPERATURE_ENGINE, Float.toString(parameterSet.getEngineTemperature()));
        eventProps.put(EVENT_OBD_DATA_TEMPERATURE_INDOOR, Float.toString(parameterSet.getCarIndoorTemperature()));
        eventProps.put(EVENT_OBD_DATA_TEMPERATURE_OUTDOOR, Float.toString(parameterSet.getCarOutdoorTemperature()));
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
                        String response = serialPort.readString();
                        response.replaceAll("\r", "");
// System.out.println("response = " + response);
                        if (response.contains(">")) {
                            tmp.append(response.substring(0, response.indexOf(">")));
                            System.out.println("Result = " + tmp);
                            if (tmp.toString().contains(TYPE_RPM_RESULT)) {
                                parameterSet.setEngineRPM(obdParser.getInt(tmp.toString(), TYPE_RPM_RESULT));
// serialPort.writeString(IOBDService.TYPE_CONSUMPTION + "\r");
                                serialPort.writeString(IOBDService.TYPE_TEMPERATURE_ENGINE + "\r");
                            }
                            if (tmp.toString().contains(TYPE_SPEED_RESULT)) {
                                parameterSet.setSpeed(obdParser.getFloat(tmp.toString(), TYPE_SPEED_RESULT));
                                serialPort.writeString(IOBDService.TYPE_RPM + "\r");
                            }
                            if (tmp.toString().contains(TYPE_CONSUMPTION_RESULT)) {
                                parameterSet.setFuelConsumptionRate(obdParser.getFloat(tmp.toString(), TYPE_CONSUMPTION_RESULT));
                                serialPort.writeString(IOBDService.TYPE_TEMPERATURE_ENGINE + "\r");
                            }
                            if (tmp.toString().contains(TYPE_TEMPERATURE_ENGINE_RESULT)) {
                                parameterSet.setEngineTemperature(obdParser.getFloat(tmp.toString(), TYPE_TEMPERATURE_ENGINE_RESULT));
                                serialPort.writeString(IOBDService.TYPE_TEMPERATURE_INDOOR + "\r");
                            }
                            if (tmp.toString().contains(TYPE_TEMPERATURE_INDOOR_RESULT)) {
                                parameterSet.setCarIndoorTemperature(obdParser.getFloat(tmp.toString(), TYPE_TEMPERATURE_INDOOR_RESULT));
                                serialPort.writeString(IOBDService.TYPE_TEMPERATURE_OUTDOOR + "\r");
                            }
                            if (tmp.toString().contains(TYPE_TEMPERATURE_OUTDOOR_RESULT)) {
                                parameterSet.setCarOutdoorTemperature(obdParser.getFloat(tmp.toString(), TYPE_TEMPERATURE_OUTDOOR_RESULT));
                                parameterSet.setTimestamp(Calendar.getInstance().getTimeInMillis());
                                sendEvent(parameterSet);
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

}
