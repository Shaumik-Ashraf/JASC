/* MageF class - extend Mage and implement Fire
 * 
 * Notes:
 *	packaging everything
 *
 */

package gamechars;
 
import java.io.*;
import java.util.*;
import gameutils.*;
 

public class MageF extends Mage implements Fire {
    
    public MageF (ArrayList<String> player){
        name = player.get(0);
        element = "Fire";
        
        //literally the stats for My Unit in Fire Emblem. ;)
        HP = 18;
        str = 4;
        magic = 8;
        def = 4;
        res = 4;
        luck = 6;
        speed = 5;
        EXP = 0;
        level = 1;
        
        hpInitial = HP;
        strInitial = str;
        magicInitial = magic;
        defInitial = def;
        resInitial = res;
        luckInitial = luck;
        speedInitial = speed;
        
        //SO.println( "Previous stats\n");
        //statSheet();
       // SO.println ("\n");
        bestStat (player.get(2));
        worstStat (player.get(3));
        
       // SO.println( "New stats\n");
       // statSheet();
       // SO.println ("\n");
    }
    
     public int heatWave(gChar enemy){
        SO.println (name + " cast Heat Wave!");
        this.crit = false; //Crit activation is set to false
        int damage = this.magic - enemy.res; //Work in progress. This is the damage that your character will do
        
        
        if ( (Math.random() *100) <= luck){
            this.crit = true; //Crit activation is set to true
            damage = (int)( (this.magic )- (enemy.res /1.03) ) ;//This will be the new damage your character does
        }
        
        if ( enemy.element.equals ("Wood") ){
            this.typeAdv = true;
            damage *= 2;
        }
        
        if ( enemy.element.equals ("Aqua") ){
            this.typeDis = true;
            damage /= 2;
        }
        
        if (damage <= 0){
            damage = 0;
        }
        SO.println ("The enemy took " + damage + "!\n\n");
        
        enemy.HP -= damage; //Final damage that your enemy will take
        return damage; //Returns the damage dealt to enemy
    
        }
        
        
    //does damage on res based on str stat    
    public int flameCrash(gChar enemy){
         SO.println (name + " used Flame Crash!");
        this.crit = false; //Crit activation is set to false
        int damage = this.magic - enemy.def; //Work in progress. This is the damage that your character will do
        
        
        if ( (Math.random() *100) <= luck){
            this.crit = true; //Crit activation is set to true
            damage = (int)( (this.magic )- (enemy.def /1.03) ) ;//This will be the new damage your character does
        }
        
        if ( enemy.element.equals ("Wood") ){
            this.typeAdv = true;
            damage *= 2;
        }
        
        if ( enemy.element.equals ("Aqua") ){
            this.typeDis = true;
            damage /= 2;
        }
        
        if (damage <= 0){
            damage = 0;
        }
        SO.println ("The enemy took " + damage + "!\n\n");
        
        enemy.HP -= damage; //Final damage that your enemy will take
        return damage; //Returns the damage dealt to enemy
    
        }
    
    
        
    public void reKindle(){
        SO.println (name + " cast Rekindle!");
        SO.println (name + " is enveloped by a firey aura! Resistance to magic has increased!");
        res += 4;
    }
    
     public String skillListChange( String skillList){
        if (this.level == 2){
            skillList += "Arcane Bullets\n";
            System.out.println (this.name + " learned Arcane Bullets!");
        }
        
        if (this.level == 3){
            skillList += "Concentrate\n";
            System.out.println (this.name + " learned Concentrate!");
        }
        
        return skillList;
    }
    
    public ArrayList<String> skillChange( ArrayList<String> skills){
         if (this.level == 2){
             skills.add ("Arcane Bullets");
         }
         if (this.level == 3){
             skills.add ("Concentrate");
         }
         return skills;
    }
    
        
        
        
}