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
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HUDIF {

	Dialog dialogMovimentos;

	Table tabelaMovimentos;
	Table tabelaAuxiliar;

	Texture textureButton;
	TextureRegion imageButton;
	ImageButtonStyle styleButton;
	
	@SuppressWarnings("rawtypes")
	public static SelectBox dropdown;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void LoadHUDIFTexture() {

		dialogMovimentos = new Dialog("IF", Assets.skin);
		dialogMovimentos.setName("dialogIf");
		dialogMovimentos.setMovable(false);
		dialogMovimentos.setModal(false);

		dropdown = new SelectBox(Assets.skin);
		dropdown.setItems("Colisao", "Inimigo", "Item");
		dropdown.setSelected("Colisao");
		
		tabelaMovimentos = new Table(Assets.skin);
		tabelaMovimentos.setName("tabelaMovimentos");
		tabelaMovimentos.setFillParent(true);
		
		tabelaAuxiliar = new Table(Assets.skin);
		tabelaAuxiliar.setName("tabelaAuxiliar");
		tabelaAuxiliar.setFillParent(true);
		
		tabelaAuxiliar.add(dropdown).width(175).height(20).padTop(15).padRight(-95);
		tabelaAuxiliar.row();
			
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
				tabelaMovimentos.add(newButton).width(45).height(45).padRight(21).padTop(36);
				tabelaMovimentos.row();				
			} else {
				tabelaMovimentos.add(newButton).width(45).height(45).padTop(36);
			}

		}

		tabelaMovimentos.center();

		UpdateHUDIF();

		dialogMovimentos.add(tabelaAuxiliar);
		dialogMovimentos.add(tabelaMovimentos).expand();
		dialogMovimentos.setBounds(GameScreen.w, GameScreen.h-574, 186, 90);

		Properties propertiesFase = LoadScreen.getMapConfig(LoadScreen.faseAtual);
		if (!propertiesFase.getProperty("hudIf").equals("true")){	
			dialogMovimentos.clear();
		}
		
		GameScreen.getStage().addActor(dialogMovimentos);

	}

	public void UpdateHUDIF() {

		
		dialogMovimentos.addListener(new ClickListener() {	

			public void clicked(InputEvent event, float x, float y) {
				
				DialogSelected.dialogCount = 5;
				
				HUDFor.removeFocusOnForTxts();
				
			}		
		});
		
		dialogMovimentos.addListener(new ClickListener(1) {

			public void clicked(InputEvent event, float x, float y) {

				GameScreen.hudMovimentos.limparMovimentoHUDIf();
				GameScreen.textAlgoritmos.preencheTxtAlgoritmos();
				HUDFor.removeFocusOnForTxts();

			}		
		});

	}

}
