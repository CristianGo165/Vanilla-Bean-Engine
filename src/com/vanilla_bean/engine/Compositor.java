package com.vanilla_bean.engine;

import java.util.ArrayList;

import com.vanilla_bean.engine.gfx.Layer;

public class Compositor {	
	//NOTE: Renders Front to back
	//(Away From Camera) Front B ----> A Back (Towards Camera)
	//A = Current Layer + 1
	//B = Current Layer

	
	public Compositor() {
		
	}
	
	public int getAlphaValue(int value) {
		return (value >> 24) & 0xff;
	}
	
	public Layer blendAllLayers(ArrayList<Layer> layers) {
		
		Layer layerOut = layers.get(0);
		
		//i is the layer index
		//j is the pixel index
		
		for(int i = 0 ; i < layers.size() - 1 ; i++) {
			for(int j = 0 ; j < layers.get(i).getPixels().length ; j++) {
				
				int alphaOver = getAlphaValue(layers.get(i+1).getPixels()[j]);

				
				
				int redResultant = (((layers.get(i+1).getPixels()[j] >> 16) & 0xff) * alphaOver + ((layerOut.getPixels()[j] >> 16) & 0xff) * (255 - alphaOver))/255;
				int greenResultant = (((layers.get(i+1).getPixels()[j] >> 8) & 0xff) * alphaOver + ((layerOut.getPixels()[j] >> 8) & 0xff) * (255 - alphaOver))/255;
				int blueResultant = (((layers.get(i+1).getPixels()[j]) & 0xff) * alphaOver + ((layerOut.getPixels()[j]) & 0xff) * (255 - alphaOver))/255;
				
				layerOut.getPixels()[j] = (255 << 24) | (redResultant << 16) | (greenResultant << 8) | blueResultant;
			}
		}
		
		return layerOut;
	}
}
