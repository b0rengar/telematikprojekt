/**
 *
 */
package com.tds.camera;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * <b>CameraService <br />
 * com.tds.cameraImpl - Picture <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 27.11.2014 17:55:46
 *
 */
public class Picture implements IPicture {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private byte[] pixels;

    public Picture(BufferedImage bi) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "jpg", baos);
            baos.flush();
            pixels = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public Picture(byte[] jpg){
    	this.pixels = jpg;
    }


    /*
     * (non-Javadoc)
     *
     * @see com.tds.camera.IPicture#getBufferedImage()
     */
    /*
     * (non-Javadoc)
     *
     * @see com.tds.cameraImpl.IPicture#getBufferedImage()
     */
    @Override
    public BufferedImage getBufferedImage() {

        ByteArrayInputStream bais = new ByteArrayInputStream(pixels);
        BufferedImage img = null;
        try {
            img = ImageIO.read(bais);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return img;

    }

	@Override
	public byte[] getJPEG() {
		return this.pixels;
	}

}
