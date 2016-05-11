package simon;

import enums.State;

import java.net.UnknownHostException;
import java.util.Scanner;

import de.root1.simon.Lookup;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;

@SimonRemote
public class ClientImplementation implements ClientCallbackInterface {


	private ServerInterface server;
	
	
	public ClientImplementation(){
		
		Lookup nameLookup;
		try {
			nameLookup = Simon.createNameLookup("localhost");
			server = (ServerInterface) nameLookup.lookup("server");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LookupFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EstablishConnectionFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		server.login(this);
		
		boolean b = true;
		Scanner sc = new Scanner(System.in);
		while (b){
			System.out.print("Gib die Zeile ein: ");
			int row = sc.nextInt();
			System.out.print("Gib die Spalte ein: ");
			int column = sc.nextInt();
			State s = server.place(row, column);
			System.out.println(s);
			if(s != State.NOT_DONE){
				System.out.println("Ende \n Noch ein Spiel? (1 : Ja  -  2 : Nein)");
				int i = sc.nextInt();
				if(i == 1){
					server.login(this);
				} else {
					b = false;
				}
			}
		}
		sc.close();
		System.exit(0);
	}

	@Override
	public void callback(int[] move) {
		System.out.println(server.getStringBoard());
	}

	public static void main(String[] args) {
		new ClientImplementation();

	}

}
