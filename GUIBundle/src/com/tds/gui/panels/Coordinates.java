package com.tds.gui.panels;

/**
 * A set of GPS coordinates consisting of longitude and latitude.
 *
 * @author Christian Bodler
 *
 */
public class Coordinates {
    /** The latitude value of this set of coordinates. */
    private float lat;
    /** The longitude value of this set of coordinates. */
    private float lon;

    /**
     * Creates a new set of GPS coordinates with the given latitude and longitude set.
     *
     * @param lat The latitude value of this set of coordinates.
     * @param lon The longitude value of this set of coordinates.
     */
    public Coordinates(float lat, float lon) {
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Returns the latitude value of this set of coordinates.
     *
     * @return The latitude value of this set of coordinates.
     */
    public float getLat() {
        return lat;
    }

    /**
     * Assigns the given latitude value as the new latitude of this set of coordinates.
     *
     * @param lat The new latitude value.
     */
    public void setLat(float lat) {
        this.lat = lat;
    }

    /**
     * Returns the longitude value of this set of coordinates.
     *
     * @return The longitude value of this set of coordinates.
     */
    public float getLon() {
        return lon;
    }

    /**
     * Assigns the given longitude value as the new longitude of this set of coordinates.
     *
     * @param lat The new longitude value.
     */
    public void setLon(float lon) {
        this.lon = lon;
    }

}
