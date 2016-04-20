package echoserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import generatedFromXsd.EchoMessage;
import generatedFromXsd.EchoMessageType;
import generatedFromXsd.ObjectFactory;

public class TCPClient {
	
	private static boolean b = true;
	
	public static void stop(){
		b = false;
	}
	
	  public static void main(String argv[]) throws Exception {
		    ObjectFactory of = new ObjectFactory();
			JAXBContext jaxb = JAXBContext.newInstance(EchoMessage.class);
			Marshaller marshaller = jaxb.createMarshaller();
			//Unmarshaller unmarshaller = jaxb.createUnmarshaller();
		  
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
		    	EchoMessage em = of.createEchoMessage();
		    	if (sentence.trim().startsWith("showstat")){
		    		em.setType(EchoMessageType.SHOWSTAT);
		    		sentence = sentence.substring(9);
		    	} else if (sentence.trim().startsWith("showallstat")){
		    		em.setType(EchoMessageType.SHOWALLSTAT);
		    		sentence = sentence.substring(12);
		    	} else if (sentence.trim().startsWith("broadcast")){
		    		em.setType(EchoMessageType.BROADCAST);
		    		sentence = sentence.substring(10);
		    	} else if (sentence.trim().startsWith("exit")){
		    		em.setType(EchoMessageType.EXIT);
		    		sentence = sentence.substring(5);
		    	} else if (sentence.trim().startsWith("shutdown")){
		    		em.setType(EchoMessageType.SHUTDOWN);
		    		sentence = sentence.substring(9);
		    	} else {
		    		em.setType(EchoMessageType.DEFAULT);
		    	}
		    	em.setContent(sentence);
		    	marshaller.marshal(em, outToServer);
		    	//outToServer.writeUTF(sentence + '\n');
		    }
		    clientSocket.close();
		  }
		}
