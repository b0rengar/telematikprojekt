package com.tds.gui.settings;

public class Car {
	
	String car_id;
	String brand;
	String type;
	String buildYear;
	String chassisNumber;
	
	String ipAddress;
	String ipAddressPort;
	
	String other;
	
	
	public Car(){
		
	}


	public String getCar_id() {
		return car_id;
	}


	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getBuildYear() {
		return buildYear;
	}


	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}


	public String getChassisNumber() {
		return chassisNumber;
	}


	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}


	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public String getIpAddressPort() {
		return ipAddressPort;
	}


	public void setIpAddressPort(String ipAddressPort) {
		this.ipAddressPort = ipAddressPort;
	}


	public String getOther() {
		return other;
	}


	public void setOther(String other) {
		this.other = other;
	}

}