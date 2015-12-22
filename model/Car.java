package myproject.model;

import myproject.util.TimeServer;
import myproject.model.ObjBuilder.Orientation;
import myproject.util.SettingsBag;

/**
 * A car remembers its position from the beginning of its road. Cars have random
 * velocity and random movement pattern: when reaching the end of a road, the
 * dot either resets its position to the beginning of the road, or reverses its
 * direction.
 */
public class Car implements Agent, Vehicle {

	private Double length;
	private Double velocity;
	private Double frontPosition;
	private Double stopDistance;
	private Double brakeDistance;
	private Double timeStep;
	private Orientation orientation;
	private boolean atIntersection;
	private Intersections currentIntersection;
	private CarHandler currentRoad;
	private TimeServer time;
	private java.awt.Color color;
	private Integer roadSegmentsTraversed;
	private SettingsBag sb = SettingsBag.makeSettingsBag();

	Car(Orientation orientation) {
		this.length = Math.random() * sb.getCarLengthMax();
		this.length = Math.max(sb.getCarLengthMin(), this.length);

		this.velocity = Math.random() * sb.getCarMaxVelocityMax();
		this.velocity = Math.max(sb.getCarMaxVelocityMin(), this.velocity);

		this.stopDistance = Math.random() * sb.getCarStopDistanceMax();
		this.stopDistance = Math.max(sb.getCarStopDistanceMin(), this.stopDistance);
		this.stopDistance = Math.max(sb.getCarLengthMax() / 2, this.stopDistance);

		this.brakeDistance = Math.random() * sb.getCarBrakeDistanceMax();
		this.brakeDistance = Math.max(sb.getCarBrakeDistanceMin(), this.brakeDistance);
		this.brakeDistance = Math.max(this.stopDistance, this.brakeDistance);

		this.color = new java.awt.Color(100,191,220);
		this.timeStep = sb.getTimeStep();
		this.frontPosition = 0.0;
		this.roadSegmentsTraversed = 0;

		this.orientation = orientation;
		this.time = this.sb.getTimeServer();
	}

	public void run(double time) {

		double newVelocity = getNewVelocity();
		setFrontPosition(newVelocity);
		this.time.enqueue(this.time.currentTime() + this.timeStep, this);

	}

	public void setRoad(CarHandler c) {
		this.currentRoad = c;
		this.atIntersection = false;
	}

	public void setFrontPosition(Double frontPosition) {
		Double endOfRoad;
		if (this.atIntersection) {
			endOfRoad = this.currentIntersection.getEndPosition();
		} else {
			endOfRoad = this.currentRoad.getEndPosition();
		}
		if (frontPosition > endOfRoad) {
			if (this.atIntersection) {
				Intersections newIntersection = this.currentIntersection;
				this.currentIntersection.getNextRoad(this.orientation).accept(this, frontPosition - endOfRoad);
				newIntersection.remove(this);
				this.roadSegmentsTraversed++;
				return;
			} else {
				CarHandler road = this.currentRoad;
				this.currentRoad.getNextRoad(this.orientation).accept(this, frontPosition - endOfRoad);
				road.remove(this);
				this.roadSegmentsTraversed++;
				return;
			}
		} else {
			this.frontPosition = frontPosition;
		}
	}

	public void setIntersection(Intersections intersectionCarIsIn) {
		this.currentIntersection = intersectionCarIsIn;
		this.atIntersection = true;
	}

	public Double getBackPosition() {
		return this.frontPosition - this.length;
	}

	private double getNewVelocity() {
		Double newVelocity;
		Double distanceToObstacle;

		if (this.atIntersection) {
			distanceToObstacle = this.currentIntersection.distanceToObstacle(this.frontPosition, this.orientation);
		} else {
			distanceToObstacle = this.currentRoad.distanceToObstacle(this.frontPosition, this.orientation);
		}
		if (distanceToObstacle == Double.POSITIVE_INFINITY) {
			return this.frontPosition + this.velocity * this.timeStep;
		}
		if (distanceToObstacle < this.velocity
				&& (distanceToObstacle > this.brakeDistance || distanceToObstacle > this.stopDistance))
			newVelocity = distanceToObstacle / 2;
		else {
			newVelocity = (this.velocity / (this.brakeDistance - this.stopDistance))
					* (this.currentRoad.distanceToObstacle(this.frontPosition, this.orientation) - this.stopDistance);
		}
		newVelocity = Math.max(0.0, newVelocity);
		newVelocity = Math.min(this.velocity, newVelocity);
		Double nextFrontPosition = this.frontPosition + newVelocity * this.timeStep;
		return nextFrontPosition;
	}

	public Double getFrontPosition() {
		return this.frontPosition;
	}

	public CarHandler getRoad() {
		return this.currentRoad;
	}

	public Double getCarLength() {
		return this.length;
	}

	public Double getStopDistance() {
		return this.stopDistance;
	}

	public double getRoadSegmentsTraversed() {
		return this.roadSegmentsTraversed;
	}

	public java.awt.Color getColor() {
		return color;
	}

	public Double getBrakeDistance() {
		return this.brakeDistance;
	}

	public Double getVelocity() {
		return this.velocity;
	}

	public Double getTimeStep() {
		return this.timeStep;
	}


	public Orientation getOrientation() {
		return this.orientation;
	}


}
