package bookclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Scanner;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
 
public class BookClient implements Serializable{
 
  // TODO: declare book container
  static File bookFile = new File("src/bookclient/bookFile");
  static Scanner scanner = new Scanner(System.in);
  static ArrayList<Book> books;
 
  public static void main(String[] args) {
	
    int input = -1;
    while (input != 0) {
      System.out.println("Choose");
      System.out.println(" (0) Quit program");
      System.out.println(" (1) Load books from file");
      System.out.println(" (2) Show Books");
      System.out.println(" (3) Add Book");
      System.out.println(" (4) Delete Book");
      System.out.println(" (5) Save books in file");
      input = scanner.nextInt();
      switch (input) {
        case 1:
        loadBooks(bookFile);
        break;
        case 2:
        showBooks();
        break;
        case 3:
        addBook();
        break;
        case 4:
        deleteBook();
        break;
        case 5:
        saveBooks(bookFile);
        break;
      }
    }
  }
  
  
  public static void loadBooks(File server){
	  books = new ArrayList<>();
	    try {
	    	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(server));
//			Scanner sc = new Scanner(server);
	    	
			while(true){
				String s = (String) ois.readObject();
				books.add(new JSONDeserializer<Book>().deserialize(s, Book.class));
				//Erste Methode:
//				String[] s = ((String) ois.readObject()).split(";");
//				books.add(new Book(Long.parseLong(s[0]), new Autor(s[1], s[2]), s[3]));
			}
//			sc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
 
  public static void showBooks() {
    for(int i = 0; i < books.size(); i++){
    	System.out.println("Buch "+(i+1)+": "+books.get(i));
    }
  }
 
  public static void addBook() {
    books.add(new Book((int)(Math.random()*1000000), new Autor("Justin", "Bieber"), "Sorry"));
  }
 
  public static void deleteBook() {
	System.out.println("Welches Buch?");
	Scanner sc = new Scanner(System.in);
	int i = sc.nextInt();
    books.remove(i-1);
    sc.close();
  }
  
  public static void saveBooks(File server) {
	    try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(server));
			JSONSerializer jsons = new JSONSerializer();
			for(Book b : books){
				oos.writeObject(jsons.serialize(b));
				//Erste Methode:  
//				oos.writeObject(b.getIsbn()+";"+b.getAutor().getVorname()+";"+b.getAutor().getNachname()+";"+b.getTitle());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }



 
}
