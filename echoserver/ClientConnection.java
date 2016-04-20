package echoserver;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientConnection extends Thread {
	
	private DataInputStream inFromServer;
	
	public ClientConnection(DataInputStream inFromServer){
		this.inFromServer = inFromServer;
	}
	
	@Override
	public void run(){
	    String modifiedSentence;
		while(true){
	    	try {
				modifiedSentence = inFromServer.readUTF();
		    	System.out.println(modifiedSentence);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
