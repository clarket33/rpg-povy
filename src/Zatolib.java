import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
/**
 * 
 * @author clarkt5
 * Zatolib is the main villain of the game and the first boss of the game
 */
public class Zatolib extends GameObject{
	
	Handler handler;
	private ArrayList<BufferedImage> idle;
	private ArrayList<BufferedImage> movingLeft;
	private ArrayList<BufferedImage> movingRight;
	private ArrayList<BufferedImage> hurt;
	private ArrayList<BufferedImage> laugh;
	private ArrayList<BufferedImage> die;
	private ArrayList<BufferedImage> attack1;
	private ArrayList<BufferedImage> attack2;
	private ArrayList<BufferedImage> attack3;
	
	private BufferedImage laser;
	
	private Random attackOption, healthGenerator;
	private int attackNum;
	private int health = 0;
	private boolean attackCheck = false;
	private int animationCount = 0;
	private int attack1Count, attack2Count = 0, attack3Count = 0, dieCount = 0;
	private int idleCount = 0;
	private int changeCount = 0;
	private int hurtCount = 0;
	private int maxHealth = 0;
	private int laserBulletX, laserBulletY;
	private float yFactorUp = (float) 2, yFactorDown=0;
	
	
	/**
	 * creates the alien
	 * @param x
	 * @param y
	 * @param id
	 * @param handler
	 * @param g
	 */
	public Zatolib(float x, float y, ID id, Handler handler){
		super(x, y, id);
		this.height = 120;
		this.handler = handler;
		attackOption = new Random();
		healthGenerator = new Random();
		health = healthGenerator.nextInt(3);
		health = 500;
		maxHealth = health;
		
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		
		idle = new ArrayList<BufferedImage>();
		idle.add(ss.grabImage(1, 1, 145, 133,"zatolib"));
		idle.add(ss.grabImage(1, 2, 145, 133,"zatolib"));
		idle.add(ss.grabImage(1, 3, 145, 133,"zatolib"));
		idle.add(ss.grabImage(1, 4, 145, 133,"zatolib"));
		idle.add(ss.grabImage(1, 5, 145, 133,"zatolib"));
		
		movingLeft = new ArrayList<BufferedImage>();
		movingLeft.add(ss.grabImage(1, 6, 145, 133,"zatolib"));
		movingLeft.add(ss.grabImage(1, 7, 145, 133,"zatolib"));
		movingLeft.add(ss.grabImage(1, 8, 145, 133,"zatolib"));
		movingLeft.add(ss.grabImage(2, 1, 145, 133,"zatolib"));
		movingLeft.add(ss.grabImage(2, 2, 145, 133,"zatolib"));
		movingLeft.add(ss.grabImage(2, 3, 145, 133,"zatolib"));
		
		movingRight = new ArrayList<BufferedImage>();
		movingRight.add(ss.grabImage(1, 6, 145, 133,"zatolibRight"));
		movingRight.add(ss.grabImage(1, 7, 145, 133,"zatolibRight"));
		movingRight.add(ss.grabImage(1, 8, 145, 133,"zatolibRight"));
		movingRight.add(ss.grabImage(2, 1, 145, 133,"zatolibRight"));
		movingRight.add(ss.grabImage(2, 2, 145, 133,"zatolibRight"));
		movingRight.add(ss.grabImage(2, 3, 145, 133,"zatolibRight"));
		
		hurt = new ArrayList<BufferedImage>();
		hurt.add(ss.grabImage(8, 4, 145, 133,"zatolib"));
		hurt.add(ss.grabImage(8, 5, 145, 133,"zatolib"));
		
		attack1 = new ArrayList<BufferedImage>();
		attack1.add(ss.grabImage(2, 4, 145, 133,"zatolib"));
		attack1.add(ss.grabImage(2, 5, 145, 133,"zatolib"));
		attack1.add(ss.grabImage(2, 6, 145, 133,"zatolib"));
		attack1.add(ss.grabImage(2, 7, 145, 133,"zatolib"));
		attack1.add(ss.grabImage(2, 8, 145, 133,"zatolib"));
		attack1.add(ss.grabImage(3, 1, 145, 133,"zatolib"));
		attack1.add(ss.grabImage(3, 2, 145, 133,"zatolib"));
		attack1.add(ss.grabImage(3, 3, 145, 133,"zatolib"));
		attack1.add(ss.grabImage(3, 4, 145, 133,"zatolib"));
		attack1.add(ss.grabImage(3, 5, 145, 133,"zatolib"));
		attack1.add(ss.grabImage(3, 6, 145, 133,"zatolib"));
		attack1.add(ss.grabImage(3, 7, 145, 133,"zatolib"));
		attack1.add(ss.grabImage(3, 8, 145, 133,"zatolib"));
		attack1.add(ss.grabImage(4, 1, 145, 133,"zatolib"));
		attack1.add(ss.grabImage(4, 2, 145, 133,"zatolib"));
		attack1.add(ss.grabImage(4, 3, 145, 133,"zatolib"));
		attack1.add(ss.grabImage(4, 4, 145, 133,"zatolib"));
		
		
		
		attack2 = new ArrayList<BufferedImage>();
		attack2.add(ss.grabImage(4, 5, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(4, 6, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(4, 7, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(4, 8, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(5, 1, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(5, 2, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(5, 3, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(5, 4, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(5, 5, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(5, 6, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(5, 7, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(5, 8, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(6, 1, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(6, 2, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(6, 3, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(6, 4, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(6, 5, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(6, 6, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(6, 7, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(6, 8, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(7, 1, 145, 133,"zatolib"));
		attack2.add(ss.grabImage(7, 2, 145, 133,"zatolib"));
		
		attack3 = new ArrayList<BufferedImage>();
		attack3.add(ss.grabImage(7, 3, 145, 133,"zatolib"));
		attack3.add(ss.grabImage(7, 4, 145, 133,"zatolib"));
		attack3.add(ss.grabImage(7, 5, 145, 133,"zatolib"));
		attack3.add(ss.grabImage(7, 6, 145, 133,"zatolib"));
		attack3.add(ss.grabImage(7, 7, 145, 133,"zatolib"));
		attack3.add(ss.grabImage(7, 8, 145, 133,"zatolib"));
		attack3.add(ss.grabImage(8, 1, 145, 133,"zatolib"));
		attack3.add(ss.grabImage(8, 2, 145, 133,"zatolib"));
		attack3.add(ss.grabImage(8, 3, 145, 133,"zatolib"));
		
		laugh = new ArrayList<BufferedImage>();
		laugh.add(ss.grabImage(8, 6, 145, 133,"zatolib"));
		laugh.add(ss.grabImage(8, 7, 145, 133,"zatolib"));

		
		die = new ArrayList<BufferedImage>();
		die.add(ss.grabImage(8, 8, 145, 133,"zatolib"));
		die.add(ss.grabImage(9, 1, 145, 133,"zatolib"));
		die.add(ss.grabImage(9, 2, 145, 133,"zatolib"));
		die.add(ss.grabImage(9, 3, 145, 133,"zatolib"));
		die.add(ss.grabImage(9, 4, 145, 133,"zatolib"));
		
		
		laser = null;
		try {
	        laser = ImageIO.read(new File("res/zatolibBullet.png"));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
		velX = 0;
	}
	
	/**
	 * returns a copy of the elephant guard
	 */
	public Zatolib copy() {
		return new Zatolib(x, y, id, handler);
	}
	
	/**
	 * choose out of three attacks at random
	 */
	private void generateAttack() {
		if(attackCheck == false) {
			attackNum = attackOption.nextInt(3);
			attackCheck = true;
		}
	}
	/**
	 * tick method for elephant
	 */
	public void tick() {
		if(Game.gameState == Game.STATE.Game) {

			x += velX;
			y += velY;
			
			//x = Game.clamp((int)x, 0, Game.WIDTH-56);
			//y = Game.clamp((int)y, 0, Game.HEIGHT-76);
			collision();
		}
		if(Game.gameState == Game.STATE.Battle && Battle.battleState == Battle.BATTLESTATE.EnemyTurn) {
			if(health <= 0) {
				Battle.battleState = Battle.BATTLESTATE.BattleEnd;
				if(AudioPlayer.getMusic("dungeonFight").playing()) {
					AudioPlayer.getMusic("dungeonFight").stop();
					AudioPlayer.getSound("winBattle").play(1, (float).1);
				}
				return;
			}
			x += velX;
		}
	}
	
	/**
	 * checks to see if the enemy collides with a wall or an object that cannot be passed through
	 */
	public void collision() {
		Iterator<String> itr = Game.collisionTiles.get(new Integer(0)).keySet().iterator();
		while(itr.hasNext()) {
			String cur = itr.next();
			
			for(int i = 0; i < Game.collisionTiles.get(new Integer(0)).get(cur).size(); i+=2) {
				if(getBounds().intersects(new Rectangle(Game.collisionTiles.get(new Integer(0)).get(cur).get(i), Game.collisionTiles.get(new Integer(0)).get(cur).get(i+1),
						32, 32))) {
					if(velX < 0) {
						x = Game.clampUpLeft((int)x, Game.collisionTiles.get(new Integer(0)).get(cur).get(i) + 26);
						this.setVelX((int)this.getVelX()*-1);
					}
					else if(velX > 0){
						x = Game.clampDownRight((int)x, Game.collisionTiles.get(new Integer(0)).get(cur).get(i)-42);
						this.setVelX((int)this.getVelX()*-1);
					}
				}
			}
		}
	}
	
	

	/**
	 * returns bounds of the enemy that, if the player comes in contact with it, the game goes into a battle state
	 */
	public Rectangle getBounds() {
		if(Game.gameState == Game.STATE.Battle) {
			if(Battle.menuPosition == 0 && Game.gameState == Game.STATE.Battle && Battle.battleState == Battle.BATTLESTATE.PlayerTurnAction)
				return new Rectangle((int)x + 85,(int)y+109, 59, 24);
			return new Rectangle((int)x + 66, (int)y + 112, 62, 20);
		}
		return new Rectangle((int)x-20, (int)y-20, 450, 400);
	}
	
	
	/**
	 * renders the enemy
	 */
	public void render(Graphics g) {
		if(Game.gameState == Game.STATE.Battle) {
			/**
			g.setFont(new Font("Cooper Black", 1, 50));
			if(health >= (maxHealth - (maxHealth / 3))) {
				g.setColor(Color.GREEN);
			}
			else if(health >= (maxHealth - (2*(maxHealth / 3)))) {
				g.setColor(Color.YELLOW);
			}
			else {
				g.setColor(Color.RED);
			}
			
			String display = String.valueOf(health);
			g.drawString(display, (int)this.getX() + 60, (int)this.getY()-10);
			**/
		}
		
		if(Game.gameState == Game.STATE.KeyFromGrogo) {
			g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
			changeCount++;
			if(changeCount % 2 == 0) {
				idleCount++;
			}
			if(idleCount == 5) {
				idleCount = 0;
				changeCount = 0;
			}
		}
		if(Game.gameState == Game.STATE.Game) {
			if(velX == 0) {
				g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
				changeCount++;
				if(changeCount % 2 == 0) {
					idleCount++;
				}
				if(idleCount == 5) {
					idleCount = 0;
					changeCount = 0;
				}
			}
			else if(velX > 0) {
				g.drawImage(movingRight.get(animationCount), (int)x, (int)y, null);
				changeCount++;
				if(changeCount % 2 == 0) {
					animationCount++;
				}
				if(animationCount == 6) {
					animationCount = 0;
					changeCount = 0;
				}
			}
			else if(velX < 0) {
				g.drawImage(movingLeft.get(animationCount), (int)x, (int)y, null);
				changeCount++;
				if(changeCount % 2 == 0) {
					animationCount++;
				}
				if(animationCount == 6) {
					animationCount = 0;
					changeCount = 0;
				}
			}
		}
		else if(Game.gameState == Game.STATE.Battle) {
			if(Battle.battleState == Battle.BATTLESTATE.PlayerTurnStart || Battle.battleState == Battle.BATTLESTATE.PlayerTurnAction) {
				if(Battle.contact) {
					g.drawImage(hurt.get(hurtCount), (int)x, (int)y, null);
					changeCount++;
					
					if(changeCount % 10 == 0) {
						hurtCount++;
					}
					
					
					if(hurtCount == 2) {
						hurtCount = 0;
						changeCount = 0;
					}
					return;
				}
				g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
				changeCount++;
				if(changeCount % 20 == 0) {
					idleCount++;
				}
				if(idleCount == 5) {
					idleCount = 0;
					changeCount = 0;
				}	
			}
			else if(Battle.battleState == Battle.BATTLESTATE.EnemyTurn) {
				if(velX < 0) {
					generateAttack();
					laserBulletX = (int)this.getX()-25;
					laserBulletY = (int)this.getY() + 40;
					g.drawImage(movingLeft.get(animationCount), (int)x, (int)y, null);
					changeCount++;
					if(changeCount % 10 == 0) {
						animationCount++;
					}
					if(animationCount == 6) {
						animationCount = 0;
						changeCount = 0;
					}
					return;
				}
				else if(velX > 0) {
					g.drawImage(movingRight.get(animationCount), (int)x, (int)y, null);
					changeCount++;
					if(changeCount % 20 == 0) {
						animationCount++;
					}
					if(animationCount == 6) {
						animationCount = 0;
						changeCount = 0;
					}
				}
				else if(velX==0) {
					//hair whip
					if(attackNum == 0) {
						g.drawImage(attack1.get(attack1Count), (int)x, (int)y, null);
						changeCount++;
						if(changeCount % 20 == 0) {
							attack1Count++;
						}
						
						
						if(attack1Count >= 4 && attack1Count <= 13) {
							attack1Count++;
							changeCount = 0;
						}
						
						if(attack1Count == 5) {
							AudioPlayer.getSound("hairWhip").play(1, (float).5);
						}
						
						if(attack1Count == 12) {
							AudioPlayer.getSound("golemPunch").play(1, (float).5);
							Battle.takeDamage = true;
							if(HUD.allyCount != 3) HUD.allyCount += 1;
						}
						else {
							Battle.takeDamage = false;
						}
						
						if(attack1Count >= 11 && attack1Count <= 16) {
							Battle.contact = true;
						}
						else {
							Battle.contact = false;
						}
						
						if(attack1Count == 17) {
							attack1Count = 0;
							this.setVelX(3);
							attackCheck = false;
						}
						
						return;
					}
					//blaster
					else if(attackNum == 1) {
						
						g.drawImage(attack2.get(attack2Count), (int)x, (int)y, null);
						changeCount++;
						if(changeCount % 20 == 0) {
							attack2Count++;
						}
						
						if(attack2Count >= 3 && attack2Count <= 8) {
							attack2Count++;
							changeCount = 0;
						}
						
						if((attack2Count == 13 && changeCount % 20 == 0)) {
							AudioPlayer.getSound("laserShotZ").play(1, (float).5);
						}
						
						if((attack2Count == 16 && changeCount % 20 == 0)) {
							AudioPlayer.getSound("impact2").play(1, (float).5);
							Battle.takeDamage = true;
							if(HUD.allyCount != 3) HUD.allyCount += 1;
						}
						else {
							Battle.takeDamage = false;
						}
						if(attack2Count != 22) {
							if(attack2Count >= 14 && attack2Count <=18) {
								
								laserBulletY -= yFactorUp;
								laserBulletX -= 5;
								yFactorUp -= .1;
								yFactorDown = yFactorUp;
								
								if(laserBulletY > (int)this.getY() + 50) {
									return;
								}
								
								g.drawImage(laser, laserBulletX, laserBulletY, null);
								
							}
							if(attack2Count >=19) {
								
								laserBulletY += yFactorDown;
								laserBulletX -= 5;
								yFactorDown += .1;	
								
								if(laserBulletY > (int)this.getY() + 50) {
									return;
								}
								
								g.drawImage(laser, laserBulletX, laserBulletY, null);
							}
						}
					
						
						
						
						if(attack2Count == 16 || attack2Count == 17) {
							Battle.contact = true;
						}
						else {
							Battle.contact = false;
						}
						if(attack2Count == 22) {
							attack2Count = 0;
							this.setVelX(3);
							attackCheck = false;
							yFactorUp = (float) 2;
							yFactorDown = 0;
							
						}
						
						
						return;
					}
					//eye laser
					else if(attackNum == 2) {
						g.drawImage(attack3.get(attack3Count), (int)x, (int)y, null);
						
						changeCount++;
						if(changeCount % 20 == 0) {
							attack3Count++;
						}
						
						if((attack3Count == 1 && changeCount % 20 == 0)) {
							AudioPlayer.getSound("laserEye").play(1, (float).5);
						}
						
						
						if((attack3Count == 4 && changeCount % 20 == 0)) {
							AudioPlayer.getSound("impact2").play(1, (float).5);
							Battle.takeDamage = true;
							if(HUD.allyCount != 3) HUD.allyCount += 1;
						}
						else {
							Battle.takeDamage = false;
						}
						
						if(attack3Count >= 4 && attack3Count <= 6) {
							Battle.contact = true;
						}
						else {
							Battle.contact = false;
						}
						
						if(attack3Count == 9) {
							attack3Count = 0;
							this.setVelX(3);
							attackCheck = false;
						}
						
						return;
					}
				}
			}
			else if(Battle.battleState == Battle.BATTLESTATE.CrystalCutscene && CrystalCutscene.crystalState == CrystalCutscene.CRYSTALSTATE.PovyDead) {
				g.drawImage(laugh.get(hurtCount), (int)x, (int)y, null);
				changeCount++;
				
				if(changeCount % 20 == 0) {
					hurtCount++;
				}
				
				
				if(hurtCount == 2) {
					hurtCount = 0;
					changeCount = 0;
				}
				return;
				
			}
			else if(Battle.battleState == Battle.BATTLESTATE.CrystalCutscene && CrystalCutscene.crystalState == CrystalCutscene.CRYSTALSTATE.PovyRise) {
				g.drawImage(laugh.get(hurtCount), (int)x, (int)y, null);
				changeCount++;
				
				if(changeCount % 20 == 0) {
					hurtCount++;
				}
				
				
				if(hurtCount == 2) {
					hurtCount = 0;
					changeCount = 0;
				}
				return;
				
			}
			else if(Battle.battleState == Battle.BATTLESTATE.CrystalCutscene && CrystalCutscene.crystalState == CrystalCutscene.CRYSTALSTATE.UsingCrystal) {
				if(Battle.contact) {
					g.drawImage(hurt.get(hurtCount), (int)x, (int)y, null);
					changeCount++;
					
					if(changeCount % 10 == 0) {
						hurtCount++;
					}
					
					
					if(hurtCount == 2) {
						hurtCount = 0;
						changeCount = 0;
					}
					return;
				}
				
				
				g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
				changeCount++;
				if(changeCount % 20 == 0) {
					idleCount++;
				}
				if(idleCount == 5) {
					idleCount = 0;
					changeCount = 0;
				}	
				
			}
			else if(Battle.battleState == Battle.BATTLESTATE.BackToGame && CrystalCutscene.crystalState == CrystalCutscene.CRYSTALSTATE.ZatolibDies) {
				if(dieCount != 5) {
					g.drawImage(die.get(dieCount), (int)x, (int)y, null);
					changeCount++;
					if(changeCount % 5 == 0) {
						dieCount++;
					}
					if(dieCount == 4 && changeCount % 5 == 0) {
						AudioPlayer.getSound("povyHittingGroundThud").play(1, (float).1);
					}
				}
				else {
					g.drawImage(die.get(4), (int)x, (int)y, null);
				}
				
			}
			/**
			else if(Battle.battleState == Battle.BATTLESTATE.BattleEnd) {
				g.drawImage(die.get(dieCount), (int)x, (int)y, null);
				changeCount++;
				if(changeCount % 20 == 0) {
					dieCount++;
				}
				if(dieCount == 8) {
					dieCount = 0;
					Battle.battleState = Battle.BATTLESTATE.BackToGame;
				}
			}
			else if(Battle.battleState == Battle.BATTLESTATE.BackToGame) {
				g.drawImage(die.get(7), (int)x, (int)y, null);
				
			}
			**/
			else {
				g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
				changeCount++;
				if(changeCount % 20 == 0) {
					idleCount++;
				}
				if(idleCount == 4) {
					idleCount = 0;
					changeCount = 0;
				}
			}
		}
		
		else if(Game.gameState == Game.STATE.Transition || Game.gameState == Game.STATE.Paused) {
			if(idleCount == 4) {
				idleCount = 0;
				changeCount = 0;
			}
			g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
			changeCount++;
			if(changeCount % 5 == 0) {
				idleCount++;
			}
		}
		
		
	}

	@Override
	/**
	 * enemy takes the amount of damage specified
	 */
	public void takeDamage(int damage) {
		// TODO Auto-generated method stub
		this.health -= damage;
		if(health <= 0) {
			health = 0;
		}
	}
	
	
	
	@Override
	/**
	 * returns the health of the elephant
	 */
	public int getHealth() {
		// TODO Auto-generated method stub
		int num;
		return num = this.health;
	}
	
	
	/**
	 * 
	 * @return attack that is being performed
	 */
	public int getAttackDam() {
		int num;
		return num = attackNum;
	}
	
	@Override
	/**
	 * returns the maximum health that the elephant had originally
	 */
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		int num;
		return num = this.maxHealth;
	}


}
