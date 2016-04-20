package echoserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer {
	
	public static ArrayList<Connection> conList;
	  public static void main(String argv[]) throws Exception {
		    ServerSocket welcomeSocket = new ServerSocket(6798);
		    conList = new ArrayList<>();
		    while(true) {
		      Socket connectionSocket = welcomeSocket.accept();
		      Stats s = new Stats();
		      Connection c = new Connection(connectionSocket, s);
		      conList.add(c);
		      c.start();
		 
		    }
		  }
		}
