package com.tds.gui.settings;

/**
 * Collection of different units to measure the speed of an object.
 *
 * @author Christian Bodler
 *
 */
public enum VelocityUnit {
	/** Unit to measure speed in kilometers per hours. */
	KMH,
	/** Unit to measure speed in miles per hours. */
	MPH;

	@Override
	public String toString(){
		switch(this) {
		case KMH:
			return "km/h";
		case MPH:
			return "mi/h";
		}
		return "no Name";
	}

}
