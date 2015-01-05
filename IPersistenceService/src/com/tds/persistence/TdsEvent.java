package com.tds.persistence;

public class TdsEvent {
    int eventId;
    long timestamp;
    String gps;
    String filename;

    public TdsEvent(int eventId, long timestamp, String gps, String filename) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.gps = gps;
        this.filename = filename;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
