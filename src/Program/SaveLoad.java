package program;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;






import characters.Player;
import equipment.Armor;
import equipment.Equipped;
import equipment.Inventory;
import equipment.Item;
import equipment.Weapon;


public class SaveLoad {
	
	private Player player;
	private Armor armor;
	private Weapon weapon;
	private Inventory inventory;
	private Equipped equipment;
	private String sep = " ";
	private String sep2 = "--";
	
	
	public void saveInventory(Player player){
		resetFile(player.getName(), "Inventory");
		PrintWriter out;
		try {
			/*out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\Eier\\Dropbox\\Vidya\\Logikk\\Vidya\\Res\\Inventory\\Inventory"
					+ player.getName()+".txt", true))); */
			out = new PrintWriter(new BufferedWriter(new FileWriter("Res/Game/Inventory/InventoryTest" + player.getName()+".txt", true)));
			Item[] items = player.getInventory().getInventoryItems();
			out.print("Items:");
			try{
				for (int i = 0; i < items.length; i ++){
					out.print(sep +  items[i].getName() + sep2 + items[i].getType());
				}
			}
			catch(NullPointerException e){
				System.out.println("No items in inventory.");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public Inventory loadInventory(String player){
		try{
			Inventory inventory = new Inventory(20);
			Scanner file = new Scanner(new FileReader("C:\\Users\\Eier\\Dropbox\\Vidya\\Logikk\\Vidya\\Res\\Inventory\\Inventory"+ player + ".txt"));
			while(file.hasNext()){
				String[] splits = file.nextLine().split(sep);
				try{
					for (int i = 1; i < splits.length; i++){
						String[] splits2 = splits[i].split(sep2);
						int type = Integer.parseInt(splits2[1]);
						if(0 < type && type <= 5){
							inventory.addItem(loadArmor(splits2[0]));
						}
						else if (type <= 7){
							inventory.addItem(loadWeapon(splits2[0]));
						}
						//else if (type <= 9){
							//inventory.addItem(loadConsumable(splits2[0]));
						//}
					}
						
				}
				catch(NullPointerException e){
					System.out.println("No items found.");
				}
			}
			file.close();
			System.out.println("Character " + player + " loaded.");
			return this.inventory;
		}
		catch(FileNotFoundException e){
			System.out.println("File not found: Inventory"+ player + ".txt");
		}
		return null;
		
	}
	//Utility for å resette txt.
	private void resetFile(String filnavn, String SBdel){
		
	}
		/*PrintWriter reset;
		try {
			reset = new PrintWriter("C:\\Users\\Eier\\Dropbox\\Vidya\\Logikk\\Vidya\\Res\\"+ SBdel + "\\" + SBdel + filnavn +".txt");
			reset.print("");
		}
		catch(IOException e){
			System.out.println("noe gikk galt, (resetfile)");
		}
	}*/
	public void saveEquipment(Player player){
		PrintWriter out;
		resetFile(player.getName(), "Equipment");
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\Eier\\Dropbox\\Vidya\\Logikk\\Vidya\\Res\\Equipment\\Equipment"
					+ player.getName()+".txt", true)));
			Item[] equips = player.getEquipped().getEquippedItems();
			out.print("Equipped:"); 
			try{
				
				for (int i = 0; i < equips.length; i++){
					out.print(sep + equips[i].getName() + sep + equips[i].getType());
				}
			}
			
			catch(NullPointerException e){
				System.out.println("No equipped items.");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public Equipped loadEquipment(String player){
		try{
			Equipped equipment = new Equipped();
			Scanner file = new Scanner(new FileReader("C:\\Users\\Eier\\Dropbox\\Vidya\\Logikk\\Vidya\\Res\\Inventory\\Inventory"+ player + ".txt"));
			while(file.hasNext()){
				String[] splits = file.nextLine().split(sep);
				try{
					for (int i = 1; i < splits.length; i++){
						String[] splits2 = splits[i].split(sep2);
						int type = Integer.parseInt(splits2[1]);
						if(0 < type && type <= 5){
							equipment.equipItem(loadArmor(splits2[0]));
						}
						else if (type <= 7){
							equipment.equipItem(loadWeapon(splits2[0]));
						}
						//else if (type <= 9){
							//inventory.addItem(loadConsumable(splits2[0]));
						//}
					}
						
				}
				catch(NullPointerException e){
					System.out.println("No equipment found.");
				}
			}
			file.close();
			System.out.println("Character " + player + " loaded.");
			return this.equipment;
		}
		catch(FileNotFoundException e){
			System.out.println("File not found: Inventory"+ player + ".txt");
		}
		return null;
	}
	
	public void saveCharacter(Player player){
		resetFile(player.getName(), "Character");
		try {//Lagrer player på formatet navn, level, health, mana, damage, armor. 
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\Eier\\Dropbox\\Vidya\\Logikk\\Vidya\\Res\\Character\\Character"
					+ player.getName()+".txt", true)));
			
			out.println(player.getName() + sep + player.getLevel() + sep + player.getHealth() + sep + 
					player.getMana() + sep + player.getDamage() + sep + player.getArmor());
			
			saveEquipment(player);
			saveInventory(player);
			out.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Something went wrong(SaveLoad 47).");
			e.printStackTrace();
		}
		
		
	}
	public Player loadCharacter(String name){
		try{
			Scanner file = new Scanner(new FileReader("C:\\Users\\Eier\\Dropbox\\Vidya\\Logikk\\Vidya\\Res\\Character\\Character"+ name + ".txt"));
			while(file.hasNext()){
				String[] splits = file.nextLine().split(sep);
				String nameread = splits[0];
				int level = Integer.parseInt(splits[1]);
				int health = Integer.parseInt(splits[2]);
				int mana = Integer.parseInt(splits[3]);
				int damage = Integer.parseInt(splits[4]);
				int armor = Integer.parseInt(splits[5]);
				Inventory inventory = loadInventory(nameread);
				Equipped equipped = loadEquipment(nameread);
				
				player = new Player(inventory, equipped, damage, health, mana, armor, level, nameread, null);
			}
			file.close();
			System.out.println("Character " + name + " loaded.");
			return player;
		}
		catch(FileNotFoundException e){
			System.out.println("File not found: Character"+ name + ".txt");
			return null;
		}
		
	}
	
	public void saveArmor(Armor armor){
		//resetFile(armor.getName(), "Armor");
		try {
			PrintWriter out = new PrintWriter("res/Game/Armor/Armor" + armor.getName()+".txt");
			out.print(armor.getName() + sep + armor.getType() + sep + armor.getHealth() + sep + armor.getMana() + sep + armor.getArmor());
			out.close();
			System.out.println("Armor " + armor.getName() + " saved.");
		} catch (FileNotFoundException e) {
			System.out.println("File not found, saveArmor." + e);
			e.printStackTrace();
			System.out.println("The file could not be opened." + e);
		}
		
		
	}
	
	public Armor loadArmor(String nameOfArmor){
		try {
			Scanner file = new Scanner(new FileReader("C:\\Users\\Eier\\Dropbox\\Vidya\\Logikk\\Vidya\\Res\\Armor\\Armor"+ nameOfArmor + ".txt"));
			while(file.hasNext()){
				String[] splits = file.nextLine().split(" ");
				String nameread = splits[0];
				int type = Integer.parseInt(splits[1]);
				int health = Integer.parseInt(splits[2]);
				int mana = Integer.parseInt(splits[3]);
				int armorvalue = Integer.parseInt(splits[4]);
				armor = new Armor(health, mana, armorvalue, type, nameread);
				file.close();
				System.out.println("Armor " + nameOfArmor + " loaded.");
				return armor;
			}
			
			file.close();
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found, loadArmor." + e);
			e.printStackTrace();
			
		}
		
		
		return null;
	}
	
	public void saveWeapon(Weapon weapon){
		//resetFile(weapon.getName(), "Weapon");
		try {
			PrintWriter out = new PrintWriter("res/Game/Weapon/Weapon" + weapon.getName()+".txt");
			out.print(weapon.getName() + sep + weapon.getType() + sep + weapon.getDamage() + sep + weapon.getMana() + sep + weapon.getArmor());
			out.close();
			System.out.println("Weapon " + weapon.getName() + " saved.");
		} catch (FileNotFoundException e) {
			System.out.println("File not found, saveWeapon." + e);
			e.printStackTrace();
			System.out.println("The file could not be opened.");
		}
		
		
	}
	
	public Weapon loadWeapon(String nameOfWeapon){
		try {
			Scanner file = new Scanner(new FileReader("C:\\Users\\Eier\\Dropbox\\Vidya\\Logikk\\Vidya\\Res\\Weapon\\Weapon"+ nameOfWeapon + ".txt"));
			while(file.hasNext()){
				String[] splits = file.nextLine().split(" ");
				String nameread = splits[0];
				int type = Integer.parseInt(splits[1]);
				int damage = Integer.parseInt(splits[2]);
				int mana = Integer.parseInt(splits[3]);
				int armor = Integer.parseInt(splits[4]);
				weapon = new Weapon(damage, mana, armor, type, nameread);
				file.close();
				System.out.println("Weapon " + nameOfWeapon + " loaded.");
				return weapon;
			}
			
			file.close();
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found: Weapon"+ nameOfWeapon + ".txt");
			e.printStackTrace();
			
		}
		
		
		return null;
	}


}
