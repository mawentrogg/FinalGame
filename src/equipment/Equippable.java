package equipment;

public class Equippable implements Item {
	
	private int type;
	private String name;
	protected int mana;
	
	public Equippable(int mana, int type, String name){
		this.type = type;
		this.name = name;
		this.mana = mana;
	}


	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return type;
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}


	@Override
	public int getMana() {
		// TODO Auto-generated method stub
		return mana;
	}


	@Override
	public int[] getStats() {
		// TODO Auto-generated method stub
		return null;
	}
}