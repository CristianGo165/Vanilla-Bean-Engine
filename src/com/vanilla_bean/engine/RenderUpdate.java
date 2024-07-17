package com.vanilla_bean.engine;

public class RenderUpdate implements Runnable{
	
	private double updateLimit = 1.0 / 60.0;
	
	private boolean running;
	private double firstTime = 0;
	private double lastTime = System.nanoTime() / 1000000000.0;
	private double passedTime = 0;
	private double unprocessedTime = 0;
	private boolean shouldRender = false;
	
	RenderUpdate(Renderer renderer){
		running = true;
	}
	
	public boolean isRunning() {
		return running;
	}



	public void setRunning(boolean running) {
		this.running = running;
	}

	
	public void run(){
		
		double frameTime = 0;
		int frames = 0;
		int fps;
		
		shouldRender = false;
		firstTime = System.nanoTime() / 1000000000.0;
		passedTime = firstTime - lastTime;
		lastTime = firstTime;
		
		while(unprocessedTime >= updateLimit) {
			unprocessedTime -= updateLimit;
			shouldRender = true;
			if(frameTime >= 0) {
				frameTime = 0;
				fps = frames;
				frames = 0;
			}
		}
		if(shouldRender) {
			
		}
	}
	
}
