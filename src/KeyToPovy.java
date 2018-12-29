import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
/**
 * class for the handling of the opening sequence involving gaining Grogo as an ally
 * @author clarkt5
 *
 */
public class KeyToPovy extends KeyAdapter{
	private Map<Integer, char[]> dialogue;
	private char[] copy, copy2;
	private int count = 0;
	private boolean done;
	private ArrayList<BufferedImage> text;
	private ArrayList<BufferedImage> grogoEmblem;
	private BufferedImage dungeonKey;
	private int countLine2 = 0; 
	private int doneCount = 0;
	private int talkCount = 0;
	private int changeCount = 0, animate = 1;
	private Handler handler;
	private boolean speedChanged = false;
	private int buffer = 10;
	
	/**
	 * makes the scene, loads in the text
	 * @param handler
	 */
	public KeyToPovy(Handler handler) {
	    SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
	    
	    this.handler = handler;
	    dungeonKey = null;
		
	    try {
	        dungeonKey = ImageIO.read(new File("res/dungeonKey.png"));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    
	    grogoEmblem = new ArrayList<BufferedImage>();
	    grogoEmblem.add(ss.grabImage(1, 1, 104, 104,"grogoTalking"));
		grogoEmblem.add(ss.grabImage(1, 2, 104, 104,"grogoTalking"));
		
	    text = new ArrayList<BufferedImage>();
	    text.add(ss.grabImage(1, 1, 680, 150,"dialogue"));
		text.add(ss.grabImage(1, 2, 680, 150,"dialogue"));
		text.add(ss.grabImage(1, 3, 680, 150,"dialogue"));
		
		done = false;
		
		dialogue = new HashMap<Integer, char[]>();
	    String str = "Hey, you, what are you in for?";
	    String str1 = "Geez, your whole planet! I've heard legends about the white crystal,";
	    String str2 = "no wonder Lord Zatolib wants to extract the energy from inside you.";
	    String str3 = "He could rule the galaxy with that kind of power! We got to get out";
	    String str4 = "of here before he can begin the extraction.";
	    String str5 = "I nabbed a key off of a guard awhile ago but it doesn't work on my";
	    String str6 = "door. Here, try on yours.";
	    String str7 = "Recieved Dungeon Key!";
	    String str8 = "The guard outside must have my key. Get it from him and free me.";
	    //next scene
	    String str9 = "My ship needs to be around here somewhere.";
	    String str10 = "GG with that battle, now open this up!";	
	    String str11 = "Thanks man, I'll help you get out of here.";	
	    String str12 = "Gained Grogo as an ally! Be sure to utilize him in battle!";
	    String str13 = ".";
	    dialogue.put(new Integer(0), str.toCharArray());
	    dialogue.put(new Integer(1), str1.toCharArray());
	    dialogue.put(new Integer(2), str2.toCharArray());
	    dialogue.put(new Integer(3), str3.toCharArray());
	    dialogue.put(new Integer(4), str4.toCharArray());
	    dialogue.put(new Integer(5), str5.toCharArray());
	    dialogue.put(new Integer(6), str6.toCharArray());
	    dialogue.put(new Integer(7), str7.toCharArray());
	    dialogue.put(new Integer(8), str8.toCharArray());
	    dialogue.put(new Integer(9), str9.toCharArray());
	    dialogue.put(new Integer(10), str10.toCharArray());
	    dialogue.put(new Integer(11), str11.toCharArray());
	    dialogue.put(new Integer(12), str12.toCharArray());
	    dialogue.put(new Integer(13), str13.toCharArray());
	    
	    copy = new char[dialogue.get(new Integer(0)).length];
	    copy2 = new char[dialogue.get(new Integer(2)).length];
	}
	
	
	/**
	 * if enter is pressed before the dialogue is out, speed up the dialogue to the end
	 * if it's pressed after the dialogue is out, move on to the next piece of dialogue
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ENTER && buffer >= 10) {
			if(Game.gameState == Game.STATE.KeyFromGrogo) {
				if(!speedChanged) {
					speedChanged = true;
					buffer = 0;
				}
				if(done) {
					buffer = 0;
					if(doneCount == 0 || doneCount == 7 || doneCount >= 10 ) {
						doneCount += 1;
					}
					else {
						doneCount += 2;
					}
					count = 0;
					countLine2 = 0;
					copy = new char[dialogue.get(new Integer(doneCount)).length];
					copy2 = new char[dialogue.get(new Integer(doneCount)).length];
					speedChanged = false;
					done = false;
					if(doneCount == 10 && Game.firstBattle == true) {
						Game.gameState = Game.STATE.Game;
					}
					if(doneCount == 11) {
						for(int i = 0; i < handler.objects.size(); i++) {
							if(handler.objects.get(i) instanceof Gate) {
								Gate temp = (Gate)handler.objects.get(i);
								if(temp.gateNum() == 0) {
									temp.hasKey();
									temp.open();
								}
							}
						}
					}
					if(doneCount == 12) {
						AudioPlayer.getSound("newAlly").play(1, (float).3);
						for(int i = 0; i < handler.objects.size(); i++) {
							if(handler.objects.get(i).getID() == ID.Grogo) {
								Grogo g = (Grogo)handler.objects.get(i);
								handler.removeObject(g);
							}	
						}
					}
					if(doneCount >= 13) {
						Game.gameState = Game.STATE.Game;
						for(int i = 0; i < handler.objects.size(); i++) {
							if(handler.objects.get(i).getID() == ID.Povy) {
								Povy p = (Povy)handler.objects.get(i);
								p.setVelX(0);
								p.setVelY(0);
							}
						}
						Grogo temp = new Grogo(360, 367, ID.Grogo, handler);
						Game.allies.addAlly(temp);
						temp.originalSpot();
						
					}
					if(doneCount == 7) {
						AudioPlayer.getSound("gotKey").play(1, (float).1);
					}
					System.out.println("after enter copy: " + copy.length);
					System.out.println();
					
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
		if(Game.gameState == Game.STATE.KeyFromGrogo) {
			if(buffer <= 10) buffer++;
			if(done) buffer=10;
			Font fo = new Font("System Bold", 1, 15);
			g.setColor(Color.WHITE);
			g.setFont(fo);
			if(!done) {
				g.drawImage(text.get(0), Game.camX + 300, Game.camY + 745, null);
				handleScene(doneCount,g);
				if(doneCount == 7) {
					g.drawImage(dungeonKey, Game.camX + 330, Game.camY + 770, null);
				}
				else {
					if(doneCount != 12) {
						g.drawImage(grogoEmblem.get(talkCount), Game.camX + 330, Game.camY + 770, null);
						changeCount++;
						if(changeCount % 2 == 0) {
							talkCount++;
						}
						if(talkCount == 2) {
							talkCount = 0;
							changeCount = 0;
						}
					}
				}
			}
			else {
				g.drawImage(text.get(animate), Game.camX + 300, Game.camY + 745, null);
				
				changeCount++;
				if(changeCount % 10 == 0) {
					animate++;
				}
				if(animate == 3) {
					animate = 1;
					changeCount = 0;
				}
				
				handleScene(doneCount,g);
				
	
				if(doneCount == 7) {
					g.drawImage(dungeonKey, Game.camX + 330, Game.camY + 770, null);
				}
				else if(doneCount != 12){
					g.drawImage(grogoEmblem.get(0), Game.camX + 330, Game.camY + 770, null);
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
			if(doneCount == 0 || doneCount == 7 || doneCount >= 10) {
				done = true;
			}
			else {
				if(countLine2 == dialogue.get(new Integer(sceneNumber+1)).length) {
					done = true;	
				}
				else {
					if(speedChanged) {
						countLine2 = dialogue.get(new Integer(sceneNumber+1)).length;
						for(int i = 0; i < countLine2; i++) {
							copy2[i] = dialogue.get(new Integer(sceneNumber+1))[i];
						}
					}
					else {
						copy2[countLine2] = dialogue.get(new Integer(sceneNumber+1))[countLine2];
						AudioPlayer.getSound("type").play(1, (float).3);
						countLine2++;
					}
				}
			}
			g.drawChars(copy, 0, copy.length, Game.camX + 440, Game.camY + 800);
			g.drawChars(copy2, 0, copy2.length, Game.camX + 440, Game.camY + 820);
		}
		else {
			if(speedChanged) {
				count = dialogue.get(new Integer(sceneNumber)).length;
				for(int i = 0; i < count; i++) {
					//System.out.println(doneCount);
					//System.out.println(copy);
					//System.out.println(dialogue.get(new Integer(sceneNumber)));
					copy[i] = dialogue.get(new Integer(sceneNumber))[i];
				}
			}
			else {
				copy[count] = dialogue.get(new Integer(sceneNumber))[count];
				//g.drawChars(copy, 0, copy.length, Game.camX + 440, Game.camY + 800);
				AudioPlayer.getSound("type").play(1, (float).3);
				count++;
			}
			g.drawChars(copy, 0, copy.length, Game.camX + 440, Game.camY + 800);
		}
	}

}



