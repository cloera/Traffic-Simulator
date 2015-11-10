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
		if (c != null) {  
			cars.remove(c); 
		}
		
		if(frontPosition > endPosition) {
			return nextRoad.accept(c, frontPosition - endPosition);
		} else {
			c.setCurrentRoad(this);
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
	      obstaclePosition = nextRoad.distanceToObstacle(fromPosition - this.endPosition);
	      return obstaclePosition;
	    }
	    return obstaclePosition - fromPosition;
	}
	
	public Set<Car> getCars() {
		return cars;
	}

	@Override
	public void setCurrentIntersection(Intersections intersection) {
		// TODO Auto-generated method stub
		
	}
	

}
