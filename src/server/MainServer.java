package server;

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

	private static PlayerThread[] slot1 = new  PlayerThread[3];
	private static PlayerThread[] slot2 = new  PlayerThread[3];
	private static PlayerThread[] slot3 = new  PlayerThread[3];
	private static PlayerThread[] slot4 = new  PlayerThread[3];
	
	/**
	 * Method adds new player to first free slot.
	 * @param userName 
	 * @param ipAddress
	 * @param port
	 * @return boolean
	 */
	public static synchronized boolean addNewPlayer(PlayerThread player) {
		
		int playerAddedToSlot = addPlayerToSlot(player);
		if(playerAddedToSlot!=-1) return true;
		return false;
	}
	
	
	/**
	 * Method adds new player to first free slot.
	 * @param userName 
	 * @param ipAddress
	 * @param port
	 * @return number that represents slot or -1 if there is no free spot in slots.
	 */
	public static int addPlayerToSlot(PlayerThread player) {
		if(slot1.length<3) {
			for (PlayerThread p : slot1) {
				if(p==null) {
					p = player;
					if(slot1.length==3) {
						// startGame(slot1)
					}
					return 1;
				}
			}
		}
		if(slot2.length<3) {
			for (PlayerThread p : slot2) {
				if(p==null) {
					p = player;
					if(slot1.length==3) {
						// startGame(slot1)
					}
					return 2;
				}
			}
		}

		if(slot3.length<3) {
			for (PlayerThread p : slot3) {
				if(p==null) {
					p = player;
					if(slot1.length==3) {
						// startGame(slot1)
					}
					return 3;
				}
			}
		}

		if(slot4.length<3) {
			for (PlayerThread p : slot4) {
				if(p==null) {
					p = player;
					if(slot1.length==3) {
						// startGame(slot1)
					}
					return 4;
				}
			}
		}
		return -1;
	}
	
	
	
	// int indeks slota
	public static synchronized void startGame(PlayerThread[] slot) {
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
		
	}
	
	public void emptySlot(PlayerThread[] slot) {
		for(int i=0; i<slot.length; i++) {
			slot[i] = null;
		}
	}
	
	public static void main(String[] args) {
		
	}
}
