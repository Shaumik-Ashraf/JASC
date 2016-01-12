/* interface Water - defines element base atks (methods) 
 * 
 * Notes:
 *	
 *
 */

package gamechars;
 
import java.io.*;
import java.util.*;

public interface Water{
	
	public final String attribute = "Water";  //This should be done here
	
	protected String elemAtk1Name = "AquaCuttingBlade";
	protected String elemAtk2Name = "TorrentRush";
	
	
	public void addSkills() {
		skillList.add(elemAtk1Name);
		skillList.add(elemAtk2Name);
	}
	
	public int elemAtk1() {
		//define...
	}

	public int elemAtk2() {
	    //define...
	}
		
		
}

