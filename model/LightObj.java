package myproject.model;

import myproject.model.ObjBuilder.Orientation;
import myproject.model.ObjBuilder.LightState;
import myproject.util.SettingsBag;
import myproject.util.TimeServer;

/**
 * A light has a boolean state.
 */
public class LightObj implements Agent, Light {

	private SettingsBag sb = SettingsBag.makeSettingsBag();
	private TimeServer time;
	private boolean state;
	private LightState lightState;
	private Long greenTimeNS;
	private Long yellowTimeNS;
	private Long greenTimeEW;
	private Long yellowTimeEW;
	
	public LightObj() {
		this.time = this.sb.getTimeServer();

		this.greenTimeNS = Math.round(Math.random() * sb.getTrafficLightGreenTimeMax());
		this.greenTimeNS = Math.round(Math.max(sb.getTrafficLightGreenTimeMin(), this.getGreenTimeNS()));
		this.greenTimeEW = Math.round(Math.random() * sb.getTrafficLightGreenTimeMax());
		this.greenTimeEW = Math.round(Math.max(sb.getTrafficLightGreenTimeMin(), this.getGreenTimeEW()));

		this.yellowTimeNS = Math.round(Math.random() * sb.getTrafficLightRedTimeMax());
		this.yellowTimeNS = Math.round(Math.max(sb.getTrafficLightRedTimeMin(), this.getYellowTimeNS()));
		this.yellowTimeEW = Math.round(Math.random() * sb.getTrafficLightRedTimeMax());
		this.yellowTimeEW = Math.round(Math.max(sb.getTrafficLightRedTimeMin(), this.getYellowTimeEW()));

		this.lightState = LightState.EWGREEN;
		this.state = true;
		this.time.enqueue(this.time.currentTime() + this.greenTimeEW, this);
	} 

	public void run(double time) {
		switch (this.lightState) {
		case EWGREEN:	this.lightState = LightState.EWRED;
		this.time.enqueue(this.time.currentTime() + this.yellowTimeEW, this);
		break;
		case EWRED:	this.lightState = LightState.NSGREEN;
		this.time.enqueue(this.time.currentTime() + this.greenTimeNS, this);
		this.state = !this.state;
		break;
		case NSGREEN:	this.lightState = LightState.NSRED;
		this.time.enqueue(this.time.currentTime() + this.yellowTimeNS, this);
		break;
		case NSRED:	this.lightState = LightState.EWGREEN;
		this.time.enqueue(this.time.currentTime() + this.greenTimeEW, this);
		this.state = !this.state;
		break;	
		default:	this.lightState = LightState.EWGREEN;
		this.time.enqueue(this.time.currentTime() + this.greenTimeEW, this);
		break;
		}
	}

	public LightState getLightState() {
		return lightState;
	}
	public void setLightState(LightState state) {
		this.lightState = state;
	}
	public boolean getState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public Long getGreenTimeNS() {
		return greenTimeNS;
	}
	public void setGreenTimeNS(Long greenTime) {
		this.greenTimeNS = greenTime;
	}
	public Long getGreenTimeEW() {
		return greenTimeEW;
	}
	public void setGreenTimeEW(Long greenTime) {
		this.greenTimeEW = greenTime;
	}
	public Long getYellowTimeNS() {
		return yellowTimeNS;
	}
	public void setYellowTimeNS(Long yellowTime) {
		this.yellowTimeNS = yellowTime;
	}
	public Long getYellowTimeEW() {
		return yellowTimeEW;
	}
	public void setYellowTimeEW(Long yellowTime) {
		this.yellowTimeEW = yellowTime;
	}

	public Orientation getOrientation() {
		if (this.lightState == LightState.EWGREEN || this.lightState == LightState.EWRED) {
			return Orientation.EW;
		}
		return Orientation.NS;
	}

}

