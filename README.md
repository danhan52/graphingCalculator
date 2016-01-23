---------------------------------------------------------------
| README                                                      |
| Graphing Calculator Project                                 |
| CS 204: Danny Hanson, Sam Keyes, Jamaica Lammi, Abby Lewis  |
---------------------------------------------------------------

How to use the program:
- Compile and run the file Calculator.java
- Use the keyboard on the right panel to type in numbers, operands, and other symbols to make equations. 
- Select between the tabs "Equations" and "Graph" to move between the two displays. When using the "Equations" tab, hit the Enter key to submit 
	the equation for calculation. Hit the Graph button to graph the equation as long as the "Graph" tab is visible.
- The Clear button will erase all characters typed into the upper text box, while Delete will remove the last character. Clear All will erase 
	the entries in the Current Equation box, the Previous Equations box, and the graph.
- All other buttons and functions work similarly to a standard calculator. 
- The scale for the graph is a 20 by 20 box surrounding the origin. The axes are bolded and every gridline is 1 unit apart.


Things to be aware of:
- Remember to close parentheses. The calculator will automatically close them if forgotten, but it might not be in the same manner as was intended 
	(unmatched parentheses will all be added at the end of the equation).
- When using the x variable in Graph Mode, be sure to to enter neighboring operands specifically. That is, multiplying 4 by x should be entered 
	as "4 * x" and not "4x".
- If a graphed equation's domain is not All Real Numbers, the graph will automatically interpret the y-values for x-values outside the domain as zero.
- The window size is fixed. Y-values that are outside the range of a 20x20 box around the origin will not display.
- The grid will redraw after every re-graph, therefore any graph lines on the grid will be covered by grid lines.
- We made the design decision to have two blank spaces for buttons at the end of the buttonPanel as we feel the readability stayed the same and we thought 
	it was a better route to go than either adding useless buttons or reformatting the panel.
- A few times we got a mistake while trying to graph where the graph printed on the wrong side.  It was over the buttons instead of next to them.  
	We believe we have fixed this mistake though.
- In one trial the graph display showed up but was unable to graph anything, but we have been unable to replicate the error.


The program's structure:
- The program was written using the Model-View-Controller design pattern, with a class for each 
	(named CalculatorModel.java, CalculatorView.java, CalculatorController.java) - as well as one class Calculator.java that contains a Main() method. 
- The Model is split into two main sections: one for the generic calculator functions and another for those specific to graphing equations. 
	The Model also contains all methods for calculations. It parses through the equation entry implementing a JavaScript reader that automatically 
	interprets the answer from the given string.
- The View handles all display methods, from managing the layout of the panels to drawing the graphs and displaying equation text.
- The Controller hands some of the information back and forth between the model and view. It also instantiates the CalculatorModel.
	We decided to not use Java's Observable class because it seemed unnecessary for our implementation as we created our own update() methods
	that do similar things.  
- The functionality of this calculator was provided for people who want to do basic math functions and basic graphing of equations.