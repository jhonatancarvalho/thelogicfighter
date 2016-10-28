package entities;

import java.util.Properties;

import screens.GameScreen;
import screens.LoadScreen;

public class ProximaFase {
	
	boolean needInimigos;
	boolean needItens;
	
	public ProximaFase(){
		Properties propriedadeFase = LoadScreen.getMapConfig(LoadScreen.faseAtual);
		if (propriedadeFase.getProperty("clearInimigos").equals("true")){
			needInimigos = true;
		} else {
			needInimigos = false;
		}
		if (propriedadeFase.getProperty("clearItens").equals("true")){
			needItens = true;
		} else {
			needItens = false;
		}
	}
	
	// verifica se o jogador completou o objetivo
	public boolean goNextFase(){
		if (GameScreen.itens.getQuantItem()>0 && needItens){
			return false;
		}
		if (GameScreen.listaInimigos.size()>0 && needInimigos){
			return false;
		}	
		return true;
	}

}
