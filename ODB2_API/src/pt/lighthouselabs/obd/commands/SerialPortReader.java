package pt.lighthouselabs.obd.commands;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class SerialPortReader {

	private SerialPort serialPort;
	private boolean dataAvailable = false;

	private static SerialPortReader instance = null;

	private SerialPortReader() {
		// String portName = "/dev/ttyACM0";
		String portName = "COM4";
		int baudrate = 9600;
		int dataBits = SerialPort.DATABITS_8;
		int stopBits = SerialPort.STOPBITS_1;
		int parity = SerialPort.PARITY_NONE;

		String[] portNames = SerialPortList.getPortNames();
		System.out.println(portNames.length);
		for (int i = 0; i < portNames.length; i++) {
			System.out.println(portNames[i]);
		}

		serialPort = new SerialPort(portName);

		try {

			System.out.println("Port opened: " + serialPort.openPort());
			System.out.println("Params setted: "
					+ serialPort
							.setParams(baudrate, dataBits, stopBits, parity));
			int mask = SerialPort.MASK_RXCHAR;// Prepare mask
			serialPort.setEventsMask(mask);// Set mask
			serialPort.addEventListener(new SerialPortReaderListn());// Add
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_IN
					| SerialPort.FLOWCONTROL_XONXOFF_OUT);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static SerialPortReader getInstance() {
		if (instance == null) {
			instance = new SerialPortReader();
		}
		return instance;
	}

	public SerialPort getSerialPort() {
		return serialPort;
	}

	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}

	public boolean isDataAvailable() {
		return dataAvailable;
	}

	public void setDataAvailable(boolean dataAvailable) {
		this.dataAvailable = dataAvailable;
	}

	static class SerialPortReaderListn implements SerialPortEventListener {

		public void serialEvent(SerialPortEvent event) {
			if (event.isRXCHAR()) {// If data is available
				if (event.getEventValue() > 0) {
					// try {
					// String tmp =
					// SerialPortReader.getInstance().serialPort.readString();
					// // if(tmp.contains("\r")){
					// // System.out.println(text);
					// // text = "";
					// // }else{
					// text = text.concat(tmp);
					// // }
					//
					// // System.out.println(gpsParser.parse(tmp));
					// // System.out.println(gpsParser.toString());
					// } catch (SerialPortException ex) {
					// System.out.println(ex);
					// }
					SerialPortReader.getInstance().setDataAvailable(true);
				}
			}
		}
	}
}
