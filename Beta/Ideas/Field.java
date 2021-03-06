/* 
 * My commenting formats so inconsistent...
 *
 * class Town - subclass of area where players encounter monsters and battle
 * This version is for Ideas
 *
 * ASSUMES BATTLE METHOD
 *
 *
 */

 
import java.util.ArrayList;
import Math;
 
public class Field extends Area {

	//attributes
	protected ArrayList<float> ChanceMonsterSpawn;
	protected ArrayList<Monster> MonsterTypes;
	/*setup:
	  ChanceMonsterSpawn is an array of percentages where the i-th element
	  is the chance of encountering the i-th monster in MonsterTypes
	
	  This setup allows us to add multiple types of monsters quickly
	*/
	
	//constructors
	public Field(String name_arg) {
		super(name_arg);
	}
	
	public Field(int n) {   //creates numbered fields named Field-n; ie Field-1, Field-2...
		super("Field-" + n);
	}
	
	public Field(String name_arg, float chance, Monster mon) {  //creates a field that features only mon with a chance chance of encounter
		name = name_arg;
		ChanceMonsterSpawn.add(chance);
		MonsterTypes.add(mon);
	}
	
	//creates field with chances and monsters determined by args
	public Field(String name_arg, ArrayList<float> chances, ArrayList<Monster> mons) {
		name = name_arg;
		ChanceMonsterSpawn = chances;
		MonsterTypes = mons;
	}
	
	//other methods
	protected void recalibrateChances() {
	/* Suppose there was a .1 chance encountering monster A and .1 chance encountering monster B,
		My code first checks if random()<.1; if true then battle monster A
		
		if the first is false and I use if-else ladder, then if I check for random()<.1 to encounter B
			B's actual chance of occurance is 0.9(A false) * 0.1(B true)
	
		if the first is true and I use consecutive if statements, then if random()<.1 for B, I can encounter
			both A and B in only one possible encounter (which is not intended)
			
		To Compensate for the scenarios above, I will use an if-else ladder and add the chances of all previous
			monster to the chance of the next monster, but cap it at 0.99; this is the fairest I can think of rn
	*/
	
		float sumSoFar = 0.0;
	
		for(int i=0; i<ChanceMonsterSpawn.size(); i++) {
			sumSoFar += ChanceMonsterSpawn.set(i, ChanceMonster.get(i)+sumSoFar);
			if( ChanceMonsterSpawn.get(i) > 0.99 ) {
				ChanceMonsterSpawn.set(i, 0.99);
			}
		}
	
	}
	
	//event and description methods
	public void event(gChar gch) {
		
		float r;
		
		for(int i=0; i<3; i++) { //3 possibilities for encounters
			
			r = Math.random();
			
			for(int j=0; j<ChanceMonsterSpawn.size(); j++) {
				if( r < ChanceMonsterSpawn.get(j) ) {
					gch.battle( MonsterTypes.get(j) );
					break;  //gives same effect as if-else ladder
				}
			}
			
		}
		
	}
	
	public void description() {
		SO.P(name + ": Think RuneScape wilderness.\n");  //good enuf
	}
	
}
