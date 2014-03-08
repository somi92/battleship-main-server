package protocol;

public class BattleShipMainServer {

	private static final int INIT = 0;
	private static final int SEARCH = 1;
	private static final int ERROR = 2;
	private static final int START = 3;
	private static final int BYE = 4; 
	
	
	private int state;
	private String inputMessage;
	private String outputMessage;
	
	private String IP;
	private int port;
	private String userName;
	
	public BattleShipMainServer() {
		setState(BattleShipMainServer.INIT);
	}
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getInputMessage() {
		return inputMessage;
	}
	public void setInputMessage(String inputMessage) {
		this.inputMessage = inputMessage;
	}
	public String getOutputMessage() {
		return outputMessage;
	}
	public void setOutputMessage(String outputMessage) {
		this.outputMessage = outputMessage;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	// pick the state
	public int parseProtocolMessage(String message) {
		
		String[] messageParts = message.split("_");
		
		if(messageParts.length==1) {
			String protocolMethod = messageParts[0];
			
			if(protocolMethod.equals("REPEAT")){
				setState(BattleShipMainServer.SEARCH);
				return BattleShipMainServer.SEARCH;
			}		
			if(protocolMethod.equals("START")){
				setState(BattleShipMainServer.BYE);
				return BattleShipMainServer.BYE;
			}		
			setState(BattleShipMainServer.ERROR);
			return BattleShipMainServer.ERROR;	
		}
		
		if(messageParts.length==3) {
						
			String protocolMethod = messageParts[0];
			String IPAndPort  = messageParts[1];
			
			if(protocolMethod.equals("HELLO") && IPAndPort.contains(":")){
			
				String[] IPAndPortParse = IPAndPort.split(":");
				setIP(IPAndPortParse[0]);
				setPort(Integer.parseInt(IPAndPortParse[1]));
				setUserName(messageParts[2]);
				setState(BattleShipMainServer.SEARCH);
				return BattleShipMainServer.SEARCH;
			}
			
			setState(BattleShipMainServer.ERROR);
			return BattleShipMainServer.ERROR;		
		}
		
		setState(BattleShipMainServer.ERROR);
		return BattleShipMainServer.ERROR;	
	}
	
	
	public String responseMessage(){

		if(getState() == BattleShipMainServer.SEARCH) {
			return "WAIT";
		}
		if(getState() == BattleShipMainServer.BYE) {
			return "BYE";
		}
		return "ERROR";
	}
	
	public String responseMessage(String IP1, String port1, String IP2, String port2){
	
		
		setState(BattleShipMainServer.START);
		return "PEERS_"+IP1+":"+port1+"_"+IP2+":"+port2+'\n';
	
	}
}
