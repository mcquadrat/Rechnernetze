package echoserver;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import generatedFromXsd.EchoMessage;
import generatedFromXsd.EchoMessageType;
import generatedFromXsd.ObjectFactory;

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

    	    ObjectFactory of = new ObjectFactory();
    		JAXBContext jaxb;
			jaxb = JAXBContext.newInstance(EchoMessage.class);
			
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			
    	  	while(true){
    	  		String message = in.readUTF();
    	  		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
    					message.getBytes());
    	  		EchoMessage em = (EchoMessage) unmarshaller.unmarshal(byteArrayInputStream);
                 String data = em.getContent();
                 s.addNachricht(data.trim());
                 if (em.getType().equals(EchoMessageType.EXIT)){
                	 TCPClient.stop();
                	 break;
                 }
                 if (em.getType().equals(EchoMessageType.SHOWSTAT)){
                	 out.writeUTF(s.toString());
                 }
                 if (em.getType().equals(EchoMessageType.SHOWALLSTAT)){
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

                 if (em.getType().equals(EchoMessageType.BROADCAST)){
                	 for(Connection c : TCPServer.conList){
                		 c.out.writeUTF("From: (IP:"+localAddress+" | Port:"+port+" )  "+data);
                	 }
                 } else {
                	 out.writeUTF("From Server: "+data);
                 }
    	  	}
      } catch(EOFException e) {System.out.println("EOF:"+e.getMessage()); e.printStackTrace();
    } catch(IOException e) {System.out.println("IO:"+e.getMessage()); e.printStackTrace();}
       catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
  }
}

