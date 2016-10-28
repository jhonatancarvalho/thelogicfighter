package screens;

import java.io.File;

import main.Assets;
import main.TLFGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainMenuScreen implements Screen {

	TLFGame game;
	Stage stage;

	Image imagemFundo;
	
	TextButton startGameButton;
	TextButton optionsButton;
	TextButton exitButton;

	Music themeMusic;

	public MainMenuScreen(TLFGame game) {
		this.game = game;
	}
	
	@Override
	public void show() {

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		// music
		themeMusic = LoginScreen.themeMusic;
		Table tableSom = LoginScreen.tableSom;
		
		// opçoes de jogo
		Table tableOpcoes = new Table(Assets.skin);	

		startGameButton = new TextButton("Novo Jogo", Assets.skin);
		optionsButton = new TextButton("Carregar", Assets.skin);
		exitButton = new TextButton("Sair", Assets.skin);
		
		startGameButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				File[] fases = LoadScreen.getFases();
				LoadScreen.diretorioMapLoad = fases[0].getAbsolutePath()+"/map/map.tmx";			
				game.setScreen(new GameScreen(game));
				themeMusic.stop();
				return true;
			}

		});

		optionsButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new LoadScreen(game));		
				return true;
			}

		});

		
		exitButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.exit(0);
				return false;
			}

		});
		
		tableOpcoes.setFillParent(true); 
		tableOpcoes.add(startGameButton).width(200).height(50);
		tableOpcoes.row();
		tableOpcoes.add(optionsButton).width(200).height(50).padTop(10);
		tableOpcoes.row();
		tableOpcoes.add(exitButton).width(200).height(50).padTop(10);
		tableOpcoes.row();

		// fundo
		imagemFundo = new Image(Assets.backgroundTexture);

		// adicionar componentes na tela
		stage.addActor(imagemFundo);
		stage.addActor(tableOpcoes);
		stage.addActor(tableSom);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		//stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		stage.getViewport().update(width, height, true);
		//imagemFundo = new Image(Assets.backgroundTexture);
		//imagemFundo.setWidth(Gdx.graphics.getWidth());
		//imagemFundo.setHeight(Gdx.graphics.getHeight());
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
		themeMusic.dispose();
		stage.dispose();
	}

}
