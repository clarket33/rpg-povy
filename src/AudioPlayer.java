
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


/**
 * loads in all of the sounds of the game
 * @author clarkt5
 *
 */
public class AudioPlayer {
	public static Map<String, Sound> soundMap = new HashMap<String, Sound>();
	public static Map<String, Music> musicMap = new HashMap<String, Music>();
	
	public static void load() {
		try {
			soundMap.put("cavern", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Misc/PP_Magic_Portal_Loop.wav"));
			soundMap.put("explosion", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Misc/PP_Monster_Dissipate.wav"));
			soundMap.put("abduction", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Misc/FA_Power_Up_1_1.wav"));
			soundMap.put("thump", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Misc/PP_Small_Impact_1_6.wav"));
			musicMap.put("title", new Music("res/audio/Sound temple - Space Ambient Music.ogg"));
			musicMap.put("opening", new Music("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/Stingers_Jingles/Fantasy/TD_Game_Over_Slow_Loop.wav"));
			soundMap.put("chap0Start", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/Stingers_Jingles/Racing/FA_Win_Stinger_2.ogg"));
			musicMap.put("dungeonFight", new Music("res/Dungeon/rpg-dungeon-pack v1.1(wonderdot)/Dungeon Music Pack/Dungeon Warriors (Menu).ogg"));
			musicMap.put("dungeon", new Music("res/Dungeon/rpg-dungeon-pack v1.1(wonderdot)/Dungeon Music Pack/Massacre at the Abyss (Battle).ogg"));
			soundMap.put("fightStart", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/Stingers_Jingles/Racing/FA_Lose_Stinger_1_1.wav"));
			soundMap.put("hit", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Misc/PP_Small_Impact_1_3.wav"));
			soundMap.put("menuSlider", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Misc/PP_Whoosh_1_3.wav"));
			soundMap.put("select", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/UI/CGM3_Select_button_02_1.wav"));
			soundMap.put("golemPunch", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Misc/PP_Heavy_Impact_1_1.wav"));
			soundMap.put("pummel", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Misc/PP_Small_Impact_1_5.wav"));
			soundMap.put("chestOpen", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/Stingers_Jingles/Cute_Casual/CGM3_Small_Quest_Complete_01_1.wav"));
			soundMap.put("drinking", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/Drinking.wav"));
			soundMap.put("drinkingDone", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Collect Item/PP_Collect_Item_1_2.wav"));
			musicMap.put("afterBattle", new Music("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/Music/Funky Chill 2 loop.wav"));
			soundMap.put("winBattle", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/Stingers_Jingles/Racing/PP_Win_Stinger.wav"));
			soundMap.put("expAdding", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/UI/CGM3_Bubble_Button_01_3.wav"));
			soundMap.put("levelUp", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Stinger/LQ_Stage Clear.wav"));
			soundMap.put("upgradeDone", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/Stingers_Jingles/Cute_Casual/CGM3_Save_Load_02_2.wav"));
			soundMap.put("click", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/UI/LQ_Back_Button.wav"));
			soundMap.put("laserShoot", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Misc/PP_Weapon_Shoot.wav"));
			soundMap.put("laserHit", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Misc/PP_Small_Impact_1_1.wav"));
			soundMap.put("gotKey", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/UI/FA_Confirm_Button_1_4.wav"));
			soundMap.put("type", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/UI/CGM3_Dialogue_Text_01_2.wav"));
			soundMap.put("errorGate", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/UI/CGM3_Error_Button_03_2.wav"));
			soundMap.put("newAlly", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Misc/FA_Lap_Complete_1_1.wav"));
			soundMap.put("gateOpen", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Misc/PP_Skill_Unlock.wav"));
			soundMap.put("fire", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Misc/PP_Explosion_1_3.wav"));
			soundMap.put("escaped", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/Stingers_Jingles/Racing/FA_Positive_Notification_1.wav"));
			soundMap.put("noEscape", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Stinger/PP_Negative_Feedback.wav"));
			soundMap.put("ratHit", new Sound("res/audio/soundFX/Game Music Stingers and UI SFX Pack 2/UI_SFX/Misc/PP_Cute_Impact_1_1.wav"));
			
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}
	
	public AudioPlayer() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param key
	 * @return music from the music map given a key
	 */
	public static Music getMusic(String key) {
		return musicMap.get(key);
	}
	
	/**
	 * 
	 * @param key
	 * @return a sound from the soundmap given a key
	 */
	public static Sound getSound(String key) {
		return soundMap.get(key);
	}

}
