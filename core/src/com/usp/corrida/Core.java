package com.usp.corrida;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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
	private static final float FIXED_WIDTH = 400;

	public float width = FIXED_WIDTH;
	public float height;

	public Random rand;
	public Save save;
	public Resources res;

	public TitleScreen titleScreen;
	public GameScreen gameScreen;

	public Background background;
	public Character charPlayer;

	public SpriteBatch batch;
	public OrthographicCamera camera;

	/**
	 * Chamada quando o programa é inicializado. Faz o carregamento completo do jogo
	 */
	@Override
	public void create () {
		rand = new Random();
		save = new Save();

		// Setting up the camera
		camera = new OrthographicCamera();
		updateScale(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
	 * Atualiza a escala da resolução do jogo
	 * @param width Largura da tela
	 * @param height Altura da tela
	 */
	private void updateScale(int width, int height){
		float scale = FIXED_WIDTH/width;
		this.height = height*scale;

		camera.position.set(this.width/2f, this.height/2f, 0);
		camera.setToOrtho(false, this.width, this.height);
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

	@Override
	public void resize(int width, int height){
		updateScale(width, height);
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
