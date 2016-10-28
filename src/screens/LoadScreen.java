package screens;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Properties;

import main.Assets;
import main.TLFGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LoadScreen implements Screen {

	TLFGame game;
	Stage stage;

	public static String diretorioMapLoad;
	public static String faseAtual = "1";

	Image imagemFundo;

	public static Music themeMusic;

	ScrollPane scrollPane; 
	Table tableFases;	
	ImageButton faseButton;

	public LoadScreen(TLFGame game) {
		this.game = game;
	}

	@Override
	public void show() {

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		// img fundo
		imagemFundo = new Image(Assets.backgroundTexture);	

		// som
		themeMusic = LoginScreen.themeMusic;
		Table tableSom = LoginScreen.tableSom;

		// load fases
		tableFases = new Table(Assets.skin);

		scrollPane = new ScrollPane(tableFases, Assets.skin);
		scrollPane.setForceScroll(false, true);
		scrollPane.setFlickScroll(false);
		scrollPane.setOverscroll(false, true);
		scrollPane.setBounds(Gdx.graphics.getWidth()/2-(600/2), Gdx.graphics.getHeight()/2-(550/2), 600, 550);

		File[] fases = getFases();

		int countLine = 0;
		for (int i = 0; i < fases.length; i++) {
			countLine++;

			Properties faseConfig = getMapConfig(fases[i].getName());

			if (faseConfig.getProperty("ableToPlayer").equals("true")){

				Texture textureButton = new Texture(fases[i].getAbsolutePath()+"/img.png");
				TextureRegion imageButton = new TextureRegion(textureButton);		
				ImageButtonStyle styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
				styleButton.imageUp = new TextureRegionDrawable(imageButton);
				faseButton = new ImageButton(styleButton);
				faseButton.setName(fases[i].getName());
				final String diretorioMapAtual = fases[i].getAbsolutePath()+"/map/map.tmx";
				faseButton.addListener(new InputListener() {
					final String nomeBotao = faseButton.getName();
					final String diretorioMap = diretorioMapAtual;
					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						diretorioMapLoad = diretorioMap;
						faseAtual = nomeBotao;
						game.setScreen(new GameScreen(game));
						themeMusic.stop();
						return true;
					}
				}); 			
			} else {
				Texture textureButton = new Texture(Gdx.files.internal("block/cadeado.png"));
				TextureRegion imageButton = new TextureRegion(textureButton);		
				ImageButtonStyle styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
				styleButton.imageUp = new TextureRegionDrawable(imageButton);
				faseButton = new ImageButton(styleButton);
				faseButton.setName(fases[i].getName());
				faseButton.setDisabled(true);
				/*final String diretorioMapAtual = fases[i].getAbsolutePath()+"/map/map.tmx";
				faseButton.addListener(new InputListener() {
					final String nomeBotao = faseButton.getName();
					final String diretorioMap = diretorioMapAtual;
					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						diretorioMapLoad = diretorioMap;
						faseAtual = nomeBotao;
						game.setScreen(new GameScreen(game));
						themeMusic.stop();
						return true;
					}
				});*/

			}

			if (countLine > 3){
				countLine = 1;		
				tableFases.row();

				Table tblAux = new Table(Assets.skin);
				tblAux.add("Fase "+fases[i].getName());
				tblAux.row();
				tblAux.add(faseButton).width(150).height(150).padLeft(25).padBottom(25).padRight(25);

				tableFases.add(tblAux);
			} else {

				Table tblAux = new Table(Assets.skin);
				tblAux.add("Fase "+fases[i].getName());
				tblAux.row();
				tblAux.add(faseButton).width(150).height(150).padLeft(25).padBottom(25).padRight(25);

				tableFases.add(tblAux);
			}				
		}	

		scrollPane.scrollToCenter(0, 0, 0, 0);

		stage.addActor(imagemFundo);
		stage.addActor(tableSom);
		stage.addActor(scrollPane);

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

	}

	// retorna um array de file com todas as fases
	public static File[] getFases(){
		File diretorioFases[] = new File("fases").listFiles(); 
		Arrays.sort(diretorioFases);
		return diretorioFases;
		/*for ( int i = 0; i < diretorioFases.length ; i++ ){ 
			System.out.println("Arquivo mencionado : " + diretorioFases[i]); 
			System.out.println("Arquivo mencionado : " + diretorioFases[i].getName()); 
		} */

	}
	
	// retorna o arquivo de proprities de configuração de acordo com o nome da fase
	public static Properties getMapConfig(String nomeFase){

		File[] fases = getFases();
		for (File fase : fases) {
			if (fase.getName().equals(nomeFase)){
				String arquivoConf =  fase.getAbsolutePath()+"/mapconfig.tlf";
				Properties faseConfig = new Properties();
				try {			
					faseConfig.load(new FileInputStream(arquivoConf));
				} catch (Exception e) {e.printStackTrace();	} 
				return faseConfig;
			}
		}
		return null;

	}

	// seta alterações no arquivo proprities de configuração de acordo com o nome da fase
	public static void setMapConfig(String nomeFase, String atributo, String valor){

		File[] fases = getFases();
		for (File fase : fases) {
			if (fase.getName().equals(nomeFase)){
				String arquivoConf =  fase.getAbsolutePath()+"/mapconfig.tlf";
				Properties faseConfig = new Properties();
				try {
					
					faseConfig.load(new FileInputStream(arquivoConf));
					faseConfig.setProperty(atributo,valor);		
					faseConfig.store(new FileOutputStream(arquivoConf), null);

				} catch (Exception e) {e.printStackTrace();	} 
				break;
			}
		}

	}


}
