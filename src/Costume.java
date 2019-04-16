import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * 
 * @author clarkt5
 *a costume is an outfit worn by Povy
 */
public class Costume extends GameObject{
	private boolean gotten = false;
	private int count = 0;
	private String costumeColor;
	private BufferedImage purpleCostume;
	private Handler handler;
	private ArrayList<BufferedImage> purpleCost = new ArrayList<BufferedImage>();
	private SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
	private int purpCount = 0, speedControl = 0; 
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param id
	 * initializes the costume
	 */
	public Costume(float x, float y, ID id, String costumeColor, Handler handler) {
		super(x, y, id);
		this.costumeColor = costumeColor;
		this.handler = handler;
		
		purpleCostume = null;
		try {
	        purpleCostume = ImageIO.read(new File("res/purpleOutfit.png"));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
		if(costumeColor.contains("blue")) {
			gotten = true;
		}
		
		purpleCost.add(ss.grabImage(1, 1, 96, 96,"purpleOutfit"));
		purpleCost.add(ss.grabImage(1, 2, 96, 96,"purpleOutfit"));
		purpleCost.add(ss.grabImage(1, 3, 96, 96,"purpleOutfit"));
		purpleCost.add(ss.grabImage(1, 4, 96, 96,"purpleOutfit"));
		purpleCost.add(ss.grabImage(1, 5, 96, 96,"purpleOutfit"));
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	@Override
	/**
	 * draws the costume on the screen before Povy acquires it
	 */
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if(!gotten) {
			g.drawImage(purpleCost.get(purpCount), (int)x, (int)y, null);
			speedControl++;
			if(speedControl % 2 == 0) {
				purpCount++;
			}
			if(purpCount == purpleCost.size()) {
				purpCount = 0;
			}
		}
		
	}
	@Override
	/**
	 * returns the bounds that the player can be within to obtain the costume
	 */
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	@Override
	public GameObject copy() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * if not acquired, acquire the costume
	 */
	public void get() {
		if(gotten == false) {
			gotten = true;
			AudioPlayer.getSound("chestOpen").play(1, (float).1);
			for(int i = 0; i < handler.objects.size(); i++) {
				if(handler.objects.get(i).id == ID.Povy) {
					if(handler.objects.get(i) instanceof Povy) {
						Povy temp = (Povy)handler.objects.get(i);
						temp.changeOutfit(costumeColor, new SpriteSheet(Game.sprite_sheet));
						Game.costumePouch.addCostume(this);
						Game.costumePouch.equip("purple");
					}
					
				}
			}
			
		}
	}

	@Override
	public void takeDamage(int damage) {
		// TODO Auto-generated method stub
		
	}
	
	public String getCostume() {
		String cost;
		return cost = costumeColor;
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
