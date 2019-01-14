
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
	private int idleCount = 0, dieCount = 0, riseCount = 0;
	private int speedControl = 0, crystalSpeedControl = 0, idleControl = 0, crystalCount = 0, crystalAttackCount = 0;
	private ArrayList<BufferedImage> moving;
	private ArrayList<BufferedImage> movingUp;
	private ArrayList<BufferedImage> facingLeftIdle;
	private ArrayList<BufferedImage> movingDown;
	private ArrayList<BufferedImage> movingLeft;
	private ArrayList<BufferedImage> movingUpLeft;
	private ArrayList<BufferedImage> movingDownLeft;
	private ArrayList<BufferedImage> rotation;
	private ArrayList<BufferedImage> hurt;
	private ArrayList<BufferedImage> pummel;
	private ArrayList<BufferedImage> itemTake;
	private ArrayList<BufferedImage> laserBlaster, die, rise, crystalAttack, actualCrystal;
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
	private String suitColor;
	private int curDirection;
	
	/**
	 * creates a new Povy
	 * @param x
	 * @param y
	 * @param id
	 * @param handler
	 */
	public Povy(float x, float y, ID id, Handler handler, String suitColor){
		super(x, y, id);
		this.height = 64;
		this.handler = handler;
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		this.suitColor = suitColor;
		crystalAttack = new ArrayList<BufferedImage>();
		actualCrystal = new ArrayList<BufferedImage>();
		laser = null;
		try {
	        laser = ImageIO.read(new File("res/LaserBullet.png"));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
		
		crystalAttack.add(ss.grabImage(23, 4, 64, 64,"povy"));
		crystalAttack.add(ss.grabImage(24, 1, 64, 64,"povy"));
		crystalAttack.add(ss.grabImage(24, 2, 64, 64,"povy"));
		crystalAttack.add(ss.grabImage(24, 3, 64, 64,"povy"));
		crystalAttack.add(ss.grabImage(24, 4, 64, 64,"povy"));
		crystalAttack.add(ss.grabImage(25, 1, 64, 64,"povy"));
		
		actualCrystal.add(ss.grabImage(1, 1, 480, 64,"crystalAttack"));
		actualCrystal.add(ss.grabImage(1, 2, 480, 64,"crystalAttack"));
		actualCrystal.add(ss.grabImage(1, 3, 480, 64,"crystalAttack"));
		actualCrystal.add(ss.grabImage(1, 4, 480, 64,"crystalAttack"));
		actualCrystal.add(ss.grabImage(2, 1, 480, 64,"crystalAttack"));
		actualCrystal.add(ss.grabImage(2, 2, 480, 64,"crystalAttack"));
		actualCrystal.add(ss.grabImage(2, 3, 480, 64,"crystalAttack"));
		actualCrystal.add(ss.grabImage(2, 4, 480, 64,"crystalAttack"));
		actualCrystal.add(ss.grabImage(3, 1, 480, 64,"crystalAttack"));
		actualCrystal.add(ss.grabImage(3, 2, 480, 64,"crystalAttack"));
		
		changeOutfit(suitColor, ss);
		
	}
	
	/**
	 * returns a copy of Povy
	 */
	public Povy copy() {
		return new Povy(x, y, id, handler, suitColor);
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
			if(!invincible && Game.gameState == Game.STATE.Game) {
				enemyCollision();
				obstacleCollision();
			}
			
			if(Game.gameState == Game.STATE.Game && HUD.HEALTH <= 0) {
				Game.gameState = Game.STATE.Dead;
				if(AudioPlayer.getMusic("dungeon").playing()) {
					AudioPlayer.getMusic("dungeon").stop();
					AudioPlayer.getSound("povyDies").play(1, (float).3);
				}
			}
			
			collision();
		}
		if(Game.gameState == Game.STATE.Battle) {
			x += velX;
		}
		if(velX > 0) {
			curDirection = 1;
		}
		else if(velX < 0) {
			curDirection = -1;
		}
		if(Game.battleReturn == true && Game.gameState == Game.STATE.Game) {
			this.setVelX(0);
			this.setVelY(0);
			this.isInvincible();
		}
		
		for(int i = 0; i < handler.objects.size(); i++) {
			if(handler.objects.get(i).id == ID.NonEnemy) {
				if(this.getBounds().intersects(handler.objects.get(i).getBounds())) {
					if(handler.objects.get(i) instanceof Costume) {
						Costume temp = (Costume)handler.objects.get(i);
						temp.get();
					}
				}
			}
		}
		
		
	}
	
	/**
	 * checks Povy's collision with boundaries on the map
	 */
	public void collision() {
		Iterator<String> itr = Game.collisionTiles.get(new Integer(0)).keySet().iterator();
		while(itr.hasNext()) {
			String cur = itr.next();
			if(!cur.contains("281") && !cur.contains("300") && !cur.contains("282") && !cur.contains("283")) {
				for(int i = 0; i < Game.collisionTiles.get(new Integer(0)).get(cur).size(); i+=2) {
					if(getBoundsLeft().intersects(new Rectangle(Game.collisionTiles.get(new Integer(0)).get(cur).get(i), Game.collisionTiles.get(new Integer(0)).get(cur).get(i+1),
							32, 32))) {
						x = Game.clampUpLeft((int)x, Game.collisionTiles.get(new Integer(0)).get(cur).get(i)+26);
						
							
					}
					if(getBoundsRight().intersects(new Rectangle(Game.collisionTiles.get(new Integer(0)).get(cur).get(i), Game.collisionTiles.get(new Integer(0)).get(cur).get(i+1),
							32, 32))) {	
						
						x = Game.clampDownRight((int)x, Game.collisionTiles.get(new Integer(0)).get(cur).get(i)-42);
						
					}
					if(getBoundsUp().intersects(new Rectangle(Game.collisionTiles.get(new Integer(0)).get(cur).get(i), Game.collisionTiles.get(new Integer(0)).get(cur).get(i+1),
							32, 32))) {
						y = Game.clampUpLeft((int)y, Game.collisionTiles.get(new Integer(0)).get(cur).get(i+1)-15);
						
					}
					if(getBoundsDown().intersects(new Rectangle(Game.collisionTiles.get(new Integer(0)).get(cur).get(i), Game.collisionTiles.get(new Integer(0)).get(cur).get(i+1),
							32, 32))) {
						
						y = Game.clampDownRight((int)y, Game.collisionTiles.get(new Integer(0)).get(cur).get(i+1)-64);
						
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
			if(cur.contains("281") || cur.contains("282") || cur.contains("283")) {
				if(hit == true) {
					hitSpeed++;
					if(hitSpeed % 80 == 0) {
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
						if(cur.contains("281")) {
							if(Game.animationDungeonCounter.get("floorTrap") == Game.animationDungeon.get("floorTrap").size()-2) {
								AudioPlayer.getSound("hit").play(1, (float).1);
								HUD.HEALTH -= 4;
								hit = true;
							}
						}
						else if(cur.contains("282")) {
							if(Game.animationDungeonCounter.get("floorTrapA") == Game.animationDungeon.get("floorTrap").size()-2) {
								AudioPlayer.getSound("hit").play(1, (float).1);
								HUD.HEALTH -= 4;
								hit = true;
							}
						}
						else if(cur.contains("283")) {
							if(Game.animationDungeonCounter.get("floorTrapB") == Game.animationDungeon.get("floorTrap").size()-2) {
								AudioPlayer.getSound("hit").play(1, (float).1);
								HUD.HEALTH -= 4;
								hit = true;
							}
						}
						/**
						if(HUD.HEALTH <= 0) {
							hit = false;
						}
						**/
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
		if(Game.gameState == Game.STATE.Battle) {
			if(Battle.battleState == Battle.BATTLESTATE.EnemyTurn && Battle.enemy.id == ID.Zatolib) {
				Zatolib z = (Zatolib) Battle.enemy;
				if(z.getAttackDam() == 1) {
					return new Rectangle((int)x + 300, (int)y + 51, 31, 13);
				}
				if(z.getAttackDam() == 2) {
					return new Rectangle((int)x + 50, (int)y + 51, 31, 13);
				}
			}
		}
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
		if(hit && hitWait < 3 && Game.gameState == Game.STATE.Game) {
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
	
			g.drawImage(facingLeftIdle.get(idleCount), (int)x, (int)y, null);
			speedControl++;
			if(speedControl % 2 == 0) {
				idleCount++;
			}
			if(idleCount == 5) {
				idleCount = 0;
				speedControl = 0;
			}
			return;
			
			
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
			if(curDirection < 0) {
				g.drawImage(facingLeftIdle.get(idleCount), (int)x, (int)y, null);
				speedControl++;
				if(speedControl % 5 == 0) {
					idleCount++;
				}
				if(idleCount == 5) {
					idleCount = 0;
					speedControl = 0;
				}
				return;
			}
			
			g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
			
			speedControl++;
			if(speedControl % 5 == 0) {
				idleCount++;
			}
			
			
			if(idleCount == 5) {
				idleCount = 0;
			}
			
			
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
				
			}
			else if(Battle.battleState == Battle.BATTLESTATE.PlayerDies) {
				if(dieCount != 6) {
					g.drawImage(die.get(dieCount), (int)x, (int)y, null);
					speedControl++;
					if(speedControl % 2 == 0) {
						dieCount++;
					}
					if(dieCount == 5) {
						AudioPlayer.getSound("povyHittingGroundThud").play(1, (float).1);
						if(AudioPlayer.getMusic("dungeonFight").playing()) {
							AudioPlayer.getMusic("dungeonFight").stop();
							AudioPlayer.getSound("povyDies").play(1, (float).3);
						}
					}
				}
				else {
					
					g.drawImage(die.get(5), (int)x, (int)y, null);
				}
			}
			else if(Battle.battleState == Battle.BATTLESTATE.ZatolibWins) {
				g.drawImage(die.get(dieCount), (int)x, (int)y, null);
				speedControl++;
				if(speedControl % 2 == 0) {
					dieCount++;
				}
				
				if(dieCount == 5) {
					AudioPlayer.getSound("povyHittingGroundThud").play(1, (float).1);
				}
				if(dieCount == 6) {
					Battle.battleState = Battle.BATTLESTATE.CrystalCutscene;
					CrystalCutscene.crystalState = CrystalCutscene.CRYSTALSTATE.PovyDead;
					dieCount = 0;
				}
			}
			else if(Battle.battleState == Battle.BATTLESTATE.CrystalCutscene && CrystalCutscene.crystalState == CrystalCutscene.CRYSTALSTATE.PovyDead) {
				g.drawImage(die.get(5), (int)x, (int)y, null);
				
			}
			else if(Battle.battleState == Battle.BATTLESTATE.CrystalCutscene && CrystalCutscene.crystalState == CrystalCutscene.CRYSTALSTATE.PovyRise) {
				
				g.drawImage(rise.get(riseCount), (int)x, (int)y, null);
				speedControl++;
				if(speedControl % 15 == 0) {
					riseCount++;
					speedControl = 0;
				}
				
				if(riseCount == 4 && speedControl % 15 == 0) {
					AudioPlayer.getSound("risingCrystal1").play(1, (float).4);
				}
				
				
				if(riseCount == 13) {
					riseCount = 10;
				}
			}
			else if(Battle.battleState == Battle.BATTLESTATE.CrystalCutscene && CrystalCutscene.crystalState == CrystalCutscene.CRYSTALSTATE.UsingCrystal) {
				g.drawImage(crystalAttack.get(crystalCount), (int)x, (int)y, null);
				speedControl++;
				if(speedControl % 10 == 0) {
					crystalCount++;
					speedControl = 0;
				}
				
				if(crystalCount == 6) {
					crystalCount = 3;
				}
				
				if(crystalCount >= 3) {
					if(crystalAttackCount == 3 && crystalSpeedControl % 10 == 0) {
						AudioPlayer.getSound("crystalPath").loop(1, (float).2);
					}
					g.drawImage(actualCrystal.get(crystalAttackCount), (int)x+45, (int)y, null);
					crystalSpeedControl++;
					if(crystalSpeedControl % 10 == 0) {
						crystalAttackCount++;
						crystalSpeedControl = 0;
					}
					
					if(crystalAttackCount >= 7) {
						Battle.contact = true;
					}
					else {
						Battle.contact = false;
					}
					
					if(crystalAttackCount == 10) {
						crystalAttackCount = 7;
					}
				}
			}
			else if(Battle.battleState == Battle.BATTLESTATE.CrystalCutscene && CrystalCutscene.crystalState == CrystalCutscene.CRYSTALSTATE.ZatolibDies) {
				g.drawImage(die.get(dieCount), (int)x, (int)y, null);
				speedControl++;
				if(speedControl % 2 == 0) {
					dieCount++;
				}
				
				if(dieCount == 5) {
					AudioPlayer.getSound("povyHittingGroundThud").play(1, (float).1);
				}
				if(dieCount == 6) {
					Battle.battleState = Battle.BATTLESTATE.BackToGame;
				}
				
			}
			else if(Battle.battleState == Battle.BATTLESTATE.BackToGame && CrystalCutscene.crystalState == CrystalCutscene.CRYSTALSTATE.ZatolibDies) {
				g.drawImage(die.get(5), (int)x, (int)y, null);
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
			
			if(curDirection < 0) {
				g.drawImage(facingLeftIdle.get(idleCount), (int)x, (int)y, null);
				speedControl++;
				if(speedControl % 20 == 0) {
					idleCount++;
				}
				if(idleCount == 5) {
					idleCount = 0;
					speedControl = 0;
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
			
		}
		if(Game.gameState == Game.STATE.Dead) {
			if(dieCount != 6) {
				g.drawImage(die.get(dieCount), (int)x, (int)y, null);
				speedControl++;
				if(speedControl % 1 == 0) {
					dieCount++;
				}
				if(dieCount == 5) {
					AudioPlayer.getSound("povyHittingGroundThud").play(1, (float).1);
					
				}
			}
			else {
				
				g.drawImage(die.get(5), (int)x, (int)y, null);
			}
		}
		
		
	}
	
	public void changeOutfit(String name, SpriteSheet ss) {
		suitColor = name;
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
		die = new ArrayList<BufferedImage>();
		facingLeftIdle = new ArrayList<BufferedImage>();
		rise = new ArrayList<BufferedImage>();
		
		if(name.contains("purple")) {
			
			idle.add(ss.grabImage(1, 1, 64, 64,"povyPurple"));
			idle.add(ss.grabImage(1, 2, 64, 64,"povyPurple"));
			idle.add(ss.grabImage(1, 3, 64, 64,"povyPurple"));
			idle.add(ss.grabImage(1, 4, 64, 64,"povyPurple"));
			idle.add(ss.grabImage(2, 1, 64, 64,"povyPurple"));
			
			facingLeftIdle.add(ss.grabImage(1, 1, 48, 64,"povyPurple2"));
			facingLeftIdle.add(ss.grabImage(1, 2, 48, 64,"povyPurple2"));
			facingLeftIdle.add(ss.grabImage(1, 3, 48, 64,"povyPurple2"));
			facingLeftIdle.add(ss.grabImage(1, 4, 48, 64,"povyPurple2"));
			facingLeftIdle.add(ss.grabImage(2, 1, 48, 64,"povyPurple2"));
			
			moving.add(ss.grabImage(2, 2, 64, 64,"povyPurple"));
			moving.add(ss.grabImage(2, 3, 64, 64,"povyPurple"));
			moving.add(ss.grabImage(2, 4, 64, 64,"povyPurple"));
			moving.add(ss.grabImage(3, 1, 64, 64,"povyPurple"));
			moving.add(ss.grabImage(3, 2, 64, 64,"povyPurple"));
			moving.add(ss.grabImage(3, 3, 64, 64,"povyPurple"));
			moving.add(ss.grabImage(3, 4, 64, 64,"povyPurple"));
			moving.add(ss.grabImage(4, 1, 64, 64,"povyPurple"));
			moving.add(ss.grabImage(4, 2, 64, 64,"povyPurple"));
			
			
			movingUp.add(ss.grabImage(4, 3, 64, 64,"povyPurple"));
			movingUp.add(ss.grabImage(4, 4, 64, 64,"povyPurple"));
			movingUp.add(ss.grabImage(5, 1, 64, 64,"povyPurple"));
			movingUp.add(ss.grabImage(5, 2, 64, 64,"povyPurple"));
			movingUp.add(ss.grabImage(5, 3, 64, 64,"povyPurple"));
			movingUp.add(ss.grabImage(5, 4, 64, 64,"povyPurple"));
			movingUp.add(ss.grabImage(6, 1, 64, 64,"povyPurple"));
			movingUp.add(ss.grabImage(6, 2, 64, 64,"povyPurple"));
			movingUp.add(ss.grabImage(6, 3, 64, 64,"povyPurple"));
			
			movingDown.add(ss.grabImage(6, 4, 64, 64,"povyPurple"));
			movingDown.add(ss.grabImage(7, 1, 64, 64,"povyPurple"));
			movingDown.add(ss.grabImage(7, 2, 64, 64,"povyPurple"));
			movingDown.add(ss.grabImage(7, 3, 64, 64,"povyPurple"));
			movingDown.add(ss.grabImage(7, 4, 64, 64,"povyPurple"));
			movingDown.add(ss.grabImage(8, 1, 64, 64,"povyPurple"));
			movingDown.add(ss.grabImage(8, 2, 64, 64,"povyPurple"));
			movingDown.add(ss.grabImage(8, 3, 64, 64,"povyPurple"));
			movingDown.add(ss.grabImage(8, 4, 64, 64,"povyPurple"));
			
			movingLeft.add(ss.grabImage(2, 2, 48, 64,"povyPurple2"));
			movingLeft.add(ss.grabImage(2, 3, 48, 64,"povyPurple2"));
			movingLeft.add(ss.grabImage(2, 4, 48, 64,"povyPurple2"));
			movingLeft.add(ss.grabImage(3, 1, 48, 64,"povyPurple2"));
			movingLeft.add(ss.grabImage(3, 2, 48, 64,"povyPurple2"));
			movingLeft.add(ss.grabImage(3, 3, 48, 64,"povyPurple2"));
			movingLeft.add(ss.grabImage(3, 4, 48, 64,"povyPurple2"));
			movingLeft.add(ss.grabImage(4, 1, 48, 64,"povyPurple2"));
			movingLeft.add(ss.grabImage(4, 2, 48, 64,"povyPurple2"));
			
			movingUpLeft.add(ss.grabImage(4, 3, 48, 64,"povyPurple2"));
			movingUpLeft.add(ss.grabImage(4, 4, 48, 64,"povyPurple2"));
			movingUpLeft.add(ss.grabImage(5, 1, 48, 64,"povyPurple2"));
			movingUpLeft.add(ss.grabImage(5, 2, 48, 64,"povyPurple2"));
			movingUpLeft.add(ss.grabImage(5, 3, 48, 64,"povyPurple2"));
			movingUpLeft.add(ss.grabImage(5, 4, 48, 64,"povyPurple2"));
			movingUpLeft.add(ss.grabImage(6, 1, 48, 64,"povyPurple2"));
			movingUpLeft.add(ss.grabImage(6, 2, 48, 64,"povyPurple2"));
			movingUpLeft.add(ss.grabImage(6, 3, 48, 64,"povyPurple2"));
			
			movingDownLeft.add(ss.grabImage(6, 4, 48, 64,"povyPurple2"));
			movingDownLeft.add(ss.grabImage(7, 1, 48, 64,"povyPurple2"));
			movingDownLeft.add(ss.grabImage(7, 2, 48, 64,"povyPurple2"));
			movingDownLeft.add(ss.grabImage(7, 3, 48, 64,"povyPurple2"));
			movingDownLeft.add(ss.grabImage(7, 4, 48, 64,"povyPurple2"));
			movingDownLeft.add(ss.grabImage(8, 1, 48, 64,"povyPurple2"));
			movingDownLeft.add(ss.grabImage(8, 2, 48, 64,"povyPurple2"));
			movingDownLeft.add(ss.grabImage(8, 3, 48, 64,"povyPurple2"));
			movingDownLeft.add(ss.grabImage(8, 4, 48, 64,"povyPurple2"));
			
			hurt.add(ss.grabImage(9, 1, 64, 64,"povyPurple"));
			hurt.add(ss.grabImage(9, 2, 64, 64,"povyPurple"));
			
			pummel.add(ss.grabImage(13, 1, 64, 64,"povyPurple"));
			pummel.add(ss.grabImage(13, 2, 64, 64,"povyPurple"));
			pummel.add(ss.grabImage(13, 3, 64, 64,"povyPurple"));
			pummel.add(ss.grabImage(13, 4, 64, 64,"povyPurple"));
			pummel.add(ss.grabImage(14, 1, 64, 64,"povyPurple"));
			pummel.add(ss.grabImage(14, 2, 64, 64,"povyPurple"));
			pummel.add(ss.grabImage(14, 3, 64, 64,"povyPurple"));
			pummel.add(ss.grabImage(14, 4, 64, 64,"povyPurple"));
			pummel.add(ss.grabImage(15, 1, 64, 64,"povyPurple"));
			pummel.add(ss.grabImage(15, 2, 64, 64,"povyPurple"));
			pummel.add(ss.grabImage(15, 3, 64, 64,"povyPurple"));
			pummel.add(ss.grabImage(15, 4, 64, 64,"povyPurple"));
			pummel.add(ss.grabImage(16, 1, 64, 64,"povyPurple"));
			pummel.add(ss.grabImage(16, 2, 64, 64,"povyPurple"));
			pummel.add(ss.grabImage(16, 3, 64, 64,"povyPurple"));
			
			itemTake.add(ss.grabImage(16, 4, 64, 64,"povyPurple"));
			itemTake.add(ss.grabImage(17, 1, 64, 64,"povyPurple"));
			itemTake.add(ss.grabImage(17, 2, 64, 64,"povyPurple"));
			itemTake.add(ss.grabImage(17, 3, 64, 64,"povyPurple"));
			itemTake.add(ss.grabImage(17, 4, 64, 64,"povyPurple"));
			itemTake.add(ss.grabImage(18, 1, 64, 64,"povyPurple"));
			itemTake.add(ss.grabImage(18, 2, 64, 64,"povyPurple"));
			itemTake.add(ss.grabImage(18, 3, 64, 64,"povyPurple"));
			itemTake.add(ss.grabImage(18, 4, 64, 64,"povyPurple"));
			
			laserBlaster.add(ss.grabImage(9, 3, 64, 64,"povyPurple"));
			laserBlaster.add(ss.grabImage(9, 4, 64, 64,"povyPurple"));
			laserBlaster.add(ss.grabImage(10, 1, 64, 64,"povyPurple"));
			laserBlaster.add(ss.grabImage(10, 2, 64, 64,"povyPurple"));
			laserBlaster.add(ss.grabImage(10, 3, 64, 64,"povyPurple"));
			laserBlaster.add(ss.grabImage(10, 4, 64, 64,"povyPurple"));
			laserBlaster.add(ss.grabImage(11, 1, 64, 64,"povyPurple"));
			laserBlaster.add(ss.grabImage(11, 2, 64, 64,"povyPurple"));
			laserBlaster.add(ss.grabImage(11, 3, 64, 64,"povyPurple"));
			laserBlaster.add(ss.grabImage(11, 4, 64, 64,"povyPurple"));
			laserBlaster.add(ss.grabImage(12, 1, 64, 64,"povyPurple"));
			laserBlaster.add(ss.grabImage(12, 2, 64, 64,"povyPurple"));
			laserBlaster.add(ss.grabImage(12, 3, 64, 64,"povyPurple"));
			laserBlaster.add(ss.grabImage(12, 4, 64, 64,"povyPurple"));
			
			die.add(ss.grabImage(19, 1, 64, 64,"povyPurple"));
			die.add(ss.grabImage(19, 2, 64, 64,"povyPurple"));
			die.add(ss.grabImage(19, 3, 64, 64,"povyPurple"));
			die.add(ss.grabImage(19, 4, 64, 64,"povyPurple"));
			die.add(ss.grabImage(20, 1, 64, 64,"povyPurple"));
			die.add(ss.grabImage(20, 2, 64, 64,"povyPurple"));
			
			rise.add(ss.grabImage(20, 3, 64, 64,"povyPurple"));
			rise.add(ss.grabImage(20, 4, 64, 64,"povyPurple"));
			rise.add(ss.grabImage(21, 1, 64, 64,"povyPurple"));
			rise.add(ss.grabImage(21, 2, 64, 64,"povyPurple"));
			rise.add(ss.grabImage(21, 3, 64, 64,"povyPurple"));
			rise.add(ss.grabImage(21, 4, 64, 64,"povyPurple"));
			rise.add(ss.grabImage(22, 1, 64, 64,"povyPurple"));
			rise.add(ss.grabImage(22, 2, 64, 64,"povyPurple"));
			rise.add(ss.grabImage(22, 3, 64, 64,"povyPurple"));
			rise.add(ss.grabImage(22, 4, 64, 64,"povyPurple"));
			rise.add(ss.grabImage(23, 1, 64, 64,"povyPurple"));
			rise.add(ss.grabImage(23, 2, 64, 64,"povyPurple"));
			rise.add(ss.grabImage(23, 3, 64, 64,"povyPurple"));
			
		}
		
		if(name.contains("blue")) {
			
			idle.add(ss.grabImage(1, 1, 64, 64,"povy"));
			idle.add(ss.grabImage(1, 2, 64, 64,"povy"));
			idle.add(ss.grabImage(1, 3, 64, 64,"povy"));
			idle.add(ss.grabImage(1, 4, 64, 64,"povy"));
			idle.add(ss.grabImage(2, 1, 64, 64,"povy"));
			
			facingLeftIdle.add(ss.grabImage(1, 1, 48, 64,"povy2"));
			facingLeftIdle.add(ss.grabImage(1, 2, 48, 64,"povy2"));
			facingLeftIdle.add(ss.grabImage(1, 3, 48, 64,"povy2"));
			facingLeftIdle.add(ss.grabImage(1, 4, 48, 64,"povy2"));
			facingLeftIdle.add(ss.grabImage(2, 1, 48, 64,"povy2"));
			
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
			
			movingLeft.add(ss.grabImage(2, 2, 48, 64,"povy2"));
			movingLeft.add(ss.grabImage(2, 3, 48, 64,"povy2"));
			movingLeft.add(ss.grabImage(2, 4, 48, 64,"povy2"));
			movingLeft.add(ss.grabImage(3, 1, 48, 64,"povy2"));
			movingLeft.add(ss.grabImage(3, 2, 48, 64,"povy2"));
			movingLeft.add(ss.grabImage(3, 3, 48, 64,"povy2"));
			movingLeft.add(ss.grabImage(3, 4, 48, 64,"povy2"));
			movingLeft.add(ss.grabImage(4, 1, 48, 64,"povy2"));
			movingLeft.add(ss.grabImage(4, 2, 48, 64,"povy2"));
			
			movingUpLeft.add(ss.grabImage(4, 3, 48, 64,"povy2"));
			movingUpLeft.add(ss.grabImage(4, 4, 48, 64,"povy2"));
			movingUpLeft.add(ss.grabImage(5, 1, 48, 64,"povy2"));
			movingUpLeft.add(ss.grabImage(5, 2, 48, 64,"povy2"));
			movingUpLeft.add(ss.grabImage(5, 3, 48, 64,"povy2"));
			movingUpLeft.add(ss.grabImage(5, 4, 48, 64,"povy2"));
			movingUpLeft.add(ss.grabImage(6, 1, 48, 64,"povy2"));
			movingUpLeft.add(ss.grabImage(6, 2, 48, 64,"povy2"));
			movingUpLeft.add(ss.grabImage(6, 3, 48, 64,"povy2"));
			
			movingDownLeft.add(ss.grabImage(6, 4, 48, 64,"povy2"));
			movingDownLeft.add(ss.grabImage(7, 1, 48, 64,"povy2"));
			movingDownLeft.add(ss.grabImage(7, 2, 48, 64,"povy2"));
			movingDownLeft.add(ss.grabImage(7, 3, 48, 64,"povy2"));
			movingDownLeft.add(ss.grabImage(7, 4, 48, 64,"povy2"));
			movingDownLeft.add(ss.grabImage(8, 1, 48, 64,"povy2"));
			movingDownLeft.add(ss.grabImage(8, 2, 48, 64,"povy2"));
			movingDownLeft.add(ss.grabImage(8, 3, 48, 64,"povy2"));
			movingDownLeft.add(ss.grabImage(8, 4, 48, 64,"povy2"));
			
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
			
			die.add(ss.grabImage(19, 1, 64, 64,"povy"));
			die.add(ss.grabImage(19, 2, 64, 64,"povy"));
			die.add(ss.grabImage(19, 3, 64, 64,"povy"));
			die.add(ss.grabImage(19, 4, 64, 64,"povy"));
			die.add(ss.grabImage(20, 1, 64, 64,"povy"));
			die.add(ss.grabImage(20, 2, 64, 64,"povy"));
			
			rise.add(ss.grabImage(20, 3, 64, 64,"povy"));
			rise.add(ss.grabImage(20, 4, 64, 64,"povy"));
			rise.add(ss.grabImage(21, 1, 64, 64,"povy"));
			rise.add(ss.grabImage(21, 2, 64, 64,"povy"));
			rise.add(ss.grabImage(21, 3, 64, 64,"povy"));
			rise.add(ss.grabImage(21, 4, 64, 64,"povy"));
			rise.add(ss.grabImage(22, 1, 64, 64,"povy"));
			rise.add(ss.grabImage(22, 2, 64, 64,"povy"));
			rise.add(ss.grabImage(22, 3, 64, 64,"povy"));
			rise.add(ss.grabImage(22, 4, 64, 64,"povy"));
			rise.add(ss.grabImage(23, 1, 64, 64,"povy"));
			rise.add(ss.grabImage(23, 2, 64, 64,"povy"));
			rise.add(ss.grabImage(23, 3, 64, 64,"povy"));
			
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
