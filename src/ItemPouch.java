import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
/**
 * the ItemPouch stores all of the items Povy currently has
 * @author clarkt5
 *
 */
public class ItemPouch {
	private Map<Item, Integer> items;
	
	public ItemPouch() {
		items = new LinkedHashMap<Item, Integer>();
		//addItem(new Item(Item.ItemType.SmallHP));
		
		
		
	}
	public Set<Item> getItems() {
		return items.keySet();
	}
	public void addItem(Item i) {
		if(items.get(i) == null) {
			items.put(i, new Integer(1));
		}
		else {
			items.put(i, items.get(i)+ 1);
		}
	}
	
	/**
	 * removes an item from the item pouch
	 * @param i
	 */
	public void removeItem(Item i) {
		if(items.get(i) != null) {
			if(items.get(i)>1) {
				items.put(i, items.get(i)-1);
			}
			else {
				items.remove(i);
			}
		}
	}
	
	/**
	 * uses the item from the item pouch, then performs a removeItem on it
	 * @param num
	 */
	public void useItem(int num) {
		Iterator<Item> itr = items.keySet().iterator();
		int count= 0;
		while(itr.hasNext()) {
			Item cur = itr.next();
			if(count == num) {
				cur.use();
				removeItem(cur);
				break;
			}
			count++;
		}
	}
	
	/**
	 * aids in showing the description of the item when the user is hovering over it
	 * @param num
	 */
	public Item getItem(int num) {
		Iterator<Item> itr = items.keySet().iterator();
		int count= 0;
		while(itr.hasNext()) {
			Item cur = itr.next();
			if(count == num) {
				//System.out.println(cur.toString());
				return cur;
			}
			count++;
		}
		return null;
	}
	
	/**
	 * prints the pouch(used for error checking, not implemented in-game)
	 */
	public String toString() {
		String s = "Item Pouch:\n";
		Iterator<Item> itr = items.keySet().iterator();
		while(itr.hasNext()) {
			Item cur = itr.next();
			s += cur.toString();
			s+= ": ";
			s += items.get(cur).toString();
		}
		return s;
	}
	
	/**
	 * get the size of the item pouch
	 * @return
	 */
	public int getItemAmnt() {
		return items.size();
	}
	
	/**
	 * renders the Pouch in pause Menu/battle menu
	 * @param g
	 */
	public void render(Graphics g) {
		String s = "";
		Iterator<Item> itr = items.keySet().iterator();
		g.setFont(new Font("verdana", 1, 18));
		g.setColor(new Color(106, 215, 48));
		int imgY = 198;
		int y = Game.camY + 245-g.getFontMetrics().getHeight();
		while(itr.hasNext()) {
			Item cur = itr.next();
			//System.out.println(cur.toString());
			s += cur.toString();
			s+= " x";
			s += items.get(cur).toString();
			g.drawString(s, Game.camX + 560, y + g.getFontMetrics().getHeight());
			g.drawImage(cur.getImage(), Game.camX+482, Game.camY+imgY, null);
			y+=g.getFontMetrics().getHeight()*3.6;
			imgY += 82;
			s = "";
		}
		//System.out.println();
			
	}

}
