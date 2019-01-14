import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
/**
 * 
 * @author clarkt5
 * an Elephant Guard is a mid-level boss that has three attacks
 */
public class ElephantGuard extends GameObject{
	
	Handler handler;
	private ArrayList<BufferedImage> idle;
	private ArrayList<BufferedImage> movingLeft;
	private ArrayList<BufferedImage> movingRight;
	private ArrayList<BufferedImage> hurt;
	private ArrayList<BufferedImage> attack1;
	private ArrayList<BufferedImage> attack2;
	private ArrayList<BufferedImage> attack3;
	private ArrayList<BufferedImage> die;
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
	private float beforeJumpY = 0;
	private float yFactorUp = (float) 2, yFactorDown;
	
	
	/**
	 * creates the elephant
	 * @param x
	 * @param y
	 * @param id
	 * @param handler
	 * @param g
	 */
	public ElephantGuard(float x, float y, ID id, Handler handler){
		super(x, y, id);
		this.height = 160;
		this.handler = handler;
		attackOption = new Random();
		healthGenerator = new Random();
		health = healthGenerator.nextInt(3);
		health = 90;
		maxHealth = health;
		
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		
		idle = new ArrayList<BufferedImage>();
		idle.add(ss.grabImage(1, 1, 170, 160,"elephantGuard"));
		idle.add(ss.grabImage(1, 2, 170, 160,"elephantGuard"));
		idle.add(ss.grabImage(1, 3, 170, 160,"elephantGuard"));
		idle.add(ss.grabImage(1, 4, 170, 160,"elephantGuard"));
		
		movingLeft = new ArrayList<BufferedImage>();
		movingLeft.add(ss.grabImage(1, 5, 170, 160,"elephantGuard"));
		movingLeft.add(ss.grabImage(1, 6, 170, 160,"elephantGuard"));
		movingLeft.add(ss.grabImage(2, 1, 170, 160,"elephantGuard"));
		movingLeft.add(ss.grabImage(2, 2, 170, 160,"elephantGuard"));
		
		movingRight = new ArrayList<BufferedImage>();
		movingRight.add(ss.grabImage(1, 2, 170, 160,"elephantGuard1"));
		movingRight.add(ss.grabImage(1, 1, 170, 160,"elephantGuard1"));
		movingRight.add(ss.grabImage(2, 6, 170, 160,"elephantGuard1"));
		movingRight.add(ss.grabImage(2, 5, 170, 160,"elephantGuard1"));
		
		hurt = new ArrayList<BufferedImage>();
		hurt.add(ss.grabImage(10, 2, 170, 160,"elephantGuard"));
		hurt.add(ss.grabImage(10, 3, 170, 160,"elephantGuard"));
		
		attack1 = new ArrayList<BufferedImage>();
		attack1.add(ss.grabImage(2, 3, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(2, 4, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(2, 5, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(2, 6, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(3, 1, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(3, 2, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(3, 3, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(3, 4, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(3, 5, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(3, 6, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(4, 1, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(4, 2, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(4, 3, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(4, 4, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(4, 5, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(4, 6, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(5, 1, 170, 160,"elephantGuard"));
		attack1.add(ss.grabImage(5, 2, 170, 160,"elephantGuard"));
		
		attack2 = new ArrayList<BufferedImage>();
		attack2.add(ss.grabImage(5, 3, 170, 160,"elephantGuard"));
		attack2.add(ss.grabImage(5, 4, 170, 160,"elephantGuard"));
		attack2.add(ss.grabImage(5, 5, 170, 160,"elephantGuard"));
		attack2.add(ss.grabImage(5, 6, 170, 160,"elephantGuard"));
		attack2.add(ss.grabImage(6, 1, 170, 160,"elephantGuard"));
		attack2.add(ss.grabImage(6, 2, 170, 160,"elephantGuard"));
		attack2.add(ss.grabImage(6, 3, 170, 160,"elephantGuard"));
		attack2.add(ss.grabImage(6, 4, 170, 160,"elephantGuard"));
		attack2.add(ss.grabImage(6, 5, 170, 160,"elephantGuard"));
		attack2.add(ss.grabImage(6, 6, 170, 160,"elephantGuard"));
		attack2.add(ss.grabImage(7, 1, 170, 160,"elephantGuard"));
		attack2.add(ss.grabImage(7, 2, 170, 160,"elephantGuard"));
		attack2.add(ss.grabImage(7, 3, 170, 160,"elephantGuard"));
		
		attack3 = new ArrayList<BufferedImage>();
		attack3.add(ss.grabImage(7, 4, 170, 160,"elephantGuard"));
		attack3.add(ss.grabImage(7, 5, 170, 160,"elephantGuard"));
		attack3.add(ss.grabImage(7, 6, 170, 160,"elephantGuard"));
		attack3.add(ss.grabImage(8, 1, 170, 160,"elephantGuard"));
		attack3.add(ss.grabImage(8, 2, 170, 160,"elephantGuard"));
		attack3.add(ss.grabImage(8, 3, 170, 160,"elephantGuard"));
		attack3.add(ss.grabImage(8, 4, 170, 160,"elephantGuard"));
		attack3.add(ss.grabImage(8, 5, 170, 160,"elephantGuard"));
		attack3.add(ss.grabImage(8, 6, 170, 160,"elephantGuard"));
		attack3.add(ss.grabImage(9, 1, 170, 160,"elephantGuard"));
		attack3.add(ss.grabImage(9, 2, 170, 160,"elephantGuard"));
		attack3.add(ss.grabImage(9, 3, 170, 160,"elephantGuard"));
		attack3.add(ss.grabImage(9, 4, 170, 160,"elephantGuard"));
		attack3.add(ss.grabImage(9, 5, 170, 160,"elephantGuard"));
		attack3.add(ss.grabImage(9, 6, 170, 160,"elephantGuard"));
		attack3.add(ss.grabImage(10, 1, 170, 160,"elephantGuard"));
		
		
		die = new ArrayList<BufferedImage>();
		die.add(ss.grabImage(10, 4, 170, 160,"elephantGuard"));
		die.add(ss.grabImage(10, 5, 170, 160,"elephantGuard"));
		die.add(ss.grabImage(10, 6, 170, 160,"elephantGuard"));
		die.add(ss.grabImage(11, 1, 170, 160,"elephantGuard"));
		die.add(ss.grabImage(11, 2, 170, 160,"elephantGuard"));
		die.add(ss.grabImage(11, 3, 170, 160,"elephantGuard"));
		die.add(ss.grabImage(11, 4, 170, 160,"elephantGuard"));
		die.add(ss.grabImage(11, 5, 170, 160,"elephantGuard"));
		
		
		
		velX = 0;
	}
	
	/**
	 * returns a copy of the elephant guard
	 */
	public ElephantGuard copy() {
		return new ElephantGuard(x, y, id, handler);
	}
	
	/**
	 * choose out of three attacks at random
	 */
	private void generateAttack() {
		if(attackCheck == false) {
			attackNum = attackOption.nextInt(3);
			attackCheck = true;
			beforeJumpY = this.getY();
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
				return new Rectangle((int)x + 70,(int)y+54,113, 30);
			return new Rectangle((int)x + 50, (int)y + 130, 113, 30);
		}
		return new Rectangle((int)x-600, (int)y, 1200, 160);
	}
	
	
	/**
	 * renders the enemy
	 */
	public void render(Graphics g) {
		if(Game.gameState == Game.STATE.Battle) {
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
		}
		//g.setColor(Color.green);
		//g.fillRect((int)x, (int)y, 50, 50);
		if(Game.gameState == Game.STATE.KeyFromGrogo) {
			g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
			changeCount++;
			if(changeCount % 2 == 0) {
				idleCount++;
			}
			if(idleCount == 4) {
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
				if(idleCount == 4) {
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
				if(animationCount == 4) {
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
				if(animationCount == 4) {
					animationCount = 0;
					changeCount = 0;
				}
			}
		}
		else if(Game.gameState == Game.STATE.Battle) {
			if(Battle.battleState == Battle.BATTLESTATE.PlayerTurnStart || Battle.battleState == Battle.BATTLESTATE.PlayerTurnAction) {
				if(Battle.contact) {
					g.drawImage(hurt.get(hurtCount), (int)x, (int)y, null);
					//changeCount++;
					/**
					if(changeCount % 7 == 0) {
						hurtCount++;
					}
					**/
					hurtCount++;
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
				if(idleCount == 4) {
					idleCount = 0;
					changeCount = 0;
				}	
			}
			else if(Battle.battleState == Battle.BATTLESTATE.EnemyTurn) {
				if(velX < 0) {
					g.drawImage(movingLeft.get(animationCount), (int)x, (int)y, null);
					changeCount++;
					if(changeCount % 20 == 0) {
						animationCount++;
					}
					if(animationCount == 4) {
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
					if(animationCount == 4) {
						animationCount = 0;
						changeCount = 0;
					}
				}
				else if(velX==0) {
					generateAttack();
					//Trunk attack
					if(attackNum == 0) {
						g.drawImage(attack1.get(attack1Count), (int)x, (int)y, null);
						changeCount++;
						if(changeCount % 20 == 0) {
							attack1Count++;
						}
						
						if(attack1Count == 8 && changeCount % 20 == 0) {
							AudioPlayer.getSound("menuSlider").play(1, (float).5);
						}
						if(attack1Count >= 8 && attack1Count <= 12) {
							attack1Count++;
							changeCount = 0;
						}
						
						if(attack1Count == 12 && changeCount % 20 == 0) {
							AudioPlayer.getSound("golemPunch").play(1, (float).5);
							Battle.takeDamage = true;
							if(HUD.allyCount != 3) HUD.allyCount += 1;
						}
						else {
							Battle.takeDamage = false;
						}
						if(attack1Count == 11 || attack1Count == 12 || attack1Count == 13) {
							Battle.contact = true;
						}
						else {
							Battle.contact = false;
						}
						if(attack1Count == 18) {
							attack1Count = 0;
							this.setVelX(3);
							attackCheck = false;
						}
						
						return;
					}
					//jumping attack
					else if(attackNum == 1) {
						g.drawImage(attack2.get(attack2Count), (int)x, (int)y, null);
						changeCount++;
						if(changeCount % 20 == 0) {
							attack2Count++;
						}
						if((attack2Count == 3) || (attack2Count == 4) || (attack2Count == 5) 
								|| (attack2Count == 6) ) {
							this.y -= yFactorUp;
							this.x -= .7;
							yFactorUp -= .05;
							yFactorDown = yFactorUp;
							if((attack2Count == 3 && changeCount % 20 == 0)) {
								AudioPlayer.getSound("elephantJump").play(1, (float).5);
							}
						}
						if((attack2Count == 7) || (attack2Count == 8) || (attack2Count == 9) 
								|| (attack2Count == 10)) {
							
							this.y += yFactorDown;
							this.x -= .7;
							yFactorDown += .05;	
						}
						
						
						if((attack2Count == 7 && changeCount % 20 == 0)) {
							AudioPlayer.getSound("elephantLand").play(1, (float).5);
							Battle.takeDamage = true;
							if(HUD.allyCount != 3) HUD.allyCount += 1;
							this.setY((int)beforeJumpY);
						}
						else {
							Battle.takeDamage = false;
						}
						
						
						
						if(attack2Count == 8 || attack2Count == 9 || attack2Count == 10) {
							Battle.contact = true;
						}
						else {
							Battle.contact = false;
						}
						if(attack2Count == 13) {
							attack2Count = 0;
							this.setVelX(3);
							attackCheck = false;
							yFactorUp = 2;
							yFactorDown = 0;
							
						}
						
						
						return;
					}
					//punch
					else if(attackNum == 2) {
						g.drawImage(attack3.get(attack3Count), (int)x, (int)y, null);
						
						changeCount++;
						if(changeCount % 20 == 0) {
							attack3Count++;
						}
						if(attack3Count >= 2 && attack3Count <= 9) {
							attack3Count++;
							changeCount = 0;
						}
						if((attack3Count == 8 && changeCount % 20 == 0)) {
							AudioPlayer.getSound("golemPunch").play(1, (float).5);
							Battle.takeDamage = true;
							if(HUD.allyCount != 3) HUD.allyCount += 1;
						}
						else {
							Battle.takeDamage = false;
						}
						if(attack3Count == 8 || attack3Count == 9 || attack3Count == 10) {
							Battle.contact = true;
						}
						else {
							Battle.contact = false;
						}
						if(attack3Count == 16) {
							attack3Count = 0;
							this.setVelX(3);
							attackCheck = false;
						}
						
						return;
					}
				}
			}
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
			else if(Battle.battleState == Battle.BATTLESTATE.PlayerDies) {
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
			g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
			changeCount++;
			if(changeCount % 5 == 0) {
				idleCount++;
			}
			if(idleCount == 4) {
				idleCount = 0;
				changeCount = 0;
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
