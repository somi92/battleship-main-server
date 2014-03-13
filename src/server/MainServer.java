package server;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {

	private static volatile PlayerThread[] slot1 = new  PlayerThread[3];
	private static volatile PlayerThread[] slot2 = new  PlayerThread[3];
	private static volatile PlayerThread[] slot3 = new  PlayerThread[3];
	private static volatile PlayerThread[] slot4 = new  PlayerThread[3];
	private static volatile int[] elementsNumber = new int[4];
	
	static {
		for(int i=0; i<elementsNumber.length; i++) {
			elementsNumber[i] = 0;
		}
	}
	
	public static synchronized boolean addNewPlayer(PlayerThread player) {
		int playerAddedToSlot = addPlayerToSlot(player);
		if(playerAddedToSlot==0) {
//			System.out.println("Slot filled, notifying peers...");
			return true;
		}
		return false;
	}
	
	private static synchronized int addPlayerToSlot(PlayerThread player) {
		if(elementsNumber[0]<3) {
			for(int i=0; i<slot1.length; i++) {
				if(slot1[i]==null) {
					slot1[i] = player;
					elementsNumber[0]++;
					System.out.println("New player added to slot 1: "+player);
					if(elementsNumber[0]==3) {
						startGame(slot1,0);
						return 0;
					}
					return 1;
				}
			}
		}
		
		if(elementsNumber[1]<3) {
			for(int i=0; i<slot2.length; i++) {
				if(slot2[i]==null) {
					slot2[i] = player;
					elementsNumber[1]++;
					System.out.println("New player added to slot 2: "+player);
					if(elementsNumber[1]==3) {
						startGame(slot2,1);
						return 0;
					}
					return 1;
				}
			}
		}

		if(elementsNumber[2]<3) {
			for(int i=0; i<slot3.length; i++) {
				if(slot3[i]==null) {
					slot3[i] = player;
					elementsNumber[2]++;
					System.out.println("New player added to slot 3: "+player);
					if(elementsNumber[2]==3) {
						startGame(slot3,2);
						return 0;
					}
					return 1;
				}
			}
		}

		if(elementsNumber[3]<3) {
			for(int i=0; i<slot4.length; i++) {
				if(slot4[i]==null) {
					slot4[i] = player;
					elementsNumber[3]++;
					System.out.println("New player added to slot 4: "+player);
					if(elementsNumber[3]==3) {
						startGame(slot4,3);
						return 0;
					}
					return 1;
				}
			}
		}
		
		return -1;
	}
	
	
	private static synchronized void startGame(PlayerThread[] slot, int slotID) {
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
		emptySlot(slot, slotID);
		
	}
	
	private static synchronized void emptySlot(PlayerThread[] slot, int slotID) {
		for(int i=0; i<slot.length; i++) {
			slot[i] = null;
		}
		elementsNumber[slotID]=0;
		displaySlot(slot);
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
