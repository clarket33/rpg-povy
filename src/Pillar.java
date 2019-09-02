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
 *a pillar is a part of a puzzle the player has to solve, selecting pillars in a correct 
 *order
 */
public class Pillar extends GameObject{
	private boolean selected = false;
	private int num;
	private int order;
	private Handler handler;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param id
	 * uses a random integer to randomize the item that is stored
	 */
	public Pillar(float x, float y, ID id, int num, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.num = num;
		if(num == 367) {
			order = 1;
		}
		else if(num == 366) {
			order = 2;
		}
		else if(num == 368) {
			order = 3;
		}
		
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
			g.drawImage(Game.dungeonTiles.get(num), (int)x, (int)y, null);
		//	g.drawRect((int)x, (int)y-48, 48, 144);
			
		
	}
	@Override
	/**
	 * returns the bounds that the player can be within to select a pillar
	 */
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y-48, 48, 144);
	}
	@Override
	public GameObject copy() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * if unselected, select the pillar
	 */
	public boolean select() {
		if(Game.pillarOrder != 4) {
			if(selected == false) {
				selected = true;
				if(Game.pillarOrder == order) {
					AudioPlayer.getSound("pillarTouch").play(1, (float).1);
					Game.pillarOrder++;
					if(Game.pillarOrder == 4) {
						handler.addObject(new Stair(3072, 3024, ID.NonEnemy, 8, handler));
						HUD.HEALTH = HUD.maxHealth;
						AudioPlayer.getSound("pillarFinish").play(1, (float).5);
					}
					return true;
				}
				else {
					return false;
				}
			}
			else {
				if(Game.pillarOrder == order+1) {
					return true;
				}
				return false;
			}
		}
		return true;
	}
	
	public boolean canSelect() {
		if(Game.pillarOrder != 4) {
			if(selected == false) {
				return true;
			}
			if(order == 1) {
				if(Game.pillarOrder == 3) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void deSelect() {
		selected = false;
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