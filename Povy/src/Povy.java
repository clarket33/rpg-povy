import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Povy extends GameObject{
	
	Handler handler;
	private BufferedImage player_image;
	
	public Povy(int x, int y, ID id, Handler handler){
		super(x, y, id);
		this.handler = handler;
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		
		player_image = ss.grabImage(1, 1, 50, 50);
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		x = Game.clamp((int)x, 0, Game.WIDTH-56);
		y = Game.clamp((int)y, 0, Game.HEIGHT-76);
		
		collision();
	}
	public void collision() {
		for(int i = 0; i < handler.objects.size(); i++) {
			GameObject temp = handler.objects.get(i);
			if(temp.getID() == ID.BasicEnemy || temp.getID() == ID.FastEnemy || temp.getID() == ID.SmartEnemy || temp.getID() == ID.EnemyBoss ||
					temp.getID() == ID.Minion) {
				if(getBounds().intersects(temp.getBounds())) {
					//collides
					HUD.HEALTH -= 2;
				}
			}
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 50, 50);
	}
	
	public void render(Graphics g) {
		//g.setColor(Color.green);
			
			
		//g.fillRect((int)this.x, (int)this.y, 50, 50);
		g.drawImage(player_image, (int)x, (int)y, null);
	}

}
