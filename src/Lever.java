
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Lever object, Povy can interact with it to perform a specific action
 * @author clarkt5
 *
 */

public class Lever extends GameObject{
	private boolean pushed = false;
	private int count = 0;
	Handler handler;
	
	public Lever(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	@Override
	/**
	 * renders the lever
	 */
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if(pushed) {
			if(count >= 2) {
				g.drawImage(Game.dungeonTiles.get(383), (int)x, (int)y, null);
			}
			else {
				g.drawImage(Game.dungeonTiles.get(382), (int)x, (int)y, null);
				count++;
			}
		}
		else {
			g.drawImage(Game.dungeonTiles.get(381), (int)x, (int)y, null);
		}
		
	}
	@Override
	/**
	 * returns bounds that if the user is within, it can interact with it
	 */
	public Rectangle getBounds() {
		return new Rectangle((int)x-32, (int)y-32, 96, 96);
	}
	@Override
	public GameObject copy() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * pushes the lever, performs an action(opens a gate)
	 */
	public void push() {
		if(pushed == false) {
			pushed = true;
			AudioPlayer.getSound("chestOpen").play(1, (float).1);
			for(int i = 0; i < handler.objects.size(); i++) {
				if(handler.objects.get(i) instanceof Gate) {
					Gate temp = (Gate)handler.objects.get(i);
					if(temp.gateNum() == 2) {
						temp.hasKey();
						temp.open();
					}
				}
			}
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
