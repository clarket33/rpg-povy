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
		grogoIdle.add(ss.grabImage(1, 1, 324, 160,"grogo"));
		grogoIdle.add(ss.grabImage(1, 2, 324, 160,"grogo"));
		grogoIdle.add(ss.grabImage(1, 3, 324, 160,"grogo"));
		grogoIdle.add(ss.grabImage(1, 4, 324, 160,"grogo"));
		grogoIdle.add(ss.grabImage(1, 5, 324, 160,"grogo"));
		
		
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
	 * @param 
	 * returns amount of allies
	 */
	public int allyAmount() {
		return allies.size();
	}
	
	


}
