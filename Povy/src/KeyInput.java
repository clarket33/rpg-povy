import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	private Handler handler;
	private Game game;
	private boolean[] keyDown = new boolean[4];
	public KeyInput(Handler handler, Game game) {
		this.handler = handler;
		this.game = game;
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject temp = handler.objects.get(i);
			if(temp.getID() == ID.Povy) {
				//key Events for Povy
				if(key == KeyEvent.VK_UP) {temp.setVelY(-handler.spd);keyDown[0] = true;}
				if(key == KeyEvent.VK_LEFT) { temp.setVelX(-handler.spd);keyDown[1] = true;}
				if(key == KeyEvent.VK_DOWN) { temp.setVelY(handler.spd);keyDown[2] = true;}
				if(key == KeyEvent.VK_RIGHT) { temp.setVelX(handler.spd);keyDown[3] = true;}
			}
		}
		
		if(key == KeyEvent.VK_SPACE) {
			if(game.gameState == Game.STATE.Game) {
				if(Game.paused) {
					Game.paused = false;
				}
				else {
					Game.paused = true;
				}
			}
		}
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		
		if(key == KeyEvent.VK_S) {
			if(game.gameState == Game.STATE.Game)
				game.gameState = Game.STATE.Shop;
			else if(game.gameState == Game.STATE.Shop) {
				game.gameState = Game.STATE.Game;
			}
		}
	}
	
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject temp = handler.objects.get(i);
			if(temp.getID() == ID.Povy) {
				//key Events for Povy
				if(key == KeyEvent.VK_UP) keyDown[0] = false;
				if(key == KeyEvent.VK_LEFT)	keyDown[1] = false;
				if(key == KeyEvent.VK_DOWN) keyDown[2] = false;
				if(key == KeyEvent.VK_RIGHT) keyDown[3] = false;
				//vertical movement
				if(!keyDown[0] && !keyDown[2]) temp.setVelY(0);
				//horizontal movement
				if(!keyDown[1] && !keyDown[3]) temp.setVelX(0);
			}
		}
	}

}
