package com.vanilla_bean.engine;

import java.awt.image.DataBufferInt;

import java.util.ArrayList;

import com.vanilla_bean.engine.gfx.Font;
import com.vanilla_bean.engine.gfx.Image;
import com.vanilla_bean.engine.gfx.ImageTile;
import com.vanilla_bean.engine.gfx.Layer;

public class Renderer {
	private int pixelWidth, pixelHeight;
	private Layer compositeLayer = new Layer();
	private ArrayList<Layer> layers = new ArrayList<Layer>();
	private Font font = Font.STANDARD;
	private Compositor compositor;

	public Renderer(GameContainer gc) {
		pixelWidth = gc.getWidth();
		pixelHeight = gc.getHeight();

		// Edits Pixel Data of Image Being Rendered
		compositeLayer.setPixels(((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData());
		System.out.println(compositeLayer.getPixels().length);
		layers.add(compositeLayer);
		for(int i = 1 ; i < layers.size() ; i++) {
			layers.set(i, new Layer());
		}
		
		compositor = new Compositor(layers, gc);
	}

	public void clearAllLayers() {
		for(Layer layer : layers) {
			layer.clearLayer();
		}
	}
	
	public void setPixel(int x, int y, int value, int layer) {
		//System.out.println(Integer.toHexString(value));
		//System.out.println(pixel[x + y * pixelWidth]);
		
		
		
		int alphaA = ((value >> 24) & 0xff);
		//int alphaB = ((layers[0].getPixels()[x + y * pixelWidth] >> 24) & 0xff);
		

		if((x < 0 || x>= pixelWidth || y < 0 || y >= pixelHeight )) {
			return;
		}
		if(alphaA == 0) {
			//layers[layer].getPixels()[x + y * pixelWidth] = 0;
			return;
		}else {
			layers.get(layer).getPixels()[x + y * pixelWidth] = value;
		}
		/*
		Layer composite = null;
		
		if(layers.length > 1) {
			composite = compositeLayers();
		}
		
		composite.getPixels()[x + y * pixelWidth] = value;
		*/
	}
	
	public Layer compositeLayers() {
		if(layers.size() <= 1) {
			return compositeLayer;
		}
		
		for(int layerNumber = 0 ; layerNumber > layers.size()-1 ; layerNumber++) {
			for(int x = 0 ; x < pixelWidth ; x++) {
				for(int y = 0 ; y < pixelHeight ; y++) {
					/*
					int alphaLayerA = ((layers[layerNumber].getPixels()[x + y * pixelWidth] >> 24) & 0xff);
					int colorA = (layers[layerNumber].getPixels()[x + y * pixelWidth]);
					int alphaLayerB = ((layers[layerNumber+1].getPixels()[x + y * pixelWidth] >> 24) & 0xff);
					int colorB = (layers[layerNumber + 1].getPixels()[x + y * pixelWidth]);
					int alphaOverall = alphaLayerA + alphaLayerB * (1 - alphaLayerA);
					int colorOverall = (colorA*alphaLayerA + colorB*alphaLayerB*(1-alphaLayerA));
					compositeLayer.getPixels()[x + y * pixelWidth] = colorOverall;*/
				}
			}
		}
		
		return compositeLayer;
	}
	
	public void addLayer(int position) {
		//NOTE position is before or after "compositeLayer"
		layers.add(new Layer());
	}
	
	public void drawText(String text, int offsetX, int offsetY, int color, int layer) {
		//Only for fonts with only upper case
		
		//Image fontImage = font.getFontImage();
		
		text = text.toUpperCase();
		
		int offset = 0;
		
		for(int i = 0; i< text.length(); i++){
			int unicode = text.codePointAt(i) - 32;
			
			for(int y = 0; y < font.getFontImage().getHeight(); y++) {
				for(int x = 0; x < font.getWidths()[unicode]; x++) {
					if(font.getFontImage().getPixel()[(x + font.getOffsets()[unicode])+y*font.getFontImage().getWidth()] == 0xffffffff) {
						setPixel(x+ offsetX + offset, y + offsetY, color, layer);
					}
				}
			}
			offset += font.getWidths()[unicode];
		}
	}
	
	public int getTextWidth(String text) {
		text = text.toUpperCase();
		
		int offsetTotal = 0;
		
		for(int i = 0 ; i < text.length() ; i++) {
			int unicode = text.codePointAt(i) - 32;
			
			offsetTotal += font.getWidths()[unicode];
		}
		return offsetTotal/2;
	}

	public void drawImage(Image image, int offsetX, int offsetY, int layer) {
		
		//Don't Render
		if(offsetX < -image.getWidth()) return;
		if(offsetY < -image.getHeight()) return;
		
		if(offsetX >= pixelWidth) return;
		if(offsetY >= pixelHeight) return;
		
		int newX = 0;
		int newY = 0;
		
		//newWith and newHeight are for the clipping code
		int newWidth = image.getWidth();
		int newHeight = image.getHeight();
		
		
		
		//Clipping
		if(newX+ offsetX < 0) {
			newX -= offsetX;
		}
		
		if(newY+ offsetY < 0) {
			newY -= offsetY;
		}
		
		if(newWidth + offsetX > pixelWidth) {
			newWidth -= (newWidth+offsetX - pixelWidth);
		}
		
		if(newHeight + offsetY > pixelHeight) {
			newHeight -= (newHeight+offsetY - pixelHeight);
		}
		//Clipping
		
		for(int y = newY ; y < newHeight; y++) {
			for(int x = newX; x < newWidth; x++) {
				setPixel(x + offsetX, y + offsetY, image.getPixel()[x + y * image.getWidth()], layer);
			}
		}
	}
	
	public void drawImageTile(ImageTile image, int offsetX, int offsetY, int tileX, int tileY, int layer) {

		//Don't Render
		if(offsetX < -image.getTileWidth()) return;
		if(offsetY < -image.getTileHeight()) return;
		
		if(offsetX >= pixelWidth) return;
		if(offsetY >= pixelHeight) return;
		
		int newX = 0;
		int newY = 0;
		
		//newWith and newHeight are for the clipping code
		int newWidth = image.getTileWidth();
		int newHeight = image.getTileHeight();
		
		
		
		//Clipping
		if(newX+ offsetX < 0) {
			newX -= offsetX;
		}
		
		if(newY+ offsetY < 0) {
			newY -= offsetY;
		}
		
		if(newWidth + offsetX > pixelWidth) {
			newWidth -= (newWidth+offsetX - pixelWidth);
		}
		
		if(newHeight + offsetY > pixelHeight) {
			newHeight -= (newHeight+offsetY - pixelHeight);
		}
		//Clipping
		
		for(int y = newY ; y < newHeight; y++) {
			for(int x = newX; x < newWidth; x++) {
				setPixel(x + offsetX, y + offsetY, image.getPixel()[(x + tileX * image.getTileWidth()) + (y + tileY * image.getTileHeight()) * image.getWidth()], layer);
			}
		}
	}
}