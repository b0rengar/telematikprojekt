package com.tds.gui.settings;

/**
 * Collection of different units to measure the temperature of an object.
 *
 * @author Christian Bodler
 *
 */
public enum TemperatureUnit {
	/** Unit to temperature in Celsius. */
	CELSIUS,
	/** Unit to temperature in Fahrenheit. */
	FAHRENHEIT,
	/** Unit to temperature in Kelvin. */
	KELVIN;

	@Override
	public String toString() {
		switch (this) {
		case CELSIUS:
			return "\u00B0C";
		case FAHRENHEIT:
			return "\u00B0F";
		case KELVIN:
			return "\u00B0K";
		}
		return "no Name";
	}

}
