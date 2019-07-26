import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
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
	
	private Map<Integer, ArrayList<Integer>> layers;
	private Handler handler;
	private int torchCounter = 0, shipX = 4710, shipY = 3450;
	private int trapCounter = 0, trapCounterA = 0, trapCounterB = 0;
	private ArrayList<BufferedImage> transition, trans2;
	private int changeCount = 0;
	private int idleCount = 0, textCount = 0;;
	private int animationHold = 0, animationHoldA = 0, animationHoldB = 0;
	private boolean doLeft = true;
	private Map<Integer, ArrayList<Integer>> unusedTiles;
	private Povy povy;
	private BufferedImage grogoShip, grogoShipNoShad;
	private int tempX, tempY;
	private boolean introTitle = false, across = false;
	private ArrayList<BufferedImage> sparkle = new ArrayList<BufferedImage>();
	private int sparkleCount = 0;
	private int countControl = 0;
	
	/**
	 * loads in the xml files and stores information about the map and tiles in various structures
	 * @param handler
	 */
	public MapReader(Handler handler) {
		this.handler = handler;
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		layers = new HashMap<Integer, ArrayList<Integer>>();
		Game.dungeonTiles = new ArrayList<BufferedImage>();
		unusedTiles = new HashMap<Integer, ArrayList<Integer>>();
		Game.dungeonTiles.add(null);
		Game.collisionTiles.put(new Integer(0), new HashMap<String, ArrayList<Integer>>());
		Game.animationDungeon.put("floorTrap", new ArrayList<String>());
		Game.animationDungeon.put("gate", new ArrayList<String>());
		Game.animationDungeon.put("torch", new ArrayList<String>());
		Game.animationDungeon.put("chest", new ArrayList<String>());
		Game.animationDungeon.put("lever", new ArrayList<String>());
		Game.animationDungeon.put("floorTrapB", new ArrayList<String>());
		Game.animationDungeon.put("floorTrapA", new ArrayList<String>());
		Game.animationDungeonCounter.put("floorTrap", new Integer(1));
		Game.animationDungeonCounter.put("floorTrapB", new Integer(0));
		Game.animationDungeonCounter.put("floorTrapA", new Integer(0));
		Game.animationDungeonCounter.put("gate", new Integer(0));
		Game.animationDungeonCounter.put("torch", new Integer(0));
		Game.animationDungeonCounter.put("chest", new Integer(0));
		Game.animationDungeonCounter.put("lever", new Integer(0));
		
		grogoShip = null;
		grogoShipNoShad = null;
			
	    try {
	        grogoShip = ImageIO.read(new File("res/grogoShip.png"));
	        grogoShipNoShad = ImageIO.read(new File("res/grogoShipNoShad.png"));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
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
		
		trans2 = new ArrayList<BufferedImage>();
		trans2.add(ss.grabImage(1, 1, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(1, 2, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(1, 3, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(1, 4, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(2, 1, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(2, 2, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(2, 3, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(2, 4, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(3, 1, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(3, 2, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(3, 3, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(3, 4, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(4, 1, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(4, 2, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(4, 3, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(4, 4, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(5, 1, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(5, 2, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(5, 3, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(5, 4, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(6, 1, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(6, 2, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(6, 3, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(6, 4, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(7, 1, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(7, 2, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(7, 3, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(7, 4, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(8, 1, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(8, 2, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(8, 3, 1280, 960,"viniaOpening"));
		trans2.add(ss.grabImage(8, 4, 1280, 960,"viniaOpening"));
	//	trans2.add(ss.grabImage(9, 1, 1280, 960,"viniaOpening"));


		sparkle.add(ss.grabImage(1, 1, 124, 101, "sparkle"));
        sparkle.add(ss.grabImage(1, 2, 124, 101, "sparkle"));
        sparkle.add(ss.grabImage(1, 3, 124, 101, "sparkle"));
        sparkle.add(ss.grabImage(1, 4, 124, 101, "sparkle"));
        sparkle.add(ss.grabImage(2, 1, 124, 101, "sparkle"));
        sparkle.add(ss.grabImage(2, 2, 124, 101, "sparkle"));
        sparkle.add(ss.grabImage(2, 3, 124, 101, "sparkle"));
        sparkle.add(ss.grabImage(2, 4, 124, 101, "sparkle"));
		
		
		
		for(int i = 1; i <= 24; i++) {
			for(int j = 1; j <= 20; j++) {
				Game.dungeonTiles.add(toBufferedImage(ss.grabImage(i, j, 16, 16, "dungeonTiles").getScaledInstance(48, 48, Image.SCALE_AREA_AVERAGING)));
			}
		}
		for(int i = 1; i <= 7; i++) {
			for(int j = 1; j <= 6; j++) {
				Game.dungeonTiles.add(toBufferedImage(ss.grabImage(i, j, 16, 16, "dungeonShadows").getScaledInstance(48, 48, Image.SCALE_AREA_AVERAGING)));
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
	               String tempStr =  (eElement.getElementsByTagName("data").item(0).getTextContent());
	               ArrayList<Integer> ints = new ArrayList<Integer>();
	               String[] strArr = tempStr.split(",");
	               for(int i = 0; i < strArr.length; i++) {
	            	   String newStr = strArr[i].replace("\n", "");
	            	   ints.add(Integer.parseInt(newStr));
	               }
	               layers.put((new Integer(temp)), ints);
	               //System.out.println(strArr);
	            }
	         }
	         
	        File tileSet = new File("res/Dungeon/rpg-dungeon-pack v1.1(wonderdot)/DungeonTileSet.xml");
	        dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(tileSet);
			doc.getDocumentElement().normalize();
			nList = doc.getElementsByTagName("tile");
			NodeList nList1 = doc.getElementsByTagName("frame");
			//System.out.println(nList1.getLength());
			 for (int temp = 0; temp < nList.getLength(); temp++) {
		            Node nNode = nList.item(temp);
		            
		            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		               Element eElement = (Element) nNode;
		               String cur = eElement.getAttribute("id");
		               Game.collisionTiles.get(new Integer(0)).put(cur, new ArrayList<Integer>());
		               
		        
		            }
		     }
			 
			 
			//String[] curr;
	 		int x, y;
	 		int prevID = 0;
	 		int gateNum = 0;
	 		for(int i = 0; i < layers.size(); i++) {
	 	       	ArrayList<Integer> curr = layers.get(i);
	 	        x = 0;
	 	     	y = 0;
	 	       	for(int j = 0; j < curr.size(); j++) {
	 	       		int theID = curr.get(j) - 1;
		 	       	if(theID == 327){
		 	       		if(x == 4560) handler.addObject(new Stair(x, y, ID.NonEnemy, 0, handler));
		 	       		else if(x == 3168) handler.addObject(new Stair(x, y, ID.NonEnemy, 1, handler));
		 	       		else if(y == 1200) handler.addObject(new Stair(x, y, ID.NonEnemy, 2, handler));
		 	       		else if(y == 1632) handler.addObject(new Stair(x, y, ID.NonEnemy, 3, handler));
		 	       		else if(y == 2112) handler.addObject(new Stair(x, y, ID.NonEnemy, 4, handler));
		 	       		else if(y == 4800) handler.addObject(new Stair(x, y, ID.NonEnemy, 5, handler));
		 	       		else if(y == 1920) handler.addObject(new Stair(x, y, ID.NonEnemy, 6, handler));
		 	       		else if(y == 1488) handler.addObject(new Stair(x, y, ID.NonEnemy, 7, handler));
		 	       		else if(y == 3360) handler.addObject(new Stair(x, y, ID.NonEnemy, 9, handler));
		 	       	}
	 	       		if(theID == 334){
	 	       			handler.addObject(new Chest(x, y, ID.NonEnemy));
	 	       		}
		 	       	if(theID == 214 && prevID != theID){
	 	       			handler.addObject(new Gate(x, y, ID.NonEnemy, gateNum));
	 	       			gateNum += 1;
	 	       		}
		 	       if(theID == 365) {
		 	    	   handler.addObject(new Pillar(x, y, ID.NonEnemy, 366, handler));
		 	       }
		 	       if(theID == 366) {
		 	    	   handler.addObject(new Pillar(x, y, ID.NonEnemy, 367, handler));
		 	       }
		 	       if(theID == 367) {
		 	    	   handler.addObject(new Pillar(x, y, ID.NonEnemy, 368, handler));
		 	       }
		 	       if(theID == 380){
		 	    	  handler.addObject(new Lever(x, y, ID.NonEnemy, handler));
		 	       }
		 	      
	 	       		if(Game.collisionTiles.get(new Integer(0)).containsKey(String.valueOf(theID))) {
		 	       		Game.collisionTiles.get(new Integer(0)).get(String.valueOf(theID)).add(x);
		 	       		Game.collisionTiles.get(new Integer(0)).get(String.valueOf(theID)).add(y);
	 	       		}
	 	       		x += 48;
	 	       		if(x == 7680) {
	 	       			x = 0;
	 	       			y += 48;
	 	       		}
	 	       		prevID = theID;
	 	       	 }
	 		}
	 		
	 		
	 		Game.animationDungeon.get("floorTrap").add("282");
	 		Game.animationDungeon.get("floorTrap").add("284");
	 		Game.animationDungeon.get("floorTrap").add("283");
	 		Game.animationDungeon.get("floorTrap").add("284");
	 		
	 		Game.animationDungeon.get("floorTrapA").add("282");
	 		Game.animationDungeon.get("floorTrapA").add("284");
	 		Game.animationDungeon.get("floorTrapA").add("283");
	 		Game.animationDungeon.get("floorTrapA").add("284");
	 		
	 		Game.animationDungeon.get("floorTrapB").add("282");
	 		Game.animationDungeon.get("floorTrapB").add("284");
	 		Game.animationDungeon.get("floorTrapB").add("283");
	 		Game.animationDungeon.get("floorTrapB").add("284");
	 		
	 		
		
		 
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
		if(Game.gameState == Game.STATE.levelTwoTran) {
			//shipY -= 1;
			if(shipX < 7000 && !introTitle) {
				if(shipY < 3250) {
					shipX += (.0025 * shipX);
				}
				else shipY -= 2;
			}
			else {
				if(!introTitle) {
					introTitle = true;
				}
			}
					
			return;
		}
		ArrayList<Integer> curr = layers.get(3);
       	for(int j = 0; j < curr.size(); j++) {
       		
       		int curID = curr.get(j);
       		if(curID == 282 || curID == 283 || curID == 284 || curID == 301) {
       			
       			if(Game.animationDungeon.get("torch").contains(String.valueOf(curID))) {
       				//g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("torch").get(Game.animationDungeonCounter.get("torch")))), x, y, null);
       				torchCounter++;
       				if(torchCounter == 300) {
       					
       					Game.animationDungeonCounter.put("torch", Game.animationDungeonCounter.get("torch") + 1);
       					torchCounter = 0;
       					if(Game.animationDungeonCounter.get("torch") == Game.animationDungeon.get("torch").size()) {
	       					
	       					Game.animationDungeonCounter.put("torch", new Integer(0));
	       				}
       				}
       				
       			}
       			else if(String.valueOf(curID).contains("282")) {
       				if(Game.gameState != Game.STATE.Paused) {
       					
       					if(animationHold == 0) {
       						trapCounter++;
       					}
	       				if(trapCounter == 23000 || animationHold != 0) {
	       					if(Game.animationDungeonCounter.get("floorTrap") == 0 || Game.animationDungeonCounter.get("floorTrap") == 2) {
		       					Game.animationDungeonCounter.put("floorTrap", Game.animationDungeonCounter.get("floorTrap") + 1);
		       					trapCounter = 0;
	       					}
	       					if(Game.animationDungeonCounter.get("floorTrap") == 1) {
	       						//g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrap").get(Game.animationDungeonCounter.get("floorTrap")))), x, y, null);
	       						if(animationHold == 0) {
	       							animationHold++;
	       						}
	       						else {
	       							animationHold++;
	       							if(animationHold == 200) {
	       								animationHold = 0;
	       								Game.animationDungeonCounter.put("floorTrap", Game.animationDungeonCounter.get("floorTrap") + 1);
	       								trapCounter = 0;
	       							}
	       						}
	       					}
	       					else if(Game.animationDungeonCounter.get("floorTrap") == 3) {
	       						//g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrap").get(Game.animationDungeonCounter.get("floorTrap")))), x, y, null);
	       						if(animationHold == 0) {
	       							animationHold++;
	       						}
	       						else {
	       							animationHold++;
	       							if(animationHold == 200) {
	       								animationHold = 0;
	       								Game.animationDungeonCounter.put("floorTrap", new Integer(0));
	       								trapCounter = 0;
	       							}
	       						}
	       					}
	       					
	       				}
	       				else {
	       					//g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrap").get(Game.animationDungeonCounter.get("floorTrap")))), x, y, null);
	       				}
	       				
       				
       				}
       				else {
       					//g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrap").get(Game.animationDungeonCounter.get("floorTrap")))), x, y, null);
       					
       				}
       				
       			}
       			
       			
       			else if(String.valueOf(curID).contains("283")) {
       				
       				if(doLeft) {
	       				if(Game.gameState != Game.STATE.Paused) {
	       					if(animationHoldA == 0) {
	       						trapCounterA++;
	       					}
		       				if(trapCounterA == 12000 || animationHoldA != 0) {
		       					if(Game.animationDungeonCounter.get("floorTrapA") == 0 || Game.animationDungeonCounter.get("floorTrapA") == 2) {
			       					Game.animationDungeonCounter.put("floorTrapA", Game.animationDungeonCounter.get("floorTrapA") + 1);
			       					trapCounterA = 0;
		       					}
		       					if(Game.animationDungeonCounter.get("floorTrapA") == 1) {
		       						//g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapA").get(Game.animationDungeonCounter.get("floorTrapA")))), x, y, null);
		       						if(animationHoldA == 0) {
		       							animationHoldA++;
		       						}
		       						else {
		       							animationHoldA++;
		       							if(animationHoldA == 200) {
		       								animationHoldA = 0;
		       								Game.animationDungeonCounter.put("floorTrapA", Game.animationDungeonCounter.get("floorTrapA") + 1);
		       								trapCounterA = 0;
		       							}
		       						}
		       					}
		       					else if(Game.animationDungeonCounter.get("floorTrapA") == 3) {
		       						//g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapA").get(Game.animationDungeonCounter.get("floorTrapA")))), x, y, null);
		       						if(animationHoldA == 0) {
		       							animationHoldA++;
		       						}
		       						else {
		       							animationHoldA++;
		       							if(animationHoldA == 200) {
		       								animationHoldA = 0;
		       								Game.animationDungeonCounter.put("floorTrapA", new Integer(0));
		       								trapCounterA = 0;
		       								doLeft = false;
		       							}
		       						}
		       					}
		       					
		       				}
		       				else {
		       					//g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapA").get(Game.animationDungeonCounter.get("floorTrapA")))), x, y, null);
		       				}
	       				
	       				}
	       				else {
	       					//g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapA").get(Game.animationDungeonCounter.get("floorTrapA")))), x, y, null);
	       				}
       				}
       				else {
       					//g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapA").get(Game.animationDungeonCounter.get("floorTrapA")))), x, y, null);
       				}
       				
       			}
       			
       			
       			
       			
       			
       			else if(String.valueOf(curID).contains("284")) {
       				
       				if(!doLeft) {
	       				if(Game.gameState != Game.STATE.Paused) {
	       					if(animationHoldB == 0) {
	       						trapCounterB++;
	       					}
		       				if(trapCounterB == 12000 || animationHoldB != 0) {
		       					if(Game.animationDungeonCounter.get("floorTrapB") == 0 || Game.animationDungeonCounter.get("floorTrapB") == 2) {
			       					Game.animationDungeonCounter.put("floorTrapB", Game.animationDungeonCounter.get("floorTrapB") + 1);
			       					trapCounterB = 0;
		       					}
		       					if(Game.animationDungeonCounter.get("floorTrapB") == 1) {
		       						//g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapB").get(Game.animationDungeonCounter.get("floorTrapB")))), x, y, null);
		       						if(animationHoldB == 0) {
		       							animationHoldB++;
		       						}
		       						else {
		       							animationHoldB++;
		       							if(animationHoldB == 200) {
		       								animationHoldB = 0;
		       								Game.animationDungeonCounter.put("floorTrapB", Game.animationDungeonCounter.get("floorTrapB") + 1);
		       								trapCounterB = 0;
		       							}
		       						}
		       					}
		       					else if(Game.animationDungeonCounter.get("floorTrapB") == 3) {
		       						//g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapB").get(Game.animationDungeonCounter.get("floorTrapB")))), x, y, null);
		       						if(animationHoldB == 0) {
		       							animationHoldB++;
		       						}
		       						else {
		       							animationHoldB++;
		       							if(animationHoldB == 200) {
		       								animationHoldB = 0;
		       								Game.animationDungeonCounter.put("floorTrapB", new Integer(0));
		       								trapCounterB = 0;
		       								doLeft = true;
		       							}
		       						}
		       					}
		       					
		       				}
		       				else {
		       					//g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapB").get(Game.animationDungeonCounter.get("floorTrapB")))), x, y, null);
		       				}
	       				
	       				}
	       				else {
	       					//g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapB").get(Game.animationDungeonCounter.get("floorTrapB")))), x, y, null);
	       				}
       				}
       				else {
       					//g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapB").get(Game.animationDungeonCounter.get("floorTrapB")))), x, y, null);
       				}
       			}
       		}
       	}
       				
       			
	}
	
	/**
	 * renders the map
	 * @param g
	 */
	public void render(Graphics g) {
		if(Game.gameState == Game.STATE.levelTwoTran) {
			if(!introTitle) {
				Game.camX = tempX;
				Game.camY = tempY;
				g.translate(-Game.camX, -Game.camY);
				g.drawImage(transition.get(15), Game.camX, Game.camY, null);
				g.drawImage(grogoShip, shipX, shipY, null);
			}
			else {
				g.drawImage(trans2.get(textCount), 0, 0, null);
				if(textCount == 7 && changeCount == 0) {
					AudioPlayer.getSound("chapStart").play(1, (float).1);
					try {
						Thread.sleep(3700);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
				if(textCount != 30) {
					//System.out.println(textCount);
					changeCount++;
					if(changeCount % 8 == 0) {
						changeCount = 0;
						if(textCount == 14) {
							countControl++;
							if(countControl == 20) textCount++;
						}
						else textCount++;
					}
				}
				
				
				
				
				if(textCount == 30) {
					g.drawImage(trans2.get(textCount), 0, 0, null);
				}
				if(textCount == 30 && !across) {
					g.drawImage(sparkle.get(sparkleCount), 1010, 117, null);
					if(sparkleCount == 1 && changeCount == 0) AudioPlayer.getSound("starClick").play(1, (float).7);
					changeCount++;
					if(changeCount % 5 == 0) {
						changeCount = 0;
						sparkleCount++;
					}
					if(sparkleCount == 8) {
						across = true;
						sparkleCount = 0;
						//Placeholder, load up levelTwo
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.exit(0);
					}
					
				}
				
				
				
			}
			return;
		}
		
		for(int i = 0; i< handler.objects.size();i++) {
			if(handler.objects.get(i).id == ID.Povy) {
				followPlayer(g, handler.objects.get(i).getX(), handler.objects.get(i).getY());
			}
		}
		
		
		//String[] cur;
		int x, y;
		int curID = 0;
		for(int i = 0; i < layers.size(); i++) {
			//layer where Povy will appear above some objects and below others
			if(i == 4) {
				
				for(int m = 0; m< handler.objects.size();m++) {
	    			if(handler.objects.get(m).id == ID.NonEnemy) {
	    				handler.objects.get(m).render(g);
	    			}
				}
				
				//cur = layers.get(i).split(",");
				int thisX = 0;
		     	int thisY = 0;
		    	ArrayList<Integer> curr = layers.get(i);
				for(int k = 0; k < curr.size(); k++) {
					if(!Game.shouldRender(thisX, thisY)) {
						thisX += 48;
			       		if(thisX == 7680) {
			       			thisX = 0;
			       			thisY += 48;
			       		}
						continue;
					}
		       		curID = curr.get(k);
		       		if(curID != 0) {
			       		for(int m = 0; m< handler.objects.size();m++) {
			    			if(handler.objects.get(m).id == ID.Povy) {
			    				Povy p = (Povy)(handler.objects.get(m));
			    				if(curID >= 241 && curID <= 246) {
			    					if(unusedTiles.get(curID)== null) unusedTiles.put(curID, new ArrayList<Integer>());
			    					unusedTiles.get(curID).add(thisX);
			    					unusedTiles.get(curID).add(thisY);
			    					continue;
			    				}
			    				if(curID >= 261 && curID <= 266) {
			    					if(unusedTiles.get(curID)== null) unusedTiles.put(curID, new ArrayList<Integer>());
			    					unusedTiles.get(curID).add(thisX);
			    					unusedTiles.get(curID).add(thisY);
			    					continue;
			    				}
			    				if(p.getY()>(thisY)) {
			    					
			    					g.drawImage(Game.dungeonTiles.get(curID), thisX, thisY, null);
			    				}
			    				else {
			    					if(unusedTiles.get(curID)== null) unusedTiles.put(curID, new ArrayList<Integer>());
			    					unusedTiles.get(curID).add(thisX);
			    					unusedTiles.get(curID).add(thisY);
			    					
			    				}
			    					
			    			}
			    		}
		       		}
		       		thisX += 48;
		       		if(thisX == 7680) {
		       			thisX = 0;
		       			thisY += 48;
		       		}
		    	}
		       		
				handler.livingRender(g);
				
				Iterator<Integer> itr = unusedTiles.keySet().iterator();
				while(itr.hasNext()) {
					int curNum = itr.next();
					for(int m = 0; m < unusedTiles.get(curNum).size(); m+= 2) {
						
						g.drawImage(Game.dungeonTiles.get(curNum), unusedTiles.get(curNum).get(m), unusedTiles.get(curNum).get(m+1), null);
					}
				}
				
				unusedTiles.clear();
				
				handler.chestDisplayRender(g);
				
				
			}
		
	       	
	    	ArrayList<Integer> curr = layers.get(i);
	        x = 0;
	     	y = 0;
	       	for(int j = 0; j < curr.size(); j++) {
	       		if(!Game.shouldRender(x, y)) {
		       				
	       			x += 48;
		       		if(x == 7680) {
		       			x = 0;
		       			y += 48;
		       		}
	       			continue;
	       		}
	       		curID = curr.get(j);
	       		if(curID != 0) {
	       			
	       			if(Game.animationDungeon.get("torch").contains(String.valueOf(curID))) {
	       				
	       				g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("torch").get(Game.animationDungeonCounter.get("torch")))), x, y, null);
	       				
	       				
	       			}
	       			else if(String.valueOf(curID).contains("282")) {
	       				if(Game.gameState != Game.STATE.Paused) {
	       					
	       					
		       				if(trapCounter == 10000 || animationHold != 0) {
		       					
		       					if(Game.animationDungeonCounter.get("floorTrap") == 1) {
		       						g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrap").get(Game.animationDungeonCounter.get("floorTrap")))), x, y, null);
		       						
		       					}
		       					else if(Game.animationDungeonCounter.get("floorTrap") == 3) {
		       						g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrap").get(Game.animationDungeonCounter.get("floorTrap")))), x, y, null);
		       						
		       					}
		       					
		       				}
		       				else {
		       					g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrap").get(Game.animationDungeonCounter.get("floorTrap")))), x, y, null);
		       				}
		       				
	       				
	       				}
	       				else {
	       					g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrap").get(Game.animationDungeonCounter.get("floorTrap")))), x, y, null);
	       					
	       				}
	       				
	       			}
	       			
	       			
	       			else if(String.valueOf(curID).contains("283")) {
	       				
	       				if(doLeft) {
		       				if(Game.gameState != Game.STATE.Paused) {
		       					
			       				if(trapCounterA == 1500 || animationHoldA != 0) {
			       					
			       					if(Game.animationDungeonCounter.get("floorTrapA") == 1) {
			       						g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapA").get(Game.animationDungeonCounter.get("floorTrapA")))), x, y, null);
			       						
			       					}
			       					else if(Game.animationDungeonCounter.get("floorTrapA") == 3) {
			       						g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapA").get(Game.animationDungeonCounter.get("floorTrapA")))), x, y, null);
			       						
			       					}
			       					
			       				}
			       				else {
			       					g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapA").get(Game.animationDungeonCounter.get("floorTrapA")))), x, y, null);
			       				}
		       				
		       				}
		       				else {
		       					g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapA").get(Game.animationDungeonCounter.get("floorTrapA")))), x, y, null);
		       				}
	       				}
	       				else {
	       					g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapA").get(Game.animationDungeonCounter.get("floorTrapA")))), x, y, null);
	       				}
	       				
	       			}
	       			
	       			
	       			else if(String.valueOf(curID).contains("284")) {
	       				
	       				if(!doLeft) {
		       				if(Game.gameState != Game.STATE.Paused) {
		       					
			       				if(trapCounterB == 1500 || animationHoldB != 0) {
			       					
			       					if(Game.animationDungeonCounter.get("floorTrapB") == 1) {
			       						g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapB").get(Game.animationDungeonCounter.get("floorTrapB")))), x, y, null);
			       						
			       					}
			       					else if(Game.animationDungeonCounter.get("floorTrapB") == 3) {
			       						g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapB").get(Game.animationDungeonCounter.get("floorTrapB")))), x, y, null);
			       						
			       					}
			       					
			       				}
			       				else {
			       					g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapB").get(Game.animationDungeonCounter.get("floorTrapB")))), x, y, null);
			       				}
		       				
		       				}
		       				else {
		       					g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapB").get(Game.animationDungeonCounter.get("floorTrapB")))), x, y, null);
		       				}
	       				}
	       				else {
	       					g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapB").get(Game.animationDungeonCounter.get("floorTrapB")))), x, y, null);
	       				}
	       				
	       			}
	       			
	       			else {
	       				if(curID != 335 && curID != 215 && curID != 381 && curID != 366 && curID != 367 && curID != 368 && i != 4 && curID != 328) {
	       					
	       					g.drawImage(Game.dungeonTiles.get(curID), x, y, null);
	       					
	       				}
	       			}
	       		}
	       		x += 48;
	       		if(x == 7680) {
	       			x = 0;
	       			y += 48;
	       		}
	       		
	       	 }
	       	if(i == layers.size()-1 && (Game.gameState == Game.STATE.Game || Game.gameState == Game.STATE.AfterZatolib)) {
	       		for(int j = 0; j < handler.objects.size(); j++) {
					//System.out.println(handler.objects.get(j).getID());
					if(handler.objects.get(j).id == ID.Povy) {
						povy = (Povy) handler.objects.get(j);
			       		for(int p = 0; p < handler.objects.size(); p++) {
			       			if(handler.objects.get(p).id == ID.SpaceShip) {
			       				if(povy.getBounds().intersects(handler.objects.get(p).getBounds())) {
			       					SpaceShip s = (SpaceShip)handler.objects.get(p);
									if(!s.isSelected()) {
										g.drawImage(Game.crystalConfirm.get(Game.crysConfCount), (int)s.getX()+300, (int)s.getY()-50, null);
										changeCount++;
										if(changeCount % 20 == 0) {
											Game.crysConfCount++;
											changeCount = 0;
										}
										if(Game.crysConfCount == 2) {
											Game.crysConfCount = 0;
										}
										return;
									}
			       				}
			       			}
							if(handler.objects.get(p).id == ID.NonEnemy) {
								if(povy.getBounds().intersects(handler.objects.get(p).getBounds())) {
									if(handler.objects.get(p) instanceof Chest) {
										Chest c = (Chest)handler.objects.get(p);
										if(!c.isOpen()) {
											g.drawImage(Game.crystalConfirm.get(Game.crysConfCount), (int)c.getX(), (int)c.getY()-50, null);
											changeCount++;
											if(changeCount % 20 == 0) {
												Game.crysConfCount++;
												changeCount = 0;
											}
											if(Game.crysConfCount == 2) {
												Game.crysConfCount = 0;
											}
										}
									}
									if(handler.objects.get(p) instanceof Stair) {
										//System.out.println("huhhh");
										Stair temp = (Stair)handler.objects.get(p);
										if(temp.getNum() != 9) {
											g.drawImage(Game.crystalConfirm.get(Game.crysConfCount), (int)handler.objects.get(p).getX(), (int)handler.objects.get(p).getY()-50, null);
											changeCount++;
											if(changeCount % 20 == 0) {
												Game.crysConfCount++;
												changeCount = 0;
											}
											if(Game.crysConfCount == 2) {
												Game.crysConfCount = 0;
											}
										}
									}
									if(handler.objects.get(p) instanceof Gate) {
										Gate ga = (Gate)handler.objects.get(p);
										if(!ga.isOpened()) {
											g.drawImage(Game.crystalConfirm.get(Game.crysConfCount), (int)ga.getX()+24, (int)ga.getY()-40, null);
											changeCount++;
											if(changeCount % 20 == 0) {
												Game.crysConfCount++;
												changeCount = 0;
											}
											if(Game.crysConfCount == 2) {
												Game.crysConfCount = 0;
											}
										}
									}
									if(handler.objects.get(p) instanceof Lever) {
										Lever l = (Lever)handler.objects.get(p);
										if(!l.isPushed()) {
											g.drawImage(Game.crystalConfirm.get(Game.crysConfCount), (int)l.getX(), (int)l.getY()-50, null);
											changeCount++;
											if(changeCount % 20 == 0) {
												Game.crysConfCount++;
												changeCount = 0;
											}
											if(Game.crysConfCount == 2) {
												Game.crysConfCount = 0;
											}
										}
									}
									if(handler.objects.get(p) instanceof Pillar) {
										Pillar pi = (Pillar)handler.objects.get(p);
										if(pi.canSelect() && !povy.isHit()) {
											g.drawImage(Game.crystalConfirm.get(Game.crysConfCount), (int)pi.getX(), (int)pi.getY()-80, null);
											changeCount++;
											if(changeCount % 20 == 0) {
												Game.crysConfCount++;
												changeCount = 0;
											}
											if(Game.crysConfCount == 2) {
												Game.crysConfCount = 0;
											}
										}
									}
								}
							}
			       		}
					}
	       		}
	       	}
	    	if(i == layers.size()-1 && Game.gameState == Game.STATE.Transition && Game.stair) {
	    		g.drawImage(transition.get(idleCount), Game.camX, Game.camY, null);
	       		
				changeCount++;
				if(changeCount % 4 == 0) {
					idleCount++;
				}
				
				if(idleCount == 15) {
					
					
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
					
					
					for(int j = 0; j < handler.objects.size(); j++) {
						//System.out.println(handler.objects.get(j).getID());
						if(handler.objects.get(j).id == ID.Povy) {
							povy = (Povy)handler.objects.get(j);
							//System.out.println("found povy");
							for(int p = 0; p < handler.objects.size(); p++) {
								if(handler.objects.get(p).getID() == ID.NonEnemy) {
									if(povy.getBounds().intersects(handler.objects.get(p).getBounds())) {
										//System.out.println("intersects");
										//System.out.println((handler.objects.get(p).getID()));
										if(handler.objects.get(p) instanceof Stair) {
											//System.out.println("we mad eit");
											Game.gameState = Game.STATE.Game;
											Stair temp = (Stair)handler.objects.get(p);
											temp.activate();
											povy.stairCaseDescent();
											break;
										}
									}
								}
							}
						}
					}
					Game.battleReturn = true;
					
					
				}
				return;
	    		
	    	}
	       	if(i == layers.size()-1 && Game.gameState == Game.STATE.Transition && !Game.stair) {
	       		g.drawImage(transition.get(idleCount), Game.camX, Game.camY, null);
	       		if(Game.levTwo) {
	       			g.drawImage(grogoShip, shipX, shipY, null);
	       		}
	       		//System.out.println("girrlllll");
				changeCount++;
				if(changeCount % 4 == 0) {
					idleCount++;
				}
				
				if(idleCount == 15) {
					if(Game.levTwo) {
						changeCount = 0;
						tempX = Game.camX;
						tempY = Game.camY;
						handler.clear();
						Game.gameState = Game.STATE.levelTwoTran;
						Game.levTwo = false;
						AudioPlayer.getSound("explosion").play(1, (float).8);
						return;
					}
					changeCount = 0;
					Game.battleReturn = true;
					Game.camX = 0;
					Game.camY = 0;
					//System.out.println("HOWWW");
					Game.gameState = Game.STATE.Battle;
				}
			}
	       	if(i == layers.size()-1 && Game.gameState == Game.STATE.Dead) {
	       		g.drawImage(transition.get(idleCount), Game.camX, Game.camY, null);
	       		
				changeCount++;
				if(changeCount % 4 == 0) {
					idleCount++;
				}
				
				if(idleCount == 15) {
					AudioPlayer.getMusic("gameOver").loop();
					Game.gameState = Game.STATE.GameOver;
				}
			}
	       	if(Game.gameState == Game.STATE.PostBattle) {
	       		g.drawImage(transition.get(15), Game.camX, Game.camY, null);
	       		if(Battle.expToBeAdded != 0) {
	       			if(ExperienceBar.exp == ExperienceBar.expToUpgrade) {
	       				AudioPlayer.getSound("levelUp").play(1, (float).1);
	       				ExperienceBar.exp = 0;
	       				ExperienceBar.expToUpgrade *= 2;
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
						if(Game.lastBattle) {
							Game.lastBattle = false;
							Game.gameState = Game.STATE.FinalCutscene;
						}
					}
					else {
						if(AudioPlayer.getMusic("dungeon").playing() == false) {
							AudioPlayer.getMusic("dungeon").loop(1, (float).1);
						}
					}
					Game.battleReturn = false;
					if(Game.stair) Game.stair = false;
					//if(Game.stairBuffer) Game.stairBuffer = false;
				}
	       	}
		}
		
		
	    
	}
	
	/**
	 * allows the camera to follow the player
	 * @param g
	 * @param x
	 * @param y
	 */
	private void followPlayer(Graphics g, float x, float y) {
		int offsetMaxX = 6400; //world size(5120) - view size
		int offsetMaxY = 4800; // world size(3840) - view size
		int offsetMinX = 0;
		int offsetMinY = 0;
		Game.camX = (int)x - 1280 / 2; //viewport / 2 (so the player is in the center)
		Game.camY = (int)y - 960 / 2;//viewport /2
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
