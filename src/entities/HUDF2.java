package entities;

import java.util.Properties;

import main.Assets;
import screens.GameScreen;
import screens.LoadScreen;

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

public class HUDF2 {

	Dialog dialogMovimentos;

	Table tabelaMovimentos;

	Texture textureButton;
	TextureRegion imageButton;
	ImageButtonStyle styleButton;

	public void LoadHUDF2Texture() {

		dialogMovimentos = new Dialog("F2", Assets.skin);
		dialogMovimentos.setName("dialogF2");
		dialogMovimentos.setMovable(false);
		dialogMovimentos.setModal(false);

		tabelaMovimentos = new Table(Assets.skin);
		tabelaMovimentos.setName("tabelaMovimentos");
		tabelaMovimentos.setFillParent(true);

		for (int i = 0; i < 8; i++) {

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
			} else if (i==7){
				tabelaMovimentos.add(newButton).width(45).height(45).padRight(4);
				tabelaMovimentos.row();				
			} else {
				tabelaMovimentos.add(newButton).width(45).height(45);
			}

		}

		tabelaMovimentos.center();

		UpdateHUDF2();

		dialogMovimentos.add(tabelaMovimentos).expand();
		dialogMovimentos.setBounds(GameScreen.w, GameScreen.h-392, 186, 115);

		Properties propertiesFase = LoadScreen.getMapConfig(LoadScreen.faseAtual);
		if (!propertiesFase.getProperty("hudF2").equals("true")){	
			dialogMovimentos.clear();
		}
		
		GameScreen.getStage().addActor(dialogMovimentos);

	}

	public void UpdateHUDF2() {

		dialogMovimentos.addListener(new ClickListener() {	

			public void clicked(InputEvent event, float x, float y) {
				
				DialogSelected.dialogCount = 3;
				
				HUDFor.removeFocusOnForTxts();

			}		
		});
		
		dialogMovimentos.addListener(new ClickListener(1) {

			public void clicked(InputEvent event, float x, float y) {

				GameScreen.hudMovimentos.limparMovimentoHUDF2();
				GameScreen.textAlgoritmos.preencheTxtAlgoritmos();
				HUDFor.removeFocusOnForTxts();

			}		
		});

	}

}
