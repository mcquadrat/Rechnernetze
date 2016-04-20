package echoserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {
	
	private static boolean b = true;
	
	public static void stop(){
		b = false;
	}
	
	  public static void main(String argv[]) throws Exception {
		    String sentence;
		    boolean flag = true;
		    Socket clientSocket = new Socket("localhost", 6798);
		    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	    	DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	    	DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
	    	ClientConnection clientCon = new ClientConnection(inFromServer);
	    	clientCon.start();
		    while(b){
			    
		    	
		    	sentence = inFromUser.readLine();
		    	outToServer.writeUTF(sentence + '\n');
		    }
		    clientSocket.close();
		  }
		}
