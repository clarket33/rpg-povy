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
public class Stair extends GameObject{
	private int num;
	private Handler handler;
	/**
	 * 
	 * @param x
	 * @param y
	 * @param id
	 * uses a random integer to randomize the item that is stored
	 */
	public Stair(float x, float y, ID id, int num, Handler h) {
		super(x, y, id);
		this.num = num;
		this.handler = h;
		
	
		
	}
	
	@Override
	public void tick() {
		if(num == 8) {
			for(int m = 0; m< handler.objects.size();m++) {
				if(handler.objects.get(m).id == ID.Povy) {
					Povy p = (Povy)(handler.objects.get(m));
				
					if(p.getBoundsLeft().intersects(new Rectangle((int)x, (int)y, 48, 48))) {
						
						p.setX((int)Game.clampUpLeft((int)p.getX(), (int)x+35));
						//break;
							
					}
					if(p.getBoundsRight().intersects(new Rectangle((int)x, (int)y, 48, 48))) {
						
						p.setX((int)Game.clampDownRight((int)p.getX(), (int)x-60));
						//break;
						
					}
					if(p.getBoundsUp().intersects(new Rectangle((int)x, (int)y, 48, 48))) {
						
						//y = Game.clampUpLeft((int)y, y-25);
						p.setY((int)Game.clampUpLeft((int)p.getY(), (int)y-25));
						//break;
						
					}
					if(p.getBoundsDown().intersects(new Rectangle((int)x, (int)y, 48, 48))) {
						
						//y = Game.clampDownRight((int)y, y-100);
						p.setY((int)Game.clampDownRight((int)p.getY(), (int)y-100));
						//break;
						
					}
				}
			}
		}
		
	}
	@Override
	/**
	 * draws it open or closed
	 */
	public void render(Graphics g) {
		g.drawImage(Game.dungeonTiles.get(328), (int)x, (int)y, null);
		
	}
	@Override
	/**
	 * returns the bounds that the player can be within to select a pillar
	 */
	public Rectangle getBounds() {
		return new Rectangle((int)x-24, (int)y-24, 96, 96);
	
	}
	@Override
	public GameObject copy() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void activate() {
		if(num == 0) {
			for(int m = 0; m< handler.objects.size();m++) {
    			if(handler.objects.get(m).id == ID.Povy) {
    				Povy p = (Povy)(handler.objects.get(m));
    				p.setX(3264);
    				p.setY(4272);
    				//AudioPlayer.getSound("stairs").play(1, (float).5);
    			}
			}
		}
		else if(num == 1) {
			for(int m = 0; m< handler.objects.size();m++) {
    			if(handler.objects.get(m).id == ID.Povy) {
    				Povy p = (Povy)(handler.objects.get(m));
    				p.setX(4656);
    				p.setY(4416);
    			}
			}
		}
		else if(num == 2) {
			for(int m = 0; m< handler.objects.size();m++) {
    			if(handler.objects.get(m).id == ID.Povy) {
    				Povy p = (Povy)(handler.objects.get(m));
    				p.setX(432);
    				p.setY(4752);
    			}
			}
		}
		else if(num == 3) {
			for(int m = 0; m< handler.objects.size();m++) {
    			if(handler.objects.get(m).id == ID.Povy) {
    				Povy p = (Povy)(handler.objects.get(m));
    				p.setX(6384);
    				p.setY(1872);
    			}
			}
		}
		else if(num == 4) {
			for(int m = 0; m< handler.objects.size();m++) {
    			if(handler.objects.get(m).id == ID.Povy) {
    				Povy p = (Povy)(handler.objects.get(m));
    				
    				
    				p.setX(3072);
    				p.setY(1440);
    			}
			}
		}
		else if(num == 5) {
			for(int m = 0; m< handler.objects.size();m++) {
    			if(handler.objects.get(m).id == ID.Povy) {
    				Povy p = (Povy)(handler.objects.get(m));
    				p.setX(1872);
    				p.setY(1152);
    			}
			}
		}
		else if(num == 6) {
			for(int m = 0; m< handler.objects.size();m++) {
    			if(handler.objects.get(m).id == ID.Povy) {
    				Povy p = (Povy)(handler.objects.get(m));
    				p.setX(1872);
    				p.setY(1584);
    			}
			}
		}
		else if(num == 7) {
			for(int m = 0; m< handler.objects.size();m++) {
    			if(handler.objects.get(m).id == ID.Povy) {
    				Povy p = (Povy)(handler.objects.get(m));
    				
    				
    				p.setX(1872);
    				p.setY(2064);
    			}
			}
		}
		else if(num == 8) {
			for(int m = 0; m< handler.objects.size();m++) {
    			if(handler.objects.get(m).id == ID.Povy) {
    				Povy p = (Povy)(handler.objects.get(m));
    				
    				
    				p.setX(4368);
    				p.setY(3312);
    			}
			}
		}

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