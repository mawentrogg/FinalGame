package program;

import characters.*;
import equipment.*;
import gameplay.*;

public class Game {

	private final Player[] players;
	
	
	
	public Game(Player[] players) {
		super();
		this.players = players;
	}
	
	public void combat(NPC[] monsters){
		Combat combat = new Combat(players, monsters);
	}

}
