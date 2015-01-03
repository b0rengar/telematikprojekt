/**
 *
 */
package com.tds.webImpl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class WebService extends HttpServlet implements IWebService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// resp.getWriter().write("Hello TDS Servlet!\n");
// resp.getWriter().write(new Date().toString());
// resp.getWriter().write("\n---------------------------------");
// resp.getWriter().flush();
        String startDate = request.getParameter("date");
        String quest = request.getParameter("quest");
        String id = request.getParameter("id");
// List<String> msgs = getMessages(getDateFromString(startDate));
//
// for (String msg : msgs) {
// response.getWriter().write(msg);
// }
        System.out.println("given Parameter: " + startDate + " " + quest + " " + id);

        try {

            if (quest.equals("image")) {
                System.out.println("send Image");
                // get id from List with notifications
                response.setContentType("image/jpeg");

                BufferedImage img;
                if (Integer.valueOf(id) == 1) {
                    img = ImageIO.read(new URL("http://data.motor-talk.de/data/galleries/0/147/9424/43109894/bild--7008737403287221413.jpg"));
                } else {
                    img = ImageIO.read(new URL("http://4.bp.blogspot.com/-19cColZlWD8/T3tTfo34DkI/AAAAAAAABIo/CLh7EkgGwPk/s1600/5376_1_articleorg_Facebook_Daumen_runter.jpg"));
                }
                OutputStream out = response.getOutputStream();
                ImageIO.write(img, "jpg", out);
                out.close();
            } else if (quest.equals("data")) {
                System.out.println("send Data");
                // PROTOCOLL YEAR;MONTH;DAY;HOUR;MINUTES;SECONDS;TYPE;LATITUDE;LONGITUDE;IMAGE
                response.getWriter().write("2013;12;01;12;33;17;Warning;52.342052;13.512783;0#");
                response.getWriter().write("2013;12;22;17;22;33;Information;52.342052;13.40292;1#");
                response.getWriter().write("2013;12;24;19;44;12;Warning;52.50452;13.40292;0#");
                response.getWriter().write("2013;12;24;19;44;12;Information;52.50452;13.40292;1#");
                response.getWriter().write("2013;12;24;19;44;12;Information;52.50452;13.40292;1#");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
