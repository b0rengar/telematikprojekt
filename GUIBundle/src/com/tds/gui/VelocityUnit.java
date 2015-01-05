package com.tds.gui;

public enum VelocityUnit {
	KMH, MPH;
	
	@Override
	public String toString(){
		switch(this) {
		case KMH:
			return "km/h";
		case MPH:
			return "mp/h";
		}
		return "no Name";
	}

}
