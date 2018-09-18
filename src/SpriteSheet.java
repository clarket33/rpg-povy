
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * SpriteSheet is a map of images that is used to aid in animation by gathering
 * all images in one map
 * @author clarkt5
 *
 */
public class SpriteSheet {
	private Map<String, BufferedImage> sprite;
	public SpriteSheet(Map<String, BufferedImage> ss) {
		sprite = new HashMap<String, BufferedImage>();
		this.sprite = ss;
	}
	
	public BufferedImage grabImage(int row, int col, int width, int height, String type) {
		BufferedImage img = sprite.get(type).getSubimage((col*width)-width, (row*height)-height, width, height);
		return img;
	}

}
