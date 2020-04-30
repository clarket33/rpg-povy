
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * analyzes the key presses by the user
 * @author clarkt5
 *
 */
public class KeyInput extends KeyAdapter{
	private Handler handler;
	private GameObject povy;
	public static int numItems = 0, numOutfits = 0, numAllies = 0;
	public static boolean[] keyDown = new boolean[4];
	private Menu menu;
	public KeyInput(Handler handler, Menu menu) {
		this.handler = handler;
		this.menu = menu;
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
	}
	/**
	 * if in game, use arrow keys and WASD to move player
	 * SPACE pauses the game
	 * ENTER causes interaction with game objects
	 * arrow keys and ENTER allow you to select your move in battle state
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {keyDown[0] = true;}
		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) { keyDown[1] = true;}
		if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) { keyDown[2] = true;}
		if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) { keyDown[3] = true;}
		
		if((Game.gameState == Game.STATE.Game && !Game.stair && FightText.enemyState == FightText.ENEMYSTATE.INACTIVE) || Game.gameState == Game.STATE.AfterZatolib) {
			
			for (int i = 0; i < handler.objects.size(); i++) {
				GameObject temp = handler.objects.get(i);
				
				if(temp.getID() == ID.Povy) {
					//key Events for Povy
					
					
					if(keyDown[0]) {temp.setVelY(-handler.spd);}
					if(keyDown[1]) { temp.setVelX(-handler.spd);}
					if(keyDown[2]) { temp.setVelY(handler.spd);}
					if(keyDown[3]) { temp.setVelX(handler.spd);}
					
					
					
					//vertical movement
					if(keyDown[0] && keyDown[2]) temp.setVelY(0);
					//horizontal movement
					if(keyDown[1] && keyDown[3]) temp.setVelX(0);
					//System.out.println("Up: " + keyDown[0] + " Down: " + keyDown[2] + " Right: " + keyDown[3] + " Left: " + keyDown[1]);
					
				
				}
			}
			if(key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ESCAPE) {
				if(Game.gameState != Game.STATE.AfterZatolib) {
					keyDown[0] = false;
					keyDown[1] = false;
					keyDown[2] = false;
					keyDown[3] = false;
					Game.gameState = Game.STATE.Paused;
					PauseScreen.changeScreens();
					return;
				}
			}
			if(key == KeyEvent.VK_ENTER || key == KeyEvent.VK_E) {
				for(int i = 0; i < handler.objects.size(); i++) {
					if(handler.objects.get(i).id == ID.Povy) {
						povy = handler.objects.get(i);
						break;
					}
				}
				for(int i = 0; i < handler.objects.size(); i++) {
					if(handler.objects.get(i).id == ID.SpaceShip) {
	       				if(povy.getBounds().intersects(handler.objects.get(i).getBounds())) {
	       					SpaceShip s = (SpaceShip)handler.objects.get(i);
	       					if(!s.isSelected()) {
	       						s.select();
	       						if(AudioPlayer.getMusic("dungeon").playing()) {
	       							AudioPlayer.getMusic("dungeon").stop();
	       							AudioPlayer.getSound("stairs").play(1, (float).6);
	       							
	       						}
	       					}
	       				}
					}
					if(handler.objects.get(i).id == ID.NonEnemy) {
						if(povy.getBounds().intersects(handler.objects.get(i).getBounds())) {
							if(handler.objects.get(i) instanceof Chest) {
								Chest temp = (Chest)handler.objects.get(i);
								temp.open();
							}
							if(handler.objects.get(i) instanceof Stair) {
								if(!Game.stair) {
									Stair temp = (Stair)handler.objects.get(i);
									if(temp.getNum() != 9) {
										Game.gameState = Game.STATE.Transition;
										Game.stair = true;
										//Game.stairBuffer = true;
										AudioPlayer.getSound("stairs").play(1, (float).6);
									}
									if(temp.getNum() == 8) {
										if(AudioPlayer.getMusic("dungeon").playing()) {
											AudioPlayer.getMusic("dungeon").stop();
											
										}
									}
								}
								
								break;
							}
							if(handler.objects.get(i) instanceof Gate) {
								Gate temp = (Gate)handler.objects.get(i);
								temp.open();
							}
							if(handler.objects.get(i) instanceof Lever) {
								Lever temp = (Lever)handler.objects.get(i);
								temp.push();
							}
							if(handler.objects.get(i) instanceof Pillar) {
								Pillar temp = (Pillar)handler.objects.get(i);
								boolean success = temp.select();
								if(!success) {
									for(int j = 0; j < handler.objects.size(); j++) {
										if(handler.objects.get(j) instanceof Povy) {
											Povy po = (Povy)handler.objects.get(j); 
											po.getsHit();
										}
										if(handler.objects.get(j) instanceof Pillar) {
											Pillar p = (Pillar)handler.objects.get(j);
											p.deSelect();
											
										}
									}
								}
							}
						}
					}
				}
			}
			if(key == KeyEvent.VK_5) {
				System.out.println("Up: " + keyDown[0] + " Down: " + keyDown[2] + " Right: " + keyDown[3] + " Left: " + keyDown[1]);
			}
		}
		if(Game.gameState == Game.STATE.Paused || Game.gameState == Game.STATE.Battle) {
			if(Game.gameState == Game.STATE.Paused) {
				if(key == KeyEvent.VK_SPACE  || key == KeyEvent.VK_ESCAPE) {
					Game.gameState = Game.STATE.Game;
					PauseScreen.pauseState = PauseScreen.PauseState.Regular;
					PauseScreen.changeScreens();
					return;
				}
			}
			//vertical movement
			if(keyDown[0] && keyDown[2]) return;
			if(keyDown[1] && keyDown[3]) return;
			
			if(Game.gameState == Game.STATE.Paused && PauseScreen.pauseState == PauseScreen.PauseState.Regular) {
				if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D){
					if(!PauseScreen.option4) {
						PauseScreen.option4 = true;
						PauseScreen.option3 = false;
						PauseScreen.option5 = false;
						PauseScreen.option1 = false;
						PauseScreen.option2 = false;
						return;
					}
					else {
						PauseScreen.option4 = false;
						PauseScreen.option1 = true;
						return;
					}
				}
				if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A){
					if(PauseScreen.option4) {
						PauseScreen.option4 = false;
						PauseScreen.option1 = true;
						return;
					}
				}
				if((key == KeyEvent.VK_UP || key == KeyEvent.VK_W)) {
					if(PauseScreen.none) {
						PauseScreen.option1 = true;
						PauseScreen.none = false;
					}
					else if(PauseScreen.option4) {
						PauseScreen.option5 = true;
						PauseScreen.option4 = false;
					}
					else if(PauseScreen.option5) {
						PauseScreen.option3 = true;
						PauseScreen.option5 = false;
					}
					else if(PauseScreen.option3) {
						PauseScreen.option2 = true;
						PauseScreen.option3 = false;
					}
					else if(PauseScreen.option2) {
						PauseScreen.option1 = true;
						PauseScreen.option2 = false;
					}
					else if(PauseScreen.option1) {
						PauseScreen.option4 = true;
						PauseScreen.option1 = false;
					}
					return;
				}
				if((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)) {
					if(PauseScreen.none) {
						PauseScreen.option1 = true;
						PauseScreen.none = false;
					}
					else if(PauseScreen.option4) {
						PauseScreen.option1 = true;
						PauseScreen.option4 = false;
					}
					else if(PauseScreen.option1) {
						PauseScreen.option2 = true;
						PauseScreen.option1 = false;
					}
					else if(PauseScreen.option2) {
						PauseScreen.option3 = true;
						PauseScreen.option2 = false;
					}
					else if(PauseScreen.option3) {
						PauseScreen.option5 = true;
						PauseScreen.option3 = false;
					}
					else if(PauseScreen.option5) {
						PauseScreen.option4 = true;
						PauseScreen.option5 = false;
					}
					return;
				}
			}
			if(Game.gameState == Game.STATE.Paused && PauseScreen.pauseState == PauseScreen.PauseState.ProgressScreen) {
				if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A){
					if(!PauseScreen.goBackFromProg) {
						PauseScreen.goBackFromProg = true;
						PauseScreen.overHealth = false;
						PauseScreen.overAlly = false;
						PauseScreen.overPummel = false;
						PauseScreen.overLaser = false;
						return;
					}
					else {
						PauseScreen.goBackFromProg = false;
						PauseScreen.overHealth = true;
						return;
					}
				}
				if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D){
					if(PauseScreen.goBackFromProg) {
						PauseScreen.goBackFromProg = false;
						PauseScreen.overHealth = true;
						return;
					}
				}
				if((key == KeyEvent.VK_UP || key == KeyEvent.VK_W)) {
					if(!PauseScreen.goBackFromProg && !PauseScreen.overHealth && !PauseScreen.overAlly && !PauseScreen.overPummel
							&& !PauseScreen.overLaser) {
						PauseScreen.goBackFromProg = true;
					}
					else if(PauseScreen.goBackFromProg) {
						PauseScreen.overLaser = true;
						PauseScreen.goBackFromProg = false;
					}
					else if(PauseScreen.overLaser) {
						PauseScreen.overPummel = true;
						PauseScreen.overLaser = false;
					}
					else if(PauseScreen.overPummel) {
						PauseScreen.overAlly = true;
						PauseScreen.overPummel = false;
					}
					else if(PauseScreen.overAlly) {
						PauseScreen.overHealth = true;
						PauseScreen.overAlly = false;
					}
					else if(PauseScreen.overHealth) {
						PauseScreen.goBackFromProg = true;
						PauseScreen.overHealth = false;
					}	
				}
				if((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)) {
					if(!PauseScreen.goBackFromProg && !PauseScreen.overHealth && !PauseScreen.overAlly && !PauseScreen.overPummel
							&& !PauseScreen.overLaser) {
						PauseScreen.goBackFromProg = true;
					}
					else if(PauseScreen.goBackFromProg) {
						PauseScreen.overHealth = true;
						PauseScreen.goBackFromProg = false;
					}
					else if(PauseScreen.overLaser) {
						PauseScreen.goBackFromProg = true;
						PauseScreen.overLaser = false;
					}
					else if(PauseScreen.overPummel) {
						PauseScreen.overLaser = true;
						PauseScreen.overPummel = false;
					}
					else if(PauseScreen.overAlly) {
						PauseScreen.overPummel = true;
						PauseScreen.overAlly = false;
					}
					else if(PauseScreen.overHealth) {
						PauseScreen.overAlly = true;
						PauseScreen.overHealth = false;
					}	
				}
			}
			if(Game.gameState == Game.STATE.Paused && PauseScreen.pauseState == PauseScreen.PauseState.ItemScreen || (Game.gameState == Game.STATE.Battle && Battle.itemSelected && !PauseScreen.itemSelect)) {
				if((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) 
						|| (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A || key == KeyEvent.VK_UP || key == KeyEvent.VK_W)) {
					//System.out.println("over Back : " + PauseScreen.backToRegFromItem + ", " + "over an Item: " + PauseScreen.overItem);
					//System.out.println("y coord : " + PauseScreen.overItemY + " num : " + PauseScreen.curItemOver);
					if(!PauseScreen.backToRegFromItem && !PauseScreen.overItem) {
						if(numItems != Game.itemPouch.getItemAmnt()) {
							PauseScreen.overItem = true;
							PauseScreen.overItemY = 198;
							//System.out.println("lalala");
							int num = (PauseScreen.overItemY - 198) / 82;
							PauseScreen.curItemOver = Game.itemPouch.getItem(num);
							PauseScreen.backToRegFromItem = false;
							return;
						}
						else {
							numItems = 0;
							PauseScreen.overItemY = 198;
							int num = (PauseScreen.overItemY - 198) / 82;
							PauseScreen.curItemOver = Game.itemPouch.getItem(num);
							PauseScreen.backToRegFromItem = true;
							PauseScreen.overItem = false;
							return;
						}
					}
					
				}
				
				if((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)) {
					if(PauseScreen.backToRegFromItem) {
						if(numItems != Game.itemPouch.getItemAmnt()) {
							PauseScreen.overItem = true;
							PauseScreen.overItemY = 198;
							int num = (PauseScreen.overItemY - 198) / 82;
							PauseScreen.curItemOver = Game.itemPouch.getItem(num);
							PauseScreen.backToRegFromItem = false;
						}
						return;
					}
					if(PauseScreen.overItem) {
						PauseScreen.overItemY += 82;
						numItems = (PauseScreen.overItemY-198)/82;
						if(numItems == Game.itemPouch.getItemAmnt()) {
							numItems = 0;
							PauseScreen.overItemY = 198;
							PauseScreen.backToRegFromItem = true;
							PauseScreen.overItem = false;
						}
						else {
							PauseScreen.overItem = true;
							int num = (PauseScreen.overItemY - 198) / 82;
							PauseScreen.curItemOver = Game.itemPouch.getItem(num);
						}
						return;
					}	
				}
				
				if((key == KeyEvent.VK_UP || key == KeyEvent.VK_W)) {
					if(PauseScreen.backToRegFromItem) {
						if(numItems != Game.itemPouch.getItemAmnt()) {
							numItems = (Game.itemPouch.getItemAmnt()-1);
							PauseScreen.overItem = true;
							PauseScreen.overItemY = 198 + (82 * numItems);
							int num = (PauseScreen.overItemY - 198) / 82;
							PauseScreen.curItemOver = Game.itemPouch.getItem(num);
							PauseScreen.backToRegFromItem = false;
						}
						return;
					}
					if(PauseScreen.overItem) {
						PauseScreen.overItemY -= 82;
						numItems = (PauseScreen.overItemY-198)/82;
						if(numItems < 0 ) {
							numItems = 0;
							PauseScreen.overItemY = 198;
							PauseScreen.backToRegFromItem = true;
							PauseScreen.overItem = false;
						}
						else {
							PauseScreen.overItem = true;
							int num = (PauseScreen.overItemY - 198) / 82;
							PauseScreen.curItemOver = Game.itemPouch.getItem(num);
						}
						return;
					}	
				}
			}
			if((Game.gameState == Game.STATE.Paused && PauseScreen.pauseState == PauseScreen.PauseState.ItemUse) || (Game.gameState == Game.STATE.Battle && Battle.itemSelected && PauseScreen.itemSelect)) {
				if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A){
					if(PauseScreen.overNo) {
						PauseScreen.overYes = true;
						PauseScreen.overNo = false;
					}
					else if(!PauseScreen.overNo && !PauseScreen.overYes) {
						PauseScreen.overYes = true;
						PauseScreen.overNo = false;
					}
					return;
				}
				if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D){
					if(PauseScreen.overYes) {
						PauseScreen.overYes = false;
						PauseScreen.overNo = true;
					}
					else if(!PauseScreen.overNo && !PauseScreen.overYes) {
						PauseScreen.overNo = true;
						PauseScreen.overYes = false;
					}
					return;
				}
			}
			if(Game.gameState == Game.STATE.Paused && PauseScreen.pauseState == PauseScreen.PauseState.AttireScreen) {
				if((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)) {
					if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
						if(PauseScreen.overAnOutfit) {
							numOutfits = 0;
							PauseScreen.xValForDrawingAttire = 182;
							PauseScreen.goBackFromAttire= true;
							PauseScreen.overAnOutfit = false;
							return;
						}
					}
					if(!PauseScreen.goBackFromAttire && !PauseScreen.overAnOutfit) {
						PauseScreen.goBackFromAttire = true;
						return;
					}
					if(PauseScreen.goBackFromAttire) {
						PauseScreen.overAnOutfit = true;
						PauseScreen.xValForDrawingAttire = 182;
						PauseScreen.goBackFromAttire = false;
						return;
					}
					if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D){
						if(PauseScreen.overAnOutfit) {
							PauseScreen.xValForDrawingAttire += 232;
							numOutfits = (PauseScreen.xValForDrawingAttire-182)/232;
							if(numOutfits == Game.costumePouch.getCostumeAmount()) {
								numOutfits = 0;
								PauseScreen.xValForDrawingAttire = 182;
								PauseScreen.goBackFromAttire= true;
								PauseScreen.overAnOutfit = false;
							}
							else {
								PauseScreen.overAnOutfit = true;
							}
							return;
						}	
					}
					return;
				}
				if((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A || key == KeyEvent.VK_UP || key == KeyEvent.VK_W)) {
					if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
						if(PauseScreen.overAnOutfit) {
							numOutfits = 0;
							PauseScreen.xValForDrawingAttire = 182;
							PauseScreen.goBackFromAttire= true;
							PauseScreen.overAnOutfit = false;
							return;
						}
					}
					if(!PauseScreen.goBackFromAttire && !PauseScreen.overAnOutfit) {
						PauseScreen.goBackFromAttire = true;
						return;
					}
					if(PauseScreen.goBackFromAttire) {
						PauseScreen.overAnOutfit = true;
						PauseScreen.xValForDrawingAttire = 182 + (232 * (Game.costumePouch.getCostumeAmount()-1));
						PauseScreen.goBackFromAttire = false;
						return;
					}
					if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A){
						if(PauseScreen.overAnOutfit) {
							PauseScreen.xValForDrawingAttire -= 232;
							numOutfits = (PauseScreen.xValForDrawingAttire-182)/232;
							if(numOutfits < 0) {
								numOutfits = 0;
								PauseScreen.xValForDrawingAttire = 182;
								PauseScreen.goBackFromAttire= true;
								PauseScreen.overAnOutfit = false;
							}
							else {
								PauseScreen.overAnOutfit = true;
							}
							return;
						}	
					}
					return;
				}
			}
			
			if((Game.gameState == Game.STATE.Paused && PauseScreen.pauseState == PauseScreen.PauseState.AllyScreen) || (Game.gameState == Game.STATE.Battle && Battle.allySelected)) {
				if((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) 
						|| (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A || key == KeyEvent.VK_UP || key == KeyEvent.VK_W)) {
					if(!PauseScreen.goBackFromAlly && !PauseScreen.overAnAlly) {
						if(numAllies != Game.allies.allyAmount()) {
							PauseScreen.overAnAlly = true;
							PauseScreen.xValForDrawingAlly = 182;
							PauseScreen.goBackFromAlly = false;
							return;
						}
						else {
							numAllies = 0;
							PauseScreen.xValForDrawingAlly = 182;
							PauseScreen.goBackFromAlly= true;
							PauseScreen.overAnAlly = false;
							return;
						}
					}
				}
				if((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)) {
					if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
						if(PauseScreen.overAnAlly) {
							numAllies = 0;
							PauseScreen.xValForDrawingAlly = 182;
							PauseScreen.goBackFromAlly= true;
							PauseScreen.overAnAlly = false;
							return;
						}
					}
					
					if(PauseScreen.goBackFromAlly) {
						if(numAllies != Game.allies.allyAmount()) {
							PauseScreen.overAnAlly = true;
							PauseScreen.xValForDrawingAlly = 182;
							PauseScreen.goBackFromAlly = false;
							return;
						}
					}
					if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D){
						if(PauseScreen.overAnAlly) {
							PauseScreen.xValForDrawingAlly += 232;
							numAllies = (PauseScreen.xValForDrawingAlly-182)/232;
							if(numAllies == Game.allies.allyAmount()) {
								numAllies = 0;
								PauseScreen.xValForDrawingAlly = 182;
								PauseScreen.goBackFromAlly= true;
								PauseScreen.overAnAlly = false;
							}
							else {
								PauseScreen.overAnAlly = true;
							}
							return;
						}	
					}
					return;
				}
				if((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A || key == KeyEvent.VK_UP || key == KeyEvent.VK_W)) {
					//System.out.println(PauseScreen.xValForDrawingAlly);
					//System.out.println(numAllies);
					if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
						if(PauseScreen.overAnAlly) {
							numAllies = 0;
							PauseScreen.xValForDrawingAlly = 182;
							PauseScreen.goBackFromAlly= true;
							PauseScreen.overAnAlly = false;
							return;
						}
					}
					if(!PauseScreen.goBackFromAlly && !PauseScreen.overAnAlly) {
						PauseScreen.goBackFromAlly = true;
						return;
					}
					if(PauseScreen.goBackFromAlly) {
						if(numAllies != Game.allies.allyAmount()) {
							PauseScreen.overAnAlly = true;
							PauseScreen.xValForDrawingAlly = 182 + (232 * (Game.allies.allyAmount()-1));
							//System.out.print("here we go ");
							//System.out.println(PauseScreen.xValForDrawingAlly);
							PauseScreen.goBackFromAlly = false;
							return;
						}
					}
					if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A){
						if(PauseScreen.overAnAlly) {
							numAllies--;
							PauseScreen.xValForDrawingAlly -= 232;
							if(numAllies < 0) {
								numAllies = 0;
								PauseScreen.xValForDrawingAlly = 182;
								PauseScreen.goBackFromAlly= true;
								PauseScreen.overAnAlly = false;
							}
							else {
								PauseScreen.overAnAlly = true;
							}
							return;
						}	
					}
					return;
				}
			}
			
			
			
			if(key == KeyEvent.VK_ENTER || key == KeyEvent.VK_E) {
				PauseScreen.menuControl();
			}
		}
		if(Game.gameState == Game.STATE.PostBattle) {
			if(ExperienceBar.levelUp != true && Battle.expToBeAdded == 0) {
				if((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) 
						|| (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A || key == KeyEvent.VK_UP || key == KeyEvent.VK_W)) {
					PauseScreen.overNext = true;
				}
			}
			if(ExperienceBar.levelUp == true) {
				if((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)) {
					if(!PauseScreen.overHealth && !PauseScreen.overAlly && !PauseScreen.overPummel
							&& !PauseScreen.overLaser) {
						PauseScreen.overHealth = true;
					}
					else if(PauseScreen.overHealth) {
						PauseScreen.overAlly = true;
						PauseScreen.overHealth = false;
					}
					else if(PauseScreen.overAlly) {
						PauseScreen.overPummel = true;
						PauseScreen.overAlly = false;
					}
					else if(PauseScreen.overPummel) {
						PauseScreen.overLaser = true;
						PauseScreen.overPummel = false;
					}
					else if(PauseScreen.overLaser) {
						PauseScreen.overHealth = true;
						PauseScreen.overLaser = false;
					}
					
				}
				if((key == KeyEvent.VK_UP || key == KeyEvent.VK_W)) {
					if(!PauseScreen.overHealth && !PauseScreen.overAlly && !PauseScreen.overPummel
							&& !PauseScreen.overLaser) {
						PauseScreen.overHealth = true;
					}
					else if(PauseScreen.overLaser) {
						PauseScreen.overPummel = true;
						PauseScreen.overLaser = false;
					}
					else if(PauseScreen.overPummel) {
						PauseScreen.overAlly = true;
						PauseScreen.overPummel = false;
					}
					else if(PauseScreen.overAlly) {
						PauseScreen.overHealth = true;
						PauseScreen.overAlly = false;
					}
					else if(PauseScreen.overHealth) {
						PauseScreen.overLaser = true;
						PauseScreen.overHealth = false;
					}	
				}
			}
			if(key == KeyEvent.VK_ENTER || key == KeyEvent.VK_E) {
				PauseScreen.menuControl();
			}
		}
		
		if(Game.gameState == Game.STATE.Battle && Battle.battleState == Battle.BATTLESTATE.PlayerTurnStart) {
			if(Battle.itemSelected == false && Battle.allySelected == false) {
				
				if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
					Battle.left = true;
					AudioPlayer.getSound("menuSlider").play(1, (float).1);
					Battle.menuChange = 0;
					Battle.menuPosition+=1;
					if(Battle.menuPosition == 6) {
						Battle.menuPosition = 0;
					}
				}
				if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
					Battle.left = false;
					AudioPlayer.getSound("menuSlider").play(1, (float).1);
					Battle.menuChange = 0;
					Battle.menuPosition-=1;
					if(Battle.menuPosition == -1) {
						Battle.menuPosition = 5;
					}
				}
				if(key == KeyEvent.VK_ENTER || key == KeyEvent.VK_E) {
					if(Battle.menuPosition == 4 && (Battle.useAlly == false || Game.firstBattle)) {
						AudioPlayer.getSound("errorGate").play(1, (float).1);
						return;
					}
					if(Battle.menuPosition == 3 && !Game.battle.possRun()) {
						AudioPlayer.getSound("errorGate").play(1, (float).1);
						return;
					}
					if(Battle.menuPosition == 2) {
						AudioPlayer.getSound("errorGate").play(1, (float).1);
						return;
					}
					AudioPlayer.getSound("select").play(1, (float).1);
					if(Battle.menuPosition != 1 && Battle.menuPosition != 4) {
						Battle.battleState = Battle.BATTLESTATE.PlayerTurnAction;
						return;
					}
					else if(Battle.menuPosition == 1) {
						PauseScreen.changeScreens();
						if(!Battle.itemAllyRet) Battle.itemSelected = true;
						else Battle.itemAllyRet = false;
						PauseScreen.backToRegFromItem = true;
						return;
					}
					else if(Battle.menuPosition == 4) {
						PauseScreen.changeScreens();
						if(!Battle.itemAllyRet) Battle.allySelected = true;
						else Battle.itemAllyRet = false;
						PauseScreen.goBackFromAlly = true;
						return;
					}
				}
			}
		}
		if(Game.gameState == Game.STATE.Menu) {
			if((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) 
					|| (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A || key == KeyEvent.VK_UP || key == KeyEvent.VK_W)) {
				if(!Menu.overLoad && !Menu.overNew && !Menu.overOptions && !Menu.overQuit) {
					Menu.overNew = true; 
					return;
				}
			}
			if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D){
				if(Menu.overNew) {
					Menu.overNew = false; 
					Menu.overOptions = true;
				}
				if(Menu.overLoad) {
					Menu.overLoad = false; 
					Menu.overQuit = true;
				}
			}
			else if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
				if(Menu.overNew) {
					Menu.overNew = false; 
					Menu.overLoad = true;
				}
				if(Menu.overOptions) {
					Menu.overOptions = false; 
					Menu.overQuit = true;
				}
			}
			else if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
				if(Menu.overOptions) {
					Menu.overOptions = false; 
					Menu.overNew = true;
				}
				if(Menu.overQuit) {
					Menu.overQuit = false; 
					Menu.overLoad = true;
				}
			}
			else if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
				if(Menu.overQuit) {
					Menu.overQuit = false; 
					Menu.overOptions = true;
				}
				if(Menu.overLoad) {
					Menu.overLoad = false; 
					Menu.overNew = true;
				}
			}
			else if(key == KeyEvent.VK_ENTER || key == KeyEvent.VK_E) {
				menu.press();
			}
		}
		
	}
	
	/**
	 * set velocity to 0 if a key is released
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) keyDown[0] = false;
		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)	keyDown[1] = false;
		if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) keyDown[2] = false;
		if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) keyDown[3] = false;
		if((Game.gameState == Game.STATE.Game && !Game.stair && FightText.enemyState == FightText.ENEMYSTATE.INACTIVE) || Game.gameState == Game.STATE.AfterZatolib) {
			for (int i = 0; i < handler.objects.size(); i++) {
				GameObject temp = handler.objects.get(i);
				
				if(temp.getID() == ID.Povy) {
					
					if(keyDown[0] == true) temp.setVelY(-handler.spd);
					if(keyDown[1] == true) temp.setVelX(-handler.spd);
					if(keyDown[2] == true) temp.setVelY(handler.spd);
					if(keyDown[3] == true) temp.setVelX(handler.spd);
					
					
					//vertical movement
					if(!keyDown[0] && !keyDown[2]) temp.setVelY(0);
					//horizontal movement
					if(!keyDown[1] && !keyDown[3]) temp.setVelX(0);
					
					//vertical movement
					if(keyDown[0] && keyDown[2]) temp.setVelY(0);
					//horizontal movement
					if(keyDown[1] && keyDown[3]) temp.setVelX(0);

				}
			}
		}
	}
	


}
