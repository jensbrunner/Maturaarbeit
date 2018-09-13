package brunner.jens.interaction;

import java.awt.event.MouseEvent;

import brunner.jens.editor.EditorHandler;
import brunner.jens.main.Body;
import brunner.jens.main.BodyHandler;
import brunner.jens.main.Main;
import brunner.jens.main.SimulationWindow;
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
		
		SimulationWindow.resetFocus();
		
		Vector2 drawPos = Toolbox.translateToScreen(new Vector2(e.getX(),e.getY()), true);
		//Vector2 drawPosInverse = Toolbox.translateToScreen(new Vector2(e.getX(),e.getY()), true);
		
		if(Main.creatingGalaxy && !(Main.isBounding || EditorHandler.inEditor)) {
			Toolbox.createGalaxy(drawPos, 300, 400);
			
		}else if(Main.isBounding && !EditorHandler.inEditor) {
			Main.boundVec = new Vector2(drawPos.x, drawPos.y);
			
		}else if(EditorHandler.inEditor) {
			if(EditorHandler.drawingVelocity) {
				Vector2 diff = Vector2Math.subtract(drawPos, EditorHandler.createdBody.position);
				EditorHandler.createdBody.vel = Vector2Math.scale(diff, Vector2Math.magnitude(diff), 0.001/**(1/Main.scaleFactor)*/);
				EditorHandler.drawingVelocity = false;
				EditorHandler.createdBody = null;
			}else {
				EditorHandler.createdBody = new Body(drawPos, Constants.ZERO_VECTOR, EditorHandler.setMass);
				BodyHandler.planets.add(EditorHandler.createdBody);
				SimulationWindow.updateBodyLabel(Main.curBodyAmount + 1);
				if(EditorHandler.velocity) EditorHandler.drawingVelocity = true;
			}
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
