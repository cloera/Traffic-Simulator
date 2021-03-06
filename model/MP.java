package myproject.model;


/**
 * Static class for model parameters.
 */
public class MP {
	private MP() {}

	/** Length of cars, in meters */
	public static double carLength = 10;
	/** Length of roads, in meters */
	public static double roadLength =  200;
	/** Maximum car velocity, in meters/second */
	public static double maxVelocity = 6;
	public static double brakeDistance = 10.0;
	public static double stopDistance = 1.0;
	public static double timeStep = 0.5;
	public static double carDelay = 10.0;
}

