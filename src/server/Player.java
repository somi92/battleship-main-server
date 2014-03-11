package server;

public class Player {

	private String userName;
	private String ipAddress;
	private int port;
	
	public Player() {
		
	}
	
	public Player(String userName, String ipAddress, int port) {
		this.userName = userName;
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	
}
