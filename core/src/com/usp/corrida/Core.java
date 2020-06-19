package com.usp.corrida;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.usp.corrida.logic.Background;
import com.usp.corrida.logic.Character;
import com.usp.corrida.screens.GameScreen;
import com.usp.corrida.screens.TitleScreen;
import com.usp.corrida.utils.Random;
import com.usp.corrida.utils.Save;

/**
 * Controlador central do programa. Faz a comunicação entre todos os outros componentes
 */
public class Core extends Game {
	private static final Boolean showFPS = false;

	public float width;
	public float height;

	public Random rand;
	public Save save;
	public Resources res;

	public TitleScreen titleScreen;
	public GameScreen gameScreen;

	public Background background;
	public Character charPlayer;

	public SpriteBatch batch;
	private OrthographicCamera camera;

	/**
	 * Chamada quando o programa é inicializado. Faz o carregamento completo do jogo
	 */
	@Override
	public void create () {
		rand = new Random();
		save = new Save();

		// Setting up the camera
		width = Gdx.graphics.getWidth()/2f;
		height = Gdx.graphics.getHeight()/2f;
		camera = new OrthographicCamera(width, height);
		camera.position.set(width/2f, height/2f, 0);
		camera.update();

		// Loading resources
		res = new Resources();

		// Setting up the batch
		batch = new SpriteBatch();

		// Loading and setting up the background
		background = new Background(this);

		// Setting up the player
		setupPlayer();

		// Setting up the screens
		titleScreen = new TitleScreen(this);
		gameScreen = new GameScreen(this);

		// Initial screen
		setScreen(titleScreen);
	}

	/**
	 * Faz a primeira configuração do jogador
	 */
	private void setupPlayer(){
		charPlayer = new Character(this, 0);
		charPlayer.setHorizontalFlip(true);
	}

	/**
	 * Renderiza todo o programa
	 */
	@Override
	public void render () {
		Gdx.gl.glClearColor(69/255f, 186/255f, 230/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		// Drawing the current screen
		screen.render(Gdx.graphics.getDeltaTime());

		// Debug
		if (showFPS){
			res.font20.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 10, 20);
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
