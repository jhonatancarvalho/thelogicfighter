package entities;

import java.util.ArrayList;
import java.util.Properties;

import main.Assets;
import screens.GameScreen;
import screens.LoadScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TextAlgoritmos {

	public static boolean focusInTxtArea = false;
	public static int escPresed = 2;

	static TextArea txtArea;
	ScrollPane scrollPane; 
	Table textAreaHolder;
	TextArea chatArea;	

	ImageButton buttonMin;
	ImageButton buttonMax;
	Texture textureButton;
	TextureRegion imageButton;
	ImageButtonStyle styleButton;

	ArrayList<ImageButton> movimentosHUDLateral;
	ArrayList<ImageButton> movimentosHUDF1;
	ArrayList<ImageButton> movimentosHUDF2;
	ArrayList<ImageButton> movimentosHUDFor;
	ArrayList<ImageButton> movimentosHUDIf;

	ArrayList<String> txtAlgoritmos;
	ArrayList<String> txtF1;
	ArrayList<String> txtF2;
	ArrayList<String> txtFor;
	ArrayList<String> txtIf;
	
	ArrayList<String> textoTela;

	public void LoadTextAlgoritmosTexture() {

		txtArea = new TextArea("", Assets.skin);
		txtArea.setMessageText("Digite seu algoritmo aqui!");

		textAreaHolder = new Table();

		scrollPane = new ScrollPane(textAreaHolder, Assets.skin);
		scrollPane.setForceScroll(false, true);
		scrollPane.setFlickScroll(false);
		scrollPane.setOverscroll(false, true);
		scrollPane.setBounds(0, 0, 771, 150);

		textureButton = new Texture(Gdx.files.internal("data/min.png"));
		imageButton = new TextureRegion(textureButton);
		styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
		styleButton.imageUp = new TextureRegionDrawable(imageButton);
		buttonMin = new ImageButton(styleButton);
		buttonMin.setBounds(0, 150, 15, 15);
		buttonMin.setName("buttonMin");

		textureButton = new Texture(Gdx.files.internal("data/max.png"));
		imageButton = new TextureRegion(textureButton);
		styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
		styleButton.imageUp = new TextureRegionDrawable(imageButton);
		buttonMax = new ImageButton(styleButton);
		buttonMax.setBounds(15, 150, 15, 15);
		buttonMax.setName("buttonMax");

		Properties propertiesFase = LoadScreen.getMapConfig(LoadScreen.faseAtual);
		if (!propertiesFase.getProperty("textAlgoritmo").equals("true")){	
			txtArea.setMessageText("Campo bloqueado nesta fase!");
			txtArea.setDisabled(true);
		}
		
		
		
		textAreaHolder.add(txtArea).fill().expand().height(1500);

		scrollPane.scrollToCenter(0, 0, 0, 0);

		UpdateTextAlgoritmos();

		GameScreen.getStage().addActor(scrollPane);
		GameScreen.getStage().addActor(buttonMin);
		GameScreen.getStage().addActor(buttonMax);

		movimentosHUDLateral = new ArrayList<>();
		movimentosHUDF1 = new ArrayList<>();
		movimentosHUDF2 = new ArrayList<>();
		movimentosHUDFor = new ArrayList<>();
		movimentosHUDIf = new ArrayList<>();
		textoTela = new ArrayList<>();

	}

	public void UpdateTextAlgoritmos() {

		buttonMin.addListener(new ClickListener() {		
			public void clicked(InputEvent event, float x, float y) {
				// verifica o GameScreen.getStage() em que esta o ScrollPane e centraliza o scrollbar de acordo
				float tamanhoH = 5;
				if (getTamanhoScroll() == 450){
					tamanhoH = 350;
				} else if (getTamanhoScroll() == 350){
					tamanhoH = 250;
				} else if (getTamanhoScroll() == 250){
					tamanhoH = 150;
				} else if (getTamanhoScroll() == 150){
					tamanhoH = 5;
				}
				for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
					if (GameScreen.getStage().getActors().get(i).toString().equals("ScrollPane")){
						((ScrollPane) GameScreen.getStage().getActors().get(i)).setSize(771, tamanhoH);
					}
					if (GameScreen.getStage().getActors().get(i).toString().equals("buttonMin")){
						((ImageButton) GameScreen.getStage().getActors().get(i)).setPosition(0, tamanhoH);
					}
					if (GameScreen.getStage().getActors().get(i).toString().equals("buttonMax")){
						((ImageButton) GameScreen.getStage().getActors().get(i)).setPosition(15, tamanhoH);
					}
				}
			}		
		});

		buttonMax.addListener(new ClickListener() {		
			public void clicked(InputEvent event, float x, float y) {
				// verifica o GameScreen.getStage() em que esta o ScrollPane e centraliza o scrollbar de acordo	
				float tamanhoH = 450;
				if (getTamanhoScroll() == 5){
					tamanhoH = 150;
				} else if (getTamanhoScroll() == 150){
					tamanhoH = 250;
				} else if (getTamanhoScroll() == 250){
					tamanhoH = 350;
				} else if (getTamanhoScroll() == 350){
					tamanhoH = 450;
				}			
				for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
					if (GameScreen.getStage().getActors().get(i).toString().equals("ScrollPane")){
						((ScrollPane) GameScreen.getStage().getActors().get(i)).setSize(771, tamanhoH);
					}
					if (GameScreen.getStage().getActors().get(i).toString().equals("buttonMin")){
						((ImageButton) GameScreen.getStage().getActors().get(i)).setPosition(0, tamanhoH);
					}
					if (GameScreen.getStage().getActors().get(i).toString().equals("buttonMax")){
						((ImageButton) GameScreen.getStage().getActors().get(i)).setPosition(15, tamanhoH);
					}
				}
			}		
		});

		txtArea.addListener(new ClickListener() {		
			public void clicked(InputEvent event, float x, float y) {
				escPresed = 0;
				focusInTxtArea = true;
			}		
		});

		txtArea.addListener(new InputListener() {		
			public boolean keyTyped(InputEvent event, char character) {
				
				// verifica o GameScreen.getStage() em que esta o ScrollPane e centraliza o scrollbar de acordo
				atualizaPosScroll();

				// se apertar ESC
				if (event.getKeyCode() == Keys.ESCAPE){
					removeFocusTextArea();
				}

				if(event.getKeyCode() == Keys.TAB){
					removeFocusTextArea();
					escPresed++;

				}

				return true;
			}
		});

	}
	
	// atualiza a possição do scroll de acordo com o cursor
	public void atualizaPosScroll(){
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
			if (GameScreen.getStage().getActors().get(i).toString().equals("ScrollPane")){
				((ScrollPane) GameScreen.getStage().getActors().get(i)).setScrollY((txtArea.getCursorLine()-6)*20);
			}
		}
	}

	public float getTamanhoScroll(){
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
			if (GameScreen.getStage().getActors().get(i).toString().equals("ScrollPane")){
				return ((ScrollPane) GameScreen.getStage().getActors().get(i)).getHeight();
			}
		}
		return 0f;
	}
	
	public static void removeFocusTextArea(){
		focusInTxtArea = false;
		GameScreen.getStage().unfocus(txtArea);
		escPresed++;
	}

	public static boolean getEscPressed(){
		if (escPresed>2){
			return true;
		}
		return false;
	}

	public void preencheArraysMovimentos(){
		movimentosHUDLateral.clear();
		movimentosHUDF1.clear();
		movimentosHUDF2.clear();
		movimentosHUDFor.clear();
		movimentosHUDIf.clear();
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
			if (GameScreen.getStage().getActors().get(i).toString().equals("dialogMovimentos")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

							if (!buttonAtual.isDisabled()){

								movimentosHUDLateral.add(buttonAtual);

							}

						}

					}
				}
			} else if (GameScreen.getStage().getActors().get(i).toString().equals("dialogF1")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

							if (!buttonAtual.isDisabled()){

								movimentosHUDF1.add(buttonAtual);

							}

						}

					}
				}
			} else if (GameScreen.getStage().getActors().get(i).toString().equals("dialogF2")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

							if (!buttonAtual.isDisabled()){

								movimentosHUDF2.add(buttonAtual);

							}

						}

					}
				}
			} else if (GameScreen.getStage().getActors().get(i).toString().equals("dialogFor")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							try {						

								ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

								if (!buttonAtual.isDisabled()){

									movimentosHUDFor.add(buttonAtual);

								}
							} catch (Exception e) {	}

						}

					}
				}
			} else if (GameScreen.getStage().getActors().get(i).toString().equals("dialogIf")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							try {						

								ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

								if (!buttonAtual.isDisabled()){

									movimentosHUDIf.add(buttonAtual);

								}
							} catch (Exception e) {	}

						}

					}
				}
			}
		}

	}


	public void preencheTxtAlgoritmos(){
		try {

			preencheArraysMovimentos();
			txtAlgoritmos = new ArrayList<>();
			txtF1 = new ArrayList<>();
			txtF2 = new ArrayList<>();
			txtFor = new ArrayList<>();
			txtIf = new ArrayList<>();
			txtArea.setText("");

			txtAlgoritmos.add("inicio {");
			for (int i = 0; i < movimentosHUDLateral.size(); i++) {
				if (movimentosHUDLateral.get(i).getName().equals("F1")){			
					txtAlgoritmos.add("     "+movimentosHUDLateral.get(i).getName().toLowerCase()+"();");
					if (txtF1.size()<=0){
						preencheF1();
					}
				} else if (movimentosHUDLateral.get(i).getName().equals("F2")){
					txtAlgoritmos.add("     "+movimentosHUDLateral.get(i).getName().toLowerCase()+"();");
					if (txtF2.size()<=0){
						preencheF2();
					}
				} else if (movimentosHUDLateral.get(i).getName().equals("FOR")){
					int inicioFor;
					int fimFor;
					try {
						inicioFor = Integer.parseInt(HUDFor.txtFor1.getText());
						fimFor = Integer.parseInt(HUDFor.txtFor2.getText());
					} catch (Exception e) {
						inicioFor = 0;
						fimFor = 0;
					}
					txtAlgoritmos.add("     for (int i="+inicioFor+"; i<="+fimFor+"; i++) {");
					if (txtFor.size()<=0){
						preencheFor(txtAlgoritmos,"MAIN");
					}
					txtAlgoritmos.add("     }");
				}  else if (movimentosHUDLateral.get(i).getName().equals("IF")){
					txtAlgoritmos.add("     if (player.proximoPasso() == '"+HUDIF.dropdown.getSelected().toString().toLowerCase()+"' {");
					if (txtIf.size()<=0){
						preencheIf(txtAlgoritmos, "MAIN");
					}
					txtAlgoritmos.add("     }");
				} else {
					txtAlgoritmos.add("     "+movimentosHUDLateral.get(i).getName().toLowerCase()+"();");
				}
			}

			txtAlgoritmos.add("} fim.");

			for (int j = 0; j < txtAlgoritmos.size(); j++) {
				if (j>0) {
					txtArea.setText(TextAlgoritmos.txtArea.getText()+"\n"+txtAlgoritmos.get(j));
				} else {
					txtArea.setText(txtAlgoritmos.get(j));
				}
			}

			for (int j = 0; j < txtF1.size(); j++) {
				if (j>0) {
					txtArea.setText(TextAlgoritmos.txtArea.getText()+"\n"+txtF1.get(j));
				} else {
					txtArea.setText(TextAlgoritmos.txtArea.getText()+"\n");
					txtArea.setText(TextAlgoritmos.txtArea.getText()+"\n"+txtF1.get(j));
				}
			}

			for (int j = 0; j < txtF2.size(); j++) {
				if (j>0) {
					txtArea.setText(TextAlgoritmos.txtArea.getText()+"\n"+txtF2.get(j));
				} else {
					txtArea.setText(TextAlgoritmos.txtArea.getText()+"\n");
					txtArea.setText(TextAlgoritmos.txtArea.getText()+"\n"+txtF2.get(j));
				}
			}

		} catch (Exception e) {e.printStackTrace();}
	}

	// lista de preenchimento, de qual procedimento vem
	public void preencheIf(ArrayList<String> lista, String controle){
		try {
			String espaco = "          ";
			if (controle.equals("FOR")){
				espaco = "               ";
			}
					
			for (int i = 0; i < movimentosHUDIf.size(); i++) {
				if (movimentosHUDIf.get(i).getName().equals("F1")){
					lista.add(espaco+movimentosHUDIf.get(i).getName().toLowerCase()+"();");
					if (txtF1.size()<=0){
						preencheF1();
					}
				}  else if (movimentosHUDIf.get(i).getName().equals("F2")){
					lista.add(espaco+movimentosHUDIf.get(i).getName().toLowerCase()+"();");
					if (txtF2.size()<=0){
						preencheF2();
					}
				}  else if (movimentosHUDIf.get(i).getName().equals("FOR")){
					int inicioFor;
					int fimFor;
					try {
						inicioFor = Integer.parseInt(HUDFor.txtFor1.getText());
						fimFor = Integer.parseInt(HUDFor.txtFor2.getText());
					} catch (Exception e) {
						inicioFor = 0;
						fimFor = 0;
					}
					lista.add(espaco+"for (int i="+inicioFor+"; i<="+fimFor+"; i++) {");
					if (txtFor.size()<=0){
						preencheFor(lista, "IF");
					}
					lista.add(espaco+"}");
				}   else if (movimentosHUDIf.get(i).getName().equals("IF")){

				} else {
					lista.add(espaco+movimentosHUDIf.get(i).getName().toLowerCase()+"();");				
				}
			}

		} catch (Exception e) {}
	}

	public void preencheFor(ArrayList<String> lista, String controle){
		try {
			String espaco = "          ";
			if (controle.equals("IF")){
				espaco = "               ";
			}
			for (int i = 0; i < movimentosHUDFor.size(); i++) {
				if (movimentosHUDFor.get(i).getName().equals("F1")){
					lista.add(espaco+movimentosHUDFor.get(i).getName().toLowerCase()+"();");
					if (txtF1.size()<=0){
						preencheF1();
					}
				}  else if (movimentosHUDFor.get(i).getName().equals("F2")){
					lista.add(espaco+movimentosHUDFor.get(i).getName().toLowerCase()+"();");
					if (txtF2.size()<=0){
						preencheF2();
					}
				}  else if (movimentosHUDFor.get(i).getName().equals("FOR")){

				}   else if (movimentosHUDFor.get(i).getName().equals("IF")){
					lista.add(espaco+"if (player.proximoPasso() == '"+HUDIF.dropdown.getSelected().toString().toLowerCase()+"' {");
					if (txtIf.size()<=0){
						preencheIf(lista, "FOR");
					}
					lista.add(espaco+"}");
				} else {
					lista.add(espaco+movimentosHUDFor.get(i).getName().toLowerCase()+"();");				
				}
			}

		} catch (Exception e) {}
	}

	public void preencheF1(){
		try {
			txtF1.add("f1() {");	
			for (int i = 0; i < movimentosHUDF1.size(); i++) {
				if (movimentosHUDF1.get(i).getName().equals("F1")){
					txtF1.add("     "+movimentosHUDF1.get(i).getName().toLowerCase()+"();");			
				}  else if (movimentosHUDF1.get(i).getName().equals("F2")){
					txtF1.add("     "+movimentosHUDF1.get(i).getName().toLowerCase()+"();");
					if (txtF2.size()<=0){
						preencheF2();
					}
				}  else if (movimentosHUDF1.get(i).getName().equals("FOR")){
					int inicioFor;
					int fimFor;
					try {
						inicioFor = Integer.parseInt(HUDFor.txtFor1.getText());
						fimFor = Integer.parseInt(HUDFor.txtFor2.getText());
					} catch (Exception e) {
						inicioFor = 0;
						fimFor = 0;
					}
					txtF1.add("     for (int i="+inicioFor+"; i<="+fimFor+"; i++) {");
					if (txtFor.size()<=0){
						preencheFor(txtF1, "F1");
					}
					txtF1.add("     }");
				}   else if (movimentosHUDF1.get(i).getName().equals("IF")){
					txtF1.add("     if (player.proximoPasso() == '"+HUDIF.dropdown.getSelected().toString().toLowerCase()+"' {");
					if (txtIf.size()<=0){
						preencheIf(txtF1, "F1");
					}
					txtF1.add("     }");
				} else {
					txtF1.add("     "+movimentosHUDF1.get(i).getName().toLowerCase()+"();");				
				}
			}

			txtF1.add("}.");

		} catch (Exception e) {}

	}

	public void preencheF2(){
		try {
			txtF2.add("f2() {");		
			for (int i = 0; i < movimentosHUDF2.size(); i++) {
				if (movimentosHUDF2.get(i).getName().equals("F1")){
					txtF2.add("     "+movimentosHUDF2.get(i).getName().toLowerCase()+"();");
					if (txtF1.size()<=0){
						preencheF1();
					}				
				}  else if (movimentosHUDF2.get(i).getName().equals("F2")){
					txtF2.add("     "+movimentosHUDF2.get(i).getName().toLowerCase()+"();");				
				}  else if (movimentosHUDF2.get(i).getName().equals("FOR")){
					int inicioFor;
					int fimFor;
					try {
						inicioFor = Integer.parseInt(HUDFor.txtFor1.getText());
						fimFor = Integer.parseInt(HUDFor.txtFor2.getText());
					} catch (Exception e) {
						inicioFor = 0;
						fimFor = 0;
					}
					txtF2.add("     for (int i="+inicioFor+"; i<="+fimFor+"; i++) {");
					if (txtFor.size()<=0){
						preencheFor(txtF2, "F2");
					}
					txtF2.add("     }");
				}   else if (movimentosHUDF2.get(i).getName().equals("IF")){
					txtF2.add("     if (player.proximoPasso() == '"+HUDIF.dropdown.getSelected().toString().toLowerCase()+"' {");
					if (txtIf.size()<=0){
						preencheIf(txtF2, "F2");
					}
					txtF2.add("     }");
				} else {
					txtF2.add("     "+movimentosHUDF2.get(i).getName().toLowerCase()+"();");					
				}
			}

			txtF2.add("}.");

		} catch (Exception e) {}

	}


}
