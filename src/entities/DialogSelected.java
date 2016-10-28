package entities;

import screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class DialogSelected {

	public static int dialogCount = 1;

	Texture dialogSelected;
	
	public static String getName(){
		String dialogSelected = "HUDLateral";
		if (dialogCount == 2){
			dialogSelected = "HUDF1";
		} else if (dialogCount == 3){
			dialogSelected = "HUDF2";
		} else if (dialogCount == 4){
			dialogSelected = "HUDFor";
		} else if (dialogCount == 5){
			dialogSelected = "HUDIF";
		}
		return dialogSelected;
	}
	
	public void LoadDialogSelectedTexture() {

		dialogSelected = new Texture(Gdx.files.internal("data/mira.png"));

	}
	
	public void DrawDialogSelected(Batch batch, float deltaTime) {

		batch.begin();	
		if (DialogSelected.dialogCount == 1){
			batch.draw(dialogSelected, GameScreen.w-190, GameScreen.h-20);
		} else if (DialogSelected.dialogCount == 2){
			batch.draw(dialogSelected, GameScreen.w-190, GameScreen.h-182);
		} else if (DialogSelected.dialogCount == 3){
			batch.draw(dialogSelected, GameScreen.w-190, GameScreen.h-298);
		} else if (DialogSelected.dialogCount == 4){
			batch.draw(dialogSelected, GameScreen.w-190, GameScreen.h-414);
		} else if (DialogSelected.dialogCount == 5){
			batch.draw(dialogSelected, GameScreen.w-190, GameScreen.h-504);
		}
		batch.end();

	}
	
}
