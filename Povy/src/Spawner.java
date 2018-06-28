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
				handler.addObject(new SmartEnemy(r.nextInt(WIDTH-56), r.nextInt(HEIGHT-76), ID.SmartEnemy, handler));
				handler.addObject(new SmartEnemy(r.nextInt(WIDTH-56), r.nextInt(HEIGHT-76), ID.SmartEnemy, handler));
			}
			if(hud.getLevel() == 10) {
				handler.clearEnemies();
				handler.addObject(new EnemyBoss((Game.WIDTH / 2)-48, -120, ID.EnemyBoss, handler));
			}
			if(hud.getLevel() == 17) {
				handler.clearEnemies();
				handler.addObject(new HardEnemy(r.nextInt(WIDTH-56), r.nextInt(HEIGHT-76), ID.BasicEnemy, handler));
				handler.addObject(new HardEnemy(r.nextInt(WIDTH-56), r.nextInt(HEIGHT-76), ID.BasicEnemy, handler));
				handler.addObject(new HardEnemy(r.nextInt(WIDTH-56), r.nextInt(HEIGHT-76), ID.BasicEnemy, handler));
			}
			
			if((hud.getLevel() >= 20 && hud.getLevel() <= 30) || (hud.getLevel() >= 45 && hud.getLevel() <= 55)) {
				int num = r.nextInt(4);
				if(num == 0) {
					handler.addObject(new BasicEnemy(r.nextInt(WIDTH-56), r.nextInt(HEIGHT-76), ID.BasicEnemy, handler));
				}
				if(num == 1) {
					handler.addObject(new FastEnemy(r.nextInt(WIDTH-56), r.nextInt(HEIGHT-76), ID.FastEnemy, handler));
				}
				if(num == 2) {
					handler.addObject(new SmartEnemy(r.nextInt(WIDTH-56), r.nextInt(HEIGHT-76), ID.SmartEnemy, handler));
				}
				if(num == 3) {
					handler.addObject(new HardEnemy(r.nextInt(WIDTH-56), r.nextInt(HEIGHT-76), ID.BasicEnemy, handler));
				}
			}
			if(hud.getLevel() == 44) {
				handler.clearEnemies();
			}
			if(hud.getLevel() == 35) {
				handler.clearEnemies();
				handler.addObject(new EnemyBoss((Game.WIDTH / 2)-48, -120, ID.EnemyBoss, handler));
			}
			
			if(hud.getLevel() == 60) {
				handler.addObject(new EnemyBoss((Game.WIDTH / 2)-48, -120, ID.EnemyBoss, handler));
			}
			
		}
	}
}
