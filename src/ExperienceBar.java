import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * 
 * @author clarkt5
 * experience is what Povy gains after defeating an enemy
 * Once the bar fills, it resets and Povy can perform an upgrade
 * rendered in the post battle screen, once filled, more points are required
 * to level up again
 */
public class ExperienceBar {
	public static float exp;
	public static float expToUpgrade;
	public static int healthLevel;
	public static int laserLevel;
	public static int pummelLevel;
	public static int allyLevel;
	public static boolean levelUp = false;
	
	private ArrayList<BufferedImage> progressBar;
	private ArrayList<BufferedImage> healthBar;
	private ArrayList<BufferedImage> laserBar;
	private ArrayList<BufferedImage> pummelBar;
	private ArrayList<BufferedImage> allyBar;
	
	/**
	 * creates the experience bar
	 */
	public ExperienceBar() {
		progressBar = new ArrayList<BufferedImage>();
		healthBar = new ArrayList<BufferedImage>();
		laserBar = new ArrayList<BufferedImage>();
		pummelBar = new ArrayList<BufferedImage>();
		allyBar = new ArrayList<BufferedImage>();
		exp = 0;
		expToUpgrade = 50;
		healthLevel = 0;
		laserLevel = 0;
		pummelLevel = 0;
		allyLevel = 0;
		
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		
		
		progressBar.add(ss.grabImage(1, 1, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(2, 1, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(3, 1, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(4, 1, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(5, 1, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(6, 1, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(7, 1, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(1, 2, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(2, 2, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(3, 2, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(4, 2, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(5, 2, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(6, 2, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(7, 2, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(1, 3, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(2, 3, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(3, 3, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(4, 3, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(5, 3, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(6, 3, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(7, 3, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(1, 4, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(2, 4, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(3, 4, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(4, 4, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(5, 4, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(6, 4, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(7, 4, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(1, 5, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(2, 5, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(3, 5, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(4, 5, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(5, 5, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(6, 5, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(7, 5, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(1, 6, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(2, 6, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(3, 6, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(4, 6, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(5, 6, 727, 45, "expBar"));
		progressBar.add(ss.grabImage(6, 6, 727, 45, "expBar"));
		
		allyBar.add(ss.grabImage(1, 1, 727, 45, "allyBar"));
		allyBar.add(ss.grabImage(1, 2, 727, 45, "allyBar"));
		allyBar.add(ss.grabImage(1, 3, 727, 45, "allyBar"));
		allyBar.add(ss.grabImage(1, 4, 727, 45, "allyBar"));
		allyBar.add(ss.grabImage(1, 5, 727, 45, "allyBar"));
		allyBar.add(ss.grabImage(1, 6, 727, 45, "allyBar"));
		allyBar.add(ss.grabImage(1, 7, 727, 45, "allyBar"));
		allyBar.add(ss.grabImage(2, 1, 727, 45, "allyBar"));
		allyBar.add(ss.grabImage(2, 2, 727, 45, "allyBar"));
		allyBar.add(ss.grabImage(2, 3, 727, 45, "allyBar"));
		allyBar.add(ss.grabImage(2, 4, 727, 45, "allyBar"));
		
		healthBar.add(ss.grabImage(1, 1, 727, 45, "healthBar"));
		healthBar.add(ss.grabImage(1, 2, 727, 45, "healthBar"));
		healthBar.add(ss.grabImage(1, 3, 727, 45, "healthBar"));
		healthBar.add(ss.grabImage(1, 4, 727, 45, "healthBar"));
		healthBar.add(ss.grabImage(1, 5, 727, 45, "healthBar"));
		healthBar.add(ss.grabImage(1, 6, 727, 45, "healthBar"));
		healthBar.add(ss.grabImage(1, 7, 727, 45, "healthBar"));
		healthBar.add(ss.grabImage(2, 1, 727, 45, "healthBar"));
		healthBar.add(ss.grabImage(2, 2, 727, 45, "healthBar"));
		healthBar.add(ss.grabImage(2, 3, 727, 45, "healthBar"));
		healthBar.add(ss.grabImage(2, 4, 727, 45, "healthBar"));
		
		laserBar.add(ss.grabImage(1, 1, 727, 45, "laserBar"));
		laserBar.add(ss.grabImage(1, 2, 727, 45, "laserBar"));
		laserBar.add(ss.grabImage(1, 3, 727, 45, "laserBar"));
		laserBar.add(ss.grabImage(1, 4, 727, 45, "laserBar"));
		laserBar.add(ss.grabImage(1, 5, 727, 45, "laserBar"));
		laserBar.add(ss.grabImage(1, 6, 727, 45, "laserBar"));
		laserBar.add(ss.grabImage(1, 7, 727, 45, "laserBar"));
		laserBar.add(ss.grabImage(2, 1, 727, 45, "laserBar"));
		laserBar.add(ss.grabImage(2, 2, 727, 45, "laserBar"));
		laserBar.add(ss.grabImage(2, 3, 727, 45, "laserBar"));
		laserBar.add(ss.grabImage(2, 4, 727, 45, "laserBar"));
		
		pummelBar.add(ss.grabImage(1, 1, 727, 45, "pummelBar"));
		pummelBar.add(ss.grabImage(1, 2, 727, 45, "pummelBar"));
		pummelBar.add(ss.grabImage(1, 3, 727, 45, "pummelBar"));
		pummelBar.add(ss.grabImage(1, 4, 727, 45, "pummelBar"));
		pummelBar.add(ss.grabImage(1, 5, 727, 45, "pummelBar"));
		pummelBar.add(ss.grabImage(1, 6, 727, 45, "pummelBar"));
		pummelBar.add(ss.grabImage(1, 7, 727, 45, "pummelBar"));
		pummelBar.add(ss.grabImage(2, 1, 727, 45, "pummelBar"));
		pummelBar.add(ss.grabImage(2, 2, 727, 45, "pummelBar"));
		pummelBar.add(ss.grabImage(2, 3, 727, 45, "pummelBar"));
		pummelBar.add(ss.grabImage(2, 4, 727, 45, "pummelBar"));
		
		
		
	}
	
	/**
	 * 
	 * @param g
	 * renders the bar at its current level
	 */
	public void render(Graphics g) {
		
		if(exp < ((expToUpgrade / 41) * 1)) g.drawImage(progressBar.get(0), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 2)) g.drawImage(progressBar.get(1), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 3)) g.drawImage(progressBar.get(2), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 4)) g.drawImage(progressBar.get(3), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 5)) g.drawImage(progressBar.get(4), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 6)) g.drawImage(progressBar.get(5), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 7)) g.drawImage(progressBar.get(6), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 8)) g.drawImage(progressBar.get(7), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 9)) g.drawImage(progressBar.get(8), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 10)) g.drawImage(progressBar.get(9), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 11)) g.drawImage(progressBar.get(10), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 12)) g.drawImage(progressBar.get(11), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 13)) g.drawImage(progressBar.get(12), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 14)) g.drawImage(progressBar.get(13), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 15)) g.drawImage(progressBar.get(14), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 16)) g.drawImage(progressBar.get(15), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 17)) g.drawImage(progressBar.get(16), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 18)) g.drawImage(progressBar.get(17), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 19)) g.drawImage(progressBar.get(18), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 20)) g.drawImage(progressBar.get(19), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 21)) g.drawImage(progressBar.get(20), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 22)) g.drawImage(progressBar.get(21), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 23)) g.drawImage(progressBar.get(22), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 24)) g.drawImage(progressBar.get(23), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 25)) g.drawImage(progressBar.get(24), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 26)) g.drawImage(progressBar.get(25), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 27)) g.drawImage(progressBar.get(26), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 28)) g.drawImage(progressBar.get(27), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 29)) g.drawImage(progressBar.get(28), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 30)) g.drawImage(progressBar.get(29), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 31)) g.drawImage(progressBar.get(30), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 32)) g.drawImage(progressBar.get(31), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 33)) g.drawImage(progressBar.get(32), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 34)) g.drawImage(progressBar.get(33), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 35)) g.drawImage(progressBar.get(34), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 36)) g.drawImage(progressBar.get(35), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 37)) g.drawImage(progressBar.get(36), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 38)) g.drawImage(progressBar.get(37), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 39)) g.drawImage(progressBar.get(38), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 40)) g.drawImage(progressBar.get(39), Game.camX + 284, Game.camY + 335, null);
		else if(exp < ((expToUpgrade / 41) * 41)) g.drawImage(progressBar.get(39), Game.camX + 284, Game.camY + 335, null);
		
		else if(exp >= ((expToUpgrade / 41) * 41)) {
			g.drawImage(progressBar.get(40), Game.camX + 316, Game.camY + 335, null);
		}
		
		g.drawImage(healthBar.get(healthLevel), Game.camX + 316, Game.camY + 499, null);
		
		g.drawImage(allyBar.get(allyLevel), Game.camX + 316, Game.camY + 549, null);
		
		g.drawImage(pummelBar.get(pummelLevel), Game.camX + 316, Game.camY + 599, null);
		
		g.drawImage(laserBar.get(laserLevel), Game.camX + 316, Game.camY + 649, null);
		
	}


}
