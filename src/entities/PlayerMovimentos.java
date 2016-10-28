package entities;

import java.util.Random;

import screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class PlayerMovimentos {

	private boolean attackAnimation;
	private boolean handAnimation;
	boolean shieldAnimation;
	private boolean healAnimation;

	private Texture movimentTexture;

	private TextureRegion swordefx1;
	private TextureRegion swordefx2;
	private TextureRegion swordefx3;
	private TextureRegion swordefx4;
	private TextureRegion swordefx5;
	private TextureRegion swordefx6;
	private TextureRegion swordefx7;
	//private TextureRegion swordefx8;

	private TextureRegion handefx1;
	private TextureRegion handefx2;
	private TextureRegion handefx3;
	private TextureRegion handefx4;
	private TextureRegion handefx5;

	private TextureRegion shieldefx1;
	private TextureRegion shieldefx2;
	private TextureRegion shieldefx3;
	private TextureRegion shieldefx4;
	private TextureRegion shieldefx5;
	private TextureRegion shieldefx6;
	private TextureRegion shieldefx7;
	private TextureRegion shieldefx8;

	private TextureRegion healfx1;
	private TextureRegion healfx2;
	private TextureRegion healfx3;
	private TextureRegion healfx4;
	private TextureRegion healfx5;
	private TextureRegion healfx6;
	private TextureRegion healfx7;
	private TextureRegion healfx8;
	private TextureRegion healfx9;

	private Animation animatedAttack;
	private Animation animatedHand;
	private Animation animatedShield;
	private Animation animatedHeal;

	public void LoadPlayerMovimentos() {

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

		animatedAttack = new Animation(0.02f, framesSword);
		animatedAttack.setPlayMode(PlayMode.LOOP);

		handAnimation = false;

		movimentTexture = new Texture(Gdx.files.internal("animations/hand.png"));

		handefx1 = new TextureRegion(movimentTexture, 0, 0, 32, 24);
		handefx2 = new TextureRegion(movimentTexture, 32, 0, 32, 24);
		handefx3 = new TextureRegion(movimentTexture, 64, 0, 32, 24);
		handefx4 = new TextureRegion(movimentTexture, 96, 0, 32, 24);
		handefx5 = new TextureRegion(movimentTexture, 128, 0, 32, 24);

		TextureRegion[] framesHand = new TextureRegion[5];
		framesHand[0] = handefx1;
		framesHand[1] = handefx2;
		framesHand[2] = handefx3;
		framesHand[3] = handefx4;
		framesHand[4] = handefx5;

		animatedHand = new Animation(1f, framesHand);
		animatedHand.setPlayMode(PlayMode.LOOP);

		shieldAnimation = false;

		movimentTexture = new Texture(Gdx.files.internal("animations/shield.png"));

		shieldefx1 = new TextureRegion(movimentTexture, 0, 0, 34, 50);
		shieldefx2 = new TextureRegion(movimentTexture, 34, 0, 34, 50);
		shieldefx3 = new TextureRegion(movimentTexture, 68, 0, 34, 50);
		shieldefx4 = new TextureRegion(movimentTexture, 136, 0, 34, 50);
		shieldefx5 = new TextureRegion(movimentTexture, 0, 50, 34, 50);
		shieldefx6 = new TextureRegion(movimentTexture, 34, 50, 34, 50);
		shieldefx7 = new TextureRegion(movimentTexture, 68, 50, 34, 50);
		shieldefx8 = new TextureRegion(movimentTexture, 136, 50, 34, 50);

		TextureRegion[] framesShield = new TextureRegion[8];
		framesShield[0] = shieldefx1;
		framesShield[1] = shieldefx2;
		framesShield[2] = shieldefx3;
		framesShield[3] = shieldefx4;
		framesShield[4] = shieldefx5;
		framesShield[5] = shieldefx6;
		framesShield[6] = shieldefx7;
		framesShield[7] = shieldefx8;

		animatedShield = new Animation(0.09f, framesShield);
		animatedShield.setPlayMode(PlayMode.LOOP);

		healAnimation = false;

		movimentTexture = new Texture(Gdx.files.internal("animations/heal.png"));

		healfx1 = new TextureRegion(movimentTexture, 0, 0, 34, 50);
		healfx2 = new TextureRegion(movimentTexture, 34, 0, 34, 50);
		healfx3 = new TextureRegion(movimentTexture, 68, 0, 34, 50);
		healfx4 = new TextureRegion(movimentTexture, 0, 50, 34, 50);
		healfx5 = new TextureRegion(movimentTexture, 34, 50, 34, 50);
		healfx6 = new TextureRegion(movimentTexture, 68, 50, 34, 50);
		healfx7 = new TextureRegion(movimentTexture, 0, 100, 34, 50);
		healfx8 = new TextureRegion(movimentTexture, 34, 100, 34, 50);
		healfx9 = new TextureRegion(movimentTexture, 68, 100, 34, 50);

		TextureRegion[] framesHeal = new TextureRegion[9];
		framesHeal[0] = healfx1;
		framesHeal[1] = healfx2;
		framesHeal[2] = healfx3;
		framesHeal[3] = healfx4;
		framesHeal[4] = healfx5;
		framesHeal[5] = healfx6;
		framesHeal[6] = healfx7;
		framesHeal[7] = healfx8;
		framesHeal[8] = healfx9;

		animatedHeal = new Animation(0.06f, framesHeal);
		animatedHeal.setPlayMode(PlayMode.LOOP);

	}

	int countAttackAnimation = 0;
	int countHandAnimation = 0;
	float stateTime = 0;
	float stateTimeHeal = 0;
	public void DrawPlayerMovimentos(SpriteBatch batch, float deltaTime) {
		try {

			if (attackAnimation || handAnimation || shieldAnimation || healAnimation){

				float playerPosX = GameScreen.player.position.x;
				float playerPosY = GameScreen.player.position.y;
				int dirPlayer = GameScreen.player.dir;
				if (dirPlayer == 0){
					playerPosY = playerPosY - 32;
				} else if (dirPlayer == 1){
					playerPosX = playerPosX - 32;
				} else if (dirPlayer == 2){
					playerPosY = playerPosY + 32;
				} else if (dirPlayer == 3){
					playerPosX = playerPosX + 32;
				}

				if (attackAnimation){
					batch.begin();
					batch.draw(animatedAttack.getKeyFrame(countAttackAnimation), playerPosX, playerPosY);
					batch.end();
					countAttackAnimation++;
					Thread.sleep(35);
					if (countAttackAnimation == 7){			
						attackAnimation = false;
						countAttackAnimation = 0;
					}
					if (countAttackAnimation == 3){
						Rectangle ataquePos = new Rectangle(playerPosX, playerPosY, 30, 30);
						for (Inimigo inimigo : GameScreen.listaInimigos) {
							Rectangle inimigoPos = new Rectangle(inimigo.position.x, inimigo.position.y, 30, 30);
							if (inimigoPos.overlaps(ataquePos)){
								int hit = 0;
								try {
									int nextInt = Player.level;
									if (nextInt == 0){
										nextInt = 1;
									}
									hit = (Player.forca+new Random().nextInt(nextInt))-inimigo.defesa;
								} catch (Exception e) {hit = 0;}
								inimigo.removeLife(hit);
								if (inimigo.hpAtual<=0){
									Player.addXp(inimigo.experiencia);
									GameScreen.listaInimigos.remove(inimigo);
									break;
								}
							}
						}
						GameScreen.itens.verificaBlockedItem((int) playerPosX, (int) playerPosY, "ataque");
					}

				}

				if (handAnimation){
					batch.begin();
					batch.draw(animatedHand.getKeyFrame(countHandAnimation), playerPosX, playerPosY);
					batch.end();
					countHandAnimation++;
					Thread.sleep(40);
					if (countHandAnimation == 5){			
						handAnimation = false;
						countHandAnimation = 0;
						GameScreen.itens.verificaItem((int) playerPosX, (int) playerPosY);
					}
				}

				if (shieldAnimation){
					stateTime += Gdx.graphics.getDeltaTime(); 
					batch.begin();
					batch.draw(animatedShield.getKeyFrame(stateTime), GameScreen.player.position.x, GameScreen.player.position.y-5);
					batch.end();
				}

				if (healAnimation){
					stateTimeHeal += Gdx.graphics.getDeltaTime(); 
					if (animatedHeal.isAnimationFinished(stateTimeHeal)){
						stateTimeHeal = 0;
						healAnimation = false;
					} else {
						batch.begin();
						batch.draw(animatedHeal.getKeyFrame(stateTimeHeal), GameScreen.player.position.x, GameScreen.player.position.y-10);
						batch.end();
					}
				}

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void attack (){
		attackAnimation = true;
	}

	public void pegar (){
		handAnimation = true;	
	}

	public void defender (){
		shieldAnimation = true;	
	}

	public void defenderStop (){
		shieldAnimation = false;	
	}

	public void gain (){
		healAnimation = true;	
	}

}
