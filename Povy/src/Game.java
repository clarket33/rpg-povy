import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -1442798787354930462L;

	public static final int WIDTH = 1300, HEIGHT = WIDTH / 12 * 9;
	
	private Thread thread;
	private boolean running = false;
	private Handler handler;
	private HUD hud;
	private Random r;
	private Spawner spawner;
	private Menu menu;
	
	public enum STATE{
		Menu,
		Help,
		End,
		Game
	};
	
	public STATE gameState = STATE.Menu;
	
	public Game() {
		// TODO Auto-generated constructor stub
		handler = new Handler(this);
		hud = new HUD();
		menu = new Menu(this, handler, hud);
		this.addMouseListener(menu);
		this.addKeyListener(new KeyInput(handler));
		new Window(WIDTH, HEIGHT, "Povy the Alien", this);
		spawner = new Spawner(handler, hud);
		r = new Random();
		for(int i = 0; i <= 1000; i+=50) {
			handler.addObject(new MenuParticle(10, i, ID.MenuParticle, handler));
			handler.addObject(new MenuParticle(1250, 1000-i+25, ID.MenuParticle, handler));
		}
		
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
		handler.tick();
		if(gameState == STATE.Game) {
			hud.tick();
			spawner.tick();
			if(hud.HEALTH <= 0) {
				hud.HEALTH = 100;
				gameState = STATE.End;
				handler.clearEnemies();
				for(int i = 0; i <= 1000; i+=50) {
					handler.addObject(new MenuParticle(10, i, ID.MenuParticle, handler));
					handler.addObject(new MenuParticle(1250, 1000-i+25, ID.MenuParticle, handler));
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
		
		handler.render(g);
		if(gameState == STATE.Game) {
			hud.render(g);
		}else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End) {
			menu.render(g);
		} 
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		new Game();
	}

}
