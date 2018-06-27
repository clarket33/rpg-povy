import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BasicTrail extends GameObject{
	
	private float alpha = 1, life;
	private Handler handler;
	private Color color;
	private int width;
	private int height;
	
	public BasicTrail(float x, float y, ID id, Color color, int width, int height, float life, Handler handler){
		super(x, y, id);
		this.handler = handler;
		this.color = color;
		this.width = width;
		this.height = height;
		this.life = life;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(alpha > life) {
			alpha -= life - 0.001f;
		}else handler.removeObject(this);
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(makeTransparent(alpha));
		
		g.setColor(color);
		g.fillRect((int)x, (int)y, width, height);
		
		g2.setComposite(makeTransparent(1));
		
	}
	
	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
