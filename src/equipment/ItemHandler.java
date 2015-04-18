package equipment;


//Handles equipment and item commands.
public class ItemHandler {

	private Inventory inventory;
	private Equipped equipped;
	
	public ItemHandler(Inventory inventory, Equipped equipped) {
		super();
		this.inventory = inventory;
		this.equipped = equipped;
	};
	
	public void equip(int itemIndex){
		//Save the item to equip to a temporary variable.
		Item itemToEquip = inventory.getItem(itemIndex);
		
		//Save the item to unequip to a temporary variable.
		int itemType = itemToEquip.getType();
		Item itemToUnequip = equipped.getItem(itemType);
		
		//Remove the item to equip from the inventory.
		inventory.removeItem(itemIndex);
		
		//Remove the item to unequip from its slot.
		equipped.removeItem(itemType);
		
		//Add the items back to their new slots.
		inventory.addItem(itemToUnequip);
		equipped.equipItem(itemToEquip);
	}
	
	public void unequip(int itemType){
		if(inventory.isFull()){
			System.out.println("Cannot uequip: Inventory is full");
		}
		else{
			Item itemToUnequip = equipped.getItem(itemType);
			equipped.removeItem(itemType);
			inventory.addItem(itemToUnequip);
		}
		

	}

	
}
