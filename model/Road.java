package myproject.model;


import java.util.Set;
import java.util.HashSet;


/**
 * A road holds cars.
 */
public class Road implements CarHandler{

	private Set<Car> cars;
	private double endPosition;
	Intersections nextRoad;
	
	Road() { 
		this.endPosition = MP.roadLength;
		this.cars = new HashSet<Car>();
	}
	
	public boolean accept(Car c, double frontPosition) {
		if (this.cars != null) {  
			this.cars.remove(c); 
		}
		
		if(frontPosition > endPosition) {
			return nextRoad.accept(c, frontPosition - endPosition);
		} else {
			c.setRoad(this);
			c.setFrontPosition(frontPosition);
			cars.add(c);
			return true;
		}	
	}
	
	private double distanceToCarBack(double fromPosition) {
	    double carBackPosition = Double.POSITIVE_INFINITY;
	    for (Car c : cars)
	      if (c.backPosition() >= fromPosition && c.backPosition() < carBackPosition)
	    	  carBackPosition = c.backPosition();
	    return carBackPosition;
	}
	
	public double distanceToObstacle(double fromPosition) {
	    double obstaclePosition = this.distanceToCarBack(fromPosition);
	    if (obstaclePosition == Double.POSITIVE_INFINITY) {
	      double distanceToEnd = fromPosition - this.endPosition;
	      obstaclePosition = nextRoad.distanceToObstacle(0.0) + distanceToEnd;
	      return obstaclePosition;
	    }
	    return obstaclePosition - fromPosition;
	}
	
	public Set<Car> getCars() {
		return cars;
	}

	public void setCurrentIntersection(Intersections intersection) {
		this.nextRoad = intersection;
	}

	public Double getEndPosition() {
		return endPosition;
	}


	public boolean remove(Car car) {
		if(this.cars.contains(car)) {
			this.cars.remove(car);
			return true;
		} else {
			return false;
		}
	}


	public Intersections getNextRoad() {
		return nextRoad;
	}
	

}
