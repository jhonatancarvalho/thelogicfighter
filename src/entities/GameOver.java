package entities;

import main.Assets;
import screens.GameScreen;
import screens.LoginScreen;
import screens.MainMenuScreen;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

public class GameOver {

	public GameOver() {

		Player.isDead = true;
		new Dialog("Game Over", Assets.skin, "dialog") {
			protected void result (Object object) {
				if (object.equals(true)){
					GameScreen.getGame().setScreen(new GameScreen(GameScreen.getGame()));
				} else {
					GameScreen.getGame().setScreen(new MainMenuScreen(GameScreen.getGame()));
					LoginScreen.themeMusic.play();
				}
			}
		}.text("    Voce morreu!   ").button("Continuar", true).button("Sair", false).key(Keys.ENTER, true)
		.key(Keys.ESCAPE, false).show(GameScreen.getStage()).setMovable(false);
	
	}

}
