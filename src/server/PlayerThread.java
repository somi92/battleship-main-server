package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import protocol.BattleShipMainServer;

public class PlayerThread implements Runnable {

	private String userName;
	private String ipAddress;
	private int port;
	
	private Socket communicationSocket;
	private BufferedReader inputFromClient;
	private DataOutputStream outputToClient;
	private BattleShipMainServer protocol;
	
	private boolean connectionTerminated;
	private boolean slotFilled;
//	private boolean addedToSlot;
	
	public PlayerThread() {
		
	}
	
	public PlayerThread(Socket communicationSocket) {
		this.communicationSocket = communicationSocket;
		this.connectionTerminated = false;
		this.slotFilled = false;
//		this.addedToSlot = false;
	}
	
//	public PlayerThread(String userName, String ipAddress, int port) {
//		this.userName = userName;
//		this.ipAddress = ipAddress;
//		this.port = port;
//	}
	
	public String getUserName() {
		if(this.userName==null) {
			return "";
		}
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			System.out.println("Connected to client "+this.communicationSocket.getInetAddress()+" on port "+this.communicationSocket.getPort());
			String clientMessage;
			String response;
			
			setIpAddress(this.communicationSocket.getInetAddress().toString());
			setPort(this.communicationSocket.getPort());
			setUserName("");
			
			protocol = new BattleShipMainServer();
			
			inputFromClient = new BufferedReader(new InputStreamReader(this.communicationSocket.getInputStream()));
			outputToClient = new DataOutputStream(this.communicationSocket.getOutputStream());
			
			while(!this.connectionTerminated) {
				
				clientMessage = inputFromClient.readLine();
				
				System.out.println("Received message from client "+toString()+" : "+clientMessage);
				int responseCode = protocol.parseProtocolMessage(clientMessage);
				response = "";
				
				switch(responseCode) {
				
					case BattleShipMainServer.SEARCH: {
						setUserName(protocol.getUserName());
						slotFilled = MainServer.addNewPlayer(this);
						if(!slotFilled) {
							response = protocol.responseMessage();
						}
					}
					break;
					
					case BattleShipMainServer.BYE: {
						response = protocol.responseMessage();
						this.connectionTerminated = true;
					}
					break;
					
					case BattleShipMainServer.ERROR: {
						response = protocol.responseMessage();
					}
					break;
					
					default: {
						response = protocol.responseMessage();
					}
				}
				
				if(!slotFilled) {
					outputToClient.writeBytes(response);
				}
				System.out.println("Response sent to CLIENT: "+toString()+" : "+response);
			}
			
			inputFromClient.close();
			outputToClient.close();
			this.communicationSocket.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void peersFound(String IP1, int port1, String IP2, int port2) {
		try {
			String message = protocol.responseMessage(IP1, port1+"", IP2, port2+"");
			this.outputToClient.writeBytes(message);
			System.out.println("Response sent to client: "+toString()+" : "+message);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String toString() {
//		if(getUserName().equals("")) {
//			return getIpAddress()+":"+getPort();
//		}
		return getUserName()+" "+getIpAddress()+":"+getPort();
	}
	
}
