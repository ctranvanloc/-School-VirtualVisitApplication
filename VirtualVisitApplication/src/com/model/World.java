package com.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class World {
	private TiledMap map;
	private Player player;
	private MapLayer collision, event;
	private MapObject currentObject;
	private boolean isColidePlayer;
	
	public TiledMap getMap() {
		return map;
	}
	public Player getPlayer() {
		return player;
	}
	public MapLayer getColisionLayer(){
		return this.collision;
	}
	public MapLayer getEventLayer(){
		return this.event;
	}
	public MapObject getCurrentObject(){
		return this.currentObject;
	}
	public void setCurrentObject(MapObject mapObj){
		this.currentObject = mapObj;
	}
	public World(){
		this.player = new Player(new Vector2(496,200));
		Texture.setEnforcePotImages(false);
		this.map = new TmxMapLoader().load("data/map/map.tmx");
		collision = new MapLayer();
		event = new MapLayer();

		//Recherche et enregistrement du calque de collision et event
		//on le supprime ensuite pour qu'il soit invisible
		for(int i=0;i<map.getLayers().getCount();i++){
			if(map.getLayers().get(i).getName().equals("colision")){
				this.collision=map.getLayers().get(i);
				this.map.getLayers().remove(i);
			}
			if(map.getLayers().get(i).getName().equals("events")){
				this.event=map.getLayers().get(i);
				this.map.getLayers().remove(i);
			}
		}
	}
	
	public void setEventTouch(Player player) {
		this.isColidePlayer = false;
		MapObject clearEvent = new MapObject();
		this.setCurrentObject(clearEvent);
		
		for(MapObject objectEvent: this.getEventLayer().getObjects()) {
			RectangleMapObject rectangleObject = (RectangleMapObject)(objectEvent);
			Rectangle eventRectangle = rectangleObject.getRectangle();
			if(player.getHitBox().overlaps(eventRectangle))
			{
				this.isColidePlayer = true;
				this.setCurrentObject(objectEvent);
			}
		}
	}
	
	public boolean getEventTouch(){
		return this.isColidePlayer;
	}
}
