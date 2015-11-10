package myproject.model;

import java.util.HashSet;
import java.util.Set;


final class IntersectionObj implements Intersections {
	private Set<Car> cars;
	private double endPosition;
	private CarHandler nextRoad;
	private Light light;
	
	IntersectionObj() {
		this.cars = new HashSet<Car>();
		this.endPosition = MP.roadLength;
		this.light = new Light();
		this.nextRoad = new Road();
	}
	
	public boolean accept(Car c, double frontPosition) {
		if(frontPosition > this.endPosition) {
			return this.nextRoad.accept(c, frontPosition - this.endPosition);
		} else {
			c.setCurrentIntersection(this);
			c.setFrontPosition(frontPosition);
			this.cars.add(c);
			return true;
		}
	}
	
	public double distanceToObstacle(double fromPosition) {
		double obstaclePosition = this.distanceToCarBack(fromPosition);
	    if (obstaclePosition == Double.POSITIVE_INFINITY) {
	      double distanceToEnd = fromPosition - this.endPosition;
	      obstaclePosition = nextRoad.distanceToObstacle(fromPosition - this.endPosition);
	      return obstaclePosition;
	    }
	    return obstaclePosition - fromPosition;
	}
	
	private double distanceToCarBack(double fromPosition) {
	    double carBackPosition = Double.POSITIVE_INFINITY;
	    for (Car c : cars)
	      if (c.backPosition() >= fromPosition && c.backPosition() < carBackPosition)
	    	  carBackPosition = c.backPosition();
	    return carBackPosition;
	}
	
	public void setNextRoad(CarHandler nextRoad) {
		this.nextRoad = nextRoad;
	}
	
	public CarHandler getNextRoad() {
		return nextRoad;
	}

	public Light getLight() {
		return light;
	}


}
