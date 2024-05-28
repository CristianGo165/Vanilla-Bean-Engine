package com.vanilla_bean.engine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.awt.AlphaComposite;

import com.vanilla_bean.engine.gfx.Layer;

public class Compositor {
	private ArrayList<Layer> layers;
	
	//NOTE: Renders Front to back
	//(Away From Camera) Front A ----> B Back (Towards Camera)
	
	private int alphaA;
	private int colorA;
	private int alphaB;
	private int colorB;
	private int alphaFinal;
	private int colorFinal;
	private GameContainer gc;
	private AlphaComposite ac;
	
	private Layer compositeLayer = new Layer();

	
	public Compositor(ArrayList<Layer> layers, GameContainer gc) {
		this.layers = layers;
		this.gc = gc;
		compositeLayer.setPixels(((DataBufferInt) this.gc.getWindow().getImage().getRaster().getDataBuffer()).getData());
		
		
	}
	
	public int getAlphaValue(int value) {
		return (value >> 24) & 0xff;
	}
	
	public Layer compositeAllLayers() {
		for(Layer layers : this.layers) {
			
		}
		
		
		return null;
	}
	
	public ArrayList<Layer> getLayers() {
		return layers;
	}

	public void setLayers(ArrayList<Layer> layers) {
		this.layers = layers;
	}
	
	
	
}
