package characters;
import equipment.Armor;
import equipment.Consumable;
import equipment.Equipped;
import equipment.Inventory;
import equipment.Item;
import equipment.ItemHandler;
import equipment.Weapon;


public class Player extends Person {

	
	private int bonusHealth;
	private int bonusMana;
	private int bonusDamage;
	private int bonusArmor;
	
	private Inventory inventory;
	private Equipped equipped;
	private ItemHandler itemHandler;
	
	private int currentExp = 0;
	private int[] expToLevel = {100, 150, 250};
	
	
	public Player(Inventory inventory, Equipped equipped,  int damage, int health, int mana, int armor, int level, String name) {
		super(damage, health, mana, armor, level, name);
		this.inventory = inventory;
		this.equipped = equipped;
		this.itemHandler = new ItemHandler(inventory, equipped);
		updateStats();
	}
	
	
	//************************<ITEMS>********************************************
	/*ITEM TYPE: 
	 * 0 - Head.
	 * 1 - Chest.
	 * 2 - Hands.
	 * 3 - Legs.
	 * 4 - Feet.
	 * 5 - Weapon.
	 * 6 - Shield.
	 - Item type corresponds to indexes of the equippedItems Array. F.eks.: equippedItems[0] is the 
	 Head armor, equippedItems[5] is the weapon etc.
	 */
	//========================<EQUIPPED/EQUIPPABLE ITEMS>========================
	public Item getEquippedItem(int type){
		return equipped.getItem(type);
	}
	public void equipItem(int itemIndex){
		itemHandler.equip(itemIndex);
		updateStats();
		
	}
	public void unEquipItem(int itemType){
		itemHandler.unequip(itemType);
		updateStats();
	}
	
	
	//Resets and updates the bonus stats from items.
	private void updateStats(){
		Item[] equippedItems = equipped.getEquippedItems();
		
		//Reset stats to base stats.
		maxHealth -= bonusHealth;
		maxMana -= bonusMana;
		armor -= bonusArmor;
		damage -= bonusDamage;
		
		//Resets the bonus stats to 0.
		bonusArmor = 0;
		bonusDamage = 0;
		bonusHealth = 0;
		bonusMana = 0;
		
		//Ads up bonus stats from items
		for (Item item : equippedItems) {
			if(item == null){
				//Do nothing
			}
			else{
				if(item.getType() < 5){ //If it is an Armor piece
					bonusArmor += ((Armor) item).getArmor();
					bonusHealth += ((Armor) item).getHealth();
				}
				else{//If it is a weapon
					bonusArmor += ((Weapon) item).getArmor();
					bonusMana += ((Weapon) item).getMana();
					bonusDamage += ((Weapon) item).getDamage();
				}
			}
		}
		
		//Re-applies bonus stats.
		maxHealth += bonusHealth;
		currentHealth = maxHealth;
		maxMana += bonusMana;
		currentMana = maxMana;
		armor += bonusArmor;
		damage += bonusDamage;
	}
	
	//===========================================================================
	//========================<INVENTORY>========================================
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
	
	//Returns the the players inventory
	public Item[] getInventory(){
		return inventory.getInventoryItems();
	}
	
	public void addItem(Item itemToAdd){
		inventory.addItem(itemToAdd);
	}
	public void useItem(int itemIndex){
		Item itemToUse = (Consumable) inventory.getItem(itemIndex);
		int itemType = itemToUse.getType();
		if(itemType == 7){
			int healthToAdd = ((Consumable) itemToUse).getHealth();
			addHealth(healthToAdd);
		}
		else if(itemType == 8){
			int manaToAdd = ((Consumable) itemToUse).getMana();
			addMana(manaToAdd);
		}
	}
		
		
	//************************</ITEMS>*******************************************
	//========================<EXPERIENCE & LEVELS>============================
		public void addExperience(int expAmount){
			int expToLevel = this.expToLevel[level] - currentExp;
			if(expToLevel <= expAmount){
				currentExp = expAmount - expToLevel;
				levelUp();
			}
			else{
				currentExp += expAmount;
			}
		}
		private void levelUp(){
			level++;
			this.maxHealth += 25*level;
			this.currentHealth += (maxHealth-currentHealth)/2;
			this.maxMana += 25*level;
			this.currentMana += (maxMana-currentMana)/2;
			this.damage += 2*level;
		}
		
		
}
