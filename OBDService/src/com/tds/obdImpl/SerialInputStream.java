/**
 * 
 */
package com.tds.obdImpl;

import java.io.IOException;
import java.io.InputStream;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * <b>OBDService <br />
 * com.tds.obdImpl <br />
 * SerialIOPort <br />
 * </b>
 * 
 * Description.
 * 
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 03.01.2015 17:49:21
 * 
 */
public class SerialInputStream extends InputStream {

	private SerialPort port;

	public SerialInputStream(SerialPort port) {
		this.port = port;
	}

	@Override
	public int read() throws IOException {

		try {
			if (!port.isOpened()) {
				port.openPort();
			}
			// read one byte to return.
			byte[] bytes = port.readBytes(1);
			if (bytes == null || bytes.length == 0) {
				// no input from the serial port
				return 0;
			} else {
				// return input
				return bytes[0];
			}
		} catch (SerialPortException e) {
			throw new IOException("serialportexception", e);
		}

	}

}
