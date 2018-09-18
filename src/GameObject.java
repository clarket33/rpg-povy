
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Comparator;
/**
 * 
 * @author clarkt5
 * a game object is any object of the game that can move/change in any way
 */
public abstract class GameObject {
	protected float x, y;
	protected ID id;
	protected float velX, velY, height;
	public GameObject(float x, float y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	public abstract void tick(); 
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	public abstract GameObject copy();
	public abstract void takeDamage(int damage);
	public abstract int getHealth();
	public abstract int getMaxHealth();
	/**
	 * sets x coordinate of the game object
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * sets y coordinate of the game object
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * 
	 * @return x coordinate of object
	 */
	public float getX() {
		return this.x;
	}
	/**
	 * 
	 * @return y coordinate of game object
	 */
	public float getY() {
		return this.y;
	}
	/**
	 * sets the id of the object
	 * @param id
	 */
	public void setId(ID id) {
		this.id = id;
	}
	/**
	 * 
	 * @return id of the object
	 */
	public ID getID() {
		return this.id;
	}
	/**
	 * sets current velocity in the x direction of the object
	 * @param velX
	 */
	public void setVelX(int velX) {
		this.velX = velX;
	}
	/**
	 * sets current velocity in the y direction of the object
	 * @param velY
	 */
	public void setVelY(int velY) {
		this.velY = velY;
	}
	/**
	 * 
	 * @return x velocity of object
	 */
	public float getVelX() {
		return this.velX;
	}
	/**
	 * 
	 * @return y velocity of object
	 */
	public float getVelY() {
		return this.velY;
	}
	/**
	 * 
	 * @return height of the objects, aids in sorting by Y
	 */
	public float getHeight() {
		return this.height;
	}
	
}
/**
 * 
 * @author clarkt5
 * comparator to aid in rendering
 */
class SortbyY implements Comparator<GameObject>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(GameObject a, GameObject b)
    {
        return ((int)a.getY() + (int)(a.getHeight())) - ((int)b.getY() + (int)(b.getHeight()));
    }
}
