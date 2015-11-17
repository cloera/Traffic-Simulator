package myproject.model;

final class Sink implements Intersections {

	Sink() {}

	public boolean accept(Car c, double frontPosition) {
		return true;
	}

	public void setNextRoad(CarHandler nextRoad) {
		return;
	}

	public CarHandler getNextRoad() {
		return null;
	}

	public double distanceToObstacle(double d) {
		return Double.POSITIVE_INFINITY;
	}

	public Light getLight() {
		return null;
	}


	public Double getEndPosition() {

		return null;
	}


	public boolean remove(Car car) {

		return false;
	}

}
