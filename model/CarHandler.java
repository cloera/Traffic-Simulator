package myproject.model;

import java.util.Set;


public abstract interface CarHandler {
	public boolean accept(Car c, double frontPosition);
	public double distanceToObstacle(double fromPosition);
	public Set<Car> getCars();
	void setCurrentIntersection(Intersections intersection);
	public Double getEndPosition();
	public boolean remove(Car car);
	public Intersections getNextRoad();
}
