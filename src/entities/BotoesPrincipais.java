package entities;

import java.util.ArrayList;

import main.Assets;
import screens.GameScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;

public class BotoesPrincipais {

	TextButton executarButton;
	TextButton passarButton;

	public static int quantidadeDePassos;
	public static int quantidadeRodadas;
	public static int quantidadeComandosMovimentos;
	public static int quantidadeComandosF1;
	public static int quantidadeComandosF2;
	public static int quantidadeComandosFor;
	public static int quantidadeComandosIf;

	public static ArrayList<ImageButton> movimentosHUDLateral;
	public static ArrayList<ImageButton> movimentosHUDF1;
	public static ArrayList<ImageButton> movimentosHUDF2;
	public static ArrayList<ImageButton> movimentosHUDFor;
	public static ArrayList<ImageButton> movimentosHUDIf;

	public static ArrayList<String> txtAlgoritmos;

	public void LoadBotoesPrincipaisTexture() {

		quantidadeRodadas = 0;
		quantidadeDePassos = 0;
		quantidadeComandosMovimentos = 0;
		quantidadeComandosF1 = 0;
		quantidadeComandosF2 = 0;
		quantidadeComandosFor = 0;
		quantidadeComandosIf = 0;

		movimentosHUDLateral = new ArrayList<>();
		movimentosHUDF1 = new ArrayList<>();
		movimentosHUDF2 = new ArrayList<>();
		movimentosHUDFor = new ArrayList<>();
		movimentosHUDIf = new ArrayList<>();

		executarButton = new TextButton("Executar", Assets.skin);
		executarButton.setBounds(GameScreen.w-186, 34, 186, 30);

		passarButton = new TextButton("Encerrar Turno", Assets.skin);
		passarButton.setBounds(GameScreen.w-186, 2, 186, 30);

		UpdateBotoesPrincipais();

		GameScreen.getStage().addActor(executarButton);
		GameScreen.getStage().addActor(passarButton);

	}

	public boolean checkForInIfTxt(){
		String texto = TextAlgoritmos.txtArea.getText().replaceAll(" ","").trim().toLowerCase();
		String linhas[] = texto.split("\n"); 
		int i = 0;
		while (i < linhas.length) {
			if (linhas[i].startsWith("if")){
				while (linhas[i].equals("}") && i < linhas.length) {
					if (linhas[i].startsWith("for")){
						return true;
					}
					i++;
				}
			}		
			i++;			
		}
		return false;
	}

	public boolean checkIfInForTxt(){
		String texto = TextAlgoritmos.txtArea.getText().replaceAll(" ","").trim().toLowerCase();
		String linhas[] = texto.split("\n"); 
		int i = 0;
		while (i < linhas.length) {
			if (linhas[i].startsWith("for")){
				while (linhas[i].equals("}") && i < linhas.length) {
					if (linhas[i].startsWith("if")){
						return true;
					}
					i++;
				}
			}		
			i++;			
		}
		return false;
	}

	public boolean checkForLine(String string) {
		try {

			String partInicio = "for(inti=";
			if (!string.startsWith(partInicio)){
				return false;
			}

			String partFim = ";i++){";
			if (!string.endsWith(partFim)){
				return false;
			}

			String part = string.substring(partInicio.length(), string.length()-partFim.length());

			if (part.contains(";i<=")){
				part = part.replaceAll(";i<=", "__");
			} else{
				return false;
			}

			String numeros[] = part.split("__");  
			String num1 = numeros[0];
			String num2 = numeros[1];

			Integer.parseInt(num1);
			Integer.parseInt(num2);

			HUDFor.txtFor1.setText(num1);
			HUDFor.txtFor2.setText(num2);

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean checkIfLine(String string) {
		for (int i = 0; i < HUDIF.dropdown.getItems().size; i++) {
			String itemAtual = HUDIF.dropdown.getItems().get(i).toString().toLowerCase();
			if (string.endsWith("if(player.proximopasso()=='"+itemAtual+"'{")) {
				return true;
			}
		}
		return false;
	}

	public void UpdateBotoesPrincipais() {

		executarButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				if (GameScreen.rodada == 1 && GameScreen.listaInimigos.size() > 0){
					HUDMovimentos.animatedMessage("Aguarde o fim do movimento inimigo!", 5);
					return true;
				}
				ArrayList<String> movimentosValidos = new ArrayList<>();
				movimentosValidos.add("frente();");
				movimentosValidos.add("direita();");
				movimentosValidos.add("esquerda();");
				movimentosValidos.add("pegar();");
				movimentosValidos.add("atacar();");
				movimentosValidos.add("defender();");
				movimentosValidos.add("f1();");
				movimentosValidos.add("f2();");

				//ir verificando o texto escrito e colocando o botão de acordo
				//////////////////////////////////////////////////////////////
				int linhaErro = 0;
				String texto = TextAlgoritmos.txtArea.getText().replaceAll(" ","").trim().toLowerCase();
				String linhas[] = texto.split("\n");  
				boolean checkBreak = false;
				int j = 0;

				ArrayList<String> movimentosMain = new ArrayList<>();

				ArrayList<String> movimentosF1 = new ArrayList<>();
				ArrayList<String> movimentosF2 = new ArrayList<>();

				ArrayList<MovimentosFor> movimentosFor = new ArrayList<>();
				MovimentosFor forMov = new MovimentosFor();

				ArrayList<MovimentosIf> movimentosIf= new ArrayList<>();
				MovimentosIf ifMov = new MovimentosIf();

				boolean checkF1 = false;
				boolean checkF2 = false;
				boolean checkFim = false;
				for (int i = 0; i < linhas.length; i++) {
					if (linhas[i].equals("f1();")){
						checkF1 = true;
					}
					if (linhas[i].equals("f2();")){
						checkF2 = true;
					}
				}	

				if (!texto.equals("")){
					while (!checkBreak &&  j < linhas.length) {			

						if (!checkFim){
							while (!checkBreak && !linhas[j].equals("}fim.")) {
								if (!linhas[j].equals("")){	
									if(j==0){	
										if (linhas[j].equals("inicio{")){
											j++;
										} else {
											HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar [inicio {].", 5);
											linhaErro = j;
											checkBreak = true;
											break;
										}
									} 

									if (j>0 &&  j < linhas.length){

										if (linhas[j].startsWith("if")){
											if (checkIfLine(linhas[j])){
												movimentosMain.add("IF");
												ifMov = new MovimentosIf();
												ifMov.setCabecalho(linhas[j]);
												j++;
												while (!checkBreak && !linhas[j].equals("}")) {
													if (linhas[j].startsWith("if")){
														HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o IF!", 5);
														linhaErro = j;
														checkBreak = true;
														break;
													} else if (linhas[j].startsWith("for")){
														if (!checkIfInForTxt()){
															if (checkForLine(linhas[j])){
																forMov = new MovimentosFor();
																forMov.setCabecalho(linhas[j]);
																ifMov.movimentos.add("FOR");
																j++;
																while (!checkBreak &&  !linhas[j].equals("}")) {
																	if (linhas[j].startsWith("if")){
																		HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o IF!", 5);																	
																		linhaErro = j;
																		checkBreak = true;
																		break;
																	} else if (linhas[j].startsWith("for")){
																		HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o FOR!", 5);
																		linhaErro = j;
																		checkBreak = true;
																		break;
																	} else if (movimentosValidos.contains(linhas[j])){
																		forMov.movimentos.add(linhas[j]);
																		j++;			
																	}  else if (!linhas[j].equals("}")) {	
																		HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", sentenca invalida! Esperava encontrar o [}], fim do FOR.", 5);							
																		linhaErro = j;
																		checkBreak = true;
																		break;
																	}										

																	if (j >= linhas.length && !checkBreak){
																		HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar o [}], fim do FOR.", 5);
																		linhaErro = j;
																		checkBreak = true;
																		break;
																	}
																}	
																movimentosFor.add(forMov);
																j++;
															} else {
																HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", o FOR não esta instanciado corretamente!", 5);
																linhaErro = j;
																checkBreak = true;
																break;
															}
														} else {
															HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o FOR!", 5);
															linhaErro = j;
															checkBreak = true;
															break;
														}
													} else if (movimentosValidos.contains(linhas[j])){
														ifMov.movimentos.add(linhas[j]);
														j++;			
													}  else if (!linhas[j].equals("}")) {	
														HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", sentenca invalida! Esperava encontrar o [}], fim do IF.", 5);
														linhaErro = j;
														checkBreak = true;
														break;
													}

													if (j >= linhas.length && !checkBreak){
														HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar o [}], fim do IF.", 5);
														linhaErro = j;
														checkBreak = true;
														break;
													}
												}
												movimentosIf.add(ifMov);
												j++;					

											} else {
												HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", o IF não esta instanciado corretamente!", 5);
												linhaErro = j;
												checkBreak = true;
												break;
											}
										} else if (linhas[j].startsWith("for")){
											if (checkForLine(linhas[j])){	
												forMov = new MovimentosFor();
												forMov.setCabecalho(linhas[j]);
												movimentosMain.add("FOR");
												j++;
												while (!checkBreak &&  !linhas[j].equals("}")) {
													if (linhas[j].startsWith("for")){
														HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o FOR!", 5);
														linhaErro = j;
														checkBreak = true;
														break;
													} else if (linhas[j].startsWith("if")){
														if (!checkForInIfTxt()){		
															forMov.movimentos.add("IF");
															ifMov = new MovimentosIf();
															ifMov.setCabecalho(linhas[j]);
															j++;
															while (!checkBreak &&  !linhas[j].equals("}")) {
																if (linhas[j].startsWith("if")){
																	HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o IF!", 5);
																	linhaErro = j;
																	checkBreak = true;
																	break;
																} else if (linhas[j].startsWith("for")){
																	HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o FOR!", 5);
																	linhaErro = j;
																	checkBreak = true;
																	break;
																} else if (movimentosValidos.contains(linhas[j])){
																	ifMov.movimentos.add(linhas[j]);
																	j++;			
																}  else if (!linhas[j].equals("}")) {		
																	HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", sentenca invalida! Esperava encontrar o [}], fim do IF.", 5);
																	linhaErro = j;
																	checkBreak = true;
																	break;
																}										

																if (j >= linhas.length && !checkBreak){
																	HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar o [}], fim do IF.", 5);
																	linhaErro = j;
																	checkBreak = true;
																	break;
																}
															}
															movimentosIf.add(ifMov);
															j++;

														} else {
															HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o IF!", 5);
															linhaErro = j;
															checkBreak = true;
															break;
														}														
													} else if (movimentosValidos.contains(linhas[j])){
														forMov.movimentos.add(linhas[j]);
														j++;			
													} else if (!linhas[j].equals("}")) {	
														HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", sentenca invalida! Esperava encontrar o [}], fim do FOR.", 5);
														linhaErro = j;
														checkBreak = true;
														break;
													}										

													if (j >= linhas.length && !checkBreak){
														HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar o [}], fim do FOR.", 5);
														linhaErro = j;
														checkBreak = true;
														break;
													}
												}
												movimentosFor.add(forMov);
												j++;
											} else {
												HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", o FOR não esta instanciado corretamente!", 5);
												linhaErro = j;
												checkBreak = true;
												break;
											}
										} else if (movimentosValidos.contains(linhas[j])){
											movimentosMain.add(linhas[j]);
											j++;
										} else if (linhas[j].equals("")){
											j++;
										} else if (!linhas[j].equals("}fim.")){
											HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", sentenca invalida!", 5);
											linhaErro = j;
											checkBreak = true;
											break;
										}

										if (j >= linhas.length && !checkBreak){
											HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar o [} fim.].", 5);
											linhaErro = j;
											checkBreak = true;
											break;
										}		
									} else {
										HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar o [} fim.].", 5);
										linhaErro = j;
										checkBreak = true;
										break;
									}
								} else {
									j++;
								}					
							}
							checkFim = true;
						}


						if (j < linhas.length && checkF1 && linhas[j].equals("f1(){")){
							if (linhas[j].equals("f1(){")){
								j++;
								while (!checkBreak && !linhas[j].equals("}.")) {
									if (linhas[j].startsWith("if")){
										if (checkIfLine(linhas[j])){
											movimentosF1.add("IF");
											ifMov = new MovimentosIf();
											ifMov.setCabecalho(linhas[j]);
											j++;
											while (!checkBreak && !linhas[j].equals("}")) {
												if (linhas[j].startsWith("if")){
													HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o IF!", 5);
													linhaErro = j;
													checkBreak = true;
													break;
												} else if (linhas[j].startsWith("for")){
													if (!checkIfInForTxt()){
														if (checkForLine(linhas[j])){	
															forMov = new MovimentosFor();
															forMov.setCabecalho(linhas[j]);
															ifMov.movimentos.add("FOR");
															j++;
															while (!checkBreak &&  !linhas[j].equals("}")) {
																if (linhas[j].startsWith("if")){
																	HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o IF!", 5);
																	linhaErro = j;
																	checkBreak = true;
																	break;
																} else if (linhas[j].startsWith("for")){
																	HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o FOR!", 5);
																	linhaErro = j;
																	checkBreak = true;
																	break;
																} else if (movimentosValidos.contains(linhas[j])){
																	forMov.movimentos.add(linhas[j]);
																	j++;			
																}  else if (!linhas[j].equals("}")) {		
																	HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", sentenca invalida! Esperava encontrar o [}], fim do FOR.", 5);
																	linhaErro = j;
																	checkBreak = true;
																	break;
																}										

																if (j >= linhas.length && !checkBreak){
																	HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar o [}], fim do FOR.", 5);
																	linhaErro = j;
																	checkBreak = true;
																	break;
																}
															}
															movimentosFor.add(forMov);
															j++;
														} else {
															HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", o FOR não esta instanciado corretamente!", 5);
															linhaErro = j;
															checkBreak = true;
															break;
														}
													} else {
														HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o FOR!", 5);
														linhaErro = j;
														checkBreak = true;
														break;
													}
												} else if (movimentosValidos.contains(linhas[j])){
													ifMov.movimentos.add(linhas[j]);
													j++;			
												}  else if (!linhas[j].equals("}")) {					
													HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", sentenca invalida! Esperava encontrar o [}], fim do IF.", 5);
													linhaErro = j;
													checkBreak = true;
													break;
												}

												if (j >= linhas.length && !checkBreak){
													HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar o [}], fim do IF.", 5);
													linhaErro = j;
													checkBreak = true;
													break;
												}
											}
											movimentosIf.add(ifMov);
											j++;					

										} else {
											HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", o IF não esta instanciado corretamente!", 5);
											linhaErro = j;
											checkBreak = true;
											break;
										}
									} else if (linhas[j].startsWith("for")){
										if (checkForLine(linhas[j])){
											forMov = new MovimentosFor();
											forMov.setCabecalho(linhas[j]);
											movimentosF1.add("FOR");
											j++;
											while (!checkBreak &&  !linhas[j].equals("}")) {
												if (linhas[j].startsWith("for")){
													HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o FOR!", 5);
													linhaErro = j;
													checkBreak = true;
													break;
												} else if (linhas[j].startsWith("if")){
													if (!checkForInIfTxt()){		
														forMov.movimentos.add("IF");
														ifMov = new MovimentosIf();
														ifMov.setCabecalho(linhas[j]);
														j++;
														while (!checkBreak &&  !linhas[j].equals("}")) {
															if (linhas[j].startsWith("if")){
																HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o IF!", 5);
																linhaErro = j;
																checkBreak = true;
																break;
															} else if (linhas[j].startsWith("for")){
																HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o FOR!", 5);
																linhaErro = j;
																checkBreak = true;
																break;
															} else if (movimentosValidos.contains(linhas[j])){
																ifMov.movimentos.add(linhas[j]);
																j++;			
															}  else if (!linhas[j].equals("}")) {		
																HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", sentenca invalida! Esperava encontrar o [}], fim do IF.", 5);
																linhaErro = j;
																checkBreak = true;
																break;
															}										

															if (j >= linhas.length && !checkBreak){
																HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar o [}], fim do IF.", 5);
																linhaErro = j;
																checkBreak = true;
																break;
															}
														}
														movimentosIf.add(ifMov);
														j++;

													} else {
														HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o IF!", 5);
														linhaErro = j;
														checkBreak = true;
														break;
													}														
												} else if (movimentosValidos.contains(linhas[j])){
													forMov.movimentos.add(linhas[j]);
													j++;			
												} else if (!linhas[j].equals("}")) {		
													HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", sentenca invalida! Esperava encontrar o [}], fim do FOR.", 5);
													linhaErro = j;
													checkBreak = true;
													break;
												}										

												if (j >= linhas.length && !checkBreak){
													HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar o [}], fim do FOR.", 5);
													linhaErro = j;
													checkBreak = true;
													break;
												}
											}	
											movimentosFor.add(forMov);
											j++;
										} else {
											HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", o FOR não esta instanciado corretamente!", 5);
											linhaErro = j;
											checkBreak = true;
											break;
										}
									} else if (movimentosValidos.contains(linhas[j])){
										movimentosF1.add(linhas[j]);
										j++;
									} else if (!linhas[j].equals("}.")){
										HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", sentenca invalida!", 5);
										linhaErro = j;
										checkBreak = true;
										break;
									}

									if (j >= linhas.length && !checkBreak){
										HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar o [}.].", 5);
										linhaErro = j;
										checkBreak = true;
										break;
									}
								}
							}
							checkF1 = false;
						}

						if (j < linhas.length && checkF2 && linhas[j].equals("f2(){")){
							if (linhas[j].equals("f2(){")){
								j++;
								while (!checkBreak && !linhas[j].equals("}.")) {
									if (linhas[j].startsWith("if")){
										if (checkIfLine(linhas[j])){
											movimentosF2.add("IF");
											ifMov = new MovimentosIf();
											ifMov.setCabecalho(linhas[j]);
											j++;
											while (!checkBreak && !linhas[j].equals("}")) {
												if (linhas[j].startsWith("if")){
													HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o IF!", 5);
													linhaErro = j;
													checkBreak = true;
													break;
												} else if (linhas[j].startsWith("for")){
													if (!checkIfInForTxt()){
														if (checkForLine(linhas[j])){	
															forMov = new MovimentosFor();
															forMov.setCabecalho(linhas[j]);
															ifMov.movimentos.add("FOR");
															j++;
															while (!checkBreak &&  !linhas[j].equals("}")) {
																if (linhas[j].startsWith("if")){
																	HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o IF!", 5);
																	linhaErro = j;
																	checkBreak = true;
																	break;
																} else if (linhas[j].startsWith("for")){
																	HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o FOR!", 5);
																	linhaErro = j;
																	checkBreak = true;
																	break;
																} else if (movimentosValidos.contains(linhas[j])){
																	forMov.movimentos.add(linhas[j]);
																	j++;			
																}  else if (!linhas[j].equals("}")) {		
																	HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", sentenca invalida! Esperava encontrar o [}], fim do FOR.", 5);
																	linhaErro = j;
																	checkBreak = true;
																	break;
																}										

																if (j >= linhas.length && !checkBreak){
																	HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar o [}], fim do FOR.", 5);
																	linhaErro = j;
																	checkBreak = true;
																	break;
																}
															}
															movimentosFor.add(forMov);
															j++;
														} else {
															HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", o FOR não esta instanciado corretamente!", 5);
															linhaErro = j;
															checkBreak = true;
															break;
														}
													} else {
														HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o FOR!", 5);
														linhaErro = j;
														checkBreak = true;
														break;
													}
												} else if (movimentosValidos.contains(linhas[j])){
													ifMov.movimentos.add(linhas[j]);
													j++;			
												}  else if (!linhas[j].equals("}")) {					
													HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", sentenca invalida! Esperava encontrar o [}], fim do IF.", 5);
													linhaErro = j;
													checkBreak = true;
													break;
												}

												if (j >= linhas.length && !checkBreak){
													HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar o [}], fim do IF.", 5);
													linhaErro = j;
													checkBreak = true;
													break;
												}
											}
											movimentosIf.add(ifMov);
											j++;					

										} else {
											HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", o IF não esta instanciado corretamente!", 5);
											linhaErro = j;
											checkBreak = true;
											break;
										}
									} else if (linhas[j].startsWith("for")){
										if (checkForLine(linhas[j])){
											forMov = new MovimentosFor();
											forMov.setCabecalho(linhas[j]);
											movimentosF2.add("FOR");
											j++;
											while (!checkBreak &&  !linhas[j].equals("}")) {
												if (linhas[j].startsWith("for")){
													HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o FOR!", 5);
													linhaErro = j;
													checkBreak = true;
													break;
												} else if (linhas[j].startsWith("if")){
													if (!checkForInIfTxt()){		
														forMov.movimentos.add("IF");
														ifMov = new MovimentosIf();
														ifMov.setCabecalho(linhas[j]);
														j++;
														while (!checkBreak &&  !linhas[j].equals("}")) {
															if (linhas[j].startsWith("if")){
																HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o IF!", 5);
																linhaErro = j;
																checkBreak = true;
																break;
															} else if (linhas[j].startsWith("for")){
																HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o FOR!", 5);
																linhaErro = j;
																checkBreak = true;
																break;
															} else if (movimentosValidos.contains(linhas[j])){
																ifMov.movimentos.add(linhas[j]);
																j++;			
															}  else if (!linhas[j].equals("}")) {		
																HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", sentenca invalida! Esperava encontrar o [}], fim do IF.", 5);
																linhaErro = j;
																checkBreak = true;
																break;
															}										

															if (j >= linhas.length && !checkBreak){
																HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar o [}], fim do IF.", 5);
																linhaErro = j;
																checkBreak = true;
																break;
															}
														}
														movimentosIf.add(ifMov);
														j++;

													} else {
														HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", não foi possivel instanciar o IF!", 5);
														linhaErro = j;
														checkBreak = true;
														break;
													}														
												} else if (movimentosValidos.contains(linhas[j])){
													forMov.movimentos.add(linhas[j]);
													j++;			
												} else if (!linhas[j].equals("}")) {		
													HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", sentenca invalida! Esperava encontrar o [}], fim do FOR.", 5);
													linhaErro = j;
													checkBreak = true;
													break;
												}										

												if (j >= linhas.length && !checkBreak){
													HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar o [}], fim do FOR.", 5);
													linhaErro = j;
													checkBreak = true;
													break;
												}
											}	
											movimentosFor.add(forMov);
											j++;
										} else {
											HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", o FOR não esta instanciado corretamente!", 5);
											linhaErro = j;
											checkBreak = true;
											break;
										}
									} else if (movimentosValidos.contains(linhas[j])){
										movimentosF2.add(linhas[j]);
										j++;
									} else if (!linhas[j].equals("}.")){
										HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", sentenca invalida!", 5);
										linhaErro = j;
										checkBreak = true;
										break;
									}

									if (j >= linhas.length && !checkBreak){
										HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", esperava encontrar o [}.].", 5);
										linhaErro = j;
										checkBreak = true;
										break;
									}
								}
							}
							checkF2 = false;
						}

						if (j >= linhas.length && !checkBreak && !checkF1 && linhas[j].equals("f1(){")){
							linhaErro = j;
							HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", duplicidade de F1.", 5);
						}
						if (j >= linhas.length && !checkBreak && !checkF2 && linhas[j].equals("f2(){")){
							linhaErro = j;
							HUDMovimentos.animatedMessage("Erro linha: "+(j+1)+", duplicidade de F2.", 5);
						}

						j++;

					}
				}

				if (checkIdenticFor(movimentosFor)){
					if (checkIdenticIf(movimentosIf)){

						if (linhaErro<=0){

							if (movimentosMain.size()>0){
								GameScreen.hudMovimentos.limparMovimentoHUDLateral();
								for (int i = 0; i < movimentosMain.size(); i++) {
									GameScreen.hudMovimentos.preencheMovimentoTxT("HUDLateral", getAcao(movimentosMain.get(i)),  getImagem(movimentosMain.get(i)));
								}
							}

							if (movimentosF1.size()>0){
								GameScreen.hudMovimentos.limparMovimentoHUDF1();
								for (int i = 0; i < movimentosF1.size(); i++) {
									GameScreen.hudMovimentos.preencheMovimentoTxT("HUDF1", getAcao(movimentosF1.get(i)),  getImagem(movimentosF1.get(i)));
								}
							}

							if (movimentosF2.size()>0){
								GameScreen.hudMovimentos.limparMovimentoHUDF2();
								for (int i = 0; i < movimentosF2.size(); i++) {
									GameScreen.hudMovimentos.preencheMovimentoTxT("HUDF2", getAcao(movimentosF2.get(i)),  getImagem(movimentosF2.get(i)));
								}
							}

							if (movimentosIf.size()>0){
								GameScreen.hudMovimentos.limparMovimentoHUDIf();
								for (int k = 0; k < movimentosIf.get(0).getMovimentos().size(); k++) {
									GameScreen.hudMovimentos.preencheMovimentoTxT("HUDIF", getAcao(movimentosIf.get(0).getMovimentos().get(k)),  getImagem(movimentosIf.get(0).getMovimentos().get(k)));
								}
							}

							if (movimentosFor.size()>0){
								GameScreen.hudMovimentos.limparMovimentoHUDFor();
								for (int k = 0; k < movimentosFor.get(0).getMovimentos().size(); k++) {
									GameScreen.hudMovimentos.preencheMovimentoTxT("HUDFor", getAcao(movimentosFor.get(0).getMovimentos().get(k)),  getImagem(movimentosFor.get(0).getMovimentos().get(k)));
								}
							}

							getMovimentacoesHUD();

						} else {
							selectLineError(linhaErro);
						}


					} else {
						HUDMovimentos.animatedMessage("Erro: Existem IF's declarados de forma diferente!", 5);
					}
				} else {
					HUDMovimentos.animatedMessage("Erro: Existem FOR's declarados de forma diferente!", 5);
				}


				return true;
			}

		});

		passarButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				HUDFor.removeFocusOnForTxts();

				if (GameScreen.rodada == 1 && GameScreen.listaInimigos.size() > 0){
					HUDMovimentos.animatedMessage("Aguarde o fim do movimento inimigo!", 5);
					return true;
				}

				GameScreen.rodada = 1;

				return true;
			}

		});
	}

	private void selectLineError(int linhaErro){
		String textoOriginal = TextAlgoritmos.txtArea.getText();
		int selectionStart = 0;
		int selectionEnd = 0;
		String linhas[] = textoOriginal.split("\n");  
		for (int i = 0; i < linhas.length; i++) {
			if (i == linhaErro){
				selectionStart = selectionStart +linhaErro;
				selectionEnd = selectionStart + linhas[i].length();
				break;
			}
			selectionStart = selectionStart + linhas[i].length();
		}
		TextAlgoritmos.txtArea.setSelection(selectionStart, selectionEnd);

	}

	public String getImagem(String proc){
		String img = "";
		if (proc.equals("frente();")){
			img = "movimentos/cima.png";
		} else if (proc.equals("direita();")){
			img = "movimentos/girar_dir.png";
		} else if (proc.equals("esquerda();")){
			img = "movimentos/girar_esq.png";
		} else if (proc.equals("pegar();")){
			img = "movimentos/mao.png";
		} else if (proc.equals("defender();")){
			img = "movimentos/escudo.png";
		} else if (proc.equals("atacar();")){
			img = "movimentos/espada.png";
		} else if (proc.equals("f1();")){
			img = "movimentos/f1.png";
		} else if (proc.equals("f2();")){
			img = "movimentos/f2.png";
		} else if (proc.equals("IF")){
			img = "movimentos/if.png";
		} else if (proc.equals("FOR")){
			img = "movimentos/for.png";
		}
		return img;
	}

	public String getAcao(String proc){
		String acao = "";
		if (proc.equals("frente();")){
			acao = "FRENTE";
		} else if (proc.equals("direita();")){
			acao = "DIREITA";
		} else if (proc.equals("esquerda();")){
			acao = "ESQUERDA";
		} else if (proc.equals("pegar();")){
			acao = "PEGAR";
		} else if (proc.equals("defender();")){
			acao = "DEFENDER";
		} else if (proc.equals("atacar();")){
			acao = "ATACAR";
		} else if (proc.equals("f1();")){
			acao = "F1";
		} else if (proc.equals("f2();")){
			acao = "F2";
		} else if (proc.equals("IF")){
			acao = "IF";
		} else if (proc.equals("FOR")){
			acao = "FOR";
		}
		return acao;
	}

	public boolean checkIdenticFor(ArrayList<MovimentosFor> movimentosFor){
		for (int i = 0; i < movimentosFor.size(); i++) {
			if (!movimentosFor.get(0).equals(movimentosFor.get(i))){
				return false;
			}
		}
		return true;
	}

	public boolean checkIdenticIf(ArrayList<MovimentosIf> movimentosIf){
		for (int i = 0; i < movimentosIf.size(); i++) {
			if (!movimentosIf.get(0).equals(movimentosIf.get(i))){
				return false;
			}
		}
		return true;
	}

	/*quantidadeDePassos = 0;
	quantidadeComandosMovimentos = 0;
	quantidadeComandosF1 = 0;
	quantidadeComandosF2 = 0;
	quantidadeComandosFor = 0;
	quantidadeComandosIf = 0;*/

	public static void executaMovimentos(){
		HUDFor.removeFocusOnForTxts();
		GameScreen.hudMovimentos.desabilitaHudMovimentos();
		new Thread() {
			@Override 
			public void run() { 
				try {
					if (movimentosHUDLateral.size()>0){
						quantidadeRodadas++;
					}
					for (int i = 0; i < movimentosHUDLateral.size(); i++) {		
						if (Player.movimentacaoExcessiva() || Player.isDead || LevelUp.isUp){
							break;
						} else {
							quantidadeComandosMovimentos++;
							GameScreen.hudMovimentos.setMovimentoAtual(movimentosHUDLateral.get(i).getName());
							if (movimentosHUDLateral.get(i).getName().equals("FRENTE")){
								quantidadeDePassos++;
								GameScreen.player.setCountMovimento(GameScreen.player.position.x+32.0f, GameScreen.player.position.x-32.0f, GameScreen.player.position.y+32.0f, GameScreen.player.position.y-32.0f);
								Player.addSp(-2);
								movimentosHUDLateral.get(i).setColor(Color.YELLOW);
								Thread.sleep(1300);
								movimentosHUDLateral.get(i).setColor(Color.WHITE);
							} else if (movimentosHUDLateral.get(i).getName().equals("F1")){
								GameScreen.player.setCountMovimento(0, 0, 0, 0);
								movimentosHUDLateral.get(i).setColor(Color.GREEN);	
								executaF1();
								movimentosHUDLateral.get(i).setColor(Color.WHITE);
							} else if (movimentosHUDLateral.get(i).getName().equals("F2")){
								GameScreen.player.setCountMovimento(0, 0, 0, 0);
								movimentosHUDLateral.get(i).setColor(Color.GREEN);	
								executaF2();
								movimentosHUDLateral.get(i).setColor(Color.WHITE);
							} else if (movimentosHUDLateral.get(i).getName().equals("FOR")){
								GameScreen.player.setCountMovimento(0, 0, 0, 0);
								movimentosHUDLateral.get(i).setColor(Color.GREEN);	
								executaFor();
								movimentosHUDLateral.get(i).setColor(Color.WHITE);
							}  else if (movimentosHUDLateral.get(i).getName().equals("IF")){
								GameScreen.player.setCountMovimento(0, 0, 0, 0);
								movimentosHUDLateral.get(i).setColor(Color.GREEN);
								Thread.sleep(500);
								executaIf();
								movimentosHUDLateral.get(i).setColor(Color.WHITE);
							}  else if (movimentosHUDLateral.get(i).getName().equals("ATACAR")){
								GameScreen.player.setCountMovimento(0, 0, 0, 0);
								Player.addSp(-2);
								movimentosHUDLateral.get(i).setColor(Color.RED);
								GameScreen.playerMovimentos.attack();
								Thread.sleep(1000);	
								movimentosHUDLateral.get(i).setColor(Color.WHITE);
								if (GameScreen.proximaFase.goNextFase()){
									new LevelUp();
									break;
								}
							}  else if (movimentosHUDLateral.get(i).getName().equals("PEGAR")){
								GameScreen.player.setCountMovimento(0, 0, 0, 0);
								Player.addSp(-2);
								movimentosHUDLateral.get(i).setColor(Color.RED);
								GameScreen.playerMovimentos.pegar();
								Thread.sleep(1000);		
								movimentosHUDLateral.get(i).setColor(Color.WHITE);
								if (GameScreen.proximaFase.goNextFase()){
									new LevelUp();
									break;
								}
							}  else if (movimentosHUDLateral.get(i).getName().equals("DEFENDER")){
								GameScreen.player.setCountMovimento(0, 0, 0, 0);
								Player.addSp(-2);
								movimentosHUDLateral.get(i).setColor(Color.RED);
								GameScreen.playerMovimentos.defender();
								Thread.sleep(1000);		
								movimentosHUDLateral.get(i).setColor(Color.WHITE);
							} else {
								quantidadeDePassos++;
								GameScreen.player.setCountMovimento(0, 0, 0, 0);
								Player.addSp(-2);
								movimentosHUDLateral.get(i).setColor(Color.YELLOW);
								Thread.sleep(500);
								movimentosHUDLateral.get(i).setColor(Color.WHITE);
							}

						}

					}

					if (!Player.isDead || LevelUp.isUp){
						Player.spAtual = Player.spTotal;
						GameScreen.playerMovimentos.defenderStop();
						GameScreen.hudMovimentos.habilitaHudMovimentos();
						GameScreen.rodada = 1;
					}

				} catch (InterruptedException e) {}
			} 
		}.start();
	}

	public static void executaF1(){
		try {

			if (movimentosHUDF1.size()<=0){
				HUDMovimentos.animatedMessage("O personagem nao pode executar a F1 pois a mesma esta vazia!", 3);
			}

			for (int i = 0; i < movimentosHUDF1.size(); i++) {

				if (Player.movimentacaoExcessiva() || Player.isDead || LevelUp.isUp){
					break;
				} else {
					quantidadeComandosF1++;
					GameScreen.hudMovimentos.setMovimentoAtual(movimentosHUDF1.get(i).getName());
					if (movimentosHUDF1.get(i).getName().equals("FRENTE")){
						quantidadeDePassos++;
						GameScreen.player.setCountMovimento(GameScreen.player.position.x+32.0f, GameScreen.player.position.x-32.0f, GameScreen.player.position.y+32.0f, GameScreen.player.position.y-32.0f);
						Player.addSp(-1);
						movimentosHUDF1.get(i).setColor(Color.YELLOW);
						Thread.sleep(1300);
						movimentosHUDF1.get(i).setColor(Color.WHITE);
					} else if (movimentosHUDF1.get(i).getName().equals("F1")){
						GameScreen.player.setCountMovimento(0, 0, 0, 0);
						Player.addSp(-1);
						movimentosHUDF1.get(i).setColor(Color.GREEN);	
						Thread.sleep(500);
						executaF1();
						movimentosHUDF1.get(i).setColor(Color.WHITE);
					}  else if (movimentosHUDF1.get(i).getName().equals("F2")){
						GameScreen.player.setCountMovimento(0, 0, 0, 0);
						Player.addSp(-1);
						movimentosHUDF1.get(i).setColor(Color.GREEN);
						Thread.sleep(500);
						executaF2();
						movimentosHUDF1.get(i).setColor(Color.WHITE);
					}  else if (movimentosHUDF1.get(i).getName().equals("FOR")){
						GameScreen.player.setCountMovimento(0, 0, 0, 0);
						Player.addSp(-1);
						movimentosHUDF1.get(i).setColor(Color.GREEN);
						Thread.sleep(500);
						executaFor();
						movimentosHUDF1.get(i).setColor(Color.WHITE);
					}   else if (movimentosHUDF1.get(i).getName().equals("IF")){
						GameScreen.player.setCountMovimento(0, 0, 0, 0);
						Player.addSp(-1);
						movimentosHUDF1.get(i).setColor(Color.GREEN);	
						Thread.sleep(500);
						executaIf();
						movimentosHUDF1.get(i).setColor(Color.WHITE);
					}  else if (movimentosHUDF1.get(i).getName().equals("ATACAR")){
						GameScreen.player.setCountMovimento(0, 0, 0, 0);
						Player.addSp(-1);
						movimentosHUDF1.get(i).setColor(Color.RED);
						GameScreen.playerMovimentos.attack();
						Thread.sleep(1000);		
						movimentosHUDF1.get(i).setColor(Color.WHITE);
						if (GameScreen.proximaFase.goNextFase()){
							new LevelUp();
							break;
						}
					}  else if (movimentosHUDF1.get(i).getName().equals("PEGAR")){
						GameScreen.player.setCountMovimento(0, 0, 0, 0);
						Player.addSp(-1);
						movimentosHUDF1.get(i).setColor(Color.RED);
						GameScreen.playerMovimentos.pegar();
						Thread.sleep(1000);		
						movimentosHUDF1.get(i).setColor(Color.WHITE);
						if (GameScreen.proximaFase.goNextFase()){
							new LevelUp();
							break;
						}
					}  else if (movimentosHUDF1.get(i).getName().equals("DEFENDER")){
						GameScreen.player.setCountMovimento(0, 0, 0, 0);
						Player.addSp(-1);
						movimentosHUDF1.get(i).setColor(Color.RED);
						GameScreen.playerMovimentos.defender();
						Thread.sleep(1000);		
						movimentosHUDF1.get(i).setColor(Color.WHITE);
					} else {
						quantidadeDePassos++;
						GameScreen.player.setCountMovimento(0, 0, 0, 0);
						Player.addSp(-1);
						movimentosHUDF1.get(i).setColor(Color.YELLOW);
						Thread.sleep(500);
						movimentosHUDF1.get(i).setColor(Color.WHITE);
					}

				}

			}
		} catch (Exception e) {}

	}

	public static void executaF2(){
		try {

			if (movimentosHUDF2.size()<=0){
				HUDMovimentos.animatedMessage("O personagem nao pode executar a F2 pois a mesma esta vazia!", 3);
			}

			for (int i = 0; i < movimentosHUDF2.size(); i++) {

				if (Player.movimentacaoExcessiva() || Player.isDead || LevelUp.isUp){
					break;
				} else {
					quantidadeComandosF2++;
					GameScreen.hudMovimentos.setMovimentoAtual(movimentosHUDF2.get(i).getName());
					if (movimentosHUDF2.get(i).getName().equals("FRENTE")){
						quantidadeDePassos++;
						GameScreen.player.setCountMovimento(GameScreen.player.position.x+32.0f, GameScreen.player.position.x-32.0f, GameScreen.player.position.y+32.0f, GameScreen.player.position.y-32.0f);
						Player.addSp(-1);
						movimentosHUDF2.get(i).setColor(Color.YELLOW);
						Thread.sleep(1300);
						movimentosHUDF2.get(i).setColor(Color.WHITE);
					} else if (movimentosHUDF2.get(i).getName().equals("F1")){
						GameScreen.player.setCountMovimento(0, 0, 0, 0);
						Player.addSp(-1);
						movimentosHUDF2.get(i).setColor(Color.GREEN);	
						Thread.sleep(500);;
						executaF1();
						movimentosHUDF2.get(i).setColor(Color.WHITE);
					} else if (movimentosHUDF2.get(i).getName().equals("F2")){
						GameScreen.player.setCountMovimento(0, 0, 0, 0);
						Player.addSp(-1);
						movimentosHUDF2.get(i).setColor(Color.GREEN);	
						Thread.sleep(500);
						executaF2();
						movimentosHUDF2.get(i).setColor(Color.WHITE);
					}  else if (movimentosHUDF2.get(i).getName().equals("FOR")){
						GameScreen.player.setCountMovimento(0, 0, 0, 0);
						Player.addSp(-1);
						movimentosHUDF2.get(i).setColor(Color.GREEN);	
						Thread.sleep(500);
						executaFor();
						movimentosHUDF2.get(i).setColor(Color.WHITE);
					} else if (movimentosHUDF2.get(i).getName().equals("IF")){
						GameScreen.player.setCountMovimento(0, 0, 0, 0);
						Player.addSp(-1);
						movimentosHUDF2.get(i).setColor(Color.GREEN);	
						Thread.sleep(500);
						executaIf();
						movimentosHUDF2.get(i).setColor(Color.WHITE);
					}  else if (movimentosHUDF2.get(i).getName().equals("ATACAR")){
						GameScreen.player.setCountMovimento(0, 0, 0, 0);
						Player.addSp(-1);
						movimentosHUDF2.get(i).setColor(Color.RED);
						GameScreen.playerMovimentos.attack();
						Thread.sleep(1000);	
						movimentosHUDF2.get(i).setColor(Color.WHITE);
						if (GameScreen.proximaFase.goNextFase()){
							new LevelUp();
							break;
						}
					}  else if (movimentosHUDF2.get(i).getName().equals("PEGAR")){
						GameScreen.player.setCountMovimento(0, 0, 0, 0);
						Player.addSp(-1);
						movimentosHUDF2.get(i).setColor(Color.RED);
						GameScreen.playerMovimentos.pegar();
						Thread.sleep(1000);		
						movimentosHUDF2.get(i).setColor(Color.WHITE);
						if (GameScreen.proximaFase.goNextFase()){
							new LevelUp();
							break;
						}
					}  else if (movimentosHUDF2.get(i).getName().equals("DEFENDER")){
						GameScreen.player.setCountMovimento(0, 0, 0, 0);
						Player.addSp(-1);
						movimentosHUDF2.get(i).setColor(Color.RED);
						GameScreen.playerMovimentos.defender();
						Thread.sleep(1000);		
						movimentosHUDF2.get(i).setColor(Color.WHITE);
					} else {
						quantidadeDePassos++;
						GameScreen.player.setCountMovimento(0, 0, 0, 0);
						Player.addSp(-1);
						movimentosHUDF2.get(i).setColor(Color.YELLOW);
						Thread.sleep(500);
						movimentosHUDF2.get(i).setColor(Color.WHITE);
					}

				}

			}
		} catch (Exception e) {}

	}

	public static void executaFor(){
		try {

			if (movimentosHUDFor.size()<=0){
				HUDMovimentos.animatedMessage("O personagem nao pode executar a FOR pois a mesma esta vazia!", 3);
			}

			int inicioFor;
			int fimFor;
			try {
				inicioFor = Integer.parseInt(HUDFor.txtFor1.getText());
				fimFor = Integer.parseInt(HUDFor.txtFor2.getText());
			} catch (Exception e) {
				inicioFor = 0;
				fimFor = 0;
			}

			for (int j = inicioFor; j <= fimFor; j++) {
				for (int i = 0; i < movimentosHUDFor.size(); i++) {

					if (Player.movimentacaoExcessiva() || Player.isDead || LevelUp.isUp){
						break;
					} else {
						if (j == inicioFor){
							quantidadeComandosFor++;
						}
						GameScreen.hudMovimentos.setMovimentoAtual(movimentosHUDFor.get(i).getName());
						if (movimentosHUDFor.get(i).getName().equals("FRENTE")){
							quantidadeDePassos++;
							GameScreen.player.setCountMovimento(GameScreen.player.position.x+32.0f, GameScreen.player.position.x-32.0f, GameScreen.player.position.y+32.0f, GameScreen.player.position.y-32.0f);
							Player.addSp(-1);
							movimentosHUDFor.get(i).setColor(Color.YELLOW);
							Thread.sleep(1300);
							movimentosHUDFor.get(i).setColor(Color.WHITE);
						} else if (movimentosHUDFor.get(i).getName().equals("F1")){
							GameScreen.player.setCountMovimento(0, 0, 0, 0);
							Player.addSp(-1);
							movimentosHUDFor.get(i).setColor(Color.GREEN);	
							Thread.sleep(500);
							executaF1();
							movimentosHUDFor.get(i).setColor(Color.WHITE);
						} else if (movimentosHUDFor.get(i).getName().equals("F2")){
							GameScreen.player.setCountMovimento(0, 0, 0, 0);
							Player.addSp(-1);
							movimentosHUDFor.get(i).setColor(Color.GREEN);	
							Thread.sleep(500);
							executaF2();
							movimentosHUDFor.get(i).setColor(Color.WHITE);
						} else if (movimentosHUDFor.get(i).getName().equals("FOR")){
							GameScreen.player.setCountMovimento(0, 0, 0, 0);
							Player.addSp(-1);
							movimentosHUDFor.get(i).setColor(Color.GREEN);	
							Thread.sleep(500);
							executaFor();
							movimentosHUDFor.get(i).setColor(Color.WHITE);
						} else if (movimentosHUDFor.get(i).getName().equals("IF")){
							GameScreen.player.setCountMovimento(0, 0, 0, 0);
							Player.addSp(-1);
							movimentosHUDFor.get(i).setColor(Color.GREEN);	
							Thread.sleep(500);
							executaIf();
							movimentosHUDFor.get(i).setColor(Color.WHITE);
						}  else if (movimentosHUDFor.get(i).getName().equals("ATACAR")){
							GameScreen.player.setCountMovimento(0, 0, 0, 0);
							Player.addSp(-1);
							movimentosHUDFor.get(i).setColor(Color.RED);
							GameScreen.playerMovimentos.attack();
							Thread.sleep(1000);		
							movimentosHUDFor.get(i).setColor(Color.WHITE);
							if (GameScreen.proximaFase.goNextFase()){
								new LevelUp();
								break;
							}
						}  else if (movimentosHUDFor.get(i).getName().equals("PEGAR")){
							GameScreen.player.setCountMovimento(0, 0, 0, 0);
							Player.addSp(-1);
							movimentosHUDFor.get(i).setColor(Color.RED);
							GameScreen.playerMovimentos.pegar();
							Thread.sleep(1000);		
							movimentosHUDFor.get(i).setColor(Color.WHITE);
							if (GameScreen.proximaFase.goNextFase()){
								new LevelUp();
								break;
							}
						}  else if (movimentosHUDFor.get(i).getName().equals("DEFENDER")){
							GameScreen.player.setCountMovimento(0, 0, 0, 0);
							Player.addSp(-1);
							movimentosHUDFor.get(i).setColor(Color.RED);
							GameScreen.playerMovimentos.defender();
							Thread.sleep(1000);		
							movimentosHUDFor.get(i).setColor(Color.WHITE);
						} else {
							quantidadeDePassos++;
							GameScreen.player.setCountMovimento(0, 0, 0, 0);
							Player.addSp(-1);
							movimentosHUDFor.get(i).setColor(Color.YELLOW);
							Thread.sleep(500);
							movimentosHUDFor.get(i).setColor(Color.WHITE);
						}

					}

				}
			}

		} catch (Exception e) {}

	}

	public static void executaIf(){
		try {

			if (movimentosHUDIf.size()<=0){
				HUDMovimentos.animatedMessage("O personagem nao pode executar a IF pois a mesma esta vazia!", 3);
			}

			if (HUDIF.dropdown.getSelected().toString().equals(getIfStatus())){


				for (int i = 0; i < movimentosHUDIf.size(); i++) {

					if (Player.movimentacaoExcessiva() || Player.isDead || LevelUp.isUp){
						break;
					} else {
						quantidadeComandosIf++;
						GameScreen.hudMovimentos.setMovimentoAtual(movimentosHUDIf.get(i).getName());
						if (movimentosHUDIf.get(i).getName().equals("FRENTE")){
							quantidadeDePassos++;
							GameScreen.player.setCountMovimento(GameScreen.player.position.x+32.0f, GameScreen.player.position.x-32.0f, GameScreen.player.position.y+32.0f, GameScreen.player.position.y-32.0f);
							Player.addSp(-1);
							movimentosHUDIf.get(i).setColor(Color.YELLOW);
							Thread.sleep(1300);
							movimentosHUDIf.get(i).setColor(Color.WHITE);
						} else if (movimentosHUDIf.get(i).getName().equals("F1")){
							GameScreen.player.setCountMovimento(0, 0, 0, 0);
							Player.addSp(-1);
							movimentosHUDIf.get(i).setColor(Color.GREEN);	
							Thread.sleep(500);
							executaF1();
							movimentosHUDIf.get(i).setColor(Color.WHITE);
						} else if (movimentosHUDIf.get(i).getName().equals("F2")){
							GameScreen.player.setCountMovimento(0, 0, 0, 0);
							Player.addSp(-1);
							movimentosHUDIf.get(i).setColor(Color.GREEN);	
							Thread.sleep(500);
							executaF2();
							movimentosHUDIf.get(i).setColor(Color.WHITE);
						}  else if (movimentosHUDIf.get(i).getName().equals("FOR")){
							GameScreen.player.setCountMovimento(0, 0, 0, 0);
							Player.addSp(-1);
							movimentosHUDIf.get(i).setColor(Color.GREEN);	
							Thread.sleep(500);
							executaFor();
							movimentosHUDIf.get(i).setColor(Color.WHITE);
						} else if (movimentosHUDIf.get(i).getName().equals("IF")){
							GameScreen.player.setCountMovimento(0, 0, 0, 0);
							Player.addSp(-1);
							movimentosHUDIf.get(i).setColor(Color.GREEN);	
							Thread.sleep(500);
							executaIf();
							movimentosHUDIf.get(i).setColor(Color.WHITE);
						}  else if (movimentosHUDIf.get(i).getName().equals("ATACAR")){
							GameScreen.player.setCountMovimento(0, 0, 0, 0);
							Player.addSp(-1);
							movimentosHUDIf.get(i).setColor(Color.RED);
							GameScreen.playerMovimentos.attack();
							Thread.sleep(1000);		
							movimentosHUDIf.get(i).setColor(Color.WHITE);
							if (GameScreen.proximaFase.goNextFase()){
								new LevelUp();
								break;
							}
						}  else if (movimentosHUDIf.get(i).getName().equals("PEGAR")){
							GameScreen.player.setCountMovimento(0, 0, 0, 0);
							Player.addSp(-1);
							movimentosHUDIf.get(i).setColor(Color.RED);
							GameScreen.playerMovimentos.pegar();
							Thread.sleep(1000);		
							movimentosHUDIf.get(i).setColor(Color.WHITE);
							if (GameScreen.proximaFase.goNextFase()){
								new LevelUp();
								break;
							}
						}  else if (movimentosHUDIf.get(i).getName().equals("DEFENDER")){
							GameScreen.player.setCountMovimento(0, 0, 0, 0);
							Player.addSp(-1);
							movimentosHUDIf.get(i).setColor(Color.RED);
							GameScreen.playerMovimentos.defender();
							Thread.sleep(1000);		
							movimentosHUDIf.get(i).setColor(Color.WHITE);
						} else {
							quantidadeDePassos++;
							GameScreen.player.setCountMovimento(0, 0, 0, 0);
							Player.addSp(-1);
							movimentosHUDIf.get(i).setColor(Color.YELLOW);
							Thread.sleep(500);
							movimentosHUDIf.get(i).setColor(Color.WHITE);
						}

					}

				}

			} 

		} catch (Exception e) {}

	}

	public static String getIfStatus(){

		Array<Rectangle> tiles = new Array<Rectangle>();
		Rectangle playerRectangle = new Rectangle(GameScreen.player.position.x, GameScreen.player.position.y, 32, 32);
		GameScreen.player.getTiles(tiles); // prenche o arraylist de Rectangle com todos os retangles de colisão
		if (GameScreen.player.dir==0){
			playerRectangle.y = GameScreen.player.position.y-32;		
			for (Rectangle tile : tiles) {
				if (playerRectangle.overlaps(tile)){			
					return "Colisao";
				}
			}
			for (int i = 0; i < GameScreen.listaInimigos.size(); i++) {
				Inimigo inimigo = GameScreen.listaInimigos.get(i);
				Rectangle inimigoRectangle = new Rectangle(inimigo.position.x, inimigo.position.y, 32, 32);		
				if (playerRectangle.overlaps(inimigoRectangle)){	
					return "Inimigo";
				}
			}
			if (GameScreen.itens.verificaBlockedItem((int) playerRectangle.x, (int) playerRectangle.y, "")){
				return "Colisao";
			}
			if (GameScreen.itens.verificaColisionItem((int) playerRectangle.x, (int) playerRectangle.y)){
				return "Item";
			}
		}else if(GameScreen.player.dir==1){
			playerRectangle.x = GameScreen.player.position.x-32;
			for (Rectangle tile : tiles) {
				if (playerRectangle.overlaps(tile)){			
					return "Colisao";
				}
			}
			for (int i = 0; i < GameScreen.listaInimigos.size(); i++) {
				Inimigo inimigo = GameScreen.listaInimigos.get(i);
				Rectangle inimigoRectangle = new Rectangle(inimigo.position.x, inimigo.position.y, 32, 32);		
				if (playerRectangle.overlaps(inimigoRectangle)){	
					return "Inimigo";
				}
			}
			if (GameScreen.itens.verificaBlockedItem((int) playerRectangle.x, (int) playerRectangle.y, "")){
				return "Colisao";
			}
			if (GameScreen.itens.verificaColisionItem((int) playerRectangle.x, (int) playerRectangle.y)){
				return "Item";
			}
		}else if(GameScreen.player.dir==2){
			playerRectangle.y = GameScreen.player.position.y+32;
			for (Rectangle tile : tiles) {
				if (playerRectangle.overlaps(tile)){			
					return "Colisao";
				}
			}
			for (int i = 0; i < GameScreen.listaInimigos.size(); i++) {
				Inimigo inimigo = GameScreen.listaInimigos.get(i);
				Rectangle inimigoRectangle = new Rectangle(inimigo.position.x, inimigo.position.y, 32, 32);		
				if (playerRectangle.overlaps(inimigoRectangle)){	
					return "Inimigo";
				}
			}	
			if (GameScreen.itens.verificaBlockedItem((int) playerRectangle.x, (int) playerRectangle.y, "")){
				return "Colisao";
			}
			if (GameScreen.itens.verificaColisionItem((int) playerRectangle.x, (int) playerRectangle.y)){
				return "Item";
			}
		}else if(GameScreen.player.dir==3){
			playerRectangle.x = GameScreen.player.position.x+32;
			for (Rectangle tile : tiles) {
				if (playerRectangle.overlaps(tile)){			
					return "Colisao";
				}
			}
			for (int i = 0; i < GameScreen.listaInimigos.size(); i++) {
				Inimigo inimigo = GameScreen.listaInimigos.get(i);
				Rectangle inimigoRectangle = new Rectangle(inimigo.position.x, inimigo.position.y, 32, 32);		
				if (playerRectangle.overlaps(inimigoRectangle)){	
					return "Inimigo";
				}
			}
			if (GameScreen.itens.verificaBlockedItem((int) playerRectangle.x, (int) playerRectangle.y, "")){
				return "Colisao";
			}
			if (GameScreen.itens.verificaColisionItem((int) playerRectangle.x, (int) playerRectangle.y)){
				return "Item";
			}
		}

		return "";
	}

	public void getMovimentacoesHUD(){
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

		executaMovimentos();
	}

	public void getTiles(Array<Rectangle> tiles) {
		try {
			int countLayers = GameScreen.map.getMap().getLayers().getCount();
			for (int i = 0; i < countLayers; i++) {
				MapLayer collisionObjectLayer = GameScreen.map.getMap().getLayers().get(i);
				MapObjects objects = collisionObjectLayer.getObjects();
				for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
					tiles.add(rectangleObject.getRectangle());
				}
				for (PolygonMapObject polygonObject : objects.getByType(PolygonMapObject.class)) {
					tiles.add(polygonObject.getPolygon().getBoundingRectangle());
				}
				for (EllipseMapObject ellipseObject : objects.getByType(EllipseMapObject.class)) {
					tiles.add(new Rectangle(ellipseObject.getEllipse().x, ellipseObject.getEllipse().y, ellipseObject.getEllipse().width, ellipseObject.getEllipse().height));
				}
				for (PolylineMapObject polylineObject : objects.getByType(PolylineMapObject.class)) {
					Polygon polygonObject = new Polygon(polylineObject.getPolyline().getVertices());
					float posX = polylineObject.getPolyline().getX();
					float posY = polylineObject.getPolyline().getY()-polygonObject.getBoundingRectangle().width;
					float width = polygonObject.getBoundingRectangle().width;
					float height = polygonObject.getBoundingRectangle().height;
					tiles.add(new Rectangle(posX, posY, width, height));
				}
			}
		} catch (Exception e) {	}
	}

	public static int getPoints(){
		int pontuacaoAtual = 500;
		pontuacaoAtual = pontuacaoAtual - (quantidadeRodadas*3);
		pontuacaoAtual = pontuacaoAtual - (quantidadeComandosMovimentos*2);
		pontuacaoAtual = pontuacaoAtual - quantidadeComandosF1;
		pontuacaoAtual = pontuacaoAtual - quantidadeComandosF2;
		pontuacaoAtual = pontuacaoAtual - quantidadeComandosFor;
		pontuacaoAtual = pontuacaoAtual - quantidadeComandosIf;	
		if (pontuacaoAtual<= 0){
			pontuacaoAtual = 1;
		} else if (pontuacaoAtual == 500){
			pontuacaoAtual = 0;
		}
		return pontuacaoAtual;
	}

}
