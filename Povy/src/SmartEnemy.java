import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class SmartEnemy extends GameObject{

	private Handler handler;
	private GameObject player;
	
	public SmartEnemy(float x, float y, ID id, Handler handler){
		super(x, y, id);
		
		this.handler = handler;
		
		for(int i = 0; i < handler.objects.size(); i++) {
			if(handler.objects.get(i).getID() == ID.Povy) {
				player = handler.objects.get(i);
			}
		}
		
		
	}
	
	public void tick() {
		x += (velX*4);
		y += (velY*4);
		
		float diffX = x - player.getX();
		float diffY = y - player.getY();
		
		float distance = (float) Math.sqrt((diffX*diffX) + (diffY*diffY));
		
		velX = ((-1/distance) * diffX);
		velY = ((-1/distance) * diffY);
		
		if(y <= 0 || y >= Game.HEIGHT-60) {
			velY *= -1;
		}
		if(x<= 0 || x >= Game.WIDTH-32) {
			velX*= -1;
		}
		
		handler.addObject(new BasicTrail(x, y, ID.Trail, Color.MAGENTA, 25, 25, 0.02f, handler));
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 25, 25);
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g.setColor(Color.MAGENTA);
		g.fillRect((int)this.x, (int)this.y, 25, 25);
	}
}
