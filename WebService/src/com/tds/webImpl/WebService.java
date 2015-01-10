/**
 *
 */
package com.tds.webImpl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.tds.persistence.IPersistenceService;
import com.tds.persistence.TdsEvent;
import com.tds.web.IWebService;

/**
 * <b>WebService <br />
 * com.tds.web <br />
 * WebService <br />
 * </b>
 * 
 * Description.
 * 
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 18.11.2014 15:17:20
 * 
 */
public class WebService extends HttpServlet implements IWebService, ServiceTrackerCustomizer<IPersistenceService, IPersistenceService> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final BundleContext context;
    private IPersistenceService persistenceService;

    public WebService(BundleContext context) {
        this.context = context;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// resp.getWriter().write("Hello TDS Servlet!\n");
// resp.getWriter().write(new Date().toString());
// resp.getWriter().write("\n---------------------------------");
// resp.getWriter().flush();
        long startDate = Long.valueOf(request.getParameter("date"));
        String quest = request.getParameter("quest");
// List<String> msgs = getMessages(getDateFromString(startDate));
//
// for (String msg : msgs) {
// response.getWriter().write(msg);
// }
        System.out.println("REQUEST with the given Parameters: " + startDate + " " + quest);

        try {

            if (quest.equals("image")) {
                // System.out.println("send Image");
                // get id from List with notifications
                response.setContentType("image/jpeg");

                BufferedImage img = getBuffImageFromTdsEvent(startDate);
// if (Integer.valueOf(id) == 1) {
// img = ImageIO.read(new URL("http://upload.wikimedia.org/wikipedia/commons/0/0b/B61_G%C3%BCnser_Stra%C3%9Fe_-Unterloisdorf.JPG"));
// } else {
// img = ImageIO.read(new URL("http://images.fotocommunity.de/bilder/landschaft/wege-und-pfade/strasse-ins-nichts-1abbc2c9-52d4-4eb6-a26c-61798d13ddec.jpg"));
// }
                OutputStream out = response.getOutputStream();
                ImageIO.write(img, "jpg", out);
                out.close();
            } else if (quest.equals("data")) {
                // System.out.println("send Data");
                String eventData = getStringFromEvents(getEventsFromMongoDB(startDate));
                System.out.println(eventData);
                response.getWriter().write(eventData);

                // ---------------------------------------------------------------------------------
                // TestData
                // PROTOCOLL TIMESTAMP;TYPE;LATITUDE;LONGITUDE;IMAGE
// response.getWriter().write("1418068174826;0;52.342052;13.512783#");
// response.getWriter().write("1418068174826;1;52.342052;13.40292#");
// response.getWriter().write("1418068174826;2;52.50452;13.40292#");
// response.getWriter().write("1418068174826;3;52.50452;13.40292#");
// response.getWriter().write("1418068174826;4;52.50452;13.40292#");
// response.getWriter().write("1418068174826;5;52.50452;13.40292#");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * Method to get Picture from event
     * 
     * @param startDate - System time in milliseconds
     * @return BufferedImage
     * @throws IOException
     */
    private BufferedImage getBuffImageFromTdsEvent(long startDate) throws IOException {
        BufferedImage img = null;
        List<TdsEvent> events = null;
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(startDate);

        try {
            events = persistenceService.getTdsEventsFromDB();
        } catch (Exception e) {
            System.out.println("getBuffImageFromTdsEvent: " + e);
        }

        for (TdsEvent temp : events) {
            Calendar tmpDate = Calendar.getInstance();
            tmpDate.setTimeInMillis(temp.getTimestamp());
            if (tmpDate.getTimeInMillis() > date.getTimeInMillis()) {
                img = ImageIO.read(new File(temp.getFilename()));
            }
        }
        return img;
    }

    /**
     * method to get Events from DB with time range and sorted by time stamp
     * 
     * @param startDate - beginning of event time
     */
    public ArrayList<TdsEvent> getEventsFromMongoDB(long startDate) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(startDate);

        List<TdsEvent> events = null;
        try {
            events = persistenceService.getTdsEventsFromDB();
        } catch (Exception e) {
            System.out.println("getEventsFromMongoDB: " + e);
        }
        // System.out.println(persistenceService.getTdsEventsFromDB());
        ArrayList<TdsEvent> neededEvents = new ArrayList<TdsEvent>();
        // loop through all events, to get events in time range
        for (TdsEvent temp : events) {
            Calendar tmpDate = Calendar.getInstance();
            tmpDate.setTimeInMillis(temp.getTimestamp());
            if (tmpDate.getTimeInMillis() > date.getTimeInMillis()) {
                // System.out.println(temp.getEventId());
                neededEvents.add(temp);
            }
        }
        // Sorting events newest on top
        Collections.sort(neededEvents, new Comparator<TdsEvent>() {
            @Override
            public int compare(TdsEvent event1, TdsEvent event2) {
                return Long.valueOf(event1.getTimestamp()).compareTo(Long.valueOf(event2.getTimestamp()));
            }
        });
        return neededEvents;
    }

    /**
     * method to get data string from event array
     * 
     * @param events - array
     * @return data - string
     */
    public String getStringFromEvents(ArrayList<TdsEvent> events) {
        String str = "";
        for (TdsEvent temp : events) {
            str += temp.getTimestamp();
            str += ";";
            str += temp.getEventId();
            str += ";";
            // str += temp.getGps();
            str += "52.342052";
            str += ";";
            str += "13.40292";

            str += "#";
        }
        return str;
    }

    /**
     * Adding IPersistanceService to WebService
     */
    @Override
    public IPersistenceService addingService(ServiceReference<IPersistenceService> arg0) {
        System.out.println("WebService adding Persistence Service");
        persistenceService = this.context.getService(arg0);
        return persistenceService;
    }

    /**
     * Modify IPersistanceService
     */
    @Override
    public void modifiedService(ServiceReference<IPersistenceService> arg0, IPersistenceService arg1) {
        // TODO Auto-generated method stub

    }

    /**
     * Removing IPersistanceService from WebService
     */
    @Override
    public void removedService(ServiceReference<IPersistenceService> arg0, IPersistenceService arg1) {
        // TODO Auto-generated method stub

    }

}
