import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -1442798787354930462L;

	public static final int WIDTH = 1300, HEIGHT = WIDTH / 12 * 9;
	
	private Thread thread;
	private boolean running = false;
	private Handler handler;
	private HUD hud;
	private Spawner spawner;
	private Menu menu;
	public static boolean paused = false;
	private Shop shop;
	
	public enum STATE{
		Menu,
		Help,
		End,
		Shop,
		Game
	};
	
	public STATE gameState = STATE.Menu;
	
	public static BufferedImage sprite_sheet;
	
	public Game() {
		
		BufferedImageLoader loader = new BufferedImageLoader();
		
		try {
			sprite_sheet = loader.loadImage("/sprite_board.png");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		handler = new Handler(this);
		hud = new HUD();
		shop = new Shop(handler, hud);
		menu = new Menu(this, handler, hud);
		this.addMouseListener(menu);
		this.addMouseListener(shop);
		this.addKeyListener(new KeyInput(handler, this));
		AudioPlayer.load();
		AudioPlayer.getMusic("music").loop();
		new Window(WIDTH, HEIGHT, "Povy the Alien", this);
		
		spawner = new Spawner(handler, hud);
		
		
	}

	@Override
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
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	private void tick() {
		
		if(gameState == STATE.Game) {
			if(!paused) {
				hud.tick();
				spawner.tick();
				handler.tick();
				
				if(hud.HEALTH <= 0) {
					hud.HEALTH = 100;
					gameState = STATE.End;
					handler.clearEnemies();
				}
			}	
		}
		else if (gameState == STATE.Menu) {
			menu.tick();
		}
	}
	
	public static float clamp(float var, float min, float max) {
		if(var >= max) {
			return var = max;
		}
		else if(var <= min){
			return var = min;
		}
		else {
			return var;
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if(paused) {
			Font fo = new Font("arial", 2, 50);
			g.setFont(fo);
			g.setColor(Color.pink);
			g.drawString("PAUSED", 550, 487);
		}
		if(gameState == STATE.Game) {
			handler.render(g);
			hud.render(g);
		}else if(gameState == STATE.Shop) {
			shop.render(g);
		}
		else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End) {
			handler.render(g);
			menu.render(g);
		} 
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		new Game();
	}

}
