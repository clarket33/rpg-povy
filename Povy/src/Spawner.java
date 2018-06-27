import java.util.Random;

public class Spawner {
	
	private Handler handler;
	private HUD hud;
	
	private int scoreKeep;
	private Random r = new Random();
	public static final int WIDTH = 1300, HEIGHT = WIDTH / 12 * 9;
	
	public Spawner(Handler handler, HUD hud) {
		this.handler = handler;
		this.hud = hud;
	}
	
	public void tick() {
		scoreKeep++;
		if(scoreKeep >= 125) {
			scoreKeep = 0;
			hud.setLevel(hud.getLevel() + 1);
			
			
			if(hud.getLevel() == 2) {
				handler.addObject(new BasicEnemy(r.nextInt(WIDTH-56), r.nextInt(HEIGHT-76), ID.BasicEnemy, handler));
			}
			if(hud.getLevel() == 3) {
				handler.addObject(new BasicEnemy(r.nextInt(WIDTH-56), r.nextInt(HEIGHT-76), ID.BasicEnemy, handler));
			}
			if(hud.getLevel() == 4) {
				handler.addObject(new FastEnemy(r.nextInt(WIDTH-56), r.nextInt(HEIGHT-76), ID.FastEnemy, handler));
			}
			if(hud.getLevel() == 5) {
				handler.addObject(new FastEnemy(r.nextInt(WIDTH-56), r.nextInt(HEIGHT-76), ID.FastEnemy, handler));
			}
			if(hud.getLevel() == 6) {
				handler.addObject(new FastEnemy(r.nextInt(WIDTH-56), r.nextInt(HEIGHT-76), ID.FastEnemy, handler));
			}
			if(hud.getLevel() == 7) {
				handler.addObject(new SmartEnemy(r.nextInt(WIDTH-56), r.nextInt(HEIGHT-76), ID.SmartEnemy, handler));
			}
			if(hud.getLevel() == 10) {
				handler.clearEnemies();
				handler.addObject(new EnemyBoss((Game.WIDTH / 2)-48, -120, ID.EnemyBoss, handler));
			}
			
		}
	}
}
