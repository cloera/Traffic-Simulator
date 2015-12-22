package myproject.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;

import myproject.model.CarHandler;
import myproject.model.ObjBuilder.Orientation;
import myproject.util.Animator;
import myproject.util.SettingsBag;
import myproject.util.TimeServer;



/**
 * An example to model for a simple visualization.
 * The model contains roads organized in a matrix.
 * See {@link #Model(AnimatorBuilder, Integer, Integer)}.
 */
public class Model extends Observable {
	private Animator animator;
	private boolean disposed;
	private TimeServer time;
	private SettingsBag sb;

	/** Creates a model to be visualized using the <code>builder</code>.
	 *  If the builder is null, no visualization is performed.
	 *  The number of <code>rows</code> and <code>columns</code>
	 *  indicate the number of {@link LightObj}s, organized as a 2D
	 *  matrix.  These are separated and surrounded by horizontal and
	 *  vertical {@link Road}s.  For example, calling the constructor with 1
	 *  row and 2 columns generates a model of the form:
	 *  <pre>
	 *     |  |
	 *   --@--@--
	 *     |  |
	 *  </pre>
	 *  where <code>@</code> is a {@link LightObj}, <code>|</code> is a
	 *  vertical {@link Road} and <code>--</code> is a horizontal {@link Road}.
	 *  Each road has one {@link Car}.
	 *
	 *  <p>
	 *  The {@link AnimatorBuilder} is used to set up an {@link
	 *  Animator}.
	 *  {@link AnimatorBuilder#getAnimator()} is registered as
	 *  an observer of this model.
	 *  <p>
	 */
	public Model(AnimatorBuilder builder, Integer rows, Integer columns) {
		this.sb = SettingsBag.makeSettingsBag();
		this.time = this.sb.getTimeServer();
		if (rows < 0 || columns < 0 || (rows == 0 && columns == 0)) {
			throw new IllegalArgumentException();
		}
		if (builder == null) {
			builder = new NullAnimatorBuilder();
		}
		setup(builder, rows, columns);
		animator = builder.getAnimator();
		super.addObserver(animator);
		this.time.addObserver(animator);
	}

	/**
	 * Run the simulation for <code>duration</code> model seconds.
	 */
	public void run(double duration) {
		if (disposed)
			throw new IllegalStateException();
		this.time.run(this.sb.getRunTime());
		super.setChanged();
		super.notifyObservers();

	}

	/**
	 * Throw away this model.
	 */
	public void dispose() {
		animator.dispose();
		disposed = true;
	}

	/**
	 * Construct the model, establishing correspondences with the visualizer.
	 */
	private void setup(AnimatorBuilder builder, Integer rows, Integer columns) {
		List<CarHandler> roads = new ArrayList<CarHandler>();
		Intersections[][] intersections = new Intersections[rows][columns];

		// Add Lights
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				intersections[i][j] = ObjBuilder.makeIntersection();
				builder.addLight(intersections[i][j], i, j);
				time.enqueue(this.time.currentTime(), intersections[i][j].getLight());
			}
		}

		// Add Horizontal Roads
		boolean eastToWest = false;
		for (int i=0; i<rows; i++) {
			Source carsource = ObjBuilder.makeSource(Orientation.EW);
			if (eastToWest) {
				for (int j=columns; j>=0; j--) {
					CarHandler l = ObjBuilder.makeRoad();
					if (j == columns) {
						carsource.setNextRoad(l);
						l.setNextRoad(intersections[i][j-1]);
					}
					else if (j == 0) {
						intersections[i][j].setNextRoad(l, Orientation.EW);
						l.setNextRoad(ObjBuilder.makeSink());
					}
					else {
						intersections[i][j].setNextRoad(l, Orientation.EW);
						l.setNextRoad(intersections[i][j-1]);
					}

					builder.addHorizontalRoad(l, i, j, eastToWest);
					roads.add(l);
				}
			}
			else {
				for (int j=0; j<=columns; j++) {
					CarHandler l = ObjBuilder.makeRoad();
					if (j == 0) {
						carsource.setNextRoad(l);
						l.setNextRoad(intersections[i][j]);
					}
					else if (j == columns) {
						intersections[i][j-1].setNextRoad(l, Orientation.EW);
						l.setNextRoad(ObjBuilder.makeSink());
					}
					else {
						intersections[i][j-1].setNextRoad(l, Orientation.EW);
						l.setNextRoad(intersections[i][j]);
					}
					builder.addHorizontalRoad(l, i, j, eastToWest);
					roads.add(l);
				}
			}
			eastToWest = !eastToWest;
		}

		// Add Vertical Roads
		boolean southToNorth = false;
		for (int j=0; j<columns; j++) {
			Source carsource = ObjBuilder.makeSource(Orientation.NS);
			if (southToNorth) {
				for (int i=rows; i>=0; i--) {
					CarHandler l = ObjBuilder.makeRoad();
					if (i == rows) {
						carsource.setNextRoad(l);
						l.setNextRoad(intersections[i-1][j]);
					}
					else if (i == 0) {
						intersections[i][j].setNextRoad(l, Orientation.NS);
						l.setNextRoad(ObjBuilder.makeSink());
					}
					else {
						intersections[i][j].setNextRoad(l, Orientation.NS);
						l.setNextRoad(intersections[i-1][j]);
					}

					builder.addVerticalRoad(l, i, j, southToNorth);
					roads.add(l);
				}
			}
			else {
				for (int i=0; i<=rows; i++) {
					CarHandler l = ObjBuilder.makeRoad();
					if (i == 0) {
						carsource.setNextRoad(l);
						l.setNextRoad(intersections[i][j]);	
					}
					else if (i == rows) {
						intersections[i-1][j].setNextRoad(l, Orientation.NS);
						l.setNextRoad(ObjBuilder.makeSink());
					}
					else {
						intersections[i-1][j].setNextRoad(l, Orientation.NS);
						l.setNextRoad(intersections[i][j]);
					}

					builder.addVerticalRoad(l, i, j, southToNorth);
					roads.add(l);
				}
			}
			southToNorth = !southToNorth;
		}
	}
}
