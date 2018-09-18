import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
/**
 * 
 * @author clarkt5
 * AllyPouch stores all of Povy's current allies in a linked list
 */
public class AllyPouch {
	
	LinkedList<GameObject> allies = new LinkedList<GameObject>();
	private ArrayList<BufferedImage> grogoIdle;
	private int changeCount = 0, idleAnimation = 0;
	
	public AllyPouch() {
		allies = new LinkedList<GameObject>();
		
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		grogoIdle = new ArrayList<BufferedImage>();
		grogoIdle.add(toBufferedImage(ss.grabImage(1, 1, 216, 100,"grogo").getScaledInstance(358, 166, Image.SCALE_AREA_AVERAGING)));
		grogoIdle.add(toBufferedImage(ss.grabImage(1, 2, 216, 100,"grogo").getScaledInstance(358, 166, Image.SCALE_AREA_AVERAGING)));
		grogoIdle.add(toBufferedImage(ss.grabImage(1, 3, 216, 100,"grogo").getScaledInstance(358, 166, Image.SCALE_AREA_AVERAGING)));
		grogoIdle.add(toBufferedImage(ss.grabImage(1, 4, 216, 100,"grogo").getScaledInstance(358, 166, Image.SCALE_AREA_AVERAGING)));
		grogoIdle.add(toBufferedImage(ss.grabImage(1, 5, 216, 100,"grogo").getScaledInstance(358, 166, Image.SCALE_AREA_AVERAGING)));
		
		
	}
	
	
	/**
	 * 
	 * @param g
	 * adds a game object ally to the linked list
	 */
	public void addAlly(GameObject g) {
		allies.add(g);
	}
	
	/**
	 * 
	 * @param g
	 * renders the allies to the screen, done in the ally screen of a battle or
	 * in the pause menu
	 */
	public void render(Graphics g) {
		g.setFont(new Font("arial", 1, 15));
		g.setColor(Color.GREEN);
		for(int i = 0; i < allies.size(); i++) {
			//allies.get(i).render(g);
			if(allies.get(i).getID() == ID.Grogo) {
				if(Game.gameState == Game.STATE.Battle) {
					g.drawImage(grogoIdle.get(idleAnimation), Game.camX + 235, Game.camY + 255, null);
					changeCount++;
					if(changeCount % 5 == 0) {
						idleAnimation++;
					}
					if(idleAnimation == 5) {
						idleAnimation = 0;
						changeCount = 0;
					}
				}
				else {
					g.drawImage(grogoIdle.get(idleAnimation), Game.camX + 235, Game.camY + 255, null);
					changeCount++;
					if(changeCount % 2 == 0) {
						idleAnimation++;
					}
					if(idleAnimation == 5) {
						idleAnimation = 0;
						changeCount = 0;
					}
				}
				g.drawString("Name: Grogo", Game.camX + 185, Game.camY + 435);
				g.drawString("Ally Type: Attack", Game.camX + 185,  Game.camY + 455);
				g.drawString("Type Description: Heats up", Game.camX + 185,  Game.camY + 475);
				g.drawString("enemies with a blazing fire", Game.camX + 185,  Game.camY + 495);
			}
		}
	}
	
	/**
	 * 
	 * @param img
	 * converts an image to a bufferedimage
	 * @return a bufferedImage after taking in an image
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
