 import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
/**
 * A Rat is a weak enemy found in the dungeon, has one attack, low health
 * @author clarkt5
 *
 */
public class Rat extends GameObject{
	
	Handler handler;
	private ArrayList<BufferedImage> idle;
	private ArrayList<BufferedImage> movingLeft;
	private ArrayList<BufferedImage> movingRight;
	private ArrayList<BufferedImage> hurt;
	private ArrayList<BufferedImage> attack1;
	private ArrayList<BufferedImage> attack2;
	private ArrayList<BufferedImage> die;
	private Random healthGenerator;
	private int health = 0;
	private int animationCount = 0;
	private int attack1Count, dieCount = 0;
	private int idleCount = 0;
	private int changeCount = 0;
	private int hurtCount = 0;
	private int maxHealth = 0;
	private int idt;
	private boolean setVel = false;
	
	/**
	 * creates the rat
	 * @param x
	 * @param y
	 * @param id
	 * @param handler
	 * @param idt
	 */
	public Rat(float x, float y, ID id, Handler handler, int idt){
		super(x, y, id);
		this.height = 126;
		this.handler = handler;
		this.idt = idt;
		healthGenerator = new Random();
		health = healthGenerator.nextInt(3);
		if(health == 0) {
			health = 10;
		}
		else if(health == 1) {
			health = 10;
		}
		else if(health == 2) {
			health = 10;
		}
	
		maxHealth = health;
		
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		
		idle = new ArrayList<BufferedImage>();
		idle.add(ss.grabImage(1, 1, 100, 52,"rat"));
		idle.add(ss.grabImage(1, 2, 100, 52,"rat"));
		idle.add(ss.grabImage(1, 3, 100, 52,"rat"));
		idle.add(ss.grabImage(1, 4, 100, 52,"rat"));
		
		
		movingLeft = new ArrayList<BufferedImage>();
		movingLeft.add(ss.grabImage(1, 5, 100, 52,"rat"));
		movingLeft.add(ss.grabImage(1, 6, 100, 52,"rat"));
		movingLeft.add(ss.grabImage(2, 1, 100, 52,"rat"));
		movingLeft.add(ss.grabImage(2, 2, 100, 52,"rat"));
		
		
		attack1 = new ArrayList<BufferedImage>();
		attack1.add(ss.grabImage(2, 3, 100, 52,"rat"));
		attack1.add(ss.grabImage(2, 4, 100, 52,"rat"));
		attack1.add(ss.grabImage(2, 5, 100, 52,"rat"));
		attack1.add(ss.grabImage(2, 6, 100, 52,"rat"));
		attack1.add(ss.grabImage(3, 1, 100, 52,"rat"));
		attack1.add(ss.grabImage(3, 2, 100, 52,"rat"));
		
		hurt = new ArrayList<BufferedImage>();
		hurt.add(ss.grabImage(3, 3, 100, 52,"rat"));
		hurt.add(ss.grabImage(3, 4, 100, 52,"rat"));
		hurt.add(ss.grabImage(3, 5, 100, 52,"rat"));
		
		movingRight = new ArrayList<BufferedImage>();
		movingRight.add(ss.grabImage(1, 1, 100, 52,"rat1"));
		movingRight.add(ss.grabImage(1, 2, 100, 52,"rat1"));
		movingRight.add(ss.grabImage(2, 5, 100, 52,"rat1"));
		movingRight.add(ss.grabImage(2, 6, 100, 52,"rat1"));
		
		
		die = new ArrayList<BufferedImage>();
		die.add(ss.grabImage(3, 6, 100, 52,"rat"));
		die.add(ss.grabImage(4, 1, 100, 52,"rat"));
		die.add(ss.grabImage(4, 2, 100, 52,"rat"));
		die.add(ss.grabImage(4, 3, 100, 52,"rat"));
		die.add(ss.grabImage(4, 4, 100, 52,"rat"));
		
		
		velX = -4;
	}
	
	/**
	 * creates a copy of the rat
	 */
	public Rat copy() {
		return new Rat(x, y, id, handler, idt);
	}
	
	
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
	 * checks the rat's collision with obstacles on the map
	 */
	public void collision() {
		Iterator<String> itr = Game.collisionTiles.get(new Integer(0)).keySet().iterator();
		while(itr.hasNext()) {
			String cur = itr.next();
			if(!cur.contains("281")) {
				for(int i = 0; i < Game.collisionTiles.get(new Integer(0)).get(cur).size(); i+=2) {
					if(getBounds().intersects(new Rectangle(Game.collisionTiles.get(new Integer(0)).get(cur).get(i), Game.collisionTiles.get(new Integer(0)).get(cur).get(i+1),
							32, 32))) {
						if(!setVel) {
							if(idt == 1) {
								System.out.println("hit");
								System.out.println(velX);
							}
							if(velX < 0) {
								//x = Game.clampUpLeft((int)x, Game.collisionTiles.get(new Integer(0)).get(cur).get(i)+11);
								this.setVelX((int)this.getVelX()*-1);
								setVel = true;
								break;
							}
							else if(velX > 0){
								//x = Game.clampDownRight((int)x, Game.collisionTiles.get(new Integer(0)).get(cur).get(i)-10);
								this.setVelX((int)this.getVelX()*-1);
								setVel = true;
								break;
							}
							
						}
					}
				}
				if(setVel) {
					setVel = false;
					break;
				}
			}
		}
	}
	
	
	
	/**
	 * returns the bounds of the rat
	 */
	public Rectangle getBounds() {
		if(Game.gameState == Game.STATE.Battle) {
			if(Battle.menuPosition == 0 && Game.gameState == Game.STATE.Battle && Battle.battleState == Battle.BATTLESTATE.PlayerTurnAction)
				return new Rectangle((int)x + 10,(int)y+10,104, 49);
			return new Rectangle((int)x + 10, (int)y + 10, 104, 49);
		}
		
		return new Rectangle((int)x, (int)y + 10, 104, 49);
	}
	
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
			g.drawString(display, (int)this.getX() + 20, (int)this.getY()-10);
		}
		//g.setColor(Color.green);
		//g.fillRect((int)x, (int)y, 50, 50);
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
					
					g.drawImage(attack1.get(attack1Count), (int)x, (int)y, null);
					
					changeCount++;
					if(changeCount % 20 == 0) {
						attack1Count++;
					}
					if(attack1Count == 4 && changeCount % 20 == 0) {
						AudioPlayer.getSound("ratHit").play(1, (float).5);
						Battle.takeDamage = true;
						if(HUD.allyCount != 3) HUD.allyCount += 1;
					}
					else {
						Battle.takeDamage = false;
					}
					if(attack1Count == 4 || attack1Count == 5) {
						Battle.contact = true;
					}
					else {
						Battle.contact = false;
					}
					if(attack1Count == 6) {
						attack1Count = 0;
						this.setVelX(3);
					}
					
					return;
					
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
				if(dieCount == 5) {
					dieCount = 0;
					Battle.battleState = Battle.BATTLESTATE.BackToGame;
				}
			}
			else if(Battle.battleState == Battle.BATTLESTATE.BackToGame) {
				g.drawImage(die.get(4), (int)x, (int)y, null);
				
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
		
		if(Game.gameState == Game.STATE.Battle) {
			g.drawRect((int)x+10, (int)y + 10, 104, 49);
		}
		if(Game.gameState == Game.STATE.Game) {
			
			g.drawRect((int)x, (int)y + 10, 104, 49);
		}
		
	}

	@Override
	public void takeDamage(int damage) {
		// TODO Auto-generated method stub
		this.health -= damage;
		if(health <= 0) {
			health = 0;
		}
	}
	
	
	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		int num;
		return num = this.health;
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		int num;
		return num = this.maxHealth;
	}

	
}
