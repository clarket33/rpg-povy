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
 * a Golem is an enemy that has two attacks and is a guard in the dungeon
 */
public class Golem extends GameObject{
	
	Handler handler;
	private ArrayList<BufferedImage> idle;
	private ArrayList<BufferedImage> facingRightIdle;
	private ArrayList<BufferedImage> movingLeft;
	private ArrayList<BufferedImage> movingRight;
	private ArrayList<BufferedImage> hurt;
	private ArrayList<BufferedImage> attack1;
	private ArrayList<BufferedImage> attack2;
	private ArrayList<BufferedImage> die;
	private Random attackOption, healthGenerator;
	private int attackNum;
	private int health = 0;
	private boolean attackCheck = false;
	private int animationCount = 0;
	private int attack1Count, attack2Count = 0, dieCount = 0;
	private int idleCount = 0;
	private int changeCount = 0;
	private int hurtCount = 0;
	private int maxHealth = 0;
	private GolemType golemState;
	private int curDirection;
	
	public enum GolemType{
		firstGolem,
		normal,
		treasureGuard
	};
	
	/**
	 * creates the golem
	 * @param x
	 * @param y
	 * @param id
	 * @param handler
	 * @param g
	 */
	public Golem(float x, float y, ID id, Handler handler, GolemType g){
		super(x, y, id);
		this.height = 126;
		this.handler = handler;
		attackOption = new Random();
		healthGenerator = new Random();
		golemState = g;
		health = healthGenerator.nextInt(3);
		if(health == 0) {
			health = 60;
		}
		else if(health == 1) {
			health = 60;
		}
		else if(health == 2) {
			health = 60;
		}
		if(g == GolemType.firstGolem) {
			health = 3;
		}
		maxHealth = health;
		
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		facingRightIdle = new ArrayList<BufferedImage>();
		facingRightIdle.add(ss.grabImage(1, 6, 164, 126,"golem1"));
		facingRightIdle.add(ss.grabImage(1, 5, 164, 126,"golem1"));
		facingRightIdle.add(ss.grabImage(1, 4, 164, 126,"golem1"));
		facingRightIdle.add(ss.grabImage(1, 3, 164, 126,"golem1"));
		facingRightIdle.add(ss.grabImage(1, 2, 164, 126,"golem1"));
		
		idle = new ArrayList<BufferedImage>();
		idle.add(ss.grabImage(1, 1, 164, 126,"golem"));
		idle.add(ss.grabImage(1, 2, 164, 126,"golem"));
		idle.add(ss.grabImage(1, 3, 164, 126,"golem"));
		idle.add(ss.grabImage(1, 4, 164, 126,"golem"));
		idle.add(ss.grabImage(1, 5, 164, 126,"golem"));
		
		
		movingLeft = new ArrayList<BufferedImage>();
		movingLeft.add(ss.grabImage(1, 6, 164, 126,"golem"));
		movingLeft.add(ss.grabImage(2, 1, 164, 126,"golem"));
		movingLeft.add(ss.grabImage(2, 2, 164, 126,"golem"));
		movingLeft.add(ss.grabImage(2, 3, 164, 126,"golem"));
		movingLeft.add(ss.grabImage(2, 4, 164, 126,"golem"));
		movingLeft.add(ss.grabImage(2, 5, 164, 126,"golem"));
		
		movingRight = new ArrayList<BufferedImage>();
		movingRight.add(ss.grabImage(1, 1, 164, 126,"golem1"));
		movingRight.add(ss.grabImage(2, 6, 164, 126,"golem1"));
		movingRight.add(ss.grabImage(2, 5, 164, 126,"golem1"));
		movingRight.add(ss.grabImage(2, 4, 164, 126,"golem1"));
		movingRight.add(ss.grabImage(2, 3, 164, 126,"golem1"));
		movingRight.add(ss.grabImage(2, 2, 164, 126,"golem1"));
		
		hurt = new ArrayList<BufferedImage>();
		hurt.add(ss.grabImage(5, 6, 164, 126,"golem"));
		hurt.add(ss.grabImage(6, 1, 164, 126,"golem"));
		hurt.add(ss.grabImage(6, 2, 164, 126,"golem"));
		
		attack1 = new ArrayList<BufferedImage>();
		attack1.add(ss.grabImage(2, 6, 164, 126,"golem"));
		attack1.add(ss.grabImage(3, 1, 164, 126,"golem"));
		attack1.add(ss.grabImage(3, 2, 164, 126,"golem"));
		attack1.add(ss.grabImage(3, 3, 164, 126,"golem"));
		attack1.add(ss.grabImage(3, 4, 164, 126,"golem"));
		attack1.add(ss.grabImage(3, 5, 164, 126,"golem"));
		attack1.add(ss.grabImage(3, 6, 164, 126,"golem"));
		
		attack2 = new ArrayList<BufferedImage>();
		attack2.add(ss.grabImage(4, 1, 164, 126,"golem"));
		attack2.add(ss.grabImage(4, 2, 164, 126,"golem"));
		attack2.add(ss.grabImage(4, 3, 164, 126,"golem"));
		attack2.add(ss.grabImage(4, 4, 164, 126,"golem"));
		attack2.add(ss.grabImage(4, 5, 164, 126,"golem"));
		attack2.add(ss.grabImage(4, 6, 164, 126,"golem"));
		attack2.add(ss.grabImage(5, 1, 164, 126,"golem"));
		attack2.add(ss.grabImage(5, 2, 164, 126,"golem"));
		attack2.add(ss.grabImage(5, 3, 164, 126,"golem"));
		attack2.add(ss.grabImage(5, 4, 164, 126,"golem"));
		attack2.add(ss.grabImage(5, 5, 164, 126,"golem"));
		
		die = new ArrayList<BufferedImage>();
		die.add(ss.grabImage(6, 3, 164, 126,"golem"));
		die.add(ss.grabImage(6, 4, 164, 126,"golem"));
		die.add(ss.grabImage(6, 5, 164, 126,"golem"));
		die.add(ss.grabImage(6, 6, 164, 126,"golem"));
		die.add(ss.grabImage(7, 1, 164, 126,"golem"));
		die.add(ss.grabImage(7, 2, 164, 126,"golem"));
		die.add(ss.grabImage(7, 3, 164, 126,"golem"));
		die.add(ss.grabImage(7, 4, 164, 126,"golem"));
		die.add(ss.grabImage(7, 5, 164, 126,"golem"));
		
		
		if(golemState == GolemType.normal) velX = -2;
		else velX = 0;
	}
	
	/**
	 * returns a copy of the golem
	 */
	public Golem copy() {
		return new Golem(x, y, id, handler, golemState);
	}
	
	/**
	 * choose out of two attacks at random
	 */
	private void generateAttack() {
		if(attackCheck == false) {
			attackNum = attackOption.nextInt(2);
			attackCheck = true;
		}
	}
	/**
	 * tick method for golem
	 */
	public void tick() {
		if(Game.gameState == Game.STATE.Game) {
			if(golemState == GolemType.normal) {
				x += velX;
				y += velY;
			}
			
			if(velX > 0) {
				curDirection = 1;
			}
			else if(velX < 0) {
				curDirection = -1;
			}
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
	 * 
	 * @return type of golem
	 */
	public GolemType type() {
		return golemState;
	}

	/**
	 * returns bounds of the enemy that, if the player comes in contact with it, the game goe sinto a battle state
	 */
	public Rectangle getBounds() {
		if(Game.gameState == Game.STATE.Battle) {
			if(Battle.menuPosition == 0 && Game.gameState == Game.STATE.Battle && Battle.battleState == Battle.BATTLESTATE.PlayerTurnAction)
				return new Rectangle((int)x+78,(int)y+54,38, 42);
			return new Rectangle((int)x + 40, (int)y + 96, 108, 30);
		}
		else if(Game.gameState == Game.STATE.Game) {
			if(golemState == GolemType.firstGolem || golemState == GolemType.treasureGuard) return new Rectangle((int)x + 40, (int)y - 130, 108, 370);
		}
		return new Rectangle((int)x + 40, (int)y + 96, 108, 30);
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
			if(idleCount == 5) {
				idleCount = 0;
				changeCount = 0;
			}
		}
		if(Game.gameState == Game.STATE.Game) {
			if(velX == 0) {
				if(golemState == GolemType.treasureGuard) {
					g.drawImage(facingRightIdle.get(idleCount), (int)x, (int)y, null);
					changeCount++;
					if(changeCount % 2 == 0) {
						idleCount++;
					}
					if(idleCount == 5) {
						idleCount = 0;
						changeCount = 0;
					}
					return;
				}
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
					//changeCount++;
					/**
					if(changeCount % 7 == 0) {
						hurtCount++;
					}
					**/
					hurtCount++;
					if(hurtCount == 3) {
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
					g.drawImage(movingLeft.get(animationCount), (int)x, (int)y, null);
					changeCount++;
					if(changeCount % 20 == 0) {
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
					generateAttack();
					if(attackNum == 0) {
						g.drawImage(attack1.get(attack1Count), (int)x, (int)y, null);
						
						changeCount++;
						if(changeCount % 20 == 0) {
							attack1Count++;
						}
						if(attack1Count == 4 && changeCount % 20 == 0) {
							AudioPlayer.getSound("golemPunch").play(1, (float).5);
							Battle.takeDamage = true;
							if(HUD.allyCount != 3) HUD.allyCount += 1;
						}
						else {
							Battle.takeDamage = false;
						}
						if(attack1Count == 4 || attack1Count == 5 || attack1Count == 6) {
							Battle.contact = true;
						}
						else {
							Battle.contact = false;
						}
						if(attack1Count == 7) {
							attack1Count = 0;
							this.setVelX(3);
							attackCheck = false;
						}
						
						return;
					}
					else if(attackNum == 1) {
						g.drawImage(attack2.get(attack2Count), (int)x, (int)y, null);
						
						changeCount++;
						if(changeCount % 20 == 0) {
							attack2Count++;
						}
						if((attack2Count == 4 && changeCount % 20 == 0) || (attack2Count == 8 && changeCount % 20 == 0)) {
							AudioPlayer.getSound("golemPunch").play(1, (float).5);
							Battle.takeDamage = true;
							if((attack2Count == 8 && changeCount % 20 == 0) && HUD.allyCount != 3) HUD.allyCount += 1;
						}
						else {
							Battle.takeDamage = false;
						}
						if(attack2Count == 4 || attack2Count == 5 || attack2Count == 8 || attack2Count == 9 || attack2Count == 10) {
							Battle.contact = true;
						}
						else {
							Battle.contact = false;
						}
						if(attack2Count == 11) {
							attack2Count = 0;
							this.setVelX(3);
							attackCheck = false;
						}
						
						return;
					}
				}
				/**
				g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
				changeCount++;
				if(changeCount % 20 == 0) {
					idleCount++;
				}
				if(idleCount == 5) {
					idleCount = 0;
					changeCount = 0;
				}	
				**/
			}
			else if(Battle.battleState == Battle.BATTLESTATE.BattleEnd) {
				g.drawImage(die.get(dieCount), (int)x, (int)y, null);
				changeCount++;
				if(changeCount % 20 == 0) {
					dieCount++;
				}
				if(dieCount == 9) {
					dieCount = 0;
					Battle.battleState = Battle.BATTLESTATE.BackToGame;
				}
			}
			else if(Battle.battleState == Battle.BATTLESTATE.PlayerDies) {
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
			else if(Battle.battleState == Battle.BATTLESTATE.BackToGame) {
				g.drawImage(die.get(8), (int)x, (int)y, null);
				
			}
			else {
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
		}
		
		else if(Game.gameState == Game.STATE.Transition || Game.gameState == Game.STATE.Paused) {
			if(curDirection > 0) {
				g.drawImage(facingRightIdle.get(idleCount), (int)x, (int)y, null);
				changeCount++;
				if(changeCount % 2 == 0) {
					idleCount++;
				}
				if(idleCount == 5) {
					idleCount = 0;
					changeCount = 0;
				}
				return;
			}
			if(golemState == GolemType.treasureGuard) {
				g.drawImage(facingRightIdle.get(idleCount), (int)x, (int)y, null);
				changeCount++;
				if(changeCount % 2 == 0) {
					idleCount++;
				}
				if(idleCount == 5) {
					idleCount = 0;
					changeCount = 0;
				}
				return;
			}
			g.drawImage(idle.get(idleCount), (int)x, (int)y, null);
			changeCount++;
			if(changeCount % 5 == 0) {
				idleCount++;
			}
			if(idleCount == 5) {
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
	
	/**
	 * 
	 * @return the type of golem that it is
	 */
	public GolemType getgID() {
		return this.golemState;
	}
	
	@Override
	/**
	 * returns the health of the golem
	 */
	public int getHealth() {
		// TODO Auto-generated method stub
		int num;
		return num = this.health;
	}

	@Override
	/**
	 * returns the maximum health that the golem had originally
	 */
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		int num;
		return num = this.maxHealth;
	}


}
