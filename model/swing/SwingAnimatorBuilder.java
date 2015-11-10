package myproject.model.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import myproject.model.AnimatorBuilder;
import myproject.model.Car;
import myproject.model.Intersections;
import myproject.model.MP;
import myproject.model.CarHandler;
import myproject.util.Animator;
import myproject.util.SwingAnimator;
import myproject.util.SwingAnimatorPainter;

/**
 * A class for building Animators.
 */
public class SwingAnimatorBuilder implements AnimatorBuilder {
	MyPainter painter;
	public SwingAnimatorBuilder() {
		painter = new MyPainter();
	}
	public Animator getAnimator() {
		if (painter == null) { throw new IllegalStateException(); }
		Animator returnValue = new SwingAnimator(painter, "Traffic Simulator",
				VP.displayWidth, VP.displayHeight, VP.displayDelay);
		painter = null;
		return returnValue;
	}
	private static double skipInit = VP.gap;
	private static double skipRoad = VP.gap + MP.roadLength;
	private static double skipCar = VP.gap + VP.elementWidth;
	private static double skipRoadCar = skipRoad + skipCar;
	public void addLight(Intersections d, int i, int j) {
		double x = skipInit + skipRoad + j*skipRoadCar;
		double y = skipInit + skipRoad + i*skipRoadCar;
		Translator t = new TranslatorWE(x, y, MP.carLength, VP.elementWidth, VP.scaleFactor);
		painter.addLight(d,t);
	}
	public void addHorizontalRoad(CarHandler l, int i, int j, boolean eastToWest) {
		double x = skipInit + j*skipRoadCar;
		double y = skipInit + skipRoad + i*skipRoadCar;
		Translator t = eastToWest ? new TranslatorEW(x, y, MP.roadLength, VP.elementWidth, VP.scaleFactor)
				: new TranslatorWE(x, y, MP.roadLength, VP.elementWidth, VP.scaleFactor);
		painter.addRoad(l,t);
	}
	public void addVerticalRoad(CarHandler l, int i, int j, boolean southToNorth) {
		double x = skipInit + skipRoad + j*skipRoadCar;
		double y = skipInit + i*skipRoadCar;
		Translator t = southToNorth ? new TranslatorSN(x, y, MP.roadLength, VP.elementWidth, VP.scaleFactor)
				: new TranslatorNS(x, y, MP.roadLength, VP.elementWidth, VP.scaleFactor);
		painter.addRoad(l,t);
	}


	/** Class for drawing the Model. */
	private static class MyPainter implements SwingAnimatorPainter {

		/** Pair of a model element <code>x</code> and a translator <code>t</code>. */
		private static class Element<T> {
			T x;
			Translator t;
			Element(T x, Translator t) {
				this.x = x;
				this.t = t;
			}
		}

		private List<Element<CarHandler>> roadElements;
		private List<Element<Intersections>> lightElements;
		MyPainter() {
			roadElements = new ArrayList<Element<CarHandler>>();
			lightElements = new ArrayList<Element<Intersections>>();
		}
		void addLight(Intersections x, Translator t) {
			lightElements.add(new Element<Intersections>(x,t));
		}
		void addRoad(CarHandler x, Translator t) {
			roadElements.add(new Element<CarHandler>(x,t));
		}

		public void paint(Graphics g) {
			// This method is called by the swing thread, so may be called
			// at any time during execution...

			// First draw the background elements
			for (Element<Intersections> e : lightElements) {
				if (e.x.getLight().getState()) {
					g.setColor(Color.BLUE);
				} else {
					g.setColor(Color.YELLOW);
				}
				XGraphics.fillOval(g, e.t, 0, 0, MP.carLength, VP.elementWidth);
			}
			g.setColor(Color.BLACK);
			for (Element<CarHandler> e : roadElements) {
				XGraphics.fillRect(g, e.t, 0, 0, MP.roadLength, VP.elementWidth);
			}

			// Then draw the foreground elements
			for (Element<CarHandler> e : roadElements) {
				// iterate through a copy because e.x.getCars() may change during iteration...
				for (Car d : e.x.getCars().toArray(new Car[0])) {
					g.setColor(d.getColor());
					XGraphics.fillOval(g, e.t, d.getPosition(), 0, MP.carLength, VP.elementWidth);
				}
			}
		}
	}
}

