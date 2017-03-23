package engine;

import java.awt.Graphics;

/**
 * The sprite class representing a sprite
 * 
 * @author bobbydilley
 *
 */
public class Sprite {

	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;
	private boolean finished;

	private int screenWidth;
	private int screenHeight;

	/**
	 * Constructor for giving width and height
	 * 
	 * @param x
	 *            Top left x position of the sprite
	 * @param y
	 *            Top left y position of the sprite
	 * @param width
	 *            Width of the sprite
	 * @param height
	 *            Height of the sprite
	 */
	public Sprite(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Constructor for just x and y positions
	 * 
	 * @param x
	 *            Top left x position of the sprite
	 * @param y
	 *            Top left y position of the sprite
	 */
	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the size of the screen
	 * 
	 * @param width
	 *            The width of the screen
	 * @param height
	 *            The height of the screen
	 */
	public void setScreenSize(int width, int height) {
		this.screenWidth = width;
		this.screenHeight = height;
	}

	/**
	 * Gets the width of the screen
	 * 
	 * @return The width of the screen
	 */
	public int getScreenWidth() {
		return this.screenWidth;
	}

	/**
	 * Gets the height of the screen
	 * 
	 * @return The height of the screen
	 */
	public int getScreenHeight() {
		return this.screenHeight;
	}

	/**
	 * Gets the x position of the top left of the sprite
	 * 
	 * @return The top left x position
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the top left x position of the sprite
	 * 
	 * @param x
	 *            The new top left x position
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y position of the top left of the sprite
	 * 
	 * @return The top left y position
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the top left y position of the sprite
	 * 
	 * @param y
	 *            The new top left y position
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets the width of the sprite
	 * 
	 * @return The width of the sprite
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width of the sprite
	 * 
	 * @param width
	 *            The new width of the sprite
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the height of the sprite
	 * 
	 * @return The height of the sprite
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height of the sprite
	 * 
	 * @param height
	 *            The new height of the sprite
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * To be overridden
	 */
	public void update() {

	}

	/**
	 * To be overridden
	 */
	public void draw(Graphics context) {

	}

	/**
	 * Gets the finished status of the sprite's use
	 * 
	 * @return Whether the sprite is no longer used
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * Retires the sprite
	 */
	public void finish() {
		this.finished = false;
	}

}
