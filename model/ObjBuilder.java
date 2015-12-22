package myproject.model;

public class ObjBuilder {
	
	public enum Orientation {
		NS, EW
	}
	
	public enum LightState {
		NSGREEN, NSRED, EWGREEN, EWRED
	}
	
	public ObjBuilder() {}
	
	static public final Car makeCar(Orientation orientation) {
		return new Car(orientation);
	}
	
	static public final Road makeRoad() {
		return new Road();
	}
	
	static public final Intersections makeIntersection() {
		return new IntersectionObj();
	}
	
	static public final Intersections makeSink() {
		return new Sink();
	}
	
	static public final Source makeSource(Orientation orientation) {
		return new SourceObj(orientation);
	}
	
	static public final LightObj makeLight() {
		return new LightObj();
	}
}
