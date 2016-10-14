package space1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Solar_model extends Canvas implements Runnable{

	private static boolean opposite_mars;
	private static boolean opposite_earth;
	private static boolean opposite_venus;
	private static boolean opposite_mercury;
	private static boolean clear;
	private static boolean selected;
	private static int mars_orbit_x;
	private static int mars_orbit_y;
	private static int mars_orbit_r;
	private static int earth_orbit_x;
	private static int earth_orbit_y;
	private static int earth_orbit_r;
	private static int venus_orbit_x;
	private static int venus_orbit_y;
	private static int venus_orbit_r;
	private static int mercury_orbit_x;
	private static int mercury_orbit_y;
	private static int mercury_orbit_r;
	private static int Sun_r;
	private int mercury_x;
	private int mercury_y;
	private static int mercury_r;
	private int venus_x;
	private int venus_y;
	private static int venus_r;
	private int earth_x;
	private int earth_y;
	private static int earth_r;
	private int mars_x;
	private int mars_y;
	private static int mars_r;
	public Solar_model(){
		initialize();
		setSize(300,300);
		repaint();
		Thread thread = new Thread(this);
		thread.start();
	}
	public void paint(Graphics g){
		//draw in start panel
		//System.out.println("paint");
		super.paint(g);
		g.setColor(Color.BLACK);
		g.clearRect(this.getX(), this.getY(), this.getHeight(), this.getWidth());
		g.fillRect(this.getX(), this.getY(), this.getHeight(), this.getWidth());
		//draw orbits
		g.setColor(Color.WHITE);
		//mars
		g.drawArc(mars_orbit_x, mars_orbit_y, mars_orbit_r, mars_orbit_r, 0, 360);
		//earth
		g.drawArc(earth_orbit_x, earth_orbit_y, earth_orbit_r, earth_orbit_r, 0, 360);
		//venus
		g.drawArc(venus_orbit_x, venus_orbit_y, venus_orbit_r, venus_orbit_r, 0, 360);
		//mercury
		g.drawArc(mercury_orbit_x, mercury_orbit_y, mercury_orbit_r, mercury_orbit_r, 0, 360);
		
		//draw objects(NOT TO SCALE)
		//sun
		g.setColor(Color.ORANGE);
		g.fillArc(410, 210, Sun_r, Sun_r, 0, 360);
		//mercury
		g.setColor(Color.LIGHT_GRAY);
		g.fillArc(mercury_x, mercury_y, mercury_r, mercury_r, 0, 360);
		//venus
		g.setColor(Color.YELLOW);
		g.fillArc(venus_x, venus_y, venus_r, venus_r, 0, 360);
		//earth
		g.setColor(Color.BLUE);
		g.fillArc(earth_x, earth_y, earth_r, earth_r, 0, 360);
		//mars
		g.setColor(Color.RED);
		g.fillArc(mars_x, mars_y, mars_r, mars_r, 0, 360);
	}
	public void run() {
		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//run start panel
			//System.out.println("run1");
			if(mars_x < mars_orbit_x){
				mars_x = mars_orbit_x;
			}
			if(mars_x > mars_orbit_x + mars_orbit_r * 2){
				mars_x = mars_orbit_r * 2;
			}
			if(earth_x < earth_orbit_x){
				earth_x = earth_orbit_x;
			}
			if(earth_x > earth_orbit_x + earth_orbit_r * 2){
				earth_x = earth_r * 2;
			}
			if(venus_x < venus_orbit_x){
				venus_x = venus_orbit_x;
			}
			if(venus_x > venus_orbit_x + venus_orbit_r * 2){
				venus_x = venus_r * 2;
			}
			if(mercury_x < mercury_orbit_x){
				mercury_x = mercury_orbit_x;
			}
			if(mercury_x > mercury_orbit_x + mercury_orbit_r * 2){
				mercury_x = mercury_r * 2;
			}
			
			//check if the direction needs to be turned
			if(Place.if_turn(mars_x, mars_y, mars_orbit_r,mars_orbit_x) == 1){
				opposite_mars = false;
			}else if(Place.if_turn(mars_x, mars_y, mars_orbit_r,mars_orbit_x) == 2){
				opposite_mars = true;
			}
			if(Place.if_turn(earth_x, earth_y, earth_orbit_r,earth_orbit_x) == 1){
				opposite_earth = false;
			}else if(Place.if_turn(earth_x, earth_y, earth_orbit_r,earth_orbit_x) == 2){
				opposite_earth = true;
			}
			if(Place.if_turn(venus_x, venus_y, venus_orbit_r,earth_orbit_x) == 1){
				opposite_venus = false;
			}else if(Place.if_turn(venus_x, venus_y, venus_orbit_r,venus_orbit_x) == 2){
				opposite_venus = true;
			}
			if(Place.if_turn(mercury_x, mercury_y, mercury_orbit_r,mercury_orbit_x) == 1){
				opposite_mercury = false;
			}else if(Place.if_turn(mercury_x, mercury_y, mercury_orbit_r,mars_orbit_x) == 2){
				opposite_mercury = true;
			}
			//move mars
			if(opposite_mars){
				mars_x --;
				mars_y = 225-(int) Math.sqrt(125*125-((mars_x-425)*(mars_x-425)))-5;
				repaint();
			}else{
				mars_x ++;
				mars_y = (int) Math.sqrt(125*125-((mars_x-425)*(mars_x-425)))+225-5;
				repaint();
			}
			//move earth
			if(opposite_earth){
				earth_x --;
				earth_y = 225-(int) Math.sqrt(100*100-((earth_x-425)*(earth_x-425)))-5;
				repaint();
			}else{
				earth_x ++;
				earth_y = (int) Math.sqrt(100*100-((earth_x-425)*(earth_x-425)))+225-5;
				repaint();
			}
			//move venus
			if(opposite_venus){
				venus_x --;
				venus_y = 225-(int) Math.sqrt(75*75-((venus_x-425)*(venus_x-425)))-5;
				repaint();
			}else{
				venus_x ++;
				venus_y = (int) Math.sqrt(75*75-((venus_x-425)*(venus_x-425)))+225-5;
				repaint();
			}
			//move mercury
			if(opposite_mercury){
				//System.out.println("neg");
				mercury_x --;
				mercury_y = 225-(int) Math.sqrt(45*45-((mercury_x-425)*(mercury_x-425)))-5;
				repaint();
			}else{
				mercury_x ++;
				mercury_y = (int) Math.sqrt(45*45-((mercury_x-425)*(mercury_x-425)))+225-5;
				repaint();
			}
		}
	
	}
	public static void initialize(){
		//conditions
		opposite_mars = false;
		opposite_earth = false;
		opposite_venus = false;
		opposite_mercury = false;
		clear = false;
		selected = false;
		
		//objects' radius
		Sun_r = 30;
		mercury_r = 10;
		venus_r = 10;
		earth_r = 12;
		mars_r = 11;
		
		//orbits' radius
		mercury_orbit_r = 90;
		venus_orbit_r = 150;
		earth_orbit_r = 200;
		mars_orbit_r = 250;
		
		//orbits' possition
		mercury_orbit_x = 380;
		mercury_orbit_y = 180;
		venus_orbit_x = 350;
		venus_orbit_y = 150;
		earth_orbit_x = 325;
		earth_orbit_y = 125;
		mars_orbit_x = 300;
		mars_orbit_y = 100;
		/*about the orbit
		 *           application window
		 *                   |
		 *                   |
		 *                   V
		 * +-----------------------------------+
		 * |        |                          |
		 * |        |<--orbit_y                |
		 * |--------+--------+                 |
		 * |  ^     |        |                 |
		 * |  |     |        |<--orbit_r/2     |
		 * |orbit_x |        |                 |
		 * |        +--------+                 |
		 * |            ^                      |
		 * |            |                      |
		 * |         orbit_r/2                 |
		 * +-----------------------------------+
		 */
	}
	
}
