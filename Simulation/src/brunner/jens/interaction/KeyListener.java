package brunner.jens.interaction;

import java.awt.event.KeyEvent;
import java.security.Key;

import brunner.jens.main.Main;

public class KeyListener implements java.awt.event.KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			Main.pause = !Main.pause;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
