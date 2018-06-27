import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	LinkedList<GameObject> objects = new LinkedList<GameObject>();
	Game game;
	public Handler(Game game) {
		this.game = game;
	}
	
	public void tick() {
		for(int i = 0; i < objects.size(); i++) {
			GameObject tempObject =  objects.get(i);
			tempObject.tick();
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < objects.size(); i++) {
			GameObject tempObject =  objects.get(i);
			tempObject.render(g);
		}
	}
	
	public void addObject(GameObject object) {
		this.objects.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.objects.remove(object);
	}

	public void clearEnemies() {
		for(int i = 0; i < objects.size(); i++) {
			if(objects.get(i).getID() != ID.Povy) {
				removeObject(objects.get(i));
				i--;
			}
			else {
				if(game.gameState == Game.STATE.End) {
					removeObject(objects.get(i));
					i--;
				}
			}
		}
		
	}

}
