package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import protocol.BattleShipMainServer;

public class TestMainServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BattleShipMainServer p = new BattleShipMainServer();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			String message = in.readLine();
			p.parseProtocolMessage(message);
			System.out.println(p.responseMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
