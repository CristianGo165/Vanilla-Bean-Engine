package com.vanilla_bean.engine;

public abstract class AbstractGame {
	public abstract void update(GameContainer gc, float deltaTime);

	public abstract void render(GameContainer gc, Renderer renderer);
	
	public abstract void start(GameContainer gc, float deltaTime);
}
