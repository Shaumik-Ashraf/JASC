/* Legendairy - does the heavy lifting of legends
 * 
 * Notes:
 * This should still work
 * 
 *
 */

//custom api
//import gamechars.*;
//import gameutils.*;
 
//java 7 api 
import java.io.*;
import java.util.*;


    public class Legendairy{
    	
    	private static final ArrayList<String> stats = new ArrayList();
        private static final ArrayList<String> traits = new ArrayList();
        private static final ArrayList<String> jobs = new ArrayList();
        public Event e;
        public GChar c1;
            
      
        private GChar enemy;
        
        private String message;
        private String choice;

        private Scanner in;
        
        public String name;
        public String element;
        public String best;
        public String worst;
        public String job;
        

        
        public Legendairy() {
         
	        in = new Scanner(System.in);
			
	        stats.add ("health");
	        stats.add ("strength");
	        stats.add ("magic");
	        stats.add ("defense");
	        stats.add ("resistance");
	        stats.add ("luck");
	        stats.add ("agility");
	        
	        traits.add ("outgoing");
	        traits.add ("optimistic");
	        traits.add ("calm");
	        traits.add ("carefree");
	        traits.add ("malevolent");
	        traits.add ("benevolent");
	        traits.add ("empty");
	        
	        jobs.add ("warrior");
	        jobs.add ("mage");
	        jobs.add ("archer");
	        jobs.add ("rogue");
	        
	        Item.consEquipList();
        	Skill.consAllSkills();

	        newGame();
        }
        
        public void newGame(){
        	
		//-----------------INTRO/TUTORIAL
		ArrayList<GChar> party = new ArrayList<GChar>();
		c1 = characterCreation();
		party.add(c1);
		System.out.println(c1.name + " has joined the party!\n\n");
		
		System.out.println("You open your crusty eyelids as if you have woken from a long sleep. You find yourself in a dark, gloomy room.");
		e = new NoEvent(party);
		party = e.beginEvent();
		System.out.println("You wander out of the room and find yourself in a long hallway. You can only see so far down the hallway before an obscuring darkness clouds it.");
		System.out.println("You hear shuffling noises. What do you do?\nRun\nHide\n");
		while(true)
		{
			String input = in.nextLine();
			if (input.toLowerCase().equals("run"))
			{
				System.out.println("The noises chase after you. Suddenly, you are caught, as the shuffling noises change into heavy footsteps, which in turn manifest themselves as insidious creatures in your vision. \nYou are attacked.");
				break;
			}
			else if (input.toLowerCase().equals("hide"))
			{
				System.out.println("The shuffling noises grow louder and louder, and then grow softer. Then you hear a lighter shuffling approach you again as you hide behind a door. That shuffling quicky becomes the footsteps of a single entity. \nThe door creaks open as you and a horrible creature look into each other's eyes.\nThe creature lets out a hideous scream and its comerades rush to its aid.\nYou are attacked.");
				break;
			}
			else
			{
				System.out.println("Input a valid response.");
			}
		}
		e = new CombatEvent(party, true);
		party = e.beginEvent();
        System.out.print("\033[H\033[2J");
		System.out.flush();     
		System.out.println("You are heavily damaged. You lay on the floor as yet another entitiy hobbles toward you. There is nothing you can do.");
		System.out.println("'Hello there...' it says. Take this, and come with me.");
		c1.getInventory().giveItem(new EffectItem("Lesser Healing Potion"));
		System.out.println("'Go on, drink it. You'll have to wait for its effects to kick in. We can stay here for now.");
		int hptemp = c1.HP;
		while ((c1.getInventory().inv.size() > 0) || (c1.HP == hptemp))
		{
		System.out.println("Access your items to use the potion. Then, wait for the potion to do its work");
		e = new NoEvent(party);
		party = e.beginEvent();
		}
		System.out.println("You feel much better.");
		System.out.println("'Now come with me,' he says.");
		GChar c2 = characterCreation();
		party.add(c2);
		System.out.println(c2.name + " has joined the party!");
		e = new CombatEvent(party);
		party = e.beginEvent();
		//----------------------------END TUTORIAL
            /*
        
       		Add dungeon stuff here
       	
       		*/
            
	
        	
        }
        
        public GChar characterCreation(){
        	System.out.print("\033[H\033[2J");
			System.out.flush();
            message = "";
            message = "Welcome to Project Legendairy. \nWe appreciate you volunteering to help out. \nWould you mind telling me your name?";
            System.out.println (message);
            
        
	        name = in.nextLine();
	      
	        System.out.print("\033[H\033[2J");
			System.out.flush();
	        
	        message = "Choose your vocation.\n";
	        message += "Warrior\n";
	        message += "Mage\n";
        	message += "Archer\n";
        	message += "Rogue\n";
        	
        	
        	do
        	{
                
                	System.out.println (message);
                    System.out.println("Please enter an appropriate response.\n");
	                choice =in.nextLine();
	                choice = choice.toLowerCase(Locale.ENGLISH);
	               	System.out.print("\033[H\033[2J");
					System.out.flush();
	                if ( jobs.contains (choice) ) {
	                    job = choice;
	                    break;
	                }

            }while (true);
            
      
	        System.out.print("\033[H\033[2J");
			System.out.flush();
	        message = "What do you think is your strongest asset?\n";
	        message += "\nHealth\n";
	        message += "Strength\n";
        	message += "Magic\n";
        	message += "Defense\n";
        	message += "Resistance\n";
	        message += "Luck\n";
        	message += "Agility\n";
       
            
            do
        	{
              
                System.out.println (message);
                System.out.println("Please enter an appropriate response.\n");
	            choice =in.nextLine();
	            choice = choice.toLowerCase(Locale.ENGLISH);
	            System.out.print("\033[H\033[2J");
				System.out.flush();
	            if ( stats.contains (choice) ) {
	               	best = choice;
	                break;
	                }

            }while (true);
            
      
            
            System.out.print("\033[H\033[2J");
			System.out.flush();
            message = "What do you think is your weakest asset?\n";
	        message += "\nHealth\n";
	        message += "Strength\n";
        	message += "Magic\n";
        	message += "Defense\n";
        	message += "Resistance\n";
	        message += "Luck\n";
        	message += "Agility\n";
       
            
 			do
        	{
              
                System.out.println (message);
                System.out.println("Please enter an appropriate response.\n");
	            choice =in.nextLine();
	            choice = choice.toLowerCase(Locale.ENGLISH);
	            System.out.print("\033[H\033[2J");
				System.out.flush();
	            if ( stats.contains (choice) ) {
	               	worst = choice;
	                break;
	                }

            }while (true);
            
        
        
        
            System.out.print("\033[H\033[2J");
			System.out.flush();
            message = "Which of the following best describes your character?\n";
        	message += "\nOutgoing\n";
	        message += "Optimistic\n";
	        message += "Calm\n";
	        message += "Carefree\n";
	        message += "Malevolent\n";
	        message += "Benevolent\n";
	       // message += "Empty\n"; // Blank is a secret class.
        
			do
        	{
              
                System.out.println (message);
                System.out.println("Please enter an appropriate response.\n");
	            choice =in.nextLine();
	            choice = choice.toLowerCase(Locale.ENGLISH);
	            System.out.print("\033[H\033[2J");
				System.out.flush();
	            if ( traits.contains (choice) ) {
	                    element = choice;
	                    if (element.equals ("outgoing") ){
	                    	element = "Fire";
	                    }
	                    if (element.equals ("optimistic") ){
	                    	element = "Wood";
	                    }
	                    if (element.equals ("calm") ){
	                    	element = "Aqua";
	                    }
	                    if (element.equals ("carefree") ){
	                    	element = "Gale";
	                    }
	                    if (element.equals ("malevolent") ){
	                    	element = "Dark";
	                    }
	                    if (element.equals ("benevolent") ){
	                    	element = "Light";
	                    }
	                    if (element.equals ("empty") ){
	                    	element = "Void";
	                    }
	               break;
	            }

            }while (true);
            

            System.out.print("\033[H\033[2J");
			System.out.flush();
			
			System.out.println (name);
			System.out.println (element);
			System.out.println (best);
			System.out.println (worst);
			System.out.println (job);
			
			
			GChar cloud = new GChar (name, element, best, worst, job);
            message = "Hm, so you're " ;
            message += name;
            message += "?\n";
            System.out.println (message);
            return cloud;
          
        }
        
      

      	
          
        
        public static void main (String[] args){
            Legendairy game = new Legendairy();
        }
		
    }
