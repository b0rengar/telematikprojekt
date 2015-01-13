/**
 *
 */
package com.tds.inertial;

import org.osgi.service.event.EventAdmin;

/**
 * <b>IInertialMeasurementService <br />
 * com.tds.inertial <br />
 * IInertialMeasurementService <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 21:16:27
 *
 */
public interface IInertialMeasurementService {
    public static String EVENT_ACC_DATA_TIMESTAMP = "ACC/Data/ts";
    public static String EVENT_ACC_DATA_X = "ACC/Data/x";
    public static String EVENT_ACC_DATA_Y = "ACC/Data/y";
    public static String EVENT_ACC_DATA_Z = "ACC/Data/z";
    public static String EVENT_ACC_TOPIC = "ims/ACC";

    /**
     * bind OSGi EventAdmin
     *
     * @param eventAdmin
     */
    void bindEventAdmin(EventAdmin eventAdmin);

    /**
     * delete OSGi EventAdmin
     */
    void unbindEventAdmin();

    void openSP();

    void closeSP();
}
