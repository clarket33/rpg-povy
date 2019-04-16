import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


/**
 * 
 * @author clarkt5
 *a chest stores an item, until it is opened by the player
 */
public class Chest extends GameObject{
	private Item item;
	private boolean opened = false;
	private int count = 0;
	private Random rand;
	private BufferedImage text;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param id
	 * uses a random integer to randomize the item that is stored
	 */
	public Chest(float x, float y, ID id) {
		super(x, y, id);
		rand = new Random();
		int val = rand.nextInt(37);
		
		text = null;
		try {
	        text = ImageIO.read(new File("res/textbox.png"));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
		
		if(val >= 0 && val <=10)item = new Item(Item.ItemType.SmallHP);
		else if(val >= 11 && val <= 12)item = new Item(Item.ItemType.MaxHP);
		else if(val >= 13 && val <= 16)item = new Item(Item.ItemType.LargeHP);
		else if(val >= 17 && val <= 24)item = new Item(Item.ItemType.SmallAttackBoost);
		else if(val >= 25 && val <= 26)item = new Item(Item.ItemType.LargeAttackBoost);
		else if(val >= 27 && val <= 34)item = new Item(Item.ItemType.SmallDefenseBoost);
		else if(val >= 35 && val <= 36)item = new Item(Item.ItemType.LargeDefenseBoost);
		
		
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	@Override
	/**
	 * draws it open or closed
	 */
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if(opened) {
			g.drawImage(Game.dungeonTiles.get(336), (int)x, (int)y, null);
			if(count <= 80) {
				
				count++;
				Font fo = new Font("verdana", 1, 40);
				g.setColor(Color.CYAN);
				g.setFont(fo);
				g.drawImage(text, Game.camX + 120, Game.camY + 625, null);
				if(item.toString().contains("Small HP") || item.toString().contains("Large HP") || item.toString().contains("Max HP")) {
					g.setColor(Color.GREEN);
					g.drawString(item.toString(), Game.camX + 550, Game.camY + 790);
				}
				else if(item.toString().contains("Small Attack Boost") || item.toString().contains("Large Attack Boost")) {
					g.setColor(Color.RED);
					g.drawString(item.toString(), Game.camX + 470, Game.camY + 790);
				}
				else {
					g.setColor(Color.BLUE);
					g.drawString(item.toString(), Game.camX + 470, Game.camY + 790);
				}
				
				
					
				
				g.drawImage(item.getImage().getScaledInstance(140, 140, Image.SCALE_DEFAULT), Game.camX + 310, Game.camY + 700, null);
			}
		}
		else {
			g.drawImage(Game.dungeonTiles.get(335), (int)x, (int)y, null);
		}
		
		g.drawRect((int)x-48, (int)y, 144, 96);
		
	}
	@Override
	/**
	 * returns the bounds that the player can be within to open the chest
	 */
	public Rectangle getBounds() {
		return new Rectangle((int)x-48, (int)y, 144, 96);
	}
	@Override
	public GameObject copy() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * if unopened, open the chest
	 */
	public void open() {
		if(opened == false) {
			opened = true;
			AudioPlayer.getSound("chestOpen").play(1, (float).1);
			Game.itemPouch.addItem(item);
		}
	}

	@Override
	public void takeDamage(int damage) {
		// TODO Auto-generated method stub
		
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
