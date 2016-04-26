package bookclient;

import java.io.Serializable;

public class Book implements Serializable {
	private long isbn;
	private Autor autor;
	private String title;
	
	private Book(){};
	
	public Book(long i, Autor a, String t){
		isbn=i;
		autor=a;
		title=t;
	}
	
	public long getIsbn(){
		return isbn;
	}
	public Autor getAutor(){
		return autor;
	}
	public String getTitle(){
		return title;
	}
	
	public String toString(){
		return "ISBN: "+isbn+" Autor: "+autor.getVorname()+" "+autor.getNachname()+" Titel: "+title;
	}
}
