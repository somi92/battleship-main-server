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

	private static Player[] slot1 = new  Player[3];
	private static Player[] slot2 = new  Player[3];
	private static Player[] slot3 = new  Player[3];
	private static Player[] slot4 = new  Player[3];
	
	/**
	 * Method adds new player to first free slot.
	 * @param userName 
	 * @param ipAddress
	 * @param port
	 * @return boolean
	 */
	public static synchronized boolean addNewPlayer(String userName, String ipAddress, int port) {
		
		int playerAddedToSlot = addPlayerToSlot(userName, ipAddress, port);
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
	public static int addPlayerToSlot(String userName, String ipAddress, int port) {
		if(slot1.length<3) {
			for (Player p : slot1) {
				if(p==null) {
					p = new Player(userName, ipAddress, port);
					if(slot1.length==3) {
						// startGame(slot1)
					}
					return 1;
				}
			}
		}
		if(slot2.length<3) {
			for (Player p : slot2) {
				if(p==null) {
					p = new Player(userName, ipAddress, port);
					if(slot1.length==3) {
						// startGame(slot1)
					}
					return 2;
				}
			}
		}

		if(slot3.length<3) {
			for (Player p : slot3) {
				if(p==null) {
					p = new Player(userName, ipAddress, port);
					if(slot1.length==3) {
						// startGame(slot1)
					}
					return 3;
				}
			}
		}

		if(slot4.length<3) {
			for (Player p : slot4) {
				if(p==null) {
					p = new Player(userName, ipAddress, port);
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
	public static synchronized int startGame(Player[] slot) {
		return 0;
	}
	
	public static void main(String[] args) {
		
	}
}
