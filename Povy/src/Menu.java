import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;


public class Menu extends MouseAdapter{
	private Game game;
	private Handler handler;
	private Random r;
	private HUD hud;
	
	public Menu(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		r = new Random();
		this.hud = hud;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		//back in help menu
		if(game.gameState == Game.STATE.Help) {
			//return to main menu
			if(mouseOver(mx, my, 460, 600, 400, 100)) {
				AudioPlayer.getSound("click").play();
				game.gameState = Game.STATE.Menu;
				return;
			}
		}
		if(game.gameState == Game.STATE.Menu) {
			//play button
			if(mouseOver(mx, my, 460, 300, 400, 100)) {
				AudioPlayer.getSound("click").play();
				game.gameState = Game.STATE.Game;
				handler.clearEnemies();
				handler.addObject(new Povy(100, 100, ID.Povy, handler));
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-56), r.nextInt(Game.HEIGHT-76), ID.BasicEnemy, handler));
			}
			
			
			
			//quit
			if(mouseOver(mx, my, 460, 600, 400, 100)) {
				System.exit(0);
			}
			
			//help
			if(mouseOver(mx, my, 460, 450, 400, 100)) {
				AudioPlayer.getSound("click").play();
				game.gameState = Game.STATE.Help;
			}
		}
		
		if(game.gameState == Game.STATE.End) {
			//exit
			if(mouseOver(mx, my, 460, 600, 400, 100)) {
				System.exit(0);
			}
			if(mouseOver(mx, my, 460, 450, 400, 100)) {
				AudioPlayer.getSound("click").play();
				game.gameState = Game.STATE.Game;
				handler.clearEnemies();
				handler.addObject(new Povy(100, 100, ID.Povy, handler));
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-56), r.nextInt(Game.HEIGHT-76), ID.BasicEnemy, handler));
				hud.setLevel(1);
				hud.setScore(0);
				
			}
		}
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void tick() {
		
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
	
	public void render(Graphics g) {
		for(int i = 0; i <= 1000; i+=50) {
			g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
			g.fillRect(10, i, 25, 25);
			g.fillRect(1250, 1000-i, 25, 25);
		}
		if(game.gameState == Game.STATE.Menu) {
			Font fo = new Font("algerian", 1, 100);
			
			g.setFont(fo);
			g.setColor(Color.BLACK);
			g.fillRect(530, 75, 275, 85);
			g.setColor(Color.GREEN);
			g.drawString("POVY", 530, 150);
			
			fo = new Font("arial", 1, 50);
			g.setFont(fo);
			
			g.setColor(Color.BLACK);
			g.fillRect(460, 300, 400, 100);
			g.setColor(Color.WHITE);
			g.drawRect(460, 300, 400, 100);
			g.drawString("Play", 600, 370);
			
			g.setColor(Color.BLACK);
			g.fillRect(460, 450, 400, 100);
			g.setColor(Color.WHITE);
			g.drawRect(460, 450, 400, 100);
			g.drawString("Help", 600, 520);
			
			g.setColor(Color.BLACK);
			g.fillRect(460, 600, 400, 100);
			g.setColor(Color.WHITE);
			g.drawRect(460, 600, 400, 100);
			g.drawString("Quit", 600, 670);
		}
		else if(game.gameState == Game.STATE.Help){
			Font fo = new Font("algerian", 1, 100);
			
			g.setFont(fo);
			g.setColor(Color.white);
			g.drawString("Help", 530, 150);
			
			
			fo = new Font("arial", 1, 25);
			g.setFont(fo);
			g.drawString("Use the arrow keys to dodge enemies and survive"
					+ " for as long as you can", 250, 300);
			g.drawString("Use the space bar to pause", 500, 400);
			
			
			fo = new Font("arial", 1, 50);
			g.setFont(fo);
			g.setColor(Color.BLACK);
			g.fillRect(460, 600, 400, 100);
			g.setColor(Color.white);
			g.drawRect(460, 600, 400, 100);
			g.drawString("Back", 600, 670);
		}
		
		else if(game.gameState == Game.STATE.End) {
			Font fo = new Font("algerian", 1, 100);
			
			g.setFont(fo);
			g.setColor(Color.white);
			g.drawString("Game Over", 350, 150);
			
			
			fo = new Font("arial", 1, 25);
			g.setFont(fo);
			g.drawString("You survived up to level " + hud.getLevel(), 480, 300);
			
			
			fo = new Font("arial", 1, 50);
			g.setFont(fo);
			g.setColor(Color.BLACK);
			g.fillRect(460, 450, 400, 100);
			g.setColor(Color.white);
			g.drawRect(460, 450, 400, 100);
			g.drawString("Play", 600, 520);
			
			g.setColor(Color.BLACK);
			g.fillRect(460, 600, 400, 100);
			g.setColor(Color.white);
			g.drawRect(460, 600, 400, 100);
			g.drawString("Quit", 600, 670);
		}
		
	}

}
