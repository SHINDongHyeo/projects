package mvc.exception;

@SuppressWarnings("serial")
public class DataNotFoundException extends Exception {
	
	public DataNotFoundException() {
		super();
	}
	
	public DataNotFoundException(String msg) {
		super(msg);
	}
}
