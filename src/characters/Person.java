package characters;
import  java.lang.Math;

import entities.Entity;

public class Person {

	
	//RECOURCES========
	protected int currentHealth;
	protected int maxHealth;
	protected int lastHitTaken;
	protected int initiative;
	
	protected int currentMana;
	protected int maxMana;
	protected int lastManaUsed;
	
	
	protected int level = 0;
	protected Entity entity;
	//=================
	//STATES===========
	protected boolean isDead = false;
	
	
	//=================
	
	protected int damage;
	protected int armor;
	
	
	
	protected final String name;
	

	public Person(int damage, int health, int mana, int armor, int level, String name, Entity entity) {
		super();
		this.damage = damage;
		this.maxHealth = health;
		this.currentHealth = health;
		this.currentMana = mana;
		this.maxMana = mana;
		this.armor = armor;
		this.name = name;
		this.entity = entity;
	}
	
	//**************************<RECOURCES>***************************************
	
	//=====================<HEALTH & DAMGE TAKEN>=================================
	
	public int mitigateDamage(int damageTaken){
		double armor = this.armor; //Just to make armor a double
		//Armor reduction function:
		double percentageRed = (100-armor)/100; 
		
		double mitigatedDamageTaken = percentageRed*damageTaken;
		return (int) Math.floor(mitigatedDamageTaken);
	}
	public void takeDamage(int damageTaken){
		int mitigatedDamageTaken = mitigateDamage(damageTaken); //Armor = % damage reduction.
		lastHitTaken = mitigatedDamageTaken;
		if(mitigatedDamageTaken > currentHealth){
			currentHealth = 0;
			isDead = true;
		}
		else{
			currentHealth -= mitigatedDamageTaken;
		}
	}
	public void addHealth(int healAmount){
		int missingHealth = maxHealth - currentHealth;
		if(healAmount > missingHealth){
			currentHealth = maxHealth;
		}
		else{
			currentHealth += healAmount;
		}
	}
	public int getHealth(){
		return currentHealth;
	}
	public int getLastHitTaken(){
		return lastHitTaken;
	}
	
	public int getMana(){
		return currentMana;
	}
	
	//=========================================================================
	//=========================<MANA>==========================================
	
	//If enough mana: uses mana and returns true.
	//If not enough mana: returns false and does not use the mana.
	public boolean useMana(int manaUsed){
		if(manaUsed > currentMana){
			System.out.println("Not enough mana");
			return false;
		}
		else{
			currentMana -= manaUsed;
			lastManaUsed = manaUsed;
			return true;
		}
	}
	public void addMana(int manaAmount){
		int missingMana = maxMana - currentMana;
		if(manaAmount > missingMana){
			currentMana = maxMana;
		}
		else{
			currentMana += manaAmount;
		}
	}
	public int getLastManaUsed(){
		return lastManaUsed;
	}
	//=========================================================================
	
	//*********************</RECOURCES>***************************************
	
	//======================<DAMAGE>===========================================
	public int getDamage(){
		return damage;
	}
	
	//=========================================================================
	//=====================<ARMOR>=============================================
	public int getArmor(){
		return armor;
	}
	
	
	
	

	
	
	
	//==========================<VANITY>=========================================
	public String getName(){
		return this.name;
	}
	public int getLevel(){
		return this.level;
	}
	//===========================================================================
	
	//===========================<STATES>========================================
	public boolean isDead(){
		return isDead;
	}
	public int getInitiative(){
		return initiative;
	}
	public void setInitiative(int initiative){
		this.initiative = initiative;
	}

	public Entity getEntity() {
		return entity;
	}
	
	
	//===========================================================================
	
	
}
