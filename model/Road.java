package myproject.model;


import java.util.Set;

import myproject.model.ObjBuilder.Orientation;
import myproject.util.SettingsBag;
import java.util.HashSet;


/**
 * A road holds cars.
 */
public class Road implements CarHandler{

	private Set<Vehicle> cars;
	private Double endPosition;
	private Intersections nextRoad;
	private SettingsBag sb = SettingsBag.makeSettingsBag();
	
	Road() { 
		this.endPosition = Math.random() * this.sb.getRoadSegmentLengthMax();
		this.endPosition = Math.max(this.endPosition, this.sb.getRoadSegmentLengthMin());
		this.cars = new HashSet<Vehicle>();
	}
	
	public boolean accept(Vehicle c, Double frontPosition) {
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
	
	private Double distanceToObstacleBack(Double fromPosition) {
	    double carBackPosition = Double.POSITIVE_INFINITY;
	    for (Vehicle c : cars)
	      if (c.getBackPosition() >= fromPosition && c.getBackPosition() < carBackPosition)
	    	  carBackPosition = c.getBackPosition();
	    return carBackPosition;
	}
	
	public Double distanceToObstacle(Double fromPosition, Orientation orientation) {
	    double obstaclePosition = this.distanceToObstacleBack(fromPosition);
	    if (obstaclePosition == Double.POSITIVE_INFINITY) {
	      double distanceToEnd = this.endPosition - fromPosition;
	      obstaclePosition = nextRoad.distanceToObstacle(0.0, orientation) + distanceToEnd;
	      return obstaclePosition;
	    }
	    return obstaclePosition - fromPosition;
	}
	
	public Set<Vehicle> getCars() {
		return cars;
	}

	public void setNextRoad(Intersections road) {
		this.nextRoad = road;
	}

	public Double getEndPosition() {
		return endPosition;
	}


	public boolean remove(Vehicle car) {
		if(this.cars.contains(car)) {
			this.cars.remove(car);
			return true;
		} else {
			return false;
		}
	}


	public Intersections getNextRoad(Orientation orientation) {
		return nextRoad;
	}
	

}
