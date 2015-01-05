package com.tds.gui;

import java.util.ArrayList;
import java.util.List;

public class Settings {
	
	private static final boolean DEBUG = true;
	private static Settings instance = null;
	
	// Client-Server
	private String serverName;
	private String serverIpAddress;
	private String serverIpAddressPort;
	private List<Client> clientList;
	
	// Units
	private VelocityUnit velocityUnit;
	private TemperatureUnit temperatureUnit;
	
	// Paths
	private String projectPath;
	private String tempPath;
	private String msgPath;
	
	private Settings(){
		if(DEBUG){
			setServerName("tds_server.bodler.net");
			setServerIpAddress("127.0.0.1");
			setServerIpAddressPort("55555");
			Client c1 = new Client("android_device_1", "123.123.123.123");
			Client c2 = new Client("android_device_2", "432.432.432.432");
			clientList = new ArrayList<Client>();
			clientList.add(c1);
			clientList.add(c2);
			
			velocityUnit = VelocityUnit.KMH;
			temperatureUnit = TemperatureUnit.CELSIUS;
			
			projectPath = "C:\\tmp\\project";
			tempPath = "C:\\tmp\\temp";
			msgPath = "C:\\tmp\\msg";
			
		}	
	}
	
	public static synchronized Settings getSettingsSingleton(){
		if(Settings.instance == null){
			Settings.instance = new Settings();
		} 
		return Settings.instance;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerIpAddress() {
		return serverIpAddress;
	}

	public void setServerIpAddress(String serverIpAddress) {
		this.serverIpAddress = serverIpAddress;
	}

	public String getServerIpAddressPort() {
		return serverIpAddressPort;
	}

	public void setServerIpAddressPort(String serverIPAddressPort) {
		this.serverIpAddressPort = serverIPAddressPort;
	}

	public List<Client> getClientList() {
		return clientList;
	}

	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}

	public VelocityUnit getVelocityUnit() {
		return velocityUnit;
	}

	public void setVelocityUnit(VelocityUnit velocityUnit) {
		this.velocityUnit = velocityUnit;
	}

	public TemperatureUnit getTemperatureUnit() {
		return temperatureUnit;
	}

	public void setTemperatureUnit(TemperatureUnit temperatureUnit) {
		this.temperatureUnit = temperatureUnit;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}

	public String getMsgPath() {
		return msgPath;
	}

	public void setMsgPath(String msgPath) {
		this.msgPath = msgPath;
	}
}
