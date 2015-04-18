package equipment;

public class Consumable extends Equippable{
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
	
	private final int health;
	private final int mana;
	

	public Consumable(int health, int mana, int type, String name) {
		super(mana, type, name);
		this.health = health;
		this.mana = mana;

	}
	
	public int getHealth(){
		return health;
	}
	public int getMana(){
		return mana;
	}

}