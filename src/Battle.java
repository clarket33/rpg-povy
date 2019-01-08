import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author clarkt5
 * Creates a battle, happens when Povy runs into an enemy, in the game state.
 * A turn-based battle ensues
 */
public class Battle{
	
	private ArrayList<String> layers = new ArrayList<String>();
	private Handler handler;
	private int torchCounter = 0;
	private Povy player;
	public static GameObject enemy;
	private Random damageMaker;
	private int damage;
	private ArrayList<BufferedImage> transition;
	private int changeCount = 0;
	private int animationCount, runStat = 0;
	private int waiter = 0;
	private ArrayList<BufferedImage> menuActions;
	private ArrayList<BufferedImage> menuActionsRight;
	public static int menuPosition = 0;
	public static BATTLESTATE battleState;
	public static int menuChange = 0;
	public static boolean takeDamage = false;
	private GameObject ally;
	private int escaped = 0, escapeCount = 0;
	private boolean canRun = false;
	public static boolean useAlly = false;
	private int laserHit = 0;
	private Random runAway;
	private BufferedImage text;
	private float enemyX, enemyY, velX, velY;
	public static boolean left = true, itemSelected = false, allySelected = false;
	private boolean setLaser = false;
	public static boolean contact = false;
	public static int expToBeAdded;
	public static boolean smDBoost=false, lgDBoost=false, smABoost=false, lgABoost=false;
	public static int defenseCount = 0, attackCount = 0;
	private int speedControl = 0, setSpeed = 0, setSpeedEnemy = 0, originalX, enemyOriginalX;
	private int deadCount = 0;

	public enum BATTLESTATE{
		PlayerTurnStart,
		PlayerTurnAction,
		BattleEnd,
		EnemyTurn,
		gotAway,
		PlayerDies,
		ZatolibWins,
		CrystalCutscene,
		BackToGame;
	};
	
	/**
	 * 
	 * @param handler
	 * @param player
	 * @param enemy
	 * creates battle, loads in images needed for transition
	 */
	public Battle(Handler handler, Povy player, GameObject enemy) {
		this.handler = handler;
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		this.player = player;
		Battle.enemy = enemy;
		damageMaker = new Random();
		runAway = new Random();
		expToBeAdded = enemy.getMaxHealth();
		animationCount = 0;
		if(enemy.getID() == ID.Golem) {
			Golem g = (Golem)enemy;
			if(g.getgID() == Golem.GolemType.firstGolem || g.getgID() == Golem.GolemType.treasureGuard	) {
				canRun = false;
			}
			else {
				canRun = true;
			}
		}
		if(enemy.getID() == ID.Zatolib) {
			Game.lastBattle = true;
		}
		else if(enemy.getID() == ID.Rat){
			canRun = true;
		}
		else if(enemy.getID() == ID.ElephantGuard){
			canRun = false;
		}
		else if(enemy.getID() == ID.Zatolib){
			canRun = false;
		}
		text = null;
		try {
	        text = ImageIO.read(new File("res/textbox.png"));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
		transition = new ArrayList<BufferedImage>();
		transition.add(ss.grabImage(3, 3, 1280, 960,"transition"));
		transition.add(ss.grabImage(3, 4, 1280, 960,"transition"));
		transition.add(ss.grabImage(3, 5, 1280, 960,"transition"));
		transition.add(ss.grabImage(3, 6, 1280, 960,"transition"));
		
		transition.add(ss.grabImage(4, 1, 1280, 960,"transition"));
		transition.add(ss.grabImage(4, 2, 1280, 960,"transition"));
		transition.add(ss.grabImage(4, 3, 1280, 960,"transition"));
		transition.add(ss.grabImage(4, 4, 1280, 960,"transition"));
		transition.add(ss.grabImage(4, 5, 1280, 960,"transition"));
		transition.add(ss.grabImage(4, 6, 1280, 960,"transition"));
		
		transition.add(ss.grabImage(5, 1, 1280, 960,"transition"));
		transition.add(ss.grabImage(5, 2, 1280, 960,"transition"));
		transition.add(ss.grabImage(5, 3, 1280, 960,"transition"));
		transition.add(ss.grabImage(5, 4, 1280, 960,"transition"));
		transition.add(ss.grabImage(5, 5, 1280, 960,"transition"));
		
		transition.add(ss.grabImage(1, 1, 1280, 960,"transition"));
		transition.add(ss.grabImage(1, 2, 1280, 960,"transition"));
		transition.add(ss.grabImage(1, 3, 1280, 960,"transition"));
		transition.add(ss.grabImage(1, 4, 1280, 960,"transition"));
		transition.add(ss.grabImage(1, 5, 1280, 960,"transition"));
		transition.add(ss.grabImage(1, 6, 1280, 960,"transition"));
		
		transition.add(ss.grabImage(2, 1, 1280, 960,"transition"));
		transition.add(ss.grabImage(2, 2, 1280, 960,"transition"));
		transition.add(ss.grabImage(2, 3, 1280, 960,"transition"));
		transition.add(ss.grabImage(2, 4, 1280, 960,"transition"));
		transition.add(ss.grabImage(2, 5, 1280, 960,"transition"));
		transition.add(ss.grabImage(2, 6, 1280, 960,"transition"));
		
		transition.add(ss.grabImage(3, 1, 1280, 960,"transition"));
		transition.add(ss.grabImage(3, 2, 1280, 960,"transition"));
		transition.add(ss.grabImage(3, 3, 1280, 960,"transition"));
		
		menuActions = new ArrayList<BufferedImage>();
		menuActions.add(ss.grabImage(1, 1, 720, 192,"menuActions"));
		menuActions.add(ss.grabImage(1, 2, 720, 192,"menuActions"));
		menuActions.add(ss.grabImage(1, 3, 720, 192,"menuActions"));
		menuActions.add(ss.grabImage(1, 4, 720, 192,"menuActions"));
		menuActions.add(ss.grabImage(1, 5, 720, 192,"menuActions"));
		menuActions.add(ss.grabImage(1, 6, 720, 192,"menuActions"));
		
		menuActions.add(ss.grabImage(2, 1, 720, 192,"menuActions"));
		menuActions.add(ss.grabImage(2, 2, 720, 192,"menuActions"));
		menuActions.add(ss.grabImage(2, 3, 720, 192,"menuActions"));
		menuActions.add(ss.grabImage(2, 4, 720, 192,"menuActions"));
		menuActions.add(ss.grabImage(2, 5, 720, 192,"menuActions"));
		menuActions.add(ss.grabImage(2, 6, 720, 192,"menuActions"));
		
		menuActions.add(ss.grabImage(3, 1, 720, 192,"menuActions"));
		menuActions.add(ss.grabImage(3, 2, 720, 192,"menuActions"));
		menuActions.add(ss.grabImage(3, 3, 720, 192,"menuActions"));
		menuActions.add(ss.grabImage(3, 4, 720, 192,"menuActions"));
		menuActions.add(ss.grabImage(3, 5, 720, 192,"menuActions"));
		menuActions.add(ss.grabImage(3, 6, 720, 192,"menuActions"));
		
		menuActions.add(ss.grabImage(4, 1, 720, 192,"menuActions"));
		
		menuActionsRight = new ArrayList<BufferedImage>();
		menuActionsRight.add(ss.grabImage(1, 1, 720, 192,"menuActionsRight"));
		menuActionsRight.add(ss.grabImage(1, 2, 720, 192,"menuActionsRight"));
		menuActionsRight.add(ss.grabImage(1, 3, 720, 192,"menuActionsRight"));
		menuActionsRight.add(ss.grabImage(1, 4, 720, 192,"menuActionsRight"));
		menuActionsRight.add(ss.grabImage(1, 5, 720, 192,"menuActionsRight"));
		menuActionsRight.add(ss.grabImage(1, 6, 720, 192,"menuActionsRight"));
		
		menuActionsRight.add(ss.grabImage(2, 1, 720, 192,"menuActionsRight"));
		menuActionsRight.add(ss.grabImage(2, 2, 720, 192,"menuActionsRight"));
		menuActionsRight.add(ss.grabImage(2, 3, 720, 192,"menuActionsRight"));
		menuActionsRight.add(ss.grabImage(2, 4, 720, 192,"menuActionsRight"));
		menuActionsRight.add(ss.grabImage(2, 5, 720, 192,"menuActionsRight"));
		menuActionsRight.add(ss.grabImage(2, 6, 720, 192,"menuActionsRight"));
		
		menuActionsRight.add(ss.grabImage(3, 1, 720, 192,"menuActionsRight"));
		menuActionsRight.add(ss.grabImage(3, 2, 720, 192,"menuActionsRight"));
		menuActionsRight.add(ss.grabImage(3, 3, 720, 192,"menuActionsRight"));
		menuActionsRight.add(ss.grabImage(3, 4, 720, 192,"menuActionsRight"));
		menuActionsRight.add(ss.grabImage(3, 5, 720, 192,"menuActionsRight"));
		menuActionsRight.add(ss.grabImage(3, 6, 720, 192,"menuActionsRight"));
		
		menuActionsRight.add(ss.grabImage(4, 1, 720, 192,"menuActionsRight"));
		
		
		/**
		 * read in the background map from tiled and store it in the layers array list
		 */
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			File theMap = new File("res/Dungeon/rpg-dungeon-pack v1.1(wonderdot)/Battle.xml");
			 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			 Document doc = dBuilder.parse(theMap);
			 doc.getDocumentElement().normalize();
			 NodeList nList = doc.getElementsByTagName("layer");
	         
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element eElement = (Element) nNode;
	               layers.add(eElement.getElementsByTagName("data").item(0).getTextContent());
	            }
	         }
	        
	         
			 
			
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	/**
	 * handles player movement in the battle
	 */
	public void tick() {
		if(battleState == BATTLESTATE.PlayerTurnStart) {
			for(int i = 0; i < Game.allies.allies.size(); i++) {
				Game.allies.allies.get(i).tick();
			}
			
		}
		if(battleState == BATTLESTATE.PlayerTurnAction) {
			//if they select pummel, perform the attack
			if(menuPosition == 5) {
				if(setSpeed == 0) {
					player.setVelX(3);
					setSpeed++;
				}
				if(!player.getBounds().intersects(enemy.getBounds())) {
					player.tick();
					enemy.tick();
				}
				else {
					//Game.clamp(player.getX(), 0, enemy.getX());
					if(player.getVelX() == -3) {
						player.tick();
						enemy.tick();
					}
					else {
						player.setVelX(0);
					}	
				}
				if(player.getVelX() == -3) {
					if(player.getX() <= originalX) {
						player.setVelX(0);
						if(lgABoost || smABoost) {
							attackCount += 1;
							if(attackCount == 3) {
								lgABoost = false;
								smABoost = false;
								attackCount = 0;
							}
						}
						battleState = BATTLESTATE.EnemyTurn;
					}
				}
			}
			
			
			
			//laser attack
			if(menuPosition == 0) {
				if(setSpeed == 0) {
					player.setVelX(3);
					setSpeed++;
				}
				if((player.getX() <= enemy.getX()-100)) {
					player.tick();
					enemy.tick();
				}
				else {
					//Game.clamp(player.getX(), 0, enemy.getX());
					if(player.getVelX() == -3) {
						player.tick();
						enemy.tick();
					}
					else {
						if(setLaser == false) {
							Povy.laserBulletX = (int)player.getX() + 67;
							player.setVelX(0);
							setLaser = true;
						}
						//taking damage for an enemy for laser blast
						if(Povy.laserBulletX >= enemy.getBounds().x) {
							Battle.contact = true;
							AudioPlayer.getSound("laserHit").play(1, (float).1);
							if(enemy.getID() == ID.Golem) {
								damage = damageMaker.nextInt(30);
								if(lgABoost) {
									if(damage <= 20) enemy.takeDamage((4 + (2*ExperienceBar.laserLevel))*3);
									else if(damage <=26)enemy.takeDamage((10 + (2*ExperienceBar.laserLevel))*3);
									else enemy.takeDamage((15 + (2*ExperienceBar.laserLevel))*3);
								}
								else if(smABoost) {
									if(damage <= 20) enemy.takeDamage((4 + (2*ExperienceBar.laserLevel))*2);
									else if(damage <=26)enemy.takeDamage((10 + (2*ExperienceBar.laserLevel))*2);
									else enemy.takeDamage((15 + (2*ExperienceBar.laserLevel))*2);
								}
								else {
									if(damage <= 20) enemy.takeDamage(4 + (2*ExperienceBar.laserLevel));
									else if(damage <=26)enemy.takeDamage(10 + (2*ExperienceBar.laserLevel));
									else enemy.takeDamage(15 + (2*ExperienceBar.laserLevel));
								}
							}
							if(enemy.getID() == ID.Rat) {
								damage = damageMaker.nextInt(30);
								if(lgABoost) {
									if(damage <= 22) enemy.takeDamage((6 + (2*ExperienceBar.laserLevel))*4);
									else if(damage <=28)enemy.takeDamage((12 + (2*ExperienceBar.laserLevel))*4);
									else enemy.takeDamage((17 + (2*ExperienceBar.laserLevel))*4);
								}
								else if(smABoost) {
									if(damage <= 22) enemy.takeDamage((6 + (2*ExperienceBar.laserLevel))*2);
									else if(damage <=28)enemy.takeDamage((12 + (2*ExperienceBar.laserLevel))*2);
									else enemy.takeDamage((17 + (2*ExperienceBar.laserLevel))*2);
								}
								else {
									if(damage <= 20) enemy.takeDamage(6 + (2*ExperienceBar.laserLevel));
									else if(damage <=28)enemy.takeDamage(12 + (2*ExperienceBar.laserLevel));
									else enemy.takeDamage(17 + (2*ExperienceBar.laserLevel));
								}
							}
							if(enemy.getID() == ID.ElephantGuard) {
								damage = damageMaker.nextInt(30);
								if(lgABoost) {
									if(damage <= 22) enemy.takeDamage((4 + (2*ExperienceBar.laserLevel))*4);
									else if(damage <=28)enemy.takeDamage((12 + (2*ExperienceBar.laserLevel))*4);
									else enemy.takeDamage((17 + (2*ExperienceBar.laserLevel))*4);
								}
								else if(smABoost) {
									if(damage <= 22) enemy.takeDamage((4 + (2*ExperienceBar.laserLevel))*2);
									else if(damage <=28)enemy.takeDamage((12 + (2*ExperienceBar.laserLevel))*2);
									else enemy.takeDamage((17 + (2*ExperienceBar.laserLevel))*2);
								}
								else {
									if(damage <= 22) enemy.takeDamage(4 + (2*ExperienceBar.laserLevel));
									else if(damage <=28)enemy.takeDamage(12 + (2*ExperienceBar.laserLevel));
									else enemy.takeDamage(17 + (2*ExperienceBar.laserLevel));
								}
							}
							if(enemy.getID() == ID.Zatolib) {
								damage = damageMaker.nextInt(30);
								if(lgABoost) {
									if(damage <= 22) enemy.takeDamage((2 + (2*ExperienceBar.laserLevel))*4);
									else if(damage <=28)enemy.takeDamage((8 + (2*ExperienceBar.laserLevel))*4);
									else enemy.takeDamage((12 + (2*ExperienceBar.laserLevel))*4);
								}
								else if(smABoost) {
									if(damage <= 22) enemy.takeDamage((2 + (2*ExperienceBar.laserLevel))*2);
									else if(damage <=28)enemy.takeDamage((8 * + (2*ExperienceBar.laserLevel))*2);
									else enemy.takeDamage((12 + (2*ExperienceBar.laserLevel))*2);
								}
								else {
									if(damage <= 22) enemy.takeDamage(2 + (2*ExperienceBar.laserLevel));
									else if(damage <=28)enemy.takeDamage(8 + (2*ExperienceBar.laserLevel));
									else enemy.takeDamage(12 + (2*ExperienceBar.laserLevel));
								}
							}
							player.setVelX(-3);
							Povy.laserBulletX = (int)player.getX() + 67;
						}
					}	
				}
				if(player.getVelX() == -3) {
					laserHit++;
					if(laserHit == 20) {
						Battle.contact = false;
						laserHit++;
					}
					if(player.getX() <= originalX) {
						player.setVelX(0);
						if(lgABoost || smABoost) {
							attackCount += 1;
							if(attackCount == 3) {
								lgABoost = false;
								smABoost = false;
								attackCount = 0;
							}
						}
						battleState = BATTLESTATE.EnemyTurn;
					}
				}	
			}
			
			//ally attack
			if(menuPosition == 4) {
				if(setSpeed == 0) {
					ally.setVelX(3);
					setSpeed++;
				}
				if(!ally.getBounds().intersects(enemy.getBounds())) {
					ally.tick();
					enemy.tick();
				}
				else {
					//Game.clamp(player.getX(), 0, enemy.getX());
					if(ally.getVelX() == -3) {
						ally.tick();
						enemy.tick();
					}
					else {
						ally.setVelX(0);
					}	
				}
				if(ally.getVelX() == -3) {
					if(ally.getX() <= originalX - 160) {
						ally.setVelX(0);
						battleState = BATTLESTATE.EnemyTurn;
						ally.setX(360);
						if(lgABoost || smABoost) {
							attackCount += 1;
							if(attackCount == 3) {
								lgABoost = false;
								smABoost = false;
								attackCount = 0;
							}
						}
					}
				}
			}
			
			//run
			if(menuPosition == 3) {
				if(enemy.getID() == ID.Golem) runStat = runAway.nextInt(5);
				else if(enemy.getID() == ID.Rat) runStat = runAway.nextInt(3);
				else if(enemy.getID() == ID.ElephantGuard) runStat = runAway.nextInt(4);
				else if(enemy.getID() == ID.Zatolib) runStat = runAway.nextInt(4);
				else runStat = runAway.nextInt(4);
				
				if(runStat <= 1) {
					escaped = 1;
					AudioPlayer.getSound("noEscape").play(1, (float).1);
					if(lgABoost || smABoost) {
						attackCount += 1;
						if(attackCount == 3) {
							lgABoost = false;
							smABoost = false;
							attackCount = 0;
						}
					}
					battleState = BATTLESTATE.EnemyTurn;
				}
				else {
					escaped = 2;
					AudioPlayer.getSound("escaped").play(1, (float).1);
					battleState = BATTLESTATE.gotAway;
					lgABoost = false;
					smABoost = false;
					lgDBoost = false;
					smDBoost = false;
				}
			}
		}
		//enemy performs one of their attacks(chosen at random)
		if(battleState == BATTLESTATE.EnemyTurn) {
			if(setSpeedEnemy == 0) {
				enemy.setVelX(-3);
				setSpeedEnemy++;
			}
			if(!player.getBounds().intersects(enemy.getBounds())) {
				player.tick();
				enemy.tick();
			}
			else {
				if(enemy.getVelX() == 3) {
					player.tick();
					enemy.tick();
				}
				else {
					enemy.setVelX(0);
				}	
			}
			if(enemy.getVelX() == 3) {
				if(enemy.getX() >= enemyOriginalX) {
					enemy.setVelX(0);
					if(lgDBoost || smDBoost) {
						defenseCount += 1;
						if(defenseCount == 3) {
							lgDBoost = false;
							smDBoost = false;
							defenseCount = 0;
						}
					}
					if(HUD.HEALTH <= 0 && enemy.getID() == ID.Zatolib ) {
						Battle.battleState = Battle.BATTLESTATE.ZatolibWins;
						if(AudioPlayer.getMusic("dungeonFight").playing()) {
							AudioPlayer.getMusic("dungeonFight").stop();
							AudioPlayer.getSound("povyDies").play(1, (float).3);
						}
						return;
					}
					battleState = BATTLESTATE.PlayerTurnStart;
				}
			}
		}
		if(battleState == BATTLESTATE.PlayerTurnStart) {
			setSpeed = 0;
			setSpeedEnemy = 0;
			laserHit = 0;
			setLaser = false;
		}
	}
	
	/**
	 * 
	 * @param g
	 * renders the aspects of the battle
	 */
	public void render(Graphics g) {
		
		/**
		 * take damage off of the player during an enemy turn
		 */
		if(Battle.battleState == Battle.BATTLESTATE.EnemyTurn) {
			if(Battle.takeDamage == true) {
				if(enemy.getID() == ID.Golem) {
					damage = damageMaker.nextInt(5);
					if(smDBoost) {
						if(damage == 0 || damage == 1 || damage == 2 || damage == 3) HUD.HEALTH -= 0;
						else HUD.HEALTH -= 4;
					}
					else if(lgDBoost) {
						if(damage == 0 || damage == 1 || damage == 2 || damage == 3) HUD.HEALTH -= 0;
						else HUD.HEALTH -= 0;
					}
					else {
						if(damage == 0 || damage == 1 || damage == 2 || damage == 3) HUD.HEALTH -= 4;
						else HUD.HEALTH -= 8;
					}
				}
				if(enemy.getID() == ID.Rat) {
					if(smDBoost) {
						HUD.HEALTH -= 0;
					}
					else if(lgDBoost) {
						HUD.HEALTH -= 0;
					}
					else {
						HUD.HEALTH -= 4;
					}
				}
				if(enemy.getID() == ID.ElephantGuard) {
					ElephantGuard e = (ElephantGuard) enemy;
					int attack = e.getAttackDam();
					if(attack == 2) {
						damage = damageMaker.nextInt(5);
						if(smDBoost) {
							if(damage == 0) {
								HUD.HEALTH -= 0;
							}
							else if(damage == 1 || damage == 2 || damage == 3) HUD.HEALTH -= 4;
							else HUD.HEALTH -= 8;
						}
						else if(lgDBoost) {
							if(damage == 0) {
								HUD.HEALTH -= 0;
							}
							else if(damage == 1 || damage == 2 || damage == 3) HUD.HEALTH -= 0;
							else HUD.HEALTH -= 4;
						}
						else {
							if(damage == 0) {
								HUD.HEALTH -= 4;
							}
							else if(damage == 1 || damage == 2 || damage == 3) HUD.HEALTH -= 8;
							else HUD.HEALTH -= 12;
						}
					}
					else if(attack == 1) {
						damage = damageMaker.nextInt(5);
						if(smDBoost) {
							if(damage == 0 || damage == 1 || damage == 2) HUD.HEALTH -= 4;
							else HUD.HEALTH -= 8;
						}
						else if(lgDBoost) {
							if(damage == 0 || damage == 1 || damage == 2) HUD.HEALTH -= 0;
							else HUD.HEALTH -= 4;
						}
						else {
							if(damage == 0 || damage == 1 || damage == 2) HUD.HEALTH -= 8;
							else HUD.HEALTH -= 12;
						}
						
					}
					else {
						damage = damageMaker.nextInt(5);
						if(smDBoost) {
							if(damage == 0) {
								HUD.HEALTH -= 0;
							}
							else HUD.HEALTH -= 4;
						}
						else if(lgDBoost) {
							if(damage == 0) {
								HUD.HEALTH -= 0;
							}
							else HUD.HEALTH -= 0;
						}
						else {
							if(damage == 0) {
								HUD.HEALTH -= 4;
							}
							else HUD.HEALTH -= 8;
						}
					}
					
				}
				
				if(enemy.getID() == ID.Zatolib) {
					Zatolib z = (Zatolib) enemy;
					int attack = z.getAttackDam();
					if(attack == 2) {
						damage = damageMaker.nextInt(5);
						if(smDBoost) {
							if(damage == 0) {
								HUD.HEALTH -= 8;
							}
							else if(damage == 1 || damage == 2 || damage == 3) HUD.HEALTH -= 12;
							else HUD.HEALTH -= 16;
						}
						else if(lgDBoost) {
							if(damage == 0) {
								HUD.HEALTH -= 4;
							}
							else if(damage == 1 || damage == 2 || damage == 3) HUD.HEALTH -= 8;
							else HUD.HEALTH -= 12;
						}
						else {
							if(damage == 0 || damage == 1 || damage == 2 || damage == 3) HUD.HEALTH -= 12;
							else HUD.HEALTH -= 16;
						}
					}
					else if(attack == 1) {
						damage = damageMaker.nextInt(5);
						if(smDBoost) {
							if(damage == 0 || damage == 1 || damage == 2) HUD.HEALTH -= 8;
							else HUD.HEALTH -= 12;
						}
						else if(lgDBoost) {
							if(damage == 0 || damage == 1 || damage == 2) HUD.HEALTH -= 4;
							else HUD.HEALTH -= 8;
						}
						else {
							if(damage == 0 || damage == 1 || damage == 2) HUD.HEALTH -= 12;
							else HUD.HEALTH -= 16;
						}
						
					}
					else {
						damage = damageMaker.nextInt(5);
						if(smDBoost) {
							if(damage == 0) {
								HUD.HEALTH -= 4;
							}
							else HUD.HEALTH -= 8;
						}
						else if(lgDBoost) {
							if(damage == 0) {
								HUD.HEALTH -= 0;
							}
							else HUD.HEALTH -= 4;
						}
						else {
							if(damage == 0) {
								HUD.HEALTH -= 8;
							}
							else HUD.HEALTH -= 12;
						}
					}
					
				}
			}
		}
		if(Battle.battleState == Battle.BATTLESTATE.PlayerTurnAction) {
			if(Battle.takeDamage == true) {
				//pummel
				if(menuPosition == 5) {
					if(enemy.getID() == ID.Golem) {
						damage = damageMaker.nextInt(20);
						if(smABoost) {
							if(damage <= 12) enemy.takeDamage((2 + ExperienceBar.pummelLevel)*2);
							else if(damage <=18)enemy.takeDamage((3 + ExperienceBar.pummelLevel)*2);
							else enemy.takeDamage((4 + ExperienceBar.pummelLevel)*2);
						}
						else if(lgABoost) {
							if(damage <= 12) enemy.takeDamage((2 + ExperienceBar.pummelLevel)*3);
							else if(damage <=18)enemy.takeDamage((3 + ExperienceBar.pummelLevel)*3);
							else enemy.takeDamage((4 + ExperienceBar.pummelLevel)*3);
						}
						else {
							if(damage <= 12) enemy.takeDamage(2 + ExperienceBar.pummelLevel);
							else if(damage <=18)enemy.takeDamage(3 + ExperienceBar.pummelLevel);
							else enemy.takeDamage(4 + ExperienceBar.pummelLevel);
						}
						
					}
					if(enemy.getID() == ID.Rat) {
						damage = damageMaker.nextInt(20);
						if(smABoost) {
							if(damage <= 12) enemy.takeDamage((3 + ExperienceBar.pummelLevel)*2);
							else if(damage <=18)enemy.takeDamage((4 + ExperienceBar.pummelLevel)*2);
							else enemy.takeDamage((5+ ExperienceBar.pummelLevel)*2);
						}
						else if(lgABoost) {
							if(damage <= 12) enemy.takeDamage((3 + ExperienceBar.pummelLevel)*3);
							else if(damage <=18)enemy.takeDamage((4 + ExperienceBar.pummelLevel)*3);
							else enemy.takeDamage((5 + ExperienceBar.pummelLevel)*3);
						}
						else {
							if(damage <= 12) enemy.takeDamage(3 + ExperienceBar.pummelLevel);
							else if(damage <=18)enemy.takeDamage(4 + ExperienceBar.pummelLevel);
							else enemy.takeDamage(5 + ExperienceBar.pummelLevel);
						}
					}
					
					if(enemy.getID() == ID.ElephantGuard) {
						damage = damageMaker.nextInt(20);
						if(smABoost) {
							if(damage <= 12) enemy.takeDamage((2 + ExperienceBar.pummelLevel)*2);
							else enemy.takeDamage((3 + ExperienceBar.pummelLevel)*2);
						}
						else if(lgABoost) {
							if(damage <= 12) enemy.takeDamage((2 + ExperienceBar.pummelLevel)*3);
							else enemy.takeDamage((3 + ExperienceBar.pummelLevel)*3);
						}
						else {
							if(damage <= 12) enemy.takeDamage(2 + ExperienceBar.pummelLevel);
							else enemy.takeDamage(3 + ExperienceBar.pummelLevel);
						}
					}
					
					if(enemy.getID() == ID.Zatolib) {
						damage = damageMaker.nextInt(20);
						if(smABoost) {
							if(damage <= 12) enemy.takeDamage((1 + ExperienceBar.pummelLevel)*2);
							else enemy.takeDamage((2 + ExperienceBar.pummelLevel)*2);
						}
						else if(lgABoost) {
							if(damage <= 12) enemy.takeDamage((1 + ExperienceBar.pummelLevel)*3);
							else enemy.takeDamage((2 + ExperienceBar.pummelLevel)*3);
						}
						else {
							if(damage <= 12) enemy.takeDamage(1 + ExperienceBar.pummelLevel);
							else enemy.takeDamage(2 + ExperienceBar.pummelLevel);
						}
					}
					
				}
				//ally
				else if(menuPosition == 4) {
					if(ally.getID() == ID.Grogo) {
						if(enemy.getID() == ID.Golem) {
							damage = damageMaker.nextInt(20);
							if(damage <= 12) enemy.takeDamage(1);
							else if(damage <=19)enemy.takeDamage(2);
						}
						if(enemy.getID() == ID.Rat) {
							damage = damageMaker.nextInt(20);
							if(damage <= 10) enemy.takeDamage(2);
							else if(damage <=19)enemy.takeDamage(3);
						}
						if(enemy.getID() == ID.ElephantGuard) {
							damage = damageMaker.nextInt(20);
							if(damage <= 10) enemy.takeDamage(1);
							else if(damage <=19)enemy.takeDamage(2);
						}
						if(enemy.getID() == ID.Zatolib) {
							damage = damageMaker.nextInt(20);
							if(damage <= 17) enemy.takeDamage(1);
							else if(damage <=19)enemy.takeDamage(2);
						}
					}
				}
			}
		}
		
		/**
		 * draw the map and characters
		 */
		String[] cur;
		int x, y;
		int curID = 0;
		for(int i = 0; i < layers.size(); i++) {
			if(i == 1) {
				if(battleState == BATTLESTATE.PlayerTurnAction) {
					enemy.render(g);
					player.render(g);
					if(menuPosition == 4) {
						ally.render(g);
					}
				}
				else {
					player.render(g);
					enemy.render(g);
				}
				
				Game.hud.render(g);
			}
		
	       	cur = layers.get(i).split(",");
	        x = 0;
	     	y = 0;
	       	for(int j = 0; j < cur.length; j++) {
	       		curID = Integer.parseInt(cur[j].replace("\n", ""));
	       		if(curID != 0) {
	       			if(Game.animationDungeon.get("torch").contains(cur[j])) {
	       				g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("torch").get(Game.animationDungeonCounter.get("torch")))), x, y, null);
	       				torchCounter++;
	       				if(torchCounter == 48) {
	       					
	       					Game.animationDungeonCounter.put("torch", Game.animationDungeonCounter.get("torch") + 1);
	       					torchCounter = 0;
	       					if(Game.animationDungeonCounter.get("torch") == Game.animationDungeon.get("torch").size()) {
		       					
		       					Game.animationDungeonCounter.put("torch", new Integer(0));
		       				}
	       				}
	       				
	       			}
	       			
	       			else {
	       				
	       				g.drawImage(Game.dungeonTiles.get(curID), x, y, null);
	       			}
	       		}
	       		x += 32;
	       		if(x == 1280) {
	       			x = 0;
	       			y += 32;
	       		}
	       		
	       	 }
		}
		if(lgABoost) {
			g.drawImage(new Item(Item.ItemType.LargeAttackBoost).getImage(), (int)player.getX()-16, (int)player.getY()-32, null);
		}
		else if(smABoost) {
			g.drawImage(new Item(Item.ItemType.SmallAttackBoost).getImage(), (int)player.getX()-16, (int)player.getY()-32, null);
		}
		if(smDBoost) {
			g.drawImage(new Item(Item.ItemType.SmallDefenseBoost).getImage(), (int)player.getX()+16, (int)player.getY()-32, null);
		}
		else if(lgDBoost) {
			g.drawImage(new Item(Item.ItemType.LargeDefenseBoost).getImage(), (int)player.getX()+16, (int)player.getY()-32, null);
		}
		if(escaped == 1) {
			if(escapeCount <= 100) {
				g.setFont(new Font("Cooper Black",1,50));
				g.setColor(Color.red);
				g.drawString("Couldn't Escape!", Game.camX + 400, Game.camY + 600);
				escapeCount++;
			}
			else {
				escaped = 0;
				escapeCount = 0;
			}
		}
		else if(escaped == 2) {
			if(escapeCount <= 100) {
				g.setFont(new Font("Cooper Black",1,50));
				g.setColor(Color.green);
				g.drawString("Escaped!", Game.camX + 500,  Game.camY + 600);
				escapeCount++;
			}
			else {
				escaped = 0;
				escapeCount = 0;
			}
		}
       	if(battleState == BATTLESTATE.PlayerTurnStart) {
       		/**
       		 * draw the option screen to pick an attack
       		 */
       		Font fo = new Font("Bodoni MT", 1, 20);
			g.setColor(Color.WHITE);
			g.setFont(fo);
       		if(!itemSelected && !allySelected) {
	       		if(left) {
	       			//laser blaster
		       		if(menuPosition == 0) {
		       			g.drawImage(menuActions.get(menuChange+15), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3 && menuChange != -15) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
		       			}
		       			else if(menuChange == 3) {
		       				menuChange = -15;
		       			}
		       			else {
							g.drawImage(text, Game.camX + 300, Game.camY + 745, null);
							g.drawString("Hits opponent with a laser blast. Current Level: " + ExperienceBar.laserLevel, Game.camX + 420, Game.camY + 800);
		       			}
		       			
		       		}
		       		//items
		       		if(menuPosition == 1) {
		       			g.drawImage(menuActions.get(menuChange), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
		       			}
		       			else {
		       				g.drawImage(text, Game.camX + 300, Game.camY + 745, null);
							g.drawString("View Items", Game.camX + 590, Game.camY + 800);
		       			}
		       		}
		       		//special
		       		if(menuPosition == 2) {
		       			g.drawImage(menuActions.get(menuChange+3), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
		       			}
		       			else {
		       				g.drawImage(text, Game.camX + 300, Game.camY + 745, null);
							g.drawString("???", Game.camX + 620, Game.camY + 800);
		       			}
		       		}
		       		//run
		       		if(menuPosition == 3) {
		       			g.drawImage(menuActions.get(menuChange+6), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
		       			}
		       			else {
		       				g.drawImage(text, Game.camX + 300, Game.camY + 745, null);
							g.drawString("Attempt Escape from battle", Game.camX + 530, Game.camY + 800);
							if(canRun) {
								g.setColor(Color.green);
								g.drawString("Can attempt Escape", Game.camX + 545, Game.camY + 850);
							}
							else {
								g.setColor(Color.red);
								g.drawString("Cannot attempt Escape", Game.camX + 545, Game.camY + 850);
							}
		       			}
		       		}
		       		//ally
		       		if(menuPosition == 4) {
		       			g.drawImage(menuActions.get(menuChange+9), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
		       			}
		       			else {
		       				g.drawImage(text, Game.camX + 300, Game.camY + 745, null);
		       				String ready = "";
		       				Color c;
		       				if(Battle.useAlly) {
		       					c = Color.green;
		       					ready = "Ready!";
		       				}
		       				else {
		       					c = Color.red;
		       					ready = "Not Ready";
		       				}
							g.drawString("Summon an Ally", Game.camX + 565, Game.camY + 800);
							g.setColor(c);
							g.drawString(ready, Game.camX + 590, Game.camY + 850);
		       			}
		       		}
		       		//pummel
		       		if(menuPosition == 5) {
		       			g.drawImage(menuActions.get(menuChange + 12), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
		       			}
		       			else {
		       				g.drawImage(text, Game.camX + 300, Game.camY + 745, null);
							g.drawString("Pummel the opponent. Current Level: " + ExperienceBar.pummelLevel, Game.camX + 450, Game.camY + 800);
		       			}
		       		}
	       		}
	       		else {
	       			if(menuPosition == 0) {
		       			g.drawImage(menuActionsRight.get(menuChange+15), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3 && menuChange != -15) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
		       			}
		       			else if(menuChange == 1) {
		       				menuChange = -15;
		       			}
		       			else {
							g.drawImage(text, Game.camX + 300, Game.camY + 745, null);
							g.drawString("Hits opponent with a laser blast. Current Level: " + ExperienceBar.laserLevel, Game.camX + 420, Game.camY + 800);
		       			}
		       		}
		       		if(menuPosition == 1) {
		       			g.drawImage(menuActionsRight.get(menuChange+12), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
		       			}
		       			else {
		       				g.drawImage(text, Game.camX + 300, Game.camY + 745, null);
							g.drawString("View Items", Game.camX + 590, Game.camY + 800);
		       			}
		       		}
		       		if(menuPosition == 2) {
		       			g.drawImage(menuActionsRight.get(menuChange+9), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
		       			}
		       			else {
		       				g.drawImage(text, Game.camX + 300, Game.camY + 745, null);
							g.drawString("???", Game.camX + 620, Game.camY + 800);
		       			}
		       		}
		       		if(menuPosition == 3) {
		       			g.drawImage(menuActionsRight.get(menuChange+6), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
		       			}
		       			else {
		       				g.drawImage(text, Game.camX + 300, Game.camY + 745, null);
							g.drawString("Attempt Escape from battle", Game.camX + 530, Game.camY + 800);
							if(canRun) {
								g.setColor(Color.green);
								g.drawString("Can attempt Escape", Game.camX + 545, Game.camY + 850);
							}
							else {
								g.setColor(Color.red);
								g.drawString("Cannot attempt Escape", Game.camX + 545, Game.camY + 850);
							}
		       			}
		       		}
		       		if(menuPosition == 4) {
		       			g.drawImage(menuActionsRight.get(menuChange+3), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl % 10 == 0) {
		       					menuChange++;
		       				}
		       			}
		       			else {
		       				g.drawImage(text, Game.camX + 300, Game.camY + 745, null);
		       				String ready = "";
		       				Color c;
		       				if(Battle.useAlly) {
		       					c = Color.green;
		       					ready = "Ready!";
		       				}
		       				else {
		       					c = Color.red;
		       					ready = "Not Ready";
		       				}
							g.drawString("Summon an Ally", Game.camX + 565, Game.camY + 800);
							g.setColor(c);
							g.drawString(ready, Game.camX + 590, Game.camY + 850);
		       			}
		       		}
		       		if(menuPosition == 5) {
		       			g.drawImage(menuActionsRight.get(menuChange), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
		       			}
		       			else {
		       				g.drawImage(text, Game.camX + 300, Game.camY + 745, null);
							g.drawString("Pummel the opponent. Current Level: " + ExperienceBar.pummelLevel, Game.camX + 450, Game.camY + 800);
		       			}
		       		}
	       		}
       		}
       		else if(itemSelected){
       			Game.pause.render(g);
       			Game.itemPouch.render(g);
       		}
       		else {
       			Game.pause.render(g);
       		}
       	}
       	/**
       	 * once the battle begins, wait, and then display the battle
       	 */
       	if(waiter < 100) {
       		g.drawImage(transition.get(0), 0, 0, null);
       		waiter++;
       		return;
       	}
       	else {
	       	if(animationCount < 15) {
				
				g.drawImage(transition.get(animationCount), 0, 0, null);
				changeCount++;
				if(changeCount % 4 == 0) {
					animationCount++;
				}
				if(animationCount == 1 && changeCount % 4 == 0) {
					player.setX(2*180);
					player.setY(2*204);
					originalX = (int)player.getX();
					if(enemy.getID() == ID.Golem) {
						enemyX = enemy.getX();
						enemyY = enemy.getY();
						velX = enemy.getVelX();
						velY = enemy.getVelY();
						enemy.setX(2*400);
						enemy.setY(2*173);
						enemyOriginalX = (int)enemy.getX();
					}
					if(enemy.getID() == ID.Rat) {
						enemyX = enemy.getX();
						enemyY = enemy.getY();
						velX = enemy.getVelX();
						velY = enemy.getVelY();
						enemy.setX(2*410);
						enemy.setY(2*210);
						enemyOriginalX = (int)enemy.getX();
					}
					if(enemy.getID() == ID.ElephantGuard) {
						enemyX = enemy.getX();
						enemyY = enemy.getY();
						velX = enemy.getVelX();
						velY = enemy.getVelY();
						enemy.setX(2*410);
						enemy.setY(2*163);
						enemyOriginalX = (int)enemy.getX();
					}
					if(enemy.getID() == ID.Zatolib) {
						enemyX = enemy.getX();
						enemyY = enemy.getY();
						velX = enemy.getVelX();
						velY = enemy.getVelY();
						enemy.setX(2*400);
						enemy.setY(2*173);
						enemyOriginalX = (int)enemy.getX();
					}
				}
				return;
			}
			else if(animationCount == 15){
				AudioPlayer.getMusic("dungeonFight").loop(1, (float).1);
				animationCount++;
				battleState = BATTLESTATE.PlayerTurnStart;
				return;
			}
       	}
       	/**
       	 * once battle is over, go to the post battle screen
       	 */
       	if(Battle.battleState == Battle.BATTLESTATE.BackToGame) {
       		if(enemy.getID() == ID.Zatolib) {
       			deadCount++;
       			if(deadCount <= 250) {
       				return;
       			}
       		}
       		g.drawImage(transition.get(animationCount), 0, 0, null);
       		
			changeCount++;
			if(changeCount % 10 == 0) {
				animationCount++;
			}
			
			if(animationCount == 30) {
				if(enemy.getID() == ID.Zatolib) {
					Game.gameState = Game.STATE.Game;
					HUD.allyCount = 0;
					Battle.useAlly = false;
					Game.firstBattle = false;
					Battle.contact = false;
					HUD.HEALTH = HUD.maxHealth;
					if(enemy.getID() == ID.Zatolib) {
						for(int j = 0; j < handler.objects.size(); j++) {
							if(handler.objects.get(j).getID() == ID.Povy) {
								handler.objects.get(j).setX(1520*2);
								handler.objects.get(j).setY(1058*2);
							}
							
							if(handler.objects.get(j).getID() == ID.Zatolib) {
								handler.objects.get(j).setX(1550*2);
								handler.objects.get(j).setY(1058*2);
							}
							handler.addObject(new Grogo(1460*2, 1058*2, ID.Grogo, handler));
						}
					}
					return;
				}
				animationCount = 0;
				changeCount = 0;
				if(AudioPlayer.getSound("winBattle").playing()) {
					AudioPlayer.getSound("winBattle").stop();
					AudioPlayer.getMusic("afterBattle").loop(1, (float).1);
				}
				else{
					AudioPlayer.getMusic("afterBattle").loop(1, (float).1);
				}
				if(enemy.getID() == ID.Golem) {
					Golem temp = (Golem)enemy;
					if(temp.type() == Golem.GolemType.firstGolem) {
						for(int j = 0; j < handler.objects.size(); j++) {
							if(handler.objects.get(j).getID() == ID.Povy) {
								handler.objects.get(j).setX(115*2);
								handler.objects.get(j).setY(183*2);
							}
							
							if(handler.objects.get(j).getID() == ID.Grogo) {
								handler.objects.get(j).setX(115*2);
								handler.objects.get(j).setY(110*2);
							}
						
						}
					}
				}
				handler.removeObject(enemy);
				HUD.allyCount = 0;
				Battle.useAlly = false;
				Game.gameState = Game.STATE.PostBattle;
				if(enemy.getID() == ID.ElephantGuard) {
					for(int i = 0; i < handler.objects.size(); i++) {
						if(handler.objects.get(i) instanceof Gate) {
							Gate temp = (Gate)handler.objects.get(i);
							if(temp.gateNum() == 3) {
								temp.hasKey();
								temp.open();
							}
						}
					}
				}
				
				
			}
       	}
       	/**
       	 * if an escape attempt is successful
       	 */
    	if(Battle.battleState == Battle.BATTLESTATE.gotAway) {
       		g.drawImage(transition.get(animationCount), 0, 0, null);
       		
			changeCount++;
			if(changeCount % 10 == 0) {
				animationCount++;
			}
			
			if(animationCount == 30) {
				animationCount = 0;
				changeCount = 0;
				for(int i = 0; i < handler.objects.size(); i++) {
					if(handler.objects.get(i).getID() == ID.Povy) {
						Povy p = (Povy)handler.objects.get(i);
						p.isInvincible();
					}
				}
				enemy.setX((int)enemyX);
				enemy.setY((int)enemyY);
				enemy.setVelX((int)velX);
				enemy.setVelY((int)velY);
				escaped = 0;
				HUD.allyCount = 0;
				Battle.useAlly = false;
				Game.gameState = Game.STATE.Game;
				
				
			}
       	}
       	
       	
	    
	}
	/**
	 * 
	 * @return true if Povy can attempt to escape the battle,
	 * false otherwise
	 */
	public boolean possRun() {
		if(canRun) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * set the ally to whichever ally was selected for an 
	 * ally attack
	 * @param g
	 */
	public void setAlly(GameObject g) {
   		ally = g;
   	}
	
	

}
