
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
 * The HUD involves Povy's health and the ally bar, which when filled is when Povy 
 * can invoke an ally attack on an enemy
 * @author clarkt5
 *
 */
public class HUD {
	public int bounds = 0;
	public static int HEALTH = 4;
	public static int maxHealth = 64;
	public static int allyCount = 0;
	
	private ArrayList<BufferedImage> health = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> allyMeter = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> allyIcon = new ArrayList<BufferedImage>();
	
	/**
	 * Loads in the images of the Heads Up Display
	 */
	public HUD() {
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		
		health.add(toBufferedImage(ss.grabImage(1, 1, 17, 17, "health").getScaledInstance(51, 51, Image.SCALE_AREA_AVERAGING)));
		health.add(toBufferedImage(ss.grabImage(1, 2, 17, 17, "health").getScaledInstance(51, 51, Image.SCALE_AREA_AVERAGING)));
		health.add(toBufferedImage(ss.grabImage(1, 3, 17, 17, "health").getScaledInstance(51, 51, Image.SCALE_AREA_AVERAGING)));
		health.add(toBufferedImage(ss.grabImage(1, 4, 17, 17, "health").getScaledInstance(51, 51, Image.SCALE_AREA_AVERAGING)));
		health.add(toBufferedImage(ss.grabImage(1, 5, 17, 17, "health").getScaledInstance(51, 51, Image.SCALE_AREA_AVERAGING)));
		
		allyMeter.add(ss.grabImage(1, 1, 150, 45,"allyMeter"));
		allyMeter.add(ss.grabImage(1, 2, 150, 45,"allyMeter"));
		allyMeter.add(ss.grabImage(1, 3, 150, 45,"allyMeter"));
		allyMeter.add(ss.grabImage(2, 1, 150, 45,"allyMeter"));
		
		allyIcon.add(ss.grabImage(1, 1, 64, 45, "allyIcon1"));
		allyIcon.add(ss.grabImage(1, 2, 64, 45, "allyIcon1"));
	}
	
	public void tick() {
		//HEALTH = (int) Game.clamp(HEALTH, 0, 200 + (bounds/2));
		//greenValue = (int)Game.clamp((float)greenValue, 0, 255);
		if(HEALTH <= 0) {
			HEALTH = 0;
		}
	}
	
	/**
	 * renders the HUD
	 * @param g
	 */
	public void render(Graphics g) {
		if(allyCount == 3) {
			Battle.useAlly = true;
		}
		int temp = HEALTH;
		int coordX, coordY;
		if(Game.gameState == Game.STATE.Battle) {
			coordX = 2;
			coordY = 0;
		}
		else {
			coordX = Game.camX + 2;
			coordY = Game.camY;
		}
		
		for(int i = 16; i <= HEALTH; i+=16) {
			g.drawImage(health.get(0), coordX, coordY, null);
			temp -= 16;
			coordX += 53;
		}
		if(temp < 16) {
			if(temp == 12) {
				g.drawImage(health.get(1), coordX, coordY, null);
				coordX += 53;
				temp = 16 - temp;
			}
			else if(temp == 8) {
				g.drawImage(health.get(2), coordX, coordY, null);
				coordX += 53;
				temp = 16 - temp;
			}
			else if(temp == 4) {
				g.drawImage(health.get(3), coordX, coordY, null);
				coordX += 53;
				temp = 16 - temp;
			}
			
		}
		
		if((maxHealth - HEALTH - (temp)) % 16 == 0) {
			//System.out.println(maxHealth - HEALTH - (temp));
			for(int i = 16; i <= maxHealth - HEALTH - (temp); i+=16) {
				g.drawImage(health.get(4), coordX, coordY, null);
				coordX += 53;
			}
		}
		
		if(Game.gameState == Game.STATE.Battle) {
			g.drawImage(allyMeter.get(allyCount), 800, 20, null);
			g.drawImage(allyIcon.get(0), 950, 20, null);
		}
	}
	/**
	 * 
	 * @param img
	 * @return a bufferedImage converted from an image
	 */
	private static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	

}
