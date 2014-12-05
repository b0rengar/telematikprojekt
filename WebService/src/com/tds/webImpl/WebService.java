/**
 *
 */
package com.tds.webImpl;

import java.io.IOException;
import java.util.Date;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Hello TDS Servlet!\n");
        resp.getWriter().write(new Date().toString());
        resp.getWriter().write("\n---------------------------------");
        resp.getWriter().flush();
    }

}
