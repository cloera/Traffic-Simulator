package myproject.model;

import myproject.model.ObjBuilder.Orientation;

public interface Intersections {
	public boolean accept(Vehicle c, Double frontPosition);
	public void setNextRoad(CarHandler nextRoad, Orientation orientation);
	public CarHandler getNextRoad(Orientation orientation);
	public Double distanceToObstacle(Double d, Orientation orientation);
	public LightObj getLight();
	public Orientation getOrientation();
	public Double getEndPosition();
	public boolean remove(Vehicle car);
}
