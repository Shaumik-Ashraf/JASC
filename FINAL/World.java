/* World class
	will just be a matrix of areas and have main gameplay

	Note: java.util contains Random, a class for seeding and generating random numbers
	
*/
import java.util.*;

public class World {

	public int world_size;
	public Area[][] world;
	public int player_x_cor;
	public int player_y_cor;
	public long world_seed;
	public Random rand;
	
    public World(ArrayList<GChar> party) {
		world_size = 4;
		world = new Area[world_size][world_size];
		
		world_seed = (long)(Math.random()*Math.random()*Math.random()*Math.random()*1000);
		rand = new Random(world_seed);
		
		player_x_cor = Math.abs(rand.nextInt()) % world_size;
		player_y_cor = Math.abs(rand.nextInt()) % world_size;
		
		for(int i=0; i<world_size; i++) {
			for(int j=0; j<world_size; j++) {
				switch( rand.nextInt()%3 ) {
					case 0:
						world[i][j] = new AreaTown(party);
						System.err.print("world loc (" + i + "," + j + ") is town\n");
						break;
					case 1:
						world[i][j] = new AreaField(party);
						System.err.print("world loc (" + i + "," + j + ") is field\n");
						break;
					case 2:
						world[i][j] = new AreaDungeon(party);
						System.err.print("world loc (" + i + "," + j + ") is dungeon\n");
						break;
					default:
						break;
				}  //close switch
			} //close for-loop j
		} //close for-loop i
		
	} //close constructor
	
	public World(ArrayList<GChar> party, int worldsize, long worldseed) {
		world_size = worldsize;
		world = new Area[world_size][world_size];
		
		world_seed = worldseed;
		rand = new Random(world_seed);
		
		player_x_cor = Math.abs(rand.nextInt()) % world_size;
		player_y_cor = Math.abs(rand.nextInt()) % world_size;
		
		for(int i=0; i<world_size; i++) {
			for(int j=0; j<world_size; j++) {
				switch( rand.nextInt()%3 ) {
					case 0:
						world[i][j] = new AreaTown(party);
						break;
					case 1:
						world[i][j] = new AreaField(party);
						break;
					case 2:
						world[i][j] = new AreaDungeon(party);
						break;
					default:
						break;
				}  //close switch
			} //close for-loop j
		} //close for-loop i
	} //close constructor
	
	public Area getArea() {
		return( world[player_x_cor][player_y_cor] );
	}
	
	public static void main(String[] args) {
	
		ArrayList<GChar> p = new ArrayList<GChar>();
		Scanner in = new Scanner(System.in);
		World w;
		
		Creator.initCreator();
	
		p.add( Creator.characterCreation() );
		p.add( Creator.characterCreation() );
		
		w = new World(p);
	
		System.out.print("World created\nx:" + w.player_x_cor + ", y:" + w.player_y_cor + "\n\n");
		
		for(int turn=0; turn<10; turn++) {
			System.err.println("turn: " + turn);
			if( (w.getArea()).noMoreEvents() ) {  //player cleared area...
				
				w.getArea().restore(p);
				
				System.out.println("You have cleared the area!\nWhere would you like to go?\n");
				System.out.print("North\nSouth\nEast\nWest\nStay here\n\n:");
				
				if( in.hasNextLine() ) {  //prompt for direction to travel
					switch( in.nextLine().toLowerCase() ) {
						case "north":
							w.player_y_cor--;
							if( w.player_y_cor<0 ) {
								w.player_y_cor = w.world_size-1;
							}
							break;
						case "south":
							w.player_y_cor++;
							w.player_y_cor %= w.world_size;
							break;
						case "east":
							w.player_x_cor++;
							w.player_x_cor %= w.world_size;
							break;
						case "west":
							w.player_x_cor--;
							if( w.player_x_cor<0 ) {
								w.player_x_cor = w.world_size-1;
							}
							break;
						case "stay here":
							break;
						default:
							System.out.println("This isnt your GPS! Please enter a proper direction.\n");
							break;
					} //close switch-case
				} //close if input
				
			} //close if player cleared area
			
			else {
				System.err.print("Calling area event\n");
				p = w.getArea().callEvent(p);
				System.err.print("event calling complete\n");
			}
			
		} //close loop
	
		System.out.println("Done\n");
	
	} //close main
	
}

