import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	private Handler handler;
	private boolean[] keyDown = new boolean[4];
	public KeyInput(Handler handler) {
		this.handler = handler;
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
				if(key == KeyEvent.VK_UP) {temp.setVelY(-7);keyDown[0] = true;}
				if(key == KeyEvent.VK_LEFT) { temp.setVelX(-7);keyDown[1] = true;}
				if(key == KeyEvent.VK_DOWN) { temp.setVelY(7);keyDown[2] = true;}
				if(key == KeyEvent.VK_RIGHT) { temp.setVelX(7);keyDown[3] = true;}
			}
		}
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
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
