package com.tds.camera;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public interface IPicture extends Serializable {

    /*
     * (non-Javadoc)
     * 
     * @see com.tds.camera.IPicture#getBufferedImage()
     */
    public abstract BufferedImage getBufferedImage();

}
