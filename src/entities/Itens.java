package entities;

import java.util.ArrayList;
import java.util.Arrays;

import screens.GameScreen;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class Itens {

	@SuppressWarnings({ "rawtypes","unchecked" })
	public static ArrayList<String> propriedades = new ArrayList(Arrays.asList("hp", "sp", "atk", "def", "xp"));  
	public static String propriedadeColision = "blocked";


	public boolean verificaItem(int x, int y){
		int countLayers = GameScreen.map.getMap().getLayers().getCount();
		for (int i = 0; i < countLayers; i++) {
			try {		
				TiledMapTileLayer mapLayer = (TiledMapTileLayer) GameScreen.map.getMap().getLayers().get(i);
				if (mapLayer!=null){
					Cell cell = ((TiledMapTileLayer) mapLayer).getCell((int) (x / ((TiledMapTileLayer) mapLayer).getTileWidth()), (int) (y / ((TiledMapTileLayer) mapLayer).getTileHeight()));
					if (cell!=null){
						for (int j = 0; j < propriedades.size(); j++) {
							Object property = cell.getTile().getProperties().get(propriedades.get(j));
							if(property!=null){
								if (propriedades.get(j).equals("hp")){
									int lifeAdd = Integer.parseInt(property+"");
									if (lifeAdd > 0){
										Player.addLife(lifeAdd);
										GameScreen.playerMovimentos.gain();
									} else {
										Player.removeLife(lifeAdd);
									}
									HUDMovimentos.animatedMessage("Voce recebeu "+lifeAdd+" de HP.", 5);
									cell.setTile(null);
									return true;
								} else if (propriedades.get(j).equals("sp")){
									int spAdd = Integer.parseInt(property+"");
									Player.addSp(spAdd);
									HUDMovimentos.animatedMessage("Voce recebeu "+spAdd+" de SP.", 5);
									cell.setTile(null);
									GameScreen.playerMovimentos.gain();
									return true;
								} else if (propriedades.get(j).equals("atk")){
									int atkAdd = Integer.parseInt(property+"");
									Player.addAtk(atkAdd);
									HUDMovimentos.animatedMessage("Voce recebeu "+atkAdd+" de ATK.", 5);
									cell.setTile(null);
									GameScreen.playerMovimentos.gain();
									return true;
								} else if (propriedades.get(j).equals("def")){
									int defAdd = Integer.parseInt(property+"");
									Player.addDef(defAdd);
									HUDMovimentos.animatedMessage("Voce recebeu "+defAdd+" de DEF.", 5);
									cell.setTile(null);
									GameScreen.playerMovimentos.gain();
									return true;
								} else if (propriedades.get(j).equals("xp")){
									int xpAdd = Integer.parseInt(property+"");
									Player.addXp(xpAdd);
									HUDMovimentos.animatedMessage("Voce recebeu "+xpAdd+" de XP.", 5);
									cell.setTile(null);
									GameScreen.playerMovimentos.gain();
									return true;
								}
							}			
						}
					}
				}
			} catch (Exception e) {}			
		}
		HUDMovimentos.animatedMessage("Voce nao encontrou nenhum objeto.", 5);
		return false;
	}

	public boolean verificaColisionItem(int x, int y){
		int countLayers = GameScreen.map.getMap().getLayers().getCount();
		for (int i = 0; i < countLayers; i++) {
			try {		
				TiledMapTileLayer mapLayer = (TiledMapTileLayer) GameScreen.map.getMap().getLayers().get(i);
				if (mapLayer!=null){
					Cell cell = ((TiledMapTileLayer) mapLayer).getCell((int) (x / ((TiledMapTileLayer) mapLayer).getTileWidth()), (int) (y / ((TiledMapTileLayer) mapLayer).getTileHeight()));
					if (cell!=null){
						for (int j = 0; j < propriedades.size(); j++) {
							Object property = cell.getTile().getProperties().get(propriedades.get(j));
							if(property!=null){
								return true;
							}			
						}
					}
				}
			} catch (Exception e) {}			
		}
		return false;
	}

	public boolean verificaBlockedItem(int x, int y, String acao){

		int countLayers = GameScreen.map.getMap().getLayers().getCount();
		for (int i = 0; i < countLayers; i++) {
			try {		
				TiledMapTileLayer mapLayer = (TiledMapTileLayer) GameScreen.map.getMap().getLayers().get(i);
				if (mapLayer!=null){
					Cell cell = ((TiledMapTileLayer) mapLayer).getCell((int) (x / ((TiledMapTileLayer) mapLayer).getTileWidth()), (int) (y / ((TiledMapTileLayer) mapLayer).getTileHeight()));
					if (cell!=null){
						Object property = cell.getTile().getProperties().get(propriedadeColision);
						if(property!=null){
							if ((property+"").equals("true")){
								if (acao.equals("ataque")){
									cell.setTile(null);
									HUDMovimentos.animatedMessage("Voce quebrou um objeto.", 5);
								}
								return true;
							}
						} 							
					}
				}
			} catch (Exception e) {}			
		}
		return false;
	}

	public int getQuantItem(){
		int quantItem = 0;
		int countLayers = GameScreen.map.getMap().getLayers().getCount();
		for (int i = 0; i < countLayers; i++) {
			try {	
				TiledMapTileLayer mapLayer = (TiledMapTileLayer) GameScreen.map.getMap().getLayers().get(i);
				if (mapLayer!=null){
					for (int j = 0; j < 24; j++) {
						for (int j2 = 0; j2 < 20; j2++) {
							Cell cell = ((TiledMapTileLayer) mapLayer).getCell(j, j2);
							if (cell!=null){
								for (int k = 0; k < propriedades.size(); k++) {	
									try {													
										Object property = cell.getTile().getProperties().get(propriedades.get(k));
										if(property!=null){
											if (!propriedades.get(k).equals("hp")){
												quantItem++;
											}
										}	
									} catch (Exception e) {}
								}
							}
						}
					}
				}
			} catch (Exception e) {} 
		}

		return quantItem;
	}


}
