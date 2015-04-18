package equipment;

public class Equipped {

	Item[] equippedArmor;
	Item[] equippedWeapons;
	Item[] equippedItems;

	public Equipped() {
		super();
		equippedArmor = new Item[5];
		equippedWeapons = new Item[2];
		equippedItems = new Item[7];
	}
	
	
	//Equips the specified item to the right slot(Weapon or Armor)
	public void equipItem(Item itemToEquip){
		int itemType = itemToEquip.getType();
		if(itemType < 5){
			equippedArmor[itemType] = itemToEquip;
		}
		else if(itemType > 4){
			if(itemType == 5){
				equippedWeapons[0] = itemToEquip;
			}
			else if(itemType == 6){
				equippedWeapons[1] = itemToEquip;
			}
		}
	}
	
	//Removes the item at of the specified type
	public void removeItem(int type){
		if(type < 5){
			equippedArmor[type] = null;
		}
		else if(type > 4){
			if(type == 5){
				equippedWeapons[0] = null;
			}
			else if(type == 6){
				equippedWeapons[1] = null;
			}
		}
	}
	
	//Returns the item at the speciefied slot(type)
	public Item getItem(int type){
		if(type < 5){
			return equippedArmor[type];
		}
		else if(type > 4){
			if(type == 5){
				return equippedWeapons[0];
			}
			else if(type == 6){
				return equippedWeapons[1];
			}
		}
		return null;
	}
	
	//Returns the full list of equipped items
	public Item[] getEquippedItems(){
		for(int i = 0; i < 5; i++){
			equippedItems[i] = equippedArmor[i];
		}
		equippedItems[5] = equippedWeapons[0];
		equippedItems[6] = equippedWeapons[1];
		return equippedItems;
	}
	
	
}
