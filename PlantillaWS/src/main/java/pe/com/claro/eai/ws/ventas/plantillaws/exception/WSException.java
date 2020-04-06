package pe.com.claro.eai.ws.ventas.plantillaws.exception;

public class WSException extends BaseException {

	private static final long serialVersionUID = 7070437867800228547L;

	public WSException() {
		super();
	}

	public WSException(Exception objException) {
		super(objException);
	}

	public WSException(String msjError) {
		super(msjError);
	}
	public WSException(String codError, String msjError, Exception objException) {
		super(codError, msjError, objException);
	}

	public WSException(String codError, String msjError, String nombreWS, Exception objException) {
		super(codError, msjError, nombreWS, objException);
	}
}
