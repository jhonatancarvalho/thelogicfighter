package entities;

import main.Assets;
import screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HUDLateral {

	Dialog dialogMovimentos;

	Table tabelaMovimentos;

	Texture textureButton;
	TextureRegion imageButton;
	ImageButtonStyle styleButton;

	public void LoadHUDLateralTexture() {

		dialogMovimentos = new Dialog("Movimentos", Assets.skin);
		dialogMovimentos.setName("dialogMovimentos");
		dialogMovimentos.setMovable(false);
		dialogMovimentos.setModal(false);

		tabelaMovimentos = new Table(Assets.skin);
		tabelaMovimentos.setName("tabelaMovimentos");
		tabelaMovimentos.setFillParent(true);

		for (int i = 0; i < 12; i++) {

			textureButton = new Texture(Gdx.files.internal("movimentos/null.png"));
			imageButton = new TextureRegion(textureButton);		
			styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
			styleButton.imageUp = new TextureRegionDrawable(imageButton);
			final ImageButton newButton = new ImageButton(styleButton);
			newButton.setDisabled(true);
			newButton.setLayoutEnabled(false);
			newButton.setName("btn"+i);

			newButton.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

					textureButton = new Texture(Gdx.files.internal("movimentos/null.png"));
					imageButton = new TextureRegion(textureButton);		
					styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
					styleButton.imageUp = new TextureRegionDrawable(imageButton);
					newButton.setStyle(styleButton);
					newButton.setDisabled(true);
					newButton.setName("");

					GameScreen.textAlgoritmos.preencheTxtAlgoritmos();

					return true;

				}
			});

			if (i<=3){
				if (i==3){
					tabelaMovimentos.add(newButton).width(45).height(45).padTop(15).padRight(4);
					tabelaMovimentos.row();			
				} else {
					tabelaMovimentos.add(newButton).width(45).height(45).padTop(15);		
				}
			} else if (i==7 || i==11){
				tabelaMovimentos.add(newButton).width(45).height(45).padRight(4);
				tabelaMovimentos.row();				
			} else {
				tabelaMovimentos.add(newButton).width(45).height(45);
			}

		}

		tabelaMovimentos.center();

		UpdateOptionsGame();

		dialogMovimentos.add(tabelaMovimentos).expand();
		dialogMovimentos.setBounds(GameScreen.w, GameScreen.h, 186, 160);
		
		GameScreen.getStage().addActor(dialogMovimentos);

	}

	public void UpdateOptionsGame() {

		dialogMovimentos.addListener(new ClickListener() {	

			public void clicked(InputEvent event, float x, float y) {

				DialogSelected.dialogCount = 1;

				HUDFor.removeFocusOnForTxts();

			}		
		});

		dialogMovimentos.addListener(new ClickListener(1) {

			public void clicked(InputEvent event, float x, float y) {

				GameScreen.hudMovimentos.limparMovimentoHUDLateral();
				GameScreen.textAlgoritmos.preencheTxtAlgoritmos();
				HUDFor.removeFocusOnForTxts();

			}		
		});

	}

}
