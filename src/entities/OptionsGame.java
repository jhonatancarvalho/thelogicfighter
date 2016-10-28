package entities;

import java.util.Properties;

import main.Assets;
import main.TLFGame;
import screens.GameScreen;
import screens.LoadScreen;
import screens.LoginScreen;
import screens.MainMenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class OptionsGame {

	private TLFGame game;

	private Dialog dialogScreenOpcoes;

	private TextButton startGameButton;
	private TextButton objetivoButton;
	private TextButton optionsButton;
	private TextButton recordButton;
	private TextButton exitButton;

	public void LoadOptionsGameTexture() {

		game = GameScreen.getGame();
		
		dialogScreenOpcoes = new Dialog("TLF", Assets.skin);
		dialogScreenOpcoes.setName("dialogScreenOpcoes");
		dialogScreenOpcoes.setMovable(false);

		Table tabela = new Table(Assets.skin);

		startGameButton = new TextButton("Novo Jogo", Assets.skin);
		objetivoButton = new TextButton("Objetivo", Assets.skin);
		optionsButton = new TextButton("Carregar", Assets.skin);
		recordButton = new TextButton("Record", Assets.skin);
		exitButton = new TextButton("Sair", Assets.skin);

		tabela.setFillParent(true); 

		tabela.add(startGameButton).width(110).padTop(25).padRight(2);
		tabela.row();	
		tabela.add(objetivoButton).width(110).padTop(2).padRight(2);
		tabela.row();	
		tabela.add(optionsButton).width(110).padTop(2).padRight(2);
		tabela.row();	
		tabela.add(recordButton).width(110).padTop(2).padRight(2);
		tabela.row();
		tabela.add(exitButton).width(110).padTop(2).padRight(2);

		UpdateOptionsGame();

		dialogScreenOpcoes.add(tabela);
		dialogScreenOpcoes.key(Keys.ESCAPE, true);		
	}

	public void UpdateOptionsGame() {

		startGameButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new GameScreen(game));
				return true;
			}

		});
		
		objetivoButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Properties propertiesFase = LoadScreen.getMapConfig(LoadScreen.faseAtual);
				new Dialog("Objetivo", Assets.skin, "dialog") {
				}.text("    "+propertiesFase.getProperty("msgObjetivo")+"   ").button("Ok", false).key(Keys.ENTER, false)
				.key(Keys.ESCAPE, false).show(GameScreen.getStage()).setMovable(false);
				return true;
			}

		});

		optionsButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new LoadScreen(game));
				LoginScreen.themeMusic.play();
				return true;
			}

		});
		
		recordButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				new Dialog("Record", Assets.skin, "dialog") {
				}.text("    Pontuacao: "+GameScreen.record.getRecords()+"   ").button("Ok", false).key(Keys.ENTER, false)
				.key(Keys.ESCAPE, false).show(GameScreen.getStage()).setMovable(false);
				return true;
			}

		});



		exitButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new MainMenuScreen(game));
				LoginScreen.themeMusic.play();
				return false;
			}

		});


	}

	public void DrawOptionsGame(SpriteBatch batch, float deltaTime) {

		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			TextAlgoritmos.escPresed++;
			if (!TextAlgoritmos.focusInTxtArea && !HUDMovimentos.verificaAction("dialogScreen") && !HUDMovimentos.verificaAction("dialogScreenOpcoes") && TextAlgoritmos.getEscPressed()){
				dialogScreenOpcoes.show(GameScreen.getStage()).setBounds(0, GameScreen.h, startGameButton.getWidth()+6, (startGameButton.getHeight()*5)+30);
			} else {
				for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
					if (GameScreen.getStage().getActors().get(i).toString().equals("dialogScreenOpcoes")){
						GameScreen.getStage().getActors().removeIndex(i);
					}
				}
			}

		}

	}
}
