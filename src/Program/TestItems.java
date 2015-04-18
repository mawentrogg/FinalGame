package program;

import characters.*;
import equipment.*;

public class TestItems {

	public void printStats(Player player){
		System.out.println("Health: " + player.getHealth() + " Armor: " + player.getArmor() + " Mana: " + player.getMana() + " Damage: " + player.getDamage() );
	}
	
	public static void main(String[] args) {
		TestItems t = new TestItems();
		Inventory inventory = new Inventory(20);
		Equipped equipped = new Equipped();
		Player player = new Player(inventory, equipped, 50, 100, 100, 0, 1, "andre");
		Armor head = new Armor(50, 0, 10, 0, "head");
		Weapon sword = new Weapon(500, 10, 0, 5, "Sword");
		player.addItem(head);
		player.equipItem(0);
		player.addItem(sword);
		player.equipItem(0);
		t.printStats(player);
		player.unEquipItem(0);
		player.unEquipItem(5);
		t.printStats(player);
	}
	
}
