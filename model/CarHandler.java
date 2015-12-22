package myproject.model;

import java.util.Set;

import myproject.model.ObjBuilder.Orientation;


public abstract interface CarHandler {
	public boolean accept(Vehicle c, Double frontPosition);
	public Double distanceToObstacle(Double fromPosition, Orientation orientation);
	public Set<Vehicle> getCars();
	public void setNextRoad(Intersections road);
	public Double getEndPosition();
	public boolean remove(Vehicle car);
	public Intersections getNextRoad(Orientation orientation);
}
