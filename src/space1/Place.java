package space1;

public class Place {
	public static int if_turn(int x,int y,int r,int orbit_x){
		if(x == orbit_x){
			return 1;
		}else if(x == orbit_x + 2 * r ){
			return 2;
		}
		return 0;
		
	}
	
}