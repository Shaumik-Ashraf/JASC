import java.util.*;
import java.io.*;

public abstract class Event 
{
    protected boolean isComplete;
    
    public boolean eventComplete()
    {
        return isComplete;
    }
    
    public abstract ArrayList<GChar> beginEvent();
}

//--------------------COMBAT EVENT--------------------------------

class CombatEvent extends Event
{
    public ArrayList<GChar> enemies = new ArrayList<GChar>();
    public ArrayList<GChar> party = new ArrayList<GChar>();
    public ArrayList<GChar> engagement = new ArrayList<GChar>();
    public ArrayList<GChar> aliveParty = new ArrayList<GChar>();
    public ArrayList<GChar> aliveEnemies = new ArrayList<GChar>();    
    private Scanner in = new Scanner(System.in);
    private boolean called;
   
    public CombatEvent (ArrayList<GChar> p)
    {
        called = false;
        isComplete = false;
        for (GChar c: p)
        {
            party.add(c);
            engagement.add(c);
            aliveParty.add(c);
        }
        spawnEnemies();
        
        for (int k = 0; k < engagement.size(); k++)
        {
            for (int i = 1; i < engagement.size()-1; i++)
            {
                if (engagement.get(i).speed < engagement.get(i+1).speed)
                {
                    GChar lower = engagement.get(i);
                    GChar higher = engagement.get(i+1);
                    engagement.set(i, higher);
                    engagement.set(i+1, lower);
                }
            }            
        }
    }
    
    //if combat event is called from another event, no NoEvent() takes place
    public CombatEvent (ArrayList<GChar> p, boolean call)
    {
        called = call;
        isComplete = false;
        for (GChar c: p)
        {
            party.add(c);
            engagement.add(c);
            aliveParty.add(c);
        }
        spawnEnemies();
        
        for (int k = 0; k < engagement.size(); k++)
        {
            for (int i = 1; i < engagement.size()-1; i++)
            {
                if (engagement.get(i).speed < engagement.get(i+1).speed)
                {
                    GChar lower = engagement.get(i);
                    GChar higher = engagement.get(i+1);
                    engagement.set(i, higher);
                    engagement.set(i+1, lower);
                }
            }            
        }
    }
    
    public void spawnEnemies()
    {
        enemies.add(new GChar("Goblin Spellcaster", "Fire", "Magic", "Agility", "Mage"));
        enemies.add(new GChar("Goblin Recruit", "Wood", "Agility", "Magic", "Rogue"));
        for (GChar c : enemies)
        {
            engagement.add(c);
            aliveEnemies.add(c);
        }
    }
    
    public ArrayList<GChar> beginEvent()
    {
        while (!(eventComplete()))
        {
            if ((aliveEnemies.size() == 0) || (aliveParty.size() == 0))
            {
                isComplete = true;
            }
            for (GChar c : engagement)
            {
                if ((aliveEnemies.size() == 0) || (aliveParty.size() == 0))
                {
                    break;
                }
                if (aliveParty.contains(c))
                {
                    //System.out.print("\033[H\033[2J");
        			//System.out.flush(); 
                    System.out.println("\nEnemies: " + aliveEnemies);
                    System.out.println("Party: " + aliveParty);
                    System.out.println("Who will " + c.name + " engage?\n");
                    String input = in.nextLine();
                    for (GChar e : aliveEnemies)
                    {
                        if (e.toString().equals(input))
                        {
                            c.battleBeat(e);
                            break;
                        }
                    }
                }
                if (aliveEnemies.contains(c))
                {
                    c.useSkill("Basic Attack", aliveParty.get((int)(party.size()*Math.random())));
                }
                for (GChar ap : party)
                {
                    if (!(ap.isAlive()))
                    {
                        try 
                        {
                            aliveEnemies.remove(ap);                        
                        } catch(Exception e) {}
                    }                    
                }
                for (GChar ae : enemies)
                {
                    if (!(ae.isAlive()))
                    {
                        try 
                        {
                            aliveEnemies.remove(ae);                            
                        } catch(Exception e) {}
                    }
                }
                if ((aliveEnemies.size() == 0) || (aliveParty.size() == 0))
                {
                    break;
                }
                
            }
        }
        System.out.println("Your Party is Victorious");
        System.out.println("Your Remaining Party: " + aliveParty);
        if (called == false)
        {
            NoEvent e = new NoEvent(aliveParty);
            return e.beginEvent();            
        }
        else
        {
            return party;
        }
    }
}

//------------------------NO EVENT----------------------------------------

class NoEvent extends Event
{
    String delay;
    private Scanner in = new Scanner(System.in);
    public ArrayList<GChar> party = new ArrayList<GChar>();
    public NoEvent(ArrayList<GChar> p)
    {
        isComplete = false;
        for (GChar c: p)
        {
            party.add(c);
        }
    }
    public ArrayList<GChar> beginEvent()
    {
		System.out.println("\nType any character to continue");
		delay = in.nextLine();
		String input = "";
		GChar charInput = party.get(0);
        while (!(input.toLowerCase().equals("advance")))
        {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("You are safe... For now...");
            System.out.println("Party: " + party);
            System.out.println("What do you do?\nItems\nAdvance\nRest\n");
            input = in.nextLine().toLowerCase();
            if (input.equals ("items"))
		        {
		            System.out.println("Whose items?\n");
		            input = in.nextLine();
		            for (int j = 0; j < party.size(); j++)
		            {
		                if (party.get(j).name.equals(input))
		                {
		                    charInput = party.get(j);
		                }
		            }
		            
		            if (party.contains(charInput))
		            {
                        while(!(input.equals("nothing")))
    		            {
                            System.out.print("\033[H\033[2J");
                		    System.out.flush();
    			            System.out.println(charInput.getInventory() + "\nWhat will " + charInput.toString() + " do?\nEquip Item\nUse Item\nClear Effects\nView Stats\nNothing (go back)\n");	                
    		                input = in.nextLine().toLowerCase(Locale.ENGLISH);
    		                switch(input)
    		                {
    		                    case "use item":
    		                        try 
    		                        {
    		                            System.out.println("\nUse what item? ");
    		                            charInput.getInventory().useItem(in.nextLine());		                            
    		                        } catch(Exception e) {System.out.println("" + charInput.toString() + " can't use that item.");}
    
    		                        break;
    		                    case "equip item":
    		                        try 
    		                        {
        		                        System.out.println("\nEquip what item?");
        		                        charInput.getInventory().equipItem(in.nextLine());		                            
    		                        } catch(Exception e) {System.out.println("" + charInput.toString() + " can't equip that item.");}
    
    		                        break;
    		                    case "clear effects":
    		                        charInput.getInventory().killEffects();
    		                        break;
    		                    case "nothing":
    		                        break;
    		                    case "view stats":
    		                        charInput.getInventory().onlyViewing();
    		                        charInput.augmentStats();
    		                        System.out.println("\nStats: ");
    		                        charInput.statSheet();
    		                        charInput.normalize();
    		                        break;
    		                }
    		                System.out.println("\nType any character to continue");
    		                delay = in.nextLine();
    		            }		                
		            }
		        }

		           
		    if (input.equals("rest"))
		    {
		        for (GChar c : party)
		        {
		            c.HP = c.hpInitial;
		            c.MP = c.mpInitial;
		            System.out.println("Your Party Rests.");
		        }
		        if (100*Math.random() <= 23)
		        {
		            System.out.println("You are Ambushed!!!");
		            CombatEvent surprise = new CombatEvent(party);
		            party = surprise.beginEvent();
		        }
		    }
		    
		    if (input.equals("advance"))
		    {
		        break;
		    }
            
        }
    return party;
    }
}

//--------------------CHEST EVENTS--------------------------

class ChestEvent extends Event
{
    String delay;
    private Scanner in = new Scanner(System.in);
    public ArrayList<GChar> party = new ArrayList<GChar>();
    String type;
    private int chestLevel;
    
    public ChestEvent (ArrayList<GChar> p)
    {
        isComplete = false;
        for (GChar c: p)
        {
            party.add(c);
        }
        int spawnType = (int)(Math.random()*100);
        if (spawnType <= 15)
        {
            type = "ambush";
        }
        else if ((spawnType > 15) && (spawnType <= 35))
        {
            type = "trap";
        }
        else if ((spawnType > 35) && (spawnType <= 65))
        {
            type = "locked";
        }
        else
        {
            type = "normal";
        }
        chestLevel = party.get(0).level;
    }
    
    public ArrayList<GChar> beginEvent()
    {
        String input = "";
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\nYou encounter a chest.");
        while(!isComplete)
        {
            System.out.println("\nWhat do you do?\nOpen\nExamine\nPick Lock\nNothing (advance)\n");
            input = in.nextLine().toLowerCase();
            switch(input)
            {
                case "nothing":
                    return party;
                case "examine":
                    System.out.println("Who should examine it?");
                    input = in.nextLine();
                    GChar charInput = party.get(0);
    		        for (int j = 0; j < party.size(); j++)
    		        {
    		            if (party.get(j).name.equals(input))
    		            {
    		                    charInput = party.get(j);
    		            }
    		        }
    		        System.out.println(charInput.toString()+ " examines the chest...");
    		        if (type.equals("normal")) {System.out.println("It appears to be a normal, harmless chest.");}
    		        else if (type.equals("locked") || type.equals("trapped") || type.equals("ambush"))
    		        {
    		            if (charInput.luck < 55) {System.out.println("It appears to be locked.");}
    		            if ((charInput.luck >= 55)  && (charInput.luck < 85))  
    		            {
    		                if (type.equals("locked")){System.out.println("It appears to be locked.");}
    		                else{System.out.println("There is something suspicious about this locked chest");}
    		            }
    		            if (charInput.luck >= 85)
    		            {
    		                if (type.equals("locked")) {System.out.println("It appears to be locked.");}
    		                if (type.equals("ambush")) {System.out.println("Something is amiss with this locked chest. It's almost as if somebody is watching you open it...");}
    		                if (type.equals("trapped")) {System.out.println("This locked chest is booby trapped. Is it worth opening anyway?");}
    		            }
    		        }
    		        break;
    		    case "open":
                    System.out.println("Who should open it?");
                    input = in.nextLine();
                    charInput = party.get(0);
    		        for (int j = 0; j < party.size(); j++)
    		        {
    		            if (party.get(j).name.equals(input))
    		            {
    		                    charInput = party.get(j);
    		            }
    		        }
    		        System.out.println(charInput.toString() + " Tries to open the chest");
    		        if (type.equals("normal")) 
    		        {
    		            System.out.println("The chest creaks open. "+ charInput.toString() + " receives glorious loot.");
    		            //
    		            //CODE FOR LOOT HERE
    		            //
    		            isComplete = true;
    		        }
    		        else
    		        {
    		            System.out.println("The chest is locked. You may have to pick it before opening");
    		        }
    		        break;
    		    case "pick lock":
                    System.out.println("Who should pick it?");
                    input = in.nextLine();
                    charInput = party.get(0);
    		        for (int j = 0; j < party.size(); j++)
    		        {
    		            if (party.get(j).name.equals(input))
    		            {
    		                    charInput = party.get(j);
    		            }
    		        }
    		        System.out.println("You send " +charInput.toString()+ " to pick the lock...");
    		        switch (type)
    		        {
    		            case "normal":
    		                System.out.println("This chest is not locked");
    		                break;
    		            case "locked":
    		                if (charInput.luck > (35 + chestLevel * chestLevel*Math.random() /2))
    		                {
    		                    System.out.println (charInput + " unlocks the chest.");
    		                    type = "normal";
    		                }
    		                else if  ((charInput.luck < (35 + chestLevel * chestLevel*Math.random() /2)) && (charInput.luck > (5 + chestLevel * chestLevel*Math.random() /2)))
    		                {
    		                    System.out.println (charInput + " unlocks the chest, after fumbling for a while");
    		                    if ((charInput.speed < 9*Math.random()*chestLevel) || (charInput.luck < 9*Math.random()*chestLevel))
    		                    {
    		                        System.out.println(charInput.toString() + " opened the chest too slowly and with too much noise. The attention of nearby enemies has been drawn!");
    		                        CombatEvent surprise = new CombatEvent(party, true);
    		                        party = surprise.beginEvent();
    		                    }
    		                    type = "normal";
    		                }
    		                else
    		                {
    		                    System.out.println(charInput + " could not open the chest.");
    		                    if ((charInput.speed < 14*Math.random()*chestLevel) || (charInput.luck < 14*Math.random()*chestLevel))
    		                    {
    		                        System.out.println(charInput.toString() + " attempted to open the chest too slowly and with too much noise. The attention of nearby enemies has been drawn!");
    		                        CombatEvent surprise = new CombatEvent(party, true);
    		                        party = surprise.beginEvent();
    		                    }		        
    		                    return party;
    		                }
    		                break;
    		            case "ambush":
    		                System.out.println(charInput + " unlocks the chest. It was very easy to unlock.\nYou are ambushed!!!");
    		                CombatEvent surprise = new CombatEvent(party, true);
    		                party = surprise.beginEvent();	
    		                type = "normal";
    		                break;
    		            case "trapped":
    		                System.out.println(charInput + " unlocks the chest.");	
    		                type = "normal";
    		                if (charInput.speed < chestLevel*15*Math.random())
    		                {
    		                    System.out.println(charInput + " was too slow!\nA trap is sprung!");
    		                    //TRAP EVENT CODE GOES HERE
    		                }
    		                break;
    		        }
    		  }
		        
                
        }
    NoEvent resting = new NoEvent(party);
    return resting.beginEvent();
    }
}