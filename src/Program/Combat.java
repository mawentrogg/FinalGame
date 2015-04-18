package program;

import characters.*;
import equipment.*;
import gameplay.*;

public class Combat {

	private final Player[] players;
	private final NPC[] monsters;
	private Person[] turnOrder;
	
	private int iteratorIndex = 0;
	
	public Combat(Player[] players, NPC[] monsters) {
		super();
		this.players = players;
		this.monsters = monsters;
		turnOrder = new Person[monsters.length + players.length];
		makeTurnOrder();
	}
	
	public void start(){
		
	}
	
	public NPC[] getMonsters(){
		return monsters;
	}
	
	public void nextTurn(){
		
	}
	
	
	//Returns the next character to have its turn.
	public Person getNext(){
		Person personToReturn = turnOrder[iteratorIndex];
		if(iteratorIndex == players.length + monsters.length)
		iteratorIndex++;
		return personToReturn;
	}
	
	private void makeTurnOrder(){
		int length = players.length + monsters.length;
		for(int i = 0; i < length; i++){
			if(i < players.length){
				turnOrder[i] = players[i];				
			}
			else{
				turnOrder[i] = monsters[i - players.length];
			}
		}
	}
	
	public static void main(String[] args) {
		Inventory inventory = new Inventory(20);
		Equipped equipped = new Equipped();
		Player player = new Player(inventory, equipped, 50, 100, 100, 0, 1, "andre");
		Player player1 = new Player(inventory, equipped, 50, 100, 100, 0, 1, "andre1");
		Player player2 = new Player(inventory, equipped, 50, 100, 100, 0, 1, "andre2");
		Player player3 = new Player(inventory, equipped, 50, 100, 100, 0, 1, "andre3");
		
		NPC monster =  new NPC(50, 500, 0, 3, 1, "m1");
		NPC monster1 =  new NPC(50, 500, 0, 3, 1, "m2");
		NPC monster2 =  new NPC(50, 500, 0, 3, 1, "m3");
		NPC monster3 =  new NPC(50, 500, 0, 3, 1, "m4");
		
		
		Player[] players = {player, player1, player2, player3};
		NPC[] monsters = {monster, monster1, monster2, monster3};
		Combat combat =  new Combat(players, monsters);
		for(int i = 0; i < 8; i++){
			System.out.println(combat.getNext().getName());
		}
	}
}
