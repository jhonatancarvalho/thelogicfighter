package entities;

import screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StatusBar {

	NinePatch empty;
	Texture emptyT;
	
	NinePatch full;
	Texture fullT;
	
	NinePatch fullR;
	Texture fullTR;
	
	BitmapFont font;

	public void LoadStatusBarTexture() {

		emptyT = new Texture(Gdx.files.internal("load/empty.png"));
		empty = new NinePatch(new TextureRegion(emptyT,24,24),6,6,6,6);
	
		fullT = new Texture(Gdx.files.internal("load/full.png"));
		full = new NinePatch(new TextureRegion(fullT,24,24),6,6,6,6);
		
		fullTR = new Texture(Gdx.files.internal("load/fullred.png"));
		fullR = new NinePatch(new TextureRegion(fullTR,24,24),6,6,6,6);

		font = new BitmapFont(); 
		
	}

	public void DrawStatusBar(SpriteBatch batch, float deltaTime) {

		batch.begin();	
		
		font.drawMultiLine(batch, "HP: ", 20, GameScreen.h-6, 0, BitmapFont.HAlignment.CENTER);
		
		empty.draw(batch, 35, GameScreen.h-20, 150, 15);	
		float hpAuxiliar = Player.hpAtual/Player.hpTotal;
		if (hpAuxiliar>0.0f){
			if (hpAuxiliar >= 1.0f){
				hpAuxiliar = 1.0f;
			} else if (hpAuxiliar <= 0.08f){
				hpAuxiliar = 0.08f;
			}
			fullR.draw(batch, 35, GameScreen.h-20, hpAuxiliar*150, 15);
		}
		
		font.drawMultiLine(batch,((int) Player.hpAtual)+" / "+((int) Player.hpTotal), 110, GameScreen.h-6, 0, BitmapFont.HAlignment.CENTER);
		
		font.drawMultiLine(batch, "SP: ", 20, GameScreen.h-22, 0, BitmapFont.HAlignment.CENTER);
		
		empty.draw(batch, 35, GameScreen.h-36, 150, 15);	
		float spAuxiliar = Player.spAtual/Player.spTotal;
		if (spAuxiliar>0.0f){
			if (spAuxiliar >= 1.0f){
				spAuxiliar = 1.0f;
			} else if (spAuxiliar <= 0.08f){
				spAuxiliar = 0.08f;
			}
			full.draw(batch, 35, GameScreen.h-36, spAuxiliar*150, 15);
		}
		
		font.drawMultiLine(batch,((int) Player.spAtual)+" / "+((int) Player.spTotal), 110, GameScreen.h-22, 0, BitmapFont.HAlignment.CENTER);

		batch.end();

	}
}
