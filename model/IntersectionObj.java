package myproject.model;

import java.util.HashSet;
import java.util.Set;

import myproject.model.Vehicle;
import myproject.model.ObjBuilder.LightState;
import myproject.model.ObjBuilder.Orientation;
import myproject.util.SettingsBag;

final class IntersectionObj implements Intersections {
	private Set<Vehicle> carsNS;
	private Set<Vehicle> carsEW;
	private Double endPosition;
	private CarHandler nextRoadNS;
	private CarHandler nextRoadEW;
	private LightObj light;
	private SettingsBag sb = SettingsBag.makeSettingsBag();

	IntersectionObj() {
		this.endPosition = Math.random() * this.sb.getIntersectionLengthMax();
		this.endPosition = Math.max(this.endPosition, this.sb.getIntersectionLengthMin());
		this.carsNS = new HashSet<Vehicle>();
		this.carsEW = new HashSet<Vehicle>();
		this.light = ObjBuilder.makeLight();
	}

	public boolean accept(Vehicle c, Double frontPosition) {
		if (c.getOrientation() == Orientation.NS) {
			if (frontPosition > this.endPosition) {
				return this.nextRoadNS.accept(c, frontPosition - this.endPosition);
			} else {
				c.setIntersection(this);
				c.setFrontPosition(frontPosition);
				this.carsNS.add(c);
				return true;
			}
		} else {
			if (frontPosition > this.endPosition) {
				return this.nextRoadEW.accept(c, frontPosition - this.endPosition);
			} else {
				c.setIntersection(this);
				c.setFrontPosition(frontPosition);
				this.carsEW.add(c);
				return true;
			}
		}
	}

	public Double distanceToObstacle(Double fromPosition, Orientation orientation) {
		if (orientation == Orientation.NS) {
			if (this.light.getLightState() == LightState.NSGREEN || this.light.getLightState() == LightState.NSRED) {
				double obstaclePosition = this.distanceToCarBack(fromPosition, this.carsNS);
				if (obstaclePosition == Double.POSITIVE_INFINITY) {
					double distanceToEnd = this.endPosition - fromPosition;
					obstaclePosition = nextRoadNS.distanceToObstacle(0.0, Orientation.NS) + distanceToEnd;
				}
				return obstaclePosition - fromPosition;
			} else {
				return 0.0;
			}
		} else {
			if (this.light.getLightState() == LightState.EWGREEN || this.light.getLightState() == LightState.EWRED) {
				double obstaclePosition = this.distanceToCarBack(fromPosition, this.carsEW);
				if (obstaclePosition == Double.POSITIVE_INFINITY) {
					double distanceToEnd = this.endPosition - fromPosition;
					obstaclePosition = nextRoadEW.distanceToObstacle(0.0, Orientation.EW) + distanceToEnd;
				}
				return obstaclePosition - fromPosition;
			} else {
				return 0.0;
			}
		}
	}

	private Double distanceToCarBack(Double fromPosition, Set<Vehicle> cars) {
		double carBackPosition = Double.POSITIVE_INFINITY;
		for (Vehicle c : cars)
			if (c.getBackPosition() >= fromPosition && c.getBackPosition() < carBackPosition)
				carBackPosition = c.getBackPosition();
		return carBackPosition;
	}

	public void setNextRoad(CarHandler nextRoad, Orientation orientation) {
		if(orientation == Orientation.NS) {
			this.nextRoadNS = nextRoad;
			return;
		}
		this.nextRoadEW = nextRoad;
	}

	public CarHandler getNextRoad(Orientation orientation) {
		if(orientation == Orientation.NS) {
			return this.nextRoadNS;
		}
		return this.nextRoadEW;
	}

	public Double getEndPosition() {
		return this.endPosition;
	}

	public LightObj getLight() {
		return this.light;
	}

	public boolean remove(Vehicle car) {
		if(this.carsEW.contains(car)) {
			this.carsEW.remove(car);
			return true;
		}
		if(this.carsNS.contains(car)) {
			this.carsNS.remove(car);
			return true;
		} else {
			return false;
		}
	}

	public Orientation getOrientation() {
		throw new UnsupportedOperationException();
	}


}
