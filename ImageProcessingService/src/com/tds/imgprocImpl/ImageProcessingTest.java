package com.tds.imgprocImpl;

import org.apache.felix.ipojo.junit4osgi.OSGiTestCase;

public class ImageProcessingTest extends OSGiTestCase {

// public void testServiceAvailability() {
// ServiceReference ref = context
// .getServiceReference(ImageProcessingService.class.getName());
// assertNotNull("Assert Availability", ref);
// }
//
// public void testOpenEyes() {
// ServiceReference ref = context
// .getServiceReference(ImageProcessingService.class.getName());
// assertNotNull("Assert Availability", ref);
// ImageProcessingService imageProcessing = (ImageProcessingService) context
// .getService(ref);
// try {
// File img = new File("OpenEye.png");
// BufferedImage image = ImageIO.read(img);
// assertEquals("Kein Schlaf erkannt", false,
// imageProcessing.detectSleep(image));
// } catch (IOException e) {
// e.printStackTrace();
// }
// }
//
// public void testClosedEyes() {
// ServiceReference ref = context
// .getServiceReference(ImageProcessingService.class.getName());
// assertNotNull("Assert Availability", ref);
// ImageProcessingService imageProcessing = (ImageProcessingService) context
// .getService(ref);
// try {
// File img = new File("ClosedEye.png");
// BufferedImage image = ImageIO.read(img);
// boolean bool = imageProcessing.detectSleep(image);
// assertEquals("Kein Schlaf erkannt", true,
// imageProcessing.detectSleep(image));
// try {
// Thread.sleep(2500);
// } catch (InterruptedException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
// assertEquals("Schlaf erkannt", true,
// imageProcessing.detectSleep(image));
// } catch (IOException e) {
// e.printStackTrace();
// }
// }

}
