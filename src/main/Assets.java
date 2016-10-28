package main;

import java.io.FileInputStream;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class Assets {

	public static Skin skin;
	public static Texture backgroundTexture;
	public static BitmapFont font;
	public static LabelStyle labelStyleRemoveLife;
	public static LabelStyle labelStyleAddLife;
	public static LabelStyle labelStyleBlue;

	public static EntityManagerFactory emf; 
	public static EntityManager em; 

	public static Properties config = new Properties();

	public static void load () {

		loadPersistence();

		backgroundTexture = new Texture(Gdx.files.internal("data/background5.jpg"));
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		font = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
		labelStyleRemoveLife = new LabelStyle(font, Color.RED);
		labelStyleAddLife = new LabelStyle(font, Color.GREEN);
		labelStyleBlue = new LabelStyle(font, Color.BLUE);

		config = new Properties();
		try {
			config.load(new FileInputStream("config.tlf"));
		} catch (Exception e) {e.printStackTrace();}

	}

	// atualiza persistence
	public static void loadPersistence(){
		new Thread() {
			@Override 
			public void run() { 
				try {	
					emf = Persistence.createEntityManagerFactory("DBTLF"); 
					em = emf.createEntityManager(); 
					//Thread.sleep(2000);
					//loadPersistence();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
		}.start();
	}
}
