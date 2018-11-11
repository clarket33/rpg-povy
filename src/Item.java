import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
		else if(it == ItemType.SmallAttackBoost) {
			itemImage = ss.grabImage(1, 1, 32, 32, "smallAttackBoost");
		}
		else if(it == ItemType.SmallDefenseBoost) {
			itemImage = ss.grabImage(1, 1, 32, 32, "smallDefenseBoost");
		}
		else if(it == ItemType.LargeAttackBoost) {
			itemImage = ss.grabImage(1, 1, 32, 32, "largeAttackBoost");
		}
		else if(it == ItemType.LargeDefenseBoost) {
			itemImage = ss.grabImage(1, 1, 32, 32, "largeDefenseBoost");
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
		SmallAttackBoost,
		LargeAttackBoost,
		SmallDefenseBoost,
		LargeDefenseBoost,
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
		if(Game.gameState == Game.STATE.Battle) {
			if(it == ItemType.SmallAttackBoost) {
				Battle.smABoost = true;
				Battle.lgABoost = false;
				Battle.attackCount = 0;
			}
			else if(it == ItemType.SmallDefenseBoost) {
				Battle.smDBoost = true;
				Battle.lgDBoost = false;
				Battle.defenseCount = 0;
			}
			else if(it == ItemType.LargeAttackBoost) {
				Battle.lgABoost = true;
				Battle.smABoost = false;
				Battle.attackCount = 0;
			}
			else if(it == ItemType.LargeDefenseBoost) {
				Battle.lgDBoost = true;
				Battle.smDBoost = false;
				Battle.defenseCount = 0;
			}
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
	 * 
	 * @return description of the item in list format
	 */
	public ArrayList<String> itemDescript() {
		ArrayList<String> lst;
		lst = new ArrayList<String>();
		if(it == ItemType.SmallHP) {
			lst.add("Replenishes a single heart of health(16)");
		}
		else if(it == ItemType.LargeHP) {
			lst.add("Replenishes two hearts of health(32)");
		}
		else if(it == ItemType.MaxHP) {
			lst.add("Replenishes all of Povy's health");
		}
		else if(it == ItemType.SmallAttackBoost) {
			lst.add("Gives Povy a small attack boost for 3 turns");
			lst.add("(Ineffective when used outside of battle)");
			lst.add("(Overrides any attack boost currently in use)");
		}
		else if(it == ItemType.SmallDefenseBoost) {
			lst.add("Gives Povy a small defense boost for 3 turns");
			lst.add("(Ineffective when used outside of battle)");
			lst.add("(Overrides any defense boost currently in use)");
		}
		else if(it == ItemType.LargeAttackBoost) {
			lst.add("Gives Povy a large attack boost for 3 turns");
			lst.add("(Ineffective when used outside of battle)");
			lst.add("(Overrides any attack boost currently in use)");
		}
		else if(it == ItemType.LargeDefenseBoost) {
			lst.add("Gives Povy a large defense boost for 3 turns");
			lst.add("(Ineffective when used outside of battle)");
			lst.add("(Overrides any defense boost currently in use)");
		}
		return lst;
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

