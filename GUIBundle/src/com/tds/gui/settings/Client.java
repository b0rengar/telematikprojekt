package com.tds.gui.settings;

/**
 * Class to represent mobile clients which have subscribed to the system to receive event notification.
 *
 * @author Christian Bodler
 *
 */
public class Client {

    /** A human-readable name to identify the client by. */
    private String name;
    /** The IP address of the client to send the notifications to. */
    private String ipAddress;

    /**
     * Creates a new client using the given name and IP address.
     *
     * @param name A human-readable name to identify the client by.
     * @param ipAddress The IP address of the client to send the notifications to.
     */
    public Client(String name, String ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
    }

    /**
     * Returns the human-readable name of the client.
     *
     * @return The human-readable name of the client.
     */
    public String getName() {
        return name;
    }

    /**
     * Assigns a new name to the client.
     *
     * @param name The new name of the client.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the IP address of the client notification are being send to.
     *
     * @return The IP address of the client notification are being send to.
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Assigns a new IP address to send the notifications to.
     *
     * @param ipAddress The new IP address.
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}
