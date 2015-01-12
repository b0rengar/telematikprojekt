/**
 * 
 */
package com.tds.serial.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import jssc.SerialPort;
import jssc.SerialPortException;
import ch.ethz.iks.r_osgi.URI;
import ch.ethz.iks.r_osgi.channels.ChannelEndpoint;
import ch.ethz.iks.r_osgi.channels.NetworkChannel;
import ch.ethz.iks.r_osgi.messages.RemoteOSGiMessage;

/**
 * <b>SerialService <br />
 * com.tds.serial.network <br />
 * SerialNetworkChannel <br />
 * </b>
 * 
 * Description.
 * 
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.01.2015 11:23:04
 * 
 */
public class SerialNetworkChannel implements NetworkChannel, Runnable {

    private SerialPort serialPort;
    private ChannelEndpoint endpoint;
    private String portName;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private boolean connected = true;
    private Thread t;

    public SerialNetworkChannel(String portName, ChannelEndpoint endpoint, URI endpointUri) {
        System.out.println("portname: " + portName);
        System.out.println("SerialNetworkChannel (connecting) created...");
        this.endpoint = endpoint;
        openPort(portName);
    }

    public SerialNetworkChannel(String portName) {
        System.out.println("SerialNetworkChannel (listening) created...");
        this.portName = portName;
    }

    private void openPort(String portName) {
        try {

            this.portName = portName;
            serialPort = new SerialPort(portName);
            serialPort.openPort();
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
            oos = new ObjectOutputStream(new SerialOutputStream(serialPort));
            ois = new ObjectInputStream(new SerialInputStream(serialPort));
            t = new Thread(this);
            t.start();
            System.out.println("SerialNetworkChannel Port opened...");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void bind(ChannelEndpoint endpoint) {
        this.endpoint = endpoint;
        System.out.println("SerialNetworkChannel Port binded...");
        openPort(portName);

    }

    @Override
    public void close() throws IOException {
        if (serialPort != null && serialPort.isOpened()) {
            try {
                serialPort.closePort();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
        if (t != null && t.isAlive()) {
            t.stop();
        }
        connected = false;
        System.out.println("SerialNetworkChannel Port closed...");
    }

    @Override
    public URI getLocalAddress() {

        return new URI("local");
    }

    @Override
    public String getProtocol() {
        // TODO Auto-generated method stub
        return SerialNetworkFactory.PROTOCOL;
    }

    @Override
    public URI getRemoteAddress() {
        return new URI(portName);
    }

    @Override
    public void sendMessage(RemoteOSGiMessage msg) throws IOException {
        msg.send(oos);

    }

    @Override
    public void run() {
        while (connected) {
            try {
                final RemoteOSGiMessage msg = RemoteOSGiMessage.parse(ois);

                endpoint.receivedMessage(msg);
                System.out.println("SerialNetworkChannel Message recieved...");
            } catch (final IOException ioe) {
                connected = false;
                try {
                    serialPort.closePort();
                    System.out.println("SerialNetworkChannel Port closed (receiving error)...");
                } catch (final SerialPortException e1) {}
                endpoint.receivedMessage(null);
                return;
            } catch (final Throwable t) {
                t.printStackTrace();
            }
        }

    }
}
