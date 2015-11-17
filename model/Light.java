package myproject.model;

/**
 * A light has a boolean state.
 */
public class Light implements Agent {

	public enum LightState {
		GREEN, RED
	}
	private LightState state;
	private TimeServer time;
	private long lightTime;
	
	Light() { 
		this.lightTime = 4;
		this.time = new TimeServerList();
		this.state = LightState.GREEN;
		this.time.enqueue(this.time.currentTime() + this.lightTime, this);
	}
	
	public LightState getState() {
		return state;
	}
	
	public void setState(LightState newState) {
		this.state = newState;
	}
	
	public void run(double time) {
		switch(this.state) {
		case GREEN:
			this.state = LightState.RED;
			this.time.enqueue(this.time.currentTime() + this.lightTime, this);
			break;
		case RED:
			this.state = LightState.GREEN;
			this.time.enqueue(this.time.currentTime() + this.lightTime, this);
			break;
		default:
			this.state = LightState.GREEN;
			this.time.enqueue(this.time.currentTime() + this.lightTime, this);
			break;
		}
		
		/*
		if(state != null) {
			if(state == LightState.GREEN) {
				state = LightState.RED;
				this.time.enqueue(this.time.currentTime() + lightTime, this);
			} else {
				state = LightState.GREEN;
				this.time.enqueue(this.time.currentTime() + lightTime, this);
			}
		}
		*/
	}


}

