package equipment;


public class Inventory {

	private Item[] inventoryItems;
	private int INVENTORY_SLOTS; //Size of the inventory.

	public Inventory(int INVENTORY_SLOTS) {
		super();
		this.inventoryItems = new Item[INVENTORY_SLOTS];
		this.INVENTORY_SLOTS = INVENTORY_SLOTS;
	}
	
	//If any slot in the inventory is null(empty) it returns false.
	public boolean isFull(){
		for(int i = 0; i < INVENTORY_SLOTS; i++){
			if(inventoryItems[i] == null){
				return false;
			}
		}
		return true;
	}
	
	//Finds the first free inventory slot and returns the index.
	private int findFreeSlot(){
		for(int i = 0; i < INVENTORY_SLOTS; i++){
			if(inventoryItems[i] == null){
				return i;
			}
		}
		return 0;
	}
	//If the inventory is not full, ads the item to the first available(first null) inventory slot.
	public void addItem(Item itemToAdd){
		if(!isFull()){
			int index = findFreeSlot();
			inventoryItems[index] = itemToAdd;
		}
		else{
			System.out.println("Inventory full.");
		}
		
	}
	
	//Returns the item at the specified index.
	public Item getItem(int itemIndex){
		return inventoryItems[itemIndex];
	}
	
	//Returns the full inventory list.
	public Item[] getInventoryItems(){
		return inventoryItems;
	}
	
	//Removes the item from the inventory at the specified index.
	public void removeItem(int itemToRemove){
		inventoryItems[itemToRemove] = null;
	}

	
}
