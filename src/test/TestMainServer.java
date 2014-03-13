package test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestMainServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		BattleShipMainServer p = new BattleShipMainServer();
//		
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		
//		try {
//			String message = in.readLine();
//			p.parseProtocolMessage(message);
//			System.out.println(p.responseMessage());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			@SuppressWarnings("resource")
			Socket s = new Socket("localhost", 9080);

			BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			DataOutputStream out = new DataOutputStream(s.getOutputStream());


			while(true) {
				String i = "";
				String m = c.readLine();
				m = m + '\n';
				out.writeBytes(m);
				System.out.println("Sent to server: "+m);

				while(true) {
					i = in.readLine();
					if(i.contains("WAIT")) {
						continue;
					} else {
						break;
					}
				}
				System.out.println("Received from server: "+i);
			}

//			s.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
