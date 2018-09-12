package brunner.jens.interaction;

import java.awt.event.MouseEvent;

import brunner.jens.editor.EditorHandler;
import brunner.jens.main.Body;
import brunner.jens.main.BodyHandler;
import brunner.jens.main.Main;
import brunner.jens.utils.Constants;
import brunner.jens.utils.Toolbox;
import brunner.jens.utils.Vector2;
import brunner.jens.utils.Vector2Math;

public class MouseMotionListener implements java.awt.event.MouseMotionListener {

	@Override
	public void mouseDragged(MouseEvent e) {
		
		Vector2 drawPos = Toolbox.translateToScreen(new Vector2(e.getX(),e.getY()), true);
		
		if(Main.isBounding && !Main.creatingGalaxy && !Main.inEditor) {
			if(drawPos.x > Main.boundVec.x && drawPos.y > Main.boundVec.y) {
				Main.boundVec2 = new Vector2(drawPos.x, drawPos.y);
				Main.boundingAcceptable = true;
			}else {
				Main.boundingAcceptable = false;
			}
		}else if(Main.inEditor && EditorHandler.drag) {
			BodyHandler.planets.add(new Body(drawPos, Constants.ZERO_VECTOR, EditorHandler.setMass));
		}

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
