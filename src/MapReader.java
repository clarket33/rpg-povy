import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * MapReader takes in a xml file and draws the surrounding map using this file
 * @author clarkt5
 *
 */
public class MapReader {
	
	private ArrayList<String> layers;
	private Handler handler;
	private int torchCounter = 0;
	private int trapCounter = 0;
	private ArrayList<BufferedImage> transition;
	private int changeCount = 0;
	private int idleCount = 0;
	
	/**
	 * loads in the xml files and stores information about the map and tiles in various structures
	 * @param handler
	 */
	public MapReader(Handler handler) {
		this.handler = handler;
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		layers = new ArrayList<String>();
		Game.dungeonTiles = new ArrayList<BufferedImage>();
		Game.dungeonTiles.add(null);
		Game.collisionTiles.put(new Integer(0), new HashMap<String, ArrayList<Integer>>());
		Game.animationDungeon.put("floorTrap", new ArrayList<String>());
		Game.animationDungeon.put("gate", new ArrayList<String>());
		Game.animationDungeon.put("torch", new ArrayList<String>());
		Game.animationDungeon.put("chest", new ArrayList<String>());
		Game.animationDungeon.put("lever", new ArrayList<String>());
		Game.animationDungeonCounter.put("floorTrap", new Integer(0));
		Game.animationDungeonCounter.put("gate", new Integer(0));
		Game.animationDungeonCounter.put("torch", new Integer(0));
		Game.animationDungeonCounter.put("chest", new Integer(0));
		Game.animationDungeonCounter.put("lever", new Integer(0));
		
		
		transition = new ArrayList<BufferedImage>();
		transition.add(ss.grabImage(1, 1, 1280, 960,"transition"));
		transition.add(ss.grabImage(1, 2, 1280, 960,"transition"));
		transition.add(ss.grabImage(1, 3, 1280, 960,"transition"));
		transition.add(ss.grabImage(1, 4, 1280, 960,"transition"));
		transition.add(ss.grabImage(1, 5, 1280, 960,"transition"));
		transition.add(ss.grabImage(1, 6, 1280, 960,"transition"));
		
		transition.add(ss.grabImage(2, 1, 1280, 960,"transition"));
		transition.add(ss.grabImage(2, 2, 1280, 960,"transition"));
		transition.add(ss.grabImage(2, 3, 1280, 960,"transition"));
		transition.add(ss.grabImage(2, 4, 1280, 960,"transition"));
		transition.add(ss.grabImage(2, 5, 1280, 960,"transition"));
		transition.add(ss.grabImage(2, 6, 1280, 960,"transition"));
		
		transition.add(ss.grabImage(3, 1, 1280, 960,"transition"));
		transition.add(ss.grabImage(3, 2, 1280, 960,"transition"));
		transition.add(ss.grabImage(3, 3, 1280, 960,"transition"));
		
		transition.add(ss.grabImage(3, 3, 1280, 960,"transition"));
		transition.add(ss.grabImage(3, 4, 1280, 960,"transition"));
		transition.add(ss.grabImage(3, 5, 1280, 960,"transition"));
		transition.add(ss.grabImage(3, 6, 1280, 960,"transition"));
		
		transition.add(ss.grabImage(4, 1, 1280, 960,"transition"));
		transition.add(ss.grabImage(4, 2, 1280, 960,"transition"));
		transition.add(ss.grabImage(4, 3, 1280, 960,"transition"));
		transition.add(ss.grabImage(4, 4, 1280, 960,"transition"));
		transition.add(ss.grabImage(4, 5, 1280, 960,"transition"));
		transition.add(ss.grabImage(4, 6, 1280, 960,"transition"));
		
		transition.add(ss.grabImage(5, 1, 1280, 960,"transition"));
		transition.add(ss.grabImage(5, 2, 1280, 960,"transition"));
		transition.add(ss.grabImage(5, 3, 1280, 960,"transition"));
		transition.add(ss.grabImage(5, 4, 1280, 960,"transition"));
		transition.add(ss.grabImage(5, 5, 1280, 960,"transition"));
		
		
		
		
		for(int i = 1; i <= 24; i++) {
			for(int j = 1; j <= 20; j++) {
				Game.dungeonTiles.add(toBufferedImage(ss.grabImage(i, j, 16, 16, "dungeonTiles").getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING)));
			}
		}
		
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			File theMap = new File("res/Dungeon/rpg-dungeon-pack v1.1(wonderdot)/DUNGEON.xml");
			 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			 Document doc = dBuilder.parse(theMap);
			 doc.getDocumentElement().normalize();
			 NodeList nList = doc.getElementsByTagName("layer");
	         
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element eElement = (Element) nNode;
	               layers.add(eElement.getElementsByTagName("data").item(0).getTextContent());
	            }
	         }
	         
	        File tileSet = new File("res/Dungeon/rpg-dungeon-pack v1.1(wonderdot)/DungeonTileSet.xml");
	        dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(tileSet);
			doc.getDocumentElement().normalize();
			nList = doc.getElementsByTagName("tile");
			NodeList nList1 = doc.getElementsByTagName("frame");
			 
			 for (int temp = 0; temp < nList.getLength(); temp++) {
		            Node nNode = nList.item(temp);
		            
		            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		               Element eElement = (Element) nNode;
		               String cur = eElement.getAttribute("id");
		               Game.collisionTiles.get(new Integer(0)).put(cur, new ArrayList<Integer>());
		               
		        
		            }
		     }
			 
			 
			String[] curr;
	 		int x, y;
	 		int curID = 0;
	 		String prevID = "";
	 		int gateNum = 0;
	 		for(int i = 0; i < layers.size(); i++) {
	 	       	curr = layers.get(i).split(",");
	 	        x = 0;
	 	     	y = 0;
	 	       	for(int j = 0; j < curr.length; j++) {
	 	       		String stringCur = curr[j].replace("\n", "");
	 	       		int theID = Integer.parseInt(stringCur) - 1;
	 	       		stringCur = String.valueOf(theID);
	 	       		if(stringCur.contains("334")){
	 	       			handler.addObject(new Chest(x, y, ID.NonEnemy));
	 	       		}
		 	       	if(stringCur.contains("214") && !prevID.contains(stringCur)){
	 	       			handler.addObject(new Gate(x, y, ID.NonEnemy, gateNum));
	 	       			gateNum += 1;
	 	       		}
		 	       if(stringCur.contains("380")){
		 	    	  handler.addObject(new Lever(x, y, ID.NonEnemy, handler));
		 	       }
	 	       		if(Game.collisionTiles.get(new Integer(0)).containsKey(stringCur)) {
		 	       		curID = Integer.parseInt(curr[j].replace("\n", ""));
		 	       		Game.collisionTiles.get(new Integer(0)).get(stringCur).add(x);
		 	       		Game.collisionTiles.get(new Integer(0)).get(stringCur).add(y);
	 	       		}
	 	       		x += 32;
	 	       		if(x == 5120) {
	 	       			x = 0;
	 	       			y += 32;
	 	       		}
	 	       		prevID = stringCur;
	 	       	 }
	 		}
	 		
		         
		
		 
			 for (int temp = 0; temp < nList1.getLength(); temp++) {
		            Node nNode = nList1.item(temp);
		            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		               Element eElement = (Element) nNode;
		               int cur = 0;
		               String cur1 = "";
		               if(eElement.getAttribute("tileid").contains("214") ||  eElement.getAttribute("tileid").contains("154")) {
		            	   cur = Integer.parseInt(eElement.getAttribute("tileid"));
		            	   cur+=1;
		            	   cur1 = String.valueOf(cur);
		            	   Game.animationDungeon.get("gate").add(eElement.getAttribute("tileid"));
		               }
		               else if(eElement.getAttribute("tileid").contains("281") ||  eElement.getAttribute("tileid").contains("283") || 
		            		   eElement.getAttribute("tileid").contains("282")) {
		            	   cur = Integer.parseInt(eElement.getAttribute("tileid"));
		            	   cur+=1;
		            	   cur1 = String.valueOf(cur);
		            	  
		            	   Game.animationDungeon.get("floorTrap").add(cur1);
		            	   
		               }
		               else if(eElement.getAttribute("tileid").contains("300") || eElement.getAttribute("tileid").contains("301") || 
		            		   eElement.getAttribute("tileid").contains("302") || eElement.getAttribute("tileid").contains("303")) {
		            	   cur = Integer.parseInt(eElement.getAttribute("tileid"));
		            	   cur+=1;
		            	   cur1 = String.valueOf(cur);
		     
		            	   Game.animationDungeon.get("torch").add(cur1);
		               }
		               else if(eElement.getAttribute("tileid").contains("334") ||  eElement.getAttribute("tileid").contains("335")) {
		            	   cur = Integer.parseInt(eElement.getAttribute("tileid"));
		            	   cur+=1;
		            	   cur1 = String.valueOf(cur);
		            	   Game.animationDungeon.get("chest").add(eElement.getAttribute("tileid"));
		               }
		               else if(eElement.getAttribute("tileid").contains("380") ||  eElement.getAttribute("tileid").contains("381") || 
		            		   eElement.getAttribute("tileid").contains("382")) {
		            	   cur = Integer.parseInt(eElement.getAttribute("tileid"));
		            	   cur+=1;
		            	   cur1 = String.valueOf(cur);
		            	   Game.animationDungeon.get("lever").add(eElement.getAttribute("tileid"));
		               }
		            }
		            
		     }
			 
			
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	public void tick() {
		
	}
	
	/**
	 * renders the map
	 * @param g
	 */
	public void render(Graphics g) {
		
		for(int i = 0; i< handler.objects.size();i++) {
			if(handler.objects.get(i).id == ID.Povy) {
				followPlayer(g, handler.objects.get(i).getX(), handler.objects.get(i).getY());
			}
		}
		
		String[] cur;
		int x, y;
		int curID = 0;
		for(int i = 0; i < layers.size(); i++) {
			if(i == 2) {
				//System.out.println(Game.gameState);
				handler.render(g);
			}
		
	       	cur = layers.get(i).split(",");
	        x = 0;
	     	y = 0;
	       	for(int j = 0; j < cur.length; j++) {
	       		String stringCur = cur[j].replace("\n", "");
	       		curID = Integer.parseInt(cur[j].replace("\n", ""));
	       		if(curID != 0) {
	       			if(Game.animationDungeon.get("torch").contains(cur[j])) {
	       				g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("torch").get(Game.animationDungeonCounter.get("torch")))), x, y, null);
	       				torchCounter++;
	       				if(torchCounter == 96) {
	       					
	       					Game.animationDungeonCounter.put("torch", Game.animationDungeonCounter.get("torch") + 1);
	       					torchCounter = 0;
	       					if(Game.animationDungeonCounter.get("torch") == Game.animationDungeon.get("torch").size()) {
		       					
		       					Game.animationDungeonCounter.put("torch", new Integer(0));
		       				}
	       				}
	       				
	       			}
	       			else if(Game.animationDungeon.get("floorTrap").contains(cur[j])) {
	       				if(Game.gameState != Game.STATE.Paused) {
		       				g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrap").get(Game.animationDungeonCounter.get("floorTrap")))), x, y, null);
		       				trapCounter++;
		       				if(trapCounter == 11000) {
		       					
		       					Game.animationDungeonCounter.put("floorTrap", Game.animationDungeonCounter.get("floorTrap") + 1);
		       					trapCounter = 0;
		       					if(Game.animationDungeonCounter.get("floorTrap") == 1) {
		       						g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrap").get(Game.animationDungeonCounter.get("floorTrap")))), x, y, null);
		       						Game.animationDungeonCounter.put("floorTrap", Game.animationDungeonCounter.get("floorTrap") + 1);
		       					}
		       					if(Game.animationDungeonCounter.get("floorTrap") == Game.animationDungeon.get("floorTrap").size()) {
			       					Game.animationDungeonCounter.put("floorTrap", new Integer(0));
			       				}
		       				}
	       				
	       				}
	       				else {
	       					g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrap").get(Game.animationDungeonCounter.get("floorTrap")))), x, y, null);
	       				}
	       			}
	       			else {
	       				/**
	       				if(Game.collisionTiles.get(new Integer(0)).containsKey(stringCur)) {
	       					g.setColor(Color.green);
	       					g.drawRect(x, y, 32, 32);
	       				}
	       				**/
	       				if(curID != 335 && curID != 215 && curID != 381) {
	       					g.drawImage(Game.dungeonTiles.get(curID), x, y, null);
	       				}
	       			}
	       		}
	       		x += 32;
	       		if(x == 5120) {
	       			x = 0;
	       			y += 32;
	       		}
	       		
	       	 }
	       	if(i == layers.size()-1 && Game.gameState == Game.STATE.Transition) {
	       		g.drawImage(transition.get(idleCount), Game.camX, Game.camY, null);
	       		
				changeCount++;
				if(changeCount % 4 == 0) {
					idleCount++;
				}
				
				if(idleCount == 15) {
					changeCount = 0;
					Game.battleReturn = true;
					Game.camX = 0;
					Game.camY = 0;
					Game.gameState = Game.STATE.Battle;
				}
			}
	       	if(Game.gameState == Game.STATE.PostBattle) {
	       		g.drawImage(transition.get(15), Game.camX, Game.camY, null);
	       		if(Battle.expToBeAdded != 0) {
	       			if(ExperienceBar.exp == ExperienceBar.expToUpgrade) {
	       				AudioPlayer.getSound("levelUp").play(1, (float).1);
	       				ExperienceBar.exp = 0;
	       				ExperienceBar.expToUpgrade += 25;
	       				ExperienceBar.levelUp = true;
	       			}
	       			Battle.expToBeAdded -= 1;
	       			ExperienceBar.exp += 1;
	       			AudioPlayer.getSound("expAdding").play(1, (float).1);
	       		}
	       		return;
	       	}
	       	if(i == layers.size()-1 && Game.battleReturn == true) {
	       		g.drawImage(transition.get(idleCount), Game.camX, Game.camY, null);
	       		
				changeCount++;
				if(changeCount % 1 == 0) {
					idleCount++;
				}
				
				if(idleCount == 30) {
					idleCount = 0;
					changeCount = 0;
					if(AudioPlayer.getMusic("afterBattle").playing()) {
						AudioPlayer.getMusic("afterBattle").stop();
						AudioPlayer.getMusic("dungeon").loop(1, (float).1);
						if(Game.firstBattle) {
							Game.firstBattle = false;
							Game.gameState = Game.STATE.KeyFromGrogo;
						}
					}
					Game.battleReturn = false;
				}
	       	}
		}
		
		/**Check Collision
		for(int i = 0; i < layers.size(); i++) {
		
			if(i == 1) {
				break;
			}
			
			cur = layers.get(i).split(",");
			for(int j = 0; j < cur.length; j++) {
	       		String stringCur = cur[j].replace("\n", "");
	       		int theID = Integer.parseInt(stringCur) - 1;
 	       		stringCur = String.valueOf(theID);
				if(Game.collisionTiles.get(new Integer(0)).containsKey(stringCur)) {
					for(int k = 0; k < Game.collisionTiles.get(new Integer(0)).get(stringCur).size(); k+=2) {
						g.setColor(Color.green);
	   					g.drawRect(Game.collisionTiles.get(new Integer(0)).get(stringCur).get(k), Game.collisionTiles.get(new Integer(0)).get(stringCur).get(k+1),
	   							32, 32);
					}	
				}
			}
		}
		**/
		/**
		Iterator<String> itr = Game.collisionTiles.get(new Integer(0)).keySet().iterator();
		while(itr.hasNext()) {
			String currr = itr.next();
				
			for(int i = 0; i < Game.collisionTiles.get(new Integer(0)).get(currr).size(); i+=2) {
				g.setColor(Color.green);
				g.drawRect(Game.collisionTiles.get(new Integer(0)).get(currr).get(i), Game.collisionTiles.get(new Integer(0)).get(currr).get(i+1),32,32);
			}
		}
		**/
	    
	}
	
	/**
	 * allows the camera to follow the player
	 * @param g
	 * @param x
	 * @param y
	 */
	private void followPlayer(Graphics g, float x, float y) {
		int offsetMaxX = 3840;
		int offsetMaxY = 2880;
		int offsetMinX = 0;
		int offsetMinY = 0;
		Game.camX = (int)x - 1280 / 2;
		Game.camY = (int)y - 960 / 2;
		if(Game.camX > offsetMaxX)
		    Game.camX = offsetMaxX;
		else if(Game.camX < offsetMinX)
		    Game.camX = offsetMinX;
		if(Game.camY > offsetMaxY)
		    Game.camY = offsetMaxY;
		else if(Game.camY < offsetMinY)
		    Game.camY = offsetMinY;
		g.translate(-Game.camX, -Game.camY);
	}
	
	/**
	 * 
	 * @param img
	 * @return buffered image converted from an image
	 */
	private static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	

}
