package com.vanilla_bean.engine;

import java.io.IOException;

import javax.imageio.ImageIO;

//import java.awt.event.KeyEvent;
//import java.awt.event.MouseEvent;

public class GameContainer implements Runnable {

	private Thread thread;
	private boolean running = false;
	private final double UPDATE_LIMIT = 1.0 /10.0;

	// Window Variables
	private int width = 320, height = 320;
	private float scale = 2;
	//Current Vanilla Bean Version (NOTE: SHOULD BE UPDATED)
	private String title = "Vanilla Bean Engine V0.1";
	//Current Vanilla Bean Version (NOTE: SHOULD BE UPDATED)
	// Window Variables

	// Window Setup
	private Window window;
	// Window Setup
	
	//Player name field setup
	private TextField textField;
	//Player name field setup;

	// Renderer Setup
	private Renderer renderer;
	// Renderer Setup

	// Input Setup
	private Input input;
	// Input Setup

	// Abstract Game Setup
	private AbstractGame aGame;
	// Abstract Game Setup
	
	//Setup Icon
	private java.awt.Image logo;
	//Setup Icon

	public Window getWindow() {
		return window;
	}
	
	public TextField getTextField() {
		return this.textField;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		getWindow().getFrame().setTitle(this.title);
	}
	
	public void setWindowIcon(String path) {
		try {
			logo = ImageIO.read(GameContainer.class.getResourceAsStream(path));
			window.getFrame().setIconImage(logo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public GameContainer(AbstractGame game) {
		this.aGame = game;
	}

	public Input getInput() {
		return input;
	}

	public void startProgram() {
		
		window = new Window(this);
		
		//Standard Logo
		setWindowIcon("/images/vanilla_bean/Vanilla Bean Logo Prototype.png");
		//Standard Logo

		renderer = new Renderer(this);
		
		textField = new TextField();

		input = new Input(this);

		thread = new Thread(this);
		
		thread.run();
	}

	public void stop() {

	}

	public void run() {

		running = true;

		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;
		boolean render = false;

		double frameTime = 0;
		int frames = 0;
		int fps = 0;
		
		System.out.println("Start Ran");
		aGame.start(this, (float) UPDATE_LIMIT);

		while (running) {

			render = false;

			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;

			unprocessedTime += passedTime;

			frameTime += passedTime;

			while (unprocessedTime >= UPDATE_LIMIT) {
				unprocessedTime -= UPDATE_LIMIT;
				render = true;

				aGame.update(this, (float) UPDATE_LIMIT);

				input.update();

				if (frameTime >= 1.0) {
					frameTime = 0;
					fps = frames;
					frames = 0;
				}
			}

			if (render) {

				renderer.clear();
				aGame.render(this, renderer);
				//renderer.drawText("FPS: " + fps, 0, 10, 0xff00ffff);
				window.update();
				frames++;

			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		dispose();
	}

	private void dispose() {

	}
}
