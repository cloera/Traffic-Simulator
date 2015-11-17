package myproject.model;

import java.util.HashSet;
import java.util.Set;

import myproject.model.Light.LightState;


final class IntersectionObj implements Intersections {
	private Set<Car> cars;
	private double endPosition;
	private CarHandler nextRoad;
	private Light light;
	
	IntersectionObj() {
		this.cars = new HashSet<Car>();
		this.endPosition = MP.roadLength;
		this.light = new Light();
	}
	
	public boolean accept(Car c, double frontPosition) {
		if(frontPosition > this.endPosition) {
			return this.nextRoad.accept(c, frontPosition - this.endPosition);
		} else {
			c.setIntersection(this);
			c.setFrontPosition(frontPosition);
			this.cars.add(c);
			return true;
		}
	}
	
	public double distanceToObstacle(double fromPosition) {
		if(this.light.getState() == LightState.GREEN || this.light.getState() == LightState.RED) {
			double obstaclePosition = this.distanceToCarBack(fromPosition);
		    if (obstaclePosition == Double.POSITIVE_INFINITY) {
		      double distanceToEnd = fromPosition - this.endPosition;
		      obstaclePosition = nextRoad.distanceToObstacle(distanceToEnd);
		      return obstaclePosition;
		    }
		    return obstaclePosition - fromPosition;
		}
		return 0.0;
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
	
	public Double getEndPosition() {
		return this.endPosition;
	}

	public Light getLight() {
		return this.light;
	}


	public boolean remove(Car car) {
		if(cars.contains(car)) {
			cars.remove(car);
			return true;
		} else {
			return false;
		}
	}


}
