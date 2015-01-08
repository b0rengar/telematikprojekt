package com.tds.gui.settings;

public enum TemperatureUnit {
	CELSIUS, FAHRENHEIT, KELVIN;

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
