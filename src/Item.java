import java.awt.image.BufferedImage;
/**
 * 
 * @author clarkt5
 * Items are things that Povy can use to aid him in the game, stored in the item pouch
 * can be consumed in game or in battle
 */
public class Item {
	private ItemType it;
	private BufferedImage itemImage;
	
	/**
	 * creates and loads in images for the item
	 * @param it
	 */
	public Item(ItemType it) {
		this.it = it;
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		if(it == ItemType.SmallHP) {
			itemImage = ss.grabImage(1, 1, 32, 32, "smallHP");
		}
		else if(it == ItemType.LargeHP) {
			itemImage = ss.grabImage(1, 1, 32, 32, "largeHP");
		}
		else if(it == ItemType.MaxHP) {
			itemImage = ss.grabImage(1, 1, 32, 32, "maxHP");
		}
		
	}
	/**
	 * possible item types
	 * @author clarkt5
	 *
	 */
	public enum ItemType{
		SmallHP,
		LargeHP,
		MaxHP;
	};
	
	/**
	 * returns string representation of the item
	 */
	public String toString() {
		return it.toString();
	}
	
	/**
	 * uses the item, has an effect on Povy
	 */
	public void use() {
		if(it == ItemType.SmallHP) {
			HUD.HEALTH += 16;
			if(HUD.HEALTH> HUD.maxHealth) {
				HUD.HEALTH = HUD.maxHealth;
			}
		}
		else if(it == ItemType.LargeHP) {
			HUD.HEALTH += 32;
			if(HUD.HEALTH> HUD.maxHealth) {
				HUD.HEALTH = HUD.maxHealth;
			}
		}
		else if(it == ItemType.MaxHP) {
			HUD.HEALTH = HUD.maxHealth;
		}
		
	}
	
	/**
	 * 
	 * @return image representation of the item
	 */
	public BufferedImage getImage() {
		return itemImage;
	}
	
	/**
	 * aids in checking if items are equal to each other
	 */
	public boolean equals(Object i) {
		 // If the object is compared with itself then return true  
        if (i == this) {
            return true;
        }
 
        /* Check if i is an instance of Item or not
          "null instanceof [type]" also returns false */
        if (!(i instanceof Item)) {
            return false;
        }
         
        // typecast i to Item so that we can compare data members 
        Item c = (Item) i;
		if(this.toString().contains(i.toString())) {
			return true;
		}
		return false;
	}
	
	 @Override
	 /**
	 * aids in checking if items are equal to each other
	 */
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + 1;
        hash = hash * 31 + it.hashCode();
        return hash;
    }

}

