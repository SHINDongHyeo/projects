package mvc.exception;

public class OutOfStockException extends Exception {

	public OutOfStockException() {
		super();
	}
	
	public OutOfStockException(String msg) {
		super(msg);
	}
}
