import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
/**
 * The Pause Screen is the pause menu, triggered by the user pressing SPACE while in the game state. In this menu,
 * the user can view items held, allies in their ally pouch, current levels in all skills and how close they are to upgardes
 * @author clarkt5
 *
 */
public class PauseScreen extends MouseAdapter implements MouseMotionListener{
	
	//regular pause screen
	public static boolean none = true;
	public static boolean option1 = false;
	public static boolean option2 = false;
	public static boolean option3 = false;
	public static boolean option4 = false;
	public static boolean option5 = false;
	//itemScreen
	public static boolean backToRegFromItem = false;
	public static boolean overItem = false;
	//use item? screen
	public static boolean overYes = false, overNo = false;
	//post battle screen
	public static boolean overNext = false;
	public static boolean overHealth = false, overAlly = false, overPummel = false, overLaser = false;
	//if item was selected as attack
	public static boolean itemSelect = false;
	//progress pause screen
	public static boolean goBackFromProg = false;
	
	//item currently hovering over
	private ArrayList<String> curItemOverLst;
	public static Item curItemOver;
	
	//ally pause screen
	public static boolean goBackFromAlly = false, goBackFromAttire = false;
	public static boolean overAnAlly = false, overAnOutfit = false;
	public static int xValForDrawingAlly, xValForDrawingAttire, gameOverCount = 0, gameOverSpeed = 0;
	
	private BufferedImage text;
	private BufferedImage allyCover;
	public static int overItemY = 0;
	//public static int yItem = 198;
	private ArrayList<BufferedImage> pauseMenu, attireMenu;
	private ArrayList<BufferedImage> itemMenu;
	private ArrayList<BufferedImage> useItem;
	private ArrayList<BufferedImage> expMenuBattle;
	private ArrayList<BufferedImage> expMenuPause;
	
	private ArrayList<BufferedImage> healthIcon;
	private ArrayList<BufferedImage> allyIcon;
	private ArrayList<BufferedImage> pummelIcon;
	private ArrayList<BufferedImage> laserIcon;
	private ArrayList<BufferedImage> allyMenu;
	private ArrayList<BufferedImage> gameOverMenu;
	
	private BufferedImage itemCover;
	public static PauseState pauseState = PauseState.Regular;
	
	/**
	 * makes a new pause screen, loads in all images
	 */
	public PauseScreen() {
		curItemOverLst = new ArrayList<String>();
		text = null;
		allyCover = null;
		
		try {
	        text = ImageIO.read(new File("res/textbox.png"));
	        allyCover = ImageIO.read(new File("res/allyCover.png"));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		itemCover = ss.grabImage(1, 1, 320, 78, "itemCover");
		
		pauseMenu = new ArrayList<BufferedImage>();
		pauseMenu.add(ss.grabImage(1, 1, 1280, 960, "pauseMenu"));
		pauseMenu.add(ss.grabImage(1, 2, 1280, 960, "pauseMenu"));
		pauseMenu.add(ss.grabImage(2, 1, 1280, 960, "pauseMenu"));
		pauseMenu.add(ss.grabImage(2, 2, 1280, 960, "pauseMenu"));
		pauseMenu.add(ss.grabImage(3, 1, 1280, 960, "pauseMenu"));
		pauseMenu.add(ss.grabImage(3, 2, 1280, 960, "pauseMenu"));
		
		gameOverMenu = new ArrayList<BufferedImage>();
		gameOverMenu.add(ss.grabImage(1, 1, 1280, 960, "gameOverMenu"));
		gameOverMenu.add(ss.grabImage(1, 2, 1280, 960, "gameOverMenu"));
		gameOverMenu.add(ss.grabImage(1, 3, 1280, 960, "gameOverMenu"));
		gameOverMenu.add(ss.grabImage(2, 1, 1280, 960, "gameOverMenu"));
		gameOverMenu.add(ss.grabImage(2, 2, 1280, 960, "gameOverMenu"));
		
		
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
		
		attireMenu = new ArrayList<BufferedImage>();
		attireMenu.add(ss.grabImage(1, 1, 1280, 960, "attireMenu"));
		attireMenu.add(ss.grabImage(1, 2, 1280, 960, "attireMenu"));
		
		healthIcon = new ArrayList<BufferedImage>();
		allyIcon = new ArrayList<BufferedImage>();
		pummelIcon = new ArrayList<BufferedImage>();
		laserIcon = new ArrayList<BufferedImage>();
		
		healthIcon.add(ss.grabImage(1, 1,96, 67, "healthIcon"));
		healthIcon.add(ss.grabImage(1, 2, 96, 67, "healthIcon"));
		
		allyIcon.add(ss.grabImage(1, 1, 96, 67, "allyIcon"));
		allyIcon.add(ss.grabImage(1, 2, 96, 67, "allyIcon"));
		
		pummelIcon.add(ss.grabImage(1, 1, 96, 67, "pummelIcon"));
		pummelIcon.add(ss.grabImage(1, 2, 96, 67, "pummelIcon"));
		
		laserIcon.add(ss.grabImage(1, 1, 96, 67, "laserIcon"));
		laserIcon.add(ss.grabImage(1, 2, 96, 67, "laserIcon"));
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
		AttireScreen,
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
		if(Game.gameState != Game.STATE.Battle) changeScreens();
		int mx = e.getX();
		int my = e.getY();
		if(Game.gameState == Game.STATE.Paused) {
			if(pauseState == PauseState.Regular) {
				//player progress
				if(mouseOver(mx, my, 500, 261, 278, 64)) {
					option1 = true;
				}
				else {
					option1 = false;
				}
				
				//allies
				if(mouseOver(mx, my, 500, 348, 278, 64)) {
					option2 = true;
				}
				else {
					option2 = false;
				}
				
				//items
				if(mouseOver(mx, my, 500, 435, 278, 64)) {
					option3 = true;
				}
				else {
					option3 = false;
				}
				
				//attire
				if(mouseOver(mx, my, 500, 522, 278, 64)) {
					option5 = true;
				}
				else {
					option5 = false;
				}
				
				//x out
				if(mouseOver(mx, my, 820, 88, 33, 34)) {
					option4 = true;
				}
				else {
					option4 = false;
				}
				
				if(!option1 && !option2 && !option3 && !option4 && !option5) {
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
					if(mouseOver(mx, my, 482, yItemP, 320, 78)) {
						overItem = true;
						overItemY = yItemP;
						break;
					}
					else {
						overItem = false;
					}
					yItemP += 82;
				}
				int num = (yItemP - 198) / 82;
				curItemOver = Game.itemPouch.getItem(num);
			}
			else if(pauseState == PauseState.ItemUse) {
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
				if(mouseOver(mx, my, 142, 429, 96, 67)) {
					overHealth = true;
				}
				else {
					overHealth = false;
				}
				if(mouseOver(mx, my, 142, 499, 96, 67)) {
					overAlly = true;
				}
				else {
					overAlly = false;
				}
				if(mouseOver(mx, my, 142, 569, 96, 67)) {
					overPummel = true;
				}
				else {
					overPummel = false;
				}
				if(mouseOver(mx, my, 142, 639, 96, 67)) {
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
						xValForDrawingAlly = xAlly;
						break;
					}
					else {
						overAnAlly = false;
					}
					xAlly += 232;
				}
			}
			if(pauseState == PauseState.AttireScreen) {
				if(mouseOver(mx, my, 53, 82, 36, 36)) {
					goBackFromAttire = true;
				}
				else {
					goBackFromAttire = false;
				}
				int xAttire = 182;
				for(int i = 0; i < Game.costumePouch.costumes.size(); i++) {
					if(mouseOver(mx, my, xAttire, 300, 208, 208)) {
						overAnOutfit = true;
						xValForDrawingAttire = xAttire;
						break;
					}
					else {
						overAnOutfit = false;
					}
					xAttire += 232;
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
						if(mouseOver(mx, my, 482, yItemP, 320, 78)) {
							overItem = true;
							overItemY = yItemP;
							break;
						}
						else {
							overItem = false;
						}
						yItemP += 82;
					}
					int num = (yItemP - 198) / 82;
					curItemOver = Game.itemPouch.getItem(num);
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
						xValForDrawingAlly = xAlly;
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
			if(mouseOver(mx, my, 142, 429, 96, 67)) {
				overHealth = true;
			}
			else {
				overHealth = false;
			}
			if(mouseOver(mx, my, 142, 499, 96, 67)) {
				overAlly = true;
			}
			else {
				overAlly = false;
			}
			if(mouseOver(mx, my, 142, 569, 96, 67)) {
				overPummel = true;
			}
			else {
				overPummel = false;
			}
			if(mouseOver(mx, my, 142, 639, 96, 67)) {
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
		mouseMoved(e);
		menuControl();
		
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
		if(Game.gameState == Game.STATE.GameOver) {
			if(gameOverCount != 5) {
				g.drawImage(gameOverMenu.get(gameOverCount), 0, 0, null);
				gameOverSpeed++;
				if(gameOverSpeed % 5 == 0) {
					gameOverCount++;
				}
			}
			else{
				g.drawImage(gameOverMenu.get(4), 0, 0, null);
			}
		}
		else if(Game.gameState == Game.STATE.Battle) {
			if(Battle.itemSelected) {
				if(itemSelect == false) {
					//System.out.println("FFF");
					if(backToRegFromItem == true) {
						g.drawImage(itemMenu.get(1), 0, 0, null);
						Game.itemPouch.render(g);
					}
					else {
						g.drawImage(itemMenu.get(0), 0, 0, null);
						Game.itemPouch.render(g);
					}
					
					if(overItem == true) {
						Font fo = new Font("verdana", 1, 20);
						g.setColor(Color.WHITE);
						g.setFont(fo);
						g.drawImage(itemCover, 0+482, 0 + overItemY, null);
						g.drawImage(text, Game.camX + 120, Game.camY + 625, null);
						if(curItemOver != null) {
							curItemOverLst = curItemOver.itemDescript();
							int ySpot = 0;
							for(int i = 0; i < curItemOverLst.size(); i++) {
								g.drawString(curItemOverLst.get(i), Game.camX + 390, Game.camY + 800 + ySpot);
								ySpot += 25;
							}
						}
					}
				}
				else {
					//System.out.println("we mad eit");
					g.drawImage(itemMenu.get(0), 0, 0, null);
					Game.itemPouch.render(g);
					Font fo = new Font("verdana", 1, 20);
					g.setColor(Color.WHITE);
					g.setFont(fo);
					g.drawImage(itemCover, Game.camX+482, Game.camY + overItemY, null);
					g.drawImage(text, Game.camX + 120, Game.camY + 625, null);
					if(curItemOver != null) {
						curItemOverLst = curItemOver.itemDescript();
						int ySpot = 0;
						for(int i = 0; i < curItemOverLst.size(); i++) {
							g.drawString(curItemOverLst.get(i), Game.camX + 390, Game.camY + 800 + ySpot);
							ySpot += 25;
						}
					}
					if(overYes) g.drawImage(useItem.get(1), 0, 0, null);
					else if(overNo)  g.drawImage(useItem.get(2), 0, 0, null);
					else  g.drawImage(useItem.get(0), 0, 0, null);
					//System.out.println("wtf!");
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
					g.drawImage(allyCover, Game.camX + xValForDrawingAlly, Game.camY + 300, null);
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
				if(overHealth)g.drawImage(healthIcon.get(1), Game.camX + 142, Game.camY + 429, null);
				else g.drawImage(healthIcon.get(0), Game.camX + 142, Game.camY + 429, null);
				
				if(overAlly)g.drawImage(allyIcon.get(1), Game.camX + 142, Game.camY + 499, null);
				else g.drawImage(allyIcon.get(0), Game.camX + 142, Game.camY + 499, null);
				
				if(overPummel)g.drawImage(pummelIcon.get(1), Game.camX + 142, Game.camY + 569, null);
				else g.drawImage(pummelIcon.get(0), Game.camX + 142, Game.camY + 569, null);
				
				if(overLaser)g.drawImage(laserIcon.get(1), Game.camX + 142, Game.camY + 639, null);
				else g.drawImage(laserIcon.get(0), Game.camX + 142, Game.camY + 639, null);
			}
			else {
				g.setFont(new Font("Cooper Black",1,30));
				g.setColor(Color.GREEN);
				g.drawString("Upgrade Available!", Game.camX + 490, Game.camY + 370);
				g.drawString("Select an area to upgrade by selecting an icon:", Game.camX + 260, Game.camY + 410);
				
				if(overHealth)g.drawImage(healthIcon.get(1), Game.camX + 142, Game.camY + 429, null);
				else g.drawImage(healthIcon.get(0), Game.camX + 142, Game.camY + 429, null);
				
				if(overAlly)g.drawImage(allyIcon.get(1), Game.camX + 142, Game.camY + 499, null);
				else g.drawImage(allyIcon.get(0), Game.camX + 142, Game.camY + 499, null);
				
				if(overPummel)g.drawImage(pummelIcon.get(1), Game.camX + 142, Game.camY + 569, null);
				else g.drawImage(pummelIcon.get(0), Game.camX + 142, Game.camY + 569, null);
				
				if(overLaser)g.drawImage(laserIcon.get(1), Game.camX + 142, Game.camY + 639, null);
				else g.drawImage(laserIcon.get(0), Game.camX + 142, Game.camY + 639, null);
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
					g.drawImage(pauseMenu.get(5), Game.camX, Game.camY, null);
				}
				else if(option5 == true){
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
					Font fo = new Font("verdana", 1, 20);
					g.setColor(new Color(106, 215, 48));
					g.setFont(fo);
					g.drawImage(itemCover, Game.camX+482, Game.camY + overItemY, null);
					g.drawImage(text, Game.camX + 120, Game.camY + 625, null);
					if(curItemOver != null) {
						curItemOverLst = curItemOver.itemDescript();
						int ySpot = 0;
						for(int i = 0; i < curItemOverLst.size(); i++) {
							g.drawString(curItemOverLst.get(i), Game.camX + 390, Game.camY + 750 + ySpot);
							ySpot += 25;
						}
					}
				}
			}
			if(pauseState == PauseState.ItemUse) {
				g.drawImage(itemMenu.get(0), Game.camX, Game.camY, null);
				Game.itemPouch.render(g);
				Font fo = new Font("verdana", 1, 20);
				g.setColor(new Color(106, 215, 48));
				g.setFont(fo);
				g.drawImage(itemCover, Game.camX+482, Game.camY + overItemY, null);
				g.drawImage(text, Game.camX + 120, Game.camY + 625, null);
				if(curItemOver != null) {
					curItemOverLst = curItemOver.itemDescript();
					int ySpot = 0;
					for(int i = 0; i < curItemOverLst.size(); i++) {
						g.drawString(curItemOverLst.get(i), Game.camX + 390, Game.camY + 750 + ySpot);
						ySpot += 25;
					}
				}
				if(overYes) g.drawImage(useItem.get(1), Game.camX, Game.camY, null);
				else if(overNo)  g.drawImage(useItem.get(2), Game.camX, Game.camY, null);
				else  g.drawImage(useItem.get(0), Game.camX, Game.camY, null);
				return;
			}
			if(pauseState == PauseState.ProgressScreen) {
				Font fo = new Font("verdana", 1, 20);
				g.setColor(new Color(106, 215, 48));
				g.setFont(fo);
				if(goBackFromProg) g.drawImage(expMenuPause.get(1), Game.camX, Game.camY, null);
				else  g.drawImage(expMenuPause.get(0), Game.camX, Game.camY, null);
				Game.expBarTracker.render(g);
				if(overHealth) {
					g.drawImage(healthIcon.get(1), Game.camX + 142, Game.camY + 429, null);
					g.drawImage(text, Game.camX + 120, Game.camY + 687, null);
					g.drawString("Status of Povy's health. Current level: " + ExperienceBar.healthLevel + " (" + HUD.maxHealth + " max health)", Game.camX + 340, Game.camY + 830);
				}
				else {
					g.drawImage(healthIcon.get(0), Game.camX + 142, Game.camY + 429, null);
				}
				
				if(overAlly) {
					g.drawImage(allyIcon.get(1), Game.camX + 142, Game.camY + 499, null);
					g.drawImage(text, Game.camX + 120, Game.camY + 687, null);
					g.drawString("The effectiveness of Povy's allies. Current level: " + ExperienceBar.allyLevel, Game.camX + 350, Game.camY + 830);
				}
				else g.drawImage(allyIcon.get(0), Game.camX + 142, Game.camY + 499, null);
				
				if(overPummel) {
					g.drawImage(pummelIcon.get(1), Game.camX + 142, Game.camY + 569, null);
					g.drawImage(text, Game.camX + 120, Game.camY + 687, null);
					g.drawString("The strength of Povy's pummel attack. Current level: " + ExperienceBar.pummelLevel, Game.camX + 350, Game.camY + 830);
				}
				else g.drawImage(pummelIcon.get(0), Game.camX + 142, Game.camY + 569, null);
				
				if(overLaser) {
					g.drawImage(laserIcon.get(1), Game.camX + 142, Game.camY + 639, null);
					g.drawImage(text, Game.camX + 120, Game.camY + 687, null);
					g.drawString("The power of Povy's Laser Blaster. Current level: " + ExperienceBar.laserLevel, Game.camX + 350, Game.camY + 830);
				}
				else g.drawImage(laserIcon.get(0), Game.camX + 142, Game.camY + 639, null);
				
				
				
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
					g.drawImage(allyCover, Game.camX + xValForDrawingAlly, Game.camY + 300, null);
				}
			}
			if(pauseState == PauseState.AttireScreen) {
				if(goBackFromAttire == true) {
					g.drawImage(attireMenu.get(1), Game.camX, Game.camY, null);
					Game.costumePouch.render(g);
				}
				else {
					g.drawImage(attireMenu.get(0), Game.camX, Game.camY, null);
					Game.costumePouch.render(g);
				}
				if(overAnOutfit) {
					g.drawImage(allyCover, Game.camX + xValForDrawingAttire, Game.camY + 300, null);
				}
			}
		}
	}
	
	
	
	/**
	 * aids in rendering so when a certain screen is returned to,
	 * it resets the rendering so no buttons are highlighted as if
	 * the mouse is hovering over
	 */
	public static void changeScreens() {
		none = true;
		option1 = false;
		option2 = false;
		option3 = false;
		option4 = false;
		option5 = false;
		backToRegFromItem = false;
		overItem = false;
		overYes = false;
		overNo = false;
		overNext = false;
		overHealth = false;
		overAlly = false;
		overPummel = false;
		overLaser = false;
		itemSelect = false;
		goBackFromProg = false;
		goBackFromAlly = false;
		overAnAlly = false;
		goBackFromAttire = false;
		overAnOutfit = false;
		
		
		
	}
	
	public static void menuControl() {
		if(Game.gameState == Game.STATE.GameOver) {
			
			//if mouse over quit, exit game
		}
		else if(Game.gameState == Game.STATE.Paused) {
			if(pauseState == PauseState.Regular) {
				//player progress
				if(option1) {//mouseOver(mx, my, 500, 261, 278, 64)) {
					AudioPlayer.getSound("click").play(1, (float).1);
					pauseState = PauseState.ProgressScreen;
					goBackFromProg = true;
					//changeScreens();
				}
				
				
				//allies
				if(option2) {//mouseOver(mx, my, 500, 348, 278, 64)) {
					AudioPlayer.getSound("click").play(1, (float).1);
					pauseState = PauseState.AllyScreen;
					goBackFromAlly = true;
					//changeScreens();
				}
				
				
				//items
				if(option3) {//mouseOver(mx, my, 500, 435, 278, 64)) {
					pauseState = PauseState.ItemScreen;
					AudioPlayer.getSound("click").play(1, (float).1);
					backToRegFromItem = true;
					//changeScreens();
				}
				
				//attire
				if(option5) {//mouseOver(mx, my, 500, 522, 278, 64)) {
					
					pauseState = PauseState.AttireScreen;
					AudioPlayer.getSound("click").play(1, (float).1);
					goBackFromAttire = true;
					//changeScreens();
					
				}
				
				
				//x out
				if(option4) {//mouseOver(mx, my, 820, 88, 33, 34)) {
					Game.gameState = Game.STATE.Game;
					AudioPlayer.getSound("click").play(1, (float).1);
					changeScreens();
				}
				return; 
			}
			if(pauseState == PauseState.ItemScreen) {
				if(backToRegFromItem) {//mouseOver(mx, my, 430, 78, 36, 36)) {
					pauseState = PauseState.Regular;
					AudioPlayer.getSound("click").play(1, (float).1);
					KeyInput.numItems = 0;
					//changeScreens();
				}
				if(overItem) {
					pauseState = PauseState.ItemUse;
					//overItem = false;
					overYes = true;
					AudioPlayer.getSound("click").play(1, (float).1);
				}
				return;
				
			}
			if(pauseState == PauseState.ItemUse) {
				if(overYes) {//mouseOver(mx, my, 516, 496, 114, 38)) {
					Game.gameState = Game.STATE.Game;
					Povy.takingPotion = true;
					pauseState = PauseState.Regular;
					AudioPlayer.getSound("click").play(1, (float).1);
					overYes = false;
					KeyInput.numItems = 0;
					changeScreens();
				}
				else if(overNo) {//mouseOver(mx, my, 650, 496, 114, 38)) {
					overNo = false;
					pauseState = PauseState.ItemScreen;
					AudioPlayer.getSound("click").play(1, (float).1);
					//KeyInput.numItems = 0;
				}
			}
			if(pauseState == PauseState.ProgressScreen) {
				if(goBackFromProg) {//mouseOver(mx, my, 53, 82, 36, 36)) {
					pauseState = PauseState.Regular;
					AudioPlayer.getSound("click").play(1, (float).1);
					//changeScreens();
				}
			}
			if(pauseState == PauseState.AllyScreen) {
				if(goBackFromAlly) {//mouseOver(mx, my, 53, 82, 36, 36)) {
					pauseState = PauseState.Regular;
					AudioPlayer.getSound("click").play(1, (float).1);
					//changeScreens();
				}
			}
			if(pauseState == PauseState.AttireScreen) {
				if(goBackFromAttire) {//mouseOver(mx, my, 53, 82, 36, 36)) {
					pauseState = PauseState.Regular;
					AudioPlayer.getSound("click").play(1, (float).1);
					//changeScreens();
				}
				if(overAnOutfit) {
					Iterator<Costume> itr = Game.costumePouch.costumes.iterator();
					int tempX = 182;
					while(itr.hasNext()) {
						Costume cur = itr.next();
						if(xValForDrawingAttire == tempX) {
							Game.costumePouch.equip(cur.getCostume());
							AudioPlayer.getSound("click").play(1, (float).1);
						}
						tempX += 232;
					}
				}
				
				return;
			}
		}
		else if(Game.gameState == Game.STATE.Battle) {
			if(Battle.itemSelected) {
				//System.out.println(backToRegFromItem);
				//System.out.println(itemSelect);
				if(itemSelect == false) {
					if(backToRegFromItem) {//mouseOver(mx, my, 430, 78, 36, 36)) {
						Battle.itemSelected = false;
						AudioPlayer.getSound("click").play(1, (float).1);
						//changeScreens();
						KeyInput.numItems = 0;
						//System.out.println("ItemSlect After click: " + Battle.itemSelected);
						Battle.itemAllyRet = true;
						return;
					}
					if(overItem) {
						//overItem = false;
						itemSelect = true;
						overYes = true;
						AudioPlayer.getSound("click").play(1, (float).1);
						//System.out.println("huh");
						//changeScreens();
					}
					return;
				}
				else {
					if(overYes) {//mouseOver(mx, my, 516, 496, 114, 38)) {
						Battle.battleState = Battle.BATTLESTATE.PlayerTurnAction;
						Battle.itemSelected = false;
						itemSelect = false;
						overYes = false;
						AudioPlayer.getSound("click").play(1, (float).1);
						KeyInput.numItems = 0;
						changeScreens();
					}
					else if(overNo){//mouseOver(mx, my, 650, 496, 114, 38)) {
						overNo = false;
						itemSelect = false;
						//KeyInput.numItems = 0;
						AudioPlayer.getSound("click").play(1, (float).1);
					}
				}
			}
			else if(Battle.allySelected) {
				if(goBackFromAlly) {//mouseOver(mx, my, 53, 82, 36, 36)) {
					Battle.allySelected = false;
					AudioPlayer.getSound("click").play(1, (float).1);
					changeScreens();
					Battle.itemAllyRet = true;
				}
				//System.out.println(Game.itemPouch.getItemAmnt());
				if(overAnAlly) {
					Iterator<GameObject> itr = Game.allies.allies.iterator();
					int tempX = 182;
					while(itr.hasNext()) {
						GameObject cur = itr.next();
						if(xValForDrawingAlly == tempX) {
							Game.battle.setAlly(cur);
							AudioPlayer.getSound("click").play(1, (float).1);
							Battle.battleState = Battle.BATTLESTATE.PlayerTurnAction;
							Battle.allySelected = false;
							Battle.useAlly = false;
							HUD.allyCount = 0;
							overAnAlly = false;
							changeScreens();
							break;
						}
						tempX += 232;
					}
				}
				return;
				
			}
		}
		else if(Game.gameState == Game.STATE.PostBattle) {
			System.out.println(overNext);
			if(ExperienceBar.levelUp != true && Battle.expToBeAdded == 0) {
				if(overNext) {//mouseOver(mx, my, 483, 716, 278, 64)) {
					Game.gameState = Game.STATE.Game;
					AudioPlayer.getSound("click").play(1, (float).1);
					changeScreens();
				}
			}
			if(ExperienceBar.levelUp == true) {
				if(overHealth) {//mouseOver(mx, my, 142, 429, 96, 67)) {
					if(ExperienceBar.healthLevel != 10) {
						ExperienceBar.healthLevel += 1;
						ExperienceBar.levelUp = false;
						AudioPlayer.getSound("upgradeDone").play(1, (float).1);
						HUD.maxHealth += 16;
						HUD.HEALTH = HUD.maxHealth;
						overNext = true;
						overHealth = false;
						AudioPlayer.getSound("click").play(1, (float).1);
					}
				}
				else if(overAlly) {//mouseOver(mx, my, 142, 499, 96, 67)) {
					if(ExperienceBar.allyLevel != 10) {
						ExperienceBar.allyLevel += 1;
						ExperienceBar.levelUp = false;
						AudioPlayer.getSound("upgradeDone").play(1, (float).1);
						HUD.HEALTH = HUD.maxHealth;
						overNext = true;
						overAlly = false;
						AudioPlayer.getSound("click").play(1, (float).1);
					}
				}
				else if(overPummel) {//mouseOver(mx, my, 142, 569, 96, 67)) {
					if(ExperienceBar.pummelLevel != 10) {
						ExperienceBar.pummelLevel += 1;
						ExperienceBar.levelUp = false;
						AudioPlayer.getSound("upgradeDone").play(1, (float).1);
						HUD.HEALTH = HUD.maxHealth;
						overNext = true;
						overPummel = false;
						AudioPlayer.getSound("click").play(1, (float).1);
					}
				}
				else if(overLaser) {//mouseOver(mx, my, 142, 639, 96, 67)) {
					if(ExperienceBar.laserLevel != 10) {
						ExperienceBar.laserLevel += 1;
						ExperienceBar.levelUp = false;
						AudioPlayer.getSound("upgradeDone").play(1, (float).1);
						HUD.HEALTH = HUD.maxHealth;
						overNext = true;
						overLaser = false;
						AudioPlayer.getSound("click").play(1, (float).1);
					}
				}
			}
		}
	}

}
