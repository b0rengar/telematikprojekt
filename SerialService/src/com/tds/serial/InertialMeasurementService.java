/**
 *
 */
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

import com.tds.inertial.IInertialMeasurementService;

/**
 * <b>InertialMeasurementService <br />
 * com.tds.inertial <br />
 * InertialMeasurementService <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 21:20:57
 *
 */
public class InertialMeasurementService implements IInertialMeasurementService {
    private static EventAdmin eventAdmin;

    static SerialPort serialPort;
    // TODO ref-by-id
// static String portName = "/dev/ttyUSB1";
    static String portName = "/dev/acc0";
    static long last_event_time = 0;
    static long event_timer = 100; // ein event aller xxxx ms

    private final static int voltage = 3300; // in mV -> 3.3V
    private static double voltsPerUnit = voltage / 1024.0;
    private static int voltsPerG = voltage / 10;

    @Override
    public void bindEventAdmin(EventAdmin eventAdmin) {
        InertialMeasurementService.eventAdmin = eventAdmin;
    }

    @Override
    public void unbindEventAdmin() {
        InertialMeasurementService.eventAdmin = null;
    }

    @Override
    public void openSP() {
        serialPort = new SerialPort(portName);
        try {
            System.out.println("ACC Port opened: " + serialPort.openPort());
            //
            // System.out.println("Params setted: " + serialPort.setParams(baudrate, dataBits, stopBits, parity));
            int mask = SerialPort.MASK_RXCHAR;// Prepare mask

            serialPort.setEventsMask(mask);// Set mask
            serialPort.addEventListener(new SerialPortReader());// Add SerialPortEventListener

        } catch (SerialPortException ex) {
            // TODO LOGGER
            System.out.println(ex);
        }
    }

    @Override
    public void closeSP() {
        try {
            System.out.println("ACC Port " + portName + " closed: " + serialPort.closePort());
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    /*
     * In this class must implement the method serialEvent But we will not report on all events but only those that we put in the mask. In this case the arrival of the data and change the status lines CTS and DSR
     */
    static class SerialPortReader implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent event) {

            if (event.isRXCHAR()) {// If data is available
                if (event.getEventValue() > 0) {
                    try {
                        String tmp = serialPort.readString();
                        String[] tokens = tmp.split(" ");
                        double x = ((Double.parseDouble(tokens[0]) - 512) * voltsPerUnit) / voltsPerG;
                        double y = ((Double.parseDouble(tokens[1]) - 512) * voltsPerUnit) / voltsPerG;
                        double z = ((Double.parseDouble(tokens[2]) - 512) * voltsPerUnit) / voltsPerG;

                        long ts = Calendar.getInstance().getTimeInMillis();
                        // System.out.println(ts + " ACC Event x:" + x + " y:" + y + " z: " + z);

                        if (last_event_time < ts) {
                            if ((ts - last_event_time) >= event_timer) {
                                Dictionary<String, String> eventProps = new Hashtable<String, String>();
                                eventProps.put(EVENT_ACC_DATA_TIMESTAMP, Long.toString(ts));
                                eventProps.put(EVENT_ACC_DATA_X, Double.toString(x));
                                eventProps.put(EVENT_ACC_DATA_Y, Double.toString(y));
                                eventProps.put(EVENT_ACC_DATA_Z, Double.toString(z));
                                Event osgiEvent = new Event(EVENT_ACC_TOPIC, eventProps);
                                // "sendEvent()" synchron "postEvent()" asynchron:
                                eventAdmin.sendEvent(osgiEvent);
                                last_event_time = ts;
                            }
                        }

                    } catch (SerialPortException ex) {
                        // TODO LOGGER
                        System.out.println(ex);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println("Cant create ACC Event: " + e.getMessage());
                    }
                }
            }
        }
    }

}
