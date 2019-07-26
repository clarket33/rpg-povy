import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * 
 * @author clarkt5
 *a pillar is a part of a puzzle the player has to solve, selecting pillars in a correct 
 *order
 */
public class LightningTrap extends GameObject{
	private int num, lightCount = 0, changeCount = 0;
	private ArrayList<BufferedImage> lightning, lightning1;
	private boolean pos;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param id
	 * uses a random integer to randomize the item that is stored
	 */
	public LightningTrap(float x, float y, ID id, int num) {
		super(x, y, id);
		this.num = num;
		if(num == 0) {
			this.height = 200;
			velX = 5;
		}
		if(num == 1 || num == 2) {
			this.height = 48;
			velY = 4;
		}
		if(num == 2) {
			this.height = 48;
			velY = 2;
		}
		if(num == 3) {
			this.height = 48;
			velY = 1;
		}
		if(num == 4) {
			this.height = 200;
			velX = 3;
		}
		if(num == 5) {
			this.height = 200;
			velX = 3;
		}
		if(num == 6) {
			this.height = 48;
			velY = 3;
		}
		
		pos = true;
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		lightning = new ArrayList<BufferedImage>();
		lightning.add(ss.grabImage(1, 1, 48, 200, "lightningTrap"));
		lightning.add(ss.grabImage(1, 2, 48, 200, "lightningTrap"));
		lightning.add(ss.grabImage(1, 3, 48, 200, "lightningTrap"));
		lightning.add(ss.grabImage(1, 4, 48, 200, "lightningTrap"));
		lightning.add(ss.grabImage(2, 1, 48, 200, "lightningTrap"));
		lightning.add(ss.grabImage(2, 2, 48, 200, "lightningTrap"));
		//lightning.add(ss.grabImage(2, 3, 48, 192, "lightningTrap"));
		
		lightning1 = new ArrayList<BufferedImage>();
		lightning1.add(ss.grabImage(1, 1, 192, 48, "lightningTrap1"));
		lightning1.add(ss.grabImage(1, 2, 192, 48, "lightningTrap1"));
		lightning1.add(ss.grabImage(1, 3, 192, 48, "lightningTrap1"));
		lightning1.add(ss.grabImage(2, 1, 192, 48, "lightningTrap1"));
		lightning1.add(ss.grabImage(2, 2, 192, 48, "lightningTrap1"));
		lightning1.add(ss.grabImage(2, 3, 192, 48, "lightningTrap1"));
		
		
		
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(num == 0) {
			this.x += velX;
			if(this.x >= (1696*3) && pos == true) {
				velX *= -1;
				pos = false;
			}
			if(this.x <= (1296*3) && pos == false) {
				velX *= -1;
				pos = true;
			}
		}
		else if(num == 1){
			this.y += velY;
			if(this.y >= (1632*3) && pos == true) {
				velY *= -1;
				pos = false;
			}
			if(this.y <= (1440*3) && pos == false) {
				velY *= -1;
				pos = true;
			}
		}
		else if(num == 2) {
			this.y += velY;
			if(this.y >= (5616) && pos == true) {
				velY *= -1;
				pos = false;
			}
			if(this.y <= (5088) && pos == false) {
				velY *= -1;
				pos = true;
			}
		}
		else if(num == 3) {
			this.y += velY;
			if(this.y >= (240) && pos == true) {
				velY *= -1;
				pos = false;
			}
			if(this.y <= (96) && pos == false) {
				velY *= -1;
				pos = true;
			}
		}
		else if(num == 4) {
			this.x += velX;
			if(this.x >= (6288) && pos == true) {
				velX *= -1;
				pos = false;
			}
			if(this.x <= (5760) && pos == false) {
				velX *= -1;
				pos = true;
			}
		}
		else if(num == 5) {
			this.x += velX;
			if(this.x >= (6816) && pos == true) {
				velX *= -1;
				pos = false;
			}
			if(this.x <= (6288) && pos == false) {
				velX *= -1;
				pos = true;
			}
		}
		else if(num == 6) {
			this.y += velY;
			if(this.y >= (1488) && pos == true) {
				velY *= -1;
				pos = false;
			}
			if(this.y <= (1152) && pos == false) {
				velY *= -1;
				pos = true;
			}
		}
		
	}
	@Override
	/**
	 * draws it open or closed
	 */
	public void render(Graphics g) {
		if(num == 0 || num == 4 || num == 5) {
			g.drawImage(lightning.get(lightCount), (int)x, (int)y, null);
			changeCount++;
			if(changeCount % 7 == 0) {
				lightCount++;
				changeCount = 0;
			}
			if(lightCount == 6) {
				lightCount = 0;
			}
		}
		else {
			g.drawImage(lightning1.get(lightCount), (int)x, (int)y, null);
			changeCount++;
			if(changeCount % 7 == 0) {
				lightCount++;
				changeCount = 0;
			}
			if(lightCount == 6) {
				lightCount = 0;
			}
		}
		//g.drawRect((int)x+7, (int)y+10, 35, 170);
		
	}
	@Override
	/**
	 * returns the bounds that the player can be within to select a pillar
	 */
	public Rectangle getBounds() {
		if(num == 0 || num == 4 || num == 5) return new Rectangle((int)x+7, (int)y+10, 35, 170);
		else return new Rectangle((int)x+10, (int)y+7, 170, 35);
	}
	@Override
	public GameObject copy() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	public int getNum() {
		int tempNum = num;
		return tempNum;
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

	@Override
	public Rectangle areaCoverage() {
		// TODO Auto-generated method stub
		return null;
	}

	
}