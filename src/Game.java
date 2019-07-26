import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
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
	public static FightText ft;
	private CrystalCutscene cc;
	public static ExperienceBar expBarTracker;
	public static MapReader map;
	public static Battle battle;
	public static ArrayList<BufferedImage> dungeonTiles;
	public static ArrayList<BufferedImage> crystalConfirm;
	public static Map<Integer, Map<String, ArrayList<Integer>>> collisionTiles;
	public static Map<String, ArrayList<String>> animationDungeon;
	public static Map<String, Integer> animationDungeonCounter;
	public static int camX, camY;
	public static ItemPouch itemPouch;
	public static CostumePouch costumePouch;
	public static boolean battleReturn = false;
	public static boolean firstBattle = true, lastBattle = false;
	public static AllyPouch allies;
	public static int pillarOrder = 1, crysConfCount = 0;
	public static boolean stair = false, levTwo = false;
	
	public enum STATE{
		Menu,
		Game,
		GameOver,
		Paused,
		Battle,
		PostBattle,
		Transition,
		FinalCutscene,
		Dead,
		KeyFromGrogo,
		AfterZatolib,
		levelTwoTran
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
			sprite_sheet.put("povyPurple", loader.loadImage("/povy_board_purple.png"));
			sprite_sheet.put("povyPurple2", loader.loadImage("/povy_board_purple2.png"));
			sprite_sheet.put("wasp", loader.loadImage("/wasp-Sheet.png"));
			sprite_sheet.put("yeti", loader.loadImage("/yeti-Sheet.png"));
			sprite_sheet.put("werewolf", loader.loadImage("/werewolf-Sheet.png"));
			sprite_sheet.put("golem", loader.loadImage("/golem-Sheet.png"));
			sprite_sheet.put("elephantGuard", loader.loadImage("/Guard.png"));
			sprite_sheet.put("elephantGuard1", loader.loadImage("/Guard1.png"));
			sprite_sheet.put("zatolib", loader.loadImage("/Lord_Zatolib.png"));
			sprite_sheet.put("zatolibRight", loader.loadImage("/Lord_Zatolib_Right.png"));
			sprite_sheet.put("zatolibBullet", loader.loadImage("/zatolibBullet.png"));
			sprite_sheet.put("golem1", loader.loadImage("/golem-SheetRight.png"));
			sprite_sheet.put("space", loader.loadImage("/Space_Sheet.png"));
			sprite_sheet.put("rat", loader.loadImage("/rat-Sheet.png"));
			sprite_sheet.put("rat1", loader.loadImage("/rat-SheetRight.png"));
			sprite_sheet.put("broken_crystal", loader.loadImage("/broken_crystal-sheet.png"));
			sprite_sheet.put("fallingPovy", loader.loadImage("/fallingPovy-sheet.png"));
			sprite_sheet.put("endOfOpening", loader.loadImage("/cobit_explosion-sheet.png"));
			sprite_sheet.put("dungeonTiles", loader.loadImage("/Dungeon/rpg-dungeon-pack v1.1(wonderdot)/tiles_dungeon_v1.1.png"));
			sprite_sheet.put("dungeonShadows", loader.loadImage("/Dungeon/rpg-dungeon-pack v1.1(wonderdot)/shadowTile.png"));
			sprite_sheet.put("grogo", loader.loadImage("/grogo_right.png"));
			sprite_sheet.put("grogo2", loader.loadImage("/grogo_left.png"));
			sprite_sheet.put("transition", loader.loadImage("/Transition.png"));
			sprite_sheet.put("health", loader.loadImage("/heart_animated_2.png"));
			sprite_sheet.put("menuActions", loader.loadImage("/attackMenu.png"));
			sprite_sheet.put("purpleOutfit", loader.loadImage("/purpleOutfitOnMap.png"));
			sprite_sheet.put("pauseMenu", loader.loadImage("/pauseMenu.png"));
			sprite_sheet.put("attireMenu", loader.loadImage("/attireMenu.png"));
			sprite_sheet.put("itemMenu", loader.loadImage("/itemScreen.png"));
			sprite_sheet.put("smallHP", loader.loadImage("/Items/SmallHealth.png"));
			sprite_sheet.put("largeHP", loader.loadImage("/Items/largeHealth.png"));
			sprite_sheet.put("maxHP", loader.loadImage("/Items/maxHealth.png"));
			sprite_sheet.put("itemCover", loader.loadImage("/Items/itembg.png"));
			sprite_sheet.put("itemOption", loader.loadImage("/selectItemChoice.png"));
			sprite_sheet.put("crystalAttack", loader.loadImage("/CrystalAttack1.png"));
			sprite_sheet.put("gameOverMenu", loader.loadImage("/GameOverMenu.png"));
			
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
			sprite_sheet.put("elephantTalking", loader.loadImage("/elephantEmblem.png"));
			sprite_sheet.put("zatolibTalking", loader.loadImage("/zatolib_emblem.png"));
			
			sprite_sheet.put("allyMenu", loader.loadImage("/allyMenu.png"));
			sprite_sheet.put("allyMeter", loader.loadImage("/allyMeter.png"));
			
			sprite_sheet.put("smallAttackBoost", loader.loadImage("/Items/smallAttackBoost.png"));
			sprite_sheet.put("smallDefenseBoost", loader.loadImage("/Items/smallDefenseBoost.png"));
			sprite_sheet.put("largeAttackBoost", loader.loadImage("/Items/LargeAttackBoost.png"));
			sprite_sheet.put("largeDefenseBoost", loader.loadImage("/Items/largeDefenseBoost.png"));
			
			sprite_sheet.put("sparkle", loader.loadImage("/sparkle.png"));
			sprite_sheet.put("menuButtons", loader.loadImage("/MenuOptions.png"));
			sprite_sheet.put("leverShadow", loader.loadImage("/leverShad.png"));
			sprite_sheet.put("menuButtons", loader.loadImage("/MenuOptions.png"));
			sprite_sheet.put("lightningTrap", loader.loadImage("/lightning_trap.png"));
			sprite_sheet.put("lightningTrap1", loader.loadImage("/lightning_trap1.png"));
			sprite_sheet.put("crystalConfirm", loader.loadImage("/crystalConfirm.png"));
			sprite_sheet.put("viniaOpening", loader.loadImage("/ViniaOpening.png"));
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		expBarTracker = new ExperienceBar();
		collisionTiles = new HashMap<Integer, Map<String, ArrayList<Integer>>>();
		animationDungeon = new HashMap<String, ArrayList<String>>();
		animationDungeonCounter = new HashMap<String, Integer>();
		SpriteSheet ss = new SpriteSheet(sprite_sheet);
		crystalConfirm = new ArrayList<BufferedImage>();
		crystalConfirm.add(ss.grabImage(1, 1, 48, 48,"crystalConfirm"));
		crystalConfirm.add(ss.grabImage(1, 2, 48, 48,"crystalConfirm"));
		handler = new Handler(this);
		pause = new PauseScreen();
		allies = new AllyPouch();
		hud = new HUD();
		ktp = new KeyToPovy(handler);
		ft = new FightText(handler);
		cc = new CrystalCutscene(handler);
		menu = new Menu(this, handler, hud);
		itemPouch = new ItemPouch();
		costumePouch = new CostumePouch(handler);
		costumePouch.addCostume(new Costume(0, 0, ID.NonEnemy, "blue", handler));
		this.addMouseListener(menu);
		this.addKeyListener(new KeyInput(handler, this));
		this.addKeyListener(ktp);
		this.addKeyListener(cc);
		this.addKeyListener(ft);
		this.addMouseMotionListener(pause);
		this.addMouseMotionListener(menu);
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
				System.out.println("FPS: " + frames);
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
		
		if(gameState == STATE.levelTwoTran){
			map.tick();
		}
		if(gameState == STATE.Game || gameState == STATE.KeyFromGrogo || gameState == STATE.AfterZatolib) {
			hud.tick();
			if(FightText.enemyState == FightText.ENEMYSTATE.INACTIVE) handler.tick();	
			map.tick();
		}
		else if (gameState == STATE.Menu) {
			menu.tick();
		}
		else if(gameState == STATE.Battle) {
			hud.tick();
			battle.tick();
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
	 * if block is on the screen, render it
	 * @param x x value
	 * @param y y valu
	 * @return
	 */
	public static boolean shouldRender(int x, int y) {
		if(x > (Game.camX + 1260 + 240) || x < (Game.camX - 240)) {
			return false;
		}
		if(y > (Game.camY + 1260 + 240) || y < (Game.camY - 240)) {
			return false;
		}
		return true;
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
		if( gameState == STATE.levelTwoTran) {
			map.render(g);
		}
		if(gameState == STATE.KeyFromGrogo) {
			map.render(g);
			hud.render(g);
			ktp.render(g);
			
		}
		else if(gameState == STATE.Game || gameState == STATE.AfterZatolib) {
			map.render(g);
			hud.render(g);
			if(FightText.enemyState != FightText.ENEMYSTATE.INACTIVE) ft.render(g);
		}
		else if (gameState == STATE.Menu) {
			menu.render(g);
		} 
		else if (gameState == STATE.Battle) {
			battle.render(g);
			cc.render(g);
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
		else if(gameState == STATE.Dead) {
			map.render(g);
			hud.render(g);
		}
		else if(gameState == STATE.GameOver) {
			pause.render(g);
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

