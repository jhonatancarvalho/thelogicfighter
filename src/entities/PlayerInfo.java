package entities;

import main.Assets;
import screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PlayerInfo {

	Dialog dialogScreen;

	public void DrawPlayerInfo(SpriteBatch batch, float deltaTime) {

		if (Gdx.input.isKeyJustPressed(Keys.I) && !TextAlgoritmos.focusInTxtArea && !HUDMovimentos.verificaAction("dialogScreen") && !HUDMovimentos.verificaAction("dialogScreenOpcoes")){
			
			dialogScreen = new Dialog("Player Info", Assets.skin);	
			dialogScreen.setName("dialogScreen");
			dialogScreen.setMovable(false);

			Table tabela = new Table(Assets.skin);

			tabela.row();
			tabela.add(("Level: "+ Player.level)).padLeft(10);
			tabela.add(("XP: "+((int) Player.xpAtual)+" / "+((int) Player.xpTotal))).padRight(20).padLeft(30);
			tabela.row();	
			tabela.add(("HP: "+((int) Player.hpAtual)+" / "+((int) Player.hpTotal))).padLeft(10).padTop(5);
			tabela.add(("SP: "+((int) Player.spAtual)+" / "+((int) Player.spTotal))).padRight(20).padLeft(30);	
			tabela.row();
			tabela.add("Atk: "+Player.forca).padLeft(10).padTop(5);
			tabela.add("Def: "+Player.defesa).padRight(20).padLeft(30);	
			tabela.row();

			dialogScreen.add(tabela);
			dialogScreen.key(Keys.ENTER, true)
			.key(Keys.ESCAPE, true).key(Keys.I, true);			

			dialogScreen.show(GameScreen.getStage());

		}

	}
}
