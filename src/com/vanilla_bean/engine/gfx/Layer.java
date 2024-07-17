package com.vanilla_bean.engine.gfx;

public class Layer {
	
	private int[] pixels;

	public Layer() {
		pixels = new int[102400];
		for(int p : pixels) {
			p = 0x00000000;
		}
	}
	public int[] getPixels() {
		return pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}
	
	public void clearLayer() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0x00000000;
		}
	}
}