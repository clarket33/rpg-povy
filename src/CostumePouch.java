import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
/**
 * 
 * @author clarkt5
 * CostumePouch stores all of Povy's current costumes in a linked list
 */
public class CostumePouch {
	
	LinkedList<Costume> costumes = new LinkedList<Costume>();
	private BufferedImage purpleCostume;
	private BufferedImage blueCostume;
	private String equipped = "blue";
	private Handler handler;
	
	public CostumePouch(Handler handler) {
		costumes = new LinkedList<Costume>();
		purpleCostume = null;
		blueCostume = null;
		this.handler = handler;
		try {
	        purpleCostume = ImageIO.read(new File("res/purpleOutfit.png"));
	        blueCostume = ImageIO.read(new File("res/blueOutfit.png"));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		//SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		
		
		
	}
	
	
	/**
	 * 
	 * @param costume c
	 * adds a costume to the costume linked list
	 */
	public void addCostume(Costume c) {
		costumes.add(c);
	}
	
	/**
	 * 
	 * @param 
	 * returns the amount of Costumes Povy has
	 */
	public int getCostumeAmount() {
		return costumes.size();
	}
	
	/**
	 * 
	 * @param g
	 * renders the costumes to the screen, done in the pause menu
	 * 
	 */
	public void render(Graphics g) {
		g.setFont(new Font("arial", 1, 15));
		g.setColor(Color.GREEN);
		for(int i = 0; i < costumes.size(); i++) {
			//allies.get(i).render(g);
			if(costumes.get(i).getCostume() == "blue") {
				
				g.drawImage(blueCostume, Game.camX + 250, Game.camY + 345, null);
				
				g.drawString("Attire: Original Blue", Game.camX + 185, Game.camY + 435);
				g.drawString("Description: Povy's original", Game.camX + 185,  Game.camY + 455);
				g.drawString("suit given to him as", Game.camX + 185,  Game.camY + 475);
				g.drawString("child", Game.camX + 185,  Game.camY + 495);
				
				if(equipped.contains("blue")) {
					g.setFont(new Font("arial", 1, 25));
					g.drawString("EQUIPPED", Game.camX + 220, Game.camY + 330);
				}
			}
			
			if(costumes.get(i).getCostume() == "purple") {
				g.setFont(new Font("arial", 1, 15));
				g.drawImage(purpleCostume, Game.camX + 483, Game.camY + 345, null);
				
				g.drawString("Attire: Plasmic Purple", Game.camX + 418, Game.camY + 435);
				g.drawString("Description: Found on a", Game.camX + 418,  Game.camY + 455);
				g.drawString("secret path in Zatolib's", Game.camX + 418,  Game.camY + 475);
				g.drawString("dungeon", Game.camX + 418,  Game.camY + 495);
				
				if(equipped.contains("purple")) {
					g.setFont(new Font("arial", 1, 25));
					g.drawString("EQUIPPED", Game.camX + 448, Game.camY + 330);
				}
			}
		}
	}
	
	public void equip(String name) {
		equipped = name;
		for(int i = 0; i < handler.objects.size(); i++) {
			if(handler.objects.get(i).id == ID.Povy) {
				if(handler.objects.get(i) instanceof Povy) {
					Povy temp = (Povy)handler.objects.get(i);
					temp.changeOutfit(name, new SpriteSheet(Game.sprite_sheet));
					
				}
				
			}
		}
	}
}