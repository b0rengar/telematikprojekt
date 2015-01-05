/**
 * 
 */
package com.tds.obdImpl;

import java.io.IOException;
import java.io.OutputStream;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * <b>OBDService <br />
 * com.tds.obdImpl <br />
 * SerialOuputStream <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 03.01.2015 17:59:31
 *
 */
public class SerialOutputStream extends OutputStream {
	
	private SerialPort port;

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
