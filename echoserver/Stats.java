package echoserver;

public class Stats {
	private int zeichenanzahl;
	private int nachrichtenanzahl;
	private int kurz;
	private int lang;
	
	public Stats(){
		zeichenanzahl = 0;
		nachrichtenanzahl = 0;
		kurz = -1;
		lang = 0;
	}
	
	public void addNachricht(String data){
		int length = data.length();
		if(kurz == -1 || kurz > length){
			kurz = length;
		}
		if(lang < length){
			lang = length;
		}
		nachrichtenanzahl++;
		zeichenanzahl += length;
	}
	
	@Override
	public String toString(){
		return "Zeichenanzahl: "+zeichenanzahl
				+"\nNachrichtenanzahl: "+nachrichtenanzahl
				+"\nKuerzeste Nachricht: "+kurz
				+"\nLaengste Nachricht: "+lang+"\n";
	}
}
