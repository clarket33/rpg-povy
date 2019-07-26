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
public class FightText extends KeyAdapter{
	private Map<Integer, char[]> dialogue;
	private char[] copy, copy2;
	private int count = 0, countLine2 = 0, talkCount = 0;
	private boolean done;
	private ArrayList<BufferedImage> text;
	private int doneCount = 2;
	private int changeCount = 0, animate = 1;
	private Handler handler;
	private int buffer = 300;
	private ArrayList<BufferedImage> grogoEmblem, zatolibEmblem, elephantEmblem;
	public static ENEMYSTATE enemyState;
	private boolean speedChanged = false;
	private Povy p;
	private GameObject enemy;
	public static boolean levOver = false;
	
	/**
	 * makes the scene, loads in the text
	 * @param handler
	 */
	public FightText(Handler handler) {
	    SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
	    
	    this.handler = handler;
		
	    text = new ArrayList<BufferedImage>();
	    text.add(ss.grabImage(1, 1, 1080, 285,"dialogue"));
		text.add(ss.grabImage(1, 2, 1080, 285,"dialogue"));
		text.add(ss.grabImage(1, 3, 1080, 285,"dialogue"));
		
		done = false;
		
		dialogue = new HashMap<Integer, char[]>();
		
		grogoEmblem = new ArrayList<BufferedImage>();
		grogoEmblem.add(ss.grabImage(1, 1, 130, 130,"grogoTalking"));
		grogoEmblem.add(ss.grabImage(1, 2, 130, 130,"grogoTalking"));
		
		zatolibEmblem = new ArrayList<BufferedImage>();
		zatolibEmblem.add(ss.grabImage(1, 1, 130, 130,"zatolibTalking"));
		zatolibEmblem.add(ss.grabImage(1, 2, 130, 130,"zatolibTalking"));
		
		elephantEmblem = new ArrayList<BufferedImage>();
		elephantEmblem.add(ss.grabImage(1, 1, 130, 130,"elephantTalking"));
		elephantEmblem.add(ss.grabImage(1, 2, 130, 130,"elephantTalking"));
		
		
		//elephant
	    String str = "This path leads directly to Lord Zatolib.";
	    String str1 = "I am not about to let a tiny green pest get through!";
	    
	    
	    //zatolib
	    String str2 = "I am surprised you have made it this far.";
	    
	    String str3 = "You possess an inexplicable power, yet you lack the strength";
	    String str4 = "and intelligence to utilize it properly.";
	    
	    String str5 = "I, on the other hand, am a superior being.";
	    
	    String str6 = "And once I gain control of what's rightfully mine,";
	    String str7 = "I will build an army to rule the galaxy!";
	    
	    String str8 = "And there is nothing you can do to stop me!";
	    
	    
	    //grogo post fight
	    String str9 = "Povy?!?!";
	    String str10 = "Oh boy, I've got to get us out of here.";
	    
	    
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
	    
	    enemyState = ENEMYSTATE.INACTIVE;
	    
	    copy = new char[dialogue.get(new Integer(0)).length];
	    for(int i = 0; i < dialogue.get(new Integer(0)).length; i++) {
	    	copy[i] = ' ';
	    }
	    copy2 = new char[dialogue.get(new Integer(2)).length];
	    for(int i = 0; i < dialogue.get(new Integer(2)).length; i++) {
	    	copy2[i] = ' ';
	    }
	}
	
	public enum ENEMYSTATE{
		ELEPHANT,
		ZATOLIB,
		GROGZATOLIB,
		INACTIVE;
	};
	
	
	/**
	 * if enter is pressed before the dialogue is out, speed up the dialogue to the end
	 * if it's pressed after the dialogue is out, move on to the next piece of dialogue
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ENTER) {
			if(Game.gameState == Game.STATE.Game || Game.gameState == Game.STATE.AfterZatolib) {
				//System.out.println(buffer);
				if(!speedChanged && buffer >= 10) {
					speedChanged = true;
					//buffer = 0;
				}
				if(done && buffer >= 10) {
					
					buffer = 0;
					if(doneCount == 3 || doneCount == 6) {
						doneCount += 2;
					}
					else {
						doneCount += 1;
					}
					if(doneCount == 11 && enemyState == ENEMYSTATE.GROGZATOLIB) {
						//System.out.println("yert");
						enemyState = ENEMYSTATE.INACTIVE;
						levOver = true;
					}
					if(doneCount >=11) {
						return;
					}
				
				
					count = 0;
					countLine2 = 0;
					speedChanged = false;
					done = false;
					
					copy = new char[dialogue.get(new Integer(doneCount)).length];
					for(int i = 0; i < dialogue.get(new Integer(doneCount)).length; i++) {
					    copy[i] = ' ';
					 }
					copy2 = new char[dialogue.get(new Integer(doneCount)).length];
					for(int i = 0; i < dialogue.get(new Integer(doneCount)).length; i++) {
					   copy2[i] = ' ';
					}
					
					if(doneCount == 2 && enemyState == ENEMYSTATE.ELEPHANT) {
						initiateBattle();
					}
					
					if(doneCount == 9 && enemyState == ENEMYSTATE.ZATOLIB) {
						initiateBattle();
					}
					
				
				}
				
				
				
			}
		}
	}
	
	
	public void assignBattle(Povy p, GameObject enemy) {
		this.p = p;
		this.enemy = enemy;
	}
	
	public void initiateBattle() {
		if(AudioPlayer.getMusic("zatFight").playing()) AudioPlayer.getMusic("zatFight").stop();
		if(AudioPlayer.getMusic("dungeon").playing()) AudioPlayer.getMusic("dungeon").stop();
		AudioPlayer.getSound("fightStart").play(1, (float).1);
		Game.gameState = Game.STATE.Transition;
		Battle.battleState = null;
		Game.battle = new Battle(handler, p.copy(), enemy);
		KeyInput.keyDown[0] = false;
		KeyInput.keyDown[1] = false;
		KeyInput.keyDown[2] = false;
		KeyInput.keyDown[3] = false;
		enemyState = ENEMYSTATE.INACTIVE;
		//return true;
	}
	
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	/**
	 * draws the text
	 * @param g
	 */
	public void render(Graphics g) {
		//System.out.println("BREH");
		if(doneCount >= 11) {
			return;
		}
		if(Game.gameState == Game.STATE.Game || Game.gameState == Game.STATE.AfterZatolib) {
			//System.out.println(doneCount);
			if(buffer < 10) {
				buffer++;
				//return;
			}
			//if(done) buffer=150;
			Font fo = new Font("verdana", 1, 18);
			g.setColor(new Color(106, 215, 48));
			g.setFont(fo);
			if(!done) {
				g.drawImage(text.get(0), Game.camX + 120, Game.camY + 625, null);
				handleScene(doneCount,g);
				if(doneCount == 0 || doneCount == 1) {
					g.drawImage(elephantEmblem.get(talkCount), Game.camX + 210, Game.camY + 700, null);
					changeCount++;
					if(changeCount % 2 == 0) {
						talkCount++;
					}
					if(talkCount == 2) {
						talkCount = 0;
						changeCount = 0;
					}
				}
				else if(doneCount >= 2 && doneCount <= 8) {
					g.drawImage(zatolibEmblem.get(talkCount), Game.camX + 210, Game.camY + 700, null);
					changeCount++;
					if(changeCount % 2 == 0) {
						talkCount++;
					}
					if(talkCount == 2) {
						talkCount = 0;
						changeCount = 0;
					}
				}
				else {
					g.drawImage(grogoEmblem.get(talkCount), Game.camX + 210, Game.camY + 700, null);
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
				
				if(doneCount < 11)handleScene(doneCount,g);
				
	
				if(doneCount == 0 || doneCount == 1) {
					g.drawImage(elephantEmblem.get(0), Game.camX + 210, Game.camY + 700, null);
				}
				else if(doneCount >= 2 && doneCount <= 8) {
					g.drawImage(zatolibEmblem.get(0), Game.camX + 210, Game.camY + 700, null);
				}
				else {
					g.drawImage(grogoEmblem.get(0), Game.camX + 210, Game.camY + 700, null);
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
			if(doneCount == 0 || doneCount == 1 || doneCount == 2 || doneCount == 5 || doneCount >= 8) {
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
			g.drawChars(copy, 0, copy.length, Game.camX + 350, Game.camY + 740);
			g.drawChars(copy2, 0, copy2.length, Game.camX + 350, Game.camY + 760);
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
			g.drawChars(copy, 0, copy.length, Game.camX + 350, Game.camY + 740);
		}
	}

}


