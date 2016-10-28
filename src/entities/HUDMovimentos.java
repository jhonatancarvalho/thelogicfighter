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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HUDMovimentos {

	private String movimentoAtual = "";

	Table tableMovimentos;

	ImageButton frenteButton;
	ImageButton direitaButton;
	ImageButton esquerdaButton;
	ImageButton pegarButton;
	ImageButton atacarButton;
	ImageButton defenderButton;
	ImageButton ifButton;
	ImageButton forButton;
	ImageButton f1Button;
	ImageButton f2Button;

	Texture textureButton;
	TextureRegion imageButton;
	ImageButtonStyle styleButton;

	public void LoadHUDMovimentosTexture() {

		Properties propertiesFase = LoadScreen.getMapConfig(LoadScreen.faseAtual);

		Gdx.input.setInputProcessor(GameScreen.getStage());

		tableMovimentos = new Table(Assets.skin);

		textureButton = new Texture(Gdx.files.internal("movimentos/cima.png"));
		imageButton = new TextureRegion(textureButton);
		styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
		styleButton.imageUp = new TextureRegionDrawable(imageButton);
		frenteButton = new ImageButton(styleButton);

		textureButton = new Texture(Gdx.files.internal("movimentos/girar_dir.png"));
		imageButton = new TextureRegion(textureButton);		
		styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
		styleButton.imageUp = new TextureRegionDrawable(imageButton);
		direitaButton = new ImageButton(styleButton);

		textureButton = new Texture(Gdx.files.internal("movimentos/girar_esq.png"));
		imageButton = new TextureRegion(textureButton);	
		styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
		styleButton.imageUp = new TextureRegionDrawable(imageButton);
		esquerdaButton = new ImageButton(styleButton);

		textureButton = new Texture(Gdx.files.internal("movimentos/mao.png"));
		imageButton = new TextureRegion(textureButton);	
		styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
		styleButton.imageUp = new TextureRegionDrawable(imageButton);
		pegarButton = new ImageButton(styleButton);

		textureButton = new Texture(Gdx.files.internal("movimentos/espada.png"));
		imageButton = new TextureRegion(textureButton);	
		styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
		styleButton.imageUp = new TextureRegionDrawable(imageButton);
		atacarButton = new ImageButton(styleButton);
		if (!propertiesFase.getProperty("btnAtacar").equals("true")){	
			atacarButton.setVisible(false);
		}

		textureButton = new Texture(Gdx.files.internal("movimentos/escudo.png"));
		imageButton = new TextureRegion(textureButton);	
		styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
		styleButton.imageUp = new TextureRegionDrawable(imageButton);
		defenderButton = new ImageButton(styleButton);
		if (!propertiesFase.getProperty("btnDefender").equals("true")){	
			defenderButton.setVisible(false);
		}

		textureButton = new Texture(Gdx.files.internal("movimentos/if.png"));
		imageButton = new TextureRegion(textureButton);		
		styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
		styleButton.imageUp = new TextureRegionDrawable(imageButton);
		ifButton = new ImageButton(styleButton);
		if (!propertiesFase.getProperty("hudIf").equals("true")){	
			ifButton.setVisible(false);
		}

		textureButton = new Texture(Gdx.files.internal("movimentos/for.png"));
		imageButton = new TextureRegion(textureButton);		
		styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
		styleButton.imageUp = new TextureRegionDrawable(imageButton);
		forButton = new ImageButton(styleButton);
		if (!propertiesFase.getProperty("hudFor").equals("true")){	
			forButton.setVisible(false);
		}

		textureButton = new Texture(Gdx.files.internal("movimentos/f1.png"));
		imageButton = new TextureRegion(textureButton);		
		styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
		styleButton.imageUp = new TextureRegionDrawable(imageButton);
		f1Button = new ImageButton(styleButton);
		if (!propertiesFase.getProperty("hudF1").equals("true")){	
			f1Button.setVisible(false);
		}

		textureButton = new Texture(Gdx.files.internal("movimentos/f2.png"));
		imageButton = new TextureRegion(textureButton);	
		styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
		styleButton.imageUp = new TextureRegionDrawable(imageButton);
		f2Button = new ImageButton(styleButton);
		if (!propertiesFase.getProperty("hudF2").equals("true")){	
			f2Button.setVisible(false);
		}

		UpdateHUDMovimentos();

		tableMovimentos.add(frenteButton).width(50).height(50);
		tableMovimentos.add(direitaButton).width(50).height(50).padLeft(5);
		tableMovimentos.add(esquerdaButton).width(50).height(50).padLeft(5);
		tableMovimentos.add(pegarButton).width(50).height(50).padLeft(5);
		if (atacarButton.isVisible()){
			tableMovimentos.add(atacarButton).width(50).height(50).padLeft(5);
		}
		if (defenderButton.isVisible()){
			tableMovimentos.add(defenderButton).width(50).height(50).padLeft(5);
		}
		if (ifButton.isVisible()){
			tableMovimentos.add(ifButton).width(50).height(50).padLeft(5);
		}
		if (forButton.isVisible()){
			tableMovimentos.add(forButton).width(50).height(50).padLeft(5);
		}
		if (f1Button.isVisible()){
			tableMovimentos.add(f1Button).width(50).height(50).padLeft(5);
		}
		if (f2Button.isVisible()){
			tableMovimentos.add(f2Button).width(50).height(50).padLeft(5);
		}

		tableMovimentos.setPosition(GameScreen.w/2, GameScreen.h - 25);
		tableMovimentos.row();

		GameScreen.getStage().addActor(tableMovimentos);

	}

	public void UpdateHUDMovimentos() {

		frenteButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				preencheMovimento("FRENTE", "movimentos/cima.png");			

				TextAlgoritmos.removeFocusTextArea();

				GameScreen.textAlgoritmos.preencheTxtAlgoritmos();

				//movimentoAtual = "FRENTE";
				//GameScreen.player.setCountMovimento(GameScreen.player.position.x+32.0f, GameScreen.player.position.x-32.0f, GameScreen.player.position.y+32.0f, GameScreen.player.position.y-32.0f);

				return true;
			}

		});

		direitaButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				preencheMovimento("DIREITA", "movimentos/girar_dir.png");

				TextAlgoritmos.removeFocusTextArea();

				GameScreen.textAlgoritmos.preencheTxtAlgoritmos();

				//movimentoAtual = "DIREITA";
				//GameScreen.player.setCountMovimento(0, 0, 0, 0);

				return true;
			}
		});

		esquerdaButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				preencheMovimento("ESQUERDA", "movimentos/girar_esq.png");

				TextAlgoritmos.removeFocusTextArea();

				GameScreen.textAlgoritmos.preencheTxtAlgoritmos();

				//movimentoAtual = "ESQUERDA";
				//GameScreen.player.setCountMovimento(0, 0, 0, 0);

				return true;
			}
		});

		pegarButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				preencheMovimento("PEGAR", "movimentos/mao.png");

				TextAlgoritmos.removeFocusTextArea();

				GameScreen.textAlgoritmos.preencheTxtAlgoritmos();

				return true;
			}
		});

		atacarButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				preencheMovimento("ATACAR", "movimentos/espada.png");

				TextAlgoritmos.removeFocusTextArea();

				GameScreen.textAlgoritmos.preencheTxtAlgoritmos();

				return true;
			}
		});

		defenderButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				preencheMovimento("DEFENDER", "movimentos/escudo.png");

				TextAlgoritmos.removeFocusTextArea();

				GameScreen.textAlgoritmos.preencheTxtAlgoritmos();

				return true;
			}
		});

		ifButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				preencheMovimento("IF", "movimentos/if.png");

				TextAlgoritmos.removeFocusTextArea();

				GameScreen.textAlgoritmos.preencheTxtAlgoritmos();

				return true;
			}
		});

		forButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				preencheMovimento("FOR", "movimentos/for.png");

				TextAlgoritmos.removeFocusTextArea();

				GameScreen.textAlgoritmos.preencheTxtAlgoritmos();

				return true;
			}
		});

		f1Button.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				preencheMovimento("F1", "movimentos/f1.png");

				TextAlgoritmos.removeFocusTextArea();

				GameScreen.textAlgoritmos.preencheTxtAlgoritmos();

				return true;
			}
		});

		f2Button.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				preencheMovimento("F2", "movimentos/f2.png");

				TextAlgoritmos.removeFocusTextArea();

				GameScreen.textAlgoritmos.preencheTxtAlgoritmos();

				return true;
			}
		});

	}

	public void DrawHUDMovimentos(float deltaTime) {
		GameScreen.getStage().act(deltaTime);
		GameScreen.getStage().draw();
	}

	public String getMovimentoAtual() {
		return movimentoAtual;
	}

	public void setMovimentoAtual(String movimentoAtual) {
		this.movimentoAtual = movimentoAtual;
	}

	public static boolean verificaAction(String actorName){
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
			if (GameScreen.getStage().getActors().get(i).toString().equals(actorName)){
				return true;
			}
		}
		return false;
	}

	public static boolean verificaActionStartsWith(String actorName){
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
			if (GameScreen.getStage().getActors().get(i).toString().startsWith(actorName)){
				return true;
			}
		}
		return false;
	}

	public static void animatedMessage(String msg, int duration){	
		if (!GameScreen.getStage().getActors().toString().contains("animatedText")){		
			Label animatedMsg = new Label(msg, Assets.skin);
			animatedMsg.setPosition(GameScreen.h/2 - msg.length()*2, 3);				
			animatedMsg.setName("animatedText");
			GameScreen.getStage().addActor(animatedMsg);
			removeStage(duration);			
		}		
	}

	public static void removeStage(final int duration){
		new Thread() {
			@Override 
			public void run() { 
				try {
					Thread.sleep(duration*1000);
				} catch (InterruptedException e) {}
				for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
					if (GameScreen.getStage().getActors().get(i).toString().equals("animatedText")){
						GameScreen.getStage().getActors().removeIndex(i);
					}
				}
			} 
		}.start();
	}

	/*public void removeMovimento(){
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
			if (GameScreen.getStage().getActors().get(i).toString().equals("dialogMovimentos") && DialogSelected.dialogSelected.getName().equals("HUDLateral")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							final ImageButton buttonAtual1 = (ImageButton) tabelaAtual.getCells().get(k).getActor();

							if (!buttonAtual1.isDisabled()){
								buttonAtual1.addListener(new ClickListener() {	

									public void clicked(InputEvent event, float x, float y) {

										textureButton = new Texture(Gdx.files.internal("movimentos/null.png"));
										imageButton = new TextureRegion(textureButton);		
										styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
										styleButton.imageUp = new TextureRegionDrawable(imageButton);
										buttonAtual1.setStyle(styleButton);
										buttonAtual1.setDisabled(true);

									}
								});
							}

						}

					}
				}
			} else if (GameScreen.getStage().getActors().get(i).toString().equals("dialogF1") && DialogSelected.dialogSelected.getName().equals("HUDF1")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							final ImageButton buttonAtual2 = (ImageButton) tabelaAtual.getCells().get(k).getActor();

							if (!buttonAtual2.isDisabled()){
								buttonAtual2.addListener(new ClickListener() {	

									public void clicked(InputEvent event, float x, float y) {

										textureButton = new Texture(Gdx.files.internal("movimentos/null.png"));
										imageButton = new TextureRegion(textureButton);		
										styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
										styleButton.imageUp = new TextureRegionDrawable(imageButton);
										buttonAtual2.setStyle(styleButton);
										buttonAtual2.setDisabled(true);

									}
								});
							}

						}

					}
				}
			}
		}
	}*/

	public void preencheMovimento(String nomeBotao, String imgBotao){
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
			if (GameScreen.getStage().getActors().get(i).toString().equals("dialogMovimentos") && DialogSelected.getName().equals("HUDLateral")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

							if (buttonAtual.isDisabled()){

								textureButton = new Texture(Gdx.files.internal(imgBotao));
								imageButton = new TextureRegion(textureButton);
								styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
								styleButton.imageUp = new TextureRegionDrawable(imageButton);
								buttonAtual.setLayoutEnabled(true);
								buttonAtual.setDisabled(false);
								buttonAtual.setStyle(styleButton);
								buttonAtual.setName(nomeBotao);

								break;

							}

						}

					}
				}
			} else if (GameScreen.getStage().getActors().get(i).toString().equals("dialogF1") && DialogSelected.getName().equals("HUDF1")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

							if (buttonAtual.isDisabled()){

								textureButton = new Texture(Gdx.files.internal(imgBotao));
								imageButton = new TextureRegion(textureButton);
								styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
								styleButton.imageUp = new TextureRegionDrawable(imageButton);
								buttonAtual.setLayoutEnabled(true);
								buttonAtual.setDisabled(false);
								buttonAtual.setStyle(styleButton);
								buttonAtual.setName(nomeBotao);

								break;

							}

						}

					}
				}
			} else if (GameScreen.getStage().getActors().get(i).toString().equals("dialogF2") && DialogSelected.getName().equals("HUDF2")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

							if (buttonAtual.isDisabled()){

								textureButton = new Texture(Gdx.files.internal(imgBotao));
								imageButton = new TextureRegion(textureButton);
								styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
								styleButton.imageUp = new TextureRegionDrawable(imageButton);
								buttonAtual.setLayoutEnabled(true);
								buttonAtual.setDisabled(false);
								buttonAtual.setStyle(styleButton);
								buttonAtual.setName(nomeBotao);

								break;

							}

						}

					}
				}
			} else if (GameScreen.getStage().getActors().get(i).toString().equals("dialogFor") && DialogSelected.getName().equals("HUDFor")){
				if (!nomeBotao.equals("FOR") && !(nomeBotao.equals("IF") && haveForInIf())){
					Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
					for (int j = 0; j < dialogAtual.getCells().size; j++) {
						if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
							Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

							for (int k = 0; k < tabelaAtual.getCells().size; k++) {

								try {						

									ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

									if (buttonAtual.isDisabled()){

										textureButton = new Texture(Gdx.files.internal(imgBotao));
										imageButton = new TextureRegion(textureButton);
										styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
										styleButton.imageUp = new TextureRegionDrawable(imageButton);
										buttonAtual.setLayoutEnabled(true);
										buttonAtual.setDisabled(false);
										buttonAtual.setStyle(styleButton);
										buttonAtual.setName(nomeBotao);

										break;

									}
								} catch (Exception e) {	}

							}

						}
					}
				}else{
					HUDMovimentos.animatedMessage("Nao e possivel fazer isto!", 3);
				}
			} else if (GameScreen.getStage().getActors().get(i).toString().equals("dialogIf") && DialogSelected.getName().equals("HUDIF")){
				if (!nomeBotao.equals("IF") && !(nomeBotao.equals("FOR") && haveIfInFor())){
					Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
					for (int j = 0; j < dialogAtual.getCells().size; j++) {
						if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
							Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

							for (int k = 0; k < tabelaAtual.getCells().size; k++) {

								try {						

									ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

									if (buttonAtual.isDisabled()){

										textureButton = new Texture(Gdx.files.internal(imgBotao));
										imageButton = new TextureRegion(textureButton);
										styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
										styleButton.imageUp = new TextureRegionDrawable(imageButton);
										buttonAtual.setLayoutEnabled(true);
										buttonAtual.setDisabled(false);
										buttonAtual.setStyle(styleButton);
										buttonAtual.setName(nomeBotao);

										break;

									}
								} catch (Exception e) {	}

							}

						}
					}
				}else{
					HUDMovimentos.animatedMessage("Nao e possivel fazer isto!", 3);
				}
			}
		}
	}

	public void preencheMovimentoTxT(String dialog, String nomeBotao, String imgBotao){
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
			if (GameScreen.getStage().getActors().get(i).toString().equals("dialogMovimentos") && dialog.equals("HUDLateral")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

							if (buttonAtual.isDisabled()){

								textureButton = new Texture(Gdx.files.internal(imgBotao));
								imageButton = new TextureRegion(textureButton);
								styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
								styleButton.imageUp = new TextureRegionDrawable(imageButton);
								buttonAtual.setLayoutEnabled(true);
								buttonAtual.setDisabled(false);
								buttonAtual.setStyle(styleButton);
								buttonAtual.setName(nomeBotao);

								break;

							}

						}

					}
				}
			} else if (GameScreen.getStage().getActors().get(i).toString().equals("dialogF1") && dialog.equals("HUDF1")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

							if (buttonAtual.isDisabled()){

								textureButton = new Texture(Gdx.files.internal(imgBotao));
								imageButton = new TextureRegion(textureButton);
								styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
								styleButton.imageUp = new TextureRegionDrawable(imageButton);
								buttonAtual.setLayoutEnabled(true);
								buttonAtual.setDisabled(false);
								buttonAtual.setStyle(styleButton);
								buttonAtual.setName(nomeBotao);

								break;

							}

						}

					}
				}
			} else if (GameScreen.getStage().getActors().get(i).toString().equals("dialogF2") && dialog.equals("HUDF2")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

							if (buttonAtual.isDisabled()){

								textureButton = new Texture(Gdx.files.internal(imgBotao));
								imageButton = new TextureRegion(textureButton);
								styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
								styleButton.imageUp = new TextureRegionDrawable(imageButton);
								buttonAtual.setLayoutEnabled(true);
								buttonAtual.setDisabled(false);
								buttonAtual.setStyle(styleButton);
								buttonAtual.setName(nomeBotao);

								break;

							}

						}

					}
				}
			} else if (GameScreen.getStage().getActors().get(i).toString().equals("dialogFor") && dialog.equals("HUDFor")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							try {						

								ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

								if (buttonAtual.isDisabled()){

									textureButton = new Texture(Gdx.files.internal(imgBotao));
									imageButton = new TextureRegion(textureButton);
									styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
									styleButton.imageUp = new TextureRegionDrawable(imageButton);
									buttonAtual.setLayoutEnabled(true);
									buttonAtual.setDisabled(false);
									buttonAtual.setStyle(styleButton);
									buttonAtual.setName(nomeBotao);

									break;

								}
							} catch (Exception e) {	}

						}

					}
				}
			} else if (GameScreen.getStage().getActors().get(i).toString().equals("dialogIf") && dialog.equals("HUDIF")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							try {						

								ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

								if (buttonAtual.isDisabled()){

									textureButton = new Texture(Gdx.files.internal(imgBotao));
									imageButton = new TextureRegion(textureButton);
									styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
									styleButton.imageUp = new TextureRegionDrawable(imageButton);
									buttonAtual.setLayoutEnabled(true);
									buttonAtual.setDisabled(false);
									buttonAtual.setStyle(styleButton);
									buttonAtual.setName(nomeBotao);

									break;

								}
							} catch (Exception e) {	}

						}

					}
				}
			}
		}
	}

	public void limparMovimento(){
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
			if (GameScreen.getStage().getActors().get(i).toString().equals("dialogMovimentos")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

							textureButton = new Texture(Gdx.files.internal("movimentos/null.png"));
							imageButton = new TextureRegion(textureButton);		
							styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
							styleButton.imageUp = new TextureRegionDrawable(imageButton);
							buttonAtual.setStyle(styleButton);
							buttonAtual.setDisabled(true);
							buttonAtual.setName("");



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

							textureButton = new Texture(Gdx.files.internal("movimentos/null.png"));
							imageButton = new TextureRegion(textureButton);		
							styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
							styleButton.imageUp = new TextureRegionDrawable(imageButton);
							buttonAtual.setStyle(styleButton);
							buttonAtual.setDisabled(true);
							buttonAtual.setName("");

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

							textureButton = new Texture(Gdx.files.internal("movimentos/null.png"));
							imageButton = new TextureRegion(textureButton);		
							styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
							styleButton.imageUp = new TextureRegionDrawable(imageButton);
							buttonAtual.setStyle(styleButton);
							buttonAtual.setDisabled(true);
							buttonAtual.setName("");

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

								textureButton = new Texture(Gdx.files.internal("movimentos/null.png"));
								imageButton = new TextureRegion(textureButton);		
								styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
								styleButton.imageUp = new TextureRegionDrawable(imageButton);
								buttonAtual.setStyle(styleButton);
								buttonAtual.setDisabled(true);
								buttonAtual.setName("");

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

								textureButton = new Texture(Gdx.files.internal("movimentos/null.png"));
								imageButton = new TextureRegion(textureButton);		
								styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
								styleButton.imageUp = new TextureRegionDrawable(imageButton);
								buttonAtual.setStyle(styleButton);
								buttonAtual.setDisabled(true);
								buttonAtual.setName("");

							} catch (Exception e) {	}

						}

					}
				}
			}
		}
	}

	public void limparMovimentoHUDLateral(){
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
			if (GameScreen.getStage().getActors().get(i).toString().equals("dialogMovimentos")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

							textureButton = new Texture(Gdx.files.internal("movimentos/null.png"));
							imageButton = new TextureRegion(textureButton);		
							styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
							styleButton.imageUp = new TextureRegionDrawable(imageButton);
							buttonAtual.setStyle(styleButton);
							buttonAtual.setDisabled(true);
							buttonAtual.setName("");



						}

					}
				}
			}
		}
	}

	public void limparMovimentoHUDF1(){
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {

			if (GameScreen.getStage().getActors().get(i).toString().equals("dialogF1")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

							textureButton = new Texture(Gdx.files.internal("movimentos/null.png"));
							imageButton = new TextureRegion(textureButton);		
							styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
							styleButton.imageUp = new TextureRegionDrawable(imageButton);
							buttonAtual.setStyle(styleButton);
							buttonAtual.setDisabled(true);
							buttonAtual.setName("");

						}

					}
				}
			}
		}
	}

	public void limparMovimentoHUDF2(){
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
			if (GameScreen.getStage().getActors().get(i).toString().equals("dialogF2")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

							textureButton = new Texture(Gdx.files.internal("movimentos/null.png"));
							imageButton = new TextureRegion(textureButton);		
							styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
							styleButton.imageUp = new TextureRegionDrawable(imageButton);
							buttonAtual.setStyle(styleButton);
							buttonAtual.setDisabled(true);
							buttonAtual.setName("");

						}

					}
				}
			}
		}
	}

	public void limparMovimentoHUDFor(){
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
			if (GameScreen.getStage().getActors().get(i).toString().equals("dialogFor")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							try {						

								ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

								textureButton = new Texture(Gdx.files.internal("movimentos/null.png"));
								imageButton = new TextureRegion(textureButton);		
								styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
								styleButton.imageUp = new TextureRegionDrawable(imageButton);
								buttonAtual.setStyle(styleButton);
								buttonAtual.setDisabled(true);
								buttonAtual.setName("");

							} catch (Exception e) {	}

						}

					}
				}
			} 
		}
	}


	public void limparMovimentoHUDIf(){
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
			if (GameScreen.getStage().getActors().get(i).toString().equals("dialogIf")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();

						for (int k = 0; k < tabelaAtual.getCells().size; k++) {

							try {						

								ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();

								textureButton = new Texture(Gdx.files.internal("movimentos/null.png"));
								imageButton = new TextureRegion(textureButton);		
								styleButton = new ImageButtonStyle(Assets.skin.get(ButtonStyle.class));
								styleButton.imageUp = new TextureRegionDrawable(imageButton);
								buttonAtual.setStyle(styleButton);
								buttonAtual.setDisabled(true);
								buttonAtual.setName("");

							} catch (Exception e) {	}


						}
					}
				}
			}
		}
	}

	// verifica de tem algum FOR no IF para posteriormente não permitir colocar um IF no FOR.
	public boolean haveForInIf(){
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
			if (GameScreen.getStage().getActors().get(i).toString().equals("dialogIf")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();
						for (int k = 0; k < tabelaAtual.getCells().size; k++) {
							try {						
								ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();
								if (buttonAtual.getName().equals("FOR")){
									return true;
								}
							} catch (Exception e) {	}
						}
					}
				}
			}
		}
		return false;
	}

	// verifica de tem algum IF no FOR para posteriormente não permitir colocar um FOR no IF.
	// se tem algum if no for!
	public boolean haveIfInFor(){
		for (int i = 0; i < GameScreen.getStage().getActors().size; i++) {
			if (GameScreen.getStage().getActors().get(i).toString().equals("dialogFor")){
				Dialog dialogAtual = (Dialog) GameScreen.getStage().getActors().get(i);
				for (int j = 0; j < dialogAtual.getCells().size; j++) {
					if (dialogAtual.getCells().get(j).getActor().toString().equals("tabelaMovimentos")){
						Table tabelaAtual = (Table) dialogAtual.getCells().get(j).getActor();
						for (int k = 0; k < tabelaAtual.getCells().size; k++) {
							try {						
								ImageButton buttonAtual = (ImageButton) tabelaAtual.getCells().get(k).getActor();
								if (buttonAtual.getName().equals("IF")){
									return true;
								}
							} catch (Exception e) {	}
						}
					}
				}
			}
		}
		return false;
	}

	// desabilitar os HUDS durante o movimento do personagem
	public void desabilitaHudMovimentos(){
		GameScreen.hudF1.dialogMovimentos.setTouchable(Touchable.disabled);
		GameScreen.hudF2.dialogMovimentos.setTouchable(Touchable.disabled);
		GameScreen.hudFor.dialogMovimentos.setTouchable(Touchable.disabled);
		GameScreen.hudIf.dialogMovimentos.setTouchable(Touchable.disabled);
		GameScreen.hudLateral.dialogMovimentos.setTouchable(Touchable.disabled);
		GameScreen.hudMovimentos.tableMovimentos.setTouchable(Touchable.disabled);
		GameScreen.btnPrincipais.executarButton.setTouchable(Touchable.disabled);
		GameScreen.btnPrincipais.passarButton.setTouchable(Touchable.disabled);
	}

	// habilitar os HUDS durante o movimento do personagem
	public void habilitaHudMovimentos(){
		GameScreen.hudF1.dialogMovimentos.setTouchable(Touchable.enabled);
		GameScreen.hudF2.dialogMovimentos.setTouchable(Touchable.enabled);
		GameScreen.hudFor.dialogMovimentos.setTouchable(Touchable.enabled);
		GameScreen.hudIf.dialogMovimentos.setTouchable(Touchable.enabled);
		GameScreen.hudLateral.dialogMovimentos.setTouchable(Touchable.enabled);
		GameScreen.hudMovimentos.tableMovimentos.setTouchable(Touchable.enabled);
		GameScreen.btnPrincipais.executarButton.setTouchable(Touchable.enabled);
		GameScreen.btnPrincipais.passarButton.setTouchable(Touchable.enabled);
	}

}
