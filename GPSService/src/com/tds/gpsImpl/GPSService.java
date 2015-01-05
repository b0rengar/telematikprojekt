/**
 *
 */
package com.tds.gpsImpl;

import java.util.Dictionary;
import java.util.Hashtable;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.tds.gps.IGPSService;

/**
 * <b>GPSService <br />
 * com.tds.gps <br />
 * GPSService <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 21:31:26
 *
 * @author Eric
 * @edited 16.12.2014
 *
 * @author André Finsterbusch
 * @edited 24.12.2014
 */
public class GPSService implements IGPSService {
    private static EventAdmin eventAdmin;

    static SerialPort serialPort;
    private static NMEA gpsParser = new NMEA();
    String portName = "/dev/ttyUSB0";
    int baudrate = 1200;
    int dataBits = SerialPort.DATABITS_8;
    int stopBits = SerialPort.STOPBITS_1;
    int parity = SerialPort.PARITY_NONE;
    private static String gpsString;

    public void bindEventAdmin(EventAdmin eventAdmin) {
        GPSService.eventAdmin = eventAdmin;
    }

    public void unbindEventAdmin(EventAdmin eventAdmin) {
        GPSService.eventAdmin = null;
    }

    @Override
    public void openSP() {
        serialPort = new SerialPort(portName);
        try {
            System.out.println("Port opened: " + serialPort.openPort());
            System.out.println("Params setted: " + serialPort.setParams(baudrate, dataBits, stopBits, parity));
            int mask = SerialPort.MASK_RXCHAR;// Prepare mask
            serialPort.setEventsMask(mask);// Set mask
            serialPort.addEventListener(new SerialPortReader());// Add SerialPortEventListener

        } catch (SerialPortException ex) {
            // TODO LOGGER
            System.out.println(ex);
        }
    }

    /*
     * In this class must implement the method serialEvent, through it we learn about events that happened to our port. But we will not report on all events but only those that we put in the mask. In this case the arrival of the data and change the status lines CTS and DSR
     */
    static class SerialPortReader implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent event) {

            if (event.isRXCHAR()) {// If data is available
                if (event.getEventValue() > 0) {
                    try {
                        String tmp = serialPort.readString();
                        gpsString = gpsParser.parse(tmp).getPosition();

                        Dictionary<String, String> eventProps = new Hashtable<String, String>();
                        eventProps.put(Event_DATA, gpsString);
                        Event osgiEvent = new Event(Event_TOPIC, eventProps);

                        // "sendEvent()" synchron
                        // "postEvent()" asynchron:
                        eventAdmin.sendEvent(osgiEvent);

                    } catch (SerialPortException ex) {
                        // TODO LOGGER
                        System.out.println(ex);
                    }
                }
            }
        }
    }

    @Override
    public String getGpsPosition() {
        return gpsString;
    }

}
