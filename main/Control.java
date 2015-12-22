package myproject.main;

import myproject.model.Model;

import properties.PropertyBag;
import ui.UI;
import ui.UIError;
import ui.UIForm;
import ui.UIFormBuilder;
import ui.UIFormTest;
import ui.UIMenu;
import ui.UIMenuAction;
import ui.UIMenuBuilder;
import myproject.model.swing.SwingAnimatorBuilder;


class Control {
	private static final int EXITED = 0;
	private static final int EXIT = 1;
	private static final int START = 2;
	private static final int SUBMENU = 3;
	private static final int NUMSTATES = 10;
	private UIMenu[] _menus;
	private int _state;
	PropertyBag propertyBag = properties.PropertyBag.makePropertyBag();

	private UIFormTest _numberTest;

	private UI _ui;

	Control(UI ui) {
		_ui = ui;

		_menus = new UIMenu[NUMSTATES];
		_state = START;
		addSTART(START);
		addSUBMENU(SUBMENU);
		addEXIT(EXIT);

		_numberTest = new UIFormTest() {
			public boolean run(String input) {
				try {
					Double.parseDouble(input);
					return true;
				} catch (NumberFormatException e) {
					return false;
				}
			}
		};
	}

	void run() {
		try {
			while (_state != EXITED) {
				_ui.processMenu(_menus[_state]);
			}
		} catch (UIError e) {
			_ui.displayError("UI closed");
		}
	}

	private Double buildDoubleForm(String title, String prompt) {
		UIFormBuilder form = new UIFormBuilder();
		form.add(title, _numberTest);

		UIForm _getForm = form
				.toUIForm(prompt);
		String[] result1 = _ui.processForm(_getForm);
		Double newValue = Double.parseDouble(result1[0]);

		return newValue;
	}

	private Integer buildIntegerForm(String title, String prompt) {
		UIFormBuilder form = new UIFormBuilder();
		form.add(title, _numberTest);

		UIForm _getForm = form
				.toUIForm(prompt);
		String[] result1 = _ui.processForm(_getForm);
		Integer newValue = Integer.parseInt(result1[0]);

		return newValue;
	}

	private void addSTART(int stateNum) {
		UIMenuBuilder m = new UIMenuBuilder();

		m.add("Default", new UIMenuAction() {
			public void run() {
				_ui.displayError("doh!");
			}
		});
		m.add("Run Simulation", new UIMenuAction() {
			public void run() {
				Model model = new Model(new SwingAnimatorBuilder(), propertyBag.getGridRow(), propertyBag.getGridColumn());
				model.run(propertyBag.getRunTime());
				model.dispose();
				_state = START;

//								Model model = new Model(new TextAnimatorBuilder(), propertyBag.getGridRow(), propertyBag.getGridColumn());
//								model.run(propertyBag.getRunTime());
//								model.dispose();
//								_state = START;
			}
		});
		m.add("Change Simulation Parameters", new UIMenuAction() {
			public void run() {
				_state = SUBMENU;
			}
		});
		m.add("Exit", new UIMenuAction() {
			public void run() {
				_state = EXIT;
			}
		});

		_menus[stateNum] = m.toUIMenu("Main Menu");
	}

	private void addSUBMENU(int stateNum) {
		UIMenuBuilder m = new UIMenuBuilder();

		m.add("Default", new UIMenuAction() {
			public void run() {
				_ui.displayError("doh!");
			}
		});
		m.add("Show current values", new UIMenuAction() {
			public void run() {
				System.out.println(propertyBag.toString());
			}
		});
		m.add("Simulation time step", new UIMenuAction() {
			public void run() {
				Double newValue = buildDoubleForm("Time Step", "Enter New Value:");
				propertyBag.setTimeStep(newValue);
			}


		});
		m.add("Simulation run time", new UIMenuAction() {
			public void run() {
				Double newValue = buildDoubleForm("Run Time", "Enter New Value:");
				propertyBag.setRunTime(newValue);
			}
		});
		m.add("Grid size", new UIMenuAction() {
			public void run() {
				Integer newRowValue = buildIntegerForm("Grid Rows", "Enter New Value:");
				propertyBag.setGridRow(newRowValue);

				Integer newColumnValue = buildIntegerForm("Grid Columns", "Enter New Value:");
				propertyBag.setGridColumn(newColumnValue);
			}
		});
		m.add("Car entry rate", new UIMenuAction() {
			public void run() {
				String titleMax = "Car Entry Rate Max";
				String titleMin = "Car Entry Rate Min";
				String prompt = "Enter New Value";

				Double newValueMax = buildDoubleForm(titleMax, prompt);
				propertyBag.setCarGenerationDelayMax(newValueMax);

				Double newValueMin = buildDoubleForm(titleMin, prompt);
				propertyBag.setCarGenerationDelayMin(newValueMin);	
			}
		});
		m.add("Road segment length", new UIMenuAction() {
			public void run() {
				String titleMax = "Road Length Max";
				String titleMin = "Road Length Min";
				String prompt = "Enter New Value";

				Double newValueMax = buildDoubleForm(titleMax, prompt);
				propertyBag.setRoadSegmentLengthMax(newValueMax);

				Double newValueMin = buildDoubleForm(titleMin, prompt);
				propertyBag.setRoadSegmentLengthMin(newValueMin);		
			}
		});
		m.add("Intersection length", new UIMenuAction() {
			public void run() {
				String titleMax = "Intersection Length Max";
				String titleMin = "Intersection Length Min";
				String prompt = "Enter New Value";

				Double newValueMax = buildDoubleForm(titleMax, prompt);
				propertyBag.setIntersectionLengthMax(newValueMax);

				Double newValueMin = buildDoubleForm(titleMin, prompt);
				propertyBag.setIntersectionLengthMin(newValueMin);			
			}
		});
		m.add("Car length", new UIMenuAction() {
			public void run() {
				String titleMax = "Car Length Max";
				String titleMin = "Car Length Min";
				String prompt = "Enter New Value";

				Double newValueMax = buildDoubleForm(titleMax, prompt);
				propertyBag.setCarLengthMax(newValueMax);

				Double newValueMin = buildDoubleForm(titleMin, prompt);
				propertyBag.setCarLengthMin(newValueMin);	
			}
		});
		m.add("Car maximum velocity", new UIMenuAction() {
			public void run() {
				String titleMax = "Car Maximum Velocity Max";
				String titleMin = "Car Minimum Velocity Min";
				String prompt = "Enter New Value";

				Double newValueMax = buildDoubleForm(titleMax, prompt);
				propertyBag.setCarMaxVelocityMax(newValueMax);

				Double newValueMin = buildDoubleForm(titleMin, prompt);
				propertyBag.setCarMaxVelocityMin(newValueMin);
			}
		});
		m.add("Car stop distance", new UIMenuAction() {
			public void run() {
				String titleMax = "Car Stop Distance Max";
				String titleMin = "Car Stop Distance Min";
				String prompt = "Enter New Value";

				Double newValueMax = buildDoubleForm(titleMax, prompt);
				propertyBag.setCarStopDistanceMax(newValueMax);

				Double newValueMin = buildDoubleForm(titleMin, prompt);
				propertyBag.setCarStopDistanceMin(newValueMin);	
			}
		});
		m.add("Car brake distance", new UIMenuAction() {
			public void run() {
				String titleMax = "Car Brake Distance Max";
				String titleMin = "Car Brake Distance Min";
				String prompt = "Enter New Value";

				Double newValueMax = buildDoubleForm(titleMax, prompt);
				propertyBag.setCarBrakeDistanceMax(newValueMax);

				Double newValueMin = buildDoubleForm(titleMin, prompt);
				propertyBag.setCarBrakeDistanceMin(newValueMin);			
			}
		});
		m.add("Traffic light green time", new UIMenuAction() {
			public void run() {
				String titleMax = "Traffic Light Green Time Max";
				String titleMin = "Traffic Light Green Time Min";
				String prompt = "Enter New Value";

				Double newValueMax = buildDoubleForm(titleMax, prompt);
				propertyBag.setTrafficLightGreenTimeMax(newValueMax);

				Double newValueMin = buildDoubleForm(titleMin, prompt);
				propertyBag.setTrafficLightYellowTimeMin(newValueMin);		
			}
		});
		m.add("Traffic light yellow time", new UIMenuAction() {
			public void run() {
				String titleMax = "Traffic Light Yellow Time Max";
				String titleMin = "Traffic Light Yellow Time Min";
				String prompt = "Enter New Value";

				Double newValueMax = buildDoubleForm(titleMax, prompt);
				propertyBag.setTrafficLightYellowTimeMax(newValueMax);

				Double newValueMin = buildDoubleForm(titleMin, prompt);
				propertyBag.setTrafficLightYellowTimeMin(newValueMin);		
			}
		});
		m.add("Reset simulation and return to the main menu",
				new UIMenuAction() {
			public void run() {
				_state = START;
			}
		});

		_menus[stateNum] = m.toUIMenu("Settings Menu");

	}

	private void addEXIT(int stateNum) {
		UIMenuBuilder m = new UIMenuBuilder();

		m.add("Default", new UIMenuAction() {
			public void run() {
			}
		});
		m.add("Yes", new UIMenuAction() {
			public void run() {
				_state = EXITED;
			}
		});
		m.add("No", new UIMenuAction() {
			public void run() {
				_state = START;
			}
		});

		_menus[stateNum] = m.toUIMenu("Are you sure you want to exit?");
	}
}