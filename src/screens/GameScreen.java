package screens;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import main.Assets;
import main.TLFGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import database.Records;
import database.Status;
import database.Usuario;
import entities.BotoesPrincipais;
import entities.CharactersOrdem;
import entities.DialogSelected;
import entities.HUDF1;
import entities.HUDF2;
import entities.HUDFor;
import entities.HUDIF;
import entities.HUDLateral;
import entities.HUDMovimentos;
import entities.Inimigo;
import entities.Itens;
import entities.LevelUp;
import entities.OptionsGame;
import entities.Player;
import entities.PlayerInfo;
import entities.PlayerMovimentos;
import entities.ProximaFase;
import entities.StatusBar;
import entities.TextAlgoritmos;


public class GameScreen implements Screen {

	public static TiledMap tiledMap;
	public static OrthogonalTiledMapRenderer map;
	public static OrthographicCamera camera;
	public static SpriteBatch batch;

	public static Usuario usuario;
	public static Status statusUsuario;
	public static Player player;
	public static Records record;

	public static ArrayList<Inimigo> listaInimigos;

	public static CharactersOrdem characterOrdem;
	public static ArrayList<CharactersOrdem> listaOrdenadaCharacter;

	public static HUDMovimentos hudMovimentos;
	public static HUDLateral hudLateral;
	public static HUDF1 hudF1;
	public static HUDF2 hudF2;
	public static HUDFor hudFor;
	public static HUDIF hudIf;
	public static BotoesPrincipais btnPrincipais;
	public static StatusBar statusBar;
	public static PlayerInfo playerInfo;
	public static TextAlgoritmos textAlgoritmos;
	public static OptionsGame optGame;
	public static DialogSelected dlgSelected;
	public static PlayerMovimentos playerMovimentos;
	public static Itens itens;
	public static ProximaFase proximaFase;

	public static int rodada;

	public static float w;
	public static  float h;

	public static Stage stage;

	public static TLFGame game;
	public GameScreen(TLFGame game) {
		GameScreen.game = game;
	}

	@Override
	public void show() {

		stage = new Stage();

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		tiledMap = new TmxMapLoader().load(LoadScreen.diretorioMapLoad);
		map = new OrthogonalTiledMapRenderer(tiledMap);

		batch = (SpriteBatch) map.getSpriteBatch();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w-6, h);

		usuario = new Usuario();
		usuario = LoginScreen.usuario;

		rodada = 0;

		statusUsuario = new Status();
		statusUsuario = statusUsuario.getStatus();
		
		record = new Records();
		record.setId(GameScreen.usuario.getId());
		record.setFase(LoadScreen.faseAtual);
		record.setPontos(BotoesPrincipais.getPoints());

		player = new Player();
		player.LoadPlayerTexture();
		player.map = map;
		ArrayList<Integer> posPlayer = getPositionPlayerInMap();
		player.position = new Vector2(posPlayer.get(0), posPlayer.get(1));

		listaInimigos = getInimigosInMap();

		itens = new Itens();
		hudMovimentos = new HUDMovimentos();
		hudMovimentos.LoadHUDMovimentosTexture();
		hudLateral = new HUDLateral();
		hudLateral.LoadHUDLateralTexture();
		hudF1 = new HUDF1();
		hudF1.LoadHUDF1Texture();
		hudF2 = new HUDF2();
		hudF2.LoadHUDF2Texture();
		hudFor = new HUDFor();
		hudFor.LoadHUDForTexture();
		hudIf = new HUDIF();
		hudIf.LoadHUDIFTexture();
		textAlgoritmos = new TextAlgoritmos();
		textAlgoritmos.LoadTextAlgoritmosTexture();

		btnPrincipais = new BotoesPrincipais();
		btnPrincipais.LoadBotoesPrincipaisTexture();

		dlgSelected = new DialogSelected();
		dlgSelected.LoadDialogSelectedTexture();

		statusBar = new StatusBar();
		statusBar.LoadStatusBarTexture();

		playerInfo = new PlayerInfo();

		optGame = new OptionsGame();
		optGame.LoadOptionsGameTexture();

		playerMovimentos = new PlayerMovimentos();
		playerMovimentos.LoadPlayerMovimentos();

		proximaFase = new ProximaFase();
		
		LevelUp.isUp = false;
		
		Properties propertiesFase = LoadScreen.getMapConfig(LoadScreen.faseAtual);
		new Dialog("Objetivo", Assets.skin, "dialog") {
		}.text("    "+propertiesFase.getProperty("msgObjetivo")+"   ").button("Ok", false).key(Keys.ENTER, false)
		.key(Keys.ESCAPE, false).show(GameScreen.getStage()).setMovable(false);

	}

	private ArrayList<Inimigo> getInimigosInMap() {

		ArrayList<Inimigo> inimigosMap = new ArrayList<>();

		int countLayers = GameScreen.map.getMap().getLayers().getCount();

		for (int i = 0; i < countLayers; i++) {
			try {	
				TiledMapTileLayer mapLayer = (TiledMapTileLayer) GameScreen.map.getMap().getLayers().get(i);
				if (mapLayer!=null){
					for (int j = 0; j < 24; j++) {
						for (int j2 = 0; j2 < 20; j2++) {
							Cell cell = ((TiledMapTileLayer) mapLayer).getCell(j, j2);
							if (cell!=null){
								Object property = cell.getTile().getProperties().get("nome");
								if(property!=null){
									Inimigo novoInimigo = new Inimigo();
									if (property.equals("soldado")){
										novoInimigo = new Inimigo(map, j*32, j2*32, new Random().nextInt(3)+1, "soldado", Integer.parseInt(cell.getTile().getProperties().get("experiencia")+""), Integer.parseInt(cell.getTile().getProperties().get("health")+""), Integer.parseInt(cell.getTile().getProperties().get("stamina")+""), Integer.parseInt(cell.getTile().getProperties().get("ataque")+""), Integer.parseInt(cell.getTile().getProperties().get("defesa")+""));
										novoInimigo.LoadPlayerTexture();
										inimigosMap.add(novoInimigo);
										cell.setTile(null);		
									} else if (property.equals("rei")){
										novoInimigo = new Inimigo(map, j*32, j2*32, new Random().nextInt(3)+1, "rei", Integer.parseInt(cell.getTile().getProperties().get("experiencia")+""), Integer.parseInt(cell.getTile().getProperties().get("health")+""), Integer.parseInt(cell.getTile().getProperties().get("stamina")+""), Integer.parseInt(cell.getTile().getProperties().get("ataque")+""), Integer.parseInt(cell.getTile().getProperties().get("defesa")+""));
										novoInimigo.LoadPlayerTexture();
										inimigosMap.add(novoInimigo);
										cell.setTile(null);		
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {} 
		}

		return inimigosMap;
	}
	
	private ArrayList<Integer> getPositionPlayerInMap() {

		ArrayList<Integer> positionInMap = new ArrayList<>();

		int countLayers = GameScreen.map.getMap().getLayers().getCount();

		for (int i = 0; i < countLayers; i++) {
			try {	
				TiledMapTileLayer mapLayer = (TiledMapTileLayer) GameScreen.map.getMap().getLayers().get(i);
				if (mapLayer!=null){
					for (int j = 0; j < 24; j++) {
						for (int j2 = 0; j2 < 20; j2++) {
							Cell cell = ((TiledMapTileLayer) mapLayer).getCell(j, j2);
							if (cell!=null){
								Object property = cell.getTile().getProperties().get("nome");
								if(property!=null){
									if (property.equals("player")){
										positionInMap.add(j*32); 
										positionInMap.add(j2*32);
										cell.setTile(null);		
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {} 
		}
		if (positionInMap.size() <= 0){
			positionInMap.add(0); 
			positionInMap.add(545); 
		}

		return positionInMap;
	}

	@Override
	public void render(float deltaTime) {

		//Gdx.graphics.setTitle("The Logic Fighter - 1.0.1 Beta -- FPS: " + Gdx.graphics.getFramesPerSecond());

		// limpar tela
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// desenha mapa
		map.setView(camera);
		map.render();	 

		// verifica ordem que vai desenhar os characters na tela de acordo com a posição de Y
		characterOrdem = new CharactersOrdem();
		listaOrdenadaCharacter = new ArrayList<>();
		listaOrdenadaCharacter.add(characterOrdem.convertePlayer(player));
		for (Inimigo inimigo : listaInimigos) {
			listaOrdenadaCharacter.add(characterOrdem.converteInimigo(inimigo));
		}
		characterOrdem.ordenaLista(listaOrdenadaCharacter);
		for (CharactersOrdem character : listaOrdenadaCharacter) {
			for (Inimigo inimigo : listaInimigos) {
				if (character.equals(characterOrdem.converteInimigo(inimigo))){
					// desenha inimigo
					inimigo.UpdatePlayer(deltaTime);
					inimigo.DrawPlayer(batch, deltaTime);
				}
			}
			if (character.equals(characterOrdem.convertePlayer(player)) && !Player.isDead){
				// desenha o player
				player.UpdatePlayer(deltaTime);
				player.DrawPlayer(batch, deltaTime);
			}
		}

		// desenha barra de status do jogador
		statusBar.DrawStatusBar(batch, deltaTime);

		// desenha infos do player caso aperte I
		playerInfo.DrawPlayerInfo(batch, deltaTime);

		// desenha opções do jogo caso aperte ESC
		optGame.DrawOptionsGame(batch, deltaTime);

		// desenha ataque, pegar, defender do player na posição da frente do personagem caso chamado os determinados metodos
		playerMovimentos.DrawPlayerMovimentos(batch, deltaTime);	

		// desenha todos os componentes que usam o stage das classes
		try {
			stage.act(deltaTime);
			stage.draw();
		} catch (Exception e) {	e.printStackTrace();}

		// desenha no dialog lateral "icone de alvo" que esta selecionado para identifica-lo
		dlgSelected.DrawDialogSelected(batch, deltaTime);

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
		// TODO Auto-generated method stub

	}

	public static Stage getStage() {return stage;}

	public static TLFGame getGame() {return game;}


}
