package entities;

import java.io.File;

import main.Assets;
import screens.GameScreen;
import screens.LoadScreen;
import screens.LoginScreen;
import screens.MainMenuScreen;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import database.Records;

public class LevelUp {

	public static boolean isUp; 
	
	public LevelUp() {
		
		Records record = new Records();
		record.setId(GameScreen.usuario.getId());
		record.setFase(LoadScreen.faseAtual);
		record.setPontos(BotoesPrincipais.getPoints());
		if (!record.checkRecords()){
			record.cadastrarRecords();
		}
		record.atualizaRecords();
		
		isUp = true;
		new Dialog("Parabens", Assets.skin, "dialog") {
			protected void result (Object object) {
				if (object.equals(true)){
					File[] fases = LoadScreen.getFases();
					try {
						if ((new File(fases[Integer.parseInt((Integer.parseInt(LoadScreen.faseAtual)+1)+"")-1].getAbsolutePath()+"/map/map.tmx")).exists()){
							LoadScreen.faseAtual = (Integer.parseInt(LoadScreen.faseAtual)+1)+"";
							LoadScreen.setMapConfig(LoadScreen.faseAtual, "ableToPlayer", "true");
							LoadScreen.diretorioMapLoad = fases[Integer.parseInt(LoadScreen.faseAtual)-1].getAbsolutePath()+"/map/map.tmx";
							GameScreen.getGame().setScreen(new GameScreen(GameScreen.getGame()));
						} else {
							new Dialog("Erro", Assets.skin, "dialog") {
								protected void result (Object object) {
									GameScreen.getGame().setScreen(new MainMenuScreen(GameScreen.getGame()));
									LoginScreen.themeMusic.play();
								}
							}.text("   Nao foram encontradas mais fases a partir desta!   ").button("Inicio", true).key(Keys.ENTER, true)
							.key(Keys.ESCAPE, false).show(GameScreen.getStage()).setMovable(false);
						}
					} catch (Exception e) {
						new Dialog("Erro", Assets.skin, "dialog") {
							protected void result (Object object) {
								GameScreen.getGame().setScreen(new MainMenuScreen(GameScreen.getGame()));
								LoginScreen.themeMusic.play();
							}
						}.text("   Nao foram encontradas mais fases a partir desta!   ").button("Inicio", true).key(Keys.ENTER, true)
						.key(Keys.ESCAPE, false).show(GameScreen.getStage()).setMovable(false);
					}
				} else {
					GameScreen.getGame().setScreen(new GameScreen(GameScreen.getGame()));
				}
			}
		}.text("    Voce alcancou seu objetivo, e liberou a proxima fase!   \n" +
				"\n" +
				"   Relatorio:   \n" +
				"   Quantidade de rodadas: "+BotoesPrincipais.quantidadeRodadas+"   \n" +
				"   Quantidade de passos: "+BotoesPrincipais.quantidadeDePassos+"   \n" +
				"   Quantidade de comandos executados no Movimentos: "+BotoesPrincipais.quantidadeComandosMovimentos+"   \n" +
				"   Quantidade de comandos executados no F1: "+BotoesPrincipais.quantidadeComandosF1+"   \n" +
				"   Quantidade de comandos executados no F2: "+BotoesPrincipais.quantidadeComandosF2+"   \n" +
				"   Quantidade de comandos executados no For: "+BotoesPrincipais.quantidadeComandosFor+"   \n" +
				"   Quantidade de comandos executados no If: "+BotoesPrincipais.quantidadeComandosIf+"   \n" +
				"\n" +
				"   Pontuacao final: "+BotoesPrincipais.getPoints()+"   " +
				"").button("Proximo", true).button("Reiniciar", false).key(Keys.ENTER, true)
		.key(Keys.ESCAPE, false).show(GameScreen.getStage()).setMovable(false);
		BotoesPrincipais.quantidadeRodadas = 0;
		BotoesPrincipais.quantidadeDePassos = 0;
		BotoesPrincipais.quantidadeComandosMovimentos = 0;
		BotoesPrincipais.quantidadeComandosF1 = 0;
		BotoesPrincipais.quantidadeComandosF2 = 0;
		BotoesPrincipais.quantidadeComandosFor = 0;
		BotoesPrincipais.quantidadeComandosIf = 0;
	}
}
