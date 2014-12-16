/**
 *
 */
package com.tds.gpsImpl;

import com.tds.gps.IGPSService;
import com.tds.gpsImpl.NMEA.GPSPosition;

import java.io.IOException;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

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
 */
public class GPSService implements IGPSService, SerialPortEventListener {
	static SerialPort serialPort;
	private static NMEA gpsParser = new NMEA();
	String portName = "/dev/ttyACM0";
	// String portName = "COM7";
	int baudrate = 1200;
	int dataBits = SerialPort.DATABITS_8;
	int stopBits = SerialPort.STOPBITS_1;
	int parity = SerialPort.PARITY_NONE;
	private String gpsString;

	/*
	 * In this class must implement the method serialEvent, through it we learn
	 * about events that happened to our port. But we will not report on all
	 * events but only those that we put in the mask. In this case the arrival
	 * of the data and change the status lines CTS and DSR
	 */
	@Override
	public void serialEvent(SerialPortEvent event) {

		if (event.isRXCHAR()) {// If data is available
			if (event.getEventValue() > 0) {
				try {
					String tmp = serialPort.readString();
					// System.out.println(tmp);
					gpsString = gpsParser.parse(tmp).toString();
					// System.out.println(gpsParser.toString());
				} catch (SerialPortException ex) {
					System.out.println(ex);
				}
			}
		}
	}
	@Override
	public String getGpsPosition() {
		return gpsString;
	}

}
