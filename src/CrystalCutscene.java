import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * class for the handling of the final sequence of level 1 where the crystal's
 * energy saves Povy
 * @author clarkt5
 *
 */
public class CrystalCutscene extends KeyAdapter{
	private Map<Integer, char[]> dialogue;
	private char[] copy;
	private int count = 0;
	private boolean done;
	private ArrayList<BufferedImage> text;
	private int doneCount = 0;
	private int changeCount = 0, animate = 1;
	private Handler handler;
	private int buffer = 300;
	public static CRYSTALSTATE crystalState;
	
	/**
	 * makes the scene, loads in the text
	 * @param handler
	 */
	public CrystalCutscene(Handler handler) {
	    SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
	    
	    this.handler = handler;
		
	    text = new ArrayList<BufferedImage>();
	    text.add(ss.grabImage(1, 1, 1080, 285,"dialogue"));
		text.add(ss.grabImage(1, 2, 1080, 285,"dialogue"));
		text.add(ss.grabImage(1, 3, 1080, 285,"dialogue"));
		
		done = false;
		
		dialogue = new HashMap<Integer, char[]>();
	    String str = "Oh no!";
	    String str1 = "Wait, what's happening!?!?";
	    String str2 = "It's the crystal!";
	    String str3 = ".";
	    dialogue.put(new Integer(0), str.toCharArray());
	    dialogue.put(new Integer(1), str1.toCharArray());
	    dialogue.put(new Integer(2), str2.toCharArray());
	    dialogue.put(new Integer(3), str3.toCharArray());
	    
	    copy = new char[dialogue.get(new Integer(0)).length];
	}
	
	public enum CRYSTALSTATE{
		PovyDead,
		PovyRise,
		UsingCrystal,
		ZatolibDies;
	};
	
	
	/**
	 * if enter is pressed before the dialogue is out, speed up the dialogue to the end
	 * if it's pressed after the dialogue is out, move on to the next piece of dialogue
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ENTER) {
			if(Game.gameState == Game.STATE.Battle && Battle.battleState == Battle.BATTLESTATE.CrystalCutscene) {
				if(crystalState == CRYSTALSTATE.PovyDead || crystalState == CRYSTALSTATE.PovyRise || crystalState == CRYSTALSTATE.UsingCrystal) {
					if(done && buffer == 0) {
						
						doneCount += 1;
						if(doneCount == 2) {
							buffer = 500;
						}
						else {
							buffer = 300;
						}
						count = 0;
						copy = new char[dialogue.get(new Integer(doneCount)).length];
						done = false;
						
						if(doneCount == 1) {
							AudioPlayer.getMusic("crystalScene").loop(1, (float).1);
							crystalState = CRYSTALSTATE.PovyRise;
						}
						if(doneCount == 2) {
							crystalState = CRYSTALSTATE.UsingCrystal;
						}
						if(doneCount == 3) {
							if(AudioPlayer.getSound("crystalPath").playing() == true) {
								AudioPlayer.getSound("crystalPath").stop();
								if(AudioPlayer.getMusic("crystalScene").playing()) {
									AudioPlayer.getMusic("crystalScene").stop();
								}
							}
							crystalState = CRYSTALSTATE.ZatolibDies;
						}
						
						
					}
				}
			}
		}
	}
	
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	/**
	 * draws the text
	 * @param g
	 */
	public void render(Graphics g) {
		if(Game.gameState == Game.STATE.Battle && Battle.battleState == Battle.BATTLESTATE.CrystalCutscene) {
			if(crystalState == CRYSTALSTATE.PovyDead || crystalState == CRYSTALSTATE.PovyRise || crystalState == CRYSTALSTATE.UsingCrystal) {
				if(done) {
					buffer -=1;
					if(buffer <= 0) buffer = 0;
				}
				Font fo = new Font("verdana", 1, 40);
				g.setColor(new Color(106, 215, 48));
				g.setFont(fo);
				if(!done) {
					g.drawImage(text.get(0), Game.camX + 120, Game.camY + 625, null);
					handleScene(doneCount,g);
					
				}
				else if(done && buffer > 0) {
					g.drawImage(text.get(0), Game.camX + 120, Game.camY + 625, null);
					handleScene(doneCount,g);
				}
				else {
					g.drawImage(text.get(animate), Game.camX + 120, Game.camY + 625, null);
					
					changeCount++;
					if(changeCount % 30 == 0) {
						animate++;
					}
					if(animate == 3) {
						animate = 1;
						changeCount = 0;
					}
					
					handleScene(doneCount,g);
					
				}
			}
		}
	}

	
	
	/**
	 * aids in the rendering of the line
	 * @param sceneNumber
	 * @param g
	 */
	private void handleScene(int sceneNumber, Graphics g) {
		if(count == dialogue.get(new Integer(sceneNumber)).length) {
			done = true;
			
			g.drawChars(copy, 0, copy.length, Game.camX + 300, Game.camY + 770);
		}
		else {
			copy[count] = dialogue.get(new Integer(sceneNumber))[count];
			AudioPlayer.getSound("type").play(1, (float).3);
			count++;
			
			g.drawChars(copy, 0, copy.length, Game.camX + 300, Game.camY + 770);
		}
	}

}



