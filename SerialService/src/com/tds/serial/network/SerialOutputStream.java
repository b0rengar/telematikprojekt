/**
 * 
 */
package com.tds.serial.network;

import java.io.IOException;
import java.io.OutputStream;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * A class to write to a serial port.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 03.01.2015 17:59:31
 *
 */
public class SerialOutputStream extends OutputStream {
	
	/** The port the device to write to is connected. E.g. COM5 or /dev/ttyUSB0 */
	private SerialPort port;

	/**
	 * Creates a new instance to write to the connected device.
	 *
	 * @param port The port the device to write to is connected. E.g. COM5 or /dev/ttyUSB0
	 */
	public SerialOutputStream(SerialPort port) {
		this.port = port;
	}
	
	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {
		
		try {
			if (!port.isOpened()) {
				port.openPort();
			}
			if(!port.writeInt(b)){
				throw new IOException("could not write on serial port ");
			}
			
		} catch (SerialPortException e) {
			
		}

	}

}
