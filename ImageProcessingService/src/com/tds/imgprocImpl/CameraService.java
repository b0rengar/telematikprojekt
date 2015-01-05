package com.tds.imgprocImpl;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.activation.UnsupportedDataTypeException;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.osgi.framework.BundleContext;

import com.tds.camera.ICameraService;
import com.tds.camera.IPicture;
import com.tds.camera.Picture;

public class CameraService implements ICameraService {

    private List<VideoCapture> cams;

    private HashMap<Integer, Timer> timers;

    private BundleContext context;

    public CameraService(BundleContext context, int numCams) {
        this.context = context;
        timers = new HashMap<Integer, Timer>();
        cams = new ArrayList<VideoCapture>();
        
        for(int i = 0; i < numCams; i++){
        	try{
        		VideoCapture vc = new VideoCapture(i);
	        	cams.add(vc);
	        	vc.open(i);
	        	Thread.sleep(1000);
	        	if(vc.isOpened()){
	        		System.out.println("camera " + i);
	        	}
        	}
        	catch(Exception e){
        		System.out.println("camera exception :\n"+ e.getMessage());
        	}
        }

    }

    @Override
    public void destroyService() {
        
    	for(VideoCapture vc : cams){
    		vc.release();
    	}
        for (Timer t : timers.values()) {
            t.cancel();
            t.purge();
        }
    }



    @Override
    public BufferedImage getLocalCamImage(int camID) {
    	if(camID < 0 && camID >= cams.size())
    		return null;
    	
    	VideoCapture camera = cams.get(camID);
    	if(!camera.isOpened()){
    		camera.open(camID);
    	}
    	
    	if(camera.isOpened()){
 	    
	 	    Mat frame = new Mat();
	 	    camera.read(frame);
	 	    
	 	    try {
				return Util.convertToBufferedImage(frame);
			} catch (UnsupportedDataTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 	    
    	}
    	
    	return null;

    }

    @Override
    public IPicture getRemoteCamImage(int camID) {
        return new Picture(getLocalCamImage(camID));
    }

    
    @Override
    public void startCameraEvents(int camID, String topic, int fps) {

        TimerTask tt = new com.tds.imgprocImpl.CamPublisher(this.context, this, camID, topic, fps);
        System.out.println("start timer");
        Timer t = timers.get(camID);

        t = new Timer();
        t.scheduleAtFixedRate(tt, 1000 / fps, 1000 / fps);
    }

}
