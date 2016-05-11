package simon;

import java.io.IOException;

import de.root1.simon.Registry;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.NameBindingException;
import enums.State;

@SimonRemote
public class ServerImplementation implements ServerInterface{

	private ClientCallbackInterface cCI;
	private int[][] field = new int[3][3];
	
	public static void main(String[] args) throws NameBindingException, IOException {
		
		Registry registry = Simon.createRegistry();
		registry.start();
		registry.bind("server", new ServerImplementation());
		
	}

	@Override
	public void login(ClientCallbackInterface clientCallbackInterface) {
		this.cCI = clientCallbackInterface;
		int[][] f = {{0, 0, 0},{0, 0, 0},{0, 0, 0}};
		field = f;
		
	}

	@Override
	public String getStringBoard() {
		String s = "";
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				s += field[i][j]+" ";
			}
			s += "\n";
		}
		return s;
	}

	@Override
	public State place(int row, int column) {
		if (field[row][column] == 0){
			field[row][column] = 1;
			cCI.callback(null);
			if (this.getState() != State.NOT_DONE){
				return this.getState();
			}
			boolean b = true;
			int r = -1;
			int c = -1;
			while (b){
				r = (int)(Math.random()*3);
				c = (int)(Math.random()*3);
				if (field[r][c] == 0){
					b = false;
				}
			}
			field[r][c] = 2;
			cCI.callback(null);
			
			return this.getState();
		} else {
			return State.FIELD_NOT_EMPTY;
		}
	}

	@Override
	public State getState() {
		if(field[0][0]*field[0][1]*field[0][2] == 1 || field[1][0]*field[1][1]*field[1][2] == 1 || field[2][0]*field[2][1]*field[2][2] == 1 
				|| field[0][0]*field[1][0]*field[2][0] == 1 || field[0][1]*field[1][1]*field[2][1] == 1 || field[0][2]*field[1][2]*field[2][2] == 1 
				|| field[0][0]*field[1][1]*field[2][2] == 1 || field[2][0]*field[1][1]*field[0][2] == 1){
			return State.WINNER_HUMAN;
		}
		if(field[0][0]*field[0][1]*field[0][2] == 8 || field[1][0]*field[1][1]*field[1][2] == 8 || field[2][0]*field[2][1]*field[2][2] == 8 
				|| field[0][0]*field[1][0]*field[2][0] == 8 || field[0][1]*field[1][1]*field[2][1] == 8 || field[0][2]*field[1][2]*field[2][2] == 8 
				|| field[0][0]*field[1][1]*field[2][2] == 8 || field[2][0]*field[1][1]*field[0][2] == 8){
			return State.WINNER_AI;
		}
		if(field[0][0]*field[0][1]*field[0][2]*field[1][0]*field[1][1]*field[1][2]*field[2][0]*field[2][1]*field[2][2] == 0){
			return State.NOT_DONE;
		}
		return State.DRAW;
	}

}
