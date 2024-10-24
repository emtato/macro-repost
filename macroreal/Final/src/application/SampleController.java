/**
 *May 21, 2022
 * Description: Programme permettant de 
 */
package application;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

public class SampleController implements Initializable {

    @FXML
    private Button previousStep;

    @FXML
    private Button btnHelp;
    @FXML
    private ComboBox<String> actionChooser;

    @FXML
    private ComboBox<String> mouseDirectionChooser;

    @FXML
    private ComboBox<String> specialKeyChooser;

    @FXML
    private ComboBox<String> mouseButtonChooser;

    @FXML
    private TextField actionValue;

    @FXML
    private Button nextStep;

    @FXML
    private Text stepdisplay;

    @FXML
    private TextField numberOfSteps;

    @FXML
    private Button btnStart;

    @FXML
    private TextField repeatNumber;

    public ObservableList<String> list = FXCollections.observableArrayList("Move mouse", "Use mouse button",
	    "Type phrase", "Press special keys", "Wait");
    public ObservableList<String> mouseList = FXCollections.observableArrayList("← Move left", "↖ Move top left",
	    "↑ Move up", "↗ Move top right", "→ Move right", "↘ Move bottom right", "↓ Move down",
	    "↙ Move bottom left");
    public ObservableList<String> Specialkeys = FXCollections.observableArrayList("Press Delete", "Press Enter",
	    "Press release Enter", "Press release Delete", "Press Shift", "Press Command", "Press Space",
	    "Press Option", "Press tab", "Press escape", "Release Delete", "Release Enter", "Release Shift",
	    "Release Command", "Release Space", "Release Option", "Release tab", "Release escape", "Q", "Release Q",
	    "W", "Release W", "A", "Release A", "S", "Release S", "D", "Release D", "T", "Release T", "F", "Release F",
	    "C", "Release C", "V", "Release V", "Z", "Release Z");
    public ObservableList<String> mouseButtonsList = FXCollections.observableArrayList("Left Click",
	    "Press and release left click", "Right Click", "Press and release right click", "Scroll Wheel",
	    "Release Left", "Release Right");

    public void initialize(URL arg0, ResourceBundle arg1) {
	actionChooser.setItems(list);
	specialKeyChooser.setItems(Specialkeys);
    }

    String[] values = new String[0];
    String[] actions = new String[0];
    String[] mouseDirection = new String[0];
    String[] specialKeys = new String[0];
    String[] mouseButtons = new String[0];
    int currentNumberOfStepsValue = 0;
    boolean firstTime = true;

    public void stepsEntered() { // step counter + display
	try {
	    int newNumberOfStepsValue = Integer.parseInt((numberOfSteps.getText()));
	    int currentstep = Integer.parseInt(stepdisplay.getText());

	    System.out.println("Number of steps is " + newNumberOfStepsValue);

	    values = copyAndCreateStringArray(values, newNumberOfStepsValue);
	    actions = copyAndCreateStringArray(actions, newNumberOfStepsValue);
	    mouseDirection = copyAndCreateStringArray(mouseDirection, newNumberOfStepsValue);
	    specialKeys = copyAndCreateStringArray(specialKeys, newNumberOfStepsValue);
	    mouseButtons = copyAndCreateStringArray(mouseButtons, newNumberOfStepsValue);

	    if (currentstep > newNumberOfStepsValue) {
		stepdisplay.setText(String.valueOf(newNumberOfStepsValue));
		loadData();
	    }

	} catch (NumberFormatException e) {
	}
    }

    String[] copyAndCreateStringArray(String[] oldArray, int newSize) {
	int size = Math.min(newSize, oldArray.length);
	String[] newArray = new String[newSize];
	for (int i = 0; i < size; i++) {
	    newArray[i] = oldArray[i];
	}
	return newArray;
    }

    public void nextStep() { // step manager

	if (numberOfSteps.getText().isEmpty()) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setHeaderText("Incomplete arguments");
	    alert.setTitle("Input the number of steps");
	    alert.setContentText("if need help to use, read IMPORTANT.txt in \"Macro\" folder, or press ? button");
	    alert.show();
	    return;
	}
	String stringInput = (numberOfSteps.getText());
	int StepNumber = Integer.parseInt(stringInput);

	String stringstep = stepdisplay.getText();
	int currentstep = Integer.parseInt(stringstep);
	if (currentstep < StepNumber) {

	    int nextstep = currentstep + 1;
	    String stringnextstep = String.valueOf(nextstep);
	    stepdisplay.setText(String.valueOf(stringnextstep));
	    loadData();

	}

    }

    public void previousStep() {
	String stringstep = stepdisplay.getText();
	int currentstep = Integer.parseInt(stringstep);
	if (currentstep > 1) {

	    int nextstep = currentstep - 1;
	    String stringnextstep = String.valueOf(nextstep);
	    stepdisplay.setText(String.valueOf(stringnextstep));
	}
	loadData();

    }

    public void helpNeed() {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setHeaderText("Help menu");
	alert.setTitle("HOW TO USE:");
	alert.setContentText(
		"First enter the number of steps your automation has. A step can be a delay (ms) , moving mouse x pixels, pressing mouse, typing a string of text, or pressing special keys (like command, delete, enter)"
			+ "\n After pressing start, the program will start in 1 second"
			+ "\n \n To use Type, type the phrase in the value box after choosing \"Type phrase\" in action box"
			+ "\n  \n To use Special keys, chose special keys in actions box and chose the special key you want to use in the special keys box"
			+ "\n  \n to use move mouse, chose the direction to move the mouse in, as well as the number of pixels to move in that direction in the value box"
			+ "\n  \n to use wait, enter the number of milliseconds to wait in the value box"
			+ "\n \n no value is needed for \"use mouse button\" and \"Special keys\" "
			+ "\n \n to use mouse buttons, simply chose a button to press/drag. to use scroll wheel, enter a positive value to scroll up, and negative to scroll down."
			+ "\n \n finally, to repeat the program a certain amount of times, enter the number of repetitions in the bottom left box");
	alert.show();
	return;

    }

    public void actionChosen() {

	if (numberOfSteps.getText().isEmpty()) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setHeaderText("Incomplete arguments");
	    alert.setTitle("Input the number of steps");
	    alert.setContentText("if need help to use, read IMPORTANT.txt in \"Macro\" folder, or press ? button");
	    alert.show();
	    return;
	}
	String mouseSpecificationValue = mouseDirectionChooser.getValue();
	String mouseButtonValue = mouseButtonChooser.getValue();
	String specialKeyValue = specialKeyChooser.getValue();
	String value = actionValue.getText();
	String action = actionChooser.getValue();
	int currentstep = Integer.parseInt(stepdisplay.getText());
	values[currentstep - 1] = value;
	if (mouseSpecificationValue != null)
	    mouseDirection[currentstep - 1] = mouseSpecificationValue;
	actions[currentstep - 1] = action;
	if (mouseButtonValue != null)
	    mouseButtons[currentstep - 1] = mouseButtonValue;
	if (specialKeyValue != null)
	    specialKeys[currentstep - 1] = specialKeyValue;

	System.out.print(("values        "));
	System.out.println(Arrays.toString(values));
	System.out.print(("mouseDirection"));
	System.out.println(Arrays.toString(mouseDirection));
	System.out.print(("Special keys  "));
	System.out.println(Arrays.toString(specialKeys));
	System.out.print(("mouse buttons "));
	System.out.println(Arrays.toString(mouseButtons));
	System.out.println();

	switch (action) {
	case "Move mouse":
	    mouseDirectionChooser.setItems(mouseList);
	    mouseButtonChooser.setItems(null);

	    break;
	case "Use mouse button":
	    mouseButtonChooser.setItems(mouseButtonsList);
	    mouseDirectionChooser.setItems(null);
	    break;
	case "Press special keys":
	    values[currentstep - 1] = null;
	    mouseDirectionChooser.setItems(null);
	    specialKeyChooser.setItems(Specialkeys);
	    break;

	default:
	    mouseDirectionChooser.setItems(null);
	    break;
	}
	if (firstTime) {
	    Start();
	    firstTime = false;
	    System.out.println("error possible: " + firstTime);
	}

    }

    public void moveMouse(int currentstep) {
	try {
	    int inputValue = Integer.valueOf(values[currentstep]);
	    String direction = mouseDirection[currentstep];

	    double X = MouseInfo.getPointerInfo().getLocation().getX();
	    double Y = MouseInfo.getPointerInfo().getLocation().getY();
	    try {
		Thread.sleep(10);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	    int mouseX = (int) X;
	    int mouseY = (int) Y;
	    if (direction == "← Move left") { // y values inverted
		mouseX = mouseX - inputValue;
	    }
	    if (direction == "↑ Move up") {
		mouseY = mouseY - inputValue;
	    }
	    if (direction == "→ Move right") {
		mouseX = mouseX + inputValue;
	    }
	    if (direction == "↓ Move down") {
		mouseY = mouseY + inputValue;
	    }
	    if (direction == "↖ Move top left") {
		mouseX = mouseX - (inputValue / 2);
		mouseY = mouseY - (inputValue) / 2;
	    }
	    if (direction == "↗ Move top right") {
		mouseX = mouseX + (inputValue / 2);
		mouseY = mouseY - (inputValue) / 2;
	    }
	    if (direction == "↘ Move bottom right") {
		mouseX = mouseX + (inputValue / 2);
		mouseY = mouseY + (inputValue) / 2;
	    }
	    if (direction == "↙ Move bottom left") {
		mouseX = mouseX - (inputValue / 2);
		mouseY = mouseY + (inputValue) / 2;
	    }

	    Robot robot;
	    try {

		try {
		    Thread.sleep(10);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}

		robot = new Robot();
		robot.mouseMove(mouseX, mouseY);
	    } catch (AWTException e) {

		e.printStackTrace();
	    }
	    try {
		Thread.sleep(10);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }

	} catch (NumberFormatException e) {
	}

    }

    public void pressMouse(int currentstep) {

	String stringInputValue = mouseButtons[currentstep];
	try {
	    Robot robot = new Robot();
	    System.out.println(stringInputValue);
	    if (stringInputValue == "Left Click") {
		robot.mousePress(InputEvent.BUTTON1_MASK);
	    }
	    if (stringInputValue == "Press and release left click") {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	    }
	    if (stringInputValue == "Right Click") {
		robot.mousePress(InputEvent.BUTTON3_MASK);
	    }
	    if (stringInputValue == "Press and release right click") {
		robot.mousePress(InputEvent.BUTTON3_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_MASK);
	    }
	    if (stringInputValue == "Scroll Wheel") {
		robot.mouseWheel(Integer.valueOf(values[currentstep]));
	    }
	    if (stringInputValue == "Release Left") {
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	    }
	    if (stringInputValue == "Release Right") {
		robot.mouseRelease(InputEvent.BUTTON3_MASK);
	    }
	} catch (AWTException e) {
	    e.printStackTrace();
	}
    }

    public void type(String value) {

	char[] testArray = value.toCharArray();
	char[] specialCharacters = new char[testArray.length];
	String[] specialCharactersValue = new String[testArray.length];

	for (int commaPeriods = 0; commaPeriods < testArray.length; commaPeriods++) {
	    if (testArray[commaPeriods] == ',' || testArray[commaPeriods] == '!' || testArray[commaPeriods] == '.'
		    || testArray[commaPeriods] == '?' || testArray[commaPeriods] == '@'
		    || testArray[commaPeriods] == '#' || testArray[commaPeriods] == '$'
		    || testArray[commaPeriods] == '%' || testArray[commaPeriods] == '('
		    || testArray[commaPeriods] == ')' || testArray[commaPeriods] == '-'
		    || testArray[commaPeriods] == '+' || testArray[commaPeriods] == '1'
		    || testArray[commaPeriods] == '2' || testArray[commaPeriods] == '3'
		    || testArray[commaPeriods] == '4' || testArray[commaPeriods] == '5'
		    || testArray[commaPeriods] == '6' || testArray[commaPeriods] == '7'
		    || testArray[commaPeriods] == '8' || testArray[commaPeriods] == '9'
		    || testArray[commaPeriods] == '0' || testArray[commaPeriods] == ':'
		    || testArray[commaPeriods] == '=' || testArray[commaPeriods] == '\'') {
		specialCharacters[commaPeriods] = testArray[commaPeriods];
		System.out.println(Arrays.toString(specialCharacters));
		System.out.println("has special characters");
		specialCharactersValue[commaPeriods] = "yes";
	    }
	}
	try {
	    Robot robot = new Robot();
	    String InputType = "";
	    for (int e = 0; e < testArray.length; e++) {
		InputType = InputType + testArray[e];
	    }
	    String stringCaps = InputType.toUpperCase();

	    char[] chars = InputType.toCharArray();
	    char[] charsCaps = stringCaps.toCharArray();
	    try {
		Thread.sleep(1);
	    } catch (InterruptedException ex) {
		Thread.currentThread().interrupt();
	    }

	    for (int v = 0; v < charsCaps.length; v++) {
		if (specialCharactersValue[v] != "yes") {
		    if (chars[v] == charsCaps[v])
			robot.keyPress(KeyEvent.VK_SHIFT);
		    robot.keyPress(charsCaps[v]);
		    robot.keyRelease(charsCaps[v]);
		    robot.keyRelease(KeyEvent.VK_SHIFT);
		} else {
		    if (testArray[v] == '!') {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_1);
			robot.keyRelease(KeyEvent.VK_1);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		    }
		    if (testArray[v] == '?') {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_SLASH);
			robot.keyRelease(KeyEvent.VK_SLASH);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		    }
		    if (testArray[v] == '@') {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_2);
			robot.keyRelease(KeyEvent.VK_2);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		    }
		    if (testArray[v] == '#') {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_3);
			robot.keyRelease(KeyEvent.VK_3);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		    }
		    if (testArray[v] == '$') {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_4);
			robot.keyRelease(KeyEvent.VK_4);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		    }
		    if (testArray[v] == '%') {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_5);
			robot.keyRelease(KeyEvent.VK_5);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		    }
		    if (testArray[v] == '(') {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_9);
			robot.keyRelease(KeyEvent.VK_9);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		    }
		    if (testArray[v] == ')') {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_0);
			robot.keyRelease(KeyEvent.VK_0);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		    }
		    if (testArray[v] == '-') {
			robot.keyPress(KeyEvent.VK_MINUS);
			robot.keyRelease(KeyEvent.VK_MINUS);
			robot.keyPress(KeyEvent.VK_BACK_SPACE);
			robot.keyRelease(KeyEvent.VK_BACK_SPACE);
		    }
		    if (testArray[v] == '+') {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_EQUALS);
			robot.keyRelease(KeyEvent.VK_EQUALS);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		    }
		    if (testArray[v] == ':') {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_SEMICOLON);
			robot.keyRelease(KeyEvent.VK_SEMICOLON);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		    }
		    if (testArray[v] == '=') {
			robot.keyPress(KeyEvent.VK_EQUALS);
			robot.keyRelease(KeyEvent.VK_EQUALS);
			robot.keyPress(KeyEvent.VK_BACK_SPACE);
			robot.keyRelease(KeyEvent.VK_BACK_SPACE);
		    }
		    if (testArray[v] == '\'') {
			robot.keyPress(KeyEvent.VK_QUOTE);
			robot.keyRelease(KeyEvent.VK_QUOTE);
		    }

		    if (testArray[v] != '%' || testArray[v] != '=') {
			robot.keyPress(specialCharacters[v]);
			robot.keyRelease(specialCharacters[v]);
		    }
		}
	    }

	} catch (AWTException e) {
	    e.printStackTrace();
	}
    }

    public void specialKeys(String value) {
	Robot robot;
	try {
	    robot = new Robot();
	    if (value == "Press Delete")
		robot.keyPress(KeyEvent.VK_BACK_SPACE);
	    if (value == "Press Enter")
		robot.keyPress(KeyEvent.VK_ENTER);
	    if (value == "Press Shift")
		robot.keyPress(KeyEvent.VK_SHIFT);
	    if (value == "Press Command")
		robot.keyPress(KeyEvent.VK_META);
	    if (value == "Press Option")
		robot.keyPress(KeyEvent.VK_ALT);
	    if (value == "Press tab")
		robot.keyPress(KeyEvent.VK_TAB);
	    if (value == "Press escape")
		robot.keyPress(KeyEvent.VK_ESCAPE);

	    if (value == "Q")
		robot.keyPress(KeyEvent.VK_Q);
	    if (value == "W") {
		System.out.println("W");
		robot.keyPress(KeyEvent.VK_W);
	    }
	    if (value == "T")
		robot.keyPress(KeyEvent.VK_T);
	    if (value == "F")
		robot.keyPress(KeyEvent.VK_F);
	    if (value == "A")
		robot.keyPress(KeyEvent.VK_A);
	    if (value == "C")
		robot.keyPress(KeyEvent.VK_C);
	    if (value == "V")
		robot.keyPress(KeyEvent.VK_V);
	    if (value == "Z")
		robot.keyPress(KeyEvent.VK_Z);
	    if (value == "S")
		robot.keyPress(KeyEvent.VK_Z);
	    if (value == "D")
		robot.keyPress(KeyEvent.VK_Z);
	    if (value == "E")
		robot.keyPress(KeyEvent.VK_Z);

	    if (value == "Release Delete")
		robot.keyRelease(KeyEvent.VK_BACK_SPACE);
	    if (value == "Release Enter")
		robot.keyRelease(KeyEvent.VK_ENTER);
	    if (value == "Release Shift")
		robot.keyRelease(KeyEvent.VK_SHIFT);
	    if (value == "Release Command")
		robot.keyRelease(KeyEvent.VK_META);
	    if (value == "Release Option")
		robot.keyRelease(KeyEvent.VK_ALT);
	    if (value == "Release tab")
		robot.keyRelease(KeyEvent.VK_TAB);
	    if (value == "Press Space")
		robot.keyPress(KeyEvent.VK_SPACE);

	    if (value == "Release Space")
		robot.keyRelease(KeyEvent.VK_SPACE);
	    if (value == "Release W")
		robot.keyRelease(KeyEvent.VK_W);
	    if (value == "Release T")
		robot.keyRelease(KeyEvent.VK_T);
	    if (value == "Release F")
		robot.keyRelease(KeyEvent.VK_F);
	    if (value == "Release A")
		robot.keyRelease(KeyEvent.VK_A);
	    if (value == "Release C")
		robot.keyRelease(KeyEvent.VK_C);
	    if (value == "Release V")
		robot.keyRelease(KeyEvent.VK_V);
	    if (value == "Release Z")
		robot.keyRelease(KeyEvent.VK_Z);
	    if (value == "Release escape")
		robot.keyRelease(KeyEvent.VK_ESCAPE);
	    if (value == "Press release Enter") {
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	    }
	    if (value == "Release S")
		robot.keyRelease(KeyEvent.VK_Z);
	    if (value == "Release D")
		robot.keyRelease(KeyEvent.VK_Z);
	    if (value == "Release E")
		robot.keyRelease(KeyEvent.VK_Z);

	    if (value == "Press release delete") {
		robot.keyPress(KeyEvent.VK_BACK_SPACE);
		robot.keyRelease(KeyEvent.VK_BACK_SPACE);
	    }
	    try {
		Thread.sleep(5);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }

	} catch (AWTException e) {
	    e.printStackTrace();
	}

    }

    public void delay(String value) {
	int intValue = Integer.valueOf(value);
	try {
	    Thread.sleep(intValue);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

    }

    public void StartBtn() {
	try {
	    Thread.sleep(1000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	Start();
    }

    public void Start() {
	

	int numberOfTotalActions = actions.length;
	int cyclecount = 0;
	if (repeatNumber.getText().isEmpty()) {
	    cyclecount = 1;
	} else {
	    cyclecount = Integer.valueOf(repeatNumber.getText());
	}
	for (int cycle = 0; cycle < cyclecount; cycle++) {
	    try {
		Thread.sleep(1);
	    } catch (InterruptedException ex) {
		Thread.currentThread().interrupt();
	    }
	    for (int currentstep = 0; currentstep < numberOfTotalActions; currentstep++) {
		String action = actions[currentstep];
		if(action == "Move mouse")
		    moveMouse(currentstep);
		if(action == "Use mouse button")
		    pressMouse(currentstep);

		if(action == "Type phrase")
		    type(values[currentstep]);
		
		if(action == "Press special keys")
		    specialKeys(specialKeys[currentstep]);
		
		if(action == "Wait")
		    delay(values[currentstep]);
				

//		switch (action) {
//		case "Move mouse":
//		    moveMouse(currentstep);
//		    break;
//		case "Use mouse button":
//		    pressMouse(currentstep);
//		    break;
//		case "Type phrase":
//		    type(values[currentstep]);
//		    break;
//		case "Press special keys":
//		    specialKeys(specialKeys[currentstep]);
//		    break;
//		case "Wait":
//		    delay(values[currentstep]);
//		    break;
//		default:
//		    System.out.println("Not supported");
//		    break;
		//}
	    }
	}

    }

    public void Loop() {

    }

    public void loadData() {
	String stringstep = stepdisplay.getText();
	int currentstep = Integer.parseInt(stringstep);
	if (values[currentstep - 1] == null) {
	    actionValue.setText(String.valueOf(""));
	} else {
	    actionValue.setText(String.valueOf(values[currentstep - 1]));
	}
	if (actions[currentstep - 1] != null) {
	    actionChooser.getSelectionModel().select(actions[currentstep - 1]);
	}
	if (specialKeys[currentstep - 1] != null) {
	    specialKeyChooser.getSelectionModel().select(specialKeys[currentstep - 1]);
	}
	if (String.valueOf(actionChooser.getValue()) == "Move mouse") {
	    mouseButtonChooser.setItems(null);
	    mouseButtonChooser.getSelectionModel().select(null);
	    mouseDirectionChooser.getSelectionModel().select(mouseDirection[currentstep - 1]);
	}
	if (String.valueOf(actionChooser.getValue()) == "Use mouse button") {
	    mouseDirectionChooser.setItems(null);
	    mouseButtonChooser.getSelectionModel().select(null);
	    mouseButtonChooser.getSelectionModel().select(mouseButtons[currentstep - 1]);
	}
	String actionType = String.valueOf(actionChooser.getValue());
	if (actionType == "Type phrase" | actionType == "Wait") {
	    mouseDirectionChooser.setItems(null);
	    mouseButtonChooser.getSelectionModel().select(null);
	    mouseButtonChooser.setItems(null);
	    mouseButtonChooser.getSelectionModel().select(null);
	    specialKeyChooser.setItems(null);
	    specialKeyChooser.getSelectionModel().select(null);

	}
	if (actionType == "Press special keys") {
	    specialKeyChooser.setItems(Specialkeys);
	    specialKeyChooser.getSelectionModel().select(specialKeys[currentstep - 1]);

	}

    }
}
