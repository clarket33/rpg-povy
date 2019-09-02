
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;


/**
 * the Menu is the Main Menu of the game. You can start a new game or quit
 * @author clarkt5
 *
 */
public class Menu extends MouseAdapter implements MouseMotionListener{
	private Game game;
	private Handler handler;
	private Random r;
	private HUD hud;
	private BufferedImage titleScreen, titleScreen1;
	private BufferedImage titleText, titleBg;
	private int sparkleCount = 0;
	private float menuX = 0;
	private boolean right = true;
	private int changeCount = 0, sparkleX, sparkleY;
	public static boolean overNew = true, overLoad = false, overQuit = false, overOptions = false;
	private ArrayList<BufferedImage> sparkle = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> menu = new ArrayList<BufferedImage>();
	
	/**
	 * creates the menu
	 * @param game
	 * @param handler
	 * @param hud
	 */
	public Menu(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		r = new Random();
		this.hud = hud;
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		 try {
	        menu.add(ss.grabImage(1, 1, 1280, 320, "menuButtons"));
	        menu.add(ss.grabImage(1, 2, 1280, 320, "menuButtons"));
	        menu.add(ss.grabImage(2, 1, 1280, 320, "menuButtons"));
	        menu.add(ss.grabImage(2, 2, 1280, 320, "menuButtons"));
	        menu.add(ss.grabImage(3, 1, 1280, 320, "menuButtons"));
	        
	        titleText = ImageIO.read(new File("res/mainTitle.png"));
	        titleBg = ImageIO.read(new File("res/mainTitleBg.png"));
	        
	        sparkle.add(ss.grabImage(1, 1, 124, 101, "sparkle"));
	        sparkle.add(ss.grabImage(1, 2, 124, 101, "sparkle"));
	        sparkle.add(ss.grabImage(1, 3, 124, 101, "sparkle"));
	        sparkle.add(ss.grabImage(1, 4, 124, 101, "sparkle"));
	        sparkle.add(ss.grabImage(2, 1, 124, 101, "sparkle"));
	        sparkle.add(ss.grabImage(2, 2, 124, 101, "sparkle"));
	        sparkle.add(ss.grabImage(2, 3, 124, 101, "sparkle"));
	        sparkle.add(ss.grabImage(2, 4, 124, 101, "sparkle"));
	        
	        titleScreen = ImageIO.read(new File("res/space/continuousBackground.png"));
	        
	        
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
	}
	
	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if(mouseOver(mx, my, 17, 627, 374, 88)) overNew = true;
		else overNew = false;
		if(mouseOver(mx, my, 889, 627, 374, 88)) overOptions = true;
		else overOptions = false;
		if(mouseOver(mx, my, 17, 831, 374, 88)) overLoad = true;
		else overLoad = false;
		if(mouseOver(mx, my, 889, 831, 374, 88)) overQuit = true;
		else overQuit = false;
	}
	
	/**
	 * either starts a new game or quits
	 */
	public void mousePressed(MouseEvent e) {
		press();
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void press() {

		if(Game.gameState == Game.STATE.Menu) {
			//play button
			/**
			if(mouseOver(mx, my, sparkleX, sparkleY, 124, 101)) {
				AudioPlayer.getSound("starClick").play(1, (float).4);
			}
			**/
			
			if(overNew) {
				AudioPlayer.getSound("click").play(1, (float).1);
				AudioPlayer.getMusic("title").stop();
				
				handler.clear();
				Game.map = new MapReader(handler);
				AudioPlayer.getMusic("dungeon").loop(1, (float).1);
				
				//handler.addObject(new Povy(2000*3, 224, ID.Povy, handler, "blue")); //near chest
				handler.addObject(new Povy(255*3, 40*3, ID.Povy, handler, "blue")); //original
				//handler.addObject(new Povy(2000*3, 864*3, ID.Povy, handler, "blue")); //before final spike trap
				//handler.addObject(new Povy(4320, 3072, ID.Povy, handler, "blue")); //near zatolib
				//handler.addObject(new Povy(192*3, 1184*3, ID.Povy, handler, "blue")); //elephant
				//handler.addObject(new Povy(686*3, 160*3, ID.Povy, handler, "blue")); //first trap
				//handler.addObject(new Povy(1520*3, 1392*3, ID.Povy, handler, "blue")); // lightning test
				//handler.addObject(new Povy(1056, 1584, ID.Povy, handler, "blue")); //stair case test
				//handler.addObject(new Povy(6816, 1344, ID.Povy, handler, "blue")); //tracker test (spikes)
				//handler.addObject(new Povy(2448, 2928, ID.Povy, handler, "blue")); //pillar test
				//handler.addObject(new Povy(1440, 5232, ID.Povy, handler, "blue")); //golem treasure tracker test
				
				
				handler.addObject(new Grogo(182*3, 24*3, ID.Grogo, handler));
				handler.addObject(new Golem(550*3, 216*3, ID.Golem, handler, Golem.GolemType.firstGolem, 0));
				handler.addObject(new Golem(1753*3, 98*3, ID.Golem, handler, Golem.GolemType.tracker, 3));
				handler.addObject(new Golem(7104, 1104, ID.Golem, handler, Golem.GolemType.tracker, 1));
				handler.addObject(new Golem(2259*3, 1729*3, ID.Golem, handler, Golem.GolemType.normal, 0));
				
				
				
				handler.addObject(new Golem(298*3, 1769*3, ID.Golem, handler, Golem.GolemType.tracker, 4));
				
				
				handler.addObject(new Golem(420, 4662, ID.Golem, handler, Golem.GolemType.standStill, 0));
				handler.addObject(new Golem(2592, 3408, ID.Golem, handler, Golem.GolemType.tracker, 2));
				
				handler.addObject(new Rat(2180*3, 170*3, ID.Rat, handler, 2));
				handler.addObject(new Rat(1424*3, 252*3, ID.Rat, handler, 0));
				handler.addObject(new Rat(6408, 1896, ID.Rat, handler, 1));
				
				handler.addObject(new LightningTrap(2880, 96, ID.Lightning, 3));
				handler.addObject(new LightningTrap(3072, 240, ID.Lightning, 3));

				handler.addObject(new LightningTrap(5712, 1152, ID.Lightning, 4));
				handler.addObject(new LightningTrap(6288, 1344, ID.Lightning, 4));
				handler.addObject(new LightningTrap(6864, 1152, ID.Lightning, 5));
				handler.addObject(new LightningTrap(6288, 1344, ID.Lightning, 5));
				handler.addObject(new LightningTrap(5952, 1152, ID.Lightning, 6));
				handler.addObject(new LightningTrap(6480, 1488, ID.Lightning, 6));
				
				
				
				handler.addObject(new LightningTrap(1296*3, 1376*3, ID.Lightning, 0));
				handler.addObject(new LightningTrap(1696*3, 1584*3, ID.Lightning, 0));
				handler.addObject(new LightningTrap(1712*3, 1440*3, ID.Lightning, 1));
				handler.addObject(new LightningTrap(1232*3, 1632*3, ID.Lightning, 1));
				
				
				handler.addObject(new LightningTrap(2256, 5288, ID.Lightning, 2));
				handler.addObject(new LightningTrap(2256, 5616, ID.Lightning, 2));
				
				handler.addObject(new LightningTrap(2448, 5516, ID.Lightning, 2));
				handler.addObject(new LightningTrap(2448, 5116, ID.Lightning, 2));
				
				handler.addObject(new LightningTrap(2640, 5088, ID.Lightning, 2));
				handler.addObject(new LightningTrap(2640, 5616, ID.Lightning, 2));
				
				handler.addObject(new LightningTrap(2832, 5416, ID.Lightning, 2));
				handler.addObject(new LightningTrap(2832, 5216, ID.Lightning, 2));
				
				handler.addObject(new LightningTrap(3024, 5188, ID.Lightning, 2));
				handler.addObject(new LightningTrap(3024, 5588, ID.Lightning, 2));
				
				handler.addObject(new LightningTrap(3216, 5116, ID.Lightning, 2));
				handler.addObject(new LightningTrap(3216, 5616, ID.Lightning, 2));
				
				handler.addObject(new Zatolib(1480*3, 1025*3, ID.Zatolib, handler));
				
				
				
				handler.addObject(new ElephantGuard(192*3, 1010*3, ID.ElephantGuard, handler));
				
				handler.addObject(new Costume(920*3, 165*3, ID.NonEnemy, "purple", handler));
				handler.addObject(new SpaceShip(1570*3, 1150*3, ID.SpaceShip));
				
				
				
				Game.gameState = Game.STATE.KeyFromGrogo;		
			}
			
			
			
			//quit
			if(overQuit) {
				AudioPlayer.getMusic("title").stop();
				AudioPlayer.getSound("click").play(1, (float).1);
				System.exit(0);
			}
			
		}
	}
	
	public void tick() {
		if(right) menuX -= 1;
		if(!right) menuX+=1; 
		if(menuX <= -11520) {
			right = false;
		}
		else if (menuX >= 0){
			right = true;
		}
	}
	
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
	 * renders the menu
	 * @param g
	 */
	public void render(Graphics g) {
	
		if(Game.gameState == Game.STATE.Menu) {
			g.drawImage(titleScreen, (int)menuX, 0, null);
			//g.drawImage(sparkle.get(sparkleCount), sparkleX, sparkleY, null);
			/**
			changeCount++;
			if(changeCount >= 5) {
				changeCount = 0;
				sparkleCount++;
			}
			if(sparkleCount == 8) {
				sparkleX = r.nextInt(1100);
				sparkleY = r.nextInt(800);
				sparkleCount = 0;
			}
			**/
			
			g.drawImage(titleBg, 50, Game.HEIGHT/2-500, null);
			g.drawImage(titleText, 50, Game.HEIGHT/2-500, null);
			//System.out.println(overNew);
			if(overNew)g.drawImage(menu.get(4), 0, Game.HEIGHT-350, null);
			else if(overQuit)g.drawImage(menu.get(1), 0, Game.HEIGHT-350, null);
			else if(overOptions)g.drawImage(menu.get(2), 0, Game.HEIGHT-350, null);
			else if(overLoad)g.drawImage(menu.get(3), 0, Game.HEIGHT-350, null);
			else g.drawImage(menu.get(0), 0, Game.HEIGHT-350, null);
			

		}
	}

}
