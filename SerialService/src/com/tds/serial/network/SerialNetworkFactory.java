/**
 * 
 */
package com.tds.serial.network;

import java.io.IOException;

import ch.ethz.iks.r_osgi.Remoting;
import ch.ethz.iks.r_osgi.URI;
import ch.ethz.iks.r_osgi.channels.ChannelEndpoint;
import ch.ethz.iks.r_osgi.channels.NetworkChannel;
import ch.ethz.iks.r_osgi.channels.NetworkChannelFactory;

/**
 * <b>SerialService <br />
 * com.tds.serial <br />
 * SerialNetworkFactory <br />
 * </b>
 * 
 * Description.
 * 
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.01.2015 11:18:37
 * 
 */
public class SerialNetworkFactory implements NetworkChannelFactory {

    public static final String PROTOCOL = "serial";
    private Remoting remoting;
    private String portName;

    public SerialNetworkFactory(String portName) {
        this.portName = portName;
        System.out.println("SerialNetworkFactory created...");
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.ethz.iks.r_osgi.channels.NetworkChannelFactory#activate(ch.ethz.iks.r_osgi.Remoting)
     */
    @Override
    public void activate(Remoting remoting) throws IOException {
        System.out.println("SerialNetworkFactory activated...");

        this.remoting = remoting;
        remoting.createEndpoint(new SerialNetworkChannel(portName));

    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.ethz.iks.r_osgi.channels.NetworkChannelFactory#deactivate(ch.ethz.iks.r_osgi.Remoting)
     */
    @Override
    public void deactivate(Remoting arg0) throws IOException {
        // nothing to do
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.ethz.iks.r_osgi.channels.NetworkChannelFactory#getConnection(ch.ethz.iks.r_osgi.channels.ChannelEndpoint, ch.ethz.iks.r_osgi.URI)
     */
    @Override
    public NetworkChannel getConnection(ChannelEndpoint endpoint, URI endpointUri) throws IOException {

        return new SerialNetworkChannel(portName, endpoint, endpointUri);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.ethz.iks.r_osgi.channels.NetworkChannelFactory#getListeningPort(java.lang.String)
     */
    @Override
    public int getListeningPort(String protocol) {
        return 0;
    }

}
