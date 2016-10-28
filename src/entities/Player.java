package entities;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import main.Assets;
import screens.GameScreen;
import screens.LoadScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Player {

	TextureRegion downChar_STILL;
	TextureRegion downChar_LEFT;
	TextureRegion downChar_RIGHT;
	TextureRegion leftChar_STILL;
	TextureRegion leftChar_LEFT;
	TextureRegion leftChar_RIGHT;
	TextureRegion rightChar_STILL;
	TextureRegion rightChar_LEFT;
	TextureRegion rightChar_RIGHT;
	TextureRegion upChar_STILL;
	TextureRegion upChar_LEFT;
	TextureRegion upChar_RIGHT;

	TextureRegion mainChar;

	ArrayList<Label> msgsAnimadas;

	// status do personagem
	public enum State
	{
		standby,
		walkingDOWN,
		walkingUP,
		walkingLEFT,
		walkingRIGHT,
		digging,
		dying,
	}

	// left = esquerda
	// 0 = down; 1 = left; 2 = up; 3 = right
	public int dir = 0;

	public static float hpTotal;
	public static float hpAtual;

	public static float spTotal;
	public static float spAtual;

	public static float xpTotal;
	public static float xpAtual;

	public static int level;

	public static int forca;
	public static int defesa;

	public float WIDTH = 32; // largura tile
	public float HEIGHT = 32; // altura tile

	public float playerWIDTH = 32; // largura personagem
	public float playerHEIGHT = 48; // altura personagem

	public static boolean isDead;

	// indicador se a movimentação 
	private float countMaisX = 0;
	private float countMenosX = 0;
	private float countMaisY = 0;
	private float countMenosY = 0;

	static float speedmax = 30f; // velocidade de movimento

	//static float damping = 0.87f; 
	static float damping = 0f; // delay de curva

	// posição inicial do jogador no mapa
	public Vector2 position = new Vector2();

	public Vector2 speed = new Vector2();

	State status = State.standby;

	public OrthogonalTiledMapRenderer map;

	private Pool<Rectangle> rectPool = new Pool<Rectangle>(){
		@Override
		protected Rectangle newObject(){
			return new Rectangle();
		}
	};

	private Array<Rectangle> tiles = new Array<Rectangle>();

	Rectangle playerRectangle;


	public void UpdatePlayer(float deltaTime) {

		if (deltaTime == 0)
			return;

		// movimentação pelo menu de movimentos
		if (GameScreen.hudMovimentos.getMovimentoAtual().equals("FRENTE")){
			if (!verificaColisao()){
				if (dir==0){
					if (countMenosY<=position.y){
						status = State.walkingDOWN;
						speed.y = -speedmax;
					} else {
						position.y = countMenosY;
						GameScreen.hudMovimentos.setMovimentoAtual("");
					}

				}else if(dir==1){
					if (countMenosX<=position.x){
						status = State.walkingLEFT;
						speed.x = -speedmax;
					} else {
						position.x = countMenosX;
						GameScreen.hudMovimentos.setMovimentoAtual("");
					}

				}else if(dir==2){
					if (countMaisY>=position.y){
						status = State.walkingUP;
						speed.y += speedmax;
					} else {
						position.y = countMaisY;
						GameScreen.hudMovimentos.setMovimentoAtual("");
					}	

				}else if(dir==3){
					if (countMaisX>=position.x){
						status = State.walkingRIGHT;
						speed.x += speedmax;
					} else {
						position.x = countMaisX;
						GameScreen.hudMovimentos.setMovimentoAtual("");
					}

				}
			} else {
				if (Assets.config.getProperty("colisionLoseHp").equals("true")){
					if (!GameScreen.playerMovimentos.shieldAnimation){
						HUDMovimentos.animatedMessage("Voce colidiu!", 2);
						Player.removeLife(Integer.parseInt(Assets.config.getProperty("colisionHp")));
					}
				}
				if (Player.hpAtual <= 0){
					new GameOver();
				}		
			}
		} else if (GameScreen.hudMovimentos.getMovimentoAtual().equals("DIREITA")){
			dir++;
			if (dir > 3){
				dir = 0;
			}
			GameScreen.hudMovimentos.setMovimentoAtual("");
		} else if (GameScreen.hudMovimentos.getMovimentoAtual().equals("ESQUERDA")){
			dir--;
			if (dir < 0){
				dir = 3;
			}
			GameScreen.hudMovimentos.setMovimentoAtual("");
		}

		//rightwalking
		if(Math.abs(speed.x) > speedmax){
			speed.x = Math.signum(speed.x) * speedmax;
			status = State.walkingRIGHT;
		}

		//leftwalking
		if(Math.abs(-speed.x)>speedmax){
			speed.x = Math.signum(-speed.x) * speedmax;
			status = State.walkingLEFT;
		}

		if(Math.abs(speed.x) < 1){
			speed.x = 0;
		}

		if(Math.abs(-speed.x)<1){
			speed.x = 0;
		}

		//upwalking
		if(Math.abs(speed.y) > speedmax) {
			speed.y = Math.signum(speed.y) * speedmax;
			status = State.walkingUP;
		}

		if(Math.abs(speed.y) < 1) {
			speed.y = 0;
		}

		//downwalking
		if(Math.abs(-speed.y)>speedmax){
			speed.y = Math.signum(-speed.y) * speedmax;
			status = State.walkingDOWN;
		}

		if(Math.abs(-speed.y)<1){
			speed.y = 0;

		}

		if((speed.y == 0) && (speed.x == 0)){
			status = State.standby;
		}

		speed.scl(deltaTime);
		position.add(speed);
		speed.scl(1 / deltaTime);

		speed.x *= damping;
		speed.y *= damping;

	}

	public boolean verificaColisao(){

		playerRectangle = rectPool.obtain();
		playerRectangle.set(position.x, position.y, 30, 30);

		tiles = new Array<>();
		getTiles(tiles);
		if (GameScreen.hudMovimentos.getMovimentoAtual().equals("FRENTE")){

			if (dir==0){

				playerRectangle.y = countMenosY;
				for (Rectangle tile : tiles) {
					if (playerRectangle.overlaps(tile)){			
						speed.y = 0;
						speed.x = 0;
						status = State.standby;
						GameScreen.hudMovimentos.setMovimentoAtual("");
						return true;
					}
				}

				for (int i = 0; i < GameScreen.listaInimigos.size(); i++) {
					Inimigo inimigo = GameScreen.listaInimigos.get(i);
					Rectangle inimigoRectangle = new Rectangle(inimigo.position.x, inimigo.position.y, 32, 32);		
					if (playerRectangle.overlaps(inimigoRectangle)){	
						speed.y = 0;
						speed.x = 0;
						status = State.standby;
						GameScreen.hudMovimentos.setMovimentoAtual("");
						return true;
					}
				}	

				if (GameScreen.itens.verificaBlockedItem((int) playerRectangle.x, (int) playerRectangle.y, "")){
					speed.y = 0;
					speed.x = 0;
					status = State.standby;
					GameScreen.hudMovimentos.setMovimentoAtual("");
					return true;
				}

			}else if(dir==1){

				playerRectangle.x = countMenosX;
				for (Rectangle tile : tiles) {
					if (playerRectangle.overlaps(tile)){			
						speed.y = 0;
						speed.x = 0;
						status = State.standby;
						GameScreen.hudMovimentos.setMovimentoAtual("");
						return true;
					}
				}

				for (int i = 0; i < GameScreen.listaInimigos.size(); i++) {
					Inimigo inimigo = GameScreen.listaInimigos.get(i);
					Rectangle inimigoRectangle = new Rectangle(inimigo.position.x, inimigo.position.y, 32, 32);		
					if (playerRectangle.overlaps(inimigoRectangle)){	
						speed.y = 0;
						speed.x = 0;
						status = State.standby;
						GameScreen.hudMovimentos.setMovimentoAtual("");
						return true;
					}
				}

				if (GameScreen.itens.verificaBlockedItem((int) playerRectangle.x, (int) playerRectangle.y, "")){
					speed.y = 0;
					speed.x = 0;
					status = State.standby;
					GameScreen.hudMovimentos.setMovimentoAtual("");
					return true;
				}

			}else if(dir==2){

				playerRectangle.y = countMaisY;
				for (Rectangle tile : tiles) {
					if (playerRectangle.overlaps(tile)){			
						speed.y = 0;
						speed.x = 0;
						status = State.standby;
						GameScreen.hudMovimentos.setMovimentoAtual("");
						return true;
					}
				}

				for (int i = 0; i < GameScreen.listaInimigos.size(); i++) {
					Inimigo inimigo = GameScreen.listaInimigos.get(i);
					Rectangle inimigoRectangle = new Rectangle(inimigo.position.x, inimigo.position.y, 32, 32);		
					if (playerRectangle.overlaps(inimigoRectangle)){	
						speed.y = 0;
						speed.x = 0;
						status = State.standby;
						GameScreen.hudMovimentos.setMovimentoAtual("");
						return true;
					}
				}

				if (GameScreen.itens.verificaBlockedItem((int) playerRectangle.x, (int) playerRectangle.y, "")){
					speed.y = 0;
					speed.x = 0;
					status = State.standby;
					GameScreen.hudMovimentos.setMovimentoAtual("");
					return true;
				}

			}else if(dir==3){

				playerRectangle.x = countMaisX;
				for (Rectangle tile : tiles) {
					if (playerRectangle.overlaps(tile)){			
						speed.y = 0;
						speed.x = 0;
						status = State.standby;
						GameScreen.hudMovimentos.setMovimentoAtual("");
						return true;
					}
				}

				for (int i = 0; i < GameScreen.listaInimigos.size(); i++) {
					Inimigo inimigo = GameScreen.listaInimigos.get(i);
					Rectangle inimigoRectangle = new Rectangle(inimigo.position.x, inimigo.position.y, 32, 32);		
					if (playerRectangle.overlaps(inimigoRectangle)){	
						speed.y = 0;
						speed.x = 0;
						status = State.standby;
						GameScreen.hudMovimentos.setMovimentoAtual("");
						return true;
					}
				}

				if (GameScreen.itens.verificaBlockedItem((int) playerRectangle.x, (int) playerRectangle.y, "")){
					speed.y = 0;
					speed.x = 0;
					status = State.standby;
					GameScreen.hudMovimentos.setMovimentoAtual("");
					return true;
				}

			}
		}

		return false;
	}

	public void getTiles(Array<Rectangle> tiles) {
		try {
			int countLayers = GameScreen.map.getMap().getLayers().getCount();
			for (int i = 0; i < countLayers; i++) {
				MapLayer collisionObjectLayer = GameScreen.map.getMap().getLayers().get(i);
				MapObjects objects = collisionObjectLayer.getObjects();
				for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
					tiles.add(rectangleObject.getRectangle());
				}
				for (PolygonMapObject polygonObject : objects.getByType(PolygonMapObject.class)) {
					tiles.add(polygonObject.getPolygon().getBoundingRectangle());
				}
				for (EllipseMapObject ellipseObject : objects.getByType(EllipseMapObject.class)) {
					tiles.add(new Rectangle(ellipseObject.getEllipse().x, ellipseObject.getEllipse().y, ellipseObject.getEllipse().width, ellipseObject.getEllipse().height));
				}
				for (PolylineMapObject polylineObject : objects.getByType(PolylineMapObject.class)) {
					Polygon polygonObject = new Polygon(polylineObject.getPolyline().getVertices());
					float posX = polylineObject.getPolyline().getX();
					float posY = polylineObject.getPolyline().getY()-polygonObject.getBoundingRectangle().width;
					float width = polygonObject.getBoundingRectangle().width;
					float height = polygonObject.getBoundingRectangle().height;
					tiles.add(new Rectangle(posX, posY, width, height));
				}
			}
		} catch (Exception e) {	}
	}

	float timer = 0;
	public void DrawPlayer(SpriteBatch batch, float deltaTime) {	

		if (timer < 4)
			timer += .1f;
		else
			timer = 0;

		switch (status) {

		case walkingRIGHT:
			if (timer < 1) {
				mainChar = rightChar_STILL;
			} else if (timer > 1 && timer < 2) {
				mainChar = rightChar_LEFT;
			} else if (timer > 2 && timer < 3) {
				mainChar = rightChar_STILL;
			} else if (timer > 3) {
				mainChar = rightChar_RIGHT;
			}
			break;
		case walkingLEFT:
			if (timer < 1) {
				mainChar = leftChar_STILL;
			} else if (timer > 1 && timer < 2) {
				mainChar = leftChar_LEFT;
			} else if (timer > 2 && timer < 3) {
				mainChar = leftChar_STILL;
			} else if (timer > 3) {
				mainChar = leftChar_RIGHT;
			}
			break;
		case walkingUP:
			if (timer < 1) {
				mainChar = upChar_STILL;
			} else if (timer > 1 && timer < 2) {
				mainChar = upChar_LEFT;
			} else if (timer > 2 && timer < 3) {
				mainChar = upChar_STILL;
			} else if (timer > 3) {
				mainChar = upChar_RIGHT;
			}
			break;
		case walkingDOWN:
			if (timer < 1) {
				mainChar = downChar_STILL;
			} else if (timer > 1 && timer < 2) {
				mainChar = downChar_LEFT;
			} else if (timer > 2 && timer < 3) {
				mainChar = downChar_STILL;
			} else if (timer > 3) {
				mainChar = downChar_RIGHT;
			}
			break;
		case standby:
			if (dir==0){
				mainChar = downChar_STILL;
			}else if(dir==1){
				mainChar = leftChar_STILL;
			}else if(dir==2){
				mainChar = upChar_STILL;
			}else if(dir==3){
				mainChar = rightChar_STILL;
			}
			break;
		default:
			break;
		}

		checkPlayerModify();

		batch.begin();	
		batch.flush();
		batch.draw(mainChar, position.x, position.y, playerWIDTH, playerHEIGHT);
		batch.end();

	}

	public void LoadPlayerTexture() {

		Texture character = new Texture(Gdx.files.internal("characters/player.png"));

		// character animations
		downChar_STILL = new TextureRegion(character, 32, 0, 32, 48);
		downChar_LEFT = new TextureRegion(character, 0, 0, 32, 48);
		downChar_RIGHT = new TextureRegion(character, 64, 0, 32, 48);

		leftChar_STILL = new TextureRegion(character, 32, 48, 32, 48);
		leftChar_LEFT = new TextureRegion(character, 0, 48, 32, 48);
		leftChar_RIGHT = new TextureRegion(character, 64, 48, 32, 48);

		rightChar_STILL = new TextureRegion(character, 32, 96, 32, 48);
		rightChar_LEFT = new TextureRegion(character, 0, 96, 32, 48);
		rightChar_RIGHT = new TextureRegion(character, 64, 96, 32, 48);

		upChar_STILL = new TextureRegion(character, 32, 144, 32, 48);
		upChar_LEFT = new TextureRegion(character, 0, 144, 32, 48);
		upChar_RIGHT = new TextureRegion(character, 64, 144, 32, 48);

		mainChar = downChar_STILL;

		isDead = false;

		msgsAnimadas = new ArrayList<>();

		Properties propertiesFase = LoadScreen.getMapConfig(LoadScreen.faseAtual);
		if (propertiesFase.getProperty("playerSaveLoadDb").equals("true")){	
			level = GameScreen.statusUsuario.getLvl(); // level atual do personagem

			xpTotal = Integer.parseInt(Assets.config.getProperty(level+"")); // experiencia total do personagem
			xpAtual = GameScreen.statusUsuario.getXp(); // experiencia total do personagem

			hpTotal = GameScreen.statusUsuario.getHp(); // life total do personagem
			hpAtual = GameScreen.statusUsuario.getHp(); // life atual do personagem

			spTotal = GameScreen.statusUsuario.getSp(); // stamina total do personagem
			spAtual = GameScreen.statusUsuario.getSp(); // stamina total do personagem

			forca = GameScreen.statusUsuario.getAtk(); // força atual do personagem

			defesa = GameScreen.statusUsuario.getDef(); // defesa atual do personagem
		} else {
			level = Integer.parseInt(propertiesFase.getProperty("levelFase"));

			xpTotal = Integer.parseInt(Assets.config.getProperty(level+"")); 
			xpAtual = Integer.parseInt(propertiesFase.getProperty("expFase"));

			hpTotal = Integer.parseInt(propertiesFase.getProperty("lifeFase"));
			hpAtual = Integer.parseInt(propertiesFase.getProperty("lifeFase"));

			spTotal = Integer.parseInt(propertiesFase.getProperty("staminaFase"));
			spAtual = Integer.parseInt(propertiesFase.getProperty("staminaFase"));

			forca = Integer.parseInt(propertiesFase.getProperty("forcaFase"));

			defesa = Integer.parseInt(propertiesFase.getProperty("defesaFase"));
		}

	}

	// só checar se na fase estiver playerSaveLoadDb = true
	public void checkPlayerModify(){
		
		if (Player.xpAtual > GameScreen.statusUsuario.getXp()
				
				|| Player.hpTotal > GameScreen.statusUsuario.getHp()
				|| Player.spTotal > GameScreen.statusUsuario.getSp()
				|| Player.forca > GameScreen.statusUsuario.getAtk()
				|| Player.defesa > GameScreen.statusUsuario.getDef()){

			if (Player.xpAtual>= Integer.parseInt(Assets.config.getProperty((Player.level+"")))){	

				Player.level = Player.level + 1;
				Player.hpTotal = Player.hpTotal + Integer.parseInt(Assets.config.getProperty("life"));
				Player.spTotal = Player.spTotal + Integer.parseInt(Assets.config.getProperty("stamina"));
				Player.forca = Player.forca + Integer.parseInt(Assets.config.getProperty("forca"));
				Player.defesa = Player.defesa + Integer.parseInt(Assets.config.getProperty("defesa"));
				animatedPlayerMessage("LEVEL UP!");

				Properties propertiesFase = LoadScreen.getMapConfig(LoadScreen.faseAtual);
				if (propertiesFase.getProperty("playerSaveLoadDb").equals("true")){		
					GameScreen.playerMovimentos.gain();	
				}

				xpTotal = Integer.parseInt(Assets.config.getProperty(level+""));
				hpAtual = hpTotal;
				spAtual = spTotal; 

			}

			GameScreen.statusUsuario.atualizaStatus();

		}
	}

	public void setCountMovimento(float a, float b, float c, float d) {
		this.countMaisX = a;
		this.countMenosX = b;
		this.countMaisY = c;
		this.countMenosY = d;
	}

	public static void animatedPlayerMessage(String mensagem){
		animatedHit(mensagem, Assets.labelStyleAddLife);
	}

	public static void addSp(int stamina){
		if (stamina > 0){
			if (spAtual<spTotal){
				spAtual = spAtual + stamina;
				if (spAtual>spTotal){
					spAtual = spTotal;
				}	
			}
		} else {
			spAtual = spAtual + stamina;

			if (spAtual<0){
				spAtual = 0;
			}	
		}
	}

	public static void addAtk(int atk){
		if (atk > 0){
			forca = forca + atk;
		} else if (atk < 0){
			if (forca + atk <= 0){
				forca = 0;
			} else {
				forca = forca + atk;
			}
		}	
	}

	public static void addDef(int def){
		if (def > 0){
			defesa = defesa + def;
		} else if (def < 0){
			if (defesa + def <= 0){
				defesa = 0;
			} else {
				defesa = defesa + def;
			}
		}
	}

	public static void addXp(int xp){
		if (xp > 0){
			xpAtual = xpAtual + xp;
		} else if (xp < 0){
			if (xpAtual + xp <= 0){
				xpAtual = 0;
			} else {
				xpAtual = xpAtual + xp;
			}
		}	
		HUDMovimentos.animatedMessage("Voce recebeu +"+xp+" de XP.", 5);
	}

	public static void removeLife(int life){
		int hit = life;
		if (hpAtual>0){
			if (hit>hpAtual){
				animatedHit("-"+ (int) hpAtual, Assets.labelStyleRemoveLife);
			} else {
				animatedHit("-"+hit, Assets.labelStyleRemoveLife);
			}

			hpAtual = hpAtual - (hit);

			if (hpAtual<0){
				hpAtual = 0;
			}	
		}
	}

	public static void addLife(int life){
		int hit = life;
		if (hpAtual<hpTotal){
			if (hit+hpAtual>hpTotal){
				animatedHit("+"+ ((int) hpTotal- (int) hpAtual), Assets.labelStyleAddLife);
			} else {
				animatedHit("+"+hit, Assets.labelStyleAddLife);
			}

			hpAtual = hpAtual + (hit);

			if (hpAtual>hpAtual){
				hpAtual = hpTotal;
			}	
		}
	}

	public static boolean movimentacaoExcessiva(){
		if (spAtual<=0){
			HUDMovimentos.animatedMessage("Voce esta cansado demais para executar outros movimentos!", 5);
			return true;
		}
		return false;		
	}

	public static void animatedHit(String msg, LabelStyle lblStyle){
		Label animatedMsg = new Label(msg, lblStyle);
		animatedStageMessage(animatedMsg);	
	}

	public static void animatedStageMessage(final Label animatedMsg){
		new Thread() {
			@Override 
			public void run() { 
				float x = GameScreen.player.position.x+(new Random().nextInt(20)-10);
				float y = GameScreen.player.position.y;
				GameScreen.getStage().addActor(animatedMsg);
				try {				
					float inicialY = y+25;
					for (int j = 0; j < 10; j++) {
						for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
							Actor msgAnimada = GameScreen.getStage().getActors().get(i);
							if (msgAnimada.equals(animatedMsg)){
								msgAnimada.setPosition(x, inicialY);
								inicialY++;
							}
						}
						Thread.sleep(75);
					}				

				} catch (InterruptedException e) {}
				for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
					Actor msgAnimada = GameScreen.getStage().getActors().get(i);
					if (msgAnimada.equals(animatedMsg)){
						msgAnimada.setVisible(false);
					}
				}
			} 
		}.start();
	}

	public static void removeStage(final String animationName, final float x, final float y){
		new Thread() {
			@Override 
			public void run() { 
				try {				
					float inicialY = y+25;
					for (int j = 0; j < 10; j++) {

						for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
							if (GameScreen.getStage().getActors().get(i).toString().equals(animationName)){
								GameScreen.getStage().getActors().get(i).setPosition(x, inicialY);
								inicialY++;
							}
						}

						Thread.sleep(75);
					}				

				} catch (InterruptedException e) {}
				for (Actor actor : GameScreen.getStage().getActors()) {
					if (actor.toString().equals(animationName)){
						actor.setVisible(false);
					}
				}
				for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
					if (GameScreen.getStage().getActors().get(i).toString().equals(animationName)){
						GameScreen.getStage().getActors().removeIndex(i);
					}
				}
			} 
		}.start();
	}

}