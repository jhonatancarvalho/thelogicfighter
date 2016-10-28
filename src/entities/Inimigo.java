package entities;

import java.util.ArrayList;
import java.util.Random;

import main.Assets;
import screens.GameScreen;
import astar.AStar;
import astar.Quadrado;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Inimigo {

	public TextureRegion downChar_STILL;
	public TextureRegion downChar_LEFT;
	public TextureRegion downChar_RIGHT;
	public TextureRegion leftChar_STILL;
	public 	TextureRegion leftChar_LEFT;
	public TextureRegion leftChar_RIGHT;
	public TextureRegion rightChar_STILL;
	public TextureRegion rightChar_LEFT;
	public TextureRegion rightChar_RIGHT;
	public TextureRegion upChar_STILL;
	public TextureRegion upChar_LEFT;
	public TextureRegion upChar_RIGHT;

	public TextureRegion mainChar;

	public ShapeRenderer shapeRenderer;

	// status do personagem
	public enum State
	{
		standby,
		walkingDOWN,
		walkingUP,
		walkingLEFT,
		walkingRIGHT,
	}

	public String passoAtual;
	public boolean passoTerminated;
	public boolean moved;

	public String nome;

	// left = esquerda
	// 0 = down; 1 = left; 2 = up; 3 = right
	public int dir = 0;

	public int experiencia;

	public float hpTotal;
	public float hpAtual;

	public float spTotal;
	public float spAtual;

	public float xpTotal;
	public float xpAtual;

	public int level;

	public int forca;
	public int defesa;

	public float WIDTH = 32; // largura tile
	public float HEIGHT = 32; // altura tile

	public float playerWIDTH = 32; // largura personagem
	public float playerHEIGHT = 48; // altura personagem


	// indicador se a movimentação 
	public float countMaisX = 0;
	public float countMenosX = 0;
	public float countMaisY = 0;
	public float countMenosY = 0;

	public float speedmax = 30f; // velocidade de movimento

	//static float damping = 0.87f; 
	public float damping = 0f; // delay de curva

	// posição inicial do jogador no mapa
	public Vector2 position = new Vector2(32, 545);

	public Vector2 speed = new Vector2();

	public State status = State.standby;

	public OrthogonalTiledMapRenderer map;

	private Pool<Rectangle> rectPool = new Pool<Rectangle>(){
		@Override
		protected Rectangle newObject(){
			return new Rectangle();
		}
	};

	private Array<Rectangle> tiles = new Array<Rectangle>();

	Rectangle playerRectangle;

	public Inimigo(){}

	public Inimigo(OrthogonalTiledMapRenderer map, int posX, int posY, int dir, String nome, int experiencia, int hp, int sp, int atk, int def) {
		this.map = map;
		this.position.x = posX;
		this.position.y = posY;
		this.dir = dir;
		this.nome = nome;
		this.experiencia = experiencia;
		this.hpTotal = hp;
		this.hpAtual = hp;
		this.spTotal = sp;
		this.spAtual = sp;
		this.forca = atk;
		this.defesa = def;
	}

	public void UpdatePlayer(float deltaTime) {

		if (deltaTime == 0)
			return;

		if (GameScreen.rodada == 1 && isPassoTerminated() && !isMoved()){
			setMoved(true);
			setPassoTerminated(false);
			executaMovimentos();
		}

		// movimentação pelo menu de movimentos
		if (getPassoAtual().equals("FRENTE")){
			if (!verificaColisao()){
				if (dir==0){
					if (countMenosY<=position.y){
						status = State.walkingDOWN;
						speed.y = -speedmax;
					} else {
						position.y = countMenosY;
						setPassoAtual("");
					}

				}else if(dir==1){
					if (countMenosX<=position.x){
						status = State.walkingLEFT;
						speed.x = -speedmax;
					} else {
						position.x = countMenosX;
						setPassoAtual("");
					}

				}else if(dir==2){
					if (countMaisY>=position.y){
						status = State.walkingUP;
						speed.y += speedmax;
					} else {
						position.y = countMaisY;
						setPassoAtual("");
					}	

				}else if(dir==3){
					if (countMaisX>=position.x){
						status = State.walkingRIGHT;
						speed.x += speedmax;
					} else {
						position.x = countMaisX;
						setPassoAtual("");
					}

				}
			}
		} else if (getPassoAtual().equals("DIREITA")){
			dir++;
			if (dir > 3){
				dir = 0;
			}
			setPassoAtual("");
		} else if (getPassoAtual().equals("ESQUERDA")){
			dir--;
			if (dir < 0){
				dir = 3;
			}
			setPassoAtual("");
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
		playerRectangle.set(position.x, position.y, WIDTH, HEIGHT);
		tiles = new Array<>();
		getTiles(tiles);
		if (getPassoAtual().equals("FRENTE")){
			if (dir==0){
				playerRectangle.y = countMenosY;
				for (Rectangle tile : tiles) {
					if (playerRectangle.overlaps(tile)){			
						speed.y = 0;
						speed.x = 0;
						status = State.standby;

						return true;
					}
				}
			}else if(dir==1){
				playerRectangle.x = countMenosX;
				for (Rectangle tile : tiles) {
					if (playerRectangle.overlaps(tile)){			
						speed.y = 0;
						speed.x = 0;
						status = State.standby;

						return true;
					}
				}
			}else if(dir==2){
				playerRectangle.y = countMaisY;
				for (Rectangle tile : tiles) {
					if (playerRectangle.overlaps(tile)){			
						speed.y = 0;
						speed.x = 0;
						status = State.standby;

						return true;
					}
				}
			}else if(dir==3){
				playerRectangle.x = countMaisX;
				for (Rectangle tile : tiles) {
					if (playerRectangle.overlaps(tile)){			
						speed.y = 0;
						speed.x = 0;
						status = State.standby;

						return true;
					}
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

		healthBar(shapeRenderer, mainChar);

		inimigoAttack(map.getSpriteBatch());
		
		map.getSpriteBatch().begin();	
		map.getSpriteBatch().flush();
		map.getSpriteBatch().draw(mainChar, position.x, position.y, playerWIDTH, playerHEIGHT);	
		map.getSpriteBatch().end();

	}

	public void LoadPlayerTexture() {

		Texture character = new Texture(Gdx.files.internal("characters/"+nome+".png"));

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

		shapeRenderer = new ShapeRenderer();

		setPassoAtual("");
		setPassoTerminated(true);
		setMoved(false);

		loadAttack();

	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public String getPassoAtual() {
		return passoAtual;
	}

	public void setPassoAtual(String passoAtual) {
		this.passoAtual = passoAtual;
	}

	public boolean isPassoTerminated() {
		return passoTerminated;
	}

	public void setPassoTerminated(boolean passoTerminated) {
		this.passoTerminated = passoTerminated;
	}

	public void setCountMovimento(float a, float b, float c, float d) {
		this.countMaisX = a;
		this.countMenosX = b;
		this.countMaisY = c;
		this.countMenosY = d;
	}

	float stateTime = 0;
	boolean checkShowHit = true;
	public void inimigoAttack(Batch batch){
		try {

			if (attackAnimation){
				float inimigoPosX = position.x;
				float inimigoPosY = position.y;
				int dirInimigo = dir;
				if (dirInimigo == 0){
					inimigoPosY = inimigoPosY - 32;
				} else if (dirInimigo == 1){
					inimigoPosX = inimigoPosX - 32;
				} else if (dirInimigo == 2){
					inimigoPosY = inimigoPosY + 32;
				} else if (dirInimigo == 3){
					inimigoPosX = inimigoPosX + 32;
				}

				stateTime += Gdx.graphics.getDeltaTime(); 

				if (animatedAttack.isAnimationFinished(stateTime)){
					stateTime = 0;
					attackAnimation = false;	
					checkShowHit = true;
				} else {	

					batch.begin();
					batch.draw(animatedAttack.getKeyFrame(stateTime), inimigoPosX, inimigoPosY);
					batch.end();

					if (checkShowHit){
						int hit = forca+new Random().nextInt(5)-Player.defesa;
						if (hit<=0){
							hit = 0;
						}
						Player.removeLife(hit);
						if (Player.hpAtual<=0){
							new GameOver();
						}
						checkShowHit = false;
					}

				}

			}
		} catch (Exception e) {	e.printStackTrace(); }
	}

	public void healthBar(ShapeRenderer shapeRenderer, TextureRegion mainChar) {
		if (hpAtual>0) {

			shapeRenderer.begin(ShapeType.Filled);

			shapeRenderer.setColor(Color.GRAY);
			shapeRenderer.rect(position.x-1, position.y + mainChar.getRegionHeight() + 0, 40, 4);

			shapeRenderer.setColor(245, 0, 0, 1);
			int percentHp = (int) (((100*(int) hpAtual) / (int) hpTotal) * 0.4);
			if (percentHp == 0) {
				percentHp = 1;
			}
			shapeRenderer.rect(position.x-1, position.y + mainChar.getRegionHeight() + 0, percentHp, 4);

			shapeRenderer.setColor(Color.GRAY);
			shapeRenderer.rect(position.x-1, position.y + mainChar.getRegionHeight() - 5 + 0, 40, 4);

			shapeRenderer.setColor(0, 184, 213, 1);
			shapeRenderer.rect(position.x-1, position.y + mainChar.getRegionHeight() - 5, (int) (((100*(int) spAtual) / (int) spTotal) * 0.4), 4);

			shapeRenderer.end();
		}
	}

	public void addSp(int stamina){
		if (stamina > 0){
			if (spAtual<spTotal){
				spAtual = spAtual + stamina;
				if (spAtual>spAtual){
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

	public void addAtk(int atk){
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

	public void addDef(int def){
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

	public void addXp(int xp){
		if (xp > 0){
			xpAtual = xpAtual + xp;
		} else if (xp < 0){
			if (xpAtual + xp <= 0){
				xpAtual = 0;
			} else {
				xpAtual = xpAtual + xp;
			}
		}	
	}

	public void removeLife(int life){
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

	public void addLife(int life){
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

	public void animatedHit(String msg, LabelStyle lblStyle){
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
			if (GameScreen.getStage().getActors().get(i).toString().startsWith("hit")){
				GameScreen.getStage().getActors().removeIndex(i);
			}
		}
		Label animatedMsg = new Label(msg, lblStyle);
		float playerX = position.x;
		float playerY = position.y;
		animatedMsg.setPosition(playerX, playerY);
		String animationName = "hit"+new Random().nextInt(100);
		animatedMsg.setName(animationName);
		GameScreen.getStage().addActor(animatedMsg);
		int xPos = (int) playerX + new Random().nextInt(10)-5;
		removeStage(animationName, xPos, playerY);			
	}

	public void removeStage(final String animationName, final float x, final float y){
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
					for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
						if (GameScreen.getStage().getActors().get(i).toString().startsWith("hit")){
							GameScreen.getStage().getActors().removeIndex(i);
						}
					}

				} catch (InterruptedException e) {}
			} 
		}.start();
	}

	public void executaMovimentos(){
		// calcular passos baseado na stamina total do inimigo para chegar perto do player e poder atacar	
		new Thread() {
			@Override 
			public void run() { 
				try {

					while ((int) spAtual > 0 && !Player.isDead) {

						int posPlayerX = (int) (GameScreen.player.position.x/32);
						int posPlayerY = (int) (GameScreen.player.position.y/32);

						int posInimigoX = (int) position.x/32;			
						int posInimigoY = (int) position.y/32;

						Quadrado[][] grade = new Quadrado[24][20];
						for (int i = 0; i < grade.length; i++) {
							for (int j = 0; j < grade[i].length; j++) {
								grade[i][j] = new Quadrado(i, j);
							}
						}

						//configura grade 
						Quadrado origem = grade[posInimigoX][posInimigoY];
						Quadrado destino = grade[posPlayerX][posPlayerY];

						AStar aStar = new AStar(grade, origem, destino);

						ArrayList<Quadrado> listaBloqueio = getBloqueio();
						for (Quadrado quadrado : listaBloqueio) {
							aStar.addBloqueio(quadrado);
						}

						for (int i = 0; i < 24; i++) {
							for (int j = 0; j < 20; j++) {
								if (GameScreen.itens.verificaBlockedItem(i*32, j*32, "")){
									aStar.addBloqueio(new Quadrado(i, j));
								}
							}
						}

						boolean pesquisaOk = aStar.iniciarPesquisa();

						ArrayList<String> caminhos = new ArrayList<>();
						if (pesquisaOk) {
							//lista caminho encontrado
							for (int i = 0; i < aStar.getListaCaminho().size(); i++) {
								caminhos.add(aStar.getListaCaminho().get(i).getX()+";"+aStar.getListaCaminho().get(i).getY());
							}
						} else {
							//System.out.println("Caminho nao foi encontrado.");
						}

						String[] posLocomove = caminhos.get(caminhos.size()-2).split(";");

						// 0 = down; 1 = left; 2 = up; 3 = right
						if (getColisionPlayer()){
							spAtual = spAtual - 1;	
							attack();
							Thread.sleep(1300);
							////System.out.println("ATACAR!!!");
						} else if (Integer.parseInt(posLocomove[0]) > posInimigoX){
							// ficar pra >> e andar
							if (dir == 0) {
								spAtual = spAtual - 1;
								setPassoAtual("ESQUERDA");				
								Thread.sleep(500);
							} else if (dir == 1){
								spAtual = spAtual - 1;
								setPassoAtual("DIREITA");				
								Thread.sleep(500);
							} else if (dir == 2){
								spAtual = spAtual - 1;
								setPassoAtual("DIREITA");				
								Thread.sleep(500);
							} else if (dir == 3){
								spAtual = spAtual - 1;
								setCountMovimento(position.x+32.0f, position.x-32.0f, position.y+32.0f, position.y-32.0f);
								setPassoAtual("FRENTE");	
								Thread.sleep(1300);
							}
						} else if (Integer.parseInt(posLocomove[0]) < posInimigoX){
							// ficar pra << e andar
							if (dir == 0) {
								spAtual = spAtual - 1;
								setPassoAtual("DIREITA");				
								Thread.sleep(500);
							} else if (dir == 1){
								spAtual = spAtual - 1;
								setCountMovimento(position.x+32.0f, position.x-32.0f, position.y+32.0f, position.y-32.0f);
								setPassoAtual("FRENTE");	
								Thread.sleep(1300);
							} else if (dir == 2){
								spAtual = spAtual - 1;
								setPassoAtual("ESQUERDA");				
								Thread.sleep(500);
							} else if (dir == 3){
								spAtual = spAtual - 1;
								setPassoAtual("ESQUERDA");				
								Thread.sleep(500);
							}
						} else if (Integer.parseInt(posLocomove[1]) > posInimigoY){
							// ficar pra cima e andar
							if (dir == 0) {
								spAtual = spAtual - 1;
								setPassoAtual("DIREITA");				
								Thread.sleep(500);
							} else if (dir == 1){
								spAtual = spAtual - 1;
								setPassoAtual("DIREITA");				
								Thread.sleep(500);
							} else if (dir == 2){
								spAtual = spAtual - 1;
								setCountMovimento(position.x+32.0f, position.x-32.0f, position.y+32.0f, position.y-32.0f);
								setPassoAtual("FRENTE");	
								Thread.sleep(1300);
							} else if (dir == 3){
								spAtual = spAtual - 1;
								setPassoAtual("ESQUERDA");				
								Thread.sleep(500);
							}
						} else if (Integer.parseInt(posLocomove[1]) < posInimigoY){
							// 0 = down; 1 = left; 2 = up; 3 = right
							// ficar pra baixo e andar
							if (dir == 0) {
								spAtual = spAtual - 1;
								setCountMovimento(position.x+32.0f, position.x-32.0f, position.y+32.0f, position.y-32.0f);
								setPassoAtual("FRENTE");	
								Thread.sleep(1300);							
							} else if (dir == 1){
								spAtual = spAtual - 1;
								setPassoAtual("ESQUERDA");				
								Thread.sleep(500);
							} else if (dir == 2){
								spAtual = spAtual - 1;
								setPassoAtual("ESQUERDA");				
								Thread.sleep(500);
							} else if (dir == 3){
								spAtual = spAtual - 1;
								setPassoAtual("DIREITA");				
								Thread.sleep(500);
							}
						}

					}
				} catch (Exception e) {}		
				
				setPassoTerminated(true);
				if (!Player.isDead){
					spAtual = spTotal;
				}
				if (!isMovingInimigo()){
					GameScreen.rodada = 0;
					setMovedFalseInInimigos();
				}
			} 
		}.start();


	}

	public static boolean isMovingInimigo(){
		for (int i = 0; i < GameScreen.listaInimigos.size(); i++) {
			if (!GameScreen.listaInimigos.get(i).isPassoTerminated()){
				return true;
			}
		}
		return false;
	}

	public static void setMovedFalseInInimigos(){
		for (int i = 0; i < GameScreen.listaInimigos.size(); i++) {
			GameScreen.listaInimigos.get(i).setMoved(false);
		}
	}

	public boolean getColisionPlayer(){

		Rectangle playerRectangle = new Rectangle(GameScreen.player.position.x, GameScreen.player.position.y, 30, 30);
		Rectangle inimigoRectangle = new Rectangle(position.x, position.y, 30, 30);

		if (dir==0){
			inimigoRectangle.y = position.y-32;		
			if (inimigoRectangle.overlaps(playerRectangle)){			
				return true;
			}
		}else if(dir==1){
			inimigoRectangle.x = position.x-32;
			if (inimigoRectangle.overlaps(playerRectangle)){			
				return true;
			}
		}else if(dir==2){
			inimigoRectangle.y = position.y+32;
			if (inimigoRectangle.overlaps(playerRectangle)){			
				return true;
			}
		}else if(dir==3){
			inimigoRectangle.x = position.x+32;
			if (inimigoRectangle.overlaps(playerRectangle)){			
				return true;
			}
		}
		return false;
	}

	public boolean getColision(){

		Array<Rectangle> tiles = new Array<Rectangle>();
		Rectangle playerRectangle = new Rectangle(position.x, position.y, 30, 30);

		for (int i = 0; i < GameScreen.listaInimigos.size(); i++) {
			if (!GameScreen.listaInimigos.get(i).equals(this)){
				Inimigo inimigo = GameScreen.listaInimigos.get(i);
				Rectangle inimigoRectangle = new Rectangle(inimigo.position.x, inimigo.position.y, 32, 32);
				if (dir==0){
					playerRectangle.y = position.y-32;		
					if (playerRectangle.overlaps(inimigoRectangle)){	
						return true;
					}
				}else if(dir==1){
					playerRectangle.x = position.x-32;
					if (playerRectangle.overlaps(inimigoRectangle)){		
						return true;
					}
				}else if(dir==2){
					playerRectangle.y = position.y+32;
					if (playerRectangle.overlaps(inimigoRectangle)){	
						return true;
					}
				}else if(dir==3){
					playerRectangle.x = position.x+32;
					if (playerRectangle.overlaps(inimigoRectangle)){
						return true;
					}
				}
			}
		}

		getTiles(tiles); // prenche o arraylist de Rectangle com todos os retangles de colisão
		if (dir==0){
			playerRectangle.y = position.y-32;		
			for (Rectangle tile : tiles) {
				if (playerRectangle.overlaps(tile)){			
					return true;
				}
			}
		}else if(dir==1){
			playerRectangle.x = position.x-32;
			for (Rectangle tile : tiles) {
				if (playerRectangle.overlaps(tile)){			
					return true;
				}
			}
		}else if(dir==2){
			playerRectangle.y = position.y+32;
			for (Rectangle tile : tiles) {
				if (playerRectangle.overlaps(tile)){			
					return true;
				}
			}
		}else if(dir==3){
			playerRectangle.x = position.x+32;
			for (Rectangle tile : tiles) {
				if (playerRectangle.overlaps(tile)){			
					return true;
				}
			}
		}

		return false;
	}

	public ArrayList<Quadrado> getBloqueio(){
		ArrayList<Quadrado> listaBloqueio = new ArrayList<>();
		for (Inimigo inimigo : GameScreen.listaInimigos) {
			if (!inimigo.equals(this)){
				listaBloqueio.add(new Quadrado((int) inimigo.position.x/32, (int) inimigo.position.y/32));
			}
		}
		Array<Rectangle> tiles = new Array<Rectangle>();
		getTiles(tiles);
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 24; j++) {
				Rectangle retanguloPosicao = new Rectangle(i*32, j*32, 32, 32);
				for (Rectangle tile : tiles) {
					if (retanguloPosicao.overlaps(tile)){			
						listaBloqueio.add(new Quadrado(i, j));
					}
				}
			}
		}

		return listaBloqueio;
	}


	private boolean attackAnimation;
	private Texture movimentTexture;
	private TextureRegion swordefx1;
	private TextureRegion swordefx2;
	private TextureRegion swordefx3;
	private TextureRegion swordefx4;
	private TextureRegion swordefx5;
	private TextureRegion swordefx6;
	private TextureRegion swordefx7;
	private Animation animatedAttack;

	public void loadAttack(){

		attackAnimation = false;

		movimentTexture = new Texture(Gdx.files.internal("animations/sword.png"));

		swordefx1 = new TextureRegion(movimentTexture, 0, 0, 32, 32);
		swordefx2 = new TextureRegion(movimentTexture, 32, 0, 32, 32);
		swordefx3 = new TextureRegion(movimentTexture, 64, 0, 32, 32);
		swordefx4 = new TextureRegion(movimentTexture, 96, 0, 32, 32);
		swordefx5 = new TextureRegion(movimentTexture, 0, 32, 32, 32);
		swordefx6 = new TextureRegion(movimentTexture, 32, 32, 32, 32);
		swordefx7 = new TextureRegion(movimentTexture, 64, 32, 32, 32);
		//swordefx8 = new TextureRegion(movimentTexture, 96, 32, 32, 32);

		TextureRegion[] framesSword = new TextureRegion[7];
		framesSword[0] = swordefx1;
		framesSword[1] = swordefx2;
		framesSword[2] = swordefx3;
		framesSword[3] = swordefx4;
		framesSword[4] = swordefx5;
		framesSword[5] = swordefx6;
		framesSword[6] = swordefx7;
		//framesSword[7] = swordefx8;

		animatedAttack = new Animation(0.09f, framesSword);
		animatedAttack.setPlayMode(PlayMode.LOOP);


	}

	public void attack(){
		attackAnimation = true;
	}


}