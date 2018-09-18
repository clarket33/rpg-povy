import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
/**
 * 
 * @author clarkt5
 * gate object, either closed or open
 */
public class Gate extends GameObject{
	private boolean opened = false;
	private int gateNum;
	private boolean canBeOpened = false;
	
	
	public Gate(float x, float y, ID id, int gateNum) {
		super(x, y, id);
		this.gateNum = gateNum;
		if(gateNum == 1) {
			hasKey();
		}
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	@Override
	/**
	 * render sthe gate as closed or open
	 */
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawString(Integer.toString(gateNum), (int)x, (int)y-20);
		if(!opened) {
			g.drawImage(Game.dungeonTiles.get(215), (int)x, (int)y, null);
			g.drawImage(Game.dungeonTiles.get(215), (int)x+32, (int)y, null);
		}
		g.drawRect((int)x, (int)y-32, 64, 96);
		
	}
	@Override
	/**
	 * bounds of which the user can be within to interact with the gate
	 */
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y-32, 64, 96);
	}
	@Override
	public GameObject copy() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * if Povy has a key(canBeOpened = true) for the gate and the gate is closed,
	 * open it
	 * If doesn't have a key, play an error sound
	 */
	public void open() {
		if(opened == false && canBeOpened == true) {
			opened = true;
			AudioPlayer.getSound("gateOpen").play(1, (float).1);
			//System.out.println(Game.collisionTiles.get(new Integer(0)).get("214"));
			Game.collisionTiles.get(new Integer(0)).get("214").set((gateNum*4), 0);
			Game.collisionTiles.get(new Integer(0)).get("214").set((gateNum*4) + 1, 0);
			Game.collisionTiles.get(new Integer(0)).get("214").set((gateNum*4) + 2, 0);
			Game.collisionTiles.get(new Integer(0)).get("214").set((gateNum*4) + 3, 0);
			return;
		}
		else if(opened == false) {
			AudioPlayer.getSound("errorGate").play(1, (float).1);
			return;
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
	/**
	 * 
	 * @return "id" number of the gate
	 */
	public int gateNum() {
		return gateNum;
	}
	/**
	 * 
	 * @return true because Povy now has the key and the gate can be opened
	 */
	public boolean hasKey() {
		canBeOpened = true;
		return canBeOpened;
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}

