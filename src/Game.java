import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author clarkt5
 * The main Game class for Povy the Alien. Loads in all resources for usage,
 * creates main objects and implements the game loop
 */
public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -1442798787354930462L;

	public static int WIDTH = 1280, HEIGHT = 960;
	
	private Thread thread;
	private boolean running = false;
	private Menu menu;
	private Handler handler;
	public static HUD hud;
	private Window w;
	private KeyToPovy ktp;
	public static ExperienceBar expBarTracker;
	public static MapReader map;
	public static Battle battle;
	public static ArrayList<BufferedImage> dungeonTiles;
	public static Map<Integer, Map<String, ArrayList<Integer>>> collisionTiles;
	public static Map<String, ArrayList<String>> animationDungeon;
	public static Map<String, Integer> animationDungeonCounter;
	public static int camX, camY;
	public static ItemPouch itemPouch;
	public static boolean battleReturn = false;
	public static boolean firstBattle = true;
	public static AllyPouch allies;
	
	public enum STATE{
		Menu,
		Game,
		Paused,
		Battle,
		PostBattle,
		Transition,
		KeyFromGrogo
	};
	
	public static STATE gameState = STATE.Menu;
	public static Map<String, BufferedImage> sprite_sheet = new HashMap<String, BufferedImage>();
	public static PauseScreen pause;
	
	/**
	 * creates the Game
	 */
	public Game() {
		
		BufferedImageLoader loader = new BufferedImageLoader();
		AudioPlayer.load();
		AudioPlayer.getMusic("title").loop(1,(float).1);
		try {
			sprite_sheet.put("spinning", loader.loadImage("/spinning_povy.png"));
			sprite_sheet.put("povy", loader.loadImage("/povy_board.png"));
			sprite_sheet.put("povy2", loader.loadImage("/povy_board2.png"));
			sprite_sheet.put("wasp", loader.loadImage("/wasp-Sheet.png"));
			sprite_sheet.put("yeti", loader.loadImage("/yeti-Sheet.png"));
			sprite_sheet.put("werewolf", loader.loadImage("/werewolf-Sheet.png"));
			sprite_sheet.put("golem", loader.loadImage("/golem-Sheet.png"));
			sprite_sheet.put("golem1", loader.loadImage("/golem-SheetRight.png"));
			sprite_sheet.put("space", loader.loadImage("/Space_Sheet.png"));
			sprite_sheet.put("rat", loader.loadImage("/rat-Sheet.png"));
			sprite_sheet.put("rat1", loader.loadImage("/rat-SheetRight.png"));
			sprite_sheet.put("broken_crystal", loader.loadImage("/broken_crystal-sheet.png"));
			sprite_sheet.put("fallingPovy", loader.loadImage("/fallingPovy-sheet.png"));
			sprite_sheet.put("endOfOpening", loader.loadImage("/cobit_explosion-sheet.png"));
			sprite_sheet.put("dungeonTiles", loader.loadImage("/Dungeon/rpg-dungeon-pack v1.1(wonderdot)/tiles_dungeon_v1.1.png"));
			sprite_sheet.put("grogo", loader.loadImage("/grogo_right.png"));
			sprite_sheet.put("grogo2", loader.loadImage("/grogo_left.png"));
			sprite_sheet.put("transition", loader.loadImage("/Transition.png"));
			sprite_sheet.put("health", loader.loadImage("/heart_animated_2.png"));
			sprite_sheet.put("menuActions", loader.loadImage("/attackMenu.png"));
			sprite_sheet.put("menuActionsRight", loader.loadImage("/attackMenuRight.png"));
			sprite_sheet.put("pauseMenu", loader.loadImage("/pauseMenu.png"));
			sprite_sheet.put("itemMenu", loader.loadImage("/itemScreen.png"));
			sprite_sheet.put("smallHP", loader.loadImage("/Items/SmallHealth.png"));
			sprite_sheet.put("largeHP", loader.loadImage("/Items/largeHealth.png"));
			sprite_sheet.put("maxHP", loader.loadImage("/Items/maxHealth.png"));
			sprite_sheet.put("smallBP", loader.loadImage("/Items/smallBattlePoints.png"));
			sprite_sheet.put("largeBP", loader.loadImage("/Items/LargeBattlePoints.png"));
			sprite_sheet.put("maxBP", loader.loadImage("/Items/maxBP.png"));
			sprite_sheet.put("itemCover", loader.loadImage("/Items/itemCover.png"));
			sprite_sheet.put("itemOption", loader.loadImage("/selectItemChoice.png"));
			
			sprite_sheet.put("expBar", loader.loadImage("/expBar.png"));
			sprite_sheet.put("healthBar", loader.loadImage("/healthBar.png"));
			sprite_sheet.put("laserBar", loader.loadImage("/laserBar.png"));
			sprite_sheet.put("pummelBar", loader.loadImage("/pummelBar.png"));
			sprite_sheet.put("allyBar", loader.loadImage("/allyBar.png"));
			sprite_sheet.put("progressMenu", loader.loadImage("/expMenu.png"));
			
			sprite_sheet.put("healthIcon", loader.loadImage("/healthIcon.png"));
			sprite_sheet.put("allyIcon", loader.loadImage("/allyIcon.png"));
			sprite_sheet.put("allyIcon1", loader.loadImage("/allyIcon1.png"));
			sprite_sheet.put("pummelIcon", loader.loadImage("/pummelIcon.png"));
			sprite_sheet.put("laserIcon", loader.loadImage("/laserIcon.png"));
			
			sprite_sheet.put("dialogue", loader.loadImage("/cutsceneText.png"));
			sprite_sheet.put("grogoTalking", loader.loadImage("/grogoTalking.png"));
			
			sprite_sheet.put("allyMenu", loader.loadImage("/allyMenu.png"));
			sprite_sheet.put("allyMeter", loader.loadImage("/allyMeter.png"));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		expBarTracker = new ExperienceBar();
		collisionTiles = new HashMap<Integer, Map<String, ArrayList<Integer>>>();
		animationDungeon = new HashMap<String, ArrayList<String>>();
		animationDungeonCounter = new HashMap<String, Integer>();
		handler = new Handler(this);
		pause = new PauseScreen();
		allies = new AllyPouch();
		hud = new HUD();
		ktp = new KeyToPovy(handler);
		menu = new Menu(this, handler, hud);
		itemPouch = new ItemPouch();
		this.addMouseListener(menu);
		this.addKeyListener(new KeyInput(handler, this));
		this.addKeyListener(ktp);
		this.addMouseMotionListener(pause);
		this.addMouseListener(pause);
		w = new Window(WIDTH, HEIGHT, "Povy the Alien", this);
		
		
	}

	@Override
	/**
	 * creates a thread and runs the game, invokes tick and render of all objects
	 */
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running) {
				render();
			}
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	/**
	 * wats for thread to die
	 */
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * new thread
	 */
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	private void tick() {
		
		if(gameState == STATE.Game) {
			hud.tick();
			handler.tick();	
		}
		else if (gameState == STATE.Menu) {
			menu.tick();
		}
		else if(gameState == STATE.Battle) {
			battle.tick();
		}
		else if(gameState == STATE.Paused) {
			
		}
	}
	/**
	 * 
	 * @param var
	 * @param min
	 * @return position povy should be when colliding with an object
	 */
	public static float clampUpLeft(float var, float min) {
		if(var <= min){
			return var = min;
		}
		else {
			return var;
		}
	}
	
	/**
	 * 
	 * @param var
	 * @param max
	 * @return position povy should be when colliding with an object
	 */
	public static float clampDownRight(float var, float max) {
		if(var >= max) {
			return var = max;
		}
		else {
			return var;
		}
	}
	
	/**
	 * 
	 * @param var
	 * @param min
	 * @param max
	 * @return position a game object should be(normally enemies) should be when
	 * colliding into an object
	 */
	public static float clamp(float var, float min, float max) {
		if(var >= max) {
			return var = max;
		}
		if(var <= min){
			return var = min;
		}
		else {
			return var;
		}
	}
	
	/**
	 * calls the render methods of all aspects of the game depending on the game state
	 */
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		if(gameState == STATE.KeyFromGrogo) {
			map.render(g);
			hud.render(g);
			ktp.render(g);
			
		}
		else if(gameState == STATE.Game) {
			map.render(g);
			hud.render(g);
		}
		else if (gameState == STATE.Menu) {
			menu.render(g);
		} 
		else if (gameState == STATE.Battle) {
			battle.render(g);
		}
		else if (gameState == STATE.Transition) {
			map.render(g);
		}
		else if(gameState == STATE.Paused) {
			map.render(g);
			hud.render(g);
			pause.render(g);
		}
		else if(gameState == STATE.PostBattle) {
			map.render(g);
			pause.render(g);
			expBarTracker.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	
	/**
	 * 
	 * @param args
	 * makes the game
	 */
	public static void main(String[] args) {
		new Game();
	}

	

}

