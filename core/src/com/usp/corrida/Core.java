package com.usp.corrida;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Core extends Game {

	// Resolution variables
	public float width;
	public float height;

	// Public batch
	public SpriteBatch batch;

	// Screens
	TitleScreen titleScreen;
	GameScreen gameScreen;

	// Background
	Background background;

	// Font
	BitmapFont font;

	// Camera
	OrthographicCamera camera;

	// Debug
	Boolean showFPS = true;

	@Override
	public void create () {
		// Batch building
		batch = new SpriteBatch();

		// Screen building
		titleScreen = new TitleScreen(this);
		gameScreen = new GameScreen(this);

		// Font building
		font = new BitmapFont();

		// Background building
		background = new Background(this);

		// Camera building
		width = Gdx.graphics.getWidth()/2f;
		height = Gdx.graphics.getHeight()/2f;
		camera = new OrthographicCamera(width, height);
		camera.position.set(width/2f, height/2f, 0);
		camera.update();

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

		background.dispose();
	}
}
