import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

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
	private GameObject enemy;
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
	private float enemyX, enemyY, velX, velY;
	public static boolean left = true, itemSelected = false, allySelected = false;
	private boolean setLaser = false;
	public static boolean contact = false;
	public static int expToBeAdded; 
	private int speedControl = 0, setSpeed = 0, setSpeedEnemy = 0, originalX, enemyOriginalX;

	public enum BATTLESTATE{
		PlayerTurnStart,
		PlayerTurnAction,
		EnemyTurn,
		BattleEnd,
		gotAway,
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
		this.enemy = enemy;
		damageMaker = new Random();
		runAway = new Random();
		expToBeAdded = enemy.getMaxHealth();
		animationCount = 0;
		if(enemy.getID() == ID.Golem) {
			Golem g = (Golem)enemy;
			if(g.getgID() == Golem.GolemType.firstGolem) {
				canRun = false;
			}
			else {
				canRun = true;
			}
		}
		else if(enemy.getID() == ID.Rat){
			canRun = true;
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
			File theMap = new File("Povy/res/Dungeon/rpg-dungeon-pack v1.1(wonderdot)/Battle.xml");
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
								if(damage <= 20) enemy.takeDamage(4);
								else if(damage <=28)enemy.takeDamage(10);
								else enemy.takeDamage(15);
							}
							if(enemy.getID() == ID.Rat) {
								damage = damageMaker.nextInt(30);
								if(damage <= 20) enemy.takeDamage(6);
								else if(damage <=28)enemy.takeDamage(12);
								else enemy.takeDamage(17);
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
					}
				}
			}
			
			//run
			if(menuPosition == 3) {
				if(enemy.getID() == ID.Golem) runStat = runAway.nextInt(5);
				else if(enemy.getID() == ID.Rat) runStat = runAway.nextInt(3);
				else runStat = runAway.nextInt(4);
				
				if(runStat <= 1) {
					escaped = 1;
					AudioPlayer.getSound("noEscape").play(1, (float).1);
					battleState = BATTLESTATE.EnemyTurn;
				}
				else {
					escaped = 2;
					AudioPlayer.getSound("escaped").play(1, (float).1);
					battleState = BATTLESTATE.gotAway;
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
					if(damage == 0 || damage == 1 || damage == 2 || damage == 3) HUD.HEALTH -= 4;
					else HUD.HEALTH -= 8;
				}
				if(enemy.getID() == ID.Rat) {
					damage = damageMaker.nextInt(5);
					if(damage == 0 || damage == 1 || damage == 2 || damage == 3) HUD.HEALTH -= 4;
					else HUD.HEALTH -= 4;
				}
			}
		}
		if(Battle.battleState == Battle.BATTLESTATE.PlayerTurnAction) {
			if(Battle.takeDamage == true) {
				//pummel
				if(menuPosition == 5) {
					if(enemy.getID() == ID.Golem) {
						damage = damageMaker.nextInt(20);
						if(damage <= 12) enemy.takeDamage(2);
						else if(damage <=18)enemy.takeDamage(3);
						else enemy.takeDamage(4);
					}
					if(enemy.getID() == ID.Rat) {
						damage = damageMaker.nextInt(20);
						if(damage <= 12) enemy.takeDamage(3);
						else if(damage <=18)enemy.takeDamage(4);
						else enemy.takeDamage(5);
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
	       		String stringCur = cur[j].replace("\n", "");
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
       		if(!itemSelected && !allySelected) {
	       		if(left) {
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
		       		}
		       		if(menuPosition == 1) {
		       			g.drawImage(menuActions.get(menuChange), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
		       			}
		       		}
		       		if(menuPosition == 2) {
		       			g.drawImage(menuActions.get(menuChange+3), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
		       			}
		       		}
		       		if(menuPosition == 3) {
		       			g.drawImage(menuActions.get(menuChange+6), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
		       			}
		       		}
		       		if(menuPosition == 4) {
		       			g.drawImage(menuActions.get(menuChange+9), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
		       			}
		       		}
		       		if(menuPosition == 5) {
		       			g.drawImage(menuActions.get(menuChange + 12), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
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
		       		}
		       		if(menuPosition == 1) {
		       			g.drawImage(menuActionsRight.get(menuChange+12), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
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
		       		}
		       		if(menuPosition == 3) {
		       			g.drawImage(menuActionsRight.get(menuChange+6), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
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
		       		}
		       		if(menuPosition == 5) {
		       			g.drawImage(menuActionsRight.get(menuChange), 50, 150, null);
		       			speedControl++;
		       			if(menuChange != 3) {
		       				if(speedControl %10 == 0) {
		       					menuChange++;
		       				}
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
       		g.drawImage(transition.get(animationCount), 0, 0, null);
       		
			changeCount++;
			if(changeCount % 10 == 0) {
				animationCount++;
			}
			
			if(animationCount == 30) {
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