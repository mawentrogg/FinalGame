package gameplay;

import characters.Person;

public class Attack {
	
	Person attacker;
	Person defender;
	
	public Attack(Person attacker, Person defender){
		this.attacker = attacker;
		this.defender = defender;
		defender.takeDamage(attacker.getDamage());
		System.out.println(defender.getName() + " took " + defender.getLastHitTaken() + " damage from " + attacker.getName());
		System.out.println("Remaining HP of " + defender.getName() + " is " + defender.getHealth());
		System.out.println("");
	}
	
	
}
