package program;
import characters.Person;
import characters.Player;
import equipment.Armor;
import equipment.Equipped;
import equipment.Inventory;
import equipment.Item;
import program.SaveLoad;
import equipment.Weapon;


public class TestProgram {

	

	
	public void attack(Person attacker, Person defender){
		defender.takeDamage(attacker.getDamage());
		System.out.println(defender.getName() + " took " + defender.getLastHitTaken() + " damage from " + attacker.getName());
		System.out.println("Remaining HP of " + defender.getName() + " is " + defender.getHealth());
		System.out.println("");
	}
	
	/*TYPE: 
	 * 0 - Head.
	 * 1 - Chest.
	 * 2 - Hands.
	 * 3 - Legs.
	 * 4 - Feet.
	 * 5 - Weapon.
	 * 6 - Shield.
	 * 7 - HP potion.
	 * 8 - MP potion.
	 */
	
	public static void main(String[] args) {
		Player Kim = new Player(new Inventory(20), new Equipped(), 50, 300, 200, 30, 2, "Kim" , null);
		Kim.addItem(new Armor(50, 10, 10, 0, "IronHead"));
		Kim.addItem(new Armor(50, 30, 15, 1, "IronChest"));
		Kim.addItem(new Armor(30, 10, 8, 2, "IronGloves"));
		Kim.addItem(new Armor(50, 20, 10, 3, "IronLegs"));
		Kim.addItem(new Armor(20, 10, 5, 4, "IronBoots"));
		Kim.addItem(new Weapon(60, 10, 0, 5, "IronSword"));
		//Kim.equipItem(0);
		
		for (int i = 0; i < 6; i++) {
			Kim.equipItem(i);
		}
		SaveLoad save = new SaveLoad();
		for (int i = 0; i < 5; i++){
			if (Kim.getEquipped().getItem(i) == null){
				
			}
			else{
				save.saveArmor((Armor) (Kim.getEquipped().getItem(i)));
			}
		}
		for (int i = 5; i < 7; i++){
			if (Kim.getEquipped().getItem(i) == null){
				
			}
			else{
				save.saveWeapon((Weapon) (Kim.getEquipped().getItem(i)));
			}
			
		}
		
		
		//save.saveInventory(Kim);
		//save.saveCharacter(Kim);
		//save.loadCharacter("Kim");
		
//		TestProgram t = new TestProgram();
//		NPC kim = new NPC(1, 50, 400, 1, 0, 1,  "kim");
//		Player andre = new Player(1, 60, 293, 1, 15, 1, "andre");
//		andre.addItem(c);
//		t.attack(kim, andre);
//		t.attack(andre, kim);
//		t.attack(kim, andre);
//		t.attack(andre, kim);
//		t.attack(kim, andre);
//		t.attack(andre, kim);
//		andre.useItem(0);
//		System.out.println(andre.getHealth());
		
		
		
		
		
	}
}
