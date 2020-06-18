package com.usp.corrida;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.usp.corrida.logic.Background;
import com.usp.corrida.logic.Character;
import com.usp.corrida.screens.GameScreen;
import com.usp.corrida.screens.TitleScreen;
import com.usp.corrida.utils.Random;

/**
 * Classe principal do jogo.
 */
public class Core extends com.badlogic.gdx.Game {

	public float width;
	public float height;

	// Public batch
	public SpriteBatch batch;

	// Random instance
	public Random rand = new Random();

	// Screens
	public TitleScreen titleScreen;
	public GameScreen gameScreen;

	// Game instance
	public Background background;

	// Character
	public Character charPlayer;

	// Shared resources
	public Resources res;

	// Camera
	private OrthographicCamera camera;

	// Debug
	Boolean showFPS = false;

	/**
	 * Faz as primeiras configurações do programa
	 */
	@Override
	public void create () {
		// Camera building
		width = Gdx.graphics.getWidth()/2f;
		height = Gdx.graphics.getHeight()/2f;
		camera = new OrthographicCamera(width, height);
		camera.position.set(width/2f, height/2f, 0);
		camera.update();

		// Resouces loading
		res = new Resources();

		// Batch building
		batch = new SpriteBatch();

		// Screen building
		titleScreen = new TitleScreen(this);
		gameScreen = new GameScreen(this);

		// Background building
		background = new Background(this);

		setupPlayer();

		// Set initial screen
		setScreen(titleScreen);
	}

	/**
	 * Configuração inicial do jogador
	 */
	public void setupPlayer(){
		charPlayer = new Character(this, 0);
		charPlayer.setHorizontalFlip(true);
		charPlayer.setPos(32, 32);
	}

	/**
	 * Cuida de toda a renderização do jogo
	 */
	@Override
	public void render () {
		Gdx.gl.glClearColor(69/255f, 186/255f, 230/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		// Draw current screen
		screen.render(Gdx.graphics.getDeltaTime());

		// Debug
		if (showFPS){
			res.font.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 10, 20);
		}

		batch.end();
	}

	/**
	 * Descarrega todos os recursos
	 */
	@Override
	public void dispose () {
		batch.dispose();

		setScreen(null);
		titleScreen.dispose();
		gameScreen.dispose();

		background.dispose();

		charPlayer.dispose();

		res.dispose();
	}
}
