package myproject.model;

import myproject.model.ObjBuilder.LightState;

public interface Light {
	public LightState getLightState(); 
	public void setLightState(LightState state);
	public boolean getState();
	public void setState(boolean state);
	public Long getGreenTimeNS();
	public void setGreenTimeNS(Long greenTime);
	public Long getGreenTimeEW();
	public void setGreenTimeEW(Long greenTime);
	public Long getYellowTimeNS();
	public void setYellowTimeNS(Long yellowTime);
	public Long getYellowTimeEW();
	public void setYellowTimeEW(Long yellowTime);
}
