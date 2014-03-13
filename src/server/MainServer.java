package server;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {
//
//	private static Player players[] = new  Player[21];
//	private static int startIndex = 0;
//	private static int endIndex = -1;
//	private static int numberOfPlayers = 0;
//	
//	public static synchronized boolean addNewPlayer(String userName, String ipAddress, int port) {
//		if(numberOfPlayers==players.length) {
//			return false;
//		}
//		if(endIndex == 20) {
//			endIndex = 0;
//		} 
//		Player newPlayer = new Player(userName, ipAddress, port);
//		players[++endIndex] = newPlayer;
//		numberOfPlayers++;
//		return true;
//	}
//	
//	public static synchronized void removePlayer() {
//		if(numberOfPlayers==0) {
//			return;
//		}
//		if(startIndex==20) {
//			startIndex = 0;
//		} else {
//			startIndex++;
//		}
//		numberOfPlayers--;
//	}
//	
//	public static synchronized boolean isThereEnoughPlayers() {
//		
//	}

	private static volatile PlayerThread[] slot1 = new  PlayerThread[3];
	private static volatile PlayerThread[] slot2 = new  PlayerThread[3];
	private static volatile PlayerThread[] slot3 = new  PlayerThread[3];
	private static volatile PlayerThread[] slot4 = new  PlayerThread[3];
	
	private static volatile int slot1Elements = 0;
	private static volatile int slot2Elements = 0;
	private static volatile int slot3Elements = 0;
	private static volatile int slot4Elements = 0;
	
	/**
	 * Method adds new player to first free slot.
	 * @param userName 
	 * @param ipAddress
	 * @param port
	 * @return boolean
	 */
	public static synchronized boolean addNewPlayer(PlayerThread player) {
		int playerAddedToSlot = addPlayerToSlot(player);
		if(playerAddedToSlot==0) {
			System.out.println("Slot filled, notifying peers...");
			return true;
		}
		return false;
	}
	
	
	/**
	 * Method adds new player to first free slot.
	 * @param userName 
	 * @param ipAddress
	 * @param port
	 * @return number that represents slot or -1 if there is no free spot in slots.
	 */
	private static synchronized int addPlayerToSlot(PlayerThread player) {
		if(slot1Elements<3) {
			for(int i=0; i<slot1.length; i++) {
				if(slot1[i]==null) {
					slot1[i] = player;
					slot1Elements++;
					System.out.println("New player added to slot 1: "+player);
					if(slot1Elements==3) {
						startGame(slot1);
						return 0;
					}
					return 1;
				}
			}
		}
		
		if(slot2Elements<3) {
			for(int i=0; i<slot2.length; i++) {
				if(slot2[i]==null) {
					slot2[i] = player;
					slot2Elements++;
					System.out.println("New player added to slot 2: "+player);
					if(slot2Elements==3) {
						startGame(slot2);
						return 0;
					}
					return 1;
				}
			}
		}

		if(slot3Elements<3) {
			for(int i=0; i<slot3.length; i++) {
				if(slot3[i]==null) {
					slot3[i] = player;
					slot3Elements++;
					System.out.println("New player added to slot 3: "+player);
					if(slot3Elements==3) {
						startGame(slot3);
						return 0;
					}
					return 1;
				}
			}
		}

		if(slot4Elements<3) {
			for(int i=0; i<slot4.length; i++) {
				if(slot4[i]==null) {
					slot4[i] = player;
					slot4Elements++;
					System.out.println("New player added to slot 4: "+player);
					if(slot4Elements==3) {
						startGame(slot4);
						return 0;
					}
					return 1;
				}
			}
		}
		
		return -1;
	}
	
	
	private static synchronized void startGame(PlayerThread[] slot) {
		for(int i=0; i<slot.length; i++) {
			
			switch (i) {
				case 0: {
					slot[i].peersFound(slot[1].getIpAddress(), slot[1].getPort(), slot[2].getIpAddress(), slot[2].getPort());
				}
				break;
				
				case 1: {
					slot[i].peersFound(slot[0].getIpAddress(), slot[0].getPort(), slot[2].getIpAddress(), slot[2].getPort());
				}
				break;
				
				case 2: {
					slot[i].peersFound(slot[0].getIpAddress(), slot[0].getPort(), slot[1].getIpAddress(), slot[1].getPort());
				}
				break;
			}
		}
//		System.out.println("Slot filled, notifying peers...");
//		displaySlot(slot);
//		emptySlot(slot);
		
	}
	
	private static synchronized void emptySlot(PlayerThread[] slot) {
		for(int i=0; i<slot.length; i++) {
			slot[i] = null;
		}
	}
	
	private static synchronized void displaySlot(PlayerThread[] slot) {
		for (int i = 0; i < slot.length; i++) {
			System.out.println(slot[i]);
		}
	}
	
	public static void main(String[] args) {
		
		ExecutorService mainServerExecutor = Executors.newCachedThreadPool();
		
		ServerSocket mainServerSocket;
		int listeningPort = 9080;
		
		try {
			
			mainServerSocket = new ServerSocket(listeningPort);
			System.out.println("Main server up and running. Listening on port 9080...");
			while(true) {
				mainServerExecutor.execute(new PlayerThread(mainServerSocket.accept()));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
