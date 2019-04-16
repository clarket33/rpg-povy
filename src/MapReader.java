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
	
	private ArrayList<String> layers;
	private Handler handler;
	private int torchCounter = 0;
	private int trapCounter = 0, trapCounterA = 0, trapCounterB = 0;
	private ArrayList<BufferedImage> transition;
	private BufferedImage grogoShip;
	private int changeCount = 0;
	private int idleCount = 0;
	private int animationHold = 0, animationHoldA = 0, animationHoldB = 0;
	private boolean doLeft = true;
	private Map<Integer, ArrayList<Integer>> unusedTiles;
	
	/**
	 * loads in the xml files and stores information about the map and tiles in various structures
	 * @param handler
	 */
	public MapReader(Handler handler) {
		this.handler = handler;
		SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
		layers = new ArrayList<String>();
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
			
		    try {
		        grogoShip = ImageIO.read(new File("res/grogoShip.png"));
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
		
		
		
		
		for(int i = 1; i <= 24; i++) {
			for(int j = 1; j <= 20; j++) {
				Game.dungeonTiles.add(toBufferedImage(ss.grabImage(i, j, 16, 16, "dungeonTiles").getScaledInstance(48, 48, Image.SCALE_AREA_AVERAGING)));
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
			//System.out.println(nList1.getLength());
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
		 	       if(stringCur.contains("365")) {
		 	    	   handler.addObject(new Pillar(x, y, ID.NonEnemy, 366));
		 	       }
		 	       if(stringCur.contains("366")) {
		 	    	   handler.addObject(new Pillar(x, y, ID.NonEnemy, 367));
		 	       }
		 	       if(stringCur.contains("367")) {
		 	    	   handler.addObject(new Pillar(x, y, ID.NonEnemy, 368));
		 	       }
		 	       if(stringCur.contains("380")){
		 	    	  handler.addObject(new Lever(x, y, ID.NonEnemy, handler));
		 	       }
	 	       		if(Game.collisionTiles.get(new Integer(0)).containsKey(stringCur)) {
		 	       		curID = Integer.parseInt(curr[j].replace("\n", ""));
		 	       		Game.collisionTiles.get(new Integer(0)).get(stringCur).add(x);
		 	       		Game.collisionTiles.get(new Integer(0)).get(stringCur).add(y);
	 	       		}
	 	       		x += 48;
	 	       		if(x == 7680) {
	 	       			x = 0;
	 	       			y += 48;
	 	       		}
	 	       		prevID = stringCur;
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
		               /**
		               else if(eElement.getAttribute("tileid").contains("281") ||  eElement.getAttribute("tileid").contains("283") || 
		            		   eElement.getAttribute("tileid").contains("282")) {
		            	   cur = Integer.parseInt(eElement.getAttribute("tileid"));
		            	   cur+=1;
		            	   cur1 = String.valueOf(cur);
		            	  
		            	   Game.animationDungeon.get("floorTrap").add(cur1);
		            	   
		               }
		               **/
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
			//layer where Povy will appear above some objects and below others
			if(i == 2) {

				for(int m = 0; m< handler.objects.size();m++) {
	    			if(handler.objects.get(m).id == ID.NonEnemy) {
	    				handler.objects.get(m).render(g);
	    			}
				}
				cur = layers.get(i).split(",");
				int thisX = 0;
		     	int thisY = 0;
				for(int k = 0; k < cur.length; k++) {
					if(!Game.shouldRender(thisX, thisY)) {
						thisX += 48;
			       		if(thisX == 7680) {
			       			thisX = 0;
			       			thisY += 48;
			       		}
						continue;
					}
		       		curID = Integer.parseInt(cur[k].replace("\n", ""));
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
				
				
				
				
			}
		
	       	cur = layers.get(i).split(",");
	        x = 0;
	     	y = 0;
	       	for(int j = 0; j < cur.length; j++) {
	       		if(!Game.shouldRender(x, y)) {
		       				
	       			x += 48;
		       		if(x == 7680) {
		       			x = 0;
		       			y += 48;
		       		}
	       			continue;
	       		}
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
	       			else if(cur[j].contains("282")) {
	       				if(Game.gameState != Game.STATE.Paused) {
	       					if(animationHold == 0) {
	       						trapCounter++;
	       					}
		       				if(trapCounter == 10000 || animationHold != 0) {
		       					if(Game.animationDungeonCounter.get("floorTrap") == 0 || Game.animationDungeonCounter.get("floorTrap") == 2) {
			       					Game.animationDungeonCounter.put("floorTrap", Game.animationDungeonCounter.get("floorTrap") + 1);
			       					trapCounter = 0;
		       					}
		       					if(Game.animationDungeonCounter.get("floorTrap") == 1) {
		       						g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrap").get(Game.animationDungeonCounter.get("floorTrap")))), x, y, null);
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
		       						g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrap").get(Game.animationDungeonCounter.get("floorTrap")))), x, y, null);
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
		       					g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrap").get(Game.animationDungeonCounter.get("floorTrap")))), x, y, null);
		       				}
	       				
	       				}
	       				else {
	       					g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrap").get(Game.animationDungeonCounter.get("floorTrap")))), x, y, null);
	       				}
	       			}
	       			
	       			
	       			else if(cur[j].contains("283")) {
	       				if(doLeft) {
		       				if(Game.gameState != Game.STATE.Paused) {
		       					if(animationHoldA == 0) {
		       						trapCounterA++;
		       					}
			       				if(trapCounterA == 1500 || animationHoldA != 0) {
			       					if(Game.animationDungeonCounter.get("floorTrapA") == 0 || Game.animationDungeonCounter.get("floorTrapA") == 2) {
				       					Game.animationDungeonCounter.put("floorTrapA", Game.animationDungeonCounter.get("floorTrapA") + 1);
				       					trapCounterA = 0;
			       					}
			       					if(Game.animationDungeonCounter.get("floorTrapA") == 1) {
			       						g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapA").get(Game.animationDungeonCounter.get("floorTrapA")))), x, y, null);
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
			       						g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapA").get(Game.animationDungeonCounter.get("floorTrapA")))), x, y, null);
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
	       			
	       			
	       			
	       			
	       			
	       			else if(cur[j].contains("284")) {
	       				if(!doLeft) {
		       				if(Game.gameState != Game.STATE.Paused) {
		       					if(animationHoldB == 0) {
		       						trapCounterB++;
		       					}
			       				if(trapCounterB == 1500 || animationHoldB != 0) {
			       					if(Game.animationDungeonCounter.get("floorTrapB") == 0 || Game.animationDungeonCounter.get("floorTrapB") == 2) {
				       					Game.animationDungeonCounter.put("floorTrapB", Game.animationDungeonCounter.get("floorTrapB") + 1);
				       					trapCounterB = 0;
			       					}
			       					if(Game.animationDungeonCounter.get("floorTrapB") == 1) {
			       						g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapB").get(Game.animationDungeonCounter.get("floorTrapB")))), x, y, null);
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
			       						g.drawImage(Game.dungeonTiles.get(Integer.parseInt(Game.animationDungeon.get("floorTrapB").get(Game.animationDungeonCounter.get("floorTrapB")))), x, y, null);
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
	       				/**
	       				if(Game.collisionTiles.get(new Integer(0)).containsKey(stringCur)) {
	       					g.setColor(Color.green);
	       					g.drawRect(x, y, 32, 32);
	       				}
	       				**/
	       				if(curID != 335 && curID != 215 && curID != 381 && curID != 366 && curID != 367 && curID != 368 && i != 2) {
	       					
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
