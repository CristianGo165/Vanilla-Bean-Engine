package com.vanilla_bean.engine.gfx;

public class Layer {
	
	private int[] pixels;

	public Layer() {
		pixels = new int[102400];
		clearLayer();
	}
	public int[] getPixels() {
		return pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}
	
	public void clearLayer() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 1;
		}
	}
}