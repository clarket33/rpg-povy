
import java.awt.Graphics;
import java.util.LinkedList;
/**
 * 
 * @author clarkt5
 * Handler is a linked list of game objects, holds every "being" currently in the game
 */
public class Handler {
	LinkedList<GameObject> objects = new LinkedList<GameObject>();
	Game game;
	public static int spd = 3;
	public Handler(Game game) {
		this.game = game;
	}
	
	/**
	 * implements a tick method for all objects
	 */
	public void tick() {
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).tick();
		}
	}
	
	/**
	 * 
	 * @param g
	 * renders all of the objects
	 */
	public void render(Graphics g) {
		/**
		 * implement a sort by the y position of every object to ensure rendering
		 * is done in the correct order
		 */
		objects.sort(new SortbyY());
	
		
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).render(g);
		}
	
	}
	/**
	 * 
	 * @param object
	 * adds an object to the linked list
	 */
	public void addObject(GameObject object) {
		this.objects.add(object);
	}
	/**
	 * 
	 * @param object
	 * removes an object from the linked list,
	 * unchanged if the object is not in the list
	 */
	public void removeObject(GameObject object) {
		this.objects.remove(object);
	}
	/**
	 * clear all of the objects in the linked list
	 */
	public void clear() {
		for(int i = 0; i < this.objects.size(); i++) {
			removeObject(objects.get(i));
			i--;
		}
		
	}

}
