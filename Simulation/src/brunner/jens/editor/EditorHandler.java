package brunner.jens.editor;

import brunner.jens.main.Main;

public class EditorHandler {

	public static EditorWindow editorWindow;
	public static boolean drag = false;
	public static int setMass = 1;
	
	public static void enterEditor() {
		Main.pause = true;
		Main.inEditor = true;
		
		editorWindow.setVisible(true);
	}
	
	public static void exitEditor() {
		Main.pause = false;
		Main.inEditor = false;
	}
}
