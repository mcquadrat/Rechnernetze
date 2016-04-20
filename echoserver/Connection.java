package echoserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    public Stats s;
    public static ArrayList<Connection> list = new ArrayList<>();
    
    public Connection (Socket aClientSocket, Stats s) {
      this.s = s;
      try {
    	  		 list.add(this);
                 clientSocket = aClientSocket;
                 in = new DataInputStream( clientSocket.getInputStream());
                 out = new DataOutputStream( clientSocket.getOutputStream());
      } catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
    }
    
    public void run(){
      try {// an echo server
    	  	while(true){
                 String data = in.readUTF();
                 s.addNachricht(data.trim());
                 if (data.trim().equals("exit")){
                	 TCPClient.stop();
                	 break;
                 }
                 if (data.trim().startsWith("showstat")){
                	 out.writeUTF(s.toString());
                 }
                 if (data.trim().startsWith("showallstat")){
                	 for(Connection c : list){
                		 out.writeUTF(c.s.toString());
                	 }
                 }
                 int port = clientSocket.getPort();
   		      	 byte[] address = clientSocket.getLocalAddress().getAddress();
   		      	 String localAddress = "";
   		      	 for(int i = 0; i < 3; i++){
   		      		 localAddress += address[i]+".";
   		      	 }
   		      	 localAddress += address[3];
   		      	 
   		      	 System.out.println("(IP:"+localAddress+" | Port:"+port+" )  "+data);

                 if (data.trim().startsWith("broadcast")){
                	 data = data.trim().substring(10); // Ein Leerzeichen oder Doppelpunkt dazwischen
                	 for(Connection c : TCPServer.conList){
                		 c.out.writeUTF("From: (IP:"+localAddress+" | Port:"+port+" )  "+data);
                	 }
                 } else {
                	 out.writeUTF("From Server: "+data);
                 }
    	  	}
      } catch(EOFException e) {System.out.println("EOF:"+e.getMessage()); e.printStackTrace();
    } catch(IOException e) {System.out.println("IO:"+e.getMessage()); e.printStackTrace();}
  }
}

