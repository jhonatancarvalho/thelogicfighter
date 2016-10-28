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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HUDFor {

	Dialog dialogMovimentos;

	Table tabelaMovimentos;

	Texture textureButton;
	TextureRegion imageButton;
	ImageButtonStyle styleButton;
	
	Label labelFor1;
	Label labelFor2;
	public static  TextField txtFor1;
	public static TextField txtFor2;

	public void LoadHUDForTexture() {

		dialogMovimentos = new Dialog("FOR", Assets.skin);
		dialogMovimentos.setName("dialogFor");
		dialogMovimentos.setMovable(false);
		dialogMovimentos.setModal(false);

		labelFor1 = new Label("De: ", Assets.skin);
		labelFor2 = new Label("Ate: ", Assets.skin);
		txtFor1 = new TextField("1", Assets.skin);
		txtFor2 = new TextField("5", Assets.skin);
		
		tabelaMovimentos = new Table(Assets.skin);
		tabelaMovimentos.setName("tabelaMovimentos");
		tabelaMovimentos.setFillParent(true);
		
		tabelaMovimentos.add(labelFor1).width(30).height(20).padTop(15);
		tabelaMovimentos.add(txtFor1).width(40).height(20).padTop(15);
		tabelaMovimentos.add(labelFor2).width(30).height(20).padTop(15);
		tabelaMovimentos.add(txtFor2).width(40).height(20).padTop(15).padRight(4);
		tabelaMovimentos.row();	
			
		for (int i = 0; i < 4; i++) {

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

			if (i==3){
				tabelaMovimentos.add(newButton).width(45).height(45).padRight(4).padTop(1);
				tabelaMovimentos.row();				
			} else {
				tabelaMovimentos.add(newButton).width(45).height(45).padTop(1);
			}

		}

		tabelaMovimentos.center();

		UpdateHUDFor();

		dialogMovimentos.add(tabelaMovimentos).expand();
		dialogMovimentos.setBounds(GameScreen.w, GameScreen.h-483, 186, 90);

		Properties propertiesFase = LoadScreen.getMapConfig(LoadScreen.faseAtual);
		if (!propertiesFase.getProperty("hudFor").equals("true")){	
			dialogMovimentos.clear();
		}
		
		GameScreen.getStage().addActor(dialogMovimentos);

	}

	public void UpdateHUDFor() {

		txtFor1.addListener(new InputListener() {
			public boolean keyTyped(InputEvent event, char character) {
				try {
					Integer.parseInt(txtFor1.getText());
				} catch (Exception e) {
					txtFor1.setText("");
				}
				
				if (event.getKeyCode() == 131){
					removeFocusOnForTxts();
				}
				
				return true;	
			}		
		});
		
		txtFor2.addListener(new InputListener() {
			public boolean keyTyped(InputEvent event, char character) {
				try {
					Integer.parseInt(txtFor2.getText());
				} catch (Exception e) {
					txtFor2.setText("");
				}
				
				if (event.getKeyCode() == 131){
					removeFocusOnForTxts();
				}
				
				return true;	
			}		
		});

		dialogMovimentos.addListener(new ClickListener() {	

			public void clicked(InputEvent event, float x, float y) {
				
				DialogSelected.dialogCount = 4;

			}		
		});
		
		dialogMovimentos.addListener(new ClickListener(1) {

			public void clicked(InputEvent event, float x, float y) {

				GameScreen.hudMovimentos.limparMovimentoHUDFor();
				GameScreen.textAlgoritmos.preencheTxtAlgoritmos();
				HUDFor.removeFocusOnForTxts();

			}		
		});

	}
	
	public static void removeFocusOnForTxts(){
		GameScreen.getStage().unfocus(txtFor1);
		GameScreen.getStage().unfocus(txtFor2);
	}
	
}
