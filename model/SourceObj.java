package myproject.model;

import myproject.model.ObjBuilder.Orientation;
import myproject.util.SettingsBag;
import myproject.util.TimeServer;


final class SourceObj implements Agent, Source {

	private Double carDelay;
	private CarHandler nextRoad;
	private TimeServer time;
	private Orientation orient;
	private SettingsBag sb = SettingsBag.makeSettingsBag();

	SourceObj(Orientation orientation) {
		this.carDelay = Math.random() * sb.getCarGenerationDelayMax();
		this.carDelay = Math.max(sb.getCarGenerationDelayMin(), this.carDelay);
		this.time = this.sb.getTimeServer();
		this.time.enqueue(this.time.currentTime(), this);
		this.orient = orientation;
	}
	
	public void setNextRoad(CarHandler road) {
		this.nextRoad = road;
	}

	public void run(double time) {
		Car c = ObjBuilder.makeCar(orient);
		if(this.nextRoad == null) {
			throw new NullPointerException("Next Road Was Not Set");
		}
		Boolean blocked = false;
		for(Vehicle b : this.nextRoad.getCars()) {
			if(b.getFrontPosition() <= c.getCarLength() + c.getStopDistance()) {
				blocked = true;
			}
		}
		if(blocked == false) {
			this.nextRoad.accept(c, 0.0);
			this.time.enqueue(this.time.currentTime() + this.sb.getTimeStep(), c);
		}
		this.time.enqueue(this.time.currentTime() + this.carDelay, this);
	}
	
	
	
}
