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

public class MouseListener implements java.awt.event.MouseListener {
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		Vector2 drawPos = Toolbox.translateToScreen(new Vector2(e.getX(),e.getY()), true);
		//Vector2 drawPosInverse = Toolbox.translateToScreen(new Vector2(e.getX(),e.getY()), true);
		
		if(Main.creatingGalaxy && !(Main.isBounding || Main.inEditor)) {
			BodyHandler.createGalaxy(drawPos, 300, 400);
			
		}else if(Main.isBounding && !Main.inEditor) {
			Main.boundVec = new Vector2(drawPos.x, drawPos.y);
			
		}else if(Main.inEditor) {
			BodyHandler.planets.add(new Body(drawPos, Constants.ZERO_VECTOR, EditorHandler.setMass));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(Main.isBounding && Main.boundingAcceptable && !Main.creatingGalaxy) {
			Main.isBounding = false;
			Main.boundingAcceptable = false;
			Main.bounded = true;
		}

	}

}
