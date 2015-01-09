package com.tds.persistenceImpl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.osgi.framework.BundleContext;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.tds.persistence.IPersistenceService;
import com.tds.persistence.TdsEvent;

/**
 * Concrete class implementing {@link IPersistenceService} to provide access to the persistence layer of the application.
 *
 * @author Andre Finsterbusch
 *
 */
public class PersistenceService implements IPersistenceService {
    private IPersistenceService persistenceService;
    private BundleContext context;

    /** An instance of the database used to store the data. */
    private static DB db;
    /** An API instance to access the database. */
    private static MongoClient mongoClient;
    /** The hostname of the database to store the data in. */
    private static final String mDB = "tm13.ddns.net";
    /** The port on the database host to access the database through. */
    private static final int mDBPort = 27017;

    /** Creates a new instance to store data. */
    PersistenceService() {
        try {
            mongoClient = new MongoClient(mDB, mDBPort);
            db = mongoClient.getDB("tmp");
        } catch (UnknownHostException e) {
            // TODO LOGGER
            System.out.println("cant connect to MongoDB: " + mDB + " " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<TdsEvent> getTdsEventsFromDB() {
        ArrayList events = getItems(IPersistenceService.TABLE_EVENT);
        Iterator<Object> iter = events.iterator();
        ArrayList<TdsEvent> tdsEvents = new ArrayList<TdsEvent>();
        TdsEvent e;
        while (iter.hasNext()) {
            BasicDBObject event = (BasicDBObject) iter.next();
            BasicDBObject location = (BasicDBObject) event.get("location");

            String gps = (String) location.get("coordinates");
            long timestamp = (Long) event.get("timestamp");
            String filename = (String) event.get("filename");
            int event_id = (Integer) event.get("event_id");
            e = new TdsEvent(event_id, timestamp, gps, filename);
            tdsEvents.add(e);
            System.out.println("ID: " + event_id);
        }
        return tdsEvents;
    }

    @Override
    public ArrayList<Object> getItems(String table_name) {
        ArrayList<Object> events = new ArrayList<Object>();
        DBCollection coll = db.getCollection(table_name);
        DBCursor cursor = coll.find();
        try {
            while (cursor.hasNext()) {
                events.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return events;
    }

    /**
     *
     * @param tableName
     * @param data
     * @throws Exception
     */
    @Override
    public void setData(String tableName, Object data) throws Exception {
        throw new Exception("not yet implemented: use default java classes instead of mongodb specific");
// DBCollection coll = db.getCollection(tableName);
// coll.insert(data);
    }

    @Override
    public void setEvent(int evnetid, String filename, long timestamp, String gps) {
        DBCollection coll = db.getCollection(TABLE_EVENT);
        BasicDBObject event = new BasicDBObject("timestamp", System.currentTimeMillis()).append("location", new BasicDBObject("type", "Point").append("coordinates", gps)).append("filename", filename).append("event_id", evnetid);
        coll.insert(event);
    }

    @Override
    public void setTelemetrie(Calendar timestamp, String gps, String acceleration, int vehicle_velocity, int fuel_consumption, int fuel, int engine_speed, float engine_temperature, float steering_angle, boolean ABS, boolean ESP, float internal_temperature, float ambient_temperature, int luminosity) {

        DBCollection coll = db.getCollection(TABLE_TELEMETRIE);
        BasicDBObject telemetrie = new BasicDBObject("timestamp", timestamp).append("location", new BasicDBObject("type", "Point").append("coordinates", gps)).append("acceleration", acceleration).append("vehicle_velocity", vehicle_velocity).append("fuel_consumption", fuel_consumption).append("fuel", fuel).append("engine_speed", engine_speed).append("engine_temperature", engine_temperature)
                .append("steering_angle", steering_angle).append("ABS", ABS).append("ESP", ESP).append("internal_temperature", internal_temperature).append("ambient_temperature", ambient_temperature).append("luminosity", luminosity);

        coll.insert(telemetrie);
    }

    @Override
    // wird eigentlich nicht benötigt, daten können über die Telemetrie tabelle geholt werden
    public void setStreamData(String filename, Calendar timestamp, String gps) {
        DBCollection coll = db.getCollection(TABLE_STREAM_DATA);
        BasicDBObject streamData = new BasicDBObject("filename", filename).append("timestamp", timestamp).append("location", new BasicDBObject("type", "Point").append("coordinates", gps));
        coll.insert(streamData);

    }

    @Override
    public void setFileInfo(int stream_type, String filename, Calendar timestamp_start, Calendar timestamp_end, String gps) {
        DBCollection coll = db.getCollection(TABLE_FILES);
        BasicDBObject fileInfo = new BasicDBObject("stream_type", stream_type).append("filename", filename).append("timestamp_start", timestamp_start).append("timestamp_end", timestamp_end).append("location", new BasicDBObject("type", "Point").append("coordinates", gps));
        coll.insert(fileInfo);
    }

}
