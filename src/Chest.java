import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

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
		
		if(val >= 0 && val <=10)item = new Item(Item.ItemType.SmallHP);
		else if(val >= 11 && val <= 12)item = new Item(Item.ItemType.MaxHP);
		else if(val >= 13 && val <= 16)item = new Item(Item.ItemType.LargeHP);
		else if(val >= 17 && val <= 22)item = new Item(Item.ItemType.SmallAttackBoost);
		else if(val >= 23 && val <= 26)item = new Item(Item.ItemType.LargeAttackBoost);
		else if(val >= 27 && val <= 32)item = new Item(Item.ItemType.SmallDefenseBoost);
		else if(val >= 33 && val <= 36)item = new Item(Item.ItemType.LargeDefenseBoost);
		
		
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
		g.drawRect((int)x, (int)y, 32, 64);
		if(opened) {
			g.drawImage(Game.dungeonTiles.get(336), (int)x, (int)y, null);
			if(count <= 20) {
				g.drawImage(item.getImage(), (int)x, (int)y-32, null);
				count++;
			}
		}
		else {
			g.drawImage(Game.dungeonTiles.get(335), (int)x, (int)y, null);
		}
		
	}
	@Override
	/**
	 * returns the bounds that the player can be within to open the chest
	 */
	public Rectangle getBounds() {
		return new Rectangle((int)x-32, (int)y, 96, 64);
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
