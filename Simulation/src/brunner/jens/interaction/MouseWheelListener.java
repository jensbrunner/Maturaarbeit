package brunner.jens.interaction;

import java.awt.event.MouseWheelEvent;

import brunner.jens.main.Main;
import brunner.jens.main.SimulationWindow;

public class MouseWheelListener implements java.awt.event.MouseWheelListener {

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.isShiftDown()) {

			if(e.getWheelRotation() == -1) {
				Main.timeScale *= 1.1;
				SimulationWindow.time.setFloatValue(Main.timeScale);
				double rounded = Math.floor(10000 * (double)Main.timeScale + 0.5) / 10000;
				SimulationWindow.timeLabel.setText("Time: " + rounded+"x");

			}else if(e.getWheelRotation() == 1){
				Main.timeScale *= 0.9;
				SimulationWindow.time.setFloatValue(Main.timeScale);
				double rounded = Math.floor(10000 * (double)Main.timeScale + 0.5) / 10000;
				SimulationWindow.timeLabel.setText("Time: " + rounded + "x");
			}
			
		}else {

			if(e.getWheelRotation() == -1) {
				Main.scaleFactor *= 1.3;
				SimulationWindow.zoom.setFloatValue(Main.scaleFactor);
				double rounded = Math.floor(10000 * (double)Main.scaleFactor + 0.5) / 10000;
				SimulationWindow.zoomLabel.setText("Zoom: " + rounded+"x");

			}else if(e.getWheelRotation() == 1){
				Main.scaleFactor *= 0.8;
				SimulationWindow.zoom.setFloatValue(Main.scaleFactor);
				double rounded = Math.floor(10000 * (double)Main.scaleFactor + 0.5) / 10000;
				SimulationWindow.zoomLabel.setText("Zoom: " + rounded + "x");
			}
		}

	}

}
