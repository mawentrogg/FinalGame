package equipment;

public class Weapon extends Equippable {

	private final int damage;
	private final int armor;
	
	public Weapon(int damage, int mana, int armor, int type, String name) {
		super(mana, type, name);
		this.damage = damage;
		this.armor = armor;
	}

	public int getDamage(){
		return damage;
	}
	
	public int getArmor(){
		return armor;
	}
	

}