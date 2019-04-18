package guiLife;

public class PatternFormatException extends Exception{
public PatternFormatException(){
	super("Invalid Format!!!!");
}
public PatternFormatException(String message) {
	super(message);
}
}
