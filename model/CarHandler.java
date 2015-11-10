package myproject.model;

import java.util.Set;

interface CarHandler {
	public boolean accept(Car c, double frontPosition);
	public double distanceToObstacle(double fromPosition);
	public Set<Car> getCars();
}
