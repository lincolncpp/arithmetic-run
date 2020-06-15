package com.usp.corrida;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Core extends com.badlogic.gdx.Game {

	// Resolution variables
	public float width;
	public float height;

	// Public batch
	public SpriteBatch batch;

	// Random instance
	public Random rand = new Random();

	// Screens
	TitleScreen titleScreen;
	GameScreen gameScreen;

	// Game instance
	Game game;

	// Font
	BitmapFont font;

	// Camera
	OrthographicCamera camera;

	// Debug
	Boolean showFPS = true;

	@Override
	public void create () {
		// Camera building
		width = Gdx.graphics.getWidth()/2f;
		height = Gdx.graphics.getHeight()/2f;
		camera = new OrthographicCamera(width, height);
		camera.position.set(width/2f, height/2f, 0);
		camera.update();

		// Batch building
		batch = new SpriteBatch();

		// Screen building
		titleScreen = new TitleScreen(this);
		gameScreen = new GameScreen(this);

		// Font building
		font = new BitmapFont(Gdx.files.internal("font/clacon.fnt"));

		// Background building
		game = new Game(this);

		// Set initial screen
		setScreen(titleScreen);
	}

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
			font.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 10, 20);
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

		setScreen(null);

		titleScreen.dispose();
		gameScreen.dispose();

		font.dispose();

		game.dispose();
	}
}
