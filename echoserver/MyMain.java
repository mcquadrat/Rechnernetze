package echoserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import generatedFromXsd.EchoMessage;
import generatedFromXsd.EchoMessageType;
import generatedFromXsd.ObjectFactory;

public class MyMain {

	public static void main(String[] args) throws JAXBException, FileNotFoundException {
		ObjectFactory of = new ObjectFactory();
		JAXBContext jaxb = JAXBContext.newInstance(EchoMessage.class);
		Marshaller marshaller = jaxb.createMarshaller();
		Unmarshaller unmarshaller = jaxb.createUnmarshaller();
		
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		EchoMessage em = of.createEchoMessage();
		
		em.setType(EchoMessageType.DEFAULT);
		em.setContent("Blabla");
		
		marshaller.marshal(em, System.out);
		marshaller.marshal(em, new FileOutputStream(new File("test.xml")));
		
		System.out.println("1");
		EchoMessage em2 = (EchoMessage) unmarshaller.unmarshal(new FileInputStream(new File("test.xml")));
		System.out.println("2");
		System.out.println(em2.getContent());

	}

}
