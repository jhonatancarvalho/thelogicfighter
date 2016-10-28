package main;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class TheLogicFighterDesktop {

	public static void main(String[] args) {
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "The Logic Fighter - 1.0.1 Beta";
		cfg.useGL30 = false;
		cfg.width = 960;
		cfg.height = 640;
		cfg.resizable = false;
		cfg.foregroundFPS = 60;
		cfg.backgroundFPS = 60;
		cfg.addIcon("assets/data/icon_16x16.png", FileType.Internal);
		cfg.addIcon("assets/data/icon_32x32.png", FileType.Internal);
		
		new LwjglApplication(new TLFGame(), cfg);
		
	}

}
