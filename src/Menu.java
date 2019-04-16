
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;


/**
 * the Menu is the Main Menu of the game. You can start a new game or quit
 * @author clarkt5
 *
 */
public class Menu extends MouseAdapter{
	private Game game;
	private Handler handler;
	private Random r;
	private HUD hud;
	private BufferedImage titleScreen;
	private BufferedImage play, quit;
	
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
		play = null;
		quit = null;
		 try {
	        play = ImageIO.read(new File("res/NewGame.png"));
	        quit = ImageIO.read(new File("res/quit.png"));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		titleScreen = ss.grabImage(1, 1, 1280, 975, "space");
	}
	
	/**
	 * either starts a new game or quits
	 */
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if(game.gameState == Game.STATE.Menu) {
			//play button
			if(mouseOver(mx, my, game.WIDTH/2 - 190, game.HEIGHT/2 - 186, 400, 100)) {
				AudioPlayer.getSound("click").play(1, (float).1);
				AudioPlayer.getMusic("title").stop();
				
				handler.clear();
				Game.map = new MapReader(handler);
				AudioPlayer.getMusic("dungeon").loop(1, (float).1);
				
				//handler.addObject(new Povy(2848, 224, ID.Povy, handler, "blue"));
				handler.addObject(new Povy(255*3, 40*3, ID.Povy, handler, "blue"));
				//handler.addObject(new Povy(2000*3, 864*3, ID.Povy, handler, "blue"));
				//handler.addObject(new Povy(832*3, 1008*3, ID.Povy, handler, "blue"));
				
				
				handler.addObject(new Grogo(182*3, 24*3, ID.Grogo, handler));
				handler.addObject(new Golem(550*3, 216*3, ID.Golem, handler, Golem.GolemType.firstGolem));
				handler.addObject(new Golem(1753*3, 98*3, ID.Golem, handler, Golem.GolemType.normal));
				handler.addObject(new Golem(2349*3, 510*3, ID.Golem, handler, Golem.GolemType.normal));
				handler.addObject(new Golem(2259*3, 1729*3, ID.Golem, handler, Golem.GolemType.normal));
				handler.addObject(new Golem(765*3, 509*3, ID.Golem, handler, Golem.GolemType.normal));
				handler.addObject(new Golem(298*3, 1769*3, ID.Golem, handler, Golem.GolemType.treasureGuard));
				
				handler.addObject(new Rat(2165*3, 170*3, ID.Rat, handler, 0));
				handler.addObject(new Rat(1424*3, 252*3, ID.Rat, handler, 1));
				handler.addObject(new Rat(2335*3, 1097*3, ID.Rat, handler, 0));
				handler.addObject(new Rat(435*3, 1566*3, ID.Rat, handler, 0));
				
				
				handler.addObject(new Zatolib(1550*3, 1058*3, ID.Zatolib, handler));
				
				
				handler.addObject(new ElephantGuard(192*3, 1010*3, ID.ElephantGuard, handler));
				
				handler.addObject(new Costume(920*3, 165*3, ID.NonEnemy, "purple", handler));
				
				
				game.gameState = Game.STATE.Game;		
			}
			
			
			
			//quit
			if(mouseOver(mx, my, game.WIDTH/2 - 190, game.HEIGHT/2 + 114, 400, 100)) {
				AudioPlayer.getMusic("title").stop();
				AudioPlayer.getSound("click").play(1, (float).1);
				System.exit(0);
			}
			
		}
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void tick() {
		
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
	
		if(game.gameState == Game.STATE.Menu) {
			g.drawImage(titleScreen, 0, 0, null);
			
			
			
			Font fo = new Font("algerian", 1, 100);
			
			
			String fName = "/android/Android Scratch.ttf";
		    InputStream is = Menu.class.getResourceAsStream(fName);
		    try {
		    	Font font = Font.createFont(Font.TRUETYPE_FONT, is);
		    	Font titleFont = font.deriveFont(1, 75);
		    	g.setFont(titleFont);
		    }catch(Exception e) {
		    	g.setFont(fo);
		    }

			
			
			g.setColor(Color.GREEN);
			g.drawString("POVY THE ALIEN", Game.WIDTH / 2 - 380, game.HEIGHT /2 - 336);
			
			fo = new Font("arial", 1, 50);
			g.setFont(fo);
			g.drawImage(play, game.WIDTH/2-190, game.HEIGHT/2-186, null);
			g.drawImage(quit, game.WIDTH/2-190, game.HEIGHT/2+114, null);

		}
	}

}
