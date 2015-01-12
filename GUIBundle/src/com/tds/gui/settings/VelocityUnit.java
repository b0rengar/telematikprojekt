package com.tds.gui.settings;

public enum VelocityUnit {
	KMH, MPH;
	
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
