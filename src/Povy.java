
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;




/**
 * Povy is the main character of the game, the user controls him with the arrow keys and tries
 * to save the universe
 * @author clarkt5
 *
 */
public class Povy extends GameObject{
	
	Handler handler;
	public static boolean takingPotion = false;
	private BufferedImage player_image;
	private ArrayList<BufferedImage> idle;
	private int idleCount = 0;
	private int speedControl = 0, idleControl = 0;
	private ArrayList<BufferedImage> moving;
	private ArrayList<BufferedImage> movingUp;
	private ArrayList<BufferedImage> movingDown;
	private ArrayList<BufferedImage> movingLeft;
	private ArrayList<BufferedImage> movingUpLeft;
	private ArrayList<BufferedImage> movingDownLeft;
	private ArrayList<BufferedImage> rotation;
	private ArrayList<BufferedImage> hurt;
	private ArrayList<BufferedImage> pummel;
	private ArrayList<BufferedImage> itemTake;
	private ArrayList<BufferedImage> laserBlaster;
	private BufferedImage laser;
	private int moveCount = 0;
	private int openingCount = 0;
	private int invCounter = 0;
	private int pummelCount = 0;
	private int potionCount = 0, laserCount = 0;
	public static int laserBulletX = 0;
	private boolean hit = false;
	private int hitWait = 0, hitSpeed = 0, hitCount = 0;
	private int pummelTotal = 0;
	private boolean invincible = false;
	
	/**
	 * creates a new Povy
	 * @param x
	 * @param y
	 * @param id
	 * @param handler
	 */
	public Povy(float x, float y, ID id, Handler handler){
		super(x, y, id);
		this.height = 64;
		this.handler = handler;
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		idle = new ArrayList<BufferedImage>();
		moving = new ArrayList<BufferedImage>();
		movingUp = new ArrayList<BufferedImage>();
		movingDown = new ArrayList<BufferedImage>();
		movingLeft = new ArrayList<BufferedImage>();
		movingUpLeft = new ArrayList<BufferedImage>();
		movingDownLeft = new ArrayList<BufferedImage>();
		rotation = new ArrayList<BufferedImage>();
		hurt = new ArrayList<BufferedImage>();
		pummel = new ArrayList<BufferedImage>();
		itemTake = new ArrayList<BufferedImage>();
		laserBlaster = new ArrayList<BufferedImage>();
		laser = null;
		try {
	        laser = ImageIO.read(new File("Povy/res/LaserBullet.png"));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
		idle.add(ss.grabImage(1, 1, 64, 64,"povy"));
		idle.add(ss.grabImage(1, 2, 64, 64,"povy"));
		idle.add(ss.grabImage(1, 3, 64, 64,"povy"));
		idle.add(ss.grabImage(1, 4, 64, 64,"povy"));
		idle.add(ss.grabImage(2, 1, 64, 64,"povy"));
		
		moving.add(ss.grabImage(2, 2, 64, 64,"povy"));
		moving.add(ss.grabImage(2, 3, 64, 64,"povy"));
		moving.add(ss.grabImage(2, 4, 64, 64,"povy"));
		moving.add(ss.grabImage(3, 1, 64, 64,"povy"));
		moving.add(ss.grabImage(3, 2, 64, 64,"povy"));
		moving.add(ss.grabImage(3, 3, 64, 64,"povy"));
		moving.add(ss.grabImage(3, 4, 64, 64,"povy"));
		moving.add(ss.grabImage(4, 1, 64, 64,"povy"));
		moving.add(ss.grabImage(4, 2, 64, 64,"povy"));
		
		
		movingUp.add(ss.grabImage(4, 3, 64, 64,"povy"));
		movingUp.add(ss.grabImage(4, 4, 64, 64,"povy"));
		movingUp.add(ss.grabImage(5, 1, 64, 64,"povy"));
		movingUp.add(ss.grabImage(5, 2, 64, 64,"povy"));
		movingUp.add(ss.grabImage(5, 3, 64, 64,"povy"));
		movingUp.add(ss.grabImage(5, 4, 64, 64,"povy"));
		movingUp.add(ss.grabImage(6, 1, 64, 64,"povy"));
		movingUp.add(ss.grabImage(6, 2, 64, 64,"povy"));
		movingUp.add(ss.grabImage(6, 3, 64, 64,"povy"));
		
		movingDown.add(ss.grabImage(6, 4, 64, 64,"povy"));
		movingDown.add(ss.grabImage(7, 1, 64, 64,"povy"));
		movingDown.add(ss.grabImage(7, 2, 64, 64,"povy"));
		movingDown.add(ss.grabImage(7, 3, 64, 64,"povy"));
		movingDown.add(ss.grabImage(7, 4, 64, 64,"povy"));
		movingDown.add(ss.grabImage(8, 1, 64, 64,"povy"));
		movingDown.add(ss.grabImage(8, 2, 64, 64,"povy"));
		movingDown.add(ss.grabImage(8, 3, 64, 64,"povy"));
		movingDown.add(ss.grabImage(8, 4, 64, 64,"povy"));
		
		movingLeft.add(ss.grabImage(2, 3, 48, 64,"povy2"));
		movingLeft.add(ss.grabImage(2, 2, 48, 64,"povy2"));
		movingLeft.add(ss.grabImage(2, 1, 48, 64,"povy2"));
		movingLeft.add(ss.grabImage(3, 4, 48, 64,"povy2"));
		movingLeft.add(ss.grabImage(3, 3, 48, 64,"povy2"));
		movingLeft.add(ss.grabImage(3, 2, 48, 64,"povy2"));
		movingLeft.add(ss.grabImage(3, 1, 48, 64,"povy2"));
		movingLeft.add(ss.grabImage(4, 4, 48, 64,"povy2"));
		movingLeft.add(ss.grabImage(4, 3, 48, 64,"povy2"));
		
		
		movingUpLeft.add(ss.grabImage(4, 2, 48, 64,"povy2"));
		movingUpLeft.add(ss.grabImage(4, 1, 48, 64,"povy2"));
		movingUpLeft.add(ss.grabImage(5, 4, 48, 64,"povy2"));
		movingUpLeft.add(ss.grabImage(5, 3, 48, 64,"povy2"));
		movingUpLeft.add(ss.grabImage(5, 2, 48, 64,"povy2"));
		movingUpLeft.add(ss.grabImage(5, 1, 48, 64,"povy2"));
		movingUpLeft.add(ss.grabImage(6, 4, 48, 64,"povy2"));
		movingUpLeft.add(ss.grabImage(6, 3, 48, 64,"povy2"));
		movingUpLeft.add(ss.grabImage(6, 2, 48, 64,"povy2"));
		
		movingDownLeft.add(ss.grabImage(6, 1, 48, 64,"povy2"));
		movingDownLeft.add(ss.grabImage(7, 4, 48, 64,"povy2"));
		movingDownLeft.add(ss.grabImage(7, 3, 48, 64,"povy2"));
		movingDownLeft.add(ss.grabImage(7, 2, 48, 64,"povy2"));
		movingDownLeft.add(ss.grabImage(7, 1, 48, 64,"povy2"));
		movingDownLeft.add(ss.grabImage(8, 4, 48, 64,"povy2"));
		movingDownLeft.add(ss.grabImage(8, 3, 48, 64,"povy2"));
		movingDownLeft.add(ss.grabImage(8, 2, 48, 64,"povy2"));
		movingDownLeft.add(ss.grabImage(8, 1, 48, 64,"povy2"));
		
		
		rotation.add(ss.grabImage(7, 3, 150, 140,"spinning"));
		rotation.add(ss.grabImage(7, 4, 150, 140,"spinning"));
		rotation.add(ss.grabImage(8, 1, 150, 140,"spinning"));
		rotation.add(ss.grabImage(8, 2, 150, 140,"spinning"));
		rotation.add(ss.grabImage(8, 3, 150, 140,"spinning"));
		rotation.add(ss.grabImage(8, 4, 150, 140,"spinning"));
		rotation.add(ss.grabImage(9, 1, 150, 140,"spinning"));
		rotation.add(ss.grabImage(9, 2, 150, 140,"spinning"));
		rotation.add(ss.grabImage(9, 3, 150, 140,"spinning"));
		rotation.add(ss.grabImage(9, 4, 150, 140,"spinning"));
		rotation.add(ss.grabImage(10, 1, 150, 140,"spinning"));
		rotation.add(ss.grabImage(10, 2, 150, 140,"spinning"));
		rotation.add(ss.grabImage(10, 3, 150, 140,"spinning"));
		
		hurt.add(ss.grabImage(9, 1, 64, 64,"povy"));
		hurt.add(ss.grabImage(9, 2, 64, 64,"povy"));
		
		pummel.add(ss.grabImage(13, 1, 64, 64,"povy"));
		pummel.add(ss.grabImage(13, 2, 64, 64,"povy"));
		pummel.add(ss.grabImage(13, 3, 64, 64,"povy"));
		pummel.add(ss.grabImage(13, 4, 64, 64,"povy"));
		pummel.add(ss.grabImage(14, 1, 64, 64,"povy"));
		pummel.add(ss.grabImage(14, 2, 64, 64,"povy"));
		pummel.add(ss.grabImage(14, 3, 64, 64,"povy"));
		pummel.add(ss.grabImage(14, 4, 64, 64,"povy"));
		pummel.add(ss.grabImage(15, 1, 64, 64,"povy"));
		pummel.add(ss.grabImage(15, 2, 64, 64,"povy"));
		pummel.add(ss.grabImage(15, 3, 64, 64,"povy"));
		pummel.add(ss.grabImage(15, 4, 64, 64,"povy"));
		pummel.add(ss.grabImage(16, 1, 64, 64,"povy"));
		pummel.add(ss.grabImage(16, 2, 64, 64,"povy"));
		pummel.add(ss.grabImage(16, 3, 64, 64,"povy"));
		
		itemTake.add(ss.grabImage(16, 4, 64, 64,"povy"));
		itemTake.add(ss.grabImage(17, 1, 64, 64,"povy"));
		itemTake.add(ss.grabImage(17, 2, 64, 64,"povy"));
		itemTake.add(ss.grabImage(17, 3, 64, 64,"povy"));
		itemTake.add(ss.grabImage(17, 4, 64, 64,"povy"));
		itemTake.add(ss.grabImage(18, 1, 64, 64,"povy"));
		itemTake.add(ss.grabImage(18, 2, 64, 64,"povy"));
		itemTake.add(ss.grabImage(18, 3, 64, 64,"povy"));
		itemTake.add(ss.grabImage(18, 4, 64, 64,"povy"));
		
		laserBlaster.add(ss.grabImage(9, 3, 64, 64,"povy"));
		laserBlaster.add(ss.grabImage(9, 4, 64, 64,"povy"));
		laserBlaster.add(ss.grabImage(10, 1, 64, 64,"povy"));
		laserBlaster.add(ss.grabImage(10, 2, 64, 64,"povy"));
		laserBlaster.add(ss.grabImage(10, 3, 64, 64,"povy"));
		laserBlaster.add(ss.grabImage(10, 4, 64, 64,"povy"));
		laserBlaster.add(ss.grabImage(11, 1, 64, 64,"povy"));
		laserBlaster.add(ss.grabImage(11, 2, 64, 64,"povy"));
		laserBlaster.add(ss.grabImage(11, 3, 64, 64,"povy"));
		laserBlaster.add(ss.grabImage(11, 4, 64, 64,"povy"));
		laserBlaster.add(ss.grabImage(12, 1, 64, 64,"povy"));
		laserBlaster.add(ss.grabImage(12, 2, 64, 64,"povy"));
		laserBlaster.add(ss.grabImage(12, 3, 64, 64,"povy"));
		laserBlaster.add(ss.grabImage(12, 4, 64, 64,"povy"));
	}
	
	/**
	 * returns a copy of Povy
	 */
	public Povy copy() {
		return new Povy(x, y, id, handler);
	}
		
	/**
	 * makes Povy invincible(able to avoid combat for a short period of time)
	 */
	public void isInvincible() {
		invincible = true;
	}
	
	/**
	 * tick method for Povy
	 */
	public void tick() {
		if(Game.gameState == Game.STATE.Game) {
			x += velX;
			y += velY;
			//x = Game.clamp((int)x, 0, Game.WIDTH-96);
			//y = Game.clamp((int)y, 0, Game.HEIGHT-196);
			if(!invincible) {
				enemyCollision();
				obstacleCollision();
			}
			
			collision();
		}
		if(Game.gameState == Game.STATE.Battle) {
			x += velX;
		}
		if(Game.battleReturn == true && Game.gameState == Game.STATE.Game) {
			this.setVelX(0);
			this.setVelY(0);
		}
		/**
		for(int i = 0; i < handler.objects.size(); i++) {
			System.out.println(handler.objects.get(i));
		}
		System.out.println();
		**/
		
		
		
	}
	
	/**
	 * checks Povy's collision with boundaries on the map
	 */
	public void collision() {
		Iterator<String> itr = Game.collisionTiles.get(new Integer(0)).keySet().iterator();
		while(itr.hasNext()) {
			String cur = itr.next();
			if(!cur.contains("281") && !cur.contains("300")) {
				for(int i = 0; i < Game.collisionTiles.get(new Integer(0)).get(cur).size(); i+=2) {
					if(getBoundsLeft().intersects(new Rectangle(Game.collisionTiles.get(new Integer(0)).get(cur).get(i), Game.collisionTiles.get(new Integer(0)).get(cur).get(i+1),
							32, 32))) {
						x = Game.clampUpLeft((int)x, Game.collisionTiles.get(new Integer(0)).get(cur).get(i)+26);
						break;
							
					}
					if(getBoundsRight().intersects(new Rectangle(Game.collisionTiles.get(new Integer(0)).get(cur).get(i), Game.collisionTiles.get(new Integer(0)).get(cur).get(i+1),
							32, 32))) {	
						
						x = Game.clampDownRight((int)x, Game.collisionTiles.get(new Integer(0)).get(cur).get(i)-42);
						break;
					}
					if(getBoundsUp().intersects(new Rectangle(Game.collisionTiles.get(new Integer(0)).get(cur).get(i), Game.collisionTiles.get(new Integer(0)).get(cur).get(i+1),
							32, 32))) {
						y = Game.clampUpLeft((int)y, Game.collisionTiles.get(new Integer(0)).get(cur).get(i+1)-15);
						break;
					}
					if(getBoundsDown().intersects(new Rectangle(Game.collisionTiles.get(new Integer(0)).get(cur).get(i), Game.collisionTiles.get(new Integer(0)).get(cur).get(i+1),
							32, 32))) {
						
						y = Game.clampDownRight((int)y, Game.collisionTiles.get(new Integer(0)).get(cur).get(i+1)-64);
						break;
					}
				}
			}
		}
	}
	
	
	/**
	 * checks Povy's collision with enemies, triggers battle if they collide
	 * @return
	 */
	public boolean enemyCollision() {
		for(int i = 0; i < handler.objects.size(); i++) {
			if(handler.objects.get(i).id != ID.Povy && handler.objects.get(i).id != ID.NonEnemy) {
				if(getBounds().intersects(handler.objects.get(i).getBounds())) {
					AudioPlayer.getMusic("dungeon").stop();
					AudioPlayer.getSound("fightStart").play(1, (float).1);
					Game.gameState = Game.STATE.Transition;
					/**
						Battle.menuPosition = 0;
						Battle.menuChange = 0;
						Battle.takeDamage = false;
						Battle.left = true;
						Battle.itemSelected = false;
						Battle.contact = false;
					**/
					Battle.battleState = null;
					Game.battle = new Battle(handler, this.copy(), handler.objects.get(i));
					
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * checks collision with objects that can hurt(ex. floor spikes)
	 */
	public void obstacleCollision() {
		Iterator<String> itr = Game.collisionTiles.get(new Integer(0)).keySet().iterator();
		while(itr.hasNext()) {
			String cur = itr.next();
			if(cur.contains("281")) {
				if(hit == true) {
					hitSpeed++;
					if(hitSpeed % 20 == 0) {
						hitWait++;
					}
					
					if(hitWait == 2) {
						hitWait = 0;
						hit = false;
					}
				}
				for(int i = 0; i < Game.collisionTiles.get(new Integer(0)).get(cur).size(); i+=2) {
					if(hit == false && getBounds().intersects(new Rectangle(Game.collisionTiles.get(new Integer(0)).get(cur).get(i), Game.collisionTiles.get(new Integer(0)).get(cur).get(i+1),
							32, 32))) {
						if(Game.animationDungeonCounter.get("floorTrap") == Game.animationDungeon.get("floorTrap").size()-1) {
							AudioPlayer.getSound("hit").play(1, (float).1);
							HUD.HEALTH -= 4;
							hit = true;
						}
					}
				}
			}
		}
	}
	/**
	 * 
	 * @return bounds of upper lower body
	 */
	public Rectangle getBoundsUp() {
		return new Rectangle((int)(x+20), (int)(y+47), 7, 10);
	}
	/**
	 * @return bounds to check enemy collision(all of lower body)
	 */
	public Rectangle getBounds() {
		return new Rectangle((int)x + 8, (int)y + 51, 31, 13);
	}
	/**
	 * 
	 * @return bounds of lower lower body
	 */
	public Rectangle getBoundsDown() {
		return new Rectangle((int)(x+20), (int)(y+59), 7, 10);
	}
	/**
	 * 
	 * @return bounds of lower left body
	 */
	public Rectangle getBoundsLeft() {
		return new Rectangle((int)(x+7), (int)(y+56), 10, 5);
	}
	/**
	 * 
	 * @return bounds of lower right body
	 */
	public Rectangle getBoundsRight() {
		return new Rectangle((int)(x+30), (int)(y+55), 10, 5);
	}
	
	
	/**
	 * renders Povy
	 */
	public void render(Graphics g) {
		//g.setColor(Color.green);
		//g.fillRect((int)x, (int)y, 50, 50);
		if(Game.gameState == Game.STATE.Game) {
			if(invincible) {
				invCounter++;
				if(invCounter % 4 == 0) {
					return;
				}
				if(invCounter >= 100) {
					invCounter = 0;
					invincible = false;
				}
			}
		}
		if(hit && hitWait < 3) {
			g.drawImage(hurt.get(hitCount), (int)x, (int)y, null);
			
			speedControl++;
			if(speedControl % 2 == 0) {
				hitCount++;
			}
			
			if(hitCount == 2) {
				hitCount = 0;
			}
			return;
		}
		if(Game.gameState == Game.STATE.KeyFromGrogo) {
			g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
			
			speedControl++;
			if(speedControl % 20 == 0) {
				idleCount++;
			}
			
			if(idleCount == 5) {
				idleCount = 0;
			}
			
		}
		if(Game.gameState == Game.STATE.Game) {
			if(takingPotion) {
				g.drawImage(itemTake.get(potionCount), (int)x, (int)y, null);
				
				speedControl++;
				if(speedControl % 5 == 0) {
					potionCount++;
					if(potionCount == 4) {
						AudioPlayer.getSound("drinking").play(1, (float).5);	
					}
				}
				if(potionCount == 9) {
					potionCount = 0; 
					AudioPlayer.getSound("drinkingDone").play(1, (float).1);
					int num = (PauseScreen.yItem - 198) / 34;
					Game.itemPouch.useItem(num);
					takingPotion = false;
				}
				return;
			}
			if(velX != 0 || velY != 0) {
				if(velY < 0) {
					if(velX >= 0) {
						g.drawImage(movingUp.get(moveCount), (int)x, (int)y, null);
						/**
						speedControl++;
						if(speedControl % 2 == 0) {
							moveCount++;
						}
						**/
						moveCount++;
						if(moveCount == 7) {
							moveCount = 0;
						}
						return;
					}
					else if(velX < 0) {
						g.drawImage(movingUpLeft.get(moveCount), (int)x, (int)y, null);
						/**
						speedControl++;
						if(speedControl % 2 == 0) {
							moveCount++;
						}
						**/
						moveCount++;
						if(moveCount == 7) {
							moveCount = 0;
						}
						return;
					}
				}
				else if(velY > 0) {
					if(velX >= 0) {
						g.drawImage(movingDown.get(moveCount), (int)x, (int)y, null);
						/**
						speedControl++;
						if(speedControl % 2 == 0) {
							moveCount++;
						}
						**/
						moveCount++;
						if(moveCount == 7) {
							moveCount = 0;
						}
						return;
					}
					else if(velX < 0) {
						g.drawImage(movingDownLeft.get(moveCount), (int)x, (int)y, null);
						/**
						speedControl++;
						if(speedControl % 2 == 0) {
							moveCount++;
						}
						**/
						moveCount++;
						if(moveCount == 7) {
							moveCount = 0;
						}
						return;
					}
					
				}
				else {
					if(velX > 0) {
						g.drawImage(moving.get(moveCount), (int)x, (int)y, null);
						/**
						speedControl++;
						if(speedControl % 2 == 0) {
							moveCount++;
						}
						**/
						moveCount++;
						if(moveCount == 7) {
							moveCount = 0;
						}
						return;
					}
					else if(velX < 0) {
						g.drawImage(movingLeft.get(moveCount), (int)x, (int)y, null);
						/**
						speedControl++;
						if(speedControl % 2 == 0) {
							moveCount++;
						}
						**/
						moveCount++;
						if(moveCount == 7) {
							moveCount = 0;
						}
						return;
					}
				}
			}
			
			
			g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
			
			speedControl++;
			if(speedControl % 5 == 0) {
				idleCount++;
			}
			
			
			if(idleCount == 5) {
				idleCount = 0;
			}
			g.setColor(Color.green);
			g.drawRect((int)(x+8), (int)(y+51), 31, 13);
			
		}
		
		if(Game.gameState == Game.STATE.Battle) {
			if(Battle.battleState == Battle.BATTLESTATE.PlayerTurnStart) {
				g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
				
				speedControl++;
				if(speedControl % 20 == 0) {
					idleCount++;
				}
				
				if(idleCount == 5) {
					idleCount = 0;
				}
				g.setColor(Color.green);
				g.drawRect((int)(x+8), (int)(y+51), 31, 13);
			}
			else if(Battle.battleState == Battle.BATTLESTATE.PlayerTurnAction) {
				//attack chosen is pummel
				if(Battle.menuPosition == 5) {
					if(velX > 0) {
						g.drawImage(moving.get(moveCount), (int)x, (int)y, null);
						
						speedControl++;
						if(speedControl % 5 == 0) {
							moveCount++;
						}
						
						if(moveCount == 7) {
							moveCount = 0;
						}
						return;
					}
					else if(velX == 0){
						g.drawImage(pummel.get(pummelCount), (int)x, (int)y, null);
						
						speedControl++;
						if(speedControl % 10 == 0) {
							pummelCount++;
						}
						if(pummelCount == 7 && speedControl % 10 == 0) {
							AudioPlayer.getSound("pummel").play(1, (float).1);
							Battle.takeDamage = true;
						}
						else {
							Battle.takeDamage = false;
						}
						if(pummelCount == 7 || pummelCount == 8 || pummelCount == 9) {
							pummelCount++;
						}
						if(pummelCount == 7 || pummelCount == 8 || pummelCount == 9 || pummelCount == 10 || pummelCount == 11 || pummelCount == 12) {
							Battle.contact = true;
						}
						else {
							Battle.contact = false;
						}
						if(pummelCount == 15) {
							pummelCount = 0;
							pummelTotal += 1;
						}
						if(pummelTotal == 3) {
							pummelTotal = 0;
							this.setVelX(-3);
						}
						return;
					}
					else {
						g.drawImage(movingLeft.get(moveCount), (int)x, (int)y, null);
						
						speedControl++;
						if(speedControl % 5 == 0) {
							moveCount++;
						}
						
						
						if(moveCount == 7) {
							moveCount = 0;
						}
						return;
					}
				}
				//using an item
				if(Battle.menuPosition == 1) {
					g.drawImage(itemTake.get(potionCount), (int)x, (int)y, null);
					
					speedControl++;
					if(speedControl % 20 == 0) {
						potionCount++;
						if(potionCount == 4) {
							AudioPlayer.getSound("drinking").play(1, (float).5);	
						}
					}
					if(potionCount == 9) {
						potionCount = 0; 
						AudioPlayer.getSound("drinkingDone").play(1, (float).1);
						int num = (PauseScreen.yItem - 198) / 34;
						Game.itemPouch.useItem(num);
						Battle.battleState = Battle.BATTLESTATE.EnemyTurn;
					}
					return;
				}
				//using laser attack
				if(Battle.menuPosition == 0) {
					if(velX > 0) {
						g.drawImage(moving.get(moveCount), (int)x, (int)y, null);
						speedControl++;
						if(speedControl % 5 == 0) {
							moveCount++;
						}
						
						if(moveCount == 7) {
							moveCount = 0;
						}
						return;
					}
					else if(velX == 0){
						
						if(laserCount == 14) {
							g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
							
							idleControl++;
							if(idleControl % 20 == 0) {
								idleCount++;
							}
							
							if(idleCount == 5) {
								idleCount = 0;
							}
							
							//this.setVelX(-3);
						}
						else {
							g.drawImage(laserBlaster.get(laserCount), (int)x, (int)y, null);
							speedControl++;
							if(speedControl % 10 == 0) {
								laserCount++;
							}
							if(laserCount == 6 && speedControl % 10 == 0) {
								AudioPlayer.getSound("laserShoot").play(1, (float).1);
							}
						}
						if(laserCount >= 8) {
							
							g.drawImage(laser, laserBulletX, (int)(this.getY() + 26), null);
							laserBulletX += 3;
						}
					
						return;
						
					}
					else {
						g.drawImage(movingLeft.get(moveCount), (int)x, (int)y, null);
						
						speedControl++;
						if(speedControl % 5 == 0) {
							moveCount++;
						}
						if(moveCount == 1 && speedControl % 5 == 0) {
							laserCount = 0;
						}
						
						if(moveCount == 7) {
							moveCount = 0;
						}
						
						return;
					}
				}
				if(Battle.menuPosition == 4 || Battle.menuPosition == 3) {
					g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
					
					idleControl++;
					if(idleControl % 20 == 0) {
						idleCount++;
					}
					
					if(idleCount == 5) {
						idleCount = 0;
					}
					
				}
				
			}
			else if(Battle.battleState == Battle.BATTLESTATE.EnemyTurn){
				if(Battle.contact == true) {
					g.drawImage(hurt.get(hitCount), (int)x, (int)y, null);
					
					speedControl++;
					if(speedControl % 20 == 0) {
						hitCount++;
					}
					
					if(hitCount == 2) {
						hitCount = 0;
					}
					return;
				}
				g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
				
				speedControl++;
				if(speedControl % 20 == 0) {
					idleCount++;
				}
				
				if(idleCount == 5) {
					idleCount = 0;
				}
				g.setColor(Color.green);
				g.drawRect((int)(x+8), (int)(y+51), 31, 13);
			}
			//battle beginning, post battle, battle end
			else{
				g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
				
				speedControl++;
				if(speedControl % 20 == 0) {
					idleCount++;
				}
				
				if(idleCount == 5) {
					idleCount = 0;
				}
				
			}
			
		}
		if(Game.gameState == Game.STATE.Transition || Game.gameState == Game.STATE.Paused) {
			g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
			
			speedControl++;
			if(speedControl % 20 == 0) {
				idleCount++;
			}
			
			if(idleCount == 5) {
				idleCount = 0;
			}
			g.setColor(Color.green);
			g.drawRect((int)(x+8), (int)(y+51), 31, 13);
		}
		
		
	}

	@Override
	public void takeDamage(int damage) {
		// TODO Auto-generated method stub
		HUD.HEALTH -= damage;
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		int num;
		return num = HUD.HEALTH;
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}
}
