import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import java.util.*;

/**
 * CalculatorModel.java
 * 
 * CS 204 Final Project- Graphing Calculator
 * 
 * @author  Abby Lewis, Jamaica Lammi, Danny Hanson, Sam Keyes
 * 
 * Class for implementing the correct button actions for the 
 * calculator.  This class is the model, so it contains 
 * everything the calculator needs to know about calculations.
 *
 */

public class CalculatorModel {
	private ArrayList<String> currentEq;
	private ScriptEngine solver;
	private int size;

	/**
	 * Constructor creates a script engine to solve equations, 
	 * then initializes an ArrayList and sets the size to zero.
	 */
	public CalculatorModel() {
		ScriptEngineManager mgr = new ScriptEngineManager();
		solver = mgr.getEngineByName("JavaScript");
		currentEq = new ArrayList<String>();
		size = 0;
	}

	/**
	 * Adds the correct symbol to the equation based on the String
	 * parameter action and returns an Array of Strings.
	 */
	public String[] performAction(String action) {
		// Initializes an Array of Strings of size 2
		String[] finalEquation = new String[2];
		
		// If the input is "Enter" evaluate the equation
		if (action.equals("Enter")) {
			return this.evaluate();
		}	
		
		// basic operators
		else if (action.equals("+")) {
			this.plus();
		}
		else if (action.equals("-")) {
			this.minus();
		}
		else if (action.equals("*")) {
			this.times();
		}
		else if (action.equals("/")) {
			this.divide();
		}
		else if (action.equals("(-)")) {
			this.negative();
		}
		
		// parentheses
		else if (action.equals("(")) {
			this.openParen();
		}
		else if (action.equals(")")) {
			this.closeParen();
		}
		
		// trigonometric operations
		else if (action.equals("sin()")) {
			this.sin();
		}
		else if (action.equals("cos()")) {
			this.cos();
		}
		else if (action.equals("tan()")) {
			this.tan();
		}
		else if (action.equals("pi")) {
			this.pi();
		}
		
		// logarithms
		else if (action.equals("ln()")) {
			this.ln();
		}
		else if (action.equals("e")) {
			this.e();
		}
		
		// powers
		else if (action.equals("x^2")) {
			this.square();
		}
		else if (action.equals("sqrt")) {
			this.sqrt();
		}
		else if (action.equals("^")) {
			this.power();
		}
		
		// delete
		else if (action.equals("Delete")) {
			this.delete();
		}
		// clear
		else if (action.equals("Clear")) {
			this.clear();
		}
		else if (action.equals("<html>"+"Clear"+"<br>"+"All"+"<html>")) {
			this.clear();
		}
		else if (action.equals("<html>"+"Clear"+"<br>"+"Graph"+"<html>")) {
			this.clear();
		}
		// decimal point
		else if (action.equals(".")) {
			this.decimal();
		}
		// digits
		else if (action.matches("[0-9]")) {
			this.number(action);
		}
		// variable x
		else if (action.matches("x")) {
			this.number(action);
		}
		
		// Returns the displayable form of the equation
		finalEquation[0]=equationToNiceForm(this.copyEquation());
		return finalEquation;
	}
	
	/**
	 * Clears the current equation by making a new ArrayList of Strings 
	 * of size 0.
	 */
	public void clear() {
		currentEq = new ArrayList<String>();
		size = 0;
	}
	
	/**
	 * Deletes the most recent character in the equation
	 */
	public void delete() {
		if (currentEq.size() > 0) {
			String temp = currentEq.get(size-1);
			// Ensures only one digit is deleted
			if (isNum(temp)) {
				if (temp.length()>1) {
					currentEq.set(size-1, temp.substring(0,temp.length()-1));
				} else {
					currentEq.remove(size-1);
					size--;
				}
			} 
			// Ensures that the entire power function is deleted
			else if (temp.charAt(0)==',') {
				currentEq.remove(size-1);
				int i = 2;
				while (!currentEq.get(size-i).equals("Math.pow(")) {
					i++;
				}
				currentEq.remove(size-i);
				size-=2;
			} 
			// Otherwise delete the most recent thing entered
			else {
				currentEq.remove(size-1);
				size--;
			}
		}
	}

	/**
	 * Adds a digit to the equation
	 */
	public void number(String num) {
		if (size>0) {
			String temp = currentEq.get(size-1);
			if (isNum(temp)) {
				currentEq.set(size-1, temp+num);
			} else {
				currentEq.add(num);
				size++;
			}
		} else {
			currentEq.add(num);
			size++;
		}
	}
	
	/**
	 * Adds a decimal point to the equation
	 */
	public void decimal() {
		if (size>0) {
			String temp = currentEq.get(size-1);
			if (isNum(temp)) {
				currentEq.set(size-1, temp+".");
			} else {
				currentEq.add(".");
				size++;
			}
		} else {
			currentEq.add(".");
			size++;
		}
	}

	
	/**
	 * The next few functions add the basic operators
	 */
	
	public void plus() {
		currentEq.add("+");
		size++;
	}
	public void minus() {
		currentEq.add("-");
		size++;
	}
	public void times() {
		currentEq.add("*");
		size++;
	}
	public void divide() {
		currentEq.add("/");
		size++;
	}
	public void negative() {
		currentEq.add("(-1)*");
		size++;
	}

	/**
	 * The next few functions add the parentheses
	 */ 
	public void openParen() {
		currentEq.add("(");
		size++;
	}
	public void closeParen() {
		currentEq.add(")");
		size++;
	}

	
	/**
	 * The next few functions add the trigonometric operations
	 */
	
	public void sin() {
		currentEq.add("Math.sin(");
		size++;
	}
	public void cos() {
		currentEq.add("Math.cos(");
		size++;
	}
	public void tan() {
		currentEq.add("Math.tan(");
		size++;
	}
	public void pi() {
		currentEq.add("Math.PI");
		size++;
	}

	
	/**
	 * The next few functions add the logarithms
	 */
	public void ln() {
		currentEq.add("Math.log(");
		size++;
	}
	public void e() {
		currentEq.add("Math.E");
		size++;
	}
	
	
	/**
	 * Adds the "x" variable
	 */
	public void x() {
		currentEq.add("x");
		size++;
	}

	/**
	 * Adds the sqrt function
	 */
	public void sqrt() {
		currentEq.add("Math.sqrt(");
		size++;
	}
	
	/**
	 * Adds the "square" function
	 */
	public void square() {
		// Checks for different cases to figure out where to add "Math.pow(" and ",2)"
		if (size>0) {
			String temp = currentEq.get(size-1);
			Stack<String> tempStack = new Stack<String>();
			if (isNum(temp) || temp.equals("x")) {
				currentEq.add(size-1, "Math.pow(");
				currentEq.add(",2)");
				size+=2;
			} else if (temp.equals(")")) {
				tempStack.push(")");
				int i = 2;
				while (!tempStack.empty()) {
					String temp2 = currentEq.get(size-i);
					if (temp2.equals(")")) {
						tempStack.push(")");
					} else if (temp2.equals("(")) {
						tempStack.pop();
					} else if (temp2.matches("Math.+[(]")) {
						tempStack.pop();
					}
					i ++;
				}
				currentEq.add(size-i, "Math.pow(");
				currentEq.add(",2)");
				size+=2;
			} else if (temp.matches("Math.(E|(PI))")) {
				currentEq.add(size-1, "Math.pow(");
				currentEq.add(",2)");
				size+=2;
			}
		}
	}
	
	/**
	 * Adds the power function
	 */
	public void power() {
		// Checks for different cases to figure out where to add "Math.pow(" and ","
		if (size>0) {
			String temp = currentEq.get(size-1);
			Stack<String> tempStack = new Stack<String>();
			if (isNum(temp) || temp.equals("x")) {
				currentEq.add(size-1, "Math.pow(");
				currentEq.add(",");
				size+=2;
			} else if (temp.equals(")")) {
				tempStack.push(")");
				int i = 2;
				while (!tempStack.empty()) {
					String temp2 = currentEq.get(size-i);
					if (temp2.equals(")")) {
						tempStack.push(")");
					} else if (temp2.equals("(")) {
						tempStack.pop();
					} else if (temp2.matches("Math.+[(]")) {
						tempStack.pop();
					}
					i ++;
				}
				i--;
				currentEq.add(size-i, "Math.pow(");
				currentEq.add(",");
				size+=2;
			} else if (temp.matches("Math.(E|(PI))")) {
				currentEq.add(size-1, "Math.pow(");
				currentEq.add(",");
				size+=2;
			}
		}
	}

	/**
	 * Used so we can get a copy of the current equation, 
	 * it returns an ArrayList of Strings which represent 
	 * the equation.
	 */
	public ArrayList<String> copyEquation() {
		return (ArrayList<String>) currentEq.clone();
	}

	/**
	 * Evaluates the equation and returns an Array of Strings 
	 * where the first entry is the displayable form of the equation, 
	 * and the second entry is the answer.
	 */
	public String[] evaluate() {
		String[] evaluatedEquation = new String[2];
		
		// Creates a displayable form of the equation
		String displayableEquation = equationToNiceForm(copyEquation());
		evaluatedEquation[0] = displayableEquation;
		
		// Makes the equation readable by a ScriptEngine
		String readableEquation = javascriptEquation();
		String fixedParen = parenthesesChecker(readableEquation);
		
		// Rounds to 6 decimal points
		String withRounding = "Math.round(1000000*(" + fixedParen + "))/1000000";
		String tempSolution = "";
		
		// Equation is evaluated here and catches any errors
		try {
			tempSolution = solver.eval(withRounding).toString();
		} catch (Exception e) {
			tempSolution = "Error";
		}
		evaluatedEquation[1] = tempSolution;
		
		// Resets the equation to size 0
		currentEq = new ArrayList<String>();
		size=0; 
		return evaluatedEquation;
	}

	/************************************
	 * SECTION CHANGE: GRAPH MODEL HERE *
	 ***********************************/
	
	/**
	 * Evaluates the equation over the range x = -10 to x = 10, 
	 * and returns an Array of Strings which contain the y-values 
	 * for the graph.
	 */
	public String[] evaluateGraph() {
		String[] solutionArray = new String[600];
		
		// Gets the equation to a form that the ScriptEngine can read
		String readableEquation = javascriptEquation();
		String fixedParen = parenthesesChecker(readableEquation);
		String scaledEquation = "30*(" + fixedParen + ")";
		
		// Loops through possible x values
		for (int i =-300; i<300; i++) {
			// Scales x value so it fits with 20 by 20 grid
			double scaleFactor = i*1/30.0;
			
			// Solves equations at a given x value
			String graphedEq = replaceX(scaledEquation, Double.toString(scaleFactor));
			String tempSolution = "";
			try {
				tempSolution = solver.eval(graphedEq).toString();
			} catch (Exception e) {
				tempSolution = "0";
			}
			solutionArray[i+300] = tempSolution;
		}
		
		// Resets current equation
		currentEq = new ArrayList<String>();
		size=0; 
		return solutionArray;
	}
	
	/******************
	 * HELPER METHODS *
	 *****************/
	
	/**
	 * Returns a String with all x's in the parameter equation with a number
	 */
	private String replaceX(String equation, String num){
		String output = new String(equation);
		
		// Loops through the output equation and if an "x" is found replace it with the correct number
		for (int i=0;i<output.length();i++){
			if (output.charAt(i) == 'x'){
				String firstPart = output.substring(0, i);
				String secondPart = output.substring(i+1);
				output = "";
				output = output.concat(firstPart);
				output = output.concat(num);
				output = output.concat(secondPart);
			}	
		}
		return output;
	}

	/**
	 * Checks if a value is a number and returns a boolean
	 */
	private boolean isNum(String nm) {
		if (nm.matches("[0-9]+.?[0-9]*")) {
			return true;
		} 
		return false;
	}
	
	/**
	 * Converts the current equation to readable form for a ScriptEngine
	 */
	private String javascriptEquation() {
		String currentEquation = "";
		for (int i = 0; i<size; i++) {
			if (i<size-1) {
				if (isNum(currentEq.get(i)) && currentEq.get(i+1).matches("Math.+")) {
					currentEq.add(i+1, "*");
					size++;
				}
			}
			currentEquation += currentEq.get(i);
		}
		return currentEquation;
	}
	
	/**
	 * Converts the equation to a displayable form for text areas
	 */
	private String equationToNiceForm(ArrayList<String> eq) {
		String currentEquation = "";
		for (int i = 0; i<eq.size(); i++) {
			if (i<eq.size()-1) {
				if (isNum(eq.get(i)) && eq.get(i+1).matches("Math.+")) {
					eq.add(i+1, "*");
				}
			} if (eq.get(i).equals("Math.pow(")) {
				eq.remove(i);
			} if (eq.get(i).matches("Math.+")) {
				String replace = eq.get(i).substring(5);
				eq.set(i, replace);
			} if (eq.get(i).equals(",")) {
				eq.set(i, "^(");
			} if (eq.get(i).equals(",2)")) {
				eq.set(i, "^2");
			} if (eq.get(i).equals("(-1)*")) {
				eq.set(i, "(-)");
			} 
			currentEquation += eq.get(i);
		}
		return currentEquation;
	}
	
	/**
	 * Adds in all missing parentheses
	 */
	private String parenthesesChecker(String checkedEq) {
		String withParens = new String(checkedEq);
		Stack<String> parenStack = new Stack<String>();
		for (int i=0; i<checkedEq.length(); i++) {
			if (withParens.charAt(i) == '(') {
				parenStack.push("off cliff");
			}
			if (withParens.charAt(i) == ')' && !parenStack.empty()) {
				parenStack.pop();
			}
		}
		while (!parenStack.empty()) {
			withParens += ")";
			parenStack.pop();
		}
		return withParens;
	}
}