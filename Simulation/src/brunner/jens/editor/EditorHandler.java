package brunner.jens.editor;

import brunner.jens.main.Body;
import brunner.jens.main.Main;

public class EditorHandler {

	public static EditorWindow editorWindow;
	public static boolean inEditor = false, velocity = false, drawingVelocity = false;
	public static Body createdBody;
	public static int setMass = 1;
	
	public static void enterEditor() {
		Main.pause = true;
		EditorHandler.inEditor = true;
		
		editorWindow.setVisible(true);
	}
	
	public static void exitEditor() {
		Main.pause = false;
		EditorHandler.inEditor = false;
		
		editorWindow.setVisible(false);
	}
}
