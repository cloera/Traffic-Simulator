package myproject.model;

public interface Intersections {
	public boolean accept(Car c, double frontPosition);
	public void setNextRoad(CarHandler nextRoad);
	public CarHandler getNextRoad();
	public double distanceToObstacle(double d);
	public Light getLight();
	public Double getEndPosition();
	public boolean remove(Car car);
}
