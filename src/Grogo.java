
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
 * Grogo is the first ally that Povy gains in the game. He is stored in the Ally Pouch
 * after the opening sequence where he is acquired. He is used to help povy in battle
 * @author clarkt5
 *
 */
public class Grogo extends GameObject{
	
	Handler handler;
	private ArrayList<BufferedImage> idle, largeIdle;
	private ArrayList<BufferedImage> walkRight;
	private ArrayList<BufferedImage> walkLeft;
	private ArrayList<BufferedImage> fireAttack;
	private int idleAnimation, walkAnimation, fireAnimation;
	private int changeCount = 0;
	private boolean setX = false;
	private int origX = 0, origY = 0;
	
	/**
	 * creates Grogo and loads in all of the resources needed
	 * @param x
	 * @param y
	 * @param id
	 * @param handler
	 */
	public Grogo(float x, float y, ID id, Handler handler){
		super(x, y, id);
		this.handler = handler;
		this.height = 60;
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		idle = new ArrayList<BufferedImage>();
		largeIdle = new ArrayList<BufferedImage>();
		walkRight = new ArrayList<BufferedImage>();
		walkLeft = new ArrayList<BufferedImage>();
		fireAttack = new ArrayList<BufferedImage>();
		
		idle.add(ss.grabImage(1, 1, 324, 150,"grogo"));
		idle.add(ss.grabImage(1, 2, 324, 150,"grogo"));
		idle.add(ss.grabImage(1, 3, 324, 150,"grogo"));
		idle.add(ss.grabImage(1, 4, 324, 150,"grogo"));
		idle.add(ss.grabImage(1, 5, 324, 150,"grogo"));
		
		largeIdle.add(toBufferedImage(ss.grabImage(1, 1, 324, 150,"grogo").getScaledInstance(358, 166, Image.SCALE_AREA_AVERAGING)));
		largeIdle.add(toBufferedImage(ss.grabImage(1, 2, 324, 150,"grogo").getScaledInstance(358, 166, Image.SCALE_AREA_AVERAGING)));
		largeIdle.add(toBufferedImage(ss.grabImage(1, 3, 324, 150,"grogo").getScaledInstance(358, 166, Image.SCALE_AREA_AVERAGING)));
		largeIdle.add(toBufferedImage(ss.grabImage(1, 4, 324, 150,"grogo").getScaledInstance(358, 166, Image.SCALE_AREA_AVERAGING)));
		largeIdle.add(toBufferedImage(ss.grabImage(1, 5, 324, 150,"grogo").getScaledInstance(358, 166, Image.SCALE_AREA_AVERAGING)));
		
		walkRight.add(ss.grabImage(2, 1, 324, 150,"grogo"));
		walkRight.add(ss.grabImage(2, 2, 324, 150,"grogo"));
		walkRight.add(ss.grabImage(2, 3, 324, 150,"grogo"));
		walkRight.add(ss.grabImage(2, 4, 324, 150,"grogo"));
		walkRight.add(ss.grabImage(2, 5, 324, 150,"grogo"));
		
		walkLeft.add(ss.grabImage(2, 1, 100, 150,"grogo2"));
		walkLeft.add(ss.grabImage(2, 2, 100, 150,"grogo2"));
		walkLeft.add(ss.grabImage(2, 3, 100, 150,"grogo2"));
		walkLeft.add(ss.grabImage(2, 4, 100, 150,"grogo2"));
		walkLeft.add(ss.grabImage(2, 5, 100, 150,"grogo2"));
		
		
		fireAttack.add(ss.grabImage(3, 1, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(3, 2, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(3, 3, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(3, 4, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(3, 5, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(4, 1, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(4, 2, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(4, 3, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(4, 4, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(4, 5, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(5, 1, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(5, 2, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(5, 3, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(5, 4, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(5, 5, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(6, 1, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(6, 2, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(6, 3, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(6, 4, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(6, 5, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(7, 1, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(7, 2, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(7, 3, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(7, 4, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(7, 5, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(8, 1, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(8, 2, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(8, 3, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(8, 4, 324, 150,"grogo"));
		fireAttack.add(ss.grabImage(8, 5, 324, 150,"grogo"));
		
		idleAnimation = 0;
		walkAnimation = 0;
		fireAnimation = 0;
		velX = 0;
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		
		
		//x = Game.clamp((int)x, 0, Game.WIDTH-56);
		//y = Game.clamp((int)y, 0, Game.HEIGHT-76);
		
	}
	
	/**
	 * returns the bounds of Grogo
	 */
	public Rectangle getBounds() {
		return new Rectangle((int)x + 40, (int)y + 88, 39, 13);
	}
	
	/**
	 * renders Grogo
	 */
	public void render(Graphics g) {
		//g.setColor(Color.green);
		//g.fillRect((int)x, (int)y, 50, 50);
		if(Game.gameState == Game.STATE.Battle) {
			if(Battle.menuPosition == 4 && Battle.battleState == Battle.BATTLESTATE.PlayerTurnAction) {
				if(velX == 0) {
					g.drawImage(fireAttack.get(fireAnimation), (int)x, (int)y, null);
					changeCount++;
					if(changeCount % 15 == 0) {
						fireAnimation++;
					}
					if(fireAnimation >= 11 && fireAnimation <= 17) {
						Battle.contact = true;
					}
					else {
						Battle.contact = false;
					}
					if((fireAnimation >= 7 && fireAnimation <= 20) && changeCount % 15 == 0) {
						Battle.takeDamage = true;
						AudioPlayer.getSound("fire").play(1, (float).1);
					}
					else {
						Battle.takeDamage = false;
					}
					if(fireAnimation == 28) {
						fireAnimation = 0;
						changeCount = 0;
						velX = -3;
						this.setX((int)this.getX()-150);
					}
				}
				else if(velX > 0) {
					g.drawImage(walkRight.get(walkAnimation), (int)x, (int)y, null);
					changeCount++;
					if(changeCount % 4 == 0) {
						walkAnimation++;
					}
					if(walkAnimation == 9) {
						walkAnimation = 0;
						changeCount = 0;
					}
				}
				else {
					g.drawImage(walkLeft.get(walkAnimation), (int)x, (int)y, null);
					changeCount++;
					if(changeCount % 4 == 0) {
						walkAnimation++;
					}
					if(walkAnimation == 9) {
						walkAnimation = 0;
						changeCount = 0;
					}
				}
			}
			else {
				g.drawImage(largeIdle.get(idleAnimation), Game.camX + (int)x-20, Game.camY + (int)y-40, null);
				changeCount++;
				if(changeCount % 5 == 0) {
					idleAnimation++;
				}
				if(idleAnimation == 5) {
					idleAnimation = 0;
					changeCount = 0;
				}
			}
		}
		else {
			if(Game.allies.allies.contains(this)) {
				g.drawImage(largeIdle.get(idleAnimation), Game.camX + (int)x-20, Game.camY + (int)y-40, null);
				changeCount++;
				if(changeCount % 2 == 0) {
					idleAnimation++;
				}
				if(idleAnimation == 5) {
					idleAnimation = 0;
					changeCount = 0;
				}
			}
			else {
				g.drawImage(idle.get(idleAnimation), (int)x, (int)y, null);
				changeCount++;
				if(changeCount % 10 == 0) {
					idleAnimation++;
				}
				if(idleAnimation == 5) {
					idleAnimation = 0;
					changeCount = 0;
				}
			}
		}
	}

	@Override
	/**
	 * returns a copy of Grogo
	 */
	public Grogo copy() {
		// TODO Auto-generated method stub
		return new Grogo(x, y, id, handler);
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
	 * saves his original spot in the battle state
	 */
	public void originalSpot() {
		origX = (int)this.x;
		origY = (int)this.y;
	}
	/**
	 * 
	 * @param img
	 * @return a buffered image converted from an original image
	 */
	private static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
