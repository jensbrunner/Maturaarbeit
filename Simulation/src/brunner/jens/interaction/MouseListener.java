package brunner.jens.interaction;

import java.awt.event.MouseEvent;

import brunner.jens.main.Main;
import brunner.jens.utils.Constants;
import brunner.jens.utils.Vector2;
import brunner.jens.utils.Vector2Math;

public class MouseListener implements java.awt.event.MouseListener {
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(Main.isBounding) {
			
			Vector2 vec = new Vector2(e.getX(), e.getY());
			
			Vector2 diff = new Vector2(Constants.WINDOW_DIMENSION.width/2, Constants.WINDOW_DIMENSION.height/2);
			Vector2 scaled = Vector2Math.mult(Vector2Math.subtract(vec,diff), 1./Main.scaleFactor);
			Vector2 drawPos = Vector2Math.add(scaled, diff);
			
			Main.boundVec = new Vector2(drawPos.x, drawPos.y);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(Main.isBounding && Main.boundingAcceptable) {
			Main.isBounding = false;
			Main.bounded = true;
		}

	}

}
