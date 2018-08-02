package brunner.jens.interaction;

import java.awt.event.MouseEvent;

import brunner.jens.main.Main;
import brunner.jens.utils.Constants;
import brunner.jens.utils.Vector2;
import brunner.jens.utils.Vector2Math;

public class MouseMotionListener implements java.awt.event.MouseMotionListener {

	@Override
	public void mouseDragged(MouseEvent e) {
		if(Main.isBounding && !Main.creatingGalaxy) {

			Vector2 vec = new Vector2(e.getX(), e.getY());

			Vector2 diff = new Vector2(Constants.WINDOW_DIMENSION.width/2, Constants.WINDOW_DIMENSION.height/2);
			Vector2 scaled = Vector2Math.mult(Vector2Math.subtract(vec,diff), 1./Main.scaleFactor);
			Vector2 drawPos = Vector2Math.add(scaled, diff);

			if(drawPos.x > Main.boundVec.x && drawPos.y > Main.boundVec.y) {
				Main.boundVec2 = new Vector2(drawPos.x, drawPos.y);
				Main.boundingAcceptable = true;
			}else {
				Main.boundingAcceptable = false;
			}
		}

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
