import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class MenuParticle extends GameObject{

	private Handler handler;
	Random r = new Random();
	private Color col;
	int dir = 0;
	
	public MenuParticle(float x, float y, ID id, Handler handler){
		super(x, y, id);
		
		this.handler = handler;
		
		velX = 5;
		velY = 0;
		col = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
		
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
		
		handler.addObject(new BasicTrail(x, y, ID.Trail, col, 25, 25, 0.02f, handler));
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 25, 25);
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(col);
		
		g.fillRect((int)this.x, (int)this.y, 25, 25);
	}
}

