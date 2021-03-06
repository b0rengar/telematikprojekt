/**
 *
 */
package com.tds.serial;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.Hashtable;

import jssc.SerialPort;
import jssc.SerialPortException;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.tds.gps.IGPSService;
import com.tds.serial.network.SerialInputStream;

/**
 * Concrete class implementing {@link IGPSService} to provides access to the GPS coordinates of the On-Board Unit.
 * 
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 21:31:26
 * 
 * @author Erik Fröhlich
 * @edited 16.12.2014
 * 
 * @author André Finsterbusch
 * @edited 24.12.2014
 */
public class GPSService implements IGPSService, Runnable {
    private static EventAdmin eventAdmin;

    static SerialPort serialPort;
    private static NMEA gpsParser = new NMEA();
// String portName = "/dev/ttyUSB0";
    String portName = "/dev/gps0";

// int baudrate = 4800;
// int dataBits = SerialPort.DATABITS_8;
// int stopBits = SerialPort.STOPBITS_1;
// int parity = SerialPort.PARITY_NONE;

    private BufferedReader inputStream;

    private Thread t;

    @Override
    public void bindEventAdmin(EventAdmin eventAdmin) {
        GPSService.eventAdmin = eventAdmin;
    }

    @Override
    public void unbindEventAdmin() {
        GPSService.eventAdmin = null;
    }

    @Override
    public void openSP() {
        serialPort = new SerialPort(portName);

        try {
            System.out.println("Port opened: " + serialPort.openPort());
            //
            // System.out.println("Params setted: " + serialPort.setParams(baudrate, dataBits, stopBits, parity));
            int mask = SerialPort.MASK_RXCHAR;// Prepare mask

            serialPort.setEventsMask(mask);// Set mask
// serialPort.addEventListener(new SerialPortReader());// Add SerialPortEventListener

            inputStream = new BufferedReader(new InputStreamReader(new SerialInputStream(serialPort)));
            t = new Thread(this);
            t.start();

        } catch (SerialPortException ex) {
            // TODO LOGGER
            System.out.println(ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            String tmp;
            try {
                tmp = inputStream.readLine();

                gpsParser.parse(tmp);
                float lat = gpsParser.position.lat;
                float lon = gpsParser.position.lon;
                long ts = Calendar.getInstance().getTimeInMillis();
                Dictionary<String, String> eventProps = new Hashtable<String, String>();
                eventProps.put(EVENT_GPS_DATA_TIMESTAMP, Long.toString(ts));
                eventProps.put(EVENT_GPS_DATA_LAT, Float.toString(lat));
                eventProps.put(EVENT_GPS_DATA_LONG, Float.toString(lon));
                Event osgiEvent = new Event(EVENT_GPS_TOPIC, eventProps);

                // "sendEvent()" synchron "postEvent()" asynchron:
                eventAdmin.sendEvent(osgiEvent);
                System.out.println("new gps event");

            } catch (Exception e) {

            }

        }

    }

    /**
     * Class to handle serialport events and create osgi event if data available
     * 
     * In this class must implement the method serialEvent But we will not report on all events but only those that we put in the mask. In this case the arrival of the data and change the status lines CTS and DSR
     **/
// static class SerialPortReader implements SerialPortEventListener {
//
// @Override
// public void serialEvent(SerialPortEvent event) {
//
// if (event.isRXCHAR()) {// If data is available
// if (event.getEventValue() > 0) {
// try {
// String tmp = serialPort.readString();
// gpsParser.parse(tmp);
// float lat = gpsParser.position.lat;
// float lon = gpsParser.position.lon;
// long ts = Calendar.getInstance().getTimeInMillis();
// Dictionary<String, String> eventProps = new Hashtable<String, String>();
// eventProps.put(EVENT_GPS_DATA_TIMESTAMP, Long.toString(ts));
// eventProps.put(EVENT_GPS_DATA_LAT, Float.toString(lat));
// eventProps.put(EVENT_GPS_DATA_LONG, Float.toString(lon));
// Event osgiEvent = new Event(EVENT_GPS_TOPIC, eventProps);
//
// // "sendEvent()" synchron "postEvent()" asynchron:
// eventAdmin.sendEvent(osgiEvent);
// // System.out.println("GPS-Event with pos:" + lat + " : " + lon + "; pushed");
// } catch (SerialPortException ex) {
// // TODO LOGGER
// System.out.println(ex);
// } catch (Exception e) {
// // TODO: handle exception
// System.out.println("Cant create Event: " + e.getMessage());
// }
// }
// }
// }
// }

    /**
     * open serialPort defined in {@link portname}
     */
    @Override
    public void closeSP() {
        try {
            System.out.println("GPS Port closed: " + serialPort.closePort());
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    /**
     * nur für debug zwecke
     */
    @Override
    public float getLat() {
        float lat = 52.745975f;
        return lat;
    }

    /**
     * nur für debug zwecke
     */
    @Override
    public float getLon() {
        float lon = 13.238044f;
        return lon;
    }

}
