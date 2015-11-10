package myproject.model;

import java.util.List;
import java.util.ArrayList;
import myproject.model.CarHandler;

/**
 * A road holds cars.
 */
public class Road implements CarHandler{
	Road() { } // Created only by this package

	private List<Car> cars = new ArrayList<Car>();
	private double endPosition;
	CarHandler nextRoad;
	
	public boolean accept(Car c, double firstPosition) {
		if (c == null || nextRoad == null) { throw new IllegalArgumentException(); }
		cars.remove(c);
		if(firstPosition > endPosition) {
			return nextRoad.accept(c, firstPosition - endPosition);
		} else {
			c.setCurrentRoad(this);
			c.setFirstPosition(firstPosition);
			cars.add(c);
			return true;
		}	
	}
	
	public List<Car> getCars() {
		return cars;
	}
}
