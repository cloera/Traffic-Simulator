package myproject.model;

import myproject.model.ObjBuilder.Orientation;

final class Sink implements Intersections {

	Sink() {}

	public boolean accept(Vehicle c, Double frontPosition) {
		return true;
	}

	public void setNextRoad(CarHandler nextRoad, Orientation orientation) {
		throw new UnsupportedOperationException();
	}

	public CarHandler getNextRoad(Orientation orientation) {
		throw new UnsupportedOperationException();
	}

	public Double distanceToObstacle(Double d, Orientation orientation) {
		return Double.POSITIVE_INFINITY;
	}

	public LightObj getLight() {
		return null;
	}


	public Double getEndPosition() {
		return Double.POSITIVE_INFINITY;
	}


	public boolean remove(Vehicle car) {
		throw new UnsupportedOperationException();
	}

	public Orientation getOrientation() {
		throw new UnsupportedOperationException();
	}
}
