package bookclient;

import java.io.Serializable;

public class Autor implements Serializable{
	private String vorname;
	private String nachname;
	
	private Autor(){};
	
	public Autor(String v, String n){
		vorname=v;
		nachname=n;
	}
	public String getVorname(){
		return vorname;
	}
	public String getNachname(){
		return nachname;
	}
	
}
