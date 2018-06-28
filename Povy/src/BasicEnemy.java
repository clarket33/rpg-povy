import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class BasicEnemy extends GameObject{

	private Handler handler;
	private BufferedImage enemy_image;
	
	public BasicEnemy(float x, float y, ID id, Handler handler){
		super(x, y, id);
		
		this.handler = handler;
		
		velX = 7;
		velY = 7;
		
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		
		enemy_image = ss.grabImage(1, 2, 25, 25);
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
		
		//handler.addObject(new BasicTrail(x, y, ID.Trail, Color.red, 25, 25, 0.02f, handler));
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 25, 25);
	}
	
	public void render(Graphics g) {
		g.drawImage(enemy_image, (int)x, (int)y, null);
	}
}
