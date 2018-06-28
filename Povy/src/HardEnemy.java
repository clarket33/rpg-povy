import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class HardEnemy extends GameObject{

	private Handler handler;
	private Random r;
	private BufferedImage enemy_image;
	
	public HardEnemy(float x, float y, ID id, Handler handler){
		super(x, y, id);
		r = new Random();
		
		this.handler = handler;
		
		velX = 7;
		velY = 7;
		
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		
		enemy_image = ss.grabImage(1, 3, 25, 25);
	}
	
	public void tick() {
		x += velX;
		y += velY;
		if(y <= 0 || y >= Game.HEIGHT-60) {
			if(y <= 0) {
				velY = (r.nextInt(6) + 12);
			}
			else {
				velY = -(r.nextInt(6) + 12);
			}
		}
		if(x<= 0 || x >= Game.WIDTH-32) {
			if(x <= 0) {
				velX = (r.nextInt(6) + 1);
			}
			else {
				velX = -(r.nextInt(6) + 1);
			}
		}
		
		//handler.addObject(new BasicTrail(x, y, ID.Trail, Color.YELLOW, 25, 25, 0.02f, handler));
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 25, 25);
	}
	
	public void render(Graphics g) {
		g.drawImage(enemy_image, (int)x, (int)y, null);
	}
}
