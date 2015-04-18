package equipment;

public class Armor extends Equippable {

	private final int health;
	private final int armor;

	
	
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
	
	public Armor(int health, int mana, int armor, int type, String name) {
		super(mana, type, name);
		this.health = health;
		this.armor = armor;
	}
	
	public int getArmor(){
		return armor;
	}
	
	public int getHealth(){
		return health;
	}

}
