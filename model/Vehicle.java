package myproject.model;

import java.awt.Color;

import myproject.model.ObjBuilder.Orientation;

public interface Vehicle {
	public CarHandler getRoad();
	public Double getFrontPosition();
	public void setFrontPosition(Double position);
	public Double getBackPosition();
	public void setRoad(CarHandler roadCarIsOn);
	public void setIntersection(Intersections intersectionCarIsIn);
	public Color getColor(); 
	public Double getCarLength(); 
	public Double getBrakeDistance(); 
	public Double getVelocity(); 
	public Double getStopDistance(); 
	public Double getTimeStep(); 
	public double getRoadSegmentsTraversed();
	public Orientation getOrientation();
}
