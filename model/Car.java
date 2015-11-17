package myproject.model;


/**
 * A car remembers its position from the beginning of its road.
 * Cars have random velocity and random movement pattern:
 * when reaching the end of a road, the dot either resets its position
 * to the beginning of the road, or reverses its direction.
 */
public class Car implements Agent {

	private double position;
	private double length;
	private double velocity;
	private double frontPosition;
	private double stopDistance;
	private double brakeDistance;
	private double timeStep;
	private boolean atIntersection;
	private Intersections intersection;
	private CarHandler currentRoad;
	private TimeServer time;
	private java.awt.Color color;

	
	
	Car() { 
		this.position = 0;
		this.length = MP.carLength;
		this.velocity = (int) Math.ceil(Math.random() * MP.maxVelocity);
		this.frontPosition = 0.0;
		this.stopDistance = MP.carLength/2;
		this.brakeDistance = this.stopDistance;
		this.timeStep = MP.timeStep;
		this.time = new TimeServerList();
		this.color = new java.awt.Color((int)Math.ceil(Math.random()*155 + 100), 
				(int)Math.ceil(Math.random()*155 + 100), 
					(int)Math.ceil(Math.random()*155 + 100));
	}
	

	public void run(double time) {
				
		double newVelocity = getNewVelocity();
		setFrontPosition(newVelocity);
		this.time.enqueue(this.time.currentTime() + this.timeStep, this);

	}
	
	
	void setRoad(CarHandler c) {
		this.currentRoad = c;
		this.atIntersection = false;
	}
	
	void setFrontPosition(double frontPosition) {
		Double endOfRoad;
		if(this.atIntersection){
			endOfRoad = this.intersection.getEndPosition();
		} else {
			endOfRoad = this.currentRoad.getEndPosition();
		}
		if(frontPosition > endOfRoad) {
			if(this.atIntersection) {
				Intersections newIntersection = this.intersection;
				this.intersection.getNextRoad().accept(this, frontPosition - endOfRoad);
				newIntersection.remove(this);
				return;
			} else {
				CarHandler road = this.currentRoad;
				this.currentRoad.getNextRoad().accept(this, frontPosition - endOfRoad);
				road.remove(this);
				return;
			}
		} else {
			this.frontPosition = frontPosition;
		}
	}
	
	void setIntersection(Intersections intersection) {
		this.intersection = intersection;
		this.atIntersection = true;
	}
	
	double backPosition() {
		return this.frontPosition-this.length;
	}
	
	private Double getNewVelocity() {
		Double newVelocity;
		Double distanceToObstacle;

		if(atIntersection) {
			distanceToObstacle = this.intersection.distanceToObstacle(this.frontPosition);
		} else {
			distanceToObstacle = this.currentRoad.distanceToObstacle(this.frontPosition);
		}
		
		if (distanceToObstacle == Double.POSITIVE_INFINITY) {
			return this.frontPosition + this.velocity * this.timeStep;
		}
		
		if (distanceToObstacle < this.velocity && (distanceToObstacle > this.brakeDistance || distanceToObstacle > this.stopDistance))
			newVelocity = distanceToObstacle / 2;
		else {
			newVelocity = (this.velocity / (this.brakeDistance - this.stopDistance)) * (this.currentRoad.distanceToObstacle(this.frontPosition) - this.stopDistance);
		}
		newVelocity = Math.max(0.0, newVelocity);
		newVelocity = Math.min(this.velocity, newVelocity);
		Double nextFrontPosition = this.frontPosition + newVelocity * this.timeStep;
		return nextFrontPosition;
	}

	public double getFrontPosition() {
		return this.frontPosition;
	}
	
	public CarHandler getRoad() {
		return this.currentRoad;
	}
	
	public double getCarLength() {
		return this.length;
	}
	
	public double getStopDistance() {
		return this.stopDistance;
	}
	
	public double getPosition() {
		return this.position;
	}
	
	public java.awt.Color getColor() {
		return color;
	}
	
}
