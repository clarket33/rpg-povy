import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Minions extends GameObject{

	private Handler handler;
	
	public Minions(float x, float y, ID id, Handler handler){
		super(x, y, id);
		
		this.handler = handler;
		Random r = new Random();
		velX = r.nextInt((5-(-5)) + -5);
		velY = 7;
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		if(y >= Game.HEIGHT || y <= 0) handler.removeObject(this);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 13, 13);
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g.setColor(Color.white);
		g.fillRect((int)this.x, (int)this.y, 13, 13);
	}
}
