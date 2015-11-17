package myproject.model;

final class SourceObj implements Agent, Source {

	private CarHandler nextRoad;
	private TimeServer time;

	SourceObj() {
		this.time = new TimeServerList();
		this.time.enqueue(this.time.currentTime(), this);

	}
	
	public void setNextRoad(CarHandler road) {
		this.nextRoad = road;
	}

	public void run(double time) {
		Car c = new Car();
		if(this.nextRoad == null) {
			throw new NullPointerException();
		}
		Boolean blocked = false;
		for(Car b : this.nextRoad.getCars()) {
			if(b.getFrontPosition() <= c.getCarLength() + c.getStopDistance()) {
				blocked = true;
			}
		}
		if(blocked == false) {
			this.nextRoad.accept(c, 0.0);
			this.time.enqueue(this.time.currentTime() + MP.timeStep, c);
		}
		this.time.enqueue(this.time.currentTime() + MP.carDelay, this);
	}
	
	
	
}
