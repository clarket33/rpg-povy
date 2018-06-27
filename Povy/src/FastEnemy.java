import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class FastEnemy extends GameObject{

	private Handler handler;
	
	public FastEnemy(float x, float y, ID id, Handler handler){
		super(x, y, id);
		
		this.handler = handler;
		
		velX = 2;
		velY = 10;
	}
	
	public void tick() {
		x += velX;
		y += velY;
		if(y <= 0 || y >= Game.HEIGHT-60) {
			velY *= -1;
		}
		if(x<= 0 || x >= Game.WIDTH-32) {
			velX*= -1;
		}
		
		handler.addObject(new BasicTrail(x, y, ID.Trail, Color.orange, 25, 25, 0.02f, handler));
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 25, 25);
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g.setColor(Color.orange);
		g.fillRect((int)this.x, (int)this.y, 25, 25);
	}
}
