package engine;

import input.InputHandler;
import network.Client.Network;
import network.Server.Server;
import screens.Overlay;
import songmanager.SongFile;

/**
 * The game object is given around to all the different scenes
 * 
 * @author bobbydilley
 *
 */
public class GameObject {

	public final int PERFECT = 10;
	public final int EXCELLENT = 20;
	public final int GOOD = 30;
	public final int OKAY = 50;

	private int width;
	private int height;
	private Overlay overlay;
	private InputHandler inputHandler;
	private int p1Score;
	private int p2Score;
	private int p2Power;
	private int p2Combo;
	private String p2Text;
	private boolean isServer = false;
	private boolean isConnected = false;
	private boolean isReady = false;
	private String p1Name = "E2";
	private String p2Name;
	private String hostname = "localhost";
	private Network network;
	private Screen mode;
	private Server server;
	private String networkError = "";
	private SongFile[] songFiles;
	private int currentSelect;

	private SongFile songFile;

	private int[] scoreQuality;
	private double speed;

	private int aiLevel;
	private String aiLevelText;

	/**
	 * Constructor
	 * 
	 * @param width
	 *            The width of the screen
	 * @param height
	 *            The height of the screen
	 */
	public GameObject(int width, int height) {
		this.width = width;
		this.height = height;
		songFiles = songmanager.SongFileProcessor.readAllSongFiles();
	}

	/**
	 * Getter for the offset
	 * 
	 * @return The offset
	 */
	public int getOffset() {
		return 25;
	}

	/**
	 * Returns whether the instance is a server or client
	 * 
	 * @return Whether this instance is a server
	 */
	public boolean isServer() {
		return isServer;
	}

	/**
	 * Sets the server status of this instance
	 * 
	 * @param isServer
	 *            Whether this instance will be a server or not
	 */
	public void setServer(boolean isServer) {
		this.isServer = isServer;
	}

	/**
	 * Gets the input handler instance
	 * 
	 * @return The input handler
	 */
	public InputHandler getInputHandler() {
		return this.inputHandler;
	}

	/**
	 * Sets the input handler
	 * 
	 * @param inputHandler
	 *            The instance of input handler to be used
	 */
	public void setInputHandler(InputHandler inputHandler) {
		this.inputHandler = inputHandler;
	}

	/**
	 * Gets the width of the screen
	 * 
	 * @return Integer width of the screen
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width of the screen
	 * 
	 * @param width
	 *            The new integer width of the screen
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the width of the screen
	 * 
	 * @return Integer width of the screen
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height of the screen
	 * 
	 * @param height
	 *            The new integer height of the screen
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Sets the overlay
	 * 
	 * @param overlay
	 *            The overlay instance to be used
	 */
	public void setOverlay(Overlay overlay) {
		this.overlay = overlay;
	}

	/**
	 * Returns the current overlay
	 * 
	 * @return The overlay currently in use
	 */
	public Overlay getOverlay() {
		return this.overlay;
	}

	/**
	 * Gets the score of player 1, which is the default player
	 * 
	 * @return Integer score of player 1
	 */
	public int getP1Score() {
		return p1Score;
	}

	/**
	 * Sets the score value of player 1
	 * 
	 * @param score
	 *            The new score value of player 1
	 */
	public void setP1Score(int score) {
		this.p1Score = score;
	}

	/**
	 * Gets the score of player 2, which is the AI player or non-server player
	 * 
	 * @return Integer score of player 2
	 */
	public int getP2Score() {
		return p2Score;
	}

	/**
	 * Sets the score value of player 2
	 * 
	 * @param score
	 *            The new score value of player 2
	 */
	public void setP2Score(int p2Score) {
		this.p2Score = p2Score;
	}

	/**
	 * Gets the power value of player 2 (AI or client)
	 * 
	 * @return The power value of AI or client player
	 */
	public int getP2Power() {
		return p2Power;
	}

	/**
	 * Sets new value of player 2 power
	 * 
	 * @param p2Power
	 *            The new power value
	 */
	public void setP2Power(int p2Power) {
		this.p2Power = p2Power;
	}

	/**
	 * Gets the current combo of player 2
	 * 
	 * @return The integer value of combo for player 2
	 */
	public int getP2Combo() {
		return p2Combo;
	}

	/**
	 * Sets the combo value for player 2
	 * 
	 * @param p2Combo
	 *            The new combo value for player 2
	 */
	public void setP2Combo(int p2Combo) {
		this.p2Combo = p2Combo;
	}

	/**
	 * Gets the text of player 2
	 * 
	 * @return The text of player 2
	 */
	public String getP2Text() {
		return p2Text;
	}

	/**
	 * Sets the text of player 2
	 * 
	 * @param p2Text
	 *            The new text value for player 2
	 */
	public void setP2Text(String p2Text) {
		this.p2Text = p2Text;
	}

	/**
	 * Sets the name for player 1
	 * 
	 * @param p1Name
	 *            The new name for player 1
	 */
	public void setP1Name(String p1Name) {
		this.p1Name = p1Name;
	}

	/**
	 * Gets the name for player 1
	 * 
	 * @return The name of player 1
	 */
	public String getP1Name() {
		return p1Name;
	}

	/**
	 * Sets the name for player 2
	 * 
	 * @param p2Name
	 *            The new name for player 2
	 */
	public void setP2Name(String p2Name) {
		this.p2Name = p2Name;
	}

	/**
	 * Gets the name of player 2
	 * 
	 * @return The name of player 2
	 */
	public String getP2Name() {
		return this.p2Name;
	}

	/**
	 * Sets the IP address for the host (localhost for running two games on one
	 * machine)
	 * 
	 * @param hostname
	 *            The IP address for the host
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * Gets the hostname IP address
	 * 
	 * @return The IP address of the host
	 */
	public String getHostname() {
		return this.hostname;
	}

	/**
	 * Sets the network
	 * 
	 * @param network
	 *            The new network instance
	 */
	public void setNetwork(Network network) {
		this.network = network;
	}

	/**
	 * Gets the current network
	 * 
	 * @return The current network
	 */
	public Network getNetwork() {
		return this.network;
	}

	/**
	 * Gets the current mode as a screen
	 * 
	 * @return The game mode as a screen
	 */
	public Screen getMode() {
		return mode;
	}

	/**
	 * Sets the game mode by the next screen
	 * 
	 * @param mode
	 *            The screen of the game mode
	 */
	public void setMode(Screen mode) {
		this.mode = mode;
	}

	/**
	 * Gets the server instance
	 * 
	 * @return The instance of the server
	 */
	public Server getServer() {
		return this.server;
	}

	/**
	 * Sets the server instance
	 * 
	 * @param server
	 *            The new server instance
	 */
	public void setServer(Server server) {
		this.server = server;
	}

	/**
	 * Gets the connection status
	 * 
	 * @return The status of the connection
	 */
	public boolean isConnected() {
		return this.isConnected;
	}

	/**
	 * Sets whether the game is connected
	 * 
	 * @param isConnected
	 *            Whether the game is connected or not
	 */
	public void setConnect(boolean isConnected) {
		this.isConnected = isConnected;
	}

	/**
	 * Returns whether the game is ready to begin online
	 * 
	 * @return Connection status for starting a game
	 */
	public boolean isReady() {
		return this.isReady;
	}

	/**
	 * Sets the game to be ready
	 * 
	 * @param isReady
	 *            The readiness of the game
	 */
	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}

	/**
	 * Gets the network error if there is one
	 * 
	 * @return The current network error
	 */
	public String getNetworkError() {
		return networkError;
	}

	/**
	 * Sets a network error
	 * 
	 * @param error
	 *            The new network error
	 */
	public void setNetworkError(String error) {
		this.networkError = error;
	}

	/**
	 * Calls the opponent's key pressed method
	 * 
	 * @param key
	 *            The key that has been pressed
	 */
	public void receivedKeyPressed(int key) {
		this.inputHandler.getScreen().oppoKeyPressed(key);
	}

	/**
	 * Calls the opponent's key released method
	 * 
	 * @param key
	 *            The key that has been released
	 */
	public void receivedKeyReleased(int key) {
		this.inputHandler.getScreen().oppoKeyReleased(key);
	}

	/**
	 * Gets the array of how well the player has scored
	 * 
	 * @return The array of score quality
	 */
	public int[] getScoreQuality() {
		return scoreQuality;
	}

	/**
	 * Sets the array of how well the player has scored
	 * 
	 * @param scoreQuality
	 *            The integer array of how well the player has scored
	 */
	public void setScoreQuality(int[] scoreQuality) {
		this.scoreQuality = scoreQuality;
	}

	/**
	 * Gets the game speed
	 * 
	 * @return The speed of the game
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Sets the game speed, with 0.4 being standard
	 * 
	 * @param speed
	 *            The new game speed
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * Gets the array of all song files currently installed
	 * 
	 * @return The array of all song files
	 */
	public SongFile[] getSongFiles() {
		return this.songFiles;
	}

	/**
	 * Sets the current song file being played
	 * 
	 * @param songFile
	 *            The song currently being played
	 */
	public void setSongFile(SongFile songFile) {
		this.songFile = songFile;
	}

	/**
	 * Gets the current song being played
	 * 
	 * @return The song file currently being played
	 */
	public SongFile getSongFile() {
		return songFile;
	}

	/**
	 * Gets the AI level (1-10)
	 * 
	 * @return The level of the AI
	 */
	public int getAiLevel() {
		return aiLevel;
	}

	/**
	 * Sets the AI level (1-10)
	 * 
	 * @param aiLevel
	 *            The new AI level
	 */
	public void setAiLevel(int aiLevel) {
		this.aiLevel = aiLevel;
	}

	/**
	 * Gets the AI level in string form
	 * 
	 * @return The AI level in string form
	 */
	public String getAiLevelText() {
		return aiLevelText;
	}

	/**
	 * Sets the string form of the AI level
	 * 
	 * @param aiLevelText
	 *            The new string form of the AI level
	 */
	public void setAiLevelText(String aiLevelText) {
		this.aiLevelText = aiLevelText;
	}

	/**
	 * Sets the currently selected menu option
	 * 
	 * @param currentSelect
	 *            The currently selected menu option
	 */
	public void setCurrentSelect(int currentSelect) {
		this.currentSelect = currentSelect;
	}

	/**
	 * Gets the currently selected menu option
	 * 
	 * @return The menu option currently selected
	 */
	public int getCurrentSelect() {
		return this.currentSelect;
	}

}