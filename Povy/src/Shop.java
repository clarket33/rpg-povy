import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Shop extends MouseAdapter{
	Handler handler;
	private int B1 = 1000;
	private int B2 = 1000;
	private int B3 = 1000;
	HUD hud;
	
	public Shop(Handler handler, HUD hud){
		this.handler =handler;
		this.hud = hud;
	}
	
	public void render(Graphics g) {
		Font fo = new Font("algerian", 1, 100);
		
		g.setFont(fo);
		g.setColor(Color.GREEN);
		g.drawString("SHOP", 530, 150);
		
		fo = new Font("arial", 1, 14);
		g.setFont(fo);
		g.drawString("Upgrade Health", 170, 490);
		g.drawString("Cost: " + B1, 190, 513);
		g.drawRect(130, 387, 200, 200);
		
		g.drawString("Upgrade Speed", 570, 490);
		g.drawString("Cost: " + B2, 590, 513);
		g.drawRect(530, 387, 200, 200);
		
		g.drawString("Refill Health", 970, 490);
		g.drawString("Cost: " + B3, 990, 513);
		g.drawRect(930, 387, 200, 200);
		
		g.drawString("SCORE: " + hud.getScore(), 480, 250);
		g.drawString("Press S to return to Game", 480, 280);
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx >= x && mx <= x + width) {
			if(my >= y && my <= y + height) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		//box 1
		if(mouseOver(mx, my, 130, 387, 200, 200) && hud.getScore() >= B1) {
			hud.setScore(hud.getScore()-B1);
			B1 += 1000;
			hud.bounds += 20;
			hud.HEALTH = 100 + (hud.bounds/2);
		}
		//box 2
		if(mouseOver(mx, my, 530, 387, 200, 200) && hud.getScore() >= B2) {
			hud.setScore(hud.getScore()-B2);
			B2 += 1000;
			handler.spd++;
		}
		//box 3
		if(mouseOver(mx, my, 930, 387, 200, 200) && hud.getScore() >= B3) {
			hud.setScore(hud.getScore()-B3);
			hud.HEALTH = 100 + (hud.bounds/2);
		}
	}

}
