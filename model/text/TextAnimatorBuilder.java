package myproject.model.text;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import myproject.model.AnimatorBuilder;
import myproject.model.Car;
import myproject.model.Intersections;
import myproject.model.Light.LightState;
import myproject.model.CarHandler;
import myproject.util.Animator;

/**
 * A class for building Animators.
 */
public class TextAnimatorBuilder implements AnimatorBuilder {
	TextAnimator animator;
	public TextAnimatorBuilder() {
		this.animator = new TextAnimator();
	}
	public Animator getAnimator() {
		if (animator == null) { throw new IllegalStateException(); }
		Animator returnValue = animator;
		animator = null;
		return returnValue;
	}
	public void addLight(Intersections d, int i, int j) {
		animator.addLight(d,i,j);
	}
	public void addHorizontalRoad(CarHandler l, int i, int j, boolean eastToWest) {
		animator.addRoad(l,i,j);
	}
	public void addVerticalRoad(CarHandler l, int i, int j, boolean southToNorth) {
		animator.addRoad(l,i,j);
	}


	/** Class for drawing the Model. */
	private static class TextAnimator implements Animator {

		/** Triple of a model element <code>x</code> and coordinates. */
		private static class Element<T> {
			T x;
			int i;
			int j;
			Element(T x, int i, int j) {
				this.x = x;
				this.i = i;
				this.j = j;
			}
		}

		private List<Element<CarHandler>> roadElements;
		private List<Element<Intersections>> lightElements;
		TextAnimator() {
			roadElements = new ArrayList<Element<CarHandler>>();
			lightElements = new ArrayList<Element<Intersections>>();
		}
		void addLight(Intersections x, int i, int j) {
			lightElements.add(new Element<Intersections>(x,i,j));
		}
		void addRoad(CarHandler x, int i, int j) {
			roadElements.add(new Element<CarHandler>(x,i,j));
		}

		public void dispose() { }

		public void update(Observable o, Object arg) {
			for (Element<Intersections> e : lightElements) {
				System.out.print("Light at (" + e.i + "," + e.j + "): ");
				if (e.x.getLight().getState() == LightState.GREEN) {
					System.out.println("Green");
				} else {
					System.out.println("Red");
				}
			}
			for (Element<CarHandler> e : roadElements) {
				for (Car d : e.x.getCars()) {
					System.out.print("Road at (" + e.i + "," + e.j + "): ");
					System.out.println("Car at " + d.getPosition());
				}
			}
		}
	}
}
