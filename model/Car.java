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
	private Intersections intersection;
	private CarHandler currentRoad;
	private TimeServer agents;
	private java.awt.Color color;

	
	
	Car() { 
		this.position = 0;
		this.length = MP.carLength;
		this.velocity = (int) Math.ceil(Math.random() * MP.maxVelocity);
		this.frontPosition = 0.0;
		this.stopDistance = MP.carLength/2;
		this.brakeDistance = this.stopDistance;
		this.timeStep = MP.timeStep;
		this.agents = new TimeServerList();
		this.color = new java.awt.Color((int)Math.ceil(Math.random()*155 + 100), 
				(int)Math.ceil(Math.random()*155 + 100), 
					(int)Math.ceil(Math.random()*155 + 100));
	}
	

	public void run(double time) {
				
		double newVelocity = getNewVelocity();
		setFrontPosition(newVelocity);
		
		if(currentRoad.accept(this, getFrontPosition()))
			agents.enqueue(agents.currentTime() + timeStep, this);
		//if ((position + velocity) > (MP.roadLength-MP.carLength))
			//position = 0;
	}
	
	
	void setCurrentRoad(CarHandler c) {
		this.currentRoad = c;
	}
	
	void setFrontPosition(double frontPosition) {
		this.frontPosition = frontPosition;
	}
	
	void setCurrentIntersection(Intersections intersection) {
		this.intersection = intersection;
	}
	
	double backPosition() {
		return frontPosition-length;
	}
	
	private Double getNewVelocity() {
		Double newVelocity;
		Double distanceToObstacle = currentRoad.distanceToObstacle(frontPosition);

		if (distanceToObstacle == Double.POSITIVE_INFINITY) {
			return frontPosition + velocity * timeStep;
		}
		
		if (distanceToObstacle < velocity && 
				(distanceToObstacle > brakeDistance || distanceToObstacle > stopDistance))
			newVelocity = distanceToObstacle / 2;
		else {
			newVelocity = (velocity / (brakeDistance - stopDistance))
					* (currentRoad.distanceToObstacle(frontPosition) - stopDistance);
		}
		newVelocity = Math.max(0.0, newVelocity);
		newVelocity = Math.min(velocity, newVelocity);
		Double nextFrontPosition = frontPosition + newVelocity * timeStep;
		return nextFrontPosition;
	}

	public double getFrontPosition() {
		return frontPosition;
	}
	
	public double getPosition() {
		return position;
	}
	
	public java.awt.Color getColor() {
		return color;
	}
	
}
