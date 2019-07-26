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
public class SpaceShip extends GameObject{
	/**
	 * 
	 * @param x
	 * @param y
	 * @param id
	 * grogo's spaceship
	 */
	private BufferedImage grogoShip;
	private boolean selected = false;
	public SpaceShip(float x, float y, ID id) {
		super(x, y, id);
		this.height = 200;
		 grogoShip = null;
			
		    try {
		        grogoShip = ImageIO.read(new File("res/grogoShip.png"));
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * @return if spaceship is selected or not
	 */
	public boolean isSelected() {
		return selected;
	}
	
	
	/**
	 * selects spaceship triggers level 2;
	 */
	public void select() {
		Game.gameState = Game.STATE.Transition;
		Game.levTwo = true;
		selected = true;
		
	}
	
	
	
	@Override
	/**
	 * draws it open or closed
	 */
	public void render(Graphics g) {
		// TODO Auto-generated method stub
			if(!Game.levTwo && Game.gameState != Game.STATE.levelTwoTran) g.drawImage(grogoShip, (int)x, (int)y, null);
			
		
		
	}
	@Override
	/**
	 * returns the bounds that the player can be within to select a pillar
	 */
	public Rectangle getBounds() {
		return new Rectangle((int)x + 80, (int)y + 96, 453, 160);
	}
	@Override
	public GameObject copy() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * if unselected, select the pillar
	 */

	

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