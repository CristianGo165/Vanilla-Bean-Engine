package com.vanilla_bean.engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Window {

	public JFrame getFrame() {
		return frame;
	}

	private JFrame frame;
	private BufferedImage image;
	private Canvas canvas;
	
	
	public BufferedImage getImage() {
		return image;
	}

	public Canvas getCanvas() {
		return canvas;
	}
	
	public void setWindowResizable(boolean resizable) {
		frame.setResizable(resizable);
	}

	private Graphics2D graphics;
	private BufferStrategy bufferStrategy;

	public Window(GameContainer gc) {
		// Image Stored in RAM
		image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_ARGB);
		canvas = new Canvas();
		Dimension dimension = new Dimension((int) (gc.getWidth() * gc.getScale()),
				(int) (gc.getHeight() * gc.getScale()));
		canvas.setPreferredSize(dimension);
		canvas.setMaximumSize(dimension);
		canvas.setMinimumSize(dimension);

		frame = new JFrame(gc.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
		graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
		
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		frame.getContentPane().setCursor(blankCursor);
	}

	public void update() {
		graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphics.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		bufferStrategy.show();
	}
}