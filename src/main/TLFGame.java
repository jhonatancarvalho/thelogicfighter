package main;

import screens.LoginScreen;

import com.badlogic.gdx.Game;
public class TLFGame extends Game {

	@Override
	public void create() {
		Assets.load();
		setScreen(new LoginScreen(this));
	}

}
