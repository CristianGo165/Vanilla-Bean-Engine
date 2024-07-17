package com.vanilla_bean.engine;

import java.awt.image.DataBufferInt;

import java.util.ArrayList;

import com.vanilla_bean.engine.gfx.Font;
import com.vanilla_bean.engine.gfx.Image;
import com.vanilla_bean.engine.gfx.ImageTile;
import com.vanilla_bean.engine.gfx.Layer;

public class Renderer {
	private int pixelWidth, pixelHeight;
	private Compositor compositor;
	private Layer outputLayer = new Layer();
	private ArrayList<Layer> layers = new ArrayList<Layer>();
	private Font font = Font.STANDARD;

	public Renderer(GameContainer gc) {
		pixelWidth = gc.getWidth();
		pixelHeight = gc.getHeight();
		
		compositor = new Compositor();
		
		outputLayer = new Layer();
		
		layers = new ArrayList<Layer>();
		
		//Background
		layers.add(new Layer());
		for (int i = 0; i < layers.get(0).getPixels().length; i++) {
			layers.get(0).getPixels()[i] = 0xff000000;
		}
		
		//Tiles 1
		layers.add(new Layer());
		
		//Text
		layers.add(new Layer());
		
		//Cursor
		layers.add(new Layer());
		

		// Edits Pixel Data of Image Being Rendered
		outputLayer.setPixels(((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData());
	}
	
	public void compositeLayers() {
		Layer composite = compositor.blendAllLayers(this.layers);
		for(int i = 0 ; i < outputLayer.getPixels().length ; i++) {
			outputLayer.getPixels()[i] = composite.getPixels()[i];
		}
	}
	
	public void clearLayerOut() {
		for (int i = 0; i < outputLayer.getPixels().length; i++) {
			outputLayer.getPixels()[i] = 0xffff0000;
		}
	}

	public void clearAllLayers() {
		//clearLayerOut();
		for(Layer layer : layers) {
			layer.clearLayer();
		}
	}
	
	public ArrayList<Layer> getLayers(){
		return this.layers;
	}
	
	public void setPixel(int x, int y, int value, int layer) {
		
		//int alpha = ((value >> 24) & 0xff);		

		if((x < 0 || x>= pixelWidth || y < 0 || y >= pixelHeight || ((value >> 24) & 0xff) == 0)) {
			return;
		}
		if(((value >> 24) & 0xff) == 0) {
			//layers.get(layer).getPixels()[x + y * pixelWidth] = 0x00000000;
			return;
		}
		layers.get(layer).getPixels()[x + y * pixelWidth] = value;
	}
	
	public void addLayer() {
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