import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
/**
 * The Pause Screen is the pause menu, triggered by the user pressing SPACE while in the game state. In this menu,
 * the user can view items held, allies in their ally pouch, current levels in all skills and how close they are to upgardes
 * @author clarkt5
 *
 */
public class PauseScreen extends MouseAdapter implements MouseMotionListener{
	
	//regular pause screen
	private boolean none = true, option1 = false, option2 = false, option3 = false, option4 = false;
	//itemScreen
	private boolean backToRegFromItem = false;
	private boolean overItem = false;
	//use item? screen
	private boolean overYes = false, overNo = false;
	//post battle screen
	private boolean overNext = false;
	private boolean overHealth = false, overAlly = false, overPummel = false, overLaser = false;
	//if item was selected as attack
	private boolean itemSelect = false;
	//progress pause screen
	private boolean goBackFromProg = false;
	
	//ally pause screen
	private boolean goBackFromAlly = false;
	private boolean overAnAlly = false;
	private int xValAlly, yValAlly, xValForDrawing;
	
	private BufferedImage text;
	private BufferedImage allyCover;
	private int overItemY = 0;
	public static int yItem = 198;
	private ArrayList<BufferedImage> pauseMenu;
	private ArrayList<BufferedImage> itemMenu;
	private ArrayList<BufferedImage> useItem;
	private ArrayList<BufferedImage> expMenuBattle;
	private ArrayList<BufferedImage> expMenuPause;
	
	private ArrayList<BufferedImage> healthIcon;
	private ArrayList<BufferedImage> allyIcon;
	private ArrayList<BufferedImage> pummelIcon;
	private ArrayList<BufferedImage> laserIcon;
	private ArrayList<BufferedImage> allyMenu;
	
	private BufferedImage itemCover;
	public static PauseState pauseState = PauseState.Regular;
	
	/**
	 * makes a new pause screen, loads in all images
	 */
	public PauseScreen() {
		text = null;
		allyCover = null;
		try {
	        text = ImageIO.read(new File("res/textbox.png"));
	        allyCover = ImageIO.read(new File("res/allyCover.png"));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		itemCover = ss.grabImage(1, 1, 320, 32, "itemCover");
		
		pauseMenu = new ArrayList<BufferedImage>();
		pauseMenu.add(ss.grabImage(1, 1, 1280, 960, "pauseMenu"));
		pauseMenu.add(ss.grabImage(1, 2, 1280, 960, "pauseMenu"));
		pauseMenu.add(ss.grabImage(2, 1, 1280, 960, "pauseMenu"));
		pauseMenu.add(ss.grabImage(2, 2, 1280, 960, "pauseMenu"));
		pauseMenu.add(ss.grabImage(3, 1, 1280, 960, "pauseMenu"));
		
		itemMenu = new ArrayList<BufferedImage>();
		itemMenu.add(ss.grabImage(1, 1, 1280, 960, "itemMenu"));
		itemMenu.add(ss.grabImage(1, 2, 1280, 960, "itemMenu"));
		
		useItem = new ArrayList<BufferedImage>();
		useItem.add(ss.grabImage(1, 1, 1280, 960, "itemOption"));
		useItem.add(ss.grabImage(1, 2, 1280, 960, "itemOption"));
		useItem.add(ss.grabImage(1, 3, 1280, 960, "itemOption"));
		
		expMenuBattle = new ArrayList<BufferedImage>();
		expMenuPause = new ArrayList<BufferedImage>();
		
		expMenuBattle.add(ss.grabImage(1, 1, 1280, 960, "progressMenu"));
		expMenuBattle.add(ss.grabImage(2, 1, 1280, 960, "progressMenu"));
		
		expMenuPause.add(ss.grabImage(1, 2, 1280, 960, "progressMenu"));
		expMenuPause.add(ss.grabImage(2, 2, 1280, 960, "progressMenu"));
		
		allyMenu = new ArrayList<BufferedImage>();
		allyMenu.add(ss.grabImage(1, 1, 1280, 960, "allyMenu"));
		allyMenu.add(ss.grabImage(1, 2, 1280, 960, "allyMenu"));
		
		healthIcon = new ArrayList<BufferedImage>();
		allyIcon = new ArrayList<BufferedImage>();
		pummelIcon = new ArrayList<BufferedImage>();
		laserIcon = new ArrayList<BufferedImage>();
		
		healthIcon.add(ss.grabImage(1, 1, 64, 45, "healthIcon"));
		healthIcon.add(ss.grabImage(1, 2, 64, 45, "healthIcon"));
		
		allyIcon.add(ss.grabImage(1, 1, 64, 45, "allyIcon"));
		allyIcon.add(ss.grabImage(1, 2, 64, 45, "allyIcon"));
		
		pummelIcon.add(ss.grabImage(1, 1, 64, 45, "pummelIcon"));
		pummelIcon.add(ss.grabImage(1, 2, 64, 45, "pummelIcon"));
		
		laserIcon.add(ss.grabImage(1, 1, 64, 45, "laserIcon"));
		laserIcon.add(ss.grabImage(1, 2, 64, 45, "laserIcon"));
	}
	
	/**
	 * various states of the pause screen
	 * @author clarkt5
	 *
	 */
	public enum PauseState{
		Regular,
		ItemScreen,
		ItemUse,
		AllyScreen,
		ProgressScreen;
	};
	
	
	/**
	 * 
	 * @param mx
	 * @param my
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return if mouse is over a specific boundary, return true. otherwise, return false
	 */
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx >= x && mx <= x + width) {
			if(my >= y && my <= y + height) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * checks where the mouse is on the screen and signals to the render method
	 * what images should be drawn based on that location
	 */
	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if(Game.gameState == Game.STATE.Paused) {
			if(pauseState == PauseState.Regular) {
				//player progress
				if(mouseOver(mx, my, 512, 261, 278, 64)) {
					option1 = true;
				}
				else {
					option1 = false;
				}
				
				//allies
				if(mouseOver(mx, my, 512, 348, 278, 64)) {
					option2 = true;
				}
				else {
					option2 = false;
				}
				
				//items
				if(mouseOver(mx, my, 512, 438, 278, 64)) {
					option3 = true;
				}
				else {
					option3 = false;
				}
				
				//x out
				if(mouseOver(mx, my, 820, 88, 33, 34)) {
					option4 = true;
				}
				else {
					option4 = false;
				}
				
				if(!option1 && !option2 && !option3 && !option4) {
					none = true;
				}
				else {
					none = false;
				}
			}
			if(pauseState == PauseState.ItemScreen) {
				if(mouseOver(mx, my, 430, 78, 36, 36)) {
					backToRegFromItem = true;
				}
				else {
					backToRegFromItem = false;
				}
				int yItemP = 198;
				//System.out.println(Game.itemPouch.getItemAmnt());
				for(int i = 0; i < Game.itemPouch.getItemAmnt(); i++) {
					if(mouseOver(mx, my, 491, yItemP, 320, 32)) {
						overItem = true;
						overItemY = yItemP;
						break;
					}
					else {
						overItem = false;
					}
					yItemP += 34;
				}
			}
			if(pauseState == PauseState.ItemUse) {
				if(mouseOver(mx, my, 516, 496, 114, 38)) {
					overYes = true;
				}
				else {
					overYes = false;
				}
				
				if(mouseOver(mx, my, 650, 496, 114, 38)) {
					overNo = true;
				}
				else {
					overNo = false;
				}
			}
			if(pauseState == PauseState.ProgressScreen) {
				if(mouseOver(mx, my, 53, 82, 36, 36)) {
					goBackFromProg = true;
				}
				else {
					goBackFromProg = false;
				}
				if(mouseOver(mx, my, 252, 499, 64, 45)) {
					overHealth = true;
				}
				else {
					overHealth = false;
				}
				if(mouseOver(mx, my, 252, 549, 64, 45)) {
					overAlly = true;
				}
				else {
					overAlly = false;
				}
				if(mouseOver(mx, my, 252, 599, 64, 45)) {
					overPummel = true;
				}
				else {
					overPummel = false;
				}
				if(mouseOver(mx, my, 252, 649, 64, 45)) {
					overLaser = true;
				}
				else {
					overLaser = false;
				}
			}
			if(pauseState == PauseState.AllyScreen) {
				if(mouseOver(mx, my, 53, 82, 36, 36)) {
					goBackFromAlly = true;
				}
				else {
					goBackFromAlly = false;
				}
				int xAlly = 182;
				for(int i = 0; i < Game.allies.allies.size(); i++) {
					if(mouseOver(mx, my, xAlly, 300, 208, 208)) {
						overAnAlly = true;
						xValForDrawing = xAlly;
						break;
					}
					else {
						overAnAlly = false;
					}
					xAlly += 232;
				}
			}
		}
		else if(Game.gameState == Game.STATE.Battle) {
			if(Battle.itemSelected) {
				if(itemSelect == false) {
					if(mouseOver(mx, my, 430, 78, 36, 36)) {
						backToRegFromItem = true;
					}
					else {
						backToRegFromItem = false;
					}
					int yItemP = 198;
					//System.out.println(Game.itemPouch.getItemAmnt());
					for(int i = 0; i < Game.itemPouch.getItemAmnt(); i++) {
						if(mouseOver(mx, my, 491, yItemP, 320, 32)) {
							overItem = true;
							overItemY = yItemP;
							break;
						}
						else {
							overItem = false;
						}
						yItemP += 34;
					}
				}
				else {
					if(mouseOver(mx, my, 516, 496, 114, 38)) {
						overYes = true;
					}
					else {
						overYes = false;
					}
					
					if(mouseOver(mx, my, 650, 496, 114, 38)) {
						overNo = true;
					}
					else {
						overNo = false;
					}
				}
			}
			else if(Battle.allySelected) {
				if(mouseOver(mx, my, 53, 82, 36, 36)) {
					goBackFromAlly = true;
				}
				else {
					goBackFromAlly = false;
				}
				int xAlly = 182;
				//System.out.println(Game.itemPouch.getItemAmnt());
				for(int i = 0; i < Game.allies.allies.size(); i++) {
					if(mouseOver(mx, my, xAlly, 300, 208, 208)) {
						overAnAlly = true;
						xValForDrawing = xAlly;
						break;
					}
					else {
						overAnAlly = false;
					}
					xAlly += 232;
				}
			}
			
		}
		else if(Game.gameState == Game.STATE.PostBattle) {
			if(ExperienceBar.levelUp != true && Battle.expToBeAdded == 0) {
				if(mouseOver(mx, my, 483, 716, 278, 64)) {
					overNext = true;
				}
				else {
					overNext = false;
				}
			}
			if(mouseOver(mx, my, 252, 499, 64, 45)) {
				overHealth = true;
			}
			else {
				overHealth = false;
			}
			if(mouseOver(mx, my, 252, 549, 64, 45)) {
				overAlly = true;
			}
			else {
				overAlly = false;
			}
			if(mouseOver(mx, my, 252, 599, 64, 45)) {
				overPummel = true;
			}
			else {
				overPummel = false;
			}
			if(mouseOver(mx, my, 252, 649, 64, 45)) {
				overLaser = true;
			}
			else {
				overLaser = false;
			}
		}
		
	}
	
	
	/**
	 * based on the location, change the state of the menu
	 */
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if(Game.gameState == Game.STATE.Paused) {
			if(pauseState == PauseState.Regular) {
				//player progress
				if(mouseOver(mx, my, 512, 261, 278, 64)) {
					AudioPlayer.getSound("click").play(1, (float).1);
					pauseState = PauseState.ProgressScreen;
				}
				
				
				//allies
				if(mouseOver(mx, my, 512, 348, 278, 64)) {
					AudioPlayer.getSound("click").play(1, (float).1);
					pauseState = PauseState.AllyScreen;
				}
				
				
				//items
				if(mouseOver(mx, my, 512, 438, 278, 64)) {
					pauseState = PauseState.ItemScreen;
					AudioPlayer.getSound("click").play(1, (float).1);
					
				}
				
				
				//x out
				if(mouseOver(mx, my, 820, 88, 33, 34)) {
					Game.gameState = Game.STATE.Game;
					AudioPlayer.getSound("click").play(1, (float).1);
				}
			}
			if(pauseState == PauseState.ItemScreen) {
				if(mouseOver(mx, my, 430, 78, 36, 36)) {
					pauseState = PauseState.Regular;
					AudioPlayer.getSound("click").play(1, (float).1);
				}
				yItem = 198;
				for(int i = 0; i < Game.itemPouch.getItemAmnt(); i++) {
					if(mouseOver(mx, my, 491, yItem, 320, 32)) {
						pauseState = PauseState.ItemUse;
						overItem = false;
						AudioPlayer.getSound("click").play(1, (float).1);
						break;
					}
					yItem += 34;
				}
			}
			if(pauseState == PauseState.ItemUse) {
				if(mouseOver(mx, my, 516, 496, 114, 38)) {
					Game.gameState = Game.STATE.Game;
					Povy.takingPotion = true;
					pauseState = PauseState.Regular;
					AudioPlayer.getSound("click").play(1, (float).1);
					overYes = false;
				}
				else if(mouseOver(mx, my, 650, 496, 114, 38)) {
					overNo = false;
					pauseState = PauseState.ItemScreen;
					AudioPlayer.getSound("click").play(1, (float).1);
				}
			}
			if(pauseState == PauseState.ProgressScreen) {
				if(mouseOver(mx, my, 53, 82, 36, 36)) {
					pauseState = PauseState.Regular;
				}
			}
			if(pauseState == PauseState.AllyScreen) {
				if(mouseOver(mx, my, 53, 82, 36, 36)) {
					pauseState = PauseState.Regular;
				}
			}
		}
		else if(Game.gameState == Game.STATE.Battle) {
			if(Battle.itemSelected) {
				if(itemSelect == false) {
					if(mouseOver(mx, my, 430, 78, 36, 36)) {
						Battle.itemSelected = false;
						AudioPlayer.getSound("click").play(1, (float).1);
					}
					yItem = 198;
					for(int i = 0; i < Game.itemPouch.getItemAmnt(); i++) {
						if(mouseOver(mx, my, 491, yItem, 320, 32)) {
							itemSelect = true;
							overItem = false;
							AudioPlayer.getSound("click").play(1, (float).1);
							break;
						}
						yItem += 34;
					}
					return;
				}
				else {
					if(mouseOver(mx, my, 516, 496, 114, 38)) {
						Battle.battleState = Battle.BATTLESTATE.PlayerTurnAction;
						Battle.itemSelected = false;
						itemSelect = false;
						overYes = false;
						AudioPlayer.getSound("click").play(1, (float).1);
					}
					else if(mouseOver(mx, my, 650, 496, 114, 38)) {
						overNo = false;
						itemSelect = false;
						AudioPlayer.getSound("click").play(1, (float).1);
					}
				}
			}
			else if(Battle.allySelected) {
				if(mouseOver(mx, my, 53, 82, 36, 36)) {
					Battle.allySelected = false;
					AudioPlayer.getSound("click").play(1, (float).1);
				}
				//System.out.println(Game.itemPouch.getItemAmnt());
				xValAlly = 182;
				for(int i = 0; i < Game.allies.allies.size(); i++) {
					if(mouseOver(mx, my, xValAlly, 300, 208, 208)) {
						Battle.battleState = Battle.BATTLESTATE.PlayerTurnAction;
						Battle.allySelected = false;
						Battle.useAlly = false;
						HUD.allyCount = 0;
						overAnAlly = false;
						break;
					}
					xValAlly += 232;
				}
				int tempX = 182;
				Iterator<GameObject> itr = Game.allies.allies.iterator();
				while(itr.hasNext()) {
					GameObject cur = itr.next();
					if(tempX == xValAlly) {
						Game.battle.setAlly(cur);
					}
					tempX += 232;
				}
				
				return;
			}
		}
		else if(Game.gameState == Game.STATE.PostBattle) {
			if(ExperienceBar.levelUp != true && Battle.expToBeAdded == 0) {
				if(mouseOver(mx, my, 483, 716, 278, 64)) {
					Game.gameState = Game.STATE.Game;
					AudioPlayer.getSound("click").play(1, (float).1);
				}
			}
			if(ExperienceBar.levelUp == true) {
				if(mouseOver(mx, my, 252, 499, 64, 45)) {
					if(ExperienceBar.healthLevel != 10) {
						ExperienceBar.healthLevel += 1;
						ExperienceBar.levelUp = false;
						AudioPlayer.getSound("upgradeDone").play(1, (float).1);
						HUD.maxHealth += 16;
						HUD.HEALTH = HUD.maxHealth;
					}
				}
				else if(mouseOver(mx, my, 252, 549, 64, 45)) {
					if(ExperienceBar.allyLevel != 10) {
						ExperienceBar.allyLevel += 1;
						ExperienceBar.levelUp = false;
						AudioPlayer.getSound("upgradeDone").play(1, (float).1);
						HUD.HEALTH = HUD.maxHealth;
					}
				}
				else if(mouseOver(mx, my, 252, 599, 64, 45)) {
					if(ExperienceBar.pummelLevel != 10) {
						ExperienceBar.pummelLevel += 1;
						ExperienceBar.levelUp = false;
						AudioPlayer.getSound("upgradeDone").play(1, (float).1);
						HUD.HEALTH = HUD.maxHealth;
					}
				}
				else if(mouseOver(mx, my, 252, 649, 64, 45)) {
					if(ExperienceBar.laserLevel != 10) {
						ExperienceBar.laserLevel += 1;
						ExperienceBar.levelUp = false;
						AudioPlayer.getSound("upgradeDone").play(1, (float).1);
						HUD.HEALTH = HUD.maxHealth;
					}
				}
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void tick() {
		
	}
	/**
	 * draw images to the screen based on the state of the game and location of mouse
	 * @param g
	 */
	public void render(Graphics g) {
		if(Game.gameState == Game.STATE.Battle) {
			if(Battle.itemSelected) {
				if(itemSelect == false) {
					if(backToRegFromItem == true) {
						g.drawImage(itemMenu.get(1), 0, 0, null);
						Game.itemPouch.render(g);
					}
					else {
						g.drawImage(itemMenu.get(0), 0, 0, null);
						Game.itemPouch.render(g);
					}
					
					if(overItem == true) {
						g.drawImage(itemCover, 0+491, 0 + overItemY, null);
					}
				}
				else {
					g.drawImage(itemMenu.get(0), 0, 0, null);
					Game.itemPouch.render(g);
					if(overYes) g.drawImage(useItem.get(1), 0, 0, null);
					else if(overNo)  g.drawImage(useItem.get(2), 0, 0, null);
					else  g.drawImage(useItem.get(0), 0, 0, null);
				}
				return;
			}
			else if(Battle.allySelected) {
				if(goBackFromAlly == true) {
					g.drawImage(allyMenu.get(1), Game.camX, Game.camY, null);
					Game.allies.render(g);
				}
				else {
					g.drawImage(allyMenu.get(0), Game.camX, Game.camY, null);
					Game.allies.render(g);
				}
				if(overAnAlly) {
					g.drawImage(allyCover, Game.camX + xValForDrawing, Game.camY + 300, null);
				}
			}
		}
		else if(Game.gameState == Game.STATE.PostBattle) {
			if(ExperienceBar.levelUp != true && Battle.expToBeAdded == 0) {
				if(overNext) {
					g.drawImage(expMenuBattle.get(1), Game.camX, Game.camY, null);
				}
				else {
					g.drawImage(expMenuBattle.get(0), Game.camX, Game.camY, null);
				}
			}
			else if(Battle.expToBeAdded == 0){
				g.drawImage(expMenuBattle.get(0), Game.camX, Game.camY, null);
			}
			else {
				g.drawImage(expMenuBattle.get(0), Game.camX, Game.camY, null);
			}
			if(!ExperienceBar.levelUp) {
				if(overHealth)g.drawImage(healthIcon.get(1), Game.camX + 252, Game.camY + 499, null);
				else g.drawImage(healthIcon.get(0), Game.camX + 252, Game.camY + 499, null);
				
				if(overAlly)g.drawImage(allyIcon.get(1), Game.camX + 252, Game.camY + 549, null);
				else g.drawImage(allyIcon.get(0), Game.camX + 252, Game.camY + 549, null);
				
				if(overPummel)g.drawImage(pummelIcon.get(1), Game.camX + 252, Game.camY + 599, null);
				else g.drawImage(pummelIcon.get(0), Game.camX + 252, Game.camY + 599, null);
				
				if(overLaser)g.drawImage(laserIcon.get(1), Game.camX + 252, Game.camY + 649, null);
				else g.drawImage(laserIcon.get(0), Game.camX + 252, Game.camY + 649, null);
			}
			else {
				g.setFont(new Font("Cooper Black",1,30));
				g.setColor(Color.GREEN);
				g.drawString("Upgrade Available!", Game.camX + 490, Game.camY + 420);
				g.drawString("Select an area to upgrade by selecting an icon:", Game.camX + 252, Game.camY + 460);
				
				if(overHealth)g.drawImage(healthIcon.get(1), Game.camX + 252, Game.camY + 499, null);
				else g.drawImage(healthIcon.get(0), Game.camX + 252, Game.camY + 499, null);
				
				if(overAlly)g.drawImage(allyIcon.get(1), Game.camX + 252, Game.camY + 549, null);
				else g.drawImage(allyIcon.get(0), Game.camX + 252, Game.camY + 549, null);
				
				if(overPummel)g.drawImage(pummelIcon.get(1), Game.camX + 252, Game.camY + 599, null);
				else g.drawImage(pummelIcon.get(0), Game.camX + 252, Game.camY + 599, null);
				
				if(overLaser)g.drawImage(laserIcon.get(1), Game.camX + 252, Game.camY + 649, null);
				else g.drawImage(laserIcon.get(0), Game.camX + 252, Game.camY + 649, null);
			}
			
			return;
		}
		else if(Game.gameState == Game.STATE.Paused) {
			if(pauseState == PauseState.Regular) {
				if(none == true) {
					g.drawImage(pauseMenu.get(0), Game.camX, Game.camY, null);
				}
				else if(option1 == true) {
					g.drawImage(pauseMenu.get(1), Game.camX, Game.camY, null);
				}
				else if(option2 == true) {
					g.drawImage(pauseMenu.get(2), Game.camX, Game.camY, null);
				}
				else if(option3 == true){
					g.drawImage(pauseMenu.get(3), Game.camX, Game.camY, null);
				}
				else if(option4 == true){
					g.drawImage(pauseMenu.get(4), Game.camX, Game.camY, null);
				}
			}
			if(pauseState == PauseState.ItemScreen) {
				if(backToRegFromItem == true) {
					g.drawImage(itemMenu.get(1), Game.camX, Game.camY, null);
					Game.itemPouch.render(g);
				}
				else {
					g.drawImage(itemMenu.get(0), Game.camX, Game.camY, null);
					Game.itemPouch.render(g);
				}
				
				if(overItem == true) {
					g.drawImage(itemCover, Game.camX+491, Game.camY + overItemY, null);
				}
			}
			if(pauseState == PauseState.ItemUse) {
				g.drawImage(itemMenu.get(0), Game.camX, Game.camY, null);
				Game.itemPouch.render(g);
				if(overYes) g.drawImage(useItem.get(1), Game.camX, Game.camY, null);
				else if(overNo)  g.drawImage(useItem.get(2), Game.camX, Game.camY, null);
				else  g.drawImage(useItem.get(0), Game.camX, Game.camY, null);
			}
			if(pauseState == PauseState.ProgressScreen) {
				Font fo = new Font("System Bold", 1, 15);
				g.setColor(Color.WHITE);
				g.setFont(fo);
				if(goBackFromProg) g.drawImage(expMenuPause.get(1), Game.camX, Game.camY, null);
				else  g.drawImage(expMenuPause.get(0), Game.camX, Game.camY, null);
				
				if(overHealth) {
					g.drawImage(healthIcon.get(1), Game.camX + 252, Game.camY + 499, null);
					g.drawImage(text, Game.camX + 300, Game.camY + 745, null);
					g.drawString("Status of Povy's health. Current level: " + ExperienceBar.healthLevel + " (" + HUD.maxHealth + " max health)", Game.camX + 330, Game.camY + 800);
				}
				else {
					g.drawImage(healthIcon.get(0), Game.camX + 252, Game.camY + 499, null);
				}
				
				if(overAlly) {
					g.drawImage(allyIcon.get(1), Game.camX + 252, Game.camY + 549, null);
					g.drawImage(text, Game.camX + 300, Game.camY + 745, null);
					g.drawString("The effectiveness of Povy's allies. Current level: " + ExperienceBar.allyLevel, Game.camX + 330, Game.camY + 800);
				}
				else g.drawImage(allyIcon.get(0), Game.camX + 252, Game.camY + 549, null);
				
				if(overPummel) {
					g.drawImage(pummelIcon.get(1), Game.camX + 252, Game.camY + 599, null);
					g.drawImage(text, Game.camX + 300, Game.camY + 745, null);
					g.drawString("The strength of Povy's pummel attack. Current level: " + ExperienceBar.pummelLevel, Game.camX + 330, Game.camY + 800);
				}
				else g.drawImage(pummelIcon.get(0), Game.camX + 252, Game.camY + 599, null);
				
				if(overLaser) {
					g.drawImage(laserIcon.get(1), Game.camX + 252, Game.camY + 649, null);
					g.drawImage(text, Game.camX + 300, Game.camY + 745, null);
					g.drawString("The power of Povy's Laser Blaster. Current level: " + ExperienceBar.laserLevel, Game.camX + 330, Game.camY + 800);
				}
				else g.drawImage(laserIcon.get(0), Game.camX + 252, Game.camY + 649, null);
				
				
				Game.expBarTracker.render(g);
			}
			if(pauseState == PauseState.AllyScreen) {
				if(goBackFromAlly == true) {
					g.drawImage(allyMenu.get(1), Game.camX, Game.camY, null);
					Game.allies.render(g);
				}
				else {
					g.drawImage(allyMenu.get(0), Game.camX, Game.camY, null);
					Game.allies.render(g);
				}
				if(overAnAlly) {
					g.drawImage(allyCover, Game.camX + xValForDrawing, Game.camY + 300, null);
				}
			}
		}
	}

}
