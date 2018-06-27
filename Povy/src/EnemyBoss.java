
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class EnemyBoss extends GameObject{

	private Handler handler;
	Random r = new Random();
	private int timer = 50;
	private int timer2 = 50;
	
	public EnemyBoss(float x, float y, ID id, Handler handler){
		super(x, y, id);
		this.handler = handler;
		Random r = new Random();
		velX = 0;
		velY = 2;
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		if(timer <= 0) {velY = 0;}
		else {timer--;}
		
		if(timer <= 0) timer2 --;
		if(timer2 <= 0) {
			if(velX == 0) velX = 8;
			int spawn = r.nextInt(10);
			if(spawn == 0) {
				handler.addObject(new Minions((int)x+48, (int)y+48, ID.Minion, handler));
				handler.addObject(new Minions((int)x+48, (int)y+48, ID.Minion, handler));
				handler.addObject(new Minions((int)x+48, (int)y+48, ID.Minion, handler));
			}
		}
		if(x<= -100 || x >= Game.WIDTH-100) {
			velX*= -1;
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 96, 96);
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g.setColor(Color.white);
		g.fillRect((int)this.x, (int)this.y, 96, 96);
	}
}
