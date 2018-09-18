
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 * analyzes the key presses by the user
 * @author clarkt5
 *
 */
public class KeyInput extends KeyAdapter{
	private Handler handler;
	private Game game;
	private GameObject povy;
	private boolean[] keyDown = new boolean[4];
	public KeyInput(Handler handler, Game game) {
		this.handler = handler;
		this.game = game;
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
		if(game.gameState == Game.STATE.Game) {
			for (int i = 0; i < handler.objects.size(); i++) {
				GameObject temp = handler.objects.get(i);
				if(temp.getID() == ID.Povy) {
					//key Events for Povy
					
					if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {temp.setVelY(-handler.spd);keyDown[0] = true;}
					if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) { temp.setVelX(-handler.spd);keyDown[1] = true;}
					if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) { temp.setVelY(handler.spd);keyDown[2] = true;}
					if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) { temp.setVelX(handler.spd);keyDown[3] = true;}
				}
			}
			if(key == KeyEvent.VK_SPACE) {
				Game.gameState = Game.STATE.Paused;
				return;
			}
			if(key == KeyEvent.VK_ENTER) {
				for(int i = 0; i < handler.objects.size(); i++) {
					if(handler.objects.get(i).id == ID.Povy) {
						povy = handler.objects.get(i);
					}
				}
				for(int i = 0; i < handler.objects.size(); i++) {
					if(handler.objects.get(i).id == ID.NonEnemy) {
						if(povy.getBounds().intersects(handler.objects.get(i).getBounds())) {
							if(handler.objects.get(i) instanceof Chest) {
								Chest temp = (Chest)handler.objects.get(i);
								temp.open();
							}
							if(handler.objects.get(i) instanceof Gate) {
								Gate temp = (Gate)handler.objects.get(i);
								temp.open();
							}
							if(handler.objects.get(i) instanceof Lever) {
								Lever temp = (Lever)handler.objects.get(i);
								temp.push();;
							}
						}
					}
				}
			}
		}
		if(game.gameState == Game.STATE.Paused) {
			if(key == KeyEvent.VK_SPACE) {
				Game.gameState = Game.STATE.Game;
				PauseScreen.pauseState = PauseScreen.PauseState.Regular;
				return;
			}
		}
		
		if(game.gameState == Game.STATE.Battle && Battle.battleState == Battle.BATTLESTATE.PlayerTurnStart) {
			if(Battle.itemSelected == false && Battle.allySelected == false) {
				
				if(key == KeyEvent.VK_LEFT) {
					Battle.left = true;
					AudioPlayer.getSound("menuSlider").play(1, (float).1);
					Battle.menuChange = 0;
					Battle.menuPosition+=1;
					if(Battle.menuPosition == 6) {
						Battle.menuPosition = 0;
					}
				}
				if(key == KeyEvent.VK_RIGHT) {
					Battle.left = false;
					AudioPlayer.getSound("menuSlider").play(1, (float).1);
					Battle.menuChange = 0;
					Battle.menuPosition-=1;
					if(Battle.menuPosition == -1) {
						Battle.menuPosition = 5;
					}
				}
				if(key == KeyEvent.VK_ENTER) {
					if(Battle.menuPosition == 4 && Battle.useAlly == false) {
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
					}
					else if(Battle.menuPosition == 1) {
						Battle.itemSelected = true;
					}
					else if(Battle.menuPosition == 4) {
						Battle.allySelected = true;
					}
				}
			}
		}
		
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		
	}
	
	/**
	 * set velocity to 0 if a key is released
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(game.gameState == Game.STATE.Game) {
			for (int i = 0; i < handler.objects.size(); i++) {
				GameObject temp = handler.objects.get(i);
				if(temp.getID() == ID.Povy) {
					//key Events for Povy
					if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) keyDown[0] = false;
					if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)	keyDown[1] = false;
					if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) keyDown[2] = false;
					if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) keyDown[3] = false;
					//vertical movement
					if(!keyDown[0] && !keyDown[2]) temp.setVelY(0);
					//horizontal movement
					if(!keyDown[1] && !keyDown[3]) temp.setVelX(0);
				}
			}
		}
	}

}
