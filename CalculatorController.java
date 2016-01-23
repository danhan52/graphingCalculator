/**
 * CalculatorController.java
 * 
 * CS 204 Final Project- Graphing Calculator
 * 
 * @author  Abby Lewis, Jamaica Lammi, Danny Hanson, Sam Keyes
 * 
 * Class for the controller of the calculator, it is 
 * comparable to the Observer class in Java.
 *
 */

public class CalculatorController {
	private CalculatorModel calcModel;

	/**
	 * Creates a new instance of the CalculatorModel.
	 */
	public CalculatorController() {
		calcModel = new CalculatorModel();
	}

	/**
	 * Evaluates the equation once "Enter" is pushed, 
	 * graphs the equation if "Graph" is pushed, or 
	 * just interprets the given action
	 */
	public String[] update(String action) {
		if (action.equals("Graph")) {
			return calcModel.evaluateGraph();
		}
		return calcModel.performAction(action);
	}
}