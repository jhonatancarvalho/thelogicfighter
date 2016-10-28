package screens;

import main.Assets;
import main.TLFGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import database.Usuario;

public class LoginScreen implements Screen {

	TLFGame game;
	Stage stage;

	Image imagemFundo;

	Dialog dialogScreenLogin;

	Label loginLabel;
	Label senhaLabel;
	TextField txtLogin;
	TextField txtSenha;
	TextButton entrarButton;
	TextButton criarButton;

	public static Table tableSom;
	public static Slider sliderMusicVolume;
	public static ImageButton musicButton;
	public static Texture textureButton;
	public static TextureRegion imageButton;
	public static TextureRegion imageFlipped;	
	public static ImageButtonStyle styleButton;
	public static boolean mute;

	public static Music themeMusic;

	public static Usuario usuario;

	public LoginScreen(TLFGame game) {
		this.game = game;
	}

	@Override
	public void show() {

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		// login	
		dialogScreenLogin = new Dialog("Login", Assets.skin);
		dialogScreenLogin.setName("dialogScreenLogin");
		dialogScreenLogin.setMovable(false);
		dialogScreenLogin.setModal(false);

		entrarButton = new TextButton("Entrar", Assets.skin);
		criarButton = new TextButton("Criar conta", Assets.skin);

		loginLabel = new Label("Login: ", Assets.skin);
		senhaLabel = new Label("Senha: ", Assets.skin);
		txtLogin = new TextField("", Assets.skin);
		txtSenha = new TextField("", Assets.skin);
		txtSenha.setMessageText("senha");
		txtSenha.setPasswordCharacter('*');
		txtSenha.setPasswordMode(true);

		Table tableOpcoes = new Table(Assets.skin);
		tableOpcoes.add(loginLabel).width(50).height(30).padLeft(5);
		tableOpcoes.add(txtLogin).width(150).height(30).padRight(5).padLeft(10);
		tableOpcoes.row();
		tableOpcoes.add(senhaLabel).width(50).height(30).padTop(5).padLeft(5);
		tableOpcoes.add(txtSenha).width(150).height(30).padTop(5).padRight(5).padLeft(10);

		Table tableButtons = new Table(Assets.skin);
		tableButtons.add(entrarButton).width(100).padBottom(5);
		tableButtons.add(criarButton).width(100).padBottom(5).padLeft(10);
		tableButtons.row();

		dialogScreenLogin.row();
		dialogScreenLogin.add(tableOpcoes).expand();
		dialogScreenLogin.row();
		dialogScreenLogin.add(tableButtons).expand();
		dialogScreenLogin.setBounds(Gdx.graphics.getWidth()/2-(250/2), Gdx.graphics.getHeight()/2-(160/3), 250, 160);

		entrarButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				try {
					usuario = new Usuario(txtLogin.getText(), txtSenha.getText());
					if (usuario.verificaUsuario()){
						if (usuario.verificaFirstLogin()){
							String msgFirstLogin = "Bem vindo ao The Logic Fighter!";
							((Dialog) new Dialog("Mensagem", Assets.skin, "dialog") {
								protected void result (Object object) {
									game.setScreen(new MainMenuScreen(game));
								}
							}.text(msgFirstLogin).button("Continuar", true).key(Keys.ENTER, true)
							.key(Keys.ESCAPE, false).padTop(25).padLeft(10).padRight(10).padBottom(15)).show(stage).setMovable(false);
						} else {
							game.setScreen(new MainMenuScreen(game));
						}
					} else {
						showMessagem("Erro", "Login ou senha informados sao invalidos.", "Ok");
					}

				} catch (Exception e) {
					showMessagem("Erro", "Ocorreu algum problema, tente novamente.", "Ok");
				}
				return true;
			}
		});

		criarButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {				
				usuario = new Usuario(txtLogin.getText(), txtSenha.getText());
				try {
					if (usuario.cadastrarUsuario()){
						showMessagem("Mensagem", "Usuario cadastrado com sucesso!", "Ok");
					} else {
						showMessagem("Erro", "Ja possui um usuario cadastrado com esse nome!", "Ok");
					}
				} catch (Exception e) {
					showMessagem("Erro", "Ocorreu algum problema, tente novamente.", "Ok");
				}
				return true;
			}
		});			

		// img fundo
		imagemFundo = new Image(Assets.backgroundTexture);	

		// som
		mute = false;
		themeMusic = Gdx.audio.newMusic(Gdx.files.internal("music/theme.mp3"));
		themeMusic.setLooping(true);
		themeMusic.play();

		sliderMusicVolume = new Slider(0, 100, 1, false, Assets.skin);
		sliderMusicVolume.setValue(100.0f);		

		imageButton = new TextureRegion(new Texture(Gdx.files.internal("music/som_on.png")));
		imageFlipped = new TextureRegion(new Texture(Gdx.files.internal("music/som_mute.png")));			
		styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));		
		styleButton.imageUp = new TextureRegionDrawable(imageButton);	
		musicButton = new ImageButton(styleButton);

		tableSom = new Table(Assets.skin);
		tableSom.setFillParent(true);
		tableSom.add(sliderMusicVolume).width(150).height(20);
		tableSom.add(musicButton).width(20).height(20).padLeft(2);
		tableSom.setPosition(380, 300);
		tableSom.row();

		musicButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (!mute){
					styleButton.imageUp = new TextureRegionDrawable(imageFlipped);	
					musicButton = new ImageButton(styleButton);
					themeMusic.pause();
					mute = true;
				} else {
					styleButton.imageUp = new TextureRegionDrawable(imageButton);	
					musicButton = new ImageButton(styleButton);
					themeMusic.play();
					mute = false;
				}
				return true;
			}
		});



		sliderMusicVolume.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				themeMusic.setVolume(sliderMusicVolume.getValue()/100.0f);			
			}

		});
		//

		stage.addActor(imagemFundo);
		stage.addActor(tableSom);
		stage.addActor(dialogScreenLogin);

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
		stage.getViewport().update(width, height, true);
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

	public void showMessagem(String cabecalho, String mensagem, String nomeBotao){
		((Dialog) new Dialog(cabecalho, Assets.skin, "dialog") {
		}.text(mensagem).button(nomeBotao, true).key(Keys.ENTER, true)
		.key(Keys.ESCAPE, false).padTop(25).padLeft(10).padRight(10).padBottom(15)).show(stage).setMovable(false);
	}

}
