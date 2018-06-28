import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD {
	public int bounds = 0;
	public static float HEALTH = 100;
	private float greenValue = 255f;
	
	private int score = 0;
	private int level = 1;
	
	public void tick() {
		HEALTH = (int) Game.clamp(HEALTH, 0, 200 + (bounds/2));
		greenValue = HEALTH * 2;
		greenValue = (int)Game.clamp((float)greenValue, 0, 255);
		score++;
	}
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(15, 15, 200 + bounds, 32);
		g.setColor(new Color(100, (int)greenValue, 0));
		g.fillRect(15, 15, (int)HEALTH*2, 32);
		g.setColor(Color.WHITE);
		g.drawRect(15, 15, 200 + bounds, 32);
		
		Font fo = new Font("arial", 1, 13);
		g.setFont(fo);
		g.drawString("Score: " + score, 15, 64);
		g.drawString("Level: " + level, 15, 80);
		g.drawString("S for Shop", 15, 94);
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

}
