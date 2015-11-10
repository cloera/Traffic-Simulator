package myproject.model;

/**
 * A car remembers its position from the beginning of its road.
 * Cars have random velocity and random movement pattern:
 * when reaching the end of a road, the dot either resets its position
 * to the beginning of the road, or reverses its direction.
 */
public class Car implements Agent {
	Car() { } // Created only by this package

	private double position = 0;
	private double velocity = (int) Math.ceil(Math.random() * MP.maxVelocity);
	private java.awt.Color color = new java.awt.Color((int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255));

	private int sleepSeconds;
	private double firstPosition;
	private Road currentRoad;
	private TimeServer agents;
	
	public double getPosition() {
		return position;
	}
	
	public java.awt.Color getColor() {
		return color;
	}
	
	public void run(double time) {
		double maxVelocity = 30.0; 
		double brakeDistance = 10.0;
		double stopDistance = 1.0;
		
		double newFirstPosition = firstPosition + velocity * time;
		
		if(currentRoad.accept(this, newFirstPosition))
			agents.enqueue(agents.currentTime() + sleepSeconds, this);
		//if ((position + velocity) > (MP.roadLength-MP.carLength))
			//position = 0;
		//position += velocity;
	}
	
	void setCurrentRoad(Road r) {
		this.currentRoad = r;
	}
	
	void setFirstPosition(double firstPosition) {
		this.firstPosition = firstPosition;
	}

	public double getFirstPosition() {
		return firstPosition;
	}
	
}
