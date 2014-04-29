package anandgames.spacegame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class LoadingScreen implements Screen {

	private Stage stage;
	private Table table;
	private Label loading;
	private Skin skin;
	private BitmapFont white, black;
	private TextureAtlas atlas;
	private AssetManager manager;

	public LoadingScreen() {
		super();
		manager = new AssetManager();
		queueAssets();
	}
	
	public void queueAssets() {
		manager.load("data/Space Game/Images/Sprites.png", Pixmap.class);
		manager.load("data/Space Game/Sounds/Explosion.wav", Sound.class);
//		manager.load("data/Space Game/Other/StarMap.tmx", TiledMap.class);
		manager.load("data/Space Game/Sounds/loop1.mp3", Sound.class);
		manager.load("data/Space Game/Images/ShipFrames.png", Texture.class);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (manager.update())
			((Game) Gdx.app.getApplicationListener())
			.setScreen(new GameScreen(manager));
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		stage = new Stage();
		atlas = new TextureAtlas("data/Space Game/ui/Button.pack");
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(atlas);
		table = new Table(skin);
		table.setFillParent(true);
		white = new BitmapFont(
				Gdx.files.internal("data/Space Game/Fonts/White.fnt"), false);
		black = new BitmapFont(
				Gdx.files.internal("data/Space Game/Fonts/Black.fnt"), false);
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.up");
		textButtonStyle.down = skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = black;

		LabelStyle labelStyle = new LabelStyle(white, Color.WHITE);

		loading = new Label("LOADING...\n", labelStyle);
		loading.setAlignment(Align.center);
		loading.setFontScale(2f);

		table.add(loading).center();
		table.debug();
		stage.addActor(table);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
