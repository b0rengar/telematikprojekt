/**
 *
 */
package com.tds.obdImpl;

import com.tds.obd.IOBDService;

/**
 * <b>OBDService <br />
 * com.tds.obd <br />
 * OBDService <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 20:05:47
 *
 */
public class OBDService implements IOBDService {

    @Override
    public double getSpeed() {
        // TODO Auto-generated method stub
        double speed = Math.random() * 180;

        System.out.println("Service called: Speed = " + speed);

        return speed;
    }

}
